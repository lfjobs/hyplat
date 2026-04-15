package com.tiantai.wfj.front;


import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.*;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.DrivingSchool.SchProCategory;
import hy.ea.bo.company.*;
import hy.ea.bo.finance.ProductComment;
import hy.ea.bo.finance.ProductCommentMain;
import hy.ea.bo.human.*;
import hy.ea.bo.office.Certificate;
import hy.ea.bo.production.recruit.RecruitInfo;
import hy.ea.finance.service.SetSubsidizeService;
import hy.ea.production.service.ConsultManageService;
import hy.ea.service.UpLoadFileService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.HttpRequestDeviceUtils;
import hy.ea.util.StringUtil;
import hy.ea.util.Utilities;
import hy.ea.websitemall.card.service.QRCodeService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.BusinessType;
import hy.plat.bo.PageForm;
import hy.plat.dao.BusinessTypeDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tiantai.wfj.util.SessionWrap.KEY_CUSTOMER;
import static com.tiantai.wfj.util.SessionWrap.KEY_SHOPCUSCOM;


/**
 * 行业分类
 */
@Controller
@Scope("prototype")
public class IndustryClassificationAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private UpLoadFileService fileService;
    @Resource
    private ServerService serverService;
    @Resource
    private QRCodeService qrCodeService;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private IndustryClassificationService industryService;
    @Resource
    private ProductlaunchService productlaunchService;
    @Resource
    private WfjJifenService wfjserv;
    @Resource
    private SetSubsidizeService setSubsidizeService;
    @Resource
    private ConsultManageService consultService;
    @Resource
    private BusinessTypeDao businessTypeDao;


    @Resource
    private EarthIndexService earthIndexService;
    private int count = 0;
    private String companyId;// 微信传过来的companyId
    private String search;// 判断搜索
    private String result;
    private List<BaseBean> beans;
    private List<BaseBean> companyJList;//公司简介
    private List<BaseBean> BankCardList;//银行卡列表
    private List<BaseBean> ActivitiesList;//活动列表
    private List<BaseBean> productList;// 产品列表
    private List<Object> companyMienList;//公司风采
    private List<BaseBean> companyForumsList;//公司论坛

    private String codePID;//行业分类，二级分类
    private String industryType;//行业类型
    private String ccompanyId;//往来单位
    private ContactCompany contactCompany;//往来单位
    private RecruitInfo recruitInfo;//招聘表
    private CcomConf ccomconf;//公司的首页配置
    private String ccomConfId;//公司首页配置id
    private StaffBankAccount staffbank;//银行卡管理
    private String user;//用户名
    private String bankAccountId;//银行卡id
    private Activities activities;//活动
    private String activitiesId;//活动id;
    private CardMaterial material;//素材管理
    private MienStyle ms;//风采管理
    private String materialId;//素材id;
    private String mienstyleId;//风采业务id
    private Company company;
    private File[] pics;//多个文件
    private String[] picsFileName;//多个文件名

    private int pageNumber;
    private int pageSize;
    private PageForm pageForm;
    private List<BaseBean> companycertificates;//公司证件
    private Certificate certificate;//证件信息
    private String certificateID;//证件信息Id
    private String certificatetype;//证件类型
    private String certificatephoto;//证件照片正面
    private String certificatephoto1;//证件照片反面
    private String companyAddr;//公司地址
    private List<BaseBean> companynewsList;//公司新闻列表
    private String ppname;//产品名称
    private int companycount;//入驻公司数
    private PageForm productsPageForm;//
    private String style;//公司风格
    private String editType;//判断公司名片是否为编辑 0：可编辑
    private String onlyCompany;//判断公司名称

    private CCode code;
    private String stype;//做判断用的一级，二级,三级详细的
    private String codeID;//行业的ID
    private String codesId;
    private String ajax;
    private String codename;
    private String parentName;

    private String addressDetailed;//收货人详细地址
    private String phone;//收货人电话
    private String area;//收货人所在区
    private String consignee;//收货人
    private String postCode;//收货人邮编
    private String branchName;  //支行的名称
    private StaffAddress staffAddres;


    //银行卡卡号和对应的银行匹配
    private String cardnum = "cardnum";
    private String httpArg;
    final String httpUrl = "http://apis.baidu.com/datatiny/cardinfo/cardinfo";
    private String typeNews;
    private String ppId;
    private Map<String, Object> map;
    private String type;//公司网站简介和文化
    private String activityType;//活动类别
    private Map<Integer, String[]> maplist3;
    private int back;//标识-xgb
    private String goodsid;//物品ID-xgb
    private List<BaseBean> list1;//更多精彩-xgb
    private Object object1;//更多精彩-xgb
    private String miniSystemJudge;
    private Map<Integer, String[]> maplist1;//更多精彩-xgb
    private String sccid;
    private String province;//所属省份
    private String bankAddress;// 所属城市

    private String identifying;//判断个人 登录    or 公司登录
    private String flag;//判断是移动办公进去   还是个人中心进入    标识
    private String khd; //0为网页查看  1为APP查看
    private String mark;
    private String staffid;
    private String applied;
    private String accuracy;
    private String dimension;
    private String city;
    private ByteArrayInputStream stream;
    private BusinessType businessType;

    @Resource
    private RestaurantService restaurantService;

    private ConsultingRegistration consultingRegistration;

    private final Logger logger = LoggerFactory.getLogger(IndustryClassificationAction.class);


    public String marketing() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, KEY_CUSTOMER);
        if (cus == null) {
            typeNews = "咨询";
            return "login";
        }

        return "marketing";
    }

    /**
     * 行业导航
     * 一级行业
     */
    public String getIndustry() {
        String hql = "from SCode a where a.codePID = ? and a.codetype = ? order by a.codeNumber";
        String codeType = "HY";
        if (codePID == null || codePID.equals("")) {
            codePID = "scode20170714cnjcrn5jm20000000067";
        }
        Object[] params = {codePID, codeType};
        List<BaseBean> industryList = baseBeanService.getListBeanByHqlAndParams(hql, params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("industryList", industryList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 中联招商的
     * 行业导航
     * 一级行业
     */
    public String getiInvestment() {
        String hql = "from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";
        // 公司id《北京天太世统科技有限公司的》
        String companyIDD = "company201009046vxdyzy4wg0000000025";
        if (stype.equals("one") && stype != null) {
            if (codePID == null || codePID.equals("")) {
                codePID = "scode20150815wygb79q82p0000000005";
            }
            Object[] params = {companyIDD, codePID};
            // 一级
            beans = baseBeanService.getListBeanByHqlAndParams(hql, params);
            return "Investment";
        } else if (stype.equals("two") && stype != null) {
            Object[] params = {companyIDD, codesId};
            // 二级
            pageForm = baseBeanService.getPageForm(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 13, hql,
                    params);
            // 上拉加载
            if (ajax.equals("ajax")) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pageForm", pageForm);
                JSONObject obj = JSONObject.fromObject(map);
                result = obj.toString();
                return "success";
            }

            return "secondLevel";
        } else {
            String hqls = "from CCode a where a.companyID = ? and a.codeID = ?";
            code = (CCode) baseBeanService.getBeanByHqlAndParams(hqls,
                    new Object[]{companyIDD, codeID});

            return huiyuan();
        }

    }

    /**
     * 中联招商的
     * 下订单填写的地址
     */
    public void address() {
        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCustomer teshopCustomer = (TEshopCustomer) sessionWrap.getObject(
                httpSession, KEY_CUSTOMER);
        String hql1 = "from TEshopCusCom cc where cc.account=?";
        TEshopCusCom teshopcuscom = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams(hql1,
                        new Object[]{teshopCustomer.getAccount()});
        String hqlo = " from StaffAddress s where s.phone=?";
        StaffAddress staffAddressAdd = new StaffAddress();
        staffAddres = (StaffAddress) baseBeanService.getBeanByHqlAndParams(
                hqlo, new Object[]{phone});
        if (staffAddres.getPhone() == null && "" == staffAddres.getPhone()
                && staffAddres.getPhone().length() < 0) {
            staffAddressAdd.setStaffID(teshopcuscom.getStaffid());
            staffAddressAdd.setCompanyID(teshopcuscom.getCompanyId());
            staffAddressAdd.setAddressID(serverService
                    .getServerID("StaffAddress"));
            staffAddressAdd.setAddressDetailed(addressDetailed);
            staffAddressAdd.setArea(area);
            staffAddressAdd.setConsignee(consignee);
            staffAddressAdd.setPhone(phone);
            staffAddressAdd.setPostCode(postCode);
            baseBeanService.save(staffAddressAdd);
        }

    }

    /**
     * 中联招商的
     * 如果存在了，直接给他默认地址
     */
    public String getAddress() {
        String shq1 = "select s.addressID,to_char(s.livestartDate,'YYYY-MM-DD HH24:MI:SS'),to_char(s.liveendDate,'YYYY-MM-DD HH24:MI:SS') from dt_hr_staff_address s where s.phone=? and s.isDefault='是'";
        Object obj = baseBeanService.getObjectBySqlAndParams(shq1, new Object[]{phone});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("obj", obj);
        JSONObject js = JSONObject.fromObject(map);
        result = js.toString();
        return "success";
    }

    /**
     * 查询公司加入跟个人加入
     */
    @SuppressWarnings("unchecked")
    public String huiyuan() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession sessions = request.getSession();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                KEY_CUSTOMER);

        //公司
        String hql = "from TEshopCusCom t where t.state = ? and t.companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)";
        TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"2", ccompanyId});
        String cusType = "";
        String lowType = "";
        //查找下级
        if (tcc != null) {
            cusType = tcc.getCusType();//网站的
            String sqlt = "select t.cusType from T_ESHOP_CUSCOM t connect by nocycle prior t.account = t.Superioragent start with t.account = ? order by t.cusType desc";
            List<Object> list = baseBeanService.getListBeanBySqlAndParams(sqlt, new Object[]{tcc.getAccount()});
            lowType = list.get(0).toString();
            if (lowType.equals("0.5")) {
                lowType = "1";
            } else if (lowType.equals("0")) {
                lowType = "0.5";
            } else {
                lowType = Integer.parseInt(lowType) + 1 + "";
            }
        }
        String sql = "select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? "
                + " and p.ppid=ps.ppid and p.showweixin ='01' and p.type= ? and  ps.fcom_id is null ";
        String sqlgs = sql + " and model != ?  order by sorting";
        sql = sql + " and ((p.model<=?";
        if (cusType.equals("0")) {
            sql += " and p.model>=?";
            String sqls = "select t.cusType from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?) connect by nocycle prior t.account = t.Superioragent start with t.account = ?";
            List<Object> lists = baseBeanService.getListBeanBySqlAndParams(sqls, new Object[]{"0.5", "1", tcc.getAccount()});

            if (lists.contains("0.5")) {
                sql += " and p.model!='0.5'";
            }
            if (lists.contains("1")) {
                sql += " and p.model!='1'";
            }
        } else {
            sql += " and p.model>?";
        }
        String sqlgr = sql + ") or model = '8') order by sorting";
        // 查询公司会员列表
        productList = baseBeanService.getListBeanBySqlAndParams(sqlgs,
                new String[]{"company201009046vxdyzy4wg0000000025", "公司会员", "0"});
        // 查询个人会员列表
        beans = baseBeanService.getListBeanBySqlAndParams(sqlgr, new String[]{
                "company201009046vxdyzy4wg0000000025", "个人会员", lowType, cusType});
        if (cus == null) {
            String url = request.getRequestURL() + "?codeID="
                    + codeID + "&codesId=" + codesId + "&stype=";
            sessions.setAttribute("url", url);
            return "login";
        }
        return "threeIndustry";
    }


    @SuppressWarnings("unchecked")
    public String getExpo() {
        //ea/industry/ea_getAllCompanyList.jspa?industryType=汽车交通工具/汽车加油&share=加盟中联园区的公司
        //汽车交通工具/汽车驾校
        String sql = "select a.codevalue,a.iconPath"
                + " from dtCCode a where a.companyID = ?"
                + " and (a.codePID = ?"
                + " or a.codePID = ?"
                + " or a.codePID =?"
                + " or a.codePID=?"
                + "or a.codePID=?"
                + " or a.codePID=?) "
                + " and (a.codevalue=?"
                + " or a.codevalue=? "
                + " or a.codevalue=?"
                + " or a.codevalue=?"
                + " or a.codevalue=?"
                + " or a.codevalue=?)"
                + " order by a.codeNumber";
        //汽车驾校
        Object[] params = {"company201009046vxdyzy4wg0000000025",
                "scode20150815wygb79q82p0000000057", "scode20150815wygb79q82p0000000306",
                "scode20150815wygb79q82p0000000090", "scode20150815wygb79q82p0000000241",
                "scode20150817wygb79q82p0000000703", "scode20150815wygb79q82p0000000276",
                "汽车驾校", "西餐厅", "台式机", "卧室家具", "能源五金", "休闲装"};
        productList = baseBeanService.getListBeanBySqlAndParams(sql, params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productList", productList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //二级行业分页
    public String getAjaxIndustry() {
        String hql = "from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";
        Object[] params = {"company201009046vxdyzy4wg0000000025", codePID};
        pageForm = baseBeanService.getPageForm(
                (null != pageForm ? pageForm.getPageNumber() : 1), 15, hql, params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //入驻公司数
    public String getCompanyCount() {
        String sql = "select count(*) from "
                + " dt_ccom_com r ,dtContactCompany cc,dt_ccom_conf ccf "
                + " where ccf.ccompnay_id=cc.ccompanyid "
                + " and r.ccompany_id=cc.ccompanyid and cc.webstatus='01' and ccf.modal_type='0'"
                + " and cc.industryType=?";
        companycount = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{industryType});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companycount", companycount);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //按行业查询公司
    public PageForm getList() {
        HttpServletRequest req = ServletActionContext.getRequest();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        if (HttpRequestDeviceUtils.isMobileDevice(req) == false) {
            if ("activity".equals(flag)) {
                sql.append(" with a as (select r.compnay_id,ccf.pic_path,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industryType ,p.activity_type  from");
            } else {
                sql.append(" with a as (select r.compnay_id,ccf.pic_path,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industryType from");
            }
            sql.append(" dt_ccom_com r ,dtContactCompany cc,dt_ccom_conf ccf");
            if ("activity".equals(flag)) {
                sql.append(" ,dt_prize_activity p");
            }
            sql.append(" where ccf.ccompnay_id=cc.ccompanyid");
            sql.append(" and r.ccompany_id=cc.ccompanyid and cc.webstatus='01' and ccf.modal_type='0'");
            if ("activity".equals(flag)) {
                sql.append(" and  sysdate between p.starting_time+0 and p.end_time+0 and p.activity_range = 0 and p.status = 0 and p.company_id = r.compnay_id and trim(p.activity_type) = ?");
                params.add(activityType);
            }
        } else {
            sql.delete(0, sql.length());
            if ("activity".equals(flag)) {
                sql.append(" with a as (select r.compnay_id,cc.jjPath,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industryType,p.activity_type from");
                sql.append(" dt_ccom_com r ,dtContactCompany cc,dt_prize_activity p where r.ccompany_id=cc.ccompanyid and cc.webstatus='01'and  sysdate between p.starting_time+0 and p.end_time+0 and p.activity_range = 0 and p.status = 0 and p.company_id = r.compnay_id and trim(p.activity_type) = ?");
                params.add(activityType);
            } else {
                sql.append(" with a as (select r.compnay_id,cc.jjPath,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industryType from");
                sql.append(" dt_ccom_com r ,dtContactCompany cc where r.ccompany_id=cc.ccompanyid and cc.webstatus='01'");
            }
        }
        if (industryType == null || industryType.equals("")) {
            sql.append(" )select a.*,cus.account from a left join t_eshop_cuscom cus on cus.companyid=a.compnay_id order by cus.account nulls last");
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 15, sql.toString(), "select count(*) from (" + sql + ")", params.toArray());
        } else {

            String[] ind = industryType.split(",");
            if (ind.length == 1) {
                sql.append(" and cc.industryType = ?");
                params.add(industryType);
            } else {
                sql.append(" and cc.industryType in (");
                for (int i = 0; i < ind.length; i++) {
                    if (i == ind.length - 1) {
                        sql.append("?)");
                        params.add(ind[i]);
                    } else {
                        sql.append("?,");
                        params.add(ind[i]);
                    }
                }
            }
            sql.append(" )select a.*,cus.account from a left join t_eshop_cuscom cus on cus.companyid=a.compnay_id order by cus.account nulls last");
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 15, sql.toString(), "select count(*) from (" + sql + ")", params.toArray());
        }
        return pageForm;
    }

    public String getPlatform() {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        String sccid = req.getParameter("sccid");
        if (sccid != null && !"".equals(sccid)) {
            TEshopCusCom eshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom c where c.sccId = ? ", new Object[]{sccid});
            TEshopCustomer eshopCustomer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer c where c.staffid = ? ", new Object[]{eshopCusCom.getStaffid()});
            sw.setObject(session, SessionWrap.KEY_CUSTOMER, eshopCustomer);
            sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, eshopCusCom);
        }
        TEshopCustomer customer = (TEshopCustomer) sw.getObject(session, KEY_CUSTOMER);
        if (customer != null) {
            Object fkStatus = baseBeanService.getObjectBySqlAndParams("select e.companyid,c.fkstatus from dtenroll e ,dtCashierBills c where e.cashierbillsid = c.cashierbillsid and e.staffid = ? and c.fkstatus = ? and rownum = 1", new Object[]{customer.getStaffid(), "00"});
            Object[] fkStatusList = (Object[]) fkStatus;
            if (fkStatusList != null) {
                applied = "00";
                if (fkStatusList[0] != null && !"".equals(fkStatusList[0])) {
                    companyId = fkStatusList[0].toString();
                }
                staffid = customer.getStaffid();
            }
        }
        String industryType = req.getParameter("industryType");
        String hql = "from IndustryRelation where industryName like ?";
        IndustryRelation ind = (IndustryRelation) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"%" + industryType + "%"});
        if (ind != null && ind.getPlatforReturn() != null && ind.getPlatforReturn() != "") {
            String sql = "select p.ppid,c.companyname,cc.ccompany_id,y.logopath from dt_ccom_com cc, t_eshop_cuscom e,dt_productpackaging p,dtcompany c,dtcontactcompany y,DT_ccom_com ccom where c.companyid = e.companyid and e.ppid = p.ppid and e.companyid = c.companyid and y.ccompanyid = ccom.ccompany_id and ccom.compnay_id = c.companyid and c.companyid = cc.compnay_id and e.custype = ? and p.ppid = ?";
            Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{"0", ind.getPpId()});
            req.setAttribute("ccom", obj);
            req.setAttribute("type", "1");
            return ind.getPlatforReturn();
        } else {
            return getAllCompanyList();
        }
