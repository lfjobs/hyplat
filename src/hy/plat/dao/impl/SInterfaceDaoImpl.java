package hy.plat.dao.impl;

import hy.plat.bo.SInterface;
import hy.plat.dao.SInterfaceDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class SInterfaceDaoImpl implements SInterfaceDao{
	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public SInterface getSInterfaceByID(String interfaceID) {
		String hqlSInterface = " from SInterface  where interfaceID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSInterface);
		query.setString(0, interfaceID);
		List<SInterface> list = query.list();
		if(null != list && list.size() > 0){
			return (SInterface)list.get(0);
		}
		return null;
	}

	@Override
	public void deleteSInterfaceByID(String interfaceID) {
		String hqlRMCO = " delete SREMI where interfaceID = ? ";
		String hqlRole = " delete SInterface where interfaceID = ? ";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hqlRMCO);
		query.setString(0, interfaceID);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createQuery(hqlRole);
		query.setString(0, interfaceID);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SInterface> getSInterfaceList() {
		String hqlSInterface = " from SInterface  where interfaceStatus like '0%' order by interfaceID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSInterface);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SInterface> getSinterfaceListByBoID(String boID) {
		String hqlSInterface = " from SInterface  where interfaceStatus like '0%'  and  boID = ? order by interfaceID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSInterface).setString(0, boID);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SInterface> getSInterfaceListByStatus() {
		String hqlSInterface = " from SInterface  where interfaceStatus = '03' order by interfaceID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSInterface);
		return query.list();
	}

}