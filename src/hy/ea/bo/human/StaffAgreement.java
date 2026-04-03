/**
 * Contact
 */
package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 合同管理
 * @author Administrator
 *
 */
public class StaffAgreement implements BaseBean{
	private String agreementKey;
	private String agreementID;
	private String companyID;
	private String staffID; //用于人事合同档案是哪个员工的\
	private String contractCode;//合同编号
	private Date contractSignDate;//合同签订日期
	private Date relieveContractDate;//终止合同日期
	private Date renewalDate;//解除日期；
	private String attachUrl;//附件路劲
	private String status;//合同类型
	private String remark;//备注
	
	
	
	public String getAgreementKey() {
		return agreementKey;
	}
	public void setAgreementKey(String agreementKey) {
		this.agreementKey = agreementKey;
	}
	public String getAgreementID() {
		return agreementID;
	}
	public void setAgreementID(String agreementID) {
		this.agreementID = agreementID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Date getContractSignDate() {
		return contractSignDate;
	}
	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}
	public Date getRelieveContractDate() {
		return relieveContractDate;
	}
	public void setRelieveContractDate(Date relieveContractDate) {
		this.relieveContractDate = relieveContractDate;
	}
	public Date getRenewalDate() {
		return renewalDate;
	}
	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}
	public String getAttachUrl() {
		return attachUrl;
	}
	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
