package hy.plat.dao.impl;

import hy.plat.bo.SDistrict;
import hy.plat.dao.SDistrictnDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SDistrictDaoImpl implements SDistrictnDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void deleteDistrictByID(String districtID) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				" delete  SDistrict where districtID = ? ");
		query.setString(0, districtID).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SDistrict> getDistrictListByPID(String districtPID) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				" from SDistrict where districtPID = ? order by districtName ");
		query.setString(0, districtPID);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public SDistrict getDistrictByID(String districtID) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				" from SDistrict where districtID = ?");
		query.setString(0, districtID);
		List list = query.list();
		if (null != list && list.size() > 0) {
			return (SDistrict) list.get(0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public SDistrict getSDistrictByPIDAndName(String districtPID,
			String districtName) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				" from SDistrict where districtPID = ? and districtName = ? ");
		query.setString(0, districtPID);
		query.setString(1, districtName);
		List list = query.list();
		if (null != list && list.size() > 0) {
			return (SDistrict) list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SDistrict> getDistrictListNOselfByPID(String districtPID,
			String districtID) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				" from SDistrict where  districtID <> ? and districtPID = ? order by districtName ");
		query.setString(0, districtID);
		query.setString(1, districtPID);
		return query.list();
	}
}