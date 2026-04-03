package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseGoal;
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

import com.opensymphony.xwork2.ActionContext;

public class EnterpriseGoalCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseGoal enterpriseGoal;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询企业目标
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseGoal);
		return getEnterpriseGoalList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseGoal.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			enterpriseGoal = (EnterpriseGoal) session.get("tablesearch");
			if(enterpriseGoal.getEnterpriseName() != null
					&& !"".equals(enterpriseGoal.getEnterpriseName().trim())){
				dc.add(Restrictions.like("enterpriseName", enterpriseGoal.getEnterpriseName(), MatchMode.ANYWHERE));
				
			}if(enterpriseGoal.getGoalContent() != null
					&& !"".equals(enterpriseGoal.getGoalContent().trim())){
				dc.add(Restrictions.like("goalContent", enterpriseGoal.getGoalContent(), MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	// 企业目标列表
	public String getEnterpriseGoalList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
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
		excelStream = excelService.showExcel(EnterpriseGoal.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook logbook=logBookService.saveCLogBook(organizationID,"导出企业目标公司汇总列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public EnterpriseGoal getEnterpriseGoal() {
		return enterpriseGoal;
	}

	public void setEnterpriseGoal(EnterpriseGoal enterpriseGoal) {
		this.enterpriseGoal = enterpriseGoal;
	}



	
}
