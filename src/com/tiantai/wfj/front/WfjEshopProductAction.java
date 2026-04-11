package com.tiantai.wfj.front;


import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.service.IndustryClassificationService;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.sft.CombinePayerInfo;
import com.wechat.bo.sft.SubOrders;
import com.wechat.utils.ConstantURL;
import com.wechat.utils.HTTPV3;
import com.wechatpay.bo.AppPackage;
import com.wechatpay.bo.FinalPackage;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.bo.WxPayResult;
import com.wechatpay.utils.GetWxOrderno;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.*;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BenDis.*;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.Agencies;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.lottery.WinningRecordBean;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.DocInviteRecord;
import hy.ea.bo.production.AttriProduction;
import hy.ea.bo.production.GoodFunction;
import hy.ea.finance.service.AssiCartService;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.finance.service.SetSubsidizeService;
import hy.ea.finance.service.transferService;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.ea.marketing.service.SupermarketSerivce;
import hy.ea.office.service.CarManageService;
import hy.ea.office.service.MakeAppointmentService;
import hy.ea.service.CCodeService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SDistrictService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Controller
@Scope("prototype")
public class WfjEshopProductAction {
	private static final Logger logger = LoggerFactory.getLogger(WfjEshopProductAction.class);

    private final Logger logger = LoggerFactory.getLogger(WfjEshopProductAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CCodeService codeService;
    @Resource
    private ServerService serverService;
    @Resource
    private transferService transferService;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private WfjJifenService wfjserv;
    @Resource
    private BonusPointsService bonusPointsService;
    @Resource
    private SDistrictService regionService;

    @Resource
    private GoldOrderService goldOrderService;
    @Resource
    private AssiCartService assiCartService;
    @Resource
    private CarManageService carManageService;
    @Resource
    private SetSubsidizeService setSubsidizeService;
    @Resource
    private SuperSelfSerivce smSerivce;
    @Resource
    private SupermarketSerivce sumkSerivce;//mz

    @Resource
    private IndustryClassificationService icService;
    @Resource
    private MakeAppointmentService mappointmentService;

    private String phones;//手机号码
    private Agencies agencies;// 负责人
    private TEshopExtinfo shopExtinfo;
    private String getip;
    private COrganization corganization;
    private ProductPackaging productDesign;
    private Inventory inventory;
    private String companyId; // 微信传过来的companyId
    private String weidiantype;// 产品类型
    private String organizationID;// 微店id
    private String ppid; // 产品id
    private String goodsid;// 物品id
    private String search;// 判断搜索
    private String comId;
    private String sort;// 排序
    private String activity;// 从微信活动查询店铺
    private String shopCategory;// 店铺类别
    private List<BaseBean> shopList;// 微店列表
    private List<BaseBean> productList;// 产品列表

    private List<BaseBean> functionlist;// 产品功能信息ljc
    private Map<String, String> maplist;
    private Map<Integer, String[]> maplist1;

    private List<BaseBean> beans;
    private List<BaseBean> num;
    private List<Object> objbeanns; // 新物品的东西
    private String code;// 微信支付用的 //或者支付方式
    private String total;
    private Object result;// AJAXshiyong
    private String user;// 用于微分金能力 //产品购买数量


    private Company comp;// 购买产品所查出的公司
    private String indus;
    private List<CCode> proType;
    private Company company;// 购买使用对象
    private CDetail cdl;// 公司详细信息
    private Staff staff;// 个人注册填写的信息

    private StaffAddress staffaddress;// 购买地址
    private CarInformation cf;//车辆信息
    // 系统会员产品信息
    private String goodname;// 物品名称
    private String morre;// 物品钱数
    private String lei;// 所选会员类别
    private String page;// 图片地址
    private String fenlei;// 列别//还提供给APP注册参数使用
    private String ddid;// 订单号
    private String journalNum;// 编号
    private String baseUrl;// 路径前缀
    private String intf;// 消费者组织机构名字

    // 收货地址管理ljc
    private String area; // 收货人所在地区
    private String consignee; // 收货人
    private String phone; // 收货人电话
    private String postCode; // 收货人邮政编码
    private StaffAddress staffAddress;// 地址管理 //购买的地址
    private String addressDetailed;// 详细地址
    private List<BaseBean> staffAddressList;
    private String count;
    // 库房
    private String weiid;// /库房DI

    private ProSetup prosetup;// 查看零售价
    private String ccompanyId;
    private String state;//购物车状态

    private PageForm pageForm;
    private List<BaseBean> list;
    private Object object1;
    private String typeLeix;
    private String typeNews;
    private String judge;//用于判断是从哪个页面跳转进来的
    private String promotionID;//页面传值所需
    private List<String> slist;//极光推送设备号
    private String industy;
    private String comName;
    private String ppids; //资格代理产品的详情ppid

    private String identify;//识别标识,用于判断是否是车辆推荐进来

    @Resource
    private transferService transService;
    private String pos;//购物车类别

    private String industryType;
    private String posNum;//终端机编号
    private String itemNum;
    @Resource
    private EarthIndexService earthIndexService;
    //登录用户信息获取
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    SessionWrap sw = SessionWrap.getInstance();

    TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
            SessionWrap.KEY_SHOPCUSCOM);

    /*
     * 邮编查询常量ljc
     * */
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    //配置您申请的KEY
    public static final String APPKEY = "6cb55407d7b0ad92c9050e2db4350f3b";
    private String pid;//省id
    private String cid;//市id
    private String did;//区id
    private String q;//关键词
    private List<BaseBean> list1;//更多精彩
    private String flag;//判断标识
    private List<BaseBean> listsize;
    private List<BaseBean> listcolor;
    private String stype;// 地址返回判断，有参返回【餐饮】
    private List<Object> cclist;//新闻页面的二维码信息
    //预算模块ljc
    private String budget;//隐藏产品页面底部标识
    private String goodsBillsID;
    private String cashierBillsID;
    private String ptppid;//促销传来的产品id，可能多个
    private String ptmorre;//促销品总价格
    private String ptstandard;//促销品规格
    private String recordId; //抽奖记录
    private String custype;//客户会员类别

    private CashierBills cbills;
    private FinalPackage finalPackage;

    private String sccid;

    private WxPayDto payDto;

    private String staffid;
    private String enrollId;
    private String pricetype;//价格类型 (0或者null：零售订单  1：批发价格 2：VIP  3： 普通活动 4：特价活动)

    //个人粉丝加入

