package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.carPurchase;
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
public class Car_PurchaseAction {
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
	private carPurchase purchase;
	private CarInformation carInformation;
	private String parameter;
	private String type;
	

	//查询
	private DetachedCriteria getList(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account=(CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String companyID=account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(carPurchase.class);
		
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
		if(search !=null && search.equals("search")){
			purchase=(carPurchase) session.get("tablesearch");
			if(purchase.getCarNum()!=null&&!purchase.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",purchase.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(purchase.getCarType()!=null&&!purchase.getCarType().equals("")){
				dc.add(Restrictions.like("carType",purchase.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(purchase.getEngineNum()!=null&&!purchase.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",purchase.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(purchase.getCarPeople()!=null&&!purchase.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",purchase.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getPurchaseList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getPurchaseList";
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", purchase);
		return getPurchaseList();
	}
	//导出功能
	@SuppressWarnings("unchecked")
	public String showPurchaseExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select carNum,cartype,frameNum,enginenum,carPeople,numbers," +
				"taxpayer,brandModel,collectionNum,agentSignature from DT_PURCHASE_TAXCARD where " +
				"companyID= ? and organizationID=? and carID is not null";
		Object[]params={account.getCompanyID(),organizationID};
		list=baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(carPurchase.columnHeadings(),list);
		CLogBook logBook=logBookService.saveCLogBook(organizationID, "导出车辆购置税政管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//删除功能
	public String deletpurchase(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account=(CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,purchase.getPurchaseID()};
		String hql="from carPurchase where companyID=? and organizationID=? and purchaseID=?";
		carPurchase ps=(carPurchase) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook =logBookService.saveCLogBook(organizationID,"删除车辆购置税政:("+ps.getPurchaseID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete carPurchase where companyID=? and organizationID=? and purchaseID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql2 }, param);
		return "success";
	}
	//添加保存
	public String savePurchaseList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		purchase.setPurchaseID(serverService.getServerID("carPurchase"));
		purchase.setCarID(cars.getCarID());
		purchase.setCarNum(cars.getCarNum());
		purchase.setCarType(cars.getCarType());
		purchase.setFrameNum(cars.getCarFrameNum());
		purchase.setEngineNum(cars.getEngineNum());
		purchase.setCarPeople(cars.getStaffName());
		purchase.setOrganizationID(organizationID);
		purchase.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(purchase);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updatePurchase(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from carPurchase where companyID=?  and purchaseID=?";
		carPurchase pss=(carPurchase) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),purchase.getPurchaseID()});
		purchase.setCarID(pss.getCarID());
		purchase.setCarNum(pss.getCarNum());
		purchase.setFrameNum(pss.getFrameNum());
		purchase.setCarType(pss.getCarType());
		purchase.setEngineNum(pss.getEngineNum());
		purchase.setCarPeople(pss.getCarPeople());
		purchase.setOrganizationID(organizationID);
		purchase.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(purchase);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//查询按钮
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch",purchase);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 purchase=  (carPurchase) ActionContext.getContext().getSession().get("tablesearch");
			 StringBuffer suner=new StringBuffer("from carPurchase cars where 1=1");
			 List<Object> parms=new ArrayList<Object>();
			 suner.append("and cars.companyID=? ");
			 parms.add(companyID);
			 suner.append("and cars.organizationID=? ");
			 parms.add(organizationID);
			 if(purchase.getCarNum() !=null && !"".equals(purchase.getCarNum().trim())){
				 suner.append("and cars.carNum like ? ");
				 parms.add("%" + purchase.getCarNum().trim()+"%");
			 }
			 if(purchase.getCarType()!=null && !"".equals(purchase.getCarType().trim())){
				 suner.append("and cars.carType like ? ");
				parms.add("%" + purchase.getCarType().trim()+"%");
			 }
			 if(purchase.getEngineNum()!=null && !"".equals(purchase.getEngineNum().trim())){
				 suner.append("and cars.engineNum like ? ");
				 parms.add("%" + purchase.getEngineNum().trim()+"%");
			 }
			 if(purchase.getCarPeople()!=null && !"".equals(purchase.getCarPeople().trim())){
				 suner.append("and cars.carPeople like ? ");
				 parms.add("%" + purchase.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						suner.toString(), parms.toArray());
			 return "getPurchaseList";
		 }
		 session.put("tablesearch", purchase);
		 return "getPurchaseList";
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

	public carPurchase getPurchase() {
		return purchase;
	}

	public void setPurchase(carPurchase purchase) {
		this.purchase = purchase;
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