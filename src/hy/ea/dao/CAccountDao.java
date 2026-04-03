package hy.ea.dao;



import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;

public interface CAccountDao { 
	/**
	 * 通过companyID、accountEmail
	 * @param companyID
	 * @param logname
	 * @return
	 */
	CAccount getAccountByCompanyIDAndAccountEmail(String companyID,String accountEmail);	
	
	/**
	 * 根据ID(Not Key)取得CAccount
	 * @param accountID
	 * @return
	 */
	CAccount getCAccountByCompanyIDAndaccountID(String companyID,String accountID);
	
	
	/**
	 * 根据ID(Not Key)删除CAccount
	 * @param companyID
	 * @param accountID
	 */
	void deleteCAccountByCompanyIDAndaccountID(String companyID,String accountID);
	/**
	 * 根据ID(Not Key)拿到此公司的账号总数
	 * @param companyID
	 * @param accountID
	 */
	int  getConutByCompanyID(String companyID);

	/*
	 * 账户版本升级
	 */
	void updateRelease(CAccount  account,CLogBook logBook);

}
