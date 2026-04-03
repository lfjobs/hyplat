package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 兑现申请表
 * 
 * @author mz
 * 
 */
/*币种	日期	明细标志	顺序号	付款账号开户行	付款账号/卡号
		付款账号名称/卡名称	收款账号开户行	收款账号省份
		收款账号地市	收款账号地区码	收款账号	收款账号名称
		金额	汇款用途	备注信息
		汇款方式
		收款账户短信通知手机号码
		自定义序号
	*/

public class WithDrawApply implements BaseBean,ExcelBean,java.io.Serializable {
	private String wdaKey;
	private String wdaID;
	private String companyID;
	private String sccid;//申请人
	private String exportStaffID;//最新一次导出人ID
	private int exportTimes;//导出次数
	private Date exportDate;//导出日期
	private String payState;//状态　，00 初始状态  ， 01 通过，02 不通过  03：已打款
	private Date  payDate;//审核操作时间
	private String payOperatorID;//审核操作人员ID
	private String payOperatorName;//审核操作人员姓名
	private String  tradeCode;//第三方交易号，回执单上面的回执单号
	private String receiptOprID;//回执填单操作人
	private String receiptOprName;//回执填单操作人姓名
	private Date receiptOprDate;//回执填单操作日期
	private String  auditOpinion;//审核意见


     /*汇款需要导出字段开始*/
	private String currency;//币种默认RMB
	private Date applyDate;//   申请日期   (当日时间格式：20180319)
	private String detailmark;//明细标志
	private String orderNum;//顺序号
	private String payOpenAccountBank;//付款账号开户行
	private String payCardAccount;//付款账号/卡号
	private String payCardName;//付款账号名称/卡名称
	private String recevOpenAccountBank;//收款账号开户行
	private String recevCardProvince;//收款账号省份
	private String recevCardCity;//收款账号地市
	private String receCardDCode;//收款账号地区码  工行：0200， 其他：0000
	private String recevCardAccount;//收款账号
	private String recevCardName;//收款账号名称
	private String money;//金额
	private  String payurpose;//汇款用途
	private String remark;//备注信息
	private String payMode;//付款方式  0，普通；1，加急；3，跨行快汇  默认为0
	private String recevTel;// 收款账户短信通知手机号码
	private String userDefined;//自定义序号
   /*汇款需要导出字段结束*/

	@Override
	public String[] properties() {
		
		String[] properties = {
				currency,
				String.format("%1$tY%1$tm%1$td",applyDate),
				detailmark,
				orderNum,
				payOpenAccountBank,
				payCardAccount,
				payCardName,
				recevOpenAccountBank,
				recevCardProvince,
				recevCardCity,
				receCardDCode,
				recevCardAccount,
		        recevCardName,
				money,
				payurpose,
				remark,
				payMode,
				recevTel,
				userDefined

				};
		return properties;
	}

	public static String[] columnHeadings() {
		String[] titles = { "币种", "日期","明细标志", "顺序号","付款账号开户行","付款账号/卡号","付款账号名称/卡名称","收款账号开户行","收款账号省份","收款账号地市","收款账号地区码","收款账号","收款账号名称","金额","汇款用途","备注信息","汇款方式","收款账户短信通知手机号码","自定义序号"};
		return titles;
	}

    public WithDrawApply() {
	    super();
    }

    public WithDrawApply(String wdaKey, String wdaID, String companyID, String sccid, String exportStaffID, int exportTimes, Date exportDate, String payState, Date payDate, String payOperatorID, String payOperatorName, String tradeCode, String receiptOprID, String receiptOprName, Date receiptOprDate, String currency, Date applyDate, String detailmark, String orderNum, String payOpenAccountBank, String payCardAccount, String payCardName, String recevOpenAccountBank, String recevCardProvince, String recevCardCity, String receCardDCode, String recevCardAccount, String recevCardName, String money, String payurpose, String remark, String payMode, String recevTel, String userDefined) {
        this.wdaKey = wdaKey;
        this.wdaID = wdaID;
        this.companyID = companyID;
        this.sccid = sccid;
        this.exportStaffID = exportStaffID;
        this.exportTimes = exportTimes;
        this.exportDate = exportDate;
        this.payState = payState;
        this.payDate = payDate;
        this.payOperatorID = payOperatorID;
        this.payOperatorName = payOperatorName;
        this.tradeCode = tradeCode;
        this.receiptOprID = receiptOprID;
        this.receiptOprName = receiptOprName;
        this.receiptOprDate = receiptOprDate;
        this.currency = currency;
        this.applyDate = applyDate;
        this.detailmark = detailmark;
        this.orderNum = orderNum;
        this.payOpenAccountBank = payOpenAccountBank;
        this.payCardAccount = payCardAccount;
        this.payCardName = payCardName;
        this.recevOpenAccountBank = recevOpenAccountBank;
        this.recevCardProvince = recevCardProvince;
        this.recevCardCity = recevCardCity;
        this.receCardDCode = receCardDCode;
        this.recevCardAccount = recevCardAccount;
        this.recevCardName = recevCardName;
        this.money = money;
        this.payurpose = payurpose;
        this.remark = remark;
        this.payMode = payMode;
        this.recevTel = recevTel;
        this.userDefined = userDefined;
    }

