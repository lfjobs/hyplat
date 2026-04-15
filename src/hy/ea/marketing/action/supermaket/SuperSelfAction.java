package hy.ea.marketing.action.supermaket;


import com.alipay.bo.TradePayParam;
import com.alipay.config.AlipayConfig;
import com.alipay.faceTopay.AlipayTradePay;
import com.alipay.faceTopay.TradePayReuslt;
import com.alipay.util.AlipayNotify;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.service.WfjAccountService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.Micropay;
import com.wechat.bo.WxUserInfo;
import com.wechat.utils.WeixinUtil;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.service.WchatPay;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.finance.BenDis.OperatorInfo;
import hy.ea.bo.finance.BenDis.transferPay;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.finance.service.AssiCartService;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.finance.service.transferService;
import hy.ea.marketing.bo.DtPayOffline;
import hy.ea.marketing.service.PayFaceDeviceSerivce;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.ea.marketing.service.SupermarketSerivce;
import hy.ea.office.service.MakeAppointmentService;
import hy.ea.util.Constant;
import hy.ea.util.CookieUtil;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Controller
@Scope("prototype")
public class SuperSelfAction {
    @Resource
    private ServerService serverService;
    @Resource
    private SuperSelfSerivce smSerivce;
    @Resource
    private SupermarketSerivce sumkSerivce;//mz
    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    AssiCartService assiCartService;
    @Resource
    private WfjAccountService wfjAccountService;
    @Resource
    private GoldOrderService goldOrderService;
    @Resource
    private transferService transService;
    @Resource
    private BonusPointsService bonusPointsService;
    @Resource
    private PayFaceDeviceSerivce payFaceSerivce;
    @Resource
    private PayFaceDeviceSerivce faceDeviceSerivce;
    Map<String, SelfCart> selfCartmap;
    @Resource
    private MakeAppointmentService makeAppointmentService;
    private ScanGoodsRecord scanGoods;

    private String result;
    private String barCode;//条码号
    private CAccount account;
    private String companyIdentifier;
    private String pos;
    private String journalNum;
    private Micropay micropay;
    private String posNum;
    private String scaleNo;//电子秤编号
    private String sccId;
    private String staffName;
    private String ccompanyID;
    private String comID;
    private PayBackupBill payBackupBill;
    private List<BaseBean> beans;
    private StaffAddress staffAddress;
    private String ppid;
    private TEshopCustomer tEshopCustomer;
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
    private int lxType = 0;//查询类型，0：其他入口进入1：批发商城进入


    private Logger logger = Logger.getLogger(SuperSelfAction.class);

