package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseManner;
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
/*
 * 工作态度管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseMannerCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseManner enterpriseManner;
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

	//根据条件查询工作态度
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseManner);
		return getEnterpriseMannerList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseManner.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			enterpriseManner = (EnterpriseManner) session.get("tablesearch");
			if(enterpriseManner.getEnterpriseName() != null
					&& !"".equals(enterpriseManner.getEnterpriseName().trim())){
				dc.add(Restrictions.like("enterpriseName", enterpriseManner.getEnterpriseName(), MatchMode.ANYWHERE));
				
			}if(enterpriseManner.getMannerContent() != null
					&& !"".equals(enterpriseManner.getMannerContent().trim())){
				dc.add(Restrictions.like("mannerContent", enterpriseManner.getMannerContent(), MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	// 工作态度列表
	public String getEnterpriseMannerList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
		return "mannerlist";	
	}
  
	//导出excel
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String companyID =account.getCompanyID();
		excelStream = excelService.showExcel(EnterpriseManner.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook logbook=logBookService.saveCLogBook(companyID,"导出工作态度公司汇总列表", account);
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

	public EnterpriseManner getEnterpriseManner() {
		return enterpriseManner;
	}

	public void setEnterpriseManner(EnterpriseManner enterpriseManner) {
		this.enterpriseManner = enterpriseManner;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


}
