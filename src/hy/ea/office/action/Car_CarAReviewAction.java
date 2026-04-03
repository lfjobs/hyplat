package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarAReview;
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
 * 车辆年审信息
 * 
 * @author Administrator
 * 
 */
@Controller
@Scope("prototype")
public class Car_CarAReviewAction {
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
	private CarAReview carAReview;
	private CarInformation carInformation;
	private Map<String, CarAReview>  carareviewmap;
	private String parameter;
	private String type;

	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarAReview.class);
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
			carAReview =    (CarAReview) session.get("tablesearch");
			if(carAReview.getCarNum()!=null&&!carAReview.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carAReview.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carAReview.getCarType()!=null&&!carAReview.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carAReview.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carAReview.getEngineNum()!=null&&!carAReview.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carAReview.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carAReview.getCarPeople()!=null&&!carAReview.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",carAReview.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getCarAReviewList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarAReviewList";
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carAReview);
		return getCarAReviewList();
	}
	//导出功能
	@SuppressWarnings("unchecked")
	public String showCarareviewExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String sql="select carnum,cartype,enginenum,carPeople, registrationdate, " +
				"areviewvalid, areviewdate, policycode, areviewcode, remarks from dt_car_areview " +
				"where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(CarAReview.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆年审信息管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//车辆年审删除信息
	public String deleteCarareview(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,carAReview.getAreviewID()};
		//String hql="from CarAReview where companyID=? and organizationID=? and areviewID=?";
		//CarAReview areview=(CarAReview) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆年审信息管理", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete CarAReview where companyID=? and organizationID=? and areviewID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[] { hql2 }, param);
		return "success";
	}
	//车辆添加保存信息
	public String savecarareviewList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carAReview.setAreviewID(serverService.getServerID("CarAReview"));
		carAReview.setCarID(cars.getCarID());
		carAReview.setCarNum(cars.getCarNum());
		carAReview.setCarType(cars.getCarType());
		carAReview.setEngineNum(cars.getEngineNum());
		carAReview.setCarPeople(cars.getStaffName());
		carAReview.setOrganizationID(organizationID);
		carAReview.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carAReview);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updatecarareview(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarAReview where companyID=? and areviewID=?";
		CarAReview tax= (CarAReview) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),carAReview.getAreviewID()});
		carAReview.setCarID(tax.getCarID());
		carAReview.setCarNum(tax.getCarNum());
		carAReview.setCarType(tax.getCarType());
		carAReview.setEngineNum(tax.getEngineNum());
		carAReview.setCarPeople(tax.getCarPeople());
		carAReview.setOrganizationID(organizationID);
		carAReview.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(carAReview);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//查询按钮功能
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", carAReview);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 carAReview=   (CarAReview) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from CarAReview cars where 1=1";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=?";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=?";
			 parms.add(organizationID);
			 if(carAReview.getCarNum() !=null && !"".equals(carAReview.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ?";
				 parms.add("%" + carAReview.getCarNum().trim()+"%");
			 }
			 if(carAReview.getCarType()!=null && !"".equals(carAReview.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ?";
				parms.add("%" + carAReview.getCarType().trim()+"%");
			 }
			 if(carAReview.getEngineNum()!=null && !"".equals(carAReview.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ?";
				 parms.add("%" + carAReview.getEngineNum().trim()+"%");
			 }
			 if(carAReview.getCarPeople()!=null && !"".equals(carAReview.getCarPeople().trim())){
				 hqlcarsearch+="and cars.carPeople like ?";
				 parms.add("%" + carAReview.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarAReviewList";
		 }
		 session.put("tablesearch", carAReview);
		 return "getCarAReviewList";
	}
	public CarAReview getCarAReview() {
		return carAReview;
	}

	public void setCarAReview(CarAReview carAReview) {
		this.carAReview = carAReview;
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

	public Map<String, CarAReview> getCarareviewmap() {
		return carareviewmap;
	}

	public void setCarareviewmap(Map<String, CarAReview> carareviewmap) {
		this.carareviewmap = carareviewmap;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}