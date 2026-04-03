package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;


public class DtMycertificateapply implements java.io.Serializable,BaseBean, ExcelBean {

	/*
	 *  各类证书领用申请
	 */
	private String  key;//主键
	private String  id; //业务主键
	private String  companyname;//公司名称
	private String  companyid;//公司ID
	private String  organizationname;//部门名称
	private String  organizationid;//部门ID
	private String  status;//状态（审核状态）
	private String  staffid;//制单人ID
	private String  staffname;//制单人name
	private Date    addtime;//制单时间或者提交审核时间
    private String  serialNumber;//单据编号
    
	private String  catename;    //证书名称
	private String  cateuse;     //证书用途
	private String  catenum;     //证书数量
	private String  cateusername;//证书领用人
	private String  cateuserid;  //证书领用人id
	private Date    cateusedate; //证书领用时间
	private String  remarks;     //备注
	
	public String[] properties() {
		String[] properties = { serialNumber, catename, cateuse,
				catenum, cateusername, String.format("%1$tF %1$tT", cateusedate), staffname,
				          ("01".endsWith(status)?"未审核":
					       "02".endsWith(status)?"已审核":
						   "03".endsWith(status)?"驳回":
						   "04".endsWith(status)?"办理中":   
						   "05".endsWith(status)?"已办理":"草稿"),remarks};
		return properties;

	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "证书名称", "证书用途", "证书数量", "领用人",
				"领用时间", "制单人", "单据状态","备注"};
		return titles;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getOrganizationname() {
		return organizationname;
	}
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
	public String getOrganizationid() {
		return organizationid;
	}
	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
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
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
	}
	public String getCateuse() {
		return cateuse;
	}
	public void setCateuse(String cateuse) {
		this.cateuse = cateuse;
	}
	public String getCatenum() {
		return catenum;
	}
	public void setCatenum(String catenum) {
		this.catenum = catenum;
	}
	public String getCateusername() {
		return cateusername;
	}
	public void setCateusername(String cateusername) {
		this.cateusername = cateusername;
	}
	public Date getCateusedate() {
		return cateusedate;
	}
	public void setCateusedate(Date cateusedate) {
		this.cateusedate = cateusedate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCateuserid() {
		return cateuserid;
	}

	public void setCateuserid(String cateuserid) {
		this.cateuserid = cateuserid;
	}
    
}