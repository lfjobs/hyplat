package hy.ea.bo.history.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 出纳单明细管理：GoodsCashierBillsVO
 * 
 * @author 杨金歌
 * 
 */
public class HistoryGoodsBillVO implements BaseBean, ExcelBean {
	private String cashierBillsID;
	private String pcompanyID;
	private String companyID;
	private String organizationID;
	private String companyBankNum;// 公司银行账号
	private String departmentID;// 部门ID
	private String contactUserID;// 往来个人ID
	private String ccompanyID;// 往来单位ID
	private String billsType;// 单据类型
	private String PbillsTypeID;// 父单据类型id
	private String journalNum;// 粘帖单编号
	private String staffID;// 责任人
	private Date cashierDate;// 制单日期
	private String discountMoney;// 折扣金额（当做库房ID）
	private String afterDiscount;// 折扣后金额（当做库房Name）
	private String dataDepotID;// 资料仓库ID
	private String dataDepotName;// 资料仓库名称
	private String bankDepotID;// 财务仓库ID
	private String bankDepotName;// 财务仓库名称
	private String accountNum;// 公司银行账户
	private String userAccountNum;// 个人银行账户
	private String cashierStatus;// 单据状态 '00'作废 '01'待主管审核 '02'待会计审核 '03'待出纳审核 '04'已审核
	  							// '10' 驳回 '20' 转税务单据
	private String taxstatus;// 税务单据状态 '00'草稿 '01'待主管审核 '02'待经理审核
							// '03'待财务审计审核 '04' 已报税 '10' 驳回
	private String taxstatus2;//  归档状态    '01'已归档
	private String input;  //录入人员
	private String responsible;// 普通单据主管审核人
	private String accountant;// 普通单据会计审核人
	private String cashier;// 普通单据出纳审核人
	private String competent;// 税务单据主管审核人
	private String manager;// 税务单据经理审核人
	private String financial;// 税务单据财务审计审核人
	private Date taxDate;//报税日期
	private String companyname;// 公司名称
	private String departmentname;// 部门名称
	private String staffname;// 负责人姓名
	private String recordcode;// 负责人档案号
	// 往来单位
	private String ccompanyname;// 往来单位名称
	private String companyAddr;// 具体地址
	private String companyTel;// 公司电话
	private String cresponsible;// 负责人
	private String responsibleTel;// 负责人电话
	private String industryType;// 行业类别
	private String contactConnections;// 单位往来关系
	// 往来个人
	private String contactUserName;// 姓名
	private String staffIdentityCard;// 身份证
	private String tel; // 电话
	private String phone; // 个人往来关系
	private String userQq; // qq
	private String email; // 邮箱
	private String userAddr;// 具体地址

	// 物品单
	private String goodsBillsID;
	private String goodsBillsKey;
	private String goodsNomber;//物品在账单中的排列序号
	private String goodsID;// 物品ID
	private Date startDate;// 款源日期
	private Date endDate;// 入账日期
	private String batchNumber;// 批号
	private Date periodDate;// 有效日期
	private String remindedContent;// 提醒内容
	private String costType;// 费用类别
	private String payType;// 付款方式
	private String priceManage;// 单价管理
	private String weight;// 重量
	private String boxStandard;// 箱规格
	private String price;// 单价
	private String quantity;// 数量
	private String money;// 金额
	private String subjectsID;// 科目管理ID
	private String subjectsName;// 科目管理名称
	private String depotType;// 库房类型
	private String depotID;// 物品所在库房
	private String depotName;// 库房名称
	private String loan;// 借方金额
	private String forLoan;// 贷方金额
	private String direction;// 方向
	private String balance;// 余额
	private String status;// 状态
	private String reasonThing;//事由
	private String logistics;// 物流方式
	private String goodsVariableID;// 物品单位
	private String identifyingCode;// 标识条码

	private String goodsVariable;// 换算比例
	private String variable1ID;// 单位
	private String variable2ID;// 单位
	private String variable3ID;// 单位
	private String variable4ID;// 单位
	private String goodsCoding;// 品名编号
	private String goodsName;// 品名名称
	private String typeID;// 类型
	private String variableID;// 单位
	private String standard;// 品牌规格
	private String mnemonicCode;// 品牌
	private String model;// 型号
	private String defaultStorage;// 统一分类条码
	private String moneySpent;// 用款原因
	
