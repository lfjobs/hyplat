package hy.ea.production.action.cprocedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.WfjJifen;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.recruit.*;
import hy.ea.bo.production.resume.ResumeS;
import hy.ea.production.service.BidCommonService;
import hy.ea.production.service.BidsRecruitService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SDistrictService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 招聘
 */
@Controller
@Scope("prototype")
public class BidsRecruitAction {
	private static final Logger logger = LoggerFactory.getLogger(BidsRecruitAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private ShowExcelService excelService;
    @Resource
    private BidCommonService bidCommonService;
    @Resource
    private SDistrictService regionService;

    @Resource
    private BidsRecruitService bidsRecruitService;
    @Resource
    private EarthIndexService earthIndexService;

    private InputStream excelStream;

    private PageForm pageForm;
    private String result;
    private int pageNumber;
    private String search;
    private List<BaseBean> list;
    private Staff staff;
    private String parameter;
    private RecruitInfo recruitInfo;
    private SearchCriteria searchCriteria;

    private String ccompanyID;
    private String companyId;
    private String pageSize;
    private String back;
    private String type;

    private TalentPool talentPool;

    /************************************************5l5c招聘信息发布*************************************************/

    /**
     * 获取添加页面
     *
     * @return
     */
    public String getAddPage() {

        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");

        HttpServletRequest request = ServletActionContext.getRequest();
        String city = "";
        String address = "";
        if (recruitInfo != null && (recruitInfo.getRiId() != null && !"".equals(recruitInfo.getRiId()))) {
            String hql = "from RecruitInfo where riId = ?";
            recruitInfo = (RecruitInfo) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{recruitInfo.getRiId()});
            if (recruitInfo.getJobRequire() != null) {
                recruitInfo.setJobRequire(recruitInfo.getJobRequire().replace("<br/>", "\r\n"));
            }
            city = recruitInfo.getWorkCity();
            address = recruitInfo.getWorkPlace();
        } else {
            //查询城市
            String hqlcom = "from ContactCompany c where c.ccompanyID = (select t.ccompanyId from CcomCom t where t.comanyId = ?)";
            ContactCompany ccom = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hqlcom, new Object[]{account.getCompanyID()});
            if (ccom.getAddress() != null && "".equals(ccom.getAddress())) {
                List<SDistrict> distinctlistSaved = regionService.getDistrictListByID(ccom.getAddress());
                Collections.reverse(distinctlistSaved);
                city = (distinctlistSaved != null && distinctlistSaved.get(1) != null) ? distinctlistSaved.get(1).getDistrictName() : "";
            }
            address = ccom.getCompanyAddr();
        }

        //查询城市
        request.setAttribute("city", city);
        request.setAttribute("address", address);

