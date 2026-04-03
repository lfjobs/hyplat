package hy.plat.dao;

import hy.plat.bo.SDistrict;

import java.util.List;

public interface SDistrictnDao {
	
	/**
     * 根据ID删除SDistrict
     * @param districtID
     */
	void deleteDistrictByID(String districtID);
	
	/**
	 * 根据PID得到所有的子地域
	 * @param districtPID
	 * @return
	 */
	List<SDistrict> getDistrictListByPID(String districtPID);
	
	/**
	 * 根据ID(Not Key)得到SDistrict
	 * @param districtID
	 * @return
	 */
	SDistrict getDistrictByID(String districtID);
	
	/**
	 * 根据PID和Name取得地域信息
	 * @param districtPID
	 * @param districtName
	 * @return
	 */
	SDistrict getSDistrictByPIDAndName(String districtPID,String districtName);
	/**
	 * 根据PID得到飞本身的子地域
	 * @param districtPID
	 * @return
	 */
	List<SDistrict> getDistrictListNOselfByPID(String districtPID, String districtID);
	
}
