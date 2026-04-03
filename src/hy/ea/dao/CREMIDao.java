package hy.ea.dao;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CREMI;
import hy.plat.bo.SInterface;

import java.util.List;

public interface CREMIDao {
	
	/**
	 * 根据roleID、eaID取得给此role分配的此ea的Interface
	 * @param roleID
	 * @param eaID
	 * @return
	 */
	List<CREMI> getCRemiListByRoleIDAndEaID(String companyID,String roleID,String eaID);
	
	/**
	 * 通过eaID、menuID取得分配给某个ea的菜单的BO的Interface
	 * @param eaID
	 * @param menuID
	 * @return
	 */
	List<SInterface> getSInterfaceListByEaIDAndMenuID(String companyID,String eaID,String menuID);
	
	/**
	 * 通过roleID、eaID、menuID取得此角色可以访问的此菜单下的Interface，如果此Interface可以作为入口，则置状态为02，用于登录
	 * @param roleID
	 * @param eaID
	 * @param menuID
	 * @return
	 */
	List<SInterface> getSInterfaceListByRoleIDAndEaIDAndMenuID(String companyID,String roleID,String eaID,String menuID);
	
	/**
	 * 保存REMI，权限分配信息
	 * @param roleID
	 * @param eaID
	 * @param mi
	 * @param logBook
	 */
	void saveCREMI(String companyID,String roleID,String eaID,String []mis,CLogBook logBook);
	/**
	 * 保存多个角色REMI，权限分配信息
	 * @param roleID
	 * @param eaID
	 * @param mi
	 * @param logBook
	 */
	void saveCREMI(String companyID,String[] roleIDS,String eaID,String []mis,String []misno,CLogBook[] logBooks);
	
	/**
	 * 通过roleID取得分配此角色的权限信息
	 * @param roleID
	 * @return
	 */
	List<CREMI> getCREMIListByRoleID(String companyID,String roleID);
}
