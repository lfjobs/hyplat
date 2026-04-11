package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.office.*;
import hy.ea.bo.office.Document.DocumentSearchInfo;
import hy.ea.bo.office.vo.*;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocAndroidService;
import hy.ea.office.service.DocCommonService;
import hy.ea.office.service.DocumentFlowService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.service.SealOffice;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.*;

@Controller
@Scope("prototype")
public class AndroidDocFlowAction {
	private static final Logger logger = LoggerFactory.getLogger(AndroidDocFlowAction.class);
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private DocAndroidService docAndroidService;
	@Resource
	private DocumentFlowService docFlowService;// 工作流相关的业务逻辑
	@Resource
	private DocCommonService docCommonService;
	@Resource
	private ServerService serverService;

	@Resource
	private ContractService contractService;
	private PageForm pageForm;
	private String   readers;


	private TemplateType templateType;

    private ArchiveDocType archiveDocType;
	private List<BaseBean> childlist;
	private String childrenID;
	private String source;//记录是哪发来的

	private  DocRelateOther docRelateOther;

	/**
	 * 每页显示条数
	 */
	private int pageSize;
	/**
	 * 页数
	 */
	private int pageNumber;
	/**
	 * 总数
	 */
	private int recordCount;
	private Object result;
	private String docId;
	private List<Document> doclist;
	private String search;
	private String comment;
	private DocumentSearchInfo documentSearchInfo;
	private Document document;
	private String jump;
	private String checkOrgID;
	private String checkComID;
	private String staffID;
	private String module;
	private DocumentFileAttach docFileAttach;
	private File image;
	private String imageFileName;
	private String imageContentType;;
	private String finishType;
	private String imagePath;
	private String htmlPath;
	private DocumentShare docShare;

	private  DocumentTemplate  documentTemplate;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = ServletActionContext.getRequest().getSession();



	/**
	 * 修改时获取公文信息
	 *
	 */
	public String getUpdateDocument() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String poe = request.getParameter("poe");
		 document = (Document) baseBeanService.getBeanByHqlAndParams("from Document where docId=?", new Object[] { docId });
        if(document.getSpecificTemplate()!=null&&!document.getSpecificTemplate().equals("")) {
			DocumentTemplate documentTemplate = (DocumentTemplate) baseBeanService.getBeanByHqlAndParams("from DocumentTemplate where templateId=?", new Object[]{document.getSpecificTemplate()});

			request.setAttribute("documentTemplate",documentTemplate);
		}


		// 处理附件(因为之前的数据有可能是多个附件，以免出现错误)
		List<BaseBean> attachlist = docCommonService.getOfficeAttach(document);
		if (attachlist.size() != 0) {
			docFileAttach = (DocumentFileAttach) attachlist.get(0);
		}

