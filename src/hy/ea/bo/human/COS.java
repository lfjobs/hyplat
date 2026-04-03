package hy.ea.bo.human;

import hy.plat.bo.BaseBean;
/**
 * 招聘管理
 * @author zgzg
 *
 */
public class COS implements BaseBean,java.io.Serializable,Cloneable {
	private String cosKey;
	private String cosID;
	private String companyID;
	private String organizationID;  //机构删除后，招聘管理下的在职员工部门organizationID 变成 99
	private String staffID;
	
	private String depPostID; //岗位
	private String salaryLevelId;//级别id
	private String entryDate; // 入职时间

	private String contractType; // 合同类型


	/**
	 *01 表示招聘中 
	 *10 表示面试中 
	 *20 人员面试考试管理  
	 *40 人员入职登记管理 
	 *50 表示在职中  
	 *98 表示删除或者不合格 
	 *99 表示离职 
	 */
	private String cosStatus;
	/**
	 * 01专岗 00兼岗
	 */
	private String status;

	private String roleId;

    private String roleName;

	private String organizationName;

	private String statusName;

	private String salaryLevelName;

    private String contractTypeName;

	private String depPostName;
	/**
	 *01 表示招聘中 
	 *10 表示面试中 
	 *20 人员面试考试管理  
	 *40 人员入职登记管理 
	 *50 表示在职中  
	 *98 表示删除或者不合格 
	 *99 表示离职 
	 * @return
	 */
	public String getCosStatus() {
		return cosStatus;
	}


	/**
	 *01 表示招聘中 
	 *10 表示面试中 
	 *20 人员面试考试管理  
	 *40 人员入职登记管理 
	 *50 表示在职中  
	 *98 表示删除或者不合格 
	 *99 表示离职 
	 * @return
	 */
	public void setCosStatus(String cosStatus) {
		this.cosStatus = cosStatus;
	}
	
	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getCosKey() {
		return cosKey;
	}
	public void setCosKey(String cosKey) {
		this.cosKey = cosKey;
	}
	public String getCosID() {
		return cosID;
	}
	public void setCosID(String cosID) {
		this.cosID = cosID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	/**
	 * 01专岗 00兼岗
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 岗位
	 * @return
	 */
	public String getDepPostID() {
		return depPostID;
	}
	/**
	 * 岗位
	 * @param depPostID
	 */
	public void setDepPostID(String depPostID) {
		this.depPostID = depPostID;
	}

	public String getSalaryLevelId() {
		return salaryLevelId;
	}

	public void setSalaryLevelId(String salaryLevelId) {
		this.salaryLevelId = salaryLevelId;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSalaryLevelName() {
		return salaryLevelName;
	}

	public void setSalaryLevelName(String salaryLevelName) {
		this.salaryLevelName = salaryLevelName;
	}

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

	public String getDepPostName() {
		return depPostName;
	}

	public void setDepPostName(String depPostName) {
		this.depPostName = depPostName;
	}
	@Override
    public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

}
