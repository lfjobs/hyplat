package hy.ea.finance.action.BenDis;

import com.tiantai.wfj.bo.Cart;
import com.tiantai.wfj.bo.SqSelfCart;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.RestaurantService;
import com.tiantai.wfj.service.WfjAccountService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.WxUserInfo;
import com.wechat.utils.WeixinUtil;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BenDis.OperatorInfo;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.office.CarManage;
import hy.ea.bo.production.AttriProduction;
import hy.ea.bo.production.GoodFunction;
import hy.ea.finance.service.AssiCartService;
import hy.ea.finance.service.SetSubsidizeService;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.ea.marketing.service.SupermarketSerivce;
import hy.ea.office.service.CarManageService;
import hy.ea.office.service.MakeAppointmentService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.ToChineseFirstLetter;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
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
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 餐饮
 */
@Controller
@Scope("prototype")
public class RestaurantAction {
	private static final Logger logger = LoggerFactory.getLogger(RestaurantAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private MakeAppointmentService mappService;
    @Resource
    private WfjAccountService wfjAccountService;
    @Resource
    private AssiCartService assiCartService;
    @Resource
    private SetSubsidizeService setSubsidizeService;
    @Resource
    private SuperSelfSerivce smSerivce;
    @Resource
    private CarManageService cmService;

    @Resource
    private SupermarketSerivce sumkSerivce;//mz
    private Logger logger = LoggerFactory.getLogger(RestaurantAction.class);

    private Cart cart;
    private TEshopCustomer customer;
    private TEshopCusCom shopCusCom;
    private List<BaseBean> list;
    private List<BaseBean> lists;
    private List<BaseBean> personLists;
    private Map<String, Object> map;
    private String userAccount;//当前登录账户的账号
    private StaffAddress staffaddress;// 购买地址
    private StaffAddress staffAddress;// 地址管理 //购买的地址
    private PageForm pageForm;
    private PageForm pageFormto;
    private int pageNumber;
    private String result;
    private String parentName;
    private String type;
    private String companyId;
    private String ccompanyId;
    private String privateRoom;//餐厅
    private String waiter;//服务员
    private String money;
    private String pid;
    private double mon;
    private String ajax;//餐饮服务员的判断
    private String goodsid;
    private int number;
    private File txtFile;
    private String scancode;//扫出来的码
    private ContactCompany contactCompany;
    private String sccid;
    private String staffId;
    private Object results;
    private String code;//微信授权返回
    private String tj;//推荐人sccid
    private String stardard;//规格
    private String posNum;//终端编号
    private String ppid;
    private Integer itemNum;//购物车对应商品数量
    private String totalNum; //加入购物车数量
    private String sendNum;//待配送数量
    private String showNum;
    private String barCode;

    /**
     * 查询菜单
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String products() {
        List<Object> params = new ArrayList<Object>();
        List<Object> params1 = new ArrayList<Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> map = new HashMap<String, Object>();
//        if (cus == null) {
//            String url = request.getHeader("Referer");
//            session.setAttribute("url", url);
//            map.put("login", "login");
//            JSONObject json = JSONObject.fromObject(map);
//            this.result = json.toString();
//            return "success";
//        }
        if (companyId == null || companyId.equals("")) {
            if (ccompanyId == null || ccompanyId.equals("")) {
                return "success";
            }
            CcomCom com = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?",
                    new Object[]{ccompanyId});
            if (com == null) {
                return "success";
            }
            companyId = com.getComanyId();
        }
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, String> mapat = new HashMap<String, String>();
        Map<String, String> gapat = new HashMap<String, String>();

        SetSubsidize setSubsidize = (SetSubsidize) setSubsidizeService.setSubsidizeByType(ccompanyId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(new Date());//获取当前时间
        if (parentName != null && !parentName.equals("菜品")) {
            params.add(currentTime);
            params.add(currentTime);
            params.add(companyId);
            params.add("01");
            StringBuilder sql = new StringBuilder();
            sql.append("select p.image,p.goodsName,p.ppID,ps.re_price,p.goodsID,p.monthSales, ");
            sql.append("p.categoryid,pap.actPrice,pv.vip,pa.type from DT_PRO_SETUP ps ");
            sql.append("inner join dt_ProductPackaging p on p.ppID=ps.ppid ");
            sql.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00' ");
            sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
            sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
            sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sql.append("and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
            sql.append("where p.companyID = ? and p.showweixin= ? and ps.state ='00' ");
            if (!"nullType".equals(request.getParameter("parentId"))) {
                sql.append("  and p.categoryid = ? ");
                params.add(request.getParameter("parentId"));
            } else {
                sql.append("  and p.categoryid is null");
            }
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 100 : pageNumber), sql.toString(), "select count(*) from (" + sql + ")", params.toArray());

            List<BaseBean> temp = new ArrayList<BaseBean>();

            if (posNum != null && !posNum.equals("")) {
                String hql = "from SqSelfCart s where s.posNum = ? and s.companyId = ?";
                temp = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{posNum, companyId});

            } else {
                if (cus != null) {
                    String hql = " from Cart c where c.staffId = ? and c.companyId=?";
                    temp = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cus.getStaffid(), companyId});
                }
            }
            Map<String, Integer> tempMap = new HashMap<String, Integer>();
            Map<String, String> gMap = new HashMap<String, String>();
            for (int i = 0; i < temp.size(); i++) {
                if (posNum != null && !posNum.equals("")) {
                    SqSelfCart e = (SqSelfCart) temp.get(i);
                    tempMap.put(e.getPid(), Math.round(Float.parseFloat(e.getItemNum())));
                    gMap.put(e.getPid(), e.getStardard());
                } else {
                    Cart e = (Cart) temp.get(i);
                    tempMap.put(e.getPid(), e.getItemNum());
                    gMap.put(e.getPid(), e.getStardard());
                }
            }
            if (pageForm != null && pageForm.getList() != null && pageForm.getList().size() > 0) {
                for (Object object : pageForm.getList()) {
                    Object[] obj = (Object[]) object;
                    if (tempMap.keySet().contains(obj[2].toString())) {
                        map1.put(obj[2].toString(), tempMap.get(obj[2].toString()));
                        gapat.put(obj[2].toString(), gMap.get(obj[2].toString()));
                    } else {
                        map1.put(obj[2].toString(), 0);
                        gapat.put(obj[2].toString(), "");
                    }
                }
            }
            String hql2 = "select at from AttriProduction at,ProductPackaging dt where at.goodsid=dt.goodsID and dt.companyID = ?";
            List<BaseBean> attr = baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{companyId});
            Map<String, String> attrMap = new HashMap<String, String>();
            for (int i = 0; i < attr.size(); i++) {
                AttriProduction at = (AttriProduction) attr.get(i);
                attrMap.put(at.getGoodsid(), at.getAttrivalue());
            }
            if (pageForm != null && pageForm.getList() != null && pageForm.getList().size() > 0) {
                for (Object object : pageForm.getList()) {
                    Object[] obj = (Object[]) object;
                    if (attrMap.keySet().contains(obj[4].toString())) {
                        mapat.put(obj[4].toString(), attrMap.get(obj[4].toString()));
                    } else {
                        mapat.put(obj[4].toString(), "");
                    }
                }
            }
        } else {

            // 查分类
            String sql = "from dt_schprocategory y where y.companyid = ? and y.status = ? and y.categoryId in(select p.categoryId from dt_productpackaging p where p.companyId = ?)";
            params.add(companyId);
            params.add("2");
            params.add(companyId);
            lists = baseBeanService.getListBeanBySqlAndParams(
                    "select y.categoryname,y.categoryid " + sql, params.toArray());

            if (posNum != null && !posNum.equals("")) {

                // 计算购物车商品总价 //
                StringBuilder sqlp = new StringBuilder();
                sqlp.append("select ps.ppid,p.goodsname,p.image,p.type,ps.re_price,p.companyid,c.itemNum,");
                sqlp.append("c.stardard,pap.actPrice,null,pa.type activityType ");
                sqlp.append("from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state ='00'");
                sqlp.append("inner join DT_SqSelfCart c on p.ppid = c.pid ");
                sqlp.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid ");
                sqlp.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00'");
                sqlp.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
                sqlp.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                sqlp.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
                sqlp.append("where c.companyId = ? and  posNum = ? and p.showweixin = ? ");

                list = baseBeanService.getListBeanBySqlAndParams(sqlp.toString(),
                        new Object[]{currentTime, currentTime, companyId, posNum, "01"});

            } else {
                if (cus != null) {
                    // 计算购物车商品总价 //
                    StringBuilder sqlp = new StringBuilder();
                    sqlp.append("select ps.ppid,p.goodsname,p.image,p.type,ps.re_price,p.companyid,c.itemNum,");
                    sqlp.append("c.stardard,pap.actPrice,pv.vip,pa.type activityType ");
                    sqlp.append("from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                    sqlp.append("inner join DT_cart c on p.ppid = c.pid ");
                    sqlp.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
                    sqlp.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00' ");
                    sqlp.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
                    sqlp.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                    sqlp.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
                    sqlp.append("where c.companyId = ? and c.staff_Id = ? and p.showweixin = ?  and ps.state ='00'");

                    list = baseBeanService.getListBeanBySqlAndParams(sqlp.toString(),
                            new Object[]{currentTime, currentTime, companyId, cus.getStaffid(), "01"});
                } else {
                    String url = request.getHeader("Referer");
                    session.setAttribute("url", url);
                    map.put("login", "login");
                    JSONObject json = JSONObject.fromObject(map);
                    this.result = json.toString();
                    return "success";
                }
            }


            Float pct = 1f;
            if (setSubsidize != null) {
                pct = Float.parseFloat(setSubsidize.getTotalPct() + "") / 100 + 1;
            }
            Map<String, Object> tempMap = new HashMap<String, Object>();
            if (list != null && list.size() != 0) {//购物车不为空
                BigDecimal sumprice = new BigDecimal(0);
                for (int i = 0; i < list.size(); i++) {
                    Object ppk = list.get(i);
                    Object[] objmap = (Object[]) ppk;
                    //计算所有产品总金额
                    BigDecimal num = new BigDecimal(objmap[6].toString());
                    BigDecimal dpct = new BigDecimal(pct);
                    //价格类别判断
                    if (objmap[10] != null && objmap[8] != null) {//活动价相关
                        BigDecimal price = new BigDecimal(objmap[8].toString());
                        BigDecimal cc = price.multiply(num).multiply(dpct).setScale(2, BigDecimal.ROUND_HALF_UP);
                        sumprice = sumprice.add(cc);
                    } else {//用户会员等级[小于6级时商品展示vip价]
                        if ((posNum==null||posNum.equals(""))&&objmap[9] != null && tc.getCusType().compareTo("6") < 0) {//VIP价相关
                            BigDecimal price = new BigDecimal(objmap[9].toString());
                            BigDecimal cc = price.multiply(num).multiply(dpct).setScale(2, BigDecimal.ROUND_HALF_UP);
                            sumprice = sumprice.add(cc);
                        } else {//零售价相关
                            BigDecimal price = new BigDecimal(objmap[4].toString());
                            BigDecimal cc = price.multiply(num).multiply(dpct).setScale(2, BigDecimal.ROUND_HALF_UP);
                            sumprice = sumprice.add(cc);
                        }
                    }

                }
                mon = sumprice.doubleValue();
                List<Object> temp = new ArrayList<Object>();
                if (posNum != null && !posNum.equals("")) {
                    String sql1 = "select p.categoryid,sum(c.itemnum) from  DT_SqSelfCart c join dt_productpackaging p on c.pid = p.ppid where  c.companyID = ? and c.posNum = ?  and p.showweixin= ? and p.categoryid is not null group by p.categoryid";
                    temp = baseBeanService.getListBeanBySqlAndParams(sql1,
                            new Object[]{companyId, posNum, "01"});
                } else {
                    String sql1 = "select p.categoryid,sum(c.itemnum) from  dt_cart c join dt_productpackaging p on c.pid = p.ppid where  c.companyID = ? and  c.staff_id= ? and p.showweixin= ? and p.categoryid is not null group by p.categoryid";
                    temp = baseBeanService.getListBeanBySqlAndParams(sql1,
                            new Object[]{companyId, cus.getStaffid(), "01"});
                }


                if (temp != null && temp.size() > 0) {
                    for (int i = 0; i < temp.size(); i++) {
                        Object[] arr = (Object[]) temp.get(i);
                        if (arr[0] != null && !"".equals(arr[0])) {
                            tempMap.put(arr[0].toString(), arr[1]);
                        }
                    }
                }
            }
            for (Object object : lists) {
                Object[] obje = (Object[]) object;
                if (tempMap.keySet().contains(obje[1].toString())) {
                    map2.put(obje[1].toString(), tempMap.get(obje[1].toString()));
                } else {
                    map2.put(obje[1].toString(), 0);
                }
            }

        }
        map.put("setsubsidize", setSubsidize);
        map.put("pageForm", pageForm);
        map.put("lists", lists);
        map.put("map1", map1);
        map.put("map2", map2);
        map.put("mon", mon);
        map.put("list", list);
        map.put("mapat", mapat);
        map.put("gapat", gapat);
        if(posNum==null||posNum.equals("")) {
            if (tc.getCusType().compareTo("6") < 0) {//判断用户会员等级[小于6级时商品展示vip价]
                map.put("cusType", "vip");
            }
        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";

    }

    //公司产品未分类
    public String getNotTypeByProduct() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
        String sql = "select p.image,p.goodsName,p.ppID,pro.re_price,p.goodsID,p.monthSales,p.categoryid " +
                "from DT_PRO_SETUP pro join dt_ProductPackaging p on p.ppID=pro.ppID where p.companyID = ? and p.showweixin= ? and  p.categoryid is null";
        List<BaseBean> count = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyId, "01"});

        String sql1 = "select p.categoryid,sum(c.itemnum) from  dt_cart c join dt_productpackaging p on c.pid = p.ppid where  c.companyID = ? and c.staff_id= ? and p.showweixin= ? and p.categoryid is null group by p.categoryid";

        List<Object> temp = baseBeanService.getListBeanBySqlAndParams(sql1,
                new Object[]{companyId, cus != null ? cus.getStaffid() : "", "01"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        map.put("temp", temp);
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }


    /**
     * 小数乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {        // 进行乘法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 加入购物车
     */
    public String gateringOrders() {
        if (posNum != null && !posNum.equals("")) {//社区购物车

            sumkSerivce.sqJoinCart(cart.getPid(), stardard, "1", sendNum, posNum, barCode, showNum,"","");
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            String hql = " from Cart where pid = ? and staffId = ? ";
            if(cus==null){
                String url = request.getHeader("Referer");
                session.setAttribute("url", url);
                 Map<String, Object> map = new HashMap<String, Object>();
                 map.put("nologin", "nologin");
                 JSONObject json = JSONObject.fromObject(map);
                 this.result = json.toString();
                 return "success";
            }

            Cart cc = (Cart) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cart.getPid(), cus.getStaffid()});
            if (cc == null) {
                Cart ca = new Cart();
                ca.setCartId(serverService.getServerID("cart"));
                ca.setCompanyId((String) request.getParameter("companyId"));
                ca.setStaffId(cus.getStaffid());
                ca.setPid(cart.getPid());
                ca.setItemNum(1);
                ca.setPos("0");
                ca.setPname(cart.getPname());
                ca.setPic(cart.getPic());
                ca.setStardard(stardard);
                baseBeanService.save(ca);
            } else {
                String hql2 = " from Cart where pid = ? and staffId = ? and stardard=?";

                Cart tcc = (Cart) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{cart.getPid(), cus.getStaffid(), stardard});
                if (tcc == null) {
                    Cart ca = new Cart();
                    ca.setCartId(serverService.getServerID("cart"));
                    ca.setCompanyId((String) request.getParameter("companyId"));
                    ca.setStaffId(cus.getStaffid());
                    ca.setPid(cart.getPid());
                    ca.setItemNum(1);
                    ca.setPos("0");
                    ca.setPname(cart.getPname());
                    ca.setPic(cart.getPic());
                    ca.setStardard(stardard);
                    baseBeanService.save(ca);
                }
            /*else if(cart.getItemNum()==0){
                baseBeanService.deleteBeanByKey(Cart.class, cc.getCartKey());
			}*/
                else {
                    String hql3 = " from Cart where pid = ? and staffId = ? and stardard=?";

                    Cart tcr = (Cart) baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{cart.getPid(), cus.getStaffid(), stardard});
                    if (tcr != null) {
                        int Num = tcr.getItemNum();
                        int item = Num + 1;
                        tcr.setItemNum(item);
                        baseBeanService.update(tcr);
                    }
                }
            }
        }
        return "success";

    }

    /**
     * 加入购物车
     */
    public String gateringOrders2() {
        if (posNum != null && !posNum.equals("")) {//社区购物车
            sumkSerivce.sqReduceCart(cart.getPid(), stardard, "1", sendNum, posNum, barCode, showNum);
        } else {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            String hql = " from Cart where pid = ? and staffId = ? and stardard=?";

            Cart tcr = (Cart) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cart.getPid(), cus.getStaffid(), stardard});
            if (tcr != null) {
                int Num = tcr.getItemNum();
                int item = Num - 1;
                if (item == 0) {
                    baseBeanService.deleteBeanByKey(Cart.class, tcr.getCartKey());
                } else {
                    tcr.setItemNum(item);
                    baseBeanService.update(tcr);
                }
            }
        }
        return "success";
    }

    /**
     * 清除购物车
     */
    public String clearCart() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        String staffid = "";
        if (posNum == null || posNum.equals("")) {
            if(cus!=null) {
                staffid = cus.getStaffid();
                logger.info("值：{}", staffid);
            }
        }
        String[] ppid = pid.split(",");
        if (ppid != null) {
            for (int i = 0; i < ppid.length; i++) {
                String star = ppid[i].trim();
                logger.info("值：{}", star);
                if (posNum != null && !posNum.equals("")) {
                    String hql = " from SqSelfCart where pid = ? and posNum = ?";
                    SqSelfCart cc = (SqSelfCart) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{star, posNum});
                    if (cc != null) {
                        String hql2 = "delete from SqSelfCart where pid = ? and posNum = ?";
                        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql2}, new Object[]{star, posNum});
                    }
                } else {
                    String hql = " from Cart where pid = ? and staffId = ?";
                    Cart cc = (Cart) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{star, staffid});
                    if (cc != null) {
                        String hql2 = "delete from Cart where pid = ? and staffId = ?";
                        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql2}, new Object[]{star, staffid});
                    }
                }
            }
        }

        return "success";
    }

    /**
     * 查询购物车
     */
    public String findCar() {
        List<Object> params = new ArrayList<Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
        Map<String, Object> map = new HashMap<String, Object>();
        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());

        StringBuilder sql = new StringBuilder();
        if (posNum != null && !posNum.equals("")) {
            sql.append("select p.image,p.goodsName,p.ppID,ps.re_price,p.goodsID,p.monthSales,");
            sql.append("p.categoryid,c.stardard,round(c.itemNum),pap.actPrice,null,pa.type ");
            sql.append("from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state ='00'");
            sql.append("inner join DT_SqSelfCart c on p.ppid = c.pid ");
            sql.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00'");
            sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00'");
            sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
            sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
            sql.append("where c.companyId=? and c.posNum=? ");
            params.add(currentTime);
            params.add(currentTime);
            params.add(companyId);
            params.add(posNum);
        } else {
            sql.append("select p.image,p.goodsName,p.ppID,ps.re_price,p.goodsID,p.monthSales,");
            sql.append("p.categoryid,c.stardard,c.itemNum,pap.actPrice,pv.vip,pa.type ");
            sql.append("from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state ='00'");
            sql.append("inner join DT_cart c on p.ppid = c.pid ");
            sql.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00'");
            sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
            sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
            sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
            sql.append("where c.companyId=? and c.staff_id=? and ps.state ='00' ");
            params.add(currentTime);
            params.add(currentTime);
            params.add(companyId);
            params.add(cus.getStaffid());
        }

        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 100 : pageNumber),
                sql.toString(), "select count(*) from (" + sql + ")", params.toArray());


        SetSubsidize setSubsidize = (SetSubsidize) setSubsidizeService.setSubsidizeByType(ccompanyId);
        map.put("setsubsidize", setSubsidize);
        map.put("pageForm", pageForm);
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 查询订单
     */
    @SuppressWarnings("unchecked")
    public String queryLoginName() {
        HttpServletRequest request = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        HttpSession session = request.getSession();
        String cardNum = request.getParameter("cardNum");
        String mode = request.getParameter("mode");
        String sccId = request.getParameter("sccId");

        TEshopCustomer cus = null;
        TEshopCusCom scc = null;
        if (cardNum != null && !cardNum.equals("")) {
            scc = smSerivce.getTEshopByCum(cardNum);
            cus = (TEshopCustomer) baseBeanService
                    .getBeanByHqlAndParams(
                            "from TEshopCustomer where account=? AND logOff=0",
                            new Object[]{scc.getAccount()});

        } else if(sccId!=null&&!sccId.equals("")){
            scc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            if(scc!=null) {
                cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{scc.getAccount()});
            }
        }else {
            cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            scc = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
        }
        if (cus == null) {
            if (posNum == null || posNum.equals("")) {
                return "login";
            }
        }
        if(!"cash".equals(mode)) {
            if (staffAddress == null) {
                if (cus != null) {
                    String staffaddhql = "from StaffAddress where staffID=? and isDefault='是'";
                    staffAddress = (StaffAddress) baseBeanService
                            .getBeanByHqlAndParams(staffaddhql,
                                    new String[]{cus.getStaffid()});
                }
            } else {
                if (staffAddress.getAddressID() != null && !staffAddress.getAddressID().equals("")) {
                    String staffaddhql = "from StaffAddress where  addressID=? ";
                    staffAddress = (StaffAddress) baseBeanService
                            .getBeanByHqlAndParams(staffaddhql,
                                    new String[]{staffAddress.getAddressID()});
                }
            }
        }
        String companyId = request.getParameter("companyId");

