package com.tiantai.telrec.service.impl;

import com.tiantai.telrec.bo.TelrecCustomerInfo;
import com.tiantai.telrec.dao.TelrecCustomerDao;
import com.tiantai.telrec.service.ClientInsertCustomerService;

public class ClientInsertCustomerServiceImpl implements
		ClientInsertCustomerService {
	private TelrecCustomerDao customerDao;
	private org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager;

	public org.springframework.orm.hibernate3.HibernateTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public String insertCustomer(TelrecCustomerInfo info) {
		this.customerDao.insertCustomerInfo1(info);
		return info.getId();
	}

	public TelrecCustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(TelrecCustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	
}
