package hy.ea.bo.finance.billTimeClass;

import hy.ea.bo.finance.ProSetup;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 物品单据管理：GoodsBillsDay
 * 
 */
public class GoodsBillsDay implements BaseBean, Cloneable,java.io.Serializable {
	private String goodsBillsID;
	private String goodsBillsKey;
	private String companyID;
	private String goodsNomber;// 物品在账单中的排列序号
	// @xyz
	private String inventoryID;   //库存表id
	private String stockinvID;//库存盘点外键
	private String goodsnumber;       //物品在仓库中的序号
	
	private String companyBankNum;// 公司银行账号
	private String cashierBillsID;
	private String goodsID;// 物品ID 物品编号
	private String typeID; //物品类别
	private String goodsNum; //品名编号
	private String goodsName; //物品名称
	private Date startDate;// 款源日期
	private Date endDate;// 报账日期
	private String batchNumber;// 批号或期号
	private Date periodDate;// 有效日期
	private String remindedContent;// 提醒内容
	private String costType;// 费用类别
	private String payType;// 付款方式
	private String priceManage;// 单价管理
	private String weight;// 重量
	private String boxStandard;// 箱规格 类别(会员类别,生产产品的时候写的0-8,世统公司自己写)
	private String standard;//品牌规格
	private String price;// 单价
	private String quantity;// 数量
	private String fhnum;//待商家送货数量
	private String xcnum;//现场已拿走数量
	private String money;// 申请金额
	/**
	 * @author mz
	 */
	private String pricetype;//价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
	private String activityID;//促销活动ID

	private String realMoney; //本次收款
	private String alreadyMoney; //已收款
	private String futureMoney; //未收款
	private String rebate; //折扣
	private String rebatePrice; //折后价
	private String goodsVariableID;// 单位
	private String subjectsID;// 科目管理ID
	private String subjectsName;// 科目管理名称
	private String depotType;// 库房类型
	private String depotID;// 物品所在库房
	private String depotName;// 库房名称
	private String loan;// 借方金额
	private String forLoan;// 贷方金额
	private String direction;// 方向
	private String balance;// 余额
	private String balance2;// 报表余额
	private String logistics;// 物流方式
	private String moneySpent;// 用款原因
	private String identifyingCode;// 标识条码 世标条码  （如果是系统生成单据 00:从原来单据中带过来的物品  01:新添加物品）
	private String goodsStatus;// 归档状态 '01' 已归档 '02' 已录入凭证

	private String billsNumbers;// 票据编号
	private String reasonThing;// 摘要
	private String otherAmount;// 其他金额
	
	private String isSelected;//比价后是否被选中 空为没比的，00未选中的，01选中的
	
	/******************库存流程字段*****************/
	private String kcStatus;			//状态	22:草稿状态 12：未收货  13：已收货 14：已验货 15：已入库 16：已出库  17：盘库 18：移库 19:销售出库 23:入库失败24:报损
	private String goodstatus;            //状态00正常 01维修02报废
	private String storageQuantity;		  //未入库数量
	private String reQuantity;            //收货数量   
	private String isQualify;          	  //物品合格数量
	private String sortCode;              //统一分类条码

	/********* 往来公司、往来单位 ********/
	private String ccompanyName;// 往来单位
	private String ccompanyID;// 往来单位ID
	private String connectID;// 往来个人ID
	private String connectName;// 往来个人name

	/** *******售前客户跟踪服务******* */
	private String workSite;// 工作地点
	private String serviceWay;// 服务方式
	private String serviceContent;// 服务内容
	private String serviceReason;// 跟踪原因
	private String dealStatus;// 处理状态
	private Date entryTime;// 录入时间



	/** ************教务处状态*************** */
	// 1 收集： 2 采集：
	// 科一： 11 收集，12 培训， 13 测试，14 约考：15 考试: 10 测试合格率
	// 科二： 21 收集，22 培训， 23 测试，24 约考：25 考试 20 测试合格率
	// 科三： 31 收集，32 培训， 33 测试，34 约考：35 考试 30 测试合格率
	// 科四： 41 收集，42 培训， 43 测试，44 约考：45 考试 40 测试合格率
	private String status;// 状态      27：库存不足，转生产  28:生产中   29:生产完成，转入销售库
	private String category;		//产品类型  00：单产品    01：组装产品
	
