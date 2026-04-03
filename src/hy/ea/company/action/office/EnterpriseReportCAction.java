package hy.ea.company.action.office;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseReport;
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
 * 企业纪实管理 --公司汇总
 * */
@Controller
@Scope("prototype")
public class EnterpriseReportCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseReport enterpriseReport;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	
	//根据条件查询企业纪实
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseReport);
			return getEnterpriseReportList();
		}

		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseReport.class);
			dc.add(Restrictions.eq("companyID", companyID));
			if (search != null && search.equals("search")) {
				enterpriseReport = (EnterpriseReport) session.get("tablesearch");
				if(enterpriseReport.getReportDate() != null
						&& !"".equals(enterpriseReport.getReportDate())){
					dc.add(Restrictions.eq("reportDate", enterpriseReport.getReportDate()));
					
				}if(enterpriseReport.getReportContent() != null
						&& !"".equals(enterpriseReport.getReportContent().trim())){
					dc.add(Restrictions.like("reportContent", enterpriseReport.getReportContent(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}

		// 企业纪实列表
		public String getEnterpriseReportList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "reportlist";	
		}
		
		//导出企业纪实列表
		public String showExcel(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			if(account == null){
				return "tologin";
			}
			String companyID = account.getCompanyID();
			excelStream = excelService.showExcel(EnterpriseReport.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logbook=logBookService.saveCLogBook(companyID,"导出企业纪实公司汇总列表", account);
			baseBeanService.update(logbook);
			return "showexcel";
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public EnterpriseReport getEnterpriseReport() {
		return enterpriseReport;
	}

	public void setEnterpriseReport(EnterpriseReport enterpriseReport) {
		this.enterpriseReport = enterpriseReport;
	}
	
	
}
