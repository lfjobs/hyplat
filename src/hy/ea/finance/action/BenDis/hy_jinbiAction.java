package hy.ea.finance.action.BenDis;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.alipay.config.AlipayConfig;
import com.alipay.sign.Base64;
import com.alipay.util.AlipayNotify;
import com.daifu.chinapay.meth.SinglePay;
import com.daifu.chinapay.model.bean.TransactionBean;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.sft.AccountInfo;
import com.wechat.bo.sft.Withdraw;
import com.wechat.utils.HTTPV3;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.utils.RequestHandler;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.finance.BenDis.OperatorInfo;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffBankAccount;
import hy.ea.finance.service.AssiCartService;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.finance.service.transferService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.*;

@Controller
public class hy_jinbiAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private transferService trService;
    @Resource
    private WfjJifenService jifenser;
    @Resource
    private ShowExcelService excelService;
    @Resource
    AssiCartService assiCartService;
    @Resource
    private BonusPointsService bonusPointsService;
    @Resource
    private GoldOrderService goldOrderService;
    @Resource
    private transferService transService;
    private InputStream excelStream;
    private int pageNumber;
    private int pageSize;
    private PageForm pageForm;
    private String baseUrl;// 路径前缀

    private String result;
    private String khd; //0为网页查看  1为APP查看
    private String user;
    private String sccid;
    private String morre;//金币充值金额
    private String journalNum; //金币充值订单号
    private String staffid; //金币充值人员
    private Logger logger = LoggerFactory.getLogger(hy_jinbiAction.class);
    private Object rank;
    private String flag;//判断是移动办公进去   还是个人中心进入    标识
    private TEshopCustomer customer;
    private TEshopCusCom cuscom;
    private ContactCompany contactCompany;
    private ProductPackaging productPackaging;
    private Staff staff;
    private List<BaseBean> list;
    private String type;
    private String mark; //判断金币池进入银行列表    还是  金币兑现进入银行列表
    private String bankId;
    private String identifying;//判断个人 登录    or 公司登录
    private String app;//苹果显示金币充值
    private String ccompanyId;//往来单位
    private String isflag; //判断是金币充值(gold)还是积分充值（score）
    private String ppid;
    private String wdaID;
    private String sdate;
    private String edate;
    private int exportNum;
    private String tradeCode;
    private String receiptOprName;
    private String payOperatorName;
    private String payState;
    private String auditOpinion;
    private String payOperatorID;
    private String receiptOprID;
    private String userId;//支付宝id
    private String withDraway;//支付宝微信默认提现方式

    private WithDrawReq withDraw = new WithDrawReq(null, null, null, null, null, null);

    protected ActionContext actionContext;
    private String staffName;

    /**
     * 获取金币池基础显示数据
     *
     * @return
     */
    public String gethyjifen() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("purview", request.getParameter("purview"));
        getData();
        return "jifen";
    }

    /**
     * 跳转金币提现页面
     *
     * @return
     */
    public String getwfjtixian() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("purview", request.getParameter("purview"));
        request.setAttribute("poundage", Constant.POUNDAGE);
        request.setAttribute("jum", serverService.getBillID("company201009046vxdyzy4wg0000000025"));
        Object rv = jifenser.getDate(sccid);
        if (rv != null) {
            Object[] rvs = (Object[]) rv;
            request.setAttribute("rDate", rvs[0]);
            request.setAttribute("rType", rvs[1]);
        }
        getData();
        if (withDraway != null && withDraway != "") {
            String sql = "update t_eshop_cuscom ec set ec.withdraway =? where ec.sccid = ?";
            List<Object[]> paramslist = new ArrayList<Object[]>();
            paramslist.add(new Object[]{"03", sccid});
            baseBeanService.executeSqlsByParmsList(null, new String[]{sql}, paramslist);
        }
        return "tixian";
    }

    /**
     * 跳转金币充值页面
     *
     * @return
     */
    public String getwfjchongzhi() {
        getData();
        return "chongzhi";
    }

    /**
     * 获取用户金币数
     *
     * @return
     */
    public String getjinbiScore() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String user = request.getParameter("user");
        String sccid = request.getParameter("sccid");
        request.setAttribute("user", user);
        request.setAttribute("sccid", sccid);
        TEshopCustomer cusCom = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[]{user});
        WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams(
                "from WfjJifen where staffId=? and sccid=?",
                new Object[]{cusCom.getStaffid(), sccid});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jifen", jifen);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 获取金币池基础显示数据
     */
    private void getData() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        String user = request.getParameter("user");
        String sccid = request.getParameter("sccid");
        Object syncpay = request.getAttribute("syncpay");
        //进入小系统
        if (user == null || user.equals("")) {
            CAccount account = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            if (account != null) {
                TEshopCusCom cus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId = ? ", new Object[]{account.getCompanyID()});
                CcomCom ccomcom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where comanyId=?", new Object[]{account.getCompanyID()});
                ccompanyId = ccomcom.getCcompanyId();
                //判断是否是公司登录   相等  则是公司登录
                if (account.getStaffID().equals(cus.getStaffid())) {
                    identifying = "company";
                }
                user = cus.getAccount();
                sccid = cus.getSccId();
                request.setAttribute("khd", "0");
                flag = "sys";
            }
        }
        if (syncpay != null && "syncpay".equals(syncpay.toString())) {
            user = request.getAttribute("syncuser").toString();
            sccid = request.getAttribute("syncuserid").toString();
        }
        request.setAttribute("user", user);
        request.setAttribute("sccid", sccid);
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
        TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
        sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
        sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cusCom);
        request.setAttribute("staffid", cusCom.getStaffid());
        request.setAttribute("state", cusCom.getState());
        ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where type=? and model=?", new Object[]{"会员类型级别", cusCom.getCusType()});
        request.setAttribute("pp", pp);
        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                "from Staff where staffID=?",
                new Object[]{customer.getStaffid()});
        request.setAttribute("staff", staff);
        WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams(
                " from WfjJifen where staffId=? and sccid=?",
                new Object[]{customer.getStaffid(), sccid});
        request.setAttribute("jifen", jifen);

        BonusPoints bp = new BonusPoints();
        bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{sccid});
        request.setAttribute("bp", bp);


        //查询微信商户号余额和结算卡

        if(cusCom!=null&&cusCom.getCompanyId()!=null&&!cusCom.getCompanyId().equals("")) {
            String sql = "select t.sub_mchid from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ? and t.applyment_state = 'FINISH'";
            Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{cusCom.getCompanyId()});
            if(obj!=null) {
                JSONObject ye = HTTPV3.balanceResult(obj.toString());
                int available_amount = ye.getInt("available_amount");
                request.setAttribute("available_amount", available_amount);
                AccountInfo accountInfo = HTTPV3.searchSettlement(obj.toString());
                request.setAttribute("accountInfo", accountInfo);
                request.setAttribute("companyID",cusCom.getCompanyId());
            }else{

                request.setAttribute("available_amount", 0);
                request.setAttribute("accountInfo", null);
            }
        }else{

            request.setAttribute("available_amount", 0);
            request.setAttribute("accountInfo", null);

        }


    }

    public String AjxaNotWithDrawGlod() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map map = new HashMap();
        map.put("gold_no", notWithDrawGlod(request.getParameter("wfj_jifen_id")));
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 获得当前用户当月明细
     *
     * @return
     */
    public String gethyjifenBill() {
        //HttpSession sessions = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String user = request.getParameter("user");
            String sccid = request.getParameter("sccid");
            request.setAttribute("user", user);
            request.setAttribute("sccid", sccid);
            TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});

            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                    "from Staff where staffID=?",
                    new Object[]{customer.getStaffid()});
            request.setAttribute("staff", staff);

            WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams(
                    "from WfjJifen where staffId=? and sccid=?",
                    new Object[]{customer.getStaffid(), sccid});
            request.setAttribute("jifen", jifen);
            String year = request.getParameter("year");
            String month = request.getParameter("month");
            //获得当前年份以及当前月份
            if (year == null || year.equals("")) {
                Calendar a = Calendar.getInstance();
                year = a.get(Calendar.YEAR) + "";
                month = a.get(Calendar.MONTH) + 1 + "";
                int lenght = month.length();
                if (lenght == 1) {
                    month = "0" + month;
                }
            }
            request.setAttribute("year", year.toString());
            request.setAttribute("month", month.toString());
            int day = Utilities.getDayMouth(Integer.parseInt(year.toString()), Integer.parseInt(month.toString()));
            Date date = Utilities.getDateFromString(year.toString() + "-" + month.toString()
                    + "-01 00:00:01", "yyyy-MM-dd HH:mm:ss");
            Date endDate = Utilities.getDateFromString(year.toString() + "-" + month.toString() + "-" + day + " 23:59:59", "yyyy-MM-dd HH:mm:ss");

			/*String hql=" from WfjJifenDetail d,WfjGuize g where d.wfjGuizeId=g.wfjGuizeId"
                    + " and d.wfjJifenId=? and d.jifenDetailDate between ? and ? and d.status is null order by d.jifenDetailDate desc";
			pageForm=baseBeanService.getPageForm((pageNumber==0)?1:pageNumber,13, "select new WfjJifenDetail(d.jifenDetailKey,d.jifenDetailId,d.jifenDetailName,"
					+ "d.jifenDetailDate,d.jifenDetailScore,d.jifenDetailState,d.wfjGuizeId,g.wfjGuizeCalc,g.wfjGuizeName)"+hql, "select count(*)"+hql, new Object[]{jifen.getWfjJifenId(), date,endDate });

			request.setAttribute("pageForm", pageForm);*/

            List<BaseBean> jguize = getGuize("A");
            List<Object> jobjects = new ArrayList<Object>();
            jobjects.add(jifen.getWfjJifenId());
            jobjects.add(date);
            if (jguize.size() > 0) {
                String gz = "";
                for (int i = 0; i < jguize.size(); i++) {
                    WfjGuize g = (WfjGuize) jguize.get(i);
                    gz += "?,";
                    jobjects.add(g.getWfjGuizeId());
                }
                gz = gz.substring(0, gz.length() - 1);
                Object jdetail = baseBeanService
                        .getObjectBySqlAndParams(
                                "select sum(jifen_detail_score) from dt_wfj_jifen_detail where WFJ_JIFEN_ID=? and status is null and JIFEN_DETAIL_DATE>? and WFJ_GUIZE_ID  in("
                                        + gz + ")", jobjects.toArray());
                request.setAttribute("jdetail", jdetail == null ? 0 : jdetail);
            }

            WfjGuize wfjGuize = (WfjGuize) baseBeanService
                    .getBeanByHqlAndParams(
                            "from WfjGuize where wfjGuizeCalc=? and wfjGuizeName=?",
                            new Object[]{"M", "金币提现"});
            Object xjtx = baseBeanService
                    .getObjectBySqlAndParams(
                            "select sum(jifen_detail_score) from dt_wfj_jifen_detail where WFJ_JIFEN_ID=? and status is null and JIFEN_DETAIL_DATE>? and WFJ_GUIZE_ID =?",
                            new Object[]{jifen.getWfjJifenId(), date,
                                    wfjGuize.getWfjGuizeId()});
            request.setAttribute("xjtx", xjtx == null ? 0 : xjtx);



            //
            TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
            String dc = "1";
            if(cusCom!=null&&cusCom.getCompanyId()!=null&&!cusCom.getCompanyId().equals("")) {
                String sql = "select t.sub_mchid from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ? and t.applyment_state = 'FINISH'";
                Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{cusCom.getCompanyId()});
                if(obj!=null) {
                    dc = "2";
                }else{

                    dc = "1";
                }
            }else{

                dc = "1";

            }
            request.setAttribute("dc", dc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bill";
    }

    public String AjxaHyjifenBill() {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String user = request.getParameter("user");
            String sccid = request.getParameter("sccid");
            request.setAttribute("user", user);
            request.setAttribute("sccid", sccid);
            TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});

            WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams(
                    "from WfjJifen where staffId=? and sccid=?",
                    new Object[]{customer.getStaffid(), sccid});

            String year = request.getParameter("year");
            String month = request.getParameter("month");
            int day = Utilities.getDayMouth(Integer.parseInt(year.toString()), Integer.parseInt(month.toString()));
            Date date = Utilities.getDateFromString(year.toString() + "-" + month.toString()
                    + "-01 00:00:01", "yyyy-MM-dd HH:mm:ss");
            Date endDate = Utilities.getDateFromString(year.toString() + "-" + month.toString() + "-" + day + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            String hql = " from WfjJifenDetail d,WfjGuize g where d.wfjGuizeId=g.wfjGuizeId"
                    + " and d.wfjJifenId=? and d.jifenDetailDate between ? and ? and d.status is null order by d.jifenDetailDate desc";
            pageForm = baseBeanService.getPageForm((pageNumber == 0) ? 1 : pageNumber, 6, "select new WfjJifenDetail(d.jifenDetailKey,d.jifenDetailId,d.jifenDetailName,"
                    + "d.jifenDetailDate,d.jifenDetailScore,d.jifenDetailState,d.wfjGuizeId,g.wfjGuizeCalc,g.wfjGuizeName)" + hql, "select count(*)" + hql, new Object[]{jifen.getWfjJifenId(), date, endDate});

            List<BaseBean> jguize = getGuize("A"); //A加(默认)或者M 减
            List<Object> jobjects = new ArrayList<Object>();
            jobjects.add(jifen.getWfjJifenId());
            jobjects.add(date);
            /*if (jguize.size() > 0) {
                String gz = "";
				for (int i = 0; i < jguize.size(); i++) {
					WfjGuize g = (WfjGuize) jguize.get(i);
					gz += "?,";    //统计jguize的集合的大小
					jobjects.add(g.getWfjGuizeId());
				}
				gz = gz.substring(0, gz.length() - 1);
				//查找积分明细
				Object jdetail = baseBeanService
						.getObjectBySqlAndParams(
								"select sum(jifen_detail_score) from dt_wfj_jifen_detail where WFJ_JIFEN_ID=?  and status is null and JIFEN_DETAIL_DATE>? and WFJ_GUIZE_ID  in("
										+ gz + ")", jobjects.toArray());
				request.setAttribute("jdetail", jdetail==null?0:jdetail);
			}*/
            //查找dt_wfj_guize表，根据M和规则名字为金币提现两个条件
            /*WfjGuize wfjGuize = (WfjGuize) baseBeanService
                    .getBeanByHqlAndParams(
							"from WfjGuize where wfjGuizeCalc=? and wfjGuizeName=?",
							new Object[] { "M", "金币提现" });
			Object xjtx = baseBeanService
					.getObjectBySqlAndParams(
							"select sum(jifen_detail_score) from dt_wfj_jifen_detail where WFJ_JIFEN_ID=?  and status is null and JIFEN_DETAIL_DATE>? and WFJ_GUIZE_ID =?",
							new Object[] { jifen.getWfjJifenId(), date,
									wfjGuize.getWfjGuizeId() });*/
            Map<String, Object> map = new HashMap<String, Object>();
            //map.put("xjtx", xjtx==null?0:xjtx);
            map.put("pageForm", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出
            JSONObject json = JSONObject.fromObject(map, jsonConfig);
            result = json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 金币明细详情
     *
     * @return
     */
    public String getdetails() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String detailid = request.getParameter("detailid");
        String wfjGuizeCalc = request.getParameter("wfjGuizeCalc");
        WfjJifenDetail detail = (WfjJifenDetail) baseBeanService.getBeanByHqlAndParams("from WfjJifenDetail where jifenDetailId=?  and status is null", new Object[]{detailid});
        String withdrawalWay = (String) baseBeanService.getObjectBySqlAndParams("select cb.wfstatus4 from dtcashierbills cb left join DT_WFJ_JIFEN_DETAIL wjd on cb.cashierbillsid = wjd.wfj_cash_id where wjd.jifen_detail_id = ?", new Object[]{detailid});
        StringBuilder stringBuilder = new StringBuilder();
        String val = "";
        if (detail != null && detail.getWfjGoodId() != null && !detail.getWfjGoodId().equals("")) {
            stringBuilder.append("select g.goodsname, o.oa_bill_jounum, s.staffname,");
            stringBuilder.append("case when t.custype = '0' then '平台' when t.custype = '0.5' then '国税' when t.custype = '1' then '地税'");
            stringBuilder.append("when t.custype = '2' then '公司商城业主会员' when t.custype = '3' then '合伙创业商城业主会员' when t.custype = '4' then '微分金商城业主会员'");
            stringBuilder.append("when t.custype = '5' then '代理商商城业主会员' when t.custype = '6' then 'VIP客户' else '客户' end, s.headimage,t.sccid");
            stringBuilder.append(" from dtgoodsbills g, dt_order_bill_add o, t_eshop_cuscom t, dt_hr_staff s");
            stringBuilder.append(" where g.cashierbillsid = o.oa_bill_id");
            stringBuilder.append(" and o.oa_sccid = t.sccid");
            stringBuilder.append(" and s.staffid = t.staffid");
            stringBuilder.append(" and g.goodsbillsid = ?");
            val = detail.getWfjGoodId();
        } else {
            stringBuilder.append("select '','',s.staffname,");
            stringBuilder.append(" case when t.custype = '0' then '平台' when t.custype = '0.5' then '国税' when t.custype = '1' then '地税'");
            stringBuilder.append(" when t.custype = '2' then '公司商城业主会员' when t.custype = '3' then '合伙创业商城业主会员' when t.custype = '4' then '微分金商城业主会员'");
            stringBuilder.append(" when t.custype = '5' then '代理商商城业主会员' when t.custype = '6' then 'VIP客户' else '客户' end, s.headimage,t.sccid");
            stringBuilder.append(" from dt_wfj_jifen j,t_eshop_cuscom t, dt_hr_staff s");
            stringBuilder.append(" where j.staff_id=s.staffid");
            stringBuilder.append(" and s.staffid = t.staffid");
            stringBuilder.append(" and j.sccid = t.sccid");
            stringBuilder.append(" and j.wfj_jifen_id = ?");
            val = detail.getWfjJifenId();
            StringBuffer sql = new StringBuffer();
            sql.append("select c.trade_no,c.remark from dtcashierbills c, dt_wfj_jifen_detail w");
            sql.append(" where w.wfj_cash_id = c.cashierbillsid and");
            sql.append("  w.jifen_detail_id = ?");
            //Object o=null;
            Object[] obj = (Object[]) baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{detailid});
            if (obj != null && obj[0] != null && obj[1] != null) {  //金币提现才不会为null
                String tradeNo = (String) obj[0];
                if (obj[1] != null && !"".equals(obj[1].toString())) {
                    request.setAttribute("remark", obj[1].toString());
                }
                //String tradeNo = (String)o;
                String merDate = tradeNo.substring(2, 10);
                SinglePay sp = new SinglePay();
                TransactionBean tb = sp.findOrder(merDate, tradeNo);
                String state = tb.getStat();   //交易状态
                String responseCode = tb.getResponseCode();//交易应答码
                String purpose = tb.getPurpose();//查询结果
                //处理交易状态
                if (state != null && "".equals(state)) {
                    switch (state) {
                        case "s":
                            request.setAttribute("state", "成功----交易成功");
                            break;
                        case "2":
                            request.setAttribute("state", "处理中----交易已接受");
                            break;
                        case "3":
                            request.setAttribute("state", "处理中----财务已确认");
                            break;
                        case "4":
                            request.setAttribute("state", "处理中----财务处理中");
                            break;
                        case "5":
                            request.setAttribute("state", "处理中----已发往银行");
                            break;
                        case "6":
                            request.setAttribute("state", "失败----银行已退单,请联系工作人员处理");
                            break;
                        case "7":
                            request.setAttribute("state", "处理中----重汇已提交");
                            break;
                        case "8":
                            request.setAttribute("state", "处理中----重汇已发送");
                            break;
                    }
                }
                //处理交易响应
                if (responseCode != null && "".equals(responseCode)) {
                    switch (responseCode) {
                        case "001":
                            request.setAttribute("responseCode", "查询无记录");
                            break;
                        case "002":
                            request.setAttribute("responseCode", "查询出错");
                            break;
                    }
                }
                //处理查询结果
                if (purpose != null && "".equals(purpose)) {
                    String[] queryResult = new String[17];
                    String temp = purpose.replace("|", ",");
                    queryResult = temp.split(",");
                    String bankName = queryResult[6]; //开户银行
                    String cardNo = "XXXXXXXXXX" + queryResult[7]; //收款账号
                    String usrName = queryResult[8]; //收款人姓名
                    String number = queryResult[3]; //订单号或流水号
                    request.setAttribute("bankName", bankName);
                    request.setAttribute("cardNo", cardNo);
                    request.setAttribute("usrName", usrName);
                    request.setAttribute("number", number);
                }
            }
        }
        Object object = baseBeanService.getObjectBySqlAndParams(stringBuilder.toString(), new Object[]{val});

        List<BaseBean> backList = new ArrayList<BaseBean>();
        String cushql = "SELECT t.staffid, t.companyid, t.state,t.superioragent,"
                + "t.pseudo_company_name,t.custype,t.sccid,t.account"
                + " FROM T_ESHOP_CUSCOM t START WITH t.sccid = ?"
                + " CONNECT BY PRIOR t.suppersccid = t.sccid";
        Object[] sid=isEmpty(object);
        List cusList = baseBeanService.getListBeanBySqlAndParams(cushql, new Object[]{sid[5]});
        for (int j = 0; j < cusList.size(); j++) {
            Object[] cusCom = (Object[]) cusList.get(j);
            if (cusCom[1] != null && !cusCom[1].equals("")) {
                String cushq = "select cc.ccompanyid,cc.companyname from dt_ccom_com c,dtcontactcompany cc where c.ccompany_id=cc.ccompanyid and c.compnay_id=?";
                Object cobj=baseBeanService.getObjectBySqlAndParams(cushq, new Object[]{cusCom[1]});
                request.setAttribute("ccid",isEmpty(cobj)[0]);
                request.setAttribute("ccname",isEmpty(cobj)[1]);
                break;
            }
        }

        request.setAttribute("wfjGuizeCalc", wfjGuizeCalc);
        request.setAttribute("details", object);
        request.setAttribute("detail", detail);
        request.setAttribute("withdrawalWay", withdrawalWay);
        return "details";
    }

    /**
     * 合单支付的供应商入账明细+佣金入账明细+提现明细
     * @return
     */
    public String getWxzhDetail(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String wxdId = request.getParameter("wxdId");
        String wfjGuizeCalc=request.getParameter("wfjGuizeCalc");
        Object obj = jifenser.getWxDetails(wxdId,wfjGuizeCalc);

        WfjJifenDetail detail = new  WfjJifenDetail();
        Object[] objs = ( Object[])obj;
        detail.setJifenDetailScore(objs[7].toString());
        if(wfjGuizeCalc.equals("A")) {
            request.setAttribute("withdrawalWay", objs[10].toString());
            detail.setJifenDetailName(objs[0].toString() + "-" + objs[8].toString());
        }else  if(wfjGuizeCalc.equals("M")){

            detail.setJifenDetailName(objs[8].toString());
            request.setAttribute("withdrawalWay", "02");
            request.setAttribute("state", jifenser.searchWithdraw(objs[1].toString()));
        }else  if(wfjGuizeCalc.equals("D")) {
            detail.setJifenDetailName(objs[8].toString());

        }
        detail.setJifenDetailDate(Utilities.getDateFromString(objs[6].toString(), "yyyy-MM-dd HH:mm:ss"));
        request.setAttribute("details", obj);
        request.setAttribute("detail", detail);
        request.setAttribute("wfjGuizeCalc", objs[9].toString());
           return "details";

    }

    /**
     * 合单支付的供应商入账明细+佣金入账明细+提现明细
     * @return
     */
    public String getWxzhDetailList(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        //获得当前年份以及当前月份
        if (year == null || year.equals("")) {
            Calendar a = Calendar.getInstance();
            year = a.get(Calendar.YEAR) + "";
            month = a.get(Calendar.MONTH) + 1 + "";
            int lenght = month.length();
            if (lenght == 1) {
                month = "0" + month;
            }
        }
        String user = request.getParameter("user");
        String sccid = request.getParameter("sccid");
        request.setAttribute("user", user);
        request.setAttribute("sccid", sccid);


        TEshopCusCom tc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
        Map<String, Object> map = new HashMap<String, Object>();
        PageForm pageForm = null;
        String companyID = tc.getCompanyId();
        if(companyID!=null&&!companyID.equals("")) {
           pageForm = jifenser.getWxDetailList(pageNumber, 10, companyID, year, month);

        }


        if(pageNumber==1||pageNumber==0){
            WxMonthAccount wxMonthAccount = jifenser.getWxSummry(year.toString() + "-" + month.toString(),companyID);

            String xjtx = wxMonthAccount.getZc();
            String jdetail = wxMonthAccount.getSr();
            map.put("xjtx", xjtx == null ? 0 : xjtx);
            map.put("jdetail", jdetail == null ? 0 : jdetail);

        }
        map.put("pageForm", pageForm);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出
        JSONObject json = JSONObject.fromObject(map, jsonConfig);
        result = json.toString();
        return "success";

    }


    public Object[] isEmpty(Object object) {
        Object[] obj = null;
        if (object == null) {

        }
        //数组判空
        else if (object.getClass().isArray()) {
            int len = Array.getLength(object);
            obj = new Object[len];
            for (int i = 0; i < len; i++) {
                obj[i] = Array.get(object, i);
            }
        }
        return obj;
    }


    //规则
    private List<BaseBean> getGuize(String calc) {

        List<BaseBean> guize = baseBeanService.getListBeanByHqlAndParams("from WfjGuize where wfjGuizeCalc=?", new Object[]{calc});

        return guize;
    }

    //查询支付密码
    public synchronized String toSearchCode() {

        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject json = new JSONObject();
        JSONObject obj = new JSONObject();
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String hqlCode = " from TEshopCustomer where account=? AND logOff=0";
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hqlCode, new Object[]{user});

        //查找图片
        String picHQL = " from Staff where staffID = ?";
        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(picHQL, new Object[]{customer.getStaffid()});
        json.accumulate("headImage", staff.getHeadimage());

        if (customer != null && password.equals(customer.getPaymentCode())) {
            json.accumulate("type", "0");
        } else if (customer != null && customer.getPaymentCode() == null) {
            json.accumulate("type", "1");
        } else {
            json.accumulate("type", "2");
        }
        obj.accumulate("search", json);
        result = obj.toString();
        return "success";
    }

    //查询用户支付密码
    public String toSearchPaymentCode() {

        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String paymentCode = request.getParameter("paymentCode");//获取支付密码
        String hql = " from TEshopCustomer where account= ? and logOff=0";
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{user});

        if (customer != null && (customer.getPaymentCode() != null || !"".equals(customer.getPaymentCode())) && paymentCode.equals(customer.getPaymentCode())) {
            map.put("code", 00);//支付密码正确
        } else if (customer != null && (customer.getPaymentCode() != null || !"".equals(customer.getPaymentCode())) && !paymentCode.equals(customer.getPaymentCode())) {
            map.put("code", 01);//支付密码不正确
        } else {
            map.put("code", 02);//没有支付密码
        }

        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     * 好友排名(包括自己)
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getFriends() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("user", user);
        request.setAttribute("sccid", sccid);
        return "friends";
    }

    public String getAjax() {

        HttpServletRequest request = ServletActionContext.getRequest();
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
        request.setAttribute("customer", customer);
        pageForm = getList(user, customer.getAccount(), customer.getStaffid());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject oj = JSONObject.fromObject(map);
        oj.accumulate("rank", rank);
        result = oj.toString();
        return "success";
    }

    @SuppressWarnings("unchecked")
    public PageForm getList(String user, String account, String staffid) {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Object> para = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();//查询退货信息

        sql.append(" select qq.wfj_Jifen_Score, qq.staffName, qq.staffid, qq.headimage, ");
        sql.append(" ROW_NUMBER() over(order by qq.wfj_Jifen_Score desc) ");
        sql.append(" from (select j.wfj_Jifen_Score, s.staffName,s.staffid,s.headimage ");
        sql.append(" from DT_WFJ_JIFEN j, dt_hr_Staff s, dtjoinfans jj ");
        sql.append(" where j.staff_Id = s.staffID and s.staffid = jj.staffid ");
        sql.append(" and jj.zaccount = ? ");
        sql.append(" union all ");

        sql.append(" select j.wfj_Jifen_Score,s.staffName,s.staffid,s.headimage ");
        sql.append(" from DT_WFJ_JIFEN j, dt_hr_Staff s ");
        sql.append(" where j.staff_Id = s.staffID ");
        sql.append(" and s.staffid = ? ) qq ");

        para.add(user);
        para.add(staffid);

        pageForm = baseBeanService.getPageFormBySQL(pageNumber, 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", para.toArray());

        List rlist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), para.toArray());

        Object[] ranks = null;
        for (int i = 0; i < rlist.size(); i++) {
            Object obj = rlist.get(i);
            ranks = (Object[]) obj;
            if (ranks[2].equals(staffid)) {
                rank = ranks[4].toString();
            }
        }

		/*Object[] ranks = null;
        for(int i = 0 ; i < pageForm.getList().size();i++){
			Object obj = pageForm.getList().get(i);
			ranks = (Object[]) obj;
			if(ranks[2].equals(staffid) ){
				rank = ranks[4].toString();
			}
		}	*/
        return pageForm;
    }


    /**
     * 查找用户银行卡
     *
     * @return
     */
    public String getUserBank() {
        int count = 0;
        //此处查询银行卡数量不分个人和公司，直接查询staffid
        if (staffid != null && !staffid.equals("")) {
            count = baseBeanService.getConutByByHqlAndParams("select count(*) from StaffBankAccount where staffID=? ", new Object[]{staffid});
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }


    //显示账单中银行卡（最新添加的）
    public String getShowBank() {

        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> obj = new ArrayList<Object>();
        TEshopCusCom te = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ? and staffid = ? ", new Object[]{sccid, staffid});

        StringBuilder sql = new StringBuilder();
        sql.append(" select bk.bankaccountid,bk.bankname,bk.bankaccount,bk.branchname ");
        sql.append(" from dt_hr_staff_bankaccount bk ");
        sql.append(" where bk.staffid = ? ");
        obj.add(staffid);
        sql.append(" order by bk.addTime desc ");

        List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), obj.toArray());

        if (list != null && list.size() > 0) {
            map.put("bank", list.get(0));
        } else {
            map.put("bank", null);
        }

        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    //用户平台显示
    public String findconWealth() {

        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject json = new JSONObject();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        customer = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);

        String mark = "ok";

        try {
            String hql = "from TEshopCusCom where sccId = ? ";
            cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid});
            if (cuscom.getState().equals("1")) {//1为个人
                staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
                        new Object[]{cuscom.getStaffid()});
            } else {
                contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
                        new Object[]{cuscom.getCompanyId()});
            }

            ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where model = ? and type = ?", new Object[]{cuscom.getCusType(), "会员类型级别"});
            productPackaging = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppid=?", new Object[]{cuscom.getPpid()});
            json.accumulate("custypename", pp.getGoodsName());
            json.accumulate("cuscom", cuscom);
            json.accumulate("staff", staff);
            json.accumulate("contactCompany", contactCompany);
            json.accumulate("productPackaging", productPackaging);

        } catch (Exception e) {

            mark = "no";
        }


        json.accumulate("mark", mark);
        result = json.toString();

        return "success";
    }

    //跳转至切换平台页面
    @SuppressWarnings("unchecked")
    public String Platform() {
        cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
        String hql = "select tec.sccid,tec.custype,tec.ppid,p.goodsname,p.type,pp.goodsname vipname from t_eshop_cuscom tec left join dt_productpackaging pp on pp.model = tec.custype left join dt_productpackaging p" +
                " on tec.ppid=p.ppid where tec.staffid=? and pp.type=?";
        list = baseBeanService.getListBeanBySqlAndParams(hql, new Object[]{staffid, "会员类型级别"});
        return "platform";
    }

    //银行列表 选择银行卡
    @SuppressWarnings("unchecked")
    public String choiceBank() {

        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> list = new ArrayList<Object>();
        List<Object> obj = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select bk.bankname,bk.bankaccount,bk.bankaccountid,bk.BRANCHNAME");
        sql.append(" from dt_hr_staff_bankaccount bk ");
        sql.append(" where bk.bankaccountid = ? ");
        sql.append(" order by bk.addtime desc ");

        list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{bankId});

        if (list == null || list.size() == 0) {

            StringBuilder hql = new StringBuilder();
            hql.append(" select bk.bankname,bk.bankaccount,bk.bankaccountid ");
            hql.append(" from dt_hr_staff_bankaccount bk ");

            if (ccompanyId == null || "".equals(ccompanyId)) {

                TEshopCusCom te = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ? and staffid = ? ", new Object[]{sccid, staffid});

                if ("1".equals(te.getState())) {//个人
                    hql.append(" where bk.staffid = ? and bk.type=? ");
                    obj.add(staffid);
                    obj.add("01");
                } else {
                    String chql = "from CcomCom where comanyId=?";
                    CcomCom ccomcom = (CcomCom) baseBeanService.getBeanByHqlAndParams(chql, new Object[]{te.getCompanyId()});
                    hql.append(" where bk.ccompanyid = ? and bk.type=? ");
                    obj.add(ccomcom.getCcompanyId());
                    obj.add("00");
                }
            } else {
                hql.append(" where bk.ccompanyid = ? and bk.type=? ");
                obj.add(ccompanyId);
                obj.add("00");
            }
            hql.append(" order by bk.addtime desc ");
            list = baseBeanService.getListBeanBySqlAndParams(hql.toString(), obj.toArray());

            if (list == null || list.size() == 0) {
                list = null;
            }
        }
        if (list == null) {
            map.put("bank", null);
        } else {
            Object list1 = list.get(0);
            Object[] list2 = (Object[]) list1;
            map.put("bank", list2);
            bankId = list2[2].toString();
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /*
    金币红包
	*/
    @SuppressWarnings("unchecked")
    public String redPacket() {

        HttpServletRequest request = ServletActionContext.getRequest();
        WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams(
                " from WfjJifen where staffId=? and sccid=?",
                new Object[]{staffid, sccid});
        request.setAttribute("jifen", jifen);
        return "redpackte";
    }

    public String redPacketAjax() {

        pageForm = getredPacketList(sccid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);

        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";

    }

    @SuppressWarnings("unchecked")
    public PageForm getredPacketList(String sccid) {

        List<Object> para = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select pp.sccids,pp.sender,pp.recipient,pp.goldnum,to_char(pp.reddate,'yyyy-mm-dd')  ");
        sql.append("from dtredpacket pp ");
        sql.append("where pp.sccids=? ");
        sql.append("or pp.sccidr=? order by pp.reddate desc ");
        para.add(sccid);
        para.add(sccid);
        pageForm = baseBeanService.getPageFormBySQL(pageNumber, 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", para.toArray());

        return pageForm;
    }

    /**
     * 查找冻结账户
     *
     * @return
     */
    public String getUserPurview() {
        //HttpSession sessions = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        String user = request.getParameter("user");
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("purview", customer != null && customer.getStatus() == null);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 兑现
     *
     * @return
     * @throws Exception
     */
//	public synchronized String tixian() throws Exception{
//		HttpServletRequest request = ServletActionContext.getRequest();
//		int money=(int)(Float.parseFloat((request.getParameter("money")==null?"0":request.getParameter("money")))*100);
//		logger.error("用户兑现金币数："+money);
//		String user=request.getParameter("user");
//		String sccid=request.getParameter("sccid");
//		String bankId = request.getParameter("bankId");
//		request.setAttribute("user",user);
//		request.setAttribute("sccid",sccid);
//		String str="";
//		if(bankId !=null && !bankId.equals("")) {
//			str = jifenser.gecOrderAndManualDeal(sccid, user, money, bankId);
//		}else{
//			str="兑现失败";
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("str", str);
//		JSONObject oj = JSONObject.fromObject(map);
//		result = oj.toString();
//		return "success";
//	}

    //http://www.impf2010.com/ea/jinbi/ea_tixiantest.jspa?user=&sccid=&bankId=&merSeqId=&money=
    public String tixiantest() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String user = request.getParameter("user");//帐号
        String sccid = request.getParameter("sccid");//帐号id
        String bankId = request.getParameter("bankId");//银行卡id
        String merSeqId = request.getParameter("merSeqId");//第三方交易号
        String str = "";
        try {
            int money = (int) (Float.parseFloat((request.getParameter("money") == null ? "0" : request.getParameter("money"))) * 100);//提现金额（元）
            str = jifenser.test(merSeqId, sccid, user, money, bankId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    //http://www.impf2010.com/ea/jinbi/ea_tixiantest2.jspa?user=&sccid=&jNumOrder=&trade_no=&methodPay=&money=
    public String tixiantest2() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String user = request.getParameter("user");//帐号
        String sccid = request.getParameter("sccid");//帐号id
        String jNumOrder = request.getParameter("jNumOrder");//商户订单号
        String trade_no = request.getParameter("trade_no");//第三方交易号
        String methodPay = request.getParameter("methodPay");//兑现方式
        String str = "";
        try {
            TEshopCusCom cusCom = (TEshopCusCom) this.baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
            String amount = new BigDecimal(request.getParameter("money")).subtract(new BigDecimal(Constant.POUNDAGE)).toString();
            str = jifenser.goldWithdrawalVerify(sccid, cusCom.getStaffid(), user, jNumOrder, amount, trade_no, methodPay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public String getJum() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String user = request.getParameter("user");
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
        if (customer != null) {
            String jum = serverService.getBillID("company201009046vxdyzy4wg0000000025");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("jum", jum);
            map.put("wfj_staffid", customer.getStaffid());
            JSONObject oj = JSONObject.fromObject(map);
            result = oj.toString();
        }

        return "success";
    }

    /**
     * 支付宝交易成功异步回调页面
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unused"})
    public void getzfb() {
        System.out.println("----------------------------------");
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        String morre = null;
        String staffid = null;
        String str[] = new String[8];
        try {
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
                        staffid = valueStr;

                        if (staffid != null && !staffid.equals("")) {
                            str = staffid.split(",");
                        }
                        System.out.println("staffid：" + staffid + "-");
                    }
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"),
                // "gbk");
                params.put(name, valueStr);
            }
            isflag = str[7];
            System.out.println(isflag);
            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes(
                    "ISO-8859-1"), "UTF-8");

            // 商户订单号 单据表订单号
            String journalNum = new String(request.getParameter("out_trade_no").getBytes(
                    "ISO-8859-1"), "UTF-8");

            // 交易状态
            String trade_status = new String(request.getParameter(
                    "trade_status").getBytes("ISO-8859-1"), "UTF-8");
            String total_fee = new String(request.getParameter("total_fee")
                    .getBytes("ISO-8859-1"), "UTF-8");
            String seller_id = new String(request.getParameter("seller_id")
                    .getBytes("ISO-8859-1"), "UTF-8");
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            try {
                if (AlipayNotify.verify(params)) {// 验证成功
                    if (trade_status.equals("TRADE_SUCCESS")) {
                        if (seller_id.equals(AlipayConfig.seller_id)) {
                            System.out.println("订单号：" + journalNum);
                            //处理一系列单子
                            //查看该订单是否生成
                            if (journalNum != null && !"".equals(journalNum)) {
                                CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ? ", new Object[]{journalNum});
                                if (cb == null && "gold".equals(isflag)) {
                                    trService.buyJinbi("company201009046vxdyzy4wg0000000025",
                                            journalNum, str[0], str[1], morre, "01", trade_no);
                                    response.getWriter().write("success");
                                } else if (cb == null && "score".equals(isflag)) {
                                    bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025",
                                            journalNum, str[0], str[1], morre, "01", trade_no);
                                    //app积分充值支付宝
                                    OperatorInfo operator = (OperatorInfo) baseBeanService.getBeanByHqlAndParams("from OperatorInfo where journalNum = ?", new Object[]{journalNum});
                                    if (operator != null) {
                                        operator.setStatus("01");
                                        operator.setPayWay("00");
                                        baseBeanService.saveOrUpdate(operator);
                                    }
                                    response.getWriter().write("success");
                                } else if (cb == null && "smsk".equals(isflag)) {
                                    genScanCodePay(trade_no, str[2], morre, str[1], journalNum, "01", "00", "");
                                    response.getWriter().write("success");
                                } else if (cb == null && "gsmzs".equals(isflag)) {
                                    PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                                    genScanAssiCodePay(trade_no, payBackupBill.getWaiterID(), payBackupBill.getCompanyId(), morre, payBackupBill.getSccid(), journalNum, "01", "00", payBackupBill.getCoID());
                                    response.getWriter().write("success");
                                } else if (cb == null && "dsmzs".equals(isflag)) {
                                    PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                                    genScanCodePay(trade_no, payBackupBill.getPpid(), morre, payBackupBill.getSccid(), journalNum, "01", "00", payBackupBill.getWaiterID());
                                    response.getWriter().write("success");
                                } else if (cb == null && "qyht".equals(isflag)) {
                                    PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(journalNum);
                                    genScanCodePay(trade_no, payBackupBill.getPpid(), morre, payBackupBill.getSccid(), journalNum, "01", "00", payBackupBill.getWaiterID());
                                    response.getWriter().write("success");
                                } else {
                                    logger.error("该订单已经生成过了");
                                }

                            } else {
                                logger.error("该订单号为bull");
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
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 支付宝同步
    @SuppressWarnings({"rawtypes", "unused"})
    public String call_back() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        String morre = null;
        String staffid = null;
        String str[] = new String[2];
        String ddid = "";
        try {
            for (Iterator iter = requestParams.keySet().iterator(); iter
                    .hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }

                if (name.equals("total_fee")) {
                    morre = valueStr;
                }
                if (name.equals("body")) {
                    staffid = valueStr;

                    if (staffid != null && !staffid.equals("")) {
                        str = staffid.split(",");
                    }
                    System.out.println("staffid：" + staffid + "-");
                }

                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"),
                // "utf-8");
                params.put(name, valueStr);
            }

            // 支付宝交易号
            String journalNum = new String(request.getParameter("trade_no").getBytes(
                    "ISO-8859-1"), "UTF-8");
            // 商户订单号
            ddid = new String(request.getParameter("out_trade_no").getBytes(
                    "ISO-8859-1"), "UTF-8");

            morre = session.getAttribute("total").toString();

            // 交易状态
            String trade_status = new String(request.getParameter(
                    "trade_status").getBytes("ISO-8859-1"), "UTF-8");

            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            // 计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);

            if (verify_result) {// 验证成功
                String shijian = DateUtil.getCurrentDate("yyyy-MM-dd");
                request.setAttribute("shijian", shijian);
                // 生成收款单
                // response.sendRedirect(orderUrl);返回订单页
            } else {
                // 该页面可做页面美工编辑
                System.out.println("验证失败");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where staffid=? and sccid=?", new Object[]{str[0], str[1]});
        request.setAttribute("syncpay", "syncpay");
        request.setAttribute("syncuser", cusCom.getAccount());
        request.setAttribute("syncuserid", cusCom.getSccId());
        user = cusCom.getAccount();
        sccid = cusCom.getSccId();
        if (str.length > 3) {
            if ("coin".equals(str[2].toString())) {
                khd = ("a".equals(str[3].toString()) ? "" : str[3]);
                flag = ("a".equals(str[4].toString()) ? "" : str[4]);
                identifying = ("a".equals(str[5].toString()) ? "" : str[5]);
                ccompanyId = ("a".equals(str[6].toString()) ? "" : str[6]);

            }
        }

        if (str.length > 7 && str[7] != null && str[7].indexOf("qyht") != -1) {
            request.setAttribute("ddid", ddid);
            return "qyht";
        }

        if (str.length > 7 && str[7] != null && str[7].indexOf("sm") != -1) {
            return "paysuc";
        }
        return "return";
    }

    /**
     * 扫码支付
     *
     * @param tradeCode
     * @param ppid
     * @param morre
     * @param sccid
     * @param journalNum
     * @param wfStatus4
     * @param wfStatus1
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
                        e.printStackTrace();
                        System.out.println("复制订单收款单错误");
                    }
                    //把订单状态改程03已收货
                    goldOrderService.updateFkState(cashierBillsID);
                    //分金币
                    transService.Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
                }
            }

        } catch (Exception e) {
            bool = false;
            e.printStackTrace();
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
            String cashierBillsID = goldOrderService.generateMuliProOrder(waiterID, companyId, morre, sccid, journalNum, coID);
            if (cashierBillsID != null && !cashierBillsID.equals("")) {
                //生成收款单
                Boolean bo = goldOrderService.generateBill(tradeCode, journalNum, morre, wfStatus4, wfStatus1);
                if (bo == true) {
                    try {
                        //收款单生成后复制订单和收款单到新表
                        goldOrderService.copyCash(journalNum, "d");
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("复制订单收款单错误");
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
            e.printStackTrace();
        }
        return bool;
    }


    public String showExcel() {
        HttpServletRequest req = ServletActionContext.getRequest();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String companyID = (String) session.get("companyID");
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "nologin";
        }
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        req.setAttribute("zhxm", req.getParameter("zhxm"));
        req.setAttribute("num", req.getParameter("num"));
        req.setAttribute("shr", req.getParameter("shr"));
        req.setAttribute("payState", req.getParameter("payState"));
        req.setAttribute("sdate", req.getParameter("sdate"));
        req.setAttribute("edate", req.getParameter("edate"));
        Object[] params = {req.getParameter("zhxm"), req.getParameter("num"), req.getParameter("shr"), req.getParameter("payState"),
                req.getParameter("sdate"), req.getParameter("edate")};

        List<BaseBean> excellist = jifenser.getExcelList(account.getCompanyID(), exportNum, params);
        jifenser.saveExportInfo(excellist, account.getStaffID());

        try {
            String path = ExcelUtils.batchExport(WithDrawApply.columnHeadings(), excellist, pageNum);
            InputStream data = ExcelUtils.craeteZipPath(path);
            /*InputStream data = JxlHelper.getExcel(listWinningRecordDTOList);*/

            actionContext.getContext().put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //excelStream = excelService.showExcel(WithDrawApply.columnHeadings(),excellist);
        Date date = new Date();
        String exportFileName = Utilities.getDateString(date, "yyyyMMddHHmmss") + "apply";

        ActionContext.getContext().put("exportFileName", exportFileName);

        return "zip";
    }

    public String getWdsList() {
        HttpServletRequest req = ServletActionContext.getRequest();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String companyID = (String) session.get("companyID");
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "nologin";
        }

        req.setAttribute("zhxm", req.getParameter("zhxm"));
        req.setAttribute("num", req.getParameter("num"));
        req.setAttribute("shr", req.getParameter("shr"));
        req.setAttribute("payState", req.getParameter("payState"));
        req.setAttribute("sdate", req.getParameter("sdate"));
        req.setAttribute("edate", req.getParameter("edate"));

        Object[] params = {req.getParameter("zhxm"), req.getParameter("num"), req.getParameter("shr"), req.getParameter("payState"),
                req.getParameter("sdate"), req.getParameter("edate")};

        pageForm = jifenser.getWdaList(pageForm, pageNumber, params);
        return "wda";

    }

    /**
     * 查取审核信息
     *
     * @return
     */
    public String CustomerCashingAudit() {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpSession session = req.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount account = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        WithDrawApply wday = jifenser.getQueryAudit(wdaID);
        req.setAttribute("wday", wday);
        req.setAttribute("account", account);
        if (flag.equals("01")) {
            return "receipt";
        } else {
            return "customerCashingAudit";
        }
    }

    /**
     * 查取审核信息
     *
     * @return
     */
    public String findCashingAudit() {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpSession session = req.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount account = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        WithDrawApply wday = jifenser.getQueryAudit(wdaID);
        req.setAttribute("wday", wday);
        req.setAttribute("account", account);
        return "findCashingAudit";
    }

    public String addAudit() {
        boolean bl = jifenser.addAudit(tradeCode, receiptOprName, payOperatorName, wdaID, payState, auditOpinion, flag, payOperatorID, receiptOprID);
        JSONObject json = new JSONObject();
        json.accumulate("bl", bl);
        result = json.toString();
        return "success";
    }

    /**
     * 建立请求
     *
     * @return 支付宝授权链接
     */
    public String getAuthInfo() {
        String content = "apiname=com.alipay.account.auth&app_id=" + AlipayConfig.APP_ID + "&app_name=mc&auth_type=AUTHACCOUNT&biz_type=openservice&method=alipay.open.auth.sdk.code.get&pid=" + AlipayConfig.partner + "&product_id=APP_FAST_LOGIN&scope=kuaijie&target_id=" + System.currentTimeMillis() + "&sign_type=" + AlipayConfig.sign_type;
        String sign = null;
        try {
            sign = AlipaySignature.rsaSign(content, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.input_charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        String enCodesign = null;
        try {
            enCodesign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String authInfo = content + "&sign=" + enCodesign;
        result = authInfo;
        return "success";
    }

    /**
     * 建立请求
     *
     * @return 微信授权链接
     */
    public String getWeAuthInfo() {
        //app授权的参数
        Map<String, String> params = Constant.wechatMap.get("apppay");
        // 1.0 拼凑企业支付需要的参数
        String appid = params.get("appID"); // 微信公众号的appid
        String appSecret = params.get("appSecret"); // 商户号

        String authInfo = "appid=" + appid + "&appSecret=" + appSecret;
        result = authInfo;
        return "success";
    }

    //保存支付宝授权信息
    public String saveAliUser() throws AlipayApiException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest req = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        String code = req.getParameter("code");
        sccid = req.getParameter("sccId");
        //code查询user_id和access_token
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse oauthTokenResponse = AlipayConfig.alipayClientNew.execute(request);

        System.out.println(oauthTokenResponse.getAccessToken());
        //根据access_token查询用户信息
        AlipayUserInfoShareRequest userInfoRequest = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse userInfoResponse = AlipayConfig.alipayClientNew.execute(userInfoRequest, oauthTokenResponse.getAccessToken());

        List<BaseBean> beanList = new ArrayList<BaseBean>();
        TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{cusCom.getAccount()});
        cusCom.setUserId(userInfoResponse.getUserId());
        cusCom.setNickName(userInfoResponse.getNickName());
        customer.setUserId(userInfoResponse.getUserId());
        customer.setNickName(userInfoResponse.getNickName());
        cusCom.setWithDraway("01");
        beanList.add(cusCom);
        beanList.add(customer);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
        sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
        sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cusCom);
        req.setAttribute("staffid", cusCom.getStaffid());
        req.setAttribute("state", cusCom.getState());
        req.setAttribute("user", cusCom.getAccount());
        ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where type=? and model=?", new Object[]{"会员类型级别", cusCom.getCusType()});
        req.setAttribute("pp", pp);
        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                "from Staff where staffID=?",
                new Object[]{customer.getStaffid()});
        req.setAttribute("staff", staff);
        WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams(
                " from WfjJifen where staffId=? and sccid=?",
                new Object[]{customer.getStaffid(), sccid});
        req.setAttribute("jifen", jifen);
        req.setAttribute("gold_no", notWithDrawGlod(jifen.getWfjJifenId()));
        BonusPoints bp = new BonusPoints();
        bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{sccid});
        req.setAttribute("bp", bp);
        req.setAttribute("purview", "1");
        return "tixian";
    }


    //保存微信授权信息
    public String saveWeChatUser() throws AlipayApiException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest req = ServletActionContext.getRequest();
        SessionWrap sw = SessionWrap.getInstance();
        String openID = req.getParameter("openID");
        String nickName = req.getParameter("nickName");
        sccid = req.getParameter("sccId");

        List<BaseBean> beanList = new ArrayList<BaseBean>();
        TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{cusCom.getAccount()});
        cusCom.setAppOpenId(openID);
        cusCom.setWeNickName(nickName);
        customer.setAppOpenId(openID);
        customer.setWeNickName(nickName);
        cusCom.setWithDraway("02");
        beanList.add(cusCom);
        beanList.add(customer);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
        sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
        sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cusCom);
        req.setAttribute("staffid", cusCom.getStaffid());
        req.setAttribute("state", cusCom.getState());
        req.setAttribute("user", cusCom.getAccount());
        ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where type=? and model=?", new Object[]{"会员类型级别", cusCom.getCusType()});
        req.setAttribute("pp", pp);
        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                "from Staff where staffID=?",
                new Object[]{customer.getStaffid()});
        req.setAttribute("staff", staff);
        WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams(
                " from WfjJifen where staffId=? and sccid=?",
                new Object[]{customer.getStaffid(), sccid});
        req.setAttribute("jifen", jifen);
        req.setAttribute("gold_no", notWithDrawGlod(jifen.getWfjJifenId()));
        BonusPoints bp = new BonusPoints();
        bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{sccid});
        req.setAttribute("bp", bp);
        req.setAttribute("purview", "1");
        return "tixian";
    }

    //支付宝提现
    public String aliWithdRawal() {
        String str = "";
        HttpServletRequest req = ServletActionContext.getRequest();
        String jNumOrder = req.getParameter("jNumOrder");
        TEshopCustomer tucs = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(" from TEshopCustomer where account= ? and logOff=0", new Object[]{user});
        WfjJifen wfjJifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where sccid=?", new Object[]{sccid});
        String gold = new BigDecimal(morre).multiply(new BigDecimal(100)).toString();
        logger.error("支付宝提现开始:" + tucs.getAccount() + "---" + morre + "---" + jNumOrder);

        if (jNumOrder != null && !"".equals(jNumOrder)) {
            WithDrawReq withDrawReq = new WithDrawReq(serverService.getServerID("withDraw"), sccid, "01", new Date(), jNumOrder, morre);
            withDrawReq.setState("0");
            withDrawReq.setMessage("初始化");
            int count = jifenser.saveOrUpdateithDrawReq(withDrawReq);
            if (count <= 0) {
                if (tucs.getStatus() != null && (tucs.getStatus().equals("1") || tucs.getStatus().equals("2") || tucs.getStatus().equals("3"))) {
                    withDrawReq.setState("12");
                    str = "金币账户冻结,不能提现";
                } else if (gold.indexOf("-") != -1) {
                    withDrawReq.setState("12");
                    str = "输入金币数量为负数";
                } else if (Float.parseFloat(gold) + Float.parseFloat(notWithDrawGlod(wfjJifen.getWfjJifenId())) > Float.parseFloat(wfjJifen.getWfjJifenScore())) {
                    withDrawReq.setState("12");
                    str = "输入金币数量大于金币账户！";
                } else {
                    String amount = new BigDecimal(morre).subtract(new BigDecimal(Constant.POUNDAGE)).toString();

                    try {
                        DefaultAlipayClient alipayClient = AlipayConfig.AlipayConfig();
                        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();

                        request.setBizContent("{" +
                                "\"out_biz_no\":\"" + jNumOrder + "\"," +
                                "\"trans_amount\":\"" + amount + "\"," +
                                "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
                                "\"biz_scene\":\"DIRECT_TRANSFER\"," +
                                "\"order_title\":\"提现转账\"," +
                                "\"payee_info\":{" +
                                "\"identity\":\"" + userId + "\"," +
                                "\"identity_type\":\"ALIPAY_USER_ID\"" +
                                "    }," +
                                "\"remark\":\"提现转账\"" +
                                "  }");

                        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
                        String trade_no = response.getOrderId();
                        if (response.isSuccess()) {
                            try {
                                str = jifenser.goldWithdrawalVerify(sccid, staffid, user, jNumOrder, amount, trade_no, "01");
                                withDrawReq.setState("00");
                            } catch (Exception e) {
                                withDrawReq.setState("01");
                                str = "提现成功，生成订单失败，冻结账户";
                                tucs.setStatus("2");
                                baseBeanService.saveOrUpdate(tucs);
                                e.printStackTrace();
                            }
                        } else {
                            withDrawReq.setState("10");
                            str = response.getSubMsg();
                        }
                    } catch (AlipayApiException e) {
                        withDrawReq.setState("11");
                        str = "交易失败";
                        e.printStackTrace();
                    }
                }
                withDrawReq.setMessage(str);
                jifenser.saveOrUpdateithDrawReq(withDrawReq);
            } else {
                str = "一个小时只能提现一次，您在一个小时内有提现动作，请一个小时后再进行第二次提现！";
            }
        } else {
            str = "页面错误";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        logger.error("支付宝提现结束:" + tucs.getAccount() + "---" + morre + "---" + jNumOrder);
        return "success";
    }


    //微信提现零钱
    public String weWithdRawal() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        String jNumOrder = request.getParameter("jNumOrder");
        String openID = request.getParameter("openID");
        String isWeChat = request.getParameter("isWeChat");
        String str = "";
        TEshopCustomer tucs = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(" from TEshopCustomer where account= ? and logOff=0", new Object[]{user});
        WfjJifen wfjJifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where sccid=?", new Object[]{sccid});
        String gold = new BigDecimal(morre).multiply(new BigDecimal(100)).toString();
        logger.error("微信零钱提现开始:" + tucs.getAccount() + "---" + morre + "---" + jNumOrder);
        if (jNumOrder != null && !"".equals(jNumOrder) && openID != null && !"".equals(openID) && isWeChat != null && !"".equals(isWeChat)) {
            WithDrawReq withDrawReq = new WithDrawReq(serverService.getServerID("withDraw"), sccid, "02", new Date(), jNumOrder, morre);
            withDrawReq.setState("0");
            withDrawReq.setMessage("初始化");
            int count = jifenser.saveOrUpdateithDrawReq(withDrawReq);
            if (count <= 0) {
               if (tucs.getStatus() != null && (tucs.getStatus().equals("1") || tucs.getStatus().equals("2") || tucs.getStatus().equals("3"))) {
                    withDrawReq.setState("12");
                   str = "金币账户冻结,不能提现";
               } else if (gold.indexOf("-") != -1) {
                    withDrawReq.setState("12");
                    str = "输入金币数量为负数";
                } else if (Float.parseFloat(gold) + Float.parseFloat(notWithDrawGlod(wfjJifen.getWfjJifenId())) > Float.parseFloat(wfjJifen.getWfjJifenScore())) {
                    withDrawReq.setState("12");
                    str = "输入金币数量大于金币账户！";
                } else {
                    String amount = new BigDecimal(morre).subtract(new BigDecimal(Constant.POUNDAGE)).toString();
                    amount = WeChatUtils.getMoney(amount);
                    Map<String, String> params = new HashMap<String, String>();
                    String cert_path = "";
                    if ("true".equals(isWeChat)) {
                        //公众账号
                        params = Constant.wechatMap.get("wxa1b3f84c027804c3");
                     //   cert_path = "D:/apiclient_cert.p12";
                        cert_path = "D:/cert/15/apiclient_cert.p12";
                    } else {
                        //app
                        /*params = Constant.wechatMap.get("apppay");
                        cert_path = "D:/appapiclient_cert.p12";*/
                        //商户号15
                        params = Constant.wechatMap.get("wx3eff7ad97837b78b");
                        cert_path = "D:/cert/15/apiclient_cert.p12";
                    }
                    String appID = params.get("appID");
                    String appSecret = params.get("appSecret");
                    if (!"true".equals(isWeChat)) {
                        appID = "wxf17107b0e9021507";
                        appSecret = "bc20c1f38b00b08727676b3f19043535";
                    }
             //       String mchid = params.get("mchid");
                   String mchid = "1561059921";
                    String nonce_str = WeChatUtils.getNonceStr();


                    String partner_key = params.get("partnerkey");

                   partner_key = "bjttstkjyxgsdszltpwfjwxhapi03092";

                   // 2.0 生成map集合
                    SortedMap<String, String> packageParams = new TreeMap<String, String>();
                    packageParams.put("mch_appid", appID); // 微信公众号的appid
                    packageParams.put("mchid", mchid); // 商务号
                    packageParams.put("nonce_str", nonce_str); // 随机生成后数字，保证安全性
                    packageParams.put("partner_trade_no", jNumOrder); // 生成商户订单号
                    packageParams.put("openid", openID); // 支付给用户openid
                    packageParams.put("check_name", "NO_CHECK"); // 是否验证真实姓名呢
                    packageParams.put("amount", amount); // 企业付款金额，单位为分
                    packageParams.put("desc", "提现"); // 企业付款操作说明信息。必填。
                    packageParams.put("spbill_create_ip", WeChatUtils.getRequestIp(request)); // 调用接口的机器Ip地址
                    //初始化参数
                    RequestHandler reqHandler = new RequestHandler(null, null);
                    reqHandler.init(params.get("appID"), appSecret, partner_key);
                    // 利用上面的参数，先去生成自己的签名
                    String sign = reqHandler.createSign(packageParams);
                    // 将签名再放回map中，它也是一个参数
                    packageParams.put("sign", sign);
                    // 将当前的map结合转化成xml格式
                    String xml = null;
                    try {
                        xml = WeChatUtils.mapToXml(packageParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 6.0获取需要发送的url地址
                    String wxUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; // 获取提现的api接口
                    System.out.println("发送前的xml为：" + xml);
                    // 7,向微信发送请求转账请求
                    String returnXml = null;
                    try {
                        returnXml = WeChatUtils.postData(wxUrl, xml, mchid, cert_path);
                    } catch (Exception e) {
                        withDrawReq.setState("11");
                        str = "提现失败";
                        e.printStackTrace();
                    }
                    System.out.println("返回的returnXml为:" + returnXml);
                    // 8，将微信返回的xml结果转成map格式
                    Map<String, String> returnMap = WeChatUtils.parseXmlToList2(returnXml);
                    if (returnMap.get("result_code").equals("SUCCESS")) {
                        // 付款成功
                        try {
                            str = jifenser.goldWithdrawalVerify(sccid, staffid, user, jNumOrder, new BigDecimal(amount).divide(new BigDecimal(100)).toString(), returnMap.get("payment_no"), "00");
                            withDrawReq.setState("00");
                        } catch (Exception e) {
                            withDrawReq.setState("01");
                            str = "提现成功，生成订单失败，冻结账户";
                            tucs.setStatus("2");
                            baseBeanService.saveOrUpdate(tucs);
                            e.printStackTrace();
                        }
                    } else {
                        withDrawReq.setState("10");
                        str = returnMap.get("err_code_des");
                        System.out.println("提现失败");
                    }
                    System.out.println("returnMap为:" + returnMap);
                }
                withDrawReq.setMessage(str);
                jifenser.saveOrUpdateithDrawReq(withDrawReq);
            } else {
                str = "一个小时只能提现一次，您在一个小时内有提现动作，请一个小时后再进行第二次提现！";
            }
        } else {
            str = "页面错误";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        logger.error("微信零钱提现结束:" + tucs.getAccount() + "---" + morre + "---" + jNumOrder);
        return "success";
    }


    //提现到银行卡
    public String withdRawalByBank() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String partner_trade_no = request.getParameter("jNumOrder");
        //判断是否为微信公众账号
        String isWeChat = request.getParameter("isWeChat");
        //提现信息
        String str = "";
        TEshopCustomer tucs = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(" from TEshopCustomer where account= ? and logOff=0", new Object[]{user});
        //银行卡信息
        StaffBankAccount bankAccount = (StaffBankAccount) baseBeanService.getBeanByHqlAndParams("from StaffBankAccount where bankAccountID = ?", new Object[]{bankId});
        WfjJifen wfjJifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where sccid=?", new Object[]{sccid});
       String gold = new BigDecimal(morre).multiply(new BigDecimal(100)).toString();
        logger.error("微信银行卡提现开始:" + tucs.getAccount() + "---" + morre + "---" + partner_trade_no);
        if (partner_trade_no != null && !"".equals(partner_trade_no) && isWeChat != null && !"".equals(isWeChat)) {
            WithDrawReq withDrawReq = new WithDrawReq(serverService.getServerID("withDraw"), sccid, "03", new Date(), partner_trade_no, morre);
            withDrawReq.setState("0");
            withDrawReq.setMessage("初始化");
            int count = jifenser.saveOrUpdateithDrawReq(withDrawReq);
            if (count <= 0) {
                if (tucs.getStatus() != null && (tucs.getStatus().equals("1") || tucs.getStatus().equals("2") || tucs.getStatus().equals("3"))) {
                    withDrawReq.setState("12");
                    str = "金币账户冻结,不能提现";
                } else if (gold.indexOf("-") != -1) {
                    withDrawReq.setState("12");
                    str = "输入金币数量为负数";
                } else if (Float.parseFloat(gold) + Float.parseFloat(notWithDrawGlod(wfjJifen.getWfjJifenId())) > Float.parseFloat(wfjJifen.getWfjJifenScore())) {
                    withDrawReq.setState("12");
                    str = "输入金币数量大于金币账户！";
                } else {

                    String amount = new BigDecimal(morre).subtract(new BigDecimal(Constant.POUNDAGE)).toString();
                    amount = WeChatUtils.getMoney(amount);
                    //银行卡号
                    String bank = bankAccount.getBankAccount();
                    //银行卡持卡人姓名
                    String bankName = bankAccount.getCardholder();
                    //PKCS8密钥文件地址
                    String keyfile = "";
                    Map<String, String> params = new HashMap<String, String>();
                    //证书文件
                    String cert_path = "";

                    Company c = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{"company201009046vxdyzy4wg0000000025"});


                    if ("12".equals(c.getWithDrawNum())) {
                        //TODO 账号没钱修改
                        //公众账号12
                        params = Constant.wechatMap.get("wxa1b3f84c027804c3");
                        cert_path = "D:/apiclient_cert.p12";
                        keyfile = "D:/pksc8_public.pem";

                    } else if ("15".equals(c.getWithDrawNum())) {

                        //公众账号15
                        params = Constant.wechatMap.get("wx3eff7ad97837b78b");
                        cert_path = "D:/cert/15/apiclient_cert.p12";
                        keyfile = "D:/cert/15/pksc8_zlypublic.pem";
                    } else {

                        params = Constant.wechatMap.get("apppay");
                        cert_path = "D:/appapiclient_cert.p12";
                        keyfile = "D:/pksc8_public_app.pem";
                    }
                    PublicKey pub = WeChatUtils.getPubKey(keyfile, "RSA");
                    String rsa = "RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING";
                    byte[] estr = new byte[0];
                    try {
                        //对银行账号进行加密
                        estr = WeChatUtils.encrypt(bank.getBytes("UTF-8"), pub, 2048, 11, rsa);
                    } catch (Exception e) {
                        str = "银行账号加密失败";
                        e.printStackTrace();
                    }
                    //并转为base64格式----  调用付款需要传的 银行卡号
                    bank = Base64.encode(estr);
                    byte[] name = new byte[0];
                    try {
                        //对银行卡持卡人进行加密
                        name = WeChatUtils.encrypt(bankName.getBytes("UTF-8"), pub, 2048, 11, rsa);
                    } catch (Exception e) {
                        str = "银行卡持卡人加密失败";
                        e.printStackTrace();
                    }
                    //并转为base64格式--调用付款需要传的 用户名
                    bankName = Base64.encode(name);

                    String mchid = params.get("mchid");
                    String nonce_str = WeChatUtils.getNonceStr();
                    String bank_code = bankAccount.getBankCode();
                    String desc = "提现";
                    String appSecret = params.get("appSecret");
                    String partner_key = params.get("partnerkey");


                    //获取签名
                    SortedMap<String, String> parameters = new TreeMap<String, String>();
                    parameters.put("mch_id", mchid);
                    parameters.put("partner_trade_no", partner_trade_no);
                    parameters.put("nonce_str", nonce_str);
                    parameters.put("enc_bank_no", bank);
                    parameters.put("enc_true_name", bankName);
                    parameters.put("bank_code", bank_code);
                    parameters.put("amount", amount);
                    parameters.put("desc", desc);
                    RequestHandler reqHandler = new RequestHandler(null, null);
                    reqHandler.init(params.get("appID"), appSecret, partner_key);
                    String sign = reqHandler.createSign(parameters);
                    //请求企业付款
                    parameters.put("sign", sign);
                    String xml = null;
                    try {
                        xml = WeChatUtils.mapToXml(parameters);
                    } catch (Exception e) {
                        str = "map转换xml失败";
                        e.printStackTrace();
                    }

                    String wxUrl = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";
                    String resultXml = null;
                    try {
                        //请求企业支付到银行卡
                        resultXml = WeChatUtils.postData(wxUrl, xml, mchid, cert_path);//WeChatUtils.httpClientResultPANK(xml,mchid,cert_path);
                    } catch (Exception e) {
                        withDrawReq.setState("11");
                        str = "请求提现银行卡失败";
                        e.printStackTrace();
                    }
                    System.out.println(resultXml);
                    Map<String, String> returnMap = WeChatUtils.parseXmlToList2(resultXml);
                    if (returnMap.get("result_code").equals("SUCCESS")) {

                        try {
                            //生成相关订单，扣除金币
                            str = jifenser.goldWithdrawalVerify(sccid, staffid, user, partner_trade_no, new BigDecimal(amount).divide(new BigDecimal(100)).toString(), returnMap.get("payment_no"), "02");
                            withDrawReq.setState("00");
                        } catch (Exception e) {
                            withDrawReq.setState("01");
                            //提现成功，生成订单扣除金币报错时扣除金币
                            str = "提现成功，生成订单失败,账户冻结";
//					float golds = Float.parseFloat(wfjJifen.getWfjJifenScore())-Float.parseFloat(gold);
//					wfjJifen.setWfjJifenScore(golds+"");
                            tucs.setStatus("2");
                            baseBeanService.saveOrUpdate(tucs);
                            e.printStackTrace();
                        }
                    } else if ("SYSTEMERROR".equals(returnMap.get("err_code"))) {
                        withDrawReq.setState("10");
                        //未明确错误，不明确此次付款结果
                        String strQuery = "";
                        try {
                            //避免微信服务订单未入库，隔1秒查询
                            Thread.sleep(1000);

                            SortedMap<String, String> parameter = new TreeMap<String, String>();
                            parameter.put("mch_id", mchid);
                            parameter.put("partner_trade_no", partner_trade_no);
                            parameter.put("nonce_str", nonce_str);
                            RequestHandler reqHandlerByQuery = new RequestHandler(null, null);
                            reqHandlerByQuery.init(params.get("appID"), appSecret, partner_key);
                            String signByQuery = reqHandler.createSign(parameter);
                            parameter.put("sign", signByQuery);

                            String xmlByQuery = null;
                            try {
                                xmlByQuery = WeChatUtils.mapToXml(parameter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // 获取需要发送的url地址
                            String wxUrlByQuery = "https://api.mch.weixin.qq.com/mmpaysptrans/query_bank"; // 获取查询的api接口
                            System.out.println("发送前的xml为：" + xmlByQuery);
                            // 向微信发送请求转账请求
                            String returnXmlByQuery = null;
                            try {
                                returnXmlByQuery = WeChatUtils.postData(wxUrlByQuery, xmlByQuery, mchid, cert_path);
                            } catch (Exception e) {
                                strQuery = "查询失败";
                                e.printStackTrace();
                            }
                            System.out.println("返回的returnXml为:" + returnXmlByQuery);

                            Map<String, String> returnMapByQuery = WeChatUtils.parseXmlToList2(returnXmlByQuery);

                            if ("SUCCESS".equals(returnMapByQuery.get("return_code"))) {
                                if ("PROCESSING".equals(returnMapByQuery.get("status"))) {
                                    //处理中
                                    str = "正在处理中";
                                } else if ("SUCCESS".equals(returnMapByQuery.get("status"))) {
                                    //成功
                                    try {
                                        str = jifenser.goldWithdrawalVerify(sccid, staffid, user, partner_trade_no, new BigDecimal(amount).divide(new BigDecimal(100)).toString(), returnMapByQuery.get("payment_no"), "02");
                                    } catch (Exception e) {
                                        //提现成功，生成订单扣除金币报错时扣除金币
                                        str = "提现成功，生成订单失败,账户冻结";
//								float golds = Float.parseFloat(wfjJifen.getWfjJifenScore())-Float.parseFloat(gold);
//								wfjJifen.setWfjJifenScore(golds+"");
                                        tucs.setStatus("1");
                                        baseBeanService.saveOrUpdate(tucs);
                                        e.printStackTrace();
                                    }
                                } else if ("FAILED".equals(returnMapByQuery.get("status"))) {
                                    //失败
                                    str = "提现失败";
                                } else if ("BANK_FAIL".equals(returnMapByQuery.get("status"))) {
                                    //银行退票
                                    str = "银行退票";
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        withDrawReq.setState("10");
                        str = returnMap.get("err_code_des");
                        System.out.println("提现失败");
                    }
                }
                withDrawReq.setMessage(str);
                jifenser.saveOrUpdateithDrawReq(withDrawReq);
            } else {
                str = "一个小时只能提现一次，您在一个小时内有提现动作，请一个小时后再进行第二次提现！";
            }
        } else {
            str = "页面错误";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        logger.error("微信银行卡提现结束:" + tucs.getAccount() + "---" + morre + "---" + partner_trade_no);
        return "success";
    }

    public String tixian() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String jNumOrder = req.getParameter("jNumOrder");
        String openID = req.getParameter("openID");
        String isWeChat = req.getParameter("isWeChat");
        String withDrawWay = req.getParameter("withDrawWay");

        String str = "";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 微信商户号提现
     * @return
     */
    public String withdrawbywechatsh(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String partner_trade_no = request.getParameter("jNumOrder");
        String companyID = request.getParameter("companyID");
        int amount = (int)Integer.parseInt(WeChatUtils.getMoney(morre));
        String str = jifenser.withdrawbywechatsh(companyID,amount,sccid,partner_trade_no);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
      return "success";

    }

    /**
     *  处理分账历史数据
     */
    public void dealHistory(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        String companyID = request.getParameter("companyID");

        if(type.equals("1")){//处理供应商成本

            String hql = "from HdBackupBillHistory where companyID = ?";
            List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{companyID});


            List<String> mainlist = new ArrayList<String>();
            List<String> monthlist = new ArrayList<String>();
            List<String> jourlist = new ArrayList<String>();

            for (int i = 0; i < list.size(); i++) {
                List<BaseBean> beans = new ArrayList<>();
                HdBackupBillHistory hdBackupBillHistory = (HdBackupBillHistory)list.get(i);
                hdBackupBillHistory.setStatus("2");
                beans.add(hdBackupBillHistory);
                trService.addWxAccountDetail(hdBackupBillHistory,beans,mainlist,monthlist,jourlist);
            }

        }else if(type.equals("2")){//处理提现

            String hql = "from Withdraw";

            List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,null);
            String sql = "select m.compnay_id from dt_sft_withdraw d,dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where d.sub_mchid = t.sub_mchid and t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and d.wdid=?";
            for (int i = 0; i < list.size(); i++) {
                List<BaseBean> beans = new ArrayList<BaseBean>();
                Withdraw withdraw = (Withdraw)list.get(i);
                Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{withdraw.getWdid()});

                jifenser.addWxAccountDetail(withdraw, obj.toString(), beans);

            }

        }


    }

    public void dealHistoryMoney(){
        HttpServletRequest request = ServletActionContext.getRequest();

        String companyID = request.getParameter("companyID");
        String journalNum = request.getParameter("journalNum");
        List<BaseBean> list = null;
        if(journalNum!=null&&!journalNum.equals("")){
             String hql = "from HdBackupBillHistory where journalNum = ?";
             list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{journalNum});
         }else {

             String hql = "from HdBackupBillHistory where companyID = ?";
             list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{companyID});

         }
            List<BaseBean> beans = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {

                HdBackupBillHistory hdBackupBillHistory = (HdBackupBillHistory)list.get(i);
                WxPayDto wxPayDto = trService.getProfitInfo(hdBackupBillHistory.getJournalNum(), hdBackupBillHistory.getCompanyID());

                int money = 0;
                try {
                    JSONObject jo = HTTPV3.profitsharingResult(wxPayDto);

                    money = jo.getInt("finish_amount");

                }catch (Exception e){

                    e.printStackTrace();
                }
                hdBackupBillHistory.setMoney(money+"");
                beans.add(hdBackupBillHistory);



            }
            baseBeanService.executeHqlsByParamsList(beans,null,null);




    }

    /**
     * 处理历史数据
     */
    public String dealperson(){

        String hql  = "from WxAccountDetail where staffName is null";
       List<BaseBean> list =  baseBeanService.getListBeanByHqlAndParams(hql,null);
        List<BaseBean> beans = new ArrayList<BaseBean>();
        try {
            for (BaseBean b : list) {

                WxAccountDetail wxAccountDetail = (WxAccountDetail) b;
                System.out.println(wxAccountDetail.getJournalNum());
                if (wxAccountDetail.getType().equals("供应商成本")) {

                        String hql1 = "from Staff s where s.staffID = (select c.contactUserID from CashierBills c where c.journalNum = ?)";
                        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{wxAccountDetail.getJournalNum()});
                        wxAccountDetail.setStaffName(staff.getStaffName());
                        beans.add(wxAccountDetail);


                } else if (wxAccountDetail.getType().equals("微信商户号提现")) {
                    Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff s where s.staffID = (select m.staffid from TEshopCusCom m where sccId = ?)", new Object[]{wxAccountDetail.getSccid()});
                    wxAccountDetail.setStaffName(staff.getStaffName());
                    beans.add(wxAccountDetail);
                } else if (wxAccountDetail.getType().equals("佣金代收")) {

                    PayCashierBill payCashierBill = (PayCashierBill) baseBeanService.getBeanByHqlAndParams("from PayCashierBill k where k.oriJournalNum = ?", new Object[]{wxAccountDetail.getJournalNum()});


                        if (payCashierBill != null) {
                            String hql1 = "from Staff s where s.staffID = (select c.contactUserID from CashierBills c where c.journalNum = ?)";
                            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{payCashierBill.getPayJournalNum()});
                            wxAccountDetail.setStaffName(staff.getStaffName());
                            beans.add(wxAccountDetail);
                        }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null,null);

            return "success";
    }

    //查询用户绑定信息
    public String getUserInfo() {
        TEshopCusCom customer = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customer", customer);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    //支付宝微信全绑定的情况下选择提现方式
    public String chooseWay() {
        String sql = "update t_eshop_cuscom ec set ec.withdraway =? where ec.sccid = ?";
        List<Object[]> paramslist = new ArrayList<Object[]>();
        paramslist.add(new Object[]{withDraway, sccid});
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isOK = true;
        try {
            baseBeanService.executeSqlsByParmsList(null, new String[]{sql}, paramslist);
        } catch (Exception e) {
            isOK = false;
            e.printStackTrace();
        }
        map.put("isOK", isOK);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    //解除绑定
    public String removeBind() {
        TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isOK = true;
        if (cusCom != null) {
            if ("01".equals(withDraway)) {
                cusCom.setUserId("");
                cusCom.setNickName("");
                cusCom.setWithDraway("");
            } else if ("02".equals(withDraway)) {
                cusCom.setAppOpenId("");
                cusCom.setWeNickName("");
                cusCom.setWithDraway("");
            }
        }
        try {
            baseBeanService.update(cusCom);
        } catch (Exception e) {
            isOK = false;
            e.printStackTrace();
        }
        map.put("isOK", isOK);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    //冻结账户
    public String frozenAccount() {
        List<Object[]> paramslist = new ArrayList<Object[]>();
        paramslist.add(new Object[]{"2", staffid});
        String isOK = "";
        try {
            baseBeanService.executeSqlsByParmsList(null, new String[]{"update  t_eshop_customer ect set ect.status = ?   where ect.staffid = ?"}, paramslist);
            isOK = "true";
        } catch (Exception e) {
            isOK = "false";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isOK", isOK);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    //查询用户积分金币
    public String checkGoldInte() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> map = new HashMap<String, Object>();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        TEshopCusCom tcom = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        BonusPoints bonusPoints = (BonusPoints) baseBeanService.getBeanByHqlAndParams(" from BonusPoints where sccid = ?", new Object[]{tcom.getSccId()});
        WfjJifen wfjJf = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where sccid=?", new Object[]{tcom.getSccId()});
        BigDecimal price = new BigDecimal(morre);
        if (bonusPoints != null && bonusPoints.getBonusPointScore() != null) {
            BigDecimal bp = new BigDecimal(bonusPoints.getBonusPointScore());
            if (bp.compareTo(price.multiply(new BigDecimal(100))) >= 0) {
                map.put("bonusPoints", bonusPoints);
                map.put("integral", price.multiply(new BigDecimal(100)));
            } else {
                if (bonusPoints.getBonusPointScore().contains(".")) {
                    map.put("integral", bonusPoints.getBonusPointScore().substring(0, bonusPoints.getBonusPointScore().indexOf(".")));
                } else {
                    map.put("integral", bonusPoints.getBonusPointScore());
                }
            }
        } else {
            map.put("integral", "");
        }
        //金币数
        if (wfjJf != null && wfjJf.getWfjJifenScore() != null) {
            BigDecimal gold = new BigDecimal(wfjJf.getWfjJifenScore());
            if (gold.compareTo(price.multiply(new BigDecimal(100))) >= 0) {
                map.put("wfjJf", wfjJf);
                map.put("gold", price.multiply(new BigDecimal(100)));
            } else {
                if (wfjJf.getWfjJifenScore().contains(".")) {
                    map.put("gold", wfjJf.getWfjJifenScore().substring(0, wfjJf.getWfjJifenScore().indexOf(".")));
                } else {
                    map.put("gold", wfjJf.getWfjJifenScore());
                }
            }
        } else {
            map.put("gold", "");
        }
        map.put("status", cus.getStatus());
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    public String getWithWalPage() {
        ActionContext.getContext().getSession().put("withDraw_search", withDraw);
        ActionContext.getContext().getSession().put("user_search", user);
        ActionContext.getContext().getSession().put("staffName_search", staffName);
        ActionContext.getContext().getSession().put("sdate_search", sdate);
        ActionContext.getContext().getSession().put("edate_search", edate);
        pageForm = jifenser.goldWithdrawalList(null != pageForm ? pageForm.getPageNumber() : 1, pageNumber, user, withDraw, staffName, sdate, edate, "list");
        return "withWalPage";
    }

    public String WithWalListLimit() {
        withDraw = (WithDrawReq) ActionContext.getContext().getSession().get("withDraw_search");
        user = (String) ActionContext.getContext().getSession().get("user_search");
        staffName = (String) ActionContext.getContext().getSession().get("staffName_search");
        sdate = (String) ActionContext.getContext().getSession().get("sdate_search");
        edate = (String) ActionContext.getContext().getSession().get("edate_search");
        return getWithWalPage();
    }

    public String showExcelByWal() {
        withDraw = (WithDrawReq) ActionContext.getContext().getSession().get("withDraw_search");
        user = (String) ActionContext.getContext().getSession().get("user_search");
        staffName = (String) ActionContext.getContext().getSession().get("staffName_search");
        sdate = (String) ActionContext.getContext().getSession().get("sdate_search");
        edate = (String) ActionContext.getContext().getSession().get("edate_search");
        String[] titles = {"序号", "姓名", "账号", "订单号", "金额", "提现方式", "信息", "时间", "状态"};
        pageForm = jifenser.goldWithdrawalList(null != pageForm ? pageForm.getPageNumber() : 1, pageNumber, user, withDraw, staffName, sdate, edate, "excel");
        List<BaseBean> list = pageForm.getList();
        excelStream = excelService.showExcel(titles, list);
        return "showexcel";
    }

    /**
     * 获取学员信息
     * @return
     */
    public String getCashbill(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String jnum=request.getParameter("journalNum");
        Map<String, Object> map = new HashMap<String, Object>();
        CashierBills c =(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=?",new Object[]{jnum});
        map.put("CtUserName", c.getCtUserName());
        map.put("Reference", c.getReference());
        map.put("StaffIdentityCard", c.getStaffIdentityCard());
        map.put("ReferrerAddress", c.getReferrerAddress());
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    public String notWithDrawGlod(String wfj_jifen_id) {
        /*StringBuffer sql = new StringBuffer("select nvl(sum(wjd.jifen_detail_score), 0) as score from dt_wfj_jifen_detail wjd");
        sql.append(" where (wjd.wfj_guize_id = ? or wjd.wfj_guize_id = ? or wjd.wfj_guize_id = ?) and wjd.wfj_jifen_id =?");*/
        StringBuffer sql = new StringBuffer("select nvl(envelopescore,0) from dt_wfj_jifen where wfj_jifen_id=?");
        StringBuffer goldsql = new StringBuffer("select nvl(sum(wjd.jifen_detail_score),0) as score  from dt_wfj_jifen_detail wjd");
        goldsql.append(" where wjd.wfj_guize_id = ? and wjd.wfj_jifen_id =? ");
        Object gold = baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{wfj_jifen_id});
        Object goldpay = baseBeanService.getObjectBySqlAndParams(goldsql.toString(), new Object[]{"WfjGuize201905072I849AXCPK0000000095", wfj_jifen_id});
        if (Float.parseFloat(gold.toString()) > Float.parseFloat(goldpay.toString())) {
            return Float.toString((Float.parseFloat(gold.toString()) - Float.parseFloat(goldpay.toString())));
        } else {
            return "0";
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getKhd() {
        return khd;
    }

    public void setKhd(String khd) {
        this.khd = khd;
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

    public String getMorre() {
        return morre;
    }

    public void setMorre(String morre) {
        this.morre = morre;
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

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Object getRank() {
        return rank;
    }

    public void setRank(Object rank) {
        this.rank = rank;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }


    public TEshopCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(TEshopCustomer customer) {
        this.customer = customer;
    }

    public TEshopCusCom getCuscom() {
        return cuscom;
    }

    public void setCuscom(TEshopCusCom cuscom) {
        this.cuscom = cuscom;
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }

    public String getIsflag() {
        return isflag;
    }

    public void setIsflag(String isflag) {
        this.isflag = isflag;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public int getExportNum() {
        return exportNum;
    }

    public void setExportNum(int exportNum) {
        this.exportNum = exportNum;
    }

    public String getWdaID() {
        return wdaID;
    }

    public void setWdaID(String wdaID) {
        this.wdaID = wdaID;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getReceiptOprName() {
        return receiptOprName;
    }

    public void setReceiptOprName(String receiptOprName) {
        this.receiptOprName = receiptOprName;
    }

    public String getPayOperatorName() {
        return payOperatorName;
    }

    public void setPayOperatorName(String payOperatorName) {
        this.payOperatorName = payOperatorName;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getPayOperatorID() {
        return payOperatorID;
    }

    public void setPayOperatorID(String payOperatorID) {
        this.payOperatorID = payOperatorID;
    }

    public String getReceiptOprID() {
        return receiptOprID;
    }

    public void setReceiptOprID(String receiptOprID) {
        this.receiptOprID = receiptOprID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWithDraway() {
        return withDraway;
    }

    public void setWithDraway(String withDraway) {
        this.withDraway = withDraway;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public WithDrawReq getWithDraw() {
        return withDraw;
    }

    public void setWithDraw(WithDrawReq withDraw) {
        this.withDraw = withDraw;
    }
}
