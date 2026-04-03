package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtWorkcalendar entity. @author MyEclipse Persistence Tools
 */

/**
 * @author Administrator
 *
 */
public class WorkCalendar implements BaseBean {

	private String workcalendarkey;
	private String workcalendarid;
	private String companyid;
	private Date days;
	private String week;
	private String status;
	private String uname;
	private String groupCompanySn;
	private Date ctime;
	private String cname;
	private Date utime;



	public String getWorkcalendarkey() {
		return this.workcalendarkey;
	}

	public void setWorkcalendarkey(String workcalendarkey) {
		this.workcalendarkey = workcalendarkey;
	}

	public String getWorkcalendarid() {
		return this.workcalendarid;
	}

	public void setWorkcalendarid(String workcalendarid) {
		this.workcalendarid = workcalendarid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public Date getDays() {
		return this.days;
	}

	public void setDays(Date days) {
		this.days = days;
	}

	public String getWeek() {
		return this.week;
	}

	public void setWeek(String week) {
		this.week = week;
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

	public String getGroupCompanySn() {
		return this.groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
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