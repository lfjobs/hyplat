package com.tiantai.nwa.tbank.bean;

import hy.plat.bo.BaseBean;
/**
 * 
 * @author zhb
 *
 */
public class AccountBalanceBean implements BaseBean {
	
	private String accType;//账户性质
	private String accSts;//账户状态
	private String frzAmt;//人工冻结金额
	private String frzBal;//自动冻结金额
	private String valUDLmt;//限制可用额
	private String valMonthLmt;//当月可用额
	private String valDayLmt;//当日可用额
	private String lastAvailBal;//上日可用余额
	private String lastBal;//上日余额
	private String availBal;//可用余额
	private String bal;//余额
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getAccSts() {
		return accSts;
	}
	public void setAccSts(String accSts) {
		this.accSts = accSts;
	}
	public String getFrzAmt() {
		return frzAmt;
	}
	public void setFrzAmt(String frzAmt) {
		this.frzAmt = frzAmt;
	}
	public String getFrzBal() {
		return frzBal;
	}
	public void setFrzBal(String frzBal) {
		this.frzBal = frzBal;
	}
	public String getValUDLmt() {
		return valUDLmt;
	}
	public void setValUDLmt(String valUDLmt) {
		this.valUDLmt = valUDLmt;
	}
	public String getValMonthLmt() {
		return valMonthLmt;
	}
	public void setValMonthLmt(String valMonthLmt) {
		this.valMonthLmt = valMonthLmt;
	}
	public String getValDayLmt() {
		return valDayLmt;
	}
	public void setValDayLmt(String valDayLmt) {
		this.valDayLmt = valDayLmt;
	}
	public String getLastAvailBal() {
		return lastAvailBal;
	}
	public void setLastAvailBal(String lastAvailBal) {
		this.lastAvailBal = lastAvailBal;
	}
	public String getLastBal() {
		return lastBal;
	}
	public void setLastBal(String lastBal) {
		this.lastBal = lastBal;
	}
	public String getAvailBal() {
		return availBal;
	}
	public void setAvailBal(String availBal) {
		this.availBal = availBal;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}	

}
