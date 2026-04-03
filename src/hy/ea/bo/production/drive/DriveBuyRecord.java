package hy.ea.bo.production.drive;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 
 * 
 * 学员购买记录
 * 
 * @author mz
 *
 */
public class DriveBuyRecord implements BaseBean {

 private String brkey;
 private String brid;
 private String drivelicType;//驾照类型 e.g. C1 A2...
 private String mainpname;//主产品名称
 private String mainpid;//主产品ID
 private String subpname;//子产品名称
 private String subpid;//子产品ID
 private String cartype;//车辆类型
 private String studyplace;//学习场地
 private String clientserv;//客服
 private String clientservID;//客服ID
 private String manager;//主管
 private String managerID;//主管ID
 private String cashierBillsID;//订单ID
 private String staffid;//用户ID
 private String staffname;//人员姓名
 private Date createDate;//付款时间
 private String companyID;//公司ID
 private String state;
 
 
 //非数据库字段ljc
 private String studyplaceId;
 private String cartypeId;
public String getBrkey() {
	return brkey;
}
public void setBrkey(String brkey) {
	this.brkey = brkey;
}
public String getBrid() {
	return brid;
}
public void setBrid(String brid) {
	this.brid = brid;
}
public String getMainpname() {
	return mainpname;
}
public void setMainpname(String mainpname) {
	this.mainpname = mainpname;
}
public String getMainpid() {
	return mainpid;
}
public void setMainpid(String mainpid) {
	this.mainpid = mainpid;
}
public String getSubpname() {
	return subpname;
}
public void setSubpname(String subpname) {
	this.subpname = subpname;
}
public String getSubpid() {
	return subpid;
}
public void setSubpid(String subpid) {
	this.subpid = subpid;
}
public String getCartype() {
	return cartype;
}
public void setCartype(String cartype) {
	this.cartype = cartype;
}
public String getStudyplace() {
	return studyplace;
}
public void setStudyplace(String studyplace) {
	this.studyplace = studyplace;
}
public String getClientserv() {
	return clientserv;
}
public void setClientserv(String clientserv) {
	this.clientserv = clientserv;
}
public String getManager() {
	return manager;
}
public void setManager(String manager) {
	this.manager = manager;
}
public String getCashierBillsID() {
	return cashierBillsID;
}
public void setCashierBillsID(String cashierBillsID) {
	this.cashierBillsID = cashierBillsID;
}
public String getStaffid() {
	return staffid;
}
public void setStaffid(String staffid) {
	this.staffid = staffid;
}
public Date getCreateDate() {
	return createDate;
}
public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getClientservID() {
	return clientservID;
}
public void setClientservID(String clientservID) {
	this.clientservID = clientservID;
}
public String getManagerID() {
	return managerID;
}
public void setManagerID(String managerID) {
	this.managerID = managerID;
}
public String getStaffname() {
	return staffname;
}
public void setStaffname(String staffname) {
	this.staffname = staffname;
}
public String getCompanyID() {
	return companyID;
}
public void setCompanyID(String companyID) {
	this.companyID = companyID;
}
public String getDrivelicType() {
	return drivelicType;
}
public void setDrivelicType(String drivelicType) {
	this.drivelicType = drivelicType;
}
public String getStudyplaceId() {
	return studyplaceId;
}
public void setStudyplaceId(String studyplaceId) {
	this.studyplaceId = studyplaceId;
}
public String getCartypeId() {
	return cartypeId;
}
public void setCartypeId(String cartypeId) {
	this.cartypeId = cartypeId;
}
 
 
	
}