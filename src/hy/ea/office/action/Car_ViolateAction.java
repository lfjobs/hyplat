package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.carViolate;
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

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 车辆为违章信息表
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class Car_ViolateAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private InputStream excelStream;
	private int pageNumber;
	private String search;
	private List<BaseBean> beans;
	private carViolate carviolate;
	private CarInformation carInformation;
	private String parameter;
	private String result;
	private String typeType;
	private String type;
	/**
	 * 查询车辆违章信息管理
	 */
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(carViolate.class);
	
		if(carInformation!=null&&!carInformation.getCarID().equals("undefined")&&!carInformation.getCarID().equals("")){
			dc.add(Restrictions.eq("carID", carInformation.getCarID()));
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			
		}else{
			if(type!=null&&!type.equals("")){
				if(type.equals("c")){
				 dc.add(Restrictions.eq("companyID", companyID));
					
				}else if(type.equals("g")){
					String hql = "select companyID from Company where companyPID = ? or companyID = ?";

					List<BaseBean> list = baseBeanService
							.getListBeanByHqlAndParams(hql,
									new Object[] { companyID,
									companyID });

					dc.add(Restrictions.in("companyID", list));
					
				}else{
					dc.add(Restrictions.eq("companyID", companyID));
					dc.add(Restrictions.eq("organizationID", organizationID));
				}
			}
		}
		if (search != null && search.equals("search")) {
			carviolate =  (carViolate) session.get("tablesearch");
			if(carviolate.getCarNum()!=null&&!carviolate.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carviolate.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carviolate.getCarType()!=null&&!carviolate.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carviolate.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carviolate.getEngineNum()!=null&&!carviolate.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carviolate.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carviolate.getViolatePeople()!=null&&!carviolate.getViolatePeople().equals("")){
				dc.add(Restrictions.like("violatePeople",carviolate.getViolatePeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getCarViolateList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		//CAccount account = (CAccount) session.get("account");
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarViolateList";
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carviolate);
		return getCarViolateList();
	}
	/**
	 * 车辆违章信息导出功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showViolateExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String hql=" select carnum,cartype,enginenum,violatepeople, violatedate, violatereason, case treatment when '00' then '未处理' when '01' then '已处理'end, remark from dt_car_carviolate " +
				"where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(carViolate.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆违章信息管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*
	 * 车辆违章信息删除功能
	 */
	public String deleteViolate(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,carviolate.getCarviolateID()};
		String hql="from carViolate where companyID=? and organizationID=? and carviolateID=?";
		carViolate road=(carViolate) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆违章信息管理:("+road.getCarviolateID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql1="delete carViolate where companyID=? and organizationID=? and carviolateID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[] { hql1 }, param);
		return "success";
	}
	/**
	 * 车辆违章信息添加中选则车辆查询
	 * @return
	 */
	private DetachedCriteria getcarList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		dc.add(Restrictions.in("state3",new Object[]{"00","01"}));
		if (null != parameter && !"".equals(parameter)) {
			dc.add(Restrictions.like("carNum", parameter,
					MatchMode.ANYWHERE));
		}
		if (null != typeType && !"".equals(typeType)) {
			dc.add(Restrictions.like("carType", typeType,
					MatchMode.ANYWHERE));
		}
		return dc;
	}
	public String getcmotornull(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String,Object> session =ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getcarList());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/*
	 * 车辆违章信息添加保存信息
	 */
	public String saveCarviolateList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		//String obj = (String) session.get("session_value");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carviolate.setCarviolateID(serverService.getServerID("carViolate"));
		carviolate.setCarID(cars.getCarID());
		carviolate.setCarNum(cars.getCarNum());
		carviolate.setCarType(cars.getCarType());
		carviolate.setEngineNum(cars.getEngineNum());
		carviolate.setViolatePeople(cars.getStaffName());
		carviolate.setOrganizationID(organizationID);
		carviolate.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carviolate);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	/**
	 * 车辆违章信息的修改保存
	 * @return
	 */
	public String updateCarviolate(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		//String obj = (String) session.get("session_value");
		String hql="from carViolate where companyID=? and carviolateID=?";
		carViolate wei= (carViolate) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),carviolate.getCarviolateID()});
		wei.setViolateDate(carviolate.getViolateDate());
		wei.setViolateReason(carviolate.getViolateReason());
		wei.setTreatment(carviolate.getTreatment());
		wei.setRemark(carviolate.getRemark());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(wei);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	/*
	 * 车辆违章信息查询按钮功能
	 */
	public String toSearchCar() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carviolate);
		return getListCarByCompanyID();
	 }
	 public String getListCarByCompanyID(){
		 Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount account = (CAccount) session.get("account");
		 String organizationID = (String) session.get("organizationID");
		 String companyID = account.getCompanyID();
		 if(search !=null && search.equals("search")){
			 carviolate= (carViolate) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from carViolate cars where 1=1 ";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=? ";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=? ";
			 parms.add(organizationID);
			 if(carviolate.getCarNum() !=null && !"".equals(carviolate.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ? ";
				 parms.add("%" + carviolate.getCarNum().trim()+"%");
			 }
			 if(carviolate.getCarType()!=null && !"".equals(carviolate.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ? ";
				parms.add("%" + carviolate.getCarType().trim()+"%");
			 }
			 if(carviolate.getEngineNum()!=null && !"".equals(carviolate.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ? ";
				 parms.add("%" + carviolate.getEngineNum().trim()+"%");
			 }
			 if(carviolate.getViolatePeople()!=null && !"".equals(carviolate.getViolatePeople().trim())){
				 hqlcarsearch+="and cars.violatePeople like ? ";
				 parms.add("%" + carviolate.getViolatePeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarViolateList";
		 }
		 session.put("tablesearch", carviolate);
		 return "getCarViolateList";
	 }
	public String getaaa() {
		return "ok";
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
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
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
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public carViolate getCarviolate() {
		return carviolate;
	}
	public void setCarviolate(carViolate carviolate) {
		this.carviolate = carviolate;
	}
	public CarInformation getCarInformation() {
		return carInformation;
	}
	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getTypeType() {
		return typeType;
	}
	public void setTypeType(String typeType) {
		this.typeType = typeType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}