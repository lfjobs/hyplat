package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *  调整商户费率保留历史记录
 */
public class StoreRateHistory implements BaseBean{

	private String sdhKey;
	private String sdhID;
    private String subCompanyID;
    private String storeID;
    private String storeName;

    private String subAppID;//特约商户APPID
	private String subMchid;//特约商户号

	private String storeRate;//费率
	private Date changeRateDate; //绑定时间
	private Date changeDate;//调整日期
	private String changeName;//调整人姓名
	private String changeID;//调整人

	public String getSdhKey() {
		return sdhKey;
	}

	public void setSdhKey(String sdhKey) {
		this.sdhKey = sdhKey;
	}

	public String getSdhID() {
		return sdhID;
	}

	public void setSdhID(String sdhID) {
		this.sdhID = sdhID;
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getChangeName() {
		return changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

	public String getChangeID() {
		return changeID;
	}

	public void setChangeID(String changeID) {
		this.changeID = changeID;
	}
}
