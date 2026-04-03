package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.COSJobPlan;
import hy.ea.bo.human.vo.COSJobPlanVO;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 集团工作--计划汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class JobPlanCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	private COSJobPlanVO cosJobPlan;
	private COSJobPlan jobPlan;


	/**
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		sql = "select co.companyName, org.organizationName,st.staffcode,st.staffname," +
			" jt.startdate,jt.enddate, jt.projectcode,jt.jobname," +
			" case when jt.jobstatus='00' then '日计划' when jt.jobstatus='01' then '周计划' when jt.jobstatus='02' then '月计划' when jt.jobstatus='03' then '季计划' when jt.jobstatus='04' then '年计划' end," +
			" jt.jobcontent,jt.entry,jt.staffnames,jt.supervisor,jt.humansupervisor,jt.manager "+sql.substring(sql.indexOf("from")) ;
		
		excelStream = excelService.showExcel(COSJobPlan.columnHeadingsSummary(),
				baseBeanService.getListBeanBySqlAndParams(sql, params));
		
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", cosJobPlan);
		return getJobPlanList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getJobPlanList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")),params);
 		return "getJobPlanList";
	}
	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String  sql = "select jt.jobplanid,co.companyName, org.organizationName,st.staffcode," +
				" st.staffname,jt.entryDate,jt.startdate,jt.enddate,jt.projectcode, jt.jobname,jt.codevalue," +
				"  case  when jt.jobstatus='00' then '日' when jt.jobstatus='01' then '周' when jt.jobstatus='02' then '月' when jt.jobstatus='03' then '季' when jt.jobstatus='04' then '年' else '月' end," +
				" jt.jobcontent,jt.staffNameS,jt.supervisor,jt.humansupervisor,jt.manager,sta.jobplanstaID" +
				" from dtcosjobplan jt" +
				" left join dt_jobplan_sta sta on sta.jobplanid = jt.jobplanid" +
				" left join dtcompany co on (co.companyid = sta.companyid or co.companyid = jt.companyid )" +
				" left join dtcorganization org on ( org.organizationid = sta.orgid or org.organizationid = jt.organizationid )" +
				" left join dt_hr_staff st on (st.staffid = jt.staffid or st.staffid = sta.staffid)";
		sql += " where (jt.companyid in (" +
				" select c.companyid from dtcompany c where" +
				" ( c.companyID = ? or c.companyPID = ?)) " +
				" or sta.companyid in (" +
				" select c.companyid from dtcompany c where" +
				" ( c.companyID = ? or c.companyPID = ?)))";
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			cosJobPlan = (COSJobPlanVO) session.get("tablesearch");
			//人员姓名
			if (cosJobPlan.getStaffName()!= null
					&& !cosJobPlan.getStaffName().trim().equals("")) {
				sql += " and st.staffName like ? ";
				parms.add("%" + cosJobPlan.getStaffName().trim() + "%");
			}
			//公司
			if (cosJobPlan.getCompanyID()!= null
					&& !cosJobPlan.getCompanyID().equals("")) {
				sql += " and ( jt.companyID = ? or sta.companyID = ? )";
				parms.add(cosJobPlan.getCompanyID());
				parms.add(cosJobPlan.getCompanyID());
			}
			//部门
			if (cosJobPlan.getOrganizationID()!= null
					&& !cosJobPlan.getOrganizationID().equals("") && !cosJobPlan.getCompanyID().equals(cosJobPlan.getOrganizationID())) {
				sql += " and ( jt.organizationID = ? or sta.orgID = ? ) ";
				parms.add(cosJobPlan.getOrganizationID());
				parms.add(cosJobPlan.getOrganizationID());
			}
			//人员编号
			if (cosJobPlan.getStaffCode()!= null
					&& !cosJobPlan.getStaffCode().trim().equals("")) {
				sql += " and st.staffCode like ? ";
				parms.add("%" + cosJobPlan.getStaffCode().trim() + "%");
			}
			//日期
			if (cosJobPlan.getStartDate()!= null
					&& !cosJobPlan.getStartDate().equals("")) {
				sql += " and jt.startDate between ? and ? ";
				parms.add(cosJobPlan.getStartDate());
				parms.add(cosJobPlan.getEndDate());
			}
		}
		sql += " and jt.startdate is not null order by jt.startdate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public COSJobPlanVO getCosJobPlan() {
		return cosJobPlan;
	}

	public void setCosJobPlan(COSJobPlanVO cosJobPlan) {
		this.cosJobPlan = cosJobPlan;
	}

	public COSJobPlan getJobPlan() {
		return jobPlan;
	}

	public void setJobPlan(COSJobPlan jobPlan) {
		this.jobPlan = jobPlan;
	}

}
