package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.ddsr.Ddsrsyllabus;
import hy.ea.bo.driving.DtDrivingAppointmentRecord;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.DtDrivingTrainingInfor;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DrivingAppointmentAction implements SessionAware{
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private Map<String,Object> session;
	private DtDrivingAppointmentRecord dtDrivingAppointmentRecord;
	
	private List<BaseBean> dtDrivingAppointmentRecordList;
	
	private List<CCode> addressTypelist;
	/**
	 * 报考车型 list 
	 */
	private List<CCode> carDrivingTypelist;
	/**
	 * 报考车型 map
	 */
	private Map<String, String> carDrivingTypeMap=new HashMap<String, String>();
	private String parameter;
	private Map<String, DtDrivingAppointmentRecord> dtDrivingAppointmentRecordmap;
	private int pageNumber;
	private List<BaseBean> beans;
	private PageForm pageForm;
	/**
	 *  页面导航标识
	 */
	private String module_title;
	private String search;
	/**
	 * 添加或修改培训记录信息
	 * 参数：dtDrivingAppointmentRecordmap
	 * 返回：getListAddress()
	 */
	public String saveDtDrivingAppointmentRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans= new ArrayList<BaseBean>();
		if(dtDrivingAppointmentRecordmap!=null){
		for(DtDrivingAppointmentRecord dtDrivingAppointmentRecord:dtDrivingAppointmentRecordmap.values()){
			if (null == dtDrivingAppointmentRecord.getDrivingappointmentrecordid() || "".equals(dtDrivingAppointmentRecord.getDrivingappointmentrecordid())) {
				dtDrivingAppointmentRecord.setDrivingappointmentrecordid(serverService.getServerID("Drivingappointme"));
				parameter = "添加预约信息";
			}
			else
			{
				parameter = "修改预约信息";
			}
			dtDrivingAppointmentRecord.setCompanyid(account.getCompanyID());
			/**
			 * 设置学员信息
			 */
			this.getStaffInformation(dtDrivingAppointmentRecord);
			/**
			 * 根据起止时间计算预约时长
			 */
			this.getTimelength(dtDrivingAppointmentRecord);
			/**
			 * 获得总学时
			 */
			this.getSumTimeLength(dtDrivingAppointmentRecord);
			/**
			 * 设置已预约学时与未预约学时
			 */
			this.getHaveSchoolTimeAndNoSchoolTime(dtDrivingAppointmentRecord);
			/**
			 * 设置学习项目
			 */
			this.setDdsrsyllabus(dtDrivingAppointmentRecord);
			/**
			 * 根据预约状态判断是否生成培训记录
			 */
			DtDrivingTrainingInfor  dtf=getTrainingInfor(dtDrivingAppointmentRecord);
			beans.add(dtf);
			beans.add(dtDrivingAppointmentRecord);
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "success";
	}

	// 删除某条地址信息
	public String delDtDrivingAppointmentRecord() {
		String hql1 = "delete DtDrivingAppointmentRecord where  drivingappointmentrecordid = ?";
		Object[] params = { dtDrivingAppointmentRecord.getDrivingappointmentrecordid()};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql1} , params);
		return "success";
	}
	/**
	 * 设置学员信息
	 */
	private DtDrivingAppointmentRecord getStaffInformation(DtDrivingAppointmentRecord dar){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql=" from DtDrivingPrincipal where drivingprincipalid=? and companyid=?";
		DtDrivingPrincipal bean=(DtDrivingPrincipal)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{dar.getDrivingprincipalid(),account.getCompanyID()});
		dar.setStaffid(bean.getStudentid());
		dar.setStaffname(bean.getStudentname());
		dar.setStaffphone(bean.getStudentphone());
		dar.setStaffsex(bean.getStudentsex());
		dar.setStaffcard(bean.getStudentcard());
		return dar;
	}
	/**
	 * 
	 * 设置培训项目信息
	 */
	private DtDrivingAppointmentRecord setDdsrsyllabus(DtDrivingAppointmentRecord dar){
		String hqlDdsrsyllabus=" from Ddsrsyllabus where id=? ";
		Ddsrsyllabus ddsrsyllabus=(Ddsrsyllabus)baseBeanService.getBeanByHqlAndParams(hqlDdsrsyllabus, new Object[]{dar.getDdsrsyllabusid()});
		dar.setDdsrsyllabusprogram(ddsrsyllabus.getProgram());
		return dar;
	}
	
	/**
	 * 设置 DtDrivingAppointmentRecord 类实例  timelength 字段 时间差
	 * @param dar 对象引用
	 * @return dar
	 */
	private DtDrivingAppointmentRecord getTimelength(DtDrivingAppointmentRecord dar){
		if(dar.getStartdate()!=null&&!"".equals(dar.getStartdate())
				&&dar.getEnddate()!=null&&!"".equals(dar.getEnddate())){
			Date startDate=DateUtil.parseDate("HH:mm", dar.getStartdate());
			Date endDate=DateUtil.parseDate("HH:mm", dar.getEnddate());
			Calendar c1 = Calendar.getInstance();
			c1.setTime(startDate);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(endDate);
			long mistiming= c2.getTimeInMillis()-c1.getTimeInMillis();
			dar.setTimelength(BigDecimal.valueOf(mistiming/1000/60/60));
		}
		return dar;
	}
	/**
	 * 获得报考车型，系统预设不可随意更改
	 */
	private DtDrivingAppointmentRecord getSumTimeLength(DtDrivingAppointmentRecord dar){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql=" from DtDrivingPrincipal where drivingprincipalid=? and companyid=?";
		DtDrivingPrincipal bean=(DtDrivingPrincipal)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{dar.getDrivingprincipalid(),account.getCompanyID()});
		carDrivingTypelist=codeService.getCCodeListByPID(account.getCompanyID(), "scode201211095p8932ixtz0000000014");
		for (CCode ccode : carDrivingTypelist) {
			if(ccode.getCodeValue()!=null){
				String[] ccodeArray=ccode.getCodeValue().split("/");
				if(ccodeArray!=null){
				carDrivingTypeMap.put(ccodeArray[0], ccodeArray[1]);
				}
			}
			
		}
		if(bean.getRegistrationcarname()!=null){
			if(carDrivingTypeMap.containsKey(bean.getRegistrationcarname().toUpperCase())){
				dar.setSumtimelength(bean.getRegistrationcarname().toUpperCase()+"/"+carDrivingTypeMap.get(bean.getRegistrationcarname().toUpperCase()));
			}else{
				dar.setSumtimelength(bean.getRegistrationcarname().toUpperCase()+"/48");
			}
		}
		return dar;
	}
	/**
	 * 设置已预约学时与未预约学时
	 */
	private DtDrivingAppointmentRecord getHaveSchoolTimeAndNoSchoolTime(DtDrivingAppointmentRecord dar){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql=" from DtDrivingAppointmentRecord  where drivingprincipalid=? and docstatus=? and companyid=?";
		List<BaseBean> beans=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{dar.getDrivingprincipalid(),dar.getDocstatus(),account.getCompanyID()});
		BigDecimal sumTimeLength=new BigDecimal(dar.getSumtimelength().split("/")[1]);
		BigDecimal haveSchoolTime=new BigDecimal(0);
		for (BaseBean bean : beans) {
			DtDrivingAppointmentRecord dar1=(DtDrivingAppointmentRecord)bean;
			if(dar1.getTimelength()!=null){
				if(dar1.getDrivingappointmentrecordid().equals(dar.getDrivingappointmentrecordid())){
					haveSchoolTime=haveSchoolTime.add(dar.getTimelength());
				}else{
					haveSchoolTime=haveSchoolTime.add(dar1.getTimelength());
				}
				
			}
		}
		if(dar.getDrivingappointmentrecordkey()==null||"".equals(dar.getDrivingappointmentrecordkey())){
			haveSchoolTime=haveSchoolTime.add(dar.getTimelength()==null?new BigDecimal(0):dar.getTimelength());
		}
		dar.setHaveschooltime(haveSchoolTime);
		dar.setNoschooltime(sumTimeLength.subtract(haveSchoolTime));
		return dar;
	}
	/**
	 * 
	 * 根据预约成功生成培训信息
	 */
	private DtDrivingTrainingInfor getTrainingInfor(DtDrivingAppointmentRecord dar){
		DtDrivingTrainingInfor  bean = null;
		if("01".equals(dar.getIsappointmentstatus())){
			 bean=(DtDrivingTrainingInfor)baseBeanService.getBeanByHqlAndParams(" from DtDrivingTrainingInfor dti where dti.dtDrivingAppointmentRecord=? ", new Object[]{dar});
			if(bean==null){
				 bean=new DtDrivingTrainingInfor();
				 bean.setDrivingtraininginforid(serverService.getServerID("DtDrivingTrainingInfor"));
				 bean.setDtDrivingAppointmentRecord(dar);
				 bean.setIstruestatus("00");
			}
		}
		return bean;
	}
	/**
	 * 预约培训记录
	 * 参数：
	 * 返回：appointment_list
	 */
	public String getListDtDrivingAppointmentRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = { dtDrivingAppointmentRecord.getDrivingprincipalid(), dtDrivingAppointmentRecord.getDocstatus(),account.getCompanyID() };
		
		
		String hqlDtDrivingPrincipal=" from DtDrivingPrincipal where companyID=? and drivingprincipalid=? ";
		DtDrivingPrincipal dtDrivingPrincipal=(DtDrivingPrincipal) baseBeanService.getBeanByHqlAndParams(hqlDtDrivingPrincipal, new Object[]{account.getCompanyID(),dtDrivingAppointmentRecord.getDrivingprincipalid()});
		
		
		String hqlDdsrsyllabus=" from Ddsrsyllabus where companyID=? and subject=? and type=? ";
		String subject="01".equals(dtDrivingAppointmentRecord.getDocstatus())?"科目一":"02".equals(dtDrivingAppointmentRecord.getDocstatus())?"科目二":
			"03".equals(dtDrivingAppointmentRecord.getDocstatus())?"科目三":"科目四";
		List<BaseBean>  ddsrsyllabusList=baseBeanService.getListBeanByHqlAndParams(hqlDdsrsyllabus, new Object[]{account.getCompanyID(),subject,dtDrivingPrincipal.getRegistrationcarname().toUpperCase()});
		ActionContext.getContext().put("ddsrsyllabusList", ddsrsyllabusList);
		
		String hql=" from DtDrivingAppointmentRecord where drivingprincipalid = ? and docstatus=? and companyid=? order by appointmentDate desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "appointment_list";
	}
	
	/**
	 * 导出预约记录
	 * @return
	 */
	public String showDtDrivingAppointmentRecord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = { dtDrivingAppointmentRecord.getDrivingprincipalid(), dtDrivingAppointmentRecord.getDocstatus() };
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
						" from DtDrivingAppointmentRecord where drivingprincipalid = ? and docstatus=? order by appointmentDate desc",
						params);	
		excelStream = excelService.showExcel(DtDrivingAppointmentRecord.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出预约记录", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 查询展示本公司下所有当天预约学员信息
	 * 
	 */
	public String getListDtDrivingAppointmentRecordCompany() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?20:pageNumber), getList());
		return "appointment_list_preview";
	}
	/**
	 * 查询展示本公司下所有当天预约学员信息 DetachedCriteria
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(DtDrivingAppointmentRecord.class);
		dc.add(Restrictions.eq("companyid",account.getCompanyID()));
		DateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dc.add(Restrictions.eq("appointmentdate", dateFormat.parse(dateFormat.format(new Date()))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dc;
	}
	/**
	 * 查询
	 * @return
	 */
	public String toSearch(){
		session.put("tablesearch", dtDrivingAppointmentRecord);
		return getListDtDrivingAppointmentRecordAll();
	}
	private DetachedCriteria getListAll() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(DtDrivingAppointmentRecord.class);
		dc.add(Restrictions.eq("companyid",account.getCompanyID()));
		if (search != null && search.equals("search")) {
			dtDrivingAppointmentRecord = (DtDrivingAppointmentRecord) session.get("tablesearch");
			if(dtDrivingAppointmentRecord.getCoach()!=null&&!"".equals(dtDrivingAppointmentRecord.getCoach().trim()))
			{
				dc.add(Restrictions.like("coach", dtDrivingAppointmentRecord.getCoach().trim(),MatchMode.ANYWHERE));
			}
			if(dtDrivingAppointmentRecord.getIsappointmentstatus()!=null&&!"".equals(dtDrivingAppointmentRecord.getIsappointmentstatus().trim()))
			{
				dc.add(Restrictions.eq("isappointmentstatus", dtDrivingAppointmentRecord.getIsappointmentstatus().trim()));
			}
			if(dtDrivingAppointmentRecord.getAppointmentdate()!=null&&!"".equals(dtDrivingAppointmentRecord.getAppointmentdate()))
			{
				dc.add(Restrictions.eq("appointmentdate", dtDrivingAppointmentRecord.getAppointmentdate()));
			}
		}
		dc.add(Restrictions.eq("docstatus", module_title==null?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04"));
		dc.addOrder(Order.desc("appointmentdate"));
		return dc;
	}
	/**
	 * 预约培训记录按“科目”汇总
	 * 参数：
	 * 返回：appointment_list
	 */
	public String getListDtDrivingAppointmentRecordAll() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getListAll());
		return "appointment_list_all";
	}
	
	/**
	 * 导出预约记录
	 * @return
	 */
	public String showDtDrivingAppointmentRecordAll() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		List<BaseBean> list = baseBeanService.getListByDC(getListAll());
		excelStream = excelService.showExcel(DtDrivingAppointmentRecord.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出预约记录", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 
	 * @return
	 */
	
	
	
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


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<CCode> getAddressTypelist() {
		return addressTypelist;
	}

	public void setAddressTypelist(List<CCode> addressTypelist) {
		this.addressTypelist = addressTypelist;
	}
	
	public DtDrivingAppointmentRecord getDtDrivingAppointmentRecord() {
		return dtDrivingAppointmentRecord;
	}

	public void setDtDrivingAppointmentRecord(
			DtDrivingAppointmentRecord dtDrivingAppointmentRecord) {
		this.dtDrivingAppointmentRecord = dtDrivingAppointmentRecord;
	}
	
	public Map<String, DtDrivingAppointmentRecord> getDtDrivingAppointmentRecordmap() {
		return dtDrivingAppointmentRecordmap;
	}

	public void setDtDrivingAppointmentRecordmap(
			Map<String, DtDrivingAppointmentRecord> dtDrivingAppointmentRecordmap) {
		this.dtDrivingAppointmentRecordmap = dtDrivingAppointmentRecordmap;
	}

	public List<BaseBean> getDtDrivingAppointmentRecordList() {
		return dtDrivingAppointmentRecordList;
	}

	public void setDtDrivingAppointmentRecordList(
			List<BaseBean> dtDrivingAppointmentRecordList) {
		this.dtDrivingAppointmentRecordList = dtDrivingAppointmentRecordList;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public List<CCode> getCarDrivingTypelist() {
		return carDrivingTypelist;
	}

	public void setCarDrivingTypelist(List<CCode> carDrivingTypelist) {
		this.carDrivingTypelist = carDrivingTypelist;
	}

	public Map<String, String> getCarDrivingTypeMap() {
		return carDrivingTypeMap;
	}

	public void setCarDrivingTypeMap(Map<String, String> carDrivingTypeMap) {
		this.carDrivingTypeMap = carDrivingTypeMap;
	}

	public String getModule_title() {
		return module_title;
	}

	public void setModule_title(String module_title) {
		this.module_title = module_title;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
	
	
}
