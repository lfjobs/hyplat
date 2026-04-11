package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.Document;
import hy.ea.office.service.DocCommonService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("deprecation")
@Controller
@Scope("prototype")
/**
 * 公文流转管理
 */
public class DocSummaryAction {
	private static final Logger logger = LoggerFactory.getLogger(DocSummaryAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private DocCommonService docCommonService;// 公文相关的业务逻辑

	private Document document;
	private PageForm pageForm;
	private String result;
	private String search;
	private int pageNumber;
	private String type;
	private String module;
	private List<BaseBean> attachlist;
	private List<BaseBean> doclist;
	private String overdue;//表示过期；

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", document);
		return getSummaryDocList();

	}

	/**
	 * 
	 * 打印预览
	 * 
	 * @return
	 */
	public String toPreview() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", document);
		doclist = baseBeanService.getListByDC(getList(type));
		doclist = docCommonService.getFullListBean(doclist);
		return "toPreview";
	}

	private DetachedCriteria getList(String type) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(Document.class);
		dc.addOrder(Order.desc("draftTime"));
		dc.add(Restrictions.eq("module", (String) session.get("module")));
		dc.add(Restrictions.not(Restrictions.eq("status", "D")));
		try {
			if (type.equals("group")) {
				String hql = "from Company where companyID = ?";

				hql = "select companyID from Company where companyPID = ? or companyID = ?";

				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] { account.getCompanyID(),
										account.getCompanyID() });

				dc.add(Restrictions.in("companyID", list));

			} else {
				dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			}
			
			
			if(overdue!=null&&overdue.equals("over")){
			  dc.add(Restrictions.isNotNull("endValidity"));
			  dc.add(Restrictions.lt("endValidity",new Date()));

			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		if (search != null && search.equals("search")) {
			document = (Document) session.get("tablesearch");
			if (document.getOrganizationID() != null
					&& !"".equals(document.getOrganizationID())) {
				dc.add(Restrictions.eq("organizationID", document
						.getOrganizationID()));
			}
			if (document.getDrafterID() != null
					&& !"".equals(document.getDrafterID())) {
				dc.add(Restrictions.eq("drafterID", document.getDrafterID()));
			}
			if (document.getTitle() != null
					&& !"".equals(document.getTitle())) {
				dc.add(Restrictions.like("title", document.getTitle().trim(), MatchMode.ANYWHERE));
			}
			if (document.getStatus() != null
					&& !"".equals(document.getStatus())) {
				dc.add(Restrictions.eq("status", document.getStatus()));
			}
			if (document.getDocType() != null
					&& !"".equals(document.getDocType())) {
				dc.add(Restrictions.eq("docType", document.getDocType()));
			}

			if (document.getCompanyID() != null
					&& !"".equals(document.getCompanyID())) {
				dc.add(Restrictions.eq("companyID", document.getCompanyID()));

			}
			if (document.getPartyA() != null
					&& !"".equals(document.getPartyA())) {
				dc.add(Restrictions.eq("partyA", document.getPartyA().trim()));

			}
			if (document.getPartyB() != null
					&& !"".equals(document.getPartyB())) {
				dc.add(Restrictions.eq("partyB", document.getPartyB().trim()));

			}
			
			if (document.getPartyAstaff() != null
					&& !"".equals(document.getPartyAstaff())) {
				dc.add(Restrictions.eq("partyAstaff", document.getPartyAstaff().trim()));

			}
			if (document.getPartyBstaff() != null
					&& !"".equals(document.getPartyBstaff())) {
				dc.add(Restrictions.eq("partyBstaff", document.getPartyBstaff().trim()));

			}
			if (document.getTipStatus() != null
					&& !"".equals(document.getTipStatus())) {
				if(document.getTipStatus().equals("formal")){//正常提醒，需要为空的
					dc.add(Restrictions.isNull("tipStatus"));
				}else if(document.getTipStatus().equals("00")){
					dc.add(Restrictions.eq("tipStatus","00"));
				}
				
			}
		}

		return dc;
	}

	/**
	 * 
	 * 获得汇总范围内的公文包括：公司范围，集团范围type:company,group
	 * 
	 * @return
	 */
	public String getSummaryDocList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		if (module == null || module.equals("")) {
			module = (String) session.get("module");
		}
		session.put("module", module);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList(type));
		pageForm = docCommonService.getFullPageForm(pageForm);
		if(overdue!=null&&overdue.equals("over")){
			return "tooverduelist";
		}
		return "tosummarylist";
	}

	/**
	 * 
	 * 获取公文的查看信息
	 * 
	 * @return
	 */
	public String getViewDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String docId = request.getParameter("docId");
		String hql = "from Document where docId=?";
		document = (Document) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { docId });
		document = docCommonService.getViewFullDoc(document);
		if (document != null) {
			attachlist = docCommonService.getOfficeAttach(document);

		}
		return "tosummaryview";
	}

	/**
	 * 
	 * 获取当前公司及其子公司
	 * 
	 * @return
	 */
	public String getCurrentAndSubCompany() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String hql = "from Company where companyPID = ? or companyID = ?";
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID(),
						account.getCompanyID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companylist", companylist);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}
	
	
	
	/*
	 * 
	 * 
	 * 设置/取消提醒
	 * 
	 */
	public String setTipOperate(){
		
		String docIds = document.getDocId();
	    String[] docIdArray = docIds.split(",");
	    String hql = "from Document where docId = ?";
	    Document doc = null;
	    for (int i = 0; i < docIdArray.length; i++) {
			doc = (Document) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{docIdArray[i]});
		    if(doc.getTipStatus()!=null&&!doc.getTipStatus().equals("")){
		    	doc.setTipStatus(null);
		    }else{
		    	doc.setTipStatus("00");
		    }
		    
		    baseBeanService.update(doc);
	    }
		
		return "success";
	}
	
	
	

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<BaseBean> getAttachlist() {
		return attachlist;
	}

	public void setAttachlist(List<BaseBean> attachlist) {
		this.attachlist = attachlist;
	}

	public List<BaseBean> getDoclist() {
		return doclist;
	}

	public void setDoclist(List<BaseBean> doclist) {
		this.doclist = doclist;
	}

	public String getOverdue() {
		return overdue;
	}

	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	

}
