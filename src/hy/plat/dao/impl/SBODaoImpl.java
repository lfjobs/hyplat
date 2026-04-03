package hy.plat.dao.impl;

import hy.plat.bo.SBO;
import hy.plat.dao.SBODao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class SBODaoImpl implements SBODao{
	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public SBO getSBOByID(String boID) {
		String hqlSBO = " from SBO  where boID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSBO);
		query.setString(0, boID);
		List<SBO> list = query.list();
//		sessionFactory.close();
		if(null != list && list.size() > 0){
			return (SBO)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SBO> getBOListByEaID(String eaID) {
		String hqlSBO = " from SBO where boStatus = '00' and eaID = ?  order by boID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSBO).setString(0, eaID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SBO> getBOList() {
		String hqlSBO = " from SBO where boStatus = '00' order by boID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSBO);
//		sessionFactory.close();
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getboStatusbyURL(String url) {
		String hqlSBO = " from SBO where boID = (select boID from SInterface where interfaceUrl = ?) " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSBO);
		query.setString(0, url);
		List<SBO> list = query.list();
//		sessionFactory.close();
		if(null != list && list.size() > 0){
			return list.get(0).getBoStatus();
		}
		return null;
	}

}