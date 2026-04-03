package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyattach entity. @author MyEclipse Persistence Tools
 */

public class DtMydevicerepair implements java.io.Serializable,BaseBean, ExcelBean {

	/*
	 *  办公室设备维修
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
	
	private String  dvdealname;   //处理人姓名
	private String  dvdealid;   //处理人id
	private String  dvname;       //设备名称
	private String  dvfault;      //设备故障说明
	private String  dvopinion;    //技术处理意见
	private String  remarks;      //备注
	
	public String[] properties() {
		String[] properties = { serialNumber, dvname, dvfault,
				dvopinion, dvdealname,staffname,
				          ("01".endsWith(status)?"未审核":
					       "02".endsWith(status)?"已审核":
						   "03".endsWith(status)?"驳回":
						   "04".endsWith(status)?"办理中":   
						   "05".endsWith(status)?"已办理":"草稿"),remarks};
		return properties;

	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "设备名称", "设备故障说明", "技术处理意见", "处理人", "制单人", "单据状态","备注"};
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
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getDvname() {
		return dvname;
	}
	public void setDvname(String dvname) {
		this.dvname = dvname;
	}
	public String getDvfault() {
		return dvfault;
	}
	public void setDvfault(String dvfault) {
		this.dvfault = dvfault;
	}
	public String getDvopinion() {
		return dvopinion;
	}
	public void setDvopinion(String dvopinion) {
		this.dvopinion = dvopinion;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDvdealname() {
		return dvdealname;
	}
	public void setDvdealname(String dvdealname) {
		this.dvdealname = dvdealname;
	}
	public String getDvdealid() {
		return dvdealid;
	}
	public void setDvdealid(String dvdealid) {
		this.dvdealid = dvdealid;
	}
    
}