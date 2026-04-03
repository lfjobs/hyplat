package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarPurchaseTax;
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
 * 购置税发票
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class Car_PurchaseTaxAction {
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
	private CarPurchaseTax carPurchaseTax;
	private CarInformation carInformation;
	private String parameter;
	private String result;
	private String type;

	//车辆购置税发票查询
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarPurchaseTax.class);
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
			carPurchaseTax =  (CarPurchaseTax) session.get("tablesearch");
			if(carPurchaseTax.getCarNum()!=null&&!carPurchaseTax.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carPurchaseTax.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carPurchaseTax.getCarType()!=null&&!carPurchaseTax.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carPurchaseTax.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carPurchaseTax.getEngineNum()!=null&&!carPurchaseTax.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carPurchaseTax.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carPurchaseTax.getCarpeople()!=null&&!carPurchaseTax.getCarpeople().equals("")){
				dc.add(Restrictions.like("carpeople",carPurchaseTax.getCarpeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	} 
	public String getCarPurchaseTaxList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarPurchaseTaxList";
		
	}
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carPurchaseTax);
		return getCarPurchaseTaxList();
	}
	
	//车辆购置谁发票的导出功能
	@SuppressWarnings("unchecked")
	public String showCarPurchaseTaxExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String hql="select carnum, cartype,enginenum,carpeople, invoicecode, invoicedate, carname, brandmodel, origintype, taxprice, tax, " +
				"penalty, dutypaidnum, collectors, payee, remarks from dt_car_purchasetax where"+
				" companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(CarPurchaseTax.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆购置税发票管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//车辆购置税发票的删除功能
	public String deletePurchaseTax(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,carPurchaseTax.getPurchaseTaxID()};
		String hql="from CarPurchaseTax where companyID=? and organizationID=? and purchaseTaxID=?";
		CarPurchaseTax purchasetax=(CarPurchaseTax) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆购买税发票管理:("+purchasetax.getPurchaseTaxID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql1="delete CarPurchaseTax where companyID=? and organizationID=? and purchaseTaxID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[] { hql1 }, param);
		return "success";
	}
	//车辆购买税发票的添加保存
	public String saveCarpurchasetax(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carPurchaseTax.setPurchaseTaxID(serverService.getServerID("CarPurchaseTax"));
		carPurchaseTax.setCarID(cars.getCarID());
		carPurchaseTax.setCarNum(cars.getCarNum());
		carPurchaseTax.setCarType(cars.getCarType());
		carPurchaseTax.setEngineNum(cars.getEngineNum());
		carPurchaseTax.setCarpeople(cars.getStaffName());
		carPurchaseTax.setOrganizationID(organizationID);
		carPurchaseTax.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carPurchaseTax);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//车辆购置税发票的修改保存
	public String updateCarpurchasetax(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarPurchaseTax where companyID=? and purchaseTaxID=?";
		CarPurchaseTax tax=(CarPurchaseTax) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),carPurchaseTax.getPurchaseTaxID()});
		carPurchaseTax.setCarID(tax.getCarID());
		carPurchaseTax.setCarNum(tax.getCarNum());
		carPurchaseTax.setCarType(tax.getCarType());
		carPurchaseTax.setEngineNum(tax.getEngineNum());
		carPurchaseTax.setCarpeople(tax.getCarpeople());
		carPurchaseTax.setOrganizationID(organizationID);
		carPurchaseTax.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(carPurchaseTax);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//车辆购置税发票查询按钮功能
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", carPurchaseTax);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 carPurchaseTax=  (CarPurchaseTax) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from CarPurchaseTax cars where 1=1 ";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=? ";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=? ";
			 parms.add(organizationID);
			 if(carPurchaseTax.getCarNum() !=null && !"".equals(carPurchaseTax.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ? ";
				 parms.add("%" + carPurchaseTax.getCarNum().trim()+"%");
			 }
			 if(carPurchaseTax.getCarType()!=null && !"".equals(carPurchaseTax.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ? ";
				parms.add("%" + carPurchaseTax.getCarType().trim()+"%");
			 }
			 if(carPurchaseTax.getEngineNum()!=null && !"".equals(carPurchaseTax.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ? ";
				 parms.add("%" + carPurchaseTax.getEngineNum().trim()+"%");
			 }
			 if(carPurchaseTax.getCarpeople()!=null && !"".equals(carPurchaseTax.getCarpeople().trim())){
				 hqlcarsearch+="and cars.carpeople like ? ";
				 parms.add("%" + carPurchaseTax.getCarpeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarPurchaseTaxList";
		 }
		 session.put("tablesearch", carPurchaseTax);
		 return "getCarPurchaseTaxList";
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public CarPurchaseTax getCarPurchaseTax() {
		return carPurchaseTax;
	}
	public void setCarPurchaseTax(CarPurchaseTax carPurchaseTax) {
		this.carPurchaseTax = carPurchaseTax;
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