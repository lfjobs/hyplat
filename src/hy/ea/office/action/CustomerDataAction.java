package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 企业注册客户管理
 * 
 * @author
 */
@Controller
@Scope("prototype")
public class CustomerDataAction {

	@Resource
	private BaseBeanService baseBeanService;
	@SuppressWarnings("unused")
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CompanyService companyService;

	private String parameter;
	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private Company company;

	private InputStream excelStream;

	private ContactCompany contactCompany; // 往来单位
	private List<CCode> typelist;
	private List<CCode> connectionlist;
	private String comType;

	// 客户资料查询
	public String getCustomerDataList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Company company = companyService.getCompanyByCompanyID(account
				.getCompanyID());
		

		typelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000003");
		if ( ( "strj".equals(company.getCompanyIdentifier()) || "lovers".equals(company.getCompanyIdentifier()) )
				&& "sa".equals(account.getAccountEmail())) {
			List<Object> list = getList();
			String sql = (String) list.get(0);
			Object[] parms = (Object[]) list.get(1);
			String sqlcount = "select count(1) "
					+ sql.substring(sql.indexOf("from"));
			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql, sqlcount, parms);
			return "customerDatalist";
		}
		return "no_authority";
	}

	/**
	 * 列表、导出调用
	 * 
	 * @return
	 */
	private List<Object> getList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Company company = companyService.getCompanyByCompanyID(account
				.getCompanyID());
		List<Object> result = new ArrayList<Object>();
		String sql = "select pcom.companyname pcompanyname,company.companyidentifier companyidentifier, company.companyname companyname, t.companyaddress,t.companymanager,"
				+ " t.companyphone,t.companyemail,company.companyKey from  dtcompany company  left join dtcdetail t on t.companyid = company.companyid"
				+ " left join dtcompany pcom on pcom.companyid = company.companypid where (company.companytype = '01' or company.companytype between '10' and '12') and company.companyStatus='00' ";
		List<Object> parms = new ArrayList<Object>();
		if (comType != null && !"".equals(comType)) {
			company = (Company) ActionContext.getContext().getSession()
					.get("tablesearch");
			sql += "and company.comType=?";
			parms.add(comType);
		}
		if (search != null && search.equals("search")) {
			company = (Company) ActionContext.getContext().getSession()
					.get("tablesearch");
			if (!"".equals(company.getCompanyIdentifier())) {
				sql += " and company.companyidentifier like ?";
				parms.add("%" + company.getCompanyIdentifier() + "%");
			}
			if (!"".equals(company.getCompanyName())) {
				sql += " and company.companyname like ?";
				parms.add("%" + company.getCompanyName() + "%");
			}
		}
		sql += "order by company.companypid";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 
	 * 逻辑注册删除公司
	 * 
	 * @return
	 */
	public String updateCompanyStatus() {
		if (company != null && !"".equals(company.getCompanyKey())
				&& null != company.getCompanyKey()) {
			Company companyOther = (Company) baseBeanService.getBeanByKey(
					Company.class, company.getCompanyKey());
			companyOther.setCompanyStatus("01");
			baseBeanService.update(companyOther);
		}
		return "success";
	}

	// 根据条件查询客户资料
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", company);
		return getCustomerDataList();
	}

	/**
	 * 导出客户资料
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Company company = companyService.getCompanyByCompanyID(account.getCompanyID());

		if ("strj".equals(company.getCompanyIdentifier())
				&& "sa".equals(account.getAccountEmail())) {
			List<Object> list = getList();
			String sql = (String) list.get(0);
			Object[] parms = (Object[]) list.get(1);

			excelStream = excelService.showExcel(
					ContactCompany.columnHeadings1(),
					baseBeanService.getListBeanBySqlAndParams(sql, parms));
			CLogBook logBook = logBookService.saveCLogBook(organizationID,
					"导出客户资料", account);
			baseBeanService.update(logBook);
			return "showexcel";
		}
		return "no_authority";
	}

	/**
	 * 客户列表中的查询往来单位
	 */
	public String toSearchJQM() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", contactCompany);
		return getListContactCompanyJQM();
	}

	/**
	 * 客户列表中的往来单位列表
	 */
	public String getListContactCompanyJQM() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(ContactCompany.class);
		dc.add(Restrictions.or(Restrictions.isNull("custStatus"),
				Restrictions.like("custStatus", "01")));
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000003");
		if (search != null && search.equals("search")) {
			this.contactCompany = (ContactCompany) session.get("tablesearch");
			if (null != contactCompany.getCompanyName()
					&& !"".equals(contactCompany.getCompanyName())) {
				dc.add(Restrictions.like("companyName",
						contactCompany.getCompanyName(), MatchMode.ANYWHERE));
			}
			if (null != contactCompany.getCresponsible()
					&& !"".equals(contactCompany.getCresponsible())) {
				dc.add(Restrictions.like("cresponsible",
						contactCompany.getCresponsible(), MatchMode.ANYWHERE));
			}
			if (null != contactCompany.getCompanyAddr()
					&& !"".equals(contactCompany.getCompanyAddr())) {
				dc.add(Restrictions.like("companyAddr",
						contactCompany.getCompanyAddr(), MatchMode.ANYWHERE));
			}
			if (null != contactCompany.getIndustryType()
					&& !"".equals(contactCompany.getIndustryType())) {
				dc.add(Restrictions.eq("industryType",
						contactCompany.getIndustryType()));
			}
		}
		  
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), dc);
		return "list_jqm";
	}

	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
	}

}
