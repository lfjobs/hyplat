package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

public class TemplateParams implements BaseBean {

	private String tpKey;
	private String tpId;
	private String docID;
	private String companyName;//公司名称
	private String companyAddr;//公司地址
	private String companyTel;//公司电话
	private String staffName;//学员姓名
	private String reference;//学员电话
	private String staffIdentityCard;//学员身份证
	private String referrerAddress;//学员住址
	private String subjectType;//报考驾照
	private String productName;//报名产品
	private String money;//报名金额
	private String dmoney;//报名金额大写
	private String year;//签订日期年
	private String month;//签订日期年
	private String days;//签订日期年
	private String validateYear;//3年
	private String lldate;//理论学时C1，C2 12
	private String scdate;//实操学时 C1 30 C2 27
	private String cnxsite;//场内训练地点 陆家训练场
	private String site;//地址
	private String managementFees; //管理费
	private String operatingFee;  //操作费

	private String dmanagementFees;//大写管理费
	private String doperatingFee;//大写操作费
	private String operatingStaffName;//操作费收款人
	private String cashierBillsID;
	public String getTpKey() {
		return tpKey;
	}

	public void setTpKey(String tpKey) {
		this.tpKey = tpKey;
	}

	public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getDmoney() {
		return dmoney;
	}

	public void setDmoney(String dmoney) {
		this.dmoney = dmoney;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getReferrerAddress() {
		return referrerAddress;
	}

	public void setReferrerAddress(String referrerAddress) {
		this.referrerAddress = referrerAddress;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getValidateYear() {
		return validateYear;
	}

	public void setValidateYear(String validateYear) {
		this.validateYear = validateYear;
	}

	public String getStaffName() {
		return staffName;
	}

	public String getLldate() {
		return lldate;
	}

	public void setLldate(String lldate) {
		this.lldate = lldate;
	}

	public String getScdate() {
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
	}

	public String getCnxsite() {
		return cnxsite;
	}

	public void setCnxsite(String cnxsite) {
		this.cnxsite = cnxsite;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getOperatingStaffName() {
		return operatingStaffName;
	}

	public void setOperatingStaffName(String operatingStaffName) {
		this.operatingStaffName = operatingStaffName;
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

	public String getDoperatingFee() {
		return doperatingFee;
	}

	public void setDoperatingFee(String doperatingFee) {
		this.doperatingFee = doperatingFee;
	}

	public String getDmanagementFees() {
		return dmanagementFees;
	}

	public void setDmanagementFees(String dmanagementFees) {
		this.dmanagementFees = dmanagementFees;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
}
