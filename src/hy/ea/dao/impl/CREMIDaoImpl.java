package hy.ea.dao.impl;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CREMI;
import hy.ea.dao.CREMIDao;
import hy.plat.bo.SInterface;
import hy.plat.service.ServerService;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class CREMIDaoImpl implements CREMIDao{
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private ServerService serverService;

	@SuppressWarnings("unchecked")
	@Override
	public List<CREMI> getCRemiListByRoleIDAndEaID(String companyID,String roleID,String eaID) {
		String hqlSREMI = " from CREMI  where companyID = ? and roleID = ? and eaID = ? order by roleID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSREMI);
		query.setString(0, companyID);
		query.setString(1, roleID);
		query.setString(2, eaID);
		return query.list();
	}

	@Override
	public void saveCREMI(String companyID,String roleID, String eaID, String[] mis,CLogBook logBook) {
		String hqlSREMI = " delete CREMI where companyID = ? and roleID = ? and eaID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSREMI).setString(0, companyID).setString(1, roleID).setString(2,eaID);
		query.executeUpdate();
		
		if(null != mis){
			for(String mi : mis){
				String []smi = mi.split("@");
				String menuID = smi[0];
				String interfaceID = smi[1];
				String interfaceUrl = smi[2];
				String interfaceStatus = smi[3];
				CREMI cremi = new CREMI();
				cremi.setRemiID(serverService.getServerID("cremi"));
				cremi.setRoleID(roleID);
				cremi.setEaID(eaID);
				cremi.setMenuID(menuID);
				cremi.setInterfaceID(interfaceID);
				cremi.setInterfaceUrl(interfaceUrl);
				cremi.setInterfaceStatus(interfaceStatus);
				cremi.setCompanyID(companyID);
				sessionFactory.getCurrentSession().persist(cremi);
			}
		}
		sessionFactory.getCurrentSession().merge(logBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SInterface> getSInterfaceListByEaIDAndMenuID(String companyID,String eaID, String menuID) {
		String hqlIBME = "  from SInterface where boID in (select distinct boID from CEMB where companyID = ? and eaID = ? and menuID = ?) order by interfaceID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlIBME);
		query.setString(0, companyID);
		query.setString(1, eaID);
		query.setString(2, menuID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CREMI> getCREMIListByRoleID(String companyID,String roleID) {
		String hqlREMI = "  from CREMI where companyID = ? and roleID = ? and  eaID in (select eaID from CEA where  ceaInvalidDate > sysdate and  companyID = ?) order by roleID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlREMI);
		query.setString(0, companyID);
		query.setString(1, roleID);
		query.setString(2, companyID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SInterface> getSInterfaceListByRoleIDAndEaIDAndMenuID(String companyID,String roleID, String eaID, String menuID) {
		String hqlIBME = "  from SInterface where interfaceStatus = '02' and interfaceID in (select distinct interfaceID from CREMI where companyID = ? and roleID = ? and eaID = ? and menuID = ?) order by interfaceID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlIBME);
		query.setString(0, companyID);
		query.setString(1, roleID);
		query.setString(2, eaID);
		query.setString(3, menuID);
		return query.list();
	}

	@Override
	public void saveCREMI(String companyID, String[] roleIDS, String eaID,
			String[] mis,String[] misno, CLogBook[] logBooks) {
		for (int i = 0; i < roleIDS.length; i++) {
				if(null != mis){
					for(String mi : mis){
						String []smi = mi.split("@");
						String menuID = smi[0];
						String interfaceID = smi[1];
						String interfaceUrl = smi[2];
						String interfaceStatus = smi[3];
						String hqlSREMI = " delete CREMI where companyID = ? and roleID = ? and eaID = ? and menuID=? and interfaceID=? ";
						Query query = sessionFactory.getCurrentSession().createQuery(hqlSREMI).setString(0, companyID).setString(1, roleIDS[i]).setString(2,eaID).setString(3, menuID).setString(4, interfaceID);
						query.executeUpdate();
						CREMI cremi = new CREMI();
						cremi.setRemiID(serverService.getServerID("cremi"));
						cremi.setRoleID(roleIDS[i]);
						cremi.setEaID(eaID);
						cremi.setMenuID(menuID);
						cremi.setInterfaceID(interfaceID);
						cremi.setInterfaceUrl(interfaceUrl);
						cremi.setInterfaceStatus(interfaceStatus);
						cremi.setCompanyID(companyID);
						sessionFactory.getCurrentSession().persist(cremi);
					}
				}
				if(null != misno){
					for(String mino : misno){
						String []smino = mino.split("@");
						String menuID = smino[0];
						String interfaceID = smino[1];
						String hqlSREMI = " delete CREMI where companyID = ? and roleID = ? and eaID = ? and menuID=? and interfaceID=? ";
						Query query = sessionFactory.getCurrentSession().createQuery(hqlSREMI).setString(0, companyID).setString(1, roleIDS[i]).setString(2,eaID).setString(3, menuID).setString(4, interfaceID);
						query.executeUpdate();
					}
				}
				sessionFactory.getCurrentSession().merge(logBooks[i]);
			}
		
	}
}
