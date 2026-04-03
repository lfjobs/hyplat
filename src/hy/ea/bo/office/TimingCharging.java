package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/*
 * 计时收费
 * */
public class TimingCharging implements BaseBean,Serializable {
	private String tcKey;
	private String tcId;//主键
	private String carNumber;//车牌号
	private String siteId;//场地id
	private String staffId;//人员id
	private String remark;//备注
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String journalnum;//订单序列号
	private String state;//00:已付款,01:未付款,02:已作废
	
	
	public String getTcKey() {
		return tcKey;
	}
	public void setTcKey(String tcKey) {
		this.tcKey = tcKey;
	}
	public String getTcId() {
		return tcId;
	}
	public void setTcId(String tcId) {
		this.tcId = tcId;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
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
	public String getJournalnum() {
		return journalnum;
	}
	public void setJournalnum(String journalnum) {
		this.journalnum = journalnum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
