package com.wechatpay.service;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.wechatpay.bo.AppPackage;
import com.wechatpay.bo.FinalPackage;
import com.wechatpay.bo.StatementReq;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.utils.ClientCustomSSL;
import com.wechatpay.utils.GetWxOrderno;
import com.wechatpay.utils.RequestHandler;
import com.wechatpay.utils.Sha1Util;
import com.wechatpay.utils.TenpayUtil;
import com.wechatpay.utils.WeChatUtils;

import hy.ea.util.Constant;

public class WchatPay {

	// 微信支付成功后通知地址 必须要求80端口并且地址不能带参数

	static HttpServletRequest request = ServletActionContext.getRequest();
	static String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";

	private static String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private static String notifyurl = basePath + "/ea/wfjshop/sajax_ea_notifyResult.jspa"; // Key

	/**
	 * 公众账号微信支付 获取请求预支付id报文 以及最终调起支付请求包
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static FinalPackage getPackage(WxPayDto tpWxPayDto) {

		String openId = tpWxPayDto.getOpenId();
		// 1 参数

		// 附加数据 原样返回
		String attach = tpWxPayDto.getAttach();
		// 总金额以分为单位，不带小数点
		String totalFee = WeChatUtils.getMoney(tpWxPayDto.getTotalFee());

		// 订单生成的机器 IP
		String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = notifyurl;
		String trade_type = "JSAPI";

		// ---必须参数
		// 商户号
		String mch_id = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("mchid");
		// 随机字符串
		String nonce_str = WeChatUtils.getNonceStr();

		// 商品描述根据情况修改
		String body = tpWxPayDto.getBody();

		String appid = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID");
		String appsecret = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appSecret");
		String partnerkey = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("partnerkey");
		// 商户订单号
		String out_trade_no = tpWxPayDto.getOrderId();

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);// 订单号

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openId);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body><![CDATA[" + body + "]]></body>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>" + "<attach>" + attach + "</attach>"
				+ "<total_fee>" + totalFee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openId + "</openid>" + "</xml>";
	
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		WxPayDto pto = new GetWxOrderno().getPayNo(createOrderURL, xml);

		System.out.println("获取到的预支付ID：" + pto.getPrepay_id());

		// 获取prepay_id后，拼接最后请求支付所需要的package

		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String packages = "prepay_id=" + pto.getPrepay_id();
		finalpackage.put("appId", appid);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonce_str);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		// 要签名
		String finalsign = reqHandler.createSign(finalpackage);

		String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timestamp + "\",\"nonceStr\":\""
				+ nonce_str + "\",\"package\":\"" + packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
				+ finalsign + "\"";
		FinalPackage finalPackages = new FinalPackage();
		finalPackages.setAppId(appid);
		finalPackages.setTimeStamp(timestamp);
		finalPackages.setNonceStr(nonce_str);
		finalPackages.setPackages(packages);
		finalPackages.setSignType("MD5");
		finalPackages.setPaySign(finalsign);

		System.out.println("V3 jsApi package:" + finaPackage);
		return finalPackages;
	}

	/**
	 * APP微信支付 获取请求预支付id报文以及最终调起支付请求包
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static AppPackage getAppPackage(WxPayDto tpWxPayDto) {

		// 1 参数
		// 附加数据 原样返回
		String attach = tpWxPayDto.getAttach();
		// 总金额以分为单位，不带小数点
		String totalFee = WeChatUtils.getMoney(tpWxPayDto.getTotalFee());

		// 订单生成的机器 IP
		String spbill_create_ip = "127.0.0.1";
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = notifyurl;
		String trade_type = "APP";// app支付

		// ---必须参数

		// 随机字符串
		String nonce_str = WeChatUtils.getNonceStr();

		// 商品描述根据情况修改
		String body = "数字地球微分金-" + tpWxPayDto.getBody();

		// 商户订单号
		String out_trade_no = tpWxPayDto.getOrderId();

		String appid = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID");
		String appsecret = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appSecret");
		String partnerkey = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("partnerkey");
		String mch_id = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("mchid");

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		 packageParams.put("attach",attach);
		packageParams.put("body", body);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("notify_url", notify_url);
		packageParams.put("out_trade_no", out_trade_no);// 订单号
		packageParams.put("spbill_create_ip", spbill_create_ip);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("trade_type", trade_type);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);

		StringBuilder sb = new StringBuilder();

		sb.append("<xml>");
		sb.append("<appid>" + appid + "</appid>");
		sb.append("<attach>"+attach+"</attach>");
		sb.append("<body><![CDATA[" + body + "]]></body>");
		sb.append("<mch_id>" + mch_id + "</mch_id>");
		sb.append("<nonce_str>" + nonce_str + "</nonce_str>");
		sb.append("<notify_url>" + notify_url + "</notify_url>");
		sb.append("<out_trade_no>" + out_trade_no + "</out_trade_no>");
		sb.append("<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>");
		sb.append("<total_fee>" + totalFee + "</total_fee>");
		sb.append("<trade_type>" + trade_type + "</trade_type>");
		sb.append("<sign>" + sign + "</sign>");
		sb.append("</xml>");

		String xml = sb.toString();

		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		WxPayDto pto = new GetWxOrderno().getPayNo(createOrderURL, xml);

		System.out.println("App微信支付获取到的预支付ID：" + pto.getPrepay_id());

		// 获取prepay_id后，拼接最后请求支付所需要的package
		String finalsign = "";
		String noncestr = WeChatUtils.getNonceStr();
		String timestamp = Sha1Util.getTimeStamp();
		if (!pto.getPrepay_id().equals("")) {
			SortedMap<String, String> finalpackage = new TreeMap<String, String>();

			finalpackage.put("appid", appid);
			finalpackage.put("noncestr", noncestr);
			finalpackage.put("package", "Sign=WXPay");
			finalpackage.put("partnerid", mch_id);
			finalpackage.put("timestamp", timestamp);
			finalpackage.put("prepayid", pto.getPrepay_id());

			// 要签名
			finalsign = reqHandler.createSign(finalpackage);
			System.out.println("finalsign:" + finalsign);
		}
		AppPackage appPackage = new AppPackage();
		appPackage.setAppid(appid);
		appPackage.setPartnerid(mch_id);
		appPackage.setPrepayid(pto.getPrepay_id());
		appPackage.setPackages("Sign=WXPay");
		appPackage.setNoncestr(noncestr);
		appPackage.setTimestamp(timestamp);
		appPackage.setSign(finalsign);
		appPackage.setErr_code(pto.getErr_code());
		

		return appPackage;
	}

	/**
	 * 
	 * 
	 * 公众账号获取微信对账单
	 * 
	 * @param stateReq
	 * @param tpWxPayDto
	 * @return
	 */
	public static String getWechatStatement(StatementReq stateReq, WxPayDto tpWxPayDto) {

		/*
		 * String appid =
		 * Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID"); String
		 * appsecret =
		 * Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appSecret");
		 * String partnerkey =
		 * Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("partnerkey");
		 * String mchid =
		 * Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("mchid");
		 */

		String appid = "wxff4c5683480d6664";
		String appsecret = "89b9ff8d2c921fe5511b8ae0da6b060f";
		String partnerkey = "ttsw123321456mzttst890349liutaip";
		String mchid = "1238821802";

		String billdate = "20150928";

		String billtype = "ALL";

		String noncestr = WeChatUtils.getNonceStr();// 随机数

		String device_info = "";

		SortedMap<String, String> reqParams = new TreeMap<String, String>();
		reqParams.put("appid", appid);
		reqParams.put("bill_date", billdate);
		reqParams.put("bill_type", billtype);
		reqParams.put("mch_id", mchid);
		reqParams.put("nonce_str", noncestr);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(reqParams);

		String xml = "<xml><appid>" + appid + "</appid>" + "<bill_date>" + billdate + "</bill_date>" + "<bill_type>"
				+ billtype + "</bill_type>" + "<mch_id>" + mchid + "</mch_id>" + "<nonce_str>" + noncestr
				+ "</nonce_str>" + "<sign>" + sign + "</sign>" + "</xml>";

		String createStateURL = "https://api.mch.weixin.qq.com/pay/downloadbill";
		new GetWxOrderno().getWxStatement(createStateURL, xml);
		return null;

	}

