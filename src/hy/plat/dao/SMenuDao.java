package hy.plat.dao;

import hy.plat.bo.SMenu;

import java.util.List;

public interface SMenuDao {
	
	/**
	 * 根据ID(Not Key)得到SMenu信息
	 * @param menuID
	 * @return
	 */
	SMenu getMenuByID(String menuID);
	
	/**
	 * 根据ID(Not Key)删除SMenu及相关信息
	 * @param menuID
	 */
	void deleteMenuByID(String menuID);
	
	/**
	 * 根据eaID得到此ea下所有的Menu
	 * @param eaID
	 * @return
	 */
	List<SMenu> getMenuListByEaID(String eaID);
	
	/**
	 * 取得此角色可以访问的此EA的菜单项，用于登录时生成菜单
	 * @param roleID
	 * @param eaID
	 * @return
	 */
	List<SMenu> getMenuListByRoleIDAndEaID(String roleID,String eaID);
}
