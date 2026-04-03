package hy.ea.dao.impl;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CMenu;
import hy.ea.dao.CMenuDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class CMenuDaoImpl implements CMenuDao{
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void deleteMenuByID(String companyID,CLogBook logBook,String menuID) {
		String hqlMC ="delete CEMB where companyID = ? and menuID = ? ";
		String hqlRMCO = " delete CREMI where companyID = ? and menuID = ? ";
		String hqlMenu = " delete CMenu where companyID = ? and menuID = ? ";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hqlMC);
		query.setString(0, companyID);
		query.setString(1, menuID);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createQuery(hqlRMCO);
		query.setString(0, companyID);
		query.setString(1, menuID);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createQuery(hqlMenu);
		query.setString(0, companyID);
		query.setString(1, menuID);
		query.executeUpdate();
		sessionFactory.getCurrentSession().merge(logBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CMenu getMenuByID(String companyID,String menuID) {
		String hqlCMenu = " from CMenu  where companyID = ? and menuID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlCMenu);
		query.setString(0, companyID);
		query.setString(1, menuID);
		List<CMenu> list = query.list();
		if(null != list && list.size() > 0){
			return (CMenu)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CMenu> getMenuListByEaID(String companyID,String eaID) {
		String hqlCMenu = " from CMenu  where companyID = ? and eaID = ? order by menuNumber ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlCMenu);
		query.setString(0, companyID);
		query.setString(1, eaID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CMenu> getMenuListByRoleIDAndEaID(String companyID,String roleID, String eaID) {
		String hqlCMenu = " from CMenu  where companyID = ? and menuID in ( select distinct menuID from CREMI where companyID = ? and roleID = ? and eaID = ? ) order by menuNumber ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlCMenu).setString(0, companyID).setString(1, companyID).setString(2, roleID).setString(3, eaID);
		return query.list();
	}
}