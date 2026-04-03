package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;


/**
 * 调转管理：CashierBillsAllot
 * 
 */
public class CashierBillsAllot implements BaseBean {
	private String cashierBillsAllotKey;//主键
	private String cashierBillsID;//未付款外键
	private String companyID;//调拨公司ID
	private String companyName;//调拨公司name
	private String departmentID;//调拨部门ID 
	private String departmentName;//调拨部门name
	private String staffID;// 调拨责任人ID
	private String staffName;//调拨责任人name
	
	public String getCashierBillsAllotKey() {
		return cashierBillsAllotKey;
	}
	public void setCashierBillsAllotKey(String cashierBillsAllotKey) {
		this.cashierBillsAllotKey = cashierBillsAllotKey;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
}
