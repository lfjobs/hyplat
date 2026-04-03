package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * @author mz 耗材领用申请单
 */

public class DtMyconsumerecipt implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5912517002220451206L;
	private String key;
	private String id;
	private String status;
	private String serialnumber;
	private String companyid;
	private String companyname;
	private String organizationid;
	private String organizationname;
	private String staffid;
	private String staffname;
	private Date addtime;
	

	/***********耗材领用申请单***********/
	private String name;		//品名
	private String amount;			//数量
	private String remark;//说明
	private String postName;



	@Override
	public String[] properties() {
		String[] properties = {companyname,organizationname,serialnumber,
				staffname, String.format("%1$tF", addtime),name,amount,
				"01".endsWith(status)?"未审核":
				"02".endsWith(status)?"已审核":
				"03".endsWith(status)?"驳回":
				"04".endsWith(status)?"办理中":"05".endsWith(status)?"已办理":"草稿"
				};
		return properties;
	}
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","公司","部门","凭证号","责任人","申请日期","品名","数量","单据状态"};
		return titles;
	}
	/** default constructor */
	public DtMyconsumerecipt() {
	}

	/** full constructor */
	public DtMyconsumerecipt(String id, String status,
			String serialnumber, String proposerid, String proposername,
			String companyid, String companyname, String organizationid,
			String organizationname, String staffid, String staffname,
			Date addtime, String department, String score, String note) {
		this.id = id;
		this.status = status;
		this.serialnumber = serialnumber;
		
		this.companyid = companyid;
		this.companyname = companyname;
		this.organizationid = organizationid;
		this.organizationname = organizationname;
		this.staffid = staffid;
		this.staffname = staffname;
		this.addtime = addtime;
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}


   

   



}