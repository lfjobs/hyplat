package hy.ea.bo.history;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 物品单据管理：GoodsBills-History
 * 
 * @author 杨金歌
 * 
 */
public class HistoryGoodsBills implements BaseBean {
	private String goodsBillsID;
	private String goodsBillsKey;
	private String companyID;
	private String goodsNomber;// 物品在账单中的排列序号
	private String companyBankNum;// 公司银行账号
	private String cashierBillsID;
	private String goodsID;// 物品ID 物品编号
	private Date startDate;// 款源日期
	private Date endDate;// 报账日期
	private String batchNumber;// 批号或期号
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
	private String balance2;//报表余额
	private String logistics;// 物流方式
	private String moneySpent;// 用款原因
	private String identifyingCode;// 标识条码 世标条码
	private String goodsVariableID;// 单位
	private String goodsStatus;// 归档状态   '01'已归档

	private String billsNumbers;//票据编号
	private String reasonThing;//事由
	private String otherAmount;//其他金额
	/** *******售前客户跟踪服务******* */
	private String workSite;// 工作地点
	private String serviceWay;// 服务方式
	private String serviceContent;// 服务内容
	private String serviceReason;// 跟踪原因
	private String dealStatus;// 处理状态
	private Date entryTime;// 录入时间
	// 咨询起日期 startDate
	// 咨询止日期 accompanyDate
	// 附件名称 AttachmentName
	// 附件 attachmentPath

	/** ************教务处状态*************** */
	// 1 收集： 2 采集：
	// 科一： 11 收集，12 培训， 13 测试，14 约考：15 考试: 10 测试合格率
	// 科二： 21 收集，22 培训， 23 测试，24 约考：25 考试 20 测试合格率
	// 科三： 31 收集，32 培训， 33 测试，34 约考：35 考试 30 测试合格率
	// 科四： 41 收集，42 培训， 43 测试，44 约考：45 考试 40 测试合格率
	private String status;// 状态

	private String projectDec; // 项目文件路劲 收集
	//private String AttachmentName; // 附件名称 收集
	private String attachmentPath; // 附件 收集
	private String remark; // 备注 收集

	private Date registrationDate; // （款源时间）登记时间 培训
	private Date accompanyDate; // （入账日期）培训时间 培训
	//private String accompanyContent; // 陪训内容 培训

	//private String testScores; // 测试得分 测试
	//private String eligibility; // 是否合格 测试
	private String reason; // 原因 测试

	//private String testRefNumber; // 测试参考数、 测试合格率
	//private String testEligNumber; // 测试合格数、 测试合格率
	//private String testNotEligNumber; // 测试未合格数 测试合格率

	//private String notifySuccessful; // 是否通知成功 约考

	//private Date examTime; // 考试时间、 考试
	//private String examScores; // 考试得分 考试
	// eligibility 是否合格

	private File photo;
	private String photoFileName;
	private String photoContentType;

	private String etime;
	private String sdate;
	private String edate;

	private String billDate;
	//@mz
	private String ebdID;//产品明细ID；
	private String ebbID;//产品明细ID；
	private String canstatus;//是否作废：作废为00;否则为空
	//private String projectDecContent;// 项目文件内容
	//private String projectDecContent1;// 项目文件内容1
	
	/*public String getProjectDecContent1() {
		return projectDecContent1;
	}

	public void setProjectDecContent1(String projectDecContent1) {
		this.projectDecContent1 = projectDecContent1;
	}*/

	public String getGoodsBillsID() {
		return goodsBillsID;
	}

	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}

	public String getGoodsBillsKey() {
		return goodsBillsKey;
	}

	public String getIdentifyingCode() {
		return identifyingCode;
	}

	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
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

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getDepotType() {
		return depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
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

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public String getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(String moneySpent) {
		this.moneySpent = moneySpent;
	}

	public String getGoodsVariableID() {
		return goodsVariableID;
	}

	public void setGoodsVariableID(String goodsVariableID) {
		this.goodsVariableID = goodsVariableID;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
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

	/*public String getAccompanyContent() {
		return accompanyContent;
	}

	public void setAccompanyContent(String accompanyContent) {
		this.accompanyContent = accompanyContent;
	}*/

	/*public String getTestScores() {
		return testScores;
	}

	public void setTestScores(String testScores) {
		this.testScores = testScores;
	}*/

	/*public String getEligibility() {
		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}*/

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/*public String getTestRefNumber() {
		return testRefNumber;
	}

	public void setTestRefNumber(String testRefNumber) {
		this.testRefNumber = testRefNumber;
	}*/

	/*public String getTestEligNumber() {
		return testEligNumber;
	}

	public void setTestEligNumber(String testEligNumber) {
		this.testEligNumber = testEligNumber;
	}*/

	/*public String getTestNotEligNumber() {
		return testNotEligNumber;
	}

	public void setTestNotEligNumber(String testNotEligNumber) {
		this.testNotEligNumber = testNotEligNumber;
	}
*/
	/*public String getNotifySuccessful() {
		return notifySuccessful;
	}

	public void setNotifySuccessful(String notifySuccessful) {
		this.notifySuccessful = notifySuccessful;
	}*/

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

	/*public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}*/

	/*public String getExamScores() {
		return examScores;
	}

	public void setExamScores(String examScores) {
		this.examScores = examScores;
	}*/

	public String getProjectDec() {
		return projectDec;
	}

	public void setProjectDec(String projectDec) {
		this.projectDec = projectDec;
	}

	/*public String getAttachmentName() {
		return AttachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		AttachmentName = attachmentName;
	}*/

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

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
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

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	/*public String getProjectDecContent() {
		return projectDecContent;
	}

	public void setProjectDecContent(String projectDecContent) {
		this.projectDecContent = projectDecContent;
	}
*/
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

	public String getBalance2() {
		return balance2;
	}

	public void setBalance2(String balance2) {
		this.balance2 = balance2;
	}

	public String getEbdID() {
		return ebdID;
	}

	public void setEbdID(String ebdID) {
		this.ebdID = ebdID;
	}

	public String getEbbID() {
		return ebbID;
	}

	public void setEbbID(String ebbID) {
		this.ebbID = ebbID;
	}

	public String getCanstatus() {
		return canstatus;
	}

	public void setCanstatus(String canstatus) {
		this.canstatus = canstatus;
	}
	

}
