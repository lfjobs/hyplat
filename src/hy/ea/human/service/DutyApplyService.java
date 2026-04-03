package hy.ea.human.service;

import hy.ea.bo.human.DutyScheduling;
import hy.plat.bo.PageForm;

import java.util.List;

public interface DutyApplyService {
	String findDutyApplyList(PageForm pageForm,String dutyApplyTime) throws Exception;
	void addDutyApply(DutyScheduling dutyScheduling) throws Exception;
	void updateDutyApply(DutyScheduling dutyScheduling) throws Exception;
	void deleteDutyApplyById(String id);
	String findDutyApplyById(String id);
	List findDutyList(String startDate, String endDate, List<String> staffIdList, String companyId);
//	List findCheckOnReviewer();
}
