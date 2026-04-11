package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.Projectplanbudget;
import hy.ea.bo.human.COSJobPlan;
import hy.ea.bo.human.COSJobTask;
import hy.ea.bo.human.LogBook;
import hy.ea.bo.human.LogConten;
import hy.ea.bo.human.LogLock;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 工作日志 LogBookAction
 */
@Controller
@Scope("prototype")
public class LogBookAction {
	private static final Logger logger = LoggerFactory.getLogger(LogBookAction.class);
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private int pageNumber;
	private String staffName;
	private LogBook logbook;
	private PageForm pageForm;
	private String sdate;
	private String edate;
	private String serachType;
	private List<BaseBean> logLocklist;
	private String status;
	private String scoreSort;
	// private File photo;

	private List<CCode> scoreSortlist;
	private Map<String, LogBook> logbookmap;
	private List<BaseBean> beans;
	
	private String companyname;		//公司名称
	private String organizationname;	//部门名称
	private String postname;  //岗位名称
	private String staffCode;    //人员编号
	private String result;
	private Map<String, LogConten> logcontenmap;
	private Projectplanbudget projectplanbudget;

	
	
	
	/**
	 * 
	 * 根据项目获取任务这个人的任务
	 * @return
	 */
	public String getTaskByproject() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from COSJobTask where projectID = ? and staffID = ? and companyID = ?";
		List<BaseBean> tasklist = baseBeanService.getListBeanByHqlAndParams(
				hql,
				new Object[] { projectplanbudget.getCsbid(),
						account.getStaffID(), account.getCompanyID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tasklist", tasklist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 获取LogConten工作内容
	 */
	public String getConten(){
		Map<String, Object> session = ActionContext.getContext().getSession(); 
		CAccount account = (CAccount) session.get("account");
		List<LogConten> logList = new ArrayList<LogConten>(); 
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanByHqlAndParams("from LogConten l where l.logbookid = ? and l.companyid = ?  ", new Object[]{logbook.getLogBookID(),account.getCompanyID()});
		if(beans != null){
			for (int i = 0; i < beans.size(); i++) {
				logList.add((LogConten)beans.get(i));
			}
		}
		
		//@author mz修改
		//Map<String,List<LogConten>> map = new HashMap<String, List<LogConten>>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("logList",logList);
		
		//获取未完成项目
		
		String hql = "from Projectplanbudget where staffid = ? and companyid = ?";
		List<BaseBean> projectlist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getStaffID(),account.getCompanyID()});
		map.put("projectlist", projectlist);
		//@author mz修改结束
		
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 获取COSJobPlan工作内容
	 */
	public String getJob(){
		
		Map<String, Object> session = ActionContext.getContext().getSession(); 
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String months = request.getParameter("tomanths");
		String yy = months==null||months.length()==0?"":months.split("-")[0];
		String xx = months==null||months.length()==0?"":months.split("-")[1];
		String day = "";
    	int ixx = Integer.parseInt(xx);
    	if(ixx<12){
    		if(ixx+1 < 10){
    			day = yy+"-0"+(ixx+1);
    		}else{
    			day = yy+"-"+(ixx+1);
    		}
    	}else{
    		day = (Integer.parseInt(yy)+1) +"-01";
    	}
		List jobList = new ArrayList(); 
		beans = new ArrayList<BaseBean>();
		String sql = "select jp.jobname from dtcosjobplan jp" +
				" left join dt_jobplan_sta sta on sta.jobplanid = jp.jobplanid" +
				" where ( jp.companyid = ? or sta.companyid = ? )" +
				" and ( jp.staffid = ? or sta.staffid = ? )" +
				"  and (jp.startDate >=  to_date(?,'yyyy-mm') and jp.startDate < to_date(?,'yyyy-mm') or jp.endDate >= to_date(?,'yyyy-mm') and jp.endDate < to_date(?,'yyyy-mm'))";
		jobList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID(),account.getCompanyID(),account.getStaffID(),account.getStaffID(),months,day,months,day});
		Map<String,List<COSJobPlan>> map = new HashMap<String, List<COSJobPlan>>();
		map.put("jobList",jobList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
		
	}
	
