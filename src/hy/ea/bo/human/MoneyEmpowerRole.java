package hy.ea.bo.human;

import hy.plat.bo.BaseBean;


/**
 * 菜单管理
 * @author wangshuangni
 *
 */
public class MoneyEmpowerRole implements BaseBean {
	/**
	 * 主键
	 */
	private String empowerRoleKey;
	/**
	 * 权限id
	 */
	private String empowerId;
	/**
	 * 标准化角色id
	 */
	private String roleId;

	public String getEmpowerRoleKey() {
		return empowerRoleKey;
	}

	public void setEmpowerRoleKey(String empowerRoleKey) {
		this.empowerRoleKey = empowerRoleKey;
	}

	public String getEmpowerId() {
		return empowerId;
	}

	public void setEmpowerId(String empowerId) {
		this.empowerId = empowerId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
