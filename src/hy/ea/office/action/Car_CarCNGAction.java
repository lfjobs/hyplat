package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarCNG;
import hy.ea.bo.office.CarInformation;
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
 * 车辆CNG信息
 * 
 * @author Administrator
 * 
 */
@Controller
@Scope("prototype")
public class Car_CarCNGAction {
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
	private CarCNG carCNG;
	private CarInformation carInformation;
	private String parameter;
	private String type;

	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarCNG.class);
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
			carCNG = (CarCNG) session.get("tablesearch");
			if(carCNG.getCarNum()!=null&&!carCNG.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carCNG.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carCNG.getCarType()!=null&&!carCNG.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carCNG.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carCNG.getEngineNum()!=null&&!carCNG.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carCNG.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carCNG.getCarPeople()!=null&&!carCNG.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",carCNG.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getCarCNGList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarCNGList";
	}
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carCNG);
		return getCarCNGList();
	}
	//导出功能
	@SuppressWarnings("unchecked")
	public String showCngExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String sql=" select carnum,cartype,enginenum,carPeople, refitname, refitphone, refitcontact, refitcompany, refitlicensecode, refitdate, refitagofuel, refitafterfuel, cylindertype, refiteligiblecode, cylindereligiblecode, cylinderdetectcode," +
				" cylinderdetectdate, cylinderdetectname, cylinderdetectphone, cylinderdetectcontact, remarks from dt_car_cng "+
				"where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(CarCNG.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆cng信息管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//删除功能
	public String deleteCarcng(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,carCNG.getCngID()};
		String hql="from CarCNG where companyID=? and organizationID=? and cngID=?";
		CarCNG cng= (CarCNG) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆cng信息管理:("+cng.getCngID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete CarCNG where companyID=? and organizationID=? and cngID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[] { hql2 }, param);
		return "success";
	}
	//添加保存
	public String savecarcngList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carCNG.setCngID(serverService.getServerID("CarCNG"));
		carCNG.setCarID(cars.getCarID());
		carCNG.setCarNum(cars.getCarNum());
		carCNG.setCarType(cars.getCarType());
		carCNG.setEngineNum(cars.getEngineNum());
		carCNG.setCarPeople(cars.getStaffName());
		carCNG.setOrganizationID(organizationID);
		carCNG.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carCNG);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updateCarcng(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarCNG where companyID=? and cngID=?";
		CarCNG cng=(CarCNG) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),carCNG.getCngID()});
		carCNG.setCarID(cng.getCarID());
		carCNG.setCarNum(cng.getCarNum());
		carCNG.setCarType(cng.getCarType());
		carCNG.setEngineNum(cng.getEngineNum());
		carCNG.setCarPeople(cng.getCarPeople());
		carCNG.setOrganizationID(organizationID);
		carCNG.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(carCNG);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//查询按钮功能
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", carCNG);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 carCNG=  (CarCNG) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from CarCNG cars where 1=1 ";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=? ";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=? ";
			 parms.add(organizationID);
			 if(carCNG.getCarNum() !=null && !"".equals(carCNG.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ? ";
				 parms.add("%" + carCNG.getCarNum().trim()+"%");
			 }
			 if(carCNG.getCarType()!=null && !"".equals(carCNG.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ? ";
				parms.add("%" + carCNG.getCarType().trim()+"%");
			 }
			 if(carCNG.getEngineNum()!=null && !"".equals(carCNG.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ? ";
				 parms.add("%" + carCNG.getEngineNum().trim()+"%");
			 }
			 if(carCNG.getCarPeople()!=null && !"".equals(carCNG.getCarPeople().trim())){
				 hqlcarsearch+="and cars.carPeople like ? ";
				 parms.add("%" + carCNG.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarCNGList";
		 }
		 session.put("tablesearch", carCNG);
		 return "getCarCNGList";
	}
	public CarCNG getCarCNG() {
		return carCNG;
	}
	public void setCarCNG(CarCNG carCNG) {
		this.carCNG = carCNG;
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
	public CarInformation getCarInformation() {
		return carInformation;
	}
	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}