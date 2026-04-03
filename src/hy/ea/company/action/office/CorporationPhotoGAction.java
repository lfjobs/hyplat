package hy.ea.company.action.office;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
public class CorporationPhotoGAction {
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
		
		// 企业图片列表
		
		private List<Object> getList(Map<String, Object> session,
				CAccount account) {
			List<Object> parms = new ArrayList<Object>();
			List<Object> results = new ArrayList<Object>();
			String companyID = account.getCompanyID();
			
			String hql = "from CorporationPhoto t where exists ( select c.companyID from Company c"
					+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
			parms.add(companyID);
			parms.add(companyID);
			if (search != null && search.equals("search")){
				corporationPhoto = (CorporationPhoto) session.get("tablesearch");
				if (null != corporationPhoto.getPhotoFileName()
						&& !"".equals(corporationPhoto.getPhotoFileName().trim())) {
					hql += " and t.photoFileName like ?";
					parms.add("%" + corporationPhoto.getPhotoFileName().trim() + "%");
				}
				if (null != corporationPhoto.getShootingYear()
						&& !"".equals(corporationPhoto.getShootingYear().trim())) {
					hql += " and t.shootingYear like ?";
					parms.add("%" + corporationPhoto.getShootingYear().trim() + "%");
				}
			}
			hql += " order by t.companyID desc";
			results.add(hql);
			results.add(parms.toArray());
			return results;
		}
		
		//获得图片列表
		public String getCorporationPhotoList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			List<Object> list = getList(session, account);
			String hql = list.get(0).toString();
			Object[] params = (Object[]) list.get(1);
			pageForm = baseBeanService.getPageForm(
					(null != pageForm ? pageForm.getPageNumber() : 1), 
					(pageNumber == 0 ? 10 : pageNumber), hql, params);
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
			List<Object> list = getList(session, account);
			String hql = (String) list.get(0);
			Object[] parms = (Object[]) list.get(1);
			excelStream = excelService.showExcel(CorporationPhoto.columnHeadings(),
					baseBeanService.getListBeanByHqlAndParams(hql, parms));
			CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
					"导出图片管理集团汇总", account);
			baseBeanService.update(cLogBook);
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
