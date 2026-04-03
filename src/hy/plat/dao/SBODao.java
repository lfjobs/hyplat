package hy.plat.dao;

import hy.plat.bo.SBO;

import java.util.List;

public interface SBODao {
	
	/**
	 * 根据ID(Not Key)取得BO信息
	 * @param boID
	 * @return
	 */
	SBO getSBOByID(String boID);
	
	/**
	 * 根据eaID取得所有状态正常的SBO
	 * @param eaID
	 * @return
	 */
	List<SBO> getBOListByEaID(String eaID);
	
	/**
	 * 取得所有状态正常的SBO
	 * @return
	 */
	List<SBO> getBOList();
	/**
	 * 取得BO状态
	 * @return
	 */
	String getboStatusbyURL(String url);
}
