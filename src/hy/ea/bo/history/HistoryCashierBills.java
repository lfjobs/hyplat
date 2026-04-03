package hy.ea.bo.history;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 出纳单管理：CashierBills-History
 * 
 * @author 杨金歌
 * 
 */
public class HistoryCashierBills implements BaseBean {
	private String cashierBillsID;
	private String cashierBillsKey;
	private String pcompanyID;
	private String pcompanyName;//父公司name
	private String companyID;
	private String companyName;//当前公司name
	private String organizationID; //标示数据属于哪个部门
	private String departmentID;// 部门ID 填单选择的部门
	private String departmentName;//部门name 填单选择的部门
	private String companyBankNum;// 公司银行账号
	private Date cashierDate;// 单据日期
	private String PbillsTypeID;// 父单据类型id
	private String billsType;// 单据类型
	private String journalNum;// 凭证号
	private String input;  //录入人员 
	private String billCheck;//票据支票管理
	
	
	private Date taxDate;// 报税日期
	/**************单据状态****************/
	private String status;// 单据状态 '00'草稿 '01'待主管审核 '02'待会计审核 '03'待出纳审核 '04'已审核 
						 // '10' 驳回 '20' 转税务单据 '30' 未审核作废
	private String taxstatus;// 税务单据状态 '00'草稿 '01'待主管审核 '02'待经理审核
							// '03'待财务审计审核 '04' 已报税 '10' 驳回 
	private String taxstatus2;// 归档状态    '01'已归档
	/**************审批人****************/
	private String responsible;// 普通单据主管审核人
	private String accountant;// 普通单据会计审核人
	private String cashier;// 普通单据出纳审核人
	private String competent;// 税务单据主管审核人
	private String manager;// 税务单据经理审核人
	private String financial;// 税务单据财务审计审核人
	
	
	
	/**************责任人****************/
	private String staffID;// 责任人ID
	private String staffName;//责任人name
	private String staffCode;// 责任人编号
	/**************单据原仓库信息****************/
	private String discountMoney;// 实物仓库ID（当做库房ID）
	private String afterDiscount;// 实物仓库
	private String dataDepotID;// 资料仓库ID
	private String dataDepotName;// 资料仓库名称
	private String bankDepotID;// 银行仓库ID
	private String bankDepotName;// 银行仓库名称
	
	/**************往来个人****************/
	private String contactUserID;// 往来个人ID
	private String ctUserName;//往来个人name
	private String reference;//往来个人电话
	private String staffIdentityCard;// 身份证
	private String referenceCode;// qq
	private String referenceOrganization;// 邮箱
	private String staffAddress;//家庭地址路径
	private String phone; // 个人往来关系
	private String userAccountNum;// 个人银行账户
	
	/**************往来单位****************/
	private String contactConnections;// 公司往来关系
	private String ccompanyID;// 往来单位ID
	private String ccompanyName;//往来单位name
	private String companyAddr;         //具体地址
	private String companyTel;          //公司电话
	private String cresponsible;        //负责人
	private String responsibleTel;      //负责人电话
	private String industryType;        //行业类别
	private String accountNum;// 单位银行账户
	
	/** *******售前客户服务******* */
	private String consultStatus;  //咨询跟踪单状态  '00'草稿  '01'待营销审核  '02'待人事审核  '03'已审核  '10'驳回
	private String marketer;  //咨询跟踪单营销审核人
	private String personnel; //咨询跟踪单人事审核人
	
	private String snumber;  //归档号
	
	private String goodsCoding;//设备编号
	private String goodsName;//设备名称
	private String defaultStorage;//统一分类条码
	private String mnemonicCode;//设备品牌
	private String standard;//设备品牌规格
	private String typeID;//设备类型
	private String model;//设备型号
	private String variableID;//设备单位
	private String acquiescestandard;//默认规格
	private String pieces; //张数
	
