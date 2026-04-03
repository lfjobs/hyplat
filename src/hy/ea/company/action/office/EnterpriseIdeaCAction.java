package hy.ea.company.action.office;
/*
 * 企业理念公司汇总
 * */
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseIdea;
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

@Controller
@Scope("prototype")
public class EnterpriseIdeaCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseIdea enterpriseIdea;
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

	//根据条件查询企业理念
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseIdea);
		return getEnterpriseIdeaList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseIdea.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			enterpriseIdea = (EnterpriseIdea) session.get("tablesearch");
			if(null != enterpriseIdea.getEnterpriseName()
				&&!"".equals(enterpriseIdea.getEnterpriseName().trim())){
				dc.add(Restrictions.like("enterpriseName", enterpriseIdea.getEnterpriseName(), MatchMode.ANYWHERE));
			}if(null != enterpriseIdea.getIdeaContent()
					&&!"".equals(enterpriseIdea.getIdeaContent().trim())){
				dc.add(Restrictions.like("ideaContent", enterpriseIdea.getIdeaContent(), MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	// 企业理念列表
	public String getEnterpriseIdeaList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "idealist";	
	}
	
	//导出excel
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String companyID = account.getCompanyID();
		excelStream = excelService.showExcel(EnterpriseIdea.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CLogBook cLogBook = logBookService.saveCLogBook(companyID,
				"导出企业理念公司汇总", account);
		baseBeanService.update(cLogBook);
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

	public EnterpriseIdea getEnterpriseIdea() {
		return enterpriseIdea;
	}

	public void setEnterpriseIdea(EnterpriseIdea enterpriseIdea) {
		this.enterpriseIdea = enterpriseIdea;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	

}
