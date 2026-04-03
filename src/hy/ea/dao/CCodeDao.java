package hy.ea.dao;

import hy.ea.bo.CCode;
import hy.ea.bo.DrivingSchool.TbSysGeography;
import hy.plat.bo.SCode;

import java.util.List;



public interface CCodeDao {
	
	/**
	 * 注册将Code中的数据拷贝到CCode中去
	 * @param companyID
	 */
	void pushCodeToCCode(String companyID);
	/**
	 * 注册将Code中的数据更新到CCode中去
	 * @param companyID
	 */
	void upadateCodeToCCode(String companyID);
	
	/**
	 * 根据companyID和codeID删除code
	 * @param companyID
	 * @param codeID
	 */
	void deleteCCodeByID(String companyID,String codeID);
	/**
	 * 根据codeID查询出code
	 * @param codeID
	 * @return
	 */
	CCode getCCodeByID(String companyID,String codeID);
	
	/**
	 * 根据companyID和codePID查询出其子节点
	 * @param companyID
	 * @param codePID
	 * @return
	 */
	List<CCode> getCCodeListByPID(String companyID,String codePID);
	/**
	 * 根据companyID和codePID查询出其子节点
	 * @param companyID
	 * @param codePID
	 * @return
	 */
	List<CCode> getCCodeListByPIDs(String companyID,String [] codePID);
	/**
	 * 根据companyID查询出SCode中需要更新的数据
	 * @param companyID
	 * @param codePID
	 * @return
	 */
	List<SCode> getCodeListForUpdate(String companyID);


	/**
	 * 获取所属行政区域
	 * @param @ geoLevel区域编号
	 */
	List<TbSysGeography> getAllTbSysGeographyBygeoLevel(String geoLevel);

	List<CCode> getCCodeListNewByPID(String companyID, String codePID, String codetype);
}
