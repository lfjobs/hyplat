package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 岗位标准化维护
 * @author wangshuangni
 *
 */
public class RoleStandard implements BaseBean {
	/**
	 * 主键
	 */
	private String roleKey;

	/**
	 * 主键
	 */
	private String roleId;
	/**
	 * 岗位名称（角色）
	 */
	private String roleName;
	/**
	 * 描述
	 */
	private String roleDesc;
	/**
	 * 创建人id
	 */
	private String createStaffId;
	/**
	 * 创建时间
	 * @return
	 */
	private Date createDate;
	/**
	 * 修改时间
	 * @return
	 */
	private Date updateDate;

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
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

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getCreateStaffId() {
		return createStaffId;
	}

	public void setCreateStaffId(String createStaffId) {
		this.createStaffId = createStaffId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
