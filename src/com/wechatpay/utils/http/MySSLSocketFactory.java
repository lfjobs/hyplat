package com.wechatpay.utils.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class MySSLSocketFactory extends SSLSocketFactory{
	
	static{
		mySSLSocketFactory = new MySSLSocketFactory(createSContext());
	}
	
	private static MySSLSocketFactory mySSLSocketFactory = null;
	
	
	
	private static SSLContext createSContext(){
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e) {
			logger.error("操作异常", e);
		}
		try {
			sslcontext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, null);
		} catch (KeyManagementException e) {
			logger.error("操作异常", e);
			return null;
		}
		return sslcontext;
	}
	
	private MySSLSocketFactory(SSLContext sslContext) {
		super(sslContext);
		this.setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
	}
	
	public static MySSLSocketFactory getInstance(){
		if(mySSLSocketFactory != null){
			return mySSLSocketFactory;
		}else{
			return mySSLSocketFactory = new MySSLSocketFactory(createSContext());
		}
	}
	
}