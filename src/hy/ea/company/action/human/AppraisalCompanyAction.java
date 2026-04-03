package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.vo.StaffAppraisalSummary;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 集团--综合考评汇总
 * */
@Controller
@Scope("prototype")
public class AppraisalCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	private StaffAppraisalSummary staffAppraisal;
	private String startdate;
	private String enddate;

	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		List<Object> parms = new ArrayList<Object>();
		String sql = "select a.companyName,a.staffcode,a.staffname,a.appraisalDate,a.checkPerson,a.workDateSaturation," +
				" a.responsibility1,a.responsibility2,a.responsibility3,a.achievements1,a.achievements2," +
				" a.achievements3,a.task1,a.task2,a.task3,a.ability1,a.ability2,a.ability3,a.manner1,a.manner2," +
				" a.manner3 from view_app a  where exists(select g.companyid from dtcompany" +
				" g where a.companyid =  g.companyid and (g.companyID = ? or g.companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			staffAppraisal = (StaffAppraisalSummary) session.get("tablesearch");
			//人员姓名
			if (staffAppraisal.getStaffName()!= null
					&& !staffAppraisal.getStaffName().trim().equals("")) {
				sql += " and a.staffName like ? ";
				parms.add("%" + staffAppraisal.getStaffName().trim() + "%");
			}
			//公司
			if (staffAppraisal.getCompanyID()!= null
					&& !staffAppraisal.getCompanyID().equals("")) {
				sql += " and a.companyID = ? ";
				parms.add(staffAppraisal.getCompanyID());
			}
			//人员编号
			if (staffAppraisal.getStaffCode()!= null
					&& !staffAppraisal.getStaffCode().trim().equals("")) {
				sql += " and a.staffCode like ? ";
				parms.add("%" + staffAppraisal.getStaffCode().trim() + "%");
			}
			//日期
			if (startdate!= null && !startdate.equals("")) {
				sql += " and a.appraisalDate between ? and ? ";
				parms.add( Utilities.getDateFromString(startdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(enddate, "yyyy-MM-dd"));
			}
		}
		sql += " order by a.companyName,a.staffCode,a.appraisalDate";
		
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,parms.toArray());
		excelStream = excelService.showExcel(StaffAppraisalSummary.columnHeadings(),list);
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", staffAppraisal);
		return getAppraisalList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getAppraisalList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "getAppraisalList";
	}

	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String hql = "from StaffAppraisalSummary a where exists ( select c.companyID from Company c"
				+ " where a.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			staffAppraisal = (StaffAppraisalSummary) session.get("tablesearch");
			//人员姓名
			if (staffAppraisal.getStaffName()!= null
					&& !staffAppraisal.getStaffName().trim().equals("")) {
				hql += " and a.staffName like ? ";
				parms.add("%" + staffAppraisal.getStaffName().trim() + "%");
			}
			//公司
			if (staffAppraisal.getCompanyID()!= null
					&& !staffAppraisal.getCompanyID().equals("")) {
				hql += " and a.companyID = ? ";
				parms.add(staffAppraisal.getCompanyID());
			}
			//人员编号
			if (staffAppraisal.getStaffCode()!= null
					&& !staffAppraisal.getStaffCode().trim().equals("")) {
				hql += " and a.staffCode like ? ";
				parms.add("%" + staffAppraisal.getStaffCode().trim() + "%");
			}
			//日期
			if (startdate!= null && !startdate.equals("")) {
				hql += " and a.appraisalDate between ? and ? ";
				parms.add( Utilities.getDateFromString(startdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(enddate, "yyyy-MM-dd"));
			}
		}
		hql += " order by a.companyName,a.staffCode,a.appraisalDate";
		results.add(hql);
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

	public StaffAppraisalSummary getStaffAppraisal() {
		return staffAppraisal;
	}

	public void setStaffAppraisal(StaffAppraisalSummary staffAppraisal) {
		this.staffAppraisal = staffAppraisal;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	

}