	//------ 招生信息 ---------//
	private String assignsID;//分校id  公司子部门id
	private String assignsName;//分校名称 公司子部门名称
	private Date   signUpDate;//报名时间
	private String referrerName; //推荐人  当前公司在职人员   
	private String referrerID;//推荐ID
	private String referrerPhone; //推荐人电话
	private String referrerIdentityCard; //推荐人身份证
	private String referrerEmail;
	private String referrerAddress;
	
	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public String getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getAfterDiscount() {
		return afterDiscount;
	}

	public void setAfterDiscount(String afterDiscount) {
		this.afterDiscount = afterDiscount;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getCashierBillsKey() {
		return cashierBillsKey;
	}

	public void setCashierBillsKey(String cashierBillsKey) {
		this.cashierBillsKey = cashierBillsKey;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getContactUserID() {
		return contactUserID;
	}

	public void setContactUserID(String contactUserID) {
		this.contactUserID = contactUserID;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public Date getCashierDate() {
		return cashierDate;
	}

	public void setCashierDate(Date cashierDate) {
		this.cashierDate = cashierDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDataDepotID() {
		return dataDepotID;
	}

	public void setDataDepotID(String dataDepotID) {
		this.dataDepotID = dataDepotID;
	}

	public String getDataDepotName() {
		return dataDepotName;
	}

	public void setDataDepotName(String dataDepotName) {
		this.dataDepotName = dataDepotName;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getAccountant() {
		return accountant;
	}

	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getBankDepotID() {
		return bankDepotID;
	}

	public void setBankDepotID(String bankDepotID) {
		this.bankDepotID = bankDepotID;
	}

	public String getBankDepotName() {
		return bankDepotName;
	}

	public void setBankDepotName(String bankDepotName) {
		this.bankDepotName = bankDepotName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getUserAccountNum() {
		return userAccountNum;
	}

	public void setUserAccountNum(String userAccountNum) {
		this.userAccountNum = userAccountNum;
	}

	public String getContactConnections() {
		return contactConnections;
	}

	public void setContactConnections(String contactConnections) {
		this.contactConnections = contactConnections;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPcompanyID() {
		return pcompanyID;
	}

	public void setPcompanyID(String pcompanyID) {
		this.pcompanyID = pcompanyID;
	}

	/**
	 * 税务单据状态
	 * 
	 * @return
	 */
	public String getTaxstatus() {
		return taxstatus;
	}

	public void setTaxstatus(String taxstatus) {
		this.taxstatus = taxstatus;
	}

	/**
	 * 公司银行账号
	 * 
	 * @return
	 */
	public String getCompanyBankNum() {
		return companyBankNum;
	}

	public void setCompanyBankNum(String companyBankNum) {
		this.companyBankNum = companyBankNum;
	}


	/**
	 * 历史税务单据状态
	 * 
	 * @return
	 */
	public String getTaxstatus2() {
		return taxstatus2;
	}

	public void setTaxstatus2(String taxstatus2) {
		this.taxstatus2 = taxstatus2;
	}
	
	/**
	 * 单据录入人员
	 * @return
	 */
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * 税务单据主管审核人
	 * 
	 * @return
	 */
	public String getCompetent() {
		return competent;
	}

	public void setCompetent(String competent) {
		this.competent = competent;
	}

	/**
	 * 税务单据经理审核人
	 * 
	 * @param competent
	 */
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * 税务单据财务审计审核人
	 * 
	 * @return
	 */
	public String getFinancial() {
		return financial;
	}

	public void setFinancial(String financial) {
		this.financial = financial;
	}

	/**
	 * 报税日期
	 * 
	 * @return
	 */
	public Date getTaxDate() {
		return taxDate;
	}

	public void setTaxDate(Date taxDate) {
		this.taxDate = taxDate;
	}

	/**
	 * 咨询跟踪单营销审核人
	 * 
	 * @return
	 */
	public String getMarketer() {
		return marketer;
	}

	public void setMarketer(String marketer) {
		this.marketer = marketer;
	}

	/**
	 * 咨询跟踪单人事审核人
	 * 
	 * @return
	 */
	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	/**
	 * 咨询跟踪单状态
	 * @return
	 */
	public String getConsultStatus() {
		return consultStatus;
	}

	public void setConsultStatus(String consultStatus) {
		this.consultStatus = consultStatus;
	}

	public String getSnumber() {
		return snumber;
	}

	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}

	public String getBillCheck() {
		return billCheck;
	}

	public void setBillCheck(String billCheck) {
		this.billCheck = billCheck;
	}

	public String getGoodsCoding() {
		return goodsCoding;
	}

	public void setGoodsCoding(String goodsCoding) {
		this.goodsCoding = goodsCoding;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getDefaultStorage() {
		return defaultStorage;
	}

	public void setDefaultStorage(String defaultStorage) {
		this.defaultStorage = defaultStorage;
	}

	public String getMnemonicCode() {
		return mnemonicCode;
	}

	public void setMnemonicCode(String mnemonicCode) {
		this.mnemonicCode = mnemonicCode;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVariableID() {
		return variableID;
	}

	public void setVariableID(String variableID) {
		this.variableID = variableID;
	}

	public String getAcquiescestandard() {
		return acquiescestandard;
	}

	public void setAcquiescestandard(String acquiescestandard) {
		this.acquiescestandard = acquiescestandard;
	}

	public String getPieces() {
		return pieces;
	}

	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	public String getPbillsTypeID() {
		return PbillsTypeID;
	}

	public void setPbillsTypeID(String pbillsTypeID) {
		PbillsTypeID = pbillsTypeID;
	}

	public String getPcompanyName() {
		return pcompanyName;
	}

	public void setPcompanyName(String pcompanyName) {
		this.pcompanyName = pcompanyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCtUserName() {
		return ctUserName;
	}

	public void setCtUserName(String ctUserName) {
		this.ctUserName = ctUserName;
	}

	public String getCcompanyName() {
		return ccompanyName;
	}

	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
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

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getReferenceOrganization() {
		return referenceOrganization;
	}

	public void setReferenceOrganization(String referenceOrganization) {
		this.referenceOrganization = referenceOrganization;
	}

	
	public String getStaffAddress() {
		return staffAddress;
	}

	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
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

	public String getCresponsible() {
		return cresponsible;
	}

	public void setCresponsible(String cresponsible) {
		this.cresponsible = cresponsible;
	}

	public String getResponsibleTel() {
		return responsibleTel;
	}

	public void setResponsibleTel(String responsibleTel) {
		this.responsibleTel = responsibleTel;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getAssignsID() {
		return assignsID;
	}

	public void setAssignsID(String assignsID) {
		this.assignsID = assignsID;
	}

	public String getAssignsName() {
		return assignsName;
	}

	public void setAssignsName(String assignsName) {
		this.assignsName = assignsName;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerID() {
		return referrerID;
	}

	public void setReferrerID(String referrerID) {
		this.referrerID = referrerID;
	}

	public String getReferrerPhone() {
		return referrerPhone;
	}

	public void setReferrerPhone(String referrerPhone) {
		this.referrerPhone = referrerPhone;
	}

	public String getReferrerIdentityCard() {
		return referrerIdentityCard;
	}

	public void setReferrerIdentityCard(String referrerIdentityCard) {
		this.referrerIdentityCard = referrerIdentityCard;
	}

	public String getReferrerEmail() {
		return referrerEmail;
	}

	public void setReferrerEmail(String referrerEmail) {
		this.referrerEmail = referrerEmail;
	}

	public String getReferrerAddress() {
		return referrerAddress;
	}

	public void setReferrerAddress(String referrerAddress) {
		this.referrerAddress = referrerAddress;
	}
	
}
