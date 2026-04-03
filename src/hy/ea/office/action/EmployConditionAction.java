package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.EmployCondition;
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

import com.opensymphony.xwork2.ActionContext;
/*
 * 车辆使用信息
 * */
public class EmployConditionAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private InputStream excelStream;
	private int pageNumber;
	private String search;
	private EmployCondition employcondition;
	private CarInformation carInformation;
	private String parameter;
	private String type;
	
	//车辆使用信息的查询
	private DetachedCriteria getList(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account=(CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String companyID=account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(EmployCondition.class);
	
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
			employcondition=(EmployCondition) session.get("tablesearch");
			if(employcondition.getCarNum()!=null&&!employcondition.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",employcondition.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(employcondition.getCarType()!=null&&!employcondition.getCarType().equals("")){
				dc.add(Restrictions.like("carType",employcondition.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(employcondition.getEngineNum()!=null&&!employcondition.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",employcondition.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(employcondition.getCarPeople()!=null&&!employcondition.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",employcondition.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getemployconditionList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "Getemployconditionlist";
	}
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", employcondition);
		return getemployconditionList();
	}
	//车辆使用信息的导出信息
	@SuppressWarnings("unchecked")
	public String showecondiExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select  carNum,cartype,frameNum,enginenum,carPeople, " +
				"currentsituation, conecttime, assignmentmoney, buyname, buytelphone, " +
				"certificatenum, tracknumber, remark from dt_car_condition where " +
				"companyID= ? and organizationID=? and carID is not null";
		Object[]params={account.getCompanyID(),organizationID};
		list=baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(EmployCondition.columnHeadings(),list);
		CLogBook logBook=logBookService.saveCLogBook(organizationID, "导出车辆实用信息管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//车辆实用信息的删除信息
	public String deletcondi(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account=(CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,employcondition.getConditionID()};
		String hql="from EmployCondition where companyID=? and organizationID=? and conditionID=?";
		EmployCondition ems= (EmployCondition) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook =logBookService.saveCLogBook(organizationID,"删除车辆使用信息:("+ems.getConditionID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete EmployCondition where companyID=? and organizationID=? and conditionID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql2 }, param);
		return "success";
	}
	//车辆使用信息的添加保存
	public String savecondiList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		employcondition.setConditionID(serverService.getServerID("EmployCondition"));
		employcondition.setCarID(cars.getCarID());
		employcondition.setCarNum(cars.getCarNum());
		employcondition.setCarType(cars.getCarType());
		employcondition.setFrameNum(cars.getCarFrameNum());
		employcondition.setEngineNum(cars.getEngineNum());
		employcondition.setCarPeople(cars.getStaffName());
		employcondition.setOrganizationID(organizationID);
		employcondition.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(employcondition);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//车辆使用信息的修改保存
	public String updatecondi(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from EmployCondition where companyID=?  and conditionID=?";
		EmployCondition esm=(EmployCondition) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),employcondition.getConditionID()});
		employcondition.setCarID(esm.getCarID());
		employcondition.setCarNum(esm.getCarNum());
		employcondition.setFrameNum(esm.getFrameNum());
		employcondition.setCarType(esm.getCarType());
		employcondition.setEngineNum(esm.getEngineNum());
		employcondition.setCarPeople(esm.getCarPeople());
		employcondition.setOrganizationID(organizationID);
		employcondition.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(employcondition);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";

	}
	//车辆使用信息的查询按钮
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch",employcondition);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 employcondition=  (EmployCondition) ActionContext.getContext().getSession().get("tablesearch");
			 StringBuffer suner=new StringBuffer("from EmployCondition cars where 1=1");
			 List<Object> parms=new ArrayList<Object>();
			 suner.append("and cars.companyID=? ");
			 parms.add(companyID);
			 suner.append("and cars.organizationID=? ");
			 parms.add(organizationID);
			 if(employcondition.getCarNum() !=null && !"".equals(employcondition.getCarNum().trim())){
				 suner.append("and cars.carNum like ? ");
				 parms.add("%" + employcondition.getCarNum().trim()+"%");
			 }
			 if(employcondition.getCarType()!=null && !"".equals(employcondition.getCarType().trim())){
				 suner.append("and cars.carType like ? ");
				parms.add("%" + employcondition.getCarType().trim()+"%");
			 }
			 if(employcondition.getEngineNum()!=null && !"".equals(employcondition.getEngineNum().trim())){
				 suner.append("and cars.engineNum like ? ");
				 parms.add("%" + employcondition.getEngineNum().trim()+"%");
			 }
			 if(employcondition.getCarPeople()!=null && !"".equals(employcondition.getCarPeople().trim())){
				 suner.append("and cars.carPeople like ? ");
				 parms.add("%" + employcondition.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						suner.toString(), parms.toArray());
			 return "Getemployconditionlist";
		 }
		 session.put("tablesearch", employcondition);
		 return "Getemployconditionlist";
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
	public EmployCondition getEmploycondition() {
		return employcondition;
	}
	public void setEmploycondition(EmployCondition employcondition) {
		this.employcondition = employcondition;
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
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
