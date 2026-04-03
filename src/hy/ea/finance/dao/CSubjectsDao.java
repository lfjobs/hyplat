package hy.ea.finance.dao;

import hy.ea.bo.finance.CSubjects;

import java.util.List;



public interface CSubjectsDao {
	
	/**
	 * 将Subjects中的数据拷贝到CSubjects中去
	 * @param companyID
	 */
	void pushSubjectsToCSubjects(String companyID);
	
	/**
	 * 根据companyID和subjectsID删除CSybhects
	 * @param companyID
	 * @param subjectsID
	 */
	void deleteCSubjectsByID(String companyID,String subjectsID);
	/**
	 * 根据subjectsID查询出CSubjects
	 * @param subjectsID
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
}
