package hy.ea.service.impl;


import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.dao.CAccountDao;
import hy.ea.dao.CompanyDao;
import hy.ea.service.CLoginService;
import hy.ea.util.Utilities;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CLoginServiceImpl implements CLoginService{
	
	@Resource
	private CompanyDao companyDao;
	@Resource
	private CAccountDao accountDao;
	
	@Override
	public int login(String companyIdentifier,CAccount account,String companyType) {
		//判断注册公司是否存在
		Company company = companyDao.getCompanyByIdentifier(companyIdentifier.toLowerCase());
		if(null == company){
			return 1;
		}
		if(null != company){
			if(companyType.equals("00")){ //判断登录公司是否存在
				if(!companyType.equals(company.getCompanyType())){
					return 1;
				}
			}
			if(companyType.equals("01")){ //判断客户登录公司是否存在
				if(company.getCompanyType().contains("1")==false){
					return 1;
				}
			}
		}
		
		//判断注册公司状态是否正常
		if(!"00".equals(company.getCompanyStatus())){
			return 2;
		}
		



		String password = Utilities.MD5(account.getAccountPassword());
		
		//检查登录名是否存在
		CAccount tempAccount = accountDao.getAccountByCompanyIDAndAccountEmail(company.getCompanyID(),account.getAccountEmail());
		if(null == tempAccount){
			return 3;
		}
		//如果登录账号存在则设置返回的staff
		account.setCompanyID(tempAccount.getCompanyID());
		account.setRoleID(tempAccount.getRoleID());
		account.setAccountEmail(tempAccount.getAccountEmail());
		account.setAccountID(tempAccount.getAccountID());
		account.setAccountEmail(tempAccount.getAccountEmail());
		account.setAccountName(tempAccount.getAccountName());
		account.setAccountPassword(tempAccount.getAccountPassword());
		account.setAccountStatus(tempAccount.getAccountStatus());
		account.setAccountKey(tempAccount.getAccountKey());
		account.setStaffID(tempAccount.getStaffID());
		account.setCompanyName(company.getCompanyName());
		account.setCompany(company);
		
		tempAccount = null;
		
		//账号状态不正常
		if(!"00".equals(account.getAccountStatus())){
			return 4;
		}
		
		//检查登录名和密码是否正确
       if(!password.equals(account.getAccountPassword())){
    	   return 5;
       }
		return 0;
	}

}
