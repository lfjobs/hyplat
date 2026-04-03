package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 考勤调整jilu 
 * AttendTrim entity. 
 * @author MyEclipse Persistence Tools
 */

public class AttendTrim implements BaseBean {


	private String attendTrimKey;
	private String attendTrimId;
	private String companyId;
	private String staffId;
	private String staffCode;
	private String staffName;
	private String organizationId;
	private String orgName;
	private Date days;
	private String oldStus;
	private String newStus;
	private String oldtime;
	private String newtime;
	private String remark;
	private String audname;
	private Date ctime;
	private String cname;
	private Date utime;
	private String uname;
	private String status;
	private String audstate;


	public String getAttendTrimKey() {
		return this.attendTrimKey;
	}

	public void setAttendTrimKey(String attendTrimKey) {
		this.attendTrimKey = attendTrimKey;
	}

	public String getAttendTrimId() {
		return this.attendTrimId;
	}

	public void setAttendTrimId(String attendTrimId) {
		this.attendTrimId = attendTrimId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffCode() {
		return this.staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getDays() {
		return this.days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public String getOldStus() {
		return this.oldStus;
	}

	public void setOldStus(String oldStus) {
		this.oldStus = oldStus;
	}

	public String getNewStus() {
		return this.newStus;
	}

	public void setNewStus(String newStus) {
		this.newStus = newStus;
	}

	public String getOldtime() {
		return this.oldtime;
	}

	public void setOldtime(String oldtime) {
		this.oldtime = oldtime;
	}

	public String getNewtime() {
		return this.newtime;
	}

	public void setNewtime(String newtime) {
		this.newtime = newtime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAudname() {
		return this.audname;
	}

	public void setAudname(String audname) {
		this.audname = audname;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAudstate() {
		return this.audstate;
	}

	public void setAudstate(String audstate) {
		this.audstate = audstate;
	}

}