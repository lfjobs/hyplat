package hy.plat.service;

import hy.plat.bo.SDistrict;

import java.util.List;

public interface SDistrictService {

	/**
     * 根据ID递归删除SDistrict
     * @param districtID
     */
	void deleteDistrictByID(String districtID);
	
	/**
	 * 根据PID得到子地域
	 * @param districtPID
	 * @return
	 */
	List<SDistrict> getDistrictListByPID(String districtPID);
	/**
	 * 根据PID得到飞本身的子地域
	 * @param districtPID
	 * @return
	 */
	List<SDistrict> getDistrictListNOselfByPID(String districtPID, String districtID);
	
	/**
	 * 根据ID(Not Key)得到SDistrict
	 * @param districtID
	 * @return
	 */
	SDistrict getDistrictByID(String districtID);
	
	/**
	 * 根据ID得到本地域及所有的父级地域
	 * @param districtID
	 * @return
	 */
	List<SDistrict> getDistrictListByID(String districtID);
	
	/**
	 * 根据PID和Name取得地域信息
	 * @param districtPID
	 * @param districtName
	 * @return
	 */
	SDistrict getSDistrictByPIDAndName(String districtPID,String districtName);
}
