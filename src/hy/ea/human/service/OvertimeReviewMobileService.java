package hy.ea.human.service;


import com.mysl.bo.administrative.DtMyovertime;
import com.mysl.bo.administrative.DtMytravel;
import hy.plat.bo.PageForm;

public interface OvertimeReviewMobileService {
	String findOvertimeApplyList(PageForm pageForm,String overtimeApplyTime,String status) throws Exception;
	void updateOvertimeApply(DtMyovertime overtimeApply) throws Exception;
	String findOvertimeApplyByKey(String key);
}
