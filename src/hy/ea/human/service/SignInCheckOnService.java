package hy.ea.human.service;

import hy.ea.bo.human.salary.SalaryData;
import hy.ea.bo.human.vo.SignInCheckOn;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface SignInCheckOnService {
	String findCheckOnList(PageForm pageForm,String checkOnTimeType,String employee) throws Exception;
	List<SignInCheckOn> findCheckOnListByMonth(String year, String month,String startDate,String endDate,Map<String, SalaryData> salaryDataMap,String companyId) throws Exception;
	String findCheckOnSetupById(String id);
	String findCheckOnType();
	String findSalaryLevelByCompanyID();
	String findCurrentUser();
	String getDetailFromSession(String detailType, SignInCheckOn signInCheckOn);
	String findLeaveCheckOnType();
	String[] findSignInSetup(String companyId,String signInType);
	int selectDays(String startDate,String endDate);
	double computeNoonBreakDurationHours(String signInTime,String signOutTime) throws Exception;
	long computeNoonBreakDurationSeconds(String signInTime,String signOutTime) throws Exception;
	String computeAfternoonSignInTime() throws Exception;
	String selectNextDate(String startDate);
	double selectHours(String startTime,String endTime);
	double leaveHours(double hours,String date,String startTime,String endTime, String signInSetupStartTime,String signInSetupEndTime) throws Exception;
	int findMonthWorkDays(String year,String month) throws Exception;
	public boolean checkRole(String staffId,String companyId);
	public String[] timeTypeToTimeRange(String checkOnTimeType);
	String beforeDay(String date);
	Double findCheckOnDays(String companyId,String year,String month) throws Exception;
}
