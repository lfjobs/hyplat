package hy.plat.dao;

import hy.plat.bo.SEA;

import java.util.List;

public interface SEADao {
	
	/**
	 * 根据ID(Not Key)取得SEA信息
	 * @param eaID
	 * @return
	 */
	SEA getSEAByID(String eaID);
	
	/**
	 * 得到所有的状态正常的Sea
	 * @return
	 */
	List<SEA> getListSea();
	
	/**
	 * 通过roleID得到此角色可以访问的SEA
	 * @param roleID
	 * @return
	 */
	List<SEA> getListSea(String roleID);
}
