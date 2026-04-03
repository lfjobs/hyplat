package hy.ea.dao.impl;

import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.dao.CompanyDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class CompanyDaoImpl implements CompanyDao{
	
	@Resource
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("rawtypes")
	public Company getCompanyByIdentifier(String identifier){
		Query query = sessionFactory.getCurrentSession().createQuery(" from Company where companyIdentifier = ? ");
		query.setString(0, identifier);
		List list = query.list(); 
		if(null != list && list.size() > 0){ 
			return (Company)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CDetail getCDetailByCompanyID(String companyID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from CDetail where companyID = ?");
		query.setString(0, companyID);
		List list = query.list(); 
		if(null != list && list.size() > 0){ 
			return (CDetail)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Company getCompanyByCompanyID(String companyID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from Company where companyID = ? ");
		query.setString(0, companyID);
		List list = query.list(); 
		if(null != list && list.size() > 0){ 
			return (Company)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanyListByPID(String companyID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from Company where companyPID = ? and companyStatus = '00' order by companyKey");
		query.setString(0, companyID);
		return query.list();
	}
}
