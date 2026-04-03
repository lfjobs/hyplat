package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 商户绑定设备历史
 */
public class StoreDeviceHistory implements BaseBean{
    private String sdhKey;
    private String sdhID;
    private String storeName;//特约商户名称
	private String subCompanyID;//系统中的ID
	private String storeID;//特约商户ID 32位数
	private String subAppID;//特约商户APPID
	private String subMchid;//特约商户号

    private String sn;  //设备序列号
	private String pfdID;//设备ID

	private Date bindDate;//绑定日期
	private Date removeDate;//解绑日期

	private String removeID;//解绑人ID
	private String removeName;//解绑人姓名



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

	public String getRemoveID() {
		return removeID;
	}

	public void setRemoveID(String removeID) {
		this.removeID = removeID;
	}

	public String getRemoveName() {
		return removeName;
	}

	public void setRemoveName(String removeName) {
		this.removeName = removeName;
	}
}
