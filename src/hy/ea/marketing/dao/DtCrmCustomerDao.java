package hy.ea.marketing.dao;

import hy.ea.marketing.bo.DtCrmCustomer;
import hy.ea.marketing.bo.DtCrmCustomermenu;

import java.util.List;



public interface DtCrmCustomerDao {
	
	public List getList(String groupSn);
	
	/**
	 * 根据公司id获得右上角菜单
	 * @param companyId  当前公司id
	 * @return
	 */
	public DtCrmCustomermenu getCustMenuByCompID(String companyId);
	
	/**
	 * 根据ID获取个人客户
	 * @param customerId  个人客户Id
	 * @return
	 */
	public DtCrmCustomer getCustomerById(String customerId);
	
	/**
	 * 根据ID删除个人客户
	 * @param customerId 个人客户Id
	 */
	public void delCustomerById(String customerId);
	
	
}