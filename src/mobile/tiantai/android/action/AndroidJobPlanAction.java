package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.human.COS;
import hy.ea.bo.human.COSJobPlan;
import hy.ea.bo.human.Jobplanrecord;
import hy.ea.bo.human.Staff;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


@Controller
@Scope("prototype")
public class AndroidJobPlanAction {
	private static final Logger logger = LoggerFactory.getLogger(AndroidJobPlanAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private PageForm pageForm;
	/**
	 * 每页显示条数
	 */
	private int pageSize;
	/**
	 * 页数
	 */
	private int pageNumber;
	/**
	 * 总数
	 */
	private int recordCount;
	private String search;
	private String result;
	private COSJobPlan jobplan; //实体+
	private Staff staff;
	private List<BaseBean> beans;
	private Jobplanrecord jpr  ;
	/**
	 * 日计划总数
	 */
	private int dayCount = 0;
	/**
	 * 周计划总数
	 */
	private int weekCount = 0;
	/**
	 * 月计划总数
	 */
	private int monthCount = 0;
	/**
	 * 季计划总数
	 */
	private int jdCount = 0;
	/**
	 * 年计划总数
	 */
	private int yearCount = 0;
	
	
	/**
	 * 删除
	 * @author 
	 */
	public String del(){

		/**
		 * jobplan.getJobPlanID() 工作任务id
		 */
		
		Object[] params = { jobplan.getJobPlanID() };
		
		String hql = "delete from COSJobPlan where jobPlanID=?";
		String hql3 = "delete from Jobplanrecord where jobplanid = ?";
		
		beans = new ArrayList<BaseBean>();
		
		
		JSONObject jsonObjList = new JSONObject();
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql,hql3 }, params);
			jsonObjList.accumulate("result", "success");
		} catch (Exception e) {
			jsonObjList.accumulate("result", "fail");
			logger.error("操作异常", e);
		}
		result = jsonObjList.toString();
		return "success";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		/**
		 * jobplan.getCompanyID() //责任人公司
		 * jobplan.getStaffID() //责任人ID 
		 * jobplan.getStartDate // 任务起始日期
		 * jobplan.getEndDate // 任务结束日期
		 * jobplan.getJobName//项目内容 
		 * jobplan.getJobContent// 项目明细内容
		 * jobplan.getJobstatus //日周月季年     **********  00 01 02 03 04
		 */
		
		String hql = "from COS where companyID=? and cosStatus='50' and staffID=? ";
		COS cos = (COS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { jobplan.getCompanyID(), jobplan.getStaffID() });
		String[] hql1 = { "from Staff where staffID=?" };
		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql1[0],
				new Object[] { jobplan.getStaffID() });
		beans = new ArrayList<BaseBean>();
		if(jobplan.getJobPlanID() == null || "".equals(jobplan.getJobPlanID())){
			jobplan.setJobPlanID(serverService.getServerID("jobPlan"));
			jobplan.setEntryDate(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			@SuppressWarnings("deprecation")
			String projectcodeold = new Date().toLocaleString();
			projectcodeold = projectcodeold.replace("-","").replace(":","").replaceAll(" ", "");
			jobplan.setProjectcode(projectcodeold);
			jobplan.setEntry("待办事项");
			jobplan.setStaffIDS(staff.getStaffID());
			jobplan.setStaffNameS(staff.getStaffName());
			jobplan.setStaffID(staff.getStaffID());
			jobplan.setStaffName(staff.getStaffName());
			jobplan.setCodeID("scode201304166n47qjx7re0000000004");	
			jobplan.setCodeValue("基本任务");
			if( jobplan.getOrganizationID().equals("")){
				jobplan.setOrganizationID(cos.getOrganizationID());
			}
			if( jobplan.getCompanyID().equals("")){
				jobplan.setCompanyID(cos.getCompanyID());
			}
			if( jobplan.getCompanyIDS().equals("")){
				jobplan.setCompanyIDS(cos.getCompanyID());
			}
			

			//项目发起记录状态
			jpr = new Jobplanrecord();
			jpr.setJobplanrecordid(serverService.getServerID("jobrecord"));
			jpr.setJobplanid(jobplan.getJobPlanID());
			jpr.setEntry("手机项目发起");
			jpr.setCaupdate(new Date());
			jpr.setStaffid(jobplan.getStaffIDS());
			jpr.setStaffname(jobplan.getStaffNameS());
			jpr.setCompanyid(jobplan.getCompanyIDS());
			
			jpr.setStaffids(staff.getStaffID());
			jpr.setStaffnames(staff.getStaffName());
			jpr.setCompanyids(jobplan.getCompanyID());
			beans.add(jpr);
			
			//项目指派记录状态
			jpr = new Jobplanrecord();
			jpr.setJobplanrecordid(serverService.getServerID("jobrecord"));
			jpr.setJobplanid(jobplan.getJobPlanID());
			jpr.setEntry("手机项目指派");
			jpr.setCaupdate(new Date());
			jpr.setStaffid(jobplan.getStaffID());
			jpr.setStaffname(jobplan.getStaffName());
			jpr.setCompanyid(jobplan.getCompanyID());

			jpr.setStaffids(staff.getStaffID());
			jpr.setStaffnames(staff.getStaffName());
			jpr.setCompanyids(jobplan.getCompanyID());
			beans.add(jpr);
			
		}else{
			
		}
		
		beans.add(jobplan);
		JSONObject jsonObjList = new JSONObject();
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
			jsonObjList.accumulate("result", "success");
		} catch (Exception e) {
			jsonObjList.accumulate("result", "fail");
			logger.error("操作异常", e);
		}
		result = jsonObjList.toString();
		return "success";
	}
	
	/**
	 * 工作计划列表
	 * @return
	 */
	public String list() {
		JSONObject jsonObjList = new JSONObject();
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 1 : pageNumber),
				getList());
		
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			
			jsonObjList.accumulate("pageForm", pageForm);
			jsonObjList.accumulate("list", pageForm.getList());
			jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
			jsonObjList.accumulate("pageSize", pageForm.getPageSize());
			jsonObjList.accumulate("recordCount", pageForm.getRecordCount());
			
			String hql = " select count(*) from COSJobPlan j where j.jobstatus = ? and j.staffID = ?";
			dayCount = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{"00",jobplan.getStaffID()});
			weekCount = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{"01",jobplan.getStaffID()});
			monthCount = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{"02",jobplan.getStaffID()});
			jdCount = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{"03",jobplan.getStaffID()});
			yearCount = baseBeanService.getConutByByHqlAndParams(hql, new Object[]{"04",jobplan.getStaffID()});
			
			
		}else{
			jsonObjList.accumulate("pageForm", "nodata");
		}

		jsonObjList.accumulate("dayCount", dayCount);
		jsonObjList.accumulate("weekCount", weekCount);
		jsonObjList.accumulate("monthCount", monthCount);
		jsonObjList.accumulate("jdCount", jdCount);
		jsonObjList.accumulate("yearCount", yearCount);
		result = jsonObjList.toString();
		return "success";
		
	}

	private DetachedCriteria getList(){
		
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(COSJobPlan.class);
		dc.add(Restrictions.eq("staffID", jobplan.getStaffID()));
		dc.add(Restrictions.eq("companyID", jobplan.getCompanyID()));
		
//		if (search != null && search.equals("search")) {
//			jobplan = (COSJobPlan) session.get("tablesearch");
//			if (!"".equals(jobplan.getJobstatus().trim())) {
//				dc.add(Restrictions.like("jobstatus", jobplan.getJobstatus().trim()));
//			}
//			if (!"".equals(jobplan.getEntry())) {
//				dc.add(Restrictions.eq("entry", jobplan.getEntry().trim()));
//			}
//			if (!"".equals(viewJob.getFromMember())) {
//				dc.add(Restrictions.like("frommember", viewJob
//						.getFromMember(), MatchMode.ANYWHERE));
//			}
//		}


		dc.addOrder(Order.desc("entryDate"));
		
		return dc;
	}
	
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", jobplan);
		return list();

	}
	

	public PageForm getPageForm() {
		return pageForm;
	}


	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public int getRecordCount() {
		return recordCount;
	}


	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public COSJobPlan getJobplan() {
		return jobplan;
	}

	public void setJobplan(COSJobPlan jobplan) {
		this.jobplan = jobplan;
	}

	public int getDayCount() {
		return dayCount;
	}

	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}

	public int getWeekCount() {
		return weekCount;
	}

	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}

	public int getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(int monthCount) {
		this.monthCount = monthCount;
	}

	public int getJdCount() {
		return jdCount;
	}

	public void setJdCount(int jdCount) {
		this.jdCount = jdCount;
	}

	public int getYearCount() {
		return yearCount;
	}

	public void setYearCount(int yearCount) {
		this.yearCount = yearCount;
	}
	
	
}
