package hy.ea.finance.dao.impl;


import hy.ea.finance.dao.FinanceDao;
import hy.plat.bo.BaseBean;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class FinanceDaoImpl implements FinanceDao{
	
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public String getamount(String hql, Object[] params) {
	{
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			if (params != null && params.length > 0) {
				int i = 0;
				for (Object param : params) {
					query.setParameter(i++, param);
				}
			}
			String o=null;
			if( query.uniqueResult()!=null)
			{
			o = (String) query.uniqueResult();
			}
			return o;

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseBean> getListByDC(DetachedCriteria dc) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria =dc.getExecutableCriteria(session);
		return criteria.list();
	}

}
