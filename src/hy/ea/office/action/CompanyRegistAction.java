package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 企业注册公司管理
 * 
 * @author : zl
 */
@Controller
@Scope("prototype")
public class CompanyRegistAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;

	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private Company company;
	
	private InputStream excelStream;

	/**
	 * 企业注册资料管理列表
	 * @return
	 */
	public String getCompanyRegistList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Company company = companyService.getCompanyByCompanyID(account
				.getCompanyID());
		if ( ( "lovers".equals(company.getCompanyIdentifier()) || "strj".equals(company.getCompanyIdentifier()) ) && "sa".equals(account.getAccountEmail())) {
			List<Object> list = getList();
			String sql = (String) list.get(0);
			Object[] parms = (Object[]) list.get(1);
			String sqlcount = "select count(1) " +sql.substring(sql.indexOf("from"));
			
			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql, sqlcount,parms);
			return "companyRegistlist";
		}
		return "no_authority";
	}
	
	/**
	 * 列表、导出调用
	 * @return
	 */
	private List<Object> getList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Company company = companyService.getCompanyByCompanyID(account
				.getCompanyID());
		List<Object> result = new ArrayList<Object>();
		String sql = "select pcom.companyname pcompanyname,company.companyidentifier companyidentifier, company.companyname companyname, t.companyaddress,t.companymanager,"
			+ " t.companyphone,t.companyemail from dtcompany company left join dtcdetail t on t.companyid = company.companyid"
			+ " left join dtcompany pcom on pcom.companyid = company.companypid where company.companytype = '00' ";
		
		List<Object> parms = new ArrayList<Object>();
		
		if (search != null && search.equals("search")) {
			company =(Company)ActionContext.getContext().getSession().get("tablesearch");
			if(!"".equals(company.getCompanyIdentifier())){
				sql += " and company.companyidentifier like ?";
				parms.add("%"+company.getCompanyIdentifier()+"%");
			}
			if(!"".equals(company.getCompanyName())){
				sql += " and company.companyname like ?";
				parms.add("%"+company.getCompanyName()+"%");
			}
		}
		sql += " order by company.companypid";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	//根据条件查询企业注册资料
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", company);
		return getCompanyRegistList();
	}
	
	/**
	 * 导出企业资料
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		Company company = companyService.getCompanyByCompanyID(account.getCompanyID());
		
		if ("lovers".equals(company.getCompanyIdentifier()) && "sa".equals(account.getAccountEmail())) {
			List<Object> list = getList();
			String sql = (String) list.get(0);
			Object[] parms = (Object[]) list.get(1);
			
			excelStream = excelService.showExcel(ContactCompany.columnHeadings1(),
					baseBeanService.getListBeanBySqlAndParams(sql, parms));
			CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出企业资料", account);
			baseBeanService.update(logBook);
			return "showexcel";
		}
		return "no_authority";
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
}
