package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COSJobTask;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.COSJobTaskVO;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/*
 * 工作目标任务
 * */
@Controller
@Scope("prototype")
public class MobileCOSJobTaskAction {
	private static final Logger logger = LoggerFactory.getLogger(MobileCOSJobTaskAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private COSJobTask jobTask;
	private COSJobTaskVO jobTaskVO;
	private PageForm pageForm;
	private InputStream excelStream;
	private String staffID;
	private String result;
	private String search;
	private String companyID;
	private List<BaseBean> beans;
	private int pageNumber;
	private Map<String, COSJobTask> jobTaskmap;

	// ////////////////////公司汇总////////
	public String toSearchCompanySummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobTaskVO);
		return getCompanyJobTaskListSummary();
	}

	public String getCompanyJobTaskListSummary() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getCompanyListSummary());
		return "companySummary";
	}

	public DetachedCriteria getCompanyListSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		companyID = (String) session.get("companyID");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobTaskVO.class);
		if (search != null && search.equals("search")) {
			jobTaskVO = (COSJobTaskVO) session.get("tablesearch");
			dc.add(Restrictions.like("staffName", jobTaskVO.getStaffName(),
					MatchMode.ANYWHERE));
			if (null != jobTaskVO.getStartDate()
					&& null != jobTaskVO.getEndDate()) {
				dc.add(Restrictions.between("startDate", jobTaskVO
						.getStartDate(), jobTaskVO.getEndDate()));
			}
			if (!"".equals(jobTaskVO.getCompanyID()))
				dc.add(Restrictions.eq("companyID", jobTaskVO.getCompanyID()));
			else
				dc.add(Restrictions.in("companyID", companyService
						.getCompanyAndItsChildrenIDs(companyID)));
			if (!"".equals(jobTaskVO.getOrganizationID())
					&& !jobTaskVO.getCompanyID().equals(
							jobTaskVO.getOrganizationID()))
				dc.add(Restrictions.eq("organizationID", jobTaskVO
						.getOrganizationID()));
			dc.addOrder(Order.asc("startDate"));
			return dc;
		}
		dc.add(Restrictions.in("companyID", companyService
				.getCompanyAndItsChildrenIDs(companyID)));
		dc.addOrder(Order.desc("jobTaskID"));
		return dc;
	}

	public String showExcelCompanySummary() {
		excelStream = excelService.showExcel(COSJobTaskVO.columnHeadings(),
				baseBeanService.getListByDC(getCompanyListSummary()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工作目标任务",
				account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// //////////////////公司汇总////////
	/**
	 * 工作目标任务汇总
	 * 
	 * @return
	 */
	public String getJobTaskListSummary() {
		pageForm = baseBeanService.getPageFormByDC((pageNumber), (1),
				getListSummary());
		HttpServletResponse response = ServletActionContext.getResponse();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
		String outString = jsonArray.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(outString);
			//logger.info("值：{}", outString);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return null;
		// return "listSummary";
	}

	/**
	 * ModfyTime:2010-11-4 如果输入时间为空，则查询所有与条件相符合的记录
	 * 
	 * @return
	 */
	private DetachedCriteria getListSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobTaskVO.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			jobTaskVO = (COSJobTaskVO) session.get("tablesearch");
			dc.add(Restrictions.like("staffName", jobTaskVO.getStaffName(),
					MatchMode.ANYWHERE));
			if (null != jobTaskVO.getStartDate()
					&& null != jobTaskVO.getEndDate()) {
				dc.add(Restrictions.between("startDate", jobTaskVO
						.getStartDate(), jobTaskVO.getEndDate()));
			}
		}
		dc.addOrder(Order.asc("status"));
		return dc;
	}

	public String toSearchSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobTaskVO);
		return getJobTaskListSummary();
	}

	public String showExcelSummary() {
		excelStream = excelService.showExcel(COSJobTaskVO.columnHeadings(),
				baseBeanService.getListByDC(getListSummary()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工作目标任务",
				account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobTask);
		return getJobTaskList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobTask.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", staffID));
		if (search != null && search.equals("search")) {
			jobTask = (COSJobTask) session.get("tablesearch");
			dc.add(Restrictions.between("startDate", jobTask.getStartDate(),
					jobTask.getEndDate()));
		}
		dc.addOrder(Order.desc("jobTaskID"));
		return dc;
	}

	public String getJobTaskList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 4 : pageNumber),
				getList());
		return "list";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(COSJobTask.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String organizationID = (String) ActionContext.getContext()
				.getSession().get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出工作目标任务", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String save() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		jobTask = new COSJobTask();
		beans = new ArrayList<BaseBean>();
		if (null != jobTaskmap) {
			logger.info("调试信息");
			for (COSJobTask jbt : jobTaskmap.values()) {
				this.staffID = jbt.getStaffID();
				if (null == jbt.getJobTaskID() || "".equals(jbt.getJobTaskID())) {
					jbt.setJobTaskID(serverService.getServerID("jobTask"));
					parameter = "添加工作目标任务";
				} else {
					parameter = "修改工作目标任务";
				}
				String[] hql2 = { "from Staff where staffID=?" };
				jbt.setCompanyID(account.getCompanyID());
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2[0], new Object[] { jbt.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				jbt.setOrganizationID(organizationID);
				beans.add(jbt);
			}
			CLogBook logBook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "succ";
	}

	public String del() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), jobTask.getJobTaskID() };
		String hql = "delete from COSJobTask where companyID=?  and jobTaskID=?";
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { jobTask.getStaffID() });
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除工作目标任务(人员名称：" + staff.getStaffName() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		this.staffID = jobTask.getStaffID();
		return "succ";
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

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
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

	public COSJobTask getJobTask() {
		return jobTask;
	}

	public void setJobTask(COSJobTask jobTask) {
		this.jobTask = jobTask;
	}

	public COSJobTaskVO getJobTaskVO() {
		return jobTaskVO;
	}

	public void setJobTaskVO(COSJobTaskVO jobTaskVO) {
		this.jobTaskVO = jobTaskVO;
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

	public Map<String, COSJobTask> getJobTaskmap() {
		return jobTaskmap;
	}

	public void setJobTaskmap(Map<String, COSJobTask> jobTaskmap) {
		this.jobTaskmap = jobTaskmap;
	}
}
