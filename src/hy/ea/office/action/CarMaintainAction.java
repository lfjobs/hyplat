package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarMaintain;
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
 * 车辆维护 CarMaintainAction
 */
@Controller
@Scope("prototype")
public class CarMaintainAction {
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
	private CarMaintain carMaintain; 
	private CarInformation carInformation;
	private String parameter;
	private String type;
	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarMaintain.class);
	
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
			carMaintain =  (CarMaintain) session.get("tablesearch");
			if(carMaintain.getCarnumber()!=null&&!carMaintain.getCarnumber().equals("")){
				dc.add(Restrictions.like("carnumber",carMaintain.getCarnumber().trim(),MatchMode.ANYWHERE));
			}
			
			if(carMaintain.getCarType()!=null&&!carMaintain.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carMaintain.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carMaintain.getEngineNum()!=null&&!carMaintain.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carMaintain.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carMaintain.getCarPeople()!=null&&!carMaintain.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",carMaintain.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getListCarMaintain(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "list";
	}
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carMaintain);
		return getListCarMaintain();
	}
	//导出功能
	@SuppressWarnings("unchecked")
	public String showmaintainExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select  carnumber,cartype,framenumber,enginenum,carPeople, repairpeople,  maintenanceaddress,   maintaintype, maintainisbn," +
				" totalmileage, qualitysurveyor, repairecompany,  intodate, outdate, connectcarpeople,takebackdate,  maintaincontext,maintaincost," +
				"remark from dt_car_carmaintain where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(CarMaintain.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆维护信息管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//删除功能
	public String deletMaintain(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,carMaintain.getMaintainID()};
		String hql="from CarMaintain where companyID=? and organizationID=? and maintainID=?";
		CarMaintain mains=(CarMaintain) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆cng信息管理:("+mains.getMaintainID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete CarMaintain where companyID=? and organizationID=? and maintainID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql2 }, param);
		return "success";
	}
	//添加保存
	public String savemaintainList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		@SuppressWarnings("unused")
		String obj = (String) session.get("session_value");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carMaintain.setMaintainID(serverService.getServerID("CarMaintain"));
		carMaintain.setCarID(cars.getCarID());
		carMaintain.setCarnumber(cars.getCarNum());
		carMaintain.setCarType(cars.getCarType());
		carMaintain.setFrameNumber(cars.getCarFrameNum());
		carMaintain.setEngineNum(cars.getEngineNum());
		carMaintain.setCarPeople(cars.getStaffName());
		carMaintain.setOrganizationID(organizationID);
		carMaintain.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carMaintain);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updateMaintain(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		@SuppressWarnings("unused")
		String obj = (String) session.get("session_value");
		String hql="from CarMaintain where companyID=? and maintainID=?";
		CarMaintain maintain=(CarMaintain) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),carMaintain.getMaintainID()});
		carMaintain.setCarID(maintain.getCarID());
		carMaintain.setCarnumber(maintain.getCarnumber());
		carMaintain.setFrameNumber(maintain.getFrameNumber());
		carMaintain.setCarType(maintain.getCarType());
		carMaintain.setEngineNum(maintain.getEngineNum());
		carMaintain.setCarPeople(maintain.getCarPeople());
		carMaintain.setOrganizationID(organizationID);
		carMaintain.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(carMaintain);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//查询按钮功能
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", carMaintain);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 carMaintain=  (CarMaintain) ActionContext.getContext().getSession().get("tablesearch");
			 StringBuffer suner=new StringBuffer("from CarMaintain cars where 1=1");
			 List<Object> parms=new ArrayList<Object>();
			 suner.append("and cars.companyID=? ");
			 parms.add(companyID);
			 suner.append("and cars.organizationID=? ");
			 parms.add(organizationID);
			 if(carMaintain.getCarnumber() !=null && !"".equals(carMaintain.getCarnumber() .trim())){
				 suner.append("and cars.carnumber like ? ");
				 parms.add("%" + carMaintain.getCarnumber() .trim()+"%");
			 }
			 if(carMaintain.getCarType()!=null && !"".equals(carMaintain.getCarType().trim())){
				 suner.append("and cars.carType like ? ");
				parms.add("%" + carMaintain.getCarType().trim()+"%");
			 }
			 if(carMaintain.getEngineNum()!=null && !"".equals(carMaintain.getEngineNum().trim())){
				 suner.append("and cars.engineNum like ? ");
				 parms.add("%" + carMaintain.getEngineNum().trim()+"%");
			 }
			 if(carMaintain.getCarPeople()!=null && !"".equals(carMaintain.getCarPeople().trim())){
				 suner.append("and cars.carPeople like ? ");
				 parms.add("%" + carMaintain.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						suner.toString(), parms.toArray());
			 return "list";
		 }
		 session.put("tablesearch", carMaintain);
		 return "list";
	}
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public CarMaintain getCarMaintain() {
		return carMaintain;
	}

	public void setCarMaintain(CarMaintain carMaintain) {
		this.carMaintain = carMaintain;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
