package com.wechatpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.wechatpay.bo.WxPayDto;
import com.wechatpay.utils.*;
import hy.ea.util.Constant;


/**
 * @author ex_yangxiaoyi
 * 
 */
public class Demo {
	private static final Logger logger = LoggerFactory.getLogger(Demo.class);
	//微信支付商户开通后 微信会提供appid和appsecret和商户号partner
	private static String appid = "";
	private static String appsecret = "";
	private static String partner = "";
	//这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	private static String partnerkey = "";
	//openId 是微信用户针对公众号的标识，授权的部分这里不解释
	private static String openId = "";
	//微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	private static String notifyurl = "";																	 // Key

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		//微信支付jsApi
//		WxPayDto tpWxPay = new WxPayDto();
//		tpWxPay.setOpenId(openId);
//		tpWxPay.setBody("商品信息");
//		tpWxPay.setOrderId(getNonceStr());
//		tpWxPay.setSpbillCreateIp("127.0.0.1");
//		tpWxPay.setTotalFee("0.01");
//	    getPackage(tpWxPay);
//
//	    //扫码支付
//	    WxPayDto tpWxPay1 = new WxPayDto();
//	    tpWxPay1.setBody("商品信息");
//	    tpWxPay1.setOrderId(getNonceStr());
//	    tpWxPay1.setSpbillCreateIp("127.0.0.1");
//	    tpWxPay1.setTotalFee("0.01");
//		getCodeurl(tpWxPay1);


		Map<String,String> params = Constant.wechatMap.get("wxa1b3f84c027804c3");




		// 1.0 拼凑企业支付需要的参数
		String appid = params.get("appID"); // 微信公众号的appid
		String mch_id = params.get("mchid"); // 商户号
		String nonce_str = WeChatUtils.getNonceStr(); // 生成随机数
//		String partner_trade_no = "11110098201411111234567890"; // 生成商户订单号
//		String openid = "oXoxsuBfaCSsG51mTowkIKefn1q8"; // 支付给用户openid
//		String check_name = "NO_CHECK"; // 是否验证真实姓名呢
//		String re_user_name = "KOLO"; // 收款用户姓名(非必须)
//		String amount = "10000"; // 企业付款金额，最少为100，单位为分
//		String desc = "提现"; // 企业付款操作说明信息。必填。
//		String spbill_create_ip ="127.0.0.1" ; // 用户的ip地址 request.getHeader("x-forwarded-for")
//
//		// 2.0 生成map集合
//		SortedMap<String, String> packageParams = new TreeMap<String, String>();
//		packageParams.put("mch_appid", appid); // 微信公众号的appid
//		packageParams.put("mchid", mch_id); // 商务号
//		packageParams.put("nonce_str", nonce_str); // 随机生成后数字，保证安全性
//
//		packageParams.put("partner_trade_no", partner_trade_no); // 生成商户订单号
//		packageParams.put("openid", openid); // 支付给用户openid
//		packageParams.put("check_name", check_name); // 是否验证真实姓名呢
//		packageParams.put("re_user_name", re_user_name);// 收款用户姓名
//		packageParams.put("amount", amount); // 企业付款金额，单位为分
//		packageParams.put("desc", desc); // 企业付款操作说明信息。必填。
//		packageParams.put("spbill_create_ip", spbill_create_ip); // 调用接口的机器Ip地址
//
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init("wxa1b3f84c027804c3", "26d569353c295fa8ad4fcb85a199f631", "bjttstkjyxgsdszltpwfjwxhapi12345");
//		SortedMap<String, String> data = new TreeMap<String, String>();


