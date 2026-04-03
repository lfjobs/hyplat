package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.DomainName;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 域名管理
 */
public class DomainNameAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private DomainName domainName;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private int pageNumber;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", domainName);
		return getListDomainname();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(DomainName.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			domainName = (DomainName) session.get("tablesearch");
			if (!"".equals(domainName.getCompanyName())) {
				dc.add(Restrictions.like("companyName", domainName
						.getCompanyName(), MatchMode.ANYWHERE));
			}
			if (!"".equals(domainName.getDomain())) {
				dc.add(Restrictions.eq("domain", domainName.getDomain()));
			}
		}
		return dc;
	}

	public String getListDomainname() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getList());
		return "domainname";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(DomainName.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出域名管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String addDomainname() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (domainName.getDomainID() == null
				|| "".equals(domainName.getDomainID())) {
			domainName.setDomainID(serverService.getServerID("domainName"));
			parameter = "添加域名管理(域名:" + domainName.getDomain() + ")";
		} else {
			String hql2 = "from DomainName where companyID=?  and domainID=?";
			DomainName aff = (DomainName) baseBeanService
					.getBeanByHqlAndParams(hql2, new Object[] {
							account.getCompanyID(), domainName.getDomainID() });
			parameter = "修改域名管理(域名:" + aff.getDomain() + ")";

		}
		domainName.setOrganizationID(organizationID);
		domainName.setCompanyID(account.getCompanyID());
		// baseBeanService.update(domainName);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(domainName);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				null, null);
		return "success";
	}

	public String delDomainname() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), domainName.getDomainID() };
		String hql2 = "from DomainName where companyID=?  and domainID=?";
		DomainName aff = (DomainName) baseBeanService.getBeanByHqlAndParams(
				hql2, params);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"删除域名管理(域名:" + aff.getDomainID() + ")", account);
		String hql = "delete from DomainName where companyID=?  and domainID=?";
		// baseBeanService.executeHqlByParams(hql, params);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				new String[] { hql }, params);
		return "success";
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public DomainName getDomainName() {
		return domainName;
	}

	public void setDomainName(DomainName domainName) {
		this.domainName = domainName;
	}

}
