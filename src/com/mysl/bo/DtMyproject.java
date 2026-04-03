package com.mysl.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyproject entity. @author MyEclipse Persistence Tools
 */

public class DtMyproject implements java.io.Serializable, BaseBean, ExcelBean {

	// Fields
	private String prokey;//主键key
	private String proid;//逻辑ID
	private String procode;//项目编号
	private String proname;//项目名称
	private String contactcompany;//往来单位
	private Date startdate;//启动时间
	private Date planfinishdate;//计划完成时间
	private Date factfinishdate;//实际完成时间
	private String probrief;//项目简介
	private String straffName;//最后操作人姓名
	private String staffID;//最后操作人id
	private Date addDate;//创建时间
	private String companyid;//公司id
	private String companyname;//公司name
	private String manager;//项目负责人（非数据库字段）
	private String member;//项目成员（非数据库字段）

	// Constructors
	public String[] properties() {
		String[] properties = { procode, proname, contactcompany,
				String.format("%1$tF", startdate), String.format("%1$tF", planfinishdate), String.format("%1$tF", factfinishdate),
				probrief, straffName,manager,member};
		return properties;

	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "项目编号", "项目名称", "往来单位", "启动时间", "计划完成时间",
				"实际完成时间", "项目简介", "最后操作人姓名","项目负责人","项目成员"};
		return titles;
	}
	/** default constructor */
	public DtMyproject() {
	}

	/** full constructor */
	public DtMyproject(String prokey, String proid, String procode,
			String proname, String contactcompany, Date startdate,
			Date planfinishdate, Date factfinishdate, String probrief,
			String straffName, String staffID, Date addDate) {
		super();
		this.prokey = prokey;
		this.proid = proid;
		this.procode = procode;
		this.proname = proname;
		this.contactcompany = contactcompany;
		this.startdate = startdate;
		this.planfinishdate = planfinishdate;
		this.factfinishdate = factfinishdate;
		this.probrief = probrief;
		this.straffName = straffName;
		this.staffID = staffID;
		this.addDate = addDate;
	}

	// Property accessors
	public String getProkey() {
		return this.prokey;
	}

	public void setProkey(String prokey) {
		this.prokey = prokey;
	}

	public String getProid() {
		return this.proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getProcode() {
		return this.procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public String getProname() {
		return this.proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public String getContactcompany() {
		return this.contactcompany;
	}

	public void setContactcompany(String contactcompany) {
		this.contactcompany = contactcompany;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getPlanfinishdate() {
		return this.planfinishdate;
	}

	public void setPlanfinishdate(Date planfinishdate) {
		this.planfinishdate = planfinishdate;
	}

	public Date getFactfinishdate() {
		return this.factfinishdate;
	}

	public void setFactfinishdate(Date factfinishdate) {
		this.factfinishdate = factfinishdate;
	}

	public String getProbrief() {
		return this.probrief;
	}

	public void setProbrief(String probrief) {
		this.probrief = probrief;
	}

	public String getStraffName() {
		return straffName;
	}

	public void setStraffName(String straffName) {
		this.straffName = straffName;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

}