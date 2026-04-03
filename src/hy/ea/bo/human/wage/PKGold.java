package hy.ea.bo.human.wage;

import hy.plat.bo.BaseBean;
/**
 * pkGold
 * @author Administrator
 *
 */
public class PKGold implements BaseBean,java.io.Serializable{
	
	private String pkgoldKey;			//主键
	private String pkgoldID;			//逻辑主键
	private String companyID;       //公司ID
	private String companyName;
	private String organizationID;		//部门
	private String organizationName;
	private String depID;              //职务
	private String depName;
	private String staffID;			//员工ID
	private String staffName;
	private String pkDate;		  //时间
	private String gold;	           //金额
	private String status;			//状态	'00' 存储  '01'支出
	private String remarks;			//备注
	
	
	public String getPkgoldKey() {
		return pkgoldKey;
	}
	public void setPkgoldKey(String pkgoldKey) {
		this.pkgoldKey = pkgoldKey;
	}
	public String getPkgoldID() {
		return pkgoldID;
	}
	public void setPkgoldID(String pkgoldID) {
		this.pkgoldID = pkgoldID;
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
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getDepID() {
		return depID;
	}
	public void setDepID(String depID) {
		this.depID = depID;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
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
	
	public String getPkDate() {
		return pkDate;
	}
	public void setPkDate(String pkDate) {
		this.pkDate = pkDate;
	}
	public String getGold() {
		return gold;
	}
	public void setGold(String gold) {
		this.gold = gold;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}