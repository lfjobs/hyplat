package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.DomainName;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 域名管理公司汇总
 * @author zl
 *
 */
@Controller
@Scope("prototype")
public class DomainNameCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	
	private DomainName domainName;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private InputStream excelStream;

	/**
	 * 根据条件查询域名管理公司汇总
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", domainName);
		return getListDomainnameCompany();
	}
	
	/**
	 * 域名管理公司汇总列表
	 * @return
	 */
	public String getListDomainnameCompany() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "domainname";
	}

	/**
	 * 域名管理公司汇总列表、查询、导出调用
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(DomainName.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			domainName = (DomainName) session.get("tablesearch");
			if (null != domainName.getCompanyName() 
					&& !"".equals(domainName.getCompanyName().trim())) {
				dc.add(Restrictions.like("companyName", domainName
						.getCompanyName().trim(), MatchMode.ANYWHERE));
			}
			if (null != domainName.getDomain() 
					&& !"".equals(domainName.getDomain().trim())) {
				dc.add(Restrictions.like("domain", domainName.getDomain().trim(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	/**
	 * 导出域名管理公司汇总
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(DomainName.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出域名管理公司汇总", account);
		baseBeanService.update(cLogBook);
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