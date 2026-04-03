package com.tiantai.telrec.service;

import hy.tel.bo.TelInRecord;
import hy.tel.bo.TelOutRecord;

import java.util.Date;
import java.util.List;

import com.tiantai.telrec.bo.TelrecTelInfo;

public interface ClientInsertTelInfoService {
	public String insertTelInfo(TelrecTelInfo info);

	public boolean updateTelInfoWavePath(String id, String path);

	public void deleteTelInInfo(String telinId);

	@SuppressWarnings("rawtypes")
	public List queryTelrecInfoForUserid(String id, String date);

	@SuppressWarnings("rawtypes")
	public List queryTelrecForDate(String id, String time);

	/**
	 * 打入电话记录
	 * @param telno 电话号
	 * @param company 所属公司
	 * @return
	 */
	public List<TelInRecord> queryTelrecForTelIn(String telno,String company);
	/**
	 * 打出电话记录
	 * @param telno 电话号
	 * @param company 所属公司
	 * @return
	 */
	public List<TelOutRecord> queryTelrecForTelOut(String telno,String company);
    
	
	@SuppressWarnings("rawtypes")
	List queryTelrecInfoForCompanyid(String companyid, String date);

	@SuppressWarnings("rawtypes")
	List queryTelrecInfoForUserid(String userid, String starttime,
			String endtime);
	@SuppressWarnings("rawtypes")
	public List queryTelrecInfoForCustomerId(String customerId, int rp,
			int pages);

	public int totalForCustomerId(String customerId, int rp);
	@SuppressWarnings("rawtypes")
	public List queryTelrecInfoForParams(String customerId, int rp, int pages,
			String customer_name, String user_name, Date startdate);

	public int queryTelrecInfoForParamsTotal(String customerId, int rp,
			int pages, String customer_name, String user_name, Date startdate);
	@SuppressWarnings("rawtypes")
	public List queryTelForCustomer_nameAll(String companyid);
	@SuppressWarnings("rawtypes")
	public List queryTelForUser_nameAll(String companyid);
		

}
