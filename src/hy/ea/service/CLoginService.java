package hy.ea.service;

import hy.ea.bo.CAccount;

public interface CLoginService {
	
	/**
	 * 登录
	 * @param companyIdentifier 
	 * @param account
	 * @return
	 */
	int login(String companyIdentifier,CAccount account,String companyType);
}
