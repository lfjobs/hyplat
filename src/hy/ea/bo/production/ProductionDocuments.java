package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

public class ProductionDocuments implements BaseBean{
	private String proDocumentsKey;
	private String proDocumentsID;
	private String companyID;							//公司ID
	private String companyName;					//公司名称
	private String cashierBillsID;						//关联订单ID
	private String goodsBillsID;						//物品单据ID
	private String proDate;								//单据时间
	private String batchNumber;					//批次号
	private String staffID;									//责任人ID
	private String staffName;							//责任人名称
	private String inputID;									//制单人ID
	private String inputName;							//制单人名称
	private String status;										//状态					00：生产中   01：转考核检验   02：检验完成，转成品库  03:检验失败
	private String type;										//单据类型			00：订单    01：计划
	private String category;								//类别    				00：单产品    01：组装产品
	private String fiveClear;								//组织机构
	private String superposition;						//检验失败时，是否可以返回继续组装   00：可以 01：不可以
	private String contactUserID;					//往来个人ID
	private String contactUserName;			//往来个人名称
	private String reference;								//往来个人电话
	
	
	public String getProDocumentsKey() {
		return proDocumentsKey;
	}
	public void setProDocumentsKey(String proDocumentsKey) {
		this.proDocumentsKey = proDocumentsKey;
	}
	public String getProDocumentsID() {
		return proDocumentsID;
	}
	public void setProDocumentsID(String proDocumentsID) {
		this.proDocumentsID = proDocumentsID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}
	public String getProDate() {
		return proDate;
	}
	public void setProDate(String proDate) {
		this.proDate = proDate;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getInputID() {
		return inputID;
	}
	public void setInputID(String inputID) {
		this.inputID = inputID;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContactUserID() {
		return contactUserID;
	}
	public void setContactUserID(String contactUserID) {
		this.contactUserID = contactUserID;
	}
	public String getContactUserName() {
		return contactUserName;
	}
	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getSuperposition() {
		return superposition;
	}
	public void setSuperposition(String superposition) {
		this.superposition = superposition;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	
}
