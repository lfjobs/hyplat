package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.carQuasi;
import hy.ea.service.CCodeService;
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

@Controller
@Scope("prototype")
public class Car_shuttleAction {
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
	private carQuasi carquasi;
	private carQuasi carseat;
	private CarInformation carInformation;
	private String parameter;
	private List<CCode> bywaylist;
	private  List<CCode> addlisyt;
	@Resource
	private CCodeService codeService;
	private String result;
	private ContactCompany contactCompany;
	private String typeType;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	
	//车辆接送信息查询语句
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext .getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(carQuasi.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			carquasi = (carQuasi) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", carquasi.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}
	public String getCarQuasiList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
	  //单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		//乘坐方式
		bywaylist = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode201211125p8932ixtz0000000017");
		//接送起地点
		addlisyt = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode201211125p8932ixtz0000000022");
		return "getCarQuasiList";
	}
	//车辆中接送信息导出功能
	@SuppressWarnings({ "static-access", "unchecked" })
	public String showCarseatExcels(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String hql = "select carNum,carType,carFrameNum,numeral,zuonum, case seatState when ? then ? when ? then ? end as seatState,QuasiLoad," +
				"realLoad,overLoad,dc.codevalue as byWay,departureTime,arriveTime,carUtil," +
				"utilPeople,utilTel,driver,driverTel,cod.codevalue as beganPlace,endPlace,beganTime," +
				"endTime,util,passenger,passengerTel,undertakeUtil,undertakePeo,undertakeTel,remark  from dt_car_quasi car left join dtccode dc on car.byway=dc.codeid left join dtccode cod on car.beganplace=cod.codeid " +
				"where companyID=? and organizationID=? and carID is not null ";
		Object[] params = { "00","未坐","01","已坐",account.getCompanyID(),organizationID };
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(carquasi.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆接送信息管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//车辆接送信息删除功能
	public String deleteCarquasis(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[] param={account.getCompanyID(),organizationID,carquasi.getQuasiID()};
		String hqls="from carQuasi where companyID=? and organizationID=? and quasiID = ?";
		carQuasi carseat=(carQuasi) baseBeanService.getBeanByHqlAndParams(hqls,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆准载座位管理:("+carseat.getQuasiID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql = "delete carQuasi where companyID=? and organizationID=?  and quasiID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql }, param);
		return "success";
	}
	//车辆接送信息添加中选择车辆
	private DetachedCriteria getListcar() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		dc.add(Restrictions.in("state3",new Object[]{"00","01"}));
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
	public String getcseatnull(){
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
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getListcar());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	//车辆接送信息添加保存
	public String saveCarseat(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carquasi.setQuasiID(serverService.getServerID("carQuasi"));
		carquasi.setCarID(cars.getCarID());
		carquasi.setCarNum(cars.getCarNum());
		carquasi.setCarType(cars.getCarType());
		carquasi.setCarFrameNum(cars.getCarFrameNum());
		carquasi.setQuasiLoad(cars.getRatifyPeople());
		carquasi.setRealLoad("0");
		carquasi.setOverLoad("0");
		carquasi.setOrganizationID(organizationID);
		carquasi.setCompanyID(account.getCompanyID());
		String hqls="from ContactCompany dt where dt.ccompanyID=?";
		Object[] param={contactCompany.getCcompanyID()};
		ContactCompany cutil= (ContactCompany) baseBeanService.getBeanByHqlAndParams(hqls,param);
		carquasi.setCarUtil(cutil.getCompanyName());
		carquasi.setUtilPeople(cutil.getCresponsible());
		carquasi.setUtilTel(cutil.getResponsibleTel());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carquasi);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
    	return "success";
	}
	public String updateCarseat(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql2="from carQuasi where companyID=? and quasiID=?";
		carQuasi carseats=(carQuasi) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carquasi.getQuasiID()});
		carquasi.setCarID(carseats.getCarID());
		carquasi.setCarNum(carseats.getCarNum());
		carquasi.setCarType(carseats.getCarType());
		carquasi.setCarFrameNum(carseats.getCarFrameNum());
		carquasi.setCarUtil(carseats.getCarUtil());
		carquasi.setUtilPeople(carseats.getUtilPeople());
		carquasi.setUtilTel(carseats.getUtilTel());
		carquasi.setOrganizationID(organizationID);
		carquasi.setCompanyID(account.getCompanyID());
		carquasi.setQuasiLoad(carseats.getQuasiLoad());
		carquasi.setRealLoad("0");
		carquasi.setOverLoad("0");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(carquasi);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//车辆接送信息查询按钮操作
	public String toSearchCar() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carquasi);
		return getListCarByCompanyID();
	}
 public String getListCarByCompanyID(){
	 Map<String, Object> session = ActionContext.getContext().getSession();
	 CAccount account = (CAccount) session.get("account");
	 String organizationID = (String) session.get("organizationID");
	 String companyID = account.getCompanyID();
	 if(search !=null && search.equals("search")){
		 carquasi=(carQuasi) ActionContext.getContext().getSession().get("tablesearch");
		 StringBuffer suber=new StringBuffer("from carQuasi cars where 1=1 ");
		 List<Object> parms=new ArrayList<Object>();
		 suber.append("and cars.companyID=? ");
		 parms.add(companyID);
		 suber.append("and cars.organizationID=? ");
		 parms.add(organizationID);
		 if(carquasi.getCarNum() !=null && !"".equals(carquasi.getCarNum().trim())){
			 suber.append("and cars.carNum like ? ");
			 parms.add("%" + carquasi.getCarNum().trim()+"%");
		 }
		 if(carquasi.getCarType()!=null && !"".equals(carquasi.getCarType().trim())){
			 suber.append("and cars.carType like ? ");
			parms.add("%" + carquasi.getCarType().trim()+"%");
		 }
		 if(carquasi.getCarFrameNum()!=null && !"".equals(carquasi.getCarFrameNum().trim())){
			 suber.append("and cars.carFrameNum like ? ");
			 parms.add("%" + carquasi.getCarFrameNum().trim()+"%");
		 }
		 if(carquasi.getCarUtil()!=null && !"".equals(carquasi.getCarUtil().trim())){
			 suber.append("and cars.carUtil like ? ");
			 parms.add("%" + carquasi.getCarUtil().trim()+"%");
		 }
		 if(carquasi.getUtilPeople()!=null && !"".equals(carquasi.getUtilPeople().trim())){
			 suber.append("and cars.utilPeople like ? ");
			 parms.add("%" + carquasi.getUtilPeople().trim()+"%");
		 }
		 //单位往来关系
			connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
					"scode20110224xpd2t2jvda0000000002");
			//乘坐方式
			bywaylist = codeService.getCCodeListByPID(account
					.getCompanyID(), "scode201211125p8932ixtz0000000017");
			//接送起地点
			addlisyt = codeService.getCCodeListByPID(account
					.getCompanyID(), "scode201211125p8932ixtz0000000022");
		 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					suber.toString(), parms.toArray());
		 return "getCarQuasiList";
	 }
	 session.put("tablesearch", carquasi);
	 return "getCarQuasiList";
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
	public carQuasi getCarquasi() {
		return carquasi;
	}
	public void setCarquasi(carQuasi carquasi) {
		this.carquasi = carquasi;
	}
	public CarInformation getCarInformation() {
		return carInformation;
	}
	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}
	public List<CCode> getBywaylist() {
		return bywaylist;
	}
	public void setBywaylist(List<CCode> bywaylist) {
		this.bywaylist = bywaylist;
	}

	public List<CCode> getAddlisyt() {
		return addlisyt;
	}

	public void setAddlisyt(List<CCode> addlisyt) {
		this.addlisyt = addlisyt;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public carQuasi getCarseat() {
		return carseat;
	}

	public void setCarseat(carQuasi carseat) {
		this.carseat = carseat;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
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
	
}