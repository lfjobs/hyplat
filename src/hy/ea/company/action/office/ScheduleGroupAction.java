package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.Schedule;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
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
/**
 * 公共日程安排集团汇总
 * @author zl
 *
 */
@Controller
@Scope("prototype")
public class ScheduleGroupAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private Schedule schedule;
	private PageForm pageForm;
	private int pageNumber;
	private String search; 
	private InputStream excelStream;
	
	/**
	 * 根据条件查询公共日程集团汇总列表
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", schedule);
		return getScheduleGroupList();
	}
	
	/**
	 * 公共日程集团汇总列表
	 * @return
	 */
	public String getScheduleGroupList() { 
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "schedulelist";	
	}
	
	/**
	 * 公共日程集团汇总列表、查询、导出调用
	 * @param session
	 * @param account
	 * @return
	 */
	public List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String sql = "select cor.organizationname,t.staffname,s.workcontent," 
				+ " s.workschedule,s.workstatus,s.appraise"
				+ " from dtschedule s " 
				+ " left join dt_hr_staff t on s.staffid = t.staffid"
				+ " left join dtcorganization cor on cor.organizationid = s.corganizationid"
				+ " where exists ( select c.companyid from dtcompany c"
				+ " where s.companyid = c.companyid and (c.companyid = ? or c.companypid = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")) {
			schedule = (Schedule) session.get("tablesearch");
			if(null!=schedule.getWorkContent() 
					&& !"".equals(schedule.getWorkContent().trim())){
				sql += " and s.workcontent like ?";
				parms.add("%" + schedule.getWorkContent().trim() + "%");
			}
			if(null!=schedule.getWorkSchedule() 
					&& !"".equals(schedule.getWorkSchedule().trim())){
				sql += " and s.workschedule like ?";
				parms.add("%" + schedule.getWorkSchedule().trim() + "%");
			}
			if(null!=schedule.getWorkStatus() 
					&& !"".equals(schedule.getWorkStatus().trim())){
				sql += " and s.workstatus like ?";
				parms.add("%" + schedule.getWorkStatus().trim() + "%");
			}
		}
		sql += " order by s.companyid desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}
	
	/**
	 * 公共日程集团汇总
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		
		excelStream = excelService.showExcel(Schedule.columnHeadings(), baseBeanService.getListBeanBySqlAndParams(sql, parms));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出公共日程安排集团汇总", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}	
	
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	} 
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}