	/**
	 * 
	 * 
	 * 公众账号退款申请
	 * 
	 * @param tpWxPayDto
	 * @return
	 */
	public static WxPayDto wechatRefund(WxPayDto tpWxPayDto) {
		String out_refund_no = tpWxPayDto.getRefundno();// 退款单号
		String out_trade_no = tpWxPayDto.getOrderId();// 商户订单号
		String total_fee = WeChatUtils.getMoney(tpWxPayDto.getTotalFee());// 总金额
		String refund_fee = WeChatUtils.getMoney(tpWxPayDto.getRefundfee());// 退款金额
		String nonce_str = WeChatUtils.getNonceStr();
		// 随机字符串
		String appid = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID");
		String appsecret = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appSecret");
		String partnerkey = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("partnerkey");// 商户平台上的那个KEY
		String mch_id = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("mchid");
		String op_user_id = mch_id;// 就是MCHID

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);

		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<op_user_id>" + op_user_id + "</op_user_id>" + "<out_refund_no>"
				+ out_refund_no + "</out_refund_no>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>" + "<total_fee>" + total_fee + "</total_fee>"
				+ "<transaction_id></transaction_id>" + "<sign><![CDATA[" + sign + "]]></sign></xml>";

		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String s = "";
		WxPayDto payDto = null;
		String certPath="";
		if("apppay".equals(tpWxPayDto.getWechatbz())){
			certPath="D:/certwx/app/apiclient_cert.p12";
		}else {
			certPath="D:/certwx/gzh/apiclient_cert.p12";
		}
		try {
            System.out.println("退款开始");
			s = ClientCustomSSL.doRefund(createOrderURL, xml, mch_id,certPath);
            System.out.println("退款结束");
			//查询退款订单申请详情
            System.out.println("查询开始");
			payDto = searchOrder(out_trade_no,tpWxPayDto.getWechatbz());
            System.out.println("查询结束");

		} catch (Exception e) {
			System.out.println(e);
		}

