package hy.ea.finance.dao;

import hy.plat.bo.BaseBean;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;


public interface FinanceDao {
	public String getamount(String hql,Object[]params);
	public List<BaseBean> getListByDC(DetachedCriteria dc);
}
