package com.mysl.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyattach entity. @author MyEclipse Persistence Tools
 */

public class DtMyattachrecord implements java.io.Serializable,BaseBean {

	// Fields

	private String recordkey;
	private String recordid;
	private String attachid;//附件ID
	private Date operatetime;//操作时间
	private String filename;//文件名
	private String filepath;//文件路径
	private String taskid;
	private String proid;
	private String status;//状态：03 上传，01作废,00恢复
	private String staffid;
	private String staffname;
	

	// Constructors

	/** default constructor */
	public DtMyattachrecord() {
	}

	/** full constructor */
	public DtMyattachrecord(String attachid, String filepath, String filename,
			String fileformat, String taskid, String proid, String status,
			String staffid, String staffname, Date uploadtime) {
		this.attachid = attachid;
		this.filepath = filepath;
		this.filename = filename;
		this.taskid = taskid;
		this.proid = proid;
		this.status = status;
		this.staffid = staffid;
		this.staffname = staffname;
	}

	public String getRecordkey() {
		return recordkey;
	}

	public void setRecordkey(String recordkey) {
		this.recordkey = recordkey;
	}

	

	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}

	public String getAttachid() {
		return attachid;
	}

	public void setAttachid(String attachid) {
		this.attachid = attachid;
	}

	public Date getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getProid() {
		return proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	// Property accessors

	
	
    
}