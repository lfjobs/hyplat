package com.tiantai.nwa.tbank.bo;

import hy.plat.bo.BaseBean;





public class TransConf implements BaseBean {

	private String key;
	private String confID;
	private String enameSx;
	private String enterpriseID;
	private String transName;
	private String wbatransCode;//网银
	private String btransCode;//银行
	private String ver;
	private String verDesc;
	private String isUsed;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getConfID() {
		return confID;
	}
	public void setConfID(String confID) {
		this.confID = confID;
	}
	public String getEnameSx() {
		return enameSx;
	}
	public void setEnameSx(String enameSx) {
		this.enameSx = enameSx;
	}
	public String getEnterpriseID() {
		return enterpriseID;
	}
	public void setEnterpriseID(String enterpriseID) {
		this.enterpriseID = enterpriseID;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getWbatransCode() {
		return wbatransCode;
	}
	public void setWbatransCode(String wbatransCode) {
		this.wbatransCode = wbatransCode;
	}
	public String getBtransCode() {
		return btransCode;
	}
	public void setBtransCode(String btransCode) {
		this.btransCode = btransCode;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getVerDesc() {
		return verDesc;
	}
	public void setVerDesc(String verDesc) {
		this.verDesc = verDesc;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

}
