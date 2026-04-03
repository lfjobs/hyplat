package hy.ea.dao.impl;

import hy.ea.dao.CBODao;
import hy.plat.bo.SBO;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class CBODaoImpl implements CBODao {
	@Resource
	private SessionFactory sessionFactory;


	@SuppressWarnings("unchecked")
	@Override
	public List<SBO> getBOListByEaID(String eaID) {
		String hqlSBO = " from SBO where boStatus = '00' and eaID = ?  order by boID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSBO)
				.setString(0, eaID);
		return query.list();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public SBO getBOByID(String BoID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from SBO where boStatus = '00' and  boID = ? order by boID " );
		query.setString(0, BoID);
		List list = query.list(); 
		if(null != list && list.size() > 0){
			return (SBO)list.get(0);
		}
		return null;
	}


}