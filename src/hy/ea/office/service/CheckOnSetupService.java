package hy.ea.office.service;

import hy.ea.office.bo.CheckOnSetup;
import hy.plat.bo.PageForm;

public interface CheckOnSetupService {
	String findCheckOnSetupList(PageForm pageForm);
	void addCheckOnSetup(CheckOnSetup checkOnSetup);
	void deleteCheckOnSetupById(String id);
	String findCheckOnSetupById(String id);
	void updateCheckOnSetup(CheckOnSetup checkOnSetup);
	String findCheckOnType();
	String findSalaryLevelByCompanyID();
	String findCurrentUser();
	String findRankSalary(String rankId);
}
