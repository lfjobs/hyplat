package hy.ea.bo.finance.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 出纳单管理：CashierBills
 * @author 杨金歌
 *
 */
public class GoodsBillsVO implements BaseBean{
	private String goodsBillsID;
	private String goodsBillsKey;
	private String companyID;
	private String cashierBillsID;
	private String goodsNomber;//物品在账单中的排列序号
	private String goodsID;//物品ID 
	private Date startDate;//款源日期
	private Date endDate;//报账日期
	private String batchNumber;//批号
	private Date periodDate;//有效日期
	private String remindedContent;//提醒内容
	private String costType;//费用类别
	private String payType;//付款方式
	private String priceManage;//单价管理
	private String weight;//重量
	private String boxStandard;//箱规格
	private String price;//单价
	private String quantity;//数量
	private String money;// 申请金额
	private String realMoney; //实际金额
	private String alreadyMoney; //已收款
	private String futureMoney; //未收款
	private String rebate; //折扣
	private String rebatePrice; //折后价
	private String subjectsID;//科目管理ID
	private String subjectsName;//科目管理名称
	private String depotType;//库房类型
	private String depotID;//物品所在库房
	private String depotName;//库房名称
	private String loan;//借方金额
	private String forLoan;//贷方金额
	private String direction;//方向
	private String balance;//余额
	private String logistics;//物流方式
	private String goodsVariableID;//物品单位
	private String identifyingCode;//标识条码
	
	private String billsNumbers;//票据编号
	private String reasonThing;//事由
	private String otherAmount;//其他金额
	
	
	private String goodsVariable;//换算比例
	private String variable1ID;//单位
	private String variable2ID;//单位
	private String variable3ID;//单位
	private String variable4ID;//单位
	private String goodsCoding;//品名编号
	private String goodsName;//品名名称
	private String typeID;//类型
	private String variableID;//单位
	private String standard;//品牌规格
	private String mnemonicCode;//品牌
	private String model;//型号
	private String defaultStorage;//统一分类条码
	private String moneySpent;//用款原因
	private String isSelected;//比价后是否被选中 空为没比的，00未选中的，01选中的
	
	/******************拨款操作字段*****************/
	private String appropriationMoney;//拨款金额
	private String appropriationNum;//拨款账号
	private String appropriationcompanyID;// 收款方id
	private String appropriationcompanyName;//收款方name
	private String ReceivablesID;// 收款方id
	private String ReceivablesName;//收款方name
	private String RecropriationNum;//拨款账号
	private String appstyle;  //拨款方式
	
	/********* 往来公司、往来单位 ********/
	private String ccompanyName;// 往来单位
	private String ccompanyID;// 往来单位ID
	private String connectID;// 往来个人ID
	private String connectName;// 往来个人name
	
	/********* 目标信息 ********/
	private  Date targetStart;//目标起时间
	private Date targetEnd;//目标止时间
	private String targetDeptID;//目标部门ID
	private String targetDeptName;//目标部门name
    private String targetSalerID;//目标业务员ID
    private String targetSalerName;//目标业务员Name；
    private String tiaoPrice;//调整单价
    private String tiaoQuantity;//调整数量
    private String tiaoMoney;//调整金额
    private String planGoodsBillsID;//实际收入中预算的外键
	
	
	/*********售前客户跟踪服务********/
	private String workSite;//工作地点
	private String serviceWay;//服务方式
	private String serviceContent;//服务内容
	private String serviceReason;//跟踪原因
	private String dealStatus;//处理状态
	private Date entryTime;// 录入时间 
	
	/**************教务处状态****************/
	// 1 收集： 2 采集：  
	// 科一： 11 收集，12 培训， 13 测试，14 约考：15 考试: 10 测试合格率
	// 科二： 21 收集，22 培训， 23 测试，24 约考：25 考试  20 测试合格率
	// 科三： 31 收集，32 培训， 33 测试，34 约考：35 考试  30 测试合格率
	// 科四： 41 收集，42 培训， 43 测试，44 约考：45 考试  40 测试合格率
	private String status;
	
	private String projectDec;             //项目说明   	收集

	private String attachmentPath;        //附件	  	收集
	private String remark;                 //备注	  	收集
	
	private Date registrationDate;         //（款源时间）登记时间    陪训
	private Date accompanyDate ;           //（入账日期）培训时间    陪训

	private String reason;                 //原因			     测试
	
	private String studentCode;// 报开学号
	private String studentPeriods;// 学员期数
	private Date schoolopendate;//报开学时间
	
