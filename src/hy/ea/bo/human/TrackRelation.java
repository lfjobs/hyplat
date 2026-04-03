package hy.ea.bo.human;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class TrackRelation implements BaseBean, ExcelBean {
	private String trackrelationKey;
	private String trackrelationID;
	private String staffID; //责任人 外键
	private String companyID;  //公司  外键
	private String organizationID;  //单据所在部门  外键
	private String foreignKeyID; //往来个人或往来单位的外键
	private Date collectdate; //收集时间
	private String Status; //单据状态
	private String contactConnections; //往来关系
	private String Address;//地址
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getTrackrelationKey() {
		return trackrelationKey;
	}

	public void setTrackrelationKey(String trackrelationKey) {
		this.trackrelationKey = trackrelationKey;
	}

	public String getTrackrelationID() {
		return trackrelationID;
	}

	public void setTrackrelationID(String trackrelationID) {
		this.trackrelationID = trackrelationID;
	}

	public String getForeignKeyID() {
		return foreignKeyID;
	}

	public void setForeignKeyID(String foreignKeyID) {
		this.foreignKeyID = foreignKeyID;
	}public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getContactConnections() {
		return contactConnections;
	}

	public void setContactConnections(String contactConnections) {
		this.contactConnections = contactConnections;
	}

	public Date getCollectdate() {
		return collectdate;
	}

	public void setCollectdate(Date collectdate) {
		this.collectdate = collectdate;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
	
}
