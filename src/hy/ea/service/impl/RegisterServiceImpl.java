package hy.ea.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.dao.RegisterDao;
import hy.ea.service.RegisterService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService{
	
	@Resource
	private RegisterDao registerDao;

	@Override
	public boolean register(Company company, CDetail companyDetail,
			CAccount account) {
		return registerDao.register(company, companyDetail, account);
	}
	
	@Override
	public boolean register(Company company,ContactCompany contactCompany, 
			CDetail companyDetail, CAccount account) {
		return registerDao.register(company, contactCompany, companyDetail, account);
	}

	@Override
	public boolean isRegister(String companyIdentifier) {
		return registerDao.isRegister(companyIdentifier);
	}

}
