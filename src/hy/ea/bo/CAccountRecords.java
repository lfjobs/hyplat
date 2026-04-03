package hy.ea.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class CAccountRecords  implements BaseBean{
	private String caccountRecordsKey;
	private String caccountRecordsID;
	private String companyID;
	private String accountID;
	private Date startDate;//权限开始时间
	private Date endDate;//权限结束时间
	private String beforePeople;//权限使用人
	private String afterPeople;//权限移交人
	
	
	public String getCaccountRecordsKey() {
		return caccountRecordsKey;
	}
	public void setCaccountRecordsKey(String caccountRecordsKey) {
		this.caccountRecordsKey = caccountRecordsKey;
	}
	public String getCaccountRecordsID() {
		return caccountRecordsID;
	}
	public void setCaccountRecordsID(String caccountRecordsID) {
		this.caccountRecordsID = caccountRecordsID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
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
	public String getBeforePeople() {
		return beforePeople;
	}
	public void setBeforePeople(String beforePeople) {
		this.beforePeople = beforePeople;
	}
	public String getAfterPeople() {
		return afterPeople;
	}
	public void setAfterPeople(String afterPeople) {
		this.afterPeople = afterPeople;
	}

}
