package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.LogLock;
import hy.ea.bo.human.MonthSalary;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class LogLockAction {
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	private LogLock loglock;
	
	private PageForm pageForm;
	private String parameter;
	private Map<String, LogLock> loglockmap;
	private int pageNumber;
	private List<BaseBean> beans;
	private String search;
	private String months; //月份
	private String result;
	
	/**
	 * 添加或修改加锁时间
	 * 参数：addressmap ，pageNumber（分页页数）
	 * 返回：getListAddress()
	 
	public String saveLogLock() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		loglock = new LogLock();
		if(loglockmap!=null){
		for(LogLock loglocks:loglockmap.values()){
				loglocks.setLogLockID(serverService.getServerID("loglock"));
			parameter = "添加加锁(加锁账号："+account.getAccountName()+")";
			loglocks.setAccountName(account.getAccountName());
			loglocks.setCompanyID(account.getCompanyID());
			loglocks.setLockDate(new Date());
			loglocks.setStatus("00");
			beans.add(loglocks);
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "succ";
	}*/
	
	/**
	 * 日志解锁(ajax调用)
	 * @return
	 */
	public String delLogLock() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		beans = new ArrayList<BaseBean>();
		Object[] params = {account.getCompanyID(), loglock.getLogLockID()};
		String hql2="from LogLock where companyID = ? and logLockID=? ";
		LogLock log=(LogLock) baseBeanService.getBeanByHqlAndParams(hql2, params);
		log.setLockDate(new Date());
		log.setStatus("01");
		beans.add(log);
		
		String hql = " from MonthSalary where companyid = ? and staffid = ? and months = ? and status = ?";
		MonthSalary mon = (MonthSalary)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{log.getCompanyID(),
				log.getStaffID(),Utilities.getDateString(log.getStartDate(), "yyyy-MM"),"01"});
		if(mon != null){
			Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
					new Object[]{account.getStaffID()});
			mon.setDeblockperson(staff.getStaffName());  //解锁人
			mon.setDeblocktime(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss")); //解锁时间
			beans.add(mon);
		}
		
		CLogBook logBook = logBookService.saveCLogBook(null,
				log.getStaffName() + "的"+Utilities.getDateString(log.getStartDate(),
						"yyyy-MM").replace("-","年")+"月工资已解锁", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	/**
	 * 日志加锁查询
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearchlock", loglock);
		return getListLogLock();
	}
	
	/**
	 * 根据单位查看加锁信息列表
	 * 返回：pageForm
	 */
	public String getListLogLock() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> parms = new ArrayList<Object>();
		String hql = "from LogLock where companyID = ?";
		parms.add(account.getCompanyID());
		
		if(search != null && search.equals("search")){
			loglock = (LogLock)session.get("tablesearchlock");
			if(loglock.getStaffName() != null 
					&& !"".equals(loglock.getStaffName().trim())){
				hql += " and staffName like ?";
				parms.add("%" + loglock.getStaffName().trim() + "%");
			}
			if(months != null && !"".equals(months)){
				hql += " and to_char(startdate, 'yyyy-MM') like ?";
				parms.add(months + "%");
			}
			if(loglock.getStatus() != null 
					&& !"".equals(loglock.getStatus())){
				hql += " and status = ?";
				parms.add(loglock.getStatus());
			}
		}
		hql += " order by lockDate desc";
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),hql,parms.toArray());
		return "list";
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

	public Map<String, LogLock> getLoglockmap() {
		return loglockmap;
	}

	public void setLoglockmap(Map<String, LogLock> loglockmap) {
		this.loglockmap = loglockmap;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public LogLock getLoglock() {
		return loglock;
	}

	public void setLoglock(LogLock loglock) {
		this.loglock = loglock;
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

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}