package hy.ea.bo.production.resume;

import hy.plat.bo.BaseBean;

/**
 * @author zzl 求职意向
 * */
public class JobWanted implements BaseBean {
	private String jobWantedKey;// 主键
	private String jobWantedId;// id
	private String staffID;// 人员id
	private String resumeID;// 个人简历id
	private String work;// 工作性质
	private String region;// 工作地区
	private String position;// 职位类别
	private String industry;// 行业类别
	private String salary;// 期望薪资
	private String status;// 求职状态
	private String degree;//进度

	public String getJobWantedKey() {
		return jobWantedKey;
	}

	public void setJobWantedKey(String jobWantedKey) {
		this.jobWantedKey = jobWantedKey;
	}

	public String getJobWantedId() {
		return jobWantedId;
	}

	public void setJobWantedId(String jobWantedId) {
		this.jobWantedId = jobWantedId;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getResumeID() {
		return resumeID;
	}

	public void setResumeID(String resumeID) {
		this.resumeID = resumeID;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

}