		if("print".equals(poe)) {
			document = docCommonService.bcInfo(document);
			return "printDa";
		}else if("view".equals(poe)){
			document = docCommonService.bcInfo(document);
			return "viewDoc";

		}
		return "toupload";
	}

	/**
	 *
	 * 放入回收站
	 *
	 * @return
	 */
	public String putRecycleBin() {
		HttpServletRequest request = ServletActionContext.getRequest();

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String stage = request.getParameter("stage");

		//说明选择了公司保存
		checkOrgID = contractService.getCheckOrg(account.getStaffID(), account.getCompanyID());
		String hql = "from Document where docId = ?";
		Document doc = null;



			doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] {document.getDocId()});
			String coyhql = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					coyhql, new Object[] { doc.getCompanyID() });
			DocDelRecord ddr = new DocDelRecord();
			ddr.setDelId(serverService.getServerID("delId"));
			ddr.setDocId(document.getDocId());
			ddr.setStage(stage);
			ddr.setDelstate("del");
			ddr.setOperator(account.getStaffID());
			ddr.setOperateTime(new Date());
			ddr.setDeptOperate(checkOrgID);
			if (company != null) {
				ddr.setOrgmark(company.getCompanyIdentifier());
			}

			baseBeanService.save(ddr);

			if (stage.equals("read")) {
				hql = "from DocGsendInfo where docId = ? and readerID = ?";
				DocGsendInfo dgi = (DocGsendInfo) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { document.getDocId(),
								account.getStaffID()});
				dgi.setDelstate("del");
				baseBeanService.update(dgi);
			}
			// 增加操作记录
			docCommonService.addTrackRecord(document.getDocId(), account.getStaffID(),
					checkOrgID, account
							.getCompanyID(), "放入回收站");


		return "success";
	}


	/**
	 * 读者完成归档任务
	 *
	 * @param
	 *
	 * @return
	 */
	public String completeArchive() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}

		//说明选择了公司保存
		checkOrgID = contractService.getCheckOrg(account.getStaffID(), account.getCompanyID());
		String hqlcom = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom,new Object[]{account.getCompanyID()});
		String hql = "from Document where docId = ?";
		Document doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });
		doc.setStatus("G");
		doc.setGroupCompanySn(company.getGroupCompanySn());
		doc.setGuidangTime(new Date());
		baseBeanService.update(doc);

		String content = "进行归档";


		docCommonService.addTrackRecord(docId, account.getStaffID(),
				checkOrgID, account.getCompanyID(),
				content);

		return "success";
	}



	/**
	 * 读者完成归档任务
	 *
	 * @param
	 *
	 * @return
	 */
	public String archiveDoc() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();


		String adtId = request.getParameter("adtId");
		String r = contractService.archiveDoc(docId,account.getStaffID(),account.getCompanyID(),adtId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", r);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();


		return "success";
	}


	/**
	 * 
	 *
	 * 获得草稿列表
	 * (某块，部门),module,checkOrgID
	 * @param
	 * @return
	 */
	public String getDocDraftList() {
		JSONObject jret = new JSONObject();
		//http://www.qcjx.org/ttcms/servlet/StudyServlet4App.svl?sid=40cd1390-6c40-4e5f-8a6e-8bf6332f147e&type=zjlx&ctsx=sx&qnum=
		//http:192.168.1.124:8080/hyplat/ea/androiddoc/sajax_ea_getDocDraftList.jspa?drafterID&module&checkOrgID
	//	String sql = "select from dt_OA_Document d where d.drafterID=? and d.status = ? and  d.subscriberID2 is null and d.module = ? and d.organizationID = ? and d.docId not in(select docId from DT_OA_DOC_DELRECORD) order by draftTime desc";
		String hql = " from Document d where d.drafterID=? and d.status = ? and  d.subscriberID2 is null and d.module = ? and d.organizationID = ? and d.docId not in(select docId from DocDelRecord) order by draftTime desc";
		
		module = module.substring(0,1).toLowerCase()+module.substring(1);

		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				"select new Document (d.docId,d.title) " +hql,"select count(d.docId) "+hql,new Object[] { staffID, "I", module, checkOrgID });

		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {

			
			List<Object> list=new ArrayList<Object>();
		     for(BaseBean b:pageForm.getList()){
		    	 Document doc=(Document) b;
			    JSONObject jo = new JSONObject();
			    jo.accumulate("docId", doc.getDocId());
			    jo.accumulate("title", doc.getTitle()); 
			    list.add(jo);
		     }
		     jret.accumulate("list", list);
		     jret.accumulate("pageNumber", pageForm.getPageNumber());
		     jret.accumulate("pageSize", pageForm.getPageSize());
		     jret.accumulate("recordCount", pageForm.getRecordCount());
		     jret.accumulate("result","data");

		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;
		return "success";

	}

	/**
	 * 
	 * 获取收件箱列表包括他人传阅以及驳回列表 staffID,checkOrgID,module
	 * 
	 * @param
	 * 
	 */
	public String getReceiveBoxList() {
		JSONObject jret = new JSONObject();
		module = module.substring(0,1).toLowerCase()+module.substring(1);
	    String hql = "from Document d where ((d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?)or (d.status = ? and d.drafterID = ? and organizationID = ?))and d.module = ?  and d.docId not in(select docId from DocDelRecord) order by passTime desc";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber),
					"select new Document (d.docId,d.title) " +hql,"select count(d.docId) "+hql, new Object[] { staffID,
							checkOrgID, "R",
							staffID,
							checkOrgID,
							module});
			
	
			if (pageForm != null && pageForm.getList() != null
					&& pageForm.getList().size() > 0) {


				List<Object> list=new ArrayList<Object>();
			     for(BaseBean b:pageForm.getList()){
			    	 Document doc=(Document) b;
				    JSONObject jo = new JSONObject();
				    jo.accumulate("docId", doc.getDocId());
				    jo.accumulate("title", doc.getTitle()); 
				    list.add(jo);
			     }
			     jret.accumulate("list", list);
			     jret.accumulate("pageNumber", pageForm.getPageNumber());
			     jret.accumulate("pageSize", pageForm.getPageSize());
			     jret.accumulate("recordCount", pageForm.getRecordCount());
			     jret.accumulate("result","data");

			} else {
				jret.accumulate("result", "nodata");
			}
			result =  jret;
           
			return "success";

	}
	
	
	
	
	
	/**
	 * 
	 * 盖章管理：未盖章已盖章 finishType:未盖章seal;已盖章sealyes; staffID,module,checkOrgID
	 * @param
	 * @return  
	 * 
	 * getSealDocList.jspa?finishType=seal
	 */
	
	public String getSealDocList() {

		module = module.substring(0,1).toLowerCase()+module.substring(1);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListSeal(finishType));
		
		JSONObject jret = new JSONObject();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			List<Object> list=new ArrayList<Object>();
		     for(BaseBean b:pageForm.getList()){
		    	 JSONObject jo = new JSONObject();
		    	 if (finishType.equals("seal")) {
		    		 ViewUnsealdoc doc=(ViewUnsealdoc) b;
				    jo.accumulate("docId", doc.getDocid());
				    jo.accumulate("title", doc.getTitle()); 
				   list.add(jo);
		    	 }else{
		    		 ViewSealdoc doc=(ViewSealdoc) b;
					  jo.accumulate("docId", doc.getDocid());
					  jo.accumulate("title", doc.getTitle()); 
					 list.add(jo); 
		    	 }
		     }
		     jret.accumulate("list", list);
		     jret.accumulate("pageNumber", pageForm.getPageNumber());
		     jret.accumulate("pageSize", pageForm.getPageSize());
		     jret.accumulate("recordCount", pageForm.getRecordCount());
		     jret.accumulate("result","data");
		}else{
			 jret.accumulate("result", "nodata");
		}
		result =  jret;
		return "success";
	}
	
	
	


	private DetachedCriteria getListSeal(String finishType) {
		DetachedCriteria dc = null;
		if (finishType.equals("seal")) {
			dc = DetachedCriteria.forClass(ViewUnsealdoc.class);
			dc.add(Restrictions.eq("activityname", "Seal"));
			dc.addOrder(Order.desc("subscribetime"));
		} else {
			dc = DetachedCriteria.forClass(ViewSealdoc.class);
			dc.addOrder(Order.desc("sealtime"));
		}
		dc.add(Restrictions.eq("assignee",staffID ));
		dc.add(Restrictions.eq("module", module));
		dc.add(Restrictions.eq("deptidofsealer", checkOrgID));


		return dc;
	}
	
	
	/**
	 * 
	 * 发文管理 未发，已发 finishType:未发文publish;已发文publishyes; staffID,module,checkOrgID
	 * @param
	 * @return
	 */
	public String getPublishDocList() {
		module = module.substring(0,1).toLowerCase()+module.substring(1);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListPublish(finishType));
		
		JSONObject jret = new JSONObject();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			List<Object> list=new ArrayList<Object>();
		     for(BaseBean b:pageForm.getList()){
		       	 JSONObject jo = new JSONObject();
		    	 if (finishType.equals("publish")) {
		    		 ViewUnpublishdoc doc=(ViewUnpublishdoc) b;
				    jo.accumulate("docId", doc.getDocid());
				    jo.accumulate("title", doc.getTitle()); 
				    list.add(jo);
		    	 }else{
		    		 ViewPublishdoc doc=(ViewPublishdoc) b;
					  jo.accumulate("docId", doc.getDocid());
					  jo.accumulate("title", doc.getTitle()); 
					  list.add(jo); 
		    	 }
		     }
		     jret.accumulate("list", list);
		     jret.accumulate("pageNumber", pageForm.getPageNumber());
		     jret.accumulate("pageSize", pageForm.getPageSize());
		     jret.accumulate("recordCount", pageForm.getRecordCount());
		     jret.accumulate("result","data");
		}else{
			 jret.accumulate("result", "nodata");
		}
		result =  jret;
		return "success";
	}
	
	
	
	
	
	private DetachedCriteria getListPublish(String finishType) {
		DetachedCriteria dc = null;

		if (finishType.equals("publish")) {
			dc = DetachedCriteria.forClass(ViewUnpublishdoc.class);

			dc.add(Restrictions.eq("activityname", "Publish"));
			dc.addOrder(Order.desc("sealtime"));
		} else {
			dc = DetachedCriteria.forClass(ViewPublishdoc.class);
			dc.addOrder(Order.desc("publishtime"));
		}
		dc.add(Restrictions.eq("assignee", staffID));
		dc.add(Restrictions.eq("module", module));
		dc.add(Restrictions.eq("deptidofpublisher",checkOrgID));
	
		

		return dc;
	}
	
	
	

	/**
	 * 
	 * 
	 * 阅读管理：未阅读，已阅读 finishType:未盖章read;已盖章readyes; staffID,module,checkOrgID
	 *
	 * @param
	 * @return
	 */
	public String getReadDocList() {

		module = module.substring(0,1).toLowerCase()+module.substring(1);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber),
				getListRead(finishType));

		JSONObject jret = new JSONObject();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			List<Object> list=new ArrayList<Object>();
		     for(BaseBean b:pageForm.getList()){
		    	 JSONObject jo = new JSONObject();

		    	     ViewUnreaddoc doc=(ViewUnreaddoc) b;
				    jo.accumulate("docId", doc.getDocid());
				    jo.accumulate("title", doc.getTitle());
				    list.add(jo);

		     }
		     jret.accumulate("list", list);
		     jret.accumulate("pageNumber", pageForm.getPageNumber());
		     jret.accumulate("pageSize", pageForm.getPageSize());
		     jret.accumulate("recordCount", pageForm.getRecordCount());
		     jret.accumulate("result","data");
		}else{
			 jret.accumulate("result", "nodata");
		}
		result =  jret;
		return "success";
	}
	
	
	
	private DetachedCriteria getListRead(String finishType) {
		DetachedCriteria dc = DetachedCriteria.forClass(ViewUnreaddoc.class);
		dc.add(Restrictions.eq("readerid", staffID));
		dc.add(Restrictions.eq("module", module));
		dc.add(Restrictions.eq("readorganizationid", checkOrgID));
		if (finishType.equals("read")) {
			dc.add(Restrictions.eq("readstate", "1"));
			dc.addOrder(Order.desc("recivetime"));
		} else {
			dc.add(Restrictions.eq("readstate", "0"));
			dc.addOrder(Order.desc("readtime"));
			dc.add(Restrictions.isNull("delstate"));
		}

		

		return dc;
	}

	
	
	/**
	 * 
	 * 归档管理 module
	 * @param
	 */
	public String getArchivedList() {

		module = module.substring(0,1).toLowerCase()+module.substring(1);
		String hqlcom = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				hqlcom, new Object[] {checkComID});
		String hql = "from Document d where d.groupCompanySn=? and d.status= ? and d.module = ? and d.docId not in(select docId from DocDelRecord) order by d.guidangTime desc";

		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql,
				new Object[] { company.getGroupCompanySn(), "G",
						module });
		JSONObject jret = new JSONObject();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			List<Object> list=new ArrayList<Object>();
			
		     for(BaseBean b:pageForm.getList()){
		    	 Document doc=(Document) b;
			    JSONObject jo = new JSONObject();
			    jo.accumulate("docId", doc.getDocId());
			    jo.accumulate("title", doc.getTitle()); 
			    list.add(jo);
			  
			    
		     }
		    
		     jret.accumulate("list", list);
		     jret.accumulate("pageNumber", pageForm.getPageNumber());
		     jret.accumulate("pageSize", pageForm.getPageSize());
		     jret.accumulate("recordCount", pageForm.getRecordCount());
		     jret.accumulate("result","data");
		}else{
			 jret.accumulate("result", "nodata");
		}
		result =  jret;
		return "success";

	}


	
	
	/**
	 * 盖章管理里的操作 分为至分发人,驳回，转交他人盖章
	 * 
	 * @param :jump publish(至分发人)，noSeal（驳回盖章）；seal（转交盖章）
	 *          staffID,checkOrgID,checkComID,module,document.
	 *            
	 * @return
	 */
	public String sealDocument() {
		Document doc = (Document) baseBeanService
				.getBeanByHqlAndParams("from Document where docId = ?", new Object[] { document
						.getDocId() });

		HttpServletRequest request = ServletActionContext.getRequest();


		CAccount account  = (CAccount) request.getSession().getAttribute("account");
		if(jump.equals("seal")){

			//判断当前人盖没盖章，如果当前人还没盖章就转交 就要重新发起签约

			HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();

			TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);
			String tel = "";
			if(tc!=null) {
				tel = tc.getAccount();
			}
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			//contractService.repeatApplySign(document.getDocId(),doc.getApplyNo(),doc.getSealerID(),basePath,document.getReceiverID(),document.getReceiverDeptID(),document.getReceiverCompanyID(),tel);
		}

		String hql = "";
		JSONObject jsonObjList = new JSONObject();
		try {
			int result = docFlowService.sealDocument(document, jump);
			String content = "";
			if (result == 1) {
				content = "暂存档";
			}
			if (result == 2) {
				content = "转至分发人";
				hql = "";

				String messagecontent = "[未发公文]下,请进行分发";
				docCommonService.sendPhoneMessage(document.getReceiverID(), document
						.getReceiverDeptID(),document.getReceiverCompanyID(),doc.getSealerID(),account.getCompanyID(),
						messagecontent, doc.getTitle(), doc.getModule(), "publish");

			}
			if (result == 3) {


				//转交盖章

				content = "转交盖章";

				String messagecontent = "[未盖章]下,请进行盖章";
				docCommonService.sendPhoneMessage(document.getReceiverID(), document
						.getReceiverDeptID(),document.getReceiverCompanyID(), account.getStaffID(),account.getCompanyID(),
						messagecontent, doc.getTitle(), module, "seal");


			}
			if (result == 4) {
				content = "驳回至审批人";
			}



			if(result==2||result ==3){
				contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),document.getReceiverID(),document.getReceiverDeptID(),document.getReceiverCompanyID(),source);

			}
			docCommonService.addTrackRecord(document.getDocId(), account.getStaffID(), checkOrgID,
					checkComID, content);
			jsonObjList.accumulate("result", 0);

		} catch (Exception e) {
			jsonObjList.accumulate("result", 1);
			logger.error("操作异常", e);
		}
		result = jsonObjList;
		return "success";
	}



	/**
	 *
	 * 设置分享
	 *
	 * @return
	 */

	public String setShare() {

		HttpSession session = ServletActionContext.getRequest().getSession();
		String module = (String)session.getAttribute("module");

		CAccount account = new CAccount();
		String orgID = "";
		if(module==null||module.equals("")){


			//没有选择公司个人保存
			SessionWrap sw = SessionWrap.getInstance();

			TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);

			List<Object>  lists = contractService.getStaffList(tc.getSccId());
			List<Object> params = new ArrayList<Object>();

			String hqsl = "from DocGsendInfo where docId = ? and readerID in(";

			params.add(docShare.getDocId());
			for(int i=0;i<lists.size();i++){
				if(i!=lists.size()-1){
					hqsl+=("?,");
				}else{
					hqsl+=("?)");
				}
				params.add(lists.get(i).toString());

			}

			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hqsl,params.toArray());
			if(list.size()>0) {
				DocGsendInfo dc = (DocGsendInfo) list.get(0);
				account.setStaffID(dc.getReaderID());
				account.setCompanyID(dc.getCompanyID());
				orgID = dc.getOrganizationID();
			}

		}else {
			//5l5C

			account = (CAccount) session.getAttribute("account");

			orgID = contractService.getCheckOrg(account.getStaffID(), account.getCompanyID());
		}




			String hql = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { account.getCompanyID() });
			docShare.setShareId(serverService.getServerID("share"));
			docShare.setCompanyId(account.getCompanyID());
			docShare.setGroupCompanySn(company.getGroupCompanySn());
			docShare.setShareTime(new Date());
			baseBeanService.save(docShare);

			// 记录跟踪日志
			String content = "";
			if (docShare.equals("current")) {
				content = "在公司范围内共享";
			} else {
				content = "在集团范围内共享";
			}

			docCommonService.addTrackRecord(docShare.getDocId(), account
					.getStaffID(), orgID, account
					.getCompanyID(), content);



		return "success";
	}


	/**
	 * 读者完成阅读任务当前Document的key
	 *
	 * @param
	 *
	 * @return
	 */
	public String completeRead() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String module = (String)session.getAttribute("module");

		CAccount account = new CAccount();
		String orgID = "";
		if(module==null||module.equals("")){


			//没有选择公司个人保存
			SessionWrap sw = SessionWrap.getInstance();

			TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);

			List<Object>  lists = contractService.getStaffList(tc.getSccId());
			List<Object> params = new ArrayList<Object>();

			String hqsl = "from DocGsendInfo where docId = ? and readerID in(";
			params.add(document.getDocId());
			for(int i=0;i<lists.size();i++){
				if(i!=lists.size()-1){
					hqsl+=("?,");
				}else{
					hqsl+=("?)");
				}
				params.add(lists.get(i).toString());

			}

			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hqsl,params.toArray());
			if(list.size()>0) {
				DocGsendInfo dc = (DocGsendInfo) list.get(0);
				account.setStaffID(dc.getReaderID());
				account.setCompanyID(dc.getCompanyID());
				orgID = dc.getOrganizationID();
			}

		}else {
			//5l5C

			account = (CAccount) session.getAttribute("account");

			orgID = contractService.getCheckOrg(account.getStaffID(), account.getCompanyID());
		}

		String result = null;
		String content = null;
		if (document.getDocId() != null && !document.getDocId().equals("")) {
			result = docFlowService.completeRead(document.getDocId(), account.getStaffID());
			content = "点击阅读";

		}

		if (result.equals("1")) {
			docCommonService.addTrackRecord(document.getDocId(), account.getStaffID(),
					orgID, account
							.getCompanyID(), content);
		}

		return "success";
	}
	
	/**
	 * 
	 * 上传文件
	 * @return
	 */
	   public String upload(){
		   
		   String photoPath = "";
		   if (image != null) {
				String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
				photoPath = fileService.savePhoto(path,imageFileName,image,checkComID,"/android/document/"
						+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
				
			}
		   JSONObject jsonObjList = new JSONObject();
	       jsonObjList.accumulate("result", photoPath);
	        
		   result = jsonObjList;
		   return "success";
	   }
	
	
	/**
	 * 保存草稿：全部存储；更新；document.title,document.theme,document.
	 * @param
	 * 
	 */
	public String storeDocument() {
		CAccount account = new CAccount();
		account.setCompanyID(checkComID);
		account.setStaffID(staffID);
		Map<String,Object> session = new HashMap<String, Object>();
		session.put("organizationID", checkOrgID);
		session.put("module",module);
		
		JSONObject jsonObjList = new JSONObject();
	
		try {
		docCommonService.storeDocData(document, docFileAttach, account,
					session);
		jsonObjList.accumulate("result", "suc");
		} catch (Exception e) {
			jsonObjList.accumulate("result", "fail");
			logger.error("操作异常", e);
		}
		
	        
		   result = jsonObjList;

		return "success";
	}

    /**
	 *
	 * 手机网页版保存文档
	 * @return
	 */
	public String storeDocAPP() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		String module = (String)session.getAttribute("module");

		CAccount account  = (CAccount) session.getAttribute("account");
		if(account!=null){
			staffID = account.getStaffID();
			checkComID = account.getCompanyID();

		}
		if(checkComID!=null&&!checkComID.equals("")) {

			checkOrgID = contractService.getCheckOrg(staffID, checkComID);
		}

		if (image != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String  photoPath = fileService.savePhoto(path,imageFileName,image,(checkComID==null||checkComID.equals(""))?staffID:checkComID,"/web/document/"
					+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			docFileAttach.setFilePath(photoPath);
		}



		Map<String,Object> map = new HashMap<String, Object>();
		map.put("organizationID", checkOrgID);
		map.put("module",(module==null||module.equals(""))?"doc":module);

		//JSONObject jsonObjList = new JSONObject();

		try {
			Document doc = docCommonService.storeDocApp(document, account,
					map);
			String realpath = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			docCommonService.storeAttachDocApp(doc,docFileAttach,staffID,realpath);
			String docId =  doc.getDocId();
			request.setAttribute("message","11");
			FinancialBill financialBill = new FinancialBill();//纯粹为了传值而不用修改success.jsp
			financialBill.setFinancialbillID(docId);
			request.setAttribute("financialBill",financialBill);
		} catch (Exception e) {

			logger.error("操作异常", e);
		}




		return "success";
	}



	
	
	
	/**
	 * 发起公文流转流程	document.getReceiverID,document.getReceiverDeptID,document.getReceiverCompanyID
	 * @param
	 * 
	 * 
	 *
	 */
	public String createDocument() {

		String hql = "from Document where docId=?";
		Document doc = null;
		JSONObject jsonObjList = new JSONObject();
		try {

	
				// 列表发送
				if (docId != null && !docId.equals("")) {

					doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { docId });
					doc.setSubscriberID(document.getReceiverID());
					doc.setDeptIDofSubscriber(document.getReceiverDeptID());
					if(document.getReceiverCompanyID()!=null&&document.getReceiverCompanyID().length()>11) {
						doc.setCompanyIDofSubscriber(document.getReceiverCompanyID());
						doc.setReceiverCompanyID(document.getReceiverCompanyID());
					}
					doc.setReceiverID(document.getReceiverID());
					doc.setReceiverDeptID(document.getReceiverDeptID());

				}
			HttpSession session = ServletActionContext.getRequest().getSession();


		CAccount account = (CAccount) session.getAttribute("account");
//			if(doc.getSubscriberID2()==null){
//
//				account.setStaffID(doc.getDrafterID());
//				account.setCompanyID(doc.getCompanyID());
//
//			}else if(doc.getSubscriberID2().equals(doc.getDrafterID())){
//				account.setStaffID(doc.getSubscriberID2());
//				account.setCompanyID(doc.getCompanyIDofSubscriber2());
//
//			}else{
//
//
//			}
				if (doc.getStatus() != null
						&& doc.getStatus().equalsIgnoreCase("R")) { // 驳回时发送
					docFlowService.reSendDocument(doc, account);
				} else {
					docFlowService.createDocument(doc, account); // 第一次发送
				}


			contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),document.getReceiverID(),document.getReceiverDeptID(),document.getReceiverCompanyID(),source);

			// 添加对公文的操作记录
			String hqlstaff = "from Staff where staffID = ?";
			
			Staff receiver = (Staff)baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{document.getReceiverID()});

			docCommonService.addTrackRecord(doc.getDocId(), account
					.getStaffID(), checkOrgID,
					account.getCompanyID(), "提交审批至" + receiver.getStaffName()
							+ "(" + receiver.getStaffCode() + ")");


			jsonObjList.accumulate("result", "suc");
		} catch (Exception e) {
			jsonObjList.accumulate("result", "fail");	
			logger.error("操作异常", e);
		}
	
		result = jsonObjList;

		return "success";
	}
	
	
	
	/**
	 * 
	 * 传阅 @param docId,... staffID,checkComID ,appendComment,
	 *
	 * receiverID,receiverDeptID,receiverCompanyID
	 */
	public String passDraftDocuments() {


		JSONObject jsonObjList = new JSONObject();
		try {
			String hql = "from Document where docId = ?";
			String hqlstaff = "from Staff where staffID = ?";
			Document doc = null;


				// 列表传阅
				if (docId != null && !docId.equals("")) {
					doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { docId });

					
				}

			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();


			CAccount account  = (CAccount) session.getAttribute("account");
			if(account!=null){
				staffID = account.getStaffID();
				checkComID = account.getCompanyID();

			}
			if(checkComID!=null&&!checkComID.equals("")) {

				checkOrgID = contractService.getCheckOrg(staffID, checkComID);
			}
				doc.setPassTime(new Date());
				doc.setUpdateTime(new Date());
				doc.setFromMember(account.getStaffID());

				doc.setSubscriberID2(document.getReceiverID());
				doc.setDeptIDofSubscriber2(document.getReceiverDeptID());

			if(document.getReceiverCompanyID()!=null&&document.getReceiverCompanyID().length()>11) {
				doc.setCompanyIDofSubscriber2(document.getReceiverCompanyID());

			}

				baseBeanService.update(doc);

				// 增加已传阅记录
				DocumentPass dp = new DocumentPass();
				dp.setPassId(serverService.getServerID("passId"));
				dp.setDocId(doc.getDocId());
				dp.setPassTime(new Date());
				dp.setSubscriber2(account.getStaffID());
				dp.setDeptOfsub2(checkOrgID);
				dp.setCompanyIDOfsub2(account.getCompanyID());
				dp.setToSubscriber2(doc.getReceiverID());
				dp.setDeptOftoSub2(doc.getReceiverDeptID());
				dp.setCompanyIDOftosub2(doc.getReceiverCompanyID());
				baseBeanService.save(dp);
				Staff receiver = null;
				COrganization org = null;
				Company company = null;
				String hqlorg = "from COrganization where organizationID = ?";
				String hqlcompany = "from Company where companyID = ?";
				receiver = (Staff) baseBeanService.getBeanByHqlAndParams(
						hqlstaff, new Object[] { document.getReceiverID() });

				org = (COrganization) baseBeanService.getBeanByHqlAndParams(
						hqlorg, new Object[] { document.getReceiverDeptID() });

				company = (Company) baseBeanService
						.getBeanByHqlAndParams(hqlcompany, new Object[] { document
								.getReceiverCompanyID() });
			String orgName = "";
			if(org!=null){
				orgName = org.getOrganizationName();
			}
			String comName = "";
			if(company!=null){
				comName = company.getCompanyName();
			}
				// 添加对公文的操作记录
				docCommonService.addTrackRecord(doc.getDocId(), account
						.getStaffID(), checkOrgID,
						account.getCompanyID(), "传阅公文至"
								+ receiver.getStaffName() + "("
								+ receiver.getStaffCode() + ")["
								+ orgName+ ","
								+comName + "]");

				String content = "[收件箱]下,请进行查阅处理";
		    	Map<String, Object> session1 = ActionContext.getContext().getSession();
				docCommonService.sendPhoneMessage(document.getReceiverID(), document
						.getReceiverDeptID(),document.getReceiverCompanyID(), account.getStaffID(),account.getCompanyID(), content,
						doc.getTitle(), (String) session1.get("module"), "pass");

				doc.setReceiverID(null);
				doc.setReceiverDeptID(null);
				doc.setReceiverCompanyID(null);
				baseBeanService.update(doc);

             contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),document.getReceiverID(),document.getReceiverDeptID(),document.getReceiverCompanyID(),source);
			jsonObjList.accumulate("result", "suc");
		} catch (Exception e) {
			jsonObjList.accumulate("result", "fail");
			logger.error("操作异常", e);
		}
		result = jsonObjList;
		return "success";
	}

	
	/***
	 * 
	 * 
	 * 收件箱列表
	 * @return
	 */

	// **待办公文列表
	public String list() {

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListExamine(finishType));
		JSONObject jret = new JSONObject();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			List<Object> list=new ArrayList<Object>();
		     for(BaseBean b:pageForm.getList()){
		    	 JSONObject jo = new JSONObject();
		    	 if (finishType.equals("examine")) {
		    	    ViewUnexaminedoc doc=(ViewUnexaminedoc) b;
				    jo.accumulate("docId", doc.getDocid());
				    jo.accumulate("title", doc.getTitle()); 
				    list.add(jo);
		    	 }else{
		    		  ViewExaminedoc doc=(ViewExaminedoc) b;
					  jo.accumulate("docId", doc.getDocid());
					  jo.accumulate("title", doc.getTitle()); 
					  list.add(jo); 
		    	 }
			  
		     }
		     jret.accumulate("list", list);
		     jret.accumulate("pageNumber", pageForm.getPageNumber());
		     jret.accumulate("pageSize", pageForm.getPageSize());
		     jret.accumulate("recordCount", pageForm.getRecordCount());
		     jret.accumulate("result","data");
		}else{
			 jret.accumulate("result", "nodata");
		}
		result =  jret;
		return "success";

	}

	private DetachedCriteria getListExamine(String finishType) {

		Map<String, Object> session = ActionContext.getContext().getSession();
		module = module.substring(0,1).toLowerCase()+module.substring(1);
		DetachedCriteria dc = null;
		if (finishType.equals("examine")) {
			dc = DetachedCriteria.forClass(ViewUnexaminedoc.class);
			dc.add(Restrictions.eq("activityname", "Examine and Approve"));

			dc.addOrder(Order.desc("updatetime"));
		} else {
			dc = DetachedCriteria.forClass(ViewExaminedoc.class);
			dc.addOrder(Order.desc("examinetime"));
		}
		dc.add(Restrictions.eq("assignee", staffID));
		dc.add(Restrictions.eq("module", module));
		dc.add(Restrictions.eq("deptidofsubscriber",checkOrgID));

		if (search != null && search.equals("search")) {
			documentSearchInfo = (DocumentSearchInfo) session
					.get("tablesearch");
			if (!"".equals(documentSearchInfo.getDocNum().trim())) {
				dc.add(Restrictions.like("docnum", documentSearchInfo
						.getDocNum().trim(), MatchMode.ANYWHERE));
			}

			if (!"".equals(documentSearchInfo.getTitle())) {
				dc.add(Restrictions.like("title", documentSearchInfo.getTitle()
						.trim(), MatchMode.ANYWHERE));
			}
			if (!"".equals(documentSearchInfo.getFromMember())) {
				dc.add(Restrictions.like("frommember", documentSearchInfo
						.getFromMember(), MatchMode.ANYWHERE));
			}
		}

		return dc;
	}
	
	public String toSearchexamine() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentSearchInfo);
		return list();

	}

	//

	// 审批
	/**
	 * 审批公文 jump reject 驳回，noapprove 不批准，approve 批准，transfer 转他人审批
	 * 
	 * @param
	 *
	 * @return
	 */
	public String examine() {
		HttpServletRequest request = ServletActionContext.getRequest();


		CAccount account  = (CAccount) request.getSession().getAttribute("account");

		JSONObject jsonObjList = new JSONObject();

		String hql = "from Document where docId = ?";

		Document oldDoc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { document.getDocId() });
		try {
			if(jump.equals("selftoSeal")){ //转自己盖章，把之前审核人的信息传为盖章人信息

				document.setReceiverID(oldDoc.getSubscriberID());
				document.setReceiverDeptID(oldDoc.getDeptIDofSubscriber());
				document.setReceiverCompanyID(oldDoc.getCompanyIDofSubscriber());
				jump = "approve";
			}
			int result =  docFlowService.examineDocument(document, jump);
			jsonObjList.accumulate("result", "success");





			String[] contents = new String[] { "驳回公文至拟稿人收件箱", "审批不通过",
					"审批通过转至盖章", "审批通过转至他人审批" };
			String content = "";
			if (result != 0 && result != 5) {
				content = contents[result - 1];
			}

			docCommonService.addTrackRecord(document.getDocId(), document.getSubscriberID()
							, document.getDeptIDofSubscriber(),
					document.getCompanyIDofSubscriber(), content);


			String messagecontent = "";
			String type = "";
			String receiverID = "";
			String receiverDeptID = "";
			String receiverCompanyID = "";
			if (result == 1) {
				messagecontent = "[收件箱]下,公文被驳回";
				type = "reject";
				receiverID = oldDoc.getDrafterID();
				receiverDeptID = oldDoc.getOrganizationID();
				receiverCompanyID = oldDoc.getCompanyID();


				//如果被驳回
				//判断是否是第三方的如果是第三方的处理第三方状态
				contractService.updateRelate(oldDoc.getScene(),document.getDocId(),"reject");
			}
			if (result == 2) {
				messagecontent = "审批未通过";
				type = "noexamine";
				receiverID = oldDoc.getDrafterID();
				receiverDeptID = oldDoc.getOrganizationID();
				receiverCompanyID = oldDoc.getCompanyID();
			}
			if (result == 3) {
				messagecontent = "[未盖章公文]下,请进行盖章";
				type = "seal";
				receiverID = document.getReceiverID();
				receiverDeptID = document.getReceiverDeptID();
				receiverCompanyID = document.getReceiverCompanyID();
			}
			if (result == 4) {
				messagecontent = "[未审批公文]下,请进行审批";
				type = "examine";
				receiverID = document.getReceiverID();
				receiverDeptID = document.getReceiverDeptID();
				receiverCompanyID = document.getReceiverCompanyID();
			}
			if(result==3||result==4){
				contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),receiverID
						, receiverDeptID,
						receiverCompanyID,source);

			}
			Map<String, Object> session = ActionContext.getContext().getSession();
			docCommonService.sendPhoneMessage(receiverID, receiverDeptID,receiverCompanyID,
					oldDoc.getSubscriberID(),account.getCompanyID(),  messagecontent, oldDoc.getTitle(),
					(String) session.get("module"), type);


		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		result = jsonObjList;

		return "success";
	}


	/**
	 * 公文发行readers:发行对象的读者ID（STAFFID）列表，以|分割。
	 *
	 * @param
	 *
	 * @return
	 */
	public String publishDocument() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String dreaders = request.getParameter("dreaders");
		HttpSession session = request.getSession();

		CAccount account  = (CAccount) session.getAttribute("account");
		if(dreaders!=null&&!dreaders.equals("")) {
			String r = contractService.addInventRecord(document.getDocId(), "read", dreaders, account.getStaffID());

			for (String infos1 : dreaders.split(",")) {
				String staffid = "";
				String[] info1 = infos1.split("-");
				String tel = "";
				staffid = info1[0];
				if(info1[0]==null||info1[0].equals("")){
					tel = info1[1];
				}
				logger.info("值：{}", 1);
				String id = contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),staffid,"",tel,info1[2]);
				logger.info("调试信息");

				contractService.updateRecentContact(id,"pub");
			}

		}

		if(readers!=null&&!readers.equals("")) {
			String hql1 = "from Document where docId = ?";
			Document doc = (Document) baseBeanService
					.getBeanByHqlAndParams(hql1, new Object[]{document
							.getDocId()});

			String hql = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[]{doc.getPublisherID()});
			if (comment != null && !comment.equals("")) {
				document.setPublisherComment(staff.getStaffName() + "("
						+ staff.getStaffCode() + ")" + ":" + comment + ";");
			}
			try {
				String result = docFlowService.publishDocument(document, readers);

				if (result.equals("1")) {
					String content = "分发公文";
					docCommonService.addTrackRecord(document.getDocId(), doc.getPublisherID(), doc.getDeptIDofPublisher(),
							doc.getCompanyIDofPublisher(), content);

					String messagecontent = "[未阅读]下，请进行查阅处理";
					for (String infos : readers.split(",")) {
						String[] info = infos.split("-");
						List<String> stafflist = new ArrayList<String>();
						if (!stafflist.contains(info[0])) {
							stafflist.add(info[0]);
                            if(info.length>1){
								docCommonService.sendPhoneMessage(info[0], info[2], info[1],
										doc.getPublisherID(),account.getCompanyID(),  messagecontent, doc
												.getTitle(), doc.getModule(), "read");
								String id = contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),info[0],info[2],info[1],info[3]);
								logger.info("值：{}", 2);
								logger.info("调试信息");
								contractService.updateRecentContact(id,"pub");

							}else{
								docCommonService.sendPhoneMessage(info[0], "","",
										doc.getPublisherID(),account.getCompanyID(),  messagecontent, doc
												.getTitle(), doc.getModule(), "read");
								logger.info("值：{}", 3);

								String id = contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),info[0],"","",info[3]);
								logger.info("调试信息");

								contractService.updateRecentContact(id,"pub");


							}

						}
					}

				}

			} catch (Exception e) {
				logger.error("操作异常", e);
			}
		}
		return "success";
	}


	/**
	 * 公文再发行 readers:发行对象的读者ID（STAFFID）列表，以|分割。typeros:表示read转发还是publish分发
	 *
	 * @param
	 *
	 *
	 *
	 *
	 * @return
	 */
	public String rePublishDocument() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String dreaders = request.getParameter("dreaders");
		if(dreaders!=null&&!dreaders.equals("")) {
			String r = contractService.addInventRecord(docId, "read", dreaders, account.getStaffID());

			for (String infos1 : dreaders.split(",")) {
				String[] info1 = infos1.split("-");
				String  staffid = "";
				String tel = "";
				staffid = info1[0];
				if(info1[0]==null||info1[0].equals("")){
					tel = info1[1];
				}
				contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),staffid,"",tel,info1[2]);

			}

		}


		String sendResult = "";
		if(readers!=null&&!readers.equals("")) {

			String hql = "from Document where docId = ?";

			Document document = (Document) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[]{docId});
			document.setTransfer(request.getParameter("zfStatus"));
			document.setLoad(request.getParameter("loadStatus"));
			document.setPrint(request.getParameter("printStatus"));
			document.setShare(request.getParameter("shareStatus"));
			document.setPub(request.getParameter("pubStatus"));
			try {

				sendResult = docFlowService.rePublishDocument(document, account,
						readers);
				hql = "from Document where docId = ?";
				Document doc = (Document) baseBeanService.getBeanByHqlAndParams(
						hql, new Object[]{document.getDocId()});
				String messagecontent = "[未阅读]下，请进行查阅处理";


				for (String infos : readers.split(",")) {
					String[] info = infos.split("-");
					List<String> stafflist = new ArrayList<String>();
					if (!stafflist.contains(info[0])) {
						stafflist.add(info[0]);
						if(info.length>1){
							docCommonService.sendPhoneMessage(info[0], info[2], info[1],
									account
											.getStaffID(),account.getCompanyID(), messagecontent, doc
											.getTitle(), doc.getModule(), "read");
							contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),info[0],info[2],info[1],info[3]);

						}else{
							docCommonService.sendPhoneMessage(info[0], "","",
									account
											.getStaffID(),account.getCompanyID(),  messagecontent, doc
											.getTitle(), doc.getModule(), "read");
							contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),info[0],"","",info[3]);

						}

					}
				}

			} catch (Exception e) {
				logger.error("操作异常", e);
			}


			String existedName = "";// 已被分发得人的姓名
			if (sendResult != "" && sendResult != "fail") {
				String hql2 = "from Staff where staffId = ?";
				for (String st : sendResult.split(",")) {
					Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
							hql2, new Object[]{st});
					existedName += staff.getStaffName() + ",";
				}
				sendResult = existedName;
			}

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", sendResult);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	//取消
	public String getHtmlOfOffice() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");

		String basePath = ServletActionContext.getRequest().getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/";

		String hql = "from Document where docId = ?";
		String hqlattach = "from DocumentFileAttach where document = ?";
		Document doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });
		DocumentFileAttach attach = (DocumentFileAttach) baseBeanService
				.getBeanByHqlAndParams(hqlattach, new Object[] { doc });

		String html = docAndroidService
				.getHtmlByPoi(realpath, basePath, attach);
		JSONObject jsonObjList = new JSONObject();
		jsonObjList.accumulate("html", html);
		result = jsonObjList;
		return "success";

	}
	
	
	
	/**
	 * 
	 * 获取office路径
	 * @return 参数 docId
	 */
	public String getOfficePath() {

		String hql = "from Document where docId = ?";
		String hqlattach = "from DocumentFileAttach where document = ?";
		Document doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });
		DocumentFileAttach attach = (DocumentFileAttach) baseBeanService
				.getBeanByHqlAndParams(hqlattach, new Object[] { doc });

		
		JSONObject jsonObjList = new JSONObject();
		jsonObjList.accumulate("result",attach.getFilePath());
		result = jsonObjList;
		return "success";

	}
	
	
	/**
	 * 得到下载路径
	 * @param
	 * @return
	 */
	public String getOfficeforLoad() { 
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String hql = "from Document where docId = ?";
		String hqlattach = "from DocumentFileAttach where document = ?";
		Document doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });
		DocumentFileAttach attach = (DocumentFileAttach) baseBeanService
				.getBeanByHqlAndParams(hqlattach, new Object[] { doc });
		JSONObject jsonObjList = new JSONObject();
		Map<String,Object> maps = new HashMap<String,Object>();
		if(attach!=null&&!"".equals(attach.getFilePath())){
           File file = new File(realpath+attach.getFilePath());
           maps.put("docPath", attach.getFilePath());
           if(file.exists()){
		     maps.put("docSize",file.length());
           }else{
        	 maps.put("docSize",0); 
           }
		}else{
			maps.put("docPath","");
		    maps.put("docSize",0);
		}
		jsonObjList.accumulate("docInfo", maps);
		result = jsonObjList;
		return "success";

	}
	
	
	
	
	/**
	 * 
	 * 或得公司和对应的部门
	 * @return
	 */
	public String getComAndOrgs(){
		try{
		String hql1 = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql1,
				new Object[] { checkComID });
		String groupCompanySn = company.getGroupCompanySn();

		String hql = " from Company where groupCompanySn = ? and companyStatus = ?";
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { groupCompanySn, "00" });
		Company com = null;
		String hql2= "from COrganization where companyID = ? and organizationPID = ? and Status ='00' order by organizationNumber";
		 List<BaseBean> orglist = null;
		 Map<String,List<BaseBean>> map = null;
		 List<Map<String,List<BaseBean>>> lists = new ArrayList<Map<String,List<BaseBean>>>();
		 Map<String,Object> maps = new HashMap<String,Object>();
		for(BaseBean b:companylist){
			 map = new HashMap<String, List<BaseBean>>();
		      com =  (Company) b;      
			  orglist = baseBeanService.getListBeanByHqlAndParams(
						hql2, new Object[] {  com.getCompanyID(),  com.getCompanyID() });
			  map.put(com.getCompanyID(),orglist);
			  lists.add(map);
		}
		maps.put("companylist", companylist);
		maps.put("comorgs",orglist);
		JSONObject jsonObjList = new JSONObject();
		jsonObjList.accumulate("lists",maps);
		result = jsonObjList;
		}catch(Exception e){
			logger.error("操作异常", e);
		}
		
		return "success";
	}
	
	/**
	 * 根据当前公司获取该公司和该公司一个集团下所有公司
	 * 
	 * @return
	 */
	public String getCompanyList() {
		
		String hql1 = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql1,
				new Object[] { checkComID });
		String groupCompanySn = company.getGroupCompanySn();

		String hql = " from Company where groupCompanySn = ? and companyStatus = ?";
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { groupCompanySn, "00" });

		JSONObject jsonObjList = new JSONObject();
		jsonObjList.accumulate("companylist", companylist);
		result = jsonObjList;
		return "success";
	}
	
	
	
	/**
	 * 
	 * 根据公司得到部门；
	 * @return
	 */
	public String getOrgByCompany() {

		String hql1 = "from COrganization where companyID = ? and organizationPID = ? and Status ='00' order by organizationNumber";
		List<BaseBean> orglist = baseBeanService.getListBeanByHqlAndParams(
				hql1, new Object[] { checkComID, checkComID });
		
		JSONObject jsonObjList = new JSONObject();
		jsonObjList.accumulate("orglist", orglist);
		result = jsonObjList;
	    
		return "success";
	}
	


	/**
	 * 根据选中的公司获得该公司下得所有部门 checkComID
	 * getAllOrgsAndModule.jspa?checkComID = 
	 * @return
	 */
	public String getAllOrgsAndModule() {

		String hql1 = "from COrganization where companyID = ? and organizationPID = ? and Status ='00' order by organizationNumber";
		List<BaseBean> orglist = baseBeanService.getListBeanByHqlAndParams(
				hql1, new Object[] { checkComID, checkComID });

		COrganization org = null;
		
		Map<String,Object> mapbyorg = new HashMap<String, Object>();
		Map<String,Object> maps = new HashMap<String, Object>();
		Map<String,String> map = null;
	    for(BaseBean b:orglist){
	    	map = new HashMap<String, String>();
			map.put("doc", "公文流转单");
			map.put("contract", "企业合同管理");
			map.put("cg", "公司规划管理");
			map.put("dg", "部门规划管理");
			map.put("pg", "个人规划管理");
			map.put("jg", "职业规划管理");
			map.put("countReg", "国家法定管理");
			map.put("induReg", "行业法规管理");
			map.put("annNoti", "公告通知管理");
			map.put("complaint", "投诉处理");
			map.put("regime", "制度管理");
			map.put("news", "新闻管理");
			map.put("interDis", "内部纠纷");
			map.put("exterDis", "外部纠纷");
			map.put("bulletin", "简报管理");
			map.put("MeetRecord", "会议记录管理");
	    	org = (COrganization) b;
	    	if(org.getOrganizationName().equals("人事处")||org.getOrganizationName().equals("人事部")){
				map.put("person","人事报表传输");
			}
	        if(org.getOrganizationName().equals("财务处")||org.getOrganizationName().equals("财务部")){
	        	map.put("finace","财务报表");
			}	
	        mapbyorg.put(org.getOrganizationID(), map);
	    }
		JSONObject jsonObjList = new JSONObject();
		maps.put("orglist",orglist);
		maps.put("orgmodule", mapbyorg);
		jsonObjList.accumulate("orglistAmodule", maps);
		result = jsonObjList;
	    
		return "success";
	}
	
	/**
	 * 
	 * 
	 * 根据公司部门获取module
	 * @return
	 */
	public  String getModulesByOrg(){
		
		String hql1 = "from COrganization where organizationID = ?";
		COrganization org = (COrganization) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{checkOrgID});
		   Map<String,String> map = new HashMap<String, String>();
			map.put("doc", "公文流转单");
			map.put("contract", "企业合同管理");
			map.put("cg", "公司规划管理");
			map.put("dg", "部门规划管理");
			map.put("pg", "个人规划管理");
			map.put("jg", "职业规划管理");
			map.put("countReg", "国家法定管理");
			map.put("induReg", "行业法规管理");
			map.put("annNoti", "公告通知管理");
			map.put("complaint", "投诉处理");
			map.put("regime", "制度管理");
			map.put("news", "新闻管理");
			map.put("interDis", "内部纠纷");
			map.put("exterDis", "外部纠纷");
			map.put("bulletin", "简报管理");
			map.put("MeetRecord", "会议记录管理");
	    	if(org.getOrganizationName().equals("人事处")||org.getOrganizationName().equals("人事部")){
				map.put("person","人事报表传输");
			}
	        if(org.getOrganizationName().equals("财务处")||org.getOrganizationName().equals("财务部")){
	        	map.put("finace","财务报表");
			}	
	    
		JSONObject jsonObjList = new JSONObject();
		jsonObjList.accumulate("allmodule", map);
		result = jsonObjList;
		return "success";
		
	}
	

	
	
	/**
	 * 
	 * 获取公司部门，人
	 * @return
	 */
	public String getReceiverInfo() {

		String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? "
				+ "or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))";

		Object[] params = new Object[] { checkComID, "50", checkOrgID,
				checkOrgID };
		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		JSONObject jsonObjList = new JSONObject();
		if(stafflist.size()!=0){
		    jsonObjList.accumulate("stafflist", stafflist);
		    jsonObjList.accumulate("result", "data");
		}else{
			jsonObjList.accumulate("result", "nodata");
		}
		result = jsonObjList;
		return "success";
	}
	
	

	
	/**
	 * 
	 * 获取普通印章列表
	 * @return
	 */
	public String getSealList(){
		
		String hql = "select e.stampName,e.scanningAccessories,e.enterpriseStampID  from EnterpriseStamp e where gore = ? and companyID = ? and responsibleID = ?";
		List<BaseBean> stamplist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{"g",checkComID,staffID});
		JSONObject jo = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		if(stamplist.size()!=0){
			for (Object bb : stamplist) {
				Object[] c = (Object[]) bb;
				JSONObject temp=new JSONObject();
				temp.accumulate("stampName", c[0]==null?"":c[0]);
				temp.accumulate("scanningAccessories", c[1]==null?"":c[1]);
				temp.accumulate("enterpriseStampID", c[2]==null?"":c[2]);
				list.add(temp);
			}
			jo.accumulate("seallist", list);
			jo.accumulate("result", "data");
		}else{
			jo.accumulate("result", "nodata");
		}
		result = jo;
		return "success";
	}
	
	
	/**
	 * 插入印章图片
	 * //docId imagePath, enterpriseStampID
	 * @return
	 */
	public String insertSeal(){
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String realpath = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String basePath = ServletActionContext.getRequest().getScheme()
					+ "://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath() + "/";
			String hql = "from Document where docId = ?";
			String hqlattach = "from DocumentFileAttach where document = ?";
//			Document doc = (Document) baseBeanService.getBeanByHqlAndParams(
//					hql, new Object[] { docId });
//			DocumentFileAttach attach = (DocumentFileAttach) baseBeanService
//					.getBeanByHqlAndParams(hqlattach, new Object[] { doc });
			//docId imagePath, enterpriseStampID
			String re = SealOffice.sealHtml(
					realpath + htmlPath, basePath+imagePath);//要盖章的html文件路径，以及要盖章的图片路径
			JSONObject jsonObjList = new JSONObject();
			if(re=="0"){
				jsonObjList.accumulate("filePath", basePath+htmlPath);
				jsonObjList.accumulate("result", "success");
			}else{
				jsonObjList.accumulate("result", "fail");
			}
			result = jsonObjList;
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		
		return "success";
	}
	
	
	/**
	 *  docId imagePath;
	 * 
	 * 插入印章到office
	 * @return
	 */
	public String insertSealToOffice(){

		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String hql = "from Document where docId = ?";
		String hqlattach = "from DocumentFileAttach where document = ?";
		Document doc = (Document) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { docId });
		DocumentFileAttach attach = (DocumentFileAttach) baseBeanService
				.getBeanByHqlAndParams(hqlattach, new Object[] { doc });

		//imagePath章路径 
		String re = "";
		if(attach.getExt().indexOf("doc")!=-1){
		 re = SealOffice.sealWord(attach.getFilePath(), imagePath, realpath);
		}else{
	     re = SealOffice.sealExcel(attach.getFilePath(), imagePath, realpath);	
		}
		JSONObject jsonObjList = new JSONObject();
		if(re=="0"){
			jsonObjList.accumulate("result", "success");
		}else{
			jsonObjList.accumulate("result", "fail");
		}
		result = jsonObjList;
		
		
		return "success";
	}

