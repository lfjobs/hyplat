package com.batch.chinapay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chinapay.SecureLink;

/**
 *签名验签方法
 * 
 * @author guobin
 * 
 */
public class SignData {
	private static final Logger logger = LoggerFactory.getLogger(SignData.class);

	/**
	 *根据商户ID，待签名数据，调用签名API得到签名串(该签名串用户发送给CP)
	 * 
	 * @param merId
	 * @param data
	 * @return
	 */
	public String signForCP(String merId, String data, String path) {
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		chinapay.SecureLink t;
		boolean flag;
		String ChkValue2;
		//得到商户私钥存放路径
		if (path == null || "".equals(path)) {
			logger.info("can't find the PrivateKey path!");
			return null;
		}
		logger.info("path=[{}{}", path, "]");
		//初始化，签名
		flag = key.buildKey(merId, 0, path);
		if (flag == false) {
			logger.info("build key error! merId={}{}{}", merId, " path=", path);
			return null;
		}
		t = new chinapay.SecureLink(key);
		ChkValue2 = t.Sign(data); // Value2为签名后的字符串
		return ChkValue2;
	}

	/**
	 * 通过CP公钥，验证CP传回的数据签名
	 * 
	 * @param bean
	 * @param ChkValue2
	 * @return
	 */
	public boolean verifyForCP(String merId, String data, String chkValue, String path) {
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		boolean flag;
		// 得到CP公钥存放路径
		if (path == null || "".equals(path)) {
			logger.info("can't find the PubKey path!");
			return false;
		}
		logger.info("path=[{}{}", path, "]");
		//  初始化，验签
		flag = key.buildKey(merId, 0, path);
		if (!flag) {
			logger.info("verifyForCP:生成公钥失败!merId={}{}{}", merId, ",path=", path);
			return false;
		}
		SecureLink t = new chinapay.SecureLink(key);
		boolean flag1 = t.verifyAuthToken(data, chkValue); // chkValue为ChinaPay应答传回的域段
		if (!flag1) {
			//签名验证错误处理
			System.out.println("verifyForCP error!data=" + data + ",chkValue="
					+ chkValue);
			return false;
		}
		return true;
	}
}
