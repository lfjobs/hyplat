package hy.ea.office.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.Document.DocumentSearchInfo;
import hy.ea.bo.office.DocumentAuditing;
import hy.ea.bo.office.DocumentFileAttach;
import hy.ea.bo.office.vo.*;
import hy.ea.office.service.DocCommonService;
import hy.ea.office.service.DocumentFlowService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

@Controller
@Scope("prototype")
/**
 * 公文流转管理
 */
public class DocumentFlowAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private DocumentFlowService docFlowService;// 工作流相关的业务逻辑
	@Resource
	private DocCommonService docCommonService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	public InputStream excelStream;
	private DocumentSearchInfo documentSearchInfo;
	private Document document;
	private DocumentAuditing docAudit;
	private DocumentFileAttach docFileAttach;
	private PageForm pageForm;
	private String result;
	private String search;
	private String searchType;// 查询阶段：examine;seal,publish,read
	private String finishType;// 完成状态：未完成；已完成
	private String refreshJumpType;// 却分要查询哪个阶段的列表
	private int pageNumber;
	private String readers;
	private String type;// 区分收件箱操作；receiver 草稿箱操作draft以及用于查询公文
	private String jump;// 工作流跳转方向；比如，驳回，通过，盖章，等等。
	private String docId;
	private String comment;
	private String submitType;
	private String track;//跟踪表
	private List<BaseBean> doclist;
	private String visiturl;

	// ////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////*****************触发公文工作流程的流转********************///////////
	// ///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 发起公文流转流程
	 * 
	 */
	public String createDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from Document where docId=?";
		Document doc = null;

		try {
			String[] docIds = null;
			int length = 1;
			if (document != null && !document.getDocId().equals("")) {
				docIds = document.getDocId().split(",");
				length = docIds.length;
			}
			for (int i = 0; i < length; i++) {
				// 列表发送
				if (docId != null && !docId.equals("")) {

					doc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { docId });
					// 页面发送
				} else {
					if (submitType != null && submitType.equals("examine")) {
						doc = (Document) baseBeanService.getBeanByHqlAndParams(
								hql, new Object[] { docIds[i].trim() });
						doc.setReceiverID(document.getReceiverID());
						doc.setReceiverDeptID(document.getReceiverDeptID());
						doc.setReceiverCompanyID(document
								.getReceiverCompanyID());
					} else {
						doc = docCommonService.storeDocData(document,
								docFileAttach, account, session);
					}
				}

				if (doc.getStatus() != null
						&& doc.getStatus().equalsIgnoreCase("R")) { // 驳回时发送
					docFlowService.reSendDocument(doc, account);
				} else {
					docFlowService.createDocument(doc, account); // 第一次发送
				}
				// 添加对公文的操作记录
				String hqlstaff = "from Staff where staffID = ?";
//				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
//						hqlstaff, new Object[] { account.getStaffID() });
				
				Staff receiver = (Staff)baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{document.getReceiverID()});
				
				docCommonService.addTrackRecord(doc.getDocId(), account
						.getStaffID(), (String) session.get("organizationID"),
						account.getCompanyID(), "提交审批至" + receiver.getStaffName()
								+ "(" + receiver.getStaffCode() + ")");
				String content = "[未审批公文]下,请进行审批";
				docCommonService
						.sendPhoneMessage(doc.getReceiverID(), doc
								.getReceiverDeptID(),doc.getReceiverCompanyID(),account.getStaffID(),account.getCompanyID(),
								content, doc.getTitle(), (String) session
										.get("module"), "draft");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * 审批公文
	 * 
	 * @param :
	 *            reject 驳回，noapprove 不批准，approve 批准，transfer 转他人审批
	 * @return
	 */
	public String examineDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		if (comment != null && !comment.equals("")) {

			document.setSubscriberComment(staff.getStaffName() + "("
					+ staff.getStaffCode() + ")" + ":" + comment + ";");
		} else {
			if (document.getSubscriberComment() == null) {
				document.setSubscriberComment("");
			}
		}
		
		
		if(jump.equals("selftoSeal")){
			document.setReceiverID(account.getStaffID());
			document.setReceiverDeptID((String)session.get("organizationID"));
			document.setReceiverCompanyID(account.getCompanyID());
			jump = "approve";
		}
		
		
		try {
			int result = docFlowService.examineDocument(document, jump);
			String[] contents = new String[] { "驳回公文至拟稿人收件箱", "审批不通过",
					"审批通过转至盖章", "审批通过转至他人审批" };
			String content = "";
			if (result != 0 && result != 5) {
				content = contents[result - 1];
			}

			docCommonService.addTrackRecord(document.getDocId(), account
					.getStaffID(), (String) session.get("organizationID"),
					account.getCompanyID(), content);

			hql = "from Document where docId = ?";
			Document doc = (Document) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { document.getDocId() });
			String messagecontent = "";
			String type = "";
			String receiverID = "";
			String receiverDeptID = "";
			if (result == 1) {
				messagecontent = "[收件箱]下,公文被驳回";
				type = "reject";
				receiverID = doc.getDrafterID();
				receiverDeptID = doc.getOrganizationID();

			}
			if (result == 2) {
				messagecontent = "审批未通过";
				type = "noexamine";
				receiverID = doc.getDrafterID();
				receiverDeptID = doc.getOrganizationID();
			}
			if (result == 3) {
				messagecontent = "[未盖章公文]下,请进行盖章";
				type = "seal";
				receiverID = doc.getSealerID();
				receiverDeptID = doc.getDeptIDofSealer();
			}
			if (result == 4) {
				messagecontent = "[未审批公文]下,请进行审批";
				type = "examine";
				receiverID = doc.getSubscriberID();
				receiverDeptID = doc.getDeptIDofSubscriber();
			}

			docCommonService.sendPhoneMessage(receiverID, receiverDeptID,doc.getReceiverCompanyID(),
					account.getStaffID(), account.getCompanyID(),messagecontent, doc.getTitle(),
					(String) session.get("module"), type);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * 公文盖章
	 * 
	 * @param :
	 *            finish 完成，publish 出版
	 * @return
	 */
	public String sealDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		if (comment != null && !comment.equals("")) {
			document.setSealerComment(staff.getStaffName() + "("
					+ staff.getStaffCode() + ")" + ":" + comment + ";");
		} else {
			if (document.getSealerComment() == null) {
				document.setSealerComment("");
			}
		}
		try {
			int result = docFlowService.sealDocument(document, jump);
			String content = "";
			if (result == 1) {
				content = "暂存档";
			}
			if (result == 2) {
				content = "转至分发人";
				hql = "from Document where docId = ?";
				Document doc = (Document) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { document
								.getDocId() });
				String messagecontent = "[未发公文]下,请进行分发";
				docCommonService.sendPhoneMessage(doc.getReceiverID(), doc
						.getReceiverDeptID(),doc.getReceiverCompanyID(), account.getStaffID(),account.getCompanyID(),
						messagecontent, doc.getTitle(), (String) session
								.get("module"), "publish");

			}
			if (result == 3) {
				content = "转交盖章";
				hql = "from Document where docId = ?";
				Document doc = (Document) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { document
								.getDocId() });
				String messagecontent = "[未盖章]下,请进行盖章";
				docCommonService.sendPhoneMessage(doc.getReceiverID(), doc
						.getReceiverDeptID(),doc.getReceiverCompanyID(), account.getStaffID(),account.getCompanyID(),
						messagecontent, doc.getTitle(), (String) session
								.get("module"), "seal");

			}
			if (result == 4) {
				content = "驳回至审批人";
			}
			
			
			docCommonService.addTrackRecord(document.getDocId(), account
					.getStaffID(), (String) session.get("organizationID"),
					account.getCompanyID(), content);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 公文发行
	 * 
	 * @param发行对象的读者ID（STAFFID）列表，以|分割。
	 *
	 * @return
	 */
	public String publishDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		if (comment != null && !comment.equals("")) {
			document.setPublisherComment(staff.getStaffName() + "("
					+ staff.getStaffCode() + ")" + ":" + comment + ";");
		}
		try {
			String result = docFlowService.publishDocument(document, readers);
			if (result.equals("1")) {
				String content = "分发公文";
				docCommonService.addTrackRecord(document.getDocId(), account
						.getStaffID(), (String) session.get("organizationID"),
						account.getCompanyID(), content);
				hql = "from Document where docId = ?";
				Document doc = (Document) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { document
								.getDocId() });
				String messagecontent = "[未阅读]下，请进行查阅处理";
				for (String infos : readers.split(",")) {
					String[] info = infos.split("-");
					List<String> stafflist = new ArrayList<String>();
					if (!stafflist.contains(info[0])) {

						docCommonService.sendPhoneMessage(info[0], info[2],info[1],
								account.getStaffID(),account.getCompanyID(), messagecontent, doc
										.getTitle(), (String) session
										.get("module"), "read");
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 公文再发行
	 * 
	 * readers发行对象的读者ID（STAFFID）列表，以|分割。typeros:表示read转发还是publish分发
	 * @return
	 */
	public String rePublishDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String hql = "from Document where docId = ?";
		String sendResult = "";
		Document document = (Document) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { docId });
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
					hql, new Object[] { document.getDocId() });
			String messagecontent = "[未阅读]下，请进行查阅处理";
			for (String infos : readers.split(",")) {
				String[] info = infos.split("-");
				List<String> stafflist = new ArrayList<String>();
				if (!stafflist.contains(info[0])) {

					docCommonService.sendPhoneMessage(info[0], info[2], info[1],account
							.getStaffID(), account.getCompanyID(),messagecontent, doc.getTitle(),
							(String) session.get("module"), "read");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		String existedName = "";// 已被分发得人的姓名
		if (sendResult != "" && sendResult != "fail") {
			String hql2 = "from Staff where staffId = ?";
			for (String st : sendResult.split(",")) {
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { st });
				existedName += staff.getStaffName() + ",";
			}
			sendResult = existedName;
		}
		map.put("result", sendResult);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 读者完成阅读任务
	 * 
	 * @param
	 * @return
	 */
	public String completeRead() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String result = null;
		String content = null;
		if (docId != null && !docId.equals("")) {
			result = docFlowService.completeRead(docId, account.getStaffID());
			content = "点击阅读";

		} else {
			docId = document.getDocId();
			result = docFlowService.completeRead(docId, account.getStaffID());
			content = "标记为已读";
		}

		if (result.equals("1")) {
			docCommonService.addTrackRecord(docId, account.getStaffID(),
					(String) session.get("organizationID"), account
							.getCompanyID(), content);
		}

		return "success";
	}



	// ////////////////////////////////查询列表//////////////////////////////////////////////////////

	/**
	 * 检查登录用户未完成的公文个数
	 * 
	 *
	 *      任务类型(examine:审批任务。seal:盖章任务。publish:出版任务。read: 阅读任务)
	 */
	public String checkUnFinishedDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "";
		int count = 0;
		try {
			if (type.equals("examine")) {
				hql = "select count(*) from ViewUnexaminedoc where assignee = ? and module = ? and activityname = ? and deptidofsubscriber = ?";
				count = baseBeanService.getConutByByHqlAndParams(hql,
						new Object[] { account.getStaffID(),
								(String) session.get("module"),
								"Examine and Approve",
								(String) session.get("organizationID") });
			}
			if (type.equals("seal")) {
				hql = "select count(*) from ViewUnsealdoc where assignee = ? and module = ? and activityname = ? and deptidofsealer = ?";
				count = baseBeanService.getConutByByHqlAndParams(hql,
						new Object[] { account.getStaffID(),
								(String) session.get("module"), "Seal",
								(String) session.get("organizationID") });
			}
			if (type.equals("publish")) {
				hql = "select count(*) from ViewUnpublishdoc where assignee = ? and module = ? and activityname = ? and deptidofpublisher = ?";
				count = baseBeanService.getConutByByHqlAndParams(hql,
						new Object[] { account.getStaffID(),
								(String) session.get("module"), "Publish",
								(String) session.get("organizationID") });
			}
			if (type.equals("read")) {
				hql = "select count(*) from ViewUnreaddoc where readerid = ? and readstate = ? and module = ?  and readorganizationid = ?";
				count = baseBeanService.getConutByByHqlAndParams(hql,
						new Object[] { account.getStaffID(), "1",
								(String) session.get("module"),
								(String) session.get("organizationID") });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", count);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 检查登录用户未完成的公文个数
	 *
	 * type
	 *            任务类型(examine:审批任务。seal:盖章任务。publish:出版任务。read: 阅读任务)
	 */
	public String checkUnFinishedDocument2() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "";
		int count = 0;
		try {

			if(account.getCompanyID()!=null&&!account.getCompanyID().equals("")) {
				if (type.equals("examine")) {
					hql = "select count(*) from ViewUnexaminedoc where status!=? and assignee = ? and module = ? and activityname = ? and companyIDofSubscriber = ?";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{"y",account.getStaffID(),
									(String) session.get("module"),
									"Examine and Approve",
									account.getCompanyID()});
				}
				if (type.equals("seal")) {
					hql = "select count(*) from ViewUnsealdoc where status!=? and assignee = ? and module = ? and activityname = ? and companyIDofSealer = ?";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{"y",account.getStaffID(),
									(String) session.get("module"), "Seal",
									account.getCompanyID()});


				}
				if (type.equals("publish")) {
					hql = "select count(*) from ViewUnpublishdoc where status!=? and assignee = ? and module = ? and activityname = ? and companyIDofPublisher = ?";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{"y",account.getStaffID(),
									(String) session.get("module"), "Publish",
									account.getCompanyID()});
				}
				if (type.equals("read")) {
					hql = "select count(*) from ViewUnreaddoc where readerid = ? and readstate = ? and module = ?  and readcompanyid = ?";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{account.getStaffID(), "1",
									(String) session.get("module"),
									account.getCompanyID()});
				}
			}else{
				if (type.equals("examine")) {
					hql = "select count(*) from ViewUnexaminedoc where status!=? and  assignee = ? and activityname = ? and companyIDofSubscriber is null";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{"y",account.getStaffID(),
									"Examine and Approve"
									});
				}
				if (type.equals("seal")) {
					hql = "select count(*) from ViewUnsealdoc where status!=? and assignee = ? and activityname = ? and companyIDofSealer is null";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{"y",account.getStaffID(),
									 "Seal"
								});
					hql = "select count(*) from Document where drafterID = ? and scene = '00' and (status='K' or status = 'A')";
					int countf = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{account.getStaffID()});
					count = count+countf;
				}
				if (type.equals("publish")) {
					hql = "select count(*) from ViewUnpublishdoc where status!=? and  assignee = ? and  activityname = ? and companyIDofPublisher is null";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{"y",account.getStaffID(),
									 "Publish"
									});
				}
				if (type.equals("read")) {
					hql = "select count(*) from ViewUnreaddoc where readerid = ? and readstate = ? and  readcompanyid is null";
					count = baseBeanService.getConutByByHqlAndParams(hql,
							new Object[]{account.getStaffID(), "1"

								});
				}


			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", count);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}
	public String toSearchexamine() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentSearchInfo);
		return getExamineDocList();

	}

	private DetachedCriteria getListExamine(String finishType) {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = null;
		if (finishType.equals("examine")) {
			dc = DetachedCriteria.forClass(ViewUnexaminedoc.class);
			dc.add(Restrictions.eq("activityname", "Examine and Approve"));

			dc.addOrder(Order.desc("updatetime"));
		} else {
			dc = DetachedCriteria.forClass(ViewExaminedoc.class);
			dc.addOrder(Order.desc("examinetime"));
		}
		dc.add(Restrictions.eq("assignee", account.getStaffID()));
		dc.add(Restrictions.eq("module", (String) session.get("module")));
		dc.add(Restrictions.eq("deptidofsubscriber", (String) session
				.get("organizationID")));

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
			Date startDate = null;
			Date endDate = null;
			if (!"".equals(documentSearchInfo.getFromMember())) {
				dc.add(Restrictions.like("frommember", documentSearchInfo
						.getFromMember(), MatchMode.ANYWHERE));
			}
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
		   if (startDate!=null) {
			   if(endDate==null){
				   endDate = new Date();
			   }
			   if (finishType.equals("examine")) {
				dc.add(Restrictions.between("updatetime", startDate, endDate));
			   }else{
				   dc.add(Restrictions.between("examinetime", startDate, endDate));  
			   }
			}
		  }

		return dc;
	}

	/**
	 * 
	 * 未审批公文
	 * 
	 * @return
	 */
	public String getExamineDocList() {
	
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {
			return "not_login";
		}
		
		if(track!=null&&track.equals("10")){
			doclist = baseBeanService.getListByDC(getListExamine(finishType));
			
		}else{
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListExamine(finishType));
		pageForm = docCommonService.getPostName(pageForm, finishType);
		}

		//跟踪列表
				if(track!=null&&track.equals("00")){
					visiturl = "/ea/documentflow/ea_getExamineDocList.jspa";
					return "track";
				}
				//跟踪打印列表
				if(track!=null&&track.equals("10")){
					return "trackprint";
				}
			
		return finishType;
	}

	public String toSearchseal() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentSearchInfo);
		return getSealDocList();

	}

	private DetachedCriteria getListSeal(String finishType) {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = null;
		if (finishType.equals("seal")) {
			dc = DetachedCriteria.forClass(ViewUnsealdoc.class);
			dc.add(Restrictions.eq("activityname", "Seal"));
			dc.addOrder(Order.desc("subscribetime"));
		} else {
			dc = DetachedCriteria.forClass(ViewSealdoc.class);
			dc.addOrder(Order.desc("sealtime"));
		}
		dc.add(Restrictions.eq("assignee", account.getStaffID()));
		dc.add(Restrictions.eq("module", (String) session.get("module")));
		dc.add(Restrictions.eq("deptidofsealer", (String) session
				.get("organizationID")));

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
			
			Date startDate = null;
			Date endDate = null;
			if (!"".equals(documentSearchInfo.getFromMember())) {
				dc.add(Restrictions.like("frommember", documentSearchInfo
						.getFromMember(), MatchMode.ANYWHERE));
			}
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
		   if (startDate!=null) {
			   if(endDate==null){
				   endDate = new Date();
			   }
			   if (finishType.equals("seal")) {
				dc.add(Restrictions.between("subscribetime", startDate, endDate));
			   }else{
				   dc.add(Restrictions.between("sealtime", startDate, endDate));  
			   }
			}
			
		}

		return dc;
	}

	/**
	 * 
	 * 未盖章公文
	 * 
	 * @return
	 */
	public String getSealDocList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {
			return "not_login";
		}
		
		if(track!=null&&track.equals("10")){
			doclist = baseBeanService.getListByDC(getListSeal(finishType));
			
		}else{
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListSeal(finishType));
		pageForm = docCommonService.getPostName(pageForm, finishType);
		}
		
		//跟踪列表
		if(track!=null&&track.equals("00")){
			visiturl = "/ea/documentflow/ea_getSealDocList.jspa";
			return "track";
		}
		//跟踪打印列表
		if(track!=null&&track.equals("10")){
			return "trackprint";
		}

		return finishType;
	}

	public String toSearchpublish() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentSearchInfo);
		return getPublishDocList();

	}

	private DetachedCriteria getListPublish(String finishType) {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = null;

		if (finishType.equals("publish")) {
			dc = DetachedCriteria.forClass(ViewUnpublishdoc.class);

			dc.add(Restrictions.eq("activityname", "Publish"));
			dc.addOrder(Order.desc("sealtime"));
		} else {
			dc = DetachedCriteria.forClass(ViewPublishdoc.class);
			dc.addOrder(Order.desc("publishtime"));
		}
		dc.add(Restrictions.eq("assignee", account.getStaffID()));
		dc.add(Restrictions.eq("module", (String) session.get("module")));
		dc.add(Restrictions.eq("deptidofpublisher", (String) session
				.get("organizationID")));
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
			
			Date startDate = null;
			Date endDate = null;
			if (!"".equals(documentSearchInfo.getFromMember())) {
				dc.add(Restrictions.like("frommember", documentSearchInfo
						.getFromMember(), MatchMode.ANYWHERE));
			}
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
		   if (startDate!=null) {
			   if(endDate==null){
				   endDate = new Date();
			   }
			   if (finishType.equals("publish")) {
				dc.add(Restrictions.between("sealtime", startDate, endDate));
			   }else{
				   dc.add(Restrictions.between("publishtime", startDate, endDate));  
			   }
			}
		}

		return dc;
	}

	/**
	 * 
	 * 未分发公文
	 * 
	 * @return
	 */
	public String getPublishDocList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {
			return "not_login";
		}
		if(track!=null&&track.equals("10")){
			doclist = baseBeanService.getListByDC(getListPublish(finishType));
			
		}else{
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListPublish(finishType));
		pageForm = docCommonService.getPostName(pageForm, finishType);
		}
		
		//跟踪列表
		if(track!=null&&track.equals("00")){
			visiturl = "/ea/documentflow/ea_getPublishDocList.jspa";
			return "track";
		}
		//跟踪打印列表
		if(track!=null&&track.equals("10")){
			return "trackprint";
		}

		return finishType;
	}

	public String toSearchread() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", documentSearchInfo);
		return getReadDocList();

	}

	private DetachedCriteria getListRead(String finishType) {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(ViewUnreaddoc.class);
		dc.add(Restrictions.eq("readerid", account.getStaffID()));
		dc.add(Restrictions.eq("module", (String) session.get("module")));
		dc.add(Restrictions.eq("readorganizationid", (String) session
				.get("organizationID")));
		if (finishType.equals("read")) {
			dc.add(Restrictions.eq("readstate", "1"));
			dc.addOrder(Order.desc("recivetime"));
		} else {
			dc.add(Restrictions.eq("readstate", "0"));
			dc.addOrder(Order.desc("readtime"));
			dc.add(Restrictions.isNull("delstate"));
		}

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
			
			Date startDate = null;
			Date endDate = null;
			if (!"".equals(documentSearchInfo.getFromMember())) {
				dc.add(Restrictions.like("frommember", documentSearchInfo
						.getFromMember(), MatchMode.ANYWHERE));
			}
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
		   if (startDate!=null) {
			   if(endDate==null){
				   endDate = new Date();
				   documentSearchInfo.setSEnd(Utilities.getDateString(endDate,"yyyy-MM-dd HH:mm:ss"));
			   }
			   if (finishType.equals("read")) {
				dc.add(Restrictions.between("recivetime", documentSearchInfo.getSStart(), documentSearchInfo.getSEnd()));
			   }else{
				   dc.add(Restrictions.between("readtime", startDate, endDate));  
			   }
			}
		}

		return dc;
	}

	/**
	 * 
	 * 未盖章公文
	 * 
	 * @return
	 */
	public String getReadDocList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {
			return "not_login";
		}
		if(track!=null&&track.equals("10")){
			doclist = baseBeanService.getListByDC(getListRead(finishType));
			
		}else{
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber),
				getListRead(finishType));
		pageForm = docCommonService.getPostName(pageForm, "read");
		}
		
		//跟踪列表
		if(track!=null&&track.equals("00")){
			visiturl = "/ea/documentflow/ea_getReadDocList.jspa";
			return "track";
		}
		//跟踪打印列表
		if(track!=null&&track.equals("10")){
			return "trackprint";
		}
		return finishType;
	}
	
	
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = null;
		String[] column1 = null;
		if(finishType.equals("examine")||finishType.equals("examineyes")){
			dc = getListExamine(finishType);
		    if(finishType.equals("examine")){
		    	if(session.get("module").equals("contract")){
		    		column1 = ViewUnexaminedoc.columnHeadings();
		    	}else{
			        column1 = ViewUnexaminedoc.columnHeadings1();
		    	}
		    }else{
		    	if(session.get("module").equals("contract")){
		    	  column1 = ViewExaminedoc.columnHeadings();
		    	}else{
		    		column1 = ViewExaminedoc.columnHeadings1();
		    	}
		    }
		}
		
		if(finishType.equals("seal")||finishType.equals("sealyes")){
			dc = getListSeal(finishType);
		    if(finishType.equals("seal")){
		    	if(session.get("module").equals("contract")){
			        column1 = ViewUnsealdoc.columnHeadings();
		    	}else{
		    	    column1 = ViewUnsealdoc.columnHeadings1();
		    	}
		    }else{
		    	if(session.get("module").equals("contract")){
		    	   column1 = ViewSealdoc.columnHeadings();
		    	}else{
		    	   column1 = ViewSealdoc.columnHeadings1();
		    	}
		    }
		}
		if(finishType.equals("publish")||finishType.equals("publishyes")){
			dc = getListPublish(finishType);
		    if(finishType.equals("publish")){
		    	if(session.get("module").equals("contract")){
			      column1 = ViewUnpublishdoc.columnHeadings();
		    	}else{
		    		column1 = ViewUnpublishdoc.columnHeadings1();	
		    	}
		    }else{
		    	if(session.get("module").equals("contract")){
		    	  column1 = ViewPublishdoc.columnHeadings();
		    	}else{
		    		column1 = ViewPublishdoc.columnHeadings1();
		    	}
		    }
		}
		
		if(finishType.equals("read")||finishType.equals("readyes")){
			dc = getListRead(finishType);
			if(session.get("module").equals("contract")){
		    	column1 = ViewUnreaddoc.columnHeadings();
			}else{
				column1 = ViewUnreaddoc.columnHeadings1();
			}
		}
		excelStream = excelService.showExcel(column1,
				baseBeanService.getListByDC(dc));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出公文列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
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

	public DocumentAuditing getDocAudit() {
		return docAudit;
	}

	public void setDocAudit(DocumentAuditing docAudit) {
		this.docAudit = docAudit;
	}

	public DocumentSearchInfo getDocumentSearchInfo() {
		return documentSearchInfo;
	}

	public void setDocumentSearchInfo(DocumentSearchInfo documentSearchInfo) {
		this.documentSearchInfo = documentSearchInfo;
	}

	public String getReaders() {
		return readers;
	}

	public void setReaders(String readers) {
		this.readers = readers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJump() {
		return jump;
	}

	public void setJump(String jump) {
		this.jump = jump;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFinishType() {
		return finishType;
	}

	public void setFinishType(String finishType) {
		this.finishType = finishType;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
