package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.ComplaintDealer;
import hy.ea.bo.office.DocComplaint;
import hy.ea.bo.office.DocUser;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentFileAttach;
import hy.ea.office.service.ExtralFlowService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 公文流转管理
 */
public class ExtralFlowAction {
	private static final Logger logger = LoggerFactory.getLogger(ExtralFlowAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ExtralFlowService extralFlowService;
	PageForm pageForm;
	int pageNumber;
	private String search;
	private String global;
	private String result;
	private String jump;
	private Staff searchCStaff;

	private DocUser docUser;
	private DocComplaint docComplaint;
	private Document document;

	/**
	 * 远程注册用户
	 * 
	 * @return
	 */

	public String registerAccount() {
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		docUser.setId(serverService.getServerID("docuser"));
		docUser.setUserName(docUser.getUserName());
		docUser.setPassword(docUser.getPassword());
		docUser.setTelphone(docUser.getTelphone());
		docUser.setEmail(docUser.getEmail());
		docUser.setSex(docUser.getSex());
		docUser.setWeb(docUser.getWeb());
		docUser.setRealName(docUser.getRealName());
		baseBeanService.save(docUser);
		map.put("result", "success");

		JSONObject json = JSONObject.fromObject(map);

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			logger.error("操作异常", e);
		}

		return null;
	}

	/**
	 * 
	 * 提交投诉
	 * 
	 * @return
	 */
	public String submitComplaint() {
		HttpServletResponse response = ServletActionContext.getResponse();
		Document doc = new Document();
		String docId = serverService.getServerID("docid");
		doc.setDocId(docId);
		doc.setCompanyID(docComplaint.getComplaintCompany());

		String hql = "from ComplaintDealer where companyID = ?";
		ComplaintDealer cd = (ComplaintDealer) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { docComplaint
						.getComplaintCompany() });
		if (cd != null) {
			doc.setDrafterID(cd.getDealerID());
		} else {
			doc.setDrafterID("cstaff20110712KAX2RHUQZI0000025385");
		}
		doc.setStatus("I");
		Date curtime = new Date();
		doc.setCreateTime(curtime);
		doc.setDraftTime(curtime);
		doc.setUpdateTime(curtime);
		doc.setModule("webcomplaint");
		baseBeanService.save(doc);

		docComplaint.setId(serverService.getServerID("com"));
		docComplaint.setDocId(docId);
		docComplaint.setCode(getCode(docComplaint.getComplaintCompany()));
		if (cd != null) {
			docComplaint.setResponsibilitor(cd.getDealerID());
		} else {
			docComplaint
					.setResponsibilitor("cstaff20110712KAX2RHUQZI0000025385");
		}
		docComplaint.setComplaintTime(curtime);
		docComplaint.setDocPath(docComplaint.getDocPath().replace("\\", "/"));
		docComplaint.setStatusPic("images/complaint/waitdeal.png");
		docComplaint.setStatus("waitdeal");
		docComplaint.setDelstatus("00");
		baseBeanService.save(docComplaint);

