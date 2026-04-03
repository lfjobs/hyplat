package com.mysl.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyattach entity. @author MyEclipse Persistence Tools
 */

public class DtMyattach implements java.io.Serializable,BaseBean {

	// Fields

	private String attachkey;
	private String attachid;
	private String filepath;
	private String filename;
	private String fileformat;
	private String taskid;
	private String proid;
	private String status;
	private String staffid;
	private String staffname;
	private Date uploadtime;
	private String type;//是作图还是任务所附带的附件
	// Constructors

	/** default constructor */
	public DtMyattach() {
	}

	/** full constructor */
	public DtMyattach(String attachid, String filepath, String filename,
			String fileformat, String taskid, String proid, String status,
			String staffid, String staffname, Date uploadtime) {
		this.attachid = attachid;
		this.filepath = filepath;
		this.filename = filename;
		this.fileformat = fileformat;
		this.taskid = taskid;
		this.proid = proid;
		this.status = status;
		this.staffid = staffid;
		this.staffname = staffname;
		this.uploadtime = uploadtime;
	}

	// Property accessors

	public String getAttachkey() {
		return this.attachkey;
	}

	public void setAttachkey(String attachkey) {
		this.attachkey = attachkey;
	}

	public String getAttachid() {
		return this.attachid;
	}

	public void setAttachid(String attachid) {
		this.attachid = attachid;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileformat() {
		return this.fileformat;
	}

	public void setFileformat(String fileformat) {
		this.fileformat = fileformat;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}