package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Responsibilities;
import hy.ea.bo.human.vo.StaffResponsibilities;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 岗位职责汇总
 * */
@Controller
@Scope("prototype")
public class ResponsibilitiesSummaryAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	
	private Responsibilities responsibilities;
	private StaffResponsibilities staffResponsibilities;
	private PageForm pageForm;
	private InputStream excelStream;
	private List<BaseBean> stafflist;
	private List<BaseBean> organizationlist;
	private List<BaseBean> companylist;
	private File photo;
	private String photoContentType;
	private String staffID;
	private String result;
	private String search;
	/**
	 * 当前选中公司ID
	 */
	private String companyID;
	private int pageNumber;
	
	// ///////////////////////公司汇总/////////////////////
	public String toSearchSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staffResponsibilities);
		return getResponsibilitiesListSummary();
	}

	private DetachedCriteria getListSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria
				.forClass(StaffResponsibilities.class);
		dc.addOrder(Order.asc("companyID"));
		if (search != null && search.equals("search")) {
			staffResponsibilities = (StaffResponsibilities) session
					.get("tablesearch");
			if (!"".equals(staffResponsibilities.getCompanyID()))
				dc.add(Restrictions.eq("companyID", staffResponsibilities
						.getCompanyID()));
			else
				dc.add(Restrictions.in("companyID", companyService
						.getCompanyAndItsChildrenIDs((String) session.get("companyID"))));
			if (!"".equals(staffResponsibilities.getStaffCode()))
				dc.add(Restrictions.like("staffCode", staffResponsibilities
						.getStaffCode(), MatchMode.ANYWHERE));
			if (!"".equals(staffResponsibilities.getStaffName()))
				dc.add(Restrictions.like("staffName", staffResponsibilities
						.getStaffName(), MatchMode.ANYWHERE));
			if (!"".equals(staffResponsibilities.getPostName()))
				dc.add(Restrictions.like("postName", staffResponsibilities
						.getPostName(), MatchMode.ANYWHERE));
			if (!"".equals(staffResponsibilities.getDepartmentID())&&!staffResponsibilities.getCompanyID().equals(staffResponsibilities.getDepartmentID())) {
				dc.add(Restrictions.eq("departmentID", staffResponsibilities
						.getDepartmentID()));
			}
			
			return dc;
		}
		dc.add(Restrictions.in("companyID", companyService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"))));
		dc.addOrder(Order.asc("companyID"));
		dc.addOrder(Order.asc("staffID"));
		return dc;
	}

	public String getResponsibilitiesListSummary() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getListSummary());
		return "responsibilitiessummarylistSummary";
	}


	// JSON取得部门列表
	public String getoList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params1 = { this.companyID };
		String ohql = "from COrganization where companyID=? and Status = '00' order by organizationNumber";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationlist", organizationlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		organizationlist=null;
		return "success";

	}
	
	// JSON取得部门列表
	public String getSubDeptList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		try{
		HttpServletRequest request = ServletActionContext.getRequest();
		String checkDeptID = request.getParameter("checkDeptID");
		String companyID = request.getParameter("companyID");
		Object[] params1 = {companyID,checkDeptID};
		String ohql = "from COrganization where companyID=? and organizationPID = ? and Status = '00' order by organizationNumber";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationlist", organizationlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		organizationlist=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";

	}
	// 导出

	public String showExcelSummary() {
		excelStream = excelService.showExcel(StaffResponsibilities
				.columnHeadings(), baseBeanService.getListByDC(getListSummary()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");		
		CLogBook logBook = logBookService.saveCLogBook(null,"导出岗位职责", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	// ///////////////////////公司汇总/////////////////////

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staffResponsibilities);
		return getResponsibilitiesList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria
				.forClass(StaffResponsibilities.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if (search != null && search.equals("search")) {
			staffResponsibilities = (StaffResponsibilities) session
					.get("tablesearch");
			if (!("").equals(staffResponsibilities.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", staffResponsibilities
						.getStaffCode(), MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.like("staffName", staffResponsibilities
					.getStaffName(), MatchMode.ANYWHERE));
			if (staffResponsibilities.getPostName() != ""
					&& !("").equals(staffResponsibilities.getPostName())) {
				dc.add(Restrictions.like("postName", staffResponsibilities
						.getPostName(), MatchMode.ANYWHERE));
			}
			if (!companyID.equals(staffResponsibilities.getDepartmentID())) {
				dc.add(Restrictions.like("departmentID", staffResponsibilities
						.getDepartmentID(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	// 岗位职责汇总列表
	public String getResponsibilitiesList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params = { companyID };
		String staffhql = "from Staff where staffID in ( select staffID from COS where companyID=? and cosStatus='50' )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		String ohql = "from COrganization where companyID=?";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params);
		Company com = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params);
		COrganization org = new COrganization();
		org.setOrganizationID(com.getCompanyID());
		org.setOrganizationName(com.getCompanyName());
		organizationlist.add(org);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "responsibilitiessummarylist";
	}

	// 导出

	public String showExcel() {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		CAccount account = (CAccount) session.get("account");
//		String hql = " from COrganization where companyID = ? ";
//		Object[] params = { account.getCompanyID() };
//		Company com = (Company) baseBeanService.getBeanByHqlAndParams(
//				"from Company where companyID=?", params);
//		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
//				params);
//		COrganization org = new COrganization();
//		org.setOrganizationID(com.getCompanyID());
//		org.setOrganizationName(com.getCompanyName());
//		list.add(org);
//		Map<String, String> map = new HashMap<String, String>();
//		COrganization co;
//		for (BaseBean b : list) {
//			co = (COrganization) b;
//			map.put(co.getOrganizationID(), co.getOrganizationName());
//		}
//		StaffResponsibilities.setOMap(map);
		excelStream = excelService.showExcel(StaffResponsibilities
				.columnHeadings(), baseBeanService.getListByDC(getList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");		
		CLogBook logBook = logBookService.saveCLogBook(null,"导出岗位职责", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public StaffResponsibilities getStaffResponsibilities() {
		return staffResponsibilities;
	}

	public void setStaffResponsibilities(
			StaffResponsibilities staffResponsibilities) {
		this.staffResponsibilities = staffResponsibilities;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public Responsibilities getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(Responsibilities responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
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

	public List<BaseBean> getCompanylist() {
		return companylist;
	}

	public void setCompanylist(List<BaseBean> companylist) {
		this.companylist = companylist;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

}
