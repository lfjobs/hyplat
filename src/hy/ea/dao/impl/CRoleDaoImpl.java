package hy.ea.dao.impl;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.dao.CRoleDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class CRoleDaoImpl implements CRoleDao{

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void deleteRoleByID(String companyID,String roleID,CLogBook logBook) {
		String hqlRMCO = " delete CREMI where companyID = ? and roleID = ? ";
		String hqlRole = " delete CRole where companyID = ? and roleID = ? ";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hqlRMCO);
		query.setString(0, companyID);
		query.setString(1, roleID);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createQuery(hqlRole);
		query.setString(0, companyID);
		query.setString(1, roleID);
		query.executeUpdate();
		
		sessionFactory.getCurrentSession().merge(logBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CRole getRoleByID(String companyID,String roleID) {
		String hqlCRole = " from CRole  where companyID = ? and roleID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlCRole);
		query.setString(0, companyID);
		query.setString(1, roleID);
		List<CRole> list = query.list();
		if(null != list && list.size() > 0){
			return (CRole)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CRole> getRoleList(String companyID) {
		String hqlCRole = " from CRole where companyID = ? order by roleName ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlCRole).setString(0, companyID);
		return query.list();
	}
}
