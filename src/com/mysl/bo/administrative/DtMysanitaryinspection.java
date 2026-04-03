package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMysanitaryinspection entity. @author MyEclipse Persistence Tools
 */

public class DtMysanitaryinspection implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	private String key;
	private String id;
	private String status;
	private String serialnumber;
	private String proposerid;
	private String proposername;
	private String companyid;
	private String companyname;
	private String organizationid;
	private String organizationname;
	private String staffid;
	private String staffname;
	private Date addtime;
	private String department;
	private String score;
	private String note;

	@Override
	public String[] properties() {
		String[] properties = {  serialnumber, companyname,organizationname, proposername,
				 String.format("%1$tF", addtime),  department, score,staffname, 
				"01".endsWith(status)?"未审核":
				"02".endsWith(status)?"已审核":
				"03".endsWith(status)?"驳回":
				"04".endsWith(status)?"办理中":"05".endsWith(status)?"已办理":"草稿"
				, note};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "公司", "部门", "制单人名称", "制单时间", "办公室",
				"得分", "责任人名称", "状态", "备注"};
		return titles;
	}
	/** default constructor */
	public DtMysanitaryinspection() {
	}

	/** full constructor */
	public DtMysanitaryinspection(String id, String status,
			String serialnumber, String proposerid, String proposername,
			String companyid, String companyname, String organizationid,
			String organizationname, String staffid, String staffname,
			Date addtime, String department, String score, String note) {
		this.id = id;
		this.status = status;
		this.serialnumber = serialnumber;
		this.proposerid = proposerid;
		this.proposername = proposername;
		this.companyid = companyid;
		this.companyname = companyname;
		this.organizationid = organizationid;
		this.organizationname = organizationname;
		this.staffid = staffid;
		this.staffname = staffname;
		this.addtime = addtime;
		this.department = department;
		this.score = score;
		this.note = note;
	}

	// Property accessors

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getProposerid() {
		return this.proposerid;
	}

	public void setProposerid(String proposerid) {
		this.proposerid = proposerid;
	}

	public String getProposername() {
		return this.proposername;
	}

	public void setProposername(String proposername) {
		this.proposername = proposername;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrganizationname() {
		return this.organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
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

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}