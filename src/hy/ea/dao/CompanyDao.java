package hy.ea.dao;


import hy.ea.bo.CDetail;
import hy.ea.bo.Company;

import java.util.List;

public interface CompanyDao {
	/**
	 * 通过公司标识取得公司信息
	 * @param identifier
	 * @return
	 */
	Company getCompanyByIdentifier(String identifier);
	/**
	 * 根据companyID查询出公司相信信息
	 * @param companyID
	 * @return
	 */
	CDetail getCDetailByCompanyID(String companyID);
	/**
	 * 根据companyID查询出Company公司信息
	 * @param companyID
	 * @return
	 */
	Company getCompanyByCompanyID(String companyID);
	/**
	 * 根据companyID查询子公司信息
	 * @param companyID
	 * @return
	 */
	
	List<Company> getCompanyListByPID(String companyID);
}
