package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.LogLock;
import hy.ea.bo.human.MonthSalary;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.bo.human.vo.LogBookSummary;
import hy.ea.bo.human.vo.LokBookPrintVO;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.human.service.LogBookService;
import hy.ea.human.service.impl.LogBookServiceImp;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 工作日志汇总 LogBookSummaryAction
 * 
 * @author
 * 
 */
@Controller
@Scope("prototype")
public class LogBookSummaryAction {
	private static final Logger logger = LoggerFactory.getLogger(LogBookSummaryAction.class);
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;
	@Resource
	private LogBookService showlogBookService;

	private LogBookSummary logbooksummary;

	private COrganization corganization;
	private PageForm pageForm;
	private String sdate;
	private String edate;
	private String search;
	private String staffName;
	private String result;
	private String companyID;
	private String status;
	private String aa;
	/**
	 * code表中所有有关工资的类别结合
	 */
	private List<CCode> scoreSortlist;
	private int pageNumber;
	// private List<BaseBean> comlist;
	/**
	 * 所有员工工资集合
	 */
	private List<BaseBean> wages;
	private LokBookPrintVO logBookPrintVo;
	private List<BaseBean> listLogBookSummary;
	private List<LokBookPrintVO> logBookPrintVoList;
	private String logoStatusVar;

	public String titleVar;
	private List<Object> add;

	private List<Object> cut;
	
	private String arg;
	
	private String staffcategoryid;
	
