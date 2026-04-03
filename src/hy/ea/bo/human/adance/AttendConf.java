package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 考勤预设
 * AttendConf entity. 
 * @author lwz 2014.10.11
 */

public class AttendConf implements BaseBean {

	

	private String attendConfKey;
	private String attendConfId;
	private String companyId;
	private String confname;  	//考勤项目名称
	private String happents;	//发生时间状态 00 不限01 时间
	private String playtime;	//发生起时间
	private String stopstus;	//止时间状态00 当日01 次日
	private String stoptime;	//发生止时间
	private String minstus;		//最小单位状态00 不限01 天02 小时03 分钟
	private String minnum;		//最小单位数
	private String maxstus;		//最大单位状态00 不限01 天02 小时03 分钟
	private String maxnum;		//最大单位数
	private String stus;		//00 统计 01 按次奖励 02 按次扣除 03 时间奖励 04 时间扣除
	private Long stusnum;		//奖惩单位数
	private String remarks;		//备注
	private Date ctime;
	private String cname;
	private Date utime;
	private String uname;
	private String groupCompanySn;
	private String confstus;	//考勤项目预设00  可修改不可删除  01都可以  	


	public String getAttendConfKey() {
		return this.attendConfKey;
	}

	public void setAttendConfKey(String attendConfKey) {
		this.attendConfKey = attendConfKey;
	}

	public String getAttendConfId() {
		return this.attendConfId;
	}

	public void setAttendConfId(String attendConfId) {
		this.attendConfId = attendConfId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getConfname() {
		return this.confname;
	}

	public void setConfname(String confname) {
		this.confname = confname;
	}

	public String getConfstus() {
		return this.confstus;
	}

	public void setConfstus(String confstus) {
		this.confstus = confstus;
	}

	public String getHappents() {
		return this.happents;
	}

	public void setHappents(String happents) {
		this.happents = happents;
	}

	public String getPlaytime() {
		return this.playtime;
	}

	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}

	public String getStopstus() {
		return this.stopstus;
	}

	public void setStopstus(String stopstus) {
		this.stopstus = stopstus;
	}

	public String getStoptime() {
		return this.stoptime;
	}

	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}

	public String getMinstus() {
		return this.minstus;
	}

	public void setMinstus(String minstus) {
		this.minstus = minstus;
	}

	public String getMinnum() {
		return this.minnum;
	}

	public void setMinnum(String minnum) {
		this.minnum = minnum;
	}

	public String getMaxstus() {
		return this.maxstus;
	}

	public void setMaxstus(String maxstus) {
		this.maxstus = maxstus;
	}

	public String getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(String maxnum) {
		this.maxnum = maxnum;
	}

	public String getStus() {
		return this.stus;
	}

	public void setStus(String stus) {
		this.stus = stus;
	}

	
	public Long getStusnum() {
		return stusnum;
	}

	public void setStusnum(Long stusnum) {
		this.stusnum = stusnum;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getGroupCompanySn() {
		return this.groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

}