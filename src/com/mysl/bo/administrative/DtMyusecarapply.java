package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtMyattach entity. @author MyEclipse Persistence Tools
 */

public class DtMyusecarapply implements java.io.Serializable,BaseBean, ExcelBean {

	/*
	 * 用车申请
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
	
	private String  carcode;      //车牌号
	private Date    carusetime;   //用车时间
	private Date    carbacktime;  //计划还车时间
	private String  carusereason; //项目名称
	private String  destination; //目的地
	private String  carusername;  //用车人
	private String  caruserid;    //用车人id
	private String  cardriver;    //驾驶员
	private String  cardriverid;    //驾驶员id
	private String  cardriverorgid;    //驾驶员部门id
	private String  cardriverorgname;    //驾驶员部门
	private String  cardrivercompanyid;    //驾驶员公司id
	private String  cardrivercompanyname;    //驾驶员公司
	private String  remarks;      //备注
	
	
	public String[] properties() {
		String[] properties = { serialNumber, carcode, carusername,
				carusereason,destination, cardriver,String.format("%1$tF %1$tT", carusetime),
				String.format("%1$tF %1$tT", carbacktime),staffname,
				          ("01".endsWith(status)?"未审核":
					       "02".endsWith(status)?"已审核":
						   "03".endsWith(status)?"驳回":
						   "04".endsWith(status)?"办理中":   
						   "05".endsWith(status)?"已办理":"草稿"),remarks};
		return properties;

	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "单据编号", "车牌号", "用车人", "项目名称","目的地", "驾驶员","用车时间","计划还车时间", "制单人", "单据状态","备注"};
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
	public String getCarcode() {
		return carcode;
	}
	public void setCarcode(String carcode) {
		this.carcode = carcode;
	}
	public Date getCarusetime() {
		return carusetime;
	}
	public void setCarusetime(Date carusetime) {
		this.carusetime = carusetime;
	}
	public Date getCarbacktime() {
		return carbacktime;
	}
	public void setCarbacktime(Date carbacktime) {
		this.carbacktime = carbacktime;
	}
	public String getCarusereason() {
		return carusereason;
	}
	public void setCarusereason(String carusereason) {
		this.carusereason = carusereason;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getCarusername() {
		return carusername;
	}
	public void setCarusername(String carusername) {
		this.carusername = carusername;
	}
	public String getCardriver() {
		return cardriver;
	}
	public void setCardriver(String cardriver) {
		this.cardriver = cardriver;
	}
	
	public String getCardriverorgid() {
		return cardriverorgid;
	}

	public void setCardriverorgid(String cardriverorgid) {
		this.cardriverorgid = cardriverorgid;
	}

	public String getCardriverorgname() {
		return cardriverorgname;
	}

	public void setCardriverorgname(String cardriverorgname) {
		this.cardriverorgname = cardriverorgname;
	}

	public String getCardrivercompanyid() {
		return cardrivercompanyid;
	}

	public void setCardrivercompanyid(String cardrivercompanyid) {
		this.cardrivercompanyid = cardrivercompanyid;
	}

	public String getCardrivercompanyname() {
		return cardrivercompanyname;
	}

	public void setCardrivercompanyname(String cardrivercompanyname) {
		this.cardrivercompanyname = cardrivercompanyname;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCaruserid() {
		return caruserid;
	}
	public void setCaruserid(String caruserid) {
		this.caruserid = caruserid;
	}
	public String getCardriverid() {
		return cardriverid;
	}
	public void setCardriverid(String cardriverid) {
		this.cardriverid = cardriverid;
	}
	
    
}