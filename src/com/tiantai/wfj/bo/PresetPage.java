package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 电子秤预置键信息
 * @author Administrator
 *
 */
public class PresetPage implements BaseBean {

	private String scpKey;
	private String scpId;
	private String preCode;//编号
	private String keyCount;//键数
	private String staffID;//责任ID
	private String staffname;//责任人姓名
	private String  companyID;//公司ID
	private Date cdate;//日期

	public String getScpKey() {
		return scpKey;
	}

	public void setScpKey(String scpKey) {
		this.scpKey = scpKey;
	}

	public String getScpId() {
		return scpId;
	}

	public void setScpId(String scpId) {
		this.scpId = scpId;
	}

	public String getPreCode() {
		return preCode;
	}

	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}

	public String getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(String keyCount) {
		this.keyCount = keyCount;
	}


	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
}
