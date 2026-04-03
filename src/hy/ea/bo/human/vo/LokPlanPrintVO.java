package hy.ea.bo.human.vo;

import hy.plat.bo.BaseBean;

import java.util.List;


/**
 * 员工工作日志VO
 */
public class LokPlanPrintVO implements BaseBean {
	private String companyID;
	private String companyName;
	private String staffID;
	private String staffName;
	private String organizationName; 
	private List<BaseBean> jobSummaryList; //计划汇总列表 
	private List<BaseBean>  logplanList;
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
	public List<BaseBean> getLogplanList() {
		return logplanList;
	}
	public void setLogplanList(List<BaseBean> logplanList) {
		this.logplanList = logplanList;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public List<BaseBean> getJobSummaryList() {
		return jobSummaryList;
	}
	public void setJobSummaryList(List<BaseBean> jobSummaryList) {
		this.jobSummaryList = jobSummaryList;
	} 
	
}
