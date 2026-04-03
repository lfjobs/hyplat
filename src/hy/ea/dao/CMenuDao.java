package hy.ea.dao;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CMenu;

import java.util.List;

public interface CMenuDao {
	
	/**
	 * 根据ID(Not Key)得到CMenu信息
	 * @param companyID
	 * @param menuID
	 * @return
	 */
	CMenu getMenuByID(String companyID,String menuID);
	
	/**
	 * 根据ID(Not Key)删除CMenu及相关信息
	 * @param companyID
	 * @param logBook
	 * @param menuID
	 */
	void deleteMenuByID(String companyID,CLogBook logBook,String menuID);

	/**
	 * 根据eaID得到此ea下所有的CMenu
	 * @param companyID
	 * @param eaID
	 * @return
	 */
	List<CMenu> getMenuListByEaID(String companyID,String eaID);
	
	/**
	 * 取得此角色可以访问的此EA的菜单项，用于登录时生成菜单
	 * @param companyID
	 * @param roleID
	 * @param eaID
	 * @return
	 */
	List<CMenu> getMenuListByRoleIDAndEaID(String companyID,String roleID,String eaID);
}