        String hql = "from ProductPackaging where parentName = ? and type=? order by createTime";
        List<BaseBean> edulist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"学历", "招聘"});
        List<BaseBean> yearslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"工作经验", "招聘"});
        List<BaseBean> postypelist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"职位类型", "招聘"});


        request.setAttribute("edulist", edulist);
        request.setAttribute("yearslist", yearslist);
        request.setAttribute("postypelist", postypelist);

        return "addpage";
    }


    /**
     * 保存招聘信息
     *
     * @return
     */
    public String saveRecruitInfo() {
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");

        if (recruitInfo != null && (recruitInfo.getRiId() == null || "".equals(recruitInfo.getRiId()))) {
            recruitInfo.setRiId(serverService.getServerID("riId"));
            recruitInfo.setStatus("00");
        }
        if (recruitInfo.getStatus() != null && recruitInfo.getStatus().equals("01")) {
            recruitInfo.setPublishDate(new Date());
        }
        recruitInfo.setJobRequire(recruitInfo.getJobRequire().replace("\r\n", "<br/>"));
        recruitInfo.setStaffID(account.getStaffID());
        recruitInfo.setStaffName(((Staff) baseBeanService.getBeanByHqlAndParams(
                "from Staff where staffID = ?",
                new Object[]{account.getStaffID()})).getStaffName());
        recruitInfo.setCompanyID(account.getCompanyID());
        recruitInfo.setCompanyName(account.getCompany().getCompanyName());
        recruitInfo.setCreateDate(new Date());
        String hql = "from CcomCom where comanyId = ?";
        CcomCom ccom = (CcomCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
        recruitInfo.setCcompanyID(ccom.getCcompanyId());

        baseBeanService.update(recruitInfo);

        return "success";

    }


    /**
     * 发布或者取消招聘信息
     *
     * @return
     */
    public String publishRecruitInfo() {
        CAccount account = (CAccount) ActionContext.getContext().getSession()
                .get("account");
        String hql = "from RecruitInfo where riId = ?";
        RecruitInfo rinfo = (RecruitInfo) baseBeanService
                .getBeanByHqlAndParams(hql,
                        new Object[]{recruitInfo.getRiId()});


        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                "from Staff where staffID = ?",
                new Object[]{account.getStaffID()});

        if ("00".equals(rinfo.getStatus())) {
            rinfo.setStatus("01");//发布
            rinfo.setPublishDate(new Date());
            rinfo.setStaffID(account.getStaffID());
            rinfo.setStaffName(staff.getStaffName());
        } else {
            rinfo.setStatus("00");//取消发布
            rinfo.setPublishDate(new Date());
            rinfo.setOfflineID(account.getStaffID());
            rinfo.setOfflineName(staff.getStaffName());
        }

        baseBeanService.update(rinfo);
        return "success";

    }

    /**
     * 招聘信息列表
     *
     * @return
     */
    public String findRecruitInfoList() {
        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), getLists());

        return "recruitlist";


    }

    private DetachedCriteria getLists() {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");

        DetachedCriteria dc = DetachedCriteria.forClass(RecruitInfo.class);
        dc.add(Restrictions.eq("companyID", account.getCompanyID()));
        dc.add(Restrictions.ne("status", "09"));
        dc.addOrder(Order.desc("createDate"));


        if (search != null && search.equals("search")) {
            recruitInfo = (RecruitInfo) session.get("tablesearch");
            if (recruitInfo.getJobTitle() != null && !"".equals(recruitInfo.getJobTitle())) {
                dc.add(Restrictions.like("jobTitle", recruitInfo.getJobTitle().trim(),
                        MatchMode.ANYWHERE));

            }

            if (recruitInfo.getStatus() != null
                    && !recruitInfo.getStatus().equals("")) {
                dc.add(Restrictions.eq("status", recruitInfo.getStatus()));

            }


        }
        return dc;

    }

    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", recruitInfo);
        return findRecruitInfoList();
    }

    /**
     * 删除招聘信息
     *
     * @return
     */
    public String deleteRecruitInfo() {

        bidsRecruitService.deleteRecruitInfo(recruitInfo.getRiId());

        return "success";
    }

    /**
     * 导出
     *
     * @return
     */
    public String showExcel() {
        List<BaseBean> list = baseBeanService.getListByDC(getLists());
        excelStream = excelService.showExcel(RecruitInfo.columnHeadings(),
                list);

        return "showexcel";

    }
    /********************************************微分金招聘模块************************************************/
    /**
     * 获取招聘信息首页
     */
    public String getRecruitIndex() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        if(cus!=null) {

            earthIndexService.addBrowseHistory(cus.getSccId(),"招聘","");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select b.logoPath,b.jobtitle,b.companyName,b.workCity,b.education,b.riId,nvl(p.tpid ,'0') from ");
        sql.append("(select c.logoPath,p.jobtitle,p.companyName,p.workCity,p.education,p.riId from dtRecruitInfo p,dtContactCompany c where c.ccompanyID = p.ccompanyID and p.status = ?");

        List<Object> params = new ArrayList<Object>();
        params.add("01");
        if (search != null && search.equals("search")) {

            if (parameter != null && !parameter.equals("")) {
                sql.append(" and p.positionName  like ?");
                params.add("%" + parameter + "%");
            }
        }
        sql.append(" order by publishDate desc) b");
        sql.append(" left join  Dttalentpool p on p.riid = b.riid");

        sql.append(" and p.staffid = ? ");
        String staffid = "1";
        if (cus != null) {
            staffid = cus.getStaffid();
            request.setAttribute("sccId",cus.getSccId());
            request.setAttribute("staffid",staffid);
        }
        params.add(staffid);

        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                8, sql.toString(),
                "select count(*) from (" + sql.toString() + ")", params.toArray());

        String type = request.getParameter("type");
        if ("ajax".equals(type)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";
        }
        return "torecruit";
    }


    /**
     * 职位详情
     *
     * @return
     */
    public String showPosdetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        String position = request.getParameter("position");
        String riId = request.getParameter("riId");
        String iscol = "no";
        String istou = "no";
        if (cus != null) {
            String hqlc = "from CollectThing where id = ? and staffID = ?";
            CollectThing ct = (CollectThing) baseBeanService.getBeanByHqlAndParams(hqlc, new Object[]{riId, cus.getStaffid()});

            if (ct != null) {
                iscol = "yes";
            } else {
                iscol = "no";
            }
            String hqlt = "from TalentPool t where t.staffID = ? and riId = ?";
            TalentPool tp = (TalentPool) baseBeanService.getBeanByHqlAndParams(hqlt, new Object[]{cus.getStaffid(), riId});
            if (tp != null) {
                istou = "yes";
            } else {
                istou = "no";
            }

        }
        request.setAttribute("iscol", iscol);
        request.setAttribute("istou", istou);

        //查询相似职位
        String sql = "select c.logoPath,p.jobtitle,p.companyName,p.workCity,p.education,p.riId,p.salary,p.publishDate from dtRecruitInfo p,dtContactCompany c where c.ccompanyID = p.ccompanyID and p.status = ?  and p.jobtitle like ? and p.riId!=? and rownum < 10";
        list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01", "%" + position + "%", riId});

        String sqlp = "select c.logoPath,p.jobtitle,p.companyName,p.workCity,p.education,p.riId,p.workYears,p.partorfull,p.salary,p.jobRequire,p.workPlace,p.ccompanyID from dtRecruitInfo p,dtContactCompany c where c.ccompanyID = p.ccompanyID and p.riId = ?";
        Object obj = baseBeanService.getObjectBySqlAndParams(sqlp, new Object[]{riId});
        request.setAttribute("obj", obj);
        request.setAttribute("list", list);


        return "posdetail";
    }


    /**
     * 公司信息
     *
     * @return
     */
    public String showCompanydetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ccompanyID = request.getParameter("ccompanyID");
        String hqlcom = "from ContactCompany where ccompanyID = ?";
        ContactCompany ccom = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hqlcom, new Object[]{ccompanyID});
        //查询该公司下的职位
        String sql = "select c.logoPath,p.jobTitle,p.companyName,p.workCity,p.education,p.riId,p.salary,p.publishDate from dtRecruitInfo p,dtContactCompany c where c.ccompanyID = p.ccompanyID and p.status = ? and p.ccompanyID = ?";
        list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01", ccompanyID});
        request.setAttribute("ccom", ccom);
        request.setAttribute("list", list);
        return "gsdetail";

    }

    /**
     * 获取求职信息首页
     *
     * @return
     */
    public String getResumeIndex() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);

        StringBuilder sb = new StringBuilder();

        sb.append("	select b.resumeID,b.position,b.staffname,b.region,b.PRIVACY,b.headimage,b.educationValue,nvl(p.tpid ,'0')");
        sb.append(" from (select r.resumeID,j.position,f.staffname,j.region,r.PRIVACY,f.headimage,f.educationValue");
        sb.append(" from dtresumeS r");
        sb.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
        sb.append(" left join dt_hr_staff f on f.staffID = r.staffID");
        sb.append(" where r.privacy = ?");
        sb.append(" order by r.creationTime desc)");
        sb.append(" b left join Dttalentpool p on p.resumeid = b.resumeid");
        sb.append(" and p.staffid = ? ");
        String staffid = "1";
        if (cus != null) {
            staffid = cus.getStaffid();
            request.setAttribute("staffid",staffid);
            request.setAttribute("sccId",cus.getSccId());
        }
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 8, sb.toString(),
                "select count(*) from (" + sb.toString() + ")",
                new Object[]{"00", staffid});

        String type = request.getParameter("type");
        if ("ajax".equals(type)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();

            return "success";
        }
        return "torecruit";
    }


    /***
     *
     *
     * 求职详情
     * @return
     */
    public String showResumedetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        String resumeID = request.getParameter("resumeID");
        String iscol = "no";
        String istou = "no";
        if (cus != null) {
            String hqlc = "from CollectThing where id = ? and staffID = ?";
            CollectThing ct = (CollectThing) baseBeanService.getBeanByHqlAndParams(hqlc, new Object[]{resumeID, cus.getStaffid()});

            if (ct != null) {
                iscol = "yes";
            } else {
                iscol = "no";
            }
            String hqlt = "from TalentPool t where t.staffID = ? and resumeID = ?";
            TalentPool tp = (TalentPool) baseBeanService.getBeanByHqlAndParams(hqlt, new Object[]{cus.getStaffid(), resumeID});
            if (tp != null) {
                istou = "yes";
            } else {
                istou = "no";
            }

        }
        request.setAttribute("iscol", iscol);
        request.setAttribute("istou", istou);


        StringBuilder sb = new StringBuilder();
        sb.append("select r.resumeID,j.position,f.staffname,j.region,f.headimage,f.sex,f.nativePlace,");
        sb.append("f.staffAddress,f.referenceOrganization,f.reference,j.salary,r.evaluate,f.birthday");
        sb.append(",nvl(floor(months_between(sysdate, to_date(birthday, 'yyyy-mm-dd')) / 12),0)");
        sb.append(" from dtresumeS r");
        sb.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
        sb.append(" left join dt_hr_staff f on f.staffID = r.staffID");
        sb.append(" where r.resumeID = ?");

        Object obj = baseBeanService.getObjectBySqlAndParams(sb.toString(), new Object[]{resumeID});
        //教育经历
        String hqledu = "from Educational where resumeID = ? order by admissionTime";
        List<BaseBean> edulist = baseBeanService.getListBeanByHqlAndParams(hqledu, new Object[]{resumeID});
        //工作经历
        String hqlsr = "from StaffResume where resumeID = ? order by startTime";
        List<BaseBean> srlist = baseBeanService.getListBeanByHqlAndParams(hqlsr, new Object[]{resumeID});

        Object edu = null;
        if (srlist.size() != 0) {
            String sql = "select max(endTime),min(startTime),max(startTime) from dt_hr_staff_Resume where resumeID = ?";
            edu = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{resumeID});
        }

        request.setAttribute("obj", obj);
        request.setAttribute("edu", edu);
        request.setAttribute("edulist", edulist);
        request.setAttribute("srlist", srlist);


        return "resumedetail";
    }

    /**
     * 行业搜索获取行业信息
     *
     * @return
     */
    public String getIndustryList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        String hql = "from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";
        String codePID = "scode20150815wygb79q82p0000000005";
        Object[] params = {"company201009046vxdyzy4wg0000000025", codePID};
        pageForm = baseBeanService.getPageForm(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 2 : pageNumber), hql, params);

        String hqlsub = "from CCode a where a.companyID = ? and a.codePID in(";
        List<Object> paramsub = new ArrayList<Object>();
        paramsub.add("company201009046vxdyzy4wg0000000025");
        List<BaseBean> sublist = null;
        if (pageForm != null) {
            for (int i = 0; i < pageForm.getList().size(); i++) {
                CCode ccode = (CCode) pageForm.getList().get(i);
                hqlsub += "?";
                if (i != pageForm.getList().size() - 1) {
                    hqlsub += ",";
                }
                paramsub.add(ccode.getCodeID());

            }

            hqlsub += ")";
            hqlsub += " order by a.codeNumber";
            sublist = baseBeanService.getListBeanByHqlAndParams(hqlsub,
                    paramsub.toArray());
            if ("ajax".equals(type)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pageForm", pageForm);
                map.put("sublist", sublist);
                JSONObject jo = JSONObject.fromObject(map);
                this.result = jo.toString();
                return "success";
            }
        }
        request.setAttribute("pageForm", pageForm);
        request.setAttribute("sublist", sublist);

        return "toindustry";
    }


    /**
     * 搜索查询耳机行业
     *
     * @return
     */
    public String getSecondIndustry() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String parameter = request.getParameter("parameter");
        String hql = "from CCode a  where a.codeValue like ? and a.companyID = ? and a.codePID in(select c.codeID from CCode c where c.companyID = ? and c.codePID = ?)";
        String codePID = "scode20150815wygb79q82p0000000005";
        Object[] params = {"%" + parameter + "%", "company201009046vxdyzy4wg0000000025", "company201009046vxdyzy4wg0000000025", codePID};
        List<BaseBean> sublist = baseBeanService.getListBeanByHqlAndParams(hql, params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sublist", sublist);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 查询找工作首页
     *
     * @return
     */
    public String getGssearchIndex() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (cus != null) {
            String hql = "from HistorySearch where staffID = ? and type = ? and rownum < 11 order by searchDate desc";
            List<BaseBean> historylist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cus.getStaffid(), "00"});
            request.setAttribute("historylist", historylist);
        }
        return "gssearch";
    }

    /**
     * 查询找人才首页
     *
     * @return
     */
    public String getGrsearchIndex() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        List<BaseBean> historylist = null;
        if (cus != null) {
            String hql = "from HistorySearch where staffID = ? and type = ? and rownum < 11 order by searchDate desc";
            historylist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cus.getStaffid(), "01"});
        }
        //查询热门评论
        String sqlhot = "select h.keyword2 from (select s.keyword2 from dtHistorySearch s  where s.keyword2 is not null group by s.keyword2 order by count(*) desc) h where rownum<11";
        List<BaseBean> hotlist = baseBeanService.getListBeanBySqlAndParams(sqlhot, null);

        request.setAttribute("historylist", historylist);
        request.setAttribute("hotlist", hotlist);

        return "grsearch";
    }

    /**
     * 职位
     *
     * @return
     */
    public String getPositionIndex() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        String codePID = request.getParameter("codePID");
        String hql = "from SCode a where a.codePID = ? order by a.codeNumber";
        if (codePID == null || codePID.equals("")) {
            codePID = "scode20160616493hsghahy0000000002";
        }
        List<BaseBean> positionlist = baseBeanService
                .getListBeanByHqlAndParams(hql, new Object[]{codePID});


        if ("ajax".equals(type)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("positionlist", positionlist);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";
        }

        String hqlsub = "from SCode a  where a.codePID in(select c.codeID from SCode c where c.codePID = ?) order by a.codeNumber";

        List<BaseBean> sublist = baseBeanService.getListBeanByHqlAndParams(hqlsub, new Object[]{codePID});


        request.setAttribute("positionlist", positionlist);
        request.setAttribute("sublist", sublist);

        return "toposition";
    }


    /**
     * 根据关键字搜索职位
     *
     * @return
     */
    public String searchPosByKeyword() {
        String codeID = "scode20160616493hsghahy0000000002";
        StringBuilder str = new StringBuilder();
        str.append("with t as(");
        str.append(" select t.codeID,t.codeValue,t.codePID from DTSCODE t where t.codeValue like ?");
        str.append(" connect by nocycle prior t.codeID = t.codePID ");
        str.append(" start with t.codeID = ?)");
        str.append(" select t.codeID,t.codeValue,t.codePID from t where");
        str.append(" t.codeID not in(select c.codeID from DTSCODE c where c.codePID = ?) and t.codeID!=?");
        List<BaseBean> searchlist = baseBeanService.getListBeanBySqlAndParams(str.toString(), new Object[]{"%" + parameter + "%", codeID, codeID, codeID});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchlist", searchlist);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    /**
     * '
     * <p>
     * null处理成空字符串
     *
     * @param str
     * @return
     */
    public String isNull(String str) {
        if (str == null) {
            str = "";
        }

        return str;
    }

    /**
     * 查询
     *
     * @return
     */
    public String startSearch() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        String type = request.getParameter("type");


        String ajax = request.getParameter("ajax");


        String back = "";
        HistorySearch history = new HistorySearch();
        if (searchCriteria != null) {

            history.setHsId(serverService.getServerID("hsid"));
            history.setKeyword1(searchCriteria.getIndustry());
            history.setKeyword2(searchCriteria.getPosition());
            history.setKeyword3(searchCriteria.getCity());
            history.setSearchDate(new Date());
        }
        if (cus != null) {
            history.setStaffID(cus.getStaffid());
        }

        if (type != null && "gs".equals(type)) {

            history.setType("00");
            pageForm = searchRecuitInfo(searchCriteria);

            back = "gsresult";

        } else {
            history.setType("01");
            pageForm = searchResumeInfo(searchCriteria);
            back = "grresult";
        }
        if (searchCriteria != null) {
            if ((searchCriteria.getIndustry() != null && !"".equals(searchCriteria.getIndustry())) ||
                    (searchCriteria.getPosition() != null && !"".equals(searchCriteria.getPosition())) ||
                    (searchCriteria.getCity() != null && !"".equals(searchCriteria.getCity()))) {
                baseBeanService.save(history);
            }

        }


        if (ajax != null && "ajax".equals("ajax")) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";

        } else {
            return back;
        }


    }

    /**
     * 搜索找工作
     *
     * @return
     */
    private PageForm searchRecuitInfo(SearchCriteria searchCriteria) {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);

        StringBuilder sb = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sb.append("select b.jobTitle,b.companyName,b.workCity,b.education,b.riId,b.salary,nvl(p.tpid,'0') from");
        sb.append(" (select r.jobTitle,r.companyName,r.workCity,r.education,r.riId,r.salary from dtRecruitInfo r,dtContactCompany c where");
        sb.append(" r.ccompanyID = c.ccompanyID and r.status = ? and (r.jobTitle like ? or r.companyName like ?)");
        params.add("01");
        if (searchCriteria == null) {
            searchCriteria = new SearchCriteria();
        }
        params.add("%" + searchCriteria.getKeyword() + "%");
        params.add("%" + searchCriteria.getKeyword() + "%");


        if (searchCriteria.getIndustry() != null && !"".equals(searchCriteria.getIndustry())) {
            sb.append(" and r.industry in (");
            String[] arrayindus = searchCriteria.getIndustry().split(",");
            for (int i = 0; i < arrayindus.length; i++) {
                sb.append("?");
                if (i != arrayindus.length - 1) {
                    sb.append(",");
                } else {
                    sb.append(")");
                }
                params.add(arrayindus[i]);
            }
        }

        if (searchCriteria.getPosition() != null && !"".equals(searchCriteria.getPosition())) {
            sb.append(" and r.positionName in (");
            String[] arraypos = searchCriteria.getPosition().split(",");
            for (int i = 0; i < arraypos.length; i++) {
                sb.append("?");
                if (i != arraypos.length - 1) {
                    sb.append(",");
                } else {
                    sb.append(")");
                }
                params.add(arraypos[i]);
            }
        }

        if (searchCriteria.getCity() != null && !"".equals(searchCriteria.getCity())) {
            sb.append(" and r.workCity in (");
            String[] arraycitys = searchCriteria.getCity().split(",");
            for (int i = 0; i < arraycitys.length; i++) {
                sb.append("?");
                if (i != arraycitys.length - 1) {
                    sb.append(",");
                } else {
                    sb.append(")");
                }
                params.add(arraycitys[i]);
            }
        }

        if (searchCriteria.getSalary() != null && !"".equals(searchCriteria.getSalary())) {
            sb.append(" and r.salary  = ?");
            params.add(searchCriteria.getSalary());

        }

        if (searchCriteria.getEducation() != null && !searchCriteria.getEducation().equals("")) {
            sb.append(" and r.education= ?");
            params.add(searchCriteria.getEducation());
        }

        if (searchCriteria.getPublishDate() != null && !"".equals(searchCriteria.getPublishDate())) {

            if (searchCriteria.getPublishDate().equals("今天")) {
                //今天
                sb.append("  and  trunc(r.publishDate) = trunc(sysdate)");

            } else if (searchCriteria.getPublishDate().equals("最近三天")) {
                sb.append("  and  trunc(r.publishDate)<=trunc(sysdate) and trunc(r.publishDate)>trunc(sysdate) - 3");

            } else if (searchCriteria.getPublishDate().equals("最近一周")) {
                sb.append("  and  trunc(r.publishDate)<=trunc(sysdate) and trunc(r.publishDate)>trunc(sysdate) - 7");

            } else if (searchCriteria.getPublishDate().equals("最近一个月")) {
                sb.append("  and  trunc(r.publishDate)<=trunc(sysdate) and trunc(r.publishDate)>trunc(sysdate) - 30");

            }
        }

        if (searchCriteria.getExperience() != null && !searchCriteria.getExperience().equals("")) {
            sb.append(" and r.workYears = ?");
            params.add(searchCriteria.getExperience());
        }

        if (searchCriteria.getPosType() != null && !searchCriteria.getPosType().equals("")) {
            sb.append(" and r.partorfull = ?");
            params.add(searchCriteria.getPosType());
        }
        if (searchCriteria.getComPro() != null && !searchCriteria.getComPro().equals("")) {
            sb.append(" and c.comPro = ?");
            params.add(searchCriteria.getComPro());


        }
        if (searchCriteria.getComScale() != null && !searchCriteria.getComScale().equals("")) {
            sb.append(" and c.comScale = ?");
            params.add(searchCriteria.getComScale());

        }
        if (searchCriteria.getSortbycri() != null && !searchCriteria.getSortbycri().equals("")) {
            if (searchCriteria.getSortbycri().equals("智能排序")) {
                //todo

            } else if (searchCriteria.getSortbycri().equals("月薪最高")) {
                sb.append(" order by r.salary desc");


            } else if (searchCriteria.getSortbycri().equals("最新发布")) {
                sb.append(" order by  r.publishDate desc");
            }


        }
        sb.append(") b  left join Dttalentpool p on p.riid = b.riid");

        sb.append(" and p.staffid = ? ");
        String staffid = "1";
        if (cus != null) {
            staffid = cus.getStaffid();
        }
        params.add(staffid);

        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 10, sb.toString(), "select count(*) from(" + sb.toString() + ")", params.toArray());

        return pageForm;
    }


    /**
     * 找人才
     *
     * @return
     */
    private PageForm searchResumeInfo(SearchCriteria searchCriteria) {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        StringBuilder sb = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sb.append("select b.resumeID,b.position,b.staffname,b.region,b.headimage,b.industry,b.educationValue,b.birthday,nvl(p.tpid,'0')  from");
        sb.append(" (select r.resumeID,j.position,f.staffname,j.region,f.headimage,j.industry,f.educationValue,f.birthday");
        sb.append(" from dtresumeS r");
        sb.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
        sb.append(" left join dt_hr_staff f on f.staffID = r.staffID");
        if (searchCriteria != null) {
            if (searchCriteria.getExperience() != null
                    && !"".equals(searchCriteria.getExperience())
                    && !"经验".equals(searchCriteria.getExperience())) {
                sb.append(" left join (select nvl(trunc(sum(endtime - starttime) / 365,1),0) as exp,resumeID");
                sb.append(" from dt_hr_staff_Resume  group by resumeID ) fa on r.resumeID = fa.resumeID");
            }

            if (searchCriteria.getAge() != null
                    && !"".equals(searchCriteria.getAge())
                    && !"年龄".equals(searchCriteria.getAge())) {
                sb.append(" left join (select nvl(floor(months_between(sysdate, to_date(birthday, 'yyyy-mm-dd')) / 12),0)");
                sb.append(" as age,staffid from dt_hr_staff) sf on f.staffid = sf.staffid");
            }


            sb.append(" where r.privacy = ? and j.position like ?");

            params.add("00");
            params.add("%" + searchCriteria.getKeyword() + "%");
            //行业
            if (searchCriteria.getIndustry() != null && !"".equals(searchCriteria.getIndustry())) {
                sb.append(" and j.industry in (");
                String[] arrayindus = searchCriteria.getIndustry().split(",");
                for (int i = 0; i < arrayindus.length; i++) {
                    sb.append("?");
                    if (i != arrayindus.length - 1) {
                        sb.append(",");
                    } else {
                        sb.append(")");
                    }
                    params.add(arrayindus[i]);
                }
            }
            //职位类别
            if (searchCriteria.getPosition() != null && !"".equals(searchCriteria.getPosition())) {
                sb.append(" and j.position in (");
                String[] arraypos = searchCriteria.getPosition().split(",");
                for (int i = 0; i < arraypos.length; i++) {
                    sb.append("?");
                    if (i != arraypos.length - 1) {
                        sb.append(",");
                    } else {
                        sb.append(")");
                    }
                    params.add(arraypos[i]);
                }
            }
            //城市
            if (searchCriteria.getCity() != null && !"".equals(searchCriteria.getCity())) {
                sb.append(" and j.region in (");
                String[] arraycitys = searchCriteria.getCity().split(",");
                for (int i = 0; i < arraycitys.length; i++) {
                    sb.append("?");
                    if (i != arraycitys.length - 1) {
                        sb.append(",");
                    } else {
                        sb.append(")");
                    }
                    params.add(arraycitys[i]);
                }
            }

            //性别
            if (searchCriteria.getSex() != null
                    && !"".equals(searchCriteria.getSex())
                    && !"性别".equals(searchCriteria.getSex())) {
                sb.append(" and f.sex = ?");
                params.add(searchCriteria.getSex());
            }

            //学历
            if (searchCriteria.getEducation() != null
                    && !"".equals(searchCriteria.getEducation())
                    && !"学历".equals(searchCriteria.getEducation())) {
                sb.append(" and f.educationValue = ?");
                params.add(searchCriteria.getEducation());
            }


            //工作经验
            if (searchCriteria.getExperience() != null
                    && !"".equals(searchCriteria.getExperience())
                    && !"经验".equals(searchCriteria.getExperience())) {
                String exp = searchCriteria.getExperience();
                int start = 1;
                int end = 1000;
                if (exp.equals("无经验")) {
                    start = 0;
                    end = 0;
                } else if (exp.indexOf("-") != -1) {
                    start = Integer.parseInt(exp.substring(0, exp.indexOf("-")));
                    end = Integer.parseInt(exp.substring(exp.indexOf("-") + 1, exp.indexOf("年")));
                } else if (exp.equals("1年以下")) {
                    start = 0;
                    end = 1;
                } else {
                    start = 10;
                }

                sb.append(" and nvl(exp,0) between ? and ?");
                params.add(start);
                params.add(end);
            }


            //年龄
            if (searchCriteria.getAge() != null
                    && !"".equals(searchCriteria.getAge())
                    && !"年龄".equals(searchCriteria.getAge())) {
                String age = searchCriteria.getAge();
                int start = 0;
                int end = 1000;
                if (age.indexOf("-") != -1) {
                    start = Integer.parseInt(age.substring(0, 2));
                    end = Integer.parseInt(age.substring(age.indexOf("-") + 1, age.length() - 1));
                } else {
                    start = Integer.parseInt(age.substring(0, 2));
                }

                sb.append(" and age between ? and ?");

                params.add(start);
                params.add(end);
            }
        }
        sb.append(") b left join Dttalentpool p on b.resumeid = p.resumeid ");

        sb.append(" and p.staffid = ? ");
        String staffid = "1";
        if (cus != null) {
            staffid = cus.getStaffid();
        }
        params.add(staffid);

        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 1, sb.toString(), "select count(*) from(" + sb.toString() + ")", params.toArray());

        return pageForm;
    }


    /**
     * 更多搜索条件信息获取，以及数量获取
     *
     * @return
     */
    public String moreSearchCri() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String cri = request.getParameter("cri");
        String hql = "from ProductPackaging where parentName = ? and type=? order by createTime";
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cri, "招聘"});
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "";
        List<BaseBean> countlist = new ArrayList<BaseBean>();
        if (cri != null && "学历".equals(cri)) {
            sql = "select count(*),education from dtRecruitInfo where status = ? group by education";
            countlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01"});
            map.put("countlist", countlist);
        }

        List<BaseBean> clist = null;
        if (cri != null && "发布时间".equals(cri)) {
            //今天
            sql = "select count(*),? from dtRecruitInfo where trunc(publishDate) = trunc(sysdate)";
            clist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"今天"});
            countlist.addAll(clist);
            map.put("countlist", countlist);

            sql = "select count(*),? from dtRecruitInfo where trunc(publishDate)<=trunc(sysdate) and trunc(publishDate)>trunc(sysdate) - ? ";

            //最近三天

            clist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"最近三天", 3});
            countlist.addAll(clist);

            //最近一周
            clist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"最近一周", 7});
            countlist.addAll(clist);


            //最近一周
            clist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"最近一个月", 30});
            countlist.addAll(clist);

            map.put("countlist", countlist);

        }
        if (cri != null && "工作经验".equals(cri)) {
            sql = "select count(*),workYears from dtRecruitInfo where status = ? group by workYears";
            countlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01"});
            map.put("countlist", countlist);
        }

        if (cri != null && "职位类型".equals(cri)) {
            sql = "select count(*),partorfull from dtRecruitInfo where status = ? group by partorfull";
            countlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01"});
            map.put("countlist", countlist);
        }
        if (cri != null && "公司性质".equals(cri)) {
            sql = "select count(*),c.comPro from dtRecruitInfo r,dtContactCompany c where status = ? and r.ccompanyID = c.ccompanyID group by c.comPro";
            countlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01"});
            map.put("countlist", countlist);
        }
        if (cri != null && "公司规模".equals(cri)) {
            sql = "select count(*),c.comScale from dtRecruitInfo r ,dtContactCompany c where status = ? and r.ccompanyID = c.ccompanyID group by c.comScale";
            countlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01"});
            map.put("countlist", countlist);
        }

        map.put("list", list);
        JSONObject jo = JSONObject.fromObject(map);

        result = jo.toString();

        return "success";
    }

    /**
     * 清除搜索记录
     *
     * @return
     */
    public String clearSearchRecord() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
        if (cus == null) {
            String url = request.getHeader("Referer");
            session.setAttribute("url", url);
            map.put("login", "login");
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";
        }
        String updatehql = "update HistorySearch set staffID = null where type = ? and staffID = ?";
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[]{"00", cus.getStaffid()});
        baseBeanService.executeHqlsByParamsList(null, new String[]{updatehql}, params);

        map.put("result", "success");
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 投递简历
     *
     * @return
     */
    public String postResume() {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            if (cus == null) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                map.put("login", "login");
                JSONObject jo = JSONObject.fromObject(map);
                this.result = jo.toString();
                return "success";
            }
            if (cus.getState().equals("2")) {
                map.put("result", "nojianli");
                JSONObject jo = JSONObject.fromObject(map);
                this.result = jo.toString();
                return "success";

            }
            map.put("staffid", cus.getStaffid());

            String hql = "from ResumeS where staffID = ? and isDefault = ?";
            ResumeS res = (ResumeS) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cus.getStaffid(), "01"});

            if (res == null) {

                List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams("from ResumeS where staffID = ? order by creationTime", new Object[]{cus.getStaffid()});
                if (list == null || list.size() == 0) {
                    map.put("result", "noresume");
                } else {
                    res = (ResumeS) list.get(0);
                    res.setIsDefault("01");
                    baseBeanService.update(res);
                }

            }

            if (res != null) {
                String riIds = request.getParameter("riIds");
                TalentPool tp = null;
                List<BaseBean> baseBeans = new ArrayList<BaseBean>();
                String hqlri = "from RecruitInfo where riId = ?";
                String sqltal = "select t.riId from dtTalentPool t where staffID = ? and riId in(";
                List<Object> paramtal = new ArrayList<Object>();
                paramtal.add(cus.getStaffid());
                RecruitInfo rinfo = null;
                if (riIds != null && !"".equals(riIds)) {
                    String[] arrayriId = riIds.split(",");
                    for (int i = 0; i < arrayriId.length; i++) {
                        if (i != arrayriId.length - 1) {
                            sqltal += "?,";
                        } else {
                            sqltal += "?)";
                        }

                        paramtal.add(arrayriId[i].trim());
                    }
                    List<String> existlist = baseBeanService.getListBeanBySqlAndParams(sqltal, paramtal.toArray());

                    for (int i = 0; i < arrayriId.length; i++) {
                        if (!existlist.contains(arrayriId[i].trim())) {
                            tp = new TalentPool();
                            tp.setTpId(serverService.getServerID("tpId"));
                            tp.setStaffID(cus.getStaffid());
                            tp.setState("00");//投递
                            tp.setType("00");//投递
                            tp.setRiId(arrayriId[i].trim());
                            tp.setPostDate(new Date());
                            tp.setResID(res.getResumeID());
                            //查询公司ID
                            rinfo = (RecruitInfo) baseBeanService.getBeanByHqlAndParams(hqlri, new Object[]{arrayriId[i].trim()});
                            tp.setCompanyId(rinfo.getCompanyID());

                            RelateStaff relateStaff = (RelateStaff)baseBeanService.getBeanByHqlAndParams("from RelateStaff where staffID = ? and companyID = ?",new Object[]{cus.getStaffid(),rinfo.getCompanyID()});
                            if(relateStaff==null){
                                relateStaff = new RelateStaff();
                                relateStaff.setRsId(serverService.getServerID("rsid"));
                                relateStaff.setCreateDate(new Date());
                                relateStaff.setCompanyID(rinfo.getCompanyID());
                                relateStaff.setStaffID(cus.getStaffid());
                                baseBeans.add(relateStaff);
                            }

                            baseBeans.add(tp);
                        }
                    }
                }
                if (baseBeans.size() != 0) {
                    baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeans, null, null);

                    bidsRecruitService.noticeMessage("t", tp);
                }


                map.put("result", "success");
            }
        } catch (Exception e) {
            map.put("result", "fail");
            logger.error("操作异常", e);
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }


    /**
     * 企业抢人
     *
     * @return
     */
    public String grabResume() {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            boolean b = true;
            if (cus == null) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                map.put("login", "login");
                b = false;

            } else {
                if (!cus.getState().equals("2")) {
                    map.put("result", "nocom");
                    b = false;
                }

                map.put("user", cus.getAccount());
                map.put("sccid", cus.getSccId());
            }

            if (b) {

                String resumeIDs = request.getParameter("resumeIDs");
                BigDecimal xmoney = new BigDecimal(0);

                if (resumeIDs != null && !resumeIDs.equals("")) {
                    xmoney = new BigDecimal(resumeIDs.split(",").length)
                            .multiply(new BigDecimal(100));
                }
                WfjJifen wfjJifen = (WfjJifen) baseBeanService
                        .getBeanByHqlAndParams("from WfjJifen where staffId=?",
                                new Object[]{cus.getStaffid()});

                boolean bool = true;
                if (wfjJifen == null || wfjJifen.getWfjJifenScore().equals("0")) {
                    map.put("result", "nomoney");
                    bool = false;
                } else {
                    BigDecimal cc = new BigDecimal(wfjJifen.getWfjJifenScore());
                    if (xmoney.compareTo(cc) == 1) {
                        map.put("result", "nomoney");
                        bool = false;
                    }

                }
                if (bool) {
                    bidCommonService.consumeWfjJinbi(cus, resumeIDs, "1");
                    map.put("result", "success");
                }
            }
        } catch (Exception e) {
            map.put("result", "fail");
            logger.error("操作异常", e);
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     * 抢人成功
     *
     * @return
     */
    public String grabSuccess() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        String position = request.getParameter("position");

        StringBuilder sb = new StringBuilder();
        sb.append("select r.resumeID,j.position,f.staffname,j.region,f.headimage,f.educationValue");
        sb.append(" from dtresumeS r");
        sb.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
        sb.append(" left join dt_hr_staff f on f.staffID = r.staffID");
        sb.append(" where r.privacy = ? and j.position like ?");
        sb.append(" and r.resumeID not in(select p.resumeID from dtTalentPool p where p.companyId = ?)");
        sb.append(" and rownum < 11");
        sb.append(" order by r.creationTime desc");
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sb.toString(), new Object[]{"00", "%" + position + "%", cus.getCompanyId()});
        request.setAttribute("tuilist", list);
        return "grabsuc";

    }

    /**
     * 投递成功
     *
     * @return
     */
    public String postSuccess() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        String position = request.getParameter("position");
        String hql = "from RecruitInfo p where p.status = ? and p.jobTitle like ? and rownum<11 and p.riId not in(select t.riId from TalentPool t where t.staffID = ?) order by p.publishDate desc";
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"01", "%" + position + "%", cus.getStaffid()});
        request.setAttribute("tuilist", list);

        return "postsuc";

    }

    /**
     * 收藏职位
     *
     * @return
     */
    public String collectPosition() {

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
            Map<String, Object> map = new HashMap<String, Object>();

            if (cus == null) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                map.put("login", "login");
            } else {


                String type = request.getParameter("type");
                String id = request.getParameter("id");

                String hql = "from CollectThing where id = ?";
                CollectThing ct = (CollectThing) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});
                if (ct == null) {
                    CollectThing collect = new CollectThing();
                    collect.setCoId(serverService.getServerID("coid"));
                    collect.setId(id);
                    collect.setType(type);
                    collect.setColDate(new Date());
                    collect.setStaffID(cus.getStaffid());
                    baseBeanService.save(collect);
                }
                map.put("result", "success");
            }

            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {

            logger.error("操作异常", e);
        }
        return "success";

    }

    /**
     * 取消收藏
     *
     * @return
     */
    public String cancelCollect() {

        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
            Map<String, Object> map = new HashMap<String, Object>();

            if (cus == null) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                map.put("login", "login");
            } else {
                String id = request.getParameter("id");
                String hql = "delete from CollectThing where coId = ?";
                baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{id});

                map.put("result", "success");
            }

            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {

            logger.error("操作异常", e);
        }
        return "success";

    }

    /**
     * 发布页面
     *
     * @return
     */
    public String getPositionPub() {
        if (recruitInfo != null && recruitInfo.getRiId() != null && !recruitInfo.getRiId().equals("")) {
            recruitInfo = bidsRecruitService.getEditRecruitInfo(recruitInfo.getRiId());
        }

        return "topub";
    }

    /**
     * 查看
     *
     * @return
     */
    public String getViewRecruit() {
        if (recruitInfo != null && recruitInfo.getRiId() != null && !recruitInfo.getRiId().equals("")) {
            recruitInfo = bidsRecruitService.getEditRecruitInfo(recruitInfo.getRiId());
        }

        return "todetail";
    }

    /**
     * 发布招聘信息
     *
     * @return
     */
    public String addPosition() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw=SessionWrap.getInstance();
        String sccId = request.getParameter("sccId");

        String staffID = "";
        String companyID = "";
        String companyName = "";
        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            staffID = tc.getStaffid();
            companyID = tc.getCompanyId();
            companyName = tc.getPseudoCompanyName();
        } else {

            Map<String, Object> session1 = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session1.get("account");

            staffID = account.getStaffID();
            companyID = account.getCompanyID();
            companyName = account.getCompanyName();
            if(companyName==null||companyName.equals("")){
            	Company c = (Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{companyID});
            	companyName = c.getCompanyName();
            }
        }

        bidsRecruitService.addPosition(recruitInfo, staffID, companyID, companyName);

        return "success";

    }


    /**
     * 招聘信息列表
     *
     * @return
     */
    public String findPositionList() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String sccId = request.getParameter("sccId");
        String status = request.getParameter("status");
        String ajax = request.getParameter("ajax");
        String companyID = "";

        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});

            companyID = tc.getCompanyId();

        } else {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");

            companyID = account.getCompanyID();

        }

        pageForm = bidsRecruitService.findPositionList(pageNumber, 10, companyID, status);

        if ("ajax".equals(ajax)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            JSONObject obj = JSONObject.fromObject(map, jsonConfig);
            result = obj.toString();

            return "success";
        }
        return "topostion";

    }


    /**
     * 发布或者取消招聘信息
     *
     * @return
     */
    public String onOfflineRecruit() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw=SessionWrap.getInstance();
        String sccId = request.getParameter("sccId");
        String staffID = "";
        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});

            staffID = tc.getStaffid();
        } else {
            Map<String, Object> session1 = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session1.get("account");

            staffID = account.getStaffID();

        }

        bidsRecruitService.onOfflineRecruit(recruitInfo.getRiId(), staffID);
        return "success";

    }

    /**
     * 获取投递的简历
     *
     * @return
     * @throws ParseException
     */
    public String getTalentResumeList() throws ParseException {
        HttpServletRequest re = ServletActionContext.getRequest();

        String sccId = re.getParameter("sccId");

        String jobTitle = re.getParameter("jobTitle");
        String state = re.getParameter("state");
        String ajax = re.getParameter("ajax");
        String staffID = re.getParameter("staffID");
        String companyID = "";

        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            companyID = tc.getCompanyId();

        } else {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");

            companyID = account.getCompanyID();
        }

        pageForm = bidsRecruitService.getTalentResumeList(pageNumber, 8, companyID, state, jobTitle,staffID);
        if ("ajax".equals(ajax)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            JSONObject obj = JSONObject.fromObject(map, jsonConfig);
            result = obj.toString();

            return "success";
        }
        return "totalent";
    }

    /**
     * 获取职位
     *
     * @return
     * @throws ParseException
     */
    public String getFilterResume() throws ParseException {
        HttpServletRequest re = ServletActionContext.getRequest();

        String sccId = re.getParameter("sccId");
        ;
        String companyID = "";

        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});

            companyID = tc.getCompanyId();

        } else {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");

            companyID = account.getCompanyID();

        }

        pageForm = bidsRecruitService.findPositionList(pageNumber, 30, companyID, "01");
        return "filterResume";
    }


    /***
     *
     *
     * 求职详情
     * @return
     */
    public String resumedetail() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String resumeID = request.getParameter("resumeID");
        String tpId = request.getParameter("tpId");

        Map<String, Object> map = bidsRecruitService.resumedetail(resumeID, tpId);
        request.setAttribute("obj", map.get("obj"));
        request.setAttribute("edu", map.get("edu"));
        request.setAttribute("srlist", map.get("srlist"));
        request.setAttribute("edulist", map.get("edulist"));


        return "redetail";
    }


    /***
     *
     *
     * 求职详情
     * @return
     */
    public String setOperate() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        String tpId = request.getParameter("tpId");
        String state = request.getParameter("state");
        String sccId = request.getParameter("sccId");
        String staffID = "";

        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            staffID = tc.getStaffid();

        } else {
            Map<String, Object> session1 = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session1.get("account");

            staffID = account.getStaffID();

        }
        bidsRecruitService.setOperate(tpId, state, staffID);


        return "success";
    }

    /**
     * 获取邀请信息
     *
     * @return
     */
    public String getInventInfo() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String sccId = request.getParameter("sccId");
        String tpId = request.getParameter("tpId");

        String staffID = "";
        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            staffID = tc.getStaffid();
        } else {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");


            staffID = account.getStaffID();
        }

        Map<String, Object> map = bidsRecruitService.getInventInfo(staffID, tpId);

        request.setAttribute("staff", map.get("staff"));
        request.setAttribute("talentPool", map.get("talentPool"));
        request.setAttribute("postionlist", map.get("postionlist"));
        return "toinvent";

    }


    /**
     * 发送邀请
     *
     * @return
     */
    public String sendInvent() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String tpId = request.getParameter("tpId");
        String dates = request.getParameter("dates");

        Date indate = Utilities.getDateFromString(dates+":00","yyyy-MM-dd HH:mm:ss");
        talentPool.setInterviewDate(indate);
        bidsRecruitService.sendInvent(tpId, talentPool);
        return "success";
    }

    /**
     * 我关注的职位
     */
    @SuppressWarnings("unchecked")
    public String getCollectResume() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw=SessionWrap.getInstance();
        String sccId = request.getParameter("sccId");
        String type = request.getParameter("type");

        String staffID = "";
        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            staffID = tc.getStaffid();
        } else {

            Map<String, Object> session1 = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session1.get("account");

            staffID = account.getStaffID();
        }

        pageForm = bidsRecruitService.getCollectResume(pageNumber, 30, staffID);

        if ("ajax".equals(type)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";
        }
        return "collectResume";
    }

    /**
     *
     * 取消简历收藏
     * @return
     */
    public String cancelCollectR() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw=SessionWrap.getInstance();
        String sccId = request.getParameter("sccId");
        String resumeID = request.getParameter("resumeID");

        String staffID = "";
        if (sccId != null && !sccId.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            staffID = tc.getStaffid();
        } else {

            Map<String, Object> session1 = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session1.get("account");

            staffID = account.getStaffID();
        }

        bidsRecruitService.cancelCollectR(resumeID, staffID);

        return "success";

    }


    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }


    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }


    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public RecruitInfo getRecruitInfo() {
        return recruitInfo;
    }

    public void setRecruitInfo(RecruitInfo recruitInfo) {
        this.recruitInfo = recruitInfo;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }


    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }


    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }


    public String getCcompanyID() {
        return ccompanyID;
    }


    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }


    public String getCompanyId() {
        return companyId;
    }


    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public String getPageSize() {
        return pageSize;
    }


    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getBack() {
        return back;
    }


    public void setBack(String back) {
        this.back = back;
    }


    public TalentPool getTalentPool() {
        return talentPool;
    }

    public void setTalentPool(TalentPool talentPool) {
        this.talentPool = talentPool;
    }
}
