package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyguestmeals entity. @author MyEclipse Persistence Tools
 */

public class DtMyguestmeals implements java.io.Serializable,BaseBean,ExcelBean{

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
	private String recequantity;
	private String recereason;
	private String compersonnel;
	private String results;
	private String note;

	
	@Override
	public String[] properties() {
		String[] properties = {  serialnumber, companyname,organizationname, proposername,
				 String.format("%1$tF", addtime),  recequantity, recereason,compersonnel,results,staffname, 
				"01".endsWith(status)?"未审核":
				"02".endsWith(status)?"已审核":
				"03".endsWith(status)?"驳回":
				"04".endsWith(status)?"办理中":"05".endsWith(status)?"已办理":"草稿"
				, note};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "公司", "部门", "制单人名称", "制单时间", "接待人数",
				"接待对象及事由","作陪人员","办理结果", "责任人名称", "状态", "备注"};
		return titles;
	}
	// Constructors

	/** default constructor */
	public DtMyguestmeals() {
	}

	/** full constructor */
	public DtMyguestmeals(String id, String status, String serialnumber,
			String proposerid, String proposername, String companyid,
			String companyname, String organizationid, String organizationname,
			String staffid, String staffname, Date addtime,
			String recequantity, String recereason, String compersonnel,
			String results, String note) {
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
		this.recequantity = recequantity;
		this.recereason = recereason;
		this.compersonnel = compersonnel;
		this.results = results;
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

	public String getRecequantity() {
		return this.recequantity;
	}

	public void setRecequantity(String recequantity) {
		this.recequantity = recequantity;
	}

	public String getRecereason() {
		return this.recereason;
	}

	public void setRecereason(String recereason) {
		this.recereason = recereason;
	}

	public String getCompersonnel() {
		return this.compersonnel;
	}

	public void setCompersonnel(String compersonnel) {
		this.compersonnel = compersonnel;
	}

	public String getResults() {
		return this.results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}