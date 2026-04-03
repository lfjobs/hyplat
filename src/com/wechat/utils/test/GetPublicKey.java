package com.wechat.utils.test;

import java.util.SortedMap;
import java.util.TreeMap;

public class GetPublicKey {
	public String getPublicKey() throws Exception {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();

		String nonce_str = StringUtils1.getStrRandom(28);// 获取随机字符串

		parameters.put("mch_id", WChatInfo.MCH_ID);

		parameters.put("nonce_str", nonce_str);

		parameters.put("sign_type", "MD5");

		// 创建签名
		String sign = SignUtils.creatSign(WChatInfo.CHARSET, parameters);

		TreeMap<String, String> tmap = new TreeMap<String, String>();

		tmap.put("mch_id", WChatInfo.MCH_ID);

		tmap.put("nonce_str", nonce_str);

		tmap.put("sign_type", "MD5");

		tmap.put("sign", sign);

		String xml = XMLUtils.getRequestXml(tmap);// 将请求参数转换为请求报文

		// 带证书请求
		String xml1 = HttpClientCustomSSL.httpClientResultGetPublicKey(xml);// 发送http的post请求获取公钥报文

		String publicKey = XMLUtils.Progress_resultParseXml(xml1);// 解析腾迅返回的公钥xml并获取公钥元素

		return publicKey;
	}
	
	public static void main(String[] args) {
		GetPublicKey  getkeu = new GetPublicKey();
		String publicKey = "";
		try {
			publicKey = getkeu.getPublicKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(publicKey);
	}

}