//		 Calendar c = Calendar.getInstance();
//		//过去一月
//		c.setTime(new Date());
//		c.add(Calendar.MONTH, -1);
//		Date m = c.getTime();
//
//
//		StringBuffer sqlbuff = new StringBuffer();
//		sqlbuff.append("select jd.jifen_detail_score, s.staffname");
//		sqlbuff.append(" from dt_wfj_jifen_detail jd left join dt_wfj_jifen j on  jd.wfj_jifen_id = j.wfj_jifen_id left join dt_hr_staff s");
//		sqlbuff.append(" on j.staff_id = s.staffid where");
//		sqlbuff.append(" j.wfj_jifen_id <> ?");
//		sqlbuff.append(" and j.wfj_jifen_id <> ?");
//		sqlbuff.append(" and j.wfj_jifen_id <> ?");
//		sqlbuff.append(" and j.wfj_jifen_score>? and rownum<=10 and jd.jifen_detail_date  >  ? order by jd.jifen_detail_date desc");
//
//         //公告
//         List<Object> detail=baseBeanService
// 				.getListBeanBySqlAndParams(
// 						sqlbuff.toString(),
// 						new Object[] { "WfjJifen20161011DK7H8QO41E4810668837",
// 								"WfjJifen20160617W2P2E6Y2ZF3940047359",
// 								"WfjJifen2016061769BUC38LXJ6522668250",0,m});
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());//获取当前时间
        StringBuilder sqlp = new StringBuilder();
        List<Object> ppklist = new ArrayList<Object>();
        if (posNum != null && !posNum.equals("")) {
            sqlp.append("select ps.ppid,p.goodsname,p.image,p.type,ps.re_price,p.companyid,c.itemNum,");
            sqlp.append("c.stardard,pap.actPrice,null,pa.type activityType,ps.suid,");
            sqlp.append("ps.ef_price lscc,pap.actpriceid,pap.factory hdcc,pv.vipid,pv.factory vipcc ");
            sqlp.append("from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state ='00'");
            sqlp.append("inner join DT_SqSelfCart c on p.ppid = c.pid ");
            sqlp.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
            sqlp.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00'");
            sqlp.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
            sqlp.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sqlp.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
            sqlp.append("where c.companyId = ? and c.posNum = ? and p.showweixin = ? ");

            ppklist = baseBeanService.getListBeanBySqlAndParams(sqlp.toString(),
                    new Object[]{currentTime, currentTime, companyId, posNum, "01"});
        } else {
            sqlp.append("select ps.ppid,p.goodsname,p.image,p.type,ps.re_price,p.companyid,c.itemNum,");
            sqlp.append("c.stardard,pap.actPrice,pv.vip,pa.type activityType,ps.suid,");
            sqlp.append("ps.ef_price lscc,pap.actpriceid,pap.factory hdcc,pv.vipid,pv.factory vipcc ");
            sqlp.append("from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
            sqlp.append("inner join DT_cart c on p.ppid = c.pid ");
            sqlp.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
            sqlp.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00' ");
            sqlp.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
            sqlp.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sqlp.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
            sqlp.append("where c.companyId = ? and c.staff_Id = ? and p.showweixin = ?  ");
            sqlp.append(" and ps.state ='00' ");
            ppklist = baseBeanService.getListBeanBySqlAndParams(sqlp.toString(),
                    new Object[]{currentTime, currentTime, companyId, cus.getStaffid(), "01"});
        }

        String hqlcom = "from Company where companyID = ?";
        Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom, new Object[]{companyId});
        SetSubsidize setSubsidize = (SetSubsidize) setSubsidizeService.setSubsidizeByType(ccompanyId);
        Float pct = 1f;
        if (setSubsidize != null) {
            pct = Float.parseFloat(setSubsidize.getTotalPct() + "") / 100 + 1;
        }
        BigDecimal sumprice = new BigDecimal(0);
        for (int i = 0; i < ppklist.size(); i++) {
            Object ppk = ppklist.get(i);
            Object[] objmap = (Object[]) ppk;

            //计算所有产品总金额
            BigDecimal num = new BigDecimal(objmap[6].toString());
            BigDecimal dpct = new BigDecimal(pct);
            //价格类别判断
            if (objmap[10] != null && objmap[8] != null) {//活动价相关
                BigDecimal price = new BigDecimal(objmap[8].toString());
                BigDecimal cc = price.multiply(num).multiply(dpct).setScale(2, BigDecimal.ROUND_HALF_UP);
                sumprice = sumprice.add(cc);
            } else {
                if (objmap[9] != null) {//VIP价相关
                    BigDecimal price = new BigDecimal(objmap[9].toString());
                    BigDecimal cc = price.multiply(num).multiply(dpct).setScale(2, BigDecimal.ROUND_HALF_UP);
                    sumprice = sumprice.add(cc);
                } else {//零售价相关
                    BigDecimal price = new BigDecimal(objmap[4].toString());
                    BigDecimal cc = price.multiply(num).multiply(dpct).setScale(2, BigDecimal.ROUND_HALF_UP);
                    sumprice = sumprice.add(cc);
                }
            }

        }

        request.setAttribute("company", company);
        request.setAttribute("total", sumprice + "");
        request.setAttribute("ppklist", ppklist);
        //request.setAttribute("jf", detail);
        request.setAttribute("staffID", cus != null ? cus.getStaffid() : "");
        request.setAttribute("user", cus != null ? cus.getAccount() : "");

        request.setAttribute("sccid", scc != null ? scc.getSccId() : "");
        String url = request.getRequestURL() + "?" + request.getQueryString();
        request.setAttribute("callurl", url);
        request.setAttribute("pct", pct);
        return "queryLoginName";
    }

    // 查询，服务员和包间
    public String getChoice() {
        Map<String, Object> map = new HashMap<String, Object>();
        // 餐厅
        String sqlo = "select da.goodsName,da.photoPath,da.remark,da.goodsid"
                + " from dt_pro_eqpm_dist pro,dt_pro_eqpm_dist_to_good t,dtGoodsManage da"
                + " where t.goodid=da.goodsid and pro.ped_id=t.pedid  and pro.goodsname='菜品' and (da.typeid= ? or da.typeid=?) and pro.companyid=? order by typeid ";

        // 服务员
        String sql = "select dt.staffName,dt.headimage,dt.staffCode "
                + " from dt_hr_Staff dt,dtCos cos"
                + " where dt.staffid = cos.staffid and cos.companyID = ? and cos.cosStatus = ? and cos.status = ?";


        if ("pp".equals(ajax)) {
            pageForm = baseBeanService.getPageFormBySQL(pageNumber, 5, sqlo,
                    "select count(*)" + sqlo.substring(sqlo.indexOf("from")),
                    new Object[]{"VIP", "大厅", companyId});

            pageFormto = baseBeanService.getPageFormBySQL(pageNumber, 10,
                    sql,
                    "select count(*)" + sql.substring(sql.indexOf("from")),
                    new Object[]{companyId, "50", "01"});


            return "choice";
        } else {

            if (ajax.equals("waiter")) {

                pageForm = baseBeanService.getPageFormBySQL(pageNumber, 10,
                        sql,
                        "select count(*)" + sql.substring(sql.indexOf("from")),
                        new Object[]{companyId, "50", "01"});

            } else {

                pageForm = baseBeanService.getPageFormBySQL(
                        pageNumber,
                        5,
                        sqlo,
                        "select count(*)"
                                + sqlo.substring(sqlo.indexOf("from")),
                        new Object[]{"VIP", "大厅", companyId});

            }

            // 餐厅
            map.put("pageForm", pageForm);//包间
            //map.put("pageFormto", pageFormto);//大厅
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
    }

    @SuppressWarnings("unchecked")
    public String getPuy() {
        String hql = "select da.goodsName,dt.imgurl,da.remark from  dtGoodsManage da,Dtattriproduction dt where da.goodsID=dt.goodsid and da.goodsId=?";
        list = baseBeanService.getListBeanBySqlAndParams(hql,
                new Object[]{goodsid});
        // 获取功能信息
        String hqlfun = "from GoodFunction where goodsid = ? order by orders";
        List<BaseBean> functionlist = baseBeanService
                .getListBeanByHqlAndParams(hqlfun, new Object[]{goodsid});

        // 获取每个功能信息的具体内容
        String buffer = "";
        for (int i = 0; i < functionlist.size(); i++) {
            GoodFunction goodFun = (GoodFunction) functionlist.get(i);
            buffer += getContentFromFile(goodFun.getUrl());
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("name", buffer);
        return "puy";
    }

    /**
     * 根据txt URL 获取内容
     *
     * @param filepath
     * @return
     */
    private String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\")
                + filepath;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            return contentToFileService.getContent(path);

        } catch (IOException e) {
            logger.error("操作异常", e);
            return "";
        }


    }

	/*public void addProduct() throws IOException{
        Map<String,Object> map=new HashMap<String, Object>();
		List<BaseBean> list=new ArrayList<BaseBean>();
		InputStreamReader reader = new InputStreamReader(
				new FileInputStream(txtFile)); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		line = br.readLine();
		int ir=0;
		while(line != null){
			ir++;
			String[] strs=line.split(",");
			String pinyin = ToChineseFirstLetter.getFirstLetter("组织机构");
			String Upstr = pinyin.toUpperCase();// 转换为大写
			DecimalFormat form = new DecimalFormat("000000");
			String ss = form.format(ir + 1);
			GoodsManage goods=new GoodsManage();
			goods.setGoodsID(serverService.getServerID("GoodsManage"));
			goods.setCompanyID("company201009046vxdyzy4wg0000000025");
			goods.setGoodsCoding(Upstr + "_" + ss);
			goods.setGoodsName(strs[0]);
			goods.setTypeID("其他");
			goods.setStandard("组织机构");
			goods.setModel("组织机构");
			goods.setGoodsState("00");
			goods.setSubjectsName("库存商品");
			goods.setSubjectsID("无");
			goods.setTradeCode("C10323软件");
			goods.setTradeName("C103软件网络计算机>C10323软件");
			goods.setTradeID("scode20150815wygb79q82p0000000113");
			list.add(goods);


			ProductPackaging pp=new ProductPackaging();
			pp.setPpID(serverService.getServerID("ProductPackaging"));
			pp.setGoodsID(goods.getGoodsID());
			pp.setCompanyID("company201009046vxdyzy4wg0000000025");
			pp.setGoodsName(strs[0]);
			pp.setQuantity("1");
			pp.setMoney("10");
			pp.setPrice("10");
			if(strs.length>1){
				if(!"2".equals(strs[1])){
					pp.setParentId(map.get(strs[1]).toString());
					pp.setParentName(strs[1]);
				}else{
					pp.setParentId(strs[2]);
					pp.setParentName(strs[3]);
				}
			}
			pp.setTradeCode("无");
			pp.setProducttype("组织机构");
			pp.setProductCode("BH0002000"+ir);
			pp.setTradeCode("C10323软件");
			pp.setTradeName("C103软件网络计算机>C10323软件");
			pp.setTradeID("scode20150815wygb79q82p0000000113");
			pp.setModel("组织机构");
			pp.setBrand("组织机构");
			pp.setVariableID("组织机构");
			pp.setSubjectName("库存商品");
			pp.setSubjectID("无");
			pp.setDelStatus("01");
			pp.setProductstate("00");
			map.put(strs[0], pp.getPpID());
			list.add(pp);
			line = br.readLine();
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
	}
	*/


    public void addProduct() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseBean> list = new ArrayList<BaseBean>();
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(txtFile)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        int ir = 0;
        String sjid = "";
        String sjname = "";
        String erjid = "";
        String erjname = "";
        while ((line = br.readLine()) != null) {
            ir++;
            String[] strs = line.split(",");
            if (ir == 1) {
                sjid = strs[0].trim();//记录第一行上级ID
                sjname = strs[1].trim();//记录第一行上级name
                continue;
            }


            for (int i = 0; i < strs.length; i++) {
                String pinyin = ToChineseFirstLetter.getFirstLetter("县域");
                String Upstr = pinyin.toUpperCase();// 转换为大写
                DecimalFormat form = new DecimalFormat("000000");
                String ss = form.format(ir + 1);
                GoodsManage goods = new GoodsManage();
                goods.setGoodsID(serverService.getServerID("GoodsManage"));
                goods.setCompanyID("company201009046vxdyzy4wg0000000025");
                goods.setGoodsCoding(Upstr + "_" + ss);
                goods.setGoodsName(strs[i].trim());
                goods.setTypeID("联营经济平台");
                goods.setStandard("县域");
                goods.setModel("县域");
                goods.setGoodsState("00");
                goods.setSubjectsName("库存商品");
                goods.setSubjectsID("无");
                goods.setTradeCode("C10323软件");
                goods.setTradeName("C103软件网络计算机>C10323软件");
                goods.setTradeID("scode20150815wygb79q82p0000000113");
                list.add(goods);


                ProductPackaging pp = new ProductPackaging();
                pp.setPpID(serverService.getServerID("ProductPackaging"));
                pp.setGoodsID(goods.getGoodsID());
                pp.setCompanyID("company201009046vxdyzy4wg0000000025");
                pp.setGoodsName(strs[i].trim());
                pp.setQuantity("1");
                pp.setMoney("1");
                pp.setPrice("1");
                if (strs.length == 1) {
                    erjid = pp.getPpID();//记录二级ID
                    erjname = strs[0].trim();//记录二级name
                    pp.setParentId(sjid);
                    pp.setParentName(sjname);
                    pp.setImage("upload_files/company201009046vxdyzy4wg0000000065/gooddesign/2016-07-26/f9e5944c7abf4a5aadda057d57e31111.jpg");

                } else {
                    pp.setParentId(erjid);
                    pp.setParentName(erjname);
                    pp.setImage("upload_files/company201009046vxdyzy4wg0000000065/gooddesign/2016-07-26/f9e5944c7abf4a5aadda057d57e312222.jpg");

                }


                goods.setPhotoPath(pp.getImage());
                pp.setProducttype("县域");
                pp.setProductCode("C10323" + ir);
                pp.setTradeCode("C10323软件");
                pp.setTradeName("C103软件网络计算机>C10323软件");
                pp.setTradeID("scode20150815wygb79q82p0000000113");
                pp.setModel("县域");
                pp.setBrand("县域");
                pp.setVariableID("县域");
                pp.setSubjectName("库存商品");
                pp.setSubjectID("无");
                pp.setDelStatus("00");
                pp.setProductstate("00");
                pp.setType("联营经济平台");
                pp.setFiveClear("4");

                list.add(pp);

            }


        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
    }


    /**
     * 扫码跳转
     *
     * @return
     */
    public String scancode() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String url = request.getRequestURL() + "?" + request.getQueryString();
        String realpath = request.getRealPath("/");
        SessionWrap sw = SessionWrap.getInstance();
        HttpSession session = request.getSession();
        String returntype = "";
        TEshopCusCom cus = null;
        String pos = request.getParameter("pos");

        if(!"pos".equals(pos)) {
            if (code != null && !code.equals("")) {
                String urlmicro = wfjAccountService.isWxLogin(request, url);
                if (!"".equals(urlmicro)) {
                    System.out.print("code:" + code);
                    logger.error("code:" + code);
                    WxUserInfo wxUserInfo = WeixinUtil.getWxUserInfoByCode(code, "wxa1b3f84c027804c3");
                    if (wxUserInfo == null) {
                        System.out.print("wxUserInfo:" + wxUserInfo);
                        logger.error("wxUserInfo:" + wxUserInfo);
                    }
                    String canzuo = request.getParameter("canzuo");
                    if (canzuo != null && !canzuo.equals("")) {

                        String pid = scancode.substring(2);
                        tj = wfjAccountService.getTjBycanzuo(pid);

                    }
                    sccid = wfjAccountService.createAccount(wxUserInfo, tj, realpath);
                }
            }




            if (sccid != null && !sccid.equals("")) {
                cus = (TEshopCusCom) baseBeanService.getObjectByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
                if (cus.getAccount() != null && !cus.getAccount().equals("")) {
                    customer = (TEshopCustomer) baseBeanService
                            .getBeanByHqlAndParams(
                                    "from TEshopCustomer where account=? AND logOff=0",
                                    new Object[]{cus.getAccount()});
                } else {

                    customer = (TEshopCustomer) baseBeanService
                            .getBeanByHqlAndParams(
                                    "from TEshopCustomer where openId=?",
                                    new Object[]{cus.getOpenId()});

                }

                sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
                sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);

                if (cus != null) {
                    System.out.print("customer1" + cus.getAccount());
                }

                if (customer != null) {
                    System.out.print("customer2" + customer.getAccount());
                }

            } else {

                cus = (TEshopCusCom) sw.getObject(session,
                        SessionWrap.KEY_SHOPCUSCOM);

                if (cus != null) {
                    sccid = cus.getSccId();
                    System.out.print("cus1" + cus.getAccount());
                    customer = (TEshopCustomer) baseBeanService
                            .getBeanByHqlAndParams(
                                    "from TEshopCustomer where account=? AND logOff=0",
                                    new Object[]{cus.getAccount()});
                    if (customer != null) {
                        System.out.print("cus3" + customer.getAccount());
                    } else {
                        customer = (TEshopCustomer) baseBeanService
                                .getBeanByHqlAndParams(
                                        "from TEshopCustomer where openId=?",
                                        new Object[]{cus.getOpenId()});

                    }
                    System.out.print("cus2" + customer.getAccount());
                    sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
                    sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);

                }
                TEshopCustomer teshopCustomer = (TEshopCustomer) sw.getObject(
                        session, SessionWrap.KEY_CUSTOMER);
                if (teshopCustomer != null) {
                    System.out.print("cus2" + teshopCustomer.getAccount());
                } else {
                    System.out.print("teshopCustomer是空的");
                }


            }

            session.setAttribute("url", url);
            session.setAttribute("preUrl",url);
            if (cus == null) {


                String urlmicro = wfjAccountService.isWxLogin(request, url);
                if (urlmicro != null && !urlmicro.equals("")) {
                    try {
                        response.sendRedirect(urlmicro);
                        return null;
                    } catch (IOException e) {
                        logger.error("操作异常", e);
                    }

                } else {

                    return "login";
                }

            }
        }
        String comID = "";

        if (scancode != null && !scancode.equals("") && scancode.length() >= 3) {
            returntype = scancode.substring(0, 2);


            String ppid = scancode.substring(2);


            String hql = "from ProductPackaging where ppID = ?";


            if (returntype.equals("01")) { //扫码收款
                if(ppid.contains("company")){
                    companyId=ppid;
                    staffId=cus.getStaffid();
                    returntype="10-1";
                }else {
                    ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppid});
                    contactCompany = restaurantService.scanCodePay(ppid);
                    request.setAttribute("pp", pp);

                    request.setAttribute("staffid", cus.getStaffid());
                    request.setAttribute("sccid", cus.getSccId());
                    request.setAttribute("attach", "smsk");
                    comID = pp.getCompanyID();
                }

            } else if (returntype.equals("02")) { //扫码加入购物车

                String companyID = restaurantService.joinShopCart(cus.getSccId(), ppid);
                if (companyID != null && !companyID.equals("")) {
                    Map<String, Object> maps = restaurantService.queryShopCart(companyID, cus.getStaffid());
                    List<BaseBean> beanList = (List<BaseBean>) maps.get("beanList");
                    request.setAttribute("beanList", beanList);
                    request.setAttribute("comps", maps.get("comps"));
                    request.setAttribute("num", beanList.size());
                    request.setAttribute("ccmap", null);
                    comID = companyID;
                }


            } else if (returntype.equals("03")) { //扫餐桌进入店铺商城
                ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppid});
                session.setAttribute("canzuo", request.getParameter("canzuo"));
                request.setAttribute("companyId", pp.getCompanyID());
                contactCompany = assiCartService.getContactCompany(pp.getCompanyID());
                comID = pp.getCompanyID();
                request.setAttribute("ccompanyId", contactCompany.getCcompanyID());

            } else if (returntype.equals("04")) { //考场审核人扫码验证
                JSONObject jsonObj = new JSONObject();
                boolean bl = mappService.signInOrCheckOut("pos".equals(pos)?"pos":cus.getSccId(), ppid);
                if (bl == true||(bl==false&&("pos").equals(pos))) {
                    logger.info("调试信息");
                    logger.info("调试信息");
                    Object[] os=(Object[])cmService.testRecord(ppid);//实体强转数组
                    if(os!=null) {
                        for (int i = 0; i < os.length; i++) {
                            if (os[i] == null) {
                                os[i] = "- - - -";
                            }
                        }

                    }
                    logger.info("调试信息");
                    jsonObj.accumulate("os", os);
                }
                jsonObj.accumulate("bl", bl);
                jsonObj.accumulate("scancode", scancode);
                this.results = jsonObj;
                return "success";
            } else if (returntype.equals("05")) { //选择产品后生成的二维码

                //根据ppid=staffid查出购物车的产品并计算金额
                List<Object> list = assiCartService.getCartList(ppid, companyId);

                BigDecimal sum = new BigDecimal("0");
                String goodsname = "";
                for (int i = 0; i < list.size(); i++) {
                    Object[] obj = (Object[]) list.get(i);
                    BigDecimal price = new BigDecimal(obj[1].toString());
                    BigDecimal quality = new BigDecimal(obj[2].toString());
                    BigDecimal t = price.multiply(quality);
                    sum = sum.add(t);
                    if (i != list.size() - 1) {
                        goodsname += obj[0].toString() + ",";
                    } else {
                        goodsname += obj[0].toString();
                    }


                }
                contactCompany = assiCartService.getContactCompany(companyId);
                request.setAttribute("money", sum);
                request.setAttribute("waiterID", ppid);
                ProductPackaging pp = new ProductPackaging();
                if (goodsname.length() > 100) {
                    pp.setGoodsName(goodsname.substring(0, 100) + "等多件产品");
                } else {
                    pp.setGoodsName(goodsname);
                }

                request.setAttribute("pp", pp);
                request.setAttribute("staffid", cus.getStaffid());
                request.setAttribute("sccid", cus.getSccId());
                request.setAttribute("attach", "gsmzs");
                returntype = "01";
                comID = companyId;

            } else if (returntype.equals("06")) { //选择产品后生成的二维码
                String quality = request.getParameter("quality");
                ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppid});
                ProSetup ps = (ProSetup) baseBeanService.getBeanByHqlAndParams("from ProSetup where ppid = ?", new Object[]{ppid});
                contactCompany = restaurantService.scanCodePay(ppid);
                request.setAttribute("pp", pp);
                request.setAttribute("staffid", cus.getStaffid());
                request.setAttribute("sccid", cus.getSccId());
                request.setAttribute("money", Float.parseFloat(ps.getRePrice()) * Float.parseFloat(quality) + "");
                request.setAttribute("waiterID", request.getParameter("waiterID"));
                request.setAttribute("attach", "dsmzs");
                returntype = "01";
                comID = pp.getCompanyID();
            } else if (returntype.equals("07")) { //下单后结算
                String waiterID = request.getParameter("waiterID");
                List<Object> list = assiCartService.getClientGoods(ppid);

                BigDecimal sum = new BigDecimal("0");
                String goodsname = "";
                for (int i = 0; i < list.size(); i++) {
                    Object[] obj = (Object[]) list.get(i);
                    BigDecimal price = new BigDecimal(obj[1].toString());
                    BigDecimal quality = new BigDecimal(obj[2].toString());
                    BigDecimal t = price.multiply(quality);
                    sum = sum.add(t);
                    if (i != list.size() - 1) {
                        goodsname += obj[0].toString() + ",";
                    } else {
                        goodsname += obj[0].toString();
                    }


                }
                contactCompany = assiCartService.getContactCompany(companyId);
                request.setAttribute("money", sum);
                request.setAttribute("waiterID", waiterID);
                request.setAttribute("coID", ppid);
                ProductPackaging pp = new ProductPackaging();
                if (goodsname.length() > 100) {
                    pp.setGoodsName(goodsname.substring(0, 100) + "等多件产品");
                } else {
                    pp.setGoodsName(goodsname);
                }

                request.setAttribute("pp", pp);
                request.setAttribute("staffid", cus.getStaffid());
                request.setAttribute("sccid", cus.getSccId());
                request.setAttribute("attach", "xdsmzs");
                returntype = "01";
                comID = companyId;
            } else if (returntype.equals("08")) { //自助收银扫码付款

                OperatorInfo operator = new OperatorInfo(serverService.getServerID("operator"),cus.getStaffid(),"",ppid,"","00");
                baseBeanService.save(operator);
                smSerivce.updateSelfCart(sccid, ppid, "scan");//如果是VIP价格更新金额
                String directUrl = request.getParameter("directUrl");
                String totalMoney = request.getParameter("totalMoney");
                String vipmoney = smSerivce.getVipTmoney(sccid, ppid, totalMoney);
                request.setAttribute("vipmoney", vipmoney);
                if (posNum != null && !posNum.equals("")) {
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
                    smSerivce.genPayBackupBill(sccid, ppid, staffaddress != null ? staffaddress.getAddressID() : null);
                } else {
                    smSerivce.genPayBackupBill(sccid, ppid, null);

                }
                if (directUrl != null && !directUrl.equals("")) {
                    try {

                        response.sendRedirect(getResultUrl(directUrl + "&posNum=" + posNum + "&journalNum=" + ppid+"&vipmoney="+vipmoney));
                    } catch (Exception e) {

                    }

                } else {

                    String jifen = smSerivce.searchPoint(sccid);//查询积分
                    String jb = "";
                    if(customer!=null&&!"1".equals(customer.getStatus())){
                       jb = smSerivce.searchJINBIN(sccid);
                    }else{
                        jb = "dj";
                    }
                    request.setAttribute("jb", jb);
                    String posNum = request.getParameter("posNum");


                    request.setAttribute("journalNum", ppid);
                    request.setAttribute("jifen", jifen);
                    String tj = request.getParameter("tj");

                    request.setAttribute("journalNum", ppid);
                    request.setAttribute("jifen", jifen);
                }
                //获取超市名
                String sql = "select cc.companyname from t_eshop_cuscom tt left join dtcompany cc on tt.companyid=cc.companyid where tt.sccid=?";
                String companyName = (String) baseBeanService.getObjectBySqlAndParams(sql, new Object[]{tj});
                request.setAttribute("companyName", companyName);

            }else if (returntype.equals("09")) { //停车收费
            	  String carNum = request.getParameter("carNum");
                   String equip = request.getParameter("equip");
                   String status = request.getParameter("status");

                if(carNum==null||carNum.equals("")){
            		  if(equip!=null&&!equip.equals("")){
            			  carNum = cmService.getCarNumByEq(equip,status);
            			  
            		  }
             	  }
            	  if(carNum==null||carNum.equals("")){
             
            	     carNum = mappService.getCarNumberByTcc(sccid);
            	  }
            	  
            	 

            	 if(carNum!=null&&!carNum.equals("")){
            		 //如果绑定了车牌号
            		 CarManage  carManage = mappService.getCarInRecord(carNum,status,equip);
            		 request.setAttribute("carManage", carManage);
                     List<Object> list =  mappService.searchFeeScale(carNum,equip);
                     if(list!=null&&list.size()>0) {
                         Object[] obj = (Object[]) list.get(0);

                         request.setAttribute("price", obj[1].toString());
                         request.setAttribute("ppid", obj[0].toString());
                         request.setAttribute("timeUnits", obj[3].toString());
                         Company y = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{obj[2].toString()});

                         request.setAttribute("companyName", y.getCompanyName());

                     }
                     request.setAttribute("feelist", list);
            		
            	 }
                session.setAttribute("preUrl","");
            	 request.setAttribute("carNum", carNum);
            }else if(returntype.equals("10")){
                companyId=ppid;
                StringBuilder sb=new StringBuilder();
                sb.append("select count(*) from  ProductPackaging p");
                sb.append(" where companyID = ? and showweixin = ? and type != ?");
                Integer Count=baseBeanService.getConutByByHqlAndParams(sb.toString(),new Object[]{companyId,"01","扫码收款"});
                if (Count >0) {
                    returntype="10-2";
                }else {
                    staffId=cus.getStaffid();
                    returntype="10-1";
                }
            }
            request.setAttribute("user", cus.getAccount());
            request.setAttribute("scandc", "scandc");

            //扫码加入快捷应用公司
            if (!comID.equals("")) {
                restaurantService.addComapnyKuaiJie(cus.getSccId(), comID);
            }
        }


        return returntype;
    }

    /**
     * 验证中文
     *
     * @param c
     * @return
     */
    private boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

    /**
     * 获取规格
     *
     * @return
     */
    public String specifications() {
        Map<String, Object> mapa = new HashMap<String, Object>();
        String hql = " from AttriProduction where goodsid=?";
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{goodsid});
        for (int i = 0; i < list.size(); i++) {
            AttriProduction at = (AttriProduction) list.get(i);
            if (at.getAttriname() != null && at.getAttrivalue() != null) {
                String sql = "select d.attrivalue from dtAttriProduction d where d.goodsid=? and d.attriname=?";
                List<BaseBean> vlist = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{goodsid, at.getAttriname()});
                mapa.put(at.getAttriname() + at.getType(), vlist);
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.accumulate("map", mapa);
        result = jsonObj.toString();
        return "success";
    }

    /**
     * 将中文格式化
     *
     * @param directUrl
     * @return
     */
    private String getResultUrl(String directUrl) {
        String resultURL = "";

        for (int i = 0; i < directUrl.length(); i++) {
            char charAt = directUrl.charAt(i);
            //只对汉字处理
            if (isChineseChar(charAt)) {
                String encode = null;
                try {
                    encode = URLEncoder.encode(charAt + "", "UTF-8");
                } catch (UnsupportedEncodingException e) {

                }
                resultURL += encode;
            } else {
                resultURL += charAt;
            }
        }

        return resultURL;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<BaseBean> getLists() {
        return lists;
    }

    public void setLists(List<BaseBean> lists) {
        this.lists = lists;
    }

    public List<BaseBean> getPersonLists() {
        return personLists;
    }

    public void setPersonLists(List<BaseBean> personLists) {
        this.personLists = personLists;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


    public TEshopCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(TEshopCustomer customer) {
        this.customer = customer;
    }

    public TEshopCusCom getShopCusCom() {
        return shopCusCom;
    }

    public void setShopCusCom(TEshopCusCom shopCusCom) {
        this.shopCusCom = shopCusCom;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }

    public String getPrivateRoom() {
        return privateRoom;
    }

    public void setPrivateRoom(String privateRoom) {
        this.privateRoom = privateRoom;
    }

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public double getMon() {
        return mon;
    }

    public void setMon(double mon) {
        this.mon = mon;
    }

    public StaffAddress getStaffaddress() {
        return staffaddress;
    }

    public void setStaffaddress(StaffAddress staffaddress) {
        this.staffaddress = staffaddress;
    }

    public StaffAddress getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(StaffAddress staffAddress) {
        this.staffAddress = staffAddress;
    }

    public PageForm getPageFormto() {
        return pageFormto;
    }

    public void setPageFormto(PageForm pageFormto) {
        this.pageFormto = pageFormto;
    }

    public String getAjax() {
        return ajax;
    }

    public void setAjax(String ajax) {
        this.ajax = ajax;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public File getTxtFile() {
        return txtFile;
    }

    public void setTxtFile(File txtFile) {
        this.txtFile = txtFile;
    }

    public String getScancode() {
        return scancode;
    }

    public void setScancode(String scancode) {
        this.scancode = scancode;
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public BaseBeanService getBaseBeanService() {
        return baseBeanService;
    }

    public void setBaseBeanService(BaseBeanService baseBeanService) {
        this.baseBeanService = baseBeanService;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    public String getTj() {
        return tj;
    }

    public void setTj(String tj) {
        this.tj = tj;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStardard() {
        return stardard;
    }

    public void setStardard(String stardard) {
        this.stardard = stardard;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getShowNum() {
        return showNum;
    }

    public void setShowNum(String showNum) {
        this.showNum = showNum;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
