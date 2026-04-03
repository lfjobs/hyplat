package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import hy.ea.bo.Company;


public interface  Office5L5CService {
	
	
	public TEshopCusCom getCusCom(String user);


	public TEshopCustomer getCustomer(String user);

	public Company getCompanyInfo(String companyID);
}
