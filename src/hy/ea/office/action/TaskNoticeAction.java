package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.TaskNotice;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
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

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 任务通知单管理（Action）
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class TaskNoticeAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
    private TaskNotice taskNotice;
	private PageForm pageForm;
	private InputStream excelStream;
	
	
	private int pageNumber;
	private String result;
	private String search;
	
	private Company company;
	private List<BaseBean> organizationlist;
	
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//任务通知单保存
    public String saveTaskNotice()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");

		if(null!=taskNotice.getEnFile()){
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,taskNotice.getEnFileFileName(), taskNotice.getEnFile(), account.getCompanyID(),"/office/tasknotice/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			taskNotice.setEnFilePath(photoPath);
		}
		if(null==taskNotice.getTaskNoticeID()||"".equals(taskNotice.getTaskNoticeID())){
			taskNotice.setTaskNoticeID(serverService.getServerID("tasknotice"));
			parameter = "添加任务通知单(凭证号:"+taskNotice.getVoucherNum()+")";
			
		}else{
			Object[] params = {account.getCompanyID(),taskNotice.getTaskNoticeID()};
			 String hql="from TaskNotice where  companyID=?  and taskNoticeID=?";
			 TaskNotice l=(TaskNotice) baseBeanService.getBeanByHqlAndParams(hql, params);
			 parameter = "修改任务通知单(凭证号:"+l.getVoucherNum()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		taskNotice.setOrganizationID(organizationID);
		taskNotice.setCompanyID(account.getCompanyID());
		beans.add(taskNotice);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除任务通知单
	 public String delTaskNotice()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),taskNotice.getTaskNoticeID()};
			 String hql2="from TaskNotice where  companyID=?  and taskNoticeID = ?";
			 TaskNotice l=(TaskNotice) baseBeanService.getBeanByHqlAndParams(hql2, params);
			 CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除任务通知单(凭证号:"+l.getVoucherNum()+")", account);
			 String[] hql={"delete from TaskNotice where companyID=?  and taskNoticeID = ?"};
			 List<BaseBean> beans=new ArrayList<BaseBean>();
			 beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			return "success";
	    }
	//根据条件查询任务通知单
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", taskNotice);
		return getTaskNoticeList();
	}
	//任务通知单列表
	public String getTaskNoticeList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = {companyID};
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "tasknoticelist";	
	}
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(TaskNotice.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			taskNotice = (TaskNotice) session.get("tablesearch");
			if(null!=taskNotice.getVoucherNum()&&!taskNotice.getVoucherNum().equals(""))
			{
				dc.add(Restrictions.like("voucherNum", taskNotice.getVoucherNum(),MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("deptID", taskNotice.getDeptID()));
		} 
		return dc;
	}

	
	// 导出任务通知单列表

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = {companyID};
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : organizationlist) {
			COrganization c=(COrganization) b;
			map.put(c.getOrganizationID(), c.getOrganizationName());
		}
		map.put("00", "一般通知");
		map.put("01", "紧急通知");
		
		map.put(company.getCompanyID(), company.getCompanyName());
		TaskNotice.setOMap(map);
		organizationlist.add(company);
		excelStream = excelService.showExcel(TaskNotice.columnHeadings(), baseBeanService.getListByDC(getList()));
		String organizationID=(String) session.get("organizationID");
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出任务通知单", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
	
	public TaskNotice getTaskNotice() {
		return taskNotice;
	}

	public void setTaskNotice(TaskNotice taskNotice) {
		this.taskNotice = taskNotice;
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

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	} 

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Company getCompany() {
		return company;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	 
	 
}
