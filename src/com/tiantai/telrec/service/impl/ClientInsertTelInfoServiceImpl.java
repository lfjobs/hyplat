package com.tiantai.telrec.service.impl;

import hy.tel.bo.TelOutRecord;

import java.util.Date;
import java.util.List;

import com.tiantai.telrec.bo.TelrecTelInfo;
import com.tiantai.telrec.service.ClientInsertTelInfoService;

public class ClientInsertTelInfoServiceImpl implements
		ClientInsertTelInfoService {
	private com.tiantai.telrec.dao.impl.TelrecCustomerDaoImpl customerDao;
	private com.tiantai.telrec.dao.impl.TelrecTelInfoDaoImpl telInfoDao;
	private com.tiantai.telrec.dao.impl.TelInInfoDaoImpl telinInfoDao;
	private org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager;

	public org.springframework.orm.hibernate3.HibernateTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			org.springframework.orm.hibernate3.HibernateTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public com.tiantai.telrec.dao.impl.TelrecCustomerDaoImpl getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(
			com.tiantai.telrec.dao.impl.TelrecCustomerDaoImpl customerDao) {
		this.customerDao = customerDao;
	}

	public com.tiantai.telrec.dao.impl.TelrecTelInfoDaoImpl getTelInfoDao() {
		return telInfoDao;
	}

	public void setTelInfoDao(
			com.tiantai.telrec.dao.impl.TelrecTelInfoDaoImpl telInfoDao) {
		this.telInfoDao = telInfoDao;
	}

	@Override
	public String insertTelInfo(TelrecTelInfo info) {
		this.getTelInfoDao().insertTelInfo(info);
		return info.getId();
	}

	@Override
	public boolean updateTelInfoWavePath(String id, String path) {
		return this.getTelInfoDao().updateTelInfo(id, path);
	}

	@Override
	public void deleteTelInInfo(String telinId) {
		this.getTelinInfoDao().deleteForIdForId(telinId);
	}

	public com.tiantai.telrec.dao.impl.TelInInfoDaoImpl getTelinInfoDao() {
		return telinInfoDao;
	}

	public void setTelinInfoDao(
			com.tiantai.telrec.dao.impl.TelInInfoDaoImpl telinInfoDao) {
		this.telinInfoDao = telinInfoDao;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelrecInfoForUserid(String userid, String starttime,
			String endtime) {
		return this.getTelInfoDao().queryTelrecInfoForUserid(userid, starttime,
				endtime);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelrecForDate(String id, String time) {
		List list = this.telInfoDao.queryTelForDate(id, time);
		return list;
	}

	/**
	 * 电话打入记录5条
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List queryTelrecForTelIn(String telno,String company) {
		return this.telInfoDao.queryCustomerTelRecordIn(telno,company);
	}
	/**
	 * 电话打出记录5条
	 */
	@Override
	public List<TelOutRecord> queryTelrecForTelOut(String telno, String company) { 
		return this.telInfoDao.queryCustomerTelRecordOut(telno,company);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelrecInfoForCompanyid(String companyid, String date) {
		return this.telInfoDao.queryTelForDateForCompanyid(companyid, date);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelrecInfoForUserid(String id, String date) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelrecInfoForCustomerId(String customerId, int rp,
			int pages) {
		return this.telInfoDao.queryTelForDateForCustomerId(customerId, rp,
				pages);
	}

	@Override
	public int totalForCustomerId(String customerId, int rp) {
		return this.telInfoDao.toalForCustomerId(customerId, rp);
	}



	@Override
	public int queryTelrecInfoForParamsTotal(String customerId, int rp,
			int pages, String customer_name, String user_name, Date startdate) {
		return this.telInfoDao.toalForCustomerForTatol(customerId, rp, pages,
				customer_name, user_name, startdate);

	}

	@SuppressWarnings("rawtypes")
	public List queryTelrecInfoForParams(String customerId, int rp, int pages,
			String customer_name, String user_name, Date startdate) {
		return this.telInfoDao.queryTelrecInfoFroParam(customerId, rp, pages,
				customer_name, user_name, startdate);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelForCustomer_nameAll(String companyid) {
		return this.telInfoDao.queryTelForCustomer_nameAll(companyid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryTelForUser_nameAll(String companyid) {
		return this.telInfoDao.queryTelForUser_nameAll(companyid);
	}
}