//		IndustryRelation ind = new IndustryRelation();
//		ind.setIndustryId(serverService.getServerID("industry"));
//		try{
//			ind.setIndustryName(industryType);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//
//		baseBeanService.saveOrUpdate(ind);

    }

    public String getAllCompanyList() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        if (sccid != null && !sccid.equals("")) {

//                TEshopCustomer customer = (TEshopCustomer) sw.getObject(session, KEY_CUSTOMER);
//                TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, KEY_SHOPCUSCOM);
            TEshopCusCom cus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{sccid});
            TEshopCustomer customer = (TEshopCustomer) baseBeanService
                    .getBeanByHqlAndParams(
                            "from TEshopCustomer where account=? AND logOff=0",
                            new Object[]{cus.getAccount()});
            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{cus.getStaffid()});
            sw.setObject(session, KEY_CUSTOMER, customer);
            sw.setObject(session, KEY_SHOPCUSCOM, cus);
            sw.setObject(session, SessionWrap.KEY_STAFF, staff);
        }

        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (tc != null) {

            earthIndexService.addBrowseHistory(tc.getSccId(), "商家", "");
        }
        pageForm = getList();
        return "companyList";
    }

    //行业列表
    public String getAllIndustryList() {
        return "industryList";
    }


    //搜索框模糊查询公司列表(Myapp页面搜索框)
    public String getMySelCompanyList() {

        return "companyList";
    }

    //认领公司列表
    public String claimCompanyList() {

        return "claimCompanyList";
    }

    public String perfectInfoSubmit() {
        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom shopCusCom = (TEshopCusCom) sessionWrap.getObject(
                httpSession, KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
        if (shopCusCom != null) {
            if (shopCusCom.getCompanyId() != null) {
                TEshopCusCom eshopCusCom = new TEshopCusCom();
                eshopCusCom.setSccId(serverService.getServerID("TEshopCusCom"));
                eshopCusCom.setAccount(shopCusCom.getAccount());
                eshopCusCom.setStaffid(shopCusCom.getStaffid());
                eshopCusCom.setPpid(shopCusCom.getPpid());
                eshopCusCom.setState("2");
                eshopCusCom.setTeccDate(new Date());
                eshopCusCom.setPseudoCompanyName(contactCompany.getCompanyName());
                eshopCusCom.setSupperSccId(shopCusCom.getSupperSccId());
                shopCusCom = eshopCusCom;
            }
            Company company = new Company();// 购买使用对象
            CDetail cdl = new CDetail();// 公司详细信息
            cdl.setCompanyAddress("1");
            cdl.setRegistrationNumber("1");
            cdl.setCompanyEmail("1");
            cdl.setCompanyManager("1");
            cdl.setCompanyPhone("1");

            ContactCompany contactCompany1 = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany c where c.ccompanyID = ?", new Object[]{contactCompany.getCcompanyID()});

            contactCompany1.setWebstatus("01");
            contactCompany1.setCustStatus("02");
            contactCompany1.setIndustryType(contactCompany.getIndustryType());
            contactCompany1.setCompanyName(contactCompany.getCompanyName());
            contactCompany1.setAuthState("00");
            contactCompany1.setCompanyAddr(contactCompany.getCompanyAddr());
            contactCompany1.setCompanyName(contactCompany.getCompanyName());
            contactCompany1.setCompanyTel(contactCompany.getCompanyTel());
            contactCompany1.setCresponsible(contactCompany.getCresponsible());
            contactCompany1.setLogoPath(contactCompany.getLogoPath());

            company.setCompanyIdentifier(contactCompany1.getCompanyName());
            company.setCompanyName(contactCompany1.getCompanyName());
            company.setIndustryType("");
            industryService.saveCompany(contactCompany1, shopCusCom);
            wfjserv.registerCompanyInfo("", shopCusCom, company, cdl);
            map.put("flag", "1");
        } else {
            map.put("flag", "2");
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    public String AjaxClaimClompanyList() {
        String sql = "select cc.ccompanyid,cc.companyname,cc.companyaddr,cc.logopath,cc.webstatus,GetDistance(cc.accuracy,cc.dimension, ? , ? ) meter from dtcontactcompany cc where cc.companyname like ? and cc.companyaddr like ? and cc.webstatus is not null order by meter";
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 15, sql, "select count(*) from (" + sql + ")", new Object[]{accuracy, dimension, "%" + search + "%", "%" + city + "%"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    public String getPerfectInformation() {
        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom shopCusCom = (TEshopCusCom) sessionWrap.getObject(
                httpSession, KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
        if (shopCusCom == null) {
            return "login";
        }
        return "perfectInformation";
    }

    public String submitIsOk() {
        return "submitIsOk";
    }

    public PageForm getSeleCompanyList() {
        HttpServletRequest req = ServletActionContext.getRequest();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        if (HttpRequestDeviceUtils.isMobileDevice(req) == false) {
            sql.append("with a as(select r.compnay_id,ccf.pic_path,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industrytype,cc.companyAddr");
            sql.append(" ,GetDistance(cc.accuracy,cc.dimension, ? , ? ) meter ");
            if ("activity".equals(flag)) {
                sql.append(",p.activity_type ");
                sql.append(" from dt_ccom_com r ,dtContactCompany cc,dt_ccom_conf ccf,dt_prize_activity p ");
                sql.append(" where ccf.ccompnay_id=cc.ccompanyid and  sysdate between p.starting_time+0 and p.end_time+0 and p.activity_range = 0 and p.status = 0 and p.company_id = r.compnay_id and trim(p.activity_type) = ?");
                params.add(accuracy != null ? accuracy : "");
                params.add(dimension != null ? dimension : "");
                params.add(activityType);
            } else {
                sql.append(" from dt_ccom_com r ,dtContactCompany cc,dt_ccom_conf ccf");
                sql.append(" where ccf.ccompnay_id=cc.ccompanyid");
                params.add(accuracy != null ? accuracy : "");
                params.add(dimension != null ? dimension : "");
            }
            sql.append(" and r.ccompany_id=cc.ccompanyid and cc.webstatus='01' and ccf.modal_type='0'");

            String[] ind = industryType.split(",");
            if (ind.length == 1) {
                sql.append(" and  (cc.industryType like ? ");
                params.add("%" + industryType + "%");
                if (ccompanyId != null && !"".equals(ccompanyId)) {
                    sql.append(" and cc.ccompanyid <> ?");
                    params.add(ccompanyId);
                }
            } else {
                sql.append(" and (cc.industryType in (");
                for (int i = 0; i < ind.length; i++) {
                    if (i == ind.length - 1) {
                        sql.append("?)");
                        params.add(ind[i]);
                    } else {
                        sql.append("?,");
                        params.add(ind[i]);
                    }
                }
            }

            sql.append(" or cc.companyName like ?) and cc.companyName like ?");
            params.add("%" + (industryType != null ? industryType : "") + "%");
            params.add("%" + (search != null ? search : "") + "%");
        } else {
            sql.delete(0, sql.length());
            sql.append(" with a as(select r.compnay_id,cc.jjPath,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industrytype,cc.companyAddr ");
            if ("activity".equals(flag)) {
                sql.append(" ,GetDistance(cc.accuracy,cc.dimension, ? , ? ) meter,p.activity_type ");
                sql.append(" from dt_ccom_com r ,dtContactCompany cc,dt_prize_activity p ");
                sql.append(" where r.ccompany_id=cc.ccompanyid and cc.webstatus='01' and  sysdate between p.starting_time+0 and p.end_time+0 and p.activity_range = 0 and p.status = 0 and p.company_id = r.compnay_id and trim(p.activity_type) = ?");
                params.add(accuracy != null ? accuracy : "");
                params.add(dimension != null ? dimension : "");
                params.add(activityType);
            } else {
                sql.append(" ,GetDistance(cc.accuracy,cc.dimension, ? , ? ) meter ");
                sql.append(" from dt_ccom_com r ,dtContactCompany cc ");
                sql.append(" where r.ccompany_id=cc.ccompanyid and cc.webstatus='01'");
                params.add(accuracy != null ? accuracy : "");
                params.add(dimension != null ? dimension : "");
            }
            String[] ind = industryType.split(",");
            if (ind.length == 1) {
                sql.append(" and  (cc.industryType like ? ");
                params.add("%" + industryType + "%");
                if (ccompanyId != null && !"".equals(ccompanyId)) {
                    sql.append(" and cc.ccompanyid <> ?");
                    params.add(ccompanyId);
                }
            } else {
                sql.append(" and (cc.industryType in (");
                for (int i = 0; i < ind.length; i++) {
                    if (i == ind.length - 1) {
                        sql.append("?)");
                        params.add(ind[i] != null ? ind[i] : "");
                    } else {
                        sql.append("?,");
                        params.add(ind[i] != null ? ind[i] : "");
                    }
                }
            }


            sql.append(" or cc.companyName like ?) and cc.companyName like ?");
            params.add("%" + (industryType != null ? industryType : "") + "%");
            params.add("%" + (search != null ? search : "") + "%");
        }
        if (companyAddr == null || companyAddr.equals("")) {
            sql.append(" order by meter)select a.*,cus.account from a left join t_eshop_cuscom cus on cus.companyid=a.compnay_id order by  a.meter nulls last,cus.account nulls last");
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 15, sql.toString(), "select count(*) from (" + sql + ")", params.toArray());
        } else {
            sql.append(" and cc.companyAddr like ?");
            sql.append(" )select a.*,cus.account from a left join t_eshop_cuscom cus on cus.companyid=a.compnay_id order by cus.account nulls last");
            params.add("%" + companyAddr + "%");
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 15, sql.toString(), "select count(*) from (" + sql + ")", params.toArray());
        }
        search = search.replace("%", "");
        industryType = industryType == null ? null : industryType.replace("%", "");
        companyAddr = companyAddr == null ? null : companyAddr.replace("%", "");
        return pageForm;
    }

    /*
     * 搜索框模糊查询公司列表(CompanyList页面搜索框)
     * 区域选择
     */
    public String SelCompanyList() {


        pageForm = getSeleCompanyList();
        return "companyList";
    }

    //上拉刷新
    public String getAjax() {
        pageForm = getSeleCompanyList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * @param
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司主页信息CcompanyId:往来单位id
     */
    public String homepage() {
        map = new HashMap<String, Object>();
        //公司信息
        map.put("companyMessage", industryService.companyMessage(this.getCcompanyId()));
        //联营商城
        map.put("jointOperation", getPList());

        map.put("setSubsidize", setSubsidizeService.setSubsidizeByType(this.getCcompanyId()));
        //新闻资讯
        map.put("press", industryService.NewsList(this.getCcompanyId(), "公司新闻"));
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * @param
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司主页信息往来单位id
     */
    public String homepage1() {
        map = new HashMap<String, Object>();
        //联营招商
        map.put("canvassBusinessOrders", industryService.getpk(this.getCcompanyId()));
        //团队展示
        map.put("exhibition", industryService.exhibition(ccompanyId));
        //公司招聘
        map.put("attractEngage", industryService.attractEngage(this.getCcompanyId()));
        //公司简介
        map.put("abstruct", industryService.NewsList(this.getCcompanyId(), "公司简介"));
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * @return 返回的数据
     * @Title: 跳转
     * @Description: 跳转公司主页
     */
    public String getCompanyHome() {
        HttpSession session = ServletActionContext.getRequest().getSession();

        HttpServletRequest request = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        if (sccid != null) {
            TEshopCusCom cus1 = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams(
                            "from TEshopCusCom where sccid=?",
                            new Object[]{sccid});
            sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus1);
            TEshopCustomer cus = (TEshopCustomer) baseBeanService
                    .getBeanByHqlAndParams(
                            "from TEshopCustomer where account=? AND logOff=0",
                            new Object[]{cus1.getAccount()});
            sw.setObject(session, SessionWrap.KEY_CUSTOMER, cus);
        }

        ContactCompany ccom = industryService.getCompany(ccompanyId);
        Map<String, Object> map = industryService.getTcc(ccompanyId);
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);

        //判断是否为平台
        String field = (String) industryService.getField(ccompanyId);
        if (tc != null) {
            if ("st".equals(field)) {


                earthIndexService.addBrowseHistory(tc.getSccId(), "e路快车", "");

            } else {


                earthIndexService.addBrowseHistory(tc.getSccId(), "商家", ccompanyId);

            }
        }

        //判断是否为汽车驾校公司
        Object stCompany = industryService.getStCompany(ccompanyId);
        Object[] stCompanyList = (Object[]) stCompany;
        String typelei = request.getParameter("typelei");
        String typeback = request.getParameter("typeback");
        session.setAttribute("playto", typelei);
        session.setAttribute("typeback", typeback);
        request.setAttribute("ccom", ccom);
        request.setAttribute("tcc", map.get("tcc"));
        request.setAttribute("sccid", map.get("obj"));
        request.setAttribute("ppid", map.get("obj2"));
        List<BaseBean> list = restaurantService.getNewsList();
        request.setAttribute("list", list);
        if (field != null) {
            request.setAttribute("type", "0");
            return field;
        }
        if (stCompany != null) {
            TEshopCustomer customer = (TEshopCustomer) sw.getObject(session, KEY_CUSTOMER);
            if (customer != null) {
                Object fkStatus = baseBeanService.getObjectBySqlAndParams("select c.fkstatus from dtenroll e ,dtCashierBills c where e.cashierbillsid = c.cashierbillsid and e.staffid = ? and e.companyid = ? and c.fkstatus = ? and rownum = 1", new Object[]{customer.getStaffid(), stCompanyList[1], "00"});
                if (!"".equals(fkStatus) && fkStatus != null) {
                    applied = "00";
                    staffid = customer.getStaffid();
                }
            }
            request.setAttribute("stCompany", stCompany);
            return "stCompany";
        }
        return "companyHome";
    }

    /**
     * @return 返回的数据
     * @Title: 评论
     * @Description: 产品
     */
    public String informationDetails() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                KEY_CUSTOMER);

        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (tc != null) {

            earthIndexService.addBrowseHistory(tc.getSccId(), "资讯", this.getPpId());
        }
        String mini = "";
        String fiveClear = "";
        String register = "";
        if (miniSystemJudge.equals("00")) {
            mini = "公司简介";
            fiveClear = "2";
        } else if (miniSystemJudge.equals("01")) {
            mini = "公司文化";
            fiveClear = "2";
        } else if (miniSystemJudge.equals("02")) {
            mini = "公司新闻";
            fiveClear = "2";
        } else if (miniSystemJudge.equals("03")) {
            mini = "会员分享";
            fiveClear = "5";
        } else if (miniSystemJudge.equals("04")) {
            mini = "公司论坛";
            fiveClear = "2";
        }
        maplist1 = industryService.informationDetails(this.getPpId());
        if (maplist1 == null || maplist1.size() == 0) {
            return "linkFailure";
        }
        object1 = industryService.content(this.getPpId());
        if (cus != null) {
            ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams("from ProductComment where ppid=? and staffid=? and type=?", new Object[]{ppId, cus.getStaffid(), "2"});
            if (pc != null) {
                flag = pc.getIscollect();
            } else {
                flag = "0";
            }
        } else {
            flag = "0";
            register = "0";
        }
        list1 = industryService.splendid(ccompanyId, this.getPpId(), mini, fiveClear);

        // 查询公司公众二维码
        ContactCompany concom = (ContactCompany) baseBeanService.getBeanByHqlAndParams(
                "from ContactCompany where ccompanyID = ?",
                new Object[]{"contactCompany20101230UB4U5884S30000000176"});
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("concom", concom);
        request.setAttribute("register", register);

        //获取咨询数量
        String zxcount = consultService.getCountBySource(this.getPpId());
        request.setAttribute("zxcount", zxcount);
        int readcount = 0;


        try {
            //增加阅读量
            ProductCommentMain pcm = (ProductCommentMain) baseBeanService.getBeanByHqlAndParams("from ProductCommentMain where ppid=?", new Object[]{ppId});
            if (pcm == null) {
                pcm = new ProductCommentMain();
                pcm.setPcmID(serverService.getServerID("pcmid"));
                pcm.setPpid(ppId);
                pcm.setPlcount(0);
                pcm.setPraise(0);
                pcm.setReadcount(1);

            } else {
                pcm.setReadcount(pcm.getReadcount() + 1);

            }
            baseBeanService.update(pcm);
            readcount = pcm.getReadcount();

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("readcount", readcount);
        return "informationDetails";
    }

    //公司首页上拉加载
    public String getAjaxCompanyHome() {
        String hql = "from CcomConf where ccompanyId= ? and modalType='1'";
        pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 4, hql, new Object[]{ccompanyId});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /***新版公司网站****/
    /**
     * 产品中心跳转
     */

    public String CompanyProducts() {
        if (ccompanyId != null && ccompanyId.length() > 0) {
            CcomCom ccc = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId.trim()});
            if (ccc != null) {
                companyId = ccc.getComanyId();
            }
        }
        if (companyId != null && companyId.length() > 0) {
            beans = industryService.getProductCategories(companyId.trim());
            CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where comanyId=?", new Object[]{companyId});
            String hql2 = " from ContactCompany c where c.ccompanyID=?";
            contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{cc != null ? cc.getCcompanyId() : ""});
            if (contactCompany != null) {
                String intey = contactCompany.getIndustryType();
                if (intey != null && intey.indexOf("餐饮") != -1) {
                    productlaunchService.addProductType(companyId);
                    HttpServletRequest request = ServletActionContext.getRequest();
                    String posNum = request.getParameter("posNum");
                    String categoryId = "";
                    PageForm pageForm = null;
                    //获取新闻
                    List<BaseBean> list = restaurantService.getNewsList();
                    request.setAttribute("list", list);
                    List<BaseBean> catelist = restaurantService.getALLCate(companyId);
                    request.setAttribute("catelist", catelist);
                    Map<String, Object> map = new HashMap<String, Object>();


                    if (catelist.size() > 0) {
                        SchProCategory sc = (SchProCategory) catelist.get(0);

                        if (sc != null) {
                            categoryId = sc.getCategoryId();
                            String staffid = "";
                            if (posNum == null || posNum.equals("")) {
                                HttpSession session = ServletActionContext.getRequest().getSession();
                                SessionWrap sw = SessionWrap.getInstance();
                                TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
                                if (cus != null) {
                                    staffid = cus.getStaffid();
                                }
                            }
                            pageForm = restaurantService.getProductByCate(companyId, staffid, posNum, categoryId, 1, 10);

                        }
                    }
                    request.setAttribute("pageForm", pageForm);
                    request.setAttribute("categoryId", categoryId);


                    return "companyHouse";
                }
                String id = contactCompany.getIndustryId();
                HttpServletRequest request = ServletActionContext.getRequest();
                //判断是否为超市行业
                if (id != null && "scode20190415raqvqk3uvs0000000762".equals(id)) {
                    request.setAttribute("ccompanyID", contactCompany.getCcompanyID());
                    request.setAttribute("industryType", contactCompany.getIndustryType());
                    request.setAttribute("companyName", contactCompany.getCompanyName());
                    return "supermarketGoods";//去超市商城主页
                }
            }
            HttpServletRequest request = ServletActionContext.getRequest();
            List<BaseBean> list = industryService.toADList("联营商城广告", companyId);
            request.setAttribute("adlist", list);
        }

        return "companyproducts";
    }

    /**
     * 产品分类搜索
     */
    public String searchCategories() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (ppId != null && ppId.length() > 0) {
            List<BaseBean> list = industryService.searchCategories(ppId.trim());
            map.put("list", list);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 公司网站的公司招聘
     */
    public String getRecruitIndex() {
        if (ccompanyId != null && ccompanyId.length() > 0) {
            pageForm = industryService.getRecruitIndex(ccompanyId.trim(), pageForm == null ? 1 : pageForm.getPageNumber());
        }
        return "torecruit";
    }

    /**
     * 查看更多简介，文化
     */
    public String summaryOrCulture() {
        if (type != null) {
            if (companyId != null) {
                maplist3 = industryService.summaryOrCulture(companyId.trim(), type.trim());
            }
        }
        return "PlatformSummary";
    }

    //产品pageForm,搜索公司产品

    public PageForm getPList() {
        CcomCom ccomcom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId = ?", new Object[]{ccompanyId});
        Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{ccomcom.getComanyId()});
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());//获取当前时间
        String sql = "select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type," +
                " p.monthSales ,pap.actPrice,pv.vip,pa.type activityType  " +
                " from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid  " +
                " left join dt_pro_activity_price pap on pap.ppid = p.ppid  and pap.state ='00' " +
                " left join dt_pro_vip pv on pv.ppid = p.ppid  and pv.state ='00' " +
                " left join dt_pro_activity pa on pa.activityId = pap.activityid  and  " +
                " pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') " +
                " and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' " +
                " where p.type!='公司会员' and p.type!='个人会员' and p.type!='扫码收款'  " +
                "and ps.com_id= ? and p.showweixin = '01' and ps.state ='00' ";
        List<Object> parms = new ArrayList<Object>();
        parms.add(currentTime);
        parms.add(currentTime);
        if (company != null) {
            parms.add(company.getCompanyID());
            if (ppname != null && !ppname.equals("")) {
                sql += " and ppname like ?";
                parms.add("%" + ppname.trim() + "%");
            }
            productsPageForm = baseBeanService.getPageFormBySQL(
                    (null != productsPageForm ? productsPageForm.getPageNumber() : 1), 8, sql,
                    "select count(*) from (" + sql + ")", parms.toArray());
        }
        return productsPageForm;
    }

    /**
     * ajax产品中心
     */
    public String getAjaxPorductsList() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (ccompanyId != null && ccompanyId.length() > 0) {
            pageForm = industryService.getAjaxPorductsList(ccompanyId.trim(),
                    pageForm == null ? 1 : pageForm.getPageNumber(),
                    search != null ? search.trim() : null,
                    ppId != null && !ppId.equals("categories") ? ppId.trim() : null);
            /*map.put("setsubsidize", setSubsidizeService.setSubsidizeByType(ccompanyId));*/
        }

        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 公司名片
     */
    @SuppressWarnings("unchecked")
    public String CompanyCard() {
        HttpServletRequest re = ServletActionContext.getRequest();
        String path = re.getContextPath();
        String basePath = re.getScheme() + "://"
                + re.getServerName() + ":" + re.getServerPort()
                + path + "/";
        CcomCom comc = null;
        if (ccompanyId == null) {
            TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom d where d.account=?", new Object[]{user});
            String hql = "from CcomCom c where c.comanyId = ?";
            comc = (CcomCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tcc.getCompanyId()});
            ccompanyId = comc == null ? "" : comc.getCcompanyId();
        }
        comc = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId});
        company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID= ?", new Object[]{comc.getComanyId()});
        String hql = "from ContactCompany where ccompanyID= ?";
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyId});
        //公司名片二维码
        if (contactCompany != null) {
            if (contactCompany.getLogoPath() != null && !contactCompany.getLogoPath().equals("")) {
                if (contactCompany.getQrCodePath() == null || contactCompany.getQrCodePath().equals("")) {
                    String upsql = "update dtContactCompany set qrCodePath=? where ccompanyID=? ";
                    String qrCodePath = qrCodeService.createQRCode(
                            basePath + "ea/industry/ea_CompanyCard.jspa?user=" + user, "png", "ContactCompany",
                            contactCompany.getLogoPath());
                    baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{upsql},
                            new Object[]{qrCodePath, ccompanyId});
                    contactCompany.setQrCodePath(qrCodePath);
                }
            }
        }
        ccomconf = (CcomConf) baseBeanService.getBeanByHqlAndParams("from CcomConf where ccompanyId= ? and modalType='0' ", new Object[]{ccompanyId});
        String sql = "select m.materialID,m.title,m.elaborate,m.route,ms.mienStyleID,ms.relateID from dtMaterial m, dtMienStyle ms where m.materialID=ms.materialID "
                + "and m.type='00' and ms.relateID=m.companyID and ms.relateID= ? ";
        companyMienList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{ccompanyId});
        return "companycard";
    }

    //编辑公司名片
    public String EditCompanyCard() {
        String hql = "from ContactCompany where ccompanyID= ?";
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyId});
        ccomconf = (CcomConf) baseBeanService.getBeanByHqlAndParams("from CcomConf where ccompanyId= ? and modalType='0' ", new Object[]{ccompanyId});
        return "editCompanyCard";
    }

    //保存公司名片
    public String saveCompanyCard() {
        beans = new ArrayList<BaseBean>();
        if (ccompanyId != null) {
            ContactCompany cc = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID= ?", new Object[]{ccompanyId});
            if (contactCompany.getLg() != null) {
                String path = ServletActionContext.getRequest().getSession().getServletContext()
                        .getRealPath("/");
                String photopath = fileService.savePhoto(path, contactCompany.getLgFileName(),
                        contactCompany.getLg(), contactCompany.getCcompanyID(),
                        "/contactcompany/" + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
                cc.setLogoPath(photopath);
            }
            cc.setCompanyName(contactCompany.getCompanyName());
            cc.setIndustryType(contactCompany.getIndustryType());
            cc.setCompanyAddr(contactCompany.getCompanyAddr());
            cc.setCresponsible(contactCompany.getCresponsible());
            cc.setSex(contactCompany.getSex());
            cc.setResponsibleTel(contactCompany.getResponsibleTel());
            cc.setCompanyWeb(contactCompany.getCompanyWeb());
            cc.setCompanyTel(contactCompany.getCompanyTel());
            beans.add(cc);
        }
        if (ccomConfId == null || ccomConfId.equals("")) {
            ccomconf.setCcomConfId(serverService.getServerID("CcomConf"));
            ccomconf.setModalName("公司简介");
            ccomconf.setModalType("0");
            ccomconf.setCcompanyId(ccompanyId);
            ccomconf.setSn("1");
            beans.add(ccomconf);
        } else {
            CcomConf conf = (CcomConf) baseBeanService.getBeanByHqlAndParams("from CcomConf where ccomConfId= ? ", new Object[]{ccomConfId});
            conf.setModalRemark(ccomconf.getModalRemark());
            beans.add(conf);
        }

        baseBeanService.executeHqlsByParamsList(beans, null, null);
        return CompanyCard();
    }

    //公司名片修改公司名称判断是否存在
    public String isCompanyName() {
        int cou = 0;
        String hql = "select count(*) from ContactCompany where companyName = ? ";
        if (onlyCompany != null && !onlyCompany.equals("")) {
            cou = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{onlyCompany});
            JSONObject obj = new JSONObject();
            obj.accumulate("c", cou);
            result = obj.toString();
        }
        return "success";
    }

    /*
     * 获取银行卡列表
     *
     */
    public String getBankCardsList() {

        TEshopCusCom scc = null;
        String companyID = null;
        if (sccid != null && !sccid.equals("")) {
            String scchql = "from TEshopCusCom where sccId = ?";
            scc = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams(scchql,
                            new Object[]{sccid});
            companyID = scc.getCompanyId();
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();

            CAccount account = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            companyID = account.getCompanyID();

            scc = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCusCom where companyId = ?",
                            new Object[]{companyID});
            sccid = scc.getSccId();
            khd = "00";
        }

        if (companyID != null) {
            String chql = "from CcomCom where comanyId=?";
            CcomCom ccomcom = (CcomCom) baseBeanService.getBeanByHqlAndParams(chql, new Object[]{companyID});
            String hql = "from StaffBankAccount where ccompanyId=? and type=?";
            if (ccomcom != null) {
                BankCardList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{ccomcom.getCcompanyId(), "00"});
                ccompanyId = ccomcom.getCcompanyId();
            }
        }
        return "banklist";
    }

    /*
     * 调转添加银行卡页面
     */
    public String getaddBankCardInformation() {
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID=?", new Object[]{ccompanyId});
        return "addbankcard";
    }

    /*
     * 添加银行卡信息
     */
    public String addBankCardInformation() {

        JSONObject obj = new JSONObject();
        String msg = "ok";
        staffbank.setBankAccountID(serverService.getServerID("StaffBankAccount"));
        staffbank.setCcompanyId(ccompanyId);
        staffbank.setAddTime(new Date());
        staffbank.setStaffID(staffid);
        staffbank.setProvince(province);
        staffbank.setBankAddress(bankAddress);
        staffbank.setType("00");
        staffbank.setCardType("00");
        staffbank.setBranchName(branchName);
        try {
            baseBeanService.save(staffbank);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "no";
        }
        obj.accumulate("msg", msg);
        result = obj.toString();
        return "success";
    }

    //登录银行和所对应的银行相对应

    /**
     * @param :请求接口urlAll
     * @param httpArg     :参数
     * @return 返回结果
     */
    public String checkNum(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();

        httpUrl = httpUrl + "?" + cardnum + "=" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "4fbd013b79911ccd775020d032ef2180");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbf.toString();
    }

    public String checkBankNum() {
        if (httpArg != null && httpArg.length() > 0) {
            String s = checkNum(httpUrl, httpArg);//调用checkNum方法
            result = s;
        }
        return "success";

    }

    /*
     *银行卡信息
     * */
    public String BankCardInfo() {
        if (bankAccountId != null && !bankAccountId.equals("")) {
            staffbank = (StaffBankAccount) baseBeanService.getBeanByHqlAndParams("from StaffBankAccount where bankAccountID=?", new Object[]{bankAccountId});
        }
        return "bankcardInfo";
    }

    /*
     * 设置默认
     * */
    public String setDefault() {
        if (bankAccountId != null && !bankAccountId.equals("") && ccompanyId != null) {
            String hql = "update StaffBankAccount u set u.isdefault=0 where u.ccompanyId=?";
            String hql2 = "update StaffBankAccount u set u.isdefault=1 where u.bankAccountID=?";
            List<Object[]> parm = new ArrayList<Object[]>();
            parm.add(new Object[]{ccompanyId});
            parm.add(new Object[]{bankAccountId});
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql2}, parm);
            JSONObject obj = new JSONObject();
            obj.accumulate("flag", "1");
            result = obj.toString();
        }
        return "success";

    }

    /*
     *解除绑定
     * */
    public String del() {
        if (bankAccountId != null && !bankAccountId.equals("")) {
            StaffBankAccount sba = (StaffBankAccount) baseBeanService.getBeanByHqlAndParams("from StaffBankAccount where bankAccountID=?", new Object[]{bankAccountId});
            baseBeanService.deleteBeanByKey(StaffBankAccount.class, sba.getBankAccountKey());
            JSONObject obj = new JSONObject();
            obj.accumulate("flag", "1");
            result = obj.toString();
        }

        return "success";
    }

    /*
     * 公司活动
     **/
    public String getCompanyActivitiesList() {
        if (ccompanyId != null && !ccompanyId.equals("")) {
            ActivitiesList = baseBeanService.getListBeanByHqlAndParams("from Activities where ccompanyId= ? and category='00' and type='00' ", new Object[]{ccompanyId});
        }
        return "ActivitiesList";
    }

    /*
     * 跳转编辑
     * */
    public String toEdit() {
        if (activitiesId != null && !activitiesId.equals("")) {
            activities = (Activities) baseBeanService.getBeanByHqlAndParams("from Activities where activitiesID =?", new Object[]{activitiesId});
        }
        return "toEdit";
    }

    /*
     * 保存
     * */
    public String toSaveActivities() {
        String photopath = "";
        beans = new ArrayList<BaseBean>();
        if (activities.getPic() != null) {
            String path = ServletActionContext.getRequest().getSession().getServletContext()
                    .getRealPath("/");
            photopath = fileService.savePhoto(path, activities.getPicFileName(),
                    activities.getPic(), ccompanyId,
                    "/activities/" + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
        }
        if (activitiesId != null && !activitiesId.equals("")) {
            Activities a = (Activities) baseBeanService.getBeanByHqlAndParams("from Activities where ActivitiesID=?", new Object[]{activitiesId});
            if (photopath != null && !photopath.equals("")) {
                a.setPicture(photopath);
            }
            a.setDescribe(activities.getDescribe());
            a.setReleaseTime(new Date());
            a.setTitle(activities.getTitle());
            beans.add(a);
        } else {
            String author = "";//作者
            if (user != null && !user.equals("")) {
                TEshopCusCom tec = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[]{user});
                if (tec != null && !tec.getStaffid().equals("")) {
                    Staff st = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{tec.getStaffid()});
                    if (st != null) {
                        author = st.getStaffName();
                    }
                }
            }
            Activities act = new Activities();
            act.setActivitiesID(serverService.getServerID("Activities"));
            act.setType("00");
            act.setCcompanyId(ccompanyId);
            act.setAuthor(author);
            act.setReleaseTime(new Date());
            act.setCategory("00");
            act.setPicture(photopath);
            act.setDescribe(activities.getDescribe());
            act.setTitle(activities.getTitle());
            beans.add(act);
        }
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        JSONObject obj = new JSONObject();
        obj.accumulate("flag", "1");
        result = obj.toString();
        return "success";
    }

    /*
     * 删除
     * */
    public String delActivities() {
        if (activitiesId != null && !activitiesId.equals("")) {
            activities = (Activities) baseBeanService.getBeanByHqlAndParams("from Activities where activitiesID= ?", new Object[]{activitiesId});
            baseBeanService.deleteBeanByKey(Activities.class, activities.getActivitiesKey());
            JSONObject obj = new JSONObject();
            obj.accumulate("flag", "1");
            result = obj.toString();
        }
        return "success";
    }

    /*
     * 公司风采
     * */
    @SuppressWarnings("unchecked")
    public String getCompanyMien() {
        if (ccompanyId != null && !ccompanyId.equals("")) {
            String sql = "select m.materialID,m.title,m.elaborate,m.route,ms.mienStyleID,ms.relateID from dtMaterial m, dtMienStyle ms where m.materialID=ms.materialID "
                    + "and m.type='00' and ms.relateID=m.companyID and ms.relateID= ? ";
            companyMienList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{ccompanyId});
            if (companyMienList.size() > 0) {
                Object[] obj = (Object[]) companyMienList.get(0);
                if (obj.length > 0) {
                    material = (CardMaterial) baseBeanService.getBeanByHqlAndParams("from CardMaterial where materialID= ? ", new Object[]{obj[0]});
                    ms = (MienStyle) baseBeanService.getBeanByHqlAndParams("from MienStyle where mienStyleID= ?", new Object[]{obj[4]});
                }
            }
        }
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID=?", new Object[]{ccompanyId});
        CcomCom comc1 = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId});
        if (comc1 != null) {
            company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{comc1.getComanyId()});
        }
        return "companymien";
    }

    /*
     * 跳转编辑
     * */
    public String toEditCompanyMien() {
        if (materialId != null && !materialId.equals("")) {
            material = (CardMaterial) baseBeanService.getBeanByHqlAndParams("from CardMaterial where materialID= ? ", new Object[]{materialId});
        }
        if (mienstyleId != null && !mienstyleId.equals("")) {
            ms = (MienStyle) baseBeanService.getBeanByHqlAndParams("from MienStyle where mienStyleID= ?", new Object[]{mienstyleId});
        }
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID=?", new Object[]{ccompanyId});
        CcomCom comc1 = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId});
        if (comc1 != null) {
            company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{comc1.getComanyId()});
        }
        return "toEditCompanyMien";
    }

    /*
     * 保存风采
     * */
    public String saveCompanyMien() {
        beans = new ArrayList<BaseBean>();
        CardMaterial cmater = null;
        MienStyle mstyle = null;
        if (materialId == null || materialId.equals("")) {
            if (mienstyleId == null || mienstyleId.equals("")) {
                if (pics.length > 0) {
                    for (int i = 0; i < pics.length; i++) {
                        String path = ServletActionContext.getRequest().getSession().getServletContext()
                                .getRealPath("/");
                        String photopath = fileService.savePhoto(path, picsFileName[i],
                                pics[i], ccompanyId,
                                "/material/" + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
                        cmater = new CardMaterial();
                        mstyle = new MienStyle();
                        cmater.setRoute(photopath);
                        mstyle.setMienStyleID(serverService.getServerID("MienStyle"));
                        cmater.setMaterialID(serverService.getServerID("Material"));
                        cmater.setType("00");
                        cmater.setStatus("00");
                        cmater.setTitle("公司风采");
                        cmater.setElaborate(material.getElaborate());
                        cmater.setCompanyID(ccompanyId);
                        mstyle.setRelateID(ccompanyId);
                        mstyle.setMaterialID(cmater.getMaterialID());
                        beans.add(cmater);
                        beans.add(mstyle);
                    }
                }
            }
        } else {
            material = (CardMaterial) baseBeanService.getBeanByHqlAndParams("from CardMaterial where materialID= ? ", new Object[]{materialId});
            ms = (MienStyle) baseBeanService.getBeanByHqlAndParams("from MienStyle where mienStyleID= ?", new Object[]{mienstyleId});
            beans.add(ms);
            beans.add(material);
        }
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        JSONObject obj = new JSONObject();
        obj.accumulate("flag", "1");
        result = obj.toString();
        return "success";
    }

    /*
     * 删除风采
     * */
    public String delCompanyMien() {
        if (materialId != null && !materialId.equals("")) {
            material = (CardMaterial) baseBeanService.getBeanByHqlAndParams("from CardMaterial where materialID= ? ", new Object[]{materialId});
            if (mienstyleId != null && !mienstyleId.equals("")) {
                ms = (MienStyle) baseBeanService.getBeanByHqlAndParams("from MienStyle where mienStyleID= ?", new Object[]{mienstyleId});
            }
            baseBeanService.deleteBeanByKey(CardMaterial.class, material.getMaterialKey());
            baseBeanService.deleteBeanByKey(MienStyle.class, ms.getMienStyleKey());
            JSONObject obj = new JSONObject();
            obj.accumulate("flag", "1");
            result = obj.toString();
        }

        return "success";
    }

    /*
     * 证件信息
     * */
    public String CompanyCertificates() {
        String hql = "from Certificate where ccompanyID=?";
        companycertificates = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{ccompanyId});
        return "companycertificates";
    }

    /*
     * 跳转添加证件信息
     * */
    public String toAddCompanyCertificates() {
        if (certificateID != null && !certificateID.equals(""))
            certificate = (Certificate) baseBeanService.getBeanByHqlAndParams("from Certificate where certificateID= ?", new Object[]{certificateID});
        String[] templist = new String[2];
        if (certificate != null) {
            String temp = certificate.getCertificateAccessory();
            if (temp != null && !temp.equals("")) {
                templist = temp.split("\\$\\$\\$\\$");
                certificatephoto = templist[0];
                certificatephoto1 = templist[1];
            }
        }
        contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID=?", new Object[]{ccompanyId});
        CcomCom comc1 = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId});
        if (comc1 != null) {
            company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{comc1.getComanyId()});
        }
        return "toAddCertificates";
    }

    /*
     * 判定字符串包含指定字符串个数
     *
     * */
    public int countStr(String s1, String s2) {
        if (s1.indexOf(s2) == -1) {
            return 0;
        } else if (s1.indexOf(s2) != -1) {
            count++;
            countStr(s1.substring(s1.indexOf(s2) +
                    s2.length()), s2);
            return count;
        }
        return 0;
    }


    /*
     * 保存证件信息
     * */
    public String saveCompanyCertificates() {
        beans = new ArrayList<BaseBean>();
        String temp = "";
        if (pics.length > 0) {
            for (int i = 0; i < pics.length; i++) {
                String path = ServletActionContext.getRequest().getSession().getServletContext()
                        .getRealPath("/");
                String photopath = fileService.savePhoto(path, picsFileName[i], pics[i], ccompanyId,
                        "/certificate/" + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
                temp += photopath + "$$$$";
            }
        }
        //修改
        if (certificateID != null && !certificateID.equals("")) {
            String s = certificate.getCertificateAccessory();
            String[] slist = new String[2];
            slist = s.split("\\$\\$\\$\\$");
            certificatephoto = slist[0];
            certificatephoto1 = slist[1];
            if (temp.length() > 0) {
                if (countStr(temp, "$$$$") == 1) {
                    if (flag != null && flag.equals("0")) {
                        certificate.setCertificateAccessory(temp + certificatephoto1 + "$$$$");
                    } else if (flag.equals("1")) {
                        certificate.setCertificateAccessory(certificatephoto + "$$$$" + temp);
                    }
                } else {
                    certificate.setCertificateAccessory(temp);
                }
            }
            beans.add(certificate);
        } else {
            CcomCom com = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=? ", new Object[]{ccompanyId});
            Certificate c = new Certificate();
            c.setCertificateID(serverService.getServerID("certificate"));
            c.setCcompanyID(ccompanyId);
            c.setCompanyID(com.getComanyId());
            c.setCertificateType(certificatetype);
            c.setCertificateName(certificatetype);
            c.setCertificateLocation("公司");
            c.setCertificateAccessory(temp);
            beans.add(c);
        }
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        return CompanyCertificates();
    }

    /*
     * 公司论坛
     * */
    public String CompanyForumsList() {
        String hql = "from Activities where ccompanyId=? and category='01' and type='00' ";
        companyForumsList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{ccompanyId});
        return "companyforumslist";
    }

    public String toEditForum() {
        if (activitiesId != null && !activitiesId.equals("")) {
            String hql = "from Activities where activitiesID=?";
            activities = (Activities) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{activitiesId});
        }
        return "toEditForum";
    }

    public String saveCompanyForums() {
        String photopath = "";
        beans = new ArrayList<BaseBean>();
        if (activities.getPic() != null) {
            String path = ServletActionContext.getRequest().getSession().getServletContext()
                    .getRealPath("/");
            photopath = fileService.savePhoto(path, activities.getPicFileName(),
                    activities.getPic(), ccompanyId,
                    "/activities/" + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
        }
        if (activitiesId != null && !activitiesId.equals("")) {
            String hql = "from Activities where activitiesID=?";
            Activities a = (Activities) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{activitiesId});
            if (photopath != null && !photopath.equals("")) {
                a.setPicture(photopath);
            }
            a.setDescribe(activities.getDescribe());
            a.setReleaseTime(new Date());
            a.setTitle(activities.getTitle());
            beans.add(a);
        } else {
            String author = "";//作者
            if (user != null && !user.equals("")) {
                TEshopCusCom tec = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[]{user});
                if (tec != null && !tec.getStaffid().equals("")) {
                    Staff st = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{tec.getStaffid()});
                    if (st != null) {
                        author = st.getStaffName();
                    }
                }
            }
            Activities act = new Activities();
            act.setActivitiesID(serverService.getServerID("activities"));
            act.setCcompanyId(ccompanyId);
            act.setType("00");
            act.setCategory("01");
            act.setDescribe(activities.getDescribe());
            act.setPicture(photopath);
            act.setTitle(activities.getTitle());
            act.setReleaseTime(new Date());
            act.setAuthor(author);
            beans.add(act);
        }
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        JSONObject obj = new JSONObject();
        obj.accumulate("flag", "1");
        result = obj.toString();
        return "success";
    }

    public String delCompanyForum() {
        if (activitiesId != null && !activitiesId.equals("")) {
            activities = (Activities) baseBeanService.getBeanByHqlAndParams("from Activities where activitiesID= ?", new Object[]{activitiesId});
            baseBeanService.deleteBeanByKey(Activities.class, activities.getActivitiesKey());
            JSONObject obj = new JSONObject();
            obj.accumulate("flag", "1");
            result = obj.toString();
        }
        return "success";
    }

    /*
     * 公司风格
     * */
    public String CompanyStyle() {
        String hql = "from Activities where ccompanyId=? and category='04' and type='00' ";
        activities = (Activities) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyId});
        JSONObject obj = new JSONObject();
        obj.accumulate("activities", activities);
        result = obj.toString();
        return "success";
    }

    //跳转编辑页面
    public String EditCompanyStyle() {
        String hql = "from Activities where ccompanyId=? and category='04' and type='00' ";
        activities = (Activities) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyId});
        return "editcompanystyle";
    }

    //保存
    public String SaveCompanyStyle() {
        beans = new ArrayList<BaseBean>();
        if (activitiesId != null && !activitiesId.equals("")) {
            activities = (Activities) baseBeanService.getBeanByHqlAndParams("from Activities where activitiesID= ?", new Object[]{activitiesId});
            if (style != null && !style.equals("")) {
                activities.setDescribe(style);
            }
            beans.add(activities);
        } else {
            Activities act = new Activities();
            act.setActivitiesID(serverService.getServerID("activities"));
            act.setCcompanyId(ccompanyId);
            act.setCategory("04");
            act.setDescribe(style);
            act.setReleaseTime(new Date());
            act.setType("00");
            beans.add(act);
        }
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        JSONObject obj = new JSONObject();
        obj.accumulate("flag", "1");
        result = obj.toString();
        return "success";
    }

    //删除公司风格
    public String delCompanyStyle() {
        if (activitiesId != null && !activitiesId.equals("")) {
            activities = (Activities) baseBeanService.getBeanByHqlAndParams("from Activities where activitiesID= ?", new Object[]{activitiesId});
            baseBeanService.deleteBeanByKey(Activities.class, activities.getActivitiesKey());
            JSONObject obj = new JSONObject();
            obj.accumulate("flag", "1");
            result = obj.toString();
        }
        return "success";
    }


    //帮助文档
    @SuppressWarnings("unchecked")
    public String getHelpDocList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sql = "select p.goodsName,p.goodsID from dt_ProductPackaging p where p.companyId=? and  p.type = ? and p.fiveclear=? order by p.packagingDate desc";
        List<BaseBean> doclist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"company201009046vxdyzy4wg0000000025", "帮助文档", "2"});
        request.setAttribute("doclist", doclist);
        return "doclist";
    }

    //查看文档详情
    @SuppressWarnings("unchecked")
    public String viewHelpDocDetail() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String goodsID = request.getParameter("goodsID");
        String sql = "select g.goodsName,f.url,f.gfid,f.name from dtGoodFunction f,dtGoodsManage g where g.goodsid=? and g.goodsID = f.goodsID order by orders";
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{goodsID});
        Map<String, String> maplist = new HashMap<String, String>();
        // 获取每个功能信息的具体内容
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Object[] oo = (Object[]) obj;
            maplist.put(oo[2].toString(), getContentFromFile(oo[1].toString()));
        }
        request.setAttribute("list", list);
        request.setAttribute("maplist", maplist);
        return "docdetail";
    }

    /**
     * 根据txt URL 获取内容
     *
     * @param filepath
     * @return
     */
    private String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\") + filepath;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            return contentToFileService.getContent(path);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //添加资询人
    public String ajaxAddConsultant() {

        String stuts = industryService.addConsultant(consultingRegistration);

        JSONObject obj = new JSONObject();
        obj.accumulate("stuts", stuts);
        result = obj.toString();
        return "success";
    }

    /**
     * 跳转打赏页面
     *
     * @return
     */
    public String jumpReward() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        if (cus == null) {
            if (user != null) {
                cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{user});
            } else {
                String url = request.getRequestURL() + "?" + request.getQueryString();
                session.setAttribute("url", url);
                //session.setAttribute("vipback", request.getParameter("backu")+(request.getParameter("ccompanyId")==null?"":"&ccompanyId="+request.getParameter("ccompanyId")));
                return "login";
            }
        }
        request.setAttribute("ppid", request.getParameter("ppid"));
        request.setAttribute("sccid", request.getParameter("sccid"));
        request.setAttribute("rnum", request.getParameter("rnum"));
        return "reward";
    }

    /**
     * 添加打赏信息
     *
     * @return
     */
    public String addReward() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        JSONObject obj = new JSONObject();
        try {
            String ppid = request.getParameter("ppid");
            String money = request.getParameter("money");
            String fbsccid = request.getParameter("sccid");
            TEshopCusCom tecc = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            String ordernum = industryService.saveReward(ppid, money, fbsccid, tecc.getSccId());

            obj.accumulate("stuts", true);
            obj.accumulate("ordernum", ordernum);
        } catch (Exception e) {
            e.printStackTrace();
            obj.accumulate("stuts", false);
        }
        result = obj.toString();
        return "success";
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSearch() {
        try {
            URLDecoder.decode(search, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return search;
    }

    public String getMerchant() {
        String sql = " with a as(select r.compnay_id,cc.jjPath,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industrytype,cc.companyAddr" +
                "      from dt_ccom_com r ,dtContactCompany cc  where r.ccompany_id=cc.ccompanyid and cc.webstatus=?)" +
                "      select a.*,cus.account from a left join t_eshop_cuscom cus on cus.companyid=a.compnay_id order by cus.account nulls last";
        List<BaseBean> merchantList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("merchantList", merchantList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    /**
     * 查询所有顶层行业
     */
    public String getBusinessTyperRootList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<BusinessType> list = businessTypeDao.getBusinessTypeRootList();
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }

    /**
     * 查询所有事业部数据
     */
    public String getBusinessTypeDivisionList() {
        List<BusinessType> list = businessTypeDao.getBusinessTypeDivisionList();
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }


    /**
     * 根据父编号id查询子数据
     */
    public String getBusinessTypeByParentNum() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String typePID = request.getParameter("typePID");
        List<BusinessType> industryList = businessTypeDao.getBusinessTypeByTypePID(typePID);
        JSONArray arr = JSONArray.fromObject(industryList);
        result = arr.toString();
        return "success";
    }

    /**
     * 生成行业类别数据
     */
    public String createBusinessType() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String typePID = request.getParameter("typePID");
        String typeName = request.getParameter("typeName");
        String typeNum = request.getParameter("typeNum");
        String typeDesc = request.getParameter("typeDesc");

        //typeShowNum：页面展示的编号(typeNum+typeName)，展示时拼接上名称
        BusinessType pInfo = businessTypeDao.getBusinessTypeByTypeId(typePID);
        int sortNum = businessTypeDao.getMaxSortNumBy(typePID);


        //根据编号校验数据是否已经存在
        if (!businessTypeDao.isExistTypeNum(typeNum)) {
            result = "exist";
            return "success";
        }

        String typeShowNum = typeNum + typeName;

        BusinessType info = new BusinessType();
        info.setTypeId(serverService.getServerID("bType"));
        info.setTypePID(typePID);
        info.setTypeShowNum(typeShowNum);
        info.setParentNum(pInfo.getTypeNum());
        info.setTypeDesc(typeDesc);
        info.setTypeName(typeName);
        info.setTypeNum(typeNum);
        info.setStatus("1");
        info.setSortNum(sortNum + 1);
        info.setTypeLevel(pInfo.getTypeLevel() + 1);
        businessTypeDao.createBusinessType(info);
        result = "success";
        return "success";
    }

    /**
     * 修改行业类别数据
     *
     * @return
     */
    public String updateBusinessType() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
//        String typePID = request.getParameter("typePID");
        String typeKey = request.getParameter("typeKey");
        String typeName = request.getParameter("typeName");
        String typeNum = request.getParameter("typeNum");
        String oldTypeNum = request.getParameter("oldTypeNum");
        String typeDesc = request.getParameter("typeDesc");

        if (!oldTypeNum.equals(typeNum)) {
            //根据编号校验数据是否已经存在
            if (!businessTypeDao.isExistTypeNum(typeNum)) {
                result = "exist";
                return "success";
            }
        }


        String typeShowNum = typeNum + typeName;

        BusinessType info = businessTypeDao.getBusinessTypeById(typeKey);
        info.setTypeDesc(typeDesc);
        info.setTypeName(typeName);
        info.setTypeNum(typeNum);
        info.setTypeShowNum(typeShowNum);
        businessTypeDao.updateBusinessType(info);
        result = "success";
        return "success";
    }
    /**
     * 生成行項目类别数据
     */
    public String createManageType() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String typePID = request.getParameter("typePID");
        String typeName = request.getParameter("typeName");
        String typeNum = request.getParameter("typeNum");
        String typeDesc = request.getParameter("typeDesc");

        //typeShowNum：页面展示的编号(typeNum+typeName)，展示时拼接上名称
        BusinessType pInfo = businessTypeDao.getBusinessTypeByTypeId(typePID);
        int typeLevel = pInfo.getTypeLevel() + 1;
        int sortNum = businessTypeDao.getMaxSortNumByTypeLevel(typeLevel);


        //根据编号校验数据是否已经存在
        if (!businessTypeDao.isExistTypeNum(typeNum)) {
            result = "exist";
            return "success";
        }

        String typeShowNum = typeNum + typeName;

        BusinessType info = new BusinessType();
        info.setTypeId(serverService.getServerID("bType"));
        info.setTypePID(typePID);
        info.setTypeShowNum(typeShowNum);
        //info.setParentNum(parentNum);
        info.setParentNum(pInfo.getTypeNum());
        info.setTypeDesc(typeDesc);
        info.setTypeName(typeName);
        info.setTypeNum(typeNum);
        info.setStatus("1");
        info.setSortNum(sortNum + 1);
        info.setTypeLevel(typeLevel);
        businessTypeDao.createBusinessType(info);
        result = "success";
        return "success";
    }

    /**
     * 根据主键查询项目设计数据
     *
     * @return
     */
    public String getBusinessTypeByKey() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String typeKey = request.getParameter("typeKey");

        businessType = businessTypeDao.getBusinessTypeById(typeKey);
        BusinessType pInfo = businessTypeDao.getBusinessTypeByTypeId(businessType.getTypePID());
        businessType.setTypePName(pInfo.getTypeName());
        return "bTypeEdit";
    }


    /**
     * 删除行业类别
     *
     * @return
     */
    public String delBusinessType() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String typeKey = request.getParameter("typeKey");

        if (StringUtil.isEmpty(typeKey)) {
            result = "noId";
            return "success";
        }

        BusinessType info = businessTypeDao.getBusinessTypeById(typeKey);

        List<BusinessType> childList = businessTypeDao.getBusinessTypeByTypePID(info.getTypeId());
        if (!CollectionUtils.isEmpty(childList)) {
            result = "no";
            return "success";
        }

        info.setStatus("0");
        businessTypeDao.updateBusinessType(info);
        result = "success";
        return "success";
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BaseBean> getBeans() {
        return beans;
    }

    public void setBeans(List<BaseBean> beans) {
        this.beans = beans;
    }

    public String getCodePID() {
        return codePID;
    }

    public void setCodePID(String codePID) {
        this.codePID = codePID;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public List<BaseBean> getCompanyJList() {
        return companyJList;
    }

    public void setCompanyJList(List<BaseBean> companyJList) {
        this.companyJList = companyJList;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public CcomConf getCcomconf() {
        return ccomconf;
    }

    public void setCcomconf(CcomConf ccomconf) {
        this.ccomconf = ccomconf;
    }

    public String getCcomConfId() {
        return ccomConfId;
    }

    public void setCcomConfId(String ccomConfId) {
        this.ccomConfId = ccomConfId;
    }

    public List<BaseBean> getBankCardList() {
        return BankCardList;
    }

    public void setBankCardList(List<BaseBean> bankCardList) {
        BankCardList = bankCardList;
    }

    public StaffBankAccount getStaffbank() {
        return staffbank;
    }

    public void setStaffbank(StaffBankAccount staffbank) {
        this.staffbank = staffbank;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Activities getActivities() {
        return activities;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
    }

    public String getActivitiesId() {
        return activitiesId;
    }

    public void setActivitiesId(String activitiesId) {
        this.activitiesId = activitiesId;
    }

    public List<BaseBean> getActivitiesList() {
        return ActivitiesList;
    }

    public void setActivitiesList(List<BaseBean> activitiesList) {
        ActivitiesList = activitiesList;
    }

    public List<Object> getCompanyMienList() {
        return companyMienList;
    }

    public void setCompanyMienList(List<Object> companyMienList) {
        this.companyMienList = companyMienList;
    }

    public CardMaterial getMaterial() {
        return material;
    }

    public void setMaterial(CardMaterial material) {
        this.material = material;
    }

    public MienStyle getMs() {
        return ms;
    }

    public void setMs(MienStyle ms) {
        this.ms = ms;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMienstyleId() {
        return mienstyleId;
    }

    public void setMienstyleId(String mienstyleId) {
        this.mienstyleId = mienstyleId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public File[] getPics() {
        return pics;
    }

    public void setPics(File[] pics) {
        this.pics = pics;
    }

    public String[] getPicsFileName() {
        return picsFileName;
    }

    public void setPicsFileName(String[] picsFileName) {
        this.picsFileName = picsFileName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public List<BaseBean> getCompanycertificates() {
        return companycertificates;
    }

    public void setCompanycertificates(List<BaseBean> companycertificates) {
        this.companycertificates = companycertificates;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public String getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(String certificateID) {
        this.certificateID = certificateID;
    }

    public String getCertificatetype() {
        return certificatetype;
    }

    public void setCertificatetype(String certificatetype) {
        this.certificatetype = certificatetype;
    }

    public String getCertificatephoto() {
        return certificatephoto;
    }

    public void setCertificatephoto(String certificatephoto) {
        this.certificatephoto = certificatephoto;
    }

    public String getCertificatephoto1() {
        return certificatephoto1;
    }

    public void setCertificatephoto1(String certificatephoto1) {
        this.certificatephoto1 = certificatephoto1;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<BaseBean> getCompanyForumsList() {
        return companyForumsList;
    }

    public void setCompanyForumsList(List<BaseBean> companyForumsList) {
        this.companyForumsList = companyForumsList;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public List<BaseBean> getCompanynewsList() {
        return companynewsList;
    }

    public void setCompanynewsList(List<BaseBean> companynewsList) {
        this.companynewsList = companynewsList;
    }

    public String getPpname() {
        return ppname;
    }

    public void setPpname(String ppname) {
        this.ppname = ppname;
    }

    public int getCompanycount() {
        return companycount;
    }

    public void setCompanycount(int companycount) {
        this.companycount = companycount;
    }

    public PageForm getProductsPageForm() {
        return productsPageForm;
    }

    public void setProductsPageForm(PageForm productsPageForm) {
        this.productsPageForm = productsPageForm;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public String getOnlyCompany() {
        return onlyCompany;
    }

    public void setOnlyCompany(String onlyCompany) {
        this.onlyCompany = onlyCompany;
    }

    public CCode getCode() {
        return code;
    }

    public void setCode(CCode code) {
        this.code = code;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }

    public String getCodesId() {
        return codesId;
    }

    public void setCodesId(String codesId) {
        this.codesId = codesId;
    }

    public void setAjax(String ajax) {
        this.ajax = ajax;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public List<BaseBean> getProductList() {
        return productList;
    }

    public void setProductList(List<BaseBean> productList) {
        this.productList = productList;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAddressDetailed() {
        return addressDetailed;
    }

    public void setAddressDetailed(String addressDetailed) {
        this.addressDetailed = addressDetailed;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public StaffAddress getStaffAddres() {
        return staffAddres;
    }

    public void setStaffAddres(StaffAddress staffAddres) {
        this.staffAddres = staffAddres;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getHttpArg() {
        return httpArg;
    }

    public void setHttpArg(String httpArg) {
        this.httpArg = httpArg;
    }

    public String getTypeNews() {
        return typeNews;
    }

    public void setTypeNews(String typeNews) {
        this.typeNews = typeNews;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public RecruitInfo getRecruitInfo() {
        return recruitInfo;
    }

    public void setRecruitInfo(RecruitInfo recruitInfo) {
        this.recruitInfo = recruitInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<Integer, String[]> getMaplist3() {
        return maplist3;
    }

    public void setMaplist3(Map<Integer, String[]> maplist3) {
        this.maplist3 = maplist3;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public List<BaseBean> getList1() {
        return list1;
    }

    public void setList1(List<BaseBean> list1) {
        this.list1 = list1;
    }

    public Object getObject1() {
        return object1;
    }

    public void setObject1(Object object1) {
        this.object1 = object1;
    }

    public Map<Integer, String[]> getMaplist1() {
        return maplist1;
    }

    public void setMaplist1(Map<Integer, String[]> maplist1) {
        this.maplist1 = maplist1;
    }

    public String getMiniSystemJudge() {
        return miniSystemJudge;
    }

    public void setMiniSystemJudge(String miniSystemJudge) {
        this.miniSystemJudge = miniSystemJudge;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }

    public String getKhd() {
        return khd;
    }

    public void setKhd(String khd) {
        this.khd = khd;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getApplied() {
        return applied;
    }

    public void setApplied(String applied) {
        this.applied = applied;
    }

    public ConsultingRegistration getConsultingRegistration() {
        return consultingRegistration;
    }

    public void setConsultingRegistration(ConsultingRegistration consultingRegistration) {
        this.consultingRegistration = consultingRegistration;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setBusinessTypeDao(BusinessTypeDao businessTypeDao) {
        this.businessTypeDao = businessTypeDao;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }
}
