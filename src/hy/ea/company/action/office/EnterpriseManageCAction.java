package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseManage;
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
 * 管理理念管理 ---公司汇总
 * */
@Controller
@Scope("prototype")
public class EnterpriseManageCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseManage enterpriseManage;
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

	//根据条件查询管理理念
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseManage);
		return getEnterpriseManageList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseManage.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			if(enterpriseManage.getManageContent() != null && !"".equals(enterpriseManage.getManageContent().trim())){
				dc.add(Restrictions.like("manageContent", enterpriseManage.getManageContent(), MatchMode.ANYWHERE));
				
			}if(enterpriseManage.getManageIdea() != null && !"".equals(enterpriseManage.getManageIdea().trim())){
				dc.add(Restrictions.like("manageIdea", enterpriseManage.getManageIdea(), MatchMode.ANYWHERE));
				
			}
		} 
		return dc;
	}

	// 管理理念列表
	public String getEnterpriseManageList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
		return "managelist";	
	}
  
	//excel导出
	public String showExcel(){
		Map<String,Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		if(account == null){
			return "tologin";
		}
		String companyID = account.getCompanyID();
		excelStream = excelService.showExcel(EnterpriseManage.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook logbook=logBookService.saveCLogBook(companyID,"导出管理理念公司汇总列表", account);
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

	public EnterpriseManage getEnterpriseManage() {
		return enterpriseManage;
	}

	public void setEnterpriseManage(EnterpriseManage enterpriseManage) {
		this.enterpriseManage = enterpriseManage;
	}
}