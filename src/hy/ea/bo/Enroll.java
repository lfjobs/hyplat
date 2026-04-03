package hy.ea.bo;

import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import org.apache.commons.lang.StringUtils;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 报名
 * @author LG
 *
 */
public class Enroll implements BaseBean,Serializable{
	private static final long serialVersionUID = 1L;
	private String enrollKey;
	private String enrollID;
	private String staffId;
	private String ppID;
	private String ppName;
	private String coachStaffID; //教练人员id
	private String coachName; //教练名称
	private String suppoutName;
	private String directorName;
	private String suppoutStaffID;
	private String directorStaffID;
	private String companyID;
	private String companyName;
	private String siteName;
	private String licenceType;
	private String price;  //产品金额
	private String carNumber;
	private String siteID;
	private String status;
	private Date enrollDate;
	private String cashierBillsID;
	private String staffAddress;
	private String idCard;
	/*
	 * 如果学员没有推荐人，或者推荐人不符合要求，则
	 * reserved1 推荐人sccid
	 * reserved2 推荐人帐号
	 * reserved3 学员sccid
	 */
	private String reserved1;
	private String reserved2;
	private String reserved3;

	private String managementFees; //管理费
	private String operatingFee;  //操作费
	private String operatingStaffId;//操作费收款人id
	private String operatingStaffName;//操作费收款人
	private String operatingSccid;//操作费收款人sccid
	private String operatingAcount;//操作费收款人帐号
	private String payMethod; //支付方式 01:合并支付  021:分开支付-一次性结清  022：分开支付-计时收费



	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getEnrollKey() {
		return enrollKey;
	}

	public void setEnrollKey(String enrollKey) {
		this.enrollKey = enrollKey;
	}

	public String getEnrollID() {
		return enrollID;
	}

	public void setEnrollID(String enrollID) {
		this.enrollID = enrollID;
	}

	public String getPpID() {
		return ppID;
	}

	public void setPpID(String ppID) {
		this.ppID = ppID;
	}

	public String getCoachStaffID() {
		return coachStaffID;
	}

	public void setCoachStaffID(String coachStaffID) {
		this.coachStaffID = coachStaffID;
	}

	public String getSuppoutStaffID() {
		return suppoutStaffID;
	}

	public void setSuppoutStaffID(String suppoutStaffID) {
		this.suppoutStaffID = suppoutStaffID;
	}

	public String getDirectorStaffID() {
		return directorStaffID;
	}

	public void setDirectorStaffID(String directorStaffID) {
		this.directorStaffID = directorStaffID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getLicenceType() {
		return licenceType;
	}

	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getPpName() {
		return ppName;
	}

	public void setPpName(String ppName) {
		this.ppName = ppName;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getSuppoutName() {
		return suppoutName;
	}

	public void setSuppoutName(String suppoutName) {
		this.suppoutName = suppoutName;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getManagementFees() {
		return managementFees;
	}

	public void setManagementFees(String managementFees) {
		this.managementFees = managementFees;
	}

	public String getOperatingFee() {
		return operatingFee;
	}

	public void setOperatingFee(String operatingFee) {
		this.operatingFee = operatingFee;
	}

	public String getOperatingStaffId() {
		return operatingStaffId;
	}

	public void setOperatingStaffId(String operatingStaffId) {
		this.operatingStaffId = operatingStaffId;
	}

	public String getOperatingStaffName() {
		return operatingStaffName;
	}

	public void setOperatingStaffName(String operatingStaffName) {
		this.operatingStaffName = operatingStaffName;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getOperatingSccid() {
		return operatingSccid;
	}

	public void setOperatingSccid(String operatingSccid) {
		this.operatingSccid = operatingSccid;
	}

	public String getOperatingAcount() {
		return operatingAcount;
	}

	public void setOperatingAcount(String operatingAcount) {
		this.operatingAcount = operatingAcount;
	}
}
