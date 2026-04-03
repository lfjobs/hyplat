package hy.ea.finance.service;


import hy.ea.bo.finance.CSubjects;

import java.util.List;


public interface CSubjectsService {
	/**
	 * 根据subjectsID查询出CSubjects
	 * @param subjectsIDO
	 * @return
	 */
	CSubjects getCSubjectsByID(String companyID,String subjectsID);
	
	/**
	 * 根据subjectsID和subjectsPID查询出其子节点
	 * @param companyID
	 * @param subjectsPID
	 * @return
	 */
	List<CSubjects> getCSubjectsListByPID(String companyID,String subjectsPID);
	/**
	 * 根据subjectsID获取所有子类subjectsID
	 * @param companyID
	 * @param subjectsID
	 * @return
	 */
	String getCSubjectsByPIDString(String companyID,String subjectsID,String subjectsStr);
}