///////////////////////////////////////////君子签////////////////////////////////////////////////////

	/**
	 *
	 * 获取签约链接
	 * @return
	 */
	public String getSignUrl(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();

		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String tel = "";
		if(tc!=null) {
			 tel = tc.getAccount();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";

		String realpath = ServletActionContext.getRequest()
				.getSession().getServletContext().getRealPath("/");
		String signUrl = contractService.signUrlDoc(document.getDocId(),basePath,tel,realpath);

		try {
			String urlcode =  URLEncoder.encode(basePath + "ea/androiddoc/ea_updateSealer.jspa?document.docId=" + document.getDocId(), "utf-8");
			String url =  signUrl + "&backUrl=" + urlcode;
			HttpServletResponse response = ServletActionContext.getResponse();


			logger.info("调试信息");
			response.sendRedirect(url);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("signUrl",signUrl);
//		JSONObject jo = JSONObject.fromObject(map);
//		this.result = jo.toString();

		return "success";

	}



	/**
	 *
	 * 跳转君子签
	 * @return
	 */
	public String jumpJzq(){

		String url = request.getParameter("url");
		try {

			HttpServletResponse response = ServletActionContext.getResponse();


			logger.info("调试信息");
			response.sendRedirect(url);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";
	}


	/**
	 *
	 *
	 * 下载最新文件
	 * @return
     */
	public String updateSealer(){
		HttpServletRequest request = ServletActionContext.getRequest();

		String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");

		String filePath = contractService.updateSealer(document.getDocId(),path);

		request.setAttribute("filePath",filePath);

		return "qyht";



	}


	/**
	 *
	 *
	 * 追加 签约人
	 * @return
	 */
	public String applySignAdd(){


		contractService.applySignAdd(document.getDocId());



		return "success";



	}

	/**
	 *
	 * 下载视频证据
	 */
	public String downVideo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
          String videoURl = contractService.downVideoURL(document.getDocId(),path);
		  request.setAttribute("videoUrl",videoURl);
        return "tovideo";
	}
///////////////////////////////////////////////模板管理//////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *
	 * 添加模板
	 * @return
	 */
	public String addDocTemp(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");

		if (image != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String  photoPath = fileService.savePhoto(path,imageFileName,image,(account.getCompanyID()==null||account.getCompanyID().equals(""))?account.getStaffID():account.getCompanyID(),"/web/temp/"
					+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			documentTemplate.setTemplatePath(photoPath);
		}

		contractService.addDocTemp(account.getCompanyID(),documentTemplate,account.getStaffID());
		request.setAttribute("message","11");
		return "success";
	}
	/**
	 *
	 *
	 * 获取模板
	 * @return
     */
	public  String getDocTemp(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			 companyID = account.getCompanyID();
		}

		String parameter = request.getParameter("parameter");
		String fileType = request.getParameter("fileType");

		String temptId = request.getParameter("temptId");
		String module = request.getParameter("module");

	    pageForm =  contractService.getDocTemp(pageNumber,pageSize==0?25:pageSize,companyID,account.getStaffID(),parameter,fileType,temptId,module);

		String ajax = request.getParameter("ajax");
		if("ajax".equals(ajax)){

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();



			return "success";
		}
		if("company201009046vxdyzy4wg0000000025".equals(companyID)||"company201009046vxdyzy4wg0000000065".equals(companyID)){
			request.setAttribute("isSet","0");
		}else{
			request.setAttribute("isSet","1");
		}

		return  "tempManage";


	}






	/**
	 *
	 *
	 * 获取模板
	 * @return
	 */
	public  String getDocTempList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}

		String parameter = request.getParameter("parameter");
		String fileType = request.getParameter("fileType");

		String temptId = request.getParameter("temptId");
		String range = request.getParameter("range");
		String module = request.getParameter("module");
		List<BaseBean> list =  contractService.getDocTempTree(companyID,account.getStaffID(),parameter,fileType,temptId, module,range);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();


		return  "success";


	}

	/**
	 *
	 *
	 * 获取模板
	 * @return
	 */
	public  String getDocTempTree(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}


		if("company201009046vxdyzy4wg0000000025".equals(companyID)||"company201009046vxdyzy4wg0000000065".equals(companyID)){
			request.setAttribute("isSet","0");
		}else{
			request.setAttribute("isSet","1");
		}
		request.setAttribute("companyID",companyID);

		return  "temptree";


	}


	/**
	 *
	 *
	 * 选择模板
	 * @return
	 */
	public  String getSelectTemp(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}


		if("company201009046vxdyzy4wg0000000025".equals(companyID)||"company201009046vxdyzy4wg0000000065".equals(companyID)){
			request.setAttribute("isSet","0");
		}else{
			request.setAttribute("isSet","1");
		}
		request.setAttribute("companyID",companyID);
		String source = request.getParameter("source");
		if("rz".equals(source)){
			return  "selecttemprz";
		}

		return  "selecttemp";


	}

	/**
	 *
	 *
	 * 选择模板份额里
	 * @return
	 */
	public  String getSelectTempType(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}


		if("company201009046vxdyzy4wg0000000025".equals(companyID)||"company201009046vxdyzy4wg0000000065".equals(companyID)){
			request.setAttribute("isSet","0");
		}else{
			request.setAttribute("isSet","1");
		}
		request.setAttribute("companyID",companyID);

		return  "selecttemptype";


	}

	/**
	 *
	 * 根据模板ID获取模板信息
	 * @return
     */
	public String getTempInfoByID(){
		String templateId = request.getParameter("templateId");
		DocumentTemplate temp = (DocumentTemplate)baseBeanService.getBeanByHqlAndParams("from DocumentTemplate where templateId = ?",new Object[]{templateId});

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("temp",temp);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();


		return  "success";

	}

	/**
	 *
	 * 模板排序
	 * @return
	 */
	public String getTempSortlist(){

		String temptId = request.getParameter("temptId");

		childlist  =  baseBeanService.getListBeanByHqlAndParams("from DocumentTemplate where temptId = ? order by seq",new Object[]{temptId});


		return "toTempSort";
	}


	/**
	 *
	 *
	 * 保存分类排序
	 * @return
	 */
	public String  saveTempSort(){

		contractService.saveTempSort(childrenID);

		request.setAttribute("message","11");
		return "success";
	}
	/**
	 *
	 * 删除模板
	 * @return
	 */
	public String deleteTemp(){
		HttpServletRequest request = ServletActionContext.getRequest();

		String templateId = request.getParameter("templateId");
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");

		String r = contractService.deleteTemp(templateId,path);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("r",r);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}
	/**
	 *
	 * 添加模板分类
	 * @return
     */
	public String addDocTempType(){
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}
		contractService.addDocTempType(templateType,companyID,account.getStaffID());
		request.setAttribute("message","11");
         return  "success";
	}





	/**
	 *
	 *
	 * 获取模板分类
	 * @return
     */
	public String getDocTempTypeList(){

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}

		String parameter = request.getParameter("parameter");
		String module = request.getParameter("module");


		pageForm =  contractService.getDocTempTypeList(pageNumber,30,companyID,account.getStaffID(),parameter,module);

		String ajax = request.getParameter("ajax");
		if("ajax".equals(ajax)) {


			Map<String,Object> map = new HashMap<String,Object>();

			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(Document.class);
			cfg.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor("yyyy-MM-dd"));

			map.put("pageForm", pageForm);
			JSONObject jo = JSONObject.fromObject(map,cfg);
			this.result = jo.toString();

			return "success";
		}

		if("company201009046vxdyzy4wg0000000025".equals(companyID)||"company201009046vxdyzy4wg0000000065".equals(companyID)){
			request.setAttribute("isSet","0");
		}else{
			request.setAttribute("isSet","1");
		}
		return "tempType";
	}


	/**
	 *
	 *
	 * 跳转模板分类页面
	 * @return
	 */
	public String getTempTypeTree(){

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}


		if("company201009046vxdyzy4wg0000000025".equals(companyID)||"company201009046vxdyzy4wg0000000065".equals(companyID)){
			request.setAttribute("isSet","0");
		}else{
			request.setAttribute("isSet","1");
		}

		request.setAttribute("companyID",companyID);
		return "tempTypeTree";
	}


	/**
	 *
	 *
	 * 获取模板分类不分页
	 * @return
	 */
	public String getDocTempTypeLists(){

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}

		String parameter = request.getParameter("parameter");
		String parentId = request.getParameter("parentId");
		String module = request.getParameter("module");
		List<BaseBean> list =  contractService.getDocTempTypeLists(parentId,companyID,account.getStaffID(),parameter,module);

		Map<String,Object> map = new HashMap<String,Object>();

		JsonConfig cfg = new JsonConfig();
		cfg.setRootClass(Document.class);
		cfg.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));

		map.put("list", list);

		if("company201009046vxdyzy4wg0000000025".equals(companyID)||"company201009046vxdyzy4wg0000000065".equals(companyID)){
			map.put("isSet", "0");

		}else{
			map.put("isSet", "1");
		}

		JSONObject jo = JSONObject.fromObject(map,cfg);
		this.result = jo.toString();

		return "success";





	}

	/**
	 *
	 * 获取排序下级
	 * @return
     */
	public String getSortlist(){

		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}

		String parentId = request.getParameter("parentId");

		String module = request.getParameter("module");
		childlist =  contractService.getDocTempTypeLists(parentId,companyID,account.getStaffID(),"",module);

		return "toSort";
	}

	/**
	 *
	 *
	 * 保存分类排序
	 * @return
     */
	public String  saveCateSort(){

		contractService.saveCateSort(childrenID);

		request.setAttribute("message","11");
		return "success";
	}

	/**
	 *
	 * 删除模板分类
	 * @return
     */
	public String deleteTempType(){
		 HttpServletRequest request = ServletActionContext.getRequest();

		 String tempId = request.getParameter("tempId");

		 String r = contractService.deleteTempType(tempId);

		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("r",r);
		 JSONObject jo = JSONObject.fromObject(map);
		 this.result = jo.toString();
         return "success";
	}

	/**
	 *
	 * 将文件生成新PDF
	 * @return
	 */
	public String getPdfTempView(){
		HttpServletRequest request = ServletActionContext.getRequest();

		String templateId = request.getParameter("templateId");
		String realpath = ServletActionContext.getRequest()
				.getSession().getServletContext().getRealPath("/");
		String pdfpath = contractService.getPdfTempView(templateId,realpath);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pdfpath",pdfpath);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return  "success";
	}

	/**
	 *
	 *
	 * @return
	 */
	public String addInventRecord(){
		CAccount account = (CAccount) session.getAttribute("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String status = request.getParameter("status");
		String telphone = request.getParameter("telphone");
        String r = contractService.addInventRecord(docId,status,telphone,account.getStaffID());
		contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),"","",telphone,"03");

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("r",r);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return  "success";
	}

