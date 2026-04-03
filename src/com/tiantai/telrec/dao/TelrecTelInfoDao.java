package com.tiantai.telrec.dao;

import java.util.List;

import com.tiantai.telrec.bo.TelrecTelInfo;

public interface TelrecTelInfoDao {
	/**
	 * 电话打入记录 5条
	 * 
	 * @param telno
	 *            电话号
	 * @param company
	 *            所属公司
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryCustomerTelRecordIn(String telno, String company);

	/**
	 * 电话打出记录 5条
	 * 
	 * @param telno
	 *            电话号
	 * @param company
	 *            所属公司
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryCustomerTelRecordOut(String telno, String company);

	public void insertTelInfo(TelrecTelInfo telInfo);

	public boolean updateTelInfo(String id, String path);

	@SuppressWarnings("rawtypes")
	public List queryTelForDate(int telType, String date);

	@SuppressWarnings("rawtypes")
	public List queryTelrecInfoForUserid(String userid, String starttime,
			String endtime);

}