	private String premiums;//是否是奖品  0或null:否  1:是  
	
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public String getMoneySpent() {
		return moneySpent;
	}
	public void setMoneySpent(String moneySpent) {
		this.moneySpent = moneySpent;
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
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public String getSubjectsName() {
		return subjectsName;
	}
	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}
	public String getDepotName() {
		return depotName;
	}
	public void setDepotName(String depotName) {
		this.depotName = depotName;
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
	public String getLogistics() {
		return logistics;
	}
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
	public String getDefaultStorage() {
		return defaultStorage;
	}
	public void setDefaultStorage(String defaultStorage) {
		this.defaultStorage = defaultStorage;
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
	public String getProjectDec() {
		return projectDec;
	}
	public void setProjectDec(String projectDec) {
		this.projectDec = projectDec;
	}
	
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Date getAccompanyDate() {
		return accompanyDate;
	}
	public void setAccompanyDate(Date accompanyDate) {
		this.accompanyDate = accompanyDate;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkSite() {
		return workSite;
	}
	public void setWorkSite(String workSite) {
		this.workSite = workSite;
	}
	public String getServiceWay() {
		return serviceWay;
	}
	public void setServiceWay(String serviceWay) {
		this.serviceWay = serviceWay;
	}
	public String getServiceContent() {
		return serviceContent;
	}
	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	public String getServiceReason() {
		return serviceReason;
	}
	public void setServiceReason(String serviceReason) {
		this.serviceReason = serviceReason;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getGoodsNomber() {
		return goodsNomber;
	}
	public void setGoodsNomber(String goodsNomber) {
		this.goodsNomber = goodsNomber;
	}
	public String getBillsNumbers() {
		return billsNumbers;
	}
	public void setBillsNumbers(String billsNumbers) {
		this.billsNumbers = billsNumbers;
	}
	public String getReasonThing() {
		return reasonThing;
	}
	public void setReasonThing(String reasonThing) {
		this.reasonThing = reasonThing;
	}
	public String getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(String otherAmount) {
		this.otherAmount = otherAmount;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getAppropriationMoney() {
		return appropriationMoney;
	}
	public void setAppropriationMoney(String appropriationMoney) {
		this.appropriationMoney = appropriationMoney;
	}
	public String getAppropriationNum() {
		return appropriationNum;
	}
	public void setAppropriationNum(String appropriationNum) {
		this.appropriationNum = appropriationNum;
	}
	public String getAppropriationcompanyID() {
		return appropriationcompanyID;
	}
	public void setAppropriationcompanyID(String appropriationcompanyID) {
		this.appropriationcompanyID = appropriationcompanyID;
	}
	public String getAppropriationcompanyName() {
		return appropriationcompanyName;
	}
	public void setAppropriationcompanyName(String appropriationcompanyName) {
		this.appropriationcompanyName = appropriationcompanyName;
	}
	public String getReceivablesID() {
		return ReceivablesID;
	}
	public void setReceivablesID(String receivablesID) {
		ReceivablesID = receivablesID;
	}
	public String getReceivablesName() {
		return ReceivablesName;
	}
	public void setReceivablesName(String receivablesName) {
		ReceivablesName = receivablesName;
	}
	public String getRecropriationNum() {
		return RecropriationNum;
	}
	public void setRecropriationNum(String recropriationNum) {
		RecropriationNum = recropriationNum;
	}
	public String getAppstyle() {
		return appstyle;
	}
	public void setAppstyle(String appstyle) {
		this.appstyle = appstyle;
	}
	public String getCcompanyName() {
		return ccompanyName;
	}
	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getConnectID() {
		return connectID;
	}
	public void setConnectID(String connectID) {
		this.connectID = connectID;
	}
	public String getConnectName() {
		return connectName;
	}
	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getStudentPeriods() {
		return studentPeriods;
	}
	public void setStudentPeriods(String studentPeriods) {
		this.studentPeriods = studentPeriods;
	}
	public Date getSchoolopendate() {
		return schoolopendate;
	}
	public void setSchoolopendate(Date schoolopendate) {
		this.schoolopendate = schoolopendate;
	}
	public Date getTargetStart() {
		return targetStart;
	}
	public void setTargetStart(Date targetStart) {
		this.targetStart = targetStart;
	}
	public Date getTargetEnd() {
		return targetEnd;
	}
	public void setTargetEnd(Date targetEnd) {
		this.targetEnd = targetEnd;
	}
	public String getTargetDeptID() {
		return targetDeptID;
	}
	public void setTargetDeptID(String targetDeptID) {
		this.targetDeptID = targetDeptID;
	}
	public String getTargetDeptName() {
		return targetDeptName;
	}
	public void setTargetDeptName(String targetDeptName) {
		this.targetDeptName = targetDeptName;
	}
	public String getTargetSalerID() {
		return targetSalerID;
	}
	public void setTargetSalerID(String targetSalerID) {
		this.targetSalerID = targetSalerID;
	}
	public String getTargetSalerName() {
		return targetSalerName;
	}
	public void setTargetSalerName(String targetSalerName) {
		this.targetSalerName = targetSalerName;
	}
	public String getTiaoPrice() {
		return tiaoPrice;
	}
	public void setTiaoPrice(String tiaoPrice) {
		this.tiaoPrice = tiaoPrice;
	}
	public String getTiaoQuantity() {
		return tiaoQuantity;
	}
	public void setTiaoQuantity(String tiaoQuantity) {
		this.tiaoQuantity = tiaoQuantity;
	}
	public String getTiaoMoney() {
		return tiaoMoney;
	}
	public void setTiaoMoney(String tiaoMoney) {
		this.tiaoMoney = tiaoMoney;
	}
	public String getPlanGoodsBillsID() {
		return planGoodsBillsID;
	}
	public void setPlanGoodsBillsID(String planGoodsBillsID) {
		this.planGoodsBillsID = planGoodsBillsID;
	}
	public String getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(String realMoney) {
		this.realMoney = realMoney;
	}
	public String getAlreadyMoney() {
		return alreadyMoney;
	}
	public void setAlreadyMoney(String alreadyMoney) {
		this.alreadyMoney = alreadyMoney;
	}
	public String getFutureMoney() {
		return futureMoney;
	}
	public void setFutureMoney(String futureMoney) {
		this.futureMoney = futureMoney;
	}
	public String getRebate() {
		return rebate;
	}
	public void setRebate(String rebate) {
		this.rebate = rebate;
	}
	public String getRebatePrice() {
		return rebatePrice;
	}
	public void setRebatePrice(String rebatePrice) {
		this.rebatePrice = rebatePrice;
	}
	public String getPremiums() {
		return premiums;
	}
	public void setPremiums(String premiums) {
		this.premiums = premiums;
	}
	
}
