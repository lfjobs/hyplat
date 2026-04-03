package hy.ea.dao.impl;

import hy.ea.bo.CEMB;
import hy.ea.bo.CLogBook;
import hy.ea.dao.CEMBDao;
import hy.plat.service.ServerService;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class CEMBDaoImpl implements CEMBDao{
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private ServerService serverService;

	@SuppressWarnings("unchecked")
	@Override
	public List<CEMB> getCEMBListByEaIDAndMenuID(String companyID,String eaID,String menuID) {
		String hqlSEMB = " from CEMB  where companyID = ? and eaID = ? and menuID = ? order by eaID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEMB);
		query.setString(0, companyID);
		query.setString(1, eaID);
		query.setString(2, menuID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CEMB> getCEMBListByEaID(String companyID,String eaID) {
		String hqlSEMB = " from CEMB  where companyID = ? and eaID = ? order by eaID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEMB);
		query.setString(0, companyID);
		query.setString(1, eaID);
		return query.list();
	}
	
	@Override
	public void saveCEMB(String companyID,String eaID, String menuID, String[] boIDs,CLogBook logBook) {
		
		//删除已经分配的此EA的此Menu的BO信息
		String hqlSemb = " delete CEMB where companyID = ? and eaID = ? and menuID = ? ";
		Query queryBO = sessionFactory.getCurrentSession().createQuery(hqlSemb).setString(0, companyID).setString(1, eaID).setString(2, menuID);
		queryBO.executeUpdate();
		//删除已经分配的此EA的所有Role的所有此菜单的Interface信息
		String hqlSremi = " delete CREMI where companyID = ? and eaID = ? and menuID = ? ";
		Query queryInterface = sessionFactory.getCurrentSession().createQuery(hqlSremi).setString(0, companyID).setString(1, eaID).setString(2, menuID);
		queryInterface.executeUpdate();
		
		//保存本次分配的信息
		if(null != boIDs){
			for(String boID : boIDs){
				CEMB cemb = new CEMB();
				cemb.setEmbID(serverService.getServerID("cemb"));
				cemb.setBoID(boID);
				cemb.setEaID(eaID);
				cemb.setMenuID(menuID);
				cemb.setCompanyID(companyID);
				sessionFactory.getCurrentSession().persist(cemb);
			}
		}
		sessionFactory.getCurrentSession().merge(logBook);
	}

}