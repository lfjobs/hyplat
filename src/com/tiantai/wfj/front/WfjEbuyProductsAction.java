package com.tiantai.wfj.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.AndroidEdition;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.TEshopExtinfo;
import com.tiantai.wfj.util.SessionWrap;
import com.tiantai.wfj.vo.CartItem;
import com.wechatpay.bo.FinalPackage;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.bo.WxPayResult;
import com.wechatpay.service.WchatPay;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.service.RegisterService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Constant;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class WfjEbuyProductsAction<AccountName> {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private RegisterService registerService;
	@Resource
	private UpLoadFileService fileService;
	//ty
	protected HttpServletRequest request;
	private List<BaseBean> productList;// 产品列表
	private List<BaseBean> typeList;
	private StaffAddress staffAddress;
	private Staff staffInf;
	private List<BaseBean> beans;
	private List<BaseBean> orders;
	private String listBillsId;
	private List<BaseBean> billsList;
	private CartItem cartItem;// 一件产品在放入购物车之前，需要构造一个CartItem对象
	private String address;// 缺省地址
	private String[] notSelectPid;// 没有选择的产品ID
	private String pid;
	private String cashid;// 订单id
	private String journalNum;// 订单号
	private String itemnum;
	private String orgnizationId;
	private String orgnizationName;
	private String result;
	private CashierBills cashierBills;
	private GoodsBills goodsBills;
	private ProductPackaging productPackaging;
	private String fkstatus;
	private String sfirst;// 支付方式
	private String ssecond;
	private String sthird;
	private String ssecond2;
	private List<String> cashs;
	private Map<String, CartItem> cartItemmap;
	private String goodname; // 订单名称
	private String total; // 订单金额
	private CDetail companyDetail;
	private CAccount account;
	private String projectType;
	private String trade_no;//支付宝交易号
	private List<String> comInfor;
	private List<String> shopInfor;
	private String priceSum;//订单总金额
	private String baseUrl;//路径前缀
	///给安卓ISO  提供 接口 
	private File  file;//上传的APP或者IOS
	private String fileFileName;//上传的文件名字
	private String filepahe;//上传路径
    private String fileContentType;//上传类型
	private String userfile;//谁上传的文件
	private String andrORios;//判断上传的是安卓还是IOS  0安卓 1IOS
	private String banbenhao;//版本号 
	private String company;//公司名称
	private String applogo;//标识
	private String iosFilename;//苹果的上传路径
	private String jine;
	private String shijian;
	private float jin;
	private String companyType;
	
	
	private String orderID;//用于银联传输参数
	private String wechatbz;
	private String code;
	private FinalPackage finalPackage;
	//wk 2015年12月9日14:49:14
	//加入购物车 需要的字段
	private String  number;//用户购买数量
	private String 	ppid;//产品ID
	private String  inxid;//库存ID
	private Map<String, List<BaseBean>> map;
	/**
	 * 根据订单号查询订单物品详情
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getJournalNumByBill() {
		String orderSql = "select gb.goodsname,gb.price,gb.money,gb.quantity,gb.photoFileName,cb.wfStatus1,"
				+ "cb.wfStatus2,cb.wfStatus3,cb.fkStatus,cb.cashierBillsID,gb.goodsbillsid"
				+ " from dtcashierbills cb ,dtgoodsbills gb where cb.cashierbillsid=gb.cashierbillsid and cb.billsType=? and cb.journalNum=? ";
		Object[] parms;
		parms = new Object[] { "项目收入预算单", journalNum };
		orderSql += " order by  gb.targetstart desc ";
		beans = baseBeanService.getListBeanBySqlAndParams(orderSql, parms);
		return "mywfj1";
	}

	/**
	 * 立即购买
	 * 2015年12月9日14:42:44 现在暂时没有办法 立刻购买   
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String buyProduct() {

		String sql = "select  pp.ppid,pp.goodsname,pp.image,pp.productDetail,pp.stockSize,pp.monthSales, pc.price "
				+ " from dt_productpackaging pp,dt_productpricecategory pc"
				+ " where pp.ppid=pc.ppid and pc.category='零售价' and  pp.ppid=?";
		String[] params = { cartItem.getPid() };
		productList = baseBeanService.getListBeanBySqlAndParams(sql, params);
//		orgnizationId = cartItem.getOrgnizationId();
//		orgnizationName = cartItem.getOrgnizationName();
		itemnum = cartItem.getItemNum();
		return "orderDetail";
	}

	/**
	 * 加入购物车  2015年12月9日14:22:05  WK改
	 * 新需求 ajax  加入购物车    点击加入要是已经加入就让他 提示
	 * @return
	 */
	public String putInCart() {
		HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}
		cartItem =new CartItem();
		//购买商品选择支付宝点击返回因此报错
		//address = sw.getAddress(session);
		staffAddress = (StaffAddress) baseBeanService.getBeanByHqlAndParams(
				"from StaffAddress where staffID=? and isDefault='是'",
				new Object[] { cus.getStaffid() });
		if (address == null && staffAddress != null) {
			address = staffAddress.getAddressDetailed();
		}

		// 将产品加入购物车
		if (cartItemmap != null && !cartItemmap.equals("")) {
			for (CartItem cartItem : cartItemmap.values()) {
				if (cartItem != null && !"".equals(cartItem)) {
					sw.setCartItem(session, cartItem);
				}
			}
		} else {
			if (cartItem != null && !"".equals(cartItem)) {
				sw.setCartItem(session, cartItem);
			}
		}
		return "cart";
	}
	
