package hy.plat.dao.impl;

import hy.plat.bo.SCode;
import hy.plat.dao.SCodeDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class SCodeDaoImpl implements SCodeDao{
	
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void deleteCodeByID(String codeID) {
		Query  query=sessionFactory.getCurrentSession().createQuery(" delete  SCode where  codeID = ? ");
		query.setString(0, codeID).executeUpdate();
//		sessionFactory.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SCode> getCodeListByPID(String codePID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from SCode where  codePID = ? order by codeNumber");
		query.setString(0, codePID);
//		sessionFactory.close();
		return query.list(); 
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public SCode getCodeByID(String codeID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from SCode where  codeID = ?  ");
		query.setString(0, codeID);
		List list = query.list(); 
//		sessionFactory.close();
		if(null != list && list.size() > 0){
			return (SCode)list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SCode> getCodeList() {
		Query query = sessionFactory.getCurrentSession().createQuery(" from SCode ");
//		sessionFactory.close();
		return query.list(); 
	}

}
