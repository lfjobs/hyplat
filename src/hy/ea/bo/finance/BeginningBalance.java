package hy.ea.bo.finance;

import java.util.Date;

import hy.plat.bo.BaseBean;
/**
 * BeginningBalance   期初余额
 * @author lf
 *
 */
public class BeginningBalance implements BaseBean {
	private String BeginningBalanceID;    
	private String BeginningBalanceKey;
	private Date Customizedate;    //期初余额设定时间
	private String companyID;	   //公司外键
	private String cashBalance;	   //现金期初余额
	private String bankBalance;		//银行期初余额
	private String payablesBalance;		//应付款期初余额
	private String receivablesBalance;  //应收款期初余额
	private String bbtype; 				//保存状态     日记账保存  00   设置保存01  
	public String getBeginningBalanceID() {
		return BeginningBalanceID;
	}
	public void setBeginningBalanceID(String beginningBalanceID) {
		BeginningBalanceID = beginningBalanceID;
	}
	public String getBeginningBalanceKey() {
		return BeginningBalanceKey;
	}
	public void setBeginningBalanceKey(String beginningBalanceKey) {
		BeginningBalanceKey = beginningBalanceKey;
	}
	public Date getCustomizedate() {
		return Customizedate;
	}
	public void setCustomizedate(Date customizedate) {
		Customizedate = customizedate;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(String cashBalance) {
		this.cashBalance = cashBalance;
	}
	public String getBankBalance() {
		return bankBalance;
	}
	public void setBankBalance(String bankBalance) {
		this.bankBalance = bankBalance;
	}
	public String getPayablesBalance() {
		return payablesBalance;
	}
	public void setPayablesBalance(String payablesBalance) {
		this.payablesBalance = payablesBalance;
	}
	public String getReceivablesBalance() {
		return receivablesBalance;
	}
	public void setReceivablesBalance(String receivablesBalance) {
		this.receivablesBalance = receivablesBalance;
	}
	public String getBbtype() {
		return bbtype;
	}
	public void setBbtype(String bbtype) {
		this.bbtype = bbtype;
	}
	
}
