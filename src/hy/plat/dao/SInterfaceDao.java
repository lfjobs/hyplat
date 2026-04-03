package hy.plat.dao;

import hy.plat.bo.SInterface;

import java.util.List;


public interface SInterfaceDao {
	
	/**
	 * 根据ID(Not Key)取得SInterface
	 * @param interfaceID
	 * @return
	 */
	SInterface getSInterfaceByID(String interfaceID);	
	
	/**
	 * 根据ID(Not Key)删除SInterface及相关信息
	 * @param interfaceID
	 */
	void deleteSInterfaceByID(String interfaceID);
	
	/**
	 * 取得所有状态正常的SInterface
	 * @return
	 */
	List<SInterface> getSInterfaceList();
	
	/**
	 * 通过BoID取得此BO下的所有状态正常的Interface
	 * @param boID
	 * @return
	 */
	List<SInterface> getSinterfaceListByBoID(String boID);
	/**
	 *取得状态为3的机构接口
	 * @return
	 */
	List<SInterface> getSInterfaceListByStatus();
}
