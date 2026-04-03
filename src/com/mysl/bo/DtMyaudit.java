package com.mysl.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyaudit entity. @author MyEclipse Persistence Tools
 */

public class DtMyaudit implements java.io.Serializable,BaseBean,Cloneable{

	// Fields

	private String auditkey;  //业务主键
	private String auditid;   //业务主键
	private String taskid;   //任务id
	private String proid;	 //项目id
	private String taskstatus;	//任务状态  00:生产设计阶段提交审核；01:设计完成阶段提交审核；02档案管理申请修改
	private Date applytime;		//申请审核时间
	private Date audittime;		//审核时间
	private String applyerid;	//申请人id
	private String applyername;		//申请人姓名
	private String applyorg;   //申请人部门id
	private String applyorgname;   //申请人部门名称
	private String applycompanyid;  //申请人公司id
	private String auditorid;  //审核人id
	private String auditorname;  //审核人姓名
	private String auditorcompanyid;  //审核人公司id
	private String auditorcompanyname;  //审核人公司名称
	private String comments;  //审核意见 b
	
	/****************娄飞***************/
	private String auditorstatus;   //审核状态   '01'未审核  '02'已审核 '03'驳回
	private String taskname;    //任务名称
	private String proname;		//项目名称
	private String applyerupdate;  //申请修改状态    '01'正常    '02'申请修改
	
	/****************张灿***************/
	private String applycompanyName;//申请人公司名称
	private String auditororgID;//审核人部门ID
	private String auditororgName;//审核人部门名称
	private String sqrid;  
	// Constructors
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单位", "项目名称", "任务编号", "任务标题","申请人","申请时间","执行人", "部门", "任务类型", "缓急",
				"开始时间", "计划完成时间", "实际完成时间", "派发人","任务阶段","审核状态"};
		return titles;
	}
	
	/** default constructor */
	public DtMyaudit() {
	}

	/** full constructor */
	public DtMyaudit(String auditid, String taskid, String proid,
			String taskstatus, Date applytime, Date audittime,
			String applyerid, String applyername, String applyorg,
			String applyorgname, String applycompanyid, String auditorid,
			String auditorname, String auditorcompanyid,
			String auditorcompanyname, String comments,String auditorstatus,
			String taskname,String proname) {
		this.auditid = auditid;
		this.taskid = taskid;
		this.proid = proid;
		this.taskstatus = taskstatus;
		this.applytime = applytime;
		this.audittime = audittime;
		this.applyerid = applyerid;
		this.applyername = applyername;
		this.applyorg = applyorg;
		this.applyorgname = applyorgname;
		this.applycompanyid = applycompanyid;
		this.auditorid = auditorid;
		this.auditorname = auditorname;
		this.auditorcompanyid = auditorcompanyid;
		this.auditorcompanyname = auditorcompanyname;
		this.comments = comments;
		this.auditorstatus=auditorstatus;
		this.taskname=taskname;
		this.proname=proname;
	}

	// Property accessors

	public String getAuditkey() {
		return this.auditkey;
	}

	public void setAuditkey(String auditkey) {
		this.auditkey = auditkey;
	}

	public String getAuditid() {
		return this.auditid;
	}

	public void setAuditid(String auditid) {
		this.auditid = auditid;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getProid() {
		return this.proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getTaskstatus() {
		return this.taskstatus;
	}

	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}

	public Date getApplytime() {
		return this.applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public Date getAudittime() {
		return this.audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public String getApplyerid() {
		return this.applyerid;
	}

	public void setApplyerid(String applyerid) {
		this.applyerid = applyerid;
	}

	public String getApplyername() {
		return this.applyername;
	}

	public void setApplyername(String applyername) {
		this.applyername = applyername;
	}

	public String getApplyorg() {
		return this.applyorg;
	}

	public void setApplyorg(String applyorg) {
		this.applyorg = applyorg;
	}

	public String getApplyorgname() {
		return this.applyorgname;
	}

	public void setApplyorgname(String applyorgname) {
		this.applyorgname = applyorgname;
	}

	public String getApplycompanyid() {
		return this.applycompanyid;
	}

	public void setApplycompanyid(String applycompanyid) {
		this.applycompanyid = applycompanyid;
	}

	public String getAuditorid() {
		return this.auditorid;
	}

	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}

	public String getAuditorname() {
		return this.auditorname;
	}

	public void setAuditorname(String auditorname) {
		this.auditorname = auditorname;
	}

	public String getAuditorcompanyid() {
		return this.auditorcompanyid;
	}

	public void setAuditorcompanyid(String auditorcompanyid) {
		this.auditorcompanyid = auditorcompanyid;
	}

	public String getAuditorcompanyname() {
		return this.auditorcompanyname;
	}

	public void setAuditorcompanyname(String auditorcompanyname) {
		this.auditorcompanyname = auditorcompanyname;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAuditorstatus() {
		return auditorstatus;
	}

	public void setAuditorstatus(String auditorstatus) {
		this.auditorstatus = auditorstatus;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getApplycompanyName() {
		return applycompanyName;
	}

	public void setApplycompanyName(String applycompanyName) {
		this.applycompanyName = applycompanyName;
	}

	public String getAuditororgID() {
		return auditororgID;
	}

	public void setAuditororgID(String auditororgID) {
		this.auditororgID = auditororgID;
	}

	public String getAuditororgName() {
		return auditororgName;
	}

	public void setAuditororgName(String auditororgName) {
		this.auditororgName = auditororgName;
	}
	
	public String getApplyerupdate() {
		return applyerupdate;
	}

	public void setApplyerupdate(String applyerupdate) {
		this.applyerupdate = applyerupdate;
	}

	public String getSqrid() {
		return sqrid;
	}

	public void setSqrid(String sqrid) {
		this.sqrid = sqrid;
	}
	
}