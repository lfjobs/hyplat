package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.carRoad;
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
 * 道路运输证
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class Car_RoadAction {
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
	private carRoad carroad;
	private CarInformation carInformation;
	private String parameter;
	private String result;
	private String typeType;
	private String type;

	/**
	 * 查询车辆道路运输证管理
	 */
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(carRoad.class);
		if(carInformation!=null&&!carInformation.getCarID().equals("undefined")&&!carInformation.getCarID().equals("")){
			dc.add(Restrictions.eq("carID", carInformation.getCarID()));
			
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
			carroad =  (carRoad) session.get("tablesearch");
			if(carroad.getCarNum()!=null&&!carroad.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carroad.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carroad.getCarType()!=null&&!carroad.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carroad.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carroad.getEngineNum()!=null&&!carroad.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carroad.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carroad.getCarpeople()!=null&&!carroad.getCarpeople().equals("")){
				dc.add(Restrictions.like("carpeople",carroad.getCarpeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getCarRoadList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarRoadList";
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carroad);
		return getCarRoadList();
	}
	/**
	 * 车辆道路运输证导出功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showCarroadExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String hql="select carnum, cartype,enginenum,carpeople,basename," +
				" roadaddress, permitnum, tonnage,  carlwh, businessrange, " +
				"carddate from dt_road_transport " +
				"where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(carRoad.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆道路运输证管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 车辆道路运输证删除功能
	 * @return
	 */
	public String deleteCarroad(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,carroad.getRoadID()};
		String hql="from carRoad where companyID=? and organizationID=? and roadID=?";
		carRoad road=(carRoad) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆道路运输证管理:("+road.getRoadID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql1="delete carRoad where companyID=? and organizationID=? and roadID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[] { hql1 }, param);
		return "success";
	}
	/**
	 * 车辆道路运输证添加中选择车辆查询
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
	/**
	 * 车辆道路运输证添加保存信息
	 * @return
	 */
	public String saveCarroadList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		//String obj = (String) session.get("session_value");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carroad.setRoadID(serverService.getServerID("carRoad"));
		carroad.setCarID(cars.getCarID());
		carroad.setCarNum(cars.getCarNum());
		carroad.setCarType(cars.getCarType());
		carroad.setEngineNum(cars.getEngineNum());
		carroad.setCarpeople(cars.getStaffName());
		carroad.setOrganizationID(organizationID);
		carroad.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carroad);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	/**
	 * 车辆道路行驶证的修改保存
	 * @return
	 */
	public String updateCarroad(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		//String obj = (String) session.get("session_value");
		String hql="from carRoad where companyID=? and roadID=?";
		carRoad roads= (carRoad) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),carroad.getRoadID()});
		roads.setBaseName(carroad.getBaseName());
		roads.setRoadAddress(carroad.getRoadAddress());
		roads.setPermitNum(carroad.getPermitNum());
		roads.setTonnage(carroad.getTonnage());
		roads.setCarLwh(carroad.getCarLwh());
		roads.setBusinessRange(carroad.getBusinessRange());
		roads.setCardDate(carroad.getCardDate());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(roads);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	/**
	 * 车辆道路运输证查询按钮功能
	 * @return
	 */
	 public String toSearchCar() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carroad);
		return getListCarByCompanyID();
	 }
	 public String getListCarByCompanyID(){
		 Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount account = (CAccount) session.get("account");
		 String organizationID = (String) session.get("organizationID");
		 String companyID = account.getCompanyID();
		 if(search !=null && search.equals("search")){
			 carroad= (carRoad) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from carRoad cars where 1=1 ";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=? ";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=? ";
			 parms.add(organizationID);
			 if(carroad.getCarNum() !=null && !"".equals(carroad.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ? ";
				 parms.add("%" + carroad.getCarNum().trim()+"%");
			 }
			 if(carroad.getCarType()!=null && !"".equals(carroad.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ? ";
				parms.add("%" + carroad.getCarType().trim()+"%");
			 }
			 if(carroad.getEngineNum()!=null && !"".equals(carroad.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ? ";
				 parms.add("%" + carroad.getEngineNum().trim()+"%");
			 }
			 if(carroad.getCarpeople()!=null && !"".equals(carroad.getCarpeople().trim())){
				 hqlcarsearch+="and cars.carpeople like ? ";
				 parms.add("%" + carroad.getCarpeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarRoadList";
		 }
		 session.put("tablesearch", carroad);
		 return "getCarRoadList";
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
	public carRoad getCarroad() {
		return carroad;
	}
	public void setCarroad(carRoad carroad) {
		this.carroad = carroad;
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