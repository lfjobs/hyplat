package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.driving.DtDrivingAppointmentRecord;
import hy.ea.bo.driving.DtDrivingTrainingInfor;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DrivingTrainingAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private DtDrivingAppointmentRecord dtDrivingAppointmentRecord;
	
	private List<BaseBean> dtDrivingAppointmentRecordList;
	
	private List<CCode> addressTypelist;
	private String parameter;
	private Map<String, DtDrivingAppointmentRecord> dtDrivingAppointmentRecordmap;
	private Map<String, DtDrivingTrainingInfor> dtDrivingTrainingInformap;
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
	 */
	public String saveDtDrivingTrainingInfor(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans= new ArrayList<BaseBean>();
		if(dtDrivingTrainingInformap!=null){
		for(DtDrivingTrainingInfor dtDrivingTrainingInfor:dtDrivingTrainingInformap.values()){
			if (null == dtDrivingTrainingInfor.getDrivingtraininginforid() || "".equals(dtDrivingTrainingInfor.getDrivingtraininginforid())) {
				dtDrivingTrainingInfor.setDrivingtraininginforid(serverService.getServerID("DtDrivingTrainingInfor"));
				parameter = "添加培训信息";
			}
			else
			{
				parameter = "修改培训信息";
			}
			beans.add(dtDrivingTrainingInfor);
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "success";
	}
	
	/**
	 * 返回培训记录
	 * 
	 */
	public String getListDtDrivingTrainingInfor(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = { dtDrivingAppointmentRecord.getDrivingprincipalid(),dtDrivingAppointmentRecord.getDocstatus(),account.getCompanyID() };
		
		String hql=" from DtDrivingTrainingInfor where dtDrivingAppointmentRecord.drivingprincipalid = ? and docstatus=? and companyid=?   order by appointmentDate desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "training_list";
	}
	/**
	 * 导出预约记录
	 * @return
	 */
	public String showDtDrivingTrainingInfor() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = { dtDrivingAppointmentRecord.getDrivingprincipalid(),dtDrivingAppointmentRecord.getDocstatus() };
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
						" from DtDrivingTrainingInfor where dtDrivingAppointmentRecord.drivingprincipalid = ? and docstatus=? order by appointmentDate desc",
						params);
		excelStream = excelService.showExcel(DtDrivingTrainingInfor.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出培训记录", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", dtDrivingAppointmentRecord);
		return getListDtDrivingTrainingInforAll();
	}
	private DetachedCriteria getListAll() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(DtDrivingTrainingInfor.class);
		dc.createAlias("dtDrivingAppointmentRecord", "dtDrivingAppointmentRecord",CriteriaSpecification.LEFT_JOIN);
		dc.add(Restrictions.eq("dtDrivingAppointmentRecord.companyid",account.getCompanyID()));
		if (search != null && search.equals("search")) {
			dtDrivingAppointmentRecord = (DtDrivingAppointmentRecord) session.get("tablesearch");
			if(dtDrivingAppointmentRecord.getCoach()!=null&&!"".equals(dtDrivingAppointmentRecord.getCoach().trim()))
			{
				dc.add(Restrictions.like("dtDrivingAppointmentRecord.coach", dtDrivingAppointmentRecord.getCoach().trim(),MatchMode.ANYWHERE));
			}
			if(dtDrivingAppointmentRecord.getIsappointmentstatus()!=null&&!"".equals(dtDrivingAppointmentRecord.getIsappointmentstatus().trim()))
			{
				dc.add(Restrictions.eq("istruestatus", dtDrivingAppointmentRecord.getIsappointmentstatus().trim()));
			}
			if(dtDrivingAppointmentRecord.getAppointmentdate()!=null&&!"".equals(dtDrivingAppointmentRecord.getAppointmentdate()))
			{
				dc.add(Restrictions.eq("dtDrivingAppointmentRecord.appointmentdate", dtDrivingAppointmentRecord.getAppointmentdate()));
			}
		}
		dc.add(Restrictions.eq("dtDrivingAppointmentRecord.docstatus", module_title==null?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04"));
		dc.addOrder(Order.desc("dtDrivingAppointmentRecord.appointmentdate"));
		return dc;
	}
	/**
	 * 预约培训记录按“科目”汇总
	 * 参数：
	 * 返回：training_list_all
	 */
	public String getListDtDrivingTrainingInforAll() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getListAll());
		return "training_list_all";
	}
	
	/**
	 * 导出培训记录
	 * @return
	 */
	public String showDtDrivingTrainingInforAll() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		List<BaseBean> list = baseBeanService.getListByDC(getListAll());
		excelStream = excelService.showExcel(DtDrivingTrainingInfor.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出培训记录", account);
		baseBeanService.update(logBook);
		return "showexcel";
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

	public Map<String, DtDrivingTrainingInfor> getDtDrivingTrainingInformap() {
		return dtDrivingTrainingInformap;
	}

	public void setDtDrivingTrainingInformap(
			Map<String, DtDrivingTrainingInfor> dtDrivingTrainingInformap) {
		this.dtDrivingTrainingInformap = dtDrivingTrainingInformap;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getModule_title() {
		return module_title;
	}

	public void setModule_title(String module_title) {
		this.module_title = module_title;
	}
	
	
	
}