/////////////////////////////////////////////微信支付开始//////////////////////////////////////////////////
	
	/**
	 * 微信支付  调用后台生成预支付交易单，拼接用来调起支付的参数
	 * 
	 * @return
	 */

	public String weChatpay() {

		HttpServletRequest request = ServletActionContext.getRequest();
		String params = request.getParameter("params");
		String[] arry = params.split("-"); 
		// 生成预支付单
		// 拼接调起支付需要的参数

		WxPayDto tpWxPay = new WxPayDto();
		//获取微信支付需要的openID


		String code = request.getParameter("code");
		logger.info("调试信息");
	    String openId  = getOpenID(code); 
	    logger.info("调试信息");
	
		tpWxPay.setWechatbz("wxff4c5683480d6664");
		
		tpWxPay.setOpenId(openId);
		journalNum = arry[0];
		total = arry[2];
		String staffID = arry[3];
		


		try {
			tpWxPay.setBody(URLDecoder.decode(arry[1],"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			
			logger.error("操作异常", e);
		}
		
		tpWxPay.setOrderId(arry[0]);
		tpWxPay.setSpbillCreateIp("127.0.0.1");
		tpWxPay.setTotalFee(arry[2]);
		
		tpWxPay.setCompanyId("company201009046vxdyzy4wg0000000025");

	    finalPackage = WchatPay.getPackage(tpWxPay);
	    request.setAttribute("staffID", staffID);
		return "wechats";
	}
	

	
	/**
	 * 
	 * 
	 * 获取用户对于公众账号的唯一标识openID
	 * @return
	 */
	public String getOpenID(String code){

		
		
        String openid = null;
		try {
			wechatbz="wxa1b3f84c027804c3";
			String appID = Constant.wechatMap.get(wechatbz).get("appID");
			String secret = Constant.wechatMap.get(wechatbz).get("appSecret");;
			String strparams = "appid="+appID+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
			
			
			String accessurl = "https://api.weixin.qq.com/sns/oauth2/access_token?";


		//	openid = GetWxOrderno.getOpenID(accessurl, strparams);*/
		} catch (Exception e) {
			
			logger.error("操作异常", e);
		}
		
		return openid;
	}
	
	
	/**
	 * 
	 * 
	 * 微信回调通知
	 * @throws Exception
	 */
	public void notifyResult() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
		System.out.print("微信支付回调数据开始");
		//示例报文
//		String xml = "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
		String inputLine;
		String notityXml = "";
		String resXml = "";

		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		logger.info("接收到的报文：: {}", notityXml);
		
		Map m = WeChatUtils.parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());
		
		if("SUCCESS".equals(wpr.getResultCode())){

			journalNum = wpr.getOutTradeNo();
			trade_no = m.get("transaction_id").toString();
			total = m.get("total_fee").toString();
			String hql ="from CashierBills where journalNum = ?";
			CashierBills cashier = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{journalNum});
			 if(cashier.getFkStatus()!=null&&cashier.getFkStatus().equals("01")){
				 generateNewBill();
			 }
			//支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}

		logger.info("微信支付回调数据结束");
		HttpServletResponse response = ServletActionContext.getResponse();
		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();

	}
	
	
	/**
	 * 
	 * 
	 * 微信支付成功后跳转成功或者失败页面（失败后跳回待付款页面）
	 * @return
	 */
	public String jumpResult(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		shijian = formatter.format(currentTime);
		logger.info("订单号：: {}", listBillsId);
		logger.info("金额：: {}", jine);
		if (listBillsId != null & listBillsId.equals("1")) {
			//失败后跳转到待付款页面
			fkstatus = "01";
			return getOrders();
		} else {
			if(result=="0"){
				return "companyReg";
			}
			if(result=="1"){
				String hql = " from SCode where codePID = ? "; 
				typeList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"scode20150815wygb79q82p0000000005"});
				
				return "shopReg";
			}else{
				return"sucOrd";
			}

			
			
		}

	}
	