	private String archivesNum;//公司归档号 
	private Date archivesDate;//归档日期
	private String deparchivesNum;//部门归档号
	private Date deparchivesDate;//部门归档日期

	/*public static String[] columnHeadings() {
		String[] titles = { "序号","公司名称", "黏贴单编号", "单据类别", "部门", "责任人", "制单日期",
				"物品名称", "物品编号", "类型", "单位", "单价", "数量", "金额", "方向", "余额","税务状态",
				"单据状态" };
		return titles;
	}*/
	public static String[] columnHeading() {
		String[] titles = { "序号", "公司", "部门名称", "责任人",
				"银行帐号", "单据类别", "粘贴单据号",  "票据归档号",
				"款源日期", "入账日期", "有效日期", "批号/别号", "品名编号","统一分类条码","标识条码","费用类别",
				"科目", "品名名称", "事由","类型","品牌","型号","品牌规格","单位","数量","质量","箱规格","单价管理","单价","库房管理","借方金额","方向","贷方金额","余额" };
		return titles;
	}

/*	public String[] properties() {
		String[] properties = {
				companyname,
				journalNum,
				billsType,
				departmentname,
				staffname,
				String.format("%1$tF", cashierDate),
				goodsName,
				goodsCoding,
				typeID,
				goodsVariableID,
				price,
				quantity,
				money,
				direction,
				balance,
				taxstatus== null ? " " : taxstatus.equals("01") ? "待主管审核"
						: taxstatus.equals("02") ? "待经理审核": taxstatus.equals("03") ? "待财务审计审核":  taxstatus.equals("04") ? "可报税": taxstatus.equals("10") ? "驳回作废":" " ,
				status.equals("00") ? "草稿" : (status.equals("01") ? "待主管审核"
						: status.equals("02") ? "待会计审核"
								: status.equals("03") ? "待出纳审核" : status
										.equals("04") ? "已审核" : status
										.equals("20") ? "税务单据" : "驳回作废")};
		return properties;
	}*/
	public String[] properties() {
		String[] properties = {
				companyname,departmentname,staffname,companyBankNum,
				billsType,journalNum,archivesNum,String.format("%1$tF", startDate),String.format("%1$tF", endDate),String.format("%1$tF", periodDate),
				batchNumber,goodsCoding,defaultStorage,identifyingCode,costType,subjectsName,
				goodsName,reasonThing,typeID,mnemonicCode,model,standard,
				variableID,quantity,weight,boxStandard,priceManage,price,depotType,loan,
				direction,forLoan,balance
				};
		return properties;
	}

	
	/**
	 * 历史税务单据状态
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
	 * @return
	 */
	public String getCompetent() {
		return competent;
	}
	
	public void setCompetent(String competent) {
		this.competent = competent;
	}
	
	/**
	 *  税务单据经理审核人
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
	 * @return
	 */
	public Date getTaxDate() {
		return taxDate;
	}

	public void setTaxDate(Date taxDate) {
		this.taxDate = taxDate;
	}
	
	/**
	 * 税务单据状态
	 * @return
	 */
	public String getTaxstatus() {
		return taxstatus;
	}

	public void setTaxstatus(String taxstatus) {
		this.taxstatus = taxstatus;
	}
	
	/**
	 * 物品在账单中的排列序号
	 * @return
	 */
	public String getGoodsNomber() {
		return goodsNomber;
	}

	public void setGoodsNomber(String goodsNomber) {
		this.goodsNomber = goodsNomber;
	}

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

	public String getCcompanyname() {
		return ccompanyname;
	}

