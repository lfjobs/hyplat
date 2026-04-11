package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.bo.office.DocTheme;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentAuditing;
import hy.ea.bo.office.DocumentFileAttach;
import hy.ea.bo.office.DocumentPass;
import hy.ea.bo.office.DocumentPublish;
import hy.ea.bo.office.DocumentShare;
import hy.ea.bo.office.DocumentTemplate;
import hy.ea.bo.office.InteractDocInfo;
import hy.ea.bo.office.SocialMemberInfo;
import hy.ea.bo.office.SuggestionDoc;
import hy.ea.bo.office.Document.DocumentSearchInfo;
import hy.ea.bo.office.vo.ViewUnsealdoc;
import hy.ea.office.service.DocCommonService;
import hy.ea.office.service.ZOfficeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.RandomDatas;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 公文流转管理
 */
public class DocumentInfoAction {
	private static final Logger logger = LoggerFactory.getLogger(DocumentInfoAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ZOfficeService zOfficeService;
	@Resource
	private DocCommonService docCommonService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private DocumentSearchInfo documentSearchInfo;
	public InputStream excelStream;
	private SocialMemberInfo socialMemberInfo;
	private Document document;
	private DocumentTemplate documentTemplate;
	private InteractDocInfo interactDocInfo;
	private DocumentShare docShare;
	private SuggestionDoc suggestionDoc;
	private DocumentAuditing docAudit;
	private List<DocumentAuditing> docAuditList;
	private DocumentFileAttach docFileAttach;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String search;
	private String searchType;
	private String refreshJumpType;
	private String gord;// 查询时区分是已发送拟稿中的查询，还是归档中的查询draftsearch、guidangsearch
	private String module;
	private String track;//00代表是登记表

	// 以下正式的
	private String type;
	private String docId;
	private List<BaseBean> attachlist;
	private String submitType;
	private String finishType;
    private List<BaseBean> doclist;
    private String visiturl;

	/** *************************************草稿箱所有功能开始************************************************ */

	/**
	 * 新建公文
	 * 
	 */
	public String newDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}

		try {
			document = new Document();
			document.setDocId(serverService.getServerID("docId"));
			Calendar ca = Calendar.getInstance();
			String numTime = String.valueOf(ca.get(Calendar.YEAR));// 获取年份
			document.setNumTime(numTime);
			document.setStatus("d");
			document.setModule((String) session.get("module"));
			baseBeanService.save(document);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "toNewDoc";
	}

	/**
	 * 保存草稿：全部存储；更新；
	 * 
	 */
	public String storeDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		try {

			docCommonService.storeDocData(document, docFileAttach, account,
					session);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";
	}

	/**
	 * 
	 * 获得草稿列表
	 * 
	 * @return
	 */
	public String getDocDraftList() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String organizationID = (String) session.get("organizationID");

		if (account == null) {

			return "not_login";
		}
		if (search != null && search.equals("search")) {
			searchQuery(searchType);
		} else {
			String hql = " from Document d where d.drafterID=? and d.status = ? and  d.subscriberID2 is null and d.module = ? and d.organizationID = ? and d.docId not in(select docId from DocDelRecord) order by draftTime desc";
			
			if(track!=null&&track.equals("10")){
				doclist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] { account.getStaffID(), "I",
							(String) session.get("module"), organizationID });
				doclist = docCommonService.getFullListBean(doclist);
			}else{
			
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, new Object[] { account.getStaffID(), "I",
							(String) session.get("module"), organizationID });

			// 补充Document;
			pageForm = docCommonService.getFullPageForm(pageForm);
			}
			
		}
   
		//跟踪列表
		if(track!=null&&track.equals("00")){
			visiturl = "ea/documentinfo/ea_getDocDraftList.jspa";
			return "track";
			
		}
		//跟踪打印列表
		if(track!=null&&track.equals("10")){
			return "trackprint";
		}
		return "draft";

	}

	/**
	 * 
	 * 获取收件箱列表包括他人传阅以及驳回列表
	 */
	public String getReceiveBoxList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		if (search != null && search.equals("search")) {
			searchQuery(searchType);
		} else {
			String hql = "from Document d where ((d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?)or (d.status = ? and d.drafterID = ? and organizationID = ?))and d.module = ?  and d.docId not in(select docId from DocDelRecord) order by passTime desc";
			
			
			if(track!=null&&track.equals("10")){
				doclist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] { account.getStaffID(),
						(String) session.get("organizationID"), "R",
						account.getStaffID(),
						(String) session.get("organizationID"),
						(String) session.get("module") });
				doclist = docCommonService.getFullListBean(doclist);
			}else{
			
			
			
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber),
					hql, new Object[] { account.getStaffID(),
							(String) session.get("organizationID"), "R",
							account.getStaffID(),
							(String) session.get("organizationID"),
							(String) session.get("module") });
			pageForm = docCommonService.getFullPageForm(pageForm);
			}
		}

		
		        //跟踪列表
				if(track!=null&&track.equals("00")){
					visiturl = "ea/documentinfo/ea_getReceiveBoxList.jspa";
					return "track";
				}
				//跟踪打印列表
				if(track!=null&&track.equals("10")){
					return "trackprint";
				}
				if(searchType!=null&&searchType.equals("mobile")){
					String sql = " select d.*,f.filepath from DT_OA_DOCUMENT d left join DT_OA_DOCUMENT_FILEATTACH f on d.key= f.documentkey where ((d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?)or (d.status = ? and d.drafterID = ? and organizationID = ?))and d.module = ? and d.docId not in(select docId from DT_OA_DOC_DELRECORD) order by passTime desc";
					String sqlcount = " select count(*) " + sql.substring(sql.indexOf("from"));
					try {
						pageForm= baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
								.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber), sql, sqlcount, new Object[] { account.getStaffID(),
										(String) session.get("organizationID"), "R",
										account.getStaffID(),
										(String) session.get("organizationID"),
										"doc" });
						pageForm =(PageForm) docCommonService.getFullPageForm(pageForm);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("操作异常", e);
					}
					return "receivermobile";
					
				}
		return "receiver";
	}

	/**
	 * 
	 * 已发送列表，包括已发送至审批，至信息平台，传阅
	 * 
	 * @return
	 */
	public String getSendedDocList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (account == null) {

			return "not_login";
		}
		if (search != null && search.equals("search")) {
			searchQuery(searchType);
		} else {
			String hql = "from Document d where ((d.drafterID=? and d.status!='I' and d.status!='D' and d.organizationID = ?) or (d.docId in (select p.docId from DocumentPass p where p.subscriber2 = ? and p.deptOfsub2 = ?))) and d.docId not in(select docId from DocDelRecord) and d.module = ?  order by updateTime desc";
			
			if(track!=null&&track.equals("10")){
				doclist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] { account.getStaffID(), organizationID,
						account.getStaffID(), organizationID,
						(String) session.get("module") });
				doclist = docCommonService.getFullListBean(doclist);
			}else{
			
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber),
					hql, new Object[] { account.getStaffID(), organizationID,
							account.getStaffID(), organizationID,
							(String) session.get("module") });
			pageForm = docCommonService.getFullPageForm(pageForm);
			}
		}
		 //跟踪列表
		if(track!=null&&track.equals("00")){
			visiturl = "ea/documentinfo/ea_getSendedDocList.jspa";
			return "track";
		}
		//跟踪打印列表
		if(track!=null&&track.equals("10")){
			return "trackprint";
		}

		return "yessend";

	}

	/*
	 * 
	 * 获得已归档列表
	 */
	public String getArchivedList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		if (search != null && search.equals("search")) {
			searchQuery(searchType);
		} else {
			String hqlcom = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom,new Object[]{account.getCompanyID()});
			String hql = "from Document d where d.groupCompanySn=? and d.status= ? and d.module = ? and d.docId not in(select docId from DocDelRecord) order by d.guidangTime desc";
			
			
			if(track!=null&&track.equals("10")){
				doclist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {company.getGroupCompanySn(), "G",
						(String) session.get("module") });
				doclist = docCommonService.getFullListBean(doclist);
			}else{
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, new Object[] {company.getGroupCompanySn(), "G",
							(String) session.get("module") });
			pageForm = docCommonService.getFullPageForm(pageForm);
			}
		}
		
		 //跟踪列表
		if(track!=null&&track.equals("00")){
			visiturl = "ea/documentinfo/ea_getArchivedList.jspa";
			return "track";
		}
		//跟踪打印列表
		if(track!=null&&track.equals("10")){
			return "trackprint";
		}

		return "archive";

	}

	/**
	 * 
	 * 
	 * 获得共享列表
	 * 
	 * @return
	 */
	public String getShareDocuments() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		if (search != null && search.equals("search")) {
			searchQuery(searchType);
		} else {
			String hql = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { account.getCompanyID() });

			hql = "from Document d where d.module = ? and d.docId not in(select docId from DocDelRecord where stage = ? and operator = ?) and docId in(select s.docId from DocumentShare s where (s.companyId = ? and s.shareType = ?) or (s.groupCompanySn = ? and s.shareType = ?))";

			if(track!=null&&track.equals("10")){
				doclist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] { (String) session.get("module"),
						"share", account.getStaffID(),
						account.getCompanyID(), "current",
						company.getGroupCompanySn(), "group" });
				doclist = docCommonService.getFullListBean(doclist);
			}else{
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, new Object[] { (String) session.get("module"),
							"share", account.getStaffID(),
							account.getCompanyID(), "current",
							company.getGroupCompanySn(), "group" });
			pageForm = docCommonService.getFullPageForm(pageForm);
			}
		}
		
		 //跟踪列表
		if(track!=null&&track.equals("00")){
			visiturl = "ea/documentinfo/ea_getShareDocuments.jspa";
			return "track";
		}
		//跟踪打印列表
		if(track!=null&&track.equals("10")){
			return "trackprint";
		}


		return "share";
	}

	/**
	 * 修改时获取公文信息
	 * 
	 */
	public String getUpdateDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from Document where docId=?";
