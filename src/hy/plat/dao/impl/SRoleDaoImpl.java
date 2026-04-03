package hy.plat.dao.impl;

import hy.plat.bo.SRole;
import hy.plat.dao.SRoleDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class SRoleDaoImpl implements SRoleDao{

	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void deleteRoleByID(String roleID) {
		String hqlRMCO = " delete SREMI where roleID = ? ";
		String hqlRole = " delete SRole where roleID = ? ";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hqlRMCO);
		query.setString(0, roleID);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createQuery(hqlRole);
		query.setString(0, roleID);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SRole getRoleByID(String roleID) {
		String hqlSRole = " from SRole  where roleID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSRole);
		query.setString(0, roleID);
		List<SRole> list = query.list();
		if(null != list && list.size() > 0){
			return (SRole)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SRole> getRoleList() {
		String hqlSRole = " from SRole order by roleName ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSRole);
		return query.list();
	}
}