    public String personGiveCard() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (session.getAttribute(SessionWrap.KEY_CUSTOMER) == null) {//如果没登陆,跳转登录,下面url是个人交换名片路径
            String url = ServletActionContext.getRequest().getRequestURL() + "?user=" + user;
            session.setAttribute("url", url);
            return "login";
        } else {
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            activity = cus.getAccount();
        }
        String hql = "select m from JoinFans m where m.faccount=? and m.zaccount=?";
        List<BaseBean> lst = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{user, activity});
        if ((lst != null && lst.size() > 0) || user.equalsIgnoreCase(activity)) {//存在关系.或者互粉对象一样,均跳出
            return "zcok";
        } else {
            List<BaseBean> list = new ArrayList<BaseBean>();
            JoinFans jf1 = new JoinFans();
            jf1.setJfID(serverService.getServerID("jfID"));
            jf1.setFaccount(user);
            jf1.setSource("粉丝好友名片");
            jf1.setState("00");
            jf1.setZaccount(activity);

            JoinFans jf2 = new JoinFans();
            jf2.setJfID(serverService.getServerID("jfID"));
            jf2.setFaccount(activity);
            jf2.setSource("粉丝好友名片");
            jf2.setState("00");
            jf2.setZaccount(user);

            list.add(jf1);
            list.add(jf2);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
            return "zcok";
        }
    }

    public String companyGiveCard() {
        String parentAccount = null;
        String staffId = null;
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (session.getAttribute(SessionWrap.KEY_CUSTOMER) == null) {//如果没登陆,跳转登录,下面url是公司交换名片路径
            String url = ServletActionContext.getRequest().getRequestURL() + "?ccompanyId=" + ccompanyId;
            session.setAttribute("url", url);
            return "login";
        }
        TEshopCustomer tec = (TEshopCustomer) session.getAttribute(SessionWrap.KEY_CUSTOMER);
        if (ccompanyId != null) {
            companyId = null;
            String hql = "select new CcomCom(cc.comanyId) from CcomCom cc where cc.ccompanyId=?";
            CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyId});
            if (cc != null && cc.getComanyId() != null) {
                companyId = cc.getComanyId();
            }
            if (companyId != null) {
                hql = "select m from TEshopCusCom m where m.state=? and m.companyId=?";
                TEshopCusCom te = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"2", companyId});
                if (te != null) {
                    parentAccount = te.getAccount();
                    staffId = te.getStaffid();
                } else {
                    result = "公司帐号没查到";
                    return null;
                }
            }
        }
        if (ccompanyId == null || companyId == null) {
            return null;
        }


        String hql = "select m from JoinFans m where m.faccount=? and m.zaccount=?";
        List<BaseBean> lst = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{tec.getAccount(), parentAccount});
        if ((lst != null && lst.size() > 0) || parentAccount.equalsIgnoreCase(tec.getAccount())) {//存在关系.或者互粉对象一样,均跳出
            return "zcok";
        } else {
            List<BaseBean> list = new ArrayList<BaseBean>();
            JoinFans jf1 = new JoinFans();
            jf1.setJfID(serverService.getServerID("jfID"));
            jf1.setFaccount(tec.getAccount());
            jf1.setStaffid(tec.getStaffid());
            jf1.setSource("粉丝好友名片");
            jf1.setState("00");
            jf1.setZaccount(parentAccount);

            JoinFans jf2 = new JoinFans();
            jf2.setJfID(serverService.getServerID("jfID"));
            jf2.setFaccount(parentAccount);
            jf2.setStaffid(staffId);
            jf2.setSource("粉丝好友名片");
            jf2.setState("00");
            jf2.setZaccount(tec.getAccount());

            list.add(jf1);
            list.add(jf2);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
            return "zcok";
        }
    }


    /**
     * 获取新闻详情
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getWFJnews() {
        String sql1 = "select g.url,g.name from dt_ProductPackaging p join dtGoodFunction g on p.goodsID=g.goodsid  where p.companyId=? and p.type=? and p.fiveclear=? and p.goodsID=?";
        String sql2 = "select p.goodsName,p.PackagingDate,p.image,p.model from dt_ProductPackaging p where p.companyId=? and p.type=? and p.fiveclear=? and p.goodsID=? ";
        //更多精彩
        String sql3 = "select p.goodsName,p.goodsID from dt_ProductPackaging p where p.companyId=? and p.type=? and p.fiveclear=? and p.goodsID <>? and p.delStatus='00'";
        if (ccompanyId == null || ccompanyId.equals("")) {
            object1 = baseBeanService.getObjectBySqlAndParams(sql2, new Object[]{"company201009046vxdyzy4wg0000000065", "新闻", "2", goodsid});
            list = baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{"company201009046vxdyzy4wg0000000065", "新闻", "2", goodsid});

            list1 = baseBeanService.getListBeanBySqlAndParams(sql3, new Object[]{"company201009046vxdyzy4wg0000000065", "新闻", "2", goodsid});
        } else {
            CcomCom ccomcom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId = ?", new Object[]{ccompanyId});
            Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{ccomcom.getComanyId()});
            if (company != null) {
                object1 = baseBeanService.getObjectBySqlAndParams(sql2, new Object[]{company.getCompanyID(), "新闻", "2", goodsid});
                list = baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{company.getCompanyID(), "新闻", "2", goodsid});
                list1 = baseBeanService.getListBeanBySqlAndParams(sql3, new Object[]{company.getCompanyID(), "新闻", "2", goodsid});
            }
        }
        maplist1 = new HashMap<Integer, String[]>();
        //获取每个功能信息的具体内容
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                Object[] oo = (Object[]) obj;
                maplist1.put(i, new String[]{getContentFromFile(oo[0] == null ? "" : oo[0].toString()), oo[1] == null ? "" : oo[1].toString()});
            }
        }
        //新闻底部增加公司名片的二维码
        HttpServletRequest request = ServletActionContext.getRequest();
        String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
        cclist = baseBeanService.getListBeanBySqlAndParams("select ccompanyID,companyName,logoPath,qrCodePath from dtContactCompany where ccompanyID=?", new Object[]{(ccompanyId == null || ccompanyId.equals("")) ? "contactCompany20111102YFXHT4NASR0000011516" : ccompanyId});
        Object obj = null;
        if (cclist != null && cclist.size() > 0) {
            obj = cclist.get(0);
            Object[] objs = (Object[]) obj;
            String httpUrl = Constant.HTTP + "text=" + basePath + "ea/industry/ea_CompanyCard.jspa?ccompanyId=" + ccompanyId;
            String httpArg = "&logo=" + basePath + objs[2] + "&key=" + Constant.API_KEY;
            objs[3] = httpUrl + httpArg;
        }
        return "news";

    }

    //新建新闻页
    public PageForm NewsList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String searchc = request.getParameter("searchc");
        String fbr = request.getParameter("fbr");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String cate = request.getParameter("cate");


        if ("慈善捐赠".equals(cate)) {
            if (tc != null) {

                earthIndexService.addBrowseHistory(tc.getSccId(), "慈善", "");
            }
        }
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select *  from (");
        sql.append(" select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.model,p.ppid,p.type,p.companyID,m.ccompany_Id,cc.companyname");
        sql.append(" from dt_ProductPackaging p, DT_ccom_com m,dtcontactcompany cc");
        sql.append(" where (p.companyId = ? and p.type=? and p.fiveclear=? and p.delStatus=? and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid");


        objList.add("company201009046vxdyzy4wg0000000065");
        objList.add("新闻");
        objList.add("2");
        objList.add("00");

        if (searchc != null && !searchc.equals("")) {
            sql.append(" and p.goodsName like ?");
            objList.add("%" + searchc + "%");
        }


        if (fbr != null && !fbr.equals("")) {
            sql.append(" and cc.companyName like ?");
            objList.add("%" + fbr + "%");
        }
        if (start != null && !start.equals("") && end != null && !end.equals("")) {
            sql.append(" and p.PackagingDate between ? and ?");
            objList.add(Utilities.getDateFromString(start, "yyy-MM-dd"));
            objList.add(Utilities.getDateFromString(end, "yyy-MM-dd"));
        }
        if (cate != null && !cate.equals("")) {

            sql.append(" and (");
            String[] ar = cate.split(",");
            for (int i = 0; i < ar.length; i++) {
                if (i != ar.length - 1) {
                    sql.append("categoryName = ? or ");
                } else {
                    sql.append("categoryName = ?)");

                }
                objList.add(ar[i]);
            }

        }
        sql.append(")");


        sql.append(" or (p.type = ? and p.review = ? and p.companyID=m.compnay_id and m.ccompany_id = cc.ccompanyid and p.companyid is not null");


        objList.add("会员分享");
        objList.add("00");
        if (searchc != null && !searchc.equals("")) {
            sql.append(" and p.goodsName like ?");
            objList.add("%" + searchc + "%");
        }
        sql.append(")");

        if (fbr != null && !fbr.equals("")) {
            sql.append(" and cc.companyName like ?");
            objList.add("%" + fbr + "%");
        }
        if (start != null && !start.equals("")) {
            sql.append(" and p.PackagingDate between ? and ?");
            objList.add(Utilities.getDateFromString(start, "yyy-MM-dd"));
            objList.add(Utilities.getDateFromString(end, "yyy-MM-dd"));

        }
        if (cate != null && !cate.equals("")) {

            sql.append(" and (");
            String[] ar = cate.split(",");
            for (int i = 0; i < ar.length; i++) {
                if (i != ar.length - 1) {
                    sql.append("categoryName = ? or ");
                } else {
                    sql.append("categoryName = ?)");

                }
                objList.add(ar[i]);
            }

        }
        sql.append(" union");
        sql.append(" select pp.goodsName,pp.PackagingDate,pp.image,pp.goodsID,pp.model,pp.ppid,pp.type,pp.companyid,pp.companyid,s.staffname");
        sql.append(" from dt_ProductPackaging pp, dt_hr_staff s");
        sql.append(" where pp.type = ? and pp.review = ? and pp.staffid = s.staffid and pp.companyid is null");

        objList.add("会员分享");
        objList.add("00");

        if (searchc != null && !searchc.equals("")) {
            sql.append(" and pp.goodsName like ?");
            objList.add("%" + searchc + "%");
        }

        if (fbr != null && !fbr.equals("")) {
            sql.append(" and s.staffName like ?");
            objList.add("%" + fbr + "%");
        }
        if (start != null && !start.equals("")) {
            sql.append(" and pp.PackagingDate between ? and ?");
            objList.add(Utilities.getDateFromString(start, "yyy-MM-dd"));
            objList.add(Utilities.getDateFromString(end, "yyy-MM-dd"));

        }
        if (cate != null && !cate.equals("")) {

            sql.append(" and (");
            String[] ar = cate.split(",");
            for (int i = 0; i < ar.length; i++) {
                if (i != ar.length - 1) {
                    sql.append("pp.categoryName = ? or ");
                } else {
                    sql.append("pp.categoryName = ?)");

                }
                objList.add(ar[i]);
            }

        }

        sql.append(") x");
        if (industryType != null && industryType != null) {
            //sql.append(" inner join dtcompany y on  y.companyid = x.companyID inner join dt_ccom_com ccm on x.companyID =  ccm.compnay_id inner join dtcontactcompany ctc on ctc.ccompanyid = ccm.ccompany_id where ctc.industrytype  = ? ");
            sql.append(" inner join dtcontactcompany cy on x.ccompany_id = cy.ccompanyid and cy.industrytype = ? ");
            objList.add(industryType);
        }
        sql.append(" order by x.PackagingDate desc");
        if (ccompanyId == null || ccompanyId.equals("")) {
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 6, sql.toString(), "select count(*) from (" + sql + ")",
                    objList.toArray());
            if (pageForm != null && pageForm.getPageNumber() == 1) {
                list = pageForm.getList();
                //集合非空校验
                if (null != list && !list.isEmpty()) {
                    Object obj = list.get(0);
                    Object[] oo = (Object[]) obj;
                    goodname = oo[0].toString();

                }
            }
        }
        return pageForm;
    }


    public String getNewsList() {
        if (tc != null) {

            earthIndexService.addBrowseHistory(tc.getSccId(), "资讯", "");
        }
        pageForm = NewsList();

        return "newslist";
    }

    /**
     * 新版pc新闻的跳转
     */
    public String getNews() {
        pageForm = NewsList();

        return "newsNew";
    }

    public String AjaxNewsList() {
        pageForm = NewsList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 查询打赏列表
     *
     * @throws Exception
     */
    public String getReward() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ppid = request.getParameter("ppid");//打赏新闻id
        List<Object> list = null;
        try {
            list = goldOrderService.getReward(ppid);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }


    public String dealOrder() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String trade_no = req.getParameter("trade_no");
        String ppid = req.getParameter("ppid");
        String morre = req.getParameter("morre");
        String sccid = req.getParameter("sccid");
        String journalNum = req.getParameter("journalNum");
        String wfStatus1 = req.getParameter("wfStatus1");

        try {
            genScanCodePay(trade_no, ppid, WeChatUtils.changeF2Y(morre), sccid, journalNum, "00", wfStatus1, "");
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "success";

    }

    /**
     * 查询公司的星级微分金店
     *
     * @return
     */

    /**
     * 扫码支付付款后回调函数
     *
     * @param tradeCode  第三方交易号
     * @param ppid       购买产品ID
     * @param morre      金额
     * @param sccid      用户ID
     * @param journalNum 支付订单号
     * @param wfStatus4  支付方式
     * @param wfStatus1  微信支付方式
     * @return
     */
    public Boolean genScanCodePay(String tradeCode, String ppid, String morre, String sccid, String journalNum, String wfStatus4, String wfStatus1, String waiterID) {
        Boolean bool = true;
        try {
            //生成订单
            String cashierBillsID = goldOrderService.generateOrderSheet(ppid, morre, sccid, journalNum, waiterID, wfStatus4);
            if (cashierBillsID != null && !cashierBillsID.equals("")) {

                //生成收款单
                Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, morre, wfStatus4, wfStatus1);
                if (bo == true) {
                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "d");
                    }catch (Exception e){
                        logger.error("操作异常", e);
                        logger.info("复制订单收款单错误");
                    }
                    //把订单状态改程03已收货
                    goldOrderService.updateFkState(cashierBillsID);
                    //分金币
                    transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
                }
            }

        } catch (Exception e) {
            bool = false;
            logger.error("操作异常", e);
        }
        return bool;
    }


    public Boolean genCanYin(String tradeCode, String journalNum, String morre, String wfStatus4, String wfStatus1) {
        Boolean bool = true;
        try {
            String hqlc = "from CashierBills where journalNum = ?";
            CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlc, new Object[]{journalNum});

            String hql = "from ContactCompany c where c.ccompanyID = (select m.ccompanyId from CcomCom m where m.comanyId = ?)";
            ContactCompany contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cc.getCompanyID()});

            if (contactCompany.getIndustryType() != null && contactCompany.getIndustryType().indexOf("餐饮") != -1) {
                //先生成取餐码
                if (cc.getMealNum() == null || cc.getMealNum().equals("")) {
                    goldOrderService.searchMealNum(cc.getCompanyID(), cc);
                }
                //生成收款单
                Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, morre, wfStatus4, wfStatus1);
                if (cc.getPrivateRoom() != null && !cc.getPrivateRoom().equals("")) {
                    if (bo == true) {
                        try {
                            //收款单生成后复制订单和收款单到新表
                            goldOrderService.copyCash(journalNum, "d");
                        }catch (Exception e){
                            logger.error("操作异常", e);
                            logger.info("复制订单收款单错误");
                        }

                        System.out.print(bo + "111111111111111111111111");
                        //把订单状态改程03已收货
                        goldOrderService.updateFkState(cc.getCashierBillsID());
                        //分金币
                        transService.Distribution(cc.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
                    }
                }


            } else {

                bool = false;
            }

        } catch (Exception e) {
            bool = false;
            logger.error("操作异常", e);
        }

        return bool;
    }

    /**
     * s扫码助手付款后回调
     *
     * @param tradeCode
     * @param waiterID
     * @param companyId
     * @param morre
     * @param sccid
     * @param journalNum
     * @param wfStatus4
     * @param wfStatus1
     * @return
     */
    public Boolean genScanAssiCodePay(String tradeCode, String waiterID, String companyId, String morre, String sccid, String journalNum, String wfStatus4, String wfStatus1, String coID) {
        Boolean bool = true;
        try {
            //生成订单
            //   System.out.print(tradeCode + waiterID + companyId + morre + sccid + journalNum + wfStatus4 + wfStatus1 + coID);
            //   logger.error("生成订单参数：" + tradeCode + waiterID + companyId + morre + sccid + journalNum + wfStatus4 + wfStatus1 + coID);
            String cashierBillsID = goldOrderService.generateMuliProOrder(waiterID, companyId, morre, sccid, journalNum, coID);
            //   System.out.print("cashierBillsID" + cashierBillsID);
            //    logger.error("cashierBillsID" + cashierBillsID);
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                //生成收款单
                Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, morre, wfStatus4, wfStatus1);
                if (bo == true) {
                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "d");
                    }catch (Exception e){
                        logger.error("操作异常", e);
                        logger.info("复制订单收款单错误");
                    }
                    //把订单状态改程03已收货
                    goldOrderService.updateFkState(cashierBillsID);
                    //分金币
                    transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
                    //生成已经结算的clientOrder
                    assiCartService.genFiniClientOrder(cashierBillsID, coID);
                }
            }

        } catch (Exception e) {
            bool = false;
            logger.error("操作异常", e);
        }
        return bool;
    }


    /**
     * 学员练车
     *
     * @param tradeCode
     * @param journalNum
     * @param wfStatus4
     * @param wfStatus1
     * @return
     */
    public void bookPay(String tradeCode, String journalNum, String wfStatus4, String wfStatus1) {

        //生成收款单
        Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, null, wfStatus4, wfStatus1);
        if (bo == true) {

            try {
                //收款单生成后复制订单和收款单到新表
                goldOrderService.copyCash(journalNum, "d");
            }catch (Exception e){
                logger.error("操作异常", e);
                logger.info("复制订单收款单错误");
            }
            CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});
            //把订单状态改程03已收货
            goldOrderService.updateFkState(cashierBills.getCashierBillsID());
            //分金币
            transService.Distribution(cashierBills.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
        }


    }

    /**
     * 自助收银
     *
     * @param tradeCode
     * @param morre
     * @param sccid
     * @param journalNum
     * @param wfStatus4
     * @param wfStatus1
     * @param addressID
     * @return
     */

    public Boolean genSelfPay(String tradeCode, String morre, String sccid, String journalNum, String wfStatus4, String wfStatus1, String addressID) {
        logger.info("genSelfPay");
        Boolean bool = true;
        try {
            //生成订单
            // System.out.print(tradeCode + morre + sccid + journalNum + wfStatus4 + wfStatus1);
            //  logger.error("生成订单参数：" + tradeCode + morre + sccid + journalNum + wfStatus4 + wfStatus1);
            String cashierBillsID = "";
            CashierBills   cc = (CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?",new Object[]{journalNum});

            try {
                UnPayRecord unPayRecord = smSerivce.getUnPayRecord(journalNum);
                if(unPayRecord!=null&&unPayRecord.getRemainNum()!=null&&!unPayRecord.getRemainNum().equals("")){
                    sccid = unPayRecord.getSccId();
                    morre = unPayRecord.getRemainNum();

                }
                if(cc!=null){
                    cashierBillsID = cc.getCashierBillsID();
                }
            }catch (Exception ef){
                logger.error("操作异常", ef);
            }
            if(cashierBillsID.equals("")) {
                cashierBillsID = goldOrderService.generateSelfPayOrder(morre, sccid, journalNum, wfStatus4, addressID);
                cc = (CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?",new Object[]{journalNum});
            }
            // System.out.print("cashierBillsID" + cashierBillsID);
            //    logger.error("cashierBillsID" + cashierBillsID);
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                goldOrderService.getPrice(cashierBillsID);
                //生成收款单
                Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, morre, wfStatus4, wfStatus1);
                if (bo == true) {
                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "d");
                    }catch (Exception e){
                        logger.error("操作异常", e);
                        logger.info("复制订单收款单错误");
                    }

                    if (smSerivce.isSqPos(journalNum)) {//不是社区就超市内就发货
                        if(!"智能货柜".equals(cc.getGoodsName())){
                            //发货
                            goldOrderService.autoFh(cashierBillsID);
                        }

                        //把订单状态改程03已收货
                        goldOrderService.updateFkState(cashierBillsID);
                        //分金币
                        transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
                    }
                }
            }

        } catch (Exception e) {
            bool = false;
            logger.error("操作异常", e);
        }
        return bool;
    }


    public String genFSelfPay() {
        Boolean bool = true;
        try {
            String filePath = "D:\\dz.xlsx";
            //生成订单
            String[] columns = {"tradeCode", "journalNum", "交易场景"};
            List<Map<String, String>> list = ExcelReadUtils.jxexcel(filePath, columns);
            //遍历解析出来的list
            int f = 0;

            for (Map<String, String> map : list) {
                String tradeCode = "";
                String journalNum = "";
                String wfStatus4 = "";
                String wfStatus1 = "";
                for (Entry<String, String> entry : map.entrySet()) {

                    if (entry.getKey().equals("tradeCode")) {
                        if (entry.getValue().indexOf("`") != -1) {
                            tradeCode = entry.getValue().substring(1).trim();

                        } else {
                            tradeCode = entry.getValue().trim();
                        }

                        System.out.print(entry.getKey() + ":" + tradeCode + ",");
                    }
                    if (entry.getKey().equals("journalNum")) {
                        if (entry.getValue().indexOf("`") != -1) {
                            journalNum = entry.getValue().substring(1).trim();

                        } else {
                            journalNum = entry.getValue().trim();
                        }
                        System.out.print(entry.getKey() + ":" + journalNum + ",");
                    }
                    if (entry.getKey().equals("交易场景")) {
                        if (entry.getValue().equals("APP支付")) {

                            wfStatus4 = "00";
                            wfStatus1 = "01";
                            journalNum = entry.getValue();
                        }
                        if (entry.getValue().equals("公众号支付")) {
                            wfStatus4 = "00";
                            wfStatus1 = "00";

                        }
                        if (entry.getValue().equals("即时到帐")) {
                            wfStatus4 = "01";
                            wfStatus1 = "";

                        }
                        System.out.print(entry.getKey() + ":" + wfStatus4 + ",");
                        System.out.print(entry.getKey() + ":" + wfStatus1 + ",");
                    }


                }
                f++;
                logger.info("值：{}", f);

                String phql = " from CashierBills where journalNum = ?";
                CashierBills payc = (CashierBills) baseBeanService.getBeanByHqlAndParams(phql, new Object[]{journalNum});
                if (payc != null) {
                    String cashierBillsID = payc.getCashierBillsID();
                    if (payc.getFkStatus().equals("01")) {

                        //生成收款单
                        Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, null, wfStatus4, wfStatus1);
                        if (bo == true) {
                            try {
                                //收款单生成后复制订单和收款单到新表
                                goldOrderService.copyCash(journalNum, "d");
                            }catch (Exception e){
                                logger.error("操作异常", e);
                                logger.info("复制订单收款单错误");
                            }

                            //发货
                            goldOrderService.autoFh(cashierBillsID);
                            //把订单状态改程03已收货
                            goldOrderService.updateFkState(cashierBillsID);
                            //分金币
                            transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");

                        }


                    }
                }


                logger.debug("");

            }


        } catch (Exception e) {
            bool = false;
            logger.error("操作异常", e);
        }
        return "";
    }

    /**
     * 自助收银积分购买
     *
     * @return
     */
    public String genSelfPayPoint() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest req = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        String wfStatus4 = req.getParameter("wfStatus4");

        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        Boolean bool = true;
        Boolean bo = true;
        String jfhql = null;
        BigDecimal score = BigDecimal.ZERO;
        try {
            if (wfStatus4 != null && wfStatus4.equals("05")) {
                jfhql = "from BonusPoints where sccid=?";
                BonusPoints bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams(jfhql, new Object[]{tc.getSccId()});
                score = new BigDecimal(bp.getBonusPointScore());
            } else if (wfStatus4 != null && wfStatus4.equals("07")) {
                jfhql = "from WfjJifen where sccid=?";
                WfjJifen wj = (WfjJifen) baseBeanService.getBeanByHqlAndParams(jfhql, new Object[]{tc.getSccId()});
                score = new BigDecimal(wj.getWfjJifenScore());
            }
            if (score.compareTo(new BigDecimal(morre)) >= 0) {
                //生成订单
                String addressID = ServletActionContext.getRequest().getParameter("addressID");

                String cashierBillsID = goldOrderService.generateSelfPayOrder(morre, tc.getSccId(), journalNum, wfStatus4, addressID);

                if (cashierBillsID != null && !cashierBillsID.equals("")) {
                    bo = goldOrderService.generateSpoints(journalNum, journalNum, morre, wfStatus4);

                    //生成收款单
                    if (bo == true) {
                        try {
                            //收款单生成后复制订单和收款单到新表
                            goldOrderService.copyCash(journalNum, "j");
                        }catch (Exception e3){
                            logger.error("操作异常", e3);
                            logger.info("复制订单积分或者金币入库单错误");
                        }


                        if (smSerivce.isSqPos(journalNum)) {
                            goldOrderService.autoFh(cashierBillsID);
                            //把订单状态改程03已收货
                            goldOrderService.updateFkState(cashierBillsID);
                            //分金币
                            transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
                        }
                    }
                } else {
                    bo = false;
                }
            } else {
                bo = false;
            }
        } catch (Exception e) {
            bool = false;
            logger.error("操作异常", e);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", bool);
        map.put("bo", bo);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    @SuppressWarnings("unchecked")
    public String getWFJshops() {
        StringBuilder sqls = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sqls.append("select *  from (");
        sqls.append(" select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.model,p.ppid,p.type,p.companyID,m.ccompany_Id,cc.companyname");
        sqls.append(" from dt_ProductPackaging p, DT_ccom_com m,dtcontactcompany cc");
        sqls.append(" where (p.companyId = ? and p.type=? and p.fiveclear=? and p.delStatus=? and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid)");
        sqls.append(" or (p.type = ? and p.review = ? and p.companyID=m.compnay_id and m.ccompany_id = cc.ccompanyid and p.companyid is not null)");
        sqls.append(" union");
        sqls.append(" select pp.goodsName,pp.PackagingDate,pp.image,pp.goodsID,pp.model,pp.ppid,pp.type,pp.companyid,pp.companyid,s.staffname");
        sqls.append(" from dt_ProductPackaging pp, dt_hr_staff s");
        sqls.append(" where pp.type = ? and pp.review = ? and pp.staffid = s.staffid and pp.companyid is null) x");
        objList.add("company201009046vxdyzy4wg0000000065");
        objList.add("新闻");
        objList.add("2");
        objList.add("00");
        objList.add("会员分享");
        objList.add("00");
        objList.add("会员分享");
        objList.add("00");

        sqls.append(" order by x.PackagingDate desc");

        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 6, sqls.toString(), "select count(*) from (" + sqls + ")",
                objList.toArray());


        list = pageForm != null ? pageForm.getList() : null;
        request.setAttribute("newslist", list);
        // String hql = "from CcomConf where ccompanyId= ? and modalType between ? and ? order by sn";
//        List<BaseBean> companyGList = baseBeanService
//                .getListBeanByHqlAndParams(hql,
//                        new Object[]{
//                                "contactCompany20111102YFXHT4NASR0000011516",
//                                "2", "4"});
//        ServletActionContext.getRequest().setAttribute("companyGList",
//                companyGList);
        HttpSession session = ServletActionContext.getRequest().getSession();
        ActionContext ctx = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String url = request.getRequestURL() + "?companyId=" + companyId
                + "&indus=" + indus;
        session.setAttribute("url", url);

        List<Object> parms = new ArrayList<Object>();
        String sql = "select co.organizationid, co.organizationname,co.ocode,sh.star,sh.titleimage,co.companyID ,"
                + " (select st.num from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as num,"
                + " (select st.imagepath from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as imagepath,sh.address"
                + " from dtcorganization co,T_ESHOP_EXTINFO sh "
                + " where co.organizationid=sh.organizationid and co.iswfj='是' and sh.eshopstatus='0' and sh.inused='1' and co.Status='00'";
        if ("searchShops".equals(search) && corganization != null
                && !corganization.getOrganizationName().equals("")) {
            sql += " and ( co.organizationName like ?";
            parms.add("%" + corganization.getOrganizationName() + "%");
            sql += " or co.ocode like ?";
            parms.add("%" + corganization.getOrganizationName() + "%");
            sql += " or sh.address like ?";
            parms.add("%" + corganization.getOrganizationName() + "%");
            sql += ")";
        }
        if (null != companyId && !"null".equals(companyId)) {
            sql += " and co.companyid=?";
            parms.add(companyId);
        }
        if (user != null && !"".equals(user)) {
            TEshopCustomer customer = (TEshopCustomer) baseBeanService
                    .getBeanByHqlAndParams(
                            "from TEshopCustomer where account=? AND logOff=0",
                            new Object[]{user});

			/*TEshopCusCom cus = (TEshopCusCom)baseBeanService
                    .getBeanByHqlAndParams(
							"from TEshopCusCom where account=? AND logOff=0 and",
							new Object[] { user });*/
            TEshopCusCom cus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?", new Object[]{customer.getAccount(), "01"});
            if (cus == null) {
                beans = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 order by cusType", new Object[]{customer.getAccount()});
                cus = (TEshopCusCom) beans.get(0);
                cus.setAcquiesce("01");
                baseBeanService.update(cus);
            }
            SessionWrap sw = SessionWrap.getInstance();
            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                    "from Staff where staffID=? ",
                    new Object[]{customer.getStaffid()});
            StaffAddress staffAddress = (StaffAddress) baseBeanService
                    .getBeanByHqlAndParams(
                            "from StaffAddress where staffID=? and isDefault='是'",
                            new Object[]{customer.getStaffid()});

            sw.setObject(session, SessionWrap.KEY_STAFF, staff);
            sw.setAddress(
                    session,
                    staffAddress == null ? "" : staffAddress
                            .getAddressDetailed());
            sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
            sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);
            sw.setObject(session, SessionWrap.KEY_ADDRESS, staffAddress);
        }
        if (sort != "" && "bhjx".equals(sort)) {
            sql += "order by  co.ocode desc";
        } else if (sort != "" && "dmjx".equals(sort)) {
            sql += "order by  co.organizationname desc";
        } else {
            sql += " order by  co.ocode ,co.organizationname ";
        }
        beans = baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
        // 进入有产品有店铺的页面查询该公司产品
        if (null != indus && "products".equals(indus)) {
            Object[] a = null;
            String prosql = "select  pp.ppid,pp.goodsname,pp.image,pc.price,co.companyname,pp.goodsID,trim(pp.virtual) "
                    + " ,pp.companyid  from dt_productpackaging pp, dt_productpricecategory pc ,dtcompany co"
                    + " where pp.ppid=pc.ppid and co.companyid=pp.companyID and pc.category='零售价' and  pp.showweixin='01'  ";
            if (companyId != null && companyId.length() > 1) {
                a = new Object[]{companyId};
                prosql += "and pp.companyid=?";
            }
            productList = baseBeanService.getListBeanBySqlAndParams(prosql, a);
        }
        HttpServletRequest req = ServletActionContext.getRequest();

        String pc = req.getParameter("pc");
        if (activity == null) {
            if (HttpRequestDeviceUtils.isMobileDevice(req) == false && (pc == null || pc.equals(""))) {
                typeLeix = "pc";
                return "wfjshops";
            } else {
                typeLeix = "app";
                return "wfjshops";
            }


        }
        return "activityShopSearch";
    }

    // APP注册 页面 带参数
    public String getjspzc() {
        HttpServletRequest req = ServletActionContext.getRequest();
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("sbhk", Math.random() + "");
        String vs = req.getParameter("vs");
        if(vs!=null&&!vs.equals("")){
            DocInviteRecord record = (DocInviteRecord)baseBeanService.getBeanByHqlAndParams("from DocInviteRecord where seqno = ?",new Object[]{vs});
            req.setAttribute("record",record);
        }
        return "NEWjsp";
    }


    //登陆页面的注册
    public String register() {

        return "register";
    }

    /**
     * 查询网站user
     *
     * @return
     */
    public void findWebUser() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String sccid = "";
        if (ccompanyId != null && ccompanyId.length() > 0) {
            String hql = "select new CcomCom(cc.comanyId) from CcomCom cc where cc.ccompanyId=?";
            CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyId});
            if (cc != null && cc.getComanyId() != null) {
                companyId = cc.getComanyId();
            }
            if (companyId != null) {
                hql = "select m from TEshopCusCom m where m.state=? and m.companyId=?";
                TEshopCusCom te = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"2", companyId});
                if (te != null) {
                    sccid = te.getSccId();//记录邀请人
                }
            }

        }

        try {
            response.sendRedirect(request.getContextPath() + "/ea/wfjshop/ea_getjspzc.jspa?sccid=" + sccid);
        } catch (IOException e) {
            logger.error("操作异常", e);
        }

    }

    /**
     * 已经条件：账号 (手机号)account 密码password 会员类别 custType 默认7 上级：根据pid情况绑定 网页微分金注册
     *
     * @return
     */

    public String wfjRegisterByweb() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String pid = request.getParameter("pid");

        List<BaseBean> beans = new ArrayList<BaseBean>();
        String staffid = serverService.getServerID("cstaff");

        // 用户表
        TEshopCustomer customer = new TEshopCustomer();
        customer.setStaffid(staffid);
        customer.setCustid(serverService.getServerID("custid"));
        customer.setAccount(account);
        customer.setPassword(password);
        beans.add(customer);

        // 用户级别表
        TEshopCusCom tc = new TEshopCusCom();
        tc.setSccId(serverService.getServerID("sccid"));
        tc.setStaffid(staffid);
        tc.setState("1");// 默认为个人
        tc.setAccount(account);
        tc.setCusType("7");// 默认为客户

        //String sql = "select t.account from T_ESHOP_CUSCOM t where  t.cusType = ?  connect by nocycle prior t.account = t.Superioragent start with t.account = ?";
        // 默认绑定上级问题 根据传过来的pid即用户表中的custid,以此来判断究竟绑定谁作为上级
        if (pid != null && !pid.equals("")) {
            String hqlcust = "from TEshopCusCom from  m where m.account = (select r.account from TEshopCustomer r where r.custid = ?)";
            TEshopCusCom pcustom = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams(hqlcust, new Object[]{pid});
            String pcusttype = pcustom.getCusType();

            if (Float.parseFloat(pcusttype) > 5) {
                // 大于5说明分享者级别级别高于代理商有可能是客户或者VIP客户
                tc.setSuperioragent(pcustom.getSuperioragent());

            } else {
                // 等于5说明分享者级别是代理商，正好可以绑定上
                tc.setSuperioragent(pcustom.getAccount());
            }
        } else {

            // 如果找不到分享人，默认抓天太世统里的代理商随机

			/*List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(
                    sql, new Object[] { "5", "15810799888" });
			if (list.size() != 0) {
				int i = (int) (Math.random() * (list.size() - 1)); // 0-100以内的随机数，用Matn.random()方式
				Object obj = (Object) list.get(i);
				tc.setSuperioragent(obj.toString());
			}*/
            tc.setSuperioragent("15810799888");
        }

        beans.add(tc);

        // 人员表
        Staff staff = new Staff();
        String phql = "select count(*) from Staff ";
        int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
        staff.setStaffCode("NO" + pcount);
        staff.setRecordCode("NO" + pcount);
        staff.setVerifyTime(new Date());
        staff.setStaffID(staffid);
        staff.setReference(account);// 电话默认写注册时候的电话
        staff.setSource("网页");
        staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");// 默认世统科技所在集团
        staff.setStaffStatus("00");
        staff.setStatus("00");// 默认一般人物
        staff.setStaus("01");// 默认身份证为空 为不确定人员
        staff.setVipno("1");// 注册时默认会员卡未激活

        // 粉丝相关
        if (tc.getSuperioragent() != null) {

            staff.setAccountID(tc.getSuperioragent());
            Staff staffp = (Staff) baseBeanService.getBeanByHqlAndParams(
                    "from Staff where staffID = ?",
                    new Object[]{tc.getStaffid()});
            staff.setAccountName(staffp.getStaffName());

            beans.add(staff);

            ContactRelation conRelation = new ContactRelation();
            String comID = findCompanyVip(account);// 当前会员所在
            conRelation.setCompanyID(comID);
            conRelation.setRelationID(serverService
                    .getServerID("contactrelation"));
            conRelation.setRelation("个人粉丝");
            conRelation.setStaffID(staff.getStaffID());
            beans.add(conRelation);
        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return "zcok";
    }

    private String findCompanyVip(String user) {
        String hql = "from TEshopCusCom where account = ? and logOff=0";
        TEshopCusCom tcc = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams(hql, new Object[]{user});
        if (tcc != null) {
            if (tcc.getState().equals("2")) {
                return tcc.getCompanyId();
            } else {
                return findCompanyVip(tcc.getSuperioragent());
            }
        }

        return null;

    }

    /**
     * 查询公司加入跟个人加入
     */
    @SuppressWarnings("unchecked")
    public String getpk() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
        if (cus == null) {
            if (user != null) {
                TEshopCustomer customer1 = (TEshopCustomer) baseBeanService
                        .getBeanByHqlAndParams(
                                "from TEshopCustomer where account=? AND logOff=0",
                                new Object[]{user});

                TEshopCusCom cus1 = (TEshopCusCom) baseBeanService
                        .getBeanByHqlAndParams(
                                "from TEshopCusCom where account=? AND logOff=0",
                                new Object[]{user});
                sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer1);
                sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus1);
            } else {
                return "login";
            }
        }


        //公司
        String hql = "from TEshopCusCom t where t.state = ? and t.companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)";
        TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"2", ccompanyId});
        String cusType = "";
        String lowType = "";
        //查找下级
        if (tcc != null) {
            cusType = tcc.getCusType();//网站的
            hql = "select t.cusType from T_ESHOP_CUSCOM t connect by nocycle prior t.account = t.Superioragent start with t.account = ? order by t.cusType desc";
            List<Object> list = baseBeanService.getListBeanBySqlAndParams(hql, new Object[]{tcc.getAccount()});
            hql = null;
            lowType = list.get(0).toString();
            if (lowType.equals("0.5")) {

                lowType = "1";
            } else if (lowType.equals("0")) {
                lowType = "0.5";


            } else {
                lowType = Integer.parseInt(lowType) + 1 + "";
            }

        }

        StringBuilder sql = new StringBuilder();
        StringBuilder sqlgs = new StringBuilder();
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? ");
        sql.append(" and p.ppid=ps.ppid and p.showweixin ='01' and p.type= ? and  ps.fcom_id is null ");
        sqlgs.append(sql);
        sqlgs.append(" and model != ?  order by sorting");
        sql.append(" and ((");
        if (cusType.equals("0")) {
            sql.append("p.model>=?");
            String tempsql = "select t.cusType from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?) connect by nocycle prior t.account = t.Superioragent start with t.account = ?";
            List<Object> lists = baseBeanService.getListBeanBySqlAndParams(tempsql, new Object[]{"0.5", "1", tcc.getAccount()});

            if (lists.contains("0.5")) {
                sql.append(" and p.model!='0.5'");
            }
            if (lists.contains("1")) {
                sql.append(" and p.model!='1'");
            }
        } else {
            sql.append(" p.model>?");
        }

        // 查询公司会员列表
        productList = baseBeanService.getListBeanBySqlAndParams(sqlgs.toString(),
                new String[]{"company201009046vxdyzy4wg0000000025", "公司会员", "0"});
        // 查询个人会员列表
        beans = baseBeanService.getListBeanBySqlAndParams(sql.append(") or model = '8') order by sorting").toString(), new String[]{
                "company201009046vxdyzy4wg0000000025", "个人会员", cusType});
        if ("ajax".equals(typeNews)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productList", productList);
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
            return "success";

        }
        return "jiaru";

    }

    /**
     * 验证是否有权利购买会员产品
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String validatecanBuy() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (request.getParameter("account") != null) {
            tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0", new Object[]{request.getParameter("account")});
            cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid = ?", new Object[]{tc != null ? tc.getStaffid() : ""});
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (cus == null) {
            String url = request.getHeader("Referer");
            session.setAttribute("url", url);
            map.put("login", "login");
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";

        }

//		String hqlcus = "from TEshopCusCom t where account = ? and acquiesce = ?";
//		TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
//				hqlcus, new Object[]{cus.getAccount(), "01"});

        String hql = "from ProductPackaging where ppID = ?";
        ProductPackaging pp = (ProductPackaging) baseBeanService
                .getBeanByHqlAndParams(hql, new Object[]{ppid});

        String cusType = tc.getCusType();
        String model = pp.getModel();
        String type = pp.getType();
        TEshopCusCom tcweb = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams(
                        "from TEshopCusCom t where t.companyId = (select c.comanyId from CcomCom c where c.comanyId = t.companyId and c.ccompanyId = ?)",
                        new Object[]{ccompanyId});
        JoinFans jfans = null;
        if (tcweb != null) {
            jfans = (JoinFans) baseBeanService.getBeanByHqlAndParams(
                    "from JoinFans where zaccount = ? and faccount = ?",
                    new Object[]{tcweb.getAccount(), tc.getAccount()});
        } else {
            map.put("result", "该网站尚未付款加入微分金平台系统");
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";
        }

        MarKeting mk = (MarKeting) baseBeanService.getBeanByHqlAndParams(
                "from MarKeting where userSccId = ?",
                new Object[]{tc.getSccId()});

        boolean t3 = true;
        String tip = "您的账号有问题，请联系工作人员!";

        if (cusType.equals("0.5") || cusType.equals("1")) {
            tip = "税务相关会员无法购买其他会员级别";// 税务相关用户无法购买其他会员级别
            t3 = false;
        } else if ((!model.equals("8") && !"0".equals(pp.getPrice()))
                && (Float.parseFloat(cusType) < Float.parseFloat(model))) {
            tip = "对不起，无法降级购买会员";// 不可降级购买
            t3 = false;

        } else if (tc.getState().equals("2") && !model.equals("8") && pp.getPrice() == "0") {

            t3 = false;
        } else if ((model.equals("0.5") || model.equals("1"))
                && !cusType.equals("7")) {
            tip = "对不起，只有新注册用户才能购买税务相关会员级别";// 只有7用户才能购买税务
            t3 = false;

        } else if (model.equals("0") && !cusType.equals("7")) {// 平台
            tip = "对不起，只有新注册用户才能购买平台";
            t3 = false;


        } else if (model.equals(cusType) && !model.equals("7")) {
            tip = "对不起，不能购买相同的会员级别";
            t3 = false;


        } else if (!model.equals("8") && tc.getState().equals("2") && !tc.getCusType().equals("7") && !tc.getCusType().equals("6")
                && type.equals("个人会员")) {// 平台
            tip = "对不起，您当前是公司会员不能转为个人会员";
            t3 = false;

        } else if (model.equals("8") && jfans != null) {
            tip = "对不起，您与该公司已经是粉丝好友，不可重复添加";
            t3 = false;

        } else if (model.equals("7") && tc.getCompanyId() != null && tc.getCompanyId() != "") {
            tip = "您已经拥有网站，无需0元加入";
            t3 = false;
        } else if (!model.equals("6")) {
            String hqls = "from TEshopCusCom where cusType = ?";
            List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
                    hqls, new Object[]{tc.getCusType()});
            if (list.size() == 1) {
                String hqlsub = "from TEshopCusCom where Superioragent = ?";
                List<BaseBean> listsub = baseBeanService
                        .getListBeanByHqlAndParams(hqlsub,
                                new Object[]{tc.getAccount()});
                if (listsub.size() != 0) {
                    tip = "对不起，暂时没有权限购买该会员级别";
                    t3 = false;
                }
            }
        }


        StringBuilder cushql = new StringBuilder(
                "SELECT t.staffid, t.companyid, t.state,t.superioragent,");
        cushql.append("t.pseudo_company_name,t.custype,t.sccid,t.account");
        cushql.append(" FROM T_ESHOP_CUSCOM t START WITH t.sccId = ?");
        cushql.append(" CONNECT BY nocycle PRIOR t.supperSccId = t.sccId");
        List<Object[]> cusList = baseBeanService.getListBeanBySqlAndParams(
                cushql.toString(), new Object[]{tc.getSccId()});
        if (mk == null && Float.parseFloat(cusList.get(0)[5].toString()) != 0f) {
            t3 = false;
        }
/*		else if(mk!=null&&mk.getMkuserID().equals("15810799888")&&tc.getCusType().equals("7")){
            t3 = false;
		}*/
        else {
            if (cusList.size() > 1) {
                float t1 = Float.parseFloat(cusList.get(0)[5].toString());
                if (t1 != 0f) {
                    float t2 = Float.parseFloat(cusList.get(1)[5].toString());
                    if (t2 == 0f && t1 != 0.5f) {
                        t3 = false;
                    } else if (t1 == 7f && t2 == 6f) {
                        t3 = false;
                    } else if (t1 <= t2) {
                        t3 = false;
                    } else if (t2 == 0.5f && t1 != 1f) {
                        t3 = false;
                    }
                } else {
                    t3 = false;
                }
            } else if (cusList.size() == 1) {
                float t1 = Float.parseFloat(cusList.get(0)[5].toString());
                if (t1 != 0f) {
                    t3 = false;
                }
            } else {
                t3 = false;
            }
        }
        if (!t3) {
            map.put("result", tip);
        } else {
            map.put("result", "success");
        }

        /*
         * if(tc.getCusType()!=null&&tc.getCusType().equals("7")){
         * if(mk==null){//没有推荐人 map.put("mk", "nomk"); }else{//有推荐人
         * map.put("mk", "yesmk"); } }else{ map.put("mk", "yesmk"); }
         */
        cushql.delete(0, cushql.length());
        cushql.append("select ps.ppid");
        cushql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p");
        cushql.append(" where p.ppid = ps.ppid and ps.fcom_id is null and ps.ppid in");
        cushql.append(" (select pt.ptppid from dt_promotion pt");
        cushql.append(" where pt.ppid = ?)");
        List<Object> list = baseBeanService.getListBeanBySqlAndParams(cushql.toString(), new Object[]{ppid});
        if (list != null && list.size() > 0) {
            ptppid = "";
            for (int i = 0; i < list.size(); i++) {
                ptppid += list.get(i) + ",";
            }
        }
        map.put("ptppid", ptppid);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";

    }


    /**
     * 验证购买普通商品权限
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String validateOrdiGoods() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
        if (cus == null) {
            String url = request.getHeader("Referer");
            session.setAttribute("url", url);
            map.put("login", "login");
            JSONObject json = JSONObject.fromObject(map);
            this.result = json.toString();
            return "success";
        }

        String enrollID = request.getParameter("enrollID");
        String hql = "from ProductPackaging where ppID = ?";
        ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppid});

        String tips = "";
        if (pp.getType() != null && !pp.getType().equals("公司会员") && !pp.getType().equals("个人会员")) {
            if (tc.getCusType().equals("0.5")) {
                tips = "您的会员等级为国税，无法购买普通产品,请用其他账号购买！";

            } else if (tc.getCusType().equals("1")) {
                tips = "您的会员等级为地税，无法购买普通产品,请用其他账号购买！";
            } else if (tc.getCusType().equals("0")) {
                tips = "您的会员等级为中联园平台，无法购买普通产品,请用其他账号购买！";
            } else if (tc.getSuperioragent() == null || tc.getSuperioragent().equals("")) {
                tips = "请先购买会员产品";
            } else if (pp.getType().equals("限会员购买") && Float.parseFloat(tc.getCusType()) > 5) {
                tips = "此产品限会员购买请立即升级";
            }
            boolean t3 = true;
            StringBuilder cushql = new StringBuilder("SELECT t.staffid, t.companyid, t.state,t.superioragent,");
            cushql.append("t.pseudo_company_name,t.custype,t.sccid,t.account");
            cushql.append(" FROM T_ESHOP_CUSCOM t START WITH t.sccId = ?");
            cushql.append(" CONNECT BY nocycle PRIOR t.supperSccId = t.sccId");
            List<Object[]> cusList = baseBeanService.getListBeanBySqlAndParams(cushql.toString(), new Object[]{tc.getSccId()});


            StringBuilder jxsql = new StringBuilder();
            Enroll e = (Enroll) baseBeanService.getBeanByHqlAndParams("from Enroll where enrollID=?", new Object[]{enrollID});
            jxsql.append("select count(*) from dt_order_bill_add a, dtCashierBills b, Dtgoodsbills c,dt_productpackaging p");
            jxsql.append(" where a.oa_bill_id = b.cashierbillsid");
            jxsql.append(" and b.cashierbillsid = c.cashierbillsid and p.ppid = c.ppid and b.fkstatus not in('01','13','18','15')");
            jxsql.append(" and c.typeid=? and a.oa_sccid = ? and p.categoryname=? and p.companyID = ? and b.status!=?");

            int count = 0;

            if(e!=null) {
                count = baseBeanService.getConutByBySqlAndParams(jxsql.toString(), new Object[]{"学员报名", tc.getSccId(), e.getLicenceType(), e.getCompanyID(),"99"});
            }
            MarKeting marKeting = (MarKeting) baseBeanService.getBeanByHqlAndParams("from MarKeting where userSccId =?", new Object[]{tc.getSccId()});
            if (marKeting == null && Float.parseFloat(cusList.get(0)[5].toString()) > 0f) {
                t3 = e != null;
            }
/*			else if(marKeting!=null&&marKeting.getMkuserID().equals("15810799888")&&tc.getCusType().equals("7")){
                t3 = false;
			}*/
            else {
                if (cusList.size() > 1) {
                    float t1 = Float.parseFloat(cusList.get(0)[5].toString());
                    if (t1 != 0f) {
                        float t2 = Float.parseFloat(cusList.get(1)[5].toString());
                        if (t2 == 0f && t1 != 0.5f) {
                            t3 = false;
                        } else if (t1 == 7f && t2 == 6f) {
                            t3 = false;
                        } else if (t1 <= t2) {
                            t3 = false;
                        } else if (t2 == 0.5f && t1 != 1f) {
                            t3 = false;
                        }
                    } else {
                        t3 = false;
                    }
                } else if (cusList.size() == 1) {
                    float t1 = Float.parseFloat(cusList.get(0)[5].toString());
                    if (t1 != 0f) {
                        t3 = false;
                    }
                } else {
                    t3 = false;
                }
            }


            // logger.error("" + "学员报名 getType:" + pp.getType() + "==count==" + count);
            if (!t3) {
                tips = "您的账号有问题，请联系工作人员!";
            } else if ("学员报名".equals(pp.getType()) && count > 0) {
                tips = "同一车型不可重复报名";
            }
        }


        map.put("result", tips);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";


    }

    /**
     * 查询公司产品（微店所属公司的产品） 搜索产品：中联园区搜索
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getShopProducts() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String hqlorg = "from COrganization where organizationID=?";
        COrganization org = (COrganization) baseBeanService
                .getBeanByHqlAndParams(hqlorg, new Object[]{organizationID});
        if (org != null && org.getOrganizationName() != null
                && !"".equals(org.getOrganizationName())) {
            SessionWrap.getInstance().setShopName(session,
                    org.getOrganizationName());// 把店铺名称存入session
        }
        // 公司产品
        List<Object> parms = new ArrayList<Object>();
        String sql = "select  pp.ppid,pp.goodsname,pp.image,pc.price,co.companyname,pp.goodsID,trim(pp.virtual),co.companyID "
                + " from dt_productpackaging pp, dt_productpricecategory pc ,dtcompany co"
                + " where pp.ppid=pc.ppid and co.companyid=pp.companyID and pc.category='零售价' and  pp.showweixin='01' and pp.ppcestuer='2' and pp.pptype='1'";
        if (!"".equals(search) && "search".equals(search)
                && !productDesign.getGoodsName().equals("")) {// 按产品名称搜索
            sql += " and pp.goodsname like ?";
            parms.add("%" + productDesign.getGoodsName() + "%");
        }
        if (productDesign != null && productDesign.getWeiDianType() != null
                && !"".equals(productDesign.getWeiDianType())) {
            sql += " and pp.weiDianType=?";
            parms.add(productDesign.getWeiDianType());
        }
        sql += " and pp.companyid=?";
        parms.add(comId);

        String sql1 = "select goodsid, sum(invenQuantity) nums from dt_inv_invt where companyID = ? group by goodsid";
        sql = "select p.*,vv.nums from (" + sql + ") p left join (" + sql1
                + ") vv on vv.goodsid = p.goodsid";
        parms.add(comId);
        productList = baseBeanService.getListBeanBySqlAndParams(sql,
                parms.toArray());
        // 商品分类
        proType = codeService.getCCodeListByPID(comId,
                "scode20150601pqmwffduns0000000002");
        // 微店信息
        String sql2 = "select co.organizationid, co.organizationname,sh.logo,co.companyID from dtcorganization co,T_ESHOP_EXTINFO sh  "
                + " where co.organizationid=sh.organizationid and co.organizationID=?";
        beans = baseBeanService.getListBeanBySqlAndParams(sql2,
                new Object[]{organizationID});

        return "shopproducts";
    }

    /**
     * 根据organizationID查询微店详情
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getShopDetail() {
        String sql = "select sh.intro,sh.logo,sh.address,sh.regdate,sh.owner,sh.telephone,sh.star,"
                + "(select st.num from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as num,"
                + "(select st.imagepath from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as imagepath "
                + "from T_ESHOP_EXTINFO sh " + "where sh.organizationID=? ";
        beans = baseBeanService.getListBeanBySqlAndParams(sql,
                new Object[]{organizationID});
        String orghql = "from COrganization where organizationID=?";
        corganization = (COrganization) baseBeanService.getBeanByHqlAndParams(
                orghql, new Object[]{organizationID});
        return "ShopDetail";
    }

    /**
     * 根据txt URL 获取内容
     *
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
            logger.error("操作异常", e);
            return "";
        }
    }

    /**
     * 根据产品id查询产品详情 所属店铺 负责人 产品没有店铺时要选择店铺
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String doodsDetail() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("url");
        ActionContext ctx = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        if (tc != null) {

            earthIndexService.addBrowseHistory(tc.getSccId(), "购物", ppid);
        }
        StringBuilder url = new StringBuilder();
        url.append(request.getRequestURL() + "?ppid=");
        url.append(request.getParameter("ppid") + "&organizationID=");
        url.append(request.getParameter("organizationID") + "&goodsid=");
        url.append(request.getParameter("goodsid") + "&companyId=");
        url.append(request.getParameter("companyId") + "&ccompanyId=" + request.getParameter("ccompanyId"));
        session.setAttribute("url", url.toString());
        String ppids = request.getParameter("ppids");
        request.setAttribute("ppids", ppids);
        if (ppids != null) {
            request.setAttribute("ppids", ppids);
            String hqlpro = " from ProductPackaging where ppid = ?";
            ProductPackaging proing = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hqlpro, new Object[]{ppids});
            String names = proing.getGoodsName();
            request.setAttribute("goodsldname", names);
        }
        // 产品明细
        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();
        String[] params = {companyId, ppid, currentTime, currentTime};
        String[] params1 = {companyId, ppid};
        if ("1".equals(pricetype)) {//批发商城 进入

            sql = VOtool.getShowPriceSql(1, 2);//批发价
            objbeanns = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params1);
            if (objbeanns == null || objbeanns.size() == 0) {
                pricetype = "0";
            } else {
                Object[] obj1 = (Object[]) objbeanns.get(0);
                request.setAttribute("product", obj1);
                request.setAttribute("priceType", "pf");
            }
        }

        if (!"1".equals(pricetype)) {
            sql = VOtool.getShowPriceSql(1, 5);//特价(活动)相关

            //价格类别[cx:促销价,tj:特价,vip:VIP价,yj:原价(零售价)]
            objbeanns = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params);
            if (objbeanns != null && objbeanns.size() > 0) {
                Object[] obj1 = (Object[]) objbeanns.get(0);
                request.setAttribute("product", obj1);
                request.setAttribute("priceType", "tj");

            } else {
                sql = VOtool.getShowPriceSql(1, 4);//促销价(活动)相关
                objbeanns = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params);
                if (objbeanns != null && objbeanns.size() > 0) {
                    Object[] obj1 = (Object[]) objbeanns.get(0);
                    request.setAttribute("product", obj1);
                    request.setAttribute("priceType", "cx");

                } else {
                    sql = VOtool.getShowPriceSql(1, 3);//VIP价相关
                    objbeanns = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params1);
                    if (objbeanns != null && objbeanns.size() > 0) {
                        Object[] obj1 = (Object[]) objbeanns.get(0);
                        request.setAttribute("product", obj1);
                        request.setAttribute("priceType", "vip");
                    } else {
                        sql = VOtool.getShowPriceSql(1, 1);//零售价相关
                        objbeanns = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params1);
                        Object[] obj1 = (Object[]) objbeanns.get(0);
                        request.setAttribute("product", obj1);
                        request.setAttribute("priceType", "yj");
                    }

                }

            }

        }

        sql = VOtool.getShowPriceSql(1, 1);//零售价相关
        List<Object> objbeann = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params1);
        Object[] obj1 = (Object[]) objbeann.get(0);
        request.setAttribute("productRetail", obj1);

        //主图与副图信息
        String hqlt = "from AttriProduction where type = ? and goodsid = ? and sort!=? order by sort";
        List<BaseBean> attrlist = baseBeanService.getListBeanByHqlAndParams(hqlt, new Object[]{"2", obj1[4], 1});
        request.setAttribute("tplist", attrlist);

        //公司信息
        Company cpy = (Company) baseBeanService.getBeanByHqlAndParams(
                "from Company where companyID=?", new Object[]{obj1[5]});
        indus = cpy.getCompanyName();
        comId = cpy.getCompanyID();
        List<Object> companyInfo = new ArrayList<Object>();
        List<Object> templist = new ArrayList<Object>();
        //公司logo
        sql.delete(0, sql.length());
        sql.append("select cc.logopath from dtcontactcompany cc, dt_ccom_com com");
        sql.append(" where cc.ccompanyid = com.ccompany_id and com.compnay_id =?");
        companyInfo = baseBeanService.getListBeanBySqlAndParams
                (sql.toString(), new Object[]{comId});
        //总产品数
        sql.delete(0, sql.length());
        sql.append("select count(p.ppid) from DT_PRO_SETUP ps, dt_ProductPackaging p");
        sql.append(" where ps.com_id =? and p.ppid = ps.ppid and p.showweixin='01' and ps.fcom_id is null");
        Object obj = null;

        templist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{comId});
        companyInfo.add(templist.get(0) != null ? templist.get(0) : null);
        //促销产品信息
        sql.delete(0, sql.length());
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p");
        sql.append(" where p.ppid = ps.ppid and ps.fcom_id is null and p.showweixin = ? and ps.ppid in");
        sql.append(" (select pt.ptppid from dt_promotion pt");
        sql.append(" where pt.companyid = ? and pt.ppid = ?)");
        List<BaseBean> ptlist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{"01", comId, ppid});
        request.setAttribute("ptlist", ptlist);
        //公司粉丝数
        sql.delete(0, sql.length());
        sql.append("select count(jf.jfid) from dtjoinfans jf, t_eshop_cuscom tec");
        sql.append(" where tec.companyid =? and jf.zaccount = tec.account");

        templist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{comId});
        companyInfo.add(templist.get(0) != null ? templist.get(0) : null);
        request.setAttribute("companyInfo", companyInfo);
        //评论个数
        sql.delete(0, sql.length());
        sql.append("select count(*) from DTComments where ppID = ?");
        int qp = baseBeanService.getConutByBySqlAndParams(sql.toString(), new Object[]{ppid});
        request.setAttribute("qp", qp);
        //评论中的一条内容
        sql.delete(0, sql.length());
        sql.append("select s.staffId,s.headimage,s.staffname,c.comments,c.comdate,c.anonymous");
        sql.append(" from dtComments c,dtCashierBills b,dt_hr_Staff s");
        sql.append(" where c.ppID = ? and b.cashierBillsID = c.cashierBillsID");
        sql.append(" and b.contactUserID = s.staffID");
        sql.append(" and c.commeID=(select max(st.commeID) from dtComments st where st.ppID = ?)");
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{ppid, ppid});
        if (list.size() != 0) {
            obj = list.get(0);
        }
        request.setAttribute("comobj", obj);
        //查询城市
        String hql = "from ContactCompany c where c.ccompanyID in (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?)";
        ContactCompany cc = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyId});
        List<SDistrict> distinctlistSaved = null;
        if (cc.getAddress() != null) {
            distinctlistSaved = regionService.getDistrictListByID(cc.getAddress());
            Collections.reverse(distinctlistSaved);
        }
        request.setAttribute("city", (distinctlistSaved != null && distinctlistSaved.get(1) != null) ? distinctlistSaved.get(1).getDistrictName() : "未设置");

        //颜色尺码
        String hqlattr = "from AttriProduction where type = ? and goodsid = ?";
        listsize = baseBeanService.getListBeanByHqlAndParams(hqlattr, new Object[]{"0", goodsid});
        listcolor = baseBeanService.getListBeanByHqlAndParams(hqlattr, new Object[]{"1", goodsid});
        request.setAttribute("size", listsize.size() == 0 ? null : ((AttriProduction) listsize.get(0)).getAttriname());
        request.setAttribute("color", listcolor.size() == 0 ? null : ((AttriProduction) listcolor.get(0)).getAttriname());
        //查询
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        if (cus != null) {
            String sqlcart = "select count(s.cspId) from DT_CartShopPromotion s left join DT_cart c on s.cartId = c.cart_Id where c.pid = ? and c.staff_Id = ?";
            int cxpcart = baseBeanService.getConutByBySqlAndParams(sqlcart, new Object[]{ppid, cus.getStaffid()});
            request.setAttribute("cxpcart", cxpcart);

            //是否收藏过

            String iscollect = goldOrderService.isCollect(ppid, pricetype, cus.getStaffid());
            request.setAttribute("iscollect", iscollect);


        }

        //商家客服信息
        StringBuffer consql = new StringBuffer();
        consql.append("select cus.sccid,tc.contactway,tc.contactname");
        consql.append(" from dt_hr_staff_contact tc,t_eshop_cuscom cus");
        consql.append(" where tc.contactway = cus.account");
        consql.append(" and tc.contactType = ? and tc.companyid = ?");
        consql.append(" union");
        consql.append(" select cus.sccid, cus.account, rf.staffname");
        consql.append(" from t_eshop_cuscom cus, dt_hr_staff rf");
        consql.append(" where rf.staffid = cus.staffid and cus.companyid = ?");

        List<Object> object = baseBeanService.getListBeanBySqlAndParams(
                "select e.codeid from dtCCode e where e.codevalue = ? and e.companyid = ?"
                , new Object[]{"短信提醒", "company201009046vxdygzy4w0000000025"});

        List<Object[]> conlist = baseBeanService.getListBeanBySqlAndParams(
                consql.toString()
                , new Object[]{object != null && object.size() > 0 ? object.get(0) : "",
                        comId, comId}
        );
        request.setAttribute("contactInfo", conlist);
        SetSubsidize setSubsidize = (SetSubsidize) setSubsidizeService.setSubsidizeByTypeGetcid(this.getCompanyId());
        if (setSubsidize != null) {
            request.setAttribute("setsubsidize", setSubsidize);
            request.setAttribute("ssIsnull", false);
        } else {
            request.setAttribute("ssIsnull", true);
        }

        return "doodsDetail";
    }


    /**
     * 获取详情
     *
     * @return
     */
    public String ajaxFindProDetail() {

        String hqlfun = "from GoodFunction where goodsid = ? order by orders";
        functionlist = baseBeanService.getListBeanByHqlAndParams(hqlfun,
                new Object[]{goodsid});
        maplist = new HashMap<String, String>();
        // 获取每个功能信息的具体内容
        for (int i = 0; i < functionlist.size(); i++) {
            GoodFunction goodFun = (GoodFunction) functionlist.get(i);
            maplist.put(goodFun.getGfid(), getContentFromFile(goodFun.getUrl()));

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("functionlist", functionlist);
        map.put("maplist", maplist);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 获取评论
     *
     * @return
     */
    public String findComments() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String sql = "select s.staffId,s.headimage,s.staffname,c.anonymous,c.evaluation,c.comments,c.image1,c.image2,c.image3,c.comdate,c.commeID from dtComments c,dtCashierBills b,dt_hr_Staff s where c.ppID = ? and b.cashierBillsID = c.cashierBillsID and b.contactUserID = s.staffID order by c.comdate desc";
        pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 5, sql, "select count(*) from (" + sql + ")", new Object[]{ppid});

        //统计每一个好评 中评 差评 个数
        int hp = 0, zp = 0, cp = 0, tp = 0;
        String sql1 = "select count(*) from DTComments where ppID = ? and evaluation = ?";

        String sql2 = "select count(*) from DTComments where ppID = ? and (image1 is not null or image2 is not null or image3 is not null)";
        hp = baseBeanService.getConutByBySqlAndParams(sql1, new Object[]{ppid, "好评"});
        zp = baseBeanService.getConutByBySqlAndParams(sql1, new Object[]{ppid, "中评"});
        cp = baseBeanService.getConutByBySqlAndParams(sql1, new Object[]{ppid, "差评"});
        tp = baseBeanService.getConutByBySqlAndParams(sql2, new Object[]{ppid});
        request.setAttribute("hp", hp);
        request.setAttribute("zp", zp);
        request.setAttribute("cp", cp);
        request.setAttribute("tp", tp);
        request.setAttribute("qp", hp + zp + cp);
        return "comment";

    }

    /**
     * 点击每一个评价类型ajax获取相应的评价
     *
     * @return
     */
    public String ajaxfindComBytype() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String evaluation = request.getParameter("evaluation");
        String sql = "select s.staffId,s.headimage,s.staffname,c.anonymous,c.evaluation,c.comments,c.image1,c.image2,c.image3,to_char(c.comdate,'yyyy-mm-dd hh24:mi:ss'),c.commeID  from dtComments c,dtCashierBills b,dt_hr_Staff s where c.ppID = ? and c.evaluation like ? and b.cashierBillsID = c.cashierBillsID and b.contactUserID = s.staffID";

        if (evaluation != null && evaluation.equals("全部评论")) {
            evaluation = "";

        }
        if (evaluation != null && evaluation.equals("有图")) {
            evaluation = "";
            sql += " and (c.image1 is not null or c.image2 is not null or c.image3 is not null)";
        }
        sql += " order by c.comdate desc";
        pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 5, sql, "select count(*) from (" + sql + ")", new Object[]{ppid, "%" + evaluation + "%"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    // 查询微店所属公司
    public String getComId(String org) {
        String hql = "from COrganization where organizationID=?";
        COrganization organization = (COrganization) baseBeanService
                .getBeanByHqlAndParams(hql, new Object[]{org});
        if (organization.getOrganizationPID().equals(
                organization.getCompanyID())) {
            return organization.getCompanyID();
        } else {
            return getComId(organization.getOrganizationPID());
        }
    }

    // 获取访问这IP
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    //urlAll httpArg

    /**
     * @param :请求接口
     * @param :参数   · * @return 返回结果
     */
    public String toeditOrganization() {
        String httpArg = "ip=" + getIpAddr(ServletActionContext.getRequest());
        httpArg = httpArg.equals("ip=0:0:0:0:0:0:0:1") ? "ip=140.205.192.121"
                : httpArg;
        String httpUrl;
        httpUrl = "http://apis.baidu.com/apistore/iplookupservice/iplookup";
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",
                    "e260519636034660fbd346c7e94210dd");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();

        } catch (Exception e) {
            logger.error("操作异常", e);
        }

        return Action.SUCCESS;
    }

    /**
     * 新产品购买（立即购买）
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String Shopping() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ccompanyId = request.getParameter("ccompanyId");
        //驾校报名的参数 名字 手机 身份证  地址
        String noviceName = request.getParameter("noviceName");
        String novicePhone = request.getParameter("novicePhone");
        String noviceCode = request.getParameter("noviceCode");
        String noviceAddress = request.getParameter("noviceAddress");


        String platfromid = request.getParameter("platfromid");
        String platfromAccount = request.getParameter("platfromAccount");
        String platfromConpanyName = request.getParameter("platfromConpanyName");
        //xgb
        String carid = request.getParameter("carID");
        //    logger.error("carid:" + carid);

        //活动价id
        String activityid = request.getParameter("activityid");
        //价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
        String priceType = (request.getParameter("priceType") == null || request.getParameter("priceType").equals("")) ? "0" : request.getParameter("priceType");

        List<BaseBean> operator = new ArrayList<BaseBean>();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        session.setAttribute("ppid", ppid);
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        TEshopCusCom scc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);


        String yxsccid = request.getParameter("yxsccid");
        if (yxsccid != null && !yxsccid.equals("")) {
            Map<String, Object> maps = mappointmentService.getTcTr(yxsccid);
            scc = (TEshopCusCom) maps.get("scc");
            cus = (TEshopCustomer) maps.get("cus");
        }


        // 0:人员id 1:公司id 2:状态 3:上级id 4:公司名称 5:会员类别 6:会员id 7:会员账号
        CashierBills cb = new CashierBills();

        ProductPackaging ppk = new ProductPackaging();
        ppk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppid=?", new String[]{ppid});

        cb.setCompanyID(ppk.getCompanyID());
        cb.setJournalNum(serverService.getBillID(cb.getCompanyID()));

        JSONObject obj1 = new JSONObject();

        if ("公司会员".equals(ppk.getType())) {
            if (scc != null) {

                if (("7".equals(ppk.getModel()) && "0".equals(ppk.getPrice()))) {

                    if (scc.getAccount().length() > 11) {
                        if (cdl != null && cdl.getManagertel() != null && !cdl.getManagertel().equals("")) {
                            goldOrderService.bindAccount(scc, cus, cdl.getManagertel());
                        }

                    }
                    cb.setFkStatus("04");
                    if (scc != null && (cdl.getManagertel() == null || "".equals(cdl.getManagertel())) && "1".equals(scc.getState())) {
                        //自己开通公司
                        wfjserv.registerCompanyInfo(ppk.getModel(), scc, company, cdl);
                    } else if (scc != null && scc.getAccount().equals(cdl.getManagertel())) {
                        //登陆后为业务员给别人注册公司 登陆人和负责人是同一个人
                        logger.info("调试信息");
                        if ("2".equals(scc.getState())) {
//                            obj1.accumulate("result", "2");
//                            result = obj1.toString();
//                            return "success";

                        }
                        wfjserv.registerCompanyInfo(ppk.getModel(), tc, company, cdl);

                    } else {
                        //登陆人和负责人不同
                        //判断账号无创建账号
                        if (staff == null) {
                            staff = new Staff();
                            staff.setStaffName(cdl.getCompanyManager());
                        } else {
                            staff.setStaffName(cdl.getCompanyManager());
                        }

                        String staffID = wfjserv.zhuCe(scc, scc != null ? scc.getSccId() : "", cdl.getManagertel(), "123456", staff);
                        List<BaseBean> tcc = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where staffid = ? order by cusType desc", new Object[]{staffID});
                        scc = (TEshopCusCom) tcc.get(0);
                        if ("2".equals(scc.getState())) {
//                            obj1.accumulate("result", "2");
//                            result = obj1.toString();
//                            return "success";

                        }
                        wfjserv.registerCompanyInfo(ppk.getModel(), scc, company, cdl);
                    }


                } else if (Float.parseFloat(ppk.getPrice()) > 0 && "1".equals(scc.getState())) {
                    //   cb.setFkStatus("04");
                    if (company != null && company.getGdID() != null && !company.getGdID().equals("")) {
                        //周边经济认领需要付款的记录入驻信息
                        goldOrderService.tempCompany(company, cdl, cb.getJournalNum());
                    }
                }
            } else {
                //没有登陆
                if (cdl.getManagertel() != null && !cdl.getManagertel().equals("")) {
                    //负责人不为空
                    List<BaseBean> tccc = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where staffid = ? order by cusType desc", new Object[]{cdl.getBusiManagerID()});
                    TEshopCusCom sccc = null;
                    if (tccc != null && tccc.size() != 0) {
                        sccc = (TEshopCusCom) tccc.get(0);
                    }
                    if (staff == null) {
                        staff = new Staff();
                        staff.setStaffName(cdl.getCompanyManager());
                    } else {
                        staff.setStaffName(cdl.getCompanyManager());
                    }
                    String staffID = wfjserv.zhuCe(sccc, sccc != null ? sccc.getSccId() : "", cdl.getManagertel(), "123456", staff);
                    List<BaseBean> tcc = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where staffid = ? order by cusType desc", new Object[]{staffID});
                    scc = (TEshopCusCom) tcc.get(0);
                    ContactCompany contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where gdID = ?", new Object[]{company.getGdID()});
                    if (contactCompany != null) {
                        obj1.accumulate("result", "1");
                        result = obj1.toString();
                        return "success";
                    } else if ("2".equals(scc.getState())) {
//                        obj1.accumulate("result", "2");
//                        result = obj1.toString();
//                        return "success";
                    }
                    cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid = ? ", new Object[]{staffID});

                    if (("7".equals(ppk.getModel()) && "0".equals(ppk.getPrice()) && "1".equals(scc.getState()))) {
                        cb.setFkStatus("04");
                        wfjserv.registerCompanyInfo(ppk.getModel(), scc, company, cdl);
                    } else {
                        //周边经济认领需要付款的记录入驻信息
                        goldOrderService.tempCompany(company, cdl, cb.getJournalNum());
                    }
                }
            }
        }

        if (cus == null) {
            String url = request.getHeader("Referer");
            session.setAttribute("url", url);
            obj1.accumulate("login", "login");
            result = obj1.toString();
            return "success";
        }

        //发货地址查询
        String queryAddressHQL = "from RefundAddress where " + "companyId=? and rtype=? and status=?";
        RefundAddress address = (RefundAddress) baseBeanService.getBeanByHqlAndParams(queryAddressHQL, new Object[]{ppk.getCompanyID(), 1, "00"});

        cb.setCashierBillsID(serverService.getServerID("CashierBills"));
        logger.info("调试信息");
        cb.setOrganizationID("organization20110425U539EJC3VF0000012237");
        cb.setCashierDate(new Date());
        cb.setStaffName("系统生成");
        cb.setInputName("系统生成");
        cb.setStatus("40");
        cb.setWfStatus2("00");
        if ("学员报名".equals(ppk.getType()) && Double.valueOf(indus) == 0) {
            cb.setFkStatus("00");
        }
//        else if ("金币计时".equals(ppk.getType()) && Double.valueOf(indus) != 0) {
//            cb.setFkStatus("04");
//        }

        else {
            if (!"04".equals(cb.getFkStatus())) {
                cb.setFkStatus("01");
            }

        }
        cb.setDepartmentName("客户");
        cb.setZctype("cg");
        // cb.setProjectName(sort);
        if (platfromid != null && platfromAccount != null && platfromConpanyName != null) {
            cb.setPlatfromid(platfromid);
            cb.setPlatfromAccount(platfromAccount);
            cb.setPlatfromConpanyName(platfromConpanyName);
        } else if (industy != null && company != null && company.getCompanyName() != null && company.getIndustryId() != null) {
            cb.setPlatfromAccount(industy);
            cb.setPlatfromConpanyName(company.getCompanyName());
            cb.setIndustryId(company.getIndustryId());
        }

        String hql = "from Staff d where d.staffID=? ";
        Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams(hql, new String[]{cus.getStaffid()});
        if(sf!=null&&(sf.getStaffName()==null||sf.getStaffName().equals(""))){
            if (noviceName != null || !noviceName.equals("")) {
                sf.setStaffName(noviceName);
                operator.add(sf);
            }
        }
        cb.setContactUserID(sf.getStaffID());
        if (noviceName == null || noviceName.equals("")) {
            cb.setCtUserName(sf.getStaffName());
        } else {
            cb.setCtUserName(noviceName);
            cb.setReference(novicePhone);
            cb.setStaffIdentityCard(noviceCode);
            cb.setReferrerAddress(noviceAddress);
        }

        cb.setProjectName(ppk.getGoodsName());
        cb.setPriceSub(morre);
        cb.setBillsType("项目收入预算单");
        cb.setWfStatus1("00");

        Company company1 = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{ppk.getCompanyID()});
        cb.setCompanyName(company1.getCompanyName());


//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            logger.error("操作异常", e);
//        }
        logger.info("调试信息");
        cb.setjNumOrder(cb.getJournalNum());
        cb.setStatusbill("04");
        cb.setCcompanyID(ccompanyId);
        if (!"".equals(enrollId) && enrollId != null && !"null".equals(enrollId)) {
            Enroll enroll = (Enroll) baseBeanService.getBeanByHqlAndParams("from Enroll e where e.enrollID = ? ", new Object[]{enrollId});
            enroll.setCashierBillsID(cb.getCashierBillsID());
            operator.add(enroll);
        }

        PayCashierBill payCashierBill = new PayCashierBill();
        payCashierBill.setPcbID(serverService.getServerID("pcbid"));
        payCashierBill.setOriJournalNum(cb.getJournalNum());
        payCashierBill.setPayJournalNum(cb.getJournalNum());


        String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";

        DepotManage wh = (DepotManage) baseBeanService.getBeanByHqlAndParams(hqlinvt, new Object[]{ppk.getCompanyID(), "销售库"});

        // 订单地址关联
        DtOrderBillAdd dl = new DtOrderBillAdd();
        dl.setOaId(serverService.getServerID("DtOrderBillAdd"));
        dl.setOaComId(cb.getCompanyID());
        dl.setOaSccId(scc.getSccId());

        if(noviceAddress!=null&&noviceAddress!=""){
            dl.setReceivename(noviceName);
            dl.setReceivetel(novicePhone);
            dl.setReceiveaddress(noviceAddress);
            dl.setShDate(new Date());
        }else{
            //收货信息
            StaffAddress sa = (StaffAddress) baseBeanService.getBeanByHqlAndParams("from StaffAddress where addressID = ?", new Object[]{staffAddress != null ? staffAddress.getAddressID() : ""});
            if (sa != null) {
                dl.setReceivename(sa.getConsignee());
                dl.setReceivecode(sa.getPostCode());
                dl.setReceivetel(sa.getPhone());
                dl.setReceiveaddress(sa.getArea() + sa.getAddressDetailed());
                dl.setShDate(new Date());
            } else {
                dl.setReceivename(sf.getStaffName());
                dl.setReceivetel(sf.getReference());
            }
        }

        //发货信息
        if (address != null) {
            dl.setSendname(address.getRname());
            dl.setSendtel(address.getRphone());
            dl.setSendcode(address.getRpostcode());
            dl.setSendaddress(address.getRarea() + address.getRstreet());
            dl.setFhDate(new Date());
        }
        dl.setOaBillId(cb.getCashierBillsID());
        dl.setOaBillJounum(cb.getJournalNum());
        dl.setOaGysId(ppk.getCompanyID());
        dl.setXdDate(new Date());
        dl.setFkDate(new Date());
        String p = priceType == "" ? "0" : priceType;
        StringBuilder sql = VOtool.getShowPriceSql(3, (Integer.valueOf(priceType == null || "".equals(priceType) ? "0" : priceType)) + 1);
        Object[] goodparm = new Object[]{ppid, ppk.getCompanyID(), "3".equals(priceType) ? activityid : "4".equals(priceType) ? activityid : 1};

        Object pro_su = baseBeanService.getObjectBySqlAndParams(sql.toString(), goodparm);

        GoodsBills gbs = new GoodsBills();
        gbs.setCashierBillsID(cb.getCashierBillsID());
        gbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
        gbs.setCompanyID(ppk.getCompanyID());
        gbs.setTypeID(ppk.getType());
        gbs.setDepotID(wh.getDepotID());
        gbs.setDepotName(wh.getDepotName());
        gbs.setStartDate(new Date());
        gbs.setMoney(morre);
        gbs.setQuantity(count);
        gbs.setRealMoney(morre);
        gbs.setGoodsID(ppk.getGoodsID());
        gbs.setGoodsName(sort);
        gbs.setStandard(request.getParameter("standard"));
        gbs.setPpID(ppid);
        gbs.setBoxStandard(ppk.getModel());
        gbs.setEntryTime(new Date());
        gbs.setGoodsNum(ppk.getProductCode());
        gbs.setPricetype(priceType);//价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
        gbs.setActivityID(activityid);
        if (recordId != null && recordId.length() > 0) {
            gbs.setPrice("0");
            gbs.setCostmoney("0");
        } else {
            gbs.setPrice(indus);
            gbs.setCostmoney(pro_su.toString());
        }

        //代理资格的购买判断
        if (ppid.equals("p20170220ZVZR76B88M0000000018") || ppid.equals("p20170220ZVZR76B88M0000000019") || ppid.equals("p20170220ZVZR76B88M0000000020")) {
            ProductPackaging productp = new ProductPackaging();
            String hqlt = "from ProductPackaging where ppid = ? ";
            productp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hqlt, new Object[]{ppids});
            cb.setPlatfromid(ppid);
            cb.setProjectName(ppk.getGoodsName() + "-" + productp.getGoodsName());
            gbs.setGoodsName(ppk.getGoodsName() + "-" + productp.getGoodsName());
            gbs.setAreappid(ppids);
        }
        if (ppid.equals("p20170220ZVZR76B88M0000000016") || ppid.equals("p20170220ZVZR76B88M0000000017")) {
            ProductPackaging productp = new ProductPackaging();
            String hqlt = "from ProductPackaging where ppid = ? ";
            productp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hqlt, new Object[]{ppid});
            cb.setPlatfromid(ppid);
        }
        //gbs.setPremiums("0");//是否是奖品  0或null:否  1:是
        //若是公司会员生成公司相关信息


        //购买粉丝
        if ("个人会员".equals(ppk.getType())) {

            if ("8".equals(ppk.getModel())) {
                cb.setFkStatus("04");
                obj1.accumulate("companyName", addjoinFans(ccompanyId, scc));
            } else if (("7".equals(ppk.getModel()) && "0".equals(ppk.getPrice())) && "1".equals(scc.getState())) {
                cb.setFkStatus("04");
                wfjserv.registerCompanyInfo("0", scc, company, cdl);
            }
        }

        if ("0".equals(ppk.getPrice()) && !"公司会员".equals(ppk.getType()) && !"个人会员".equals(ppk.getType())) {

            cb.setFkStatus("00");
        }


        //购买税务
        if ("个人会员".equals(ppk.getType()) && ("1".equals(ppk.getModel()) || "0.5".equals(ppk.getModel()))) {
            //公司
            String hqlt = "from TEshopCusCom t where t.state = ? and t.cusType = ? and t.companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)";
            TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqlt, new Object[]{"2", "0", ccompanyId});
            scc.setCusType(ppk.getModel());
            if ("1".equals(ppk.getModel())) {
                //购买地税
                String hqlgs = "from TEshopCusCom t where t.Superioragent = ?";
                TEshopCusCom guos = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqlgs, new Object[]{tcc.getAccount()});
                scc.setSuperioragent(guos.getAccount());
            } else {
                //国税
                scc.setSuperioragent(tcc.getAccount());
            }
        }
        //第一次买会员产品补充推荐人信息（如果没有推荐人的话），没填写的话默认给平台
        if (("个人会员".equals(ppk.getType()) || "公司会员".equals(ppk.getType())) && "7".equals(scc.getCusType())) {
            String mkuserID = request.getParameter("mkuserID");
            //验证填写推荐人号码是否存在
            if (mkuserID != null && !mkuserID.equals("")) {
                TEshopCusCom tjc = (TEshopCusCom) baseBeanService
                        .getBeanByHqlAndParams(
                                "from TEshopCusCom where account = ? and logOff=0",
                                new Object[]{mkuserID});
                if (tjc == null) {
                    obj1.accumulate("ddid", "fail");
                    result = obj1.toString();
                    return "success";
                }
            }
            MarKeting mk = (MarKeting) baseBeanService.getBeanByHqlAndParams("from MarKeting where userID = ?", new Object[]{scc.getAccount()});
            if (mk == null) {
                MarKeting mkn = new MarKeting();
                mkn.setUserID(scc.getAccount());//被邀请人
                mkn.setMkdate(new Date());
                mkn.setMkID(serverService.getServerID("MarKeting"));
                if (mkuserID != null && !mkuserID.equals("")) {
                    mkn.setMkuserID(mkuserID);//邀请人
                } else {
                    mkn.setMkuserID("15810799888");
                }
                operator.add(mkn);
            }
        }
        operator.add(cb);
        operator.add(gbs);
        operator.add(dl);
        operator.add(scc);
        operator.add(payCashierBill);
        if (ptppid != null && ptppid.length() > 0) {
            //List<BaseBean> list=promotionalOrder(ppid.trim(),ptppid.trim(),cb,sa);
            //operator.addAll(list);
            String[] ppidlists = ptppid.split(",");
            String[] ptstandards = new String[3];
            if (ptstandard != null && !ptstandard.equals(",,,,")) {
                ptstandards = ptstandard.split(",,");
            }
            StringBuffer csql = new StringBuffer();
            List<Object> params = new ArrayList<Object>();
            List<Object> tempp = new ArrayList<Object>();
            csql.append("select pp.companyid,pp.goodsname,ps.ef_price from dt_productpackaging pp,dt_pro_setup ps");
            csql.append(" where ps.ppid=pp.ppid and pp.ppid in(");
            StringBuffer temp = new StringBuffer();
            temp.append(" order by case pp.ppid ");
            PromotionAssociation pa = null;
            for (int i = 0; i < ppidlists.length; i++) {
                if (i == ppidlists.length - 1) {
                    csql.append("?");
                } else {
                    csql.append("?,");
                }
                temp.append("when ? then '" + i + "'");
                params.add(ppidlists[i]);
                tempp.add(ppidlists[i]);
            }
            csql.append(")");
            temp.append(" else pp.ppid end");
            csql.append(temp);
            params.addAll(tempp);
            List<BaseBean> clist = new ArrayList<BaseBean>();
            clist = baseBeanService.getListBeanBySqlAndParams(csql.toString(), params.toArray(new Object[]{}));
            for (int i = 0; i < ppidlists.length; i++) {
                Object o = clist.get(i);
                Object[] objc = (Object[]) o;
                pa = new PromotionAssociation();
                pa.setPaId(serverService.getServerID("paId"));
                pa.setCashierBillsID(cb.getCashierBillsID());
                pa.setCompanyId(objc[0].toString());
                pa.setPpId(ppidlists[i]);
                pa.setCreatDate(new Date());
                pa.setPpName(objc[1].toString());
                pa.setPrice(objc[2].toString());
                pa.setStandard(ptstandards[i]);
                pa.setCount(1);
                operator.add(pa);
            }
        }
        //xgb判断是否是计时产品
        if (ppk.getType().indexOf("包月") != -1 || ppk.getType().indexOf("包年") != -1 || ppk.getType().indexOf("包天") != -1) {
            if (carid != null && !carid.equals("")) {
                logger.error("计算计时");
                carManageService.addTiming(carid, ppid, cb.getJournalNum(), "", "", null);
            }
        }

        //奖品生成订单，中奖记录订单号赋值
        if (recordId != null && recordId.length() > 0) {
            WinningRecordBean winningRecordBean = (WinningRecordBean) baseBeanService.getBeanByHqlAndParams(
                    "from WinningRecordBean where recordId = ?",
                    new Object[]{recordId}
            );
            cb.setFkStatus("00");
            winningRecordBean.setOrderNum(cb.getJournalNum());
            winningRecordBean.setIsDraws("1");
            operator.add(winningRecordBean);
        }



        baseBeanService.saveBeansListAndexecuteHqlsByParams(operator, null, null);

        obj1.accumulate("ddid", cb.getJournalNum());
        result = obj1.toString();
        return "success";
    }

    /**
     * 调用生成积分出入库单
     *
     * @param @ddis 单据id
     */
    public String diaoyongJifen() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String buyPatter = request.getParameter("buyPatter");//购买方式
        String book = request.getParameter("book");//预约

        JSONObject obj1 = new JSONObject();
        String journalNum = request.getParameter("ddid");
        String jfhql = null;
        BigDecimal score = BigDecimal.ZERO;
        String jg = "cg";
        String morre = "";
        try {
            String hql = "from CashierBills where journalNum = ?";
            CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{journalNum});
            morre = cash.getPriceSub();

            try {
                UnPayRecord unPayRecord = smSerivce.getUnPayRecord(journalNum);
                if (unPayRecord != null && unPayRecord.getRemainNum() != null && !unPayRecord.getRemainNum().equals("")) {
                 //   sccid = unPayRecord.getSccId();
                    morre = unPayRecord.getRemainNum();

                }
            }catch (Exception ff){
                logger.error("操作异常", ff);
            }



            DtOrderBillAdd oba = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{journalNum});
            String hqlpay = "from PayCashierBill where oriJournalNum = ?";
            PayCashierBill pc = (PayCashierBill) baseBeanService.getBeanByHqlAndParams(hqlpay, new Object[]{cash.getjNumOrder()});

            if (buyPatter != null && buyPatter.equals("05") && oba != null) {
                jfhql = "from BonusPoints where sccid=?";
                BonusPoints bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams(jfhql, new Object[]{oba.getOaSccId()});
                score = new BigDecimal(bp.getBonusPointScore());
            } else if (buyPatter != null && buyPatter.equals("07") && oba != null) {
                jfhql = "from WfjJifen where sccid=?";
                WfjJifen wj = (WfjJifen) baseBeanService.getBeanByHqlAndParams(jfhql, new Object[]{oba.getOaSccId()});
                score = new BigDecimal(wj.getWfjJifenScore());
            }
            if (score.compareTo(new BigDecimal(morre)) >= 0) {

                boolean bool = genCanYin(cash.getTrade_no(), pc.getPayJournalNum(), morre, "05", null);
                Boolean bo = false;
                if (bool == false) {
                    bo = goldOrderService.generateSpoints(cash.getTrade_no(), pc.getPayJournalNum(),morre, buyPatter);
                    try {
                        //收款单生成后复制订单和收款单到新表
                      goldOrderService.copyCash( pc.getPayJournalNum(), "j");
                    }catch (Exception e3){
                        logger.error("操作异常", e3);
                        logger.info("复制订单积分或者金币入库单错误");
                    }

                }
                String cashierBillsID = cash.getCashierBillsID();
                if (bool == false && bo == true) {

                    if ("book".equals(book)||"智能货柜".equals(cash.getGoodsName())) {
                        //把订单状态改程03已收货
                        goldOrderService.updateFkState(cashierBillsID);
                        //分金币
                        transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
                    }

                    //会员产品只能单独下订单
                    if (custype != null && !custype.equals("") && Float.parseFloat(custype) < 7 && cashierBillsID != null && !cashierBillsID.equals("")) {
                        //虚拟发货
                        transferService.virtual(cashierBillsID);
                    }
                    addPeriod(journalNum);//购买学时产品时增加学时
                }
            } else {
                jg = "shibai";
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
            jg = "shibai";
        }
        obj1.accumulate("chenggong", jg);
        result = obj1.toString();
        return "success";
    }

    /**
     * 订单加入积分支付
     */
    public String panduanJifen() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject obj1 = new JSONObject();
        String journalNum = request.getParameter("ddid");
        String hql = "from CashierBills where journalNum = ?";
        CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{journalNum});

        String hqlpay = "from PayCashierBill where oriJournalNum = ?";
        PayCashierBill pc = (PayCashierBill) baseBeanService.getBeanByHqlAndParams(hqlpay, new Object[]{cash.getjNumOrder()});
        DtOrderBillAdd bd = (DtOrderBillAdd) baseBeanService
                .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{cash.getJournalNum()});
        String hql2 = "from TEshopCusCom d where d.sccId=?";
        TEshopCusCom tsc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql2, new String[]{bd.getOaSccId()});

        BonusPoints bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams(" from BonusPoints where sccid = ?",
                new Object[]{tsc.getSccId()});
        if (bp == null || bp.getBonusPointScore() == null) {
            obj1.accumulate("panduan", "meiyou");
            result = obj1.toString();
            return "success";
        } else {
            if (Float.parseFloat(bp.getBonusPointScore()) - Float.parseFloat(cash.getPriceSub()) * 100 >= 0) {
                Boolean bo = goldOrderService.generateSpoints(cash.getTrade_no(), pc.getPayJournalNum(), cash.getPriceSub(), "05");
                String cashierBillsID = cash.getCashierBillsID();
                if (bo == true) {

                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(pc.getPayJournalNum(), "j");
                    }catch (Exception e3){
                        logger.error("操作异常", e3);
                        logger.info("复制订单积分或者金币入库单错误");
                    }


                    //会员产品只能单独下订单
                    if (custype != null && !custype.equals("") && Float.parseFloat(custype) < 7 && cashierBillsID != null && !cashierBillsID.equals("")) {
                        //虚拟发货
                        transferService.virtual(cashierBillsID);
                    }
                } else {
                    obj1.accumulate("panduan", "shibai");
                    result = obj1.toString();
                    return "success";
                }
            } else {
                obj1.accumulate("panduan", "bugou");
                result = obj1.toString();
                return "success";
            }
        }
        obj1.accumulate("panduan", "cg");
        result = obj1.toString();
        return "success";
    }


    /**
     * 促销品下订单
     *
     * @param ppid       主产品id
     * @param ptppid     促销品id
     * @param cb         主产品订单
     * @param companyid  促销品公司id
     * @param ptstandard 促销品规格
     */
    @SuppressWarnings("unchecked")
    public List<BaseBean> promotionalOrder(String ppid, String ptppid, CashierBills cb, String companyid, String ptstandard) {
        List<BaseBean> pmlist = new ArrayList<BaseBean>();
        pmlist = goldOrderService.promotionalOrderServece(ppid, ptppid, cb, companyid, ptstandard);
        return pmlist;
    }

    /**
     * 购买成功增加学时
     */
    public void addPeriod(String ddid) throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        Object periodNum = baseBeanService.getObjectBySqlAndParams("select gb.quantity from dtGoodsBills gb,dtCashierBills cb,dt_productpackaging pp where gb.cashierbillsid = cb.cashierbillsid and gb.ppid = pp.ppid and cb.journalnum = ? and pp.type= ?", new Object[]{ddid, "学时"});
        if (periodNum != null && !"".equals(periodNum)) {
            SubjectHour subjectHour = (SubjectHour) baseBeanService.getBeanByHqlAndParams("from SubjectHour s where s.staffId = ?", new Object[]{cus.getStaffid()});
            subjectHour.setHasTime(subjectHour.getHasTime() == null ? 0 + Integer.parseInt(periodNum.toString()) : subjectHour.getHasTime() + Integer.parseInt(periodNum.toString()));
            baseBeanService.saveOrUpdate(subjectHour);
        }
    }

    /**
     * 多个产品下订单
     *
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public String ShoppingMulti() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String noviceName = request.getParameter("noviceName");
        String novicePhone = request.getParameter("novicePhone");
        String noviceCode = request.getParameter("noviceCode");
        String noviceAddress = request.getParameter("noviceAddress");


        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        TEshopCusCom scc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (cus == null) {
            return "login";
        }

//		//查询当前账号的权限表以及人员表
//		String scchql = "from TEshopCusCom where account=? AND logOff=0";
//		TEshopCusCom scc = (TEshopCusCom) baseBeanService
//				.getBeanByHqlAndParams(scchql,
//						new Object[]{cus.getAccount()});

        String hqlstaff = "from Staff d where d.staffID=?";
        Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
                new String[]{cus.getStaffid()});

        //多个公司店铺
        String companyIds = request.getParameter("companyId");
        String leavemessages = request.getParameter("leavemessage");


        String[] companyIdArray = companyIds.split(","); //公司ID
        String[] ppidArray = pid.split("zz");
        String[] leavearry = leavemessages.split(","); //公司ID

        String sqlccom = "select cc.ccompany_Id,c.companyname from DT_ccom_com cc,dtCompany c where cc.compnay_id=c.companyId and c.companyId = ?";
        CashierBills cb = null;
        PayCashierBill pb = null;
        String payJournalNum = serverService.getBillID(companyIdArray[0]);

        List<BaseBean> operator = new ArrayList<BaseBean>();
        List<String> hqls = new ArrayList<String>();
        List<Object[]> parms = new ArrayList<Object[]>();

        StringBuffer sbsql = new StringBuffer("select cc.compnay_id, c.industrytype");
        sbsql.append(" from dt_ccom_com cc, dtcontactcompany c");
        sbsql.append(" where cc.ccompany_id = c.ccompanyid");
        List<Object> sbp = new ArrayList<Object>();
        if (companyIdArray.length > 0) {
            sbsql.append(" and (");
            for (int i = 0; i < companyIdArray.length; i++) {
                if (i > 0) {
                    sbsql.append(" or");
                }
                sbsql.append(" cc.compnay_id = ?");
                sbp.add(companyIdArray[i]);
            }
            sbsql.append(" )");
        }

        List<Object> bls = baseBeanService.getListBeanBySqlAndParams(sbsql.toString(), sbp.toArray());

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < companyIdArray.length; i++) {

            Object[] objcom = (Object[]) baseBeanService.getObjectBySqlAndParams(sqlccom, new Object[]{companyIdArray[i]});
            //每个公司生成一个订单
            cb = new CashierBills();
            cb.setCashierBillsID(serverService.getServerID("CashierBills"));
            cb.setOrganizationID("organization20110425U539EJC3VF0000012237");//部门为什么写死了
            cb.setCashierDate(new Date());
            cb.setStaffName("系统生成");
            cb.setInputName("系统生成");
            cb.setStatus("40");
            cb.setWfStatus2("00");
            cb.setFkStatus("01");
            cb.setDepartmentName("客户");
            cb.setZctype("cg");
            //cb.setProjectName(sort);//注意

            cb.setContactUserID(sf.getStaffID());

            if (noviceName == null || noviceName.equals("")) {

                cb.setCtUserName(sf.getStaffName());
            } else {
                cb.setCtUserName(noviceName);
                cb.setReference(novicePhone);
                cb.setStaffIdentityCard(noviceCode);
                cb.setReferrerAddress(noviceAddress);
            }


            cb.setBillsType("项目收入预算单");
            cb.setWfStatus1("00");
            cb.setJournalNum(serverService.getBillID(cb.getCompanyID()));
            cb.setjNumOrder(cb.getJournalNum());
            cb.setStatusbill("04");
            cb.setCcompanyID(objcom[0].toString());
            cb.setCompanyID(companyIdArray[i]);
            cb.setCompanyName(objcom[1].toString());
            cb.setPricetype((pricetype == null || pricetype == "") ? "0" : "1");


            //餐饮特殊处理记录包间以及服务员
            cb.setPrivateRoom(request.getParameter("privateRoom"));
            cb.setWaiter(request.getParameter("waiter"));
            cb.setRemark(leavearry[i]);//买家

            //声明支付订单关联表
            pb = new PayCashierBill();
            pb.setPcbID(serverService.getServerID("pcbid"));
            pb.setOriJournalNum(cb.getjNumOrder());
            if (companyIdArray.length == 1) {
                pb.setPayJournalNum(cb.getjNumOrder());
            } else {
                pb.setPayJournalNum(payJournalNum);
            }
            operator.add(pb);
            // 订单地址关联
            DtOrderBillAdd dl = new DtOrderBillAdd();
            dl.setOaId(serverService.getServerID("DtOrderBillAdd"));
            dl.setOaComId(cb.getCompanyID());
            dl.setOaSccId(scc.getSccId());
            //收货信息


            StaffAddress sa = (StaffAddress) baseBeanService.getBeanByHqlAndParams("from StaffAddress where addressID = ?", new Object[]{staffAddress.getAddressID()});
            if (sa != null) {
                dl.setReceivename(sa.getConsignee());
                dl.setReceivecode(sa.getPostCode());
                dl.setReceivetel(sa.getPhone());
                dl.setReceiveaddress(sa.getArea() + sa.getAddressDetailed());
            }
            //发货地址查询
            RefundAddress address = (RefundAddress) baseBeanService
                    .getBeanByHqlAndParams("from RefundAddress where " +
                                    "companyId=? and rtype=? and status=?",
                            new Object[]{companyIdArray[i], 1, "00"});


            if (address != null) {
                dl.setSendname(address.getRname());
                dl.setSendtel(address.getRphone());
                dl.setSendcode(address.getRpostcode());
                dl.setSendaddress(address.getRarea() + address.getRstreet());
            }
            if (sa == null) {
                dl.setReceivename(sf.getStaffName());
                dl.setReceivetel(sf.getReference());
                if (address != null) {
                    dl.setReceivecode(address.getRpostcode());
                    dl.setReceiveaddress(address.getRarea() + address.getRstreet());
                }

            }
            dl.setOaBillId(cb.getCashierBillsID());
            dl.setOaBillJounum(cb.getJournalNum());
            dl.setOaGysId(companyIdArray[i]);
            BigDecimal priceSub = BigDecimal.ZERO;

            String sqlp = "select p.ppid,p.goodsname,p.goodsid,p.type,p.companyid,p.productCode,p.model from dt_ProductPackaging p where p.companyid= ? and p.ppid= ?";

            String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";

            DepotManage wh = (DepotManage) baseBeanService.getBeanByHqlAndParams(hqlinvt, new Object[]{companyIdArray[i], "销售库"});
            String gname = "";
            for (int j = 0; j < ppidArray.length; j++) {
                String[] pidsarr = ppidArray[j].split("-");//pid-数量-stardar
                Object objs = baseBeanService.getObjectBySqlAndParams(sqlp,
                        new String[]{companyIdArray[i], pidsarr[0]});
                if (objs != null) {

                    Object[] obj = (Object[]) objs;

                    GoodsBills gbs = new GoodsBills();
                    gbs.setCashierBillsID(cb.getCashierBillsID());
                    gbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                    gbs.setTypeID(obj[3] != null ? obj[3].toString() : null);
                    gbs.setCompanyID(companyIdArray[i]);
                    gbs.setDepotID(wh != null ? wh.getDepotID() : null);//销售库
                    gbs.setDepotName(wh.getDepotName());
                    gbs.setStartDate(new Date());
                    gbs.setPrice(pidsarr[5]);//售价
                    gbs.setQuantity(pidsarr[1]);//数量前台传的
                    gbs.setMoney(new BigDecimal(gbs.getQuantity()).multiply(new BigDecimal(gbs.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());//金额
                    gbs.setRealMoney(gbs.getMoney());//金额
                    gbs.setGoodsID(obj[2] != null ? obj[2].toString() : "");
                    gbs.setGoodsName(obj[1] != null ? obj[1].toString() : "");
                    gname += (obj[1] != null ? obj[1].toString() : "") + ",";
                    gbs.setStandard(pidsarr[2]);//前台传de
                    gbs.setPpID(obj[0] != null ? obj[0].toString() : "");
                    gbs.setBoxStandard(obj[6] != null ? obj[6].toString() : "");
                    gbs.setEntryTime(new Date());
                    gbs.setGoodsNum(obj[5] != null ? obj[5].toString() : "");
                    gbs.setCostmoney(pidsarr[6]);
                    gbs.setPricetype(pidsarr[4]);
                    gbs.setActivityID(pidsarr[3]);
                    gbs.setPremiums("0");//是否是奖品  0或null:否  1:是
                    operator.add(gbs);
                    priceSub = priceSub.add(new BigDecimal(gbs.getMoney()));


                    //处理促销品
                    if (sb.indexOf(pidsarr[0]) == -1) {
                        String sqlcart = "select s.ppId,s.pptId,s.ptstandard,p.companyId,p.goodsname,p.price,s.ptCount from DT_CartShopPromotion s left join DT_cart c on s.cartId = c.cart_Id left join dt_ProductPackaging p on p.ppID = s.pptId where c.pid = ? and c.staff_Id = ?";
                        List<Object> clist = baseBeanService.getListBeanBySqlAndParams(sqlcart, new Object[]{pidsarr[0], cus.getStaffid()});
                        PromotionAssociation pa = null;
                        for (int k = 0; k < clist.size(); k++) {
                            Object o = clist.get(k);
                            Object[] objc = (Object[]) o;
                            pa = new PromotionAssociation();
                            pa.setPaId(serverService.getServerID("paId"));
                            pa.setCashierBillsID(cb.getCashierBillsID());
                            pa.setCompanyId(objc[3].toString());
                            pa.setPpId(objc[1].toString());
                            pa.setCreatDate(new Date());
                            pa.setPpName(objc[4].toString());
                            pa.setPrice(objc[5].toString());
                            pa.setStandard(objc[2].toString());
                            pa.setCount(Integer.parseInt(objc[6].toString()));
                            operator.add(pa);
                        }
                        sb.append(pidsarr[0]);
                        //清空购物车促销品
                        String hqldeletecsp = "delete from CartShopPromotion p where p.staffId = ? and p.ppId = ?";
                        Object[] temp1 = new Object[2];
                        temp1[0] = sf.getStaffID();
                        temp1[1] = pidsarr[0];
                        parms.add(temp1);
                        hqls.add(hqldeletecsp);
                        //清空购物车
                        String hqldelete = "delete from Cart where staffId = ? and pid = ?";
                        hqls.add(hqldelete);
                        Object[] temp = new Object[2];
                        temp[0] = sf.getStaffID();
                        temp[1] = pidsarr[0];
                        parms.add(temp);
                    }
                    if (pidsarr.length > 7) {
                        //清空批发商城购物车中商品
                        if (pidsarr[7] != null && StringHelper.isNotEmpty(pidsarr[7])) {
                            String pfDelHql = "delete from PfscShoppingCart where staffId = ? and pscId = ?";
                            hqls.add(pfDelHql);
                            Object[] pfTemp = new Object[2];
                            pfTemp[0] = sf.getStaffID();
                            //pfTemp[0] = "cstaff201601239QHAIZP9560000000189";
                            pfTemp[1] = pidsarr[7];
                            parms.add(pfTemp);
                        }
                    }
                }
            }
            cb.setPriceSub(priceSub.toString());//计算总金额
            if (gname != null && gname.length() > 200) {
                cb.setProjectName(gname.substring(0, 200));//注意
            } else {
                cb.setProjectName(gname);//注意
            }
            operator.add(cb);
            operator.add(dl);

        }
        try {
            baseBeanService.executeHqlsByParamsList(operator, hqls.toArray(new String[]{}), parms);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        result = pb.getPayJournalNum();

        return "success";

    }

    //下线转账审核通过后生成收款单 /ea/wfjshop/ea_passAuditGenReSheet.jspa?cashierBillsID=2016051604294800001
    public String passAuditGenReSheet() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String cashierBillsID = request.getParameter("cashierBillsID");
        String hql = "from CashierBills where cashierBillsID = ?";
        CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashierBillsID});

        String hqlpay = "from PayCashierBill where oriJournalNum = ?";
        PayCashierBill pc = (PayCashierBill) baseBeanService.getBeanByHqlAndParams(hqlpay, new Object[]{cash.getjNumOrder()});
        /*List<BaseBean> ret = generateNewBill(cash.getTrade_no(), pc.getPayJournalNum(), cash.getPriceSub(),"03");
        baseBeanService.executeHqlsByParamsList(ret, null, null);*/
        Enroll e=(Enroll)baseBeanService.getBeanByHqlAndParams("from Enroll where cashierBillsID=?",new Object[]{cashierBillsID});
        if (e!=null&&e.getReserved1()!=null&&e.getReserved1()!=""){
            goldOrderService.addMarKeting(e);
        }
        Boolean bo = goldOrderService.generateBill(cash.getTrade_no(), pc.getPayJournalNum(), cash.getPriceSub(), "03", "");
        logger.info("{}{}", bo, "33333333333");
        if (bo == true) {

            try {
                //收款单生成后复制订单和收款单到新表
                goldOrderService.copyCash(pc.getPayJournalNum(), "d");
            }catch (Exception e3){
                logger.error("操作异常", e3);
                logger.info("复制订单收款单错误");
            }

            //会员产品只能单独下订单
            if (custype != null && !custype.equals("") && Float.parseFloat(custype) < 7 && cashierBillsID != null && !cashierBillsID.equals("")) {
                //虚拟发货
                transferService.virtual(cashierBillsID);
            }
        }
        return "success";
    }


    /**
     * 添加粉丝
     *
     * @return
     */
    public String addjoinFans(String ccompanyId, TEshopCusCom tc) {
        //公司
        String hql = "from TEshopCusCom t where t.state = ? and t.companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)";
        TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"2", ccompanyId});
        if (tcc == null) {
            String hqlc = "from TEshopCusCom t where t.state = ? and t.companyId = ?";
            tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqlc, new Object[]{"2", "company201009046vxdyzy4wg0000000025"});

        }
        String hcom = "from Company where companyID = ?";
        Company company = (Company) baseBeanService.getBeanByHqlAndParams(hcom, new Object[]{tcc.getCompanyId()});

        List<BaseBean> beans = new ArrayList<BaseBean>();
        JoinFans jf1 = new JoinFans();
        jf1.setJfID(serverService.getServerID("jfID"));
        jf1.setFaccount(tcc.getAccount());
        jf1.setStaffid(tcc.getStaffid());
        jf1.setFsccId(tcc.getSccId());
        jf1.setSource("购买粉丝名片");
        jf1.setState("00");
        jf1.setZaccount(tc.getAccount());
        jf1.setZsccId(tc.getSccId());

        JoinFans jf2 = new JoinFans();
        jf2.setJfID(serverService.getServerID("jfID"));
        jf2.setFaccount(tc.getAccount());
        jf2.setFsccId(tc.getSccId());
        jf2.setStaffid(tc.getStaffid());
        jf2.setSource("购买粉丝名片");
        jf2.setState("00");
        jf2.setZaccount(tcc.getAccount());
        jf2.setZsccId(tcc.getSccId());

        beans.add(jf1);
        beans.add(jf2);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return company.getCompanyName();

    }


    /**
     * 绑定上级会员列表 买的是公司会员的话，无需绑定上级
     */
    @SuppressWarnings("unchecked")

    public String bindSuperViplist() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String querytext = request.getParameter("querytext");
        String cusType = "4";
        String sqlstaff = "select t.account,d.staffname from  dt_hr_staff d,T_ESHOP_CUSCOM t where t.staffid=d.staffid and t.custype=? and t.state = ? and d.staffname like ?";
        String sqlcom = "select t.account,d.companyname from dtcompany d,T_ESHOP_CUSCOM t where t.companyid=d.companyid and t.custype=? and t.state = ? and d.companyname like ?";
        List<BaseBean> staffvip = baseBeanService.getListBeanBySqlAndParams(sqlstaff,
                new Object[]{cusType, "1", "%" + querytext + "%"});
        List<BaseBean> comvip = baseBeanService.getListBeanBySqlAndParams(sqlcom,
                new Object[]{cusType, "2", "%" + querytext + "%"});

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("staffvip", staffvip);
        map.put("comvip", comvip);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();

        return "success";

    }


    // 2015年12月5日15:36:45 购买产品详情购买方法
    // 产品ID 获取当前登陆用户 创建新订单 绑定他的订单 能再我的订单查询到 de
    @SuppressWarnings("unchecked")
    public String gm() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        TEshopCusCom tcom = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        HttpServletRequest request = ServletActionContext.getRequest();
        String ppids = request.getParameter("ppids");
        String buyIsOkPage = request.getParameter("buyIsOkPage");
        String mode = request.getParameter("mode");
        String cardNum = request.getParameter("cardNum");
        String sccId = request.getParameter("sccId");


        if (buyIsOkPage == null || buyIsOkPage == "")
            session.removeAttribute("buyIsOkPage");
        if (session.getAttribute("buyIsOkPage") == null || session.getAttribute("buyIsOkPage") == "")
            session.setAttribute("buyIsOkPage", buyIsOkPage);

        if (cus == null) {
            if (mode == null || mode.equals("")) {
                return "login";
            } else {
                if (cardNum != null && !cardNum.equals("")) {
                    tcom = smSerivce.getTEshopByCum(cardNum);
                    cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{tcom.getAccount()});
                } else if (sccId != null && !sccId.equals("")) {
                    tcom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
                    if (tcom != null) {
                        cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{tcom.getAccount()});
                    }
                }

            }
        }
        if (cus != null) {
            request.setAttribute("status", cus.getStatus());
        }
        if (count == null) {
            count = "0";
        }
        String hql = "from  ProductPackaging where  ppID=?";
        productDesign = (ProductPackaging) baseBeanService
                .getBeanByHqlAndParams(hql, new String[]{ppid});
        request.setAttribute("ppids", ppids);
        if (productDesign == null) {
            productDesign = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(hql, new String[]{ppids});
        }

        // 确认订单获取价格
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());//获取当前时间
        String[] params1 = {ppid};
        String[] params2 = {ppid, currentTime, currentTime};
        Object obj = null;
        if ("1".equals(pricetype)) {
            String sqlprice = VOtool.getShowPriceSql(2, 2).toString();//批发
            obj = baseBeanService.getObjectBySqlAndParams(sqlprice, params1);
            request.setAttribute("priceType", "pf");

        } else {

            String sqlprice = VOtool.getShowPriceSql(2, 5).toString();//特价(活动)相关

            //价格类别[cx:促销价,tj:特价,vip:VIP价,yj:原价(零售价)]
            obj = baseBeanService.getObjectBySqlAndParams(sqlprice, params2);
            if (obj != null) {
                request.setAttribute("priceType", "tj");
            } else {
                sqlprice = VOtool.getShowPriceSql(2, 4).toString();//促销价(活动)相关
                obj = baseBeanService.getObjectBySqlAndParams(sqlprice, params2);
                if (obj != null) {
                    request.setAttribute("priceType", "cx");
                } else {
                    sqlprice = VOtool.getShowPriceSql(2, 3).toString();//VIP价相关
                    obj = baseBeanService.getObjectBySqlAndParams(sqlprice, params1);
                    if ((posNum == null || posNum.equals("")) && tc != null && obj != null && tc.getCusType().compareTo("6") < 0) {//用户会员等级[小于6级时商品展示会员价]
                        request.setAttribute("priceType", "vip");
                    } else {
                        sqlprice = VOtool.getShowPriceSql(2, 1).toString();//零售价相关
                        obj = baseBeanService.getObjectBySqlAndParams(sqlprice, params1);
                        request.setAttribute("priceType", "yj");
                    }
                }
            }
        }
        request.setAttribute("actPriceId", obj);

        //xgb根据产品类型判断是否查询车辆信息
        List<BaseBean> cfList = null;
        if (productDesign != null && productDesign.getType() != null) {
            if (productDesign.getType().indexOf("包月") != -1 || productDesign.getType().indexOf("包年") != -1 || productDesign.getType().indexOf("包天") != -1) {
                cfList = mappointmentService.getBindCar(request.getParameter("carType"), cus.getStaffid());
            }
        }
        request.setAttribute("cfList", cfList);
        BigDecimal rec = new BigDecimal(0);
        //奖品下订单，奖品价格为0元ljc
        if (recordId != null && recordId.length() > 0) {
            morre = request.getParameter("morre");
        } else {
            Object[] bo = (Object[]) obj;
            rec = new BigDecimal(bo[0].toString());
            morre = rec.toString();
        }

        company = (Company) baseBeanService.getBeanByHqlAndParams(
                "from  Company where companyID=?",
                new String[]{productDesign.getCompanyID()});
        session.setAttribute("company", company);
        if (!"cash".equals(mode)) {
            if (staffAddress == null) {
                if (cus != null) {
                    String staffaddhql = "from StaffAddress where staffID=? and isDefault='是'";
                    staffAddress = (StaffAddress) baseBeanService
                            .getBeanByHqlAndParams(staffaddhql,
                                    new String[]{cus.getStaffid()});
                }
            } else {
                if (staffAddress.getAddressID() != null && staffAddress.getAddressID() != "") {
                    String staffaddhql = "from StaffAddress where  addressID=? ";
                    staffAddress = (StaffAddress) baseBeanService
                            .getBeanByHqlAndParams(staffaddhql,
                                    new String[]{staffAddress.getAddressID()});
                }
            }
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select w.wfj_jifen_id from dt_wfj_jifen w,t_eshop_cuscom t");
        sql.append(" where w.staff_id=t.staffid");
        sql.append(" and ( t.account='15810799888' or t.custype='0.5' or t.custype='1')");
        List<Object> a = baseBeanService.getListBeanBySqlAndParams(sql.toString(), null);
        sql.delete(0, sql.length());
        sql.append("select jd.jifen_detail_score,s.staffname");
        sql.append(" from dt_wfj_jifen_detail jd,dt_wfj_jifen j,dt_hr_staff s");
        sql.append(" where jd.wfj_jifen_id=j.wfj_jifen_id and j.staff_id=s.staffid and j.wfj_jifen_score>?");

        for (int i = 0; i < a.size(); i++) {
            sql.append(" and j.wfj_jifen_id != ?");
        }
        a.add(0, 0);

        sql.append(" and rownum<=200 order by jd.jifen_detail_date desc");
        List<Object> detail = baseBeanService
                .getListBeanBySqlAndParams(
                        sql.toString(), a.toArray());
        if (cus != null) {
            staff = (Staff) baseBeanService
                    .getBeanByHqlAndParams("from Staff where staffid=?",
                            new String[]{cus.getStaffid()});
        }
        //促销品
        sql.delete(0, sql.length());
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
        sql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p,dt_promotion pt");
        sql.append(" where p.ppid = ps.ppid and ps.ppid=pt.ptppid and pt.ppid=? and ps.fcom_id is null and pt.ptppid in(");
        StringBuffer stemp = new StringBuffer();
        stemp.append("order by case pt.ptppid ");
        List<Object> sp = new ArrayList<Object>();
        List<Object> params = new ArrayList<Object>();
        params.add(ppid);
        List<Object> list = new ArrayList<Object>();
        if (ptppid != null && ptppid.length() > 0) {
            String[] temp = ptppid.split(",");
            for (int i = 0; i < temp.length; i++) {
                if (i == temp.length - 1) {
                    sql.append("?");
                } else {
                    sql.append("?,");
                }
                stemp.append("when ? then '" + i + "'");
                sp.add(temp[i]);
                params.add(temp[i]);
            }
            stemp.append(" else pt.ptppid end");
            sql.append(" )");
            sql.append(stemp);
            params.addAll(sp);
            list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray(new Object[]{}));

        }

        //ljc 奖品时无需积分
        if (recordId == null) {

            //            TEshopCusCom tcom=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(" from TEshopCusCom where account = ? and logOff=0",
            //                    new Object[]{cus.getAccount()});

//            TEshopCusCom tcom=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(" from TEshopCusCom where account = ? and logOff=0",
//                    new Object[]{cus.getAccount()});
            BonusPoints bonusPoints = null;
            if (tcom != null) {
                bonusPoints = (BonusPoints) baseBeanService.getBeanByHqlAndParams(" from BonusPoints where sccid = ?", new Object[]{tcom.getSccId()});
            }
            WfjJifen wfjJf = null;
            if (tcom != null) {
                wfjJf = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where sccid=?", new Object[]{tcom.getSccId()});
            }
            if (bonusPoints != null && bonusPoints.getBonusPointScore() != null) {
                if (bonusPoints.getBonusPointScore().contains(".")) {
                    request.setAttribute("bonusPoints", bonusPoints.getBonusPointScore().substring(0, bonusPoints.getBonusPointScore().indexOf(".")));
                } else {
                    request.setAttribute("bonusPoints", bonusPoints.getBonusPointScore());
                }

            } else {
                request.setAttribute("bonusPoints", "");
            }
            //金币数
            if (wfjJf != null && wfjJf.getWfjJifenScore() != null) {
                BigDecimal gold = new BigDecimal(wfjJf.getWfjJifenScore());
                if (gold.compareTo(rec.multiply(new BigDecimal(100))) >= 0) {
                    request.setAttribute("wfjJf", bonusPoints);
                    request.setAttribute("gold", rec.multiply(new BigDecimal(100)));
                } else {
                    if (wfjJf.getWfjJifenScore().contains(".")) {
                        request.setAttribute("gold", wfjJf.getWfjJifenScore().substring(0, wfjJf.getWfjJifenScore().indexOf(".")));
                    } else {
                        request.setAttribute("gold", wfjJf.getWfjJifenScore());
                    }
                }
            } else {
                request.setAttribute("gold", "");
            }


        }

        request.setAttribute("ccompanyId", request.getParameter("ccompanyId"));
        request.setAttribute("standard", request.getParameter("standard"));
        request.setAttribute("ptlist", list);
        request.setAttribute("jf", detail);
        request.setAttribute("staffID", cus != null ? cus.getStaffid() : "");
        request.setAttribute("user", cus != null ? cus.getAccount() : "");

        request.setAttribute("jineshu", rec);
        request.setAttribute("ptppid", ptppid);
        //request.setAttribute("pct", pct);
        //非空校验
        if (tcom != null && tcom.getCompanyId() != null && !tcom.getCompanyId().equals("")) {
            request.setAttribute("haveCompany", "1");//已经加入该公司
        } else {
            request.setAttribute("haveCompany", "0");//未加入该公司
        }

        if (ptstandard != null && ptstandard.length() > 0) {
            String[] ps = ptstandard.split(",,");
            request.setAttribute("ptstandard", ps);
        }


        if (stype != null) {
            return "shippingAddresss";
        } else {
            return "newcat";
        }
    }


    /**
     * 提醒发货
     */
    public String ReminderDelivery() {

        String hql = "from CashierBills where cashierBillsID = ?";
        CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashierBillsID});
        String hqlto = "from TEshopCusCom where companyId= ?";
        TEshopCusCom tesh = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqlto, new Object[]{cash.getCompanyID()});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("txin", "提醒发货成功");
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        if (tesh != null) {
            ReminderMessage(tesh, cash != null ? cash.getProjectName() : "");
        }
        return "success";
    }

    /**
     * 提醒发货
     * 发送短信
     */

    private void ReminderMessage(TEshopCusCom com, String goodsName) {
//        try {
////            msage.setMobiles(com.getAccount());
////            msage.setMessage("主人您好，有催单的请尽快给客户发货，以下产品" + goodsName);
////            msage.sendMsg("【微分金平台】");
//        } catch (IOException e) {
//            logger.error("操作异常", e);
//        }
        //xgb
        String sql = "select t.contactway  from dt_hr_staff_contact t where t.staffid= ? and t.contactType in (select e.codeID from dtCCode e where  e.codevalue= ? )";
        List list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{com.getStaffid(), "短信提醒"});
        String cellphoneMark = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String obj = list.get(i).toString();
                if (obj != null) {
                    cellphoneMark += obj + ",";
                }
            }
        }
        cellphoneMark += com.getAccount();
        //  logger.error("提醒发货-----提醒人账号:" + com.getAccount() + "------公司下员工账号:" + cellphoneMark);
        //保存账号
        String[] arr = cellphoneMark.split(",");
        slist = Arrays.asList(arr);
        //极光推送
        JushMain.sendjiguangMessage("主人您好，有催单的请尽快给客户发货，以下产品【" + goodsName + "】", "提醒", "sellerindent", "shipments", slist);
    }

    /**
     * 购物车结算地址
     *
     * @return
     */
    public String getcitys() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);

        if (cus == null) {
            return "login";
        }
        if (staffAddress == null) {
            String staffaddhql = "from StaffAddress where staffID=? and isDefault='是'";
            staffaddress = (StaffAddress) baseBeanService
                    .getBeanByHqlAndParams(staffaddhql,
                            new String[]{cus.getStaffid()});
        } else {
            String staffaddhql = "from StaffAddress where  addressID=? ";
            staffaddress = (StaffAddress) baseBeanService
                    .getBeanByHqlAndParams(staffaddhql,
                            new String[]{staffAddress.getAddressID()});

        }

        String[] arrylist = ppid.split("zz");//pid,

        List<Object> pplist = new ArrayList<Object>();

        BigDecimal sumprice = new BigDecimal(0);
        String total = "";

        List<String> comlist = new ArrayList<String>();
        List<Object> comps = new ArrayList<Object>();

        StringBuilder sqlc = new StringBuilder();
        sqlc.append("select c.companyId,c.companyName,dc.ccompanyid,dc.logoPath,1+nvl(s.total_pct,0)/100");
        sqlc.append(" from dtCompany c left join dt_ccom_com cc on c.companyId = cc.compnay_id");
        sqlc.append(" left join dtContactCompany dc on dc.ccompanyId = cc.ccompany_Id");
        sqlc.append(" left join dt_set_subsidize s");
        sqlc.append(" on dc.industrytype = s.gtid and s.stutas=? where c.companyId = ?");


        for (int i = 0; i < arrylist.length; i++) {
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = e.format(new Date());//获取当前时间
            StringBuilder sqlp = new StringBuilder();

            sqlp.append("select ps.ppid,p.goodsname,p.image,p.type,ps.re_price,p.companyid,c.itemNum,");
            sqlp.append("c.stardard,c.cart_Id,pap.actPrice,pv.vip,pa.type activityType,ps.suid,");
            sqlp.append("ps.ef_price lscc,pap.actpriceid,pap.factory hdcc,pv.vipid,pv.factory vipcc ");

            sqlp.append("from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
            sqlp.append("inner join DT_cart c on p.ppid = c.pid ");
            sqlp.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
            sqlp.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00'");
            sqlp.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
            sqlp.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sqlp.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
            sqlp.append("where ps.ppid = ? and c.staff_id = ? and c.pos = ? ");
            sqlp.append(" and ps.state='00' ");
            String[] arrps = arrylist[i].split("-");
            //ljc
            String stardard = arrps[1];
            List<Object> pa = new ArrayList<Object>();
            pa.add(currentTime);
            pa.add(currentTime);
            pa.add(arrps[0]);
            pa.add(cus.getStaffid());
            pa.add((pos != null && pos.equals("1")) ? "1" : "0");
            if (!stardard.equals("*")) {
                sqlp.append(" and c.stardard like ?");
                pa.add("%" + stardard + "%");
            }

            Object ppk = baseBeanService.getObjectBySqlAndParams(sqlp.toString(),
                    pa.toArray());

            if (ppk != null) {
                Object[] objmap = (Object[]) ppk;

                //统计产品
                pplist.add(ppk);

                String companyID = objmap[5].toString();

                Object objcom = baseBeanService.getObjectBySqlAndParams(sqlc.toString(), new Object[]{"01", companyID});

                if (!comlist.contains(companyID)) {
                    comlist.add(companyID);
                    comps.add(objcom);

                }

                Object[] o = (Object[]) objcom;
                request.setAttribute("staffID", cus.getStaffid());
                //计算所有产品总金额
                BigDecimal num = new BigDecimal(objmap[6].toString());
                BigDecimal s = new BigDecimal(o[4].toString());
                if (objmap[11] != null && objmap[9] != null) {//活动价相关
                    BigDecimal price = new BigDecimal(objmap[9].toString());
                    BigDecimal cc = price.multiply(num).multiply(s);
                    sumprice = sumprice.add(cc).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    if (objmap[10] != null && tc.getCusType().compareTo("6") < 0) {//VIP价相关
                        BigDecimal price = new BigDecimal(objmap[10].toString());
                        BigDecimal cc = price.multiply(num).multiply(s);
                        sumprice = sumprice.add(cc).setScale(2, BigDecimal.ROUND_HALF_UP);
                    } else {//零售价相关
                        BigDecimal price = new BigDecimal(objmap[4].toString());
                        BigDecimal cc = price.multiply(num).multiply(s);
                        sumprice = sumprice.add(cc).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                }

            }

        }
        //总价格
        total = sumprice.toString();
        request.setAttribute("complist", comps);//公司列表
        request.setAttribute("pplist", pplist);//产品列表
        request.setAttribute("total", total);
        request.setAttribute("staffID", cus.getStaffid());
        request.setAttribute("user1", cus.getAccount());
        if (tc.getCusType().compareTo("6") < 0) {//判断用户会员等级[小于6级时商品展示会员价]
            request.setAttribute("cusType", "vip");
        }

        return "orderdetailss";

    }

    // 微分金购买企业商场 详细
    public String getxx() {
        // HttpSession session = ServletActionContext.getRequest().getSession();
        // SessionWrap sw = SessionWrap.getInstance();
        // TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
        // SessionWrap.KEY_CUSTOMER);
        // if (cus == null) {
        // return "login";
        // }
        beans = baseBeanService.getListBeanByHqlAndParams(
                "from CCode d  where d.codePID=? and d.companyID=? ",
                new String[]{"scode20150815wygb79q82p0000000005",
                        "company201009046vxdyzy4wg0000000025"});
        comp = (Company) baseBeanService.getBeanByHqlAndParams(
                "from Company d where d.companyName=?",
                new String[]{"北京天太世统科技有限责任公司"});
        String sql = "select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? "
                + " and p.ppid= ? and p.ppid=ps.ppid and ps.fcom_id is null ";
        Object objs = baseBeanService.getObjectBySqlAndParams(sql,
                new String[]{"company201009046vxdyzy4wg0000000025", ppid});
        // 0产品名字1产品名字id2产品銷售价格3产品图片4产品物品id5公司id
        Object[] obj = (Object[]) objs;
        goodname = (String) obj[0];
        morre = (String) obj[2];

        if (obj[6].equals("个人会员")) {
            // 根据名字所改
            if (obj[0].equals("VIP客户商城业主会员"))
                fenlei = "6";
            else if (obj[0].equals("合作创业商城业主会员"))
                fenlei = "3";
            else if (obj[0].equals("微分金商城业主会员"))
                fenlei = "4";
            else if (obj[0].equals("代理商商城业主会员"))
                fenlei = "5";
            return "grgm";
        } else if (obj[6].equals("公司会员")) {
            return "gmyemian";
        } else {
            return "shophuilist";
        }
    }

    /**
     * 打赏单据处理
     *
     * @param trade_no   第三方交易号
     * @param journalNum 订单号
     * @param wfStatus4  支付方式00微信支付，01，支付宝支付
     * @param wfStatus1  00微信公众号支付，01微信app支付
     * @return
     */
    public Boolean rewardPay(String trade_no, String journalNum, String wfStatus4, String wfStatus1) {
        Boolean bool = true;
        try {
            Rewarddetail rd = (Rewarddetail) icService.getReward(journalNum, "00");
            //生成订单
            String cashierBillsID = goldOrderService.rewardOrder(rd);
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                //生成收款单
                Boolean bo = goldOrderService.generateBill(trade_no, journalNum, rd.getRdmoney(), wfStatus4, wfStatus1);
                if (bo == true) {
                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "d");
                    }catch (Exception e){
                        logger.error("操作异常", e);
                        logger.info("复制订单收款单错误");
                    }
                    //把订单状态改程03已收货
                    goldOrderService.updateFkState(cashierBillsID);
                    //分金币
                    transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");

                    goldOrderService.addReward(rd, wfStatus4, trade_no);
                }
            }

        } catch (Exception e) {
            bool = false;
            logger.error("操作异常", e);
        }
        return bool;
    }


