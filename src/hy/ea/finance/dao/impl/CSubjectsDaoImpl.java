package hy.ea.finance.dao.impl;
import hy.ea.bo.finance.CSubjects;
import hy.ea.finance.dao.CSubjectsDao;
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
public class CSubjectsDaoImpl implements CSubjectsDao {

	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private SubjectsDao subjectsdao;

	@Override
	public void deleteCSubjectsByID(String companyID, String subjectsID) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				" update  CSubjects set subjectsStatus = '02' where companyID = ? and subjectsID = ? ");
		query.setString(0, companyID).setString(1, subjectsID).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CSubjects getCSubjectsByID(String companyID,String subjectsID) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				" from CSubjects where companyID = ? and subjectsID = ? ");
		query.setString(0, companyID);
		query.setString(1, subjectsID);
		List list = query.list();
		if (null != list && list.size() > 0) {
			return (CSubjects) list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CSubjects> getCSubjectsListByPID(String companyID,String subjectsPID) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"	from CSubjects where companyID = ? and subjectsPID = ? and subjectsStatus !='02' order by subjectsNumbers ");
		query.setString(0, companyID);
		query.setString(1, subjectsPID);
		List<CSubjects> list = query.list();
		return list;
	}

	@Override
	public void pushSubjectsToCSubjects(String companyID) {
		List<Subjects> subjectList = subjectsdao.getList();
		for (Subjects subjects : subjectList) {
			CSubjects csubject = new CSubjects();
			csubject.setCompanyID(companyID);
			csubject.setSubjectsID(subjects.getSubjectsID());
			csubject.setSubjectsName(subjects.getSubjectsName());
			csubject.setSubjectsPID(subjects.getSubjectsPID());
			csubject.setSubjectsNumbers(subjects.getSubjectsNumbers());
			csubject.setSubjectsStatus(subjects.getSubjectsStatus());
			sessionFactory.getCurrentSession().persist(csubject);
		}
	}

}
