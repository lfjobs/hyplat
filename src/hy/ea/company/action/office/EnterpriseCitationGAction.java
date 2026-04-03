package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseCitation;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
/**
 * 奖状奖牌集团汇总
 * @author fhw
 *
 */
@Controller
@Scope("prototype")
public class EnterpriseCitationGAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	
	private EnterpriseCitation enterpriseCitation;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private InputStream excelStream;

	/**
	 * 根据条件查询奖状奖牌集团汇总
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseCitation);
		return getEnterpriseCitationList();
	}
	
	/**
	 * 奖状奖牌集团汇总列表
	 * @return
	 */
	public String getEnterpriseCitationList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "citationlist";
	}
	
	/**
	 * 奖状奖牌集团汇总列表、查询、导出调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from EnterpriseCitation t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		
		if (search != null && search.equals("search")){
			enterpriseCitation = (EnterpriseCitation) session.get("tablesearch");
			if (null != enterpriseCitation.getEnName()
					&& !"".equals(enterpriseCitation.getEnName().trim())) {
				hql += " and t.enName like ?";
				parms.add("%" + enterpriseCitation.getEnName().trim() + "%");
			}
			if (null != enterpriseCitation.getEnSubject() 
					&& !"".equals(enterpriseCitation.getEnSubject().trim())) {
				hql += " and t.enSubject like ?";
				parms.add("%" + enterpriseCitation.getEnSubject().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 导出奖状奖牌集团汇总
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		
		excelStream = excelService.showExcel(EnterpriseCitation.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出奖状奖牌集团汇总", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public EnterpriseCitation getEnterpriseCitation() {
		return enterpriseCitation;
	}

	public void setEnterpriseCitation(EnterpriseCitation enterpriseCitation) {
		this.enterpriseCitation = enterpriseCitation;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
}
