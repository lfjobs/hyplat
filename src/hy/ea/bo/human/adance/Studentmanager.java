package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Studentmanager entity. @author MyEclipse Persistence Tools
 */

public class Studentmanager implements BaseBean{

	// 学员管理 （学生管理显示隐藏）
	private String studentmanagerkey;
	private String studentmanagerid;
	private String companyid;
	private String periodmanager;//学时管理
	private String changemanager;//变动管理
	private String missedlesson;//补课管理
	private String classmanager;//课程管理
	private String attendancemanager;//考勤管理
	private String awardmanager;//奖励管理
	private String produtmanagerr;// 产品管理
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;

	// Constructors

	/** default constructor */
	public Studentmanager() {
	}

	/** full constructor */
	public Studentmanager(String studentmanagerid, String companyid,
			String periodmanager, String changemanager, String missedlesson,
			String classmanager, String attendancemanager, String awardmanager,
			String produtmanagerr, String cname, Date ctime, String uname,
			Date utime) {
		this.studentmanagerid = studentmanagerid;
		this.companyid = companyid;
		this.periodmanager = periodmanager;
		this.changemanager = changemanager;
		this.missedlesson = missedlesson;
		this.classmanager = classmanager;
		this.attendancemanager = attendancemanager;
		this.awardmanager = awardmanager;
		this.produtmanagerr = produtmanagerr;
		this.cname = cname;
		this.ctime = ctime;
		this.uname = uname;
		this.utime = utime;
	}

	// Property accessors

	public String getStudentmanagerkey() {
		return this.studentmanagerkey;
	}

	public void setStudentmanagerkey(String studentmanagerkey) {
		this.studentmanagerkey = studentmanagerkey;
	}

	public String getStudentmanagerid() {
		return this.studentmanagerid;
	}

	public void setStudentmanagerid(String studentmanagerid) {
		this.studentmanagerid = studentmanagerid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getPeriodmanager() {
		return this.periodmanager;
	}

	public void setPeriodmanager(String periodmanager) {
		this.periodmanager = periodmanager;
	}

	public String getChangemanager() {
		return this.changemanager;
	}

	public void setChangemanager(String changemanager) {
		this.changemanager = changemanager;
	}

	public String getMissedlesson() {
		return this.missedlesson;
	}

	public void setMissedlesson(String missedlesson) {
		this.missedlesson = missedlesson;
	}

	public String getClassmanager() {
		return this.classmanager;
	}

	public void setClassmanager(String classmanager) {
		this.classmanager = classmanager;
	}

	public String getAttendancemanager() {
		return this.attendancemanager;
	}

	public void setAttendancemanager(String attendancemanager) {
		this.attendancemanager = attendancemanager;
	}

	public String getAwardmanager() {
		return this.awardmanager;
	}

	public void setAwardmanager(String awardmanager) {
		this.awardmanager = awardmanager;
	}

	public String getProdutmanagerr() {
		return this.produtmanagerr;
	}

	public void setProdutmanagerr(String produtmanagerr) {
		this.produtmanagerr = produtmanagerr;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

}