//		String hqlstaff = "from Staff where staffID = ?";
//		String hqlccom = "from ContactCompanyView where ccompanyID = ?";
		document = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });

//		if(document.getPartyA()!=null&&!document.getPartyA().equals("")){
//			if(document.getPartyA().startsWith("cstaff")){
//				Staff staffA = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{document.getPartyA()});
//				if(staffA!=null){
//					document.setPartyAName(staffA.getStaffName());
//				}
//			}else{
//				ContactCompanyView ContactCompanyA = (ContactCompanyView) baseBeanService.getBeanByHqlAndParams(hqlccom,new Object[]{document.getPartyA()});
//				if(ContactCompanyA!=null){
//					document.setPartyAName(ContactCompanyA.getCompanyName());
//				}
//			}
//		}
//		if(document.getPartyB()!=null&&!document.getPartyB().equals("")){
//			if(document.getPartyB().startsWith("cstaff")){
//				Staff staffB = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{document.getPartyB()});
//				if(staffB!=null){
//					document.setPartyBName(staffB.getStaffName());
//				}
//			}else{
//				ContactCompanyView ContactCompanyB = (ContactCompanyView) baseBeanService.getBeanByHqlAndParams(hqlccom,new Object[]{document.getPartyB()});
//				if(ContactCompanyB!=null){
//					document.setPartyBName(ContactCompanyB.getCompanyName());
//				}
//			}
//		}
		String eType = document.getEmergencyType();
		
		if(eType!=null&&eType.equals("p")){
			document.setEmergencyType("普通");
		} 
		if(eType!=null&&eType.equals("j")){
			document.setEmergencyType("急件");
		}
	    if(eType!=null&&eType.equals("t")){
	    	document.setEmergencyType("特急");
		}
		// 处理与信息平台交互的评论

		hql = "from SuggestionDoc where docId = ? and receiver = ? order by sugTime";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { docId, account.getStaffID() });

		if (list.size() != 0) {
			StringBuilder sb = new StringBuilder();
			for (BaseBean b : list) {
				SuggestionDoc sd = (SuggestionDoc) b;
				if (sd.getSuggestion() != null
						&& !sd.getSuggestion().equals("")) {
					sb.append(sd.getSenderName() + ":" + sd.getSuggestion()
							+ ";");
				}
			}

			document.setCommonComment(document.getCommonComment() + sb);
		}
		if (document.getCommonComment() == null) {
			document.setCommonComment("");
		}
		
		//获取项目
		if(document.getJournalNum()!=null&&!document.getJournalNum().equals("")){
		String hqlcost = "from CostSheetBill where journalNum = ?";
		CostSheetBill b = (CostSheetBill) baseBeanService.getBeanByHqlAndParams(hqlcost,new Object[]{document.getJournalNum()});
		if(b!=null){
			document.setProjectName(b.getProjectName());
		  }
		}

		// 处理附件(因为之前的数据有可能是多个附件，以免出现错误)
		List<BaseBean> attachlist = docCommonService.getOfficeAttach(document);
		if (attachlist.size() != 0) {
			docFileAttach = (DocumentFileAttach) attachlist.get(0);
		}
		return "toNewDoc";
	}
	
	
	/**
	 * 
	 * 
	 * 查看附件
	 * @return
	 */
	public String viewAttach(){
		
		String hql = "from Document where docId = ?";
	    Document document = (Document) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{docId});
		
		// 处理附件(因为之前的数据有可能是多个附件，以免出现错误)
		List<BaseBean> attachlist = docCommonService.getOfficeAttach(document);
		if (attachlist.size() != 0) {
			docFileAttach = (DocumentFileAttach) attachlist.get(0);
		}
	    Map<String,Object> map = new HashMap<String,Object>();
	    
	    JsonConfig cfg = new JsonConfig();
	    cfg.setRootClass(DocumentFileAttach.class);
	    cfg.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object arg0, String arg1, Object arg2) {
                if (arg1.equals("document")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
	    map.put("attach", docFileAttach);
	    JSONObject jo = JSONObject.fromObject(map,cfg);
	    this.result = jo.toString();
		
		return "success";
	}

	/**
	 * 查看各种类型公文
	 * 
	 * @param type:哪个阶段的查看
	 */

	public String getViewDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String load = request.getParameter("load");
		String print = request.getParameter("print");

		String hql = "from Document where docId=?";
		document = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });

		document = docCommonService.getViewFullDoc(document);
		if (document != null) {
			attachlist = docCommonService.getOfficeAttach(document);
			if (load != null && !load.equals("")) {
				document.setLoad(load);
				document.setPrint(print);
			}
		}
		// 处理与信息平台交互的评论

		hql = "from SuggestionDoc where docId = ? and receiver = ? order by sugTime";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { docId, account.getStaffID() });
		if (list.size() != 0) {
			StringBuilder sb = new StringBuilder();
			for (BaseBean b : list) {
				SuggestionDoc sd = (SuggestionDoc) b;
				if (sd.getSuggestion() != null
						&& !sd.getSuggestion().equals("")) {
					sb.append(sd.getSenderName() + ":" + sd.getSuggestion()
							+ ";");
				}
			}

			document.setCommonComment(document.getCommonComment() + sb);
		} else {
			document.setCommonComment("");

		}

		return type;
	}

	/**
	 * 
	 * 传阅
	 */
	public String passDraftDocuments() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		try {
			String hql = "from Document where docId = ?";
			String hqlstaff = "from Staff where staffID = ?";
			Document doc = null;
			Staff staff = null;
			String[] docIds = null;
			int length = 1;
			if (document != null && !document.getDocId().equals("")) {
				docIds = document.getDocId().split(",");
				length = docIds.length;
			}
			for (int i = 0; i < length; i++) {
				// 列表传阅
				if (docId != null && !docId.equals("")) {
					doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { docId });

					// 页面传阅
				} else {
					if (submitType != null && submitType.equals("pass")) {
						doc = (Document) baseBeanService.getBeanByHqlAndParams(
								hql, new Object[] { docIds[i] });
						doc.setReceiverID(document.getReceiverID());
						doc.setReceiverDeptID(document.getReceiverDeptID());
						doc.setReceiverCompanyID(document
								.getReceiverCompanyID());
					} else {
						doc = docCommonService.storeDocData(document,
								docFileAttach, account, session);
					}

				}
				staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
						new Object[] { account.getStaffID() });
				if (doc.getAppendComment() != null
						&& !doc.getAppendComment().equals("")) {
					String commonComment = doc.getCommonComment();

					if (commonComment == null) {

						commonComment = "";

					}
					doc.setCommonComment(commonComment + staff.getStaffName()
							+ "(" + staff.getStaffCode() + "):"
							+ doc.getAppendComment() + ";");

					doc.setAppendComment("");// 传阅后把附件意见清空；

				}
				doc.setPassTime(new Date());
				doc.setUpdateTime(new Date());
				doc.setFromMember(account.getStaffID());
				doc.setSubscriberID2(doc.getReceiverID());
				doc.setDeptIDofSubscriber2(doc.getReceiverDeptID());
				doc.setCompanyIDofSubscriber2(doc.getReceiverCompanyID());

				baseBeanService.update(doc);

				// 增加已传阅记录
				DocumentPass dp = new DocumentPass();
				dp.setPassId(serverService.getServerID("passId"));
				dp.setDocId(doc.getDocId());
				dp.setPassTime(new Date());
				dp.setSubscriber2(account.getStaffID());
				dp.setDeptOfsub2((String) session.get("organizationID"));
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
						hqlstaff, new Object[] { doc.getReceiverID() });

				org = (COrganization) baseBeanService.getBeanByHqlAndParams(
						hqlorg, new Object[] { doc.getReceiverDeptID() });

				company = (Company) baseBeanService
						.getBeanByHqlAndParams(hqlcompany, new Object[] { doc
								.getReceiverCompanyID() });
				// 添加对公文的操作记录
				docCommonService.addTrackRecord(doc.getDocId(), account
						.getStaffID(), (String) session.get("organizationID"),
						account.getCompanyID(), "传阅公文至"
								+ receiver.getStaffName() + "("
								+ receiver.getStaffCode() + ")["
								+ org.getOrganizationName() + ","
								+ company.getCompanyName() + "]");

				String content = "[收件箱]下,请进行查阅处理";
				docCommonService.sendPhoneMessage(doc.getReceiverID(), doc
						.getReceiverDeptID(),doc.getReceiverCompanyID(), account.getStaffID(),account.getCompanyID(), content,
						doc.getTitle(), (String) session.get("module"), "pass");

				doc.setReceiverID(null);
				doc.setReceiverDeptID(null);
				doc.setReceiverCompanyID(null);
				baseBeanService.update(doc);
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";
	}

	/**
	 * 
	 * 已发送转发公文：包括，至审批，传阅，至信息平台
	 * 
	 * @return
	 */
	public String transmitSendDoc() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String docId = request.getParameter("docId");
			String hql = "from Document where docId=?";
			document = (Document) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { docId });

			// 处理附件(因为之前的数据有可能是多个附件，以免出现错误)
			List<BaseBean> attachlist = docCommonService
					.getOfficeAttach(document);
			if (attachlist.size() != 0) {
				docFileAttach = (DocumentFileAttach) attachlist.get(0);
				String realpath = ServletActionContext.getRequest()
						.getSession().getServletContext().getRealPath("/");
				String filepath = zOfficeService.CopyOfficeFile(realpath,
						docFileAttach.getFilePath());
				docFileAttach.setFilePath(filepath);
				docFileAttach.setFileName(filepath.substring(filepath
						.lastIndexOf("/") + 1));
				docFileAttach.setFileId("");
				docFileAttach.setKey("");

			}
			type = "send";
			document.setDocId(null);
			document.setDocNum(null);
			document.setReceiverID(null);
			document.setReceiverDeptID(null);
			document.setReceiverCompanyID(null);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "toNewDoc";

	}

	// /////////////////////////////////////归档功能//////////////////////////////////////////////////////////

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
				(String) session.get("organizationID"), account.getCompanyID(),
				content);

		return "success";
	}

	// /////////////////////////////////主题操作////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * 添加主题
	 * 
	 */
	public String AddTheme() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String theme = request.getParameter("theme");
		Map<String, Object> map = new HashMap<String, Object>();
		if (!theme.equals("通知") && !theme.equals("通告")) {
			String hql = "from DocTheme where themeName = ? and creatorID = ? and module = ?";
			List<BaseBean> themelist = baseBeanService
					.getListBeanByHqlAndParams(hql, new Object[] { theme,
							account.getStaffID(),
							(String) session.get("module") });
			if (themelist.size() != 0) {
				map.put("result", "fail");
			} else {
				DocTheme docTheme = new DocTheme();
				docTheme.setThemeId(serverService.getServerID("themeId"));
				docTheme.setThemeName(theme);
				docTheme.setModule((String) session.get("module"));
				docTheme.setCreatorID(account.getStaffID());
				docTheme.setCreateTime(new Date());
				baseBeanService.save(docTheme);
				map.put("theme", theme);
			}

		} else {
			map.put("result", "fail");
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 
	 * 
	 * 
	 * 获得主题
	 * 
	 * @return
	 */
	public String getThemeInfo() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "select distinct themeName from DocTheme where module = ? and creatorID = ? and themeName != ? and themeName != ?";

		List<BaseBean> themelist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { (String) session.get("module"),
						account.getStaffID(), "通知", "通告" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", themelist);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 
	 * 
	 * 将收件箱文件转至草稿箱
	 * 
	 * @return
	 */
	public String moveOthersToMine() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}

		String hql = "from Document where docId = ?";
		String[] docIds = docId.split(",");
		for (int i = 0; i < docIds.length; i++) {
			Document doc = (Document) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { docIds[i] });
			if (doc.getStatus().equals("R")) {
				doc.setStatus("I");
			} else {
				doc.setDrafterID(account.getStaffID());
				doc.setDeptIDofDrafter((String) session.get("organizationID"));
				doc.setOrganizationID((String) session.get("organizationID"));
				doc.setCompanyID(account.getCompanyID());
				doc.setSubscriberID2("");
				doc.setDeptIDofSubscriber2("");
				doc.setCompanyIDofSubscriber2("");
			}
			baseBeanService.update(doc);
			docCommonService.addTrackRecord(docIds[i], account.getStaffID(),
					(String) session.get("organizationID"), account
							.getCompanyID(), "转至草稿箱");
		}
		return "success";

	}

	/**
	 * 查草稿箱，收件箱个数
	 * 
	 */
	public String getCountsByDraftList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "";
		hql = "from Document d where d.module = ? and d.drafterID = ? and d.status = ? and d.subscriberID2 is null and d.organizationID = ? and d.docId not in(select docId from DocDelRecord)";
		List<BaseBean> list1 = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { (String) session.get("module"),
						account.getStaffID(), "I",
						(String) session.get("organizationID") });
		hql = "from Document d where ((d.status = ? and d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?) or (d.status = ? and d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?) or (d.status = ? and d.drafterID = ? and organizationID = ?)) and d.module = ?  and d.docId not in(select docId from DocDelRecord)";
		List<BaseBean> list2 = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { "I", account.getStaffID(),
						(String) session.get("organizationID"),"R", account.getStaffID(),
						(String) session.get("organizationID"),"R",
						account.getStaffID(),
						(String) session.get("organizationID"),
						(String) session.get("module") });
		String[] counts = new String[2];
		counts[0] = list1.size() + "";
		counts[1] = list2.size() + "";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", counts);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}



	/**
	 * 查草稿箱，收件箱个数
	 *
	 */
	public String getCountsByDraftList2() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "";
		List<BaseBean> list1 = null;
		List<BaseBean> list2 = null;
		if(account.getCompanyID()!=null&&!account.getCompanyID().equals("")) {
			hql = "from Document d where d.module = ? and d.drafterID = ? and d.status = ? and d.subscriberID2 is null and  d.title is not null and d.companyID = ? and d.docId not in(select docId from DocDelRecord)";
			list1 = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[]{(String) session.get("module"),
							account.getStaffID(), "I",
							account.getCompanyID()});
			hql = "from Document d where ((d.status = ? and d.subscriberID2 = ? and d.companyIDofSubscriber2 = ?) or (d.status = ? and d.subscriberID2 = ? and d.companyIDofSubscriber2 = ?) or (d.status = ? and d.drafterID = ? and companyID = ?)) and d.module = ?  and d.docId not in(select docId from DocDelRecord)";
			list2 = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[]{"I", account.getStaffID(),
							account.getCompanyID(), "R", account.getStaffID(),
							account.getCompanyID(), "R",
							account.getStaffID(),
							account.getCompanyID(),
							(String) session.get("module")});

		}else{
			hql = "from Document d where  d.drafterID = ? and d.status = ? and d.subscriberID2 is null and  d.title is not null and d.companyID is null and d.docId not in(select docId from DocDelRecord)";
			list1 = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[]{
							account.getStaffID(), "I"});
			hql = "from Document d where ((d.status = ? and d.subscriberID2 = ? and d.companyIDofSubscriber2 is null) or (d.status = ? and d.subscriberID2 = ? and d.companyIDofSubscriber2 is null) or (d.status = ? and d.drafterID = ? and d.companyID is null)) and d.docId not in(select docId from DocDelRecord)";
			list2 = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[]{"I", account.getStaffID(),
							 "R", account.getStaffID(),
							 "R",
							account.getStaffID()});

		}
		String[] counts = new String[2];
		counts[0] = list1.size() + "";
		counts[1] = list2.size() + "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", counts);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// //////////////////////////////////////共享功能////////////////////////////////////////////////////////

	/**
	 * 
	 * 设置分享
	 * 
	 * @return
	 */

	public String setShare() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
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
				.getStaffID(), (String) session.get("organizationID"), account
				.getCompanyID(), content);

		return "success";
	}

	/**
	 * 
	 * 
	 * 判断公文是否分享
	 * 
	 * @return
	 */
	public String checkShare() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String docId = request.getParameter("docId");
		String shareType = request.getParameter("shareType");
		String hql = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });

		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseBean> list = null;
		if (shareType.equals("group")) {
			hql = "from DocumentShare where docId = ? and groupCompanySn = ? and shareType = ?";
			list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
					docId, company.getGroupCompanySn(), shareType });
			if (list.size() > 0) {
				map.put("result", "no");
			} else {
				map.put("result", "yes");
			}
		}
		if (shareType.equals("current")) {
			hql = "from DocumentShare where docId = ? and (companyId = ? or (groupCompanySn = ? and shareType = ?))";
			list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
					docId, account.getCompanyID(), company.getGroupCompanySn(),
					shareType });
			if (list.size() == 0) {
				map.put("result", "yes");
			} else {
				map.put("result", "no");
			}
		}

		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/** ******************************任务查询包括草稿箱，收件箱，已发送，已归档，共享池开始********************************************************** */

	/**
	 * 
	 * 任务查询：包括草稿箱，收件箱，已发送，已归档，共享池
	 * 
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentSearchInfo);
		if (searchType.equals("draft")) {
			return getDocDraftList();
		} else if (searchType.equals("receiver")) {
			return getReceiveBoxList();
		} else if (searchType.equals("yessend")) {
			return getSendedDocList();
		} else if (searchType.equals("archive")) {
			return getArchivedList();
		} else {
			return getShareDocuments();
		}

	}

	public String searchQuery(String searchType) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		if (account == null) {

			return "not_login";
		}
		String hql = "";
		String module = (String) session.get("module");
		String organizationID = (String) session.get("organizationID");
		Object[] params = null;

		Date startDate = null;
		Date endDate = null;
		documentSearchInfo = (DocumentSearchInfo) session.get("tablesearch");
		if (documentSearchInfo.getSStart() != null
				&& !documentSearchInfo.getSStart().equals("")) {
			startDate = Utilities.getDateFromString(documentSearchInfo
					.getSStart(), "yyyy-MM-dd HH:mm:ss");
		}
		if (documentSearchInfo.getSEnd() != null
				&& !documentSearchInfo.getSEnd().equals("")) {
			endDate = Utilities.getDateFromString(documentSearchInfo.getSEnd(),
					"yyyy-MM-dd HH:mm:ss");
		}
		if (searchType.equals("draft")) {
			String hql1 = " and d.draftTime between ? and ?";
			hql = " from Document d where d.drafterID=? and d.status = ? and  d.subscriberID2 is null and d.module = ? and d.organizationID = ? and d.docId not in(select docId from DocDelRecord) and d.docNum like ? and d.title like ? and docType like ? ";
			if (startDate != null && !startDate.equals("")) {
				if (endDate == null || endDate.equals("")) {
					endDate = new Date();
				}
			
			
			params = new Object[] { account.getStaffID(), "I", module,
					organizationID,
					"%" + documentSearchInfo.getDocNum().trim() + "%",
					"%" + documentSearchInfo.getTitle().trim() + "%",
					"%" + documentSearchInfo.getDocType().trim() + "%",startDate, endDate };
			hql = hql + hql1;
		}else{
			params = new Object[] { account.getStaffID(), "I", module,
					organizationID,
					"%" + documentSearchInfo.getDocNum() + "%",
					"%" + documentSearchInfo.getTitle() + "%",
					"%" + documentSearchInfo.getDocType() + "%"};
		 }
			hql+=" order by draftTime desc";
		}

		if (searchType.equals("receiver")) {
			String hql1 = "and d.passTime between ? and ?";
			hql = "from Document d where ((d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?)or (d.status = ? and d.drafterID = ? and organizationID = ?)) and d.module = ?  and d.docId not in(select docId from DocDelRecord) and d.docNum like ? and d.title like ? and d.fromMember like ?";
			if (startDate != null && !startDate.equals("")) {
				if (endDate == null || endDate.equals("")) {
					endDate = new Date();
				}
				params = new Object[] { account.getStaffID(), organizationID,
						"R", account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getFromMember().trim() + "%",
						startDate, endDate };
				hql = hql + hql1;
			} else {
				params = new Object[] { account.getStaffID(), organizationID,
						"R", account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getFromMember().trim() + "%" };
			}

		}

		if (searchType.equals("yessend")) {
			String hql1 = "and d.updateTime between ? and ?";
			hql = "from Document d where ((d.drafterID=? and d.status!='I' and d.status!='D' and d.organizationID = ?) or (d.docId in (select p.docId from DocumentPass p where p.subscriber2 = ? and p.deptOfsub2 = ?))) and d.docId not in(select docId from DocDelRecord) and d.module = ? and d.docNum like ? and d.title like ? and d.status like ?";
			if (startDate != null && !startDate.equals("")) {
				if (endDate == null || endDate.equals("")) {
					endDate = new Date();
				}
				params = new Object[] { account.getStaffID(), organizationID,
						account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getStatus().trim() + "%",
						startDate, endDate };
				hql = hql + hql1;
			} else {
				params = new Object[] { account.getStaffID(), organizationID,
						account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getStatus().trim() + "%" };
			}

		}

		if (searchType.equals("archive")) {
			String hql1 = " and d.guidangTime between ? and ?";
			hql = "from Document d where d.groupCompanySn=? and d.status= ? and d.module = ? and d.docId not in(select docId from DocDelRecord) and d.docNum like ? and d.title like ?";

			String hqlcom = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom,new Object[]{account.getCompanyID()});
			if (startDate != null && !startDate.equals("")) {
				if (endDate == null || endDate.equals("")) {
					endDate = new Date();
				}
				params = new Object[] {company.getGroupCompanySn(), "G", module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						startDate, endDate };
				hql = hql + hql1;
			} else {
				params = new Object[] { company.getGroupCompanySn(), "G", module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%" };
			}

		}

		if (searchType.equals("share")) {
			String hqlcom = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hqlcom, new Object[] { account.getCompanyID() });
			hql = "from Document d where d.module = ? and d.docId not in(select docId from DocDelRecord where stage = ? and operator = ?) and docId in(select s.docId from DocumentShare s where (s.companyId = ? and s.shareType = ?) or (s.groupCompanySn = ? and s.shareType = ?)) and d.docNum like ? and d.title like ?";
			params = new Object[] { (String) session.get("module"), "share",
					account.getStaffID(), account.getCompanyID(), "current",
					company.getGroupCompanySn(), "group",
					"%" + documentSearchInfo.getDocNum().trim() + "%",
					"%" + documentSearchInfo.getTitle().trim() + "%" };

		}
		
		if(track!=null&&track.equals("10")){
			doclist = baseBeanService.getListBeanByHqlAndParams(hql, params);
			doclist = docCommonService.getFullListBean(doclist);
		}else{

		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql, params);

		pageForm = docCommonService.getFullPageForm(pageForm);
		}
		return searchType;

	}

	// ///////////////////////////////////////////发布到网站////////////////////////////////////////////////
	/**
	 * 
	 * 发布公文与外界交互
	 * 
	 * @return
	 */
	public String passDraftToComPlat() {
		Map<String, Object> session = ActionContext.getContext().getSession();

		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String inner = request.getParameter("inner");
		try {
			String hql = "from Document where docId = ?";
			Document doc = null;
			if (inner == null || inner.equals("")) {
				doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { document.getDocId() });

				doc.setSocial(document.getSocial());
			} else {
				doc = docCommonService.storeDocData(document, docFileAttach,
						account, session);

			}
			doc.setStatus("Z");
			doc.setUpdateTime(new Date());
			baseBeanService.update(doc);

			hql = "from Staff where staffID = ?";
			String hqlsm = "from SocialMemberInfo where telphone = ?";

			Staff receiver = null;
			SocialMemberInfo sm = null;
			Staff sender = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID() });
			if (doc.getSocial().startsWith("cstaff"))

			{
				receiver = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { doc.getSocial() });
			} else {
				sm = (SocialMemberInfo) baseBeanService.getBeanByHqlAndParams(
						hqlsm, new Object[] { doc.getSocial() });
			}
			// 将意见保存
			if (doc.getAppendComment() != null
					&& !doc.getAppendComment().equals("")) {
				SuggestionDoc suggestionDoc = new SuggestionDoc();
				suggestionDoc.setSugId(serverService.getServerID("sugId"));
				suggestionDoc.setDocId(doc.getDocId());
				suggestionDoc.setSender(account.getStaffID());
				suggestionDoc.setSenderName(sender.getStaffName());
				suggestionDoc.setReceiver(doc.getSocial());
				if (receiver != null) {
					suggestionDoc.setReceiverName(receiver.getStaffName());
				} else {
					suggestionDoc.setReceiverName(sm.getSmName() + "("
							+ sm.getCompanyName() + ")");
				}

				suggestionDoc.setSugTime(new Date().toString());
				suggestionDoc.setSuggestion(doc.getDrafterComment());
				baseBeanService.save(suggestionDoc);
			}

			// 把数据添加到InteractDocInfo中已供外部访问

			interactDocInfo.setDocId(doc.getDocId());
			interactDocInfo.setTitle(doc.getTitle());
			interactDocInfo.setStatus("Z");
			hql = "from DocumentFileAttach where document = ?";
			DocumentFileAttach docAttach = (DocumentFileAttach) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { doc });
			if (docAttach != null) {
				String partUri = "";
				if (interactDocInfo.getVisitType().equals("onlyread")) {
					partUri = "page/ea/main/office_ea/document/wordonlyreadprint.jsp?docPath=";

				} else {
					partUri = "page/ea/main/office_ea/document/wordOut.jsp?docPath=";
				}

				interactDocInfo.setWordUri(partUri + docAttach.getFilePath()
						+ "&fileShowNameField=" + docAttach.getFileShowName()
						+ "." + docAttach.getExt() + "&fileType="
						+ docAttach.getFileType());
			}

			if (receiver != null) {
				if (receiver.getUserName() == null) {
					String telPhone = receiver.getReference();
					if (telPhone == null) {
						String userName = RandomDatas.getRandomString(8);
						interactDocInfo.setUserName(userName);
						receiver.setUserName(userName);
					} else {
						receiver.setUserName(telPhone);
						interactDocInfo.setUserName(telPhone);
					}
					receiver.setPassword(receiver.getStaffName());
					interactDocInfo.setPassword(receiver.getStaffName());
					baseBeanService.update(receiver);

				} else {
					interactDocInfo.setUserName(receiver.getUserName());
					interactDocInfo.setPassword(receiver.getPassword());
				}
			} else {
				interactDocInfo.setUserName(sm.getTelphone());
				interactDocInfo.setPassword(sm.getSmName());
			}

			interactDocInfo.setPassTime(new Date());

			hql = "from InteractDocInfo where docId = ? and userName = ?";
			InteractDocInfo idi = (InteractDocInfo) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { doc.getDocId(),
							interactDocInfo.getUserName() });
			if (idi == null) {
				String intId = serverService.getServerID("intId");
				interactDocInfo.setIntId(intId);
				baseBeanService.save(interactDocInfo);
			} else {
				interactDocInfo.setKey(idi.getKey());
				interactDocInfo.setIntId(idi.getIntId());
				baseBeanService.update(interactDocInfo);

			}

			docCommonService.addTrackRecord(doc.getDocId(), account
					.getStaffID(), (String) session.get("organizationID"),
					account.getCompanyID(), "传至信息平台");
			String comWeb = interactDocInfo.getWeb();
			String webMessage = "";
			if (comWeb.indexOf('1') != -1) {
				webMessage += "http://www.ttst2010.com 或者";
			}
			if (comWeb.indexOf('2') != -1) {
				webMessage += "http://www.ttsw2010.com 或者";
			}
			if (comWeb.indexOf('3') != -1) {
				webMessage += "http://www.fulong2010.com 或者";
			}
			if (comWeb.indexOf('4') != -1) {
				webMessage += "http://www.swjx888.com 或者";
			}
			if (comWeb.indexOf('5') != -1) {
				webMessage += "http://www.qcjx.org 或者";
			}
			webMessage = webMessage.substring(0, webMessage.lastIndexOf("或者"));
			String content = "请访问以下任意网站进行查阅：" + webMessage + "。您的用户名："
					+ interactDocInfo.getUserName() + " 密码："
					+ interactDocInfo.getPassword();
			docCommonService.sendPhoneMessage(interactDocInfo.getUserName(),
					null, null,account.getStaffID(),account.getCompanyID(), content, doc.getTitle(),
					(String) session.get("module"), "infoplat");

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";
	}
	
	
	
	/*
	 * 
	 * 
	 * 根据公文ID获得该公文的评论
	 */
	public String getSuggestions() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String docId = request.getParameter("docId");
		String hql = "from SuggestionDoc where docId = ? and receiver = ? order by sugTime";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { docId, account.getStaffID() });
		List<SuggestionDoc> suglist = new ArrayList<SuggestionDoc>();
		for (BaseBean b : list) {
			SuggestionDoc sd = (SuggestionDoc) b;
			sd.setSugTimestr(sd.getSugTime().toString());
			suglist.add(sd);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", suglist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 临时添加社会人员
	 * 
	 * @return
	 */
	public String addSocialMember() {
		Map<String, Object> session = ActionContext.getContext().getSession();

		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		socialMemberInfo.setSmId(serverService.getServerID("social"));
		socialMemberInfo.setCreatorID(account.getStaffID());
		socialMemberInfo.setCreateTime(new Date());
		baseBeanService.save(socialMemberInfo);

		return "success";
	}

	/**
	 * 
	 * 获取临时添加的历史
	 * 
	 * @return
	 */
	public String getSocialMember() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from SocialMemberInfo where creatorID  = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}

	/**
	 * 
	 * 手机号验证
	 * 
	 * @return
	 */
	public String checktel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from SocialMemberInfo where creatorID  = ? and telphone = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID(),
						socialMemberInfo.getTelphone() });
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() == 0) {
			map.put("result", "suc");
		} else {

			map.put("result", "fail");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}

	/**
	 * 
	 * 发布到网站去
	 * 
	 * @return
	 */
	public String publishToWeb() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String hql = "from Staff where StaffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });

		HttpServletRequest request = ServletActionContext.getRequest();
		String web = request.getParameter("webs");

		hql = "from Document where docId = ?";
		Document doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });

		hql = "from DocumentFileAttach where document = ?";
		List<BaseBean> attaches = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { doc });

		String wordUri = "";
		for (BaseBean b : attaches) {
			DocumentFileAttach atta = (DocumentFileAttach) b;
			wordUri += atta.getFilePath() + "&fileShowNameField="
					+ atta.getFileShowName() + "." + atta.getExt()
					+ "&fileType=" + atta.getFileType() + ",";
		}

		DocumentPublish du = new DocumentPublish();
		du.setPubId(serverService.getServerID("pubId"));
		du.setCompanyId(account.getCompanyID());
		du.setDocId(docId);
		du.setPublisherID(account.getStaffID());
		du.setPublisher(staff.getStaffName());
		du.setTitle(doc.getTitle());
		du.setWordUri(wordUri);
		du.setWeb(web);
		du.setPubTime(new Date());
		baseBeanService.save(du);

		docCommonService.addTrackRecord(docId, account.getStaffID(),
				(String) session.get("organizationID"), account.getCompanyID(),
				"发布到网站");

		return "success";
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * 根据查询词查询收件人和模板 querytxt:查询词 querytype:查询类型 receiver：收件人；doc：模板
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String getQueryResults() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String querytxt = request.getParameter("querytxt");
			String querytype = request.getParameter("querytype");
			List<BaseBean> resultlist = null;
			if (querytype != null && querytype.equals("receiver")) {

				String sql = "select s.staffID,s.staffCode,s.staffName,c.companyId,c.organizationID,g.companyName,o.organizationName from dtcos c"
						+ " join dtcorganization o on c.organizationID = o.organizationID"
						+ " join dt_hr_staff s on c.staffID = s.staffID join dtCompany g on c.companyId = g.companyID"
						+ " where  c.cosStatus = '50' and s.staffName like ?";

				resultlist = baseBeanService.getListBeanBySqlAndParams(sql,
						new Object[] { "%" + querytxt + "%" });
			} else {
				String hql = "from DocumentTemplate where companyId = ? and fileShowName like ? ";
				resultlist = baseBeanService.getListBeanByHqlAndParams(hql,
						new Object[] { account.getCompanyID(),
								"%" + querytxt + "%" });
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resultlist", resultlist);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";
	}
	
	
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = "";
		List<BaseBean> list = null;

		if (searchType != null && searchType.equals("draft")) {
			if (search != null && search.equals("search")) {
				list = searchExcel(searchType);
			} else {
				hql = " from Document d where d.drafterID=? and d.status = ? and  d.subscriberID2 is null and d.module = ? and d.organizationID = ? and d.docId not in(select docId from DocDelRecord) order by draftTime desc";
				list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] { account.getStaffID(), "I",
										(String) session.get("module"),
										organizationID });
			}

		}
		if (searchType != null && searchType.equals("receiver")) {
			if (search != null && search.equals("search")) {
				list = searchExcel(searchType);
			} else {
				hql = "from Document d where ((d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?)or (d.status = ? and d.drafterID = ? and organizationID = ?))and d.module = ?  and d.docId not in(select docId from DocDelRecord) order by passTime desc";
				list = baseBeanService.getListBeanByHqlAndParams(hql,
						new Object[] { account.getStaffID(),
								(String) session.get("organizationID"), "R",
								account.getStaffID(),
								(String) session.get("organizationID"),
								(String) session.get("module") });
			}
		}
		if (searchType != null && searchType.equals("yessend")) {

			if (search != null && search.equals("search")) {
				list = searchExcel(searchType);
			} else {
				hql = "from Document d where ((d.drafterID=? and d.status!='I' and d.status!='D' and d.organizationID = ?) or (d.docId in (select p.docId from DocumentPass p where p.subscriber2 = ? and p.deptOfsub2 = ?))) and d.docId not in(select docId from DocDelRecord) and d.module = ?  order by updateTime desc";
				list = baseBeanService.getListBeanByHqlAndParams(hql,
						new Object[] { account.getStaffID(), organizationID,
								account.getStaffID(), organizationID,
								(String) session.get("module") });
			}
		}
		if (searchType != null && searchType.equals("share")) {
			if (search != null && search.equals("search")) {
				list = searchExcel(searchType);
			} else {
				String hql1 = "from Company where companyID = ?";
				Company company = (Company) baseBeanService
						.getBeanByHqlAndParams(hql1, new Object[] { account
								.getCompanyID() });

				hql = "from Document d where d.module = ? and d.docId not in(select docId from DocDelRecord where stage = ? and operator = ?) and docId in(select s.docId from DocumentShare s where (s.companyId = ? and s.shareType = ?) or (s.groupCompanySn = ? and s.shareType = ?))";

				list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] { (String) session.get("module"),
										"share", account.getStaffID(),
										account.getCompanyID(), "current",
										company.getGroupCompanySn(), "group" });
			}
		}
		if (searchType != null && searchType.equals("archive")) {
			if (search != null && search.equals("search")) {
				list = searchExcel(searchType);
			} else {
				String hqlcom = "from Company where companyID = ?";
				Company company = (Company) baseBeanService
						.getBeanByHqlAndParams(hqlcom, new Object[] { account
								.getCompanyID() });
				hql = "from Document d where d.groupCompanySn=? and d.status= ? and d.module = ? and d.docId not in(select docId from DocDelRecord) order by d.guidangTime desc";
				list = baseBeanService.getListBeanByHqlAndParams(hql,
						new Object[] { company.getGroupCompanySn(), "G",
								(String) session.get("module") });
			}
		}
		list = docCommonService.getFullListBean(list);
		String[] column1 = null;
		if(session.get("module").equals("contract")){
			column1 = Document.columnHeadings();
		}else{
			column1 = Document.columnHeadings1();
		}
		excelStream = excelService.showExcel(column1,
				list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出公文列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	
	public List<BaseBean> searchExcel(String searchType) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String hql = "";
		String module = (String) session.get("module");
		String organizationID = (String) session.get("organizationID");
		Object[] params = null;

		Date startDate = null;
		Date endDate = null;
		documentSearchInfo = (DocumentSearchInfo) session.get("tablesearch");
		if (documentSearchInfo.getSStart() != null
				&& !documentSearchInfo.getSStart().equals("")) {
			startDate = Utilities.getDateFromString(documentSearchInfo
					.getSStart(), "yyyy-MM-dd HH:mm:ss");
		}
		if (documentSearchInfo.getSEnd() != null
				&& !documentSearchInfo.getSEnd().equals("")) {
			endDate = Utilities.getDateFromString(documentSearchInfo.getSEnd(),
					"yyyy-MM-dd HH:mm:ss");
		}
		if (searchType.equals("draft")) {
			hql = " from Document d where d.drafterID=? and d.status = ? and  d.subscriberID2 is null and d.module = ? and d.organizationID = ? and d.docId not in(select docId from DocDelRecord) and d.docNum like ? and d.title like ? and docType like ? order by draftTime desc";
			params = new Object[] { account.getStaffID(), "I", module,
					organizationID,
					"%" + documentSearchInfo.getDocNum().trim() + "%",
					"%" + documentSearchInfo.getTitle().trim() + "%",
					"%" + documentSearchInfo.getDocType() + "%" };
		}

		if (searchType.equals("receiver")) {
			String hql1 = "and d.passTime between ? and ?";
			hql = "from Document d where ((d.subscriberID2 = ? and d.deptIDofSubscriber2 = ?)or (d.status = ? and d.drafterID = ? and organizationID = ?)) and d.module = ?  and d.docId not in(select docId from DocDelRecord) and d.docNum like ? and d.title like ? and d.fromMember like ?";
			if (startDate != null && !startDate.equals("")) {
				if (endDate == null || endDate.equals("")) {
					endDate = new Date();
				}
				params = new Object[] { account.getStaffID(), organizationID,
						"R", account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getFromMember().trim() + "%",
						startDate, endDate };
				hql = hql + hql1;
			} else {
				params = new Object[] { account.getStaffID(), organizationID,
						"R", account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getFromMember().trim() + "%" };
			}

		}

		if (searchType.equals("yessend")) {
			String hql1 = "and d.updateTime between ? and ?";
			hql = "from Document d where ((d.drafterID=? and d.status!='I' and d.status!='D' and d.organizationID = ?) or (d.docId in (select p.docId from DocumentPass p where p.subscriber2 = ? and p.deptOfsub2 = ?))) and d.docId not in(select docId from DocDelRecord) and d.module = ? and d.docNum like ? and d.title like ? and d.status like ?";
			if (startDate != null && !startDate.equals("")) {
				if (endDate == null || endDate.equals("")) {
					endDate = new Date();
				}
				params = new Object[] { account.getStaffID(), organizationID,
						account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getStatus().trim() + "%",
						startDate, endDate };
				hql = hql + hql1;
			} else {
				params = new Object[] { account.getStaffID(), organizationID,
						account.getStaffID(), organizationID, module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						"%" + documentSearchInfo.getStatus().trim() + "%" };
			}

		}

		if (searchType.equals("archive")) {
			String hql1 = " and d.guidangTime between ? and ?";
			hql = "from Document d where d.companyID=? and d.status= ? and d.module = ? and d.docId not in(select docId from DocDelRecord) and d.docNum like ? and d.title like ?";

			if (startDate != null && !startDate.equals("")) {
				if (endDate == null || endDate.equals("")) {
					endDate = new Date();
				}
				params = new Object[] { account.getCompanyID(), "G", module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%",
						startDate, endDate };
				hql = hql + hql1;
			} else {
				params = new Object[] { account.getCompanyID(), "G", module,
						"%" + documentSearchInfo.getDocNum().trim() + "%",
						"%" + documentSearchInfo.getTitle().trim() + "%" };
			}

		}

		if (searchType.equals("share")) {
			String hqlcom = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hqlcom, new Object[] { account.getCompanyID() });
			hql = "from Document d where d.module = ? and d.docId not in(select docId from DocDelRecord where stage = ? and operator = ?) and docId in(select s.docId from DocumentShare s where (s.companyId = ? and s.shareType = ?) or (s.groupCompanySn = ? and s.shareType = ?)) and d.docNum like ? and d.title like ?";
			params = new Object[] { (String) session.get("module"), "share",
					account.getStaffID(), account.getCompanyID(), "current",
					company.getGroupCompanySn(), "group",
					"%" + documentSearchInfo.getDocNum().trim() + "%",
					"%" + documentSearchInfo.getTitle().trim() + "%" };

		}
		
		

		List<BaseBean> list  = baseBeanService.getListBeanByHqlAndParams(hql, params);
		return list;

	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public DocumentFileAttach getDocFileAttach() {
		return docFileAttach;
	}

	public void setDocFileAttach(DocumentFileAttach docFileAttach) {
		this.docFileAttach = docFileAttach;
	}

	public String getRefreshJumpType() {
		return refreshJumpType;
	}

	public void setRefreshJumpType(String refreshJumpType) {
		this.refreshJumpType = refreshJumpType;
	}

	public DocumentTemplate getDocumentTemplate() {
		return documentTemplate;
	}

	public void setDocumentTemplate(DocumentTemplate documentTemplate) {
		this.documentTemplate = documentTemplate;
	}

	public List<DocumentAuditing> getDocAuditList() {
		return docAuditList;
	}

	public void setDocAuditList(List<DocumentAuditing> docAuditList) {
		this.docAuditList = docAuditList;
	}

	public DocumentAuditing getDocAudit() {
		return docAudit;
	}

	public void setDocAudit(DocumentAuditing docAudit) {
		this.docAudit = docAudit;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public DocumentSearchInfo getDocumentSearchInfo() {
		return documentSearchInfo;
	}

	public void setDocumentSearchInfo(DocumentSearchInfo documentSearchInfo) {
		this.documentSearchInfo = documentSearchInfo;
	}

	public String getGord() {
		return gord;
	}

	public void setGord(String gord) {
		this.gord = gord;
	}

	public InteractDocInfo getInteractDocInfo() {
		return interactDocInfo;
	}

	public void setInteractDocInfo(InteractDocInfo interactDocInfo) {
		this.interactDocInfo = interactDocInfo;
	}

	public SuggestionDoc getSuggestionDoc() {
		return suggestionDoc;
	}

	public void setSuggestionDoc(SuggestionDoc suggestionDoc) {
		this.suggestionDoc = suggestionDoc;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public List<BaseBean> getAttachlist() {
		return attachlist;
	}

	public void setAttachlist(List<BaseBean> attachlist) {
		this.attachlist = attachlist;
	}

	public DocumentShare getDocShare() {
		return docShare;
	}

	public void setDocShare(DocumentShare docShare) {
		this.docShare = docShare;
	}

	public SocialMemberInfo getSocialMemberInfo() {
		return socialMemberInfo;
	}

	public void setSocialMemberInfo(SocialMemberInfo socialMemberInfo) {
		this.socialMemberInfo = socialMemberInfo;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public String getFinishType() {
		return finishType;
	}

	public void setFinishType(String finishType) {
		this.finishType = finishType;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public List<BaseBean> getDoclist() {
		return doclist;
	}

	public void setDoclist(List<BaseBean> doclist) {
		this.doclist = doclist;
	}

	public String getVisiturl() {
		return visiturl;
	}

	public void setVisiturl(String visiturl) {
		this.visiturl = visiturl;
	}
	

}
