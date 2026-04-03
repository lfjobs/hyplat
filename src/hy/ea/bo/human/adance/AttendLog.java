package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtHrAttendLog entity. 
 * @author MyEclipse Persistence Tools
 */

public class AttendLog implements BaseBean{

	// Fields

	private String attendLogKey;
	private String attendLogId;
	private String staffId;
	private String workcalen;		//工作日历考勤
	private String status;			//考勤设置状态外键
	private String signcome;		//签到
	private String signgo;			//签退
	private String organizationid;	
	private String companyid;		
	private String groupcompanysn;	
	private Date ctime;				
	private String cname;
	private Date utime;
	private String uname;
	private String remarks;



	public String getAttendLogKey() {
		return this.attendLogKey;
	}

	public void setAttendLogKey(String attendLogKey) {
		this.attendLogKey = attendLogKey;
	}

	public String getAttendLogId() {
		return this.attendLogId;
	}

	public void setAttendLogId(String attendLogId) {
		this.attendLogId = attendLogId;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getWorkcalen() {
		return this.workcalen;
	}

	public void setWorkcalen(String workcalen) {
		this.workcalen = workcalen;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSigncome() {
		return this.signcome;
	}

	public void setSigncome(String signcome) {
		this.signcome = signcome;
	}

	public String getSigngo() {
		return this.signgo;
	}

	public void setSigngo(String signgo) {
		this.signgo = signgo;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getGroupcompanysn() {
		return this.groupcompanysn;
	}

	public void setGroupcompanysn(String groupcompanysn) {
		this.groupcompanysn = groupcompanysn;
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

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}