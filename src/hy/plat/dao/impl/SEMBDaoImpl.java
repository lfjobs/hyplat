package hy.plat.dao.impl;

import hy.plat.bo.SEMB;
import hy.plat.dao.SEMBDao;
import hy.plat.service.ServerService;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class SEMBDaoImpl implements SEMBDao{
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private ServerService serverService;

	@SuppressWarnings("unchecked")
	@Override
	public List<SEMB> getEMBListByEaIDAndMenuID(String eaID,String menuID) {
		String hqlSEMB = " from SEMB  where eaID = ? and menuID = ? order by eaID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEMB);
		query.setString(0, eaID);
		query.setString(1, menuID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SEMB> getEMBListByEaID(String eaID) {
		String hqlSEMB = " from SEMB  where eaID = ? order by eaID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEMB);
		query.setString(0, eaID);
		return query.list();
	}
	
	@Override
	public void saveSEMB(String eaID, String menuID, String[] boIDs) {
		
		//删除已经分配的此EA的此Menu的BO信息
		String hqlSemb = " delete SEMB where eaID = ? and menuID = ? ";
		Query queryBO = sessionFactory.getCurrentSession().createQuery(hqlSemb).setString(0, eaID).setString(1, menuID);
		queryBO.executeUpdate();
		//删除已经分配的此EA的所有Role的所有此菜单的Interface信息
		String hqlSremi = " delete SREMI where eaID = ? and menuID = ? ";
		Query queryInterface = sessionFactory.getCurrentSession().createQuery(hqlSremi).setString(0, eaID).setString(1, menuID);
		queryInterface.executeUpdate();
		
		//保存本次分配的信息
		if(null != boIDs){
			for(String boID : boIDs){
				SEMB semb = new SEMB();
				semb.setEmbID(serverService.getServerID("semb"));
				semb.setBoID(boID);
				semb.setEaID(eaID);
				semb.setMenuID(menuID);
				sessionFactory.getCurrentSession().persist(semb);
			}
		}
	}

}