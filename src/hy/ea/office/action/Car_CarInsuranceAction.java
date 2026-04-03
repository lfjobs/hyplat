package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarInsurance;
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
 * 车辆保险信息
 * 
 * @author Administrator
 * 
 */
@Controller
@Scope("prototype")
public class Car_CarInsuranceAction {
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
	private CarInsurance carInsurance;
	private CarInformation carInformation;
	private String parameter;
	private String type;

	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarInsurance.class);
		
		
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
			carInsurance =   (CarInsurance) session.get("tablesearch");
			if(carInsurance.getCarNum()!=null&&!carInsurance.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carInsurance.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carInsurance.getCarType()!=null&&!carInsurance.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carInsurance.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carInsurance.getEngineNum()!=null&&!carInsurance.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carInsurance.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carInsurance.getCarPeople()!=null&&!carInsurance.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",carInsurance.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getCarInsuranceList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarInsuranceList";
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carInsurance);
		return getCarInsuranceList();
	}
	
	//导出功能
	@SuppressWarnings("unchecked")
	public String showInsuranceExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String hql="select carnum,cartype,enginenum,carPeople, insurancecode, " +
				"insurancecomname, insurancename, purchasecode, usenature, brandmodel, " +
				"passengernum, starttime, endtime, insuranceamount, insurancephone, sellinsurer, " +
				"sellinsurerphone, remarks from dt_car_insurance " +
				"where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(CarInsurance.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆保险信息管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//车辆保险删除信息
	public String deleteInsurance(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,carInsurance.getInsuranceID()};
		String hql="from CarInsurance where companyID=? and organizationID=? and insuranceID=?";
		CarInsurance insurance=(CarInsurance) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆保险信息管理：("+insurance.getInsuranceID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete CarInsurance where companyID=? and organizationID=? and insuranceID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[] { hql2 }, param);
		return "success";
	}
	//添加保存信息
	public String savecarinsuranceList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carInsurance.setInsuranceID(serverService.getServerID("CarInsurance"));
		carInsurance.setCarID(cars.getCarID());
		carInsurance.setCarNum(cars.getCarNum());
		carInsurance.setCarType(cars.getCarType());
		carInsurance.setEngineNum(cars.getEngineNum());
		carInsurance.setCarPeople(cars.getStaffName());
		carInsurance.setOrganizationID(organizationID);
		carInsurance.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carInsurance);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updateCarinsurance(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInsurance where companyID=? and insuranceID=?";
		CarInsurance tax= (CarInsurance) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),carInsurance.getInsuranceID()});
		carInsurance.setInsuranceID(tax.getInsuranceID());
		carInsurance.setInsuranceKey(tax.getInsuranceKey());
		carInsurance.setCarID(tax.getCarID());
		carInsurance.setCarNum(tax.getCarNum());
		carInsurance.setCarType(tax.getCarType());
		carInsurance.setEngineNum(tax.getEngineNum());
		carInsurance.setCarPeople(tax.getCarPeople());
		carInsurance.setOrganizationID(organizationID);
		carInsurance.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(carInsurance);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//查询按钮功能
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", carInsurance);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 carInsurance=   (CarInsurance) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from CarInsurance cars where 1=1 ";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=? ";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=? ";
			 parms.add(organizationID);
			 if(carInsurance.getCarNum() !=null && !"".equals(carInsurance.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ? ";
				 parms.add("%" + carInsurance.getCarNum().trim()+"%");
			 }
			 if(carInsurance.getCarType()!=null && !"".equals(carInsurance.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ? ";
				parms.add("%" + carInsurance.getCarType().trim()+"%");
			 }
			 if(carInsurance.getEngineNum()!=null && !"".equals(carInsurance.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ? ";
				 parms.add("%" + carInsurance.getEngineNum().trim()+"%");
			 }
			 if(carInsurance.getCarPeople()!=null && !"".equals(carInsurance.getCarPeople().trim())){
				 hqlcarsearch+="and cars.carPeople like ? ";
				 parms.add("%" + carInsurance.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarInsuranceList";
		 }
		 session.put("tablesearch", carInsurance);
		 return "getCarInsuranceList";
	}
	public CarInsurance getCarInsurance() {
		return carInsurance;
	}
	public void setCarInsurance(CarInsurance carInsurance) {
		this.carInsurance = carInsurance;
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