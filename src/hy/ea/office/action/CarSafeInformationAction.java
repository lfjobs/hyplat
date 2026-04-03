package hy.ea.office.action;



import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarSafeInformation;
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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


/*
 * 车辆安全信息
 * */
@Controller
@Scope("prototype")
public class CarSafeInformationAction {
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
	private CarSafeInformation carsafeinformation;
	private CarInformation carInformation;
	private String parameter;
	private String type;
	

	//查询
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarSafeInformation.class);
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
			carsafeinformation =  (CarSafeInformation) session.get("tablesearch");
			if(carsafeinformation.getCarNum()!=null&&!carsafeinformation.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carsafeinformation.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carsafeinformation.getCarType()!=null&&!carsafeinformation.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carsafeinformation.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carsafeinformation.getEngineNum()!=null&&!carsafeinformation.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carsafeinformation.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carsafeinformation.getCarPeople()!=null&&!carsafeinformation.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",carsafeinformation.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getCarsafeinformationList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarsafeinformationList";
	}
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carsafeinformation);
		return getCarsafeinformationList();
	}
	
	//导出
	@SuppressWarnings("unchecked")
	public String showSafeExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select carNum,carType,engineNum,carPeople, accidenttime,  accidentpass,accidentdutypeople, directlossmoney," +
				" indirectlossmoney, disposetime, disposepeople,disposeidea, accidentbox, certificatenum from dt_car_carsafeinformation" +
				" where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(CarSafeInformation.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆机安全管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//删除
	public String deleteSafe(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[] param={account.getCompanyID(),organizationID,carsafeinformation.getCarSafeID()};
		String hqls="from CarSafeInformation where companyID=? and organizationID=? and carSafeID=?";
		CarSafeInformation carm= (CarSafeInformation) baseBeanService.getBeanByHqlAndParams(hqls,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆安全信息管理:("+carm.getCarSafeID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql="delete CarSafeInformation where companyID=? and organizationID=? and carSafeID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql }, param);
		return "success";
	}
	//添加保存
	public String saveSafeList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carsafeinformation.setCarSafeID(serverService.getServerID("CarSafeInformation"));
		carsafeinformation.setCarID(cars.getCarID());
		carsafeinformation.setCarNum(cars.getCarNum());
		carsafeinformation.setCarType(cars.getCarType());
		carsafeinformation.setEngineNum(cars.getEngineNum());
		carsafeinformation.setCarPeople(cars.getStaffName());
		carsafeinformation.setOrganizationID(organizationID);
		carsafeinformation.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carsafeinformation);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updateCarSafe(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql2="from CarSafeInformation where companyID=? and carSafeID=?";
		CarSafeInformation carms=(CarSafeInformation) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carsafeinformation.getCarSafeID()});
		carsafeinformation.setCarID(carms.getCarID());
		carsafeinformation.setCarNum(carms.getCarNum());
		carsafeinformation.setCarType(carms.getCarType());
		carsafeinformation.setEngineNum(carms.getEngineNum());
		carsafeinformation.setCarPeople(carms.getCarPeople());
		carsafeinformation.setOrganizationID(organizationID);
		carsafeinformation.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carsafeinformation);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
    	return "success";
	}
	//查询
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", carsafeinformation);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		 Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount account = (CAccount) session.get("account");
		 String organizationID = (String) session.get("organizationID");
		 String companyID = account.getCompanyID();
		 if(search !=null && search.equals("search")){
			 carsafeinformation= (CarSafeInformation) ActionContext.getContext().getSession().get("tablesearch");
			 StringBuffer suber=new StringBuffer("from CarSafeInformation cars where 1=1 ");
			 List<Object> parms=new ArrayList<Object>();
			 suber.append("and cars.companyID=? ");
			 parms.add(companyID);
			 suber.append("and cars.organizationID=? ");
			 parms.add(organizationID);
			 if(carsafeinformation.getCarNum() !=null && !"".equals(carsafeinformation.getCarNum().trim())){
				 suber.append("and cars.carNum like ? ");
				 parms.add("%" + carsafeinformation.getCarNum().trim()+"%");
			 }
			 if(carsafeinformation.getCarType()!=null && !"".equals(carsafeinformation.getCarType().trim())){
				 suber.append("and cars.carType like ? ");
				parms.add("%" + carsafeinformation.getCarType().trim()+"%");
			 }
			 if(carsafeinformation.getEngineNum()!=null && !"".equals(carsafeinformation.getEngineNum().trim())){
				 suber.append("and cars.engineNum like ? ");
				 parms.add("%" + carsafeinformation.getEngineNum().trim()+"%");
			 }
			 if(carsafeinformation.getCarPeople()!=null && !"".equals(carsafeinformation.getCarPeople().trim())){
				 suber.append("and cars.carPeople like ? ");
				 parms.add("%" + carsafeinformation.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						suber.toString(), parms.toArray());
			 return "getCarsafeinformationList";
		 }
		 session.put("tablesearch", carsafeinformation);
		 return "getCarsafeinformationList";
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


	public CLogBookService getLogBookService() {
		return logBookService;
	}


	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
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



	public CarSafeInformation getCarsafeinformation() {
		return carsafeinformation;
	}


	public void setCarsafeinformation(CarSafeInformation carsafeinformation) {
		this.carsafeinformation = carsafeinformation;
	}


	public CarInformation getCarInformation() {
		return carInformation;
	}


	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}


	public String getParameter() {
		return parameter;
	}


	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	}
