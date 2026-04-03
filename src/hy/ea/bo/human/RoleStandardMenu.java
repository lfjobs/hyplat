package hy.ea.bo.human;

import hy.plat.bo.BaseBean;


/**
 * 角色菜单管理
 * @author wangshuangni
 *
 */
public class RoleStandardMenu implements BaseBean {
	/**
	 * 主键
	 */
	private String roleMenuKey;
	/**
	 * 权限id
	 */
	private String roleId;
	/**
	 * 菜单id
	 */
	private String menuId;

	public String getRoleMenuKey() {
		return roleMenuKey;
	}

	public void setRoleMenuKey(String roleMenuKey) {
		this.roleMenuKey = roleMenuKey;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
