package hy.ea.finance.service.impl;


import hy.ea.bo.finance.CSubjects;
import hy.ea.finance.dao.CSubjectsDao;
import hy.ea.finance.service.CSubjectsService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
@Service
public class CSubjectsServiceImpl implements CSubjectsService{
     
	@Resource
	private CSubjectsDao subjectsDao;
	@Override
	public CSubjects getCSubjectsByID(String companyID,String subjectsID) {
		
		return subjectsDao.getCSubjectsByID(companyID, subjectsID);
	}


	@Override
	public List<CSubjects> getCSubjectsListByPID(String companyID,String subjectsPID) {
		return subjectsDao.getCSubjectsListByPID(companyID, subjectsPID);
	}

	@Override
	public String getCSubjectsByPIDString(String companyID, String subjectsID,String subjectsStr) {
		List<CSubjects> subjectsList = subjectsDao.getCSubjectsListByPID(companyID, subjectsID);
		String SubjectsIDStr=subjectsStr;
		if (subjectsList.size()>0) {
			for (CSubjects subjects: subjectsList) {
				SubjectsIDStr+="_";
				SubjectsIDStr+=subjects.getSubjectsID();
				getCSubjectsByPIDString(companyID,subjects.getSubjectsID(),SubjectsIDStr);
			}
		}
		
		return SubjectsIDStr;
	}
}