package com.tiantai.telrec.dao;

import java.util.List;

import com.tiantai.telrec.bo.TelrecTelInInfo;
public interface TelInInfoDao {


	@SuppressWarnings("rawtypes")
	public List getTelInListForDate(String companyid, String date1, String date2);
	@SuppressWarnings("rawtypes")
	public List getTelInListForUserid(String date1, String date2, String userid);

	public void insertTelIn(TelrecTelInInfo info);
	@SuppressWarnings("rawtypes")
	public List getMissedCallForAll(String date1, String date2, String companyid);
	@SuppressWarnings("rawtypes")
	public List getMissedCallForUserId(String date1, String date2, String userid);

	public void deleteForIdForId(String telinId);
}
