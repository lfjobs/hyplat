package mobile.tiantai.android.dao.impl;

import javax.annotation.Resource;

import hy.ea.bo.human.StaffContact;

import mobile.tiantai.android.dao.TelDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;

@Service @Transactional
public class TelDaoImpl implements TelDao  {
	
	@Resource
	private SessionFactory sessionFactory;
	
	//保存联系人
	@Override
	public void updateTel (StaffContact sc){
		sessionFactory.getCurrentSession().saveOrUpdate(sc);
	}
	
	//根据联系人手机号和staffID判断获得StaffContact对象
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public StaffContact getAccount(String account,String staffID){
		Query query = sessionFactory.getCurrentSession().createQuery("from StaffContact where contactWay = ? and staffID = ?");
		query.setString(0, account);
		query.setString(1, staffID);
		StaffContact sc = (StaffContact)query.uniqueResult();
		return sc;
	}

	//根据手机号获得TEshopCusCom对象
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public TEshopCusCom getCompanyID(String sccId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from TEshopCusCom where sccId = ?");
		query.setString(0, sccId);
		TEshopCusCom tscc = (TEshopCusCom)query.uniqueResult();
		return tscc;
	}
	
}