/////////////////////////////////////////////微信支付结束//////////////////////////////////////////////////
	


	/*
	 * 未注册用户生成订单调用方法
	 */
	public String unregistered() {
		String repage = "";
		if (cashierBills.getWfStatus1().equals("01")) {// 01订单购买
			if (cashierBills.getWfStatus2().equals("01")) {// 订单购买 01现金支付
				if (cashierBills.getWfStatus3().equals("01")) {// 01货到付款（生成出库单）
					cashierBills.setFkStatus("01");
					toCasher2();
					generateNewSRBill();
					repage = "sucOrd";
				} else {// 01立即支付
					cashierBills.setFkStatus("01");
					toCasher2();
					repage = "sucOrd";
				}
			} else {// 订单购买 01在线支付
				cashierBills.setFkStatus("01");
				toCasher2();
				cashierBills.setCashierBillsID(cashid);
				repage = "sucOrd";
			}
		} else {// 01在线支付
			cashierBills.setFkStatus("01");
			toCasher2();
			cashierBills.setCashierBillsID(cashid);
			repage = "sucOrd";
		}
		return repage;
	}

	/**
	 * 保存未注册帐号的客户并保存往来关系为‘联合联盟研讨会会员’
	 * 
	 * @return
	 */
	public BaseBean register() {
		beans = new ArrayList<BaseBean>();
		String staffid = serverService.getServerID("cstaff");
		String shql = "from Staff where staffIdentityCard=? and status='00' ";
		Staff s = (Staff) baseBeanService.getBeanByHqlAndParams(shql,
				new Object[] { staffInf.getStaffIdentityCard().trim() });
		Staff staff = new Staff();
		if (s == null) {
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			staff.setStaffCode("NO" + pcount);
			staff.setRecordCode("NO" + pcount);
			staff.setStaffStatus("00");
			staff.setVerifyTime(new Date());
			staff.setStaffID(staffid);
			staff.setReference(staffInf.getReference());
			staff.setStaffName(staffInf.getStaffName());
			staff.setSource("微分金");
			if (staffInf != null && staffInf.getStaffIdentityCard() != null
					&& !"".equals(staffInf.getStaffIdentityCard().trim())) {
				staff.setStaffIdentityCard(staffInf.getStaffIdentityCard()
						.trim().toUpperCase());
				staff.setStaus("00");
			} else {
				staff.setStaus("01");
			}

			staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
			beans.add(staff);
		} else {
			staffid = s.getStaffID();
			staff = s;
			if (staffInf != null && staffInf.getStaffIdentityCard() != null
					&& !"".equals(staffInf.getStaffIdentityCard().trim())) {
				staff.setStaffIdentityCard(staffInf.getStaffIdentityCard()
						.trim().toUpperCase());
				staff.setStaus("00");
			} else {
				staff.setStaus("01");
			}
			staff.setReference(staffInf.getReference());
			staff.setStaffName(staffInf.getStaffName());
			beans.add(staff);
		}

		if (staffAddress != null && staffAddress.getAddressDetailed() != null
				&& !"".equals(staffAddress.getAddressDetailed().trim())) {
			StaffAddress addr = new StaffAddress();
			addr.setAddressID(serverService.getServerID("address"));
			addr.setStaffID(staffid);
			addr.setAddressDetailed(staffAddress.getAddressDetailed());
			addr.setCompanyID("company201009046vxdyzy4wg0000000025");
			addr.setIsDefault("是");
			beans.add(addr);
		}
		ContactRelation relation = new ContactRelation();
		relation.setRelationID(serverService.getServerID("contactrelation"));
		relation.setRelation("联合联盟研讨会会员");
		relation.setStaffID(staffid);
		relation.setCompanyID("company201009046vxdyzy4wg0000000025");
		beans.add(relation);

		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return staff;
	}

	/**
	 * 从我的微分金生成收款单
	 */
	public void generateNewBill() {
		CashierBills cBills = null;
		try {
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
			// 生成单据信息
			CashierBills caBills = (CashierBills) baseBeanService
					.getBeanByHqlAndParams(
							"from CashierBills where journalNum=?",
							new Object[] { journalNum });
			cBills = (CashierBills) caBills.cloneCashierBills();
			String cashierid = serverService.getServerID("cashierTally");
			cBills.setCashierBillsID(cashierid);
			cBills.setJournalNum(serverService.getBillID(caBills
					.getCompanyID()));
			if(trade_no!=null&&!trade_no.equals("")){
				cBills.setTrade_no(trade_no);
				caBills.setTrade_no(trade_no);
				
			}
			cBills.setCashierBillsKey(null);
			cBills.setBillsType("收款单");
			logger.info("调试信息");
			cBills.setStatus("40");
			cBills.setCashierDate(new Date());
			cBills.setStatusbill("01");
			// 生成物品单据
			List<BaseBean> gBillsList = baseBeanService
					.getListBeanByHqlAndParams(
							"from GoodsBills where cashierBillsID=?",
							new Object[] { caBills.getCashierBillsID() });
			Float qian=0f;
			for (int i = 0; i < gBillsList.size(); i++) {
				GoodsBills gBills1 = (GoodsBills)((GoodsBills) gBillsList.get(i)).cloneGoodsBills();
				String goodsbills = serverService.getServerID("goodsbills");
				gBills1.setGoodsBillsID(goodsbills);
				gBills1.setGoodsBillsKey(null);
				gBills1.setCashierBillsID(cBills.getCashierBillsID());
				gBills1.setPlanGoodsBillsID(((GoodsBills) gBillsList.get(i)).getGoodsBillsID());
				gBills1.setStartDate(new Date());
				qian=qian+Float.parseFloat(gBills1.getMoney());
				ProductPackaging pr=(ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[]{gBills1.getPpID()});
				projectType=pr.getProjectType();
				if (gBills1.getTargetStart() != null) {
					cBills.setTargetStart(gBills1.getTargetStart());
					cBills.setTargetEnd(gBills1.getTargetEnd());
					cBills.setTargetDeptID(gBills1.getTargetDeptID());// 目标部门ID
					cBills.setTargetDeptName(gBills1.getTargetDeptName());// 目标部门name
					cBills.setTargetSalerID(gBills1.getTargetSalerID());// 目标业务员ID
					cBills.setTargetSalerName(gBills1.getTargetSalerName());// 目标业务员Name
				}
				baseBeanList.add(gBills1);
				
				ProductPackaging pro = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID=? ", new Object[]{((GoodsBills) gBillsList.get(i)).getPpID()});
				if(pro.getProjectType()=="02" || pro.getProjectType()=="03" || pro.getProjectType()=="05"){
					//注册成公司
					/*generateNewCompany(caBills.getJournalNum());*/
					result="0";
					companyType = pro.getProjectType();
				}
				if(pro.getProjectType()=="04"){
					//注册成店铺
					result="1";
					/*saveWfjShop(caBills.getJournalNum());*/
				}
			}
			
			if(total!=null&&!total.equals("")){
				cBills.setPriceSub(total);
				caBills.setPriceSub(total);
			}else{
				cBills.setPriceSub(qian.toString());
				caBills.setPriceSub(qian.toString());
			}
			
			CashApplyBills applyBills = new CashApplyBills();
			applyBills.setCashApplyBillsID(serverService
					.getServerID("cashApply"));
			applyBills.setCashierBillsID(cashierid);
			applyBills.setAppropriateStatus("01");
			applyBills.setAppropriationcompanyID(caBills.getInputid());
			applyBills.setAppropriationcompanyName(caBills.getInputName());// 拨款方name
			applyBills.setReceivablesID(caBills.getCompanyID());// 收款方id
			applyBills.setReceivablesName(caBills.getCompanyName());
			applyBills.setAppstyle("01");
			baseBeanList.add(applyBills);
			// 付完款把订单状态设为已付款
			caBills.setFkStatus("00");
			baseBeanList.add(cBills);
			baseBeanList.add(caBills);
			
			// 保存与预算单据关联单据单据表信息
			RelatedBill relatedBill = new RelatedBill();
			relatedBill.setRbID(serverService.getServerID("relatedbill"));
			relatedBill.setCashid(caBills.getCashierBillsID());
			relatedBill.setCashfid(cBills.getCashierBillsID());
			baseBeanList.add(relatedBill);
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			shijian = formatter.format(currentTime);
			listBillsId=caBills.getJournalNum();
			jine=caBills.getPriceSub();
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	/**
	 * 生成新的公司
	 */
	public void generateNewCompany(String JournalNum) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "from Staff where staffId = ?";
		Staff cb = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { cus.getStaffid() });
		Company company = new Company();
		company.setCompanyName(cb.getStaffName() + "公司");
		company.setCompanyType("1");
		company.setShowwechat("01");
		String newComid=serverService.getServerID("company");
		company.setCompanyID(newComid);
		company.setCompanyPID(serverService.getServerID("pcompany"));
		company.setDistrictID(serverService.getServerID("district"));
		company.setGroupCompanySn(serverService.getServerID("groupCompanySn"));
		// 注册日期
		company.setCompanyRegisterDate(new Date());
		// 默认可以使用5个License
		company.setCompanyLicenseCount(5);
		// 指定其状态为正常状态
		company.setCompanyStatus("00");
		company.setComType(projectType);
		company.setCompanyIdentifier(cus.getAccount()+"aa5");
		companyDetail = new CDetail();
		companyDetail.setCompanyManager(cb.getStaffName());
		companyDetail.setCompanyPhone(cb.getReference());
		companyDetail.setCompanyAddress("--");
		companyDetail.setDetailID(serverService.getServerID("cdetail"));
		companyDetail.setCompanyID(company.getCompanyID());
		account = new CAccount();
		account.setStaffID(cb.getStaffID());
		account.setAccountID(serverService.getServerID("caccount"));
		account.setCompanyID(company.getCompanyID());
		account.setAccountName("系统管理员");
		// 用户登录名暂时为Email
		account.setAccountEmail("sa");
		// 加密密码
		account.setAccountPassword(Utilities.MD5("123456"));
		// 指定管理员的状态为正常状态

		account.setAccountStatus("00");
		account.setCompany(company);
		comInfor=new ArrayList<String>();
		comInfor.add(company.getCompanyName());
		comInfor.add(company.getComType());
		comInfor.add(account.getAccountEmail());
		comInfor.add(company.getCompanyIdentifier());
		comInfor.add(JournalNum);
		logger.info("值：{}", comInfor);
		
		TEshopCusCom scc = new TEshopCusCom();
		scc.setSccId(serverService.getServerID("shopcuscom"));
		scc.setCusType("01");
		scc.setCompanyId(newComid);
		scc.setStaffid(cus.getStaffid());
		scc.setAccount(cus.getAccount());
		beans.add(scc);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		registerService.register(company, companyDetail, account);
		
	}
    
	/*
	 * 保存新的WFJ店铺
	 */
	private String getOrgPostMaxNum() {

		String hql = "select max(d.postNum) from DepartmentPost d where companyID = ?";
		Object b = baseBeanService.getObjectByHqlAndParams(hql,
				new Object[] { "company201009046vxdyzy4wg0000000025" });
		String maxcount = "00";
		if (b != null) {
			int m = Integer.parseInt(b.toString()) + 1;
			maxcount = "00" + m;
		} else {
			maxcount = "000";
		}
		return maxcount;
	}
	public void saveWfjShop(String JournalNum) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "from Staff where staffId = ?";
		Staff cb = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { cus.getStaffid() });
		String hql1 = "from COrganization where storageWFJ='00'";
		COrganization or = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hql1,null);

		COrganization organization = new COrganization();
		organization.setOdutiesID(getOrgPostMaxNum());
		String orgid=serverService.getServerID("oraganization");
		organization.setOrganizationID(orgid);// 部门ID
		if(or!=null){
			organization.setOrganizationPID(or.getOrganizationID());// 上级机构ID
		}
		organization.setOrganizationName(cb.getStaffName() + "微分金店");
		organization.setOrganizationCreateDate(new Date());
		organization.setCompanyID("company201009046vxdyzy4wg0000000025");
		organization.setStatus("00");
		organization.setOpostCode("01");
		organization.setOcode(getOrgPostMaxNum());//机构编号
		organization.setIsWfj("是");
		organization.setOpostName("微分金店");
		organization.setOdutiesName("微分金店");
		organization.setOrganizationUrl("/ea/office/ea_toIncomeDepartment");//创收平台创收
		DepartmentPost departmentPost = new DepartmentPost();// 部门岗位
		departmentPost.setDepPostID(serverService.getServerID("departmentPost"));// 岗位ID
		departmentPost.setCompanyID("company201009046vxdyzy4wg0000000025");
		departmentPost.setOrganizationID(organization.getOrganizationID());
		departmentPost.setPostNum(organization.getOdutiesID());// 岗位编号
		departmentPost.setPostName(organization.getOdutiesName()); // 岗位名称
		departmentPost.setPostResponsibility(organization.getOrganizationDesc());// 岗位职责
		departmentPost.setResponsibilityRequire(organization.getOpostRequirements());// 任务要求
		departmentPost.setPostSureNum("1");
		departmentPost.setAdminNum("0");
		departmentPost.setSpecialpostNum("0");
		departmentPost.setOmppostNum("0");

		// 微分金 机构负责人
		TEshopExtinfo shopExtinfo = new TEshopExtinfo();
		shopExtinfo.setOrganizationID(organization.getOrganizationID());
		shopExtinfo.setOwner(cb.getStaffName());
		shopExtinfo.setRegdate(new Date());
		shopExtinfo.setTelephone(cb.getPhoto());
		shopExtinfo.setEshopstatus("0");
		shopExtinfo.setInused("1");

		TEshopCusCom scc = new TEshopCusCom();
		scc.setSccId(serverService.getServerID("shopcuscom"));
		scc.setCusType("02");
		scc.setOrganizationID(orgid);
		scc.setStaffid(cus.getStaffid());
		scc.setAccount(cus.getAccount());
		
		beans = new ArrayList<BaseBean>();
		beans.add(shopExtinfo);
		beans.add(organization);
		beans.add(departmentPost);
		beans.add(scc);
		shopInfor=new ArrayList<String>();
		shopInfor.add(organization.getOrganizationName());
		shopInfor.add(shopExtinfo.getOwner());
		shopInfor.add(JournalNum);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
	}
	/**
	 * 生成出库单
	 */
	public void generateNewSRBill() {
		String hql = "from CashierBills where cashierBillsID = ?";
		CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { cashid });
		try {
			/********** 生成出库单据数据 ********/
			List<BaseBean> baseList = new ArrayList<BaseBean>();
			CashierBills cashierBills = (CashierBills) cb.cloneCashierBills();
			cashierBills.setCashierBillsID(serverService
					.getServerID("cashierTally"));
			cashierBills.setCashierBillsKey(null);
			cashierBills.setBillsType("销售出库单");
			cashierBills.setJournalNum(serverService.getBillID(cb
					.getCompanyID()));
			cashierBills.setStatus("22");
			cashierBills.setCashierDate(new Date());
			baseList.add(cashierBills);

			/********** 生成出库物品数据 ********/
			List<BaseBean> goodlist = baseBeanService
					.getListBeanByHqlAndParams(
							"from GoodsBills where cashierBillsID=?",
							new Object[] { cb.getCashierBillsID() });
			for (int i = 0; i < goodlist.size(); i++) {
				GoodsBills goodsBills = (GoodsBills) goodlist.get(i);
				GoodsBills goodsBills2 = null;
				goodsBills2 = (GoodsBills) goodsBills.cloneGoodsBills();
				goodsBills2.setCashierBillsID(cashierBills.getCashierBillsID());
				goodsBills2.setGoodsBillsID(serverService
						.getServerID("goodsbills"));
				goodsBills2.setGoodsBillsKey(null);
				goodsBills2.setIdentifyingCode("00");// 00为单据原始物品
				baseList.add(goodsBills2);
			}
			/************ 保存单据子表（库存单据表） ***********/
			FinancialBill financialBill = new FinancialBill();
			financialBill.setFinancialbillID(serverService
					.getServerID("financial"));
			financialBill.setCashierBillsID(cashierBills.getCashierBillsID()); // 出纳单单据外键
			financialBill.setGroupCompanySn(cb.getGroupCompanySn()); // 集团公司的标识
																		// 外键
			financialBill.setCompanyID(cb.getCompanyID()); // 公司 外键
			financialBill.setOrganizationID(cb.getOrganizationID()); // 单据所在部门
																		// 外键
			financialBill.setDepartmentID(cb.getDepartmentID()); // 子部门ID(限定部门单据)
																	// 外键
			financialBill.setBillsType("销售出库单"); // 单据类型
			financialBill.setJournalNum(cb.getJournalNum()); // 凭证号（单据编号）
			financialBill.setBillsDate(new Date()); // 制单日期
			financialBill.setBillStaffID(cb.getInputid()); // 制单人ID 外键
			financialBill.setBillStaffName(cb.getInputName()); // 制单人名称
			financialBill.setBillStatus("22");
			baseList.add(financialBill);

			// 保存与预算单据关联单据单据表信息
			RelatedBill relatedBill2 = new RelatedBill();
			relatedBill2.setRbID(serverService.getServerID("relatedbill"));
			relatedBill2.setCashid(cb.getCashierBillsID());
			relatedBill2.setCashfid(cashierBills.getCashierBillsID());
			baseList.add(relatedBill2);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseList, null,
					null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 *  此功能 仅提供给安卓上传   
	 */

	public String addIOSandroid()
	{
		
		AndroidEdition ae=new AndroidEdition();
		String  androidphte="upload_files/ea/Android/";
		String  iosphte="upload_files/ea/Apple/";
		if (file!= null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(
					path,
					fileFileName,
					file,
					"company201009046vxdyzy4wg0000000025", 
					andrORios.equals("0")?androidphte:iosphte
					);
			         filepahe=photoPath; 
			         ae.setAEpahe(filepahe);
		}else{
			ae.setAEpahe(iosFilename);
		}
		
		ae.setAEID(serverService.getServerID("AndroidEdition"));
		ae.setAEEditionTOPUser(userfile);
		
		ae.setAEDate(new Date());
	    ae.setAEIOSORANDROID(andrORios);
		ae.setAEEdition(banbenhao);	
		ae.setAPPname(company);
		ae.setAPPLogo(applogo);
		baseBeanService.save(ae);
		return "login";
	}

	@SuppressWarnings("unchecked")
	public String queryForm(){
	
	

	beans = baseBeanService.getListBeanBySqlAndParams("select d.APPname,d.APPLogo,d.AEIOSORANDROID,AEpahe from dtAndroidEdition d  where d.APPname is not null", new Object[]{});
	
	
    return "queryForm";
	}

	/**
	 * 结算（从session中清除那些没有被选中的产品）,生成项目预算单
	 *  已取消
	 * （如果是银联支付就是也生成收款单）
	 * @return
	 */
//	public String toCasher() {
//		listBillsId = "";
//		HttpSession session = ServletActionContext.getRequest().getSession();
//		SessionWrap sw = SessionWrap.getInstance();
//		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
//				SessionWrap.KEY_CUSTOMER);
//		if (cus == null) {
//			return "login";
//		}
//		beans = new ArrayList<BaseBean>();
//		if (notSelectPid != null && notSelectPid.length > 0) {
//			for (String item : notSelectPid) {
//				SessionWrap.getInstance().clearCartItem(session, item);
//			}
//		}
//		cashs = new ArrayList<String>();// 存订单号
//		// 订单存入数据库
//		Map<String, CartItem> cart = SessionWrap.getInstance().getAllCartIteme(
//				session);
//		if (cart != null && !"".equals(cart)) {
//			Set<String> key = cart.keySet();
//			List<String> orgList = new ArrayList<String>();
//			List<String> orgList1 = new ArrayList<String>();
//			List<String> productId = new ArrayList<String>();
//			for (Iterator it = key.iterator(); it.hasNext();) {
//				String s = (String) it.next();
//				productId.add(s);
//				CartItem c = cart.get(s);
//				String org = c.getOrgnizationId();//产品所属店铺id
//				if (orgList.contains(org)) {
//					orgList1.add(org);
//				} else {
//					orgList.add(org);
//				}
//			}
//
//			for (String s : orgList) {//s是店铺ID
//				// 生成订单，一个店铺的产品生成一个订单，订单保存在店铺所属的顶级部门
//				CashierBills cashierBills = new CashierBills();
//				String billsId = serverService.getServerID("cashierTally");
//				cashierBills.setCashierBillsID(billsId);
//			
//				String comhql = "from Company where companyID=?";
//				String comid = getcomId(s);//店铺所属公司id
//				Company co = (Company) baseBeanService.getBeanByHqlAndParams(
//						comhql, new Object[] { comid });
//				cashierBills.setCompanyID(comid);
//				cashierBills.setCompanyName(co.getCompanyName());
//				cashierBills.setGroupCompanySn(co.getGroupCompanySn());
//				cashierBills.setStatusbill("01");
//				cashierBills.setOrganizationID(getPorg(s));// 微店顶级部门
//				cashierBills.setStartTime(new Date());
//				cashierBills.setBillsType("项目收入预算单");
//				String hqlstaff = "from Staff where staffID = ?";
//				COrganization cOrganization = (COrganization) baseBeanService
//						.getBeanByHqlAndParams(
//								"from COrganization where organizationID=?",
//								new Object[] { s });
//				cashierBills.setDepartmentID(s);// 创收部门
//				cashierBills.setDepartmentName(cOrganization
//						.getOrganizationName());
//				cashierBills.setWfStatus1(sfirst);
//				cashierBills.setFkStatus("01"); //付款状态：00已付款 01未付款
//				if("01".equals(sfirst)){
//					// 支付方式 --订单购买
//					if("01".equals(ssecond)){
//						cashierBills.setWfStatus3(sthird);
//					}else{
//						cashierBills.setWfStatus4(ssecond2);
//					}
//					cashierBills.setWfStatus2(ssecond);
//				}else{
//					//支付方式 --在线支付
//                    cashierBills.setWfStatus4(ssecond2);              			
//				}
//				String hqlt = "from TEshopExtinfo where organizationID = ?";
//				List<BaseBean> extinfolist = baseBeanService.getListBeanByHqlAndParams(hqlt, new Object[] { s });
//				String diantel = "";
//				if (extinfolist.size() != 0) {
//					TEshopExtinfo extinfo = (TEshopExtinfo) extinfolist.get(0);
//					if (extinfo != null) {
//						diantel = extinfo.getTelephone();
//					}
//					cashierBills.setStaffName(extinfo.getOwner());
//
//				}
//				String staffAddress = "";
//				StaffAddress address = (StaffAddress) baseBeanService
//						.getBeanByHqlAndParams(
//								"from StaffAddress where staffID = ? and isDefault = ?",
//								new Object[] { cus.getStaffid(), "是" });
//				if (address != null) {
//					staffAddress = address.getAddressDetailed();
//				}
//				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
//						hqlstaff, new Object[] { cus.getStaffid() });
//				cashierBills.setRemark(staff.getStaffName() + " "
//						+ staff.getReference() + " " + staffAddress + " "
//						+ staff.getStaffIdentityCard() + "   "
//						+ cOrganization.getOrganizationName() + " " + diantel);
//
//				cashierBills.setInputid(cus.getStaffid());// 制单人信息（客户）
//				cashierBills.setInputName(staff.getStaffName());
//
//				// 添加一个订单的产品
//				CartItem xmCartItem = null;
//				CartItem c = null;
//				for (Iterator it = key.iterator(); it.hasNext();) {
//					c = cart.get((String) it.next());
//					if (c.getOrgnizationId().equals(s)) {
//						xmCartItem = c;
//						GoodsBills goodsBills = new GoodsBills();
//						goodsBills.setGoodsBillsID(serverService
//								.getServerID("goodsBills"));
//						goodsBills.setCashierBillsID(billsId);
//						String hql = "from ProductPackaging where ppID=?";
//						ProductPackaging product = (ProductPackaging) baseBeanService
//								.getBeanByHqlAndParams(hql,
//										new Object[] { c.getPid() });
//						String goodsHql = "from GoodsManage where goodsID=?";
//						GoodsManage goods = (GoodsManage) baseBeanService
//								.getBeanByHqlAndParams(goodsHql,
//										new Object[] { product.getGoodsID() });
//						userfile="";
//					 	userfile+=goods.getGoodsName()+",";
//						goodsBills.setGoodsName(goods.getGoodsName());
//						goodsBills.setGoodsNum(goods.getGoodsCoding());
//						goodsBills.setGoodsID(goods.getGoodsID());
//						goodsBills.setTargetStart(new Date());
//						goodsBills.setPpID(productId.get(0));
//						// 15天后 的日期
//						Calendar ca = Calendar.getInstance();
//						ca.setTimeInMillis(new Date().getTime());
//						ca.add(Calendar.DATE, 15);// 天后的日期
//						Date date = new Date(ca.getTimeInMillis()); // 将c转换成Date
//						goodsBills.setTargetEnd(date);
//						goodsBills.setQuantity(c.getItemNum());
//						goodsBills.setPriceManage("零售价");
//						goodsBills.setPrice(c.getPrice());
//						int a = Integer.parseInt(c.getPrice());
//						int b = Integer.parseInt(c.getItemNum());
//						goodsBills.setMoney(String.valueOf(a * b));
//						goodsBills.setPhotoFileName(c.getPic());// 产品图片路径
//						goodsBills.setTargetDeptID(c.getOrgnizationId());// 产品属于那个店铺（目标部门）
//						goodsBills.setTargetDeptName(c.getOrgnizationName());
//						goodsBills.setCompanyID(comid);
//						goodsBills.setConnectName(staff.getStaffName());
//						if (extinfolist.size() != 0) {
//							TEshopExtinfo extinfo = (TEshopExtinfo) extinfolist
//									.get(0);
//							if (extinfo != null) {
//								goodsBills.setTargetSalerID(extinfo
//										.getStaffID());
//								goodsBills.setTargetSalerName(extinfo
//										.getOwner());
//							}
//						}
//						ContactRelation re = (ContactRelation) baseBeanService
//								.getBeanByHqlAndParams(
//										"from ContactRelation where staffID=? and companyID=? and relation=?",
//										new Object[] { cus.getStaffid(), comid,
//												"客户" });
//						if (re == null || "".equals(re)) {
//							ContactRelation relation = new ContactRelation();
//							String reId = serverService
//									.getServerID("contactrelation");
//							relation.setRelationID(reId);
//							relation.setRelation("客户");
//							relation.setStaffID(cus.getStaffid());
//							relation.setCompanyID(comid);
//							beans.add(relation);
//							goodsBills.setConnectID(reId);
//						} else {
//
//							goodsBills.setConnectID(re.getRelationID());
//						}
//
//						beans.add(goodsBills);
//					}
//				}
//
//				String hql = "from ProductPackaging where ppID=?";
//				ProductPackaging pro = (ProductPackaging) baseBeanService
//						.getBeanByHqlAndParams(hql,
//								new Object[] { xmCartItem.getPid() });
//				cashierBills.setXmtype(pro.getXmtype());
//				cashierBills.setXmtypename(pro.getXmtypename());
//				cashierBills.setProID(productId.get(0));
//				cashierBills.setProjectName(xmCartItem.getPname());
//				cashierBills.setProjectCode(pro.getGoodsNum());
//				cashierBills.setStatus("40");// 确定预算
//				cashierBills.setZctype("y");
//				cashierBills.setJournalNum(serverService.getBillID(comid));
//				filepahe=cashierBills.getJournalNum();
//				cashierBills.setCashierDate(new Date());
//				beans.add(cashierBills);
//
//				listBillsId += cashierBills.getJournalNum() + ",";
//				
//
//				cashid = billsId;
//				// 现金支付的货到付款生成出库单
//				if (sthird != null 
//						&& sthird.equals("01")
//						&& pro.getVirtual() != null
//						&& "00".equals(pro.getVirtual())) {// 虚拟物品不生成出库单
//					generateNewSRBill();
//				}
//			}
//			if (listBillsId.contains(",")) {
//				listBillsId = listBillsId
//						.substring(0, listBillsId.length() - 1);
//			}
//			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null, null);
//		}
//		// 订单存入数据库清空session
//		SessionWrap.getInstance().clearAllCartItem(session);
//	
//		billsList=new ArrayList<BaseBean>(); //查询订单和公司返回到sucOrd 
//		Object []  parm=cashs.toArray();
//		 String sql=
//		"select ca.cashierBillsID,co.companyAddr,co.companyTel from  dtcashierbills ca, dtContactCompany co "
//		 + " where ca.companyid=co.ccompanyid ";
////		 "and ca.cashierbillsid " +
////		 "in ('";
//		 
////		for (int i = 0; i < parm.length; i++) { 
////			sql+=parm[i]+"','"; 
////		}
////		 sql=sql.substring(0, sql.length()-2); sql+=")";
//		 billsList=baseBeanService.getListBeanBySqlAndParams(sql, null);
//		
//		// 返回页面
//		/*if ("00".equals(sfirst)) {// 在线支付
//*/			fkstatus="01";
//			return getOrders();
//		/*} else {/// 订单购买
//		try {
//			Date currentTime = new Date();
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			shijian = formatter.format(currentTime);
//			jine=priceSum;
//		} catch (Exception e) {
//			
//			logger.error("操作异常", e);
//		}
//			return "sucOrd";
//		}*/
//	}
	//银联支付
	public void unionpayMoney(){
		//弹出银联支付界面
		//第一个参数地址，只有成功支付才会运行方法，出现错误只会在银联界面，和我们没关系↘
//		orderID= unionpayMeth.frontConsume(baseUrl+"/ea/buyproducts/ea_unionpayQuery.jspa?",baseUrl+"/ea/buyproducts/ea_unionpayQuery.jspa", total,"");
//		logger.info("值：{}", orderID);
//		
//		HttpSession session =  ServletActionContext.getRequest().getSession();
//		session.setAttribute("orID",orderID);
//		session.setAttribute("journalNum",journalNum);
//		session.setAttribute("prm",total);
	}
	//银联查询
	public String  unionpayQuery() throws InterruptedException{
			HttpSession session =  ServletActionContext.getRequest().getSession();
			/*Map<String, String> order= unionpayMeth.query(session.getAttribute("orID")+"", session.getAttribute("orID")+"");
			logger.info("调试信息");
			logger.info("调试信息");
			session.setAttribute("qId",order.get("queryId"));
			String respMsg = order.get("respMsg").substring(0, 2);
			logger.info("值：{}", respMsg);
			if("成功".equals(respMsg)){
				 journalNum = (String) session.getAttribute("journalNum");
				 trade_no = (String) session.getAttribute("orID");
				 total = (String) session.getAttribute("prm");
				 generateNewBill();
				 if(result=="0"){
					return "companyReg";
				 }
				 if(result=="1"){
					String hql = " from SCode where codePID = ? "; 
					typeList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"scode20150815wygb79q82p0000000005"});
					return "shopReg";
				 }
				 Date currentTime = new Date();
			     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			     shijian = formatter.format(currentTime);
				 return "sucOrd";
			}else{
				return getOrders();
			}*/
		return "";
	}
	public String test(){
		String hql = " from SCode where codePID = ? "; 
		typeList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"scode20150815wygb79q82p0000000005"});
		return "shopReg";
	}
	public String testc(){
		return "companyReg";
	}
	/**
	 * 生成项目预算单(未注册客户)
	 * 
	 * @return
	 */
	public void toCasher2() {
		Staff staff = (Staff) register();
		beans = new ArrayList<BaseBean>();
		String comid = getcomId(orgnizationId);
		String cashierid = serverService.getServerID("cashierTally");
		cashierBills.setCashierBillsID(cashierid);
		cashid = cashierid;
		cashierBills.setJournalNum(serverService.getBillID(serverService
				.getBillID(comid)));
		String orghql = "from COrganization where organizationID=?";
		COrganization org = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] { orgnizationId });
		cashierBills.setCashierBillsKey(null);
		cashierBills.setBillsType("项目收入预算单");
		cashierBills.setStatus("40");
		cashierBills.setCashierDate(new Date());
		cashierBills.setTargetStart(new Date());
		cashierBills.setStatusbill("01");
		cashierBills.setCompanyID(comid);
		cashierBills.setOrganizationID(getPorg(orgnizationId));
		cashierBills.setTargetDeptName(org.getOrganizationName());
		// 制单人信息
		cashierBills.setInputName(cashierBills.getInputName());
		String comhql = "from Company where companyID=?";
		Company co = (Company) baseBeanService.getBeanByHqlAndParams(comhql,
				new Object[] { comid });
		cashierBills.setCompanyName(co.getCompanyName());
		cashierBills.setGroupCompanySn(co.getGroupCompanySn());
		cashierBills.setStartTime(new Date());
		// 15天后 的日期
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(new Date().getTime());
		ca.add(Calendar.DATE, 15);// 天后的日期
		Date date = new Date(ca.getTimeInMillis()); // 将c转换成Date
		cashierBills.setTargetEnd(date);
		cashierBills.setDepartmentID(orgnizationId);
		cashierBills.setDepartmentName(org.getOrganizationName());
		String hql = "from ProductPackaging where ppID=?";
		ProductPackaging product = (ProductPackaging) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { pid });
		cashierBills.setXmtype(product.getXmtype());
		cashierBills.setXmtypename(product.getXmtypename());
		cashierBills.setProID(pid);
		cashierBills.setProjectName(product.getGoodsName());
		cashierBills.setProjectCode(product.getGoodsNum());
		cashierBills.setZctype("y");
		cashierBills.setCashierDate(new Date());
		cashierBills.setStaffAddress(cashierBills.getStaffAddress());
		String hqlt = "from TEshopExtinfo where organizationID = ?";
		// 查询店铺电话
		List<BaseBean> extinfolist = baseBeanService.getListBeanByHqlAndParams(
				hqlt, new Object[] { comid });
		String diantel = "";
		if (extinfolist.size() != 0) {
			TEshopExtinfo extinfo = (TEshopExtinfo) extinfolist.get(0);
			if (extinfo != null) {
				diantel = extinfo.getTelephone();
				cashierBills.setStaffName(extinfo.getOwner());
			}
		}
		cashierBills.setRemark(staff.getStaffName() + " "
				+ staff.getReference() + " "
				+ staffAddress.getAddressDetailed() + " "
				+ staff.getStaffIdentityCard() == null ? "" : staff
				.getStaffIdentityCard()
				+ "   "
				+ org.getOrganizationName()
				+ " " + diantel);
		cashierBills.setInputid(staff.getStaffID());// 制单人信息（客户）
		cashierBills.setInputName(staff.getStaffName());
		beans.add(cashierBills);
		goodsBills = new GoodsBills();
		String goodsBillsId = serverService.getServerID("goodsBills");
		goodsBills.setGoodsBillsID(goodsBillsId);
		goodsBills.setCashierBillsID(cashierid);
		String goodsHql = "from GoodsManage where goodsID=?";
		GoodsManage goods = (GoodsManage) baseBeanService
				.getBeanByHqlAndParams(goodsHql,
						new Object[] { product.getGoodsID() });
		goodsBills.setGoodsName(goods.getGoodsName());
		goodsBills.setGoodsNum(goods.getGoodsCoding());
		goodsBills.setGoodsID(goods.getGoodsID());
		goodsBills.setTargetStart(new Date());
		goodsBills.setPpID(pid);
		goodsBills.setStartDate(new Date());
		goodsBills.setTargetEnd(date);
		goodsBills.setQuantity(productPackaging.getQuantity());
		goodsBills.setPriceManage("零售价");
		goodsBills.setPrice(productPackaging.getPrice());
		goodsBills.setMoney(productPackaging.getMoney());
		goodsBills.setTargetDeptID(orgnizationId);// 产品属于那个店铺（目标部门）
		goodsBills.setTargetDeptName(orgnizationName);
		goodsBills.setCompanyID(comid);
		goodsBills.setConnectName(cashierBills.getInputName());
		goodsBills.setConnectID(staff.getStaffID());
		if (extinfolist.size() != 0) {
			TEshopExtinfo extinfo = (TEshopExtinfo) extinfolist.get(0);
			if (extinfo != null) {
				goodsBills.setTargetSalerID(extinfo.getStaffID());
				goodsBills.setTargetSalerName(extinfo.getOwner());
			}
		}
		beans.add(goodsBills);
		listBillsId = cashierBills.getJournalNum();
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
	}
	// 查询微店所属顶级部门
	public String getPorg(String org) {
		String hql = "from COrganization where organizationID=?";
		COrganization organization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { org });
		if (organization.getOrganizationPID().equals(
				organization.getCompanyID())) {
			return org;
		} else {
			return getPorg(organization.getOrganizationPID());
		}
	}
	// 查询微店所属公司
	public String getcomId(String org) {
		String hql = "from COrganization where organizationID=?";
		COrganization organization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { org });
		if (organization.getOrganizationPID().equals(
				organization.getCompanyID())) {
			return organization.getCompanyID();
		} else {
			return getcomId(organization.getOrganizationPID());
		}
	}
	@SuppressWarnings("unchecked")
	public String getOrders() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String orderUrl = request.getRequestURL() + "";
		session.setAttribute("orderUrl", orderUrl);
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		List<Object> parms = new ArrayList<Object>();
		if (cus == null) {
			return "login";
		}
		String userSql = "select staffName from dt_hr_Staff where staffID = ? ";
		result = (String) baseBeanService.getObjectBySqlAndParams(userSql, new Object[]{cus.getStaffid()}); 

		String orderSql = "select cb.companyid,cb.organizationid,gb.targetdeptname,gb.goodsname,gb.price,gb.money,"
				+ " gb.quantity ,gb.photoFileName,cb.cashierDate,cb.cashierBillsID,gb.goodsbillsid,cb.status,cb.fkstatus,cb.wfStatus3,cb.journalnum,cb.wfStatus1,cb.wfStatus2,cb.wfStatus3,cb.wfStatus4  "
				+ " from dtcashierbills cb ,dtgoodsbills gb where cb.cashierbillsid=gb.cashierbillsid "
				+ " and cb.inputid=? ";
		parms.add(cus.getStaffid());
		if (fkstatus != null && !"".equals(fkstatus)) {
			orderSql += " and cb.fkstatus=? ";
			parms.add(fkstatus);
		}
		orderSql += " and cb.status=? order by  cb.cashierdate desc,gb.cashierbillsid";
		parms.add("40");
		beans = baseBeanService.getListBeanBySqlAndParams(orderSql,parms.toArray());
		return "mywfj";
	}
	
	
	
	/**
	 * 
	 * 我的微分金
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getMyOrders() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String orderUrl = request.getRequestURL() + "";
		session.setAttribute("orderUrl", orderUrl);
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		List<Object> parms = new ArrayList<Object>();
		if (cus == null) {
			return "login";
		}
		
		String userSql = "select staffName from dt_hr_Staff where staffID = ? ";
		result = (String) baseBeanService.getObjectBySqlAndParams(userSql, new Object[]{cus.getStaffid()}); 
		String orderSql = "select cb.companyid,cb.organizationid,gb.targetdeptname,gb.goodsname,gb.price,gb.money,"
				+ " gb.quantity ,gb.photoFileName,cb.cashierDate,cb.cashierBillsID,gb.goodsbillsid,cb.status,cb.fkstatus,cb.wfStatus3,cb.journalnum  "
				+ " from dtcashierbills cb ,dtgoodsbills gb where cb.cashierbillsid=gb.cashierbillsid "
				+ " and cb.inputid=? ";
		parms.add(cus.getStaffid());
		if (fkstatus != null && !"".equals(fkstatus)) {
			orderSql += " and cb.fkstatus=? ";
			parms.add(fkstatus);
		}
		orderSql += " and cb.status=? order by  cb.cashierdate desc,gb.cashierbillsid";
		parms.add("40");
		beans = baseBeanService.getListBeanBySqlAndParams(orderSql,parms.toArray());
		return "myOrder";
	}

	@SuppressWarnings("unchecked")
	public String getComOrders(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
		List<Object> parms = new ArrayList<Object>();
		String sql=" select cb.companyid,cb.organizationid,gb.targetdeptname,gb.goodsname,gb.price,gb.money, " +
				" gb.quantity ,gb.photoFileName,cb.cashierDate,cb.cashierBillsID,gb.goodsbillsid,pp.image, " +
				" cb.status,cb.fkstatus,cb.wfStatus3,cb.journalnum  ,cb.inputname,s.reference,sa.addressdetailed" +
				" from dtcashierbills cb ,dtgoodsbills gb ,dt_productpackaging pp,dt_hr_staff s,dt_hr_staff_address sa " +
				" where cb.cashierbillsid=gb.cashierbillsid and cb.inputid=s.staffid and s.staffid=sa.staffid and gb.ppid=pp.ppid ";
				
		if(scc!=null && "2".equals(scc.getCusType())){
			sql+=" and (cb.companyid=? or cb.inputid=? ) ";
			parms.add(scc.getCompanyId());
			parms.add(cus.getStaffid());
		}else if(scc!=null && "4".equals(scc.getCusType())){
			sql+=" and (cb.departmentID=? or cb.inputid=? ) ";
			parms.add(scc.getOrganizationID());
			parms.add(cus.getStaffid());
		}else if(scc!=null){
			sql+=" and cb.inputid=? ";
			parms.add(cus.getStaffid());
		}else{
			return "login";
		}
		if (fkstatus != null && !"".equals(fkstatus)) {
			sql += " and cb.fkstatus=? ";
			parms.add(fkstatus);
		}
		sql+=" and cb.status=? and sa.isdefault=?";
		parms.add("40");
		parms.add("是");
		sql+=" order by  cb.cashierdate desc,gb.cashierbillsid";
		beans=baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
		return "comOrders";
	}

	/**
	 * 功能概括  生成新的代理商  
	 * 方法 流程 购买完之后生成代理商
	 * 参数  上级店铺   当前用户
	 * @return
	 */
	
	public String sevesdl()
	{
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		TEshopCusCom scc=new TEshopCusCom();
		scc.setAccount(cus.getAccount());
		//所选上级店铺
		//scc.setSuperioragent("所选上级店铺");
		scc.setCusType("04");//代理商01公司  02店铺  04代理商 05合伙创业
		scc.setStaffid(cus.getStaffid());
		scc.setSccId(serverService.getServerID("shopcuscom"));
		baseBeanService.save(scc);
		
		return "returnok";
		
	}
	/**
	 * 注册合伙人
	 * 参数上级公司ID 当前用户
	 * @return
	 */
	public String seveshh()
	{
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		TEshopCusCom scc=new TEshopCusCom();
		scc.setAccount(cus.getAccount());
		//所选上级公司
		//scc.setSuperioragent("所选上级公司");
		//scc.setCompanyId("上级公司ID");
		scc.setCusType("05");//代理商01公司  02店铺  04代理商 05合伙创业
		scc.setStaffid(cus.getStaffid());
		scc.setSccId(serverService.getServerID("shopcuscom"));
		baseBeanService.save(scc);
		return "returnok";
		
		
	}
	/**
	 * 取消订单
	 */
	public String deteleCostSheet() {
		String hql1 = "delete from GoodsBills d where d.cashierBillsID = ? ";
		String hql = "delete from CashierBills c where c.cashierBillsID = ? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,  new String[]{hql1,hql}, new Object[]{cashierBills.getCashierBillsID()});		
		return getOrders();
	}

	/**
	 * 把购买数量放入session
	 * 
	 * @return
	 */
	public String updateCartItem() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		sw.updateCartItemNum(session, pid, itemnum);
		return null;
	}

	public List<BaseBean> getProductList() {
		return productList;
	}

	public void setProductList(List<BaseBean> productList) {
		this.productList = productList;
	}

	public StaffAddress getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(StaffAddress staffAddress) {
		this.staffAddress = staffAddress;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String[] getNotSelectPid() {
		return notSelectPid;
	}

	public void setNotSelectPid(String[] notSelectPid) {
		this.notSelectPid = notSelectPid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public GoodsBills getGoodsBills() {
		return goodsBills;
	}

	public void setGoodsBills(GoodsBills goodsBills) {
		this.goodsBills = goodsBills;
	}

	public String getOrgnizationId() {
		return orgnizationId;
	}

	public void setOrgnizationId(String orgnizationId) {
		this.orgnizationId = orgnizationId;
	}

	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}

	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}

	public String getOrgnizationName() {
		return orgnizationName;
	}

	public void setOrgnizationName(String orgnizationName) {
		this.orgnizationName = orgnizationName;
	}

	public String getFkstatus() {
		return fkstatus;
	}

	public void setFkstatus(String fkstatus) {
		this.fkstatus = fkstatus;
	}

	public String getSfirst() {
		return sfirst;
	}

	public void setSfirst(String sfirst) {
		this.sfirst = sfirst;
	}

	public String getSsecond() {
		return ssecond;
	}

	public void setSsecond(String ssecond) {
		this.ssecond = ssecond;
	}

	public String getSthird() {
		return sthird;
	}

	public void setSthird(String sthird) {
		this.sthird = sthird;
	}

	public List<String> getCashList() {
		return cashs;
	}

	public void setCashList(List<String> cashList) {
		this.cashs = cashList;
	}
	public List<String> getCashs() {
		return cashs;
	}

	public void setCashs(List<String> cashs) {
		this.cashs = cashs;
	}

	public Map<String, CartItem> getCartItemmap() {
		return cartItemmap;
	}

	public void setCartItemmap(Map<String, CartItem> cartItemmap) {
		this.cartItemmap = cartItemmap;
	}

	public List<BaseBean> getBillsList() {
		return billsList;
	}

	public void setBillsList(List<BaseBean> billsList) {
		this.billsList = billsList;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getListBillsId() {
		return listBillsId;
	}

	public void setListBillsId(String listBillsId) {
		this.listBillsId = listBillsId;
	}

	public Staff getStaffInf() {
		return staffInf;
	}

	public void setStaffInf(Staff staffInf) {
		this.staffInf = staffInf;
	}

	public String getGoodname() {
		return goodname;
	}

	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}

	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFilepahe() {
		return filepahe;
	}

	public String getAndrORios() {
		return andrORios;
	}

	public void setAndrORios(String andrORios) {
		this.andrORios = andrORios;
	}

	public void setFilepahe(String filepahe) {
		this.filepahe = filepahe;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getUserfile() {
		return userfile;
	}

	public String getBanbenhao() {
		return banbenhao;
	}

	public void setBanbenhao(String banbenhao) {
		this.banbenhao = banbenhao;
	}

	public void setUserfile(String userfile) {
		this.userfile = userfile;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public List<String> getComInfor() {
		return comInfor;
	}
	public void setComInfor(List<String> comInfor) {
		this.comInfor = comInfor;
	}
	public List<String> getShopInfor() {
		return shopInfor;
	}
	public void setShopInfor(List<String> shopInfor) {
		this.shopInfor = shopInfor;
	}

	public String getPriceSum() {
		return priceSum;
	}

	public void setPriceSum(String priceSum) {
		this.priceSum = priceSum;
	}

	public String getSsecond2() {
		return ssecond2;
	}

	public void setSsecond2(String ssecond2) {
		this.ssecond2 = ssecond2;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getWechatbz() {
		return wechatbz;
	}
	public void setWechatbz(String wechatbz) {
		this.wechatbz = wechatbz;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getJine() {
		return jine;
	}

	public void setJine(String jine) {
		this.jine = jine;
	}

	public String getShijian() {
		return shijian;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getInxid() {
		return inxid;
	}

	public void setInxid(String inxid) {
		this.inxid = inxid;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}

	public float getJin() {
		return jin;
	}
	public FinalPackage getFinalPackage() {
		return finalPackage;
	}
	public void setJin(float jin) {
		this.jin = jin;
	}

	public void setFinalPackage(FinalPackage finalPackage) {
		this.finalPackage = finalPackage;
	}

	public List<BaseBean> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<BaseBean> typeList) {
		this.typeList = typeList;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getApplogo() {
		return applogo;
	}

	public void setApplogo(String applogo) {
		this.applogo = applogo;
	}

	public Map<String, List<BaseBean>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<BaseBean>> map) {
		this.map = map;
	}

	public String getIosFilename() {
		return iosFilename;
	}

	public void setIosFilename(String iosFilename) {
		this.iosFilename = iosFilename;
	}
	

}