////////////////////////////////////////////////模板管理//////////////////////////////////////////////////////////////
	/**
	 *
	 *
	 * 跳转档案夹页面
	 * @return
	 */
	public String getArchiveTypeTree(){

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}



		request.setAttribute("companyID",companyID);
		return "arvTypeTree";
	}

	/**
	 *
	 *
	 * 获取档案夹不分页
	 * @return
	 */
	public String getArchiveTypeLists(){

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}

		String parameter = request.getParameter("parameter");
		String parentId = request.getParameter("parentId");
		String module = request.getParameter("module");
		List<BaseBean> list =  contractService.getArchiveTypeLists(parentId,companyID,account.getStaffID(),parameter,module);

		Map<String,Object> map = new HashMap<String,Object>();

		JsonConfig cfg = new JsonConfig();
		cfg.setRootClass(Document.class);
		cfg.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));

		map.put("list", list);

		JSONObject jo = JSONObject.fromObject(map,cfg);
		this.result = jo.toString();

		return "success";





	}

	/**
	 *
	 * 添加模板分类
	 * @return
	 */
	public String addArchiveType(){
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}
		contractService.addArchiveType(archiveDocType,companyID,account.getStaffID());
		request.setAttribute("message","11");
		return  "success";
	}


	/**
	 *
	 * 删除模板分类
	 * @return
	 */
	public String deleteArchiveType(){
		HttpServletRequest request = ServletActionContext.getRequest();

		String adtId = request.getParameter("adtId");

		String r = contractService.deleteArchiveType(adtId);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("r",r);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 *
	 *
	 * 获取归档文件
	 * @return
	 */
	public  String getArchiveTree(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}

		request.setAttribute("companyID",companyID);

		return  "archivetree";


	}
	/**
	 *
	 *
	 * 获取归档文件不分页
	 * @return
	 */
	public  String getArchiveList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
		}

		String parameter = request.getParameter("parameter");

		String adtId = request.getParameter("adtId");
		String module = request.getParameter("module");
		List<BaseBean> list =  contractService.getArchiveTree(companyID,account.getStaffID(),parameter,adtId, module);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();


		return  "success";


	}

	/**
	 *
	 *
	 * 获取模板
	 * @return
	 */
	public  String getPageFormArchive(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		CAccount account = (CAccount) session.getAttribute("account");
		String companyID = "";
		String staffID = "";
		if(account!=null) {
			companyID = account.getCompanyID();
			staffID = account.getStaffID();
		}

		String parameter = request.getParameter("parameter");

		String adtId = request.getParameter("adtId");
		String module = request.getParameter("module");

		pageForm =  contractService.getPageFormArchive(pageNumber,pageSize==0?25:pageSize,companyID,staffID,parameter,adtId,module);


			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();



			return "success";






	}

	/**
	 *
	 * 查看档案盒详情
	 * @return
	 */
	public String viewArchiveBox(){
		String adtId = request.getParameter("adtId");
		archiveDocType = contractService.getArchiveDeatil(adtId);
		return "arvbox";
	}


	/**
	 *
	 * 扫码档案盒条码查看
	 * @return
	 */
	public String scanArchiveBox(){
		String barcode = request.getParameter("barcode");
		archiveDocType = contractService.getArchiveDeatil(barcode);
		return "arvbox";
	}

	/**
	 *
	 * 扫码公文条码查看
	 * @return
	 */
	public String scanDoc(){
		String barcode = request.getParameter("barcode");

		document = (Document) baseBeanService.getBeanByHqlAndParams("from Document where barCode=?", new Object[] { barcode });
		if(document.getSpecificTemplate()!=null&&!document.getSpecificTemplate().equals("")) {
			DocumentTemplate documentTemplate = (DocumentTemplate) baseBeanService.getBeanByHqlAndParams("from DocumentTemplate where templateId=?", new Object[]{document.getSpecificTemplate()});

			request.setAttribute("documentTemplate",documentTemplate);
		}


		// 处理附件(因为之前的数据有可能是多个附件，以免出现错误)
		List<BaseBean> attachlist = docCommonService.getOfficeAttach(document);
		if (attachlist.size() != 0) {
			docFileAttach = (DocumentFileAttach) attachlist.get(0);
		}

		document = docCommonService.bcInfo(document);
		return "viewDoc";
	}

	/**
	 *
	 * 扫描条码跳转
	 * @return
	 */
	public String scanBarcode(){
		String barcode = request.getParameter("barcode");
		Map<String,Object> map = new HashMap<String,Object>();
           ScanBarCode scanBarCode = (ScanBarCode)baseBeanService.getBeanByHqlAndParams("from ScanBarCode where barcode = ?",new Object[]{barcode});
           if(scanBarCode!=null){
			   map.put("urls",scanBarCode.getUrls());
		   }else{
			   map.put("urls","");
		   }


		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
         return "success";
	}

	/**
	 *
	 *
	 * 转移
	 * @return
	 */
	public String transferDoc(){

		contractService.transferDoc(document.getDocId(),module);

		return "success";

	}

	/**
	 *
	 * 最近联系人
	 * @return
	 */
	public String getRecentInfo(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = contractService.getRecentContactInfo(account.getStaffID(),source);

		Map<String,Object> map = new HashMap<String,Object>();

		map.put("list",list);

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";


	}

	/**
	 *
	 * 最近联系人
	 * @return
	 */
	public String getRecentInfoByOpr(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String oprState = request.getParameter("oprState");

		List<Object> list = contractService.getRecentInfoByOpr(account.getStaffID(),oprState);

		Map<String,Object> map = new HashMap<String,Object>();

		map.put("list",list);

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";


	}

	/**
	 *
	 * 其他功能模块需要盖章的
	 * @return  /ea/androiddoc/ea_toAuditOthers.jspa?docRelateOther.title=测试第三方&docRelateOther.source=yj&docRelateOther.htmUrl=page\WFJClient\pc\earth\test.jsp&docRelateOther.tableName=Staff&docRelateOther.idName=staffID&docRelateOther.stateName=price&docRelateOther.stateValue=1111
	 */

	public String toAuditOthers(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");

		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";

		if(account!=null){
			staffID = account.getStaffID();
			checkComID = account.getCompanyID();
			docRelateOther.setCompanyID(account.getCompanyID());
			docRelateOther.setStaffID(account.getStaffID());
		}
		if(checkComID!=null&&!checkComID.equals("")) {

			checkOrgID = contractService.getCheckOrg(staffID, checkComID);
		}


		Map<String,Object> map = new HashMap<String, Object>();
		map.put("organizationID", checkOrgID);
		map.put("module",(module==null||module.equals(""))?"doc":module);


		document.setScene("03");
		document.setTitle(docRelateOther.getTitle());
		Document doc = docCommonService.storeDocApp(document, account,
				map);
		docRelateOther.setDocId(doc.getDocId());
		DocumentFileAttach docFileAttach = contractService.createAttach(docRelateOther,realpath,basePath,checkComID);

		contractService.addOthers(docRelateOther,"01",null);

		docCommonService.storeAttachDocApp(doc,docFileAttach,staffID,realpath);

		docFlowService.createDocument(doc, account);

		contractService.addRecentContact(account.getStaffID(),account.getCompanyID(),document.getReceiverID(),document.getReceiverDeptID(),document.getReceiverCompanyID(),source);

		// 添加对公文的操作记录
		String hqlstaff = "from Staff where staffID = ?";

		Staff receiver = (Staff)baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{document.getReceiverID()});

		docCommonService.addTrackRecord(doc.getDocId(), account
						.getStaffID(), checkOrgID,
				account.getCompanyID(), "提交审批至" + receiver.getStaffName()
						+ "(" + receiver.getStaffCode() + ")");


		      return "success";
	}

	/**
	 *
	 * 第三方获取审核的PDF文件
	 * @return
	 */
	public String getAuditPDF(){
		String idName = request.getParameter("idName");
		String idValue = request.getParameter("idValue");

		String hql = "from Document where docId = (select r.docId from DocRelateOther r where r.idName = ? and r.idValue = ? and r.createDate = (select max(rr.createDate) from DocRelateOther rr where rr.idName = ? and rr.idValue = ?))";
		document = (Document)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{idName,idValue,idName,idValue});


       return "pdfaudit";
	}

	/**
	 *
	 * 获取文件列表
	 * @return
	 */
	public String getAllFiles(){

		CAccount account = (CAccount) session.getAttribute("account");
		String parameter = request.getParameter("parameter");
		if(account!=null){
			pageForm = contractService.getAllFiles(pageNumber==0?1:pageNumber,30,account.getCompanyID(),module,parameter);

		}

		String ajax = request.getParameter("ajax");
		if("ajax".equals(ajax)) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("pageForm", pageForm);

			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(Document.class);
			cfg.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor("yyyy-MM-dd"));
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("attachFiles") || arg1.equals("subscribers")) {
						return true;
					} else {
						return false;
					}
				}
			});
			JSONObject jo = JSONObject.fromObject(map,cfg);
			this.result = jo.toString();
			return "success";
		}
		return "toall";
	}
	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

    

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<Document> getDoclist() {
		return doclist;
	}

	public void setDoclist(List<Document> doclist) {
		this.doclist = doclist;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public DocumentSearchInfo getDocumentSearchInfo() {
		return documentSearchInfo;
	}

	public void setDocumentSearchInfo(DocumentSearchInfo documentSearchInfo) {
		this.documentSearchInfo = documentSearchInfo;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getJump() {
		return jump;
	}

	public void setJump(String jump) {
		this.jump = jump;
	}

	public String getCheckOrgID() {
		return checkOrgID;
	}

	public void setCheckOrgID(String checkOrgID) {
		this.checkOrgID = checkOrgID;
	}

	public String getCheckComID() {
		return checkComID;
	}

	public void setCheckComID(String checkComID) {
		this.checkComID = checkComID;
	}
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public DocumentFileAttach getDocFileAttach() {
		return docFileAttach;
	}

	public void setDocFileAttach(DocumentFileAttach docFileAttach) {
		this.docFileAttach = docFileAttach;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getFinishType() {
		return finishType;
	}

	public void setFinishType(String finishType) {
		this.finishType = finishType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getReaders() {
		return readers;
	}

	public void setReaders(String readers) {
		this.readers = readers;
	}

	public DocumentShare getDocShare() {
		return docShare;
	}

	public void setDocShare(DocumentShare docShare) {
		this.docShare = docShare;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	public DocumentTemplate getDocumentTemplate() {
		return documentTemplate;
	}

	public void setDocumentTemplate(DocumentTemplate documentTemplate) {
		this.documentTemplate = documentTemplate;
	}

	public List<BaseBean> getChildlist() {
		return childlist;
	}

	public void setChildlist(List<BaseBean> childlist) {
		this.childlist = childlist;
	}

	public String getChildrenID() {
		return childrenID;
	}

	public void setChildrenID(String childrenID) {
		this.childrenID = childrenID;
	}

	public ArchiveDocType getArchiveDocType() {
		return archiveDocType;
	}

	public void setArchiveDocType(ArchiveDocType archiveDocType) {
		this.archiveDocType = archiveDocType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public DocRelateOther getDocRelateOther() {
		return docRelateOther;
	}

	public void setDocRelateOther(DocRelateOther docRelateOther) {
		this.docRelateOther = docRelateOther;
	}
}
