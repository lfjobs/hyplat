package hy.ea.human.service;

import com.mysl.bo.administrative.DtMyovertime;
import hy.plat.bo.PageForm;

import java.util.List;

public interface OvertimeApplyMobileService {
	String findOvertimeApplyList(PageForm pageForm,String outworkApplyTime) throws Exception;
	void addOvertimeApply(DtMyovertime overtimeApply) throws Exception;
	void updateOvertimeApply(DtMyovertime overtimeApply) throws Exception;
	void deleteOvertimeApplyByKey(String key);
	String findOvertimeApplyByKey(String key);
	double computeOvertimeHours(String startTime,String endTime) throws Exception;
	String[] findSignInSetup();
	List findCheckOnReviewer();
	String checkOvertime(String startDate,String endDate) throws Exception;
	boolean isWeekendDay(String date);
	boolean isHoliday(String date);
	boolean isBigHoliday(String date);
	boolean isNoNeedSignIn(String date);
	int holidayDayCount(String yearMonth);
}
