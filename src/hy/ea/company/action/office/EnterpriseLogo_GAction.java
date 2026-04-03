package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.office.EnterpriseLogo;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 企业形象管理  集团汇总
 * */
@Controller
@Scope("prototype")
public class EnterpriseLogo_GAction {
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
	private Company company;
	private int pageNumber;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	// 根据条件查询企业形象
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseLogo);
		return getEnterpriseLogoList();
	}

	private List<Object> getList(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from EnterpriseLogo t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			enterpriseLogo = (EnterpriseLogo) session.get("tablesearch");
			if (null != enterpriseLogo.getAuthor()
					&& !"".equals(enterpriseLogo.getAuthor().trim())) {
				hql += " and t.author like ?";
				parms.add("%" + enterpriseLogo.getAuthor().trim() + "%");
			}
			if (null != enterpriseLogo.getLogoType()
					&& !"".equals(enterpriseLogo.getLogoType().trim())) {
				hql += " and t.logoType like ?";
				parms.add("%" + enterpriseLogo.getLogoType().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
		}

	
	// 企业形象列表
	public String getEnterpriseLogoList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "logolist";
	}

	// 导出企业形象列表
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "tologin";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(EnterpriseLogo.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出集团企业形象", account);
		baseBeanService.update(cLogBook);
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

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public CLogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
