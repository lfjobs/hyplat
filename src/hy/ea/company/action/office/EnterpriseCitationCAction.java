package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseCitation;
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
 * 奖状奖牌管理 --公司汇总
 * */
@Controller
@Scope("prototype")
public class EnterpriseCitationCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseCitation enterpriseCitation;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	
	//根据条件查询奖状奖牌
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseCitation);
			return getEnterpriseCitationList();
		}

		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseCitation.class);
			dc.add(Restrictions.eq("companyID", companyID));
			if (search != null && search.equals("search")) {
				enterpriseCitation = (EnterpriseCitation) session.get("tablesearch");
				if(enterpriseCitation.getEnSubject() != null
						&& !"".equals(enterpriseCitation.getEnSubject().trim())){
					dc.add(Restrictions.like("enSubject", enterpriseCitation.getEnSubject(), MatchMode.ANYWHERE));
					
				}if(enterpriseCitation.getEnName() != null
						&& !"".equals(enterpriseCitation.getEnName().trim())){
					dc.add(Restrictions.like("enName", enterpriseCitation.getEnName(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}

		// 奖状奖牌列表
		public String getEnterpriseCitationList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "citationllist";	
		}
		
		//导出奖状奖牌列表
		public String showExcel(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			if(account == null){
				return "tologin";
			}
			String organizationID = (String) session.get("organizationID");
			excelStream = excelService.showExcel(EnterpriseCitation.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logbook=logBookService.saveCLogBook(organizationID,"导出奖状奖牌公司汇总列表", account);
			baseBeanService.update(logbook);
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

