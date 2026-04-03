package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 *  一个商户绑定多个设备
 */
public class BindDevice implements BaseBean,Serializable {
    private String bdKey;
    private String bdID;
	private String subCompanyID;//系统中的ID


    private String sn;  //设备序列号
	private String pfdID;//设备ID

	private String bindID;//绑定人ID
	private String bindName;//绑定人姓名
	private Date bindDate;//绑定日期


	public String getBdKey() {
		return bdKey;
	}

	public void setBdKey(String bdKey) {
		this.bdKey = bdKey;
	}

	public String getBdID() {
		return bdID;
	}

	public void setBdID(String bdID) {
		this.bdID = bdID;
	}

	public String getSubCompanyID() {
		return subCompanyID;
	}

	public void setSubCompanyID(String subCompanyID) {
		this.subCompanyID = subCompanyID;
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

	public String getBindID() {
		return bindID;
	}

	public void setBindID(String bindID) {
		this.bindID = bindID;
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
}
