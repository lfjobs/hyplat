package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.carBottle;
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

@Controller
@Scope("prototype")
public class Car_BottleAction {
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
	private carBottle bottle;
	private CarInformation carInformation;
	private String parameter;
	private String type;
	

	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(carBottle.class);
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
			bottle =  (carBottle) session.get("tablesearch");
			if(bottle.getCarNum()!=null&&!bottle.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",bottle.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(bottle.getCarType()!=null&&!bottle.getCarType().equals("")){
				dc.add(Restrictions.like("carType",bottle.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(bottle.getEngineNum()!=null&&!bottle.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",bottle.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(bottle.getCarPeople()!=null&&!bottle.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",bottle.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getBottleList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getBottleList";
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", bottle);
		return getBottleList();
	}
	//导出功能
	@SuppressWarnings("unchecked")
	public String showbottleExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select carNum,cartype,frameNum,enginenum,carPeople,boardingnum," +
				" area,  unit, brandtype, owners,  bottleaddress, telephone, installationunit," +
				" installationnum, installationdate, cylindersnum, typenum, makeunit, factorydate, " +
				"volume, inspectiondate from dt_bottle_use where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(carBottle.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车用瓶使用管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//删除功能
	public String deletbottle(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,bottle.getBottleID()};
		String hql="from carBottle where companyID=? and organizationID=? and bottleID=?";
		carBottle bott=(carBottle) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车用瓶使用信息管理:("+bott.getBottleID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete carBottle where companyID=? and organizationID=? and bottleID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql2 }, param);
		return "success";
	}
	//添加保存
	public String savebottleList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		bottle.setBottleID(serverService.getServerID("carBottle"));
		bottle.setCarID(cars.getCarID());
		bottle.setCarNum(cars.getCarNum());
		bottle.setCarType(cars.getCarType());
		bottle.setFrameNum(cars.getCarFrameNum());
		bottle.setEngineNum(cars.getEngineNum());
		bottle.setCarPeople(cars.getStaffName());
		bottle.setOrganizationID(organizationID);
		bottle.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(bottle);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updatebottle(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from carBottle where companyID=? and bottleID=?";
		carBottle bots=(carBottle) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),bottle.getBottleID()});
		bottle.setCarID(bots.getCarID());
		bottle.setCarNum(bots.getCarNum());
		bottle.setFrameNum(bots.getFrameNum());
		bottle.setCarType(bots.getCarType());
		bottle.setEngineNum(bots.getEngineNum());
		bottle.setCarPeople(bots.getCarPeople());
		bottle.setOrganizationID(organizationID);
		bottle.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(bottle);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//查询按钮
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", bottle);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 bottle=   (carBottle) ActionContext.getContext().getSession().get("tablesearch");
			 StringBuffer suner=new StringBuffer("from carBottle cars where 1=1");
			 List<Object> parms=new ArrayList<Object>();
			 suner.append("and cars.companyID=? ");
			 parms.add(companyID);
			 suner.append("and cars.organizationID=? ");
			 parms.add(organizationID);
			 if(bottle.getCarNum() !=null && !"".equals(bottle.getCarNum().trim())){
				 suner.append("and cars.carNum like ? ");
				 parms.add("%" + bottle.getCarNum().trim()+"%");
			 }
			 if(bottle.getCarType()!=null && !"".equals(bottle.getCarType().trim())){
				 suner.append("and cars.carType like ? ");
				parms.add("%" + bottle.getCarType().trim()+"%");
			 }
			 if(bottle.getEngineNum()!=null && !"".equals(bottle.getEngineNum().trim())){
				 suner.append("and cars.engineNum like ? ");
				 parms.add("%" + bottle.getEngineNum().trim()+"%");
			 }
			 if(bottle.getCarPeople()!=null && !"".equals(bottle.getCarPeople().trim())){
				 suner.append("and cars.carPeople like ? ");
				 parms.add("%" + bottle.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						suner.toString(), parms.toArray());
			 return "getBottleList";
		 }
		 session.put("tablesearch", bottle);
		 return "getBottleList";
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


	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}

	public carBottle getBottle() {
		return bottle;
	}

	public void setBottle(carBottle bottle) {
		this.bottle = bottle;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}