    public String getWdaKey() {
		return wdaKey;
	}

	public void setWdaKey(String wdaKey) {
		this.wdaKey = wdaKey;
	}

	public String getWdaID() {
		return wdaID;
	}

	public void setWdaID(String wdaID) {
		this.wdaID = wdaID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	public String getExportStaffID() {
		return exportStaffID;
	}

	public void setExportStaffID(String exportStaffID) {
		this.exportStaffID = exportStaffID;
	}

	public int getExportTimes() {
		return exportTimes;
	}

	public void setExportTimes(int exportTimes) {
		this.exportTimes = exportTimes;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPayOperatorID() {
		return payOperatorID;
	}

	public void setPayOperatorID(String payOperatorID) {
		this.payOperatorID = payOperatorID;
	}

	public String getPayOperatorName() {
		return payOperatorName;
	}

	public void setPayOperatorName(String payOperatorName) {
		this.payOperatorName = payOperatorName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getDetailmark() {
		return detailmark;
	}

	public void setDetailark(String detailmark) {
		this.detailmark = detailmark;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPayOpenAccountBank() {
		return payOpenAccountBank;
	}

	public void setPayOpenAccountBank(String payOpenAccountBank) {
		this.payOpenAccountBank = payOpenAccountBank;
	}

	public String getPayCardAccount() {
		return payCardAccount;
	}

	public void setPayCardAccount(String payCardAccount) {
		this.payCardAccount = payCardAccount;
	}

	public String getPayCardName() {
		return payCardName;
	}

	public void setPayCardName(String payCardName) {
		this.payCardName = payCardName;
	}

	public String getRecevOpenAccountBank() {
		return recevOpenAccountBank;
	}

	public void setRecevOpenAccountBank(String recevOpenAccountBank) {
		this.recevOpenAccountBank = recevOpenAccountBank;
	}

	public String getRecevCardProvince() {
		return recevCardProvince;
	}

	public void setRecevCardProvince(String recevCardProvince) {
		this.recevCardProvince = recevCardProvince;
	}

	public String getRecevCardCity() {
		return recevCardCity;
	}

	public void setRecevCardCity(String recevCardCity) {
		this.recevCardCity = recevCardCity;
	}

	public String getReceCardDCode() {
		return receCardDCode;
	}

	public void setReceCardDCode(String receCardDCode) {
		this.receCardDCode = receCardDCode;
	}

	public String getRecevCardAccount() {
		return recevCardAccount;
	}

	public void setRecevCardAccount(String recevCardAccount) {
		this.recevCardAccount = recevCardAccount;
	}

	public String getRecevCardName() {
		return recevCardName;
	}

	public void setRecevCardName(String recevCardName) {
		this.recevCardName = recevCardName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPayurpose() {
		return payurpose;
	}

	public void setPayurpose(String payurpose) {
		this.payurpose = payurpose;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getRecevTel() {
		return recevTel;
	}

	public void setRecevTel(String recevTel) {
		this.recevTel = recevTel;
	}

	public String getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(String userDefined) {
		this.userDefined = userDefined;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getReceiptOprID() {
		return receiptOprID;
	}

	public void setReceiptOprID(String receiptOprID) {
		this.receiptOprID = receiptOprID;
	}

	public String getReceiptOprName() {
		return receiptOprName;
	}

	public void setReceiptOprName(String receiptOprName) {
		this.receiptOprName = receiptOprName;
	}

	public Date getReceiptOprDate() {
		return receiptOprDate;
	}

	public void setReceiptOprDate(Date receiptOprDate) {
		this.receiptOprDate = receiptOprDate;
	}

    public void setDetailmark(String detailmark) {
		this.detailmark = detailmark;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
}
