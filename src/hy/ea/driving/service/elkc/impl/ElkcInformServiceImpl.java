package hy.ea.driving.service.elkc.impl;

import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.elyc.ElycDrivingSchoolNotice;
import hy.ea.bo.DrivingSchool.elyc.ElycNSAssociation;
import hy.ea.bo.DrivingSchool.elyc.ElycNotifyThePictures;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.driving.service.elkc.ElkcInformService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Service
public class ElkcInformServiceImpl implements ElkcInformService {
	private Logger logger = LoggerFactory.getLogger(ElkcInformServiceImpl.class);
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serverService;
    @Resource
    private UpLoadFileService fileService;


    /**
     * 转换list *
     *
     * @param sql
     *            Sql语句
     * @param object
     *            所需参数的数组
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List transition(String sql, Object[] object) {
        return beandao.getListBeanBySqlAndParams(sql, object);
    }

    /**
     * 分页
     *
     * @param pageNumber
     *            当前页
     * @param pageSize
     *            显示条数
     * @param sql
     *            sql语句
     * @param sqlcount
     *            总记录数SQL语句
     * @param params
     *            参数
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
                                     String sqlcount, Object[] params) {
        int count = beandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
        if (count == 0)
            return null;
        int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
        if (pageNumber < 1)
            pageNumber = 1;
        if (pageNumber > pageCount)
            pageNumber = pageCount;
        int firstResult = pageSize * (pageNumber - 1);
        int maxResult = Math.min(pageSize, count - firstResult);
        List<BaseBean> listBaseBean = beandao.getConutByBySqlAndParamsAndPage(
                sql, params, firstResult, maxResult);
        PageForm pageForm = new PageForm();
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        pageForm.setPageCount(pageCount);
        pageForm.setPageNumber(pageNumber);
        pageForm.setList(listBaseBean);
        return pageForm;
    }

    /**
     * 保存页面信息 *
     *
     * @return
     */
    @Override
    public String mobileLogin() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("url");
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getHeader("Referer");
        session.setAttribute("url", url);
        return "mobileLogin";
    }

    /**
     * 获取当前登录对象 *
     *
     * @return
     */
    @Override
    public CAccount goCaccount() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount cacc = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        return cacc;
    }

    /**
     *
     * 根据个人获取他的一个公司ID
     *
     * @param staffID
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String getStaffCompanyID(String staffID) {
        String companyID = "";
        String hql = "from COS where staffID = ?";
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql,
                new Object[] { staffID });
        if (list.size() != 0) {
            COS cos = (COS) list.get(0);
            companyID = cos.getCompanyID();
        }

        return companyID;
    }







    /**
     * 查询通知消息 *
     * @param staffId :接收人id
     * @param theme :主题
     * @param pageForm
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm mission(String staffId, String theme, PageForm pageForm) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select s.edsnId,s.theme,to_char(s.createdate, 'yyyy-mm-dd hh24:mi:ss') ");
        sb.append("from tb_elyc_NSAssociation n,tb_elyc_Driving_School_Notice s where n.staffId = ? ");
        sb.append("and n.edsnId = s.edsnId and s.status = ? ");
        list.add(staffId);
        list.add("00");
        if (theme!=null && !theme.equals(""))
        {
            sb.append("and s.theme like ? ");
            list.add("%"+theme+"%");
        }
        sb.append("order by s.createdate desc");

        pageForm = getPageFormBySQL(pageForm.getPageNumber(),pageForm.getPageSize(),sb.toString(),"select count(*) from (" + sb.toString() + ")",list.toArray());

        return pageForm;
    }

    /**
     * 学员消息详情 *
     * @param edsnId :消息id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> osfDetails(String edsnId) {

        ElycDrivingSchoolNotice edsn = (ElycDrivingSchoolNotice) beandao.getBeanByHqlAndParams("from ElycDrivingSchoolNotice where edsnId = ?",new Object[]{edsnId});

        List<BaseBean> list = beandao.getListBeanByHqlAndParams("from ElycNotifyThePictures where edsnId = ?",new Object[]{edsnId});

        Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{edsn!=null?edsn.getCreateperson():""});

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("edsn",edsn);
        map.put("list",list);
        map.put("staff",staff);

        return map;
    }

    /**
     * 查询消息发送 *
     * @param createperson :发送人id
     * @param theme :主题
     * @param pageForm
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String,Object> messageSent(String createperson, String theme, PageForm pageForm) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select s.edsnId,s.theme,to_char(s.createdate, 'yyyy-mm-dd hh24:mi:ss'),s.content ");
        sb.append("from tb_elyc_Driving_School_Notice s where s.createperson = ? and s.status = ? ");
        list.add(createperson);
        list.add("00");
        if (theme!=null && !theme.equals(""))
        {
            sb.append("and s.theme like ? ");
            list.add("%"+theme+"%");
        }
        sb.append("order by s.createdate ");

        pageForm = getPageFormBySQL(pageForm.getPageNumber(),pageForm.getPageSize(),sb.toString(),"select count(*) from (" + sb.toString() + ")",list.toArray());

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageForm",pageForm);
        if (pageForm!=null && pageForm.getList()!=null)
        {
            for(int i=0;i<pageForm.getList().size();i++)    {
                Object obj = pageForm.getList().get(i);
                Object[] objList = (Object[]) obj;
                List<BaseBean> stafflist = beandao.getListBeanBySqlAndParams("select f.staffid,f.headimage from tb_elyc_NSAssociation s,dt_hr_staff f where s.edsnid = ? and s.staffid = f.staffid",new Object[]{objList[0]});
                map.put(""+i+"",stafflist);
            }
        }

        return map;
    }

    /**
     * 教练发送消息详情 *
     * @param edsnId :消息id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> cmsDetails(String edsnId) {

        ElycDrivingSchoolNotice edsn = (ElycDrivingSchoolNotice) beandao.getBeanByHqlAndParams("from ElycDrivingSchoolNotice where edsnId = ?",new Object[]{edsnId});

        List<BaseBean> entplist = beandao.getListBeanByHqlAndParams("from ElycNotifyThePictures where edsnId = ?",new Object[]{edsnId});

        List<BaseBean> ensalist = beandao.getListBeanBySqlAndParams("select f.staffid,f.headimage,f.staffname from tb_elyc_NSAssociation s,dt_hr_Staff f where s.edsnId = ? and s.staffId = f.staffId ",new Object[]{edsnId});

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("edsn",edsn);
        map.put("entplist",entplist);
        map.put("ensalist",ensalist);

        return map;
    }

    /**
     * ajax查询教练学员 *
     * @param staffName :人员名称
     * @param companyid :公司id
     * @return
     **/
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm coachStudents(String staffName, String companyid, PageForm pageForm) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select f.staffid, f.headimage, f.staffname, '0' ");
        sb.append("from Tb_Jp_Teacher r, dt_hr_staff f ");
        sb.append("where r.companyid = ? ");
        list.add(companyid);
        if (staffName!=null && !staffName.equals(""))
        {
            sb.append("and f.staffname like ? ");
            list.add("%"+staffName+"%");
        }
        sb.append(" and r.staffid = f.staffid ");
        sb.append("union all ");
        sb.append("select f.staffid, f.headimage, f.staffname, '1' ");
        sb.append("from Tb_Jp_Student_Info r, dt_hr_staff f ");
        sb.append("where r.company_id = ? ");
        list.add(companyid);
        if (staffName!=null && !staffName.equals(""))
        {
            sb.append("and f.staffname like ? ");
            list.add("%"+staffName+"%");
        }
        sb.append(" and r.staffid = f.staffid ");

        pageForm = getPageFormBySQL(pageForm.getPageNumber(),pageForm.getPageSize(),sb.toString(),"select count(*) from (" + sb.toString() + ")",list.toArray());

        return pageForm;
    }

    /**
     * ajax查询教练学员 *
     * @param edsn :消息发送参数
     * @param file :图片数组
     * @param fileName :图片名称数组
     * @param staffId :接收人数组
     * @return
     **/
    @Override
    @Transactional
    public void addInform(ElycDrivingSchoolNotice edsn, File[] file, String[] fileName, String[] staffId) {


        if (edsn!=null)
        {
            List<BaseBean> list = new ArrayList<BaseBean>();

            //保存驾校消息
            edsn.setEdsnId(serverService.getServerID("edsn"));
            edsn.setCreatedate(new Date());
            edsn.setStatus("00");
            list.add(edsn);

            //保存通知学员关系
            ElycNSAssociation ensa = null;
            for(String s:staffId)
            {
                ensa = new ElycNSAssociation();
                ensa.setEnsaId(serverService.getServerID("ensa"));
                ensa.setEdsnId(edsn.getEdsnId());
                ensa.setStaffId(s);
                list.add(ensa);
            }


            //保存驾校通知图片
            if (file!=null&&file.length>0)
            {
                ElycNotifyThePictures entp = null;

                for(int i = 0; i < file.length; i++)
                {
                    entp = new ElycNotifyThePictures();
                    entp.setEntpId(serverService.getServerID("entp"));
                    entp.setEdsnId(edsn.getEdsnId());
                    String imgUrl = addImages(edsn.getCreateperson(),edsn.getCompanyId(),file[i],fileName[i]);
                    entp.setPicpath(imgUrl);
                    list.add(entp);
                }
            }

            beandao.saveBeansListAndexecuteHqlsByParams(list,new String[]{},new Object[]{});

        }

    }


    /**
     *
     * @Title: 新版添加
     * @Description: 添加图片/视频
     * @return 返回路径
     *
     */
    public String addImages(String staffid, String companyId, File picture,
                              String pictureName) {
        HttpServletRequest re = ServletActionContext.getRequest();
        String path = re.getSession().getServletContext().getRealPath("/");
        if (companyId == null || companyId.equals("")) {
            companyId = getStaffCompanyID(staffid);
        }
        String photopath = fileService.savePhoto(path, pictureName, picture,
                companyId, "drivingschool/".concat(DateUtil
                        .getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)));

        return photopath;
    }

}
