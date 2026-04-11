package com.tiantai.telrec.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import com.tiantai.telrec.dao.impl.TelInInfoDaoImpl;
import com.tiantai.telrec.service.ClientMissCalledService;

public class ClientMissCalledServiceImpl implements ClientMissCalledService {

	private TelInInfoDaoImpl telInDao;
	private org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager;

	public org.springframework.orm.hibernate3.HibernateTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public TelInInfoDaoImpl getTelInDao() {
		return telInDao;
	}

	public void setTelInDao(TelInInfoDaoImpl telInDao) {
		this.telInDao = telInDao;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getMissedCallForAll(String date1, String date2, String companyid) {
		return this.telInDao.getMissedCallForAll(date1, date2, companyid);
	}

	@SuppressWarnings({ "rawtypes", "finally" })
	@Override
	public List getMissedCallForUserId(String date1, String date2, String userid) {
		List list = new ArrayList();
		try {
			list = this.telInDao.getMissedCallForUserId(date1, date2, userid);
		} catch (Exception e) {
			logger.error("操作异常", e);
		} finally {
			return list;
		}

	}
}
