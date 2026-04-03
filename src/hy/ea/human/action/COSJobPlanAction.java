package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COSJobPlan;
import hy.ea.bo.human.JobplanSta;
import hy.ea.bo.human.Jobplanrecord;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.COSJobPlanVO;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.bo.human.vo.LokPlanPrintVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 工作计划
 * */
@Controller
@Scope("prototype")
public class COSJobPlanAction {
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
	private String codeID;//任务类别
	private String personName;
	private String personSearch;
	private String companyID;
	private int pageNumber;
	@Resource
	private CompanyService companyService;
	@Resource
	private CCodeService codeService;
	private String parameter;
	private Map<String, COSJobPlan> jobPlanmap;
	private Map<String, Staff> staffmap;
	private LokPlanPrintVO LokPlanPrintVO;
	private List<BaseBean> beans;
	private List<CCode> scoreSortlist;//任务类别ID
	/*
	 * 工作计划汇总
	 */
	private List<BaseBean> jobPlanSummarys;
	private String startDate;
	private String endDate;
	private List<List<BaseBean>> maps = new ArrayList<List<BaseBean>>(); 
	private Integer num;
	private Staff staff; //起草人
	private String result;
	private Jobplanrecord jpr  ;
	private Map<String, String> oMap1;
	private Map<String,JobplanSta> jobstamap;
	private JobplanSta jobplansta;
	
	
	/**
	 * 工作计划进度
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getJobplanRecord(){
		System.err.println(jobPlan.getJobPlanID());
		// 、、主表计划
		jobPlan = (COSJobPlan)baseBeanService.getBeanByHqlAndParams("from COSJobPlan j where j.jobPlanID = ?", new Object[]{jobPlan.getJobPlanID()});
		// .. 责任人表
		String sql = "select j.staffname,j.stusts,c.companyname,o.organizationname from dt_jobplan_sta j" +
				" join dtcompany c on c.companyid =j.companyid" +
				" join dtcorganization o on o.organizationid = j.orgID" +
				" where j.jobplanid = ? ";
		List list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{jobPlan.getJobPlanID()});
		String html = "<tr><th colspan='2'>工作计划进度流程</th></tr>" +
				" <tr><td height='30' align='center'>项目名称：</td><td>"+jobPlan.getJobName()+"</td></tr>" +
				" <tr><td height='30' align='center'>项目编号：</td><td>"+jobPlan.getProjectcode()+"</td></tr>" +
				" <tr><td height='30' align='center'>项目内容：</td><td width='300'>"+jobPlan.getJobContent()+"</td></tr>" +
				" <tr><td height='30' align='center'>项目发布人：</td><td>"+jobPlan.getStaffNameS()+"</td></tr>";
		String htmlt = "";
		int j = 0;
		if( list != null ){
			htmlt += "<tr><td colspan='2' align='center'><div style='heigt:150px;width:590px;overflow-x:scroll;'>";
			htmlt += "<div style='height:150px; width:"+255*list.size()+"px'>";
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[])list.get(i); 
				htmlt += "<div style='float:left;width:250px;overflow:hidden;margin-left:2px;border:1px solid #ccc;'><table>" +
						"<tr><td height='30'>责任人公司：</td><td>"+obj[2]+"</td></tr>" +
						"<tr><td height='30'>责任人部门：</td><td>"+obj[3]+"</td></tr>" +
						"<tr><td height='30'>责任人：</td><td>"+obj[0]+"</td></tr>" +
						"<tr><td height='30'>状态：</td><td>";
					if(obj[1].equals("00")){
						htmlt += "<span style='color:orange'>待办</span>";
					}else if(obj[1].equals("01")){
						htmlt += "<span style='color:blue'>已办</span>";
					}else if(obj[1].equals("02")){
						htmlt += "<span style='color:green'>完成</span>";
						j++;
					}else {
						htmlt += "<span style='color:gray'>退回</span>";
					}
				htmlt += "</td></tr></table></div>";
			}
			htmlt += "</div></div></td></tr>";
		}
		if(j == list.size()){
			html += htmlt +"<tr><td height='60' align='center'>项目完成情况：</td><td><span style='color:green'>完成</span></td></tr>";
		}else{
			html += htmlt +"<tr><td height='60' align='center'>项目完成情况：</td><td>"+j+" / "+list.size()+"</td></tr>";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("html", html);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		
		return "success";
	}
	/**
	 * 人事生产 工作计划
	 * @return
	 */
	public String toSearchdate(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobPlan);
		return getjobPlanListdate();
	}
	/**
	 * 人事生产 工作计划
	 * @return
	 */
	public String getjobPlanListdate(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getLists(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")),params);		
		
		//任务类型
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
															"scode201304166n47qjx7re0000000003");
		String[] hql2 = { "from Staff where staffID=?" };
		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2[0],
				new Object[] { account.getStaffID() });
		return "getjobPlanLists";
	}
	/**
	 * 人事生产 工作计划
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object>  getLists(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String  sql = "select distinct j.jobplanid,j.entrydate,j.startdate,j.enddate,j.projectcode," +
				" j.jobname,j.codevalue,j.jobstatus,j.jobcontent,j.entry,j.staffnames," +
				" j.supervisor,j.humansupervisor,j.manager," +
				" j.jobplankey,j.staffid,j.staffids,j.companyid,j.companyids,j.organizationid,j.staffname,j.codeid," +
				" j.postmanage,j.noentry,j.projectdetailscode,j.projectrequirements," +
				" j.fraction,j.points,j.actualscore,j.taskname,j.projectpercentage ," +
				" j.plancheck,j.detailsfraction,j.actualdetailsscore,j.strstartdate,j.strenddate" +
				" from dtcosjobplan j " +
				" left join dt_jobplan_sta s on j.jobPlanID = s.jobPlanID" +
				" where ( j.companyid = ? or s.companyid = ? or j.companyids = ?) and j.entry = ? and j.jobstatus = ?";
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		parms.add(jobPlan.getEntry());
		parms.add(jobPlan.getJobstatus());
		if (search != null && search.equals("search")) {
			jobPlan = (COSJobPlan) session.get("tablesearch");
			if (jobPlan.getJobName() != null
					&& !"".equals(jobPlan.getJobName())) {
				sql += " and j.jobName like ? ";
				parms.add("%"+jobPlan.getJobName()+"%");
			}
			if (jobPlan.getStartDate() != null
					&& !"".equals(jobPlan.getStartDate())) {
				sql += " and j.startDate between ? and ? ";
				parms.add(jobPlan.getStartDate());
				parms.add(jobPlan.getEndDate());
			}
		}
		sql += " order by j.startDate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
		
		
	}
	/**
	 * 人事生产 工作计划
	 * @return
	 */
	public String saveJObPlan(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
				new Object[] { account.getStaffID() });
		beans = new ArrayList<BaseBean>();
		if(jobPlan.getJobPlanID() == null || "".equals(jobPlan.getJobPlanID())){
			jobPlan.setJobPlanID(serverService.getServerID("jobPlan"));
			jobPlan.setCompanyIDS(account.getCompanyID());
			jobPlan.setStaffIDS(staff.getStaffID());
			jobPlan.setStaffID("staffID");
			jobPlan.setCompanyID("companyID");
			jobPlan.setOrganizationID("organizationID");
			jobPlan.setStaffNameS(staff.getStaffName());
			jobPlan.setCompanyIDS(account.getCompanyID());
			String projectcodeold = new Date().toLocaleString();
			projectcodeold = projectcodeold.replace("-","").replace(":","").replaceAll(" ", "");
			jobPlan.setProjectcode(projectcodeold);
			jobPlan.setEntryDate(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if( jobPlan.getCodeID().equals("scode201304166n47qjx7re0000000004")){
				jobPlan.setCodeValue("基本任务");
			}else{
				jobPlan.setCodeValue("额外任务");
			} 
			String name = "";
			if(jobstamap != null){
				for (JobplanSta jobsta : jobstamap.values()) {
					jobsta.setJobplanstaID(serverService.getServerID("jobplansta"));
					jobsta.setJobPlanID(jobPlan.getJobPlanID());
					jobsta.setStusts("00");
					beans.add(jobsta);
					if(name.equals(""))
						name = jobsta.getStaffName();
					else
						name += ","+jobsta.getStaffName();
				}
			}
			jobPlan.setStaffName(name);
			jobPlan.setEntry("待办事项");
			beans.add(jobPlan);
		}else{
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { "delete from JobplanSta where jobPlanID = ?" }, new Object[]{jobPlan.getJobPlanID()});
			if( jobPlan.getCodeID().equals("scode201304166n47qjx7re0000000004")){
				jobPlan.setCodeValue("基本任务");
			}else{
				jobPlan.setCodeValue("额外任务");
			}
			String name = "";
			if(jobstamap != null){
				for (JobplanSta jobsta : jobstamap.values()) {
					jobsta.setJobplanstaID(serverService.getServerID("jobplansta"));
					jobsta.setJobPlanID(jobPlan.getJobPlanID());
					jobsta.setStusts("00");
					beans.add(jobsta);
					if(name.equals(""))
						name = jobsta.getStaffName();
					else
						name += ","+jobsta.getStaffName();
				}
			}
			jobPlan.setStaffName(name);
			beans.add(jobPlan);
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter,
				account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		return "success";
	}
	/**
	 * 人事生产 工作计划
	 * @return
	 */
	public String toJobSta(){
		beans = new ArrayList<BaseBean>();
		jobPlan = (COSJobPlan)baseBeanService.getBeanByHqlAndParams("from COSJobPlan j where j.jobPlanID = ?", new Object[]{jobplansta.getJobPlanID()});
		if(jobPlan.getStaffName().equals("")){
			jobPlan.setStaffName(jobplansta.getStaffName());
		}else{
			jobPlan.setStaffName(jobPlan.getStaffName()+","+jobplansta.getStaffName());
		}
		String hql = "select count(*) from JobplanSta j where j.staffID = ? and j.companyID = ? and j.jobPlanID = ?";
		int i = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{jobplansta.getStaffID(),jobplansta.getCompanyID(),jobplansta.getJobPlanID()});
		if(i>0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jobplansta", "责任人已存在 ！");
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}else{
			beans.add(jobPlan);
			jobplansta.setJobplanstaID(serverService.getServerID("jobplansta"));
			jobplansta.setStusts("00");
			beans.add(jobplansta);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jobplansta", "新增责任人：< "+jobplansta.getStaffName()+" > ！");
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}
		return "success";
	}
	/**
	 * 人事生产 工作计划
	 * @return
	 */
	public String tojobEdit(){
		jobPlan = (COSJobPlan)baseBeanService.getBeanByHqlAndParams("from COSJobPlan j where j.jobPlanID = ?", new Object[]{jobPlan.getJobPlanID()});
		String sql = "select j.staffname,j.staffID,j.companyID,j.orgID from dt_jobplan_sta j" +
				" join dtcompany c on c.companyid =j.companyid" +
				" join dtcorganization o on o.organizationid = j.orgID" +
				" where j.jobplanid = ? ";
		List list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{jobPlan.getJobPlanID()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobPlan", jobPlan);
		map.put("list", list);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 人事生产 工作计划
	 * @return
	 */
	public String delJobPlanDate(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { jobPlan.getJobPlanID() };
		String hql = "delete from COSJobPlan where jobPlanID=?";
		String hql1 = "delete from JobplanSta where jobPlanID = ?";
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除工作计划(人员名称："
				+ account.getStaffName() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql,hql1 }, params);
		
		return "success";
	}
	

	// ////////////////////集团公司汇总////////
	public String toSearchCompanySummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobPlanVO);
		return getCompanyJobPlanListSummary();
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
	
	public String getCompanyJobPlanListSummary() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getCompanyListSummary());
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
			if (null != jobPlanVO.getStartDate()
					&& null != jobPlanVO.getEndDate()) {
				dc.add(Restrictions.between("startDate", jobPlanVO
						.getStartDate(), jobPlanVO.getEndDate()));
			}
			if (!"".equals(jobPlanVO.getCompanyID()))
				dc.add(Restrictions.eq("companyID", jobPlanVO.getCompanyID()));
			else
				dc.add(Restrictions.in("companyID", companyService
						.getCompanyAndItsChildrenIDs(companyID)));
			if (!"".equals(jobPlanVO.getOrganizationID())
					&& !jobPlanVO.getCompanyID().equals(
							jobPlanVO.getOrganizationID()))
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
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工作计划", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 公司汇总
	 * @return
	 */
	public String toSearchSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobPlan);
		return getJobPlanListSummary();
	}

	/**
	 * 公司汇总
	 * @return
	 */
	public String getJobPlanListSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getListSummary(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")),params);
		return "listSummary";
	}

	/**
	 * ModfyTime:2010-11-4 如果输入时间为空，则查询所有与条件相符合的记录
	 * 公司汇总
	 * @return
	 */
	private List<Object>  getListSummary(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String  sql = "select jt.jobplanid,co.companyName, org.organizationName,st.staffcode," +
				" st.staffname,jt.startdate,jt.enddate, jt.jobname,jt.codevalue," +
				"  case  when jt.jobstatus='00' then '日' when jt.jobstatus='01' then '周' when jt.jobstatus='02' then '月' when jt.jobstatus='03' then '季' when jt.jobstatus='04' then '年' else '月' end," +
				" jt.jobcontent,jt.staffNameS,jt.supervisor,jt.humansupervisor,jt.manager,sta.jobplanstaID" +
				" from dtcosjobplan jt" +
				" left join dt_jobplan_sta sta on sta.jobplanid = jt.jobplanid" +
				" left join dtcompany co on (co.companyid = sta.companyid or co.companyid = jt.companyid )" +
				" left join dtcorganization org on ( org.organizationid = sta.orgid or org.organizationid = jt.organizationid )" +
				" left join dt_hr_staff st on (st.staffid = jt.staffid or st.staffid = sta.staffid)";
		sql += " where (jt.companyid = ? or sta.companyid = ?)";
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			jobPlan = (COSJobPlan) session.get("tablesearch");
			if (jobPlan.getStaffName() != null
					&& !"".equals(jobPlan.getStaffName())) {
				sql += " and st.staffname like ? ";
				parms.add("%" + jobPlan.getStaffName()+ "%");
			}
			if (jobPlan.getStartDate() != null
					&& !"".equals(jobPlan.getStartDate())) {
				sql += " and jt.startDate between ? and ? ";
				parms.add(jobPlan.getStartDate());
				parms.add(jobPlan.getEndDate());
			}
		}
		sql += " and jt.startdate is not null order by jt.startdate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	
	public String showExcelSummary() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getListSummary(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		sql = "select co.companyName, org.organizationName,st.staffcode,st.staffname," +
			" jt.startdate,jt.enddate, jt.projectcode,jt.jobname," +
			" case when jt.jobstatus='00' then '日计划' when jt.jobstatus='01' then '周计划' when jt.jobstatus='02' then '月计划' when jt.jobstatus='03' then '季计划' when jt.jobstatus='04' then '年计划' end," +
			" jt.jobcontent,jt.entry,jt.staffnames,jt.supervisor,jt.humansupervisor,jt.manager "+sql.substring(sql.indexOf("from")) ;
		
		excelStream = excelService.showExcel(COSJobPlan.columnHeadingsSummary(),
				baseBeanService.getListBeanBySqlAndParams(sql, params));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工作计划", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	// //////////////////////////////////////////////////////////////////////
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobPlan);
		return getJobPlanList();
	}

	private List<Object>  getList(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String  sql = "select j.jobplanid,j.entrydate,j.startdate,j.enddate,j.projectcode," +
				" j.jobname,j.codevalue,j.jobstatus,j.jobcontent,j.entry,j.staffnames," +
				" j.supervisor,j.humansupervisor,j.manager,s.stusts," +
				" j.jobplankey,j.staffid,j.staffids,j.companyid,j.companyids,j.organizationid,j.staffname,j.codeid," +
				" j.postmanage,j.noentry,j.projectdetailscode,j.projectrequirements," +
				" j.fraction,j.points,j.actualscore,j.taskname,j.projectpercentage ," +
				" j.plancheck,j.detailsfraction,j.actualdetailsscore,j.strstartdate,j.strenddate" +
				" from dtcosjobplan j " +
				" left join dt_jobplan_sta s on j.jobPlanID = s.jobPlanID" +
				" where ( j.companyid = ? or s.companyid = ? ) and ( s.staffID = ? or j.staffID = ? )";
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		parms.add(account.getStaffID());
		parms.add(account.getStaffID());
		if (search != null && search.equals("search")) {
			jobPlan = (COSJobPlan) session.get("tablesearch");
			if (jobPlan.getJobstatus() != null
					&& !"".equals(jobPlan.getJobstatus())) {
				sql += " and j.jobstatus = ? ";
				parms.add(jobPlan.getJobstatus());
			}
			if (jobPlan.getStartDate() != null
					&& !"".equals(jobPlan.getStartDate())) {
				sql += " and j.startDate between ? and ? ";
				parms.add(jobPlan.getStartDate());
				parms.add(jobPlan.getEndDate());
			}
		}
		sql += " order by j.startDate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}
	public String getJobPlanList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")),params);
		//任务类型
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
																"scode201304166n47qjx7re0000000003");
		if (personSearch != null && personSearch.equals("personSearch")) {
			return "joblists";
		}
		return "list";
	}

	/**
	 * 得到个人工作计划
	 * 
	 * @return
	 */
	public String getJobPlanLists() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String[] hql2 = { "from Staff where staffID=?" };
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2[0],
				new Object[] { account.getStaffID() });
		if (staff == null) {
			return "fail";
		}
		personName = staff.getStaffName();
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")),params);		
		
		getCCodeList();
		return "joblists";
	}
	/**
	  *  添加个人工作计划 
	  **/
	public String savePlan(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from COS where companyID=? and cosStatus='50' and staffID=? ";
		COS cos = (COS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID(), account.getStaffID() });
		String[] hql1 = { "from Staff where staffID=?" };
		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql1[0],
				new Object[] { account.getStaffID() });
		beans = new ArrayList<BaseBean>();
		if(jobPlan.getJobPlanID() == null || "".equals(jobPlan.getJobPlanID())){
			jobPlan.setJobPlanID(serverService.getServerID("jobPlan"));
			jobPlan.setEntryDate(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			@SuppressWarnings("deprecation")
			String projectcodeold = new Date().toLocaleString();
			projectcodeold = projectcodeold.replace("-","").replace(":","").replaceAll(" ", "");
			jobPlan.setProjectcode(projectcodeold);
			jobPlan.setStaffIDS(staff.getStaffID());
			jobPlan.setStaffNameS(staff.getStaffName());
			jobPlan.setStaffID(staff.getStaffID());
			jobPlan.setStaffName(staff.getStaffName());
			if( jobPlan.getCodeID().equals("scode201304166n47qjx7re0000000004")){
				jobPlan.setCodeValue("基本任务");
			}else{
				jobPlan.setCodeValue("额外任务");
			} 
			if( jobPlan.getOrganizationID().equals("")){
				jobPlan.setOrganizationID(cos.getOrganizationID());
			}
			if( jobPlan.getCompanyID().equals("")){
				jobPlan.setCompanyID(cos.getCompanyID());
			}
			if( jobPlan.getCompanyIDS().equals("")){
				jobPlan.setCompanyIDS(cos.getCompanyID());
			}
			JobplanSta jobsta = new JobplanSta();
			jobsta.setCompanyID(account.getCompanyID());
			jobsta.setOrgID(jobPlan.getOrganizationID());
			jobsta.setStaffID(jobPlan.getStaffID());
			jobsta.setStaffName(jobPlan.getStaffName());
			jobsta.setJobplanstaID(serverService.getServerID("jobplansta"));
			jobsta.setJobPlanID(jobPlan.getJobPlanID());
			jobsta.setStusts("00");
			beans.add(jobsta);

			
			parameter = "添加工作计划";
		}else{
			if( jobPlan.getCodeID().equals("scode201304166n47qjx7re0000000004")){
				jobPlan.setCodeValue("基本任务");
			}else{
				jobPlan.setCodeValue("额外任务");
			}
			parameter = "修改工作计划";

		}
		
		beans.add(jobPlan);
		
		CLogBook logBook = logBookService.saveCLogBook(null, parameter,
				account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
				null);
		return "success";
		
	}
	/**
	 * 个人工作计划状态改变
	 * @return
	 */
	public String toUpdateStusts(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "工作计划(人员名称："
				+ account.getStaffName() + "、状态改变："+jobplansta.getStusts()+")", account);
		beans.add(logBook);
		
		String hql = "update JobplanSta s set s.stusts = ? where s.jobPlanID = ? and s.staffID = ?";
		Object[] obj = new Object[]{jobplansta.getStusts(),jobplansta.getJobPlanID(),account.getStaffID()};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql}, obj);
		if(jobplansta.getStusts().equals("01")){
			String hql1 = "update COSJobPlan j set j.entry = ? where j.jobPlanID = ?";
			Object[] obj1 = new Object[]{"已办事项",jobplansta.getJobPlanID()};
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql1}, obj1);
		}else if(jobplansta.getStusts().equals("02")){
			List list = baseBeanService.getListBeanByHqlAndParams(" from JobplanSta s where s.jobPlanID = ? ", new Object[]{jobplansta.getJobPlanID()});
			int k = 0;
			for(int i = 0 ; i <list.size() ; i++){
				JobplanSta job = (JobplanSta)list.get(i);
				if(!job.getStusts().equals("02")){
					if(!job.getStusts().equals("03")){
						k++;
					}
				}
			}
			if(k == 0){
				String hql2 = "update COSJobPlan j set j.entry = ? where j.jobPlanID = ?";
				Object[] obj2 = new Object[]{"完成事项",jobplansta.getJobPlanID()};
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql2}, obj2);
			}
		}else if(jobplansta.getStusts().equals("03")){
			List list = baseBeanService.getListBeanByHqlAndParams(" from JobplanSta s where s.jobPlanID = ? ", new Object[]{jobplansta.getJobPlanID()});
			int m = 0;
			for(int i = 0 ; i <list.size() ; i++){
				JobplanSta job = (JobplanSta)list.get(i);
				if(!job.getStusts().equals("02")){
					if(!job.getStusts().equals("03")){
						m++;
					}
				}
			}
			if(m == 0 && list.size() == 1){
				String hql2 = "update COSJobPlan j set j.entry = ? where j.jobPlanID = ?";
				Object[] obj2 = new Object[]{"待办事项",jobplansta.getJobPlanID()};
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql2}, obj2);
			}else if(m == 0 && list.size() > 1){
				String hql2 = "update COSJobPlan j set j.entry = ? where j.jobPlanID = ?";
				Object[] obj2 = new Object[]{"完成事项",jobplansta.getJobPlanID()};
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql2}, obj2);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(jobplansta.getStusts().equals("01")){
			map.put("text", "已办");
		}else if(jobplansta.getStusts().equals("02")){
			map.put("text", "完成");
		}else{
			map.put("text", "退回");
		}
		
		JSONObject jso = JSONObject.fromObject(map);
		result = jso.toString();
		
		return "success";
	}
	public String savePerson() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String hql3 = "from COS where companyID=? and cosStatus='50' and staffID=? ";
		COS cos = (COS) baseBeanService.getBeanByHqlAndParams(hql3,
				new Object[] { account.getCompanyID(), account.getStaffID() });
		String organizationID = cos.getOrganizationID();
		beans = new ArrayList<BaseBean>();
		if (jobPlanmap != null) {
			for (COSJobPlan job : jobPlanmap.values()) {
				job.setStaffID(account.getStaffID());
				if (job.getJobPlanID() == null || "".equals(job.getJobPlanID())) {
					job.setJobPlanID(serverService.getServerID("jobPlan"));
					job.setEntryDate(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					parameter = "添加工作计划";
				} else {
					parameter = "修改工作计划";
				}
				job.setCodeValue(getcodeValue(job.getCodeID()));
				job.setStartDate(Utilities.getDateFromString(job.getStrStartDate(), "yyyy-MM-dd"));
				job.setEndDate(Utilities.getDateFromString(job.getStrEndDate(), "yyyy-MM-dd"));
				String[] hql2 = { "from Staff where staffID=?" };
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2[0], new Object[] { account.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				job.setOrganizationID(organizationID);
				job.setCompanyID(account.getCompanyID());
				beans.add(job);

			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
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
		Object[] params = { jobPlan.getJobPlanID() };
		String hql = "delete from COSJobPlan where jobPlanID=?";
		String hql1 = "delete from JobplanSta where jobPlanID = ?";
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除工作计划(人员名称："
				+ account.getStaffName() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql,hql1 }, params);
		return "success";
	}

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		sql = "select j.entrydate,j.startdate,j.enddate,j.projectcode,j.jobname,j.codevalue,j.jobcontent," +
				" j.entry,j.staffnames, j.supervisor,j.humansupervisor,j.manager "+sql.substring(sql.indexOf("from")) ;
		excelStream = excelService.showExcel(COSJobPlan.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, params));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工作计划", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String save() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
				
				
				
		if (jobPlanmap != null) {
			for (COSJobPlan job : jobPlanmap.values()) {
				// 这里传过来 接收
				staffID = job.getStaffID();
				if (job.getJobPlanID() == null || "".equals(job.getJobPlanID())) {
					job.setJobPlanID(serverService.getServerID("jobPlan"));
					job.setEntryDate(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
					parameter = "添加工作计划";
				} else {
					parameter = "修改工作计划";
				}
				job.setStartDate(Utilities.getDateFromString(job
						.getStrStartDate(), "yyyy-MM-dd"));
				job.setEndDate(Utilities.getDateFromString(job.getStrEndDate(),
						"yyyy-MM-dd"));
				String[] hql2 = { "from Staff where staffID=?" };
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2[0], new Object[] { job.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				job.setOrganizationID(organizationID);
				job.setCompanyID(account.getCompanyID());
				beans.add(job);

			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}
 
	/**
	 * 打印工作计划汇总
	 * 
	 * @return
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException
	 */

	@SuppressWarnings({ "deprecation", "unused" })
	public String toPrint() throws ParseException { 
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		
		DetachedCriteria dc1 = DetachedCriteria.forClass(CStaffCos.class);
		dc1.add(Restrictions.eq("companyID", companyID));
		String staffName = jobplansta.getStaffName();
		
		//如果 人员姓名 为空，则 查询 全公司人员
		if (null != staffName && !("").equals(staffName)) {
			dc1.add(Restrictions.like("staffName", staffName,
					MatchMode.ANYWHERE));
		}
		
		List<BaseBean> CStaffs = baseBeanService.getListByDC(dc1);
		
		Date from = DateUtil.getCurrDate();
		Date to = from;
		String dataFrom = "";
		String dataTo = "";
		//startDate格式为:2010-10
		if(null != startDate && startDate.length() > 0){
			//startDate :2010-10-01
			dataFrom = startDate.replace("-", "").concat("01");
			from = DateUtil.parseDate(DateUtil.C_DATA_PATTON_YYYYMMDD, dataFrom);
		}
		//endDate格式为:2010-10
		if(null != endDate && endDate.length() > 0){
			//endDate :2010-11-01
			dataTo = endDate.replace("-", "").concat("01");
			to = DateUtil.parseDate(DateUtil.C_DATA_PATTON_YYYYMMDD, dataTo);
		}
		 
		if (CStaffs.size() > 0) {
			
			//判断日期 如果只填写一个 ，则就是一个月，
			//如果填写两个 ,而两个月份不一样，则多月
			//如果一个月的话，直接放入集合
			//如果多月，则一个月一个月的放
			List jobplansummarylist = null; 
			int i = 0;
			//个人 可能多月 -> endDate - startDate  > 1
			for (BaseBean baseBean : CStaffs) { 
				CStaffCos cos = (CStaffCos) baseBean;
				List<Object> parms = new ArrayList<Object>();
				String  sql = "select co.companyName, org.organizationName,st.staffcode," +
						" st.staffname,jt.startdate,jt.enddate, jt.jobname,jt.codevalue," +
						" jt.jobcontent,jt.staffNameS,jt.supervisor,jt.humansupervisor,jt.manager" +
						" from dtcosjobplan jt" +
						" left join dt_jobplan_sta sta on sta.jobplanid = jt.jobplanid" +
						" left join dtcompany co on (co.companyid = sta.companyid or co.companyid = jt.companyid )" +
						" left join dtcorganization org on ( org.organizationid = sta.orgid or org.organizationid = jt.organizationid )" +
						" left join dt_hr_staff st on (st.staffid = jt.staffid or st.staffid = sta.staffid)";
				sql += " where (jt.companyid = ? or sta.companyid = ?) and" +
					   " jt.startDate >= to_date(?,'yyyy-mm') and jt.endDate <= to_date(?,'yyyy-mm')" +
					   " and (sta.staffID = ? or jt.staffID = ? )order by jt.startDate";
				parms.add(account.getCompanyID());
				parms.add(account.getCompanyID());
				parms.add(startDate);
				parms.add(endDate);
				parms.add(cos.getStaffID());
				parms.add(cos.getStaffID());
				jobplansummarylist = baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
				if(null != jobplansummarylist && jobplansummarylist.size() > 0){
					//说明为同年同月 个人个月
				    if(from.getYear() == to.getYear() && from.getMonth() == to.getMonth()){ 
				    	if(jobplansummarylist.size() > 0){
				    		getMapsData(jobplansummarylist,cos.getStaffName(),5); 
				    	}
				    }else{//个人多月
				    	//获取月份差
				    	int months = DateUtil.getMonthNum(from, to);
				    	if(Math.abs(months) - 1 > 0){
				    		for (int j = 0; j < months - 1; j++) {
								List mutip = new ArrayList();
								Calendar calendar = DateUtil.toCalendarFromUtilDate(from);
								calendar.add(Calendar.MONDAY, j + 1);	
								//获取同月数据
								for (Object subBean : jobplansummarylist) {
									Object[] obj = new Object[]{}; 
									obj = (Object[]) subBean; 
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									Date date = sdf.parse(obj[4].toString());
									Calendar calendar1 = DateUtil.toCalendarFromUtilDate(date);  
									if(DateUtil.getMonthNum(calendar.getTime(), calendar1.getTime()) == 0){
										mutip.add(obj);
									}
								}
								if (mutip != null && mutip.size() > 0) { 
										getMapsData(mutip, cos.getStaffName(), 5); 
									
								}
								
							}
				    	}
				    }
				} 
				i++;
			} 
		}    
		return "printJobSummary";
	} // 打印某月的工作计划

	/**
	 * 组装打印某月的工作计划Map数据
	 * @param map
	 * @param jobplansummarylist
	 * @param staffName 员工名
	 * @param count 一个工作表显示条数
	 */
	private void getMapsData(List<BaseBean> jobplansummarylist,String staffName,int count) {
		
		count = count == 0 ? 5: count;
		
		if(jobplansummarylist.size() <= count){
			if (jobplansummarylist.size() < count) {
				int tempSize = jobplansummarylist.size();
				// 为了不满行数，输出空行
				for (int i = 0; i < 5 - tempSize; i++) {
					jobplansummarylist.add(null);
				}
			}
			maps.add( jobplansummarylist);
		}else{

			List temp = new ArrayList();
			int counts = jobplansummarylist.size();
			 
			if(counts % 5 != 0){
				for(int i = 0; i <5 - counts % 5;i++){
					jobplansummarylist.add(null);
				}
			}
			
			for (int j = 0; j < jobplansummarylist.size() ; j++) {
				if (j == 0) {
					temp.add(jobplansummarylist.get(j));
				} else if (j % count != 0) {
					temp.add(jobplansummarylist.get(j)); 
					
					if(j == jobplansummarylist.size() - 1){
						maps.add(temp);
					}
				} else if (j % count == 0) {
					maps.add(temp);
					temp = new ArrayList();
					temp.add(jobplansummarylist.get(j));
				}
			}
		}
	}

	public String toDaYin() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from COSJobPlan  where  staffID = ? and companyID = ? ";
		Object[] params = { jobPlan.getStaffID(), account.getCompanyID() };
		if (search != null && search.equals("search")) {

			COSJobPlan jobPlanVO = (COSJobPlan) session.get("tablesearch");
			if (null != jobPlanVO.getStartDate()
					&& null != jobPlanVO.getEndDate()) {
				hql += " and startDate >= ? and startDate <=  ?";
				Object[] params1 = { jobPlan.getStaffID(),
						account.getCompanyID(), jobPlanVO.getStartDate(),
						jobPlanVO.getEndDate() };
				params = params1;
			}
			hql += "order by startDate ";
		}
		List<BaseBean> logplanlist = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { jobPlan.getStaffID() });
		if (logplanlist.size() > 0) {
			LokPlanPrintVO = new LokPlanPrintVO();
			LokPlanPrintVO.setCompanyID(account.getCompanyID());
			LokPlanPrintVO.setCompanyName(companyService.getCompanyByCompanyID(
					account.getCompanyID()).getCompanyName());
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

	public String getCodeID() {
		return codeID;
	}
	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}
	/**
	 * 工作计划汇总
	 * 
	 * @return
	 */
	public List<BaseBean> getJobPlanSummarys() {
		return jobPlanSummarys;
	}

	/**
	 * 工作计划汇总
	 * 
	 * @param jobPlanSummarys
	 */
	public void setJobPlanSummarys(List<BaseBean> jobPlanSummarys) {
		this.jobPlanSummarys = jobPlanSummarys;
	}

	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<List<BaseBean>> getMaps() {
		return maps;
	}

	public void setMaps(List<List<BaseBean>> maps) {
		this.maps = maps;
	}
	public List<CCode> getScoreSortlist() {
		return scoreSortlist;
	}
	public void setScoreSortlist(List<CCode> scoreSortlist) {
		this.scoreSortlist = scoreSortlist;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Map<String, String> getoMap1() {
		return oMap1;
	}

	public void setoMap1(Map<String, String> oMap1) {
		this.oMap1 = oMap1;
	}

	public Map<String, Staff> getStaffmap() {
		return staffmap;
	}

	public void setStaffmap(Map<String, Staff> staffmap) {
		this.staffmap = staffmap;
	}

	public Map<String, JobplanSta> getJobstamap() {
		return jobstamap;
	}

	public void setJobstamap(Map<String, JobplanSta> jobstamap) {
		this.jobstamap = jobstamap;
	}
	public JobplanSta getJobplansta() {
		return jobplansta;
	}
	public void setJobplansta(JobplanSta jobplansta) {
		this.jobplansta = jobplansta;
	}

}
