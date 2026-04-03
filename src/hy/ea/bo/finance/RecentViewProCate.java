package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * mz
 *  最近看过
 */
public class RecentViewProCate implements BaseBean {
	private String rvpKey;  //主键
	private String rvpID;
	private String codeID;  //分类ID
	private String codeValue;//分类
	private String ccompanyID;//行业平台对应的往来单位ID；
	private String staffID;//人员ID
	private Date viewDate; //浏览时间

	public String getRvpKey() {
		return rvpKey;
	}

	public void setRvpKey(String rvpKey) {
		this.rvpKey = rvpKey;
	}

	public String getRvpID() {
		return rvpID;
	}

	public void setRvpID(String rvpID) {
		this.rvpID = rvpID;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
}
