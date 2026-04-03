package hy.ea.bo.human;

import hy.plat.bo.BaseBean;


/**
 * 菜单管理
 * @author wangshuangni
 *
 */
public class MoneyEmpowerMenu implements BaseBean {
	/**
	 * 主键
	 */
	private String empowerMenuKey;
	/**
	 * 权限id
	 */
	private String empowerId;
	/**
	 * 菜单id
	 */
	private String menuId;

	public String getEmpowerMenuKey() {
		return empowerMenuKey;
	}

	public void setEmpowerMenuKey(String empowerMenuKey) {
		this.empowerMenuKey = empowerMenuKey;
	}

	public String getEmpowerId() {
		return empowerId;
	}

	public void setEmpowerId(String empowerId) {
		this.empowerId = empowerId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
