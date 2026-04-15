package hy.ea.dao.impl;

import hy.ea.bo.CEA;
import hy.ea.bo.CEMB;
import hy.ea.bo.CMenu;
import hy.ea.bo.CREMI;
import hy.ea.dao.CEADao;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.SEA;
import hy.plat.bo.SEMB;
import hy.plat.bo.SMenu;
import hy.plat.bo.SREMI;
import hy.plat.dao.SEMBDao;
import hy.plat.dao.SMenuDao;
import hy.plat.dao.SREMIDao;
import hy.plat.service.ServerService;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class CEADaoImpl implements CEADao{
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private ServerService serverService;
	@Resource
	private SMenuDao smenuDao;
	@Resource
	private SEMBDao sembDao;
	@Resource
	private SREMIDao sremiDao;


	@SuppressWarnings("unchecked")
	@Override
	public List<SEA> getListSea(String companyID) {
		String hqlSEA = " from SEA s where s.eaStatus = '00' and exists (select 1 from CEA c where c.eaID = s.eaID and c.companyID = ?)  order by eaSort ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEA).setString(0, companyID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SEA> getListSea(String companyID,String roleID) {
		String hqlSEA = " from SEA s where s.eaStatus = '00' and s.eaType = '00' and exists ( select 1 from CREMI where eaID = s.eaID and companyID = ? and roleID = ? )  order by eaSort ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSEA).setString(0, companyID).setString(1, roleID);
		return query.list();
	}

	@Override
	public void pushSEAtoCEA(String companyID, String eaID,String roleID) {
		
		try {
			//SEA sea =seadao.getSEAByID(eaID);
			deleteForUpade(companyID, eaID);
			//生成CEA
			CEA cea = new CEA();
			cea.setCeaID(serverService.getServerID("cea"));
			cea.setCompanyID(companyID);
			cea.setEaID(eaID);
			cea.setCeaType("00");
			cea.setCeaBuyDate(new Date());
			cea.setCeaInvalidDate(Utilities.getAfterDate(new Date(), 3000));
			cea.setCeaTryDate(new Date());
			cea.setCeaStatus("00");
			sessionFactory.getCurrentSession().persist(cea);
			//生成Menu信息
			List<SMenu> smenuList = smenuDao.getMenuListByEaID(eaID);
			for (SMenu smenu : smenuList) {
				CMenu cmenu = new CMenu();
				cmenu.setMenuID(smenu.getMenuID());
				cmenu.setCompanyID(companyID);
				cmenu.setEaID(eaID);
				cmenu.setMenuName(smenu.getMenuName());
				cmenu.setMenuNumber(smenu.getMenuNumber());
				cmenu.setMenuDesc(smenu.getMenuDesc());
				sessionFactory.getCurrentSession().persist(cmenu);
			}
			//生成CEMB信息
			List<SEMB> sembList = sembDao.getEMBListByEaID(eaID);
			for (SEMB semb : sembList) {
				CEMB cemb = new CEMB();
				cemb.setEmbID(semb.getEmbID());
				cemb.setCompanyID(companyID);
				cemb.setEaID(eaID);
				cemb.setMenuID(semb.getMenuID());
				cemb.setBoID(semb.getBoID());
				sessionFactory.getCurrentSession().persist(cemb);
			}
			//生成CREMI信息
			List<SREMI> sremiList = sremiDao.getSREMIByEaID(eaID);
			for (SREMI sremi : sremiList) {
				CREMI cremi = new CREMI();
				cremi.setRemiID(sremi.getRemiID());
				cremi.setCompanyID(companyID);
				cremi.setRoleID(roleID);
				cremi.setEaID(eaID);
				cremi.setMenuID(sremi.getMenuID());
				cremi.setInterfaceID(sremi.getInterfaceID());
				cremi.setInterfaceStatus(sremi.getInterfaceStatus());
				cremi.setInterfaceUrl(sremi.getInterfaceUrl());
				sessionFactory.getCurrentSession().persist(cremi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteForUpade(String companyID,String eaID) {
		String hqlSEA1 = "delete  CMenu  where  companyID = ?  and eaID = ?";
		String hqlSEA2 = "delete  CEMB  where  companyID = ?  and eaID = ?";
		String hqlSEA3 = "delete  CREMI  where  companyID = ?  and eaID = ?";
		sessionFactory.getCurrentSession().createQuery(hqlSEA1).setString(0, companyID).setString(1, eaID).executeUpdate();
		sessionFactory.getCurrentSession().createQuery(hqlSEA2).setString(0, companyID).setString(1, eaID).executeUpdate();
		sessionFactory.getCurrentSession().createQuery(hqlSEA3).setString(0, companyID).setString(1, eaID).executeUpdate();
	}

	@Override
	public void updateSEAtoCEA(String companyID, SEA cea, String roleID) {
		deleteForUpade(companyID,cea.getEaID());
		//生成Menu信息
		List<SMenu> smenuList = smenuDao.getMenuListByEaID(cea.getEaID());
		for(SMenu smenu : smenuList){
			CMenu cmenu = new CMenu();
			cmenu.setMenuID(smenu.getMenuID());
			cmenu.setCompanyID(companyID);
			cmenu.setEaID(cea.getEaID());
			cmenu.setMenuName(smenu.getMenuName());
			cmenu.setMenuNumber(smenu.getMenuNumber());
			cmenu.setMenuDesc(smenu.getMenuDesc());
			sessionFactory.getCurrentSession().persist(cmenu);
		}
		
		//生成CEMB信息
		List<SEMB> sembList = sembDao.getEMBListByEaID(cea.getEaID());
		for(SEMB semb : sembList){
			CEMB cemb = new CEMB();
			cemb.setEmbID(semb.getEmbID());
			cemb.setCompanyID(companyID);
			cemb.setEaID(cea.getEaID());
			cemb.setMenuID(semb.getMenuID());
			cemb.setBoID(semb.getBoID());
			sessionFactory.getCurrentSession().persist(cemb);
		}
		
		//生成CREMI信息
		List<SREMI> sremiList = sremiDao.getSREMIByEaID(cea.getEaID());
		for(SREMI sremi : sremiList){
			CREMI cremi = new CREMI();
			cremi.setRemiID(sremi.getRemiID());
			cremi.setCompanyID(companyID);
			cremi.setRoleID(roleID);
			cremi.setEaID(cea.getEaID());
			cremi.setMenuID(sremi.getMenuID()); 
			cremi.setInterfaceID(sremi.getInterfaceID());
			cremi.setInterfaceStatus(sremi.getInterfaceStatus());
			cremi.setInterfaceUrl(sremi.getInterfaceUrl());
			sessionFactory.getCurrentSession().persist(cremi);
		}
		
	}
	@SuppressWarnings("unchecked")
	public void versionUpgrade(String companyID, String roleID) {
		
		//生成ea信息
		Query query_ea = sessionFactory.getCurrentSession().createQuery(" from SEA s where s.eaStatus = ? and s.eaType != ? and not exists (select 1 from CEA c where c.eaID = s.eaID and c.companyID = ?) ");
		List<BaseBean> ls_ea = query_ea.setString(0, "00").setString(1, "01").setString(2, companyID).list();
		
		for(BaseBean bean : ls_ea){
			CEA cea = new CEA();
			SEA sea = (SEA) bean;
			cea.setCeaID(serverService.getServerID("cea"));
			cea.setCompanyID(companyID);
			cea.setEaID(sea.getEaID());
			cea.setCeaType("00");
			cea.setCeaBuyDate(new Date());
			cea.setCeaInvalidDate(Utilities.getAfterDate(new Date(), 3000));
			cea.setCeaTryDate(new Date());
			cea.setCeaStatus("00");
			sessionFactory.getCurrentSession().persist(cea);
		}
		Query delete_ea = sessionFactory.getCurrentSession().createQuery("  delete CEA c where c.companyID = ? and not exists (select 1 from SEA s where s.eaID = c.eaID and s.eaStatus = ? and s.eaType != ? ) ");
		delete_ea.setString(0, companyID).setString(1, "00").setString(2, "01").executeUpdate();
		
		//生成Menu信息
		Query query_menu = sessionFactory.getCurrentSession().createQuery("select s from SMenu s where not exists (select 1 from CMenu c where  c.companyID = ? and c.menuName = s.menuName and s.eaID = c.eaID ) ");
		List<BaseBean> ls_menu = query_menu.setString(0, companyID).list();
		
		for(BaseBean smenu : ls_menu){
			CMenu cmenu = new CMenu();
			SMenu sm = (SMenu) smenu;
			cmenu.setMenuID(sm.getMenuID());
			cmenu.setCompanyID(companyID);
			cmenu.setEaID(sm.getEaID());
			cmenu.setMenuName(sm.getMenuName());
			cmenu.setMenuNumber(sm.getMenuNumber());
			cmenu.setMenuDesc(sm.getMenuDesc());
			sessionFactory.getCurrentSession().persist(cmenu);
		}
		Query delete_menu = sessionFactory.getCurrentSession().createQuery("delete  CMenu c where  c.companyID = ?  and not exists (select 1 from SMenu s where c.menuName = s.menuName and c.menuID = s.menuID)");
		delete_menu.setString(0, companyID).executeUpdate();
		
		//生成CEMB信息
		Query delete_cemb = sessionFactory.getCurrentSession().createQuery("delete  CEMB c where  c.companyID = ?");
		delete_cemb.setString(0, companyID).executeUpdate();
		
		Query query_cemb = sessionFactory.getCurrentSession().createQuery("select s from SEMB s ");
		List<BaseBean> ls_cemb = query_cemb.list();
		
		for(BaseBean bean : ls_cemb){
			CEMB cemb = new CEMB();
			SEMB semb = (SEMB) bean;
			cemb.setEmbID(semb.getEmbID());
			cemb.setCompanyID(companyID);
			cemb.setEaID(semb.getEaID());
			cemb.setMenuID(semb.getMenuID());
			cemb.setBoID(semb.getBoID());
			sessionFactory.getCurrentSession().persist(cemb);
		}
		Query delete_cremi = sessionFactory.getCurrentSession().createQuery("  delete CREMI c where c.companyID = ? and not exists (select 1 from SInterface s where s.interfaceUrl = c.interfaceUrl and s.interfaceID=c.interfaceID  ) ");
		delete_cremi.setString(0, companyID).executeUpdate();
		
	}
	

}