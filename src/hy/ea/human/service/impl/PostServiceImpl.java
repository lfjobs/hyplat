package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.*;
import hy.ea.bo.human.salary.SalaryData;
import hy.ea.human.dao.PostDao;
import hy.ea.human.dao.SalaryLevelDao;
import hy.ea.human.service.PostService;
import hy.ea.human.service.StaffLevelAllocationService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Resource
    private PostDao postDao;
    @Resource
    private ServerService serverService;
    @Resource
    private SalaryLevelDao salaryLevelDao;
    @Resource
    private StaffLevelAllocationService staffLevelAllocationService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CLogBookService logBookService;
    @Override
    public String getDeployList(DepartmentPost departmentPost, String search, Integer pageNumber, Integer pageSize) {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        PageForm pageForm = postDao.getDeployList(departmentPost,search,pageNumber,pageSize,companyId);
        JSONObject data = new JSONObject();
        data.put("post", pageForm);
        Map<String,String> organization = postDao.getOrganizationByCompanyId(companyId);
        data.put("organization", organization);
        return JSONObject.fromObject(data).toString();
    }

    @Override
    public PageForm getPostListByDeptIds(String deptIds, Integer pageNumber, Integer pageSize) {
        String[] deptIdArr = new String[]{};
        if(!"".equals(deptIds)){
            deptIdArr = deptIds.split("\\,");
        }
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        return postDao.getPostListByDeptIds(deptIdArr,pageNumber,pageSize,companyId);
    }

    @Override
    public PageForm getNeedJoinStaffData(String companyId, Integer pageNumber, Integer pageSize) {
        return postDao.getNeedJoinStaffData(companyId,pageNumber,pageSize);

    }

    /**
     * 在职员工分配岗位
     *
     * @return
     */
    public String savePersonPostAllocation(COS cos,DepartmentPost departmentPost,Audition entity){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            List<BaseBean> beans = new ArrayList<BaseBean>();
            ActionContext actionContext = ActionContext.getContext();
            Map<String, Object> session = actionContext.getSession();
            CAccount account = (CAccount) session.get("account");
            String companyId = account.getCompanyID();
            HttpServletRequest request = ServletActionContext.getRequest();
            String staffId = request.getParameter("staffID");
            String staffName = request.getParameter("staffName");
            String postName = request.getParameter("postName");
            String postId = departmentPost.getDepPostID();
            String deptName = request.getParameter("deptName");
            String status = cos.getStatus();

            String hql_audition = "from Audition where  companyID = ? and staffID = ? ";
            Audition entity1 = (Audition) baseBeanService.getBeanByHqlAndParams(
                    hql_audition, new Object[] { companyId, staffId });
            String parameter = "人员分配岗位：" + postName + ", 所属部门："
                    + deptName;
            String hql1 = "from DepartmentPost where companyID = ? and depPostID = ? ";
            DepartmentPost deppt = (DepartmentPost) baseBeanService
                    .getBeanByHqlAndParams(hql1, new Object[] {
                            companyId, postId });
            if (status.equals("01")) {
                String hql = "from COS where companyID = ? and staffID = ? and status = ?";
                COS cosl = (COS) baseBeanService.getBeanByHqlAndParams(hql,
                        new Object[] { companyId, staffId,status  });
                if (cosl != null) {
                    map.put("vals", "专岗已设置,请勿重复！");
                    map.put("status", "-1");
                } else {
                    // 专岗
                    cos.setCompanyID(companyId);
                    cos.setCosID(serverService.getServerID("cos"));
                    cos.setCosStatus("50");
                    cos.setDepPostID(departmentPost.getDepPostID());
                    cos.setOrganizationID(departmentPost.getOrganizationID());
                    cos.setStaffID(staffId);
                    beans.add(cos);
                    // 员工级别确定
                    // 保存工资级别

                    StaffLevelAllocation staffLevelAllocation = new StaffLevelAllocation();
                    staffLevelAllocation.setStaffId(staffId);
                    staffLevelAllocation.setStaffName(staffName);
                    SalaryData salaryData = salaryLevelDao.getSalaryLevelDataBySalaryLevelSerial("4",companyId);
                    staffLevelAllocation.setSalaryLevelIdOld(salaryData.getSalaryLevelId());
                    staffLevelAllocation.setSalaryLevelId(cos.getSalaryLevelId());
                    staffLevelAllocationService.saveAllocationData(staffLevelAllocation);

                    // 职员状态
                    entity1.setStaffCategoryID(entity.getStaffCategoryID());
                    entity1.setCategoryName(entity.getCategoryName());
                    entity1.setStatus("22");
                    beans.add(entity1);
                    // 设置岗位加一
                    if (deppt.getAdminNum() == null) {
                        deppt.setAdminNum("1");
                    } else {
                        int i = Integer.parseInt(deppt.getAdminNum());
                        i = i + 1;
                        deppt.setAdminNum(i + "");
                    }
                    if (deppt.getSpecialpostNum() == null) {
                        deppt.setSpecialpostNum("1");
                    } else {
                        int i = Integer.parseInt(deppt.getSpecialpostNum());
                        i = i + 1;
                        deppt.setSpecialpostNum(i + "");
                    }

                    CLogBook logbook = logBookService.saveCLogBook(null,parameter, account);
                    beans.add(deppt);
                    beans.add(logbook);

                    //同步到个人履历
                    StaffResume staffResume = new StaffResume();
                    staffResume.setStaffID(staffId);
                    staffResume.setRecordID(serverService.getServerID("record"));
                    staffResume.setCcompanyID(companyId);
                    staffResume.setStartTime(entity1.getRegisterDate());
                    Company com = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object []{companyId});
                    staffResume.setCompanyName(com.getCompanyName());
                    staffResume.setCompanyID(com.getCompanyID());
                    staffResume.setPosition(deppt.getPostName());
                    staffResume.setDuties(deppt.getResponsibilityRequire());
                    staffResume.setReference(account.getAccountName());
                    staffResume.setPostName(deppt.getPostName());
                    staffResume.setPostCase("scode20100423vw54xx7r4f0000000054"); //在职
                    staffResume.setReferencePhon("专岗");
                    CDetail cdet = (CDetail)baseBeanService.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object []{companyId});
                    if(cdet!=null){
                        staffResume.setPostAddress(cdet.getCompanyAddress());
                    }
                    beans.add(staffResume);
                    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{"delete CSP where companyID=? and staffID=?"},new String[]{companyId,staffId});
                    map.put("vals", "设置专岗成功！");
                    map.put("status", "0");


                }

            } else if (cos.getStatus().equals("00")) {
                String hql = "from COS where companyID = ? and staffID = ? and status = ? and depPostID = ?";
                COS cosl = (COS) baseBeanService.getBeanByHqlAndParams(hql,
                        new Object[] { companyId,staffId,
                                cos.getStatus() ,postId});
                if (cosl != null) {
                    map.put("vals", "兼岗已设置,请勿重复！");
                    map.put("status", "-1");
                }else{
                    // 兼岗
                    cos.setCompanyID(companyId);
                    cos.setCosID(serverService.getServerID("cos"));
                    cos.setCosStatus("50");
                    cos.setDepPostID(departmentPost.getDepPostID());
                    cos.setOrganizationID(departmentPost.getOrganizationID());
                    cos.setStaffID(staffId);
                    beans.add(cos);
                    // 岗位加一
                    if (deppt.getAdminNum() == null) {
                        deppt.setAdminNum("1");
                    } else {
                        int i = Integer.parseInt(deppt.getAdminNum());
                        deppt.setAdminNum((i + 1) + "");
                    }
                    if (deppt.getOmppostNum() == null) {
                        deppt.setOmppostNum("1");
                    } else {
                        int i = Integer.parseInt(deppt.getOmppostNum());
                        i = i + 1;
                        deppt.setOmppostNum(i + "");
                    }
                    CLogBook logbook = logBookService.saveCLogBook(null, parameter,account);
                    beans.add(logbook);
                    beans.add(deppt);

                    //同步到个人履历
                    StaffResume staffResume = new StaffResume();
                    staffResume.setStaffID(staffId);
                    staffResume.setRecordID(serverService.getServerID("record"));
                    staffResume.setCcompanyID(companyId);
                    staffResume.setStartTime(new Date());
                    Company com = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object []{companyId});
                    staffResume.setCompanyName(com.getCompanyName());
                    staffResume.setCompanyID(com.getCompanyID());
                    staffResume.setPosition(deppt.getPostName());
                    staffResume.setDuties(deppt.getResponsibilityRequire());
                    staffResume.setReference(account.getAccountName());
                    staffResume.setPostName(deppt.getPostName());
                    staffResume.setPostCase("scode20100423vw54xx7r4f0000000054"); //在职
                    staffResume.setReferencePhon("兼岗");
                    CDetail cdet = (CDetail)baseBeanService.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object []{companyId});
                    if(cdet!=null){
                        staffResume.setPostAddress(cdet.getCompanyAddress());
                    }
                    beans.add(staffResume);

                    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
                    map.put("vals", "设置兼岗成功！");
                    map.put("status", "0");
                }
            }

            JSONObject obj = JSONObject.fromObject(map);
            return  obj.toString();
        }catch(Exception e){
            e.printStackTrace();
        }

        return "success";
    }

    @Override
    public DepartmentPost getDepartmentPostById(String postId, String companyId) {
        DepartmentPost departmentPost = postDao.getDepartmentPostById(postId,companyId);
        Map<String,String> organization = postDao.getOrganizationByCompanyId(companyId);
        departmentPost.setOrganizationName(organization.get(departmentPost.getOrganizationID()));
        return departmentPost;
    }

    @Override
    public String toSaveDepartmentPost() {
        String maxNum = "";
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String hql = "select count(postNum) from DepartmentPost where companyID = ?";
        int count = baseBeanService.getConutByByHqlAndParams(hql,
                new Object[] { account.getCompanyID() });
        if (count == 0) {
            maxNum = "0001";
        } else {
            String maxhql = "select max(postNum) from DepartmentPost where companyID = ?";
            int maxcount = baseBeanService.getConutByByHqlAndParams(maxhql,
                    new Object[] { account.getCompanyID() });
            // 判断maxcount+1是否为4位数字，若不足前补0
            if (Integer.toString(maxcount + 1).length() > 3) {
                maxNum = Integer.toString(maxcount + 1);
            } else {
                DecimalFormat myformat = new DecimalFormat(); // 格式化对象的类
                myformat.applyPattern("0000"); // 修改格式模板
                maxNum = myformat.format(maxcount + 1); // 格式化数字
            }
        }
        return maxNum;
    }

    @Override
    public String saveDepartmentPost(DepartmentPost departmentPost,String orgName) {
        String parameter = "";
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        CAccount account = (CAccount) session.get("account");
        if (departmentPost.getDepPostID() == null
                || "".equals(departmentPost.getDepPostID())) {
            departmentPost.setDepPostID(serverService
                    .getServerID("departmentPost"));
            departmentPost.setCompanyID(account.getCompanyID());
            parameter = "添加岗位：" + departmentPost.getPostName() + ", 岗位所属部门："
                    + orgName;
        } else {
            parameter = "修改岗位：" + departmentPost.getPostName() + ", 岗位所属部门："
                    + orgName;
        }
        List<BaseBean> beans = new ArrayList<BaseBean>();
        beans.add(departmentPost);
        String organizationID = (String) session.get("organizationID");
        CLogBook logbook = logBookService.saveCLogBook(organizationID,
                parameter, account);
        beans.add(logbook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return "success";
    }

    @Override
    public PageForm getListPerson(Integer pageNumber,Integer pageSize,DepartmentPost departmentPost,String search) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyId = account.getCompanyID();
        String depPostId = departmentPost.getDepPostID();
        StringBuilder sql = new StringBuilder(128);
        List<Object> params = new ArrayList<Object>();
        sql.append("select distinct c.deppostid,d.postName,s.staffcode,s.staffname,c.status,s.sex,s.nativeplace,s.nationality,")
                .append("s.staffidentitycard,s.reference from dtCos c left join dt_hr_Staff s on ")
                .append("c.staffid = s.staffid left join dt_hr_deptpost d on c.deppostid = d.deppostid  ")
                .append(" where c.companyid = ? and c.cosStatus = ? and c.depPostID = ?");
        params.add(companyId);
        params.add("50");
        params.add(depPostId);
        if(search != null) {
            sql.append(" and (s.staffName like ? or s.reference like ? or staffIdentityCard like ?)");
            params.add("%" + search.trim() + "%");
            params.add("%" + search.trim() + "%");
            params.add("%" + search.trim() + "%");
        }
        return baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), sql.toString(), "select count(1)"
                        + sql.substring(sql.indexOf("from")), params.toArray());
    }

    @Override
    public List<BaseBean> getPostListByIds(String ids,String companyId) {
        StringBuilder sql = new StringBuilder();
        List<String> param = new ArrayList<>();
        sql.append(" from DepartmentPost where companyID=? and depPostID in (");
        param.add(companyId);
        List<String> idList = Arrays.asList(ids.split("\\,"));
        int idSize = idList.size();
        for (int i = 0; i < idSize; i++) {
            String id = idList.get(i).toString();
            if (!id.isEmpty()){
                sql.append("?");
                param.add(id);
                if (i < idSize - 1 ) {
                    sql.append(" ,");
                }
            }
        }
        sql.append(")");
        return baseBeanService.getListBeanByHqlAndParams(sql.toString(),param.toArray());

    }

    @Override
    public void delPostDataById(String postId, String companyId) {
        String hql = "delete from DepartmentPost where depPostID = ? and companyID=?";
        Object[] param = { postId,companyId };
        this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql}, param);
    }

}