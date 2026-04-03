package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMysealregistration entity. @author MyEclipse Persistence Tools
 */

public class DtMysealregistration implements java.io.Serializable ,BaseBean,ExcelBean{

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
	private String projectname;
	private String agentpersonid;
	private String agentpersonname;
	private String sealtype;
	private String datatype;
	private String quantity;
	private String dealsituation;
	private String recipientpersonid;
	private String recipientpersonname;
	private String recipientquantity;
	private Date recipienttime;
	private String leadersperson;
	private String phone;
	private String note;

	
	@Override
	public String[] properties() {
		String[] properties = {  serialnumber, companyname,organizationname, proposername,
				 String.format("%1$tF", addtime),  projectname, agentpersonname,sealtype,datatype,quantity,dealsituation,
				 recipientpersonname,recipientquantity,String.format("%1$tF", recipienttime),leadersperson,phone,staffname, 
				"01".endsWith(status)?"未审核":
				"02".endsWith(status)?"已审核":
				"03".endsWith(status)?"驳回":
				"04".endsWith(status)?"办理中":"05".endsWith(status)?"已办理":"草稿"
				};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "公司", "部门", "制单人名称", "制单时间", "工程名称",
				"经办人","盖章类型","资料类型","数量/份","办理情况","接收人","接收数量","接收时间","领导人","联系电话", "责任人名称", "状态", "备注"};
		return titles;
	}
	// Constructors

	/** default constructor */
	public DtMysealregistration() {
	}

	/** full constructor */
	public DtMysealregistration(String id, String status, String serialnumber,
			String proposerid, String proposername, String companyid,
			String companyname, String organizationid, String organizationname,
			String staffid, String staffname, Date addtime, String projectname,
			String agentpersonid, String agentpersonname, String sealtype,
			String datatype, String quantity, String dealsituation,
			String recipientpersonid, String recipientpersonname,
			String recipientquantity, Date recipienttime, String leadersperson,
			String phone) {
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
		this.projectname = projectname;
		this.agentpersonid = agentpersonid;
		this.agentpersonname = agentpersonname;
		this.sealtype = sealtype;
		this.datatype = datatype;
		this.quantity = quantity;
		this.dealsituation = dealsituation;
		this.recipientpersonid = recipientpersonid;
		this.recipientpersonname = recipientpersonname;
		this.recipientquantity = recipientquantity;
		this.recipienttime = recipienttime;
		this.leadersperson = leadersperson;
		this.phone = phone;
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

	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getAgentpersonid() {
		return this.agentpersonid;
	}

	public void setAgentpersonid(String agentpersonid) {
		this.agentpersonid = agentpersonid;
	}

	public String getAgentpersonname() {
		return this.agentpersonname;
	}

	public void setAgentpersonname(String agentpersonname) {
		this.agentpersonname = agentpersonname;
	}

	public String getSealtype() {
		return this.sealtype;
	}

	public void setSealtype(String sealtype) {
		this.sealtype = sealtype;
	}

	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDealsituation() {
		return this.dealsituation;
	}

	public void setDealsituation(String dealsituation) {
		this.dealsituation = dealsituation;
	}

	public String getRecipientpersonid() {
		return this.recipientpersonid;
	}

	public void setRecipientpersonid(String recipientpersonid) {
		this.recipientpersonid = recipientpersonid;
	}

	public String getRecipientpersonname() {
		return this.recipientpersonname;
	}

	public void setRecipientpersonname(String recipientpersonname) {
		this.recipientpersonname = recipientpersonname;
	}

	public String getRecipientquantity() {
		return this.recipientquantity;
	}

	public void setRecipientquantity(String recipientquantity) {
		this.recipientquantity = recipientquantity;
	}

	public Date getRecipienttime() {
		return this.recipienttime;
	}

	public void setRecipienttime(Date recipienttime) {
		this.recipienttime = recipienttime;
	}

	public String getLeadersperson() {
		return this.leadersperson;
	}

	public void setLeadersperson(String leadersperson) {
		this.leadersperson = leadersperson;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}