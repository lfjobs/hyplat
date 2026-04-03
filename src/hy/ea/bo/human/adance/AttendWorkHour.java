package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 工作时间管理
 * WorkHour entity.
 * @author lwz 2014.10.10 
 */

public class AttendWorkHour implements BaseBean {


	private String workHourKey;
	private String workHourId;
	private String companyId;
	private String groupCompanySn;
	private String workName;//01上 02休 03下
	private String begintime;
	private String endtime;
	private Double sumtime;
	private String status;//当日00  次日01
	private String uname;
	private Date ctime;
	private String cname;
	private Date utime;


	public String getWorkHourKey() {
		return this.workHourKey;
	}

	public void setWorkHourKey(String workHourKey) {
		this.workHourKey = workHourKey;
	}

	public String getWorkHourId() {
		return this.workHourId;
	}

	public void setWorkHourId(String workHourId) {
		this.workHourId = workHourId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getGroupCompanySn() {
		return this.groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getBegintime() {
		return this.begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Double getSumtime() {
		return this.sumtime;
	}

	public void setSumtime(Double sumtime) {
		this.sumtime = sumtime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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

}