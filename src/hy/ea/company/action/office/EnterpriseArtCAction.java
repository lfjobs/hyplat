package hy.ea.company.action.office;

import java.io.InputStream;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseArt;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
@Controller
@Scope("prototype")
/**
 * 文化艺术公司汇总
 * @author allen
 *
 */
public class EnterpriseArtCAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private EnterpriseArt enterpriseArt;
	//根据条件查询文化艺术
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseArt);
			return getEnterpriseArtList();
		}
		
		
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseArt.class);
			dc.add(Restrictions.eq("companyID", companyID));
			if (search != null && search.equals("search")){
				enterpriseArt = (EnterpriseArt) session.get("tablesearch");
				if (null != enterpriseArt.getEnName()
						&& !"".equals(enterpriseArt.getEnName().trim())) {
					dc.add(Restrictions.like("enName", enterpriseArt.getEnName(), MatchMode.ANYWHERE));
					
				}if (null != enterpriseArt.getEnSubject()
						&& !"".equals(enterpriseArt.getEnSubject().trim())) {
					dc.add(Restrictions.like("enSubject", enterpriseArt.getEnSubject(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}			
		//得到文化艺术列表
		public String getEnterpriseArtList() {
			 pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "artlist";	
		}
		
		//导出企业文化艺术管理列表
		public String showExcel(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			if(account == null){
				return "tologin";
			}
			String organizationID = (String) session.get("organizationID");
			excelStream = excelService.showExcel(EnterpriseArt.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logbook=logBookService.saveCLogBook(organizationID,"导出集团文化艺术公司汇总列表", account);
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


		public EnterpriseArt getEnterpriseArt() {
			return enterpriseArt;
		}


		public void setEnterpriseArt(EnterpriseArt enterpriseArt) {
			this.enterpriseArt = enterpriseArt;
		}

				
		

}
