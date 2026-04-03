package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Schedule;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 公共日程安排Action
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ScheduleAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	@Resource
	private COrganizationService organizationService;
	
	private Schedule schedule;
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private int pageNumber;
	
	private List<BaseBean> staffList; 
	private List<COrganization> orgnizationList;
	private InputStream excelStream;
	
	//保存公共日程安排信息
	public String saveSchedule(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		if(null==schedule.getScheduleID()||"".equals(schedule.getScheduleID())){
			schedule.setScheduleID(serverService.getServerID("schedule"));
			parameter = "添加公共日程安排(工作内容:"+schedule.getWorkContent()+")";
		}else{
			String hql = "from Schedule where companyID = ? and  scheduleID = ? ";
			Schedule s = (Schedule) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),schedule.getScheduleID()});
			parameter = "修改公共日程安排(工作内容:"+s.getWorkContent()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		schedule.setCompanyID(companyID);
		schedule.setOrganizationID(organizationID);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(schedule);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除公共日程安排信息
	 public String delSchedule()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),schedule.getScheduleID()};
		    String hql2="from Schedule where companyID=?  and scheduleID = ? ";
		    Schedule s=(Schedule) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除公共日程安排(工作内容:"+s.getWorkContent()+")", account);
	    	String[] hql={"delete from Schedule where companyID=?  and scheduleID= ? "};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			
		    return "success";
	    }
	 
	 //根据条件查询公共日程列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", schedule);
			return getScheduleList();
		}
	 // 公共日程列表
		public String getScheduleList() { 
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "schedulelist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			

			Map<String,String> ccmap = new HashMap<String,String>();
			//在职员工列表
			String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and organizationID = ? and cosStatus = '50' ) ";
			Object[] params = {companyID, organizationID};
			staffList = new ArrayList<BaseBean>();
			staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);
			//部门列表 
			orgnizationList  = new ArrayList<COrganization>();
			orgnizationList =	organizationService.getOrganizationList(organizationID,companyID);
			if(null!=staffList){
				for(BaseBean b:staffList){
					Staff s = (Staff)b;
					ccmap.put(s.getStaffID(), s.getStaffName());
				}
			}
			if(null!=orgnizationList){
				for(COrganization o:orgnizationList){  
					ccmap.put(o.getOrganizationID(), o.getOrganizationName());
				}
			}
			Schedule.setOMap(ccmap);
			
			DetachedCriteria dc = DetachedCriteria.forClass(Schedule.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				schedule = (Schedule) session.get("tablesearch");
				if(null!=schedule.getWorkStatus()&&!"".equals(schedule.getWorkStatus()))
				{
					dc.add(Restrictions.like("workStatus", schedule.getWorkStatus(), MatchMode.ANYWHERE));
				} 
				if(null!=schedule.getCorganizationID()&&!"".equalsIgnoreCase(schedule.getCorganizationID())){
					dc.add(Restrictions.eq("corganizationID",schedule.getCorganizationID()));
				}
				if(null!=schedule.getStaffID()&&!"".equalsIgnoreCase(schedule.getStaffID())){
					dc.add(Restrictions.eq("staffID",schedule.getStaffID()));
				}
			} 
			return dc;
		}
		
		// 导出
		public String showScheduleExcel() {
			excelStream = excelService.showExcel(Schedule.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"导出公共日程安排", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}	
		
		public Schedule getSchedule() {
			return schedule;
		}
		public void setSchedule(Schedule schedule) {
			this.schedule = schedule;
		}
		public List<COrganization> getOrgnizationList() {
			return orgnizationList;
		}
		public void setOrgnizationList(List<COrganization> orgnizationList) {
			this.orgnizationList = orgnizationList;
		}
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		} 
		public String getParameter() {
			return parameter;
		}
		public void setParameter(String parameter) {
			this.parameter = parameter;
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
		public List<BaseBean> getStaffList() {
			return staffList;
		}
		public void setStaffList(List<BaseBean> staffList) {
			this.staffList = staffList;
		} 
}