/////////////////////////////////////////////微信支付开始//////////////////////////////////////////////////


    /**
     * 微信支付  调用后台生成预支付交易单，拼接用来调起支付的参数
     *
     * @return
     */

    public String weChatpay() {

        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(
                httpSession, SessionWrap.KEY_SHOPCUSCOM);

        String ip = request.getRemoteAddr();
        String params = request.getParameter("params");
        logger.info("调试信息");
        String[] arry = params.split("-");
        String ppid = "";
        // 生成预支付单
        // 拼接调起支付需要的参数

        WxPayDto tpWxPay = new WxPayDto();
        //获取微信支付需要的openID

        String code = request.getParameter("code");
        // logger.info("authcode:: {}", code);
        String openId = getOpenID(code);
        //  logger.info("openId:: {}", openId);

        tpWxPay.setWechatbz("wxa1b3f84c027804c3");

        tpWxPay.setOpenId(openId);
        String attach = "其他";
        //  this.logger.error(arry.length + "长度");
        if (arry[1] != null && arry[1].equals("微分金金币")) {

            attach = "微分金金币";
        } else if (arry[1] != null && arry[1].equals("微分金积分")) {
            attach = "微分金积分";
        } else if (arry[1] != null && (arry[1].indexOf("扫码收款") != -1 || arry[1].indexOf("停车收费") != -1)) {
            attach = "smsk";
            ppid = arry[5];
        } else {
            if (arry.length >= 7) {
                // this.logger.error(arry[6]);
                //  System.out.print(arry[6]);
                attach = arry[6];
            }
        }
        //    System.out.print(attach + "attach");
        //this.logger.error(attach + "attach");
        tpWxPay.setAttach(attach);
        journalNum = arry[0];
        total = arry[2];
        staffid = arry[3];
        if (arry.length >= 5) {

            sccid = arry[4];
        }
        if (cus != null) {
            if (staffid == null || staffid.equals("")) {
                staffid = cus.getStaffid();
            }
            if (sccid == null || sccid.equals("")) {
                sccid = cus.getSccId();
            }
        }

        try {
            tpWxPay.setBody(URLDecoder.decode(arry[1], "UTF-8"));
        } catch (UnsupportedEncodingException e) {

            logger.error("操作异常", e);
        }

        tpWxPay.setOrderId(arry[0]);
        tpWxPay.setSpbillCreateIp(ip);
        tpWxPay.setTotalFee(arry[2]);
        String par = "";
        if (ppid != null && !ppid.equals("")) {
            par = "-" + ppid;
        }
        logger.info("调试信息");
        tpWxPay.setAttach(tpWxPay.getAttach() + "-" + staffid + "-" + sccid + par);
        // this.logger.error("zattach" + tpWxPay.getAttach());
        //  System.out.print("zattach" + tpWxPay.getAttach());

        tpWxPay.setCompanyId("company201009046vxdyzy4wg0000000025");

        Map<String, Object> map = new HashMap<String, Object>();

        List<SubOrders> sub_orderslist = goldOrderService.getOrdersList(tpWxPay);
        if (sub_orderslist == null || sub_orderslist.size() == 0) {

//          finalPackage = WchatPay.getPackage(tpWxPay);//普通模式
//          logger.info("普通模式");

           finalPackage = HTTPV3.getPackage(tpWxPay);//服务商模式
            logger.info("服务商模式");
        } else {
            CombinePayerInfo combine_payer_info = new CombinePayerInfo();  //合单模式
            combine_payer_info.setOpenid(openId);
            finalPackage = HTTPV3.getPackageV3(tpWxPay, sub_orderslist, combine_payer_info, ConstantURL.JSAPI_PAY);
            logger.info("V3模式");

        }
        map.put("finalPackage", finalPackage);
        map.put("staffID", staffid);
        map.put("journalNum", journalNum);
        map.put("sccid", sccid);
        map.put("ccompanyId", ccompanyId);
        map.put("goodsname", arry[1]);
        map.put("total", total);
        if (arry.length >= 7) {
            map.put("attach", arry[6]);
        }

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    /**
     * 获取用户对于公众账号的唯一标识openID
     *
     * @return
     */
    public String getOpenID(String code) {
        HttpSession session = ServletActionContext.getRequest().getSession();


        String openid = null;
        String access_token = null;
        try {
            String appID = Constant.wechatMap.get("wxa1b3f84c027804c3").get("appID");
            String secret = Constant.wechatMap.get("wxa1b3f84c027804c3").get("appSecret");
            String strparams = "appid=" + appID + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";


            String accessurl = "https://api.weixin.qq.com/sns/oauth2/access_token?";

            Map<String, String> map = GetWxOrderno.getOpenID(accessurl, strparams);
            if (map.get("errcode") != null && map.get("errcode").equals("1")) {

                logger.info("openid获取失败");

            } else {
                openid = map.get("openid");
                access_token = map.get("access_token");
                session.setAttribute("access_token", access_token);
                session.setAttribute("openid", openid);
            }


        } catch (Exception e) {

            logger.error("操作异常", e);
        }

        return openid;
    }


    /**
     * h5微信pay
     *
     * @return
     */
    public String h5WechatPay() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String staffid = request.getParameter("staffid");
        String sccid = request.getParameter("sccid");

        String par = "";
        if (ppid != null && !ppid.equals("")) {
            par = "-" + ppid;
        }
//        payDto.setWechatbz("apppay");
        payDto.setWechatbz("wxa1b3f84c027804c3");

        payDto.setAttach(payDto.getAttach() + "-" + staffid + "-" + sccid + par);
        payDto.setSpbillCreateIp(IPUtil.getIpAddr(request));

        List<SubOrders> sub_orderslist = goldOrderService.getOrdersList(payDto);
        String h5_url = "";
        if (sub_orderslist == null || sub_orderslist.size() == 0) {
            h5_url = HTTPV3.getH5Service_url(payDto, ConstantURL.H5Sever_PAY);

        } else {
            h5_url = HTTPV3.getH5_url(payDto, sub_orderslist, ConstantURL.H5_PAY);
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("h5_url", h5_url);

        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();

        return "success";
    }

    /**
     * app微信支付
     *
     * @return
     */
    public String appWechatPay() {
        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(
                httpSession, SessionWrap.KEY_SHOPCUSCOM);
        String elkc = request.getParameter("elkc");
        String isiOS = request.getParameter("isiOS");
        if (elkc != null && !elkc.equals("")) {
            payDto.setWechatbz("elkc");
        } else {
            payDto.setWechatbz("apppay");
        }
        String staffid = request.getParameter("staffid");
        String sccid = request.getParameter("sccid");
        if (cus != null) {
            if (staffid == null || staffid.equals("")) {
                staffid = cus.getStaffid();
            }
            if (sccid == null || sccid.equals("")) {
                sccid = cus.getSccId();
            }
        }
        String par = "";
        if (ppid != null && !ppid.equals("")) {
            par = "-" + ppid;
        }
        System.out.print("par" + par);
        payDto.setAttach(payDto.getAttach() + "-" + staffid + "-" + sccid + par);

        List<SubOrders> sub_orderslist = goldOrderService.getOrdersList(payDto);
        AppPackage appPackage = null;
        if (sub_orderslist == null || sub_orderslist.size() == 0 || "true".equals(isiOS)) {
            appPackage = HTTPV3.getAppPackage(payDto);
            logger.info("支付V2");
        } else {
            appPackage = HTTPV3.getAppPackageV3(payDto, sub_orderslist, null, ConstantURL.APP_PAY);
            logger.info("支付V3");
        }


//        logger.info("appid:: {}", appPackage.getAppid());
//        logger.info("noncestr:: {}", appPackage.getNoncestr());
//        logger.info("package:: {}", appPackage.getPackages());
//        logger.info("partnerid:: {}", appPackage.getPartnerid());
//        logger.info("prepayid:: {}", appPackage.getPrepayid());
//        logger.info("timestamp:: {}", appPackage.getTimestamp());
//        logger.info("sign:: {}", appPackage.getSign());


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appPackage", appPackage);

        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();

        return "success";
    }


    /**
     * 微信回调通知异步
     *
     * @throws Exception
     */
    public void notifyResult() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();


        String inputLine;
        String notity = "";
        String res = "";
        String resXml = "";

        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notity += inputLine;
            }
            request.getReader().close();


        } catch (Exception e) {
            // logger.error("操作异常", e);
        }
        int suc = 0;
        logger.info("接收到的报文：: {}", notity);
        WxPayResult wxPayResult = null;
        String wfStatus1 = "01";
        String wechatbz = "";
        try {
            logger.info("V2模式");
            wxPayResult = WeChatUtils.parseXmlToResult(notity);
            if ("SUCCESS".equals(wxPayResult.getResultCode())) {

                String tradeType = wxPayResult.getTradeType();
                wechatbz = "apppay";
                if (tradeType != null && tradeType.equals("JSAPI")) {
                    wfStatus1 = "00";
                    wechatbz = "wxa1b3f84c027804c3";
                }
                if (WeChatUtils.verifyWeixinNotify(notity, wechatbz)) {
                    suc = 1;
                }
            }
        } catch (Exception e) {
            logger.info("服务商模式");

            try {
                logger.info("调试信息");

                wxPayResult = HTTPV3.noticeServerResult(notity, request);
                logger.info("调试信息");
                logger.info("wxPayResult:: {}", wxPayResult.getResultCode());
                if ("SUCCESS".equals(wxPayResult.getResultCode())) {
                    String tradeType = wxPayResult.getTradeType();

                    if (tradeType != null && tradeType.equals("MWEB")) {
                        wfStatus1 = "02";

                    }
                    suc = 1;
                } else {
                    logger.info("合单模式1");
                    Map<String, String> submap = new HashMap<String, String>();
                    wxPayResult = HTTPV3.noticeResult(notity, request, submap);
                    logger.info("调试信息");
                    goldOrderService.setPayCashiber(submap, wxPayResult.getOutTradeNo());
                    if ("TRANSACTION.SUCCESS".equals(wxPayResult.getResultCode())) {
                        String tradeType = wxPayResult.getTradeType();
                        goldOrderService.addHdInfo(wxPayResult.getOutTradeNo());
                        if (tradeType != null && tradeType.equals("JSAPI")) {
                            wfStatus1 = "00";

                        }
                        suc = 1;
                    }

                }

            } catch (Exception ee) {
                logger.info("合单模式");
                Map<String, String> submap = new HashMap<String, String>();
                wxPayResult = HTTPV3.noticeResult(notity, request, submap);
                logger.info("调试信息");
                goldOrderService.setPayCashiber(submap, wxPayResult.getOutTradeNo());
                if ("TRANSACTION.SUCCESS".equals(wxPayResult.getResultCode())) {
                    String tradeType = wxPayResult.getTradeType();
                    goldOrderService.addHdInfo(wxPayResult.getOutTradeNo());
                    if (tradeType != null && tradeType.equals("JSAPI")) {
                        wfStatus1 = "00";

                    }
                    suc = 1;
                }
            }
        }

        //验证微信支付返回签名

        try {
            if (suc == 1) {

                logger.info("进入主方法");

                //修改订单的状态
                String journalNum = wxPayResult.getOutTradeNo();//商户订单

                String trade_no = wxPayResult.getTransactionId();//第三方
                morre = wxPayResult.getTotalFee();
                String attach = wxPayResult.getAttach();
                logger.info("journalNum: {}", journalNum);
                logger.info("attach: {}", attach);
                logger.info("morre: {}", morre);
                //判断是否回调过
                if (goldOrderService.isCalledPay(journalNum)) {

                    if (wxPayResult.getAttach().indexOf("wfjjb") != -1) {

                        String[] str = attach.split("-");
                        transferService.buyJinbi("company201009046vxdyzy4wg0000000025",
                                journalNum, str[1], str[2], WeChatUtils.changeF2Y(morre), "00", trade_no);

                    } else if (wxPayResult.getAttach().indexOf("wfjjf") != -1) {

                        String[] str = attach.split("-");
                        bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025",
                                journalNum, str[1], str[2], WeChatUtils.changeF2Y(morre), "00", trade_no);
                        //app积分充值微信
                        OperatorInfo operator = (OperatorInfo) baseBeanService.getBeanByHqlAndParams("from OperatorInfo where journalNum = ?", new Object[]{journalNum});
                        if (operator != null) {
                            operator.setStatus("01");
                            operator.setPayWay("01");
                            baseBeanService.saveOrUpdate(operator);
                        }

                    } else if (wxPayResult.getAttach().indexOf("smsk") != -1) {
                        logger.info("值：{}", attach);

                        String[] str = attach.split("-");
                        genScanCodePay(trade_no, str[3], WeChatUtils.changeF2Y(morre), str[2], journalNum, "00", wfStatus1, "");

                    } else if (wxPayResult.getAttach().indexOf("gsmzs") != -1 || wxPayResult.getAttach().indexOf("xdsmzs") != -1) {
                        //  logger.info("11111111111111");

                        PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                        genScanAssiCodePay(trade_no, payBackupBill.getWaiterID(), payBackupBill.getCompanyId(), WeChatUtils.changeF2Y(morre), payBackupBill.getSccid(), journalNum, "00", wfStatus1, payBackupBill.getCoID());

                    } else if (wxPayResult.getAttach().indexOf("dsmzs") != -1 || wxPayResult.getAttach().indexOf("qyht") != -1) {

                        PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                        genScanCodePay(trade_no, payBackupBill.getPpid(), WeChatUtils.changeF2Y(morre), payBackupBill.getSccid(), journalNum, "00", wfStatus1, payBackupBill.getWaiterID());

                    } else if (wxPayResult.getAttach().indexOf("dsmzs") != -1) {

                        PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                        genScanCodePay(trade_no, payBackupBill.getPpid(), WeChatUtils.changeF2Y(morre), payBackupBill.getSccid(), journalNum, "00", wfStatus1, payBackupBill.getWaiterID());

                    } else if (wxPayResult.getAttach().indexOf("selfpay") != -1) {

                        PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                        SelfCart selfCart = smSerivce.getSelfCartByJum(journalNum);
                        if (selfCart != null && selfCart.getCardNum() != null) {
                            TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                            bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", journalNum, (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), WeChatUtils.changeF2Y(morre), "00", trade_no);
                            //收银积分充值微信
                            OperatorInfo operator = (OperatorInfo) baseBeanService.getBeanByHqlAndParams("from OperatorInfo where journalNum = ?", new Object[]{journalNum});
                            if (operator != null) {
                                operator.setStatus("01");
                                operator.setPayWay("01");
                                baseBeanService.saveOrUpdate(operator);
                            }
                        } else {

                            CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});

                            if (cashierBills != null && "智能货柜".equals(cashierBills.getGoodsName())) {
                                DtOrderBillAdd dtOrderBillAdd = (DtOrderBillAdd)baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum = ?",new Object[]{journalNum});
                                genSelfPay(trade_no, WeChatUtils.changeF2Y(morre), dtOrderBillAdd.getOaSccId(), journalNum, "00", wfStatus1, "");

                            }else {

                                genSelfPay(trade_no, WeChatUtils.changeF2Y(morre), payBackupBill.getSccid(), journalNum, "00", wfStatus1, payBackupBill.getAddressID());
                            }

                        }
                    } else if (wxPayResult.getAttach().indexOf("reward") != -1) {

                        rewardPay(trade_no, journalNum, "00", wfStatus1);

                    } else if (wxPayResult.getAttach().indexOf("book") != -1) {  //练车

                        bookPay(trade_no, journalNum, "00", wfStatus1);


                    } else {
                        logger.info("2222222222222");
                        boolean bool = genCanYin(trade_no, journalNum, WeChatUtils.changeF2Y(morre), "00", wfStatus1);
                        if (bool == false) {
                            CashierBills c=(CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=?",new Object[]{journalNum});
                            Enroll ee=(Enroll)baseBeanService.getBeanByHqlAndParams("from Enroll where cashierBillsID=?",new Object[]{c.getCashierBillsID()});
                            if (ee!=null&&ee.getReserved1()!=null&&ee.getReserved1()!=""){
                                goldOrderService.addMarKeting(ee);
                            }
                            //  logger.error("其他生成收款单");
                            //   System.out.print("其他生成收款单");
                            goldOrderService.generateBill(trade_no, journalNum, WeChatUtils.changeF2Y(morre), "00", wfStatus1);
                            try {
                                //收款单生成后复制订单和收款单到新表
                                goldOrderService.copyCash(journalNum, "d");
                            }catch (Exception e){
                                logger.error("操作异常", e);
                                logger.info("复制订单收款单错误");
                            }
                        }
                    }
                }
                addPeriod(journalNum);///购买学时产品时增加学时
                res = "{\"code\": \"SUCCESS\",\"message\": \"\",}";

                resXml = WeChatUtils.backWeixinResult("SUCCESS", "OK");
                //支付成功
            } else {
                res = "{\"code\": \"ERROR_NAME\",\"message\": \"ERROR_DESCRIPTION\",}";
                resXml = WeChatUtils.backWeixinResult("FAIL", "签名失败");

            }
        } catch (Exception e) {
            res = "{\"code\": \"ERROR_NAME\",\"message\": \"ERROR_DESCRIPTION\",}";
            resXml = WeChatUtils.backWeixinResult("FAIL", wxPayResult.getReturnMsg());

            logger.error("操作异常", e);
        }

        //  logger.info("微信支付回调数据结束");
        // logger.info("值：{}", resXml);
        HttpServletResponse response = ServletActionContext.getResponse();
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(res.getBytes());
        out.write(resXml.getBytes());
        out.flush();
        out.close();

    }


    /**
     * 微信回调同步获取支付结果
     */
    public String weChatBaseRep() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String out_trade_no = request.getParameter("out_trade_no");
        PayBackupBill bc = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{out_trade_no});
        if (bc != null && "1".equals(bc.getHdpay())) {//合单
            payDto = HTTPV3.searchOrder(out_trade_no);

        } else {
            // payDto = WchatPay.searchOrder(out_trade_no, "apppay");
            payDto = HTTPV3.serverQuery(out_trade_no);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", payDto.getTrade_state());
        JSONObject jo = JSONObject.fromObject(map);
        result = jo;

        return "success";

    }


    /**
     * 微信回调同步获取支付结果
     */
    public String weChatSuccessPage() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String out_trade_no = request.getParameter("out_trade_no");
        String attach = request.getParameter("attach");
        String goodsname = request.getParameter("goodsname");


        if (goodsname != null && goodsname.equals("微分金金币")) {

            return "goldpool";

        }
        if (attach != null && attach.indexOf("qyht") != -1) {
            ddid = out_trade_no;
            return "qyht";
        }
        if (goodsname != null && goodsname.equals("smsk")) {

            return "paysuc";

        }

        if (attach != null && attach.indexOf("sm") != -1) {

            return "paysuc";
        }
        if (attach != null && attach.indexOf("selfpay") != -1) {

            return "paysuc";
        }
        if (attach != null && attach.indexOf("book") != -1) {
            request.setAttribute("jump", "book");
            return "paysuc";
        }

        if (attach != null && attach.indexOf("buyIsOkPage") != -1) {
            return "buyIsOkPage";
        }

        if (attach != null && attach.indexOf("wfjjf") != -1) {
            return "paysuc";
        }
        if (attach != null && attach.indexOf("vip") != -1) {
            request.setAttribute("jump", "rz");
            request.setAttribute("comBz", goldOrderService.getComBz(out_trade_no));
            return "vipsuc";
        }

        if (attach != null && attach.equals("reward")) {
            Rewarddetail d = (Rewarddetail) icService.getReward(out_trade_no, null);
            request.setAttribute("uacount", d.getRdAcount());
            return "reward";
        }
        String shijian = "";
        PayBackupBill bc = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{out_trade_no});
        if (bc != null && "1".equals(bc.getHdpay())) {
            payDto = HTTPV3.searchOrder(out_trade_no);

        } else {
            // payDto = WchatPay.searchOrder(out_trade_no, "apppay");
            payDto = HTTPV3.serverQuery(out_trade_no);


        }

        shijian = payDto.getTime_end();
        try {
            morre = WeChatUtils.changeF2Y(payDto.getTotalFee());
        } catch (Exception e) {
            logger.error("操作异常", e);
        }

        request.setAttribute("shijian", shijian);
        ddid = out_trade_no;

        logger.info("订单号：: {}", ddid);
        logger.info("金额：: {}", morre);

        String mealNum = null;
        try {
            mealNum = goldOrderService.genMealNum(ddid);
            if (!"-1".equals(mealNum)) {
                request.setAttribute("mealNum", mealNum);
                request.setAttribute("tip", "购买成功");
                return "bindingSuccess";
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }


        return "wxcg";


    }


    /**
     * 微信支付成功后跳转成功或者失败页面（失败后跳回待付款页面）
     *
     * @return
     */
    public String jumpResult() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String goodsname = request.getParameter("goodsname");
        String out_trade_no = request.getParameter("ddid");
        String attach = request.getParameter("attach");
        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(
                httpSession, SessionWrap.KEY_SHOPCUSCOM);
        TEshopCustomer tct = (TEshopCustomer) sessionWrap.getObject(
                httpSession, SessionWrap.KEY_CUSTOMER);

        if (goodsname != null && goodsname.equals("微分金金币")) {

            return "goldpool";

        }

        if (attach != null && attach.indexOf("vip") != -1) {
            request.setAttribute("jump", "load");
            request.setAttribute("comBz", goldOrderService.getComBz(out_trade_no));
            return "vipsuc";
        }

        if (attach != null && attach.indexOf("selfpay") != -1) {
            CashierBills cashierBills =(CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?",new Object[]{out_trade_no});
            if(cashierBills!=null){
                if("智能货柜".equals(cashierBills.getGoodsName())){
                    request.setAttribute("cashierBillsID", cashierBills.getCashierBillsID());
                    request.setAttribute("sccId", cus!=null?cus.getSccId():"");


                }
            }

            return "paysuc";
        }
        if (attach != null && attach.indexOf("qyht") != -1) {
            return "qyht";
        }
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String shijian = formatter.format(currentTime);
        request.setAttribute("shijian", shijian);
        logger.info("订单号：: {}", ddid);
        logger.info("金额：: {}", morre);
        String mealNum = null;
        try {
            mealNum = goldOrderService.genMealNum(ddid);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        request.setAttribute("mealNum", mealNum);
        if (tct != null && (tct.getPassword() == null || tct.getPassword().equals(""))) {
            return "purchaseSuccess";

        } else {
            request.setAttribute("tip", "购买成功");

//			if (goodsname != null && goodsname.equals("微分金超市消费")) {
//
//				return "paysuc";
//
//			}
            return "bindingSuccess";
        }


    }

    /**
     * 定时查询生成了取餐号
     *
     * @return
     */
    public String ajaxMealNum() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ddid = request.getParameter("ddid");
        String hqls = "from CashierBills where journalNum = ?";
        CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqls, new Object[]{ddid});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", cc.getMealNum());
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";

    }

