package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.util.List;


/**
 * 员工工作日志VO
 */
public class LokBookPrintVO implements BaseBean {
	private String LokBookPrintVOID;
	private String companyID;
	private String companyName;
	private String staffID;
	private String staffName;
	private String 	todaydate;//当天日期
	private String 	bisects;//得分小记
	private String 	momey;//工资小记
	
	private List<BaseBean>  logbookList;
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
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getTodaydate() {
		return todaydate;
	}
	public void setTodaydate(String todaydate) {
		this.todaydate = todaydate;
	}
	public List<BaseBean> getLogbookList() {
		return logbookList;
	}
	public void setLogbookList(List<BaseBean> logbookList) {
		this.logbookList = logbookList;
	}
	public String getBisects() {
		return bisects;
	}
	public void setBisects(String bisects) {
		this.bisects = bisects;
	}
	public String getMomey() {
		return momey;
	}
	public void setMomey(String momey) {
		this.momey = momey;
	}
	public String getLokBookPrintVOID() {
		return LokBookPrintVOID;
	}
	public void setLokBookPrintVOID(String lokBookPrintVOID) {
		LokBookPrintVOID = lokBookPrintVOID;
	}

}