	public void setCcompanyname(String ccompanyname) {
		this.ccompanyname = ccompanyname;
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

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserQq() {
		return userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
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

	public String getContactConnections() {
		return contactConnections;
	}

	public void setContactConnections(String contactConnections) {
		this.contactConnections = contactConnections;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getCashierStatus() {
		return cashierStatus;
	}

	public void setCashierStatus(String cashierStatus) {
		this.cashierStatus = cashierStatus;
	}

	public String getGoodsBillsID() {
		return goodsBillsID;
	}

	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}

	public String getGoodsBillsKey() {
		return goodsBillsKey;
	}

	public void setGoodsBillsKey(String goodsBillsKey) {
		this.goodsBillsKey = goodsBillsKey;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPriceManage() {
		return priceManage;
	}

	public void setPriceManage(String priceManage) {
		this.priceManage = priceManage;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBoxStandard() {
		return boxStandard;
	}

	public void setBoxStandard(String boxStandard) {
		this.boxStandard = boxStandard;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSubjectsID() {
		return subjectsID;
	}

	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}

	public String getSubjectsName() {
		return subjectsName;
	}

	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}

	public String getDepotType() {
		return depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}

	public String getDepotID() {
		return depotID;
	}

	public void setDepotID(String depotID) {
		this.depotID = depotID;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getLoan() {
		return loan;
	}

	public void setLoan(String loan) {
		this.loan = loan;
	}

	public String getForLoan() {
		return forLoan;
	}

	public void setForLoan(String forLoan) {
		this.forLoan = forLoan;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
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

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getVariableID() {
		return variableID;
	}

	public void setVariableID(String variableID) {
		this.variableID = variableID;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getMnemonicCode() {
		return mnemonicCode;
	}

	public void setMnemonicCode(String mnemonicCode) {
		this.mnemonicCode = mnemonicCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDefaultStorage() {
		return defaultStorage;
	}

	public void setDefaultStorage(String defaultStorage) {
		this.defaultStorage = defaultStorage;
	}

	public String getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(String moneySpent) {
		this.moneySpent = moneySpent;
	}

	public String getUserAccountNum() {
		return userAccountNum;
	}

	public void setUserAccountNum(String userAccountNum) {
		this.userAccountNum = userAccountNum;
	}

	public String getGoodsVariableID() {
		return goodsVariableID;
	}

	public void setGoodsVariableID(String goodsVariableID) {
		this.goodsVariableID = goodsVariableID;
	}

	public String getGoodsVariable() {
		return goodsVariable;
	}

	public void setGoodsVariable(String goodsVariable) {
		this.goodsVariable = goodsVariable;
	}

	public String getVariable1ID() {
		return variable1ID;
	}

	public void setVariable1ID(String variable1ID) {
		this.variable1ID = variable1ID;
	}

	public String getVariable2ID() {
		return variable2ID;
	}

	public void setVariable2ID(String variable2ID) {
		this.variable2ID = variable2ID;
	}

	public String getVariable3ID() {
		return variable3ID;
	}

	public void setVariable3ID(String variable3ID) {
		this.variable3ID = variable3ID;
	}

	public String getVariable4ID() {
		return variable4ID;
	}

	public void setVariable4ID(String variable4ID) {
		this.variable4ID = variable4ID;
	}

	public String getIdentifyingCode() {
		return identifyingCode;
	}

	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	public String getCompanyBankNum() {
		return companyBankNum;
	}

	public void setCompanyBankNum(String companyBankNum) {
		this.companyBankNum = companyBankNum;
	}

	public String getPcompanyID() {
		return pcompanyID;
	}

	public void setPcompanyID(String pcompanyID) {
		this.pcompanyID = pcompanyID;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	public String getRemindedContent() {
		return remindedContent;
	}

	public void setRemindedContent(String remindedContent) {
		this.remindedContent = remindedContent;
	}

	/**
	 * 负责人档案号
	 * 
	 * @return
	 */
	public String getRecordcode() {
		return recordcode;
	}

	/**
	 * 负责人档案号
	 * 
	 * @return
	 */
	public void setRecordcode(String recordcode) {
		this.recordcode = recordcode;
	}

	public String getArchivesNum() {
		return archivesNum;
	}

	public void setArchivesNum(String archivesNum) {
		this.archivesNum = archivesNum;
	}

	public Date getArchivesDate() {
		return archivesDate;
	}

	public void setArchivesDate(Date archivesDate) {
		this.archivesDate = archivesDate;
	}

	public String getDeparchivesNum() {
		return deparchivesNum;
	}

	public void setDeparchivesNum(String deparchivesNum) {
		this.deparchivesNum = deparchivesNum;
	}

	public Date getDeparchivesDate() {
		return deparchivesDate;
	}

	public void setDeparchivesDate(Date deparchivesDate) {
		this.deparchivesDate = deparchivesDate;
	}

	public String getPbillsTypeID() {
		return PbillsTypeID;
	}

	public void setPbillsTypeID(String pbillsTypeID) {
		PbillsTypeID = pbillsTypeID;
	}

	public String getReasonThing() {
		return reasonThing;
	}

	public void setReasonThing(String reasonThing) {
		this.reasonThing = reasonThing;
	}
	
	
}
