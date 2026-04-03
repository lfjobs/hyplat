package hy.ea.service;

import hy.plat.bo.SEA;

import java.util.List;



public interface CEAService {
	
	/**
	 * 得到所有的状态正常的Sea
	 * @param companyID
	 * @return
	 */
	List<SEA> getListSea(String companyID);
	
	/**
	 * 通过roleID得到此角色可以访问的SEA
	 * @param companyID
	 * @param roleID
	 * @return
	 */
	List<SEA> getListSea(String companyID,String roleID);
	
	/**
	 * 将一个SEA的数据导入到此公司的表中
	 * @param companyID
	 * @param eaID
	 * @param roleID
	 */
	void pushSEAtoCEA(String companyID,String eaID,String roleID);
	

	/**
	 * 版本升级
	 * @param companyID
	 * @param eaID
	 * @param roleID
	 */
	void updateSEAtoCEA(String companyID,SEA cea,String roleID);
	
	
}
