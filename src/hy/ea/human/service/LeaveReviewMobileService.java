package hy.ea.human.service;


import com.mysl.bo.administrative.DtMyleave;
import hy.plat.bo.PageForm;

public interface LeaveReviewMobileService {
	String findLeaveApplyList(PageForm pageForm,String leaveApplyTime,String status) throws Exception;
	void updateLeaveApply(DtMyleave leaveApply) throws Exception;
	String findLeaveApplyByKey(String key);
}
