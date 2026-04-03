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
import hy.ea.bo.office.CorporationPhoto;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
@Controller
@Scope("prototype")
/**
 * 图片管理公司汇总
 * @author allen
 *
 */
public class CorporationPhotoCAction {
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
	private CorporationPhoto corporationPhoto;
	//根据条件查询图片管理
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", corporationPhoto);
			return getCorporationPhotoList();
		}
		
		
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyID = account.getCompanyID();
			DetachedCriteria dc = DetachedCriteria.forClass(CorporationPhoto.class);
			dc.add(Restrictions.eq("companyID", companyID));
			if (search != null && search.equals("search")){
				corporationPhoto = (CorporationPhoto) session.get("tablesearch");
				if (null != corporationPhoto.getPhotoFileName()
						&& !"".equals(corporationPhoto.getPhotoFileName().trim())) {
					dc.add(Restrictions.like("photoFileName", corporationPhoto.getPhotoFileName(), MatchMode.ANYWHERE));
					
				}if (null != corporationPhoto.getShootingYear()
						&& !"".equals(corporationPhoto.getShootingYear().trim())) {
					dc.add(Restrictions.like("shootingYear", corporationPhoto.getShootingYear(), MatchMode.ANYWHERE));
				}
			} 
			return dc;
		}			
		//获得图片列表
		public String getCorporationPhotoList() {
			 pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "corporationPhotolist";	
		}
		
		//导出企业图片管理列表
		public String showExcel(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			if(account == null){
				return "tologin";
			}
			String organizationID = (String) session.get("organizationID");
			excelStream = excelService.showExcel(CorporationPhoto.columnHeadings(), baseBeanService.getListByDC(getList()));
			CLogBook logbook=logBookService.saveCLogBook(organizationID,"导出集团图片公司汇总列表", account);
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

		public CorporationPhoto getCorporationPhoto() {
			return corporationPhoto;
		}

		public void setCorporationPhoto(CorporationPhoto corporationPhoto) {
			this.corporationPhoto = corporationPhoto;
		}
		
		

}
