package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseLogo;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import java.io.File;
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
 * 企业形象管理  公司汇总
 * */
@Controller
@Scope("prototype")
public class EnterpriseLogo_CAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseLogo enterpriseLogo;
	private PageForm pageForm;
	private InputStream excelStream;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private String search;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询企业形象
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseLogo);
		return getEnterpriseLogoList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseLogo.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			enterpriseLogo = (EnterpriseLogo) session.get("tablesearch");
			if(enterpriseLogo.getAuthor() != null
					&& !"".equals(enterpriseLogo.getAuthor().trim())){
				dc.add(Restrictions.like("author", enterpriseLogo.getAuthor(), MatchMode.ANYWHERE));
			}
			if(enterpriseLogo.getLogoType() != null
					&& !"".equals(enterpriseLogo.getLogoType().trim())){
				enterpriseLogo = (EnterpriseLogo) session.get("tablesearch");
				dc.add(Restrictions.like("logoType", enterpriseLogo.getLogoType(), MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}

	// 企业形象列表
	public String getEnterpriseLogoList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
		return "logolist";	
	}
	// 导出企业形象列表

	public String showExcel() {
		excelStream = excelService.showExcel(EnterpriseLogo.columnHeadings(), baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account.getAccountName().isEmpty()){
			return "tologin";
		}
		String companyID = account.getCompanyID();
		CLogBook  logbook=logBookService.saveCLogBook(companyID,"导出企业形象", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
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

	public EnterpriseLogo getEnterpriseLogo() {
		return enterpriseLogo;
	}

	public void setEnterpriseLogo(EnterpriseLogo enterpriseLogo) {
		this.enterpriseLogo = enterpriseLogo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}


}
