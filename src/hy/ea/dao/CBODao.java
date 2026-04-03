package hy.ea.dao;

import hy.plat.bo.SBO;

import java.util.List;

public interface CBODao {
	
	/**
	 * 根据eaID取得所有状态正常的SBO
	 * @param eaID
	 * @return
	 */
	List<SBO> getBOListByEaID(String eaID);
	SBO getBOByID(String BoID);
}
