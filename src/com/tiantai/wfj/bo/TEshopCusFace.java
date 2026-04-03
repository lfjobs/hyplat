package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

public class TEshopCusFace implements BaseBean,java.io.Serializable {//帐号表

	private String cusfKey;
	private String cusfId;
	private String sccId;
	private String account;//登录账号
	private String faceEntityId;

	public TEshopCusFace() {
	}

	public TEshopCusFace(String cusfKey, String cusfId, String sccId, String account, String faceEntityId) {
		this.cusfKey = cusfKey;
		this.cusfId = cusfId;
		this.sccId = sccId;
		this.account = account;
		this.faceEntityId = faceEntityId;
	}

	public String getCusfKey() {
		return cusfKey;
	}

	public void setCusfKey(String cusfKey) {
		this.cusfKey = cusfKey;
	}

	public String getCusfId() {
		return cusfId;
	}

	public void setCusfId(String cusfId) {
		this.cusfId = cusfId;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFaceEntityId() {
		return faceEntityId;
	}

	public void setFaceEntityId(String faceEntityId) {
		this.faceEntityId = faceEntityId;
	}
}