package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 请假加班
 * AttendExtleave entity. 
 * @author MyEclipse Persistence Tools
 */

public class AttendExtleave implements BaseBean {


	private String extleaveKey;
	private String extleaveId;
	private String groupCompanySn;
	private String companyId;
	private String staffId;
	private String staffCode;
	private String staffName;
	private String organizationId;
	private String orgName;
	private String approveId;
	private String approveName;
	private Date days;
	private String leaveWork;
	private Date beginTime;
	private Date endTime;
	private Long sumTime;
	private String remark;
	private String state;
	private Date ctime;
	private String cname;
	private Date utime;
	private String uname;
	private String esastate;
	private String begin;
	private String end;

	public String getExtleaveKey() {
		return this.extleaveKey;
	}

	public void setExtleaveKey(String extleaveKey) {
		this.extleaveKey = extleaveKey;
	}

	public String getExtleaveId() {
		return this.extleaveId;
	}

	public void setExtleaveId(String extleaveId) {
		this.extleaveId = extleaveId;
	}

	public String getGroupCompanySn() {
		return this.groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
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

	public String getApproveId() {
		return this.approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}

	public String getApproveName() {
		return this.approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	public Date getDays() {
		return this.days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public String getLeaveWork() {
		return this.leaveWork;
	}

	public void setLeaveWork(String leaveWork) {
		this.leaveWork = leaveWork;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Long getSumTime() {
		return sumTime;
	}

	public void setSumTime(Long sumTime) {
		this.sumTime = sumTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getEsastate() {
		return this.esastate;
	}

	public void setEsastate(String esastate) {
		this.esastate = esastate;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}