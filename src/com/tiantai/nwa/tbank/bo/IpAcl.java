package com.tiantai.nwa.tbank.bo;

import hy.plat.bo.BaseBean;




public class IpAcl implements BaseBean {

	private String key;
	private String ipcID;
	private String enterpriseID;
	private String ipAddr;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getIpcID() {
		return ipcID;
	}
	public void setIpcID(String ipcID) {
		this.ipcID = ipcID;
	}
	public String getEnterpriseID() {
		return enterpriseID;
	}
	public void setEnterpriseID(String enterpriseID) {
		this.enterpriseID = enterpriseID;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
}
