package com.tiantai.wfj.front;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.tiantai.wfj.bo.Cart;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.service.IndustryClassificationService;
import com.tiantai.wfj.service.NewPCendService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.bo.WxPayResult;
import com.wechatpay.service.WchatPay;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.OperatorInfo;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.production.InviteBids;
import hy.ea.bo.production.recruit.RecruitInfo;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.finance.service.transferService;
import hy.ea.marketing.service.ProductAgentService;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 *
 * 行业分类、PC端招标招聘、PC端数字地球商城
 */
@Controller
@Scope("prototype")
public class NewPCendAction {
	private Logger logger = LoggerFactory.getLogger(WfjEshopProductAction.class);
	private final static String merId = "504511707060002";
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private ServerService serverService;
	@Resource
	private NewPCendService newPCendService;
	@Resource
	private IndustryClassificationService icfService;
	@Resource
	private ProductAgentService paService;
	@Resource
    private ProductAgentService productAgentService;
	@Resource
	private GoldOrderService goldOrderService;
	@Resource
	private transferService transferService;
	@Resource
	private IndustryClassificationService industryService;
	@Resource
	private BonusPointsService bonusPointsService;

	private PageForm pageForm;
	private ProductPackaging ppk;
	private ProSetupSub pss;
	private InviteBids ibs;
	private RecruitInfo recruitInfo;
	private Cart cart;//商品的购物车
	private Staff staff;
	private String result;
	private String productJudge;
	private String titleJudge;//01:网站资讯,02:办公,03:招商,04:会员中心,05:商城,06:博览,07:招聘,08:经济平台,09:关于

	private String informationJudge;//00:普通查询,01:查询推荐

	private List<BaseBean> list;

	private String codeID;//CCode表分类ID
	private String codePID;//CCode表分类PID
	private Boolean codePID2;//二级codePID
	private String search;//搜索跳转
	private String temporary;//true:进行中,false:已结束
	private String hot;//热门
	private String seven;//00:招标,01:招聘,02:招商
	private String count;//商品购买的数量
	private StaffAddress staffAddress;//用户的收货地址
	private SDistrict sdistrict;//地址
	private String showParam;//是否展示
	private String[] ptppid;//促销品ID
	private String[] ptStandard;//选择的每个促销品规格
	private String[] ptCount;//选择的每个促销品的数量
	private String totalMoney;//购买的产品总价
	private String standard;//购买的产品规格
	private String[] cartIds;//选择的需要结算的购物车商品
	private String[] counts;//选择的需要结算的购物车商品数量
	private String[] companyIds;//选择的需要结算的购物车商品所属公司
	private String[] companyNames;//驾校产品所属公司
	private String[] accountNames;//所有的学员姓名
	private String[] references;//所有的学员电话
	private String[] staffIdentityCards;//所有的学员身份证号
	private String[] staffAddresses;//所有的学员地址
	private String basePath;//根目录地址
	private String payJournalNum;//支付订单编号
	private String total_amount;//支付订单总金额
	private String projectName;//支付订单商品名称
	private String morre;// 物品钱数
	private String ddid;// 订单号
	private String baseUrl;// 路径前缀
	private String journalNum;// 编号payJournalNum
	private String custype;//客户会员类别
	private String cashierBillsID;
	private String ccompanyId;
	private String isflag;

	/**
	 * 查询是否登陆
	 * @return
	 */
	public String skip(){

		TEshopCusCom cuscom = newPCendService.queryUser();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("cuscom", cuscom);
		if(titleJudge.equals("01")){
			return "skipInformation";
		}else if(titleJudge.equals("02")){
			return "work";
		}else if(titleJudge.equals("03")){
            if(seven.equals("00")){
                return "investment";
            }else if(seven.equals("01")){
                return "economicPlatform";
            }else if(seven.equals("02")){
                return "agentQualification";
            }else if(seven.equals("03")){
				return "paySuccess";
			}

		}else if(titleJudge.equals("04")){

		}else if(titleJudge.equals("05")){
			return "skipMall";
		}else if(titleJudge.equals("06")){
            return "readExtensively";
		}else if(titleJudge.equals("07")){
			if(seven.equals("00")){
				return "theTender";
			}else if(seven.equals("01")){
				return "recruitment";
			}else if(seven.equals("02")){
				return "Chinamerchants";
			}
		}else if(titleJudge.equals("08")){

		}else if(titleJudge.equals("09")){
			return "aboutUs";
		}
		return null;
	}

	/**
	 * 退出登录
	 * @return
	 */
	public String quitSession(){

		newPCendService.quitSession();

		return "success";
	}


	/**
	 * 查询中联园周边
	 */
	public String ajaxRim(){

		List<BaseBean> industryList = newPCendService.ajaxRim();

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("industryList", industryList);
		JSONObject obj=JSONObject.fromObject(map);
		result=obj.toString();
		return "success";
	}


