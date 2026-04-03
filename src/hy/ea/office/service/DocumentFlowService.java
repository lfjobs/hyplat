package hy.ea.office.service;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.Document;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface DocumentFlowService {

	
	////////////////////////////////////////流程////////////////////////////////////////////////
	
	/**
	 * 
	 * 发起公文提交审批
	 */
	public String createDocument(Document doc, CAccount account);
   
	/**
	 * 
	 * 再次发起提交审批
	 * @param doc
	 * @param account
	 * @return
	 */
	public String reSendDocument(Document doc, CAccount account);
    
	
	/**
	 * 
	 * 进行审批
	 * @param doc
	 * @param docAudit
	 * @param account
	 * @param jump
	 * @return
	 */
	public int examineDocument(Document doc,String jump);

	
	/**
	 * 
	 * 进行盖章操作
	 * @param doc
	 * @param account
	 * @param jump
	 * @return
	 */
	public int sealDocument(Document doc,String jump);

	
	/**
	 * 
	 * 公文群发
	 * @param doc
	 * @param account
	 * @param readers
	 * @return
	 */
	public String publishDocument(Document doc,String readers);
   
	
	/**
	 * 
	 * 公文再次群发
	 * @param doc
	 * @param account
	 * @param readers
	 * @return
	 */
	public String rePublishDocument(Document doc, CAccount account,
			String readers);

	
	
  /**
   * 
   * 完成阅读
   * @param docId
   * @param reader
   * @return
   */
	public String completeRead(String docId, String reader);

	
	
   /*
    * 为不能直接分页的分页；
    */
	public PageForm getPageForm(List<BaseBean> list, int pageNumber,
			int pageSize);
	
	
	
}
