package com.tiantai.telrec.dao;

import java.util.List;

import com.tiantai.telrec.bo.TelrecCustomerInfo;

public interface TelrecCustomerDao {
	public void insertCustomerInfo1(TelrecCustomerInfo customerInfo);

	@SuppressWarnings("rawtypes")
	public List getCustomerInfo(String telno);

	public Object getCustomerInfoForId(String customerid);

	@SuppressWarnings("rawtypes")
	public List getCustomerList(TelrecCustomerInfo customer);

	public int getTotal(String companyid, int rp);
}
