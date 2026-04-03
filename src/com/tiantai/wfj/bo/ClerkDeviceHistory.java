package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 设备和业务员绑定历史记录
 */
public class ClerkDeviceHistory  implements BaseBean{

	private String cdhKey;
	private String cdhID;
	private String sn;//设备序列号
	private String pfdID;
	private String clerkID; //业务员ID
	private String clerkName;//业务员姓名
	private String clerkSccid;//业务员微分金账号ID
	private String clearkAccount;//业务员微分金账号手机号
	private Date bindDate; //绑定时间
	private Date removeDate;//解除绑定时间
	private String removeName;//解除绑定人员姓名
	private String removeID;//解除绑定人员ID

	public String getCdhKey() {
		return cdhKey;
	}

	public void setCdhKey(String cdhKey) {
		this.cdhKey = cdhKey;
	}

	public String getCdhID() {
		return cdhID;
	}

	public void setCdhID(String cdhID) {
		this.cdhID = cdhID;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPfdID() {
		return pfdID;
	}

	public void setPfdID(String pfdID) {
		this.pfdID = pfdID;
	}

	public String getClerkID() {
		return clerkID;
	}

	public void setClerkID(String clerkID) {
		this.clerkID = clerkID;
	}

	public String getClerkName() {
		return clerkName;
	}

	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}

	public String getClerkSccid() {
		return clerkSccid;
	}

	public void setClerkSccid(String clerkSccid) {
		this.clerkSccid = clerkSccid;
	}

	public String getClearkAccount() {
		return clearkAccount;
	}

	public void setClearkAccount(String clearkAccount) {
		this.clearkAccount = clearkAccount;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}

	public String getRemoveName() {
		return removeName;
	}

	public void setRemoveName(String removeName) {
		this.removeName = removeName;
	}

	public String getRemoveID() {
		return removeID;
	}

	public void setRemoveID(String removeID) {
		this.removeID = removeID;
	}
}
