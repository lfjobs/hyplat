package mobile.tiantai.android.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.driving.DtDrivingAppointmentRecord;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.tiantai.android.bo.DtMicroletterMenu;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class MicroLetterAction {
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
	private DtMicroletterMenu dtMicroletterMenu;
	private int pageNumber;
	private List<BaseBean> beans;
	private PageForm pageForm;
	
	private String result;
	
	private String search;
	
	/**
	 * 添加或修改微信菜单信息
	 * 参数：dtMicroletterMenu
	 */
	public String saveDtMicroletterMenu(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans= new ArrayList<BaseBean>();
		if(dtMicroletterMenu!=null){
			if (null == dtMicroletterMenu.getMicrolettermenuid()|| "".equals(dtMicroletterMenu.getMicrolettermenuid())) {
				dtMicroletterMenu.setMicrolettermenuid(serverService.getServerID("DtMicroletterMenu"));
				parameter = "添加微信菜单信息";
				dtMicroletterMenu.setMicrolettermenucdate(new Date());
				dtMicroletterMenu.setMicrolettermenuudate(new Date());
			}
			else
			{
				parameter = "修改微信菜单信息";
				dtMicroletterMenu.setMicrolettermenuudate(new Date());
			}
			dtMicroletterMenu.setCompanyid(account.getCompanyID());
			beans.add(dtMicroletterMenu);
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans.add(logBook);
		}	
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		
		return "success";
	}
	public String delDtMicroletterMenu(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans= new ArrayList<BaseBean>();
		
		String hql=" from DtMicroletterMenu where microlettermenuid=?";
		dtMicroletterMenu=(DtMicroletterMenu) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{dtMicroletterMenu.getMicrolettermenuid()});
		dtMicroletterMenu.setMicrolettermenustatus(new BigDecimal("11"));
		
		beans.add(dtMicroletterMenu);
		CLogBook logBook = logBookService.saveCLogBook(null, "删除微信菜单信息", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", dtMicroletterMenu);
		return getListDtMicroletterMenuAll();
	}
	private DetachedCriteria getListAll() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(DtMicroletterMenu.class);
		dc.add(Restrictions.eq("companyid",account.getCompanyID()));
		if(dtMicroletterMenu!=null&&dtMicroletterMenu.getMicrolettermenupid()!=null&&!"".equals(dtMicroletterMenu.getMicrolettermenupid().trim())){
			dc.add(Restrictions.eq("microlettermenupid",dtMicroletterMenu.getMicrolettermenupid()));
		}
		return dc;
	}
	/**
	 * 
	 * 参数：
	 * 返回：training_list_all
	 */
	public String getListDtMicroletterMenuAll() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getListAll());
		return "dtMicroletterMenu_list";
	}
	/**
	 * ajax查询微信菜单按钮
	 * @return
	 */
	public String getAjaxListDtMicroletterMenuAll(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(DtMicroletterMenu.class);
		dc.add(Restrictions.eq("companyid",account.getCompanyID()));
		dc.add(Restrictions.eq("microlettermenustatus",new BigDecimal("10")));
		if(dtMicroletterMenu!=null&&dtMicroletterMenu.getMicrolettermenupid()!=null&&!"".equals(dtMicroletterMenu.getMicrolettermenupid().trim())){
			dc.add(Restrictions.eq("microlettermenupid",dtMicroletterMenu.getMicrolettermenupid()));
		}
		List<BaseBean> listMicroMenu=baseBeanService.getListByDC(dc);
		Map<String, Object> map=new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer"});  
		map.put("listMicroMenu", listMicroMenu);
		JSONObject json=JSONObject.fromObject(map,jsonConfig);
		result=json.toString();
		return  "success";
	}
	
	/**
	 * ajax查询微信菜单按钮 for android
	 * http://localhost:8080/hyplat/ea/microletter/sajax_ea_getAjaxListDtMicroletterMenuAllforAndroid.jspa?dtMicroletterMenu.microlettermenupid=001
	 * listMicroMenu
	 * @return
	 */
	public String getAjaxListDtMicroletterMenuAllforAndroid(){
		DetachedCriteria dc = DetachedCriteria.forClass(DtMicroletterMenu.class);
		dc.add(Restrictions.eq("microlettermenustatus",new BigDecimal("10")));
		if(dtMicroletterMenu!=null&&dtMicroletterMenu.getMicrolettermenupid()!=null&&!"".equals(dtMicroletterMenu.getMicrolettermenupid().trim())){
			dc.add(Restrictions.eq("microlettermenupid",dtMicroletterMenu.getMicrolettermenupid()));
		}
		if(dtMicroletterMenu!=null&&dtMicroletterMenu.getCompanyid()!=null&&!"".equals(dtMicroletterMenu.getCompanyid().trim())){
			dc.add(Restrictions.eq("companyid",dtMicroletterMenu.getCompanyid()));
		}
		List<BaseBean> listMicroMenu=baseBeanService.getListByDC(dc);
		Map<String, Object> map=new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer","dtMicroletterMenuContents"});
		if(listMicroMenu==null){
			map.put("listMicroMenu", "无数据");
		}else{
			map.put("listMicroMenu", listMicroMenu);
		}
		JSONObject json=JSONObject.fromObject(map,jsonConfig);
		result=json.toString();
		return  "success";
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public DtMicroletterMenu getDtMicroletterMenu() {
		return dtMicroletterMenu;
	}
	public void setDtMicroletterMenu(DtMicroletterMenu dtMicroletterMenu) {
		this.dtMicroletterMenu = dtMicroletterMenu;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
