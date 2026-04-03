package hy.ea.human.service;


import com.mysl.bo.administrative.DtMytravel;
import hy.plat.bo.PageForm;

public interface OutworkReviewMobileService {
	String findOutworkApplyList(PageForm pageForm,String outworkApplyTime,String status) throws Exception;
	void updateOutworkApply(DtMytravel outworkApply) throws Exception;
	String findOutworkApplyByKey(String key);
}
