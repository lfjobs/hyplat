package hy.ea.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CDomain;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.bo.HelpDocument;
import hy.ea.bo.office.vo.ViewExaminedoc;
import hy.ea.office.service.DocCommonService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 使用帮助文档
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class HDocumentAction {
	private static final Logger logger = LoggerFactory.getLogger(HDocumentAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;
	@Resource
	private DocCommonService docCommonService;
	private String parameter;
	private HelpDocument helpDocument;
	private PageForm pageFormnews;
	private int pageNumbernews;
	private String search;
	private String sdate;
	private String edate; 
	private CRole crole;
	
	public CRole getCrole() {
		return crole;
	}

	public void setCrole(CRole crole) {
		this.crole = crole;
	}

	/**
	 * 使用帮助文档——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("helpDocument",
				helpDocument);
		return getHDocumentList();
	}

	/**
	 * 使用帮助文档——列表
	 * 
	 * @return
	 */
	public String getHDocumentList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		if (account == null) {
			return "not_login";
		}
		String hql = " from CRole where companyID = ? and roleID=? order by organizationName";
		crole=(CRole)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),account.getRoleID()});
		pageFormnews = baseBeanService.getPageFormByDC((null != pageFormnews ? pageFormnews
				.getPageNumber() : 1), (pageNumbernews == 0 ? 3 : pageNumbernews),
				getListExamine("examineyes"));
		pageFormnews = docCommonService.getPostName(pageFormnews, "examineyes");
		return "list";
	}
	/**
	 * 
	 * 新闻公告
	 * 
	 * @return
	 */
	private DetachedCriteria getListExamine(String finishType) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(ViewExaminedoc.class);
		dc.addOrder(Order.desc("examinetime"));
		//dc.add(Restrictions.eq("assignee", account.getStaffID()));
		//新闻，公告通知
		dc.add(Restrictions.or(Restrictions.eq("module", "news"),Restrictions.eq("module","AnnNoti")));
		//dc.add(Restrictions.eq("deptidofsubscriber", (String) session.get("organizationID")));
		dc.add(Restrictions.eq("companyid", account.getCompanyID()));
		return dc;
	}
	/**
	 * 使用帮助文档——添加
	 * 
	 * @return
	 */
	public String saveHDocument() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		helpDocument.setHdID(serverService.getServerID("helpDocument"));
		helpDocument.setHdDate(new Date());
		parameter = "添加使用帮助文档（名称：" + helpDocument.getHdName() + "）";
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(helpDocument);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "success";
	}

	public void toPage() {
		try {
			String domain = ServletActionContext.getRequest().getParameter(
					"domain");
			String url = "";
			if (domain == null || domain.length() < 1) {
				ServletActionContext.getResponse().sendRedirect(
						"http://www.impf2010.com/page/ea/index.jsp");
			} else {
				CDomain bb = (CDomain) baseBeanService.getBeanByHqlAndParams(
						"from CDomain m where m.state=? and m.domain=?",
						new Object[] { 0, domain });
				if (bb == null) {
					
					ServletActionContext.getResponse().sendRedirect(domain);
				}
				url =bb.getDomain()+bb.getDomainContent();
				search=URLEncoder.encode(bb.getDomainTitle(),"UTF-8");
			}
			ServletActionContext.getResponse().sendRedirect(url+"&search="+search);
		} catch (Exception e) {
			logger.info("error");
		}
	}
	
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public HelpDocument getHelpDocument() {
		return helpDocument;
	}

	public void setHelpDocument(HelpDocument helpDocument) {
		this.helpDocument = helpDocument;
	}

	public int getPageNumbernews() {
		return pageNumbernews;
	}

	public void setPageNumbernews(int pageNumbernews) {
		this.pageNumbernews = pageNumbernews;
	}

	public PageForm getPageFormnews() {
		return pageFormnews;
	}

	public void setPageFormnews(PageForm pageFormnews) {
		this.pageFormnews = pageFormnews;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}	
}
