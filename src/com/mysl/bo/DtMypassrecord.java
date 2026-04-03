package com.mysl.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMypassrecord entity. @author MyEclipse Persistence Tools
 */

public class DtMypassrecord implements java.io.Serializable,BaseBean {
	// Fields

	private String passkey;
	private String passid;		//业务id
	private String taskid;		//任务id
	private String taskname;	//任务名称
	private String proid;		//项目id
	private String projectname;	//项目名称
	private String receiverid;	//接收人id
	private String passerid;	//传阅人id
	private String passername;	//传阅人名称
	private Date passtime;		//传阅时间
	private String readstatus;	//传阅状态
	private String receiverName;//  接收人名称 zc 后加  
	// Constructors
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "项目名称", "任务编号", "任务标题","接收人","接收时间","执行人", "部门", "任务类型", "缓急",
				"开始时间", "计划完成时间", "实际完成时间", "派发人"};
		return titles;
	}
	
	/** default constructor */
	public DtMypassrecord() {
	}

	/** full constructor */
	public DtMypassrecord(String passid, String taskid, String taskname,
			String proid, String projectname, String receiverid,
			String passerid, String passername, Date passtime, String readstatus) {
		this.passid = passid;
		this.taskid = taskid;
		this.taskname = taskname;
		this.proid = proid;
		this.projectname = projectname;
		this.receiverid = receiverid;
		this.passerid = passerid;
		this.passername = passername;
		this.passtime = passtime;
		this.readstatus = readstatus;
	}

	// Property accessors

	public String getPasskey() {
		return this.passkey;
	}

	public void setPasskey(String passkey) {
		this.passkey = passkey;
	}

	public String getPassid() {
		return this.passid;
	}

	public void setPassid(String passid) {
		this.passid = passid;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getProid() {
		return this.proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getReceiverid() {
		return this.receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	public String getPasserid() {
		return this.passerid;
	}

	public void setPasserid(String passerid) {
		this.passerid = passerid;
	}

	public String getPassername() {
		return this.passername;
	}

	public void setPassername(String passername) {
		this.passername = passername;
	}

	public Date getPasstime() {
		return this.passtime;
	}

	public void setPasstime(Date passtime) {
		this.passtime = passtime;
	}

	public String getReadstatus() {
		return this.readstatus;
	}

	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
}