		SortedMap<String, String> packageParamsbyq = new TreeMap<String, String>();// 微信公众号的appid
		packageParamsbyq.put("mch_id", mch_id); // 商户号
		packageParamsbyq.put("partner_trade_no", "20171124331gang2333312"); // 随机生成后数字，保证安全性
		packageParamsbyq.put("nonce_str", nonce_str);
		String signbyq = reqHandler.createSign(packageParamsbyq);
		packageParamsbyq.put("sign", signbyq);
		String xmlbyq = null;
		try {
			xmlbyq = WeChatUtils.mapToXml(packageParamsbyq);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		String urlbyq = "https://api.mch.weixin.qq.com/mmpaysptrans/query_bank";
		logger.info("发送前的xml为：: {}", xmlbyq);
		String returnXmlbyq = WeChatUtils.postData(urlbyq, xmlbyq, "1250801501", "D:/apiclient_cert.p12");
		Map<String, String> returnMapbyq = WeChatUtils.parseXmlToList2(returnXmlbyq);
		logger.info("返回的returnXml为:: {}", returnXmlbyq);
		logger.info("返回的returnMap为:: {}", returnMapbyq);


		// 8，将微信返回的xml结果转成map格式
//		Map<String, String> returnMapbyq = WeChatUtils.parseXmlToList2(returnXmlbyq);
//		try {
//			// 3.0 利用上面的参数，先去生成自己的签名
//			String sign = reqHandler.createSign(packageParams);
//
//			// 4.0 将签名再放回map中，它也是一个参数
//			packageParams.put("sign", sign);
//
//			// 5.0将当前的map结合转化成xml格式
//			String xml = WeChatUtils.mapToXml(packageParams);
//
//			// 6.0获取需要发送的url地址
//			String wxUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; // 获取退款的api接口
//
//			logger.info("发送前的xml为：: {}", xml);
//
//			// 7,向微信发送请求转账请求
//			String returnXml = WeChatUtils.postData(wxUrl, xml, mch_id, "D:/apiclient_cert.p12");
//
//			logger.info("返回的returnXml为:: {}", returnXml);
//
//			// 8，将微信返回的xml结果转成map格式
//			Map<String, String> returnMap = WeChatUtils.parseXmlToList2(returnXml);
//
//			if (returnMap.get("return_code").equals("SUCCESS")) {
//				// 付款成功
//				logger.info("returnMap为:: {}", returnMap);
//
//			}

//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("操作异常", e);
//		}

	}
	
	/**
	 * 获取微信扫码支付二维码连接
	 */
	public static String getCodeurl(WxPayDto tpWxPayDto){
		
		// 1 参数
		// 订单号
		String orderId = tpWxPayDto.getOrderId();
		// 附加数据 原样返回
		String attach = "";
		// 总金额以分为单位，不带小数点
		String totalFee = getMoney(tpWxPayDto.getTotalFee());
		
		// 订单生成的机器 IP
		String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = notifyurl;
		String trade_type = "NATIVE";

		// 商户号
		String mch_id = partner;
		// 随机字符串
		String nonce_str = getNonceStr();

		// 商品描述根据情况修改
		String body = tpWxPayDto.getBody();

		// 商户订单号
		String out_trade_no = orderId;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + body + "]]></body>" 
				+ "<out_trade_no>" + out_trade_no
				+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
				+ "<total_fee>" + totalFee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "</xml>";
		String code_url = "";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		
		code_url = new GetWxOrderno().getCodeUrl(createOrderURL, xml);
		logger.info("调试信息");
		
		return code_url;
	}
	
	
	/**
	 * 获取请求预支付id报文
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getPackage(WxPayDto tpWxPayDto) {
		
		String openId = tpWxPayDto.getOpenId();
		// 1 参数
		// 订单号
		String orderId = tpWxPayDto.getOrderId();
		// 附加数据 原样返回
		String attach = "";
		// 总金额以分为单位，不带小数点
		String totalFee = getMoney(tpWxPayDto.getTotalFee());
		
		// 订单生成的机器 IP
		String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = notifyurl;
		String trade_type = "JSAPI";

		// ---必须参数
		// 商户号
		String mch_id = partner;
		// 随机字符串
		String nonce_str = getNonceStr();

		// 商品描述根据情况修改
		String body = tpWxPayDto.getBody();

		// 商户订单号
		String out_trade_no = orderId;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);

		// 这里写的金额为1 分到时修改
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openId);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + body + "]]></body>" 
				+ "<out_trade_no>" + out_trade_no
				+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
				+ "<total_fee>" + totalFee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openId + "</openid>"
				+ "</xml>";
		String prepay_id = "";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		
		
	/*	prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);*/

		logger.info("获取到的预支付ID：: {}", prepay_id);
		
		
		//获取prepay_id后，拼接最后请求支付所需要的package
		
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String packages = "prepay_id="+prepay_id;
		finalpackage.put("appId", appid);  
		finalpackage.put("timeStamp", timestamp);  
		finalpackage.put("nonceStr", nonce_str);  
		finalpackage.put("package", packages);  
		finalpackage.put("signType", "MD5");
		//要签名
		String finalsign = reqHandler.createSign(finalpackage);
		
		String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timestamp
		+ "\",\"nonceStr\":\"" + nonce_str + "\",\"package\":\""
		+ packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
		+ finalsign + "\"";

		logger.info("调试信息");
		return finaPackage;
	}

	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
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
	public static String getMoney(String amount) {
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

}