	private String all;
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", logbooksummary);
		return getListLogBook();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(LogBookSummary.class);
		dc.add(Restrictions.eq("companyID", companyID));
		if(aa.equals("aa")){
			dc.add(Restrictions.eq("organizationID", organizationID));
		}
		if (search != null && search.equals("search")) {
			this.logbooksummary = (LogBookSummary) session.get("tablesearch");
			dc.add(Restrictions.like("staffName",
					logbooksummary.getStaffName(), MatchMode.ANYWHERE));
			dc.add(Restrictions.like("scoreSort",
					logbooksummary.getScoreSort(), MatchMode.ANYWHERE));
			if (!("").equals(logbooksummary.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", logbooksummary
						.getStaffCode(), MatchMode.ANYWHERE));
			}
			if (!companyID.equals(logbooksummary.getOrganizationID())) {
				dc.add(Restrictions.like("organizationID", logbooksummary
						.getOrganizationID(), MatchMode.ANYWHERE));
			}
			if (!("").equals(sdate) && sdate != null && edate != null
					&& !("").equals(edate)) {
				dc.add(Restrictions.between("todaydate", Utilities
						.getDateFromString(sdate, "yyyy-MM-dd"), Utilities
						.getDateFromString(edate, "yyyy-MM-dd")));
			}
			if (null != logbooksummary.getStatus()
					&& !("").equalsIgnoreCase(logbooksummary.getStatus())) {
				dc.add(Restrictions.eq("status", logbooksummary.getStatus()));
			}

		}else{

			Calendar calendar1 = Calendar.getInstance();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			calendar1.add(Calendar.DATE, -3);
			String sdate = sdf1.format(calendar1.getTime());


			dc.add(Restrictions.between("todaydate", Utilities
					.getDateFromString(sdate, "yyyy-MM-dd"), new Date()));

		}
		dc.addOrder(Order.asc("staffID"));
		dc.addOrder(Order.desc("todaydate"));
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		return dc;
	}

	/**
	 * 工作日志汇总列表
	 * 
	 * @return
	 */
	public String getListLogBook() {
		if(aa.equals("aa")){
			//部门在职员工日志汇总;
			pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					getList());
			return "departmentlist";
		}else
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "list";
	}

	/**
	 * 导出工作日志
	 * 
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(LogBookSummary.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出工作日志", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 积分汇总（员工工资管理）
	 * 
	 * @return
	 */
	public String getListLogBookIntegral() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// SalaryIntegral 考评表
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		if(arg!=null&&arg.equals("1")){
			SalaryIntegral.setArg("1");
			LogBookServiceImp.setTitle(1);
			 sb_search= new StringBuffer(VOtool.getWagesHqlX());
		}else{
			SalaryIntegral.setArg("0");
			LogBookServiceImp.setTitle(0);
			 sb_search= new StringBuffer(VOtool.getWagesHql());
			
		}
		StringBuffer sb_count = new StringBuffer(
				"select count(*) from LogBookSummary j where j.logBookKey in "
						+ "( select max(j.logBookKey) from LogBookSummary j ");		
		sb_search.append(" where j.companyID=? ");
		sb_count.append(" where j.companyID=? ");
		parms.add(account.getCompanyID());
		sb_search.append(" and todaydate between ? and ? ");
		sb_count.append(" and todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if (staffName != null && !"".equals(staffName)) {
			sb_search.append(" and j.staffName like ? ");
			sb_count.append(" and j.staffName like ? ");
			parms.add("%" + staffName.trim() + "%");
		}
		if(staffcategoryid!=null&&!"".equals(staffcategoryid)){
			sb_search.append(" and j.staffcategoryid= ? ");
			sb_count.append(" and j.staffcategoryid= ? ");
			parms.add(staffcategoryid);
		}
		
		if(arg!=null&&arg.equals("1")){
			sb_search.append(" and j.bisect is not null "
					+ "group by to_char(j.todaydate,'yyyy-MM'),j.staffName,j.staffID,j.categoryname order by j.staffName,to_char(j.todaydate, 'yyyy-MM') ");
			sb_count.append(" and j.bisect is not null group by to_char(j.todaydate, 'yyyy-MM'),j.staffID,j.categoryname) ");
		}else{	
		sb_search.append(" and j.bisect is not null "
				+ "group by j.staffName,j.staffID,j.categoryname order by j.staffName ");
		sb_count.append(" and j.bisect is not null group by j.staffID,j.categoryname) ");
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				sb_search.toString(), sb_count.toString(), parms.toArray());
		getDynamicWage(pageForm==null?null:pageForm.getList(),account.getCompanyID(),"");
		return "LogBookIntegral";
	}

	/**
	 * 每月3号凌晨对上月日志加锁并保存月工资
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public void MonthSalsry(){
		Calendar c = Calendar.getInstance(); 
		Date date = new Date();
		try {
			c.setTime(new SimpleDateFormat("yy-MM-dd").parse(date.toLocaleString()));
		} catch (ParseException e) {
			logger.error("操作异常", e);
		} 
		int month=c.get(Calendar.MONTH);
		c.set(Calendar.MONTH,month-3); 
		String monthBefore=new SimpleDateFormat("yyyy-MM").format(c.getTime()); //获取当前时间的上一个月
		String lastday = String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH)); //获取月份最后一天
		sdate = monthBefore + "-01";
		edate = monthBefore + "-" + lastday; 
		List<BaseBean> listBaseBean = new ArrayList<BaseBean>();
		
		String accsql = "select t.companyid,t.staffid,s.staffname from dtcos t"
				+ " left join dt_hr_staff s on s.staffid = t.staffid"
				+ " where t.cosstatus = ? and t.status = ?"
				+ " order by t.companyid,t.staffid";
	
		List<BaseBean> accountlist = baseBeanService.getListBeanBySqlAndParams(accsql,
			new Object[]{"50","01"});
		for (Iterator iter = accountlist.iterator(); iter.hasNext();) {
			Object[] obj=(Object[])iter.next();
			LogLock logLock = new LogLock();
			logLock.setLogLockID(serverService.getServerID("loglock"));
			logLock.setCompanyID(obj[0].toString());
			logLock.setStaffID(obj[1].toString());
			logLock.setStaffName(obj[2].toString());
			logLock.setStartDate(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));  //加锁起日期
			logLock.setEndDate(Utilities.getDateFromString(edate, "yyyy-MM-dd"));    //加锁止日期
			logLock.setLockDate(new Date());                                         //加锁时间
			logLock.setStatus("00");                                                 //加锁状态
			
			listBaseBean.add(logLock); //日志加锁
		}
		
		String comhql = "from Company where companyStatus = ?";
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(comhql, new Object[]{"00"});
		for(BaseBean basebean : companylist){
			Company companys = (Company)basebean;
			getAlls(companys.getCompanyID(),monthBefore,"",listBaseBean,"");
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(listBaseBean, null, null);
	}
	
	/**
	 * 日志手动加锁(ajax调用)
	 * @return
	 */
	public String logLocked(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		List<BaseBean> listBaseBean = new ArrayList<BaseBean>();
		Object[] params = {account.getCompanyID(), request.getParameter("logLockID")};
		String hql2="from LogLock where companyID = ? and logLockID=? ";
		LogLock log=(LogLock) baseBeanService.getBeanByHqlAndParams(hql2, params);
		log.setLockDate(new Date());
		log.setStatus("00");
		listBaseBean.add(log);
		
		sdate = Utilities.getDateString(log.getStartDate(), "yyyy-MM-dd");
		edate = Utilities.getDateString(log.getEndDate(), "yyyy-MM-dd");
		
		CLogBook logBook = logBookService.saveCLogBook(null,
				log.getStaffName() + "的"+Utilities.getDateString(log.getStartDate(),
						"yyyy-MM").replace("-","年")+"月工资已加锁", account);
		listBaseBean.add(logBook);
		
		String hql = "from MonthSalary where companyid = ? and staffid = ? and months = ? and status = ?";
		MonthSalary mon = (MonthSalary)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{log.getCompanyID(),
				log.getStaffID(),Utilities.getDateString(log.getStartDate(), "yyyy-MM"),"01"});
		if(mon != null){
			mon.setStatus("00"); //历史数据状态改为解锁（记录）
			listBaseBean.add(mon);
		}
		
		String chql = "from COS where companyID = ? and staffID = ? and cosStatus = ? and status = ?";
		COS cos = (COS)baseBeanService.getBeanByHqlAndParams(chql, new Object[]{log.getCompanyID(),log.getStaffID(),"50","01"});
		if(cos != null){ //在职员工
			getAlls(log.getCompanyID(),Utilities.getDateString(log.getStartDate(), "yyyy-MM"),log.getStaffID(),listBaseBean,"");
		}else{ //离职员工
			getAlls(log.getCompanyID(),Utilities.getDateString(log.getStartDate(), "yyyy-MM"),log.getStaffID(),listBaseBean,"dimcos");
		}
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(listBaseBean, null, null);
		return "success";
	}
	
	/**
	 * 保存月工资记录调用
	 * @param companyID
	 * @param months
	 * @param staffID
	 * @param listBaseBean
	 * @param str
	 */
	@SuppressWarnings("static-access")
	private void getAlls(String companyID,String months,String staffID,List<BaseBean> listBaseBean,String str){
		List<Object> parms = new ArrayList<Object>();
		String hql = "";
		String hql1 = "select new SalaryIntegral(max(j.logBookKey),max(j.companyName),j.staffID,max(j.staffName),"
			+ " max(j.categoryname),max(j.postname),"
			+ " sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000003' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000002' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20120525f865m47gmi0000000002' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000004' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000003' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000004' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000006' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000031' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000005' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000006' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000030' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000029' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000032' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000007' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000008' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000033' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000034' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000035' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000036' then j.bisect else '0' end) ,"
			+ " sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000005' then j.bisect else '0' end) ) "
			+ " from LogBookSummary j ";
		
		String hql2 = " where j.companyID=? and to_char(todaydate, 'yyyy-MM') like ? and j.bisect is not null "
			+ " group by j.staffName,j.staffID,j.categoryname order by j.staffName ";
		
		String hql3 = " where j.companyID=? and to_char(todaydate, 'yyyy-MM') like ? and j.staffID=? and j.bisect is not null "
			+ " group by j.staffName,j.staffID,j.categoryname order by j.staffName ";
		
		if(staffID != null && !"".equals(staffID)){
			if(str.equals("dimcos")){
				hql1 = hql1.substring(0,hql1.indexOf("from"))+ " from DimissionLogBookSummary j ";
				hql = hql1 + hql3;
			}else{
				hql = hql1 + hql3;
			}
			parms.add(companyID);
			parms.add(months+"%");
			parms.add(staffID);
		}else{
			hql = hql1 + hql2;
			parms.add(companyID);
			parms.add(months+"%");
		}
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,parms.toArray());
		if(str.equals("dimcos")){
			getDynamicWage(list==null?null:list,companyID,str);
		}else{
			getDynamicWage(list==null?null:list,companyID,"");
		}
		for(BaseBean base : list){
			SalaryIntegral salary = (SalaryIntegral)base;
			MonthSalary monthSalary = new MonthSalary();
			monthSalary.setMonthsalaryid(serverService.getServerID("monthSalary"));
			monthSalary.setCompanyid(companyID);
			monthSalary.setCompanyname(salary.getCompanyName());
			monthSalary.setStaffid(salary.getStaffID());
			monthSalary.setStaffname(salary.getStaffName());
			monthSalary.setCategoryname(salary.getCategoryname());  //工种类别
			monthSalary.setPostname(salary.getPostname());          //职位
			monthSalary.setMonths(months);                          //月份 
			
			monthSalary.setBasicintegral(salary.getBasicIntegral());                //基本积分
			monthSalary.setFunzioneintegral(salary.getFunzioneIntegral());          //职务职责得分
			monthSalary.setWeekendintegral(salary.getWeekendIntegral());            //周末加班
			monthSalary.setWorknightintegral(salary.getWorkNightIntegral());        //晚上加班
			monthSalary.setWorkholidaysintegral(salary.getWorkHolidaysIntegral());  //节假日加班
			monthSalary.setTargettaskintegral(salary.getTargetTaskIntegral());      //目标任务考核积分
			monthSalary.setAppraisalintegral(salary.getAppraisalIntegral());        //月考评
			monthSalary.setTaskintegral(salary.getTaskIntegral());                  //任务得分
			monthSalary.setRewardintegral(salary.getRewardIntegral());              //奖励得分
			monthSalary.setSafetyintegral(salary.getSafetyIntegral());              //安全得分
			monthSalary.setHolidaysintegral(salary.getHolidaysIntegral());          //单位承担
			monthSalary.setPersonaldiscount(salary.getPersonalDiscount());          //个人所得税
			monthSalary.setStpay(salary.getStPay());                                //特殊人才
			monthSalary.setSecrecypay(salary.getSecrecyPay());                      //保密工资
			monthSalary.setTaskdiscount(salary.getTaskDiscount());                  //任务折扣
			monthSalary.setSafetydiscount(salary.getSafetyDiscount());              //暂扣安全积分
			monthSalary.setAttendancediscount(salary.getAttendanceDiscount());      //考勤折扣
			monthSalary.setPersonalsocialsecurity(salary.getPersonalSocialSecurity()); //个人应交社保
			monthSalary.setPersonalreservedfunds(salary.getPersonalReservedFunds());   //个人应交公积金
			monthSalary.setViolationdiscount(salary.getViolationDiscount());           //违规折扣
			
			if(salary.getWageFiledAdd() != null){
				monthSalary.setWagefiledadd(salary.getWageFiledAdd().toString());      //自定义加分项字段名
			}
			if(salary.getCustomWageAdd() != null){
				monthSalary.setCustomwageadd(salary.getCustomWageAdd().toString());    //自定义加分项分数
			}          
			if(salary.getWageFiledCut() != null){
				monthSalary.setWagefiledcut(salary.getWageFiledCut().toString());      //自定义减分项字段名
			}
			if(salary.getCustomWageCut() != null){
				monthSalary.setCustomwagecut(salary.getCustomWageCut().toString());    //自定义减分项分数
			}
			monthSalary.setDueintegral(salary.getDueIntegral());                       //应得积分
			monthSalary.setObtainedintegral(salary.getObtainedIntegral());             //实得积分
			monthSalary.setObtainedmenoy(salary.getObtainedMenoy());                   //实得工资
			
			monthSalary.setStatus("01"); //加锁状态                                   
			monthSalary.setLockedtime(Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss")); //加锁时间
			if(staffID != null && !"".equals(staffID)){ 
				Map<String, Object> session = ActionContext.getContext().getSession();
				CAccount account = (CAccount) session.get("account");
				Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
						new Object[]{account.getStaffID()});
				monthSalary.setLockperson(staff.getStaffName()); //加锁人
			}
			
			listBaseBean.add(monthSalary); //保存工资
		}
	}
	
	/**
	 * 获取动态工资类别列表
	 * @param list
	 * @param comID
	 * @param str
	 */
	private void getDynamicWage(List<BaseBean> list,String comID,String str) {
		String hql = " from CCode where companyID=? and codeStatus=? and wageStatus=? and codePID=?";
		String sqlTail = "";
		if(str.equals("dimcos")){ //离职员工获取动态工资类别列表
			sqlTail = " from view_DimissionLogBookSummary j  where j.companyID=?  and j.todaydate between ? and ?  and j.staffID=?  and j.bisect is not null ";
		}else{ //在职员工获取动态工资类别列表
			sqlTail = " from view_LogBookSummary j  where j.companyID=?  and j.todaydate between ? and ?  and j.staffID=?  and j.bisect is not null ";
		}
		List<BaseBean> addCCodeList = baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] {
						comID, "01", "00",
						"scode201007306kdf8m76me0000000001" });
		List<BaseBean> cutCCodeList = baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] {
						comID, "01", "01",
						"scode201007306kdf8m76me0000000001" });
		StringBuffer sqlAdd = new StringBuffer();
		if (addCCodeList != null && addCCodeList.size() > 0) {
			List<Object> filedAdd = new ArrayList<Object>();
			sqlAdd.append("select ");
			for (int j = 0; j < addCCodeList.size(); j++) {
				CCode ccode = (CCode) addCCodeList.get(j);
				filedAdd.add(ccode.getCodeValue());
				sqlAdd.append("sum(case when j.scoreSort = '"
						+ ccode.getCodeID() + "' then j.bisect else '0' end),");
			}
			SalaryIntegral.setWageFiledAdd(filedAdd);
			setAdd(filedAdd);
			sqlAdd.deleteCharAt(sqlAdd.lastIndexOf(","));
			sqlAdd.append(sqlTail);
		}else {
			SalaryIntegral.setWageFiledAdd(null);
		}
		StringBuffer sqlCut = new StringBuffer();
		if (cutCCodeList != null && cutCCodeList.size() > 0) {
			List<Object> filedCut = new ArrayList<Object>();
			sqlCut.append("select ");
			for (int j = 0; j < cutCCodeList.size(); j++) {
				CCode ccode = (CCode) cutCCodeList.get(j);
				filedCut.add(ccode.getCodeValue());
				sqlCut.append("sum(case when j.scoreSort = '"
						+ ccode.getCodeID() + "' then j.bisect else '0' end),");
			}
			SalaryIntegral.setWageFiledCut(filedCut);
			setCut(filedCut);
			sqlCut.deleteCharAt(sqlCut.lastIndexOf(","));
			sqlCut.append(sqlTail);
		}else {
			SalaryIntegral.setWageFiledCut(null);
		}

		List<Object> parms1 = new ArrayList<Object>();
		parms1.add(comID);
		parms1.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms1.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));

		int index2 = parms1.size();
		/**
		 * 查询间隔月数  一个月之内为1,俩个月为2
		 */
		int monthNum=DateUtil.getMonthNum(Utilities.getDateFromString(sdate, "yyyy-MM-dd"), Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				SalaryIntegral salaryIntegral = (SalaryIntegral) list.get(i);
				parms1.add(index2, salaryIntegral.getStaffID());
				if(arg!=null&&arg.equals("1")){
					/*if(monthNum==1){
						logger.info("无操作");
					}*/
					if(monthNum>=2){
						if(salaryIntegral.getLogBookKey().equals(sdate.substring(0, 7))){
							parms1.set(1, Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
							try {
								parms1.set(2, Utilities.getDateFromString(DateUtil.getDateOfMonthEnd(sdate,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								logger.error("操作异常", e);
							}
						}
						if(salaryIntegral.getLogBookKey().equals(edate.substring(0, 7))){
							try {
								parms1.set(1, Utilities.getDateFromString(DateUtil.getDateOfMonthBegin(edate,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								logger.error("操作异常", e);
							}
							parms1.set(2, Utilities.getDateFromString(edate, "yyyy-MM-dd"));
						}
						if(!salaryIntegral.getLogBookKey().equals(sdate.substring(0, 7))&&!salaryIntegral.getLogBookKey().equals(edate.substring(0, 7))){
							try {
								parms1.set(1, Utilities.getDateFromString(DateUtil.getDateOfMonthBegin(salaryIntegral.getLogBookKey()+"-01","yyyy-MM-dd"), "yyyy-MM-dd"));
								parms1.set(2, Utilities.getDateFromString(DateUtil.getDateOfMonthEnd(salaryIntegral.getLogBookKey()+"-01" ,"yyyy-MM-dd"), "yyyy-MM-dd"));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								logger.error("操作异常", e);
							}
						}
					}
				}
				if (addCCodeList != null && addCCodeList.size() > 0) {
					List<Object> filedValueAdd = new ArrayList<Object>();
					Object sqlListAdd = baseBeanService.getObjectBySqlAndParams(
							sqlAdd.toString(), parms1.toArray());
					if (sqlListAdd instanceof Object[]) {
						Object[] obj = (Object[]) (sqlListAdd);
						for (int j = 0; j < obj.length; j++) {
							filedValueAdd.add(obj[j]);
						}
					} else {
						filedValueAdd.add(sqlListAdd);
					}
					salaryIntegral.setCustomWageAdd(filedValueAdd);
				}
				if (cutCCodeList != null && cutCCodeList.size() > 0) {
					List<Object> filedValueCut = new ArrayList<Object>();
					Object sqlListCut = baseBeanService.getObjectBySqlAndParams(
							sqlCut.toString(), parms1.toArray());
					if (sqlListCut instanceof Object[]) {
						Object[] obj1 = (Object[]) (sqlListCut);
						for (int j = 0; j < obj1.length; j++) {
							filedValueCut.add(obj1[j]);
						}
					} else {
						filedValueCut.add(sqlListCut);
					}
					salaryIntegral.setCustomWageCut(filedValueCut);
				}
				parms1.remove(index2);
			}
		}
	}

	/**
	 * 查询全部员工工资，供导出调用
	 * 
	 * @return
	 */
	private List<BaseBean> salaryIntegralList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		if(arg!=null&&arg.equals("1")){
			 sb_search= new StringBuffer(VOtool.getWagesHqlX());
		}else{
			 sb_search= new StringBuffer(VOtool.getWagesHql());
		}
		StringBuffer sb_count = new StringBuffer(
				"select count(*) from LogBookSummary j where j.logBookKey in "
						+ "( select max(j.logBookKey) from LogBookSummary j ");	
		sb_search.append(" where j.companyID=? ");
		sb_count.append(" where j.companyID=? ");
		parms.add(account.getCompanyID());
		sb_search.append("  and todaydate between ? and ? ");
		sb_count.append(" and todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if (logoStatusVar != null && !"".equals(logoStatusVar)) {
			sb_search.append("  and j.logoStatus = ? ");
			parms.add(logoStatusVar);
		}
		if (staffName != null && !"".equals(staffName)) {
			sb_search.append("  and j.staffName like ? ");
			sb_count.append(" and j.staffName like ? ");
			parms.add("%" + staffName + "%");
		}
		if(staffcategoryid!=null&&!"".equals(staffcategoryid)){
			sb_search.append(" and j.staffcategoryid= ? ");
			sb_count.append(" and j.staffcategoryid= ? ");
			parms.add(staffcategoryid);
		}
		
		if(arg!=null&&arg.equals("1")){
			sb_search.append(" and j.bisect is not null "
					+ "group by to_char(j.todaydate,'yyyy-MM'),j.staffName,j.staffID,j.categoryname order by j.staffName,to_char(j.todaydate, 'yyyy-MM') ");
			sb_count.append(" and j.bisect is not null group by to_char(j.todaydate, 'yyyy-MM'),j.staffID,j.categoryname) ");
		}else{	
		sb_search.append(" and j.bisect is not null "
				+ "group by j.staffName,j.staffID,j.categoryname order by j.staffName ");
		sb_count.append(" and j.bisect is not null group by j.staffID,j.categoryname) ");
		}
		
		if ("all".equals(all)) {
			List<BaseBean>  list=baseBeanService.getListBeanByHqlAndParams(sb_search.toString(), parms.toArray());
			pageForm=new PageForm();
			pageForm.setList(list);
			
		}else{
			pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					sb_search.toString(), sb_count.toString(), parms.toArray());
		}
		return pageForm!=null?pageForm.getList():null;

	}

	/**
	 * 打印全部员工工资
	 * 
	 * @return
	 */
	public String printWages() {
		wages = salaryIntegralList();
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		getDynamicWage(wages,account.getCompanyID(),"");
		Company entity = (Company) baseBeanService.getBeanByHqlAndParams(
				" from Company where companyID = ? ", new Object[] { account
						.getCompanyID() });
		request.setAttribute("companyname", entity.getCompanyName());
		titleVar = sdate
				+ "至"
				+ edate
				+ (logoStatusVar != null && !"".equals(logoStatusVar) ? "01"
						.equals(logoStatusVar) ? "工资表" : "培训补助费" : "");
		String organizationID = (String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"打印全部员工工资", account);
		baseBeanService.update(logBook);
		return "printwage";
	}

	/**
	 * 单条打印所有员工工资
	 * 
	 * @return
	 */
	public String printEveryoneWages() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		wages = salaryIntegralList();
		getDynamicWage(wages,account.getCompanyID(),"");
		Company entity = (Company) baseBeanService.getBeanByHqlAndParams(
				" from Company where companyID = ? ", new Object[] { account
						.getCompanyID() });
		request.setAttribute("companyname", entity.getCompanyName());
		if (edate != null && !edate.equals(""))
			request.setAttribute("today", edate);
		String organizationID = (String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"单个打印员工工资", account);
		baseBeanService.update(logBook);
		return "printEveryoneWages";
	}

	/**
	 * 导出全部员工工资
	 * 
	 * @return
	 */
	 public String showIntegralExcel()
	 {
	 excelStream =
	 showlogBookService.showExcel(SalaryIntegral.columnHeadings(),
	 salaryIntegralList());
	 Map<String, Object> session = ActionContext.getContext().getSession();
	 CAccount account = (CAccount) session.get("account");
	 String organizationID = (String) session.get("organizationID");
	 CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出员工工资",
	 account);
	 baseBeanService.update(logBook);
	 return "showexcel";
	 }
	// ///////////////*****************公司工作日志汇总********************/
	public String tocompanySearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", logbooksummary);
		return getListcompanyLogBook();
	}

	private DetachedCriteria getcompanyList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(LogBookSummary.class);
		dc.add(Restrictions
				.in("companyID", companyserverService
						.getCompanyAndItsChildrenIDs((String) session
								.get("companyID"))));
		if (search != null && search.equals("search")) {
			this.logbooksummary = (LogBookSummary) session.get("tablesearch");
			dc.add(Restrictions.like("staffName",
					logbooksummary.getStaffName(), MatchMode.ANYWHERE));
			dc.add(Restrictions.like("scoreSort",
					logbooksummary.getScoreSort(), MatchMode.ANYWHERE));
			if (!("").equals(logbooksummary.getStaffCode())) {
				dc.add(Restrictions.like("staffCode", logbooksummary
						.getStaffCode(), MatchMode.ANYWHERE));
			}
			if (logbooksummary.getCompanyID() != null
					&& !"".equals(logbooksummary.getCompanyID()))
				dc.add(Restrictions.eq("companyID", logbooksummary
						.getCompanyID()));
			if (!("").equals(sdate) && sdate != null && edate != null
					&& !("").equals(edate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dc.add(Restrictions.between("todaydate", dateFormat
							.parse(sdate), dateFormat.parse(edate)));
				} catch (ParseException e) {
					logger.error("操作异常", e);
				}
			}
		}
		dc.addOrder(Order.asc("companyID"));
		dc.addOrder(Order.asc("staffID"));
		dc.addOrder(Order.desc("todaydate"));
		scoreSortlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201007306kdf8m76me0000000001");
		return dc;
	}

	/**
	 * 工作日志汇总列表
	 * 
	 * @return
	 */
	public String getListcompanyLogBook() {

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getcompanyList());
		return "clist";
	}

	/**
	 * 导出工作日志
	 * 
	 * @return
	 */
	public String showCExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		excelStream = excelService.showExcel(LogBookSummary.columnHeadings(),
				baseBeanService.getListByDC(getcompanyList()));
		CLogBook logBook = logBookService.saveCLogBook(null, "导出工作日志", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/** *********************************公司工资汇总******************************************* */
	/**
	 * 积分汇总（员工工资管理）
	 */
	public String getcompanyIntegral() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		/*Map<String, Object> session = ActionContext.getContext().getSession();
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		if (!"".equals(companyID) && companyID != null) {
			cids = new ArrayList<String>();
			cids.add(companyID);
		}*/
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		if(arg!=null&&arg.equals("1")){
			SalaryIntegral.setArg("1");
			LogBookServiceImp.setTitle(1);
			 sb_search= new StringBuffer(VOtool.getWagesHqlX());
		}else{
			SalaryIntegral.setArg("0");
			LogBookServiceImp.setTitle(0);
			 sb_search= new StringBuffer(VOtool.getWagesHql());
			
		}
		StringBuffer sb_count = new StringBuffer(
				" select count(*) from LogBookSummary j ");
		sb_count
				.append(" where j.logBookKey in (select  max(j.logBookKey) from LogBookSummary j ");

		sb_search.append(" where todaydate between ? and ? ");
		sb_count.append(" where todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		if (staffName != null && !"".equals(staffName.trim())) {
			sb_search.append(" and j.staffName like ? ");
			sb_count.append(" and j.staffName like ? ");
			parms.add("%" + staffName.trim() + "%");
		}
		if(staffcategoryid!=null&&!"".equals(staffcategoryid)){
			sb_search.append(" and j.staffcategoryid= ? ");
			sb_count.append(" and j.staffcategoryid= ? ");
			parms.add(staffcategoryid);
		}
		sb_search.append(" and j.bisect is not null ");
		sb_count.append(" and j.bisect is not null ");
		if(companyID.equals("all")){
			sb_search.append(" and exists ( select c.companyID from Company c where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))");
			sb_count.append(" and exists ( select c.companyID from Company c where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))");
			parms.add(account.getCompanyID());
			parms.add(account.getCompanyID());
			
		}else{
			sb_search.append(" and j.companyID = ? ");
			sb_count.append(" and j.companyID = ? ");
			
			parms.add(companyID);
		}
		/*if (cids.size() == 1) {
			sb_search.append(" and j.companyID = ? ");
			sb_count.append(" and j.companyID = ? ");
			parms.add(cids.get(0));
		} else if (cids.size() > 1) {
			for (int i = 0; i < cids.size(); i++) {
				if (i == 0) {
					sb_search.append(" and ( j.companyID = ? ");
					sb_count.append(" and ( j.companyID = ? ");
					parms.add(cids.get(i));
					continue;
				}
				if (i == cids.size() - 1) {
					sb_search.append(" or j.companyID = ? ) ");
					sb_count.append(" or j.companyID = ? ) ");
					parms.add(cids.get(i));
					break;
				}
				sb_search.append(" or j.companyID = ? ");
				sb_count.append(" or j.companyID = ? ");
				parms.add(cids.get(i));
			}
		}*/
		
		if(arg!=null&&arg.equals("1")){
			sb_search
			.append(" group by to_char(j.todaydate,'yyyy-MM'),j.companyID,j.staffID,j.staffName,j.categoryname  order by j.companyID,j.staffName,to_char(j.todaydate, 'yyyy-MM') ");
			sb_count.append(" group by to_char(j.todaydate, 'yyyy-MM'),j.companyID,j.staffID,j.categoryname ) ");
		}else{
			sb_search
			.append(" group by j.companyID,j.staffID,j.staffName,j.categoryname  order by j.companyID,j.staffName ");	
			sb_count.append(" group by j.companyID,j.staffID,j.categoryname ) ");
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				sb_search.toString(), sb_count.toString(), parms.toArray());
		getDynamicWage(pageForm==null?null:pageForm.getList(),companyID,"");
		return "cIntegral";
	}

	/**
	 * 公司员工工资导出
	 * 
	 * @return
	 */
	public String showcompanyExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs((String) session.get("companyID"));
		if (!"".equals(companyID) && companyID != null) {
			cids = new ArrayList<String>();
			cids.add(companyID);
		}
		StringBuffer sb_search = null;
		if(arg!=null&&arg.equals("1")){
			 sb_search= new StringBuffer(VOtool.getWagesHqlX());
		}else{
			 sb_search= new StringBuffer(VOtool.getWagesHql());
			
		}
		List<Object> parms = new ArrayList<Object>();
		sb_search.append(" where todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		SalaryIntegral.setTitleDate(sdate+" 至 "+edate);
		// String hql01 = " todaydate >=to_Date('"
		// + sdate
		// + "','yyyy-mm-dd') and todaydate<= to_Date('"
		// + edate
		// + "','yyyy-mm-dd') and j.bisect is not null and ( j.companyID = ";
		// String hql = "";
		if (staffName != null && !"".equals(staffName)) {
			sb_search.append(" and j.staffName like ? ");
			parms.add("%" + staffName + "%");
		}
		if(staffcategoryid!=null&&!"".equals(staffcategoryid)){
			sb_search.append(" and j.staffcategoryid= ? ");
			parms.add(staffcategoryid);
		}
		sb_search.append(" and j.bisect is not null ");
		// String hql2 = "') group by j.companyID,j.staffID,j.staffName order by
		// j.companyID,j.staffID";
		if (cids.size() == 1) {
			if(cids.get(0).equals("all")){
				sb_search.append(" and exists ( select c.companyID from Company c where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))");
				parms.add(account.getCompanyID());
				parms.add(account.getCompanyID());
			}else{
				sb_search.append("  and  j.companyID = ? ");
				parms.add(cids.get(0));
			}

		} else if (cids.size() > 1) {
			for (int i = 0; i < cids.size(); i++) {
				if (i == 0) {
					sb_search.append(" and ( j.companyID = ? ");
					parms.add(cids.get(i));
					continue;
				}
				if (i == cids.size() - 1) {
					sb_search.append(" or j.companyID = ? ) ");
					parms.add(cids.get(i));
					break;
				}
				sb_search.append(" or j.companyID = ? ");
				parms.add(cids.get(i));
			}
		}
		if(arg!=null&&arg.equals("1")){
			sb_search
			.append(" group by to_char(j.todaydate,'yyyy-MM'),j.companyID,j.staffID,j.staffName,j.categoryname  order by j.companyID,j.staffName,to_char(j.todaydate, 'yyyy-MM') ");
		}else{
			sb_search
			.append(" group by j.companyID,j.staffID,j.staffName,j.categoryname  order by j.companyID,j.staffID ");
		}
		List<BaseBean> BaseList = baseBeanService.getListBeanByHqlAndParams(
				sb_search.toString(), parms.toArray());
		getDynamicWage(BaseList,companyID,"");
		excelStream = showlogBookService.showExcel(SalaryIntegral
				.columnHeadings(), BaseList);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出公司员工工资",
				account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 归档加锁
	 * 
	 * @return lock = on 加锁 lock = off 为未加锁
	 */
	// public String toLock() {
	//
	// Map<String, Object> session = ActionContext.getContext().getSession();
	// CAccount account = (CAccount) session.get("account");
	// List<BaseBean> beans = new ArrayList<BaseBean>();
	// if (!("").equals(sdate) && sdate != null && edate != null
	// && !("").equals(edate)) {
	// CLogBook logBook = new CLogBook();
	// String lock = ServletActionContext.getRequest()
	// .getParameter("lock");
	// String hql = null;
	// if (lock.equalsIgnoreCase("on")) {
	// hql = "update LogBook set status = '01' where todaydate >= ? and
	// todaydate <= ? and companyID = ?";
	// logBook = logBookService.saveCLogBook(null, "加锁工作日志汇总(起时间:"
	// + sdate + "结束时间：" + edate + ")", account);
	//
	// }
	// if (lock.equalsIgnoreCase("off")) {
	// hql = "update LogBook set status = '00' where todaydate >= ? and
	// todaydate <= ? and companyID = ?";
	// logBook = logBookService.saveCLogBook(null, "解锁工作日志汇总(起时间:"
	// + sdate + "结束时间：" + edate + ")", account);
	// }
	// beans.add(logBook);
	// Object[] params = {
	// Utilities.getDateFromString(sdate, "yyyy-MM-dd"),
	// Utilities.getDateFromString(edate, "yyyy-MM-dd"),
	// account.getCompanyID() };
	// if (lock.equalsIgnoreCase("on") || lock.equalsIgnoreCase("off"))
	// baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
	// new String[] { hql }, params);
	// }
	// return getListLogBook();
	// }
	/**
	 * 打印某天的工作日志
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String toDaYin() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc1 = DetachedCriteria.forClass(CStaffCos.class);
		dc1.add(Restrictions.eq("companyID", companyID));
		staffName = logbooksummary.getStaffName();
		if (null != staffName && !("").equals(staffName)) {
			dc1.add(Restrictions.like("staffName", staffName,
					MatchMode.ANYWHERE));
		}
		List<BaseBean> CStaffCosList = baseBeanService.getListByDC(dc1);
		List<LokBookPrintVO> bookPrintVOAddList = new ArrayList<LokBookPrintVO>();
		if (CStaffCosList.size() > 0) {
			for (BaseBean baseBean : CStaffCosList) {
				CStaffCos cos = (CStaffCos) baseBean;
				String hql = "from LogBookSummary  where todaydate = ? and staffID = ? and companyID = ?";
				Object[] params = { logbooksummary.getTodaydate(),
						cos.getStaffID(), account.getCompanyID() };
				List<BaseBean> logbooklist = baseBeanService
						.getListBeanByHqlAndParams(hql, params);
				if (logbooklist.size() > 0) {
					LokBookPrintVO bookPrintVO = new LokBookPrintVO();
					bookPrintVO.setLokBookPrintVOID(serverService
							.getBillID(companyID + "logbook"));
					bookPrintVO.setCompanyID(cos.getCompanyID());
					bookPrintVO.setCompanyName(cos.getCompanyName());
					bookPrintVO.setStaffID(cos.getStaffID());
					bookPrintVO.setStaffName(cos.getStaffName());
					bookPrintVO.setTodaydate(Utilities.getDateString(
							logbooksummary.getTodaydate(), "yyyy-MM-dd"));
					bookPrintVO.setLogbookList(logbooklist);
					float bisects = 0;
					for (BaseBean bean : logbooklist) {
						LogBookSummary summary = (LogBookSummary) bean;
						if (summary.getBisect() != null) {
							if (summary.getScoreSort().equals(
									"scode20100812ikdv89y6kt0000000036")// 考勤折扣
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000035")// 暂扣安全积分
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000034")// 任务折扣
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000033")// 违规折扣
									|| summary
											.getScoreSort()
											.equals(
													"scode20100812ikdv89y6kt0000000032")// 个人所得税
								  /*|| summary
											.getScoreSort()
											.equals(
													"scode201202157awfwsxchm0000000005")// 特殊人才
									|| summary
											.getScoreSort()
											.equals(
													"scode201202157awfwsxchm0000000006")// 保密工资
									|| summary
											.getScoreSort()
											.equals(
													"scode20120223p2a22556py0000000002")// 补助话费
									|| summary
											.getScoreSort()
											.equals(
													"scode20120223p2a22556py0000000003")// 生活补助
*/							) {
								bisects -= Float
										.parseFloat(summary.getBisect());
							} else {
								bisects += Float
										.parseFloat(summary.getBisect());
							}

						}
					}
					bookPrintVO.setBisects(Float.toString(Math
							.round(bisects * 100) / 100f));
					bookPrintVO.setMomey(Float.toString(Math
							.round(bisects * 100 * 20) / 100f));
					bookPrintVOAddList.add(bookPrintVO);
				}
			}
			logBookPrintVoList = bookPrintVOAddList;
		}
		return "logBookPing";
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public LogBookSummary getLogbooksummary() {
		return logbooksummary;
	}

	public void setLogbooksummary(LogBookSummary logbooksummary) {
		this.logbooksummary = logbooksummary;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CompanyService getCompanyserverService() {
		return companyserverService;
	}

	public void setCompanyserverService(CompanyService companyserverService) {
		this.companyserverService = companyserverService;
	}

	// public List<BaseBean> getComlist() {
	// return comlist;
	// }
	// public void setComlist(List<BaseBean> comlist) {
	// this.comlist = comlist;
	// }
	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public COrganization getCorganization() {
		return corganization;
	}

	public void setCorganization(COrganization corganization) {
		this.corganization = corganization;
	}

	public LokBookPrintVO getLogBookPrintVo() {
		return logBookPrintVo;
	}

	public void setLogBookPrintVo(LokBookPrintVO logBookPrintVo) {
		this.logBookPrintVo = logBookPrintVo;
	}

	public List<BaseBean> getListLogBookSummary() {
		return listLogBookSummary;
	}

	public void setListLogBookSummary(List<BaseBean> listLogBookSummary) {
		this.listLogBookSummary = listLogBookSummary;
	}

	public List<LokBookPrintVO> getLogBookPrintVoList() {
		return logBookPrintVoList;
	}

	public void setLogBookPrintVoList(List<LokBookPrintVO> logBookPrintVoList) {
		this.logBookPrintVoList = logBookPrintVoList;
	}

	/**
	 * 所有员工工资集合
	 * 
	 * @return
	 */
	public List<BaseBean> getWages() {
		return wages;
	}

	/**
	 * 所有员工工资集合
	 * 
	 * @param wages
	 */
	public void setWages(List<BaseBean> wages) {
		this.wages = wages;
	}

	public String getLogoStatusVar() {
		return logoStatusVar;
	}

	public void setLogoStatusVar(String logoStatusVar) {
		this.logoStatusVar = logoStatusVar;
	}

	public List<Object> getAdd() {
		return add;
	}

	public void setAdd(List<Object> add) {
		this.add = add;
	}

	public List<Object> getCut() {
		return cut;
	}

	public void setCut(List<Object> cut) {
		this.cut = cut;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}
	public String getStaffcategoryid() {
		return staffcategoryid;
	}

	public void setStaffcategoryid(String staffcategoryid) {
		this.staffcategoryid = staffcategoryid;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}
	
}
