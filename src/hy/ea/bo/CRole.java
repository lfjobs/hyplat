package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;


public class CRole implements BaseBean,Serializable{
	private String roleKey;
	private String roleID;
	private String companyID;
	private String companyName;//公司名称
	private String organizationName;//部门名称ID
	private String organizationNameDesc;//部门名称
	private String opostName;//岗位名称
	private String roleName;//职务名称（权限名称）
	private String roleDesc;
	private Date createDate;

	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrganizationName() {
		return organizationName;
	
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOpostName() {
		return opostName;
	}
	public void setOpostName(String opostName) {
		this.opostName = opostName;
	}
	public String getOrganizationNameDesc() {
		return organizationNameDesc;
	}
	public void setOrganizationNameDesc(String organizationNameDesc) {
		this.organizationNameDesc = organizationNameDesc;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
