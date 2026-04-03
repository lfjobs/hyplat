package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户签约绑定设备
 */
public class StoreBindDevice implements BaseBean,Serializable {
    private String sbdKey;
    private String sbdID;
    private String storeName;//特约商户名称
	private String subCompanyID;//系统中的ID
	private String storeID;//特约商户ID 32位数
	private String subAppID;//特约商户APPID
	private String subMchid;//特约商户号
	private String storeRate;//费率
	private Date changeRateDate; //调整时间
	private String companyID;//维护商户信息ID
	private String staffID;//负责人
	private String staffName;//负责人姓名
	private String inputID;//录入人员
	private String inputName;//录入人员姓名
	private Date createDate;  //录入时间


	public String getSbdKey() {
		return sbdKey;
	}

	public void setSbdKey(String sbdKey) {
		this.sbdKey = sbdKey;
	}

	public String getSbdID() {
		return sbdID;
	}

	public void setSbdID(String sbdID) {
		this.sbdID = sbdID;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSubCompanyID() {
		return subCompanyID;
	}

	public void setSubCompanyID(String subCompanyID) {
		this.subCompanyID = subCompanyID;
	}

	public String getStoreID() {
		return storeID;
	}

	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	public String getSubAppID() {
		return subAppID;
	}

	public void setSubAppID(String subAppID) {
		this.subAppID = subAppID;
	}

	public String getSubMchid() {
		return subMchid;
	}

	public void setSubMchid(String subMchid) {
		this.subMchid = subMchid;
	}

	public String getStoreRate() {
		return storeRate;
	}

	public void setStoreRate(String storeRate) {
		this.storeRate = storeRate;
	}

	public Date getChangeRateDate() {
		return changeRateDate;
	}

	public void setChangeRateDate(Date changeRateDate) {
		this.changeRateDate = changeRateDate;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getInputID() {
		return inputID;
	}

	public void setInputID(String inputID) {
		this.inputID = inputID;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
