package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/*
 *  出入记录提交审核设置免费时长
 * */
public class CarManageAudit implements BaseBean{
	
	private String cmaID;//主键
	private String cmakey;
	private String carmID;//
	private String carNumber;//车牌号
	private Date indate;//免费开始时间
	private Date outdate;//免费截止时间
	private String status;//进 出
	private Date createDate;//提交审核时间
    private Date auditDate;//签章日期
	private String auditStatus;//审核结果    00 未提交审核，01 提交审核  02 人工审核通过 03 人工审核不通过
	private String staffID;//提交人
	private String posNum;//如果是终端机
	private String siteId;//场地ID
	private String siteName;//场地名称
	private String sealID;//签章人
	private String companyID;//公司

	public String getCmaID() {
		return cmaID;
	}

	public void setCmaID(String cmaID) {
		this.cmaID = cmaID;
	}

	public String getCmakey() {
		return cmakey;
	}

	public void setCmakey(String cmakey) {
		this.cmakey = cmakey;
	}

	public String getCarmID() {
		return carmID;
	}

	public void setCarmID(String carmID) {
		this.carmID = carmID;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
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

	public String getSealID() {
		return sealID;
	}

	public void setSealID(String sealID) {
		this.sealID = sealID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getPosNum() {
		return posNum;
	}

	public void setPosNum(String posNum) {
		this.posNum = posNum;
	}
}
