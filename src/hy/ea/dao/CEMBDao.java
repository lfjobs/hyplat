package hy.ea.dao;

import hy.ea.bo.CEMB;
import hy.ea.bo.CLogBook;

import java.util.List;


public interface CEMBDao {
	
	/**
	 * 根据companyID、eaID、menuID得到已经分配给菜单的BO
	 * @param companyID
	 * @param eaID
	 * @param menuID
	 * @return
	 */
	List<CEMB> getCEMBListByEaIDAndMenuID(String companyID,String eaID,String menuID);
	
	
	/**
	 * 根据companyID、eaID取得此EA下已经分配的所有的Menu下的所有的BO
	 * @param companyID
	 * @param eaID
	 * @return
	 */
	List<CEMB> getCEMBListByEaID(String companyID,String eaID);
	
	/**
	 * 保存CEMB信息
	 * @param companyID
	 * @param eaID
	 * @param menuID
	 * @param boIDs
	 * @param logBook
	 */
	void saveCEMB(String companyID,String eaID,String menuID,String[] boIDs,CLogBook logBook);
}
