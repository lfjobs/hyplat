package hy.ea.bo.invoicing.voucher;

import java.math.BigDecimal;

import hy.plat.bo.BaseBean;

/**
 * 日记帐流水表（临时表）
 * @author lf
 *
 */

public class BcDay implements BaseBean {

	// Fields

	private String workKey;
	private String workId;//作业别ID  
	private String comId;//公司ID
	private String orgId;//部门ID
	private String subjectsname;//科目  
	private String subjectscode;//科目编号
	private String subjectsid;//科目  id
	private String vouEx;//科目说明  
	private String journalnum;//凭证号码  
	private String voucherdate;//凭证日期  
	private String bankName;//银行开户行名称  
	private String bankStaff;//开户人  
	private BigDecimal BMoney;//前日余额  
	private BigDecimal SMoney;//本期收入 
	private BigDecimal ZMoney;//本期支出  
	private BigDecimal cbMoney;//本日结余 
	private String sort;//排序 
	private String tabType;//报表类别 1:明细表 2：汇总表
	private String vouchernum;//凭证序号 
	private String yhzh;//银行帐号  
	private String reasonthing;//摘要
	private String currencyid;//币别 

	// Constructors

	/** default constructor */
	public BcDay() {
	}

	/** full constructor */
	public BcDay(String workId, String comId,String orgId, String subjectsname,String subjectscode,
			String subjectsid, String vouEx, String journalnum,
			String voucherdate, String bankName, String bankStaff,
			BigDecimal BMoney, BigDecimal SMoney, BigDecimal ZMoney, BigDecimal cbMoney,
			String sort, String tabType, String vouchernum, String yhzh,
			String reasonthing, String currencyid) {
		this.workId = workId;
		this.comId = comId;
		this.orgId = orgId;
		this.subjectsname = subjectsname;
		this.subjectscode = subjectscode;
		this.subjectsid = subjectsid;
		this.vouEx = vouEx;
		this.journalnum = journalnum;
		this.voucherdate = voucherdate;
		this.bankName = bankName;
		this.bankStaff = bankStaff;
		this.BMoney = BMoney;
		this.SMoney = SMoney;
		this.ZMoney = ZMoney;
		this.cbMoney = cbMoney;
		this.sort = sort;
		this.tabType = tabType;
		this.vouchernum = vouchernum;
		this.yhzh = yhzh;
		this.reasonthing = reasonthing;
		this.currencyid = currencyid;
	}

	// Property accessors

	public String getWorkKey() {
		return this.workKey;
	}

	public void setWorkKey(String workKey) {
		this.workKey = workKey;
	}

	public String getWorkId() {
		return this.workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getComId() {
		return this.comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSubjectsname() {
		return this.subjectsname;
	}

	public void setSubjectsname(String subjectsname) {
		this.subjectsname = subjectsname;
	}
	
	public String getSubjectscode() {
		return subjectscode;
	}

	public void setSubjectscode(String subjectscode) {
		this.subjectscode = subjectscode;
	}

	public String getSubjectsid() {
		return this.subjectsid;
	}

	public void setSubjectsid(String subjectsid) {
		this.subjectsid = subjectsid;
	}

	public String getVouEx() {
		return this.vouEx;
	}

	public void setVouEx(String vouEx) {
		this.vouEx = vouEx;
	}

	public String getJournalnum() {
		return this.journalnum;
	}

	public void setJournalnum(String journalnum) {
		this.journalnum = journalnum;
	}

	public String getVoucherdate() {
		return this.voucherdate;
	}

	public void setVoucherdate(String voucherdate) {
		this.voucherdate = voucherdate;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankStaff() {
		return this.bankStaff;
	}

	public void setBankStaff(String bankStaff) {
		this.bankStaff = bankStaff;
	}

	public BigDecimal getBMoney() {
		return this.BMoney;
	}

	public void setBMoney(BigDecimal BMoney) {
		this.BMoney = BMoney;
	}

	public BigDecimal getSMoney() {
		return this.SMoney;
	}

	public void setSMoney(BigDecimal SMoney) {
		this.SMoney = SMoney;
	}

	public BigDecimal getZMoney() {
		return this.ZMoney;
	}

	public void setZMoney(BigDecimal ZMoney) {
		this.ZMoney = ZMoney;
	}

	public BigDecimal getCbMoney() {
		return this.cbMoney;
	}

	public void setCbMoney(BigDecimal cbMoney) {
		this.cbMoney = cbMoney;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTabType() {
		return this.tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public String getVouchernum() {
		return this.vouchernum;
	}

	public void setVouchernum(String vouchernum) {
		this.vouchernum = vouchernum;
	}

	public String getYhzh() {
		return this.yhzh;
	}

	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}

	public String getReasonthing() {
		return this.reasonthing;
	}

	public void setReasonthing(String reasonthing) {
		this.reasonthing = reasonthing;
	}

	public String getCurrencyid() {
		return this.currencyid;
	}

	public void setCurrencyid(String currencyid) {
		this.currencyid = currencyid;
	}

}