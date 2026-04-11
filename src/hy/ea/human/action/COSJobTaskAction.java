 package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.Projectplanbudget;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COSJobTask;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.COSJobTaskVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
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
 * 工作目标任务
 * */
@Controller
@Scope("prototype")
public class COSJobTaskAction {
	private static final Logger logger = LoggerFactory.getLogger(COSJobTaskAction.class);
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
	@Resource
	private CCodeService codeService;
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
	private Map<String,COSJobTask> jobTaskmap;
	private String personName;
	private List<CCode> scoreSortlist;//任务类别ID
	private  List<BaseBean> costlist;//项目列表
	private List<BaseBean> companylist;
	private List<BaseBean> orglist;
	private String summarytype;
	private String start;
	private String end;
	
	//////////////////////////孟竹添加开始//////////////////////////
	/**
	 * 
	 * 添加页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addTaskPage(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		//任务类型
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),"scode201304166n47qjx7re0000000003");
		//获取未完成项目
		
		String hql = "from Projectplanbudget where staffid = ? and companyid = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getStaffID(),account.getCompanyID()});
		Projectplanbudget pp = new Projectplanbudget();
	    pp.setProjectname("其他");
	    pp.setCsbid("其他");
	    beans.add(pp);
		return "addtaskp";
	}
	
	
	/**
	 * 
	 * 
	 * 任务保存
	 * @return
	 */
	public String saveTaskTarget() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String orghql = "from COS where staffID = ? and cosStatus='50' and status = '01'";
		COS cos = (COS) baseBeanService.getBeanByHqlAndParams(orghql,new Object[]{account.getStaffID()});
		beans = new ArrayList<BaseBean>();
		if(null!=jobTaskmap){
			for(COSJobTask jbt:jobTaskmap.values()){
				
				if (null == jbt.getJobTaskID() || "".equals(jbt.getJobTaskID())) {
					jbt.setJobTaskID(serverService.getServerID("jobTask"));
					parameter = "添加工作目标任务";
				}
				else
				{
					parameter = "修改工作目标任务";
				}
				jbt.setCodeValue(getcodeValue(jbt.getCodeID()));
			    jbt.setStaffID(account.getStaffID());
				String hql2 = "from Staff where staffID = ?";
				jbt.setCompanyID(account.getCompanyID());
				Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getStaffID()});
				parameter += "(人员名称:"+staff.getStaffName()+")";
				
				
				jbt.setOrganizationID(cos.getOrganizationID());
				jbt.setStaffName(staff.getStaffName());
				jbt.setProjectID(jobTask.getProjectID());
				jbt.setProjectName(jobTask.getProjectName());
				jbt.setDatemonth(Utilities.getDateFromString(start,"yyyy-MM"));
				jbt.setStartDate(jobTask.getStartDate());
				logger.info("值：{}", start);
				jbt.setEndDate(jobTask.getEndDate());
				jbt.setGroupCompanySn((String)session.get("groupCompanySn"));
				beans.add(jbt);
			}
			CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "succ";
	}
	
	
	/**
	 * 
	 * 任务跟踪
	 * @return
	 */
	public String getTaskTrack(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
	
		companylist = new ArrayList<BaseBean>();
		orglist = new ArrayList<BaseBean>();	
		costlist= new ArrayList<BaseBean>();
			
		String hqlc = "from COSJobTask c where c.projectID is not null ";
		List<Object> paramlist = new ArrayList<Object>();
		if (summarytype.equals("dept")) {
			hqlc += " and c.organizationID = ?";
			paramlist.add(organizationID);
			
			// 获取发给我的项目未完成的项目

			String hql = "from Projectplanbudget where organizationid = ?";

			costlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
					(String)session.get("organizationID")});

		}
		if (summarytype.equals("com")) {
			hqlc += " and c.companyID = ?";
			paramlist.add(account.getCompanyID());

			String hql1 = "from COrganization where companyID = ? and organizationPID = ? and Status ='00' order by organizationNumber";
			orglist = baseBeanService
					.getListBeanByHqlAndParams(hql1, new Object[] {account.getCompanyID(),
							account.getCompanyID() });
			String hql = "from Projectplanbudget where companyid = ?";
			costlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {account.getCompanyID()});

		}
		if (summarytype.equals("group")) {
			if(search==null||!search.equals("search")){
			hqlc += " and c.companyID = ?";
			paramlist.add(account.getCompanyID());
			}

			String hqlcom = "from Company where groupCompanySn = ?";
			companylist = baseBeanService.getListBeanByHqlAndParams(hqlcom,
					new Object[] { (String) session.get("groupCompanySn") });
			
			String hql = "from Projectplanbudget where companyid = ?";
			costlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {account.getCompanyID()});
           
		}

		
	

		if (search != null && search.equals("search")) {
			if (jobTask != null) {
				if (jobTask.getCompanyID() != null
						&&!jobTask.getCompanyID().equals("")) {
					hqlc += " and c.companyID = ?";
					paramlist.add(jobTask.getCompanyID());
				}else{
					hqlc += " and c.groupCompanySn = ?";
					paramlist.add((String)session.get("groupCompanySn"));
				}
				if (jobTask.getOrganizationID() != null
						&& !jobTask.getOrganizationID().equals("")) {
					hqlc += " and c.organizationID = ?";
					paramlist.add(jobTask.getOrganizationID());
				}
				if (jobTask.getProjectID() != null
						&& !jobTask.getProjectID().equals("")) {
					hqlc += " and c.projectID = ?";
					paramlist.add(jobTask.getProjectID());
				}
				if (jobTask.getStaffName() != null
						&& !jobTask.getStaffName().equals("")) {
					hqlc += " and c.staffName like ?";
					paramlist.add("%"+jobTask.getStaffName()+"%");
				}
				Date startD = null;
				Date endD = null;
				if (start != null && !start.equals("")) {
					startD = Utilities.getDateFromString(start, "yyyy-MM");
					if (end != null && !end.equals("")) {
						endD = Utilities.getDateFromString(end, "yyyy-MM");
					} else {
						endD = new Date();
					}

					hqlc += " and (c.datemonth between  ? and  ?)";
					paramlist.add(startD);
					paramlist.add(endD);

				}

			}

		}

		beans = baseBeanService.getListBeanByHqlAndParams(hqlc,
				paramlist.toArray());
		HttpServletRequest request =(HttpServletRequest)actionContext.get(ServletActionContext.HTTP_REQUEST);
		
		String mk =request.getParameter("monthK");
		if(mk!=null){
			actionContext.put("monthK",mk);
		}
		return "tasktrack";
	}
	
	
	/***
	 * 
	 * 
	 * 根据部门查询项目计划
	 * @return
	 */
	public String getProjectByOrg(){
		
		String hql = "from Projectplanbudget where organizationid = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{jobTask.getOrganizationID()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectlist", list);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	///////////////////////////孟竹添加结束/////////////////////
	
	// ////////////////////公司汇总////////
	public String toSearchCompanySummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobTaskVO);
		return getCompanyJobTaskListSummary();
	}

	/**
	 * 加载任务类型
	 * @return
	 */
	private void getCCodeList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		//任务类型
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
																"scode201304166n47qjx7re0000000003");
	} 
	
	public String getCompanyJobTaskListSummary() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getCompanyListSummary());
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
			if(null!=jobTaskVO.getStartDate()&&null!=jobTaskVO.getEndDate())
			{
				dc.add(Restrictions.between("startDate",jobTaskVO.getStartDate(),
						jobTaskVO.getEndDate()));
			}
			if (!"".equals(jobTaskVO.getCompanyID()))
				dc.add(Restrictions.eq("companyID", jobTaskVO.getCompanyID()));
			else
				dc.add(Restrictions.in("companyID", companyService
						.getCompanyAndItsChildrenIDs(companyID)));
			if (!"".equals(jobTaskVO.getOrganizationID())&&!jobTaskVO.getCompanyID().equals(jobTaskVO.getOrganizationID()))
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
		CLogBook logBook = logBookService.saveCLogBook(null,"导出工作目标任务", account);
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
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getListSummary());
		return "listSummary";
	}
	/**
	 * ModfyTime:2010-11-4
	 * 如果输入时间为空，则查询所有与条件相符合的记录
	 * @return
	 */
	private DetachedCriteria getListSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobTaskVO.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			jobTaskVO = (COSJobTaskVO) session.get("tablesearch");
			dc.add(Restrictions.like("staffName", jobTaskVO.getStaffName(), MatchMode.ANYWHERE));
			if(null!=jobTaskVO.getStartDate()&&null!=jobTaskVO.getEndDate())
			{
				dc.add(Restrictions.between("startDate", jobTaskVO.getStartDate(),
						jobTaskVO.getEndDate()));
			} 
		}
		dc.addOrder(Order.asc("status"));
		dc.addOrder(Order.desc("startDate"));
		dc.addOrder(Order.desc("staffName"));
		dc.addOrder(Order.desc("taskNumber"));
		
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
		CLogBook logBook = logBookService.saveCLogBook(null,"导出工作目标任务", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobTask);
		return getJobTaskList();
	}

	/**
	 * 加载
	 * @return
	 */
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
				.getPageNumber() : 1), (pageNumber==0?4:pageNumber), getList());
		return "list";
	}
	/**
	 * 个人工作目标任务
	 * @return
	 */
	public String getJobTaskList_a() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String[] hql2 = { "from Staff where staffID=?" };
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2[0],
				new Object[] { account.getStaffID() });
		personName = staff.getStaffName();
		staffID = staff.getStaffID();
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?4:pageNumber), getList_a());
		getCCodeList();
		return "list_a";
	}
	public String toSearch_a() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobTask);
		return getJobTaskList_a();
	}

	private DetachedCriteria getList_a() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobTask.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", account.getStaffID()));
		if (search != null && search.equals("search")) {
			jobTask = (COSJobTask) session.get("tablesearch");
			dc.add(Restrictions.between("startDate", jobTask.getStartDate(),
					jobTask.getEndDate()));
		}
		dc.addOrder(Order.desc("jobTaskID"));
		return dc;
	}
	public String showExcel() {
		excelStream = excelService.showExcel(COSJobTask.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		String organizationID = (String)ActionContext.getContext().getSession().get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出工作目标任务", account);
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
		if(null!=jobTaskmap){
			//logger.info("调试信息");
			for(COSJobTask jbt:jobTaskmap.values()){
				this.staffID = jbt.getStaffID();
				if (null == jbt.getJobTaskID() || "".equals(jbt.getJobTaskID())) {
					jbt.setJobTaskID(serverService.getServerID("jobTask"));
					parameter = "添加工作目标任务";
				}
				else
				{
					parameter = "修改工作目标任务";
				}
				jbt.setCodeValue(getcodeValue(jbt.getCodeID()));
				String[] hql2={"from Staff where staffID=?"};
				jbt.setCompanyID(account.getCompanyID());
				Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{jbt.getStaffID()});
				parameter += "(人员名称:"+staff.getStaffName()+")";
				jbt.setOrganizationID(organizationID);
				beans.add(jbt);
			}
			CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "succ";
	}

	public String getcodeValue(String codeid){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		//任务类型
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
																"scode201304166n47qjx7re0000000003");
		for(int i = 0 ; i < scoreSortlist.size() ; i++){
			if(scoreSortlist.get(i).getCodeID().equals(codeid)){
				return scoreSortlist.get(i).getCodeValue();
			}
		}
		return "";
	}
	public String del() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), jobTask.getJobTaskID() };
		String hql = "delete from COSJobTask where companyID=?  and jobTaskID=?";
		String hql2="from Staff where staffID=?";
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{jobTask.getStaffID()});
		beans = new ArrayList<BaseBean>();
	    CLogBook logBook = logBookService.saveCLogBook(organizationID,"删除工作目标任务(人员名称："+ staff.getStaffName()+")", account);
	    beans.add(logBook);
	    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql}, params);
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public List<CCode> getScoreSortlist() {
		return scoreSortlist;
	}

	public void setScoreSortlist(List<CCode> scoreSortlist) {
		this.scoreSortlist = scoreSortlist;
	}


	public List<BaseBean> getCostlist() {
		return costlist;
	}


	public void setCostlist(List<BaseBean> costlist) {
		this.costlist = costlist;
	}


	public String getSummarytype() {
		return summarytype;
	}


	public void setSummarytype(String summarytype) {
		this.summarytype = summarytype;
	}


	public List<BaseBean> getBeans() {
		return beans;
	}


	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public List<BaseBean> getCompanylist() {
		return companylist;
	}


	public void setCompanylist(List<BaseBean> companylist) {
		this.companylist = companylist;
	}


	public List<BaseBean> getOrglist() {
		return orglist;
	}


	public void setOrglist(List<BaseBean> orglist) {
		this.orglist = orglist;
	} 
	
}
