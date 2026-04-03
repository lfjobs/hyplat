package hy.plat.dao.impl;

import hy.plat.bo.SMenu;
import hy.plat.dao.SMenuDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class SMenuDaoImpl implements SMenuDao{
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public void deleteMenuByID(String menuID) {
		String hqlMC = "delete SEMB where menuID = ? ";
		String hqlRMCO = " delete SREMI where menuID = ? ";
		String hqlMenu = " delete SMenu where menuID = ? ";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hqlMC);
		query.setString(0, menuID);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createQuery(hqlRMCO);
		query.setString(0, menuID);
		query.executeUpdate();
		
		query = sessionFactory.getCurrentSession().createQuery(hqlMenu);
		query.setString(0, menuID);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SMenu getMenuByID(String menuID) {
		String hqlSMenu = " from SMenu  where menuID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSMenu);
		query.setString(0, menuID);
		List<SMenu> list = query.list();
		if(null != list && list.size() > 0){
			return (SMenu)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SMenu> getMenuListByEaID(String eaID) {
		String hqlSMenu = " from SMenu  where eaID = ? order by menuNumber ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSMenu);
		query.setString(0, eaID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SMenu> getMenuListByRoleIDAndEaID(String roleID, String eaID) {
		String hqlSMenu = " from SMenu  where menuID in ( select distinct menuID from SREMI where roleID = ? and eaID = ? ) order by menuNumber ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSMenu).setString(0, roleID).setString(1, eaID);
		return query.list();
	}
}