		return payDto;
	}

	/**
	 * 
	 * 查询微信订单
	 * 
	 * @return
	 */
	public static WxPayDto searchServOrder(String out_trade_no,String sub_appid,String sub_mch_id) {
		String appid = Constant.WXSP_APPID;
		String mch_id = Constant.WXSP_Mchid;
		String appsecret = Constant.WXSP_AppSecret;
		String partnerkey =Constant.WXSP_Partnerkey;

		if(sub_appid==null||sub_appid.equals("")){
			sub_appid = Constant.WXSP_SUBAPPID;
		}
		if(sub_mch_id==null||sub_mch_id.equals("")){
			sub_mch_id = Constant.WXSP_SUBMchid;
		}
		String nonce_str = WeChatUtils.getNonceStr();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("sub_appid", sub_appid);

		packageParams.put("mch_id", mch_id);
		packageParams.put("sub_mch_id", sub_mch_id);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("nonce_str", nonce_str);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);

		StringBuilder sb = new StringBuilder();

		sb.append("<xml>");
		sb.append("<appid>" + appid + "</appid>");
		sb.append("<sub_appid>" + sub_appid + "</sub_appid>");
		sb.append("<mch_id>" + mch_id + "</mch_id>");
		sb.append("<sub_mch_id>" + sub_mch_id + "</sub_mch_id>");
		sb.append("<nonce_str>" + nonce_str + "</nonce_str>");
		sb.append("<out_trade_no>" + out_trade_no + "</out_trade_no>");
		sb.append("<sign>" + sign + "</sign>");
		sb.append("</xml>");
		String xml = sb.toString();

		String createSearchURL = "https://api.mch.weixin.qq.com/pay/orderquery";

		WxPayDto payDto = new GetWxOrderno().getSearchResult(createSearchURL, xml);

		return payDto;
	}




	/**
	 *
	 * 服务商模式查询订单
	 *
	 * @return
	 */
	public static WxPayDto searchOrder(String out_trade_no, String weixinbz) {
		String appid = Constant.wechatMap.get(weixinbz).get("appID");
		String mch_id = Constant.wechatMap.get(weixinbz).get("mchid");
		String appsecret = Constant.wechatMap.get(weixinbz).get("appSecret");
		String partnerkey = Constant.wechatMap.get(weixinbz).get("partnerkey");
		String nonce_str = WeChatUtils.getNonceStr();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("nonce_str", nonce_str);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);

		StringBuilder sb = new StringBuilder();

		sb.append("<xml>");
		sb.append("<appid>" + appid + "</appid>");
		sb.append("<mch_id>" + mch_id + "</mch_id>");
		sb.append("<nonce_str>" + nonce_str + "</nonce_str>");
		sb.append("<out_trade_no>" + out_trade_no + "</out_trade_no>");
		sb.append("<sign>" + sign + "</sign>");
		sb.append("</xml>");
		String xml = sb.toString();

		String createSearchURL = "https://api.mch.weixin.qq.com/pay/orderquery";

		WxPayDto payDto = new GetWxOrderno().getSearchResult(createSearchURL, xml);

		return payDto;
	}
	
	/**
	 * 获取微信扫码支付二维码连接
	 * @param tpWxPayDto 微信支付请求参数类
	 * @return 链接参数
	 * @throws Exception 
	 */
	public static String getCodeurl(WxPayDto tpWxPayDto) throws Exception
	{
		// 公众账号ID(必填)
		String appid = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("appID");
		// 商户号(必填)
		String mch_id = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("mchid");
		// API密钥
		String key = Constant.wechatMap.get(tpWxPayDto.getWechatbz()).get("partnerkey");
		// 随机字符串(必填)
		String nonce_str = getNonceStr();
		// 商品描述根据情况修改(必填)
		String body = tpWxPayDto.getBody();
		//附加数据可作为自定义参数(非必填)
		String attach = tpWxPayDto.getAttach();
		// 商户订单号(必填)
		String out_trade_no = tpWxPayDto.getOrderId();
		// 总金额以分为单位，不带小数点(必填)
		String total_fee = getMoney(tpWxPayDto.getTotalFee());
		// 订单生成的机器 IP(必填)
		String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。(必填)
		String notify_url = tpWxPayDto.getNotifyUrl();
		// 交易类型(必填)，固定值NATIVE(扫码支付)
		String trade_type = "NATIVE";
		// 创建请求参数集合
		Map<String,String> data =new HashMap<String,String>();
		data.put("appid", appid);
		data.put("mch_id", mch_id);
		data.put("nonce_str", nonce_str);
		data.put("body", body);
		data.put("attach", attach);
		data.put("out_trade_no", out_trade_no);
		data.put("total_fee", total_fee);
		data.put("spbill_create_ip", spbill_create_ip);
		data.put("notify_url", notify_url);
		data.put("trade_type", trade_type);
		String sign = RequestHandler.generateSignature(data, key);
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("<xml>");
		strbuilder.append("<appid>").append(appid).append("</appid>");
		strbuilder.append("<mch_id>").append(mch_id).append("</mch_id>");
		strbuilder.append("<nonce_str>").append(nonce_str).append("</nonce_str>");
		strbuilder.append("<sign>").append(sign).append("</sign>");
		strbuilder.append("<body>").append(body).append("</body>");
		strbuilder.append("<attach>").append(attach).append("</attach>");
		strbuilder.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
		strbuilder.append("<total_fee>").append(total_fee).append("</total_fee>");
		strbuilder.append("<spbill_create_ip>").append(spbill_create_ip).append("</spbill_create_ip>");
		strbuilder.append("<notify_url>").append(notify_url).append("</notify_url>");
		strbuilder.append("<trade_type>").append(trade_type).append("</trade_type>");
		strbuilder.append("</xml>");
		String code_url = GetWxOrderno.getCodeUrl(WchatPay.createOrderURL, strbuilder.toString());
		return code_url;
	}
	
	/**
	 * 获取随机字符串
	 * @return
	 */
	private static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
	
	/**
	 * 元转换成分
	 * @param amount
	 * @return
	 */
	private static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");
        Long amLong = 0l;
		int length = currency.length();
		if(index == -1){
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}

	public static void main(String[] args) {

		 WxPayDto tpWxPayDto = new WxPayDto();

		String refundno = "2017070705271000106";
		String orderId = "2017070705214700098";
		String totalFee = "0.1";
		String refundfee = "0.1";
		String wfStatus1 = "01";

		tpWxPayDto.setRefundno(refundno);// 退款单号
		tpWxPayDto.setOrderId(orderId);// 商户订单号  测试数据：
		tpWxPayDto.setTotalFee(totalFee);// 总金额 0.1
		tpWxPayDto.setRefundfee(refundfee);// 退款金额0.1

		//判断微信支付方式 00微信公众号支付 01微信app支付
		if ("00".equals(wfStatus1))
		{
			tpWxPayDto.setWechatbz("wxa1b3f84c027804c3");
		}
		else if ("01".equals(wfStatus1))
		{
			tpWxPayDto.setWechatbz("apppay");
		}

      try {
		  WxPayDto payDto = WchatPay.wechatRefund(tpWxPayDto);

		if ("SUCCESS".equals(payDto.getReturn_code()) && "OK".equals(payDto.getReturn_msg()))
		{
			System.out.print("suc");
		}
		else
		{
			System.out.print("fail");
		}
	  }catch (Exception e){
		  e.printStackTrace();
	  }

//		 tpWxPayDto.setRefundno("2017052210355800007");// 退款单号
//		 tpWxPayDto.setOrderId("2017052210334400005");// 商户订单号 测试数据：
//		 tpWxPayDto.setTotalFee("0.01");// 总金额 0.1
//		 tpWxPayDto.setRefundfee("0.01");// 退款金额0.1
//		 tpWxPayDto.setWechatbz("apppay");
//		 WxPayDto payDto= WchatPay.wechatRefund(tpWxPayDto);
//		 System.out.print(payDto.getReturn_msg() + "----" + payDto.getReturn_code());
//		 searchOrder("2017052210334400005","apppay");

	}

}
