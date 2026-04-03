package hy.ea.service;

import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public interface CompanyService {
	/**
	 * 通过公司标识取得公司信息
	 * @param identifier
	 * @return
	 */
	Company getCompanyByIdentifier(String identifier);
	/**
	 * 根据companyID查询出Cdetail公司相信信息
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
	 * 查出公司以有所有子公司的id
	 * @return 包公司已有子公司id的数组
	 */
	ArrayList<String> getCompanyAndItsChildrenIDs(String companyID);
	/**
	 * 查出公司以有所有子公司
	 * @return 包公司已有子公司的数组
	 */
	List<BaseBean> getCompanyAndItsChildren(String companyID);
	/**
	 * 根据companyID查询子公司信息
	 * @param companyID
	 * @return
	 */
	
	List<Company> getCompanyListByPID(String companyID);

	/**
	 * 保存公司信息
	 *
	 * @param contactCompany 往来单位信息
	 * @param logo 公司logo文件
	 * @param logoFileName logo文件名
	 * @param merchant_shortname 公司/商家简称
	 */
	void saveCompanyInfo(ContactCompany contactCompany, File logo, String logoFileName,String merchant_shortname);
}
