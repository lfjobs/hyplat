package hy.ea.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.dao.CAccountDao;
import hy.ea.service.CAccountService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CAccountServiceImpl implements CAccountService{
	
	@Resource
	private CAccountDao caccountDao;

	@Override
	public void deleteCAccountByCompanyIDAndaccountID(String companyID,
			String accountID) {
		caccountDao.deleteCAccountByCompanyIDAndaccountID(companyID, accountID);
	}

	@Override
	public CAccount getCAccountByCompanyIDAndaccountID(String companyID,
			String accountID) {
		return caccountDao.getCAccountByCompanyIDAndaccountID(companyID, accountID);
	}

	@Override
	public int getConutByCompanyID(String companyID
			) {
		return caccountDao.getConutByCompanyID(companyID);
	}

	@Override
	public boolean isAccount(String companyID,String accountEmail) {
		return null == caccountDao.getAccountByCompanyIDAndAccountEmail(companyID, accountEmail)?false:true;
	}

	@Override
	public void updateRelease(CAccount  account,CLogBook logBook) {
		caccountDao.updateRelease(account,logBook);
		
	}

	
}
