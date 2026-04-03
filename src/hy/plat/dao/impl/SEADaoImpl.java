package hy.plat.dao.impl;

import hy.plat.bo.SEA;
import hy.plat.dao.SEADao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class SEADaoImpl implements SEADao{
	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public SEA getSEAByID(String eaID) {
		String hqlSEA = " from SEA  where eaID = ? order by eaSort";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEA);
		query.setString(0, eaID);
		List<SEA> list = query.list();
		if(null != list && list.size() > 0){
			return (SEA)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SEA> getListSea() {
		String hqlSEA = " from SEA  where eaStatus = '00' order by eaSort ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEA);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SEA> getListSea(String roleID) {
		String hqlSEA = " from SEA  where eaStatus = '00' and eaID in ( select distinct eaID from SREMI where roleID = ? ) order by eaSort ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEA).setString(0, roleID);
		return query.list();
	}

}