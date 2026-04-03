package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Responsibilities;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.StaffResponsibilities;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 部门岗位职责
 * 
 * @author Administrator
 * 
 */
@Controller
@Scope("prototype")
public class ResponsibilitiesAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private Responsibilities responsibilities;
	private StaffResponsibilities staffResponsibilities;
	private PageForm pageForm;
	private InputStream excelStream;
	private List<BaseBean> stafflist;
	private Staff staff;
	private List<BaseBean> organizationlist;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private String staffID;
	private String result;
	private String search;
	private int pageNumber;
	private List<BaseBean> beans;

	// 根据条件查询岗位职责
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staffResponsibilities);
		return getResponsibilitiesList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria
				.forClass(StaffResponsibilities.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("staffID", staffID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			staffResponsibilities = (StaffResponsibilities) session
					.get("tablesearch");
			if (staffResponsibilities.getPostName() != null
					&& !("").equals(staffResponsibilities.getPostName())) {
				dc.add(Restrictions.like("postName", staffResponsibilities
						.getPostName(), MatchMode.ANYWHERE));
			}
			if (staffResponsibilities.getDepartmentID() != null
					&& !("").equals(staffResponsibilities.getDepartmentID())) {
				dc.add(Restrictions.eq("departmentID", staffResponsibilities
						.getDepartmentID()));
			}
		}
		return dc;
	}

	// 岗位职责列表
	public String getResponsibilitiesList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = { companyID };
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		Company com = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params1);
		COrganization org = new COrganization();
		org.setOrganizationID(com.getCompanyID());
		org.setOrganizationName(com.getCompanyName());
		organizationlist.add(org);
		Object[] params = { staffID };
		staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=?", params);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 4 : pageNumber),
				getList());
		return "responsibilitieslist";
	}

	// 导出岗位职责列表

	public String showExcel() {
		excelStream = excelService.showExcel(StaffResponsibilities
				.columnHeadings(), baseBeanService.getListByDC(getList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String organizationID = (String) ActionContext.getContext()
				.getSession().get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出岗位职责", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// 岗位职责保存

	public String saveResponsibilities() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/responsibilities/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			responsibilities.setFileNum(photoPath);
		}
		if (responsibilities.getResponsibilitiesID() == null
				|| "".equals(responsibilities.getResponsibilitiesID())) {
			responsibilities.setResponsibilitiesID(serverService
					.getServerID("responsibilities"));
			parameter = "添加岗位职责";
		} else {
			parameter = "修改岗位职责";

		}
		String hql2 = "from Staff where staffID=?";
		staffID = responsibilities.getStaffID();
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { responsibilities.getStaffID() });
		parameter += "(人员名称:" + staff.getStaffName() + ")";
		responsibilities.setOrganizationID(organizationID);
		responsibilities.setCompanyID(account.getCompanyID());
		beans = new ArrayList<BaseBean>();
		beans.add(responsibilities);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return getResponsibilitiesList();
	}

	// 删除岗位职责
	public String delResponsibilities() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { responsibilities.getResponsibilitiesID() };
		String[] hql = { "delete from Responsibilities where responsibilitiesID=?" };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { staffID });
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除岗位职责(人员名称：" + staff.getStaffName() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}

	// JSON取得部门列表
	public String getoList() {
		Map<String, Object>  session=ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = account.getCompanyID();
		Object[] params1 =new  Object[1];
		params1[0]=companyID;
		String ohql = "from COrganization where companyID=? and Status = '00' order by organizationNumber";
		
		
		HttpServletRequest  request=ServletActionContext.getRequest();
		String series=request.getParameter("series");
		String level=request.getParameter("level");
		//-----------------//仅查询公司下一级部门 
		if(series!=null&&"one".equals(series.trim())){
			ohql = "from COrganization where organizationPID=? and Status = '00' order by organizationNumber";
			params1[0]=request.getParameter("companyID");
			//-----------------//仅查询当前部门 
			if(level!=null&&"organization".equals(level.trim())){
				ohql = "from COrganization where (organizationPID=? or organizationID=?) and  Status = '00' order by organizationNumber";
				params1=new  Object[2];
				params1[0]=organizationID;
				params1[1]=organizationID;
			}
			//-----------------//
		}
		//-----------------//
		
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		List<BaseBean> compareOList = getCutAcctOrg();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationlist", organizationlist);
		map.put("compareOList", compareOList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		organizationlist=null;
		compareOList=null;
		return "success";
	}

	/**
	 * 获取责任人为当前账户的所有部门
	 * 
	 * @return
	 */
	private List<BaseBean> getCutAcctOrg() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount acct = (CAccount) session.get("account");
		String hql = " from COrganization org where org.companyID = ? "
				+ "and org.organizationID in "
				+ "(select ag.organizationID from Agencies ag where ag.staffID =?)";
		return baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
				acct.getCompanyID(), acct.getStaffID() });
	}

	// 取得某员工的岗位职责
	public String getStaffRespons() {
		String hql = "from StaffResponsibilities where responsibilitiesID=?";
		Object[] params = { staffResponsibilities.getResponsibilitiesID() };
		staffResponsibilities = (StaffResponsibilities) baseBeanService
				.getBeanByHqlAndParams(hql, params);
		Object[] params2 = { staffResponsibilities.getStaffID() };
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=?", params2);
		result = staff.getPhoto();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params1 = { account.getCompanyID() };
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		Company com = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params1);
		COrganization org = new COrganization();
		org.setOrganizationID(com.getCompanyID());
		org.setOrganizationName(com.getCompanyName());
		organizationlist.add(org);
		return "staffrespons";
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
}