	private String projectDec; // 项目文件路劲 收集

	private String attachmentPath; // 附件 收集
	private String remark; // 备注 收集

	private Date registrationDate; // （款源时间）登记时间 培训
	private Date accompanyDate; // （入账日期）培训时间 培训

	private String reason; // 原因 测试

	private File photo;
	private String photoFileName;
	private String photoContentType;

	private String etime;
	private String sdate;
	private String edate;

	private String billDate;

	// @mz
	private String ebdID;// 产品明细ID；
	private String ebbID;// 产品明细ID；
	private String canstatus;// 是否作废：作废为00;否则为空
	
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
    private String ppID;//项目ID也就是产品ID；
   
    /**
     * @author zc
     * @describe
     */
    private String studentCode;// 报开学号
	private String studentPeriods;// 学员期数
	private Date schoolopendate;//报开学时间
	private String tradeNo; //支付宝交易号
	
	/**
	 * 生产库存转向营销模块      zj
	 */
	private String profitMargin;   //利润率
	private String profitAmount;  //利润金额
	private String pretium;       	//销售价
    //wk会员产品增加
    private String companytopshangjo;//上级代理或者公司ID
    private String hytype;//个人购买会员类型
    private String costmoney;//成本价
    private String premiums;//是否是奖品  0或null:否  1:是  
	
    //往前台传值字段，不保存数据库
	private ProSetup proSetup;//佣金分配字段
	private List<BaseBean> pBeans;//佣金分配用
	private List<Object[]> objects;//佣金分配用

	private String supplierID;		//供应商ID
	private String supplier;				//供应商
	private String supplierStaffID;		//供应商负责人ID
	private String supplierStaffName;	//供应商负责人名称
	private String supplierStaffTelephone;		//供应商负责人电话
	
	private String proInspectionID;					//生产时考核检验ID
	private String fiveClear;									//该产品在生产所属组织机构

	private String areappid; //记录购买省县村代理详细区域产品id