    private String hgcode;
    private String remainMoney;
    private String cbid;
    private String lastPay;
    private String door;
    /**
     * 登陆页面
     *
     * @return
     */
    public String poslogin() {
        if (scaleNo == null || scaleNo.equals("")) {
            //pos终端登录
            if (pos == null || pos.equals("")) {
                return "error";
            } else {
                CookieUtil.setCookie("pos", pos, response);

                //电子秤登陆
                CookieUtil.setCookie("scaleNo", "", response);
            }
        } else {
            //电子秤登陆
            Cookie cookie = new Cookie("scaleNo", scaleNo);
            cookie.setMaxAge(360 * 24 * 60);//设置一年有效期
            response.addCookie(cookie);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    if (!"".equals(cookie.getValue()) && cookie.getValue() != null) {
                        return index();
                    }
                }
            }

        }


        return "login";
    }

    public String backLogin() {
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
        Cookie[] cookies = request.getCookies();
        String cs = request.getParameter("cs");
        String pos = "";
        String scaleNo = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("pos")) {
                pos = cookie.getValue();
                continue;
            }
            if (cookie.getName().equals("scaleNo")) {
                scaleNo = cookie.getValue();
                continue;
            }

        }
        try {
            CookieUtil.setCookie("user", "", response);
            if (scaleNo != null && !"".equals(scaleNo)) {
                response.sendRedirect(basePath + "/ea/sm/ea_poslogin.jspa?scaleNo=" + scaleNo);
            } else {
                response.sendRedirect(basePath + "/ea/sm/ea_poslogin.jspa?pos=" + pos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    /**
     * 操作员登陆
     *
     * @return
     */
    public String login() {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String tip = null;

        int result = smSerivce.login(companyIdentifier, account);

        if (result == 0) {

            CookieUtil.setCookie("user", account.getStaffID(), response);

            Staff ff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{account.getStaffID()});
            CookieUtil.setCookie("staffcode", ff.getStaffCode(), response);

            CookieUtil.setCookie("companyIdentifier", companyIdentifier, response);

            CookieUtil.setCookie("comID", account.getCompanyID(), response);

            CookieUtil.setCookie("staffName", ff.getStaffName(), response);

            CookieUtil.setCookie("fkw", account.getAccountPassword(), response);

            CookieUtil.setCookie("loginGuid", account.getLoginGuid(), response);

            CookieUtil.setCookie("accountEmail", account.getAccountEmail(), response);

            comID = account.getCompanyID();

            return index();
        } else {
            switch (result) {
                case 1:
                    tip = "企业不存在";
                    break;
                case 2:
                    tip = "该企业已停用";
                    break;
                case 3:
                    tip = "用户名或者密码不正确";
                    break;
                case 4:
                    tip = "账号已停用";
                    break;
                case 5:
                    tip = "没有权限登陆超市自助收银机";
                    break;
            }


            ActionContext.getContext().put("companyIdentifier", companyIdentifier);
            ActionContext.getContext().put("account", account);
            ActionContext.getContext().put("tip", tip);
            return "login";
        }

    }

    /**
     * 首页
     *
     * @return
     */
    public String index() {

        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";
        Cookie[] cookies = request.getCookies();
        String scaleNo = "";
        String companyID = "";
        String loginGuid = account != null ? account.getLoginGuid() : "";
        String accountEmail = account != null ? account.getAccountEmail() : "";
        String staffID = account != null ? account.getStaffID() : "";
        String pos = "";


        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("scaleNo")) {
                scaleNo = cookie.getValue();
                continue;
            }
            if (cookie.getName().equals("pos")) {
                pos = cookie.getValue();
                continue;
            }
            if (cookie.getName().equals("comID")) {
                companyID = cookie.getValue();
                continue;
            }
            if (account == null) {
                if (cookie.getName().equals("loginGuid")) {
                    loginGuid = cookie.getValue();
                    continue;
                }
                if (cookie.getName().equals("accountEmail")) {
                    accountEmail = cookie.getValue();
                    continue;
                }
                if (cookie.getName().equals("user")) {
                    staffID = cookie.getValue();
                    continue;
                }
            }


        }
        if ("".equals(companyID)) {
            companyID = comID;
        }

        if (!"".equals(scaleNo)) {
            request.setAttribute("companyID", companyID);
            return "scaleindex";
        }
        if (loginGuid != null && !loginGuid.equals("")) {
            //验证用户是否被其他登陆
            String re = smSerivce.validateLogin(accountEmail, staffID, companyID, loginGuid);
            String tip = "账号被他人登陆请重新登陆";
            try {
                if ("0".equals(re)) {
                    CookieUtil.setCookie("user", "", response);
                    if (scaleNo != null && !"".equals(scaleNo)) {
                        response.sendRedirect(basePath + "/ea/sm/ea_poslogin.jspa?scaleNo=" + scaleNo + "&tip=" + URLEncoder.encode(tip, "utf-8"));
                    } else {
                        response.sendRedirect(basePath + "/ea/sm/ea_poslogin.jspa?pos=" + pos + "&tip=" + URLEncoder.encode(tip, "utf-8"));
                    }
                }
            } catch (Exception e) {

            }
        }

        return "index";
    }


    /**
     * 首页第一次扫码进入清单页面
     *
     * @return
     */
    public String qdlist() {

        String comID = "";
        String staffID = "";
        if (posNum == null || posNum.equals("")) {
            comID = CookieUtil.getCookieValue("comID", request);
            staffID = CookieUtil.getCookieValue("user", request);
        }
        if (ccompanyID != null && !ccompanyID.equals("")) {
            Company cc = smSerivce.getCompanyByCComID(ccompanyID);
            comID = cc.getCompanyID();
            request.setAttribute("companyName", cc.getCompanyName());
        }
        String fhform = request.getParameter("fhform");
        String fh = "";
        if (fhform != null && !fhform.equals("")) {
            fh = fhform;
            if (fhform.equals("3")) {
                fh = "1";
            }
        } else {
            fh = smSerivce.findFhForm(posNum, ccompanyID);
        }
        request.setAttribute("companyID", comID);
        request.setAttribute("fh", fh);
        String relateID = request.getParameter("relateID");
        if (relateID == null || relateID.equals("")) {
            request.setAttribute("relateID", serverService.getServerID("relateID"));
        } else {
            request.setAttribute("relateID", relateID);
        }
        List<BaseBean> list = smSerivce.getScanRecord(posNum, staffID, comID);
        request.setAttribute("nopay", list.size());

        return "qdlist";
    }


    /**
     * 序号   商品名称   数量   金额
     * <p>
     * 根据条码查询商品信息
     *
     * @return
     */
    public String searchProduct() {
        String ccompanyID = request.getParameter("ccompanyID");
        String comID = request.getParameter("comID");
        if (ccompanyID != null && !ccompanyID.equals("")) {
            Company cc = smSerivce.getCompanyByCComID(ccompanyID);
            comID = cc.getCompanyID();
        } else {
            if (comID == null || comID.equals("")) {
                comID = CookieUtil.getCookieValue("comID", request);
            }
        }

        Map<String, Object> map = smSerivce.searchGoods(comID, barCode, lxType);
        map.put("barCode", barCode);
        map.put("uuid", serverService.getServerID("xn"));
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public String payErCode() {
        try {
            String comID = "";
            String companyID = request.getParameter("companyID");
            String relateID = request.getParameter("relateID");
            String sccId = request.getParameter("sccId");

            String morre = request.getParameter("morre");
            String hgcode = request.getParameter("hgcode");

            if (posNum == null || posNum.equals("")) {

                comID = CookieUtil.getCookieValue("comID", request);
                posNum = CookieUtil.getCookieValue("pos", request);
            }
            if (comID == null || comID.equals("")) {
                comID = companyID;
            }
            Map<String, Object> map = smSerivce.joinSelfCart(selfCartmap, comID, posNum, relateID);


            if (sccId != null && !sccId.equals("")) {
                goldOrderService.generateSelfPayOrder(morre, sccId, (String) map.get("journalNum"), "", "");
                smSerivce.setHgCash(hgcode, (String) map.get("journalNum"));
            }
            request.setAttribute("journalNum", map.get("journalNum"));
            request.setAttribute("totalNum", map.get("totalNum"));
            request.setAttribute("totalMoney", map.get("totalMoney"));
            request.setAttribute("comID", companyID);
            request.setAttribute("companyName", map.get("companyName"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 生成订单号并加入超市购物车
     *
     * @return
     */
    public String getJumBycomID() {
        String stardard = request.getParameter("stardard");
        String price = request.getParameter("price");
        String itemNum = request.getParameter("itemNum");
        Map<String, Object> map = new HashMap<String, Object>();
        if (ppid != null && !ppid.equals("")) {
            map = smSerivce.joinDanSelfCart(ppid, stardard, price, itemNum, comID, posNum);
        } else {
            map = smSerivce.joinMultiSelfCart(posNum);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 生成订单前备份信息
     *
     * @return
     */
    public String ajaxPayBackUp() {
        Map<String, Object> map = new HashMap<String, Object>();
        String journalNum = smSerivce.savePaybackUp(payBackupBill);
        String itemNum = request.getParameter("itemNum");
        if (itemNum != null && !itemNum.equals("")) {
            smSerivce.updateItemNum(ppid, itemNum, payBackupBill.getJournalNum());
        }
        JSONObject oj = JSONObject.fromObject(map);
        map.put("jum", journalNum);
        result = oj.toString();
        return "success";
    }

    /**
     * 积分充值生成订单付款二维码
     *
     * @return
     */
    public String payErCodeByIgl() {
        Cookie[] cookies = request.getCookies();
        String comID = "";
        String posNum = "";
        String staffID = "";
        String companyId = request.getParameter("companyId");
        if (ccompanyID != null && !ccompanyID.equals("")) {
            Company cc = smSerivce.getCompanyByCComID(ccompanyID);
            comID = cc.getCompanyID();
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("comID")) {
                comID = cookie.getValue();
                continue;
            }
            if (cookie.getName().equals("pos")) {
                posNum = cookie.getValue();
                continue;
            }
            if (cookie.getName().equals("user")) {
                staffID = cookie.getValue();
                continue;
            }
        }
        String journalNum = serverService.getBillID(comID);
        smSerivce.addSelfCart(posNum, request.getParameter("cardNum"), journalNum, "1", request.getParameter("totalMoney"), request.getParameter("ppId"), request.getParameter("proName"), comID, "".equals(companyId) || companyId == null ? "company201009046vxdyzy4wg0000000025" : companyId);
        request.setAttribute("journalNum", journalNum);
        request.setAttribute("totalNum", "1");
        request.setAttribute("totalMoney", request.getParameter("totalMoney"));
        request.setAttribute("cardNum", request.getParameter("cardNum"));
        result = journalNum;
        return "success";
    }

    /**
     * 现金支付
     *
     * @return
     */
    public String showCash() {
        String comID = "";
        String staffID = "";
        String staffName = "";
        if (posNum == null || posNum.equals("")) {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("user")) {
                        staffID = cookie.getValue();
                        continue;
                    }

                    if (cookie.getName().equals("comID")) {
                        comID = cookie.getValue();
                        continue;
                    }
                    if (cookie.getName().equals("staffName")) {
                        staffName = cookie.getValue();
                        continue;
                    }
                }
            }
        }
        if ("".equals(comID)) {
            comID = request.getParameter("comID");
        }
        request.setAttribute("journalNum", request.getParameter("journalNum"));
        request.setAttribute("totalNum", request.getParameter("totalNum"));
        request.setAttribute("totalMoney", request.getParameter("totalMoney"));
        request.setAttribute("staffID", staffID);
        request.setAttribute("staffName", staffName);
        request.setAttribute("comID", comID);
        return "cashpay";
    }


    /**
     * 验证操作员密码
     *
     * @return
     */
    public String checkUser() {

        String pw = request.getParameter("pw");
        String staffID = request.getParameter("staffID");
        String ssprice = request.getParameter("ssprice");
        String zlprice = request.getParameter("zlprice");
        String journalNum = request.getParameter("journalNum");
        String comID = request.getParameter("comID");
        String totalMoney = request.getParameter("totalMoney");
        String telphone = request.getParameter("telphone");


        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = smSerivce.searchPwByStaff(staffID, comID, telphone);
        List<String> sbpwd = (List<String>) map1.get("paymentCode");
        String paymentCode = "";
        if (!sbpwd.contains("1") && !sbpwd.contains("0")) {
            if (sbpwd != null && sbpwd.contains(pw)) {
                paymentCode = pw;
                if (comID == null || comID.equals("")) {
                    comID = (String) map1.get("comID");
                }
                String sccid = smSerivce.searchSccidByCompany(comID);
                String comJIFEN = smSerivce.searchPoint(sccid);
                BigDecimal bcom = new BigDecimal(comJIFEN);
                BigDecimal pri = new BigDecimal("100");//积分单价
                BigDecimal btotalMoney = new BigDecimal(totalMoney).multiply(pri);
                if (bcom.compareTo(btotalMoney) != -1) {
                    smSerivce.addMoreInfo(journalNum, ssprice, zlprice);
                    paymentCode = "2";
                    if (staffID == null || staffID.equals("")) {
                        Map<String, String> m = smSerivce.getCashOperator(telphone, comID, pw);
                        map.put("staffID", m.get("staffID"));
                        map.put("staffName", m.get("staffName"));
                    }
                } else {
                    paymentCode = "4";
                }


            } else {
                paymentCode = "3";//密码错误
            }
        }
        map.put("result", paymentCode);
        map.put("comID", comID);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";
    }

    /**
     * 现金处理数据
     *
     * @return
     */
    public String cashData() {

        try {
            List<BaseBean> cashlist = new ArrayList<BaseBean>();

            String journalNum = request.getParameter("journalNum");
            String start = request.getParameter("start");
            String end = request.getParameter("end");
            String q = request.getParameter("q");

            StringBuilder sb = new StringBuilder();

            if (journalNum != null && !journalNum.equals("")) {
                //单个订单处理
                sb.append("from CashierBills t  where  t.journalNum = ? and t.billsType = '项目收入预算单' and t.fkStatus = '01'  and (t.wfStatus4 = '05' or t.wfStatus4 = '06')  and t.companyID = 'company20180510CQZCDKTT690000006064' and t.contactUserID='Staff20180508J94R7WB2K40000017480'");
                cashlist = baseBeanService.getListBeanByHqlAndParams(sb.toString(), new Object[]{journalNum});

            } else if (start != null && !start.equals("") && end != null && !end.equals("")) {

                sb.append("from CashierBills t  where t.billsType = '项目收入预算单' and t.fkStatus = '01'  and (t.wfStatus4 = '05' or t.wfStatus4 = '06')  and t.companyID = 'company20180510CQZCDKTT690000006064' and t.contactUserID='Staff20180508J94R7WB2K40000017480'");
                if (start.length() == 10) {
                    start = start + " " + "00:00:00";
                }
                if (end.length() == 10) {
                    end = end + " " + "23:59:59";
                }
                sb.append(" and t.cashierDate between to_date(?, 'yyyy-mm-dd hh24:mi:ss') and to_date(?, 'yyyy-mm-dd hh24:mi:ss')");

                cashlist = baseBeanService.getListBeanByHqlAndParams(sb.toString(), new Object[]{start, end});
            } else if (q != null && q.equals("q")) {
                sb.append("from CashierBills t  where t.billsType = '项目收入预算单' and t.fkStatus = '01'  and (t.wfStatus4 = '05' or t.wfStatus4 = '06')  and t.companyID = 'company20180510CQZCDKTT690000006064' and t.contactUserID='Staff20180508J94R7WB2K40000017480'");

                cashlist = baseBeanService.getListBeanByHqlAndParams(sb.toString(), null);
            }

            for (BaseBean b : cashlist) {
                CashierBills fd = (CashierBills) b;
                logger.error("cashpay" + fd.getCashierBillsID());
                String cashierBillsID = fd.getCashierBillsID();
                System.out.print(fd.getCashierBillsID());
                journalNum = fd.getJournalNum();
                if (cashierBillsID != null && !cashierBillsID.equals("")) {
                    Boolean bo = goldOrderService.generateSpoints(journalNum, journalNum, fd.getPriceSub(), "05");

                    //生成收款单
                    if (bo == true) {

                        try {
                            //收款单生成后复制订单和收款单到新表
                            goldOrderService.copyCash(journalNum, "j");
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            System.out.println("复制订单积分或者金币入库单错误");
                        }


                        goldOrderService.autoFh(cashierBillsID);
                        //把订单状态改程03已收货
                        goldOrderService.updateFkState(cashierBillsID);
                        //分金币
                        transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");


                    } else {
                        System.out.print("积分不足");

                        logger.info("积分不足");

                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return "success";

    }

    /**
     * 线下支付
     */
    public String offlinePayOrder() {
        try {
            // 公司ID
            String comID = request.getParameter("comID");
            // 订单号
            String journalNum = request.getParameter("journalNum");
            // 总金额
            String morre = request.getParameter("totalMoney");

            // 微分金账号
            String sccId = smSerivce.getSccidBycomID(comID);//公司Sccid

            String ppid = request.getParameter("ppid");

            DtPayOffline offline = (DtPayOffline) baseBeanService.getBeanByHqlAndParams("from DtPayOffline where payOfflineId = ?", new Object[]{ppid});

            smSerivce.updateSelfCart(sccId, journalNum, offline.getPaymentType());

            Boolean bo = true;
            String cashierBillsID = goldOrderService.generateSelfPayOrder(morre, sccId, journalNum, "03", null);//订单

            CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
            String oprID = account.getStaffID();
            String oprName = account.getStaffName();

            smSerivce.saveCashMoreInfo(journalNum, oprID, oprName);

            logger.info("offline_" + cashierBillsID);
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                goldOrderService.getPrice(cashierBillsID);
                bo = goldOrderService.generateSpoints(journalNum, journalNum, morre, "05");
                //生成收款单
                if (bo == true) {

                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "j");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        System.out.println("复制订单积分或者金币入库单错误");
                    }


                    SelfCart selfCart = smSerivce.getSelfCartByJum(journalNum);
                    if (selfCart != null && selfCart.getCardNum() != null) {
                        String proType = smSerivce.getSelfByProType(selfCart.getPid());
                        if (!"个人会员".equals(proType)) {
                            TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                            bonusPointsService.buyBonusPoints(comID, journalNum, (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), morre, "05", journalNum);
                            //收银积分充值现金
                            OperatorInfo operator = new OperatorInfo(serverService.getServerID("operator"), oprID, "", journalNum, "02", "01");
                            baseBeanService.save(operator);

                            //把订单状态改程03已收货
                            goldOrderService.updateFkState(cashierBillsID);
                            //把订单状态改程04已分金币
                            transService.getCoasUpdate(cashierBillsID, "04");
                        }
                    } else {
                        if (smSerivce.isSqPos(journalNum)) {
                            goldOrderService.autoFh(cashierBillsID);
                            //把订单状态改程03已收货
                            goldOrderService.updateFkState(cashierBillsID);
                            //分金币
                            transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");

                        }
                    }
                } else {
                    logger.error("？？");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return "success";

    }

    /**
     * 现金支付
     *
     * @return
     */
    public String cashPayOrder() {
        try {
            String comID = request.getParameter("comID");
            String journalNum = request.getParameter("journalNum");
            String morre = request.getParameter("totalMoney");
            String fhform = request.getParameter("fhform");
            String sccId = smSerivce.getSccidBycomID(comID);//公司Sccid

            String ppid = request.getParameter("ppid");

            smSerivce.updateSelfCart(sccId, journalNum, "cash");

            Boolean bo = true;
            String cashierBillsID = "";

            if ("3".equals(fhform)) {
                CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});
                cashierBillsID = cc.getCashierBillsID();
                smSerivce.setCashCom(journalNum, sccId);
            } else {
                if (ppid != null && !ppid.equals("")) {
                    cashierBillsID = goldOrderService.generateOrderSheet(ppid, morre, sccId, journalNum, "", "06");
                } else {
                    cashierBillsID = goldOrderService.generateSelfPayOrder(morre, sccId, journalNum, "06", null);//订单
                }
            }

            String oprID = request.getParameter("oprID");

            String oprName = request.getParameter("oprName");
            smSerivce.saveCashMoreInfo(journalNum, oprID, oprName);

            logger.error("cashpay" + cashierBillsID);
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                goldOrderService.getPrice(cashierBillsID);
                bo = goldOrderService.generateSpoints(journalNum, journalNum, morre, "05");

                //生成收款单
                if (bo == true) {
                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "j");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        System.out.println("复制订单积分或者金币入库单错误");
                    }

                    SelfCart selfCart = smSerivce.getSelfCartByJum(journalNum);
                    if (selfCart != null && selfCart.getCardNum() != null) {
                        String proType = smSerivce.getSelfByProType(selfCart.getPid());
                        if (!"个人会员".equals(proType)) {
                            TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                            bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", journalNum, (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), morre, "05", journalNum);
                            //收银积分充值现金
                            OperatorInfo operator = new OperatorInfo(serverService.getServerID("operator"), oprID, "", journalNum, "02", "01");
                            baseBeanService.save(operator);

                            //把订单状态改程03已收货
                            goldOrderService.updateFkState(cashierBillsID);
                            //把订单状态改程04已分金币
                            transService.getCoasUpdate(cashierBillsID, "04");
                        }
                    } else {
                        if (smSerivce.isSqPos(journalNum)) {
                            if (!"3".equals(fhform)) {
                                goldOrderService.autoFh(cashierBillsID);
                            }

                            //把订单状态改程03已收货
                            goldOrderService.updateFkState(cashierBillsID);
                            //分金币
                            transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");

                        }
                    }
                } else {
                    logger.info("超市现金收款公司积分不足，请立即充值，否则影响现金收款！");
                    logger.info("超市现金收款公司积分不足，请立即充值，否则影响现金收款！");
                    logger.info("超市现金收款公司积分不足，请立即充值，否则影响现金收款！");
                    logger.info("超市现金收款公司积分不足，请立即充值，否则影响现金收款！");

                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


        return "success";

    }

    /**
     * 自动贩卖机管理员登录
     *
     * @return
     */
    public String adminLogin() {
        String tip = null;
        Map<String, Object> map = new HashMap<String, Object>();
        String hgcode = request.getParameter("hgcode");
        String user = request.getParameter("user");
        if (posNum == null || posNum.equals("")) {
            posNum = CookieUtil.getCookieValue("posNum", request);
        }
        if (user != null && !user.equals("")) {
            tEshopCustomer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{user});
            if (tEshopCustomer == null) {
                //return 3;//用户名或者密码不正确
                map.put("flag", "用户不正确");
                JSONObject obj = JSONObject.fromObject(map);
                result = obj.toString();
                return "success";
            }
        }
        map = smSerivce.adminLogin(hgcode, tEshopCustomer);
        if (map.get("flag").equals("登录成功")) {
            Company c = (Company) map.get("company");
            CookieUtil.setCookie("user", tEshopCustomer.getAccount(), response);
            Staff ff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{tEshopCustomer.getStaffid()});
            CookieUtil.setCookie("staffcode", ff.getStaffCode(), response);
            CookieUtil.setCookie("companyIdentifier", companyIdentifier, response);
            CookieUtil.setCookie("comID", c.getCompanyID(), response);
            CookieUtil.setCookie("comName", c.getCompanyName(), response);
            CookieUtil.setCookie("staffName", ff.getStaffName(), response);
            CookieUtil.setCookie("staffid", ff.getStaffID(), response);
            CookieUtil.setCookie("fkw", tEshopCustomer.getPassword(), response);
            CookieUtil.setCookie("loginGuid", tEshopCustomer.getVendingMachineAdminLogoned(), response);
            CookieUtil.setCookie("posNum", posNum, response);
            CookieUtil.setCookie("hgcode", hgcode, response);
            if (user != null && !user.equals("")) {
                CookieUtil.setCookie("source", "scan", response);
            }else {
                CookieUtil.setCookie("source", "login", response);
            }
            ActionContext.getContext().put("companyIdentifier", companyIdentifier);
            account = new CAccount();
            account.setStaffID(tEshopCustomer.getStaffid());
            account.setStaffName(ff.getStaffName());
            account.setCompanyID(c.getCompanyID());
            account.setCompanyName(c.getCompanyName());
            ActionContext.getContext().getSession().put("account", account);

        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 验证用户是否被其他登陆
     * @return
     */
    public String validateAdminLogin() {
        String user = request.getParameter("user");
        String loginGuid = request.getParameter("loginGuid");
        Map<String, Object> map = new HashMap<String, Object>();
        if (loginGuid != null && !loginGuid.equals("")) {
            //验证用户是否被其他登陆
            String re = smSerivce.validateAdminLogin(user, loginGuid);
            map.put("re",re);
            try {
                if ("0".equals(re)) {
                    CookieUtil.setCookie("user", "", response);
                    map.put("tip", "账号被他人登陆请重新登陆");
                }
            } catch (Exception e) {

            }
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 推送页面
     *
     * @return
     */
    public String pageJump() {
        System.out.println("推送开门");
        String hgcode = request.getParameter("hgcode");
        String name = request.getParameter("name");
        if (posNum != null && !posNum.equals("") && name != null && !name.equals("") && hgcode != null && !hgcode.equals("")) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = basePath + "page/ea/main/marketing/supermarket/container/hgWeighing.jsp?posNum=" + posNum + "&staffName=" + name + "&hgcode=" + hgcode;
            smSerivce.pushMessage(posNum, url);
            System.out.println("推送页面");
        }
        return "success";
    }



    /////////////////////////////////////购物卡支付开始/////////////////////////////////////////////////////////////

    /**
     * 核对购物卡是否正确以及余额是否充足
     *
     * @return
     */
    public String checkScard() {
        String scard = request.getParameter("scard");
        String totalMoney = request.getParameter("totalMoney");
        String journalNum = request.getParameter("journalNum");
        String openid = request.getParameter("openid");

        Map<String, Object> map = new HashMap<String, Object>();
        if (openid != null && !openid.equals("")) {
            map = smSerivce.checkWxCard(scard, openid);
        } else {
            String sccId = smSerivce.getSccidByCard(scard);
            if ("".equals(sccId)) {
                map.put("result", "1");//不是购物卡
            } else {
                smSerivce.updateSelfCart(sccId, journalNum, "scard");
                map = smSerivce.checkScard(sccId, totalMoney, journalNum);

            }
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";

    }


    public String bindCard() {
        String sccId = request.getParameter("sccId");
        String openid = request.getParameter("openid");
        String wxbind = request.getParameter("wxbind");


        Map<String, Object> map = new HashMap<String, Object>();


        if ("lc".equals(wxbind)) {//练车签到绑定
            String bifid = makeAppointmentService.getbookId(sccId);
            if (!"".equals(bifid)) {
                smSerivce.bindCard(sccId, openid);
            }

            map.put("bifid", bifid);
            map.put("result", "6");
        } else {
            smSerivce.bindCard(sccId, openid);
            //购物绑定
            String totalMoney = request.getParameter("totalMoney");
            String journalNum = request.getParameter("journalNum");

            smSerivce.updateSelfCart(sccId, journalNum, "scard");
            map = smSerivce.checkScard(sccId, totalMoney, journalNum);

        }

        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";

    }

    /**
     * 验证刷脸购物卡支付
     *
     * @return
     */
    public String faceValiShopping() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String openid = request.getParameter("openid");
        String totalMoney = request.getParameter("totalMoney");
        String journalNum = request.getParameter("journalNum");
        Map<String, Object> map = smSerivce.faceValiShopping(openid);
        if ("5".equals(map.get("result"))) {
            String sccId = (String) map.get("sccId");
            smSerivce.updateSelfCart(sccId, journalNum, "scard");
            map = smSerivce.checkScard(sccId, totalMoney, journalNum);
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 购物卡支付
     *
     * @return
     */
    public String scardPayOrder() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            String journalNum = request.getParameter("journalNum");
            String morre = request.getParameter("totalMoney");
            String sccId = request.getParameter("sccId");
            String addressID = request.getParameter("addressID");
            String fhform = request.getParameter("fhform");


            Boolean bo = true;
            String cashierBillsID = "";
            if ("3".equals(fhform)) {
                CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});
                cashierBillsID = cc.getCashierBillsID();
                smSerivce.setPaySccid(journalNum, sccId);//扣积分是卡的人，订单是开门的人。

            } else {
                cashierBillsID = goldOrderService.generateSelfPayOrder(morre, sccId, journalNum, "05", addressID);
            }
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                goldOrderService.getPrice(cashierBillsID);
                bo = goldOrderService.generateSpoints(null, journalNum, morre, "05");

                //生成收款单
                if (bo == true) {

                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "j");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        System.out.println("复制订单积分或者金币入库单错误");
                    }


                    if (smSerivce.isSqPos(journalNum)) {
                        if (!"3".equals(fhform)) {
                            goldOrderService.autoFh(cashierBillsID);
                        }
                        //把订单状态改程03已收货
                        goldOrderService.updateFkState(cashierBillsID);
                        //分金币
                        transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
                    }
                }
            } else {//返回值为null，可能三方交易关联表已存在数据
                result = "YZF";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    /**
     * check用户地址
     *
     * @return
     */
    public String checkPayAddress() {
        Map<String, Object> map = new HashMap<String, Object>();
        String sccId = request.getParameter("sccId");
        StaffAddress staffaddress = smSerivce.getUserAddress(sccId);
        map.put("staffaddress", staffaddress);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";


    }

    // 增加修改收货地址
    public String addAddress() {
        String addressID = smSerivce.addAddress(sccId, staffAddress);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("addressID", addressID);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";
    }

/////////////////////////////////////购物卡支付结束//////////////////////////////////////////////////////////////


    public String showErCode() {
        request.setAttribute("journalNum", request.getParameter("journalNum"));
        request.setAttribute("totalNum", request.getParameter("totalNum"));
        request.setAttribute("totalMoney", request.getParameter("totalMoney"));
        Cookie[] cookies = request.getCookies();
        String comID = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("comID")) {
                comID = cookie.getValue();
                continue;
            }
        }
        if (comID.equals("") && ccompanyID != null && !ccompanyID.equals("")) {

            comID = smSerivce.getCompanyByCComID(ccompanyID).getCompanyID();

        }

        String sccid = smSerivce.searchSccidByCompany(comID);
        request.setAttribute("sccid", sccid);
        request.setAttribute("comID", comID);


        return "ercode";
    }

    public Boolean genSelfPay(String tradeCode, String morre, String sccid, String journalNum, String wfStatus4, String wfStatus1, String addressID) {
        Boolean bool = true;
        try {
            //生成订单
            /*System.out.print(tradeCode + morre + sccid + journalNum + wfStatus4 + wfStatus1);
            logger.error("生成订单参数：" + tradeCode + morre + sccid + journalNum + wfStatus4 + wfStatus1);*/
            String cashierBillsID = "";
            CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});

            try {
                UnPayRecord unPayRecord = smSerivce.getUnPayRecord(journalNum);
                if (unPayRecord != null && unPayRecord.getRemainNum() != null && !unPayRecord.getRemainNum().equals("")) {
                    sccid = unPayRecord.getSccId();
                    morre = unPayRecord.getRemainNum();

                }
                if (cc != null) {
                    cashierBillsID = cc.getCashierBillsID();
                }
            } catch (Exception ef) {
                ef.printStackTrace();
            }

            if (cashierBillsID.equals("")) {
                cashierBillsID = goldOrderService.generateSelfPayOrder(morre, sccid, journalNum, wfStatus4, addressID);
                cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});
            }
            /*System.out.print("cashierBillsID" + cashierBillsID);
            logger.error("cashierBillsID" + cashierBillsID);*/
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                Enroll e = (Enroll) baseBeanService.getBeanByHqlAndParams("from Enroll where cashierBillsID=?", new Object[]{cashierBillsID});
                if (e != null && e.getReserved1() != null && e.getReserved1() != "") {
                    goldOrderService.addMarKeting(e);
                }
                goldOrderService.getPrice(cashierBillsID);
                //生成收款单
                Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, morre, wfStatus4, wfStatus1);
                if (bo == true) {
                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "d");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        System.out.println("复制订单收款单错误");
                    }


                    if (smSerivce.isSqPos(journalNum)) {
                        if (!"智能货柜".equals(cc.getGoodsName())) {
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
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 支付宝交易成功异步回调页面
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unused"})
    public void getzfb() {
        //   System.out.println("----------------------------------");
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        String morre = null;
        String body = "";
        for (Iterator iter = requestParams.keySet().iterator(); iter
                .hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
                if (name.equals("total_fee")) {
                    morre = valueStr;
                }
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
            /*System.out.println("request:" + request);
            System.out.println("trade_no:" + request.getParameter("trade_no"));*/
        String trade_no = new String(request.getParameter("trade_no").getBytes(
                StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);


        // 商户订单号 单据表订单号
        String journalNum = new String(request.getParameter("out_trade_no").getBytes(
                StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        // 交易状态
        String trade_status = new String(request.getParameter(
                "trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String total_fee = new String(request.getParameter("total_fee")
                .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String seller_id = new String(request.getParameter("seller_id")
                .getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        try {
            if (AlipayNotify.verify(params)) {// 验证成功
                if (trade_status.equals("TRADE_SUCCESS")) {
                    if (seller_id.equals(AlipayConfig.seller_id)) {
                        //   System.out.println("订单号："+journalNum);
                        //处理一系列单子
                        //查看该订单是否生成
                        if (journalNum != null && !"".equals(journalNum)) {
                            if (body.indexOf("selfpay") != -1) {
                                PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                                SelfCart selfCart = smSerivce.getSelfCartByJum(journalNum);
                                if (selfCart != null && selfCart.getCardNum() != null) {
                                    String proType = smSerivce.getSelfByProType(selfCart.getPid());
                                    TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                                    if ("个人会员".equals(proType)) {
                                        genSelfPay(trade_no, morre, payBackupBill.getSccid(), journalNum, "01", "", payBackupBill.getAddressID());
                                    } else {
                                        bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", journalNum, (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), morre, "01", trade_no);
                                        //收银积分充值支付宝
                                        OperatorInfo operator = (OperatorInfo) baseBeanService.getBeanByHqlAndParams("from OperatorInfo where journalNum = ?", new Object[]{journalNum});
                                        if (operator != null) {
                                            operator.setStatus("01");
                                            operator.setPayWay("00");
                                            baseBeanService.saveOrUpdate(operator);
                                        }
                                    }
                                } else {
                                    genSelfPay(trade_no, morre, payBackupBill.getSccid(), journalNum, "01", "", payBackupBill.getAddressID());
                                }
                                response.getWriter().write("success");
                            } else {
                                logger.error("该订单已经生成过了");
                            }
                        } else {
                            logger.error("该订单号为null");
                        }
                    }
                }
            } else {// 验证失败
                logger.error("验证失败");
                response.getWriter().write("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 支付宝同步
    @SuppressWarnings({"rawtypes", "unused"})
    public String call_back() {
        return "paysuc";
    }


    /**
     * 取消订单，其实是取消加入购物车
     *
     * @return
     */
    public String cancelOrder() {
        try {
            smSerivce.cancelOrder(journalNum);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (posNum != null && !posNum.equals("")) {
            sumkSerivce.clearSqCart(posNum);
            Map<String, Object> map = sumkSerivce.getAccessCompany(posNum);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        return index();
    }

    /**
     * 查看订单详情修改商品
     *
     * @return
     */
    public String ajaxOrderDetail() {
        List<BaseBean> goodlist = smSerivce.viewOrderDetail(journalNum);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodlist", goodlist);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 支付成功订单详情
     *
     * @return
     */
    public String viewOrderDetail() {
        String carmID = request.getParameter("carmID");
        String posNum = request.getParameter("posNum");
        HttpServletResponse response = ServletActionContext.getResponse();

        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath()
                + "/";

        if (carmID != null && !carmID.equals("")) {
            try {
                response.sendRedirect(basePath
                        + "/ea/qrshare/ea_theVehicleDetails.jspa?carManage.carmID="
                        + carmID + "&posNum=" + posNum + "&paysuc=suc");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            List<BaseBean> goodlist = smSerivce.viewOrderDetail(journalNum);
            if (goodlist.size() == 0) {
                 //  goodlist = smSerivce.viewGoodsBills(journalNum);
                if (goodlist.size() == 0) {
                    String cbId = "";
                    String sccId = "";
                    Map<String, String> mmp = makeAppointmentService
                            .getBookInfoCash(journalNum);
                    if (mmp.size() != 0) {
                        cbId = mmp.get("cbId");
                        sccId = mmp.get("sccId");
                    }

                    try {
                        response.sendRedirect(basePath
                                + "/ea/mappointment/ea_bookingDetails.jspa?cbId="
                                + cbId + "&sccId=" + sccId + "&dp=1&paysuc=suc");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else{
                List<BaseBean> newlist =  new ArrayList<BaseBean>();
                List<String> ppidlist = new ArrayList<String>();
                for(int i = 0;i<goodlist.size();i++){
                    SelfCart selfCart = (SelfCart) goodlist.get(i);
                    if(selfCart.getDeptId()!=null&&!selfCart.getDeptId().equals("")){
                        //只能活鬼
                         if(!ppidlist.contains(selfCart.getPid())){
                             ppidlist.add(selfCart.getPid());
                             if(!"KG".equals(selfCart.getVariableID())) {
                                 int inum = 0;

                                 for (int j = 0; j < goodlist.size(); j++) {
                                     SelfCart selfCart1 = (SelfCart) goodlist.get(j);
                                     if(selfCart.getPid().equals(selfCart1.getPid())) {
                                         inum += Integer.parseInt(selfCart1.getItemNum());
                                     }
                                 }
                                 selfCart.setItemNum(inum+"");
                             }

                             newlist.add(selfCart);
                         }else{
                             continue;
                         }


                    }else{
                        break;
                    }
                }
                 if(newlist!=null&&newlist.size()>0){
                    goodlist = newlist;
                 }


            }
            ContactCompany contactCompany = smSerivce
                    .getPrintCompanyInfo(goodlist);
            request.setAttribute("goodlist", goodlist);
            request.setAttribute("contactCompany", contactCompany);
        }
        return "sucorder";
    }

    /**
     * 线下支付订单详情
     *
     * @return
     */
    public String viewOrderDetailOffline() {
        String carmID = request.getParameter("carmID");
        String posNum = request.getParameter("posNum");
        String payId = request.getParameter("payId");

        HttpServletResponse response = ServletActionContext.getResponse();

        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

        request.setAttribute("payOffline", (DtPayOffline) baseBeanService.getBeanByHqlAndParams("from DtPayOffline where payOfflineId = ?", new Object[]{payId}));

        if (carmID != null && !carmID.equals("")) {
            try {
                response.sendRedirect(basePath + "/ea/qrshare/ea_theVehicleDetails.jspa?carManage.carmID=" + carmID + "&posNum=" + posNum + "&paysuc=suc");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            List<BaseBean> goodlist = smSerivce.viewOrderDetail(journalNum);
            if (goodlist.size() == 0) {
                String cbId = "";
                String sccId = "";
                Map<String, String> mmp = makeAppointmentService
                        .getBookInfoCash(journalNum);
                if (mmp.size() != 0) {
                    cbId = mmp.get("cbId");
                    sccId = mmp.get("sccId");
                }

                try {
                    response.sendRedirect(basePath + "/ea/mappointment/ea_bookingDetails.jspa?cbId=" + cbId + "&sccId=" + sccId + "&dp=1&paysuc=suc");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ContactCompany contactCompany = smSerivce.getPrintCompanyInfo(goodlist);
            request.setAttribute("goodlist", goodlist);
            request.setAttribute("contactCompany", contactCompany);
        }
        return "sucorder_offline";
    }

    /**
     * 查询支付结果
     *
     * @returnca
     */
    public String searchPayResult() {

        boolean bool = smSerivce.searchPayResult(journalNum);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", bool);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 查询购物袋
     *
     * @return
     */
    public String searchShoppBag() {
        Cookie[] cookies = request.getCookies();
        String comID = request.getParameter("comID");
        if (comID == null || comID.equals("")) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("comID")) {
                    comID = cookie.getValue();
                    break;
                }
            }
        }
        Map<String, Object> map = smSerivce.searchShoppBag(comID, lxType);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";
    }

    /**
     * 打印小票
     *
     * @return
     */
    public String printTicket() {

        Cookie[] cookies = request.getCookies();
        String staffcode = "";
        if (posNum == null || posNum.equals("")) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("staffcode")) {
                    staffcode = cookie.getValue();
                    continue;
                }
                if (cookie.getName().equals("pos")) {
                    posNum = cookie.getValue();
                    continue;
                }
            }
        }
        CashierBills bb = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});
        String zf = "";
        if (bb != null) {
            zf = "00".equals(bb.getWfStatus4()) ? "微信支付" : "01".equals(bb.getWfStatus4()) ? "支付宝" : "05".equals(bb.getWfStatus4()) ? "积分支付" : "07".equals(bb.getWfStatus4()) ? "金币支付" : "现金支付";
            if ("".equals(staffcode)) {
                if (zf.equals("现金支付") || zf.equals("积分支付")) {
                    if (bb.getInputid() != null && !bb.getInputid().equals("")) {
                        Staff f = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{bb.getInputid()});
                        if (f != null) {
                            staffcode = f.getStaffCode();
                        }
                    }
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("staffcode", staffcode);
        map.put("posNum", posNum);
        map.put("zf", zf);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    /**
     * 积分详情
     */

    public String getbpDetail() {
        String sccId = request.getParameter("sccId");
        BonusPoints bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{sccId});
        request.setAttribute("bp", bp);

        return "sjfdetail";
    }

    /**
     * 检查是否扫码的是购物卡
     *
     * @return
     */
    public String checkScardJiFen() {
        String scard = request.getParameter("scard");
        Map<String, Object> map = smSerivce.getSccIdByscard(scard);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 充值
     *
     * @return
     */
    public String integralTopup() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String staffId = (String) baseBeanService.getObjectBySqlAndParams("select se.staffid from t_eshop_cuscom se where se.sccid = ?", new Object[]{sccId});
        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(" from Staff where staffID = ?", new Object[]{staffId});
        String cardNum = (String) baseBeanService.getObjectBySqlAndParams("select gc.cardnumber from dt_giftcards gc where gc.staffid = ? ", new Object[]{staffId});
        request.setAttribute("staff", staff);
        request.setAttribute("cardNum", cardNum);
        if (ccompanyID != null && !ccompanyID.equals("")) {
            Company cc = smSerivce.getCompanyByCComID(ccompanyID);
            comID = cc.getCompanyID();
            request.setAttribute("comID", comID);
            request.setAttribute("companyName", cc.getCompanyName());
        }
        return "topup";
    }

    ///////////////////////////////////////////////////微信付款码/////////////////////////////////////////////////////////////////////
    public String payWCode() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String device_id = request.getParameter("device_id");
            String sub_appid = "";
            String sub_mch_id = "";
            if (device_id != null && !device_id.equals("")) {
                try {
                    Object obj = faceDeviceSerivce.getWXData(device_id);

                    if (obj != null) {
                        Object[] objs = (Object[]) obj;
                        sub_appid = objs[0].toString();
                        sub_mch_id = objs[1].toString();
                    }
                } catch (Exception er) {

                }
            }
            String openid = WeixinUtil.authcodeopenid(barCode, sub_appid, sub_mch_id);
            if (openid != null && !openid.equals("")) {
                //生成微分金用户
                WxUserInfo wxUserInfo = new WxUserInfo();
                wxUserInfo.setOpenid(openid);

                String access_Token = payFaceSerivce.getAccessTokenFromDataBase();
                String nickname = "";
                try {
                    System.out.println("access_Token:" + access_Token);
                    System.out.println("openid:" + openid);
                    nickname = WeixinUtil.getUserByToken(access_Token, openid);
                } catch (Exception e) {
                    e.printStackTrace();
                    nickname = "佚名";
                }

                wxUserInfo.setNickname(nickname);
                String comID = request.getParameter("comID");
                String morre = request.getParameter("totalMoney");
                String sccid = wfjAccountService.createAccount(wxUserInfo, smSerivce.getSccidBycomID(comID), null);
                if (remainMoney != null && !remainMoney.equals("") && !remainMoney.equals(morre)) {

                    morre = remainMoney;
                }
                micropay.setAuth_code(barCode);
                micropay.setAttach("selfpay");
                micropay.setBody(Constant.SuperGood);
                micropay.setTotal_fee(WeChatUtils.getMoney(morre));


                WxPayDto dw = WeixinUtil.payCard(micropay);

                Map<String, Object> map = new HashMap<String, Object>();
                WxPayDto payDto = null;
                if (dw != null && dw.getResult_code().equals("SUCCESS")) {
                    payDto = WchatPay.searchServOrder(micropay.getOut_trade_no(), sub_appid, sub_mch_id);
                    if (payDto != null && payDto.getReturn_code().equals("SUCCESS")) {
                        if (payDto.getTrade_state().equals("SUCCESS")) {
                            SelfCart selfCart = smSerivce.getSelfCartByJum(micropay.getOut_trade_no());
                            if (selfCart != null && selfCart.getCardNum() != null) {//积分充值
                                TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                                bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", micropay.getOut_trade_no(), (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), morre, "00", payDto.getTransaction_id());
                            } else {
                                genSelfPay(payDto.getTransaction_id(), morre, sccid, micropay.getOut_trade_no(), "00", "00", null);
                            }
                            if (sub_appid != null && !sub_appid.equals("")) {
                                payFaceSerivce.recordTradeMoney(device_id, morre, micropay.getOut_trade_no(), "scancode", openid, nickname);
                            }
                        }
                    }
                    map.put("result_code", payDto.getResult_code());
                    map.put("trade_state", payDto.getTrade_state());

                } else {
                    faceDeviceSerivce.savePayBill(micropay.getOut_trade_no(), openid, nickname, dw.getTransaction_id(), WeChatUtils.getMoney(morre), "", device_id);//分单位
                    map.put("result_code", dw.getResult_code());
                    map.put("err_code", dw.getErr_code());

                }


                map.put("cresccid", sccid);
                JSONObject obj = JSONObject.fromObject(map);
                result = obj.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";

    }

    /**
     * 微信回调同步获取支付结果
     */
    public String weChatBaseRep() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String out_trade_no = request.getParameter("journalNum");
        String morre = request.getParameter("totalMoney");
        String cresccid = request.getParameter("cresccid");
        String device_id = request.getParameter("device_id");
        String sub_appid = "";
        String sub_mch_id = "";
        if (device_id != null && !device_id.equals("")) {
            try {
                Object obj = faceDeviceSerivce.getWXData(device_id);

                if (obj != null) {
                    Object[] objs = (Object[]) obj;
                    sub_appid = objs[0].toString();
                    sub_mch_id = objs[1].toString();
                }
            } catch (Exception er) {

            }
        }
        WxPayDto payDto = WchatPay.searchServOrder(out_trade_no, sub_appid, sub_mch_id);
        if (payDto != null && payDto.getTrade_state().equals("SUCCESS")) {
            if (goldOrderService.isCalledPay(out_trade_no)) {
                SelfCart selfCart = smSerivce.getSelfCartByJum(out_trade_no);
                if (selfCart != null && selfCart.getCardNum() != null) {
                    TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                    bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", out_trade_no, (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), morre, "00", payDto.getTransaction_id());
                } else {
                    genSelfPay(payDto.getTransaction_id(), morre, cresccid, out_trade_no, "00", "00", null);
                }
            }

            if (sub_appid != null && !sub_appid.equals("")) {
                PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(out_trade_no);
                String openid = "";
                String nickname = "";
                if (payBackupBill != null) {
                    openid = payBackupBill.getOpenId();
                    nickname = payBackupBill.getNickname();
                }
                faceDeviceSerivce.recordTradeMoney(device_id, morre, out_trade_no, "scancode", openid, nickname);

            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", payDto.getTrade_state());
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();

        return "success";

    }


    ////////////////////////////////////////////////支付宝付款码/////////////////////////////////////////////////////////////////////

    public String alipayWCode() {
        String out_trade_no = request.getParameter("journalNum");
        String comID = request.getParameter("comID");
        String morre = request.getParameter("totalMoney");
        TradePayReuslt tradePayReuslt = null;

        String sccid = "";
        try {
            TradePayParam tradePayParam = new TradePayParam();
            tradePayParam.setOut_trade_no(out_trade_no);
            tradePayParam.setStore_id(comID);
            tradePayParam.setSubject(Constant.SuperGood);
            tradePayParam.setBody("selfpay");
            tradePayParam.setTotal_amount(morre);
            tradePayParam.setAuth_code(barCode);
            tradePayReuslt = AlipayTradePay.tradePay(tradePayParam);
            //成功
            WxUserInfo wxUserInfo = new WxUserInfo();
            wxUserInfo.setUserid(tradePayReuslt.getBuyer_user_id());
            wxUserInfo.setNickname("佚名");
            sccid = wfjAccountService.createAccount(wxUserInfo, smSerivce.getSccidBycomID(comID), null);

            if ("10000".equals(tradePayReuslt.getCode())) {

                SelfCart selfCart = smSerivce.getSelfCartByJum(tradePayParam.getOut_trade_no());
                if (selfCart != null && selfCart.getCardNum() != null) {
                    TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                    bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", tradePayParam.getOut_trade_no(), (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), morre, "01", tradePayReuslt.getTrade_no());
                } else {
                    genSelfPay(tradePayReuslt.getTrade_no(), morre, sccid, out_trade_no, "01", "", null);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", tradePayReuslt.getCode());
        map.put("cresccid", sccid);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";

    }

    /**
     * 阿里付款码查询支付结果
     */
    public String alipayBaseRep() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String out_trade_no = request.getParameter("journalNum");
        String morre = request.getParameter("totalMoney");
        String cresccid = request.getParameter("cresccid");
        TradePayReuslt tradePayReuslt = AlipayTradePay.tradeQuery(out_trade_no);

        if (tradePayReuslt != null && tradePayReuslt.getCode().equals("10000")) {
            if (tradePayReuslt.getTrade_status().equals("TRADE_SUCCESS")) {
                if (goldOrderService.isCalledPay(out_trade_no)) {
                    SelfCart selfCart = smSerivce.getSelfCartByJum(out_trade_no);
                    if (selfCart != null && selfCart.getCardNum() != null) {
                        TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                        bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", out_trade_no, (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), morre, "01", tradePayReuslt.getTrade_no());
                    } else {
                        genSelfPay(tradePayReuslt.getTrade_no(), morre, cresccid, out_trade_no, "00", "00", null);
                    }
                }
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", tradePayReuslt.getTrade_status());
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();

        return "success";

    }

    /**
     * 刷脸支付成功回调
     *
     * @return
     */
    public String facePayCallBack() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String journalNum = request.getParameter("journalNum");

        PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);

        if (payBackupBill != null) {
            //记录交易额
            payFaceSerivce.recordTradeMoney(payBackupBill.getDevice_id(), payBackupBill.getMoney(), payBackupBill.getJournalNum(), "face", payBackupBill.getOpenId(), payBackupBill.getNickname());
            //生成系统单子
            if (payBackupBill.getJournalNum() != null && payBackupBill.getJournalNum().length() > 10) {
                //成功
                WxUserInfo wxUserInfo = new WxUserInfo();
                wxUserInfo.setOpenid(payBackupBill.getOpenId());
                wxUserInfo.setNickname(payBackupBill.getNickname());
                String sccid = wfjAccountService.createAccount(wxUserInfo, smSerivce.getSccidBycomID(payBackupBill.getCompanyId()), null);


                SelfCart selfCart = smSerivce.getSelfCartByJum(payBackupBill.getJournalNum());
                if (selfCart != null && selfCart.getCardNum() != null) {
                    TEshopCusCom cusCom = smSerivce.getTEshopByCum(selfCart.getCardNum());
                    bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025", payBackupBill.getJournalNum(), (cusCom != null ? cusCom.getStaffid() : ""), (cusCom != null ? cusCom.getSccId() : ""), payBackupBill.getMoney(), "00", payBackupBill.getTransaction_id());
                } else {
                    genSelfPay(payBackupBill.getTransaction_id(), payBackupBill.getMoney(), sccid, payBackupBill.getJournalNum(), "00", "00", null);
                }
            } else {
                //即插即用
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "success");
                JSONObject jo = JSONObject.fromObject(map);
                result = jo.toString();
            }
        }


        return "success";

    }


    /**
     * 收银端开通购物卡
     *
     * @return
     */
    public String openCard() {

        return "card";
    }

    /**
     * 开通完成加入会员
     *
     * @return
     */
    public String joinVip() {
        beans = smSerivce.getMemberList();
        return "joinvip";
    }

    /**
     * 获取交班信息
     *
     * @return
     */
    public String getChangeWorkInfo() {
        Cookie[] cookies = request.getCookies();

        String pos = "";  //终端号
        String staffcode = "";//员工编号
        String comID = "";//公司ID
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("pos")) {
                pos = cookie.getValue();
                continue;
            }

            if (cookie.getName().equals("staffcode")) {
                staffcode = cookie.getValue();
                continue;
            }
            if (cookie.getName().equals("comID")) {
                comID = cookie.getValue();
                break;
            }

        }
        ContactCompany contactCompany = smSerivce.getCCompanyBycomID(comID);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pos", pos);
        map.put("staffcode", staffcode);
        if (contactCompany != null) {
            map.put("companyName", contactCompany.getCompanyName());
            map.put("companyAddress", contactCompany.getCompanyAddr());

        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 交接班验证密码
     *
     * @return
     */
    public String checkChangeUser() {

        Cookie[] cookies = request.getCookies();
        String fkw = "";
        String psw = Utilities.MD5(request.getParameter("psw"));
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("fkw")) {
                fkw = cookie.getValue();
                break;
            }

        }
        Map<String, Object> map = new HashMap<String, Object>();
        if ("".equals(fkw)) {
            map.put("result", "1");//错误数据
        } else if (fkw.equals(psw)) {
            map.put("result", "0");//密码正确
        } else {
            map.put("result", "2");//密码错误
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 验证订单是否生成
     *
     * @return
     */
    public String checkRepeatPay() {
        CashierBills bb = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ? and billsType = ?", new Object[]{journalNum, "项目收入预算单"});
        Map<String, Object> map = new HashMap<String, Object>();

        if (bb == null) {
            map.put("result", "1");//不重复
        } else {

            if ("金币计时".equals(bb.getProjectName()) && "01".equals(bb.getFkStatus())) {
                map.put("result", "1");//不重复
            } else if (bb.getGoodsCoding() != null && "智能货柜".equals(bb.getGoodsName()) && bb.getFkStatus().equals("01")) {
                map.put("result", "1");//不重复
            } else {
                map.put("result", "2");//重复
            }


        }
        JSONObject jo = JSONObject.fromObject(map);

        this.result = jo.toString();
        return "success";
    }

    /**
     * 跳转改价页面
     *
     * @return
     */
    public String getProudct() {
        HttpServletRequest request = ServletActionContext.getRequest();

        //价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
        String priceType = request.getParameter("pritype");
        String activityID = request.getParameter("activityID");
        if (comID == null || comID.equals("")) {
            ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID = ?", new Object[]{ppid});
            comID = pp.getCompanyID();
        }
        StringBuilder sql = VOtool.getShowPriceSql(7, (Integer.valueOf(priceType == null || "".equals(priceType) ? "0" : priceType)) + 1);
        List<BaseBean> prolist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{comID, activityID});

        /*if (prolist != null && prolist.size() > 0) {
            Object o = prolist.get(0);
            Object[] obj = (Object[]) o;
            if( obj[11]!=null&& !obj[11].equals("")){
                String suid = obj[11].toString();

                StringBuilder psssql = new StringBuilder("select pss.susid,p.goodsname,pss.amount,pss.suskey,pss.type_ppid");
                psssql.append(" from dt_pro_setup s, dt_pro_setup_sub pss, dt_productpackaging p");
                psssql.append(" where s.suid = pss.suid");
                psssql.append(" and pss.type_ppid = p.ppid");
                psssql.append(" and s.suid=?");
                List<Object> dlyjlist = baseBeanService.getListBeanBySqlAndParams(psssql.toString(), new Object[]{suid});
                request.setAttribute("dlyj", dlyjlist);
            }

        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("prolist", prolist);
        /*String ret = request.getParameter("ret");
        request.setAttribute("ret", ret);*/
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     * 保存变价信息
     *
     * @return
     */
    public String savePrice() {
        HttpServletRequest request = ServletActionContext.getRequest();
        BigDecimal dj = new BigDecimal(request.getParameter("dj"));
        BigDecimal cb = new BigDecimal(request.getParameter("cb"));
        BigDecimal yj = new BigDecimal(request.getParameter("yj"));
        BigDecimal dl = new BigDecimal(request.getParameter("dl"));
        BigDecimal pri = new BigDecimal(request.getParameter("pri"));
        String pritype = request.getParameter("pritype");
        String comid = request.getParameter("comid");
        String userid = request.getParameter("userid");
        String priceid = request.getParameter("priceid");
        String ppid = request.getParameter("ppid");
        String jlid = request.getParameter("jlid");
        Boolean falg = true;
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseBean> parem = new ArrayList<BaseBean>();
        try {
            if (comid == null || comid.equals("")) {
                ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID = ?", new Object[]{ppid});
                comid = pp.getCompanyID();
            }
            Pricechange pc = null;
            if (pritype != null && pritype.equals("5") && priceid != null && !priceid.equals("")) {
                pc = (Pricechange) baseBeanService.getBeanByHqlAndParams("from Pricechange where pcid=? and sqid=?", new Object[]{priceid, jlid});
            }
            if (pc == null) {
                pc = new Pricechange();
                pc.setPcid(serverService.getServerID("priceChange"));
                pc.setPriceid(priceid);
                pc.setPricestatus(pritype);
            }
            pc.setSqid(jlid);
            pc.setPpid(ppid);
            pc.setComId(comid);
            pc.setStaffid(userid);
            pc.setEfPrice(cb.toString());
            pc.setBrokerage(yj.toString());
            pc.setRePrice(dj.toString());
            pc.setProxySumPrice(dl.toString());
            pc.setSjdate(new Date());
            parem.add(pc);
            smSerivce.savePrice(parem);
            map.put("pcid", pc.getPcid());
        } catch (Exception e) {
            falg = false;
            e.printStackTrace();
        }
        map.put("falg", falg);
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     * 补充变价信息
     *
     * @return
     */
    public String supplementPrice() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String pcid = request.getParameter("pcid");
        String sgrId = request.getParameter("sgrId");
        Boolean falg = true;
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseBean> parem = new ArrayList<BaseBean>();
        try {
            if (pcid != null && !pcid.equals("") && sgrId != null && !sgrId.equals("")) {
                Pricechange pc = (Pricechange) baseBeanService.getBeanByHqlAndParams("from Pricechange where pcid=?", new Object[]{pcid});
                if (pc != null) {
                    pc.setSqid(sgrId);
                    parem.add(pc);
                    smSerivce.savePrice(parem);
                }
            }
        } catch (Exception e) {
            falg = false;
            e.printStackTrace();
        }
        map.put("falg", falg);
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     * 变价验证操作员密码是否正确
     *
     * @return
     */
    public String verification() {
        String pw = request.getParameter("pw");
        String staffID = request.getParameter("staffID");
        String comID = request.getParameter("comID");
        String telphone = request.getParameter("telphone");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = smSerivce.searchPwByStaff(staffID, comID, telphone);
        List<String> sbpwd = (List<String>) map1.get("paymentCode");
        String paymentCode = "";
        if (!sbpwd.contains("1") && !sbpwd.contains("0")) {
            if (sbpwd != null && sbpwd.contains(pw)) {
                paymentCode = pw;
                if (comID == null || comID.equals("")) {
                    comID = (String) map1.get("comID");
                }
                paymentCode = "2";
                if (staffID == null || staffID.equals("")) {
                    Map<String, String> m = smSerivce.getCashOperator(telphone, comID, pw);
                    map.put("staffID", m.get("staffID"));
                    map.put("staffName", m.get("staffName"));
                }
            } else {
                paymentCode = "3";//密码错误
            }
        }
        map.put("result", paymentCode);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();

        return "success";

    }

    //////////////////////////////////////////////扫描记录////////////////////////////////////////////////////////////////////////////////

    /**
     * 查询扫描结果
     *
     * @return
     */
    public String getScanRecord() {

        String companyId = request.getParameter("companyId");
        String staffID = "";
        if (posNum == null || posNum.equals("")) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("comID")) {
                    comID = cookie.getValue();
                    continue;
                }
                if (cookie.getName().equals("user")) {
                    staffID = cookie.getValue();
                    continue;
                }
            }
        } else {
            comID = companyId;
        }

        List<BaseBean> list = smSerivce.getScanRecord(posNum, staffID, comID);
        request.setAttribute("list", list);

        return "nopay";

    }


    /**
     * 第一次扫码或者加入购物车
     *
     * @return
     */
    public String joinScanRecord() {
        Cookie[] cookies = request.getCookies();
        String comID = "";
        String staffID = "";
        if (posNum == null || posNum.equals("")) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("comID")) {
                    comID = cookie.getValue();
                    continue;
                }
                if (cookie.getName().equals("user")) {
                    staffID = cookie.getValue();
                    scanGoods.setStaffID(staffID);
                    continue;
                }
            }
            scanGoods.setCompanyId(comID);
        }

        String sgrId = smSerivce.joinScanRecord(scanGoods);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sgrId", sgrId);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 增加数量或者减少数量
     *
     * @return
     */
    public String joinOrReduceGoods() {
        String sgrId = request.getParameter("sgrId");
        String itemNum = request.getParameter("itemNum");

        String id = smSerivce.joinOrReduceGoods(sgrId, itemNum, scanGoods);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     * 删除记录
     *
     * @return
     */
    public String deleteScanRecord() {
        String sgrId = request.getParameter("sgrId");
        smSerivce.deleteScanRecord(sgrId);

        return "success";
    }


    /**
     * 添加理由
     *
     * @return
     */
    public String addReason() {
        smSerivce.addReason(scanGoods.getRelateID(), scanGoods.getReason());
        return "success";
    }

    //////////////////////////////////////////////////////////////转他人支付/////////////////////////////////////////////////////////////////////    

    /**
     * 获取采购商
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getCom() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String valString = request.getParameter("valString");
        StringBuilder sqlString = new StringBuilder("select cc.compnay_id,c.companyname,t.sccid from dtcontactcompany c, dt_ccom_com cc,t_eshop_cuscom t");
        sqlString.append(" where c.ccompanyid = cc.ccompany_id and cc.compnay_id=t.companyid and c.companyname like ?");
        sqlString.append(" and t.account is not null and cc.compnay_id is not null and c.companyname is not null");
        List<Object> list = baseBeanService.getListBeanBySqlAndParams(sqlString.toString(), new Object[]{"%" + valString + "%"});
        JSONObject obj = new JSONObject();
        obj.accumulate("objvalue", list);
        result = obj.toString();
        return "success";
    }

    /**
     * 获取采购商员工
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getStaff() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String valString = request.getParameter("valString");
        String comid = request.getParameter("comid");
        String hql = "select staffID,staffName from view_sc where companyID = ?  and staffName like ?";
        Object[] params = {comid, "%" + valString + "%"};
        List<Object> list = baseBeanService.getListBeanBySqlAndParams(hql, new Object[]{comid, "%" + valString + "%"});
        JSONObject obj = new JSONObject();
        obj.accumulate("objvalue", list);
        result = obj.toString();
        return "success";
    }

    /**
     * 转他人支付生成订单
     *
     * @return
     */
    public String transferPayOrder() {
        try {
            String comID = request.getParameter("comID");
            String journalNum = request.getParameter("journalNum");
            String morre = request.getParameter("totalMoney");
            String state = request.getParameter("state");
            String comName = request.getParameter("comname");
            String zzrstaffid = request.getParameter("zzrstaffid");
            String zzrstaffname = request.getParameter("zzrstaffname");
            String cwstaffid = request.getParameter("cwstaffid");
            String cwstaffname = request.getParameter("cwstaffname");
            String sgystaffid = request.getParameter("sgystaffid");
            String sgystaffname = request.getParameter("sgystaffname");
            String sccId = smSerivce.getSccidBycomID(comID);//公司Sccid

            smSerivce.updateSelfCart(sccId, journalNum, "transfer");

            String cashid = goldOrderService.generateSelfPayOrder(morre, sccId, journalNum, null, null);//订单

            transferPay tPay = new transferPay();
            tPay.setCbatpid(serverService.getServerID("transferPay"));
            tPay.setCashierbillsid(cashid);
            tPay.setJournalnum(journalNum);
            tPay.setCgscomid(comID);
            tPay.setCgscomname(comName);

            if (state != null && state.equals("01")) {
                tPay.setMapkey("责任人id-责任人名称");
                tPay.setMapval(zzrstaffid + "-" + zzrstaffname);
            } else {
                tPay.setMapkey("采购商财务id-采购商财务名称-采购人员id-采购人员名称");
                tPay.setMapval(cwstaffid + "-" + cwstaffname + "-" + sgystaffid + "-" + sgystaffname);
            }

            goldOrderService.transferPayOrder(tPay);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "success";
    }
///////////////////////////////////////////////////货柜自助服务//////////////////////////////////////////////////////////////////////////////////////

    /**
     * 扫码二维码调用的方法没有登陆跳转登录，登陆后跳转授权页面，如果之前授权过，登陆
     *
     * @return
     */
    public String getScanhg() {
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (tc == null) {
            //   String url = request.getHeader("Referer");
            String url = request.getRequestURL().toString() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
            session.setAttribute("preUrl", url);
            return "wfjlogin";
        }
        String posNum = smSerivce.getPosNumByHg(hgcode);

        //先判断是否有用户在用
       // String user = smSerivce.searchHgUser(hgcode);
         HgRelateUser hgRelateUser1 = smSerivce.getHgUser(hgcode);
        String user = "";
        if(hgRelateUser1!=null){
             user = hgRelateUser1.getSccId();
        }
        if (!"".equals(user)&&!user.equals(tc.getSccId())) {
            //有记录，且不是同一个人判断时间

            Date st = hgRelateUser1.getScanDate();
            System.out.println(st);
            Date cu = new Date();

            long diff = cu.getTime()-st.getTime();
            long diffmi =  diff/ (60 * 1000);
            System.out.println("diffmi"+diffmi);
            if (diffmi <=5) { //小于5分钟，让之前的人记录操作
                return "useing";
            }else{
                //大于5分钟，清理掉

                smSerivce.delHgRelateUser(hgcode);
                smSerivce.clearSqCart(hgcode);//清理购物车
            }

        }

        String door = request.getParameter("door");


        session.setAttribute("preUrl", "");
        //先判断是否有未结算的单子
        cbid = smSerivce.getUnPayCash(tc.getStaffid());


        if (!"".equals(cbid)) {
            //说明有未结算单子
            lastPay = "yes";
            sccId = tc.getSccId();

            smSerivce.pushAudio(posNum,"您上次购物有未支付的订单，请先支付！");
            return "topaydetail";
        }


        CusComAuth cusComAuth = smSerivce.getAuthcc(tc.getSccId());
        //  String wxfee = "0";
        String coinfee = "0";
        if (cusComAuth != null) {
            //     wxfee = cusComAuth.getWxfee();
            coinfee = cusComAuth.getCoinfee();
        }
        request.setAttribute("coinfee", coinfee);

        //    request.setAttribute("wxfee",wxfee);
        session.setAttribute("hgcode", hgcode);
        //   request.setAttribute("hgcode",hgcode);
        request.setAttribute("sccId", tc.getSccId());
        if (coinfee.equals("0")) {
            //只要有一种没开通，每次都提醒开通 如果提示下次是否提示，不提示，可以在我的钱包中开通
            return "auth";
        }
        request.setAttribute("loginMode", "scan");
        //   smSerivce.addHgRelateUser(hgcode,tc.getSccId(),"scan");
        Staff staff = smSerivce.getInfoBySccid(tc.getSccId());

        request.setAttribute("staffname", staff.getStaffName());
        request.setAttribute("staffID", staff.getStaffID());

        //推送消息给终端机调用开门

        if (door != null && !door.equals("")) {


          HgRelateUser hgRelateUser = smSerivce.getHgUser(hgcode);
            String loginMode = "";
            if (hgRelateUser == null) {
                smSerivce.addHgRelateUser(hgcode, tc.getSccId(), "scan");


                String hrId = smSerivce.addResult(tc.getSccId(),"",hgcode,"");
                smSerivce.pushSeq(posNum,hrId);
                request.setAttribute("hrId", hrId);

                String url = basePath + "ea/sm/ea_getOpenSuc.jspa?posNum=" + posNum + "&sccId=" + tc.getSccId() + "&loginMode=scan&door="+door+"&hrId="+hrId;
                smSerivce.pushMessage(posNum, url);
                System.out.println("推送页面");
                smSerivce.pushOpenDoor(posNum, door);
                System.out.println("推送开门");
                smSerivce.updateUser(hgcode);
                smSerivce.pushAudio(posNum,"门锁已开请拉开柜门挑选商品");

            }else{

                String hrId = smSerivce.getHgResultHrId(tc.getSccId(),hgcode);
                request.setAttribute("hrId", hrId);


                smSerivce.pushSeq(posNum,hrId);
                request.setAttribute("hrId", hrId);

                String url = basePath + "ea/sm/ea_getOpenSuc.jspa?posNum=" + posNum + "&sccId=" + tc.getSccId() + "&loginMode=scan&door="+door+"&hrId="+hrId;
                smSerivce.pushMessage(posNum, url);
                System.out.println("同一个人再次推送页面");
                smSerivce.pushOpenDoor(posNum, door);
                System.out.println("同一个人再次推送开门");
                smSerivce.updateUser(hgcode);
                smSerivce.pushAudio(posNum,"再次门锁已开请拉开柜门挑选商品");

            }
        } else {
            return "selectdoor";

        }

        return "opensuc";
    }

    /**
     * 添加用户信息
     *
     * @return
     */
    public String addHgRelateUser() {
        String loginMode = request.getParameter("loginMode");
        smSerivce.addHgRelateUser(hgcode, sccId, loginMode);
        return "success";
    }


    /**
     * 更新开门状态
     *
     * @return
     */
    public String updateUser() {
        smSerivce.updateUser(hgcode);

        return "success";
    }


    /**
     * 验证状态 暂时没用
     *
     * @return
     */
    public String checkStatus() {
        HgRelateUser hgRelateUser = smSerivce.getHgUser(hgcode);
        String doorState = "0";
        if (hgRelateUser != null) {
            doorState = hgRelateUser.getDoorState();

            //开门状态
        }else{
            //说明已经关门了
            doorState = "0";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("doorState", doorState);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 获取服务
     *
     * @returnde
     */
    public String getAuthcc() {
        String sccId = request.getParameter("sccId");
        String loginMode = request.getParameter("loginMode");
        String totalMoney = request.getParameter("totalMoney");
        String journalNum = request.getParameter("journalNum");

        Map<String, Object> map = new HashMap<String, Object>();
        CusComAuth cusComAuth = smSerivce.getAuthcc(sccId);

        String remainMoney = totalMoney;

        if ("scard".equals(loginMode)) {
            //判断购物卡余额


            map = smSerivce.checkHgcard(sccId, totalMoney);
            String r = (String) map.get("result");
            if ("3".equals(r)) {
                //积分不够扣，扣掉一部分
                try {
                    //         goldOrderService.generateSelfPayOrder(totalMoney, sccId, journalNum, "10", "");

                    remainMoney = goldOrderService.mixPay(journalNum, totalMoney, "10", sccId, hgcode);

                    map.put("coinfee", "0");//终端跳转到付款页面
                } catch (Exception e) {

                }
            }


        } else if ("phone".equals(loginMode) || "scan".equals(loginMode)) {
            //验证手机号登陆 先判断之前是否授权过积分金币自动扣款
            if (cusComAuth != null && "1".equals(cusComAuth.getCoinfee())) {
                //自动扣费
                try {
                    //先判断积分金币是否够扣，不够扣，先扣掉现有的，先记录
                    String r = goldOrderService.checkPay(journalNum, totalMoney, sccId);
                    if ("0".equals(r)) {//没有金币和积分跳转到手动支付
                        map.put("coinfee", "0");//终端跳转到付款页面
                    } else if ("07".equals(r) || "05".equals(r)) {
                        //积分或者金币直接支付
                        coinOrScorePay(totalMoney, sccId, journalNum, r, hgcode);

                    } else if ("11".equals(r) || "10".equals(r) || "09".equals(r)) {//积分金币不够扣
                        //        goldOrderService.generateSelfPayOrder(totalMoney, sccId, journalNum, r, "");

                        remainMoney = goldOrderService.mixPay(journalNum, totalMoney, r, sccId, hgcode);

                        map.put("coinfee", "0");//终端跳转到付款页面
                    } else if ("08".equals(r)) {//积分+金币够扣
                        coinAndScorePay(totalMoney, sccId, journalNum, r, hgcode);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                //不自动扣费
                map.put("coinfee", "0");//终端跳转到付款页面
            }

        }
        smSerivce.delHgRelateUser(hgcode);
        smSerivce.clearSqCart(hgcode);//清理购物车
        goldOrderService.autoFh(journalNum);//关门就发货
        map.put("remainMoney", remainMoney);//剩余金额
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 查询支付状态
     *
     * @return
     */
    public String searchPayStatus() {

        String sccid = smSerivce.searchHgUser(hgcode);
        if (sccid == null || sccid.equals("")) {
            //说明关门了

        }

        return null;
    }


    /**
     * 金币或者积分支付
     *
     * @param totalMoney
     * @param sccId
     * @param journalNum
     * @param wfStatus4
     */
    public void coinOrScorePay(String totalMoney, String sccId, String journalNum, String wfStatus4, String hgcode) {

        try {
            // String cashierBillsID = goldOrderService.generateSelfPayOrder(totalMoney, sccId, journalNum, wfStatus4, "");
            CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum  = ?", new Object[]{journalNum});
            boolean bo = goldOrderService.generateSpoints(null, journalNum, totalMoney, wfStatus4);
            smSerivce.updateCash(journalNum, hgcode);

            //生成收款单
            if (bo == true) {

                try {
                    //收款单生成后复制订单和收款单到新表
                    goldOrderService.copyCash(journalNum, "j");
                } catch (Exception e3) {
                    e3.printStackTrace();
                    System.out.println("复制订单积分或者金币入库单错误");
                }


                if (smSerivce.isSqPos(journalNum)) {
                    //     goldOrderService.autoFh(cashierBillsID);
                    //把订单状态改程03已收货
                    goldOrderService.updateFkState(cc.getCashierBillsID());
                    //分金币
                    transService.Distribution(cc.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 金币+积分支付
     *
     * @param totalMoney
     * @param sccId
     * @param journalNum
     * @param wfStatus4
     */
    public void coinAndScorePay(String totalMoney, String sccId, String journalNum, String wfStatus4, String hgcode) {

        try {

            CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum  = ?", new Object[]{journalNum});
            boolean bo = goldOrderService.coinAndScorePay(null, journalNum, totalMoney, wfStatus4, sccId, hgcode);
            //生成收款单
            if (bo == true) {

                try {
                    //收款单生成后复制订单和收款单到新表
                    goldOrderService.copyCash(journalNum, "j");
                } catch (Exception e3) {
                    e3.printStackTrace();
                    System.out.println("复制订单积分或者金币入库单错误");
                }


                if (smSerivce.isSqPos(journalNum)) {

                    //把订单状态改程03已收货
                    goldOrderService.updateFkState(cc.getCashierBillsID());
                    //分金币
                    transService.Distribution(cc.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 开通或者不开通金币积分扣款
     *
     * @return
     */
    public String setKt() {
        String coinfee = request.getParameter("coinfee");
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (tc != null) {
            smSerivce.setKt(coinfee, tc.getSccId());
        }

        return "success";
    }

    /**
     * 开门成功获取
     *
     * @return
     */
    public String getOpenSuc() {
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();


        String sccId = request.getParameter("sccId");
        Staff staff = null;
        if (posNum != null && !posNum.equals("")) {
            //购物卡或者手机号验证
            hgcode = smSerivce.getHgcodeing(posNum);
            request.setAttribute("hgcode", hgcode);
            staff = smSerivce.getInfoBySccid(sccId);
        } else {
            //授权后
            TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                    SessionWrap.KEY_SHOPCUSCOM);
            if (tc != null) {
                staff = smSerivce.getInfo(tc.getStaffid());

            }
        }
        if (staff != null) {
            request.setAttribute("staffname", staff.getStaffName());
            request.setAttribute("staffID", staff.getStaffID());
            request.setAttribute("sccId", sccId);
        }
//        //获取秤盘编号和库存
//        List<Object> cplist = smSerivce.getCplist(hgcode);
//
//        String str = "";
//        for (int i = 0; i < cplist.size(); i++) {
//            Object obj = cplist.get(i);
//            Object[] objs = (Object[]) obj;
//            str += objs[0] + "," + objs[1] + "-";
//
//        }
//        request.setAttribute("cpstr", str);
        //推送开门

        return "opensuc";
    }

    /**
     * 获取货柜编号
     *
     * @return
     */
    public String getHgCodeList() {

        HttpServletRequest request = ServletActionContext.getRequest();
        String accessCcomID = request.getParameter("accessCcomID");
        String companyId = "";
        if (accessCcomID != null && !accessCcomID.equals("")) {

            CcomCom ccom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId = ?", new Object[]{accessCcomID});
            companyId = ccom.getComanyId();
        }
        List<BaseBean> list = smSerivce.getHgcodeList(companyId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hgcodelist", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";

    }

    /**
     * 获取二维码
     *
     * @return
     */
    public String getCodeHg() {

       // String hgcode = smSerivce.getHgcodeing(posNum);
      //  request.setAttribute("hgcode", hgcode);

        return "codehg";
    }


    /**
     * 验证手机号开门
     * 如果已经注册，直接成功开门
     * 如果尚未注册，自动注册了
     *
     * @return
     */
    public String validAccount() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String phoneNumber = request.getParameter("phoneNumber");


        Staff staff = smSerivce.validAccount(phoneNumber);
        Map<String, Object> map = new HashMap<String, Object>();

        //先判断是否有未结算的单子
        map = smSerivce.getUnPayCashPos(staff.getSccid(), staff.getStaffID(), map);
        map.put("sccId", staff.getSccid());
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        String r = (String) map.get("r");
        String sccId = (String) map.get("sccId");
        if (!r.equals("1")) {
            smSerivce.addHgRelateUser(hgcode, sccId, "phone");
        }
        return "success";
    }


    /**
     * 核对购物卡是否正确以及余额是否充足
     *
     * @return
     */
    public String checkHgcard() {
        String scard = request.getParameter("scard");

        Map<String, Object> map = new HashMap<String, Object>();
        String sccId = smSerivce.getSccidByCard(scard);
        if ("".equals(sccId)) {
            map.put("result", "1");//不是购物卡
        } else {


            map = smSerivce.getUnPayCashPos(sccId, "", map);


            String r = (String) map.get("r");
            if ("0".equals(r)) {
                map = smSerivce.checkHgcard(sccId, "");
            }
            map.put("sccId", sccId);
        }

        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";

    }

    /**
     * 根据编号获取公司信息
     *
     * @return
     */
    public String getCcomByPosNum() {
        ContactCompany contactCompany = smSerivce.getCCompanyByPosNum(posNum);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("ccompanyID", contactCompany.getCcompanyID());
        map.put("companyName", contactCompany.getCompanyName());
        map.put("industryType", contactCompany.getIndustryType());
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 货柜加入购物车
     *
     * @return
     */
    public String addCartHg() {
        System.out.println("进入addCartHg");

        String weights = request.getParameter("weights");
        String cplist = request.getParameter("cplist");
        String staffID = request.getParameter("staffID");
        String close = request.getParameter("close");

        String  ccompanyID = smSerivce.addCartHg(weights,cplist,posNum,staffID,hgcode);


        if(!"1".equals(close)){
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("ccompanyID",ccompanyID);

            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
        }else{
            System.out.println("进入closeDoor");
              closeDoor();
        }
        return "success";


    }

    /**
     *
     * 查询扫码结果
     * @return
     */
    public String searchScanResult(){
        String sccId = smSerivce.searchHgUser(hgcode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sccId",sccId);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     *
     * 开门后，没买东西，直接关门需要做的事情
     * @return
     */
    public String mmdx(){
      //清除账号，清楚购物车，可能没有，就怕放进去\

         smSerivce.delHgRelateUser(hgcode);
         smSerivce.clearSqCart(hgcode);
        return "success";

    }

    /**
     *
     * 推送到终端
     * @return
     */
    public String openDoor(){
        String sccId = request.getParameter("sccId");
        String door = request.getParameter("door");
        HgRelateUser hgRelateUser = smSerivce.getHgUser(hgcode);
        String loginMode = "";
        Map<String, Object> map = new HashMap<String, Object>();
        if(hgRelateUser==null) {
            smSerivce.addHgRelateUser(hgcode, sccId, "scan");
            loginMode = "scan";

        }else{
            //如果不为空了
            loginMode = hgRelateUser.getLoginMode();
            String hrId = smSerivce.getHgResultHrId(sccId,hgcode);

            if(loginMode.equals("scan")){

                map.put("hrId",hrId);
                JSONObject obj = JSONObject.fromObject(map);
                result = obj.toString();
                   return "success";
            }
        }


        String posNum = smSerivce.getPosNumByHg(hgcode);
        //调用推送到终端 ea/sm/ea_getOpenSuc.jspa?hgcode=hgcode&door=door&loginMode=
        String hrId = smSerivce.addResult(sccId,"",hgcode,"");

        smSerivce.pushSeq(posNum,hrId);
        System.out.println("推送开门");
        if(loginMode.equals("scan")) {
            String url = basePath + "ea/sm/ea_getOpenSuc.jspa?hgcode="+hgcode+"&posNum=" + posNum + "&sccId=" + sccId + "&loginMode=" + loginMode+"&door="+door+"&hrId="+hrId;
            smSerivce.pushMessage(posNum, url);
            System.out.println("推送页面");
            map.put("hrId",hrId);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
        }

        smSerivce.pushOpenDoor(posNum,door);
        smSerivce.updateUser(hgcode);
        smSerivce.pushAudio(posNum,"门锁已开请拉开柜门挑选商品");



         return "success";

    }

    /**
     *
     * 接口关门
     * @return
     */
    public String pushCloseDoor(){


        smSerivce.pushCloseDoor(posNum,door);
        System.out.println("关门");

        return "success";

    }


    /**
     *
     * 查询支付情况用于手机端跳转
     * @return
     */
    public String jumpNext(){
       String hrId = request.getParameter("hrId");
       String cashid  = smSerivce.getHgResult(sccId,hgcode,hrId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cashid",cashid);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }
    /**
     *
     * 关门动作
     * @return
     */
    public String closeDoor(){

        //关门 直接调服务器，只能hgcode,没办法获取秤的总重量了，
        // 所以要提前根据购物车的情况来决定是否有购物，所以加入购物车，必须是保证总重量有变化，如果总重量无变化，不加入购物车

      try {

    //      String posNum = request.getParameter("hgcode");//那边通过Hgcodec传的posNUm
       //   String hgcode = smSerivce.getHgcodeing(posNum);
              String hrId = request.getParameter("hrId");

          HgRelateUser hgRelateUser = smSerivce.getHgUser(hgcode);
          String loginMode = hgRelateUser.getLoginMode();
          String sccId = hgRelateUser.getSccId();
      //    smSerivce.pushCloseDoor(posNum,door);


          //结算
          Map<String, Object> map = smSerivce.joinSelfCartClose(hgcode);
          String totalMoney = String.valueOf(map.get("totalMoney"));
          String totalNum = String.valueOf(map.get("totalNum"));


          String wgw = (String)map.get("wgw");

          System.out.println("wgw"+wgw);
         if(wgw!=null&&wgw.equals("true")){
             smSerivce.addResult(sccId,"",hgcode,hrId);
             //无购物
             System.out.println("无购物");
             String url = basePath+"page/ea/main/marketing/supermarket/container/mmdxsuc.jsp?posNum="+posNum;
             smSerivce.pushMessage(posNum,url);
             smSerivce.pushAudio(posNum,"您没有购买任何商品");


         }else {
             String journalNum = (String) map.get("journalNum");
             if (sccId != null && !sccId.equals("")) {
                 goldOrderService.generateSelfPayOrder(totalMoney, sccId, journalNum, "", "");
                 smSerivce.setHgCash(hgcode, journalNum);
             }
             smSerivce.addResult(sccId,journalNum,hgcode,hrId);
             smSerivce.addJournal(hgcode,journalNum);
             CusComAuth cusComAuth = smSerivce.getAuthcc(sccId);

             String remainMoney = totalMoney;

             if ("scard".equals(loginMode)) {
                 //判断购物卡余额


                 map = smSerivce.checkHgcard(sccId, totalMoney);
                 String r = (String) map.get("result");
                 if ("3".equals(r)) {
                     //积分不够扣，扣掉一部分
                     try {
                         //         goldOrderService.generateSelfPayOrder(totalMoney, sccId, journalNum, "10", "");

                         remainMoney = goldOrderService.mixPay(journalNum, totalMoney, "10", sccId, hgcode);

                         map.put("coinfee", "0");//终端跳转到付款页面
                     } catch (Exception e) {

                     }
                 }


             } else if ("phone".equals(loginMode) || "scan".equals(loginMode)) {
                 //验证手机号登陆 先判断之前是否授权过积分金币自动扣款
                 if (cusComAuth != null && "1".equals(cusComAuth.getCoinfee())) {
                     //自动扣费
                     try {
                         //先判断积分金币是否够扣，不够扣，先扣掉现有的，先记录
                         String r = goldOrderService.checkPay(journalNum, totalMoney, sccId);
                         if ("0".equals(r)) {//没有金币和积分跳转到手动支付
                             map.put("coinfee", "0");//终端跳转到付款页面
                         } else if ("07".equals(r) || "05".equals(r)) {
                             //积分或者金币直接支付
                             coinOrScorePay(totalMoney, sccId, journalNum, r, hgcode);

                         } else if ("11".equals(r) || "10".equals(r) || "09".equals(r)) {//积分金币不够扣
                             //        goldOrderService.generateSelfPayOrder(totalMoney, sccId, journalNum, r, "");

                             remainMoney = goldOrderService.mixPay(journalNum, totalMoney, r, sccId, hgcode);

                             map.put("coinfee", "0");//终端跳转到付款页面
                         } else if ("08".equals(r)) {//积分+金币够扣
                             coinAndScorePay(totalMoney, sccId, journalNum, r, hgcode);

                         }

                     } catch (Exception e) {
                         e.printStackTrace();
                     }


                 } else {
                     //不自动扣费
                     map.put("coinfee", "0");//终端跳转到付款页面
                 }

             }
             map.put("remainMoney", remainMoney);//剩余金额
             goldOrderService.autoFh(journalNum);//关门就发货

             String c = (String)map.get("coinfee");
             String comID = (String)map.get("comID");
             String ccompanyID = (String)map.get("ccompanyID");

             String url = "";
             String audio = "";
             if("0".equals(c)){
                 //到付款页面的跳转
                 Company cc = smSerivce.getCompanyByCComID(ccompanyID);
                url =  basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&ccompanyID=" + ccompanyID + "&comID=" + comID + "&companyName=" + cc.getCompanyName() + "&fh=1&fhform=3&remainMoney="+remainMoney;
                audio = "购物结束请在手机端付款结算";

             }else{
                 audio = "购物结束您已成功购买商品";

                 url =  basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum="+journalNum+"&paytype=积分或者金币支付&posNum="+posNum+"&fhform=3";
                 //直接付款成功的跳转
             }



             smSerivce.pushMessage(posNum,url);
             smSerivce.pushAudio(posNum,audio);

         }
          smSerivce.delHgRelateUser(hgcode);
          smSerivce.clearSqCart(hgcode);//清理购物车



          //推送到设备，如果没购物，设备显示购物结束，未买任何东西界面
          //如果有购物

      }catch (Exception e){
          e.printStackTrace();
      }


        return "success";

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public CAccount getAccount() {
        return account;
    }

    public void setAccount(CAccount account) {
        this.account = account;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public void setCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Map<String, SelfCart> getSelfCartmap() {
        return selfCartmap;
    }

    public void setSelfCartmap(Map<String, SelfCart> selfCartmap) {
        this.selfCartmap = selfCartmap;
    }

    public String getJournalNum() {
        return journalNum;
    }

    public void setJournalNum(String journalNum) {
        this.journalNum = journalNum;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public List<BaseBean> getBeans() {
        return beans;
    }

    public void setBeans(List<BaseBean> beans) {
        this.beans = beans;
    }

    public Micropay getMicropay() {
        return micropay;
    }

    public void setMicropay(Micropay micropay) {
        this.micropay = micropay;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public StaffAddress getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(StaffAddress staffAddress) {
        this.staffAddress = staffAddress;
    }

    public String getScaleNo() {
        return scaleNo;
    }

    public void setScaleNo(String scaleNo) {
        this.scaleNo = scaleNo;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getComID() {
        return comID;
    }

    public void setComID(String comID) {
        this.comID = comID;
    }

    public PayBackupBill getPayBackupBill() {
        return payBackupBill;
    }

    public void setPayBackupBill(PayBackupBill payBackupBill) {
        this.payBackupBill = payBackupBill;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }


    public ScanGoodsRecord getScanGoods() {
        return scanGoods;
    }

    public void setScanGoods(ScanGoodsRecord scanGoods) {
        this.scanGoods = scanGoods;
    }

    public int getLxType() {
        return lxType;
    }

    public void setLxType(int lxType) {
        this.lxType = lxType;
    }

    public String getHgcode() {
        return hgcode;
    }

    public void setHgcode(String hgcode) {
        this.hgcode = hgcode;
    }

    public String getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(String remainMoney) {
        this.remainMoney = remainMoney;
    }

    public String getCbid() {
        return cbid;
    }

    public void setCbid(String cbid) {
        this.cbid = cbid;
    }

    public String getLastPay() {
        return lastPay;
    }

    public void setLastPay(String lastPay) {
        this.lastPay = lastPay;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public TEshopCustomer gettEshopCustomer() {
        return tEshopCustomer;
    }

    public void settEshopCustomer(TEshopCustomer tEshopCustomer) {
        this.tEshopCustomer = tEshopCustomer;
    }
}
