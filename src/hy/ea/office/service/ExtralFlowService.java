package hy.ea.office.service;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.DocComplaint;
import hy.ea.bo.office.DocPosition;
import hy.ea.bo.office.Document;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface ExtralFlowService {
	public String createDocument(Document doc, CAccount account);

	public String examineDocument(Document doc,DocComplaint dc,
			CAccount account, String jump);

	public String sealDocument(Document doc,DocComplaint dc, CAccount account);
   /*
    * 
    *  全局查询公文待办公文列表
    */
	public List<BaseBean> findUnFinishedDocument(String staffID, String type,DocPosition docPosition);
 
	public List<BaseBean> findFinishedDocument(String staffID, String type,String module);
	
	/**
	 * 
	 * 投诉，获取待办公文
	 * @param staffID
	 * @param type
	 * @param module
	 * @return
	 */
	public List<BaseBean> findUnFinishedDocument(String staffID, String type,String module);
	
	public PageForm getPageForm(List<BaseBean> list ,int pageNumber,int pageSize);
	
	public String transferData(String dealerID,String oldDealerID,String companyID);
	
	public String sendMessage(String senderID,String telNumber, String typemessage); 
    
}
