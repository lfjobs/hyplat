package hy.ea.human.service;

import com.mysl.bo.administrative.DtMytravel;
import hy.plat.bo.PageForm;

import java.util.List;

public interface OutworkApplyMobileService {
	String findOutworkApplyList(PageForm pageForm,String outworkApplyTime) throws Exception;
	void addOutworkApply(DtMytravel outworkApply) throws Exception;
	void updateOutworkApply(DtMytravel outworkApply) throws Exception;
	void deleteOutworkApplyByKey(String key);
	String findOutworkApplyByKey(String key);
	double computeOutworkHours(String startTime,String endTime) throws Exception;
	String[] findSignInSetup();
	List findCheckOnReviewer();
}
