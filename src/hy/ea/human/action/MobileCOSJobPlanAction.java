package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COSJobPlan;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.COSJobPlanVO;
import hy.ea.bo.human.vo.LokPlanPrintVO;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * 工作计划
 * */
@Controller
@Scope("prototype")
public class MobileCOSJobPlanAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private COSJobPlan jobPlan;
	private COSJobPlanVO jobPlanVO;
	private PageForm pageForm;
	private InputStream excelStream;
	private String staffID;
	private String search;
	private String personName;
	private String personSearch;
	private String companyID;
	private int pageNumber;
	@Resource
	private CompanyService companyService;
	private String parameter;
	private Map<String, COSJobPlan> jobPlanmap;
	private LokPlanPrintVO LokPlanPrintVO;
	private List<BaseBean> beans;
	
	// ////////////////////公司汇总////////
	public String toSearchCompanySummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobPlanVO);
		return getCompanyJobPlanListSummary();
	}

	public String getCompanyJobPlanListSummary() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getCompanyListSummary());
		return "companySummary";
	}

	public DetachedCriteria getCompanyListSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		companyID = (String) session.get("companyID");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobPlanVO.class);
		if (search != null && search.equals("search")) {
			jobPlanVO = (COSJobPlanVO) session.get("tablesearch");
			dc.add(Restrictions.like("staffName", jobPlanVO.getStaffName(),
					MatchMode.ANYWHERE));
			if(null!=jobPlanVO.getStartDate()&&null!=jobPlanVO.getEndDate())
			{
				dc.add(Restrictions.between("startDate", jobPlanVO.getStartDate(),
						jobPlanVO.getEndDate()));
			}
			if (!"".equals(jobPlanVO.getCompanyID()))
				dc.add(Restrictions.eq("companyID", jobPlanVO.getCompanyID()));
			else
				dc.add(Restrictions.in("companyID", companyService.getCompanyAndItsChildrenIDs(companyID)));
			if (!"".equals(jobPlanVO.getOrganizationID())&&!jobPlanVO.getCompanyID().equals(jobPlanVO.getOrganizationID()))
				dc.add(Restrictions.eq("organizationID", jobPlanVO
						.getOrganizationID()));
			dc.addOrder(Order.asc("startDate"));
			return dc;
		}
		dc.add(Restrictions.in("companyID", companyService
				.getCompanyAndItsChildrenIDs(companyID)));
		dc.addOrder(Order.asc("companyID"));
		dc.addOrder(Order.desc("entryDate"));
		return dc;
	}

	public String showExcelCompanySummary() {
		excelStream = excelService.showExcel(COSJobPlanVO.columnHeadings(),
				baseBeanService.getListByDC(getCompanyListSummary()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"导出工作计划", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// //////////////////公司汇总////////
	public String getJobPlanListSummary() {
		pageForm = baseBeanService.getPageFormByDC((pageNumber), (1), getListSummary());
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
		//return "listSummary";
	}
	/**
	 * ModfyTime:2010-11-4
	 * 如果输入时间为空，则查询所有与条件相符合的记录
	 * @return
	 */
	public DetachedCriteria getListSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobPlanVO.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			jobPlanVO = (COSJobPlanVO) session.get("tablesearch");
			dc.add(Restrictions.like("staffName", jobPlanVO.getStaffName(),
					MatchMode.ANYWHERE));
			
			if(null!=jobPlanVO.getStartDate()&&null!=jobPlanVO.getEndDate()){
				dc.add(Restrictions.between("startDate", jobPlanVO.getStartDate(),
						jobPlanVO.getEndDate()));
			}
			
		}
		dc.addOrder(Order.asc("companyID"));
		dc.addOrder(Order.desc("startDate"));
		return dc;
	}

	public String toSearchSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobPlanVO);
		return getJobPlanListSummary();
	}

	public String showExcelSummary() {
		excelStream = excelService.showExcel(COSJobPlanVO.columnHeadings(),
				baseBeanService.getListByDC(getListSummary()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"导出工作计划", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// //////////////////////////////////////////////////////////////////////
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobPlan);
		return getJobPlanList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobPlan.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		String[] hql2={"from Staff where staffID=?"};
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{account.getStaffID()});
		personName=staff.getStaffName();
		//这里staffID 是传过来的  
		if(staffID !=null && !"".equals(staffID)){
			dc.add(Restrictions.eq("staffID", staffID));
		}else{
			dc.add(Restrictions.eq("staffID", account.getStaffID()));
		}
		if (search != null && search.equals("search")) {
			jobPlan = (COSJobPlan) session.get("tablesearch");
			if ( jobPlan.getStartDate()!=null&&!"".equals( jobPlan.getStartDate())) {
			dc.add(Restrictions.between("startDate", jobPlan.getStartDate(),
					jobPlan.getEndDate()));
			}
		}
		dc.addOrder(Order.desc("startDate"));
		return dc;
	}

	public String getJobPlanList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		if(personSearch !=null && personSearch.equals("personSearch")){
			return "joblists";
		}
		return "list";
	}
	
	/**
	 * 得到个人工作计划
	 * @return
	 */
	public String getJobPlanLists() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String[] hql2={"from Staff where staffID=?"};
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{account.getStaffID()});
		if(staff==null){
			return "fail";
		}
		personName=staff.getStaffName();
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "joblists";
	}
	public String savePerson() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String hql3="from COS where companyID=? and cosStatus='50' and staffID=? ";
		COS cos = (COS)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{account.getCompanyID(),account.getStaffID()});
		String organizationID = cos.getOrganizationID();
		beans = new ArrayList<BaseBean>();
		if(jobPlanmap!=null){
		for (COSJobPlan job : jobPlanmap.values()) {
			job.setStaffID(account.getStaffID());
		if (job.getJobPlanID() == null || "".equals(job.getJobPlanID())) {
			job.setJobPlanID(serverService.getServerID("jobPlan"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String rDate = format.format(new Date());
			job.setEntryDate(rDate);
			parameter = "添加工作计划";
		}
		else
		{
		parameter = "修改工作计划";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startdate = null;
		Date enddate = null;
		try {
			startdate = sdf.parse(job.getStrStartDate());
			enddate = sdf.parse(job.getStrEndDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		job.setStartDate(startdate);
		job.setEndDate(enddate);
		String[] hql2={"from Staff where staffID=?"};
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{account.getStaffID()});
		parameter += "(人员名称:"+staff.getStaffName()+")";
		job.setOrganizationID(organizationID);
		job.setCompanyID(account.getCompanyID());
		beans.add(job);
		
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		}
		return "success";
	}
	public String del() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(), jobPlan.getJobPlanID() };
		String hql = "delete from COSJobPlan where companyID=?  and jobPlanID=?";
		String hql2="from Staff where staffID=?";
		Staff staff = null;
		if(jobPlan.getStaffID()!=null && !"".equals(jobPlan.getStaffID())){
			staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{jobPlan.getStaffID()});
		}
		if(jobPlan.getStaffID()==null || "".equals(jobPlan.getStaffID())){
			staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getStaffID()});
		}
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null,"删除工作计划(人员名称："+ staff.getStaffName()+")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql}, params);
		return "success";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(COSJobPlan.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"导出工作计划", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String save() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		if(jobPlanmap!=null){
		for (COSJobPlan job : jobPlanmap.values()) {
			//这里传过来 接收
			staffID=job.getStaffID();
		if (job.getJobPlanID() == null || "".equals(job.getJobPlanID())) {
			job.setJobPlanID(serverService.getServerID("jobPlan"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String rDate = format.format(new Date());
			job.setEntryDate(rDate);
			parameter = "添加工作计划";
		}
		else
		{
		parameter = "修改工作计划";
		}
		job.setStartDate(Utilities.getDateFromString(job.getStrStartDate(), "yyyy-MM-dd HH:mm:ss"));
		job.setEndDate(Utilities.getDateFromString(job.getStrEndDate(), "yyyy-MM-dd HH:mm:ss"));
		String[] hql2={"from Staff where staffID=?"};
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{job.getStaffID()});
		parameter += "(人员名称:"+staff.getStaffName()+")";
		job.setOrganizationID(organizationID);
		job.setCompanyID(account.getCompanyID());
		beans.add(job);
		
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		}
		return "success";
	}

	


	//打印某月的工作计划
	public String toDaYin() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql= "from COSJobPlan  where  staffID = ? and companyID = ? ";
		Object[] params = {jobPlan.getStaffID(), account.getCompanyID()};
		if (search != null && search.equals("search")) {
			
			COSJobPlan 	jobPlanVO = (COSJobPlan) session.get("tablesearch");
			if(null!=jobPlanVO.getStartDate()&&null!=jobPlanVO.getEndDate()){
				hql+=" and startDate >= ? and startDate <=  ?";
				Object[] params1 = {jobPlan.getStaffID(), account.getCompanyID(),jobPlanVO.getStartDate(),jobPlanVO.getEndDate()};
				params = params1;
			}
			hql+="order by startDate ";
		}
		List<BaseBean> logplanlist = baseBeanService.getListBeanByHqlAndParams(hql, params);
		String hql2="from Staff where staffID=?";
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{jobPlan.getStaffID()});
		if(logplanlist.size() > 0){
			    LokPlanPrintVO = new LokPlanPrintVO();
				LokPlanPrintVO.setCompanyID(account.getCompanyID());
				LokPlanPrintVO.setCompanyName(companyService.getCompanyByCompanyID(account.getCompanyID()).getCompanyName());
				LokPlanPrintVO.setStaffID(jobPlan.getStaffID());
				LokPlanPrintVO.setStaffName(staff.getStaffName());
				LokPlanPrintVO.setLogplanList(logplanlist);
	   	}
	    return "logplanPing";
	}
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public COSJobPlan getJobPlan() {
		return jobPlan;
	}

	public void setJobPlan(COSJobPlan jobPlan) {
		this.jobPlan = jobPlan;
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


	public COSJobPlanVO getJobPlanVO() {
		return jobPlanVO;
	}

	public void setJobPlanVO(COSJobPlanVO jobPlanVO) {
		this.jobPlanVO = jobPlanVO;
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

	public Map<String, COSJobPlan> getJobPlanmap() {
		return jobPlanmap;
	}

	public void setJobPlanmap(Map<String, COSJobPlan> jobPlanmap) {
		this.jobPlanmap = jobPlanmap;
	}

	public LokPlanPrintVO getLokPlanPrintVO() {
		return LokPlanPrintVO;
	}

	public void setLokPlanPrintVO(LokPlanPrintVO lokPlanPrintVO) {
		LokPlanPrintVO = lokPlanPrintVO;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonSearch() {
		return personSearch;
	}

	public void setPersonSearch(String personSearch) {
		this.personSearch = personSearch;
	}


}
