package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarInvoice;
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
 * 购车发票
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class Car_InvoiceAction {
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
	private CarInvoice carinvoice;
	private CarInformation carInformation;
	private String parameter;
	private String result;
	private String typeType;
	private String type;

	//购车发票管理查询
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarInvoice.class);
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
			carinvoice = (CarInvoice) session.get("tablesearch");
			if(carinvoice.getCarNum()!=null&&!carinvoice.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carinvoice.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carinvoice.getCarType()!=null&&!carinvoice.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carinvoice.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carinvoice.getEngineNum()!=null&&!carinvoice.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",carinvoice.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carinvoice.getCarpeople()!=null&&!carinvoice.getCarpeople().equals("")){
				dc.add(Restrictions.like("carpeople",carinvoice.getCarpeople().trim(),MatchMode.ANYWHERE));
			}
			
			
		}
		return dc;
	}
	public String getCarInvoiceList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
	
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarInvoiceList";
	}
	
	
	

	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carinvoice);
		return getCarInvoiceList();
	}
	
	
	
	//购车发票导出功能
	@SuppressWarnings("unchecked")
	public String showCarinvoiceExcels(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String hql="select carNum,carType,engineNum,carpeople, invoicecode, invoicedate, purchaseunits, purchasecode, brandmodel," +
				" origin, salesname, salesaddress, remarks from dt_car_invoice"+
				" where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(CarInvoice.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆购车发票管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//购车发票删除功能
	public String deleteCarinvoice(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[] param={account.getCompanyID(),organizationID,carinvoice.getInvoiceID()};
		String hqls="from CarInvoice where companyID=? and organizationID=? and invoiceID=?";
		CarInvoice carm= (CarInvoice) baseBeanService.getBeanByHqlAndParams(hqls,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆购车发票管理:("+carm.getInvoiceID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql="delete CarInvoice where companyID=? and organizationID=? and invoiceID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, param);
		return "success";
	}
	//车辆购车发票中选择车辆查询
	private DetachedCriteria getcarList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		dc.add(Restrictions.in("state3",new Object[]{"00","01"}));
		//if (search != null && search.equals("search")) {
			//carInformation = (CarInformation) session.get("tablesearch");
			//dc.add(Restrictions.like("organizationID", carInformation.getOrganizationID(), MatchMode.ANYWHERE));
		//}
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
	//车辆购车买票添加保存信息
	public String saveCarinvoice(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carinvoice.setInvoiceID(serverService.getServerID("CarInvoice"));
		carinvoice.setCarID(cars.getCarID());
		carinvoice.setCarNum(cars.getCarNum());
		carinvoice.setCarType(cars.getCarType());
		carinvoice.setEngineNum(cars.getEngineNum());
		carinvoice.setCarpeople(cars.getStaffName());
		carinvoice.setOrganizationID(organizationID);
		carinvoice.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carinvoice);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//车辆购车买票的修改保存
	public String updateCarinvoice(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql2="from CarInvoice where companyID=? and invoiceID=?";
		CarInvoice carms=(CarInvoice) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carinvoice.getInvoiceID()});
		carms.setInvoiceCode(carinvoice.getInvoiceCode());
		carms.setInvoiceDate(carinvoice.getInvoiceDate());
		carms.setPurchaseUnits(carinvoice.getPurchaseUnits());
		carms.setPurchaseCode(carinvoice.getPurchaseCode());
		carms.setBrandModel(carinvoice.getBrandModel());
		carms.setOrigin(carinvoice.getOrigin());
		carms.setSalesName(carinvoice.getSalesName());
		carms.setSalesAddress(carinvoice.getSalesAddress());
		carms.setRemarks(carinvoice.getRemarks());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carms);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
    	return "success";
	}
	

	
	
	//车辆购车发票查询按钮的操作
	public String toSearchCar() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carinvoice);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		 Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount account = (CAccount) session.get("account");
		 String organizationID = (String) session.get("organizationID");
		 String companyID = account.getCompanyID();
		 if(search !=null && search.equals("search")){
			 carinvoice=(CarInvoice) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from CarInvoice cars where 1=1 ";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=? ";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=?";
			 parms.add(organizationID);
			 if(carinvoice.getCarNum() !=null && !"".equals(carinvoice.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ? ";
				 parms.add("%" + carinvoice.getCarNum().trim()+"%");
			 }
			 if(carinvoice.getCarType()!=null && !"".equals(carinvoice.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ? ";
				parms.add("%" + carinvoice.getCarType().trim()+"%");
			 }
			 if(carinvoice.getEngineNum()!=null && !"".equals(carinvoice.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ? ";
				 parms.add("%" + carinvoice.getEngineNum().trim()+"%");
			 }
			 if(carinvoice.getCarpeople()!=null && !"".equals(carinvoice.getCarpeople().trim())){
				 hqlcarsearch+="and cars.carpeople like ? ";
				 parms.add("%" + carinvoice.getCarpeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarInvoiceList";
		 }

		 return "getCarInvoiceList";
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
	public CarInvoice getCarinvoice() {
		return carinvoice;
	}
	public void setCarinvoice(CarInvoice carinvoice) {
		this.carinvoice = carinvoice;
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