	/**
	 * 首页查询国内联营平台
	 * @return
	 */
	public String queryPlatform() {
		pageForm = newPCendService.queryPlatform(pageForm.getPageNumber(),pageForm.getPageSize(),ppk.getStandard());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 首页查询国际联营平台
	 * @return
	 */
	public String international() {
		pageForm = newPCendService.international(pageForm.getPageNumber(),pageForm.getPageSize(),ppk.getPpID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 查询所有资讯
	 * @return
	 */
	public String ajaxInformation(){

		Map<String, Object> map = newPCendService.ajaxInformation(pageForm.getPageNumber(),pageForm.getPageSize(),informationJudge);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 查询资讯详情
	 * @return
	 */
	public String informationForDetails(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] details = null;
		Map<Integer, String[]> map = icfService.informationDetails(ppk.getPpID());
		for (Integer key : map.keySet()) {
			details = map.get(key);
		}
		request.setAttribute("details", details);
		return "InformationForDetails";
	}

	/**
	 * 查询商品所有信息
	 * @return
	 */
	public String goodsDetails(){
		HttpServletRequest request = ServletActionContext.getRequest();

		Map<String, Object> map = newPCendService.goodsDetails(ppk.getPpID());

		request.setAttribute("map", map);

		return "goodsDetails";
	}


	/**
	 * 热卖推荐
	 * @return
	 */
	public String ajaxRecommend(){
		Map<String, Object> map = new HashMap<String, Object>();
		pageForm = newPCendService.ajaxRecommend(pageForm.getPageNumber(),pageForm.getPageSize(),ppk.getPpID());
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 查询商品url
	 * @return
	 */
	public String ajaxProductParticulars(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> urlList = newPCendService.ajaxProductParticulars(ppk.getPpID());
		map.put("urlList",urlList);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 商品评价
	 * @return
	 */
	public String ajaxProductEvaluation(){
		Map<String, Object> map = new HashMap<String, Object>();
		pageForm = newPCendService.ajaxProductEvaluation(pageForm.getPageNumber(),pageForm.getPageSize(),ppk.getPpID());
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * pc端查询购物车
	 * @return
	 */
	public String pcShoppingCart(){

		HttpServletRequest request = ServletActionContext.getRequest();

		Map<String, Object> map = newPCendService.pcShoppingCart();

		request.setAttribute("map",map);

		return "pcShoppingCart";
	}

	/**
	 * PC端商品加入购物车
	 * @return
	 */
	public String ajaxPcAddShoppingCart()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Integer cartCount=newPCendService.ajaxShoppingCartCount(cus.getStaffid());
		Map<String, Object> map=new HashMap<String, Object>();
		if(cartCount <= 50)
		{
			newPCendService.pcAddShoppingCart(cus.getStaffid(),ppk.getPpID(), ptppid, standard, ptStandard, count);
			map.put("add", "addSuccess");
		}else
		{
			map.put("add", "addLimit");
		}
		JSONObject json = JSONObject.fromObject(map);
		this.result = json.toString();
		return "success";
	}

	/**
	 * PC端查询商品购物车
	 * @return
	 */
	public String pcSelShoppingCart()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> map=newPCendService.selPcShoppingCart(cus.getStaffid(),showParam);
		request.setAttribute("map", map);
		return "pcSelShoppingCart";
	}

	/**
	 * PC端删除购物车商品
	 * @return
	 */
	public String ajaxDelShoppingCart()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if(cus==null)
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			this.result="login";
		}else
		{
			newPCendService.ajaxDelShoppingCart(cus.getStaffid(), cart.getCartId());
			this.result="";
		}
		return "success";
	}

	/**
	 * PC端商品立即购买
	 * @return
	 */
	public String pcGoodsPayNow()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,Object> map = null;
		if(cus!=null){
			if("payShoppingCart".equals(showParam))
			{
				map=newPCendService.payShoppingCart(cartIds, counts);
			}else
			{
				map=newPCendService.pcGoodsPayNow(ppk.getPpID(), count, ptppid, ptStandard);
			}
		}
		request.setAttribute("map", map);
		return "pcGoodsPayNow";
	}

	/**
	 * PC端验证用户是否登陆
	 * @return
	 */
	public String ajaxValidateCusLogin()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if(cus==null)
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
		}
		JSONObject json = JSONObject.fromObject(map);
		this.result = json.toString();
		return "success";
	}

	/**
	 *
	 * PC端查询收货地址
	 * @return
	 */
	public String ajaxPcStaffAddress()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Map<String, Object> staffAddress=newPCendService.ajaxPcStaffAddress(cus.getStaffid());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffAddress", staffAddress);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 *
	 * PC端设置默认收货地址
	 * @return
	 */
	public String ajaxChangeDefaultAddress()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if(cus==null)
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			this.result="login";
		}
		else
		{
			newPCendService.ajaxChangeDefaultAddress(cus.getStaffid(), staffAddress.getAddressID());
			this.result="";
		}
		return "success";
	}

	/**
	 *
	 * PC端回显收货地址
	 * @return
	 */
	public String selShowStaffAddress()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> map=newPCendService.selShowStaffAddress(cus.getStaffid(), staffAddress.getAddressID());
		request.setAttribute("map", map);
		request.setAttribute("alter", "alter");
		return "pcAddGoodsStaffAddress";
	}

	/**
	 * PC端新增或修改用户收货地址
	 * @return
	 */
	public String ajaxAddStaffAddress()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Integer addressCount=newPCendService.selStaffAddressCount(cus.getStaffid());
		if(addressCount<10||!"".equals(staffAddress.getAddressID()))
		{
			newPCendService.ajaxAddStaffAddress(cus.getAccount(), staffAddress);
			if("是".equals(staffAddress.getIsDefault()))
			{
				newPCendService.ajaxChangeDefaultAddress(cus.getStaffid(), staffAddress.getAddressID());
			}
			this.result="addSuccess";
		}
		return "success";
	}

	/**
	 * PC端删除用户收货地址
	 * @return
	 */
	public String ajaxDeleteStaffAddress()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if(cus==null)
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			this.result="login";
		}
		else
		{
			newPCendService.ajaxDeleteStaffAddress(cus.getStaffid(), staffAddress.getAddressID());
			this.result="";
		}
		return "success";
	}

	/**
	 * PC端查询一级地址
	 * @return
	 */
	public String ajaxSelDistrictCity()
	{
		Map<String, Object> city=newPCendService.selDistrictCity();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("city", city);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * PC端查询二三级地址
     *
	 * @param @districtID地域ID
	 * @return
	 */
	public String ajaxSelDistrictByID()
	{
		Map<String, Object> district=newPCendService.selDistrictByID(sdistrict.getDistrictID(),showParam);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("district", district);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * PC端用户商城下订单
	 * @return
	 */
	public String ajaxMakePayBills()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if(cus==null)
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
		}
		else if("payShoppingCart".equals(showParam))
		{
			newPCendService.ajaxMakeShoppingCartPayBills(cus.getStaffid(), cus.getAccount(), companyIds, cartIds, counts, staffAddress.getAddressID(), companyNames, accountNames, references, staffIdentityCards, staffAddresses,map);
		}
		else
		{
			newPCendService.ajaxMakePayBills(cus.getStaffid(), cus.getAccount(), ppk.getPpID(), totalMoney, count, standard, ptppid, ptStandard,staffAddress.getAddressID(),staff,map);
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * PC端跳转至选择支付方式
	 * @return
	 */
	public String toGoodsPayMethod()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,Object> map = newPCendService.selBillsDetails(payJournalNum);
		request.setAttribute("map", map);
		return "pcGoodsPayMethod";
	}

	/**
	 * PC端验证支付订单
	 * @return
	 */
	public String ajaxValidatePayBills()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if(cus==null)
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
		}else
		{
			map = newPCendService.ajaxValidatePayBills(payJournalNum, total_amount);
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * PC端交易成功异步回调
	 * @return
	 * @throws IOException
	 */
	public void ajaxWfjAliPayCallBack() throws IOException
	{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,String[]> requestParams = request.getParameterMap();
		String staffid = null;
		String [] str  = new String[8];
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();)
		{
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++)
			{
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			if(name.equals("body")){
				staffid =valueStr;

				if(staffid!=null&&!staffid.equals("")){
					str=staffid.split(",");
				}
				System.out.println("staffid："+staffid+"-");
			}
			//乱码解决，这段代码在出现乱码时使用
			/*valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");*/
			params.put(name, valueStr);
		}
		boolean signVerified =false;
		isflag = (str !=null ?(str.length< 8 ?"":str[7]):"");
		try {
			//调用SDK验证签名
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.APP_PUBLIC_KEY, AlipayConfig.input_charset, AlipayConfig.sign_type);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		//获取响应对象
		HttpServletResponse httpResponse = ServletActionContext.getResponse();
		//设置响应对象编码
		httpResponse.setContentType("text/html;charset=" + AlipayConfig.input_charset);
		if(signVerified)
		{
			//开发者的app_id
			String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");
			//卖家支付宝账户号
			String seller_id = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"), "UTF-8");
			if(AlipayConfig.APP_ID.equals(app_id) && AlipayConfig.seller_id.equals(seller_id))
			{
				//商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
				//付款金额
				String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
				//交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
				System.out.println(trade_status);
				//验证订单是否正确
				Map<String,Object> map = newPCendService.ajaxValidatePayBills(out_trade_no,total_amount);
				if("TRADE_SUCCESS".equals(trade_status) && "score".equals(isflag)){
					//支付宝交易号
					String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
					bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025",out_trade_no,(str!=null?str[0]:""),(str!=null?str[1]:""),total_amount,"01",trade_no);
					//pc积分充值支付宝
					OperatorInfo operator = (OperatorInfo)baseBeanService.getBeanByHqlAndParams("from OperatorInfo where journalNum = ?",new Object[]{out_trade_no});
					System.out.println("操作人--------------"+operator);
					if(operator!=null){
						operator.setStatus("01");
						operator.setPayWay("00");
						baseBeanService.update(operator);
					}

					httpResponse.getWriter().write("success");
				}else if("false".equals((String)map.get("payBillsExist")))
				{
					//支付订单不存在
					logger.error("支付订单号为:"+out_trade_no+"的订单不存在");
					httpResponse.getWriter().write("failure");
				}else if("false".equals((String)map.get("payBillsMoney")))
				{
					//支付订单总金额不正确
					logger.error("支付订单金额有误,实际金额:"+map.get("priceSub")+"---支付宝金额:"+total_amount);
					httpResponse.getWriter().write("failure");
				}else if("TRADE_SUCCESS".equals(trade_status))
				{
					//支付宝交易号
					String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
					//生成收款单
					goldOrderService.generateBill(trade_no, out_trade_no, total_amount, "01", "");
					try {
						//收款单生成后复制订单和收款单到新表
						goldOrderService.copyCash(out_trade_no, "d");
					}catch (Exception e3){
						e3.printStackTrace();
						System.out.println("复制订单收款单错误");
					}
					httpResponse.getWriter().write("success");
				}
			}else
			{
				logger.error("验证身份失败");
				httpResponse.getWriter().write("failure");
			}
		}else
		{
			logger.error("验证签名失败");
			//直接将HTML输出到页面
			httpResponse.getWriter().write("failure");

		}
	    httpResponse.getWriter().flush();
	    httpResponse.getWriter().close();
	}


	/**
	 * PC端支付宝交易成功同步回调页面
	 * @return
	 * @throws IOException
	 */
	public String pcWfjAliPayCallBack() throws IOException
	{
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,String[]> requestParams = request.getParameterMap();
		String [] str  = new String[8];
		if(requestParams.get("body")!=null){
			str = requestParams.get("body")[0].split(",");
		}
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();)
		{
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			if("body".equals(name)){
				continue;
			}
			for (int i = 0; i < values.length; i++)
			{
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			/*valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");*/
			params.put(name, valueStr);
		}
		boolean signVerified =false;
		isflag = (str !=null ?(str.length< 8 ?"":str[7]):"");
		System.out.println(isflag);
		try {
			//调用SDK验证签名
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.APP_PUBLIC_KEY, AlipayConfig.input_charset, AlipayConfig.sign_type);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if(signVerified)
		{
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			//交易付款时间
			String timestamp = new String(request.getParameter("timestamp").getBytes("ISO-8859-1"),"UTF-8");
			request.setAttribute("out_trade_no", out_trade_no);
			request.setAttribute("trade_no", trade_no);
			request.setAttribute("total_amount", total_amount);
			request.setAttribute("timestamp", timestamp);
		}

	    return "pcGoodsPayComplete";
	}

	/**
	 * PC端微信支付生成链接参数
	 * @return
	 * @throws Exception
	 */
	public String ajaxMakeWeChatPayCodeUrl() throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = ServletActionContext.getRequest()
				.getSession();
		HttpSession session = request.getSession();
		SessionWrap sessionWrap = SessionWrap.getInstance();
		String sccid = request.getParameter("sccid");
		TEshopCusCom cus = null;
		if(sccid == null){
			cus  = (TEshopCusCom)sessionWrap.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
		}else {
			cus = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",new Object[]{sccid});
		}
		//创建并添加请求参数
		WxPayDto wxPayDto = new WxPayDto();
		wxPayDto.setWechatbz("wxa1b3f84c027804c3");
		wxPayDto.setBody(projectName);
		wxPayDto.setAttach(cus.getStaffid()+"-"+cus.getSccId()+"-"+projectName);
		wxPayDto.setOrderId(payJournalNum);
		wxPayDto.setTotalFee(total_amount);
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip ==null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip))
	    {
	    	ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip ==null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip))
	    {
	    	ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip ==null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip))
	    {
	    	ip = request.getRemoteAddr();
	    }
		wxPayDto.setSpbillCreateIp(ip);
		wxPayDto.setNotifyUrl(basePath+"ea/newpcend/ea_ajaxWfjWeChatPayCallBack.jspa");
		// 发送请求并接受微信服务器返回的链接参数
		this.result = WchatPay.getCodeurl(wxPayDto);
		return "success";
	}

	/**
	 * PC端微信支付交易成功异步回调
	 * @return
	 * @throws Exception
	 */
	public void ajaxWfjWeChatPayCallBack() throws Exception
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String inputLine = "";
		StringBuilder xmlBuilder = new StringBuilder();
		// 读取异步回调通知中的数据
		while((inputLine=request.getReader().readLine())!=null)
		{
			xmlBuilder.append(inputLine);
		}
		request.getReader().close();
		WxPayResult wxPayResult = WeChatUtils.parseXmlToResult(xmlBuilder.toString());
		// 交易成功并且验签成功
		if("SUCCESS".equals(wxPayResult.getResultCode()) && WeChatUtils.verifyWeChatPayNotify(xmlBuilder.toString(), "wxa1b3f84c027804c3"))
		{
			String journalNum = wxPayResult.getOutTradeNo();
			String transactionId = wxPayResult.getTransactionId().toString();
			String totalFee = wxPayResult.getTotalFee();
			String attach = wxPayResult.getAttach();
			// 判断微信异步通知是否回调过
			if(goldOrderService.isCalledPay(journalNum))
			{
				if (wxPayResult.getAttach().indexOf("微分金积分") != -1) {
					String[] str = attach.split("-");
					bonusPointsService.buyBonusPoints("company201009046vxdyzy4wg0000000025",
							journalNum, str[0], str[1], WeChatUtils.changeF2Y(totalFee), "00", transactionId);
					//pc积分充值微信
					OperatorInfo operator = (OperatorInfo)baseBeanService.getBeanByHqlAndParams("from OperatorInfo where journalNum = ?",new Object[]{journalNum});
					if(operator!=null){
						operator.setStatus("01");
						operator.setPayWay("01");
						baseBeanService.saveOrUpdate(operator);
					}

				}else {
					//生成回款单
					goldOrderService.generateBill(transactionId, journalNum, WeChatUtils.changeF2Y(totalFee), "00", "01");

					try {
						//收款单生成后复制订单和收款单到新表
						goldOrderService.copyCash(journalNum, "d");
					}catch (Exception e){
						e.printStackTrace();
						System.out.println("复制订单收款单错误");
					}
				}
				inputLine = WeChatUtils.backWeixinResult("SUCCESS", "OK");
			}else
			{
				inputLine = WeChatUtils.backWeixinResult("FAIL", "签名失败");
			}
		}else
		{
			// 处理后同步返回给微信参数
			inputLine = WeChatUtils.backWeixinResult("FAIL", wxPayResult.getReturnMsg());
		}
		HttpServletResponse httpResponse = ServletActionContext.getResponse();
		httpResponse.getWriter().write(inputLine);
		httpResponse.getWriter().flush();
		httpResponse.getWriter().close();
	}

	/**
	 * PC端验证微信支付是否交易完成
	 * @return
	 */
	public String ajaxValidateRelatedBill()
	{
		boolean flag = newPCendService.ajaxValidateRelatedBill(payJournalNum);
		if(flag)
		{
			this.result = "payComplete";
		}else
		{
			this.result = "payNotComplete";
		}
		return "success";
	}

	/**
	 *
	 * pc端查询商品招标列表
	 * @return
	 */
	public String ajaxTheTenderList(){

		pageForm = newPCendService.ajaxTheTenderList(search,ppk,temporary,hot,pageForm);

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * pc端查询招标详情
	 * @return
	 */
	public String pcTheTenderDetails(){

		HttpServletRequest request = ServletActionContext.getRequest();

		Map<String, Object> map = newPCendService.pcTheTenderDetails(ppk.getPpID(),ibs.getCashierBillsID(),ppk.getGoodsID());

		request.setAttribute("map",map);

		return "pcTheTenderDetails";
	}

	/**
	 *
	 * PC端查询招聘人才
	 * @return
	 */
	public String ajaxRecruitment()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer coust = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		pageForm =newPCendService.ajaxPCRecruitment(pageForm.getPageNumber(), pageForm.getPageSize(),ppk.getCompanyID(),coust!=null?coust.getStaffid():"");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 *
	 * PC端查询热门职位
	 * @return
	 */
	public String ajaxHotPosition()
	{
		pageForm=newPCendService.ajaxHotPosition(pageForm.getPageNumber(), pageForm.getPageSize(),ppk.getCompanyID());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * PC端查询一级职位分类
	 * @param
	 * @return
	 */
	public String ajaxCodeValueFirst()
	{
		Map<String,Object> map =newPCendService.ajaxCodeValueFirst();
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * PC端查询二级、三级职位分类
	 * @param
	 * @return
	 */
	public String ajaxCodeValue()
	{
		Map<String,Object> map =newPCendService.ajaxCodeValue(codeID);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * PC端招聘职位详情
	 * @param
	 * @return
	 */
	public String pcRecruitmentDetails()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String,Object> map=newPCendService.pcRecruitmentDetails(recruitInfo.getRiId());
		request.setAttribute("map", map);
		return "pcRecruitmentDetails";
	}

	/**
	 * PC端热门招聘职位详情
	 * @param
	 * @return
	 */
	public String pcHotRecruitmentDetails()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer coust = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Map<String,Object> map;
		if(coust!=null && !"".equals(coust)){
			map = newPCendService.pcHotRecruitmentDetails(coust.getStaffid(),recruitInfo.getRiId());
		}else {
			map=newPCendService.pcRecruitmentDetails(recruitInfo.getRiId());
		}
		request.setAttribute("tpID",map.get("resultObj"));
		request.setAttribute("map", map);
		return "pcRecruitmentDetails";
	}

	/**
	 * PC端职位搜索
	 * @param
	 * @return
	 */
	public String ajaxSelPosition()
	{
		pageForm=newPCendService.ajaxSelPosition(pageForm.getPageNumber(), pageForm.getPageSize(),
				recruitInfo.getJobTitle(), codeID, codePID,codePID2,
				recruitInfo.getWorkCity(), recruitInfo.getWorkPlace());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 *
	 * pc端查询代理
	 * @return
	 */
	public String ajaxTheagent(){

		List<Object> list = paService.getNavigation();

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

    /**
     * pc端招商服务主页ajax分页
     */
    public  String pcProAgentList()
    {

        pageForm = newPCendService.proAgentList(
                    pageForm != null ? pageForm.getPageNumber() : 1,
                    pageForm != null ? pageForm.getPageSize() : 7,ppk,hot);
        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm == null ? new PageForm() : pageForm);
        result = json.toString();
        return "success";
    }

    /**
     * pc端招商服务详情
     */
    public  String pcproAgentDetail()
    {
    	HttpServletRequest request = ServletActionContext.getRequest();
        Map<String,Object> map = productAgentService.proAgentDetail(pss.getPpid());
        request.setAttribute("map",map);
        return "pcproAgentDetail";
    }

    /**
     * pc端招商要求
     */
    public  String InvestmentDemand()
    {
    	Map<String,Object> map = productAgentService.demandsDetail(pss.getSuid());
    	list = newPCendService.theQueryImage(ppk.getGoodsID());
    	JSONObject json = new JSONObject();
        json.accumulate("map",map);
        json.accumulate("list",list);
        result = json.toString();
        return "success";
    }
	/**
	 * pc端银联扫码支付
	 *
	 */

	public String UnionPaySweep() throws ServletException, IOException{
		String txnTime = new SimpleDateFormat("HHmmss").format(new Date());
		String TranDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		//String OrderValidTime=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		morre = new BigDecimal(morre).multiply(new BigDecimal(100)).toString();
		if (morre.contains(".")) {
			morre = morre.substring(0, morre.lastIndexOf("."));
		}

		if (payJournalNum == null || payJournalNum.length() == 0) {
			logger.error("银联----ddid不能为空=" + payJournalNum);
		} else {
				JSONObject json = new JSONObject();
				json.accumulate("OrderType", "0001");
				//json.accumulate("OrderValidTime", OrderValidTime);
				json.accumulate("qrPattern","link");
				Map<String, Object> myMap = new HashMap<String, Object>();
				//参与签名的字段和值
				//版本号  固定值：20140728
				myMap.put("Version", "20140728");
				//接入类型 固定值：0
				myMap.put("AccessType", "0");
				//商户号
				myMap.put("MerId", merId);
				//商户订单号
				myMap.put("MerOrderNo", payJournalNum);
				//商户交易日期
				myMap.put("TranDate", TranDate);
				//商户交易时间
				myMap.put("TranTime", txnTime);
				//订单金额
				myMap.put("OrderAmt", morre);
				//交易类型  0001 个人网银支付
				myMap.put("TranType", "0001");
				//业务类型   固定值：0001
				myMap.put("BusiType", "0001");
				//交易币种
				myMap.put("CurryNo", "CNY");
				//商户后台通知地址
				myMap.put("MerBgUrl",basePath + "ea/newpcend/ea_asyncPaySweep.jspa");
				myMap.put("CommodityMsg",payJournalNum);
				//订单扩展域
				myMap.put("OrderReserved", json.toString());
				logger.error("验签数据"+myMap);
				/*String signature = SignUtilSweep.signSweep(myMap);
				logger.error("签名"+signature);
				myMap.put("Signature", signature);
				String url = "http://www.chinapay.com/cofcoko/bgTransGet";
		CloseableHttpResponse httpResonse = HttpSendUtil.sendToOtherServer(
				url , myMap);*/
		/*	logger.error("报文"+httpResonse);
		if (null != httpResonse) {
			String respStr = null;
			try {
				respStr = StringUtil.parseResponseToStr(httpResonse);
				logger.error("respStr:"+respStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//同步响应在页面生成二维码
			Map<String, String> resultMap = StringUtil.paserStrtoMap(respStr);
			logger.error("resultMap:"+resultMap);
			String respCode = resultMap.remove("respCode");
			String PayReserved = resultMap.remove("PayReserved");
			if ("0000".equals(respCode)) {
				resultMap.put("PayReserved", PayReserved);
				JSONObject jo = JSONObject.fromObject(PayReserved);
				this.result = jo.toString();
			}
		}*/
	}

		return "success";
	}

	//接受应答信息
	public void asyncPaySweep() throws ServletException, IOException {
		logger.error("银联异步调用开始----ddid=" + payJournalNum);
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		req.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Enumeration<String> requestNames = req.getParameterNames();
		Map<String, String> resultMap = new HashMap<String, String>();
		while(requestNames.hasMoreElements()){
			String name = requestNames.nextElement();
			String value = req.getParameter(name);
			resultMap.put(name, value);
		}
		logger.error("所要验签的字段"+resultMap);
		// 验证签名
		/*if (!SignUtilSweep.verifySweep(resultMap)) {
			LogUtil.writeLog("异步验证签名结果[失败].");
			logger.error("银联异步调用结束----失败---journalNum====" + journalNum);
		} else {
			journalNum = resultMap.get("MerOrderNo");//第三方流水号
			logger.error("journalNum====" + journalNum);
			payJournalNum=resultMap.get("MerOrderNo");
			logger.error("payJournalNum====" + payJournalNum);
			morre = resultMap.get("OrderAmt");//金额
			morre = new BigDecimal(morre).divide(new BigDecimal(100)).toString();
			LogUtil.writeLog("银联异步验证签名结果[成功].");// 打印
			Boolean bo = goldOrderService.generateBill(journalNum, payJournalNum, morre, "02","");
			if (bo == true) {
				//会员产品只能单独下订单
				if (custype != null && !custype.equals("") && Float.parseFloat(custype) < 7 && cashierBillsID != null && !cashierBillsID.equals("")) {
					//虚拟发货
					transferService.virtual(cashierBillsID);
				}
				logger.error("银联异步调用结束----success---journalNum====" + journalNum);
			}
		}*/
	}

	/**
	 * pc端银联支付
	 *
	 *
	 */

	public void UnionPay() {
		String txnTime = new SimpleDateFormat("HHmmss").format(new Date());
		String TranDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		morre = new BigDecimal(morre).multiply(new BigDecimal(100)).toString();
		if (morre.contains(".")) {
			morre = morre.substring(0, morre.lastIndexOf("."));
		}

		if (payJournalNum == null || payJournalNum.length() == 0) {
			logger.error("银联----ddid不能为空=" + payJournalNum);
		} else {

			/*PcunionpayMeth.frontConsume( baseUrl
					+ "/ea/newpcend/ea_syncPay.jspa",baseUrl
					+ "/ea/newpcend/ea_asyncPay.jspa", morre, txnTime,TranDate, payJournalNum);*/

		}


	}
	/***
	 * 银联同步
	 *
	 */

	public String syncPay() {

		logger.error("银联同步调用开始----ddid=" + payJournalNum );
		HttpServletRequest req = ServletActionContext.getRequest();
		//String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		/*Map<String, String> reqParam = PcunionpayMeth.getAllRequestParam(req);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);// 打印
		logger.error("同步=" + reqParam );
		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Map.Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				valideData.put(key, value);
			}
		}

		// 验证签名
		if (!SignUtil.verifyNocard(valideData)) {
			LogUtil.writeLog("验证签名结果[失败].");
			logger.error("银联同步调用失败----ddid=" + ddid);
		} else {

			journalNum = valideData.get("AcqSeqId");//第三方
			morre = valideData.get("OrderAmt");//金额
			morre = new BigDecimal(morre).divide(new BigDecimal(100)).toString();
			String riqi = valideData.get("TranDate");
			StringBuffer strsb = new StringBuffer();
			strsb.append(riqi.substring(0, 4));
			strsb.append("年");
			strsb.append(riqi.substring(4, 6));
			strsb.append("月");
			strsb.append(riqi.substring(6, 8));
			strsb.append("日");
			ServletActionContext.getRequest().setAttribute("shijian", strsb.toString());
			LogUtil.writeLog("同步验证签名结果[成功].");// 打印
			logger.error("银联同步调用成功----ddid=" + ddid );
		}*/

		return "pcg";
	}
	/***
	 * 银联异步
	 *
	 */

	public void asyncPay() throws ServletException, IOException {
		logger.error("银联异步调用开始----ddid=" + payJournalNum);
		HttpServletRequest req = ServletActionContext.getRequest();
		//String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		/*Map<String, String> reqParam = PcunionpayMeth.getAllRequestParam(req);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);// 打印
		logger.error("异步=" + reqParam);
		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Map.Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = URLDecoder.decode(value, "UTF-8");
				valideData.put(key, value);
			}
		}

		// 验证签名
		if (!SignUtil.verifyNocard(valideData)) {
			LogUtil.writeLog("异步验证签名结果[失败].");
			logger.error("银联异步调用结束----失败---journalNum====" + journalNum);
		} else {
			journalNum = valideData.get("MerOrderNo");//第三方流水号
			logger.error("journalNum====" + journalNum);
			payJournalNum=valideData.get("MerOrderNo");
			logger.error("payJournalNum====" + payJournalNum);
			morre = valideData.get("OrderAmt");//金额
			morre = new BigDecimal(morre).divide(new BigDecimal(100)).toString();
			LogUtil.writeLog("银联异步验证签名结果[成功].");// 打印
			Boolean bo = goldOrderService.generateBill(journalNum, payJournalNum, morre, "02","");
			if (bo == true) {
				//会员产品只能单独下订单
				if (custype != null && !custype.equals("") && Float.parseFloat(custype) < 7 && cashierBillsID != null && !cashierBillsID.equals("")) {
					//虚拟发货
					transferService.virtual(cashierBillsID);
				}
				logger.error("银联异步调用结束----success---journalNum====" + journalNum);
			}
		}*/
	}


    /**
     * pc端城市联营平台
     */
    public String UrbanEconomy()
    {
        list = newPCendService.UrbanEconomy();
        JSONObject json = new JSONObject();
        json.accumulate("list",list);
        result = json.toString();
        return "success";
    }
	/**
	 * pc端城市联营平台
	 */
	public String platformType()
	{
		list = newPCendService.platformType(hot);
		JSONObject json = new JSONObject();
		json.accumulate("list",list);
		result = json.toString();
		return "success";
	}


    /**
	 * pc端城市联营地域平台
	 */
	public String regionalTypes()
	{
		pageForm = newPCendService.regionalTypes(pageForm.getPageNumber(), pageForm.getPageSize(),hot,ppk.getGoodsName());
		JSONObject json = new JSONObject();
		json.accumulate("pageForm",pageForm);
		result = json.toString();
		return "success";
	}


	/**
	 * 查询收货地址
	 */
	public String shippingAddress()
	{
        TEshopCusCom cuscom = newPCendService.queryUser();
        staffAddress = newPCendService.shippingAddress(cuscom);
        JSONObject json = new JSONObject();
        json.accumulate("address",staffAddress!=null?staffAddress.getAddressID():"");
        json.accumulate("user",cuscom.getAccount());
        result = json.toString();
        return "success";
	}



	/**
	 * pc端代理资格
	 */
	public String qualification()
	{
		pageForm = newPCendService.qualification(pageForm.getPageNumber(), pageForm.getPageSize(),hot,ppk.getGoodsName());
		Object object = newPCendService.agencyPrice(ppk.getPpID(),ppk.getCompanyID());
		JSONObject json = new JSONObject();
		json.accumulate("pageForm",pageForm);
        json.accumulate("object",object);
		result = json.toString();
		return "success";
	}



	/**
	 * pc端个人/公司加入平台
	 */
	public String joinPlatform()
	{
		list = newPCendService.joinPlatform(ccompanyId,seven);

		return "joinPlatform";

	}

	/**
	 * pc端查询促销品
	 */
	public String promotionProducts()
	{
		Object object = newPCendService.promotionProducts(ppk.getPpID(),ppk.getCompanyID());
		JSONObject json = new JSONObject();
		json.accumulate("object",object);
		result = json.toString();
		return "success";
	}



	/**
	 * pc端公司网站
	 */
	public String companyWebsite()
	{
        TEshopCusCom cuscom = newPCendService.queryUser();
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("cuscom", cuscom);
        ContactCompany ccom = industryService.getCompany(ccompanyId);
        request.setAttribute("ccom", ccom);
        if(titleJudge.equals("00"))
        {
            list = newPCendService.shufflingFigure(ccompanyId);
            List<Object> news = industryService.NewsList(ccompanyId,"公司简介");
            request.setAttribute("news", news);
            return "homePage";
        }
        else if(titleJudge.equals("01"))
        {
        	if(seven.equals("00"))
			{
				return "companyStore";
			}
			else if(seven.equals("01"))
            {
                Map<String, Object> map = newPCendService.goodsDetails(ppk.getPpID());
                request.setAttribute("map", map);
                return "companyMallDetails";
            }

        }
        else if(titleJudge.equals("02"))
        {
            return "homePage";
        }
        else if(titleJudge.equals("03"))
        {
        	if(seven.equals("00"))
			{
				return "companyChinaMerchants";
			}
			else if(seven.equals("01"))
            {
                list = newPCendService.joinPlatform(ccompanyId,temporary);
                return "cipDetails";
            }
        }
        else if(titleJudge.equals("04"))
        {
            if(seven.equals("00"))
            {
                return "companyNews";
            }
            else if(seven.equals("01"))
            {
				String[] details = null;
				Map<Integer, String[]> map = icfService.informationDetails(ppk.getPpID());
				for (Integer key : map.keySet()) {
					details = map.get(key);
				}
				request.setAttribute("details", details);
                return "companyNewsDetails";
            }
        }
        else if(titleJudge.equals("05"))
        {
            if(seven.equals("00"))
            {
                CcomCom ccomcom=(CcomCom)baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId});
                request.setAttribute("ccomcom", ccomcom);
                return "companyRecruitment";
            }
            else if(seven.equals("01"))
            {
                Map<String,Object> map=newPCendService.pcRecruitmentDetails(recruitInfo.getRiId());
                request.setAttribute("map", map);
                return "crtDetails";
            }
        }
		else if(titleJudge.equals("06"))
		{
			if(seven.equals("00"))
			{
				return "companyAboutUs";
			}
			else if(seven.equals("01"))
			{
				return "aboutOurDetails";
			}
		}
		return null;
	}

    /**
     * 查询公司资讯/推广
     * @return
     */
    public String cnRecommendation() {
        Map<String, Object> map =  newPCendService.companyNews(pageForm.getPageNumber(),pageForm.getPageSize(),ccompanyId);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

	/**
	 * 查询公司简介/文化
	 * @return
	 */
	public String culturalDetails() {
		Map<Integer, String[]> map =  industryService.informationDetails(ppk.getPpID());
		String[] a = map.get(0);
        JSONObject json = new JSONObject();
        json.accumulate("st",a);
		this.result = json.toString();
		return "success";
	}





	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public ProductPackaging getPpk() {
		return ppk;
	}

	public void setPpk(ProductPackaging ppk) {
		this.ppk = ppk;
	}

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProductJudge() {
		return productJudge;
	}

	public void setProductJudge(String productJudge) {
		this.productJudge = productJudge;
	}

	public String getTitleJudge() {
		return titleJudge;
	}

	public void setTitleJudge(String titleJudge) {
		this.titleJudge = titleJudge;
	}

	public String getInformationJudge() {
		return informationJudge;
	}

	public void setInformationJudge(String informationJudge) {
		this.informationJudge = informationJudge;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getCodePID() {
		return codePID;
	}

	public void setCodePID(String codePID) {
		this.codePID = codePID;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getTemporary() {
		return temporary;
	}

	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}

	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}

	public InviteBids getIbs() {
		return ibs;
	}

	public void setIbs(InviteBids ibs) {
		this.ibs = ibs;
	}

	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public ProSetupSub getPss() {
		return pss;
	}

	public void setPss(ProSetupSub pss) {
		this.pss = pss;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public StaffAddress getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(StaffAddress staffAddress) {
		this.staffAddress = staffAddress;
	}

	public SDistrict getSdistrict() {
		return sdistrict;
	}

	public void setSdistrict(SDistrict sdistrict) {
		this.sdistrict = sdistrict;
	}

	public String getShowParam() {
		return showParam;
	}

	public void setShowParam(String showParam) {
		this.showParam = showParam;
	}

	public String[] getPtppid() {
		return ptppid;
	}

	public void setPtppid(String[] ptppid) {
		this.ptppid = ptppid;
	}

	public String[] getPtStandard() {
		return ptStandard;
	}

	public void setPtStandard(String[] ptStandard) {
		this.ptStandard = ptStandard;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public String[] getPtCount() {
		return ptCount;
	}

	public void setPtCount(String[] ptCount) {
		this.ptCount = ptCount;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String[] getCartIds() {
		return cartIds;
	}

	public void setCartIds(String[] cartIds) {
		this.cartIds = cartIds;
	}

	public String[] getCounts() {
		return counts;
	}

	public void setCounts(String[] counts) {
		this.counts = counts;
	}

	public String[] getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String[] companyIds) {
		this.companyIds = companyIds;
	}

	public String[] getCompanyNames() {
		return companyNames;
	}

	public void setCompanyNames(String[] companyNames) {
		this.companyNames = companyNames;
	}

	public String[] getAccountNames() {
		return accountNames;
	}

	public void setAccountNames(String[] accountNames) {
		this.accountNames = accountNames;
	}

	public String[] getReferences() {
		return references;
	}

	public void setReferences(String[] references) {
		this.references = references;
	}

	public String[] getStaffIdentityCards() {
		return staffIdentityCards;
	}

	public void setStaffIdentityCards(String[] staffIdentityCards) {
		this.staffIdentityCards = staffIdentityCards;
	}

	public String[] getStaffAddresses() {
		return staffAddresses;
	}

	public void setStaffAddresses(String[] staffAddresses) {
		this.staffAddresses = staffAddresses;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getPayJournalNum() {
		return payJournalNum;
	}

	public void setPayJournalNum(String payJournalNum) {
		this.payJournalNum = payJournalNum;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMorre() {
		return morre;
	}

	public void setMorre(String morre) {
		this.morre = morre;
	}

	public String getDdid() {
		return ddid;
	}

	public void setDdid(String ddid) {
		this.ddid = ddid;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getCustype() {
		return custype;
	}

	public void setCustype(String custype) {
		this.custype = custype;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
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

	public Boolean getCodePID2() {
		return codePID2;
	}

	public void setCodePID2(Boolean codePID2) {
		this.codePID2 = codePID2;
	}

	public String getIsflag() {
		return isflag;
	}

	public void setIsflag(String isflag) {
		this.isflag = isflag;
	}
}