		hql = "from Document where docId = ?";
		Document oldDoc = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });
		DocumentFileAttach attach = new DocumentFileAttach();
		attach.setFileId(serverService.getServerID("com"));
		attach.setExt("doc");
		attach.setFileType("W");
		attach.setFilePath(docComplaint.getDocPath().replace("\\", "/"));
		attach.setDocument(oldDoc);
		baseBeanService.save(attach);
		String telNumber = "";
		if (docComplaint.getTelphone() != null
				&& !docComplaint.getTelphone().equals("")) {
			telNumber = docComplaint.getTelphone();
			String rels = extralFlowService.sendMessage(null, telNumber, "g");
			logger.info("值：{}", rels);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		JSONObject json = JSONObject.fromObject(map);

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
		return null;

	}

	public String toSearchPrepare() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", docComplaint);
		return getComplaintList();
	}

	public PageForm toSearch(String staffID, String type) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		docComplaint = (DocComplaint) session.get("tablesearch");
		String hql = " from DocComplaint dc where dc.docId in(select docId from Document) "
				+ "and dc.responsibilitor = ? and dc.code like ?   and dc.delstatus = '00'";
		String hql2 = " and dc.complaintTime > ? and dc.complaintTime < ?";
		String hql3 = " and dc.status like ? and dc.status !='dealed' order by dc.complaintTime desc";

		Object[] params = new Object[] { staffID,
				"%" + docComplaint.getCode() + "%",
				"%" + docComplaint.getStatus() + "%" };
		if (type.equals("dealed")) {
			hql3 = " and dc.status ='dealed' order by dc.complaintTime desc";
			params = new Object[] { staffID, "%" + docComplaint.getCode() + "%" };
			if (docComplaint.getStartDate() != null
					&& !docComplaint.getStartDate().equals("")) {
				Date startTime = Utilities.getDateFromString(docComplaint
						.getStartDate(), "yyyy-MM-dd");
				Date endTime = Utilities.getDateFromString(docComplaint
						.getEndDate(), "yyyy-MM-dd");
				hql = hql + hql2 + hql3;
				params = new Object[] { staffID,
						"%" + docComplaint.getCode() + "%", startTime, endTime };

			} else {
				hql = hql + hql3;
			}

		} else {
			if (docComplaint.getStartDate() != null
					&& !docComplaint.getStartDate().equals("")) {
				Date startTime = Utilities.getDateFromString(docComplaint
						.getStartDate(), "yyyy-MM-dd");
				Date endTime = Utilities.getDateFromString(docComplaint
						.getEndDate(), "yyyy-MM-dd");
				hql = hql + hql2 + hql3;
				params = new Object[] { staffID,
						"%" + docComplaint.getCode() + "%", startTime, endTime,
						"%" + docComplaint.getStatus() + "%" };

			} else {
				hql = hql + hql3;
			}
		}

		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber), hql,
				params);
		return pageForm;
	}

	/**
	 * 获取意见箱，和已发送意见
	 * 
	 */

	public String getComplaintList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		if (type == null) {
			type = global;
		}
		String hql = null;
		if (search != null && search.equals("search")) {
			pageForm = toSearch(account.getStaffID(), type);
		} else {

			hql = " from DocComplaint dc where dc.docId in(select docId from Document) and dc.responsibilitor = ? and complaintCompany = ? and dc.delstatus = '00' and dc.status!='dealed' order by dc.complaintTime desc";
			if (type.equals("dealed")) {
				hql = " from DocComplaint dc where dc.docId in(select docId from Document) and dc.responsibilitor = ? and complaintCompany = ? and dc.delstatus = '00' and dc.status='dealed' order by dc.complaintTime desc";
			}

			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, new Object[] { account.getStaffID(),
							account.getCompanyID() });
		}

		hql = "from DocUser where userName = ?";
		DocUser du = null;
		if (pageForm != null) {
			for (BaseBean b : pageForm.getList()) {
				DocComplaint dc = (DocComplaint) b;
				if (dc.getUserName() != null && !dc.getUserName().equals("")) {
					du = (DocUser) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { dc.getUserName() });
					dc.setRealName(du.getRealName());
				}
			}
		}
		return type;

	}

	/**
	 * 
	 * 获取编号
	 * 
	 * @param companyID
	 * @return
	 */
	public String getCode(String companyID) {
		String hql = "from DocComplaint where complaintCompany = ?";
		String sql = "select max(to_number(code)) from DT_OA_DOCCOMPLAINT where complaintCompany= ?";
		int nextnum = 0;
		List<BaseBean> list = (List<BaseBean>) baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] { companyID });

		if (list.size() == 0) {
			return "1";
		} else {
			int a = baseBeanService.getConutByBySqlAndParams(sql,
					new Object[] { companyID });
			nextnum = a + 1;

		}
		return nextnum + "";

	}

	/**
	 * 
	 * 回复网站
	 * 
	 * @return
	 */
	public String toReply() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}

		String hql = "from DocComplaint where Id = ?";
		DocComplaint dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { docComplaint.getId() });
		dc.setReply(docComplaint.getReply());
		baseBeanService.update(dc);
		if (dc.getTelphone() != null
				&& !dc.getTelphone().equals("")) {
			extralFlowService.sendMessage(null,dc.getTelphone(), "reply");
		}
		
		return "success";
	}

	/**
	 * 
	 * 设置已处理
	 * 
	 * @return
	 */
	public String setDealed() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}

		String hql = "from DocComplaint where Id = ?";
		DocComplaint dc = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] Ids = request.getParameter("Ids").split(",");
		String resultstr = "";
		for (int i = 0; i < Ids.length; i++) {
			dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { Ids[i] });
			if (!dc.getStatus().equals("auditing")
					&& !dc.getStatus().equals("seal")) {
				dc.setStatusPic("images/complaint/dealed.png");
				dc.setStatus("dealed");
				baseBeanService.update(dc);
			} else {
				resultstr += dc.getCode() + ",";
			}

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", resultstr);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}

	/**
	 * 删除投诉
	 * 
	 * @return
	 */
	public String deleteComplaint() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String[] Ids = request.getParameter("Ids").split(",");
			String hql = "from DocComplaint where Id = ?";
			DocComplaint dc = null;
			String resultstr = "";
			for (int i = 0; i < Ids.length; i++) {
				dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { Ids[i] });
				if (dc.getStatus().equals("auditing")
						|| dc.getStatus().equals("seal")) {
					resultstr += dc.getCode() + ",";
				} else {
					dc.setDelstatus("01");
					baseBeanService.update(dc);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", resultstr);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 
	 * 至领导审批
	 * 
	 * @return
	 */
	public String createDoc() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String resultstr = "";
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String comSubscriber = request.getParameter("comSubscriber");
			String deptSubscriber = request.getParameter("deptSubscriber");
			String subscriberID = request.getParameter("subscriberID");
			String[] Ids = request.getParameter("Ids").split(",");
			String hql = "from DocComplaint where Id = ?";
			String hql1 = "from Document where docId = ?";
			DocComplaint dc = null;
			Document doc = null;

			for (int i = 0; i < Ids.length; i++) {
				dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { Ids[i] });
				if (dc.getStatus().equals("auditing")
						|| dc.getStatus().equals("pass")
						|| dc.getStatus().equals("reject")
						|| dc.getStatus().equals("dealed")
						|| dc.getStatus().equals("seal")) {

					resultstr += dc.getCode() + ",";

				} else {
					doc = (Document) baseBeanService.getBeanByHqlAndParams(
							hql1, new Object[] { dc.getDocId() });
					doc.setCompanyIDofSubscriber(comSubscriber);
					doc.setDeptIDofSubscriber(deptSubscriber);
					doc.setSubscriberID(subscriberID);
					extralFlowService.createDocument(doc, account);

					dc.setStatus("auditing");
					dc.setStatusPic("images/complaint/auditing.png");
					dc.setAuditTime(new Date());
					baseBeanService.update(dc);
					String hql2 = "from Staff where staffID = ?";
					Staff receive = (Staff) baseBeanService
							.getBeanByHqlAndParams(hql2,
									new Object[] { subscriberID });
					if (receive.getReference() != null
							&& !receive.getReference().equals("")) {
						extralFlowService.sendMessage(account.getStaffID(),
								receive.getReference(), "examine");
					}
				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", resultstr);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

		return "success";
	}

	/**
	 * 审批公文
	 * 
	 * @param jump:
	 *            reject 驳回，seal，transfer 转他人审批
	 * @return
	 */
	public String examineDocument() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String[] Ids = docComplaint.getId().split(",");
		String hql = "from DocComplaint where Id = ?";
		String hql1 = "from Document where docId = ?";
		DocComplaint dc = null;
		Document doc = null;
		String hql2 = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { account.getStaffID() });

		for (int i = 0; i < Ids.length; i++) {
			dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { Ids[i] });
			String sug = dc.getSuggestion();
			if (sug == null) {
				sug = "";
			}
			dc.setSuggestion(sug + staff.getStaffName() + "："
					+ docComplaint.getSuggestion() + ";");
			doc = (Document) baseBeanService.getBeanByHqlAndParams(hql1,
					new Object[] { dc.getDocId() });
			if (jump.equals("seal")) {
				doc.setCompanyIDofSealer(document.getCompanyIDofSealer());
				doc.setDeptIDofSealer(document.getDeptIDofSealer());
				doc.setSealerID(document.getSealerID());
			}
			if (jump.equals("transfer")) {
				doc.setCompanyIDofSubscriber2(document.getCompanyIDofSealer());
				doc.setDeptIDofSubscriber2(document.getDeptIDofSealer());
				doc.setSubscriberID2(document.getSealerID());
			}

			try {
				extralFlowService.examineDocument(doc, dc, account, jump);
				Staff receive = null;
				String typemessage = null;
				Object[] params = null;
				if (jump.equals("reject")) {
					params = new Object[] { doc.getDrafterID() };
					typemessage = "b";

				} else if (jump.equals("seal")) {
					params = new Object[] { doc.getSealerID() };
					typemessage = "seal";
				}else if(jump.equals("dealer")){
					params = new Object[] { doc.getDrafterID()};
					typemessage = "dealer";
					
				}else {
					params = new Object[] { doc.getSubscriberID2() };
					typemessage = "examine";
				}
				receive = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
						params);
				if (receive.getReference() != null
						&& !receive.getReference().equals("")) {
					extralFlowService.sendMessage(account.getStaffID(), receive
							.getReference(), typemessage);
				}
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
		}
		return "success";
	}

	/**
	 * 公文盖章
	 * 
	 * 
	 * 
	 * @return
	 */
	public String sealDocument() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String[] Ids = docComplaint.getId().split(",");
		String hql = "from DocComplaint where Id = ?";
		String hql1 = "from Document where docId = ?";
		DocComplaint dc = null;
		Document doc = null;

		for (int i = 0; i < Ids.length; i++) {
			dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { Ids[i] });
			doc = (Document) baseBeanService.getBeanByHqlAndParams(hql1,
					new Object[] { dc.getDocId() });

			try {
				extralFlowService.sealDocument(doc, dc, account);
				String hql2 = "from Staff where staffID = ?";
				Staff receive = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { doc.getDrafterID() });
				if (receive.getReference() != null
						&& !receive.getReference().equals("")) {
					extralFlowService.sendMessage(account.getStaffID(), receive
							.getReference(), "sealed");
				}
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
		}
		return "success";
	}

	/**
	 * 获取登录用户未完成的公文列表
	 * 
	 * @param type
	 *            任务类型(examine:审批任务。seal:盖章任务
	 */
	public String getUnfinishedList() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		if (type == null) {
			type = global;
		}
		List<BaseBean> list = extralFlowService.findUnFinishedDocument(account
				.getStaffID(), type, "webcomplaint");
		String hql = "from DocUser where userName = ?";
		String hql1 = "from DocComplaint where docId = ?";
		DocUser du = null;
		DocComplaint dc = null;
		List<BaseBean> complaintlist = new ArrayList<BaseBean>();
		if (list.size() != 0) {
			for (BaseBean b : list) {
				Document doc = (Document) b;
				dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(hql1,
						new Object[] { doc.getDocId() });
				if (dc.getUserName() != null && !dc.getUserName().equals("")) {
					du = (DocUser) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { dc.getUserName() });
					dc.setRealName(du.getRealName());
				}

				complaintlist.add(dc);
			}

		}
		if (complaintlist.size() != 0) {
			if (search != null && search.equals("search")) {
				complaintlist = finishSearch(complaintlist);
			}
		}
		pageForm = extralFlowService.getPageForm(complaintlist,
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber));

		return type;
	}

	public String unfinishSearchPrepare() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", docComplaint);

		return getUnfinishedList();
	}

	public String finishSearchPrepare() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", docComplaint);

		return getfinishedList();
	}

	public List<BaseBean> finishSearch(List<BaseBean> list) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		docComplaint = (DocComplaint) session.get("tablesearch");
		List<BaseBean> newlist = new ArrayList<BaseBean>();
		Date startTime = null;
		Date endTime = null;
		for (BaseBean b : list) {
			DocComplaint dc = (DocComplaint) b;

			if (!docComplaint.getStartDate().equals("")) {
				startTime = Utilities.getDateFromString(docComplaint
						.getStartDate(), "yyyy-MM-dd");
				endTime = Utilities.getDateFromString(
						docComplaint.getEndDate(), "yyyy-MM-dd");
			}
			if (!docComplaint.getStartDate().equals("")
					&& docComplaint.getCode() != null
					&& !docComplaint.getCode().equals("")) {

				if (dc.getCode().startsWith(docComplaint.getCode())
						&& dc.getAuditTime().after(startTime)
						&& dc.getAuditTime().before(endTime)) {

					newlist.add(b);

				}
			} else if (docComplaint.getCode() != null
					&& !docComplaint.getCode().equals("")) {
				if (dc.getCode().startsWith(docComplaint.getCode())) {
					newlist.add(b);

				}
			} else if (!docComplaint.getStartDate().equals("")) {
				if (dc.getAuditTime().after(startTime)
						&& dc.getAuditTime().before(endTime)) {

					newlist.add(b);

				}

			} else {
				newlist.add(b);
			}
		}

		return newlist;
	}

	/**
	 * 获取该登录人员已经完成的公文
	 * 
	 * @param type
	 *            任务类型(examine:审批任务。seal:盖章任务)
	 * @return
	 * 
	 */
	public String getfinishedList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		if (type == null) {
			type = global;
		}
		List<BaseBean> list = extralFlowService.findFinishedDocument(account
				.getStaffID(), type, "webcomplaint");
		String hql = "from DocUser where userName = ?";
		String hql1 = "from DocComplaint where docId = ?";
		DocUser du = null;
		DocComplaint dc = null;
		List<BaseBean> complaintlist = new ArrayList<BaseBean>();
		if (list.size() != 0) {
			for (BaseBean b : list) {
				Document doc = (Document) b;
				dc = (DocComplaint) baseBeanService.getBeanByHqlAndParams(hql1,
						new Object[] { doc.getDocId() });
				if (dc != null) {
					if (dc.getUserName() != null
							&& !dc.getUserName().equals("")) {
						du = (DocUser) baseBeanService.getBeanByHqlAndParams(
								hql, new Object[] { dc.getUserName() });
						dc.setRealName(du.getRealName());
					}
				}

				complaintlist.add(dc);
			}
		}
		if (complaintlist.size() != 0) {
			if (search != null && search.equals("search")) {
				complaintlist = finishSearch(complaintlist);
			}
		}
		pageForm = extralFlowService.getPageForm(complaintlist,
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber));

		if (type.equals("examine")) {
			return "completeaudit";
		}
		return "completeseal";

	}

	/**
	 * 检查登录用户未完成的公文个数
	 * 
	 * @param type
	 *            任务类型(examine:审批任务。seal:盖章任务)
	 */
	public String checkUnFinishedDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		List<BaseBean> docList = extralFlowService.findUnFinishedDocument(
				account.getStaffID(), type, "webcomplaint");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", docList.size());
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 
	 * 获取投诉处理箱数据的个数
	 * 
	 * @return
	 */
	public String checkDealComplaint() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}

		String hql = " select count(*)from DocComplaint dc where dc.docId in(select docId from Document) and dc.responsibilitor = ? and complaintCompany = ? and dc.delstatus = '00' and dc.status!='dealed'";

		int count = baseBeanService.getConutByByHqlAndParams(hql, new Object[] {
				account.getStaffID(), account.getCompanyID() });

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", count);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 
	 * 获取设置信息
	 * 
	 * @return
	 */
	public String getSetInfo() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = "from ComplaintDealer where companyID = ?";
			ComplaintDealer cd = (ComplaintDealer) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { account
							.getCompanyID() });
			if (cd != null) {
				map.put("result", cd);
			} else {
				map.put("result", "cstaff20110712KAX2RHUQZI0000025385");
			}

			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 
	 * 重新设置权限
	 */
	public String transferPermission() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			if (account == null) {

				return "not_login";
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			String oldDealerID = null;
			String dealerID = request.getParameter("dealerID");
			String dealerName = request.getParameter("dealerName");
			String check = request.getParameter("check");
			String hql = "from ComplaintDealer where companyID = ?";
			ComplaintDealer cd = (ComplaintDealer) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { account
							.getCompanyID() });
			if (cd != null) {
				oldDealerID = cd.getDealerID();
				cd.setDealerID(dealerID);
				cd.setDealerName(dealerName);
				cd.setUpdateTime(new Date());
				baseBeanService.update(cd);
			} else {
				oldDealerID = "cstaff20110712KAX2RHUQZI0000025385";
				ComplaintDealer newcd = new ComplaintDealer();
				newcd.setId(serverService.getServerID("cd"));
				newcd.setDealerID(dealerID);
				newcd.setDealerName(dealerName);
				newcd.setCompanyID(account.getCompanyID());
				newcd.setCreateTime(new Date());
				baseBeanService.save(newcd);
			}

			if (check.equals("yes")) {
				extralFlowService.transferData(dealerID, oldDealerID, account
						.getCompanyID());
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "suc");
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 显示设置模块，以便设置权限
	 * 
	 * @return
	 */
	public String showSetInfo() {
		return "settings";
	}

	/*
	 * 获得当期公司正式员工列表
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffformalList();
	}

	public String getStaffformalList() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			// session.put("session_value", Math.random() + "");//
			// 在不使用token的情况下,手动防止重复提交
			CAccount account = (CAccount) session.get("account");
			if (search != null && search.equals("search")) {
				searchStaff(account.getCompanyID());
				return "formallist";
			}

			String hql = "from Staff s where staffID in (select staffID from COS c where c.staffID=s.staffID and cosStatus = ? and companyID = ?)";
			Object[] params = { "50", account.getCompanyID() };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hql, params);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "formallist";
	}

	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private void searchStaff(String companyID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and cosStatus = ? and companyID = ?) and ";
		String hql2 = " staffCode like ? and staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		hql = hql1 + hql2;
		try {
			Object[] params = { "50", companyID,
					"%" + searchCStaff.getStaffCode() + "%",
					"%" + searchCStaff.getStaffName() + "%",
					"%" + searchCStaff.getStaffIdentityCard() + "%" };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hql, params);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
	}

	/**
	 * 
	 * 当初始化状态时，提示设置处理人
	 * 
	 * @return
	 */
	public String noticeSetDealer() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from ComplaintDealer where companyID = ?";
		ComplaintDealer cd = (ComplaintDealer) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { account
						.getCompanyID() });
		Map<String, Object> map = new HashMap<String, Object>();
		if (cd == null) {
			map.put("result", "set");
		} else {
			map.put("result", "noset");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}

	public String showExtralDocModule() {

		return "toFrameExtral";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public DocUser getDocUser() {
		return docUser;
	}

	public void setDocUser(DocUser docUser) {
		this.docUser = docUser;
	}

	public DocComplaint getDocComplaint() {
		return docComplaint;
	}

	public void setDocComplaint(DocComplaint docComplaint) {
		this.docComplaint = docComplaint;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getJump() {
		return jump;
	}

	public void setJump(String jump) {
		this.jump = jump;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getGlobal() {
		return global;
	}

	public void setGlobal(String global) {
		this.global = global;
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

}
