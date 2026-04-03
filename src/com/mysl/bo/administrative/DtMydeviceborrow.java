package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyattach entity. @author MyEclipse Persistence Tools
 */

public class DtMydeviceborrow implements java.io.Serializable,BaseBean, ExcelBean {

	/*
	 *  办公室设备借用
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
	
	private String  dvname;       //设备名称
	private String  dvnum;        //设备数量
	private String  dvuse;        //用途
	private String  dvusername;   //借用人姓名
	private String  dvuserid;   //借用人姓名id
	private Date    dvusetime;    //借用时间
	private Date    dvbacktime;   //计划归还时间
	private String  remarks;      //备注
	
	
	public String[] properties() {
		String[] properties = { serialNumber, dvname, dvnum,
				dvuse, dvusername, String.format("%1$tF %1$tT", dvusetime),String.format("%1$tF %1$tT", dvbacktime), staffname,
				          ("01".endsWith(status)?"未审核":
					       "02".endsWith(status)?"已审核":
						   "03".endsWith(status)?"驳回":
						   "04".endsWith(status)?"办理中":   
						   "05".endsWith(status)?"已办理":"草稿"),remarks};
		return properties;

	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "设备名称", "设备数量", "用途", "借用人姓名",
				"借用时间","计划归还时间", "制单人", "单据状态","备注"};
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
	public String getDvnum() {
		return dvnum;
	}
	public void setDvnum(String dvnum) {
		this.dvnum = dvnum;
	}
	public String getDvuse() {
		return dvuse;
	}
	public void setDvuse(String dvuse) {
		this.dvuse = dvuse;
	}
	public String getDvusername() {
		return dvusername;
	}
	public void setDvusername(String dvusername) {
		this.dvusername = dvusername;
	}
	public String getDvuserid() {
		return dvuserid;
	}
	public void setDvuserid(String dvuserid) {
		this.dvuserid = dvuserid;
	}
	public Date getDvusetime() {
		return dvusetime;
	}
	public void setDvusetime(Date dvusetime) {
		this.dvusetime = dvusetime;
	}
	public Date getDvbacktime() {
		return dvbacktime;
	}
	public void setDvbacktime(Date dvbacktime) {
		this.dvbacktime = dvbacktime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}