/////////////////////////////////////////////微信支付结束//////////////////////////////////////////////////


    //银联支付
    /*public void zfgs() {
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        HttpServletRequest request = ServletActionContext.getRequest();
        String buyIsOkPage = request.getParameter("buyIsOkPage");
        morre = new BigDecimal(morre).multiply(new BigDecimal(100)).toString();
        if (morre.contains(".")) {
            morre = morre.substring(0, morre.lastIndexOf("."));
        }

        if (ddid == null || ddid.length() == 0) {
            logger.error("银联----ddid不能为空=" + ddid);
        } else {

            unionpayMeth.frontConsume(baseUrl
                    + "/ea/wfjshop/ea_syncPay.jspa?journalNum=" + ddid + "&buyIsOkPage=" + buyIsOkPage, baseUrl
                    + "/ea/wfjshop/ea_asyncPay.jspa?journalNum=" + ddid, morre, txnTime, ddid);
        }


    }*/


    /**
     * 线下/钱盒子支付发短信
     */
    private void xMessage(TEshopCustomer cus, String goodsname) {

//        try {
//            msage.setMobiles(cus.getAccount());
//            msage.setMessage("微分金订单" + goodsname);
//            msage.sendMsg("【微分金平台】");
//        } catch (IOException e) {
//            logger.error("操作异常", e);
//        }
        logger.error("线下/钱盒子支付-----提醒人账号:" + cus.getAccount());
        //保存账号
        slist = new ArrayList<String>();
        slist.add(cus.getAccount());
        //极光推送
        JushMain.sendjiguangMessage("数字地球5L5C订单【" + goodsname + "】", "支付", "sellerindent", "defray", slist);
    }

    /**
     * 付款确认更改状态09
     */
    public String changeBillState() {
        if (ddid == null || ddid.length() < 5) {
            return Action.SUCCESS;
        }
        List<String> hqls = new ArrayList<String>();
        List<Object[]> parms = new ArrayList<Object[]>();
        String hql = "";
        String threeNo = "";

        String hqljp = "from PayCashierBill where oriJournalNum = ?";
        PayCashierBill pc = (PayCashierBill) baseBeanService.getBeanByHqlAndParams(hqljp, new Object[]{ddid});
        if (pc != null) {
            ddid = pc.getPayJournalNum();
        }

        //支付订单号
        String hqlpay = "from PayCashierBill where payJournalNum = ?";
        List<BaseBean> paylist = baseBeanService.getListBeanByHqlAndParams(hqlpay, new Object[]{ddid});

        if (fenlei != null && fenlei.equals("03")) {
            Long a = new Date().getTime();
            String b = a + "";
            threeNo = b.substring(6);
        }

        for (BaseBean pay : paylist) {
            PayCashierBill pcash = (PayCashierBill) pay;
            if (fenlei != null && fenlei.equals("03")) {
                hql = "update CashierBills set wfStatus4=?,trade_no=? where journalNum=?";
                hqls.add(hql);
                Object[] temp = new Object[3];
                temp[0] = "03";
                temp[1] = threeNo;
                temp[2] = pcash.getOriJournalNum();
                parms.add(temp);
            } else {

                hql = "update CashierBills set fkStatus=? where journalNum=?";
                hqls.add(hql);
                Object[] temp = new Object[2];
                temp[0] = "09";
                temp[1] = pcash.getOriJournalNum();
                parms.add(temp);

            }
        }

        baseBeanService.executeHqlsByParamsList(null, hqls.toArray(new String[]{}), parms);

        JSONObject obj = new JSONObject();
        obj.accumulate("threeNo", threeNo);
        obj.accumulate("succ", "success");
        if (fenlei == null) {
            TEshopCustomer cut = new TEshopCustomer();
            String userAccount = "15210904250";
            cut.setAccount(userAccount);
            xMessage(cut, ddid);
            logger.info("调试信息");
        }
        result = obj.toString();
        return Action.SUCCESS;
    }

    /**
     * 支付宝交易成功异步回调页面
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public void getzfb() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String body = "";
        try {
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                    if (name.equals("body")) {
                        body = valueStr;
                    }
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"),
                // "gbk");
                params.put(name, valueStr);
            }

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            // 商户订单号 单据表订单号
            String journalNum = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            String total_fee = new String(request.getParameter("total_fee").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            String seller_id = new String(request.getParameter("seller_id").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            logger.error(stackTraceElements[2].getLineNumber()+"total_fee----------------------"+total_fee);
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            if (AlipayNotify.verify(params)) {// 验证成功

                if (trade_status.equals("TRADE_SUCCESS")) {

                    if (seller_id.equals(AlipayConfig.seller_id)) {
                        if (body.indexOf("reward") != -1) {
                            rewardPay(trade_no, journalNum, "01", "");
                        }
                        if (body.indexOf("book") != -1) {
                            bookPay(trade_no, journalNum, "01", "");
                        }  else {

                            CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});

                            if (cashierBills != null && "智能货柜".equals(cashierBills.getGoodsName())) {
                                DtOrderBillAdd dtOrderBillAdd = (DtOrderBillAdd)baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum = ?",new Object[]{journalNum});
                                genSelfPay(trade_no, total_fee, dtOrderBillAdd.getOaSccId(), journalNum, "00", "", "");

                            } else {


                                String payhql = "from PayCashierBill where payJournalNum=?";
                                List<BaseBean> pcbs = baseBeanService.getListBeanByHqlAndParams(payhql, new String[]{journalNum});
                                if (pcbs == null || pcbs.size() == 0) {
                                    String pays = "from PayCashierBill where oriJournalNum=?";
                                    pcbs = baseBeanService.getListBeanByHqlAndParams(pays,
                                            new String[]{journalNum});
                                }
                                String hqls = "from CashierBills d where d.jNumOrder=? and d.billsType=?";
                                String hqld = "from CashierBills d where d.journalNum=?";
                                BigDecimal pricesum1 = new BigDecimal(0);
                                BigDecimal total_fee1 = new BigDecimal(total_fee);
                                for (int i = 0; i < pcbs.size(); i++) {
                                    PayCashierBill payc = (PayCashierBill) pcbs.get(i);
                                    CashierBills cbs = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqls,
                                            new String[]{payc.getOriJournalNum(), "项目收入预算单"});
                                    logger.error(stackTraceElements[2].getLineNumber()+"cbs.getJournalNum()----------------------"+cbs.getJournalNum());
                                    logger.error(stackTraceElements[2].getLineNumber()+"cbs.getProjectName()----------------------"+cbs.getProjectName());
                                    logger.error(stackTraceElements[2].getLineNumber()+"cbs.getPriceSub()----------------------"+cbs.getPriceSub());
                                    logger.error(stackTraceElements[2].getLineNumber()+"pricesum1----------------------"+pricesum1);
                                    pricesum1 = pricesum1.add(new BigDecimal(cbs.getPriceSub()));
                                }
                                if (total_fee1.compareTo(pricesum1) <= 0) {
                                    BigDecimal pricesum = new BigDecimal(0);
                                    for (int i = 0; i < pcbs.size(); i++) {
                                        PayCashierBill payc = (PayCashierBill) pcbs.get(i);
                                        CashierBills cbs = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqls,
                                                new String[]{payc.getOriJournalNum(), "收款单"});
                                        if (cbs == null) {// 未生成收款单
                                            CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqld,
                                                    new String[]{payc.getOriJournalNum()});
                                            pricesum = pricesum.add(new BigDecimal(cb.getPriceSub()));
                                        }
                                    }
                                    if (pricesum.compareTo(new BigDecimal(0)) > 0) {
                                        boolean bool = genCanYin(trade_no, journalNum, pricesum.toString(), "01", "");
                                        if (bool == false) {
                                            CashierBills c = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=?", new Object[]{journalNum});
                                            Enroll ee = (Enroll) baseBeanService.getBeanByHqlAndParams("from Enroll where cashierBillsID=?", new Object[]{c.getCashierBillsID()});
                                            if (ee != null && ee.getReserved1() != null && ee.getReserved1() != "") {
                                                goldOrderService.addMarKeting(ee);
                                            }
                                            // 生成收款单
                                            Boolean bo = goldOrderService.generateBill(trade_no, journalNum, pricesum.toString(), "01", "");

                                        try {
                                            //收款单生成后复制订单和收款单到新表
                                            goldOrderService.copyCash(journalNum, "d");
                                        }catch (Exception e){
                                            logger.error("操作异常", e);
                                            logger.info("复制订单收款单错误");
                                        }

                                        try {
                                            addPeriod(journalNum);//购买学时产品时增加学时
                                        } catch (Exception e) {
                                            logger.error("操作异常", e);
                                        }
                                    }
                                }
                            } else {
                                logger.error("实际金额是：" + pricesum1 + "---支付宝金额是:" + total_fee1);
                            }
                        }
                        response.getWriter().write("success");
                    }
                }
            } else {// 验证失败
                logger.info("验证失败");
                logger.error("验证失败");
                response.getWriter().write("fail");
            }
        }
        }catch (UnsupportedEncodingException e) {
            logger.error("操作异常", e);
        }catch (IOException e) {
            logger.error("操作异常", e);
        }
    }

    // 同步地址
    @SuppressWarnings("rawtypes")
    public String call_back() {
        HttpSession session = ServletActionContext.getRequest().getSession();

        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sessionWrap.getObject(
                session, SessionWrap.KEY_SHOPCUSCOM);

        HttpServletRequest request = ServletActionContext.getRequest();
        String pid = (String) session.getAttribute("ppid");
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        String body = null;
        try {
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                    if (name.equals("body")) {
                        body = valueStr;
                    }
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no")
                    .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 商户订单号
            String journalNum = new String(request.getParameter("out_trade_no").getBytes(
                    StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            morre = session.getAttribute("total").toString();

            //交易状态 String trade_status =new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            //计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);

            if (verify_result) {//验证成功
                String shijian = DateUtil.getCurrentDate("yyyy-MM-dd");
                request.setAttribute("shijian", shijian);
            } else {
                //该页面可做页面美工编辑
                logger.info("验证失败");
            }


            if (body != null && body.equals("vip")) {
                request.setAttribute("jump", "rz");
                request.setAttribute("comBz", goldOrderService.getComBz(trade_no));
                return "vipsuc";
            }


            String buyIsOkPage = request.getParameter("buyIsOkPage");
            if (buyIsOkPage != null && buyIsOkPage.equals("buyIsOkPage")) {
                return "buyIsOkPage";
            }
            if (body != null && body.equals("reward")) {
                Rewarddetail d = (Rewarddetail) icService.getReward(journalNum, null);
                request.setAttribute("uacount", d.getRdAcount());
                return "reward";
            }

            if (body != null && body.equals("book")) {
                request.setAttribute("jump", "book");
                return "paysuc";
            }


            // if (body != null && body.equals("selfpay")) {
            CashierBills cashierBills =(CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?",new Object[]{journalNum});
            if(cashierBills!=null){
                if("智能货柜".equals(cashierBills.getGoodsName())){
                    request.setAttribute("cashierBillsID", cashierBills.getCashierBillsID());
                    request.setAttribute("sccId", cus!=null?cus.getSccId():"");
                    return "paysuc";
                }

            }


            // }
            String mealNum = null;

            mealNum = goldOrderService.genMealNum(journalNum);
            if (!"-1".equals(mealNum)) {
                request.setAttribute("mealNum", mealNum);
                request.setAttribute("tip", "购买成功");
                return "bindingSuccess";
            }

        } catch (Exception e) {
            logger.error("操作异常", e);
        }

        return "cg";
    }


    /**
     * 判断是否选择过促销品
     *
     * @return
     */
    public String ajaxCxpcart() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        HttpServletRequest request = ServletActionContext.getRequest();
        String ppid = request.getParameter("ppid");

        Map<String, Object> map = new HashMap<String, Object>();
        if (cus == null) {
            if (posNum == null || posNum.equals("")) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                map.put("login", "login");
                JSONObject jo = JSONObject.fromObject(map);
                result = jo.toString();
                return "success";
            }
        }
        int cxpcart = 0;
        if (posNum != null && !posNum.equals("")) {
            String sqlcart = "select count(s.cspId) from DT_CartShopPromotion s where s.ppId = ? and s.posNum = ?";
            cxpcart = baseBeanService.getConutByBySqlAndParams(sqlcart, new Object[]{ppid, posNum});

        } else {
            String sqlcart = "select count(s.cspId) from DT_CartShopPromotion s where s.ppId = ? and s.staffId = ?";
            cxpcart = baseBeanService.getConutByBySqlAndParams(sqlcart, new Object[]{ppid, cus.getStaffid()});

        }
        map.put("cxpcart", cxpcart);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();


        return "success";
    }


    /**
     * 加入购物车产品详情页面上 点击加入购车 讲选择的产品就到购物车里面 1 成功加入购物车 -1 修改数量
     *
     * @return
     */
    public String setcity() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        HttpServletRequest request = ServletActionContext.getRequest();
        String stardard = request.getParameter("stardard");
        String ptppid = request.getParameter("ptppid");
        String ptstandard = request.getParameter("ptstandard");
        List<BaseBean> beans = new ArrayList<BaseBean>();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        JSONObject obj = new JSONObject();

        if (posNum != null && !posNum.equals("")) {//社区购物车
            String result = sumkSerivce.sqJoinCart(ppid, stardard, itemNum, itemNum, posNum, null, itemNum, "", "");
            if (!"0".equals(result) && !"-1".equals(result)) {
                sumkSerivce.joinCxp(ptppid, ptstandard, ppid, posNum, result);
                obj.accumulate("sect", "1");
            } else {
                obj.accumulate("sect", "-1");
            }

        } else {

            if (cus == null) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                obj.accumulate("login", "login");
                result = obj.toString();
                return "success";
            }
            String hqlcart = "from Cart where staffId=? and pid=? and stardard = ?";
            List<Object> param = new ArrayList<Object>();


            if (pos != null && pos.equals("1")) {

                hqlcart += " and pos = '1'";
            } else {
                hqlcart += " and (pos is null or pos = '0')";

            }
            Cart cat = (Cart) baseBeanService.getBeanByHqlAndParams(hqlcart, new Object[]{
                    cus.getStaffid(), ppid, stardard});
            if (cat == null) {
                StringBuffer hql = new StringBuffer();
                hql.append("select new ProSetup(e.comId,e.ppid,e.ppname,e.rePrice,pp.image as photoPath) ");
                hql.append(" from ProSetup e,ProductPackaging pp ");
                hql.append("where e.ppid=pp.ppID and e.ppid=?");
                ProSetup pp = (ProSetup) baseBeanService.getBeanByHqlAndParams(hql.toString(), new String[]{ppid});
                if (pp == null) {
                    pp = new ProSetup();
                }
                cat = new Cart();
                cat.setCartId(serverService.getServerID("cart"));
                cat.setCompanyId(pp.getComId());
                cat.setItemNum(Integer.parseInt(itemNum));
                cat.setPid(ppid);
                cat.setPname(pp.getPpname());
                cat.setPrice(pp.getRePrice());
                cat.setStaffId(cus.getStaffid());
                cat.setPic(pp.getPhotoPath());
                cat.setStardard(stardard);//规格
                cat.setPos((pos != null && pos.equals("1")) ? "1" : "0");//规格
                beans.add(cat);
                CartShopPromotion csp = null;

                if (ptppid != null && !ptppid.equals("")) {
                    String[] ptppids = ptppid.split(",");
                    String[] ptstandards = ptstandard.split(",,");
                    for (int i = 0; i < ptppids.length; i++) {
                        csp = new CartShopPromotion();
                        csp.setCspId(serverService.getServerID("cspId"));
                        csp.setPpId(ppid);
                        csp.setPptId(ptppids[i]);
                        csp.setPtstandard(ptstandards[i]);
                        csp.setPtCount(1);
                        csp.setCartId(cat.getCartId());
                        csp.setStaffId(cat.getStaffId());
                        beans.add(csp);
                    }
                }


                baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
                obj.accumulate("sect", "1");


            } else {
                String updatehql = "update Cart u set u.itemNum=u.itemNum+1 where u.staffId=? and u.pid=? and u.stardard = ?";
                if (pos != null && pos.equals("1")) {

                    updatehql += " and pos = '1'";
                } else {
                    updatehql += " and (pos is null or pos = '0')";

                }
                baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{updatehql}, new String[]{cus.getStaffid(), ppid, stardard});


                obj.accumulate("sect", "-1");
            }
        }
        result = obj.toString();

        return "success";
    }

    public String changeCartNum() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        if (cus == null) {
            if (posNum == null || posNum.equals("")) {
                return "login";
            }

        }

        String[] arr = lei.split("zz");
        String[] hqls = new String[arr.length];
        List<Object[]> parms = new ArrayList<Object[]>();
        String hql = null;
        if (posNum != null && !posNum.equals("")) {
            hql = "update SqSelfCart u set u.itemNum=?,u.sendNum = ?,u.showNum = ? where u.posNum=? and u.pid=? and stardard = ?";

        } else {
            hql = "update Cart u set u.itemNum=? where u.staffId=? and u.pid=? and stardard = ?";


        }
        for (int i = 0; i < hqls.length; i++) {
            hqls[i] = hql;
            String ppid = arr[i].substring(0, arr[i].indexOf("#"));
            String num = arr[i].substring(arr[i].indexOf("#") + 1, arr[i].indexOf("@"));
            String stardard = arr[i].substring(arr[i].indexOf("@") + 1);
            if (posNum != null && !posNum.equals("")) {
                parms.add(new Object[]{num, num, num, posNum, ppid, stardard});
            } else {
                parms.add(new Object[]{new BigDecimal(num).intValue(), cus.getStaffid(), ppid, stardard});

            }
        }
        baseBeanService.executeHqlsByParamsList(null, hqls, parms);
        result = "succ";
        return "success";
    }

    /**
     * 查询购物车 查询一个东西 |
     *
     * @return
     */
    public String getcity() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        String returnPage=request.getParameter("returnPage");
        if (cus == null) {
            if (posNum == null || posNum.equals("")) {
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                return "login";
            }
        }
        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();


        sql.append("select ps.ppid,p.goodsname,p.image,p.type,");
        sql.append("ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) pri,");
        sql.append("p.companyid,cm.companyName,round(c.itemNum),c.stardard,");
        if (posNum != null && !posNum.equals("")) {
            sql.append("c.sqId,");
        } else {
            sql.append("c.cart_Id,");
        }
        sql.append("ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2) actPrice,");
        if (posNum != null && !posNum.equals("")) {
            sql.append("null vip ");
        } else {
            sql.append("ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2) vip ");
        }
        sql.append(",pa.type activityType ");
        sql.append("from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
        if (posNum != null && !posNum.equals("")) {
            sql.append("inner join DT_SqSelfCart c ");
        } else {

            sql.append("inner join DT_cart c ");
        }
        sql.append("on p.ppid = c.pid ");
        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00' ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid  and pap.state ='00' ");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
        sql.append("where ps.fcom_id is null ");
        if (posNum != null && !posNum.equals("")) {
            sql.append("and c.posNum = ? ");
        } else {
            sql.append("and c.staff_id = ? ");
        }
        sql.append(" and ps.state='00' ");

        sql.append("and p.showweixin = ?");

        List<Object> param = new ArrayList<Object>();
        param.add(currentTime);
        param.add(currentTime);
        if (posNum != null && !posNum.equals("")) {
            param.add(posNum);
        } else {
            param.add(cus.getStaffid());
        }

        param.add("01");
        if (posNum == null || posNum.equals("")) {
            if (pos != null && pos.equals("1")) {

                sql.append(" and c.pos = ?");
                sql.append(" and c.companyId = ?");
                param.add("1");
                param.add(companyId);
            } else {
                sql.append(" and (pos is null or pos = ?)");
                param.add("0");

            }
        }


        if (state != null && state.equals("0")) {
            sql.append(" and c.companyId = ?");
            param.add(companyId);
        }

        List<Object> beanList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                param.toArray());

        List<String> comlist = new ArrayList<String>();
        List<Object> comps = new ArrayList<Object>();
        Map<String, List<BaseBean>> ccmap = new HashMap<String, List<BaseBean>>();
        sql.delete(0, sql.length());
        sql.append("select c.companyId,c.companyName,dc.ccompanyid,dc.logoPath,1+nvl(s.total_pct,0)/100");
        sql.append(" from dtCompany c left join dt_ccom_com cc on c.companyId = cc.compnay_id");
        sql.append(" left join dtContactCompany dc on dc.ccompanyId = cc.ccompany_Id");
        sql.append(" left join dt_set_subsidize s");
        sql.append(" on dc.industrytype = s.gtid and s.stutas = ? where c.companyId = ?");

        for (int i = 0; i < beanList.size(); i++) {
            Object obj = beanList.get(i);

            Object[] objs = (Object[]) obj;

            Object objcom = baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{"01", objs[5].toString()});

            if (!comlist.contains(objs[5].toString())) {
                comlist.add(objs[5].toString());
                comps.add(objcom);
            }

            //查询选择的促销品
            List<BaseBean> clist = null;
            if (posNum != null && !posNum.equals("")) {
                String sqlcart = "select s.ppId,s.pptId,s.ptstandard,p.companyId,p.goodsname,p.price,p.image,s.ptCount from DT_CartShopPromotion s left join dt_ProductPackaging p on p.ppID = s.pptId where s.ppId = ? and s.posNum = ?";
                clist = baseBeanService.getListBeanBySqlAndParams(sqlcart, new Object[]{objs[0].toString(), posNum});
            } else {
                String sqlcart = "select s.ppId,s.pptId,s.ptstandard,p.companyId,p.goodsname,p.price,p.image,s.ptCount from DT_CartShopPromotion s left join dt_ProductPackaging p on p.ppID = s.pptId where s.ppId = ? and s.staffId = ?";
                clist = baseBeanService.getListBeanBySqlAndParams(sqlcart, new Object[]{objs[0].toString(), cus.getStaffid()});
            }

            if (clist.size() != 0) {
                ccmap.put(objs[0].toString(), clist);
            }

        }

        if (tc != null && tc.getCusType().compareTo("6") < 0) {//判断用户会员等级[小于6级时商品展示vip价]
            request.setAttribute("cusType", "vip");
        }
        //统计产品
        ActionContext.getContext().getValueStack().set("beanList", beanList);
        ActionContext.getContext().getValueStack().set("comps", comps);
        ActionContext.getContext().getValueStack().set("num", beanList.size());
        ActionContext.getContext().getValueStack().set("ccmap", ccmap);
        if (returnPage != null&&returnPage.equals("01")) {
            return "shopList";
        }else {
            return "newcity";
        }
    }

    /**
     * 删除购物车 PPID 当前用户
     */
    public String delecity() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        if (cus == null) {
            if (posNum == null || posNum.equals("")) {
                return "login";
            }
        }
        String cartid = request.getParameter("cartid");
        JSONObject obj = new JSONObject();
        List<String> hqls = new ArrayList<String>();
        List<Object[]> parms = new ArrayList<Object[]>();

        if (posNum != null && !posNum.equals("")) {
            String hql = "from SqSelfCart where sqId = ?";
            SqSelfCart cart = (SqSelfCart) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cartid});
            String hqlp = "from SqSelfCart c where c.posNum = ? and c.pid = ?";
            List<BaseBean> listc = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{posNum, cart.getPid()});

            if (listc.size() == 1) {
                //清空购物车促销品
                String hqldeletecsp = "delete from CartShopPromotion p where  p.posNum = ? and p.ppId = ?)";
                hqls.add(hqldeletecsp);
                Object[] temp1 = new Object[2];
                temp1[0] = posNum;
                temp1[1] = cart.getPid();
                parms.add(temp1);
            }
            //清空购物车
            String hqldelete = "delete from SqSelfCart where sqId = ?";
            hqls.add(hqldelete);
            Object[] temp2 = new Object[1];
            temp2[0] = cartid;
            parms.add(temp2);

        } else {
            String hql = "from Cart where cartId = ?";
            Cart cart = (Cart) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cartid});
            String hqlp = "from Cart c where c.staffId = ? and c.pid = ?";
            List<BaseBean> listc = baseBeanService.getListBeanByHqlAndParams(hqlp, new Object[]{cart.getStaffId(), cart.getPid()});

            if (listc.size() == 1) {
                //清空购物车促销品
                String hqldeletecsp = "delete from CartShopPromotion p where  p.staffId = ? and p.ppId = ?)";
                hqls.add(hqldeletecsp);
                Object[] temp1 = new Object[2];
                temp1[0] = cart.getStaffId();
                temp1[1] = cart.getPid();
                parms.add(temp1);
            }
            //清空购物车
            String hqldelete = "delete from Cart where cartId = ?";
            hqls.add(hqldelete);
            Object[] temp2 = new Object[1];
            temp2[0] = cartid;
            parms.add(temp2);
        }

        try {
            baseBeanService.executeHqlsByParamsList(null, hqls.toArray(new String[]{}), parms);
            obj.accumulate("succes", "1");
        } catch (Exception e) {
            logger.error("操作异常", e);
            obj.accumulate("succes", "-1");
        }
        result = obj.toString();

        return "success";

    }

    /**
     * 功能概括 为微分金注册提供选择代理商接口 查询出所有代理商 的账号跟名字 公司:01 店主：02 04 代理商 05合伙创业 06消费者
     */
    @SuppressWarnings("unchecked")
    public String getdllist() {

        String sql = "select t.account,d.staffname from  dt_hr_staff d,T_ESHOP_CUSCOM t where t.staffid=d.staffid and t.custype=?";
        beans = baseBeanService.getListBeanBySqlAndParams(sql,
                new Object[]{sort});
        JSONObject jja = new JSONObject();
        List<Object> list = new ArrayList<Object>();
        for (Object obj1 : beans) {
            JSONObject jj = new JSONObject();
            Object[] obj = (Object[]) obj1;
            jj.accumulate("ID", obj[0]);
            jj.accumulate("name", obj[1]);
            list.add(jj);
        }
        jja.accumulate("list", list);
        result = jja.toString();
        return "success";

    }

    /**
     * 金币公告
     *
     * @return http://localhost:8080/hyplat/ea/wfjshop/sajax_ea_getjinbi.jspa
     */
    @SuppressWarnings({"unchecked"})
    public String getjinbi() {
        List<Object> a = baseBeanService.getListBeanBySqlAndParams("select w.wfj_jifen_id from dt_wfj_jifen w,t_eshop_cuscom t" +
                " where w.staff_id=t.staffid" +
                " and ( t.account='15810799888' or t.custype='0.5' or t.custype='1')", null);

        String sql = "select jd.jifen_detail_score,s.staffname"
                + " from dt_wfj_jifen_detail jd,dt_wfj_jifen j,dt_hr_staff s"
                + " where jd.wfj_jifen_id=j.wfj_jifen_id and j.staff_id=s.staffid and j.wfj_jifen_score>?";

        for (int i = 0; i < a.size(); i++) {
            sql += " and j.wfj_jifen_id != ?";
        }
        a.add(0, 0);

        sql += " and rownum<=500 order by jd.jifen_detail_date";
        List<Object> detail = baseBeanService
                .getListBeanBySqlAndParams(
                        sql, a.toArray());
        JSONObject jja = new JSONObject();
        List<Object> list = new ArrayList<Object>();
        for (Object obj1 : detail) {
            JSONObject jj = new JSONObject();
            Object[] obj = (Object[]) obj1;
            if (obj[0] == null || obj[1] == null) {
                continue;
            }
            jj.accumulate("num", obj[0]);
            jj.accumulate("name", obj[1]);
            list.add(jj);
        }
        jja.accumulate("list", list);
        result = jja;
        return "success";
    }


    /**
     * 检测组织机构名字是否重复
     *
     * @return
     */
    public String getcompispmun() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyIdentifier = request.getParameter("companyIdentifier");
        String companyName = request.getParameter("companyName");
        String type = request.getParameter("type");
        Company c = null;
        if (type.equals("name")) {
            String hql2 = "from Company where companyName=?";
            c = (Company) baseBeanService.getBeanByHqlAndParams(hql2,
                    new String[]{companyName});

        } else {
            String hql1 = "from Company where companyIdentifier=?";

            c = (Company) baseBeanService.getBeanByHqlAndParams(hql1,
                    new String[]{companyIdentifier});
        }
        if (c == null) {

            result = "0";//不存在

        } else {
            result = "1";

        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     * 店铺搜索
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String searchShops() {
        // 按信誉排序
        List<Object> parms = new ArrayList<Object>();
        String sql = "select co.organizationid, co.organizationname,sh.star,sh.titleimage,sh.owner,sh.regdate,co.companyID,"
                + " (select st.num from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as num,"
                + " (select st.imagepath from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as imagepath "
                + " from dtcorganization co,T_ESHOP_EXTINFO sh  "
                + " where co.organizationid=sh.organizationid and co.iswfj='是' "; // and
        // sh.eshopstatus='0'
        if ("searchShops".equals(search)
                && !corganization.getOrganizationName().equals("")) {
            sql += " and co.organizationName like ?";
            parms.add("%" + corganization.getOrganizationName() + "%");
        }
        if (companyId != null && !"".equals(companyId)
                && !"null".equals(companyId)) {
            sql += " and co.companyID=?";
            parms.add(companyId);
        }
        sql += " order by sh.star desc ";
        shopList = baseBeanService.getListBeanBySqlAndParams(sql,
                parms.toArray());
        return "wfjshops";
    }

    /*
     * 收货人地址管理ljc
     */

    public String getAddressList() {
        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        if (user != null && !"".equals(user)) {
            TEshopCustomer customer = (TEshopCustomer) baseBeanService
                    .getBeanByHqlAndParams(
                            "from TEshopCustomer where account=? AND logOff=0",
                            new Object[]{user});
            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                    "from Staff where staffID=? ",
                    new Object[]{customer.getStaffid()});
            StaffAddress staffAddress = (StaffAddress) baseBeanService
                    .getBeanByHqlAndParams(
                            "from StaffAddress where staffID=? and isDefault='是'",
                            new Object[]{customer.getStaffid()});

            sessionWrap.setObject(httpSession, SessionWrap.KEY_STAFF, staff);
            sessionWrap.setAddress(httpSession, staffAddress == null ? ""
                    : staffAddress.getAddressDetailed());
            sessionWrap.setObject(httpSession, SessionWrap.KEY_CUSTOMER,
                    customer);
            sessionWrap.setObject(httpSession, SessionWrap.KEY_ADDRESS,
                    staffAddress);
        }
        intf = ServletActionContext.getRequest().getParameter("intf");
        pid = ServletActionContext.getRequest().getParameter("pid");
        TEshopCustomer teshopCustomer = (TEshopCustomer) sessionWrap.getObject(
                httpSession, SessionWrap.KEY_CUSTOMER);
        String hql = "from StaffAddress where staffID = ?";

        if (staffid != null && !staffid.equals("")) {
            staffAddressList = baseBeanService.getListBeanByHqlAndParams(hql,
                    new Object[]{staffid});
        } else if (teshopCustomer != null) {
            staffAddressList = baseBeanService.getListBeanByHqlAndParams(hql,
                    new Object[]{teshopCustomer.getStaffid()});
        }
        if (stype != null) {
            return "restaurantAddress";
        } else {
            return "addressList";
        }
    }

    public String sendMsg() {
        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCustomer teshopCustomer = (TEshopCustomer) sessionWrap.getObject(
                httpSession, SessionWrap.KEY_CUSTOMER);
        if (teshopCustomer.getAccount().length() == 11) {
//            try {
//                msage.setMobiles(teshopCustomer.getAccount());
//                msage.setMessage("您的线下转账识别码为：" + ddid + "，请转账时在备注信息中回填识别码！以方便进行到账审核！");
//                msage.sendMsg("【微分金平台】");
//                result = "success";
//
//            } catch (Exception e) {
//                logger.info("发送短信失败");
//            }
        }

        return Action.SUCCESS;
    }

    //收货地址管理
    public String toDetail() {
        if (staffAddress != null && staffAddress.getAddressID() != null) {
            String hql = "from StaffAddress where addressID = ?";
            staffAddress = (StaffAddress) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staffAddress.getAddressID()});
            ActionContext.getContext().getValueStack().push(staffAddress);
        }
        return "toDetail";
    }

    //删除收货地址
    public String delAddr() {
        if (staffAddress != null && staffAddress.getAddressID() != null) {
            String hql = "delete from StaffAddress where addressID=?";
            baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{staffAddress.getAddressID()});
            return personAddress();
        } else {
            return "toDetail";
        }
    }

    // 增加收货地址跳转
    public String toaddAddress() {
        if (flag != null && flag.equals("recive"))
            return "toaddrecive";
        return "toadd";
    }

    // 增加收货地址跳转[餐饮的]
    public String shippingAddress() {
        if (stype != null) {
            return "Address";
        } else {
            logger.info("else");
            return "toadd";
        }
    }


    //会员名片 收货地址管理
    public String personAddress() {
        if (user != null && user.length() > 1) {
            String hql = "select e from StaffAddress e ,TEshopCusCom ust ,Staff s where s.staffID=ust.staffid and ust.staffid=e.staffID and ust.sccId=?";
            staffAddressList = baseBeanService.getListBeanByHqlAndParams(hql,
                    new Object[]{user});
        }

        ActionContext.getContext().getValueStack().set("user", user);
        return "perAdd";
    }
    //个人名片添加收货地址

    /**
     * 表TEshopCusCom的sccid
     *
     * @param
     * @return
     */
    public String addPersonAddr() {
        TEshopCusCom teshopcuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom cc where cc.sccId=?", new Object[]{user});
        if (teshopcuscom != null) {
            int ret = baseBeanService.getConutByByHqlAndParams("select count(s) from StaffAddress s where s.staffID = ?", new Object[]{teshopcuscom.getStaffid()});
            StaffAddress staffAddressAdd = new StaffAddress();
            staffAddressAdd.setStaffID(teshopcuscom.getStaffid());
            staffAddressAdd.setCompanyID(teshopcuscom.getCompanyId());
            staffAddressAdd.setAddressID(serverService.getServerID("StaffAddress"));
            staffAddressAdd.setAddressDetailed(addressDetailed);
            staffAddressAdd.setArea(area);
            staffAddressAdd.setConsignee(consignee);
            staffAddressAdd.setPhone(phone);
            staffAddressAdd.setPostCode(postCode);
            if (ret == 0) {
                staffAddressAdd.setIsDefault("是");
            }
            baseBeanService.save(staffAddressAdd);
        }
        return personAddress();
    }

    public String changeDefault() {
        if (staffAddress != null && staffAddress.getAddressID() != null && staffAddress.getAddressID().length() > 1) {
            String hql = "update StaffAddress u set u.isDefault='' where u.staffID=?";
            String hql1 = "update StaffAddress u set u.isDefault='是' where u.addressID=?";
            List<Object[]> parm = new ArrayList<Object[]>();
            parm.add(new Object[]{staffAddress.getStaffID()});
            parm.add(new Object[]{staffAddress.getAddressID()});
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql1}, parm);
        }
        return Action.SUCCESS;
    }

    // 增加收货地址
    public String addAddress() {
        HttpSession httpSession = ServletActionContext.getRequest()
                .getSession();
        SessionWrap sessionWrap = SessionWrap.getInstance();
        TEshopCustomer teshopCustomer = (TEshopCustomer) sessionWrap.getObject(
                httpSession, SessionWrap.KEY_CUSTOMER);
        TEshopCusCom teshopcuscom = (TEshopCusCom) sessionWrap.getObject(httpSession,
                SessionWrap.KEY_SHOPCUSCOM);
        if (staffid != null && !staffid.equals("")) {
            teshopCustomer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid = ?", new Object[]{staffid});
            teshopcuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where staffid = ? and acquiesce = ?", new Object[]{staffid, "01"});
        }
//		String hql1 = "from TEshopCusCom cc where cc.account=?";
//		TEshopCusCom teshopcuscom = (TEshopCusCom) baseBeanService
//				.getBeanByHqlAndParams(hql1,
//						new Object[] { teshopCustomer.getAccount() });
        String hql = "from StaffAddress where staffID = ?";
        if (teshopCustomer != null) {
            staffAddressList = baseBeanService.getListBeanByHqlAndParams(hql,
                    new Object[]{teshopCustomer.getStaffid()});
        }
        StaffAddress staffAddressAdd = new StaffAddress();
        if (staffAddressList != null && staffAddressList.size() == 0) {
            staffAddressAdd.setStaffID(teshopcuscom.getStaffid());
            staffAddressAdd.setCompanyID(teshopcuscom.getCompanyId());
            staffAddressAdd.setAddressID(serverService
                    .getServerID("StaffAddress"));
            staffAddressAdd.setAddressDetailed(addressDetailed);
            staffAddressAdd.setArea(area);
            staffAddressAdd.setConsignee(consignee);
            staffAddressAdd.setPhone(phone);
            staffAddressAdd.setPostCode(postCode);
            staffAddressAdd.setIsDefault("是");
        } else {
            staffAddressAdd.setStaffID(teshopcuscom.getStaffid());
            staffAddressAdd.setCompanyID(teshopcuscom.getCompanyId());
            staffAddressAdd.setAddressID(serverService
                    .getServerID("StaffAddress"));
            staffAddressAdd.setAddressDetailed(addressDetailed);
            staffAddressAdd.setArea(area);
            staffAddressAdd.setConsignee(consignee);
            staffAddressAdd.setPhone(phone);
            staffAddressAdd.setPostCode(postCode);
        }
        baseBeanService.save(staffAddressAdd);
        return getAddressList();
    }

    /*
     * 省市区列表
     * */
    public String getCitiesList() {
        String s = null;
        String url = "http://v.juhe.cn/postcode/pcd";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json

        try {
            s = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(s);
            if (object.getInt("error_code") == 0) {
                result = object.toString();

            } else {
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        return "success";
    }

    /*
     * 邮编查询 ljc 可以删除2016.08.05
     * */
    public String getZipCode() {
        String s = null;
        String url = "http://v.juhe.cn/postcode/search";//请求接口地址
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
        params.put("pid", pid);//省份ID
        params.put("cid", cid);//城市ID
        params.put("did", did);//区域ID
        params.put("q", q);//关键词
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json

        try {
            s = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(s);
            if (object.getInt("error_code") == 0) {
                result = object.toString();
            } else {
            }
        } catch (Exception e) {
            logger.error("getZipCode  error");
        }

        return "success";
    }

    public static String net(String strUrl, Map<String, Object> params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    logger.info("值：{}", e);
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            logger.error("操作异常", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                logger.error("操作异常", e);
            }
        }
        return sb.toString();
    }


    /**
     * 获取钱盒子支付信息表单
     *
     * @return
     */
    public String moneyBoxPay() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String hql = "from DtOrderBillAdd where oaBillJounum = ?";
        DtOrderBillAdd order = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ddid});
        request.setAttribute("order", order);

        return "moneybox";
    }

    /**
     * 钱盒子确认提交
     *
     * @return
     */
    public String confirmMoneyBox() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String hql = "from CashierBills where journalNum = ?";
        CashierBills cashier = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ddid});
        if (!"09".equals(cashier.getFkStatus())) {
            cashier.setBoxPayName(cbills.getBoxPayName());
            cashier.setBoxPayTel(cbills.getBoxPayTel());
            cashier.setBackName(cbills.getBackName());
            cashier.setUserAccountNum(cbills.getUserAccountNum());
            cashier.setTrade_no(cbills.getTrade_no());
            cashier.setTradeDate(cbills.getTradeDate());
            cashier.setSnNum(cbills.getSnNum());
            cashier.setRemark(cbills.getRemark());
            cashier.setFkStatus("09");
            cashier.setWfStatus4("04");
            cashier.setBoxDate(new Date());
            baseBeanService.update(cashier);
        }
        request.setAttribute("cashier", cashier);
        TEshopCustomer cut = new TEshopCustomer();
        String userAccount = "15210904250";
        cut.setAccount(userAccount);
        xMessage(cut, ddid);
        return "dsh";
    }

    //验证
    public String yanzhengzg() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject ret = new JSONObject();
        if (cus == null) {
            ret.accumulate("productp", "login");
            result = ret.toString();
            return "success";
        }
        //验证是否买过省县村代理资格
        String ppids = request.getParameter("ppids");
        String hqlpro = " from TEshopCuscomSub where account = ? and tyepPpid = ? ";
        TEshopCuscomSub product = (TEshopCuscomSub) baseBeanService.getBeanByHqlAndParams(hqlpro, new Object[]{cus.getAccount(), ppids});
        //购买过的判断
        if (product != null) {
            ret.accumulate("productp", "productp");
            result = ret.toString();
            return "success";
        }
        ret.accumulate("productp", "productpt");
        result = ret.toString();
        return "success";
    }

    /**
     * 收藏商品
     *
     * @return
     */
    public String collectGoods() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        Map<String, Object> map = new HashMap<String, Object>();
        if (cus == null) {
            String url = request.getHeader("Referer");
            session.setAttribute("url", url);
            map.put("login", "login");
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";

        }
        String re = goldOrderService.collectGoods(ppid, pricetype, cus.getStaffid());

        map.put("result", re);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 获取收藏的商品
     *
     * @return
     */
    public String getCollectGoodsList() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String parameter = request.getParameter("parameter");
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        pageForm = goldOrderService.getCollectGoodsList(
                (null != pageForm ? pageForm.getPageNumber() : 1), 20, parameter, cus.getStaffid());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 获取收藏店铺
     *
     * @return
     */
    public String getCollectShopList() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        String parameter = request.getParameter("parameter");
        pageForm = goldOrderService.getCollectShopsList(
                (null != pageForm ? pageForm.getPageNumber() : 1), 20, parameter, cus.getStaffid());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 批量取消收藏商品
     *
     * @return
     */
    public String batchCCGoods() {
        String gcids = request.getParameter("gcids");
        String[] gcIds = gcids.split(",");
        goldOrderService.batchCCGoods(gcIds);
        return "success";

    }


    /**
     * 批量取消收藏店铺
     *
     * @return
     */
    public String batchCCShops() {

        String jfids = request.getParameter("jfids");
        String[] jfIDs = jfids.split(",");
        goldOrderService.batchCCShops(jfIDs);
        return "success";
    }

    /**
     * V3服务商模式查询订单状态
     *
     * @return
     */
    public String serverQuery() {

        WxPayDto payDto = HTTPV3.serverQuery(journalNum);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trade_state", payDto.getTrade_state());
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";

    }
    /**
     *
     *  http://47.94.207.152/ea/wfjshop/ea_createTable.jspa?companyID=company201009046vxdyzy4wg0000000015&year=2024
      */

    public String createTable(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyID = request.getParameter("companyID");
        String year = request.getParameter("year");
        TableRalate tableRalate = goldOrderService.createTableDealData(companyID,"CashierBills",year);
        logger.info("调试信息");
        return "success";
    }


    /**
     *
     * http://47.94.207.152/ea/wfjshop/ea_copyData.jspa?companyID=company201009046vxdyzy4wg0000000015&year=2024&nyr=2024
     * @return
     */
    public String copyData(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyID = request.getParameter("companyID");
        String year = request.getParameter("year");
        String nyr = request.getParameter("nyr");
         goldOrderService.copyData(companyID,year,nyr);
        logger.info("执行完成");
        return "success";
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<BaseBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<BaseBean> shopList) {
        this.shopList = shopList;
    }

    public List<BaseBean> getProductList() {
        return productList;
    }

    public void setProductList(List<BaseBean> productList) {
        this.productList = productList;
    }

    public String getWeidiantype() {
        return weidiantype;
    }

    public void setWeidiantype(String weidiantype) {
        this.weidiantype = weidiantype;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public Agencies getAgencies() {
        return agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
    }

    public List<BaseBean> getBeans() {
        return beans;
    }

    public void setBeans(List<BaseBean> beans) {
        this.beans = beans;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public TEshopExtinfo getShopExtinfo() {
        return shopExtinfo;
    }

    public StaffAddress getStaffaddress() {
        return staffaddress;
    }

    public void setStaffaddress(StaffAddress staffaddress) {
        this.staffaddress = staffaddress;
    }

    public void setShopExtinfo(TEshopExtinfo shopExtinfo) {
        this.shopExtinfo = shopExtinfo;
    }

    public COrganization getCorganization() {
        return corganization;
    }

    public void setCorganization(COrganization corganization) {
        this.corganization = corganization;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public ProductPackaging getProductDesign() {
        return productDesign;
    }

    public void setProductDesign(ProductPackaging productDesign) {
        this.productDesign = productDesign;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public Company getCompany() {
        return company;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<BaseBean> getNum() {
        return num;
    }

    public void setNum(List<BaseBean> num) {
        this.num = num;
    }

    public String getGoodname() {
        return goodname;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getGetip() {
        return getip;
    }

    public void setGetip(String getip) {
        this.getip = getip;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getMorre() {
        return morre;
    }

    public void setMorre(String morre) {
        this.morre = morre;
    }

    public String getLei() {
        return lei;
    }

    public void setLei(String lei) {
        this.lei = lei;
    }

    public CDetail getCdl() {
        return cdl;
    }

    public void setCdl(CDetail cdl) {
        this.cdl = cdl;
    }

    public String getFenlei() {
        return fenlei;
    }

    public String getJournalNum() {
        return journalNum;
    }

    public void setJournalNum(String journalNum) {
        this.journalNum = journalNum;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndus() {
        return indus;
    }

    public void setIndus(String indus) {
        this.indus = indus;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<CCode> getProType() {
        return proType;
    }

    public Company getComp() {
        return comp;
    }

    public void setComp(Company comp) {
        this.comp = comp;
    }


    public String getWeiid() {
        return weiid;
    }

    public void setWeiid(String weiid) {
        this.weiid = weiid;
    }

    public List<Object> getObjbeanns() {
        return objbeanns;
    }

    public void setObjbeanns(List<Object> objbeanns) {
        this.objbeanns = objbeanns;
    }

    public void setProType(List<CCode> proType) {
        this.proType = proType;
    }

    public StaffAddress getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(StaffAddress staffAddress) {
        this.staffAddress = staffAddress;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddressDetailed() {
        return addressDetailed;
    }

    public void setAddressDetailed(String addressDetailed) {
        this.addressDetailed = addressDetailed;
    }

    public List<BaseBean> getStaffAddressList() {
        return staffAddressList;
    }

    public void setStaffAddressList(List<BaseBean> staffAddressList) {
        this.staffAddressList = staffAddressList;
    }

    public List<BaseBean> getFunctionlist() {
        return functionlist;
    }

    public void setFunctionlist(List<BaseBean> functionlist) {
        this.functionlist = functionlist;
    }

    public Map<String, String> getMaplist() {
        return maplist;
    }

    public void setMaplist(Map<String, String> maplist) {
        this.maplist = maplist;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ProSetup getProsetup() {
        return prosetup;
    }

    public void setProsetup(ProSetup prosetup) {
        this.prosetup = prosetup;
    }


    public PageForm getPageForm() {
        return pageForm;
    }


    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }


    public List<BaseBean> getList() {
        return list;
    }


    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public Map<Integer, String[]> getMaplist1() {
        return maplist1;
    }

    public void setMaplist1(Map<Integer, String[]> maplist1) {
        this.maplist1 = maplist1;
    }

    public Object getObject1() {
        return object1;
    }

    public void setObject1(Object object1) {
        this.object1 = object1;
    }

    public List<BaseBean> getList1() {
        return list1;
    }

    public void setList1(List<BaseBean> list1) {
        this.list1 = list1;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public List<BaseBean> getListsize() {
        return listsize;
    }

    public void setListsize(List<BaseBean> listsize) {
        this.listsize = listsize;
    }

    public List<BaseBean> getListcolor() {
        return listcolor;
    }

    public void setListcolor(List<BaseBean> listcolor) {
        this.listcolor = listcolor;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getTypeLeix() {
        return typeLeix;
    }

    public void setTypeLeix(String typeLeix) {
        this.typeLeix = typeLeix;
    }

    public String getTypeNews() {
        return typeNews;
    }

    public void setTypeNews(String typeNews) {
        this.typeNews = typeNews;
    }

    public List<Object> getCclist() {
        return cclist;
    }

    public void setCclist(List<Object> cclist) {
        this.cclist = cclist;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {

        this.judge = judge;
    }

    public String getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(String promotionID) {
        this.promotionID = promotionID;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getGoodsBillsID() {
        return goodsBillsID;
    }

    public void setGoodsBillsID(String goodsBillsID) {
        this.goodsBillsID = goodsBillsID;
    }

    public String getCashierBillsID() {
        return cashierBillsID;
    }

    public void setCashierBillsID(String cashierBillsID) {
        this.cashierBillsID = cashierBillsID;
    }

    public String getPtppid() {
        return ptppid;
    }

    public void setPtppid(String ptppid) {
        this.ptppid = ptppid;
    }

    public CashierBills getCbills() {
        return cbills;
    }

    public void setCbills(CashierBills cbills) {
        this.cbills = cbills;
    }

    public String getPtmorre() {
        return ptmorre;
    }

    public void setPtmorre(String ptmorre) {
        this.ptmorre = ptmorre;
    }

    public String getPtstandard() {
        return ptstandard;
    }

    public void setPtstandard(String ptstandard) {
        this.ptstandard = ptstandard;
    }

    public FinalPackage getFinalPackage() {
        return finalPackage;
    }

    public void setFinalPackage(FinalPackage finalPackage) {
        this.finalPackage = finalPackage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<String> getSlist() {
        return slist;
    }

    public void setSlist(List<String> slist) {
        this.slist = slist;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public String getIndusty() {
        return industy;
    }

    public void setIndusty(String industy) {
        this.industy = industy;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public WxPayDto getPayDto() {
        return payDto;
    }

    public void setPayDto(WxPayDto payDto) {
        this.payDto = payDto;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getPpids() {
        return ppids;
    }

    public void setPpids(String ppids) {
        this.ppids = ppids;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public CarInformation getCf() {
        return cf;
    }

    public void setCf(CarInformation cf) {
        this.cf = cf;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(String enrollId) {
        this.enrollId = enrollId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }
}
