package hy.ea.marketing.dao;

import hy.ea.marketing.bo.DtCrmCustomerProduct;



public interface DtCrmCustomerProductDao{
	
	public void save(DtCrmCustomerProduct prod);
	
	public void delete(DtCrmCustomerProduct prod);
}