	private List<BaseBean> backList;   //代理佣金分配用
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Object cloneGoodsBills() throws CloneNotSupportedException {

		return this.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accompanyDate == null) ? 0 : accompanyDate.hashCode());
		result = prime * result
				+ ((attachmentPath == null) ? 0 : attachmentPath.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result
				+ ((balance2 == null) ? 0 : balance2.hashCode());
		result = prime * result
				+ ((batchNumber == null) ? 0 : batchNumber.hashCode());
		result = prime * result
				+ ((billDate == null) ? 0 : billDate.hashCode());
		result = prime * result
				+ ((billsNumbers == null) ? 0 : billsNumbers.hashCode());
		result = prime * result
				+ ((boxStandard == null) ? 0 : boxStandard.hashCode());
		result = prime * result
				+ ((canstatus == null) ? 0 : canstatus.hashCode());
		result = prime * result
				+ ((cashierBillsID == null) ? 0 : cashierBillsID.hashCode());
		result = prime * result
				+ ((ccompanyID == null) ? 0 : ccompanyID.hashCode());
		result = prime * result
				+ ((ccompanyName == null) ? 0 : ccompanyName.hashCode());
		result = prime * result
				+ ((companyBankNum == null) ? 0 : companyBankNum.hashCode());
		result = prime * result
				+ ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result
				+ ((connectID == null) ? 0 : connectID.hashCode());
		result = prime * result
				+ ((connectName == null) ? 0 : connectName.hashCode());
		result = prime * result
				+ ((costType == null) ? 0 : costType.hashCode());
		result = prime * result
				+ ((dealStatus == null) ? 0 : dealStatus.hashCode());
		result = prime * result + ((depotID == null) ? 0 : depotID.hashCode());
		result = prime * result
				+ ((depotName == null) ? 0 : depotName.hashCode());
		result = prime * result
				+ ((depotType == null) ? 0 : depotType.hashCode());
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((ebbID == null) ? 0 : ebbID.hashCode());
		result = prime * result + ((ebdID == null) ? 0 : ebdID.hashCode());
		result = prime * result + ((edate == null) ? 0 : edate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((entryTime == null) ? 0 : entryTime.hashCode());
		result = prime * result + ((etime == null) ? 0 : etime.hashCode());
		result = prime * result + ((forLoan == null) ? 0 : forLoan.hashCode());
		result = prime * result
				+ ((goodsBillsID == null) ? 0 : goodsBillsID.hashCode());
		result = prime * result
				+ ((goodsBillsKey == null) ? 0 : goodsBillsKey.hashCode());
		result = prime * result + ((goodsID == null) ? 0 : goodsID.hashCode());
		result = prime * result
				+ ((goodsName == null) ? 0 : goodsName.hashCode());
		result = prime * result
				+ ((goodsNomber == null) ? 0 : goodsNomber.hashCode());
		result = prime * result
				+ ((goodsNum == null) ? 0 : goodsNum.hashCode());
		result = prime * result
				+ ((goodsStatus == null) ? 0 : goodsStatus.hashCode());
		result = prime * result
				+ ((goodsVariableID == null) ? 0 : goodsVariableID.hashCode());
		result = prime * result
				+ ((goodstatus == null) ? 0 : goodstatus.hashCode());
		result = prime * result
				+ ((identifyingCode == null) ? 0 : identifyingCode.hashCode());
		result = prime * result
				+ ((isQualify == null) ? 0 : isQualify.hashCode());
		result = prime * result
				+ ((isSelected == null) ? 0 : isSelected.hashCode());
		result = prime * result
				+ ((kcStatus == null) ? 0 : kcStatus.hashCode());
		result = prime * result + ((loan == null) ? 0 : loan.hashCode());
		result = prime * result
				+ ((logistics == null) ? 0 : logistics.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result
				+ ((moneySpent == null) ? 0 : moneySpent.hashCode());
		result = prime * result
				+ ((otherAmount == null) ? 0 : otherAmount.hashCode());
		result = prime * result + ((payType == null) ? 0 : payType.hashCode());
		result = prime * result
				+ ((periodDate == null) ? 0 : periodDate.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime
				* result
				+ ((photoContentType == null) ? 0 : photoContentType.hashCode());
		result = prime * result
				+ ((photoFileName == null) ? 0 : photoFileName.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((priceManage == null) ? 0 : priceManage.hashCode());
		result = prime * result
				+ ((projectDec == null) ? 0 : projectDec.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result
				+ ((reQuantity == null) ? 0 : reQuantity.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result
				+ ((reasonThing == null) ? 0 : reasonThing.hashCode());
		result = prime
				* result
				+ ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((remindedContent == null) ? 0 : remindedContent.hashCode());
		result = prime * result + ((sdate == null) ? 0 : sdate.hashCode());
		result = prime * result
				+ ((serviceContent == null) ? 0 : serviceContent.hashCode());
		result = prime * result
				+ ((serviceReason == null) ? 0 : serviceReason.hashCode());
		result = prime * result
				+ ((serviceWay == null) ? 0 : serviceWay.hashCode());
		result = prime * result
				+ ((sortCode == null) ? 0 : sortCode.hashCode());
		result = prime * result
				+ ((standard == null) ? 0 : standard.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((storageQuantity == null) ? 0 : storageQuantity.hashCode());
		result = prime * result
				+ ((subjectsID == null) ? 0 : subjectsID.hashCode());
		result = prime * result
				+ ((subjectsName == null) ? 0 : subjectsName.hashCode());
		result = prime * result + ((typeID == null) ? 0 : typeID.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		result = prime * result
				+ ((workSite == null) ? 0 : workSite.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GoodsBillsDay other = (GoodsBillsDay) obj;
		if (accompanyDate == null) {
			if (other.accompanyDate != null)
				return false;
		} else if (!accompanyDate.equals(other.accompanyDate))
			return false;
		if (attachmentPath == null) {
			if (other.attachmentPath != null)
				return false;
		} else if (!attachmentPath.equals(other.attachmentPath))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (balance2 == null) {
			if (other.balance2 != null)
				return false;
		} else if (!balance2.equals(other.balance2))
			return false;
		if (batchNumber == null) {
			if (other.batchNumber != null)
				return false;
		} else if (!batchNumber.equals(other.batchNumber))
			return false;
		if (billDate == null) {
			if (other.billDate != null)
				return false;
		} else if (!billDate.equals(other.billDate))
			return false;
		if (billsNumbers == null) {
			if (other.billsNumbers != null)
				return false;
		} else if (!billsNumbers.equals(other.billsNumbers))
			return false;
		if (boxStandard == null) {
			if (other.boxStandard != null)
				return false;
		} else if (!boxStandard.equals(other.boxStandard))
			return false;
		if (canstatus == null) {
			if (other.canstatus != null)
				return false;
		} else if (!canstatus.equals(other.canstatus))
			return false;
		if (cashierBillsID == null) {
			if (other.cashierBillsID != null)
				return false;
		} else if (!cashierBillsID.equals(other.cashierBillsID))
			return false;
		if (ccompanyID == null) {
			if (other.ccompanyID != null)
				return false;
		} else if (!ccompanyID.equals(other.ccompanyID))
			return false;
		if (ccompanyName == null) {
			if (other.ccompanyName != null)
				return false;
		} else if (!ccompanyName.equals(other.ccompanyName))
			return false;
		if (companyBankNum == null) {
			if (other.companyBankNum != null)
				return false;
		} else if (!companyBankNum.equals(other.companyBankNum))
			return false;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (connectID == null) {
			if (other.connectID != null)
				return false;
		} else if (!connectID.equals(other.connectID))
			return false;
		if (connectName == null) {
			if (other.connectName != null)
				return false;
		} else if (!connectName.equals(other.connectName))
			return false;
		if (costType == null) {
			if (other.costType != null)
				return false;
		} else if (!costType.equals(other.costType))
			return false;
		if (dealStatus == null) {
			if (other.dealStatus != null)
				return false;
		} else if (!dealStatus.equals(other.dealStatus))
			return false;
		if (depotID == null) {
			if (other.depotID != null)
				return false;
		} else if (!depotID.equals(other.depotID))
			return false;
		if (depotName == null) {
			if (other.depotName != null)
				return false;
		} else if (!depotName.equals(other.depotName))
			return false;
		if (depotType == null) {
			if (other.depotType != null)
				return false;
		} else if (!depotType.equals(other.depotType))
			return false;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (ebbID == null) {
			if (other.ebbID != null)
				return false;
		} else if (!ebbID.equals(other.ebbID))
			return false;
		if (ebdID == null) {
			if (other.ebdID != null)
				return false;
		} else if (!ebdID.equals(other.ebdID))
			return false;
		if (edate == null) {
			if (other.edate != null)
				return false;
		} else if (!edate.equals(other.edate))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (entryTime == null) {
			if (other.entryTime != null)
				return false;
		} else if (!entryTime.equals(other.entryTime))
			return false;
		if (etime == null) {
			if (other.etime != null)
				return false;
		} else if (!etime.equals(other.etime))
			return false;
		if (forLoan == null) {
			if (other.forLoan != null)
				return false;
		} else if (!forLoan.equals(other.forLoan))
			return false;
		if (goodsBillsID == null) {
			if (other.goodsBillsID != null)
				return false;
		} else if (!goodsBillsID.equals(other.goodsBillsID))
			return false;
		if (goodsBillsKey == null) {
			if (other.goodsBillsKey != null)
				return false;
		} else if (!goodsBillsKey.equals(other.goodsBillsKey))
			return false;
		if (goodsID == null) {
			if (other.goodsID != null)
				return false;
		} else if (!goodsID.equals(other.goodsID))
			return false;
		if (goodsName == null) {
			if (other.goodsName != null)
				return false;
		} else if (!goodsName.equals(other.goodsName))
			return false;
		if (goodsNomber == null) {
			if (other.goodsNomber != null)
				return false;
		} else if (!goodsNomber.equals(other.goodsNomber))
			return false;
		if (goodsNum == null) {
			if (other.goodsNum != null)
				return false;
		} else if (!goodsNum.equals(other.goodsNum))
			return false;
		if (goodsStatus == null) {
			if (other.goodsStatus != null)
				return false;
		} else if (!goodsStatus.equals(other.goodsStatus))
			return false;
		if (goodsVariableID == null) {
			if (other.goodsVariableID != null)
				return false;
		} else if (!goodsVariableID.equals(other.goodsVariableID))
			return false;
		if (goodstatus == null) {
			if (other.goodstatus != null)
				return false;
		} else if (!goodstatus.equals(other.goodstatus))
			return false;
		if (identifyingCode == null) {
			if (other.identifyingCode != null)
				return false;
		} else if (!identifyingCode.equals(other.identifyingCode))
			return false;
		if (isQualify == null) {
			if (other.isQualify != null)
				return false;
		} else if (!isQualify.equals(other.isQualify))
			return false;
		if (isSelected == null) {
			if (other.isSelected != null)
				return false;
		} else if (!isSelected.equals(other.isSelected))
			return false;
		if (kcStatus == null) {
			if (other.kcStatus != null)
				return false;
		} else if (!kcStatus.equals(other.kcStatus))
			return false;
		if (loan == null) {
			if (other.loan != null)
				return false;
		} else if (!loan.equals(other.loan))
			return false;
		if (logistics == null) {
			if (other.logistics != null)
				return false;
		} else if (!logistics.equals(other.logistics))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (moneySpent == null) {
			if (other.moneySpent != null)
				return false;
		} else if (!moneySpent.equals(other.moneySpent))
			return false;
		if (otherAmount == null) {
			if (other.otherAmount != null)
				return false;
		} else if (!otherAmount.equals(other.otherAmount))
			return false;
		if (payType == null) {
			if (other.payType != null)
				return false;
		} else if (!payType.equals(other.payType))
			return false;
		if (periodDate == null) {
			if (other.periodDate != null)
				return false;
		} else if (!periodDate.equals(other.periodDate))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (photoContentType == null) {
			if (other.photoContentType != null)
				return false;
		} else if (!photoContentType.equals(other.photoContentType))
			return false;
		if (photoFileName == null) {
			if (other.photoFileName != null)
				return false;
		} else if (!photoFileName.equals(other.photoFileName))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (priceManage == null) {
			if (other.priceManage != null)
				return false;
		} else if (!priceManage.equals(other.priceManage))
			return false;
		if (projectDec == null) {
			if (other.projectDec != null)
				return false;
		} else if (!projectDec.equals(other.projectDec))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (reQuantity == null) {
			if (other.reQuantity != null)
				return false;
		} else if (!reQuantity.equals(other.reQuantity))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (reasonThing == null) {
			if (other.reasonThing != null)
				return false;
		} else if (!reasonThing.equals(other.reasonThing))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (remindedContent == null) {
			if (other.remindedContent != null)
				return false;
		} else if (!remindedContent.equals(other.remindedContent))
			return false;
		if (sdate == null) {
			if (other.sdate != null)
				return false;
		} else if (!sdate.equals(other.sdate))
			return false;
		if (serviceContent == null) {
			if (other.serviceContent != null)
				return false;
		} else if (!serviceContent.equals(other.serviceContent))
			return false;
		if (serviceReason == null) {
			if (other.serviceReason != null)
				return false;
		} else if (!serviceReason.equals(other.serviceReason))
			return false;
		if (serviceWay == null) {
			if (other.serviceWay != null)
				return false;
		} else if (!serviceWay.equals(other.serviceWay))
			return false;
		if (sortCode == null) {
			if (other.sortCode != null)
				return false;
		} else if (!sortCode.equals(other.sortCode))
			return false;
		if (standard == null) {
			if (other.standard != null)
				return false;
		} else if (!standard.equals(other.standard))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (storageQuantity == null) {
			if (other.storageQuantity != null)
				return false;
		} else if (!storageQuantity.equals(other.storageQuantity))
			return false;
		if (subjectsID == null) {
			if (other.subjectsID != null)
				return false;
		} else if (!subjectsID.equals(other.subjectsID))
			return false;
		if (subjectsName == null) {
			if (other.subjectsName != null)
				return false;
		} else if (!subjectsName.equals(other.subjectsName))
			return false;
		if (typeID == null) {
			if (other.typeID != null)
				return false;
		} else if (!typeID.equals(other.typeID))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		if (workSite == null) {
			if (other.workSite != null)
				return false;
		} else if (!workSite.equals(other.workSite))
			return false;
		return true;
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

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getGoodsNomber() {
        return goodsNomber;
    }

    public void setGoodsNomber(String goodsNomber) {
        this.goodsNomber = goodsNomber;
    }

    public String getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getStockinvID() {
        return stockinvID;
    }

    public void setStockinvID(String stockinvID) {
        this.stockinvID = stockinvID;
    }

    public String getGoodsnumber() {
        return goodsnumber;
    }

    public void setGoodsnumber(String goodsnumber) {
        this.goodsnumber = goodsnumber;
    }

    public String getCompanyBankNum() {
        return companyBankNum;
    }

    public void setCompanyBankNum(String companyBankNum) {
        this.companyBankNum = companyBankNum;
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

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
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

    public String getGoodsVariableID() {
        return goodsVariableID;
    }

    public void setGoodsVariableID(String goodsVariableID) {
        this.goodsVariableID = goodsVariableID;
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

    public String getBalance2() {
        return balance2;
    }

    public void setBalance2(String balance2) {
        this.balance2 = balance2;
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

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
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

    public String getKcStatus() {
        return kcStatus;
    }

    public void setKcStatus(String kcStatus) {
        this.kcStatus = kcStatus;
    }

    public String getGoodstatus() {
        return goodstatus;
    }

    public void setGoodstatus(String goodstatus) {
        this.goodstatus = goodstatus;
    }

    public String getStorageQuantity() {
        return storageQuantity;
    }

    public void setStorageQuantity(String storageQuantity) {
        this.storageQuantity = storageQuantity;
    }

    public String getReQuantity() {
        return reQuantity;
    }

    public void setReQuantity(String reQuantity) {
        this.reQuantity = reQuantity;
    }

    public String getIsQualify() {
        return isQualify;
    }

    public void setIsQualify(String isQualify) {
        this.isQualify = isQualify;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
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

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
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

    public String getPpID() {
        return ppID;
    }

    public void setPpID(String ppID) {
        this.ppID = ppID;
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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(String profitMargin) {
        this.profitMargin = profitMargin;
    }

    public String getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(String profitAmount) {
        this.profitAmount = profitAmount;
    }

    public String getPretium() {
        return pretium;
    }

    public void setPretium(String pretium) {
        this.pretium = pretium;
    }

    public String getCostmoney() {
        return costmoney;
    }

    public void setCostmoney(String costmoney) {
        this.costmoney = costmoney;
    }

    public String getPremiums() {
        return premiums;
    }

    public void setPremiums(String premiums) {
        this.premiums = premiums;
    }

    public ProSetup getProSetup() {
        return proSetup;
    }

    public void setProSetup(ProSetup proSetup) {
        this.proSetup = proSetup;
    }

    public List<BaseBean> getpBeans() {
        return pBeans;
    }

    public void setpBeans(List<BaseBean> pBeans) {
        this.pBeans = pBeans;
    }

    public List<Object[]> getObjects() {
        return objects;
    }

    public void setObjects(List<Object[]> objects) {
        this.objects = objects;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierStaffID() {
        return supplierStaffID;
    }

    public void setSupplierStaffID(String supplierStaffID) {
        this.supplierStaffID = supplierStaffID;
    }

    public String getSupplierStaffName() {
        return supplierStaffName;
    }

    public void setSupplierStaffName(String supplierStaffName) {
        this.supplierStaffName = supplierStaffName;
    }

    public String getSupplierStaffTelephone() {
        return supplierStaffTelephone;
    }

    public void setSupplierStaffTelephone(String supplierStaffTelephone) {
        this.supplierStaffTelephone = supplierStaffTelephone;
    }

    public String getProInspectionID() {
        return proInspectionID;
    }

    public void setProInspectionID(String proInspectionID) {
        this.proInspectionID = proInspectionID;
    }

    public String getFiveClear() {
        return fiveClear;
    }

    public void setFiveClear(String fiveClear) {
        this.fiveClear = fiveClear;
    }

    public String getAreappid() {
        return areappid;
    }

    public void setAreappid(String areappid) {
        this.areappid = areappid;
    }

    public List<BaseBean> getBackList() {
        return backList;
    }

    public void setBackList(List<BaseBean> backList) {
        this.backList = backList;
    }

    public String getCompanytopshangjo() {
        return companytopshangjo;
    }

    public void setCompanytopshangjo(String companytopshangjo) {
        this.companytopshangjo = companytopshangjo;
    }

    public String getHytype() {
        return hytype;
    }

    public void setHytype(String hytype) {
        this.hytype = hytype;
    }

	public String getPricetype() {
		return pricetype;
	}

	public void setPricetype(String pricetype) {
		this.pricetype = pricetype;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}

	public String getFhnum() {
		return fhnum;
	}

	public void setFhnum(String fhnum) {
		this.fhnum = fhnum;
	}

	public String getXcnum() {
		return xcnum;
	}

	public void setXcnum(String xcnum) {
		this.xcnum = xcnum;
	}
}
