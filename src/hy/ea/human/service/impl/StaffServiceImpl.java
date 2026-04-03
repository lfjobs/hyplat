package hy.ea.human.service.impl;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.WfjJifenService;
import hy.ea.bo.*;
import hy.ea.bo.finance.BenDis.Sign;
import hy.ea.bo.human.*;
import hy.ea.bo.human.salary.SalaryData;
import hy.ea.bo.office.*;
import hy.ea.human.dao.SalaryLevelDao;
import hy.ea.human.dao.StaffDao;
import hy.ea.human.service.CSalaryLevelService;
import hy.ea.human.service.PostService;
import hy.ea.human.service.StaffService;
import hy.ea.office.bo.HrSalaryLevel;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocCommonService;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Resource
    private StaffDao staffDao;

    @Resource
    private ServerService serverService;

    @Resource
    private PostService postService;

    @Resource
    private DocCommonService docCommonService;

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private CLogBookService logBookService;

    private String parameter;

    private List<BaseBean> beans;

    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private SalaryLevelDao salaryLevelDao;

    @Resource
    private CSalaryLevelService salaryLevelService;
    @Resource
    private ContractService contractService;
    @Resource
    private WfjJifenService wfjJifenService;

    public String saveStaffData(Staff cstaff, COS cos, List<UpLoadFile> upLoadFileList, String postIds, String type, String deleteFileIds) {
        beans = new ArrayList();
        CAccount account = (CAccount)ActionContext.getContext().getSession()
                .get("account");
        String flag = saveStaffBaseData(cstaff);
        if(!flag.equals("success")){
            return flag;
        }
        if ("add".equals(type)){
            addStaffData(cstaff,account,cos, upLoadFileList,postIds,deleteFileIds);
        }else {
            editStaffData(cstaff,account,cos, upLoadFileList,postIds,deleteFileIds);
        }

        CLogBook logBook = logBookService
                .saveCLogBook(null, parameter, account);
        beans.add(logBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null,null);
        return "success";

    }

    @Override
    public PageForm getEntryStaffDataList(String companyId, Integer pageNumber, Integer pageSize,String queryName,COS cos) {
        return staffDao.getEntryStaffDataList(companyId,pageNumber,pageSize,queryName,cos);
    }

    @Override
    public Staff getStaffDataByStaffId(String staffId) {
        return staffDao.getStaffDataByStaffId(staffId);
    }

    @Override
    public COS getCosDataByParam(String staffId,String companyId) {
        Map<String,String> param = new HashMap<>();
        param.put("staffId",staffId);
        param.put("companyId",companyId);
        COS cos = staffDao.getCosDataByParam(param);
        List<BaseBean> roleList = staffDao.getRoleListByStaffId(staffId,companyId);
        CRole role;
        Map<String,String> deptData = getOrganizationByCompanyId(companyId);
        String roleName = "",roleId="";
        for (BaseBean baseBean : roleList){
            role = (CRole)baseBean;
            roleName += "," + role.getRoleName();
            roleId += "," + role.getRoleID();
        }
        if (!"".equals(roleName)){
            roleName = roleName.substring(1);
            roleId = roleId.substring(1);
        }
        cos.setRoleId(roleId);
        cos.setRoleName(roleName);
        List<BaseBean> postList = staffDao.getPostListByStaffId(staffId,companyId);
        DepartmentPost post;
        String postName = "",postID = "";
        for (BaseBean baseBean : postList){
            post = (DepartmentPost)baseBean;
            postName += "," + deptData.get(post.getOrganizationID()) + "-" + post.getPostName();
            postID += "," + post.getDepPostID();
        }
        if (!"".equals(postName)){
            postName = postName.substring(1);
            postID = postID.substring(1);
        }

        cos.setDepPostID(postID);
        cos.setDepPostName(postName);

        String contractTypeName = "";
        if (cos.getContractType() != null && !"".equals(cos.getContractType())){
            List<BaseBean> contractTypeList = getContractStaffData(companyId,cos.getContractType());
            DocumentTemplate documentTemplate;
            for (BaseBean baseBean : contractTypeList){
                documentTemplate = (DocumentTemplate)baseBean;
                contractTypeName += "," + documentTemplate.getContractTypeName();
            }
            if (!"".equals(contractTypeName)){
                contractTypeName = contractTypeName.substring(1);
            }
        }
        cos.setContractTypeName(contractTypeName);
        if (cos.getSalaryLevelId() != null && !"".equals(cos.getSalaryLevelId())){
            HrSalaryLevel salaryLevel = salaryLevelService.getSalaryLevelById(cos.getSalaryLevelId());
            if (salaryLevel != null){
                cos.setSalaryLevelName(salaryLevel.getSalaryLevelSerial() + "级");
            }
        }
        if(cos.getEntryDate() == null){
            Audition audition = staffDao.getAuditionDataByParam(staffId,companyId);
            if (audition != null  && audition.getRegisterDate() != null){
                cos.setEntryDate(String.valueOf(audition.getRegisterDate()).substring(0,10));
            }

        }
        String salaryLevelId = cos.getSalaryLevelName();
        if ("".equals(salaryLevelId) || salaryLevelId == null){
            HrSalaryLevel level = salaryLevelService.getSalaryLevelBySerial(companyId,"4");
            cos.setSalaryLevelId(level.getSalaryLevelId());
            cos.setSalaryLevelName(level.getSalaryLevelSerial() + "级");
        }
        return cos;
    }
    @Override
    public COS getCosDataByStaffIdAndCompanyId(String staffId,String companyId) {
        Map<String, String> param = new HashMap<>();
        param.put("staffId", staffId);
        param.put("companyId", companyId);
        return staffDao.getCosDataByParam(param);
    }

    @Override
    public COSDimissionStaff getCOSDimissionStaffDataByParam(String staffId, String companyId) {
        COSDimissionStaff cOSDimissionStaff = staffDao.getCOSDimissionStaffDataByParam(staffId, companyId);
        String contractTypeName = "";
        if (cOSDimissionStaff == null){
            cOSDimissionStaff = new COSDimissionStaff();
        }
        if (cOSDimissionStaff.getContractType() != null && !"".equals(cOSDimissionStaff.getContractType())){
            List<BaseBean> contractTypeList = getContractStaffData(companyId,cOSDimissionStaff.getContractType());
            DocumentTemplate documentTemplate;
            for (BaseBean baseBean : contractTypeList){
                documentTemplate = (DocumentTemplate)baseBean;
                contractTypeName += "," + documentTemplate.getContractTypeName();
            }
            if (!"".equals(contractTypeName)){
                contractTypeName = contractTypeName.substring(1);
            }
        }
        cOSDimissionStaff.setContractTypeName(contractTypeName);
        return cOSDimissionStaff;
    }

    @Override
    public Audition getAuditionDataByParam(String staffId, String companyId) {
        return staffDao.getAuditionDataByParam(staffId, companyId);
    }

    @Override
    public List<UpLoadFile> getFileDataByStaffId(String staffId, String companyId) {
        return staffDao.getFileDataByStaffId(staffId, companyId);
    }

    @Override
    public String deleteStaffByStaffId(String companyId, String staffId,String cosId) {
        beans = new ArrayList<>();
        Staff staff = staffDao.getStaffByStaffId(staffId);
        staff.setStaffStatus("98");
        beans.add(staff);
        Map<String,String> param = new HashMap<>();
        param.put("staffId",staffId);
        param.put("companyId",companyId);
        param.put("cosStatus","50");
        param.put("cosId",cosId);
        COS cos = staffDao.getCosDataByParam(param);
        cos.setCosStatus("99");
        beans.add(cos);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return "success";
    }

    @Override
    public PageForm getSignData(String account, String beginDate,String endDate,Integer pageNumber, Integer pageSize) {
        return staffDao.getSignData(account,beginDate,endDate,pageNumber,pageSize);
    }

    @Override
    public String saveSignData() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String account = request.getParameter("account");
        String signDate = request.getParameter("signDate");
        String signId = request.getParameter("signId");
        String type = request.getParameter("type");
        if ("del".equals(type)){
            String sql = "delete from Sign where signId = ? ";
            List<Object[]> parms = new ArrayList<>();
            parms.add(new Object[]{signId});
            baseBeanService.executeHqlsByParamsList(null, new String[]{sql}, parms);
        } else {
            Sign sign;
            Integer num = (int) (Math.random() * 900 + 100);
            if ("add".equals(type)){
                String hql = "from Sign where account=? and signDate = (select max(signDate) from Sign where account=?)";
                sign = (Sign) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { account,account});
                if(sign == null){
                    return "fail";
                }
                sign.setSignId(this.serverService.getServerID("Sign"));
                sign.setSignKey(null);
            } else {
                String hql = "from Sign where signId=?";
                sign = (Sign) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { signId});
            }
            signDate = signDate + "." + num ;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try {
                sign.setSignDate(sdf.parse(signDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            baseBeanDao.saveOrUpdate(sign);
        }

        return "success";
    }

    @Override
    public PageForm getPersonDataByParam(String companyId, Map<String, String> param, Integer pageNumber, Integer pageSize) {
        String staffName = param.get("staffName");
        String reference = param.get("reference");
        StringBuffer hql = new StringBuffer(128);
        List<String> arr = new ArrayList<>();
        hql.append(" from Staff where staffID IN ( SELECT C.staffID FROM COS C ")
           .append(" WHERE  companyID=? and cosStatus='50' and status='01')");
        arr.add(companyId);
        int size = param.size();
        if (size > 0){
            if (!"".equals(staffName) && !"".equals(reference)) {
                hql.append(" and (");
                if (!"".equals(staffName)) {
                    hql.append("staffName like ?");
                    arr.add("%" + staffName + "%");

                }
                if (!"".equals(reference)) {
                    hql.append(" or reference like ? ");
                    arr.add("%" + reference + "%");
                }
                hql.append(")");
            }

        }
        PageForm pageForm = baseBeanService.getPageForm( (null != pageNumber ? pageNumber : 1),(pageSize==0?20:pageSize),
                hql.toString(), arr.toArray());
        return pageForm;
    }

    @Override
    public PageForm getNeedJoinStaffData(String companyId, Integer pageNumber, Integer pageSize) {
        return staffDao.getNeedJoinStaffData(companyId,pageNumber,pageSize);
    }

    @Override
    public String getEntryStatisticsData() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        JSONObject data = new JSONObject();
        data.put("entryData", staffDao.getEntryData(companyId));
        data.put("educationData", staffDao.getEducationData(companyId));
        data.put("contractData", staffDao.getContractData(companyId));
        data.put("entryPersonData", staffDao.getEntryPersonData(companyId));
        return JSONObject.fromObject(data).toString();
    }

    public Map<String,String> getOrganizationByCompanyId(String companyId) {
        Map<String,String> data = new HashMap<>();
        List<BaseBean> olist= baseBeanService.getListBeanByHqlAndParams("from COrganization o where o.companyID = ?  ",new Object[]{ companyId});
        for (int i = 0; i < olist.size(); i++) {
            data.put(((COrganization)olist.get(i)).getOrganizationID(), ((COrganization)olist.get(i)).getOrganizationName());
        }
        return data;
    }
    /**
     * 添加人员数据
     * @param cstaff
     * @param account
     * @param cos
     * @param upLoadFileList
     * @return
     */
    public void addStaffData(Staff cstaff,CAccount account,COS cos,List<UpLoadFile> upLoadFileList,String postIds,String deleteFileIds){
        Audition entity = saveAuditionData(cstaff,account,cos);
        DepartmentPost departmentPost = saveDepartmentPost(account,cos,postIds);
        saveStaffRole(cstaff,account,cos);
        saveStaffSaralyLevel(cstaff,account,cos);
        saveStaffCos(cstaff,account,cos,postIds );
        saveStaffResume(cstaff,account,cos,entity,departmentPost);
        saveFileData(cstaff,account,upLoadFileList,deleteFileIds);
        saveStaffCAccount(cstaff,account,cos);
        HrSalaryLevel level = salaryLevelService.getSalaryLevelBySerial(account.getCompanyID(),"4");
        cos.setSalaryLevelId(level.getSalaryLevelId());
    }

    /**
     * 修改人员数据
     * @param cstaff
     * @param account
     * @param cos
     * @param upLoadFileList
     * @param deptIds
     */
    public void editStaffData(Staff cstaff,CAccount account,COS cos,List<UpLoadFile> upLoadFileList,String deptIds,String deleteFileIds){
        editPostData(cstaff,account,cos,deptIds);
        saveStaffRole(cstaff,account,cos);
        saveFileData(cstaff,account,upLoadFileList,deleteFileIds);
    }

    /**
     * 修改岗位数据
     * @param cstaff
     * @param account
     * @param cos
     * @param deptIds
     */
    public void editPostData(Staff cstaff,CAccount account,COS cos,String deptIds)  {
        String companyId = account.getCompanyID();
        String staffId = cstaff.getStaffID();
        List<BaseBean> cosList = staffDao.getCOSListByStaffId(companyId,staffId);
        JSONObject postJson = new JSONObject();
        String oldPost = "";
        COS cosData,cosOldData ;
        String oldStatus = "";
        for (BaseBean baseBean : cosList){
            cosData = (COS)baseBean;
            if(cosData.getDepPostID() != null){
                postJson.put(cosData.getDepPostID(),cosData);
                oldPost += "," + cosData.getDepPostID();
            }
            if(cosData.getStatus() != null){
                oldStatus = cosData.getStatus();
            }


        }
        String[] depPostIdArr = deptIds.split("\\,");
        int length = depPostIdArr.length;
        for (int i= 0; i < length; i++){
            String depPostId = depPostIdArr[i];

            cosOldData = (COS) JSONObject.toBean((JSONObject) postJson.get(depPostId), COS.class);
            if (cosOldData != null){
                try {
                    cosData = (COS)cosOldData.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                oldPost = oldPost.replace(","+depPostId,"");
                if (!cos.getStatus().equals(oldStatus)){
                    DepartmentPost departmentPost = staffDao.getPostDataByStaffId(companyId,depPostId);
                    editDepartmentPostData(departmentPost,cos.getStatus(),"delete");
                }
                cosData.setRoleId(cos.getRoleId());
                cosData.setContractType(cos.getContractType());
                cosData.setEntryDate(cos.getEntryDate());
                cosData.setStatus(cos.getStatus());
            }else {
                try {
                    cosData = (COS)cos.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                // 新添加的岗位
                DepartmentPost departmentPost = staffDao.getPostDataByStaffId(companyId,depPostId);
                cosData.setCosID(serverService.getServerID("cos"));
                cosData.setCompanyID(companyId);
                cosData.setCosStatus("50");
                cosData.setStaffID(cstaff.getStaffID());
                cosData.setDepPostID(departmentPost.getDepPostID());
                cosData.setOrganizationID(departmentPost.getOrganizationID());
            }
            beans.add(cosData);
        }
        List<String> param = new ArrayList<>();
        StringBuffer sql = new StringBuffer(128);
        if (!"".equals(oldPost)){
            oldPost = oldPost.substring(1);
            String[] delArr = oldPost.split(",");
            sql.append("delete from COS where companyID = ?  and staffID = ? and depPostID  in (");
            param.add(companyId);
            param.add(staffId);
            for (int i = 0; i < delArr.length; i++){
                String depPostID = delArr[i];
                if (!"".equals(depPostID)){
                    sql.append("?");
                    param.add(depPostID);
                    if (i == delArr.length - 1) {
                        sql.append(" )");
                    } else {
                        sql.append(" ,");
                    }
                }

            }
            this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{sql.toString()}, param.toArray());
        }
    }
    /**
     * 保存员工数据
     * @param cstaff
     * @return
     */
    public String saveStaffBaseData(Staff cstaff){
        String staffId = cstaff.getStaffID();

        //String flag = checkIdentityCard(cstaff.getStaffIdentityCard(),staffId);
        if (staffId == null || "".equals(staffId)) {
            /*if (!"success".equals(flag)){
                return flag;
            }*/
            String phql = "select count(*) from Staff ";
            int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
            cstaff.setStaffCode("NO" + pcount);
            cstaff.setRecordCode("NO" + pcount);
            cstaff.setVerifyTime(new Date());
            cstaff.setStaffID(serverService.getServerID("cstaff"));
            cstaff.setStaffStatus("00");
            beans.add(cstaff);
            parameter = "添加员工（人员姓名：:" + cstaff.getStaffName() + ")";
        } else {
            Staff staff = staffDao.getStaffDataByStaffId(cstaff.getStaffID());
            staff.setStaffName(cstaff.getStaffName());
            staff.setStaffIdentityCard(cstaff.getStaffIdentityCard());
            staff.setBirthday(cstaff.getBirthday());
            staff.setSex(cstaff.getSex());
            staff.setStaffStatus("00");
            staff.setReference(cstaff.getReference());
            staff.setCulturalDegree(cstaff.getCulturalDegree());
            beans.add(staff);
            parameter = "修改员工(人员名称:" + cstaff.getStaffName() + ")";
        }


        return "success";
    }

    /**
     * 检查身份证
     * @param identityCard
     * @param staffId
     * @return
     */
    public String checkIdentityCard(String identityCard,String staffId){
        Object[] params;
        String hql;
        if (staffId == null || "".equals(staffId)){
            hql = "select count(*) from Staff where  staffIdentityCard = ? and staffstatus != '98' ";
            params = new Object[]{identityCard};
        } else {
            hql = "select count(*) from Staff where  staffIdentityCard = ? and staffstatus != '98' and staffID != ? ";
            params = new Object[]{identityCard,staffId};
        }
        int count = baseBeanService.getConutByByHqlAndParams(hql, params);
        if (count > 0){
            return "exist";
        }
        return "success";
    }

    /**
     * 保存账号数据
     * @param cstaff
     */
    public void saveEshopCuscomData(Staff cstaff)
    {
        String phones = cstaff.getReference();
        String sccid = "";

        TEshopCusCom zccus = (TEshopCusCom)this.baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[] { phones });
        if ((zccus == null) && (phones != null))
        {
            if ((sccid == null) || (sccid.equals(""))) {
                sccid = "TEshopCusCom20161010W9FXK9NJ450000011682";
            }
            TEshopCusCom tuicus = (TEshopCusCom)this.baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[] { sccid });

            String stf = this.wfjJifenService.zhuCe(tuicus, sccid, phones, "123456", cstaff);
            if (sccid.equals("TEshopCusCom20161010W9FXK9NJ450000011682"))
            {
                String sqlslist = " select t.account from t_eshop_cuscom t  where t.custype<'6' and t.custype>'1' and t.sccid != ? ";
                List<String> list = new ArrayList();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                String date = sdf.format(new Date());
                list = this.baseBeanService.getListBeanBySqlAndParams(sqlslist, new Object[] { "TEshopCusCom20161010W9FXK9NJ450000011682" });
                JushMain.sendjiguangMessage("有新用户注册，点击立即抢单，手慢无，快来抢！" + date, "抢下级", "有新的客户注册哦！", "lowerlevel", list);
            }
            this.contractService.receiveDoc(phones);
        }


    }

    /**
     * 修改招聘数据
     * @param cstaff
     * @param account
     * @param cos
     * @return
     */
    public Audition saveAuditionData(Staff cstaff,CAccount account,COS cos){
        String companyId = account.getCompanyID();
        String staffId = cstaff.getStaffID();
        String hql_audition = "from Audition where  companyID = ? and staffID = ? ";
        Audition entity = (Audition) baseBeanService.getBeanByHqlAndParams(
                hql_audition, new Object[] { companyId, staffId });
        entity.setStatus("22");
        beans.add(entity);
        return entity;

    }

    /**
     * 修改部门岗位数据
     * @param account
     * @param cos
     * @return
     */
    public DepartmentPost saveDepartmentPost(CAccount account,COS cos,String depPostIds ) {
        String companyId = account.getCompanyID();
        List<BaseBean> postList = postService.getPostListByIds(depPostIds,companyId);
        DepartmentPost departmentPost = null;
        String status = cos.getStatus();
        for (BaseBean baseBean : postList) {
            departmentPost = (DepartmentPost) baseBean;
            editDepartmentPostData(departmentPost,status,"add");
            beans.add(departmentPost);
        }
        return departmentPost;
    }
    public void editDepartmentPostData(DepartmentPost departmentPost,String status,String type){
        if ("add".equals(type)){
            if (departmentPost.getAdminNum() == null) {
                departmentPost.setAdminNum("1");
            } else {
                int i = Integer.parseInt(departmentPost.getAdminNum());
                departmentPost.setAdminNum(i + 1 + "");
            }
            if (status.equals("01")) {
                if (departmentPost.getSpecialpostNum() == null) {
                    departmentPost.setSpecialpostNum("1");
                } else {
                    int i = Integer.parseInt(departmentPost.getSpecialpostNum());
                    i = i + 1;
                    departmentPost.setSpecialpostNum(i + "");
                }
            } else {
                if (departmentPost.getOmppostNum() == null) {
                    departmentPost.setOmppostNum("1");
                } else {
                    int i = Integer.parseInt(departmentPost.getOmppostNum());
                    i = i + 1;
                    departmentPost.setOmppostNum(i + "");
                }
            }
        } else{
            if (departmentPost.getAdminNum() == null) {
                departmentPost.setAdminNum("0");
            } else {
                int i = Integer.parseInt(departmentPost.getAdminNum());
                departmentPost.setAdminNum(i - 1 + "");
            }
            if (status.equals("01")) {
                if (departmentPost.getSpecialpostNum() == null) {
                    departmentPost.setSpecialpostNum("0");
                } else {
                    int i = Integer.parseInt(departmentPost.getSpecialpostNum());
                    i = i - 1;
                    departmentPost.setSpecialpostNum(i + "");
                }
            } else {
                if (departmentPost.getOmppostNum() == null) {
                    departmentPost.setOmppostNum("0");
                } else {
                    int i = Integer.parseInt(departmentPost.getOmppostNum());
                    i = i - 1;
                    departmentPost.setOmppostNum(i + "");
                }
            }
        }

    }
    /**
     * 保存角色数据
     * @param cstaff
     * @return
     */
    public void saveStaffRole(Staff cstaff,CAccount account,COS cos ) {
        List<String> param = new ArrayList<>();
        String companyId = account.getCompanyID();
        String staffId = cstaff.getStaffID();
        String roleIds = cos.getRoleId();
        List<String> roleIdList = Arrays.asList(roleIds.split("\\,"));
        StringBuffer sql = new StringBuffer(128);
        sql.append("delete from StaffRole where staffId = ? and companyId = ? ");
        param.add(staffId);
        param.add(companyId);
        this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{sql.toString()}, param.toArray());
        StaffRole staffRole ;
        for (String roleId : roleIdList ){
            staffRole = new StaffRole();
            if (!"".equals(roleId)){
                staffRole.setRoleId(roleId);
                staffRole.setCompanyId(companyId);
                staffRole.setStaffId(staffId);
                beans.add(staffRole);
            }
        }

    }

    /**
     * 申请级别
     * @param cstaff
     * @param account
     * @param cos
     */
    public void saveStaffSaralyLevel(Staff cstaff,CAccount account,COS cos){
        StaffLevelAllocation staffLevelAllocation = new StaffLevelAllocation();
        staffLevelAllocation.setStaffId(cstaff.getStaffID());
        staffLevelAllocation.setStaffName(cstaff.getStaffName());
        SalaryData salaryData = salaryLevelDao.getSalaryLevelDataBySalaryLevelSerial("4",account.getCompanyID());
        staffLevelAllocation.setSalaryLevelIdOld(salaryData.getSalaryLevelId());
        staffLevelAllocation.setSalaryLevelId(cos.getSalaryLevelId());
        staffLevelAllocation.setLevelAllocationKey(null);
        staffLevelAllocation.setLevelAllocationId(serverService.getServerID("allocation"));
        staffLevelAllocation.setCreateStaffId(account.getStaffID());
        staffLevelAllocation.setCreateStaffName(account.getStaffName());
        staffLevelAllocation.setCreateDate(new Date());
        staffLevelAllocation.setStatus("0");
        staffLevelAllocation.setCompanyId(account.getCompanyID());
        cos.setSalaryLevelId(salaryData.getSalaryLevelId());
        beans.add(staffLevelAllocation);
    }
    /**
     * 保存人員cos
     * @param cstaff
     * @return
     */
    public void saveStaffCos(Staff cstaff,CAccount account,COS cos,String depPostIds )  {
        String companyId = account.getCompanyID();
        Map<String,String> paramData = new HashMap<>();
        paramData.put("staffId",cstaff.getStaffID());
        paramData.put("companyId",companyId);
        paramData.put("cosStatus","50");
        //HrSalaryLevel level = salaryLevelService.getSalaryLevelBySerial(companyId,"4");
        List<BaseBean> postList = postService.getPostListByIds(depPostIds,companyId);
        DepartmentPost departmentPost;

        for (BaseBean baseBean : postList){
            COS cosData = null;
            try {
                cosData = (COS)cos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            departmentPost = (DepartmentPost)baseBean;
            cosData.setCosID(serverService.getServerID("cos"));
            cosData.setCompanyID(companyId);
            cosData.setCosStatus("50");
            cosData.setStaffID(cstaff.getStaffID());
            cosData.setDepPostID(departmentPost.getDepPostID());
            cosData.setOrganizationID(departmentPost.getOrganizationID());
            beans.add(cosData);
        }
    }

    /**
     * 保存个人履历
     * @param cstaff
     * @param account
     * @param cos
     * @param entity
     * @param departmentPost
     */
    public void saveStaffResume(Staff cstaff,CAccount account,COS cos,Audition entity,DepartmentPost departmentPost){
        String companyId = account.getCompanyID();
        String staffId = cstaff.getStaffID();
        StaffResume staffResume = new StaffResume();
        staffResume.setStaffID(staffId);
        staffResume.setRecordID(serverService.getServerID("record"));
        staffResume.setCcompanyID(companyId);
        staffResume.setStartTime(entity.getRegisterDate());
        Company com = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object []{companyId});
        staffResume.setCompanyName(com.getCompanyName());
        staffResume.setCompanyID(com.getCompanyID());
        staffResume.setPosition(departmentPost.getPostName());
        staffResume.setDuties(departmentPost.getResponsibilityRequire());
        staffResume.setReference(account.getAccountName());
        staffResume.setPostName(departmentPost.getPostName());
        staffResume.setPostCase("scode20100423vw54xx7r4f0000000054"); //在职
        if ("01".equals(cos.getStatus())){
            staffResume.setReferencePhon("专岗");
        } else {
            staffResume.setReferencePhon("兼岗");
        }
        CDetail cdet = (CDetail)baseBeanService.getBeanByHqlAndParams("from CDetail where companyID = ?", new Object []{companyId});
        if(cdet!=null){
            staffResume.setPostAddress(cdet.getCompanyAddress());
        }
        beans.add(staffResume);

    }

    /**
     * 添加后台账号
     * @param cstaff
     * @param account
     */
    public void saveStaffCAccount(Staff cstaff,CAccount account,COS cos){
        CAccount caccount = new CAccount();
        caccount.setAccountID(serverService.getServerID("caccount"));
        caccount.setStaffID(cstaff.getStaffID());
        caccount.setCompanyID(account.getCompanyID());
        String staffName = cstaff.getStaffName();
        caccount.setAccountName(staffName);
        caccount.setAccountEmail(getAbbreviation(staffName));
        // 加密密码
        caccount.setAccountPassword(Utilities.MD5("123456"));
        // 指定管理员的状态为正常状态
        caccount.setAccountStatus("00");
        String[] roleId = cos.getRoleId().split(",");
        caccount.setRoleID(roleId[0]);
        beans.add(caccount);
    }
    /**
     * 保存文件
     * @param cstaff
     * @param account
     * @param upLoadFileList
     */
    public void saveFileData(Staff cstaff,CAccount account,List<UpLoadFile> upLoadFileList,String deleteFileIds){
        Map<String,Integer> map = new HashMap<>();
        List<String> param = new ArrayList<>();
        for (UpLoadFile file : upLoadFileList ){
            if (file.getFileKey() == null || file.getFileKey() == ""){
                file.setFileID(serverService.getServerID("upload"));
            }
            Integer num = 0;
            if(map.get(file.getFileType()) == null){
                num++;
            } else {
                num = map.get(file.getFileType()) + 1;
            }
            map.put(file.getFileType(),num);
            file.setCompanyID(account.getCompanyID());
            file.setParmeterID(cstaff.getStaffID());
            file.setFiledesc(num + "");
            beans.add(file);
        }
        if (!"".equals(deleteFileIds)){
            List<String> deleteFileIdList = Arrays.asList(deleteFileIds.split("\\,"));
            StringBuffer sql = new StringBuffer(128);
            int fileSize = deleteFileIdList.size();
            if (fileSize > 0){
                sql.append("delete from UpLoadFile where fileID  in (");
                for (int i = 0; i < fileSize; i++) {
                    String fileId = deleteFileIdList.get(i);
                    if (!"".equals(fileId)){
                        sql.append("?");
                        param.add(fileId);
                        if (i == fileSize - 1) {
                            sql.append(" )");
                        } else {
                            sql.append(" ,");
                        }
                    }
                }
                this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{sql.toString()}, param.toArray());
            }
        }

    }

    public List<Object> getDocStaffInfo(String staffID, String companyID,String contractSource){
        StringBuffer sql = new StringBuffer(128);
        sql.append("select f.contractTypeName,f.contractType,t.status,t.docId,t.subscriberID2,t.drafterID ")
                .append(" from DT_OA_DocStaff f,DT_OA_DOCUMENT t where f.docId = t.docId and f.staffID = ? and f.companyID = ? and f.status = ? ");
        List<String> param = new ArrayList<>();
        param.add(staffID);
        param.add(companyID);
        param.add("00");
        if (contractSource != null){
            sql.append(" and f.contractSource=?");
            param.add(contractSource);
        }
        sql.append(" order by f.contractType");
        List<Object> list = baseBeanDao.getListObjectBySqlAndParams(sql.toString(),param.toArray());
        return list;
    }
    /**
     *
     * 保存入职合同关系
     * @param templateId
     * @param contractType
     */
    @Override
    public void saveRelateDoc(String templateId, String contractType, String contractTypeName, String companyID, String temptId, String staffID,String templateTypeName) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String hql = "from DocumentTemplate where templateId = ?";
        DocumentTemplate documentTemplate = (DocumentTemplate)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{templateId});
       if ("".equals(contractType)){
           String sql = "select nvl(max(to_number(contractType)),0) from DT_OA_DOCUMENT_TEMPLATE where companyID= ? and contractType is not null";
           int pcount = baseBeanService.getConutByBySqlAndParams(sql,
                   new Object[]{companyID});
           pcount++;
           contractType = pcount + "";
           if (pcount < 10){
               contractType = "0" + pcount;
           }
           contractTypeName= documentTemplate.getFileShowName();
       }

       if(companyID.equals(documentTemplate.getCompanyId())){
           //自己公司的，不管事预设还是非预设
           documentTemplate.setContractType(contractType);
           documentTemplate.setContractTypeName(contractTypeName);
           beans.add(documentTemplate);
       }else{
           if("00".equals(documentTemplate.getSysSet())){
               //预设的，还不是自己 公司的 要生成到自己公司一份
               DocumentTemplate newTem = null;
               try {
                   newTem = (DocumentTemplate)documentTemplate.clone();
                   newTem.setCompanyId(companyID);
                   newTem.setTemplateId(serverService.getServerID("templateId"));
                   newTem.setContractType(contractType);
                   newTem.setContractTypeName(contractTypeName);
                   newTem.setKey(null);
                   newTem.setTime(new Date());
                   newTem.setTemptId(temptId);


                   String hcl = "from TemplateType where companyId = ? and module = ? and templateTypeName = ?";
                   List<BaseBean> listtm = baseBeanDao.getListBeanByHqlAndParams(hcl,new Object[]{companyID,"contract","入职合同"});
                   if(listtm.size()>0){
                       TemplateType dt = (TemplateType) listtm.get(0);
                       newTem.setTemptId(dt.getTemptId());
                   }else{
                       //否则生成一个分类
                       TemplateType templateType = new TemplateType();
                       templateType.setTemptId(serverService.getServerID("tempid"));
                       templateType.setCompanyId(companyID);
                       templateType.setTime(new Date());
                       templateType.setCreaterID(staffID);
                       Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});
                       templateType.setCreaterName(staff.getStaffName());
                       templateType.setSeq(0);
                       templateType.setParentId("me");
                       templateType.setSysSet("01");
                       templateType.setTemplateTypeName(templateTypeName);
                       templateType.setModule("contract");
                       beans.add(templateType);

                       newTem.setTemptId(templateType.getTemptId());
                   }
                   beans.add(newTem);



               } catch (CloneNotSupportedException e) {
                   throw new RuntimeException(e);
               }
           }

       }
       baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
   }

    /**
     * 刪除公司合同模板
     * @param templateId
     * @param companyID
     */
    @Override
    public String deleteRelateDoc(String templateId, String companyID) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String hql = "from DocumentTemplate where templateId = ?";
        DocumentTemplate documentTemplate = (DocumentTemplate)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{templateId});
        List<BaseBean> list= baseBeanService.getListBeanByHqlAndParams("from DocStaff where contractType = ? and companyID=?",new Object[]{ documentTemplate.getContractType(),companyID});
        if (list.size() > 0){
            return "exist";
        }
        if(companyID.equals(documentTemplate.getCompanyId())){
            //自己公司的，不管事预设还是非预设
            documentTemplate.setContractType(null);
            documentTemplate.setContractTypeName(null);
            beans.add(documentTemplate);
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);
        }else{
            if("00".equals(documentTemplate.getSysSet())){
                //预设的，还不是自己 公司的 要刪除
                hql = "delete from DocumentTemplate where templateId=? ";
                baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[] {templateId });
            }

        }
        return "success";
    }

    @Override
    public PageForm getStaffDocList(String companyId, Integer pageNumber, Integer pageSize,Map<String,String> params) {
        return staffDao.getStaffDocList(companyId,pageNumber,pageSize,params);
    }
    @Override
    public DocStaff getStaffContractInfo(String staffId, String companyId, String docId){
        return staffDao.getStaffContractInfo(staffId,companyId,docId);
    }
    @Override
    public Document getDocumentByDocId(String docId){
       Document document = staffDao.getDocumentByDocId(docId);
       String status = document.getStatus();
       String staffId = "";
       if ("I".equals(status)){
           String subscriberID2 = document.getSubscriberID2();
           if (!"".equals(subscriberID2) && subscriberID2 != null){
               staffId = subscriberID2;
           }else {
               staffId = document.getDrafterID();
           }
       } else if ("A".equals(status) || "F".equals(status)){
           staffId = document.getSealerID();
       } else if ("S".equals(status) || "T".equals(status)){
           staffId = document.getSubscriberID();
       } else if ("P".equals(status)){
           staffId = document.getPublisherID();
       } else if ("O".equals(status)){
           staffId = document.getReceiverID();
       } else if ("R".equals(status) || "U".equals(status)){
           staffId = document.getDrafterID();
       }
       if(!"".equals(staffId)){
           Staff staff = staffDao.getStaffDataByStaffId(staffId);
           document.setExamineResult(staff.getStaffName());
       }
        getDocumentData(document);

        return document;
    }

    @Override
    public String getStaffContractByDocId(String docId) {
        Document document = staffDao.getDocumentByDocId(docId);
        getDocumentData(document);
        document.setAttachFiles(null);
        document.setSubscribers(null);
        return JSON.toJSONString(document);
    }

    @Override
    public String saveContractTemp(String templateId, String contractType, String contractTypeName, String companyID, String temptId, String staffID) {
        StringBuilder hql = new StringBuilder(128);
        hql.append(" from DocumentTemplate where companyId=? and contractTypeName=?  and contractType is not null");
        DocumentTemplate documentTemplate = (DocumentTemplate)baseBeanDao.getBeanByHqlAndParams(hql.toString(),new Object[]{companyID,contractTypeName});
        if (documentTemplate == null){
            saveRelateDoc(templateId,contractType,contractTypeName,companyID,temptId,staffID,"入职合同");
            return "success";
        } else {
            return "exist";
        }
    }

    @Override
    public PageForm getResignStaffData(String companyId, Integer pageNumber, Integer pageSize, String queryName) {
        return staffDao.getResignStaffData(companyId,pageNumber,pageSize,queryName);
    }

    @Override
    public String saveResignStaffData(COSDimissionStaff cOSDimissionStaff,Staff staff, String type ) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String[] hqls = new String[] {};
        List<Object[]> parms = new ArrayList<Object[]>();
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyID = account.getCompanyID();
        String staffID = staff.getStaffID();
        if ("add".equals(type)){
            String hql2 = "from COSDimissionStaff where staffID=? and companyID=?";
            COSDimissionStaff dimissionStaff = (COSDimissionStaff) baseBeanService.getBeanByHqlAndParams(hql2,
                    new Object[] { staffID,companyID });
            if (dimissionStaff != null){
                return "exist";
            }
            cOSDimissionStaff.setStaffID(staffID);
            cOSDimissionStaff.setDimissionStaffID(serverService.getServerID("csdID"));
            cOSDimissionStaff.setDimissionStaffKey(serverService.getServerID("csdKey"));
            cOSDimissionStaff.setCompanyID(companyID);
            cOSDimissionStaff.setIssued(account.getStaffName());
            cOSDimissionStaff.setCreateDate(new Date());

            // 在职员工中将人员设置为离职
            String hql_cos = "update dtCOS set cosStatus=99  where companyID = ?  and staffID = ? ";
            Object[] par1 = new Object[] { companyID, staffID };
            // 招聘表将员工为离职
            String hql_audition = "update dtAudition set status=99 where companyID = ?  and staffID = ? ";
            Object[] par2 = new Object[] { companyID,staffID };
            hqls = new String[] { hql_cos, hql_audition};
            parms.add(par1);
            parms.add(par2);
            CLogBook logBook = logBookService.saveCLogBook(null, "员工离职(人员名称："
                    + staff.getStaffName() + ")", account);
            beans.add(logBook);

        } else {
            cOSDimissionStaff.setStaffID(staffID);
            cOSDimissionStaff.setCompanyID(companyID);
        }
        beans.add(cOSDimissionStaff);
        baseBeanService.executeSqlsByParmsList(beans, hqls, parms);
        //baseBeanService.executeHqlsByParamsList(null, hqls, parms);
        String realpath = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("/");
        Map<String, Object> session = ActionContext.getContext().getSession();
        if(account!=null) {
            docCommonService.createDoc(realpath, account.getCompanyID(), account.getStaffID(), cOSDimissionStaff.getContractType(), staff,"02");
        }
        return "success";
    }

    /**
     * 修改离职后岗位数据
     * @param companyID
     * @param staffID
     * @param beans
     */
    public void saveResignDepartmentPostData(String companyID,String staffID,List<BaseBean> beans){
        // 专岗、兼岗 人员数减一
        List<String> strL = new ArrayList<String>() ;
        List<DepartmentPost> depList = new ArrayList<DepartmentPost>();
        List<BaseBean> cosList = baseBeanService.getListBeanByHqlAndParams("from COS c where c.companyID = ? and c.staffID = ?", new Object[] {companyID, staffID });
        for (int i = 0; i < cosList.size(); i++) {
            COS co = (COS)(cosList.get(i));
            DepartmentPost dp = (DepartmentPost)baseBeanService.getBeanByHqlAndParams("from DepartmentPost p where p.companyID = ? and p.depPostID = ?",  new Object[] {companyID, co.getDepPostID()});
            if(dp != null){
                if(co.getStatus().equals("01")){	// 专岗

                    if (dp .getAdminNum() == null || Integer.parseInt(dp.getAdminNum()) == 1) {
                        dp .setAdminNum("0");
                    } else if( Integer.parseInt(dp.getAdminNum()) > 1){
                        int n = Integer.parseInt(dp.getAdminNum());
                        n = n - 1;
                        dp.setAdminNum(n + "");
                    }
                    if (dp.getSpecialpostNum() == null || Integer.parseInt(dp.getSpecialpostNum()) == 1) {
                        dp.setSpecialpostNum("0");
                    } else if(Integer.parseInt(dp.getSpecialpostNum()) > 1){
                        int m = Integer.parseInt(dp.getSpecialpostNum());
                        m = m - 1;
                        dp.setSpecialpostNum(m + "");
                    }
                    depList.add(dp);
                }else if(co.getStatus().equals("00")){
                    int x = 0;
                    if(strL.contains(co.getDepPostID())){
                        continue;
                    }
                    for (int j = 0; j < cosList.size(); j++) {
                        COS coo = (COS)(cosList.get(j));
                        if(coo.getStatus().equals("00") && coo.getDepPostID().equals(co.getDepPostID())){
                            x++;
                        }

                    }

                    strL.add(co.getDepPostID());

                    if (dp .getAdminNum() == null || Integer.parseInt(dp.getAdminNum()) == x) {
                        dp .setAdminNum("0");
                    } else if( Integer.parseInt(dp.getAdminNum()) > x){
                        int n = Integer.parseInt(dp.getAdminNum());
                        n = n - x;
                        dp.setAdminNum(n + "");
                    }
                    if (dp.getOmppostNum() == null || Integer.parseInt(dp.getOmppostNum()) == x) {
                        dp.setOmppostNum("0");
                    } else if(Integer.parseInt(dp.getOmppostNum()) > x){
                        int m = Integer.parseInt(dp.getOmppostNum());
                        m = m - x;
                        dp.setOmppostNum(m + "");
                    }
                    depList.add(dp);
                }
            }
        }
        List<Integer> num = new ArrayList<Integer>();
        if(depList.size()>0){
            for (int i = 0; i < depList.size(); i++) {
                for (int j = 0; j < depList.size(); j++) {
                    if(depList.get(i).getDepPostID().equals(depList.get(j).getDepPostID()) && j!=i){
                        num.add(i);
                        num.add(j);
                        break;
                    }
                }
                if(num.size() > 0){
                    break;
                }
            }
        }
        if(num.size() >0){
            DepartmentPost d1 = depList.get(num.get(0));
            DepartmentPost d2 = depList.get(num.get(1));
            // 辖员人数
            if( d1.getAdminNum() == null || Integer.parseInt(d1.getAdminNum()) == 1 || Integer.parseInt(d1.getAdminNum()) == 0){
                d1.setAdminNum("0");
            }else{
                int n = Integer.parseInt(d1.getAdminNum());
                n = n - 1;
                d1.setAdminNum(n + "");
            }
            // 专岗人数
            if(d1.getSpecialpostNum() == null || Integer.parseInt(d1.getSpecialpostNum()) == 0){
                d1.setSpecialpostNum("0");
            }else{
                d1.setSpecialpostNum(Integer.parseInt(d1.getSpecialpostNum()) < Integer.parseInt(d2.getSpecialpostNum()) ? d1.getSpecialpostNum() : d2.getSpecialpostNum());
            }
            // 兼岗人数
            if(d1.getOmppostNum() == null || Integer.parseInt(d1.getOmppostNum()) == 0){
                d1.setOmppostNum("0");
            }else{
                d1.setOmppostNum(Integer.parseInt(d1.getOmppostNum()) < Integer.parseInt(d2.getOmppostNum()) ? d1.getOmppostNum() : d2.getOmppostNum());
            }
            depList.add(d1);
        }

        for (int i = 0; i < depList.size(); i++) {
            if(num.size()>0){
                if(i == num.get(0) || i == num.get(1)){
                    continue;
                }
            }
            beans.add(depList.get(i));
        }
    }

    public void getDocumentData(Document document){
        Staff staff;
        String staffId = document.getSubscriberID2();
        if (!"".equals(staffId) && staffId != null){
            staff = staffDao.getStaffDataByStaffId(staffId);
            document.setSubscriberName2(staff.getStaffName());
        }
        staffId = document.getDrafterID();
        if (!"".equals(staffId) && staffId != null){
            staff = staffDao.getStaffDataByStaffId(staffId);
            document.setDrafterName(staff.getStaffName());
        }
        staffId = document.getSealerID();
        if (!"".equals(staffId) && staffId != null){
            staff = staffDao.getStaffDataByStaffId(staffId);
            document.setSealerName(staff.getStaffName());
        }
        staffId = document.getSubscriberID();
        if (!"".equals(staffId) && staffId != null){
            staff = staffDao.getStaffDataByStaffId(staffId);
            document.setSubscriberName(staff.getStaffName());
        }
        staffId = document.getPublisherID();



        if (!"".equals(staffId) && staffId != null){
            staff = staffDao.getStaffDataByStaffId(staffId);
            document.setPublisherName(staff.getStaffName());
        }
        String docId = document.getDocId();
        document.setReceiverName(getReadContractByDocId(docId));
        ArchiveDocRelate relate = null;
        relate = (ArchiveDocRelate) baseBeanDao.getBeanByHqlAndParams("from ArchiveDocRelate where docId = ? and companyID is null", new String[]{docId});
        if (relate != null){
            document.setGuidangName(relate.getCreaterName());
            document.setGuidangTime(relate.getTime());
        }

       /*
       staffId = document.getReceiverID();
       if (!"".equals(staffId) && staffId != null){
            staff = staffDao.getStaffDataByStaffId(staffId);
            document.setReceiverName(staff.getStaffName());
        }
*/
    }
    public static String getAbbreviation(String name) {
        StringBuilder sb = new StringBuilder();
        for (char c : name.toCharArray()) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null && pinyinArray.length > 0) {
                sb.append(pinyinArray[0].charAt(0));
            } else {
                sb.append(c); // 非汉字字符直接添加
            }
        }
        return sb.toString(); // 转换为大写
    }

    public String getReadContractByDocId(String docId){
        String hcl = "\n" +
                "SELECT WM_CONCAT(case when g.readstate=0 then concat(s.STAFFNAME,'(已阅读）') else concat(s.STAFFNAME,'(未阅读）') end )staffname FROM dt_oa_doc_gsend g " +
                "LEFT JOIN DT_HR_STAFF s on g.READERID = s.staffID where docId=?";
        List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(hcl,new Object[]{docId});
        String data = "";
        int length = list.size();
        if (length > 0){
            Object obj = list.get(0);
            data = (String)obj;
        }
       return data;
    }
    public List<BaseBean> getContractStaffData(String companyId, String contractType) {
        StringBuffer sql = new StringBuffer(" from DocumentTemplate o where contractType in ( ");
        List<String> param = new ArrayList<>();

        String[] value = contractType.split(",");
        for (int i = 0; i < value.length; i++){
            sql.append("?");
            param.add(value[i]);
            if (i == value.length - 1){
                sql.append(")");
            } else {
                sql.append(",");
            }
        }
        if (!"".equals(companyId)){
            sql.append(" and companyId=?");
            param.add(companyId);
        }
        return baseBeanDao.getListBeanByHqlAndParams(sql.toString(),param.toArray());
    }

}