	/**
	 * 得到单条工作日志+
	 */
	public String getLogbookByID(){ 
		String hql = "from LogBook where logBookID= ?";
		logbook = (LogBook) baseBeanService.getObjectByHqlAndParams(hql, new Object[]{logbook.getLogBookID()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logbook",logbook);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject obj = JSONObject.fromObject(map,jsonConfig);
		result = obj.toString();
		return "success";
		
	}

	/* 
	 * 手机微网站日志修改操作
	 * */
	public String savelogMiniBook() {
		
	baseBeanService.update(logbook);
		return "success";
		
	}
	/**
	 * 保存工作日志
	 */
	@SuppressWarnings("deprecation")
	public String saveLogBook() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		logbook = new LogBook();
		List<BaseBean> logBookList = new ArrayList<BaseBean>();
		if (logbookmap != null) {
			List<String> parermiters = new ArrayList<String>();
			//Date date1 = null;
			//int num = 0;
			for (LogBook logBooks : logbookmap.values()) {
				this.logbook.setStaffID(logBooks.getStaffID());
				//num++;
				/*if (num == 1) {
					String sql = "select to_char(t.becomesdate,'yyyy-mm-dd') from dtcos c,dtaudition t where c.cosstatus=? and c.status=? and t.status = ? "
							+ " and t.companyid=c.companyid and t.staffid=c.staffid and t.staffid=? and t.companyid=? ";
					List<BaseBean> auditionList = baseBeanService
							.getListBeanBySqlAndParams(sql, new Object[] {"50","01","22",
									logBooks.getStaffID(),
									account.getCompanyID() });
					SimpleDateFormat format1 = new SimpleDateFormat(
							"yyyy-MM-dd");
					HttpServletRequest request = ServletActionContext
							.getRequest();
					if (auditionList.toArray()[0] == null) {
						request.setAttribute("message",
								"请在入职管理处填写当前员工正确的入职时间与转正时间！！！");
						return "fail";
					}
					String becomesdate = auditionList.toArray()[0].toString();
					try {
						date1 = format1.parse(becomesdate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						logger.error("操作异常", e);
					}

				}*/
				if (logBooks.getPhoto() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService
							.savePhoto(path, logBooks.getPhotoFileName(),
									logBooks.getPhoto(),
									account.getCompanyID(), "/human/logbook/"
											+ Utilities.getDateString(
													new Date(), "yyyy-MM-dd"));
					logBooks.setAttachment(photoPath);
				}

				if (null == logBooks.getLogBookID()
						|| "".equals(logBooks.getLogBookID())) {
					logBooks.setLogBookID(serverService.getServerID("logbook"));
					parameter = "添加工作日志(时间:"
							+ logBooks.getTodaydate().toLocaleString() + ")";

					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String inputTime = format.format(new Date());
					logBooks.setInputDate(inputTime);
				} else {
					parameter = "修改工作日志(时间:"
							+ logBooks.getTodaydate().toLocaleString() + ")";
				}
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						"from Staff where staffID=?", new Object[] { logBooks
								.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")   (操作时间"
						+ new Date().toLocaleString() + ")";
				if (!Utilities.isNumeric(logBooks.getBisect())) {
					logBooks.setBisect("");
				}
				/*int number1 = logBooks.getTodaydate().compareTo(date1);
				if (number1 == -1) {
					logBooks.setLogoStatus("00");// 转正的日志状态（入职前6个月的）
				} else {
					logBooks.setLogoStatus("01");// 转正后的日志状态（入职后6个月的）
				}*/
				logBooks.setCompanyID(account.getCompanyID());
				logBooks.setOrganizationID(organizationID);
				logBooks.setStatus("00");
				logBookList.add(logBooks);
				parermiters.add(parameter);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(logBookList,
					null, null);
			logBookService.saveLogsListAndexecuteHqlsByParams(organizationID,
					parermiters, account);
		}
		return "success";
	}

	/**
	 * 保存个人日志
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String savelog(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = logbook.getOrganizationID();
		List<String> parermiters = new ArrayList<String>();
		if(null != logbook.getLogBookID() && !"".equals(logbook.getLogBookID())){
			parameter = "修改工作日志(时间:" + logbook.getTodaydate().toLocaleString() + ")";
		} else {
			logbook.setLogBookID(serverService.getServerID("logbook"));
			parameter = "添加工作日志(时间:" + logbook.getTodaydate().toLocaleString() + ")";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String inputTime = format.format(new Date());
			logbook.setInputDate(inputTime);
		}
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[] { logbook.getStaffID() });
		parameter += "(人员名称:" + staff.getStaffName() + ")   (操作时间" + new Date().toLocaleString() + ")";
		if (!Utilities.isNumeric(logbook.getBisect())) {
			logbook.setBisect("");
		}
		logbook.setCompanyID(account.getCompanyID());
		logbook.setOrganizationID(organizationID);
		logbook.setStatus("00");
		beans = new ArrayList<BaseBean>();
		beans.add(logbook);
		parermiters.add(parameter);
		//删除原表数据
		String hql = "delete from LogConten l where l.companyid = ? and l.logbookid = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql}, new Object[]{account.getCompanyID(),logbook.getLogBookID()});
		if (logcontenmap != null) {
			for (LogConten log : logcontenmap.values()) {
				log.setLogcontenid(serverService.getServerID("logconten"));
				log.setLogbookid(logbook.getLogBookID());
				log.setCname(account.getAccountEmail());
				log.setCtime(new Date());
				log.setCompanyid(account.getCompanyID());
				beans.add(log);
				//任务赋完成度
				String hqltask = "from  COSJobTask where jobTaskID = ?";
				COSJobTask jobTask = (COSJobTask) baseBeanService.getBeanByHqlAndParams(hqltask,new Object[]{log.getJobTaskID()});
				if(jobTask!=null){
				jobTask.setFinishrate(log.getContactcom());
				beans.add(jobTask);
				}
			}
		}
		
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null, null);
			logBookService.saveLogsListAndexecuteHqlsByParams(logbook.getOrganizationID(),parermiters, account);
		} catch (Exception e) {
		
			logger.error("操作异常", e);
		}
		
		return "success";
	}
	
	/*
	 * 删除工作日志
	 */

	public String delLogBook() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		String hql3 = "select count(*) from LogBook where  companyID = ? and status = ? and logBookID= ?";
		Object[] params1 = { account.getCompanyID(), "01",
				logbook.getLogBookID() };
		if (baseBeanService.getConutByByHqlAndParams(hql3, params1) > 0) {
			return getListLogBook();
		}
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { logbook.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除工作日志(人员名称：" + staff.getStaffName() + ")", account);
		beans.add(logBook);
		String hql = "delete LogBook where   companyID = ? and logBookID= ? ";
		String hql4 = "delete from LogConten l where l.companyid = ? and l.logbookid = ?";
		Object[] params = { account.getCompanyID(), logbook.getLogBookID() };
		List<Object[]> promList = new ArrayList<Object[]>(); 
		promList.add(params);
		promList.add(params);
		baseBeanService.executeHqlsByParamsList(beans, new String[] { hql,hql4 }, promList);

		return "success";
	}

	/*
	 * 得到工作日志列表
	 */

	public String getListLogBook() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		//获取未完成项目
		String hql = "from Projectplanbudget where staffid = ? and companyid = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getStaffID(),account.getCompanyID()});
	    Projectplanbudget pp = new Projectplanbudget();
	    pp.setProjectname("其他");
	    pp.setCsbid("其他");
	    beans.add(pp);
		Map<String, String> map = new HashMap<String, String>();
		for (CCode b : scoreSortlist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		LogBook.setOMap(map);
		// 个人日志管理
		if (logbook == null) {
			logbook = new LogBook();
			logbook.setStaffID(account.getStaffID());
			String hqlstaff = "from CStaffCos where companyID = ?  and staffID = ?";
			Object[] paramsstaff = { account.getCompanyID(),
					logbook.getStaffID() };
			CStaffCos cosstaff = (CStaffCos) baseBeanService
					.getBeanByHqlAndParams(hqlstaff, paramsstaff);
			if (cosstaff == null) {
				return "false";
			}
			staffName = cosstaff.getStaffName();
			companyname = cosstaff.getCompanyName();
			staffCode = cosstaff.getStaffCode();
			
			String sql = "select c.organizationid,c.organizationname,d.postname"
				+ " from dtcos t"
				+ " left join dtcorganization c on t.organizationid = c.organizationid"
				+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
				+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
			Object[] orgpost = (Object[])baseBeanService.getObjectBySqlAndParams(sql, new Object[] {
					account.getCompanyID(),logbook.getStaffID()});
			logbook.setOrganizationID(orgpost[0].toString());
			organizationname = orgpost[1].toString();
			if(orgpost[2] != null){
				postname = orgpost[2].toString();
			}
			
			DetachedCriteria dc = DetachedCriteria.forClass(LogBook.class);
			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("staffID", logbook.getStaffID()));
			if ((!("").equals(status)) && null != status) {
				dc.add(Restrictions.eq("status", status));
			}
			if ((!("").equals(scoreSort)) && null != scoreSort) {
				dc.add(Restrictions.eq("scoreSort", scoreSort));
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dc.add(Restrictions.between("todaydate", dateFormat
							.parse(sdate), dateFormat.parse(edate)));
				} catch (ParseException e) {
					logger.error("操作异常", e);
				}
			}
			dc.addOrder(Order.desc("todaydate"));
			dc.addOrder(Order.desc("logBookKey"));
			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), dc);
			if("websuit".equals(serachType)){
				return "loglist";
			}if("other".equals(serachType)){
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("pageForm",pageForm);
				JSONObject obj = JSONObject.fromObject(map2,jsonConfig);
				result = obj.toString();
				return "success";
			}else{
				return "personlist";
			}
			
		}
		String hqlstaff = "from CStaffCos where companyID = ?  and staffID = ?";
		Object[] paramsstaff = { account.getCompanyID(), logbook.getStaffID() };
		CStaffCos cosstaff = (CStaffCos) baseBeanService.getBeanByHqlAndParams(
				hqlstaff, paramsstaff);
		if (cosstaff == null) {
			return "false";
		}
		staffName = cosstaff.getStaffName();
		DetachedCriteria dc = DetachedCriteria.forClass(LogBook.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", logbook.getStaffID()));
		if ((!("").equals(status)) && null != status) {
			dc.add(Restrictions.eq("status", status));
		}
		if ((!("").equals(scoreSort)) && null != scoreSort) {
			dc.add(Restrictions.eq("scoreSort", scoreSort));
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dc.add(Restrictions.between("todaydate", dateFormat
						.parse(sdate), dateFormat.parse(edate)));
			} catch (ParseException e) {
				logger.error("操作异常", e);
			}
		}
		dc.addOrder(Order.desc("todaydate"));
		dc.addOrder(Order.desc("logBookKey"));
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 4 : pageNumber), dc);
		
		return "list";	
		
				//@author mz修改结束
		
	}

	/*
	 * 导出工作日志列表
	 */

	public String showExcel() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> list;
		Map<String, String> map = new HashMap<String, String>();
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		for (CCode b : scoreSortlist) {
			map.put(b.getCodeID(), b.getCodeValue());
		}
		LogBook.setOMap(map);

		DetachedCriteria dc = DetachedCriteria.forClass(LogBook.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", logbook.getStaffID()));
		if ((!("").equals(status)) && null != status) {
			dc.add(Restrictions.eq("status", status));
		}
		if ((!("").equals(scoreSort)) && null != scoreSort) {
			dc.add(Restrictions.eq("scoreSort", scoreSort));
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dc.add(Restrictions.between("todaydate", dateFormat
						.parse(sdate), dateFormat.parse(edate)));
			} catch (ParseException e) {
				logger.error("操作异常", e);
			}
		}
		dc.addOrder(Order.desc("todaydate"));
		list = baseBeanService.getListByDC(dc);
		excelStream = excelService.showExcel(LogBook.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出工作日志", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 判断人员日志是否为加锁状态
	 * @return
	 */
	public String isLocked(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String months = request.getParameter("tomanths");
		
		String hql = "from LogLock where companyID = ? and staffID = ? and to_char(startDate, 'yyyy-MM') = ? and status= ?";
		Map<String,String> map = new HashMap<String, String>();
		if(months.indexOf(",") != -1){ //保存多条数据时调用
			String mon[] = months.split(",");
			for(int i=0;i<mon.length;i++){
				LogLock locks = (LogLock)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),
						logbook.getStaffID(),mon[i],"00"});
				if(locks != null){
					map.put("islocks", mon[i].replace("-", "年")+"月份");
				}else{
					map.put("islock", "");
				}
			}
			if(map.get("islocks") != null){
				map.put("islock",map.get("islocks"));
				JSONObject obj = JSONObject.fromObject(map);
				result = obj.toString();
				return "success";
			}
		}else{ //保存单条数据时调用
			LogLock lock = (LogLock)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),
					logbook.getStaffID(),months,"00"});
			if(lock != null){
				map.put("islock", months.replace("-", "年")+"月份");
			}else{
				map.put("islock", "");
			}
		}
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public LogBook getLogbook() {
		return logbook;
	}

	public void setLogbook(LogBook logbook) {
		this.logbook = logbook;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public List<CCode> getScoreSortlist() {
		return scoreSortlist;
	}

	public void setScoreSortlist(List<CCode> scoreSortlist) {
		this.scoreSortlist = scoreSortlist;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Map<String, LogBook> getLogbookmap() {
		return logbookmap;
	}

	public void setLogbookmap(Map<String, LogBook> logbookmap) {
		this.logbookmap = logbookmap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScoreSort() {
		return scoreSort;
	}

	public void setScoreSort(String scoreSort) {
		this.scoreSort = scoreSort;
	}

	public List<BaseBean> getLogLocklist() {
		return logLocklist;
	}

	public void setLogLocklist(List<BaseBean> logLocklist) {
		this.logLocklist = logLocklist;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, LogConten> getLogcontenmap() {
		return logcontenmap;
	}

	public void setLogcontenmap(Map<String, LogConten> logcontenmap) {
		this.logcontenmap = logcontenmap;
	}
	public Projectplanbudget getProjectplanbudget() {
		return projectplanbudget;
	}
	public void setProjectplanbudget(Projectplanbudget projectplanbudget) {
		this.projectplanbudget = projectplanbudget;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public String getSerachType() {
		return serachType;
	}
	public void setSerachType(String serachType) {
		this.serachType = serachType;
	}
	
	
}