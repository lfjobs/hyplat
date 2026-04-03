package com.tiantai.telrec.service;

import java.util.List;

import com.tiantai.telrec.bo.TelrecCustomerInfo;
import com.tiantai.telrec.bo.TelrecTelInInfo;

public interface ClientQueryCustomerService {
	public String insertTelInInfo(TelrecTelInInfo info);
	@SuppressWarnings("rawtypes")
	public List getCustomerInfo(String telno);
	public void insertCustomerInfo(TelrecCustomerInfo info);
	@SuppressWarnings("rawtypes")
	public List getCustomerInfoList(TelrecCustomerInfo info);

	public Object getCustomerInfoForId(String customerId);

	// 分页查询客户 页码、每页记录数、公司id
	@SuppressWarnings("rawtypes")
	public List queryCustomerForSplit(int pageNum, int recNum, String companyid);

	public int total(String companyid, int rp);

	//
	public boolean hasCustomer(TelrecCustomerInfo customerInfo);

	public void updateCustomer(TelrecCustomerInfo customerInfo);
}
