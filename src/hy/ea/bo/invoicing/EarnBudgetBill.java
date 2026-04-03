package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 收入预算附表 EarnBudgetBill
 * @author mz
 *
 */
public class EarnBudgetBill implements BaseBean,ExcelBean,java.io.Serializable{
	
	private String ebbKey;			        //主键
	private String ebbID;         			//业务主键
	private String groupCompanySn;     		//集团公司的标识  外键
	private String companyID;          		//公司  外键
	private String companyName;				//当前公司name
	private String organizationID;     		//单据所在部门  外键
	private String organizationName;
	
	private String companyBankNum;     		//公司银行账号
	private String pieces;
	private String billStatus;         		//单据状态  是否确认预算00未确认 01确认
	private String cstaffID;            	//往来个人ID  外键
	private String cstaffName;          	//往来个人名称
	private String cstaffRelationship;      //个人往来关系
	private String userAccountNum;			//往来个人银行帐号
	private String reference;				//往来个人电话
	private String staffIdentityCard;		// 身份证
	private String referenceCode;			// qq
	private String referenceOrganization;	// 邮箱
	private String staffAddress;			//家庭地址路径
	
	private String ccompanyID;         		//往来单位ID  外键
	private String ccompanyName;       		//往来单位名称	
	private String ccompanyRelationship;    //往来单位关系
	private String accountNum;				//往来公司银行账号
	private String companyAddr;         	//具体地址
	private String companyTel;          	//公司电话
	private String cresponsible;        	//负责人
	private String responsibleTel;      	//负责人电话
	private String industryType;        	//行业类别
	
	private String staffID;                 //限定部门内的责任人ID  外键
	private String staffName;               //责任人名称
	private String staffCode;				// 责任人编号
	
	private String billsType;                //单据类型
	private String billNum;               //凭证号（项目编号）
	private Date billsDate;                  //制单日期
	private String sdate;
	private String edate;
	
	private String billStaffID;              //制单人ID  外键
	private String billStaffName;            //制单人名称
	
	
	private String billComppanyID; //制单人公司ID
	private String billCompanyName;//制单人公司名称	
	private String billDeptID;//制单人部门ID
	private String billDeptName;//制单人部门名称
	
	
	private String year;//预算年份 
	
	private String budgetName;//预算名称
	
	private String sztype;//区分收入还是支出单子

	
	private String goodsCoding;//设备编号
	private String goodsName;//设备名称
	private String defaultStorage;//统一分类条码
	private String mnemonicCode;//设备品牌
	private String standard;//设备品牌规格
	private String typeID;//设备类型
	private String model;//设备型号
	private String variableID;//设备单位
	private String acquiescestandard;//默认规格
	
	
	/**************单据原仓库信息****************/
	private String discountMoney;// 实物仓库ID（当做库房ID）
	private String afterDiscount;// 实物仓库
	private String dataDepotID;// 资料仓库ID
	private String dataDepotName;// 资料仓库名称
	private String bankDepotID;// 银行仓库ID
	private String bankDepotName;// 银行仓库名称
	
	
	
	
	
	
	
	
	
	
	
	
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

	@Override
	public String[] properties() {
		String[] properties = { companyName, billNum, organizationName,
				staffName, billStaffName, String.format("%1$tF", billsDate),
				year, ccompanyName, accountNum, ccompanyRelationship,
				cstaffName, userAccountNum, cstaffRelationship, billStatus.equals("00") ? "草稿" : "已确认预算"};
		return properties;

	}
	
	public static String[] columnHeadings() {
	String[] titles = { "序号", "公司名称","黏贴单编号", "部门", "责任人","制单人", "制单日期","收入预算年份","往来单位","单位银行账号","单位往来关系","往来个人","个人银行账号","个人往来关系","状态"};
		return titles;
	}

	public String getEbbKey() {
		return ebbKey;
	}

	public void setEbbKey(String ebbKey) {
		this.ebbKey = ebbKey;
	}

	public String getEbbID() {
		return ebbID;
	}

	public void setEbbID(String ebbID) {
		this.ebbID = ebbID;
	}

	public String getGroupCompanySn() {
		return groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
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

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getCompanyBankNum() {
		return companyBankNum;
	}

	public void setCompanyBankNum(String companyBankNum) {
		this.companyBankNum = companyBankNum;
	}

	public String getCstaffID() {
		return cstaffID;
	}

	public void setCstaffID(String cstaffID) {
		this.cstaffID = cstaffID;
	}

	public String getCstaffName() {
		return cstaffName;
	}

	public void setCstaffName(String cstaffName) {
		this.cstaffName = cstaffName;
	}

	public String getCstaffRelationship() {
		return cstaffRelationship;
	}

	public void setCstaffRelationship(String cstaffRelationship) {
		this.cstaffRelationship = cstaffRelationship;
	}

	public String getUserAccountNum() {
		return userAccountNum;
	}

	public void setUserAccountNum(String userAccountNum) {
		this.userAccountNum = userAccountNum;
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

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getCcompanyName() {
		return ccompanyName;
	}

	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}

	public String getCcompanyRelationship() {
		return ccompanyRelationship;
	}

	public void setCcompanyRelationship(String ccompanyRelationship) {
		this.ccompanyRelationship = ccompanyRelationship;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
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

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public Date getBillsDate() {
		return billsDate;
	}

	public void setBillsDate(Date billsDate) {
		this.billsDate = billsDate;
	}

	public String getBillStaffID() {
		return billStaffID;
	}

	public void setBillStaffID(String billStaffID) {
		this.billStaffID = billStaffID;
	}

	public String getBillStaffName() {
		return billStaffName;
	}

	public void setBillStaffName(String billStaffName) {
		this.billStaffName = billStaffName;
	}

	public String getBillComppanyID() {
		return billComppanyID;
	}

	public void setBillComppanyID(String billComppanyID) {
		this.billComppanyID = billComppanyID;
	}

	public String getBillCompanyName() {
		return billCompanyName;
	}

	public void setBillCompanyName(String billCompanyName) {
		this.billCompanyName = billCompanyName;
	}

	public String getBillDeptID() {
		return billDeptID;
	}

	public void setBillDeptID(String billDeptID) {
		this.billDeptID = billDeptID;
	}

	public String getBillDeptName() {
		return billDeptName;
	}

	public void setBillDeptName(String billDeptName) {
		this.billDeptName = billDeptName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getPieces() {
		return pieces;
	}

	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	public String getSztype() {
		return sztype;
	}

	public void setSztype(String sztype) {
		this.sztype = sztype;
	}
	
	
	
}
