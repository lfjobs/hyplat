package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 金额授权管理
 * @author wangshuangni
 *
 */
public class CompanyMenu implements BaseBean {
	/**
	 * 主键
	 */
	private String companyMenuKey;
	/**
	 * 公司id
	 */
	private String companyId;
	/**
	 * 菜单id
	 */
	private String menuId;

	public String getCompanyMenuKey() {
		return companyMenuKey;
	}

	public void setCompanyMenuKey(String companyMenuKey) {
		this.companyMenuKey = companyMenuKey;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
