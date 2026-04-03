package hy.ea.human.service;


import com.mysl.bo.administrative.DtMyleave;
import hy.plat.bo.PageForm;

import java.util.List;

public interface LeaveApplyMobileService {
	String findLeaveApplyList(PageForm pageForm,String leaveApplyTime) throws Exception;
	void addLeaveApply(DtMyleave leaveApply) throws Exception;
	void updateLeaveApply(DtMyleave leaveApply) throws Exception;
	void deleteLeaveApplyByKey(String key);
	String findLeaveApplyByKey(String key);
	double computeLeaveHours(String startTime,String endTime) throws Exception;
	String[] findSignInSetup();
	List findCheckOnReviewer();
}
