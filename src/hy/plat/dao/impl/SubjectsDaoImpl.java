package hy.plat.dao.impl;

import hy.plat.bo.Subjects;
import hy.plat.dao.SubjectsDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubjectsDaoImpl implements SubjectsDao {
	@Resource
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Subjects> getList() {
		String hql = " from Subjects ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

}
