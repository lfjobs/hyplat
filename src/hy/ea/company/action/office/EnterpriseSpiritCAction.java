package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseSpirit;
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
//企业精神公司汇总
public class EnterpriseSpiritCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseSpirit enterpriseSpirit;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	//根据条件查询企业精神
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseSpirit);
			return getEnterpriseSpiritList();
		}
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseSpirit.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			enterpriseSpirit = (EnterpriseSpirit) session.get("tablesearch");
			if(enterpriseSpirit.getEnterpriseName() != null
					&& !"".equals(enterpriseSpirit.getEnterpriseName().trim())){
				dc.add(Restrictions.like("enterpriseName", enterpriseSpirit.getEnterpriseName(),MatchMode.ANYWHERE));
				
			}
			if(enterpriseSpirit.getSpiritContent() != null
					&& !"".equals(enterpriseSpirit.getSpiritContent().trim())){
				dc.add(Restrictions.like("spiritContent", enterpriseSpirit.getSpiritContent(),MatchMode.ANYWHERE));
				
			}
		} 
		return dc;
	}

	// 企业精神列表
	public String getEnterpriseSpiritList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "spiritlist";	
	}
//导出excel 
	public  String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String companyID =account.getCompanyID();
		excelStream = excelService.showExcel(EnterpriseSpirit.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook logbook=logBookService.saveCLogBook(companyID,"导出企业精神公司汇总列表", account);
		baseBeanService.update(logbook);
		
		
		return "showexcel";
		
	}
	public EnterpriseSpirit getEnterpriseSpirit() {
		return enterpriseSpirit;
	}

	public void setEnterpriseSpirit(EnterpriseSpirit enterpriseSpirit) {
		this.enterpriseSpirit = enterpriseSpirit;
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
}
