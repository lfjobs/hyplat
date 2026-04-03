package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseGoal;
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

@Controller
@Scope("prototype")
public class EnterpriseGoalGAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private EnterpriseGoal enterpriseGoal;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	//根据条件查询企业目标
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseGoal);
		return getEnterpriseGoalList();
	}


	// 企业目标列表
	
	private List<Object> getList(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from EnterpriseGoal t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			enterpriseGoal = (EnterpriseGoal) session.get("tablesearch");
			if (null != enterpriseGoal.getEnterpriseName()
					&& !"".equals(enterpriseGoal.getEnterpriseName().trim())) {
				hql += " and t.enterpriseName like ?";
				parms.add("%" + enterpriseGoal.getEnterpriseName().trim() + "%");
			}
			if (null != enterpriseGoal.getGoalContent()
					&& !"".equals(enterpriseGoal.getGoalContent().trim())) {
				hql += " and t.ideaContent like ?";
				parms.add("%" + enterpriseGoal.getGoalContent().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}
	
	public String getEnterpriseGoalList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "goallist";	
	}
	
	//导出企业目标列表
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(EnterpriseGoal.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出企业目标集团汇总", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}


	public PageForm getPageForm() {
		return pageForm;
	}


	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}


	public EnterpriseGoal getEnterpriseGoal() {
		return enterpriseGoal;
	}


	public void setEnterpriseGoal(EnterpriseGoal enterpriseGoal) {
		this.enterpriseGoal = enterpriseGoal;
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


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
