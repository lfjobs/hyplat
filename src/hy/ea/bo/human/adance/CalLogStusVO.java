package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * 工作日志 ,个人考勤,考勤状态.
 * 
 * @author lwz
 *
 */
@SuppressWarnings("rawtypes")
public class CalLogStusVO implements BaseBean {

	private String workcalendarid;
	private String companyid;
	private String days;
	private String week;
	private String status;
	private AttendLog listLog; //个人考勤记录
	private List listStus; //考勤状态
	


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

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
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

	public AttendLog getListLog() {
		return listLog;
	}

	public void setListLog(AttendLog listLog) {
		this.listLog = listLog;
	}

	public List getListStus() {
		return listStus;
	}

	public void setListStus(List listStus) {
		this.listStus = listStus;
	}


	
}