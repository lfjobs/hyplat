package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentFileAttach;
import hy.ea.bo.office.StampLog;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 印章使用日志管理
 */
@Controller
@Scope("prototype")
public class StampLogAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private COrganizationService organizationService;
	private String parameter;
	private StampLog stampLog;
	private PageForm pageForm;
	private InputStream excelStream;
	private List<COrganization> orgnizationList;
	private String search;
	private int pageNumber;
	private Map<String, StampLog> stampLogmap;
	private Staff searchCStaff;// 保存人员搜索信息
	private String type;//company;group ,null区分汇总



	// 根据条件查询印章使用日志
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", stampLog);
		return getListStampLog();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(StampLog.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("enterpriseStampID", stampLog
				.getEnterpriseStampID()));
		dc.addOrder(Order.desc("createTime"));
		// 所有部门列表
		Map<String, String> ccmap = new HashMap<String, String>();
		orgnizationList = new ArrayList<COrganization>();
		orgnizationList = organizationService.getOrganizationList(
				organizationID, companyID);
		if (null != orgnizationList) {
			for (COrganization o : orgnizationList) {
				ccmap.put(o.getOrganizationID(), o.getOrganizationName());
			}
		}
		StampLog.setOMap(ccmap);
		if (search != null && search.equals("search")) {
			stampLog = (StampLog) session.get("tablesearch");
			if (stampLog.getContractors() != null
					&& !"".equals(stampLog.getContractors())) {
				dc.add(Restrictions.like("contractors", stampLog
						.getContractors(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	// 印章使用日志列表
	public String getListStampLog() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String gore = req.getParameter("gore");

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getList());
		if (gore == null || gore.equals("")) {
			gore = stampLog.getGore();
		}
		if (gore.equals("e")) {
			return "stamplog";
		} else {
			return "generallog";
		}
	}

	// 印章使用日志导出

	public String showExcel() {
		excelStream = excelService.showExcel(StampLog.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				"导出印章使用日志", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	// 印章使用日志保存

	public String saveStampLog() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		String hql = "from Staff where staffID = ?";
		if (stampLogmap != null) {
			for (StampLog stampLogs : stampLogmap.values()) {
				stampLogs.setEnterpriseStampID(stampLog.getEnterpriseStampID());

				Staff staff1 = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql, new Object[] { stampLogs.getContractorsId() });
				Staff staff2 = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql, new Object[] { account.getStaffID() });
				if (staff1 != null) {
					stampLogs.setContractorsCode(staff1.getStaffCode());
				}
				stampLogs.setAddType("手动");
				stampLogs.setStampDate(Utilities.getDateFromString(stampLogs
						.getStampDateStr(), "yyyy-MM-dd hh:mm"));
				stampLogs.setCreateTime(new Date());
				stampLogs.setCreator(staff2.getStaffName());
				if (stampLogs.getScanAttach() != null
						&& !stampLogs.getScanAttach().equals("")) {
					stampLogs.setScanAttach(stampLogs.getScanAttach().replace(
							"\\", "\\\\"));
					stampLogs.setExt("W");
				}
				if (stampLogs.getStampLogID() == null
						|| "".equals(stampLogs.getStampLogID())) {
					stampLogs.setStampLogID(serverService
							.getServerID("stampLog"));
					parameter = "添加印章使用日志(承办单位人:" + stampLogs.getContractors()
							+ ")";
				} else {
					String hql2 = "from StampLog where companyID=?  and stampLogID=?";
					StampLog network = (StampLog) baseBeanService
							.getBeanByHqlAndParams(hql2, new Object[] {
									account.getCompanyID(),
									stampLogs.getStampLogID() });
					parameter = "修改印章使用日志(承办单位人:" + network.getContractors()
							+ ")";

				}
				stampLogs.setCompanyID(account.getCompanyID());
				stampLogs.setOrganizationID(organizationID);
				baseBeanList.add(stampLogs);
				CLogBook logbook = logBookService.saveCLogBook(organizationID,
						parameter, account);
				baseBeanList.add(logbook);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		}
		return "success";
	}

	// 删除印章使用日志
	public String delStampLog() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), stampLog.getStampLogID() };
		String hql2 = "from StampLog where companyID=?  and stampLogID=?";
		StampLog network = (StampLog) baseBeanService.getBeanByHqlAndParams(
				hql2, params);
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				"删除印章使用日志(承办单位人:" + network.getContractors() + ")", account);
		String[] hql = { "delete from StampLog where companyID=?  and stampLogID=?" };
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}

	// 当盖章时记录使用日志
	public String addStampRecord() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			// String organizationID = (String) session.get("organizationID");
			if (account == null) {

				return "not_login";
			}

			String hql = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID() });
			String companyID = account.getCompanyID();
			HttpServletRequest request = ServletActionContext.getRequest();
			String enterpriseStampID = request
					.getParameter("enterpriseStampID");// 印章ID
			String fileId = request.getParameter("fileId");// 附件的ID
			if (fileId == null || "null".equals(fileId)) {
				return "success";
			}
			String hql2 = "from DocumentFileAttach where fileId = ?";
			DocumentFileAttach dfa = (DocumentFileAttach) baseBeanService
					.getBeanByHqlAndParams(hql2, new Object[] { fileId });
			Document doc = dfa.getDocument();
			Date curtime = new Date(System.currentTimeMillis());
			StampLog stampLogs = new StampLog();
			if (stampLogs.getStampLogID() == null
					|| "".equals(stampLogs.getStampLogID())) {
				stampLogs.setStampLogID(serverService.getServerID("stampLog"));
			}
			stampLogs.setFileName(dfa.getFileShowName() + "." + dfa.getExt());
			stampLogs.setScanAttach(dfa.getFilePath().replace("\\", "\\\\"));
			stampLogs.setExt(dfa.getFileType());
			stampLogs.setDocTitle(doc.getTitle());
			stampLogs.setContractorsId(account.getStaffID());
			stampLogs.setContractorsCode(staff.getStaffCode());
			stampLogs.setContractors(staff.getStaffName());
			stampLogs.setCreator(staff.getStaffName());
			stampLogs.setCompanyID(companyID);
			stampLogs.setStampDate(curtime);
			stampLogs.setCreateTime(curtime);
			stampLogs.setEnterpriseStampID(enterpriseStampID);
			stampLogs.setFileId(fileId);
			stampLogs.setOperateType("stamp");
			stampLogs.setAddType("自动");
			baseBeanService.save(stampLogs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 
	 * 
	 * 获得正式员工列表
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffformalList();
	}

	public String getStaffformalList() {
		try{
		Map<String, Object> session = ActionContext.getContext().getSession();
		// session.put("session_value", Math.random() + "");//
		// 在不使用token的情况下,手动防止重复提交
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {
			searchStaff(account.getCompanyID());
			return "formallist";
		}
		String hql = "from Company where companyID = ?";
		Company company = (Company)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getCompanyID()});
		
		hql = "from Staff s where staffID in (select staffID from COS c where c.staffID=s.staffID and cosStatus = ?) and groupCompanySn = ?";
		Object[] params = { "50",company.getGroupCompanySn()};
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
				params);
		}catch(Exception e){
			e.printStackTrace();
		}

		return "formallist";
	}

	/**
	 * 按条件搜索人员输出列表 被调用
	 * 
	 * @param companyID
	 * @return
	 */
	private void searchStaff(String companyID) {
		searchCStaff = (Staff) ActionContext.getContext().getSession().get(
				"tablesearch");
		String hql3 = "from Company where companyID = ?";
		Company company = (Company)baseBeanService.getBeanByHqlAndParams(hql3,new Object[]{companyID});
		String hql1 = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and cosStatus = ?) and groupCompanySn = ? and ";
		String hql2 = " staffCode like ? and staffName like ? and staffIdentityCard like ?  order by staffID desc ";
		String hql = "";
		hql = hql1 + hql2;
		try {
			Object[] params = { "50", company.getGroupCompanySn(),
					"%" + searchCStaff.getStaffCode() + "%",
					"%" + searchCStaff.getStaffName() + "%",
					"%" + searchCStaff.getStaffIdentityCard() + "%" };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StampLog getStampLog() {
		return stampLog;
	}

	public void setStampLog(StampLog stampLog) {
		this.stampLog = stampLog;
	}

	public Map<String, StampLog> getStampLogmap() {
		return stampLogmap;
	}

	public void setStampLogmap(Map<String, StampLog> stampLogmap) {
		this.stampLogmap = stampLogmap;
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

	public List<COrganization> getOrgnizationList() {
		return orgnizationList;
	}

	public void setOrgnizationList(List<COrganization> orgnizationList) {
		this.orgnizationList = orgnizationList;
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
