package hy.ea.bo.finance.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 出纳单管理：CashierBills
 * 
 * @author 杨金歌
 * @version zg 2011-5-12
 * 
 */
public class CashierBillsVO implements BaseBean, ExcelBean,java.io.Serializable  {
	private String cashierBillsID;
	private String cashierBillsKey;
	private String groupCompanySn; //集团标识
	private String pcompanyID;// 父公司ID
	private String companyID;// 公司ID
	private String organizationID;// 单据所在部门ID（固定session中取的）
	private String companyBankNum;// 公司银行账号
	private String departmentID;// 单据内容中部门ID（选择出来的）
	private String contactUserID;// 往来个人ID
	private String ccompanyID;// 往来单位ID
	private String billsType;// 单据类型
	
	private String billCheck;//票据支票管理
	
	private String journalNum;// 凭证号
	private String staffID;// 责任人
	private Date cashierDate;// 单据日期
	private String discountMoney;// 实物仓库ID（当做库房ID）
	private String afterDiscount;// 实物仓库
	private String dataDepotID;// 资料仓库ID
	private String dataDepotName;// 资料仓库名称
	private String bankDepotID;// 财务仓库ID
	private String bankDepotName;// 财务仓库名称
	private String accountNum;// 公司银行账户
	private String userAccountNum;// 个人银行账户
	private String status;// 单据状态 '00'草稿 '01'待主管审核 '02'待会计审核 '03'待出纳审核 '04'已审核
						 // '10' 驳回 '20' 转税务单据
	
	private String wfStatus1;//	00在线支付，01订单购买
	private String wfStatus2;//订单购买：00在线支付，01现金支付
	private String wfStatus3;//现金支付：00立即支付，01货到付款
	private String wfStatus4;//在线支付:00微信支付，01，支付宝支付，02银联支付 03:线下转账
	private String fkStatus;//付款未付款状态  00:已付款 01:未付款 02:已出库正在物流 03:用户已收货 04:已分配金币 05:申请退货    06:同意退货   07:退货中 08:买家退货，卖家确认收货09:转账确认
	                        // 10:申请售后 11:同意售后 12：售后中 13:售后成功   14:自动收获时金币不够未分配金币
	
	private String taxstatus;// 税务单据状态 '00'草稿 '01'待主管审核 '02'待经理审核 '03'待财务审计审核 
								//'04' 已报税 '10' 驳回
	private String taxstatus2;//  归档状态   '01'已归档
	private String inputid;  //制单人id
	private String inputName;//制单人名称
	private String responsible;// 普通单据主管审核人
	private String accountant;// 普通单据会计审核人
	private String cashier;// 普通单据出纳审核人
	private String competent;// 税务单据主管审核人
	private String manager;// 税务单据经理审核人
	private String financial;// 税务单据财务审计审核人
	private Date taxDate;// 报税日期
	private String companyname;// 公司名称
	private String departmentname;// 部门名称
	private String staffname;// 负责人姓名
	private String recordcode;// 负责人档案号
	
	private String proID;         			//项目单据id
	private String projectName;				//项目名称
	private String xmtype;					//项目类型
	private String xmtypename;				//项目类型名称
	private String projectCode;           //凭证号（项目编号）
	
	private String phone; // 个人往来关系
	private String contactConnections;// 公司往来关系
	
	private String goodsCoding;//设备编号
	private String goodsName;//设备名称
	private String defaultStorage;//统一分类条码
	private String mnemonicCode;//设备品牌
	private String standard;//设备品牌规格
	private String typeID;//设备类型
	private String model;//设备型号
	private String variableID;//设备单位
	private String acquiescestandard;//默认规格
	

	// 往来单位
	private String ccompanyname;// 往来单位名称
	private String companyAddr;// 具体地址
	private String companyTel;// 公司电话
	private String cresponsible;// 负责人
	private String responsibleTel;// 负责人电话
	private String industryType;// 负责人电话

	// 往来个人
	private String contactUserName;// 往来个人姓名
	private String staffIdentityCard;// 往来个人身份证
	private String tel; // 往来个人电话

