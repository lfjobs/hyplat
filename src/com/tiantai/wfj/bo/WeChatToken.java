package com.tiantai.wfj.bo;



import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 * 微信 公众账号信息
 * @author mz
 *
 */
public class WeChatToken implements BaseBean{
	
	private String wctKey;
	private String wctID;
	private String appID;
	private String appSecret;
	private String accessToken;//微信接口唯一凭证
	private String companyID;//公司ID
	private Date accessTime;//获取时间
	private  String jsapi_ticket;
	
	
	public String getWctKey() {
		return wctKey;
	}
	public void setWctKey(String wctKey) {
		this.wctKey = wctKey;
	}
	public String getWctID() {
		return wctID;
	}
	public void setWctID(String wctID) {
		this.wctID = wctID;
	}
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public Date getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
}
