package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.List;


/**
 * 菜单管理
 * @author wangshuangni
 *
 */
public class Menu implements BaseBean {
	/**
	 * 菜单主键
	 */
	private String menuKey;
	/**
	 * 菜单id
	 */
	private String menuId;
	/**
	 * 菜单父级
	 */
	private String menuPID;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单地址
	 */
	private String menuURL;
	/**
	 * 菜单类型(0:目录，1：菜单，2：按键)
	 */
	private Integer menuType;
	/**
	 * 排序
	 */
	private Integer sortNum;
	/**
	 * 菜单标识
	 */
	private String menuMark;
	/**
	 * 角色权限
	 */
	private String rolePermission;
	/**
	 * 状态（0：删除  1：正常）
	 */
	private Integer status;
	/**
	 * 菜单描述
	 */
	private String menuDesc;
	/**
	 * 菜单层级
	 * @return
	 */
	private Integer menuLevel;
	/**
	 * 父级菜单名称
	 * @return
	 */
	private String menuParentName;

	/**
	 * 父级编码List
	 * @return
	 */
	private String menuPIDList;

	/**
	 * 子级
	 */
	private List<Menu> children;

	public Menu() {

	}


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

	public String getMenuPID() {
		return menuPID;
	}

	public void setMenuPID(String menuPID) {
		this.menuPID = menuPID;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getMenuMark() {
		return menuMark;
	}

	public void setMenuMark(String menuMark) {
		this.menuMark = menuMark;
	}

	public String getRolePermission() {
		return rolePermission;
	}

	public void setRolePermission(String rolePermission) {
		this.rolePermission = rolePermission;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuParentName() {
		return menuParentName;
	}

	public void setMenuParentName(String menuParentName) {
		this.menuParentName = menuParentName;
	}

	public String getMenuPIDList() {
		return menuPIDList;
	}

	public void setMenuPIDList(String menuPIDList) {
		this.menuPIDList = menuPIDList;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

}
