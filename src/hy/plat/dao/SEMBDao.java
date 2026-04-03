package hy.plat.dao;

import hy.plat.bo.SEMB;

import java.util.List;


public interface SEMBDao {
	
	/**
	 * 根据eaID和menuID得到已经分配给菜单的BO
	 * @param eaID
	 * @param menuID
	 * @return
	 */
	List<SEMB> getEMBListByEaIDAndMenuID(String eaID,String menuID);
	
	
	/**
	 * 根据eaID取得此EA下已经分配的所有的Menu下的所有的BO
	 * @param eaID
	 * @return
	 */
	List<SEMB> getEMBListByEaID(String eaID);
	
	/**
	 * 保存SEMB信息
	 * @param eaID
	 * @param menuID
	 * @param boIDs
	 */
	void saveSEMB(String eaID,String menuID,String[] boIDs);
}
