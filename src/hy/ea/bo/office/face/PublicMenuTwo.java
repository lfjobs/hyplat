package hy.ea.bo.office.face;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 一级菜单管理
 * @author IT
 *
 */
public class PublicMenuTwo implements BaseBean,Serializable{
	private static final long serialVersionUID = 1L;
	private String menuKey;
	private String menuId;
	private String menuTitle;
	private String menuUrl;
	private Integer menuSort;
	private Integer menuLevel;
	private String menuParentId;

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(String menuParentId) {
		this.menuParentId = menuParentId;
	}
}
