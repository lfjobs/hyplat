package hy.plat.dao;

import hy.plat.bo.SInterface;
import hy.plat.bo.SREMI;

import java.util.List;




public interface SREMIDao {
	
	/**
	 * 根据roleID、eaID取得给此role分配的此ea的Interface
	 * @param roleID
	 * @param eaID
	 * @return
	 */
	List<SREMI> getREMIListByRoleIDAndEaID(String roleID,String eaID);
	
	/**
	 * 通过eaID、menuID取得分配给某个ea的菜单的BO的Interface
	 * @param eaID
	 * @param menuID
	 * @return
	 */
	List<SInterface> getInterfaceListByEaIDAndMenuID(String eaID,String menuID);
	
	/**
	 * 通过roleID、eaID、menuID取得此角色可以访问的此菜单下的Interface，如果此Interface可以作为入口，则置状态为02，用于登录
	 * @param roleID
	 * @param eaID
	 * @param menuID
	 * @return
	 */
	List<SInterface> getInterfaceListByRoleIDAndEaIDAndMenuID(String roleID,String eaID,String menuID);
	
	/**
	 * 保存REMI，权限分配信息
	 * @param roleID
	 * @param eaID
	 * @param mi
	 */
	void saveSREMI(String roleID,String eaID,String []mis);
	
	/**
	 * 通过roleID取得分配此角色的权限信息
	 * @param roleID
	 * @return
	 */
	List<SREMI> getSREMIListByRoleID(String roleID);
	
	/**
	 * 通过eaID取得此ea下的分配信息
	 * @param eaID
	 * @return
	 */
	List<SREMI> getSREMIByEaID(String eaID);
}