	private String userQq; // 往来个人qq
	private String email; // 往来个人邮箱
	private String userAddr;// 往来个人具体地址
	private String depStatue;// 00:未缴费（未转科一）01:已缴费(已转未收集) 02:(已缴费)已转已收集 11: 售前客户服务单据
	
	private String oorgid;   //转账部门id
	private String oorgname;   //转账部门名称
	private String zzAccountNum;   //转账账户
	private String appstyle;  //拨款方式
	
	private  Date targetStart;//目标起时间
	private Date targetEnd;//目标止时间
	private String targetDeptID;//目标部门ID
	private String targetDeptName;//目标部门name
    private String targetSalerID;//目标业务员ID
    private String targetSalerName;//目标业务员Name
    
    /******************拨款操作字段*****************/
    private String appropriationcompanyName;//拨款方name
    private String receivablesName;//收款方name
	

	/** *******售前客户服务******* */
	private String consultStatus;  //咨询跟踪单状态  '00'草稿  '01'待营销审核  '02'待人事审核  '03'已审核  '10'驳回
	private String marketer;  //咨询跟踪单营销审核人
	private String personnel; //咨询跟踪单人事审核人
	private String jNumOrder; //订单号
	
	private String statusbill; //单据状态判断单据来源    00:拨款自动添加单据 01:收入预算单02：支出预算单03：调拨出库单  04：与微分金相关   10：半成品生产出库  11：成品生产出库 12：用户退款
	private String zctype;//支出类型 分为常规支出cg;和非常规类型fcg;
	private String remark; //备注
	
	

	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司名称", "黏贴单编号", "单据类型", "部门名称", "责任人",
				"制单日期", "往来单位", "单位银行账号", "单位往来关系",
				"往来个人", "个人往来关系", "单据状态"};
		return titles;
	}

	public static String[] columnHeading() {
		String[] titles = { "序号", "公司名称", "黏贴单编号", "单据类型", "部门名称", "责任人","录入人员",
				"制单日期", "实物仓库", "财务仓库", "资料仓库", "往来单位", "单位银行账号", "单位往来关系",
				"往来个人", "个人银行账号", "个人往来关系", "报税日期", "税务单据主管审核人", "税务单据经理审核人",
				"税务单据财务审计审核人", "税务状态", "是否转科一" };
		return titles;
	}
	
	public static String[] columnHeadingService() { //客户咨询单导出字段
		String[] titles = { "序号", "公司名称", "黏贴单编号", "单据类型", "部门名称", "责任人","制单人","制单日期","往来单位", 
				"单位银行账号","单位往来关系","往来个人", "个人银行账号", "个人往来关系","营销审核人","人事审核人","单据状态" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {
				companyname,
				journalNum,
				billsType,
				departmentname,
				staffname,
				String.format("%1$tF", cashierDate),

				afterDiscount,
				bankDepotName,
				dataDepotName,

				ccompanyname,
				accountNum,
				contactConnections,

				contactUserName,
				userAccountNum,
				phone,

				responsible,
				accountant,
				cashier,

				status.equals("00") ? "草稿" : (status.equals("01") ? "待主管审核"
						: status.equals("02") ? "待会计审核"
								: status.equals("03") ? "待出纳审核" : status
										.equals("04") ? "已审不报税" : status
										.equals("05") ? "已审已报税" : "未通过审核"),
				depStatue == null ? " " : depStatue.equals("00") ? "未转科一"
						: "已转科一" };
		return properties;
	}

	// public static String[] columnHeadings() {
	// String[] titles = { "序号", "公司名称", "黏贴单编号", "单据类型", "部门名称", "责任人",
	// "制单日期", "实物仓库", "财务仓库", "资料仓库", "往来单位", "单位银行账号", "单位往来关系",
	// "往来个人", "个人银行账号", "个人往来关系", "主管审核人", "会计审核人", "出纳审核人", "单据状态" };
	// return titles;
	// }
	//
	// @Override
	// public String[] properties() {
	// String[] properties = {
	// companyname,
	// journalNum,
	// billsType,
	// departmentname,
	// staffname,
	// String.format("%1$tF", cashierDate),
	// afterDiscount,
	// bankDepotName,
	// dataDepotName,
	// ccompanyname,
	// accountNum,
	// contactConnections,
	// contactUserName,
	// userAccountNum,
	// phone,
	// responsible,
	// accountant,
	// cashier,
	// status.equals("00") ? "草稿" : (status.equals("01") ? "待主管审核"
	// : status.equals("02") ? "待会计审核"
	// : status.equals("03") ? "待出纳审核" : status
	// .equals("04") ? "已审核" : "未通过审核") };
	// return properties;
	// }

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	/**
	 * 折扣金额（当做库房ID）
	 * 
	 * @return
	 */
	public String getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}

	/**
	 * 折扣后金额（当做库房Name）
	 * 
	 * @return
	 */
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

	/**
	 * 部门ID
	 * 
	 * @return
	 */
	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	/**
	 * 往来个人ID
	 * 
	 * @return
	 */
	public String getContactUserID() {
		return contactUserID;
	}

	public void setContactUserID(String contactUserID) {
		this.contactUserID = contactUserID;
	}

	/**
	 * 往来单位ID
	 * 
	 * @return
	 */
	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	/**
	 * 单据类型
	 * 
	 * @return
	 */
	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	/**
	 * 凭证号
	 * 
	 * @return
	 */
	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	/**
	 * 责任人
	 * 
	 * @return
	 */
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	/**
	 * 单据日期
	 * 
	 * @return
	 */
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
	 * 往来单位名称
	 * 
	 * @return
	 */
	public String getCcompanyname() {
		return ccompanyname;
	}

	public void setCcompanyname(String ccompanyname) {
		this.ccompanyname = ccompanyname;
	}

	/**
	 * 具体地址
	 * 
	 * @return
	 */
	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	/**
	 * 公司电话
	 * 
	 * @return
	 */
	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	/**
	 * 负责人
	 * 
	 * @return
	 */
	public String getCresponsible() {
		return cresponsible;
	}

	public void setCresponsible(String cresponsible) {
		this.cresponsible = cresponsible;
	}

	/**
	 * 负责人电话
	 * 
	 * @return
	 */
	public String getResponsibleTel() {
		return responsibleTel;
	}

	public void setResponsibleTel(String responsibleTel) {
		this.responsibleTel = responsibleTel;
	}

	/**
	 * 往来个人姓名
	 * 
	 * @return
	 */
	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	/**
	 * 往来个人身份证
	 * 
	 * @return
	 */
	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	/**
	 * 往来个人电话
	 * 
	 * @return
	 */
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 个人往来关系
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 往来个人qq
	 * 
	 * @return
	 */
	public String getUserQq() {
		return userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	/**
	 * 往来个人邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 往来个人具体地址
	 * 
	 * @return
	 */
	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	/**
	 * 公司名称
	 * 
	 * @return
	 */
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	/**
	 * 部门名称
	 * 
	 * @return
	 */
	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	/**
	 * 负责人姓名
	 * 
	 * @return
	 */
	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	/**
	 * 资料仓库ID
	 * 
	 * @return
	 */
	public String getDataDepotID() {
		return dataDepotID;
	}

	public void setDataDepotID(String dataDepotID) {
		this.dataDepotID = dataDepotID;
	}

	/**
	 * 资料仓库名称
	 * 
	 * @return
	 */
	public String getDataDepotName() {
		return dataDepotName;
	}

	public void setDataDepotName(String dataDepotName) {
		this.dataDepotName = dataDepotName;
	}

	/**
	 * 主管审核人
	 * 
	 * @return
	 */
	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getAccountant() {
		return accountant;
	}

	/**
	 * 会计审核人
	 * 
	 * @param accountant
	 */
	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}

	/**
	 * 出纳审核人
	 * 
	 * @return
	 */
	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	/**
	 * 财务仓库ID
	 * 
	 * @return
	 */
	public String getBankDepotID() {
		return bankDepotID;
	}

	public void setBankDepotID(String bankDepotID) {
		this.bankDepotID = bankDepotID;
	}

	/**
	 * 财务仓库名称
	 * 
	 * @return
	 */
	public String getBankDepotName() {
		return bankDepotName;
	}

	public void setBankDepotName(String bankDepotName) {
		this.bankDepotName = bankDepotName;
	}

	/**
	 * 公司银行账户
	 * 
	 * @return
	 */
	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	/**
	 * 公司往来关系
	 * 
	 * @return
	 */
	public String getContactConnections() {
		return contactConnections;
	}

	public void setContactConnections(String contactConnections) {
		this.contactConnections = contactConnections;
	}

	/**
	 * 负责人电话
	 * 
	 * @return
	 */
	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	/**
	 * 个人银行账户
	 * 
	 * @return
	 */
	public String getUserAccountNum() {
		return userAccountNum;
	}

	public void setUserAccountNum(String userAccountNum) {
		this.userAccountNum = userAccountNum;
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

	public String getPcompanyID() {
		return pcompanyID;
	}

	public void setPcompanyID(String pcompanyID) {
		this.pcompanyID = pcompanyID;
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
	/**
	 * 00:未缴费（未转科一）01:已缴费(已转未收集) 02:(已缴费)已转已收集 11: 售前客户服务单据
	 * @return
	 */
	public String getDepStatue() {
		return depStatue;
	}
	/**
	 * 00:未缴费（未转科一）01:已缴费(已转未收集) 02:(已缴费)已转已收集 11: 售前客户服务单据
	 * @param depStatue
	 */
	public void setDepStatue(String depStatue) {
		this.depStatue = depStatue;
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
	public String getInputid() {
		return inputid;
	}

	public void setInputid(String inputid) {
		this.inputid = inputid;
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
	
	public String getOorgid() {
		return oorgid;
	}

	public void setOorgid(String oorgid) {
		this.oorgid = oorgid;
	}

	public String getOorgname() {
		return oorgname;
	}

	public void setOorgname(String oorgname) {
		this.oorgname = oorgname;
	}

	public String getZzAccountNum() {
		return zzAccountNum;
	}

	public void setZzAccountNum(String zzAccountNum) {
		this.zzAccountNum = zzAccountNum;
	}

	public String getStatusbill() {
		return statusbill;
	}

	public void setStatusbill(String statusbill) {
		this.statusbill = statusbill;
	}

	public String getProID() {
		return proID;
	}

	public void setProID(String proID) {
		this.proID = proID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getXmtype() {
		return xmtype;
	}

	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}

	public String getXmtypename() {
		return xmtypename;
	}

	public void setXmtypename(String xmtypename) {
		this.xmtypename = xmtypename;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getGroupCompanySn() {
		return groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getAppstyle() {
		return appstyle;
	}

	public void setAppstyle(String appstyle) {
		this.appstyle = appstyle;
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

	public String getAppropriationcompanyName() {
		return appropriationcompanyName;
	}

	public void setAppropriationcompanyName(String appropriationcompanyName) {
		this.appropriationcompanyName = appropriationcompanyName;
	}

	public String getReceivablesName() {
		return receivablesName;
	}

	public void setReceivablesName(String receivablesName) {
		this.receivablesName = receivablesName;
	}

	public String getZctype() {
		return zctype;
	}

	public void setZctype(String zctype) {
		this.zctype = zctype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWfStatus1() {
		return wfStatus1;
	}

	public void setWfStatus1(String wfStatus1) {
		this.wfStatus1 = wfStatus1;
	}

	public String getWfStatus2() {
		return wfStatus2;
	}

	public void setWfStatus2(String wfStatus2) {
		this.wfStatus2 = wfStatus2;
	}

	public String getWfStatus3() {
		return wfStatus3;
	}

	public void setWfStatus3(String wfStatus3) {
		this.wfStatus3 = wfStatus3;
	}

	public String getWfStatus4() {
		return wfStatus4;
	}

	public void setWfStatus4(String wfStatus4) {
		this.wfStatus4 = wfStatus4;
	}

	public String getFkStatus() {
		return fkStatus;
	}

	public void setFkStatus(String fkStatus) {
		this.fkStatus = fkStatus;
	}

	public String getjNumOrder() {
		return jNumOrder;
	}

	public void setjNumOrder(String jNumOrder) {
		this.jNumOrder = jNumOrder;
	}
}
