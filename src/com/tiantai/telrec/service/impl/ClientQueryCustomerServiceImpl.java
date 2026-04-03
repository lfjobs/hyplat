package com.tiantai.telrec.service.impl;

import java.util.List;

import com.tiantai.telrec.bo.TelrecCustomerInfo;
import com.tiantai.telrec.bo.TelrecTelInInfo;
import com.tiantai.telrec.tool.QuerySocietalCustomerImpl;

public class ClientQueryCustomerServiceImpl implements
		com.tiantai.telrec.service.ClientQueryCustomerService {

	private com.tiantai.telrec.dao.TelInInfoDao telInDao;
	private com.tiantai.telrec.dao.impl.TelrecCustomerDaoImpl customerDao;
	private QuerySocietalCustomerImpl societalCustomerDao;
	private org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager;

	public QuerySocietalCustomerImpl getSocietalCustomerDao() {
		return societalCustomerDao;
	}

	public void setSocietalCustomerDao(
			QuerySocietalCustomerImpl societalCustomerDao) {
		this.societalCustomerDao = societalCustomerDao;
	}

	public String insertTelInInfo(final TelrecTelInInfo info) {
		telInDao.insertTelIn(info);
		return info.getId();
	}

	@SuppressWarnings("unused")
	private com.tiantai.telrec.dao.TelInInfoDao getTelInDao() {
		return telInDao;
	}

	public void setTelInDao(com.tiantai.telrec.dao.TelInInfoDao telInDao) {
		this.telInDao = telInDao;
	}

	public com.tiantai.telrec.dao.impl.TelrecCustomerDaoImpl getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(
			com.tiantai.telrec.dao.impl.TelrecCustomerDaoImpl customerDao) {
		this.customerDao = customerDao;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCustomerInfo(String telno) {

		return customerDao.getCustomerInfo(telno);

	}

	/*
	 * public List queryCustomer(String telno) {
		return this.societalCustomerDao.q
	}
*/
	@Override
	public void insertCustomerInfo(TelrecCustomerInfo info) {
		this.customerDao.insertCustomerInfo(info);
	}

	public org.springframework.orm.hibernate3.HibernateTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCustomerInfoList(TelrecCustomerInfo info) {
		return this.customerDao.getCustomerList(info);
	}

	@Override
	public Object getCustomerInfoForId(String customerId) {
		return this.customerDao.getCustomerInfoByid(customerId);

	}

	// 查找客户分页并根据公司
	@SuppressWarnings("rawtypes")
	@Override
	public List queryCustomerForSplit(int pageNum, int recNum, String companyid) {
		return this.customerDao.getCustomerForSplit(pageNum, recNum, companyid);

	}

	@Override
	public int total(String companyid, int rp) {
		return this.customerDao.getTotal(companyid, rp);
	}

	@Override
	public boolean hasCustomer(TelrecCustomerInfo customerInfo) {
		return this.customerDao.hasCustomer(customerInfo);

	}

	@Override
	public void updateCustomer(TelrecCustomerInfo customerInfo) {
		this.customerDao.updateCustomer(customerInfo);
	}
}
