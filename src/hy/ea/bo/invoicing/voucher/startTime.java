package hy.ea.bo.invoicing.voucher;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 账号启用时间
 * 陈婷
 */
public class startTime implements BaseBean{
	private String startKey;        		//主键
	private String startID;         		//业务主键
	private String companyID;				//公司ID
	private String companyName;				//公司名称
	private Date startTime;					//启用时间
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}
	public String getStartID() {
		return startID;
	}
	public void setStartID(String startID) {
		this.startID = startID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}
