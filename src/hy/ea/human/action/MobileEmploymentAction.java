package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class MobileEmploymentAction extends ActionSupport {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private Staff searchCStaff;// 保存人员搜索信息
	private String search;// 判断是否搜索人员
	private PageForm pageForm;
	private String result;
	private String sub;
	private InputStream excelStream;
	private String staffID;
	private int pageNumber;
	private String[] args;
	private String parameter;

	// private String arg1;
	// private String arg2;

	/**
	 * 从招聘人员中删除
	 * 
	 * @return
	 */
	public String delCOS() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)) {
			CAccount account = (CAccount) session.get("account");
			String[] hql = { "update COS set cosStatus = ? where companyID = ? and organizationID = ? and staffID = ?" };
			Object[] params = { args[0], account.getCompanyID(),
					session.get("organizationID"), staffID };
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { staffID });
			logBookService.saveCLogBook(null, "删除招聘人员(人员名称："
					+ staff.getStaffName() + ")", account);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql,
					params);
		}
		return getStaffList();
	}

	/**
	 * 邀请面试
	 * 
	 * @return
	 */
	public String updateCOS() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)) {
			CAccount account = (CAccount) session.get("account");
			String[] hql = { "update COS set cosStatus= ? where companyID = ? and organizationID = ? and staffID = ?" };
			Object[] params = { args[1], account.getCompanyID(),
					session.get("organizationID"), staffID };

			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql,
					params);
		}
		return getStaffList();
	}

	/**
	 * 保存模糊查询
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffList();
	}

	/**
	 * 根据公司和机构ID查询本部门下的招聘信息
	 * 
	 * @return
	 */
	public String getStaffList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			searchCStaff = (Staff) ActionContext.getContext().getSession().get(
					"tablesearch");
		}
		String hql = "from Staff where staffID in (select staffID from COS where companyID = ?  and cosStatus = ? and organizationID = ? )";
		Object[] params = { account.getCompanyID(), "50",
				session.get("organizationID") };
		pageForm = baseBeanService.getPageForm((pageNumber), (1), hql, params);
		HttpServletResponse response = ServletActionContext.getResponse();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
		String outString = jsonArray.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(outString);
			//System.out.println(outString);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 支持财务责任人的查询
	 * 
	 * @version zg 2011-6-1
	 * @return
	 */
	public String getStaffForCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			searchStaff(account.getCompanyID());
			return "staffForCashier";
		}
		String hql = "from Staff where staffID in (select staffID from COS where companyID = ?  and cosStatus = ? and organizationID = ? )";
		Object[] params = { account.getCompanyID(), "50",
				session.get("organizationID") };
		// 查询教务处教练
		if (parameter != null && !parameter.equals("")) {
			String[] arr = parameter.split("-");
			if (arr.length != 0) {
				Object[] parm = { arr[0], "50", arr[1] };
				pageForm = baseBeanService.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 5 : pageNumber), hql, parm);
				return "staffForCashier";
			}
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
				params);
		return "staffForCashier";
	}

	/**
	 * 支持财务责任人条件查询
	 * 
	 * @version zg 2011-6-1
	 * @return
	 */
	public String toSearchStaffForCashier() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffForCashier();
	}

	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private void searchStaff(String companyID) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "from Staff where staffID in (select staffID from COS where cosStatus = ? and companyID = ? and organizationID = ? ) and ";
		String hql2 = "  staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}

		Object[] params = { args[3], companyID, session.get("organizationID"),
				"%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
				params);
	}
	
	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	@SuppressWarnings("unused")
	private PageForm SearchStaff(String companyID) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql1 = "from Staff where staffID in (select staffID from COS where cosStatus = ? and companyID = ? and organizationID = ? ) and ";
		String hql2 = "  staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}

		Object[] params = { args[3], companyID, session.get("organizationID"),
				"%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		/*pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
				params);*/
		pageForm = baseBeanService.getPageForm((pageNumber), (1), hql, params);
		return pageForm;
	}
	
	/**
	 * 根据公司和机构ID导出本部门下的招聘信息
	 * 
	 * @return
	 */
	public String showExcel() {
		if (search != null && search.equals("search")) {
			return showExcelForSearched();
		}
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");

		String hql = "from Staff where  staffID in (select staffID from COS where companyID = ? and cosStatus = ? and organizationID = ? ) ";
		Object[] params = { account.getCompanyID(), args[4],
				session.get("organizationID") };
		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		excelStream = excelService.showExcel(Staff.columnHeadings(), stafflist);
		return "showexcel";
	}

	private String showExcelForSearched() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		searchCStaff = (Staff) session.get("tablesearch");
		CAccount account = (CAccount) session.get("account");
		String hql1 = "from Staff where staffID in (select staffID from COS where cosStatus = ? and companyID = ? and organizationID = ? ) and ";
		String hql2 = "  staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		if (searchCStaff.getStaffCode() != null
				&& !"".equals(searchCStaff.getStaffCode())) {
			hql = hql1 + " staffCode like '%" + searchCStaff.getStaffCode()
					+ "%' and " + hql2;
		} else {
			hql = hql1 + hql2;
		}

		Object[] params = { args[3], account.getCompanyID(),
				session.get("organizationID"),
				"%" + searchCStaff.getStaffName() + "%",
				"%" + searchCStaff.getStaffIdentityCard() + "%" };
		List<BaseBean> stafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		excelStream = excelService.showExcel(Staff.columnHeadings(), stafflist);
		return "showexcel";
	}

	public String toSersonnelSystem() {

		return "SersonnelSystem";
	}

	public String toFinancial() {
		return "toFinancial";
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public void setArgs(String[] args) {
		this.args = args[0].split("_");
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
		
}
