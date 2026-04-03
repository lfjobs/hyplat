package hy.plat.service;

import hy.plat.bo.SInterface;

import java.util.List;



public interface SInterfaceService {
	/**
	 * 根据ID(Not Key)取得SInterface
	 * @param interfaceID
	 * @return
	 */
	SInterface getSInterfaceByID(String interfaceID);
	/**
	 *取得状态为3的机构接口
	 * @return
	 */
	List<SInterface> getSInterfaceListByStatus();
	/**
	 * 根据ID(Not Key)删除SInterface及相关信息
	 * @param interfaceID
	 */
	void deleteSInterfaceByID(String interfaceID);
}
