package hy.plat.dao.impl;

import hy.plat.bo.SInterface;
import hy.plat.bo.SREMI;
import hy.plat.dao.SREMIDao;
import hy.plat.service.ServerService;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class SREMIDaoImpl implements SREMIDao{
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private ServerService serverService;

	@SuppressWarnings("unchecked")
	@Override
	public List<SREMI> getREMIListByRoleIDAndEaID(String roleID,String eaID) {
		String hqlSREMI = " from SREMI  where roleID = ? and eaID = ? order by roleID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSREMI);
		query.setString(0, roleID);
		query.setString(1, eaID);
		return query.list();
	}

	@Override
	public void saveSREMI(String roleID, String eaID, String[] mis) {
		String hqlSREMI = " delete SREMI where roleID = ? and eaID = ? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlSREMI).setString(0, roleID).setString(1,eaID);
		query.executeUpdate();
		
		if(null != mis){
			for(String mi : mis){
				String []smi = mi.split("@");
				String menuID = smi[0];
				String interfaceID = smi[1];
				String interfaceUrl = smi[2];
				String interfaceStatus = smi[3];
				SREMI sremi = new SREMI();
				sremi.setRemiID(serverService.getServerID("remi"));
				sremi.setRoleID(roleID);
				sremi.setEaID(eaID);
				sremi.setMenuID(menuID);
				sremi.setInterfaceID(interfaceID);
				sremi.setInterfaceUrl(interfaceUrl);
				sremi.setInterfaceStatus(interfaceStatus);
				sessionFactory.getCurrentSession().persist(sremi);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SInterface> getInterfaceListByEaIDAndMenuID(String eaID, String menuID) {
		String hqlIBME = "  from SInterface where boID in (select distinct boID from SEMB where eaID = ? and menuID = ?) order by interfaceID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlIBME);
		query.setString(0, eaID);
		query.setString(1, menuID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SREMI> getSREMIListByRoleID(String roleID) {
		String hqlREMI = "  from SREMI where roleID = ? order by roleID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlREMI);
		query.setString(0, roleID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SInterface> getInterfaceListByRoleIDAndEaIDAndMenuID(
			String roleID, String eaID, String menuID) {
		String hqlIBME = "  from SInterface where interfaceStatus = '02' and interfaceID in (select distinct interfaceID from SREMI where roleID = ? and eaID = ? and menuID = ?) order by interfaceID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlIBME);
		query.setString(0, roleID);
		query.setString(1, eaID);
		query.setString(2, menuID);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SREMI> getSREMIByEaID(String eaID) {
		String hqlREMI = "  from SREMI where eaID = ? order by roleID ";
		Query query = sessionFactory.getCurrentSession().createQuery(hqlREMI);
		query.setString(0, eaID);
		return query.list();
	}
}
