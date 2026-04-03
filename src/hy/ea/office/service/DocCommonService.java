package hy.ea.office.service;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentFileAttach;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface DocCommonService {
	
	/**
	 * 
	 * 获得编号 type:一个是编号，一个是号
	 * @param companyID
	 * @param type
	 * @return
	 */
	public String getDocNum(String companyID, String type);
	
	
	/*
	 * 
	 * List分页；
	 */
	
	public PageForm getPageForm(List<BaseBean> list ,int pageNumber,int pageSize);
	
	
	/**
	 * 
	 * 发送短信
	 * @param receiverlist
	 * @param sender
	 * @param content
	 * @param docTitle
	 * @return
	 */
	
	public String sendPhoneMessage(String receiverID, String receiverDeptID,String receiverCompanyID,
			String senderID, String companyID,String content, String title, String module,
			String type);
	
	/**
	 * 
	 * 补全Document
	 * @param pageForm
	 * @return
	 */
	public PageForm getFullPageForm(PageForm pageForm);
	
	
	
	public List<BaseBean> getFullListBean(List<BaseBean> list);
	
	/**
	 * 获取岗位
	 * @param pageForm
	 * @return
	 */
	public PageForm getPostName(PageForm pageForm,String type);
	
//	public String getPartyABName(String party);
	
	/**
	 * 
	 * 用于新建页面中保存公文数据包括office附件：第一次保存，更新保存，发送至审批前保存，传阅草稿保存，至信息平台前保存；
	 * @param document
	 * @return 1:保存数据失败；0：保存数据成功
	 */
	public Document storeDocData(Document document,DocumentFileAttach docFileAttach,CAccount account,Map<String, Object> session);
	
	
	/*
	 * 根据公文获得office附件
	 * 
	 */
	public List<BaseBean> getOfficeAttach(Document document);
	
	
	/**
	 *  补全修改document
	 * 
	 */
	public Document getViewFullDoc(Document document);
	
	/*
	 * 添加公文操作记录
	 *  receiveID:接收人
	 */
	public String addTrackRecord(String docId, String operatorID,
			String deptIDofOperator,String compnayIDofOperator,String content);
	
	
	/**
	 * 
	 * 打包Office
	 * @param docIds
	 * @return
	 */
	public String packZipOfOffice(String realpath,String  docIds);

	/**
	 *
	 * 根据公司名称查询正常状态公司
	 * @param companyName
	 * @return
	 */
	public List<BaseBean> getAllCompany(String companyName);


	/**
	 *
	 * 根据人员姓名或者电话查询人以及部门
	 * @param name
	 * @return
	 */
	public Map<String,Object> getAllPeople(String name,String companyID);

    /**
	 *
	 * APP端保存文档
	 * @param document
	 * @param account
	 * @param session
	 * @return
     */
	public Document storeDocApp(Document document, CAccount account,
								Map<String, Object> session);

	/**
	 *
	 * APP端保存附件
	 * @param document
	 * @param docFileAttach
	 * @param staffID
     */
	public void storeAttachDocApp(Document document,
								  DocumentFileAttach docFileAttach, String staffID,String realPath);

	/**
	 *
	 * 生成的档案条码
	 * @return
     */
	public String genArchiveBarcode();

	/**
	 *
	 * 补充信息
	 * @param document
	 * @return
     */
	public Document bcInfo(Document document);


	/**
	 *
	 *
	 *
	 *
	 * @param realpath
	 * @param companyID
	 * @param staff
	 * @return
	 */
	public String createDoc(String realpath, String companyID, String staffID, String contractTypes, Staff staff,String contractSource);


}
