package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/*
 *  免审核车辆
 * */
public class CarFeeAudit implements BaseBean{
	
	private String cfID;//主键
	private String cfkey;
	private String carNumber;//车牌号
	private Date createdate;//添加时间

	private String staffID;//提交人
	private String staffName;//提交人姓名
	private String siteId;//场地ID
	private String siteName;//场地名称
	private String companyID;//公司ID

	public String getCfID() {
		return cfID;
	}

	public void setCfID(String cfID) {
		this.cfID = cfID;
	}

	public String getCfkey() {
		return cfkey;
	}

	public void setCfkey(String cfkey) {
		this.cfkey = cfkey;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}
