package hy.ea.service;

import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;

public interface RegisterService {
	
	/**
	 * 注册
	 * @param company
	 * @param companyDetail
	 * @param account
	 * @return
	 */
	boolean register(Company company,CDetail companyDetail,CAccount account);
	
	/**
	 * 注册并修改未注册单位(社会单位)为已注册单位
	 * @param company   公司
	 * @param contactCompany  往来单位   
	 * @param companyDetail   公司详细信息
	 * @param account  当前账户
	 * @return
	 */
	boolean register(Company company,ContactCompany contactCompany, CDetail companyDetail, CAccount account);	
	
	/**
	 * 根据公司identifier判断此标识是否已经被注册过，如果没有注册返回false，如果已经注册则返回true
	 * @param companyIdentifier
	 * @return
	 */
	boolean isRegister(String companyIdentifier);
}
