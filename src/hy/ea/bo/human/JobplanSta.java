package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * 工作计划责任人
 */

public class JobplanSta implements BaseBean {


	private String jobplanstaKey;
	private String jobplanstaID;
	private String jobPlanID;
	private String staffID;
	private String stusts;
	private String companyID;
	private String orgID;
	private String staffName;

	
	public String getJobplanstaKey() {
		return jobplanstaKey;
	}

	public void setJobplanstaKey(String jobplanstaKey) {
		this.jobplanstaKey = jobplanstaKey;
	}

	public String getJobplanstaID() {
		return jobplanstaID;
	}

	public void setJobplanstaID(String jobplanstaID) {
		this.jobplanstaID = jobplanstaID;
	}

	public String getJobPlanID() {
		return jobPlanID;
	}

	public void setJobPlanID(String jobPlanID) {
		this.jobPlanID = jobPlanID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStusts() {
		return this.stusts;
	}

	public void setStusts(String stusts) {
		this.stusts = stusts;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}