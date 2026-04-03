package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.carQuasi;
import hy.ea.bo.office.carSeat;
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
public class Car_QuasiAction {
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
	private carSeat carzuo;
	private String typeType;
	private String type;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;

	/**
	 * 导航中车辆准载座位查询语句
	 * @return
	 */
	public DetachedCriteria getseatList(){
		Map<String,Object> session =ActionContext .getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(carQuasi.class);
		
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
			carquasi = (carQuasi) session.get("tablesearch");
			if(carquasi.getCarNum()!=null&&!carquasi.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carquasi.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carquasi.getCarType()!=null&&!carquasi.getCarType().equals("")){
				dc.add(Restrictions.like("carType",carquasi.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(carquasi.getCarFrameNum()!=null&&!carquasi.getCarFrameNum().equals("")){
				dc.add(Restrictions.like("carFrameNum",carquasi.getCarFrameNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carquasi.getCarUtil()!=null&&!carquasi.getCarUtil().equals("")){
				dc.add(Restrictions.like("carUtil",carquasi.getCarUtil().trim(),MatchMode.ANYWHERE));
			}
			if(carquasi.getUtilPeople()!=null&&!carquasi.getUtilPeople().equals("")){
				dc.add(Restrictions.like("utilPeople",carquasi.getUtilPeople().trim(),MatchMode.ANYWHERE));
			}
		} 
		return dc;
	}
	public String getCarseatList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getseatList());
	  //单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		return "getCarseatList";
	}
	
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carquasi);
		return getCarseatList();
	}
	
	/**
	 * 导航中车辆准载座位导出功能
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public String showCarseatExcels() {
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String hql = "select carNum,carType,carFrameNum,numeral,seatNum, case seatState when '00' then '已坐' when '01' then '未坐' end as seatState,QuasiLoad," +
				"realLoad,overLoad,carUtil," +
				"utilPeople,utilTel,driver" +
				" from dt_car_quasi  where companyID=? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(carquasi.columnHeadings2(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆准载座位管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 导航中车辆准载座位删除功能
	 */
	public String deleteCarquasis() {
		//carInformation = selectCarInfromation();
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
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, param);
		return "success";
	}
	/**
	 * 导航中车辆准载座位添加中选择车辆
	 * @return
	 
	private DetachedCriteria getListseat() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		dc.add(Restrictions.in("state3",new Object[]{"00","01"}));
		if (search != null && search.equals("search")) {
			carInformation = (CarInformation) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", carInformation.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}
	*/
	public  String  getcseatnull(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
	if (account == null) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nologin", 1);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		String sql="select carID,carNum,carType,carFrameNum,ratifyPeople from dt_car_carinformation zong  " +
					"where zong.companyid = ? " +
					"and zong.organizationid = ? " +
					"and zong.state3 in ('00','01') "+
				"and zong.carid not in " +
				"(select zi.carid from dt_car_quasi zi where zi.companyid = ? " +
				"and zi.organizationid =? )" ;
		 List<Object> parms=new ArrayList<Object>();
		 parms.add(companyID);
		 parms.add(organizationID);
		 parms.add(companyID);
		 parms.add(organizationID);
		if (null != parameter && !"".equals(parameter)) {
			sql+="and zong.carNum like ? ";
			 parms.add("%" + parameter+"%");
		}
		if (null != typeType && !"".equals(typeType)) {
			sql+="and zong.carType like ?";
			 parms.add("%" + typeType+"%");
		}
		session.put("session_value", Math.random() + "");
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber),sql,"select count(*) "
					+ sql.substring(sql.indexOf("from")),parms.toArray());
		 Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return "success";
	}
	//导航中车辆座位准载添加保存   
    public String saveCarseat()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		//String obj = (String) session.get("session_value");
		//String hql2="from CarInformation where carID=?";
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
		if((carzuo.getSeatNum() !=null) && (! "" .equals(carzuo.getSeatNum()))){
		carzuo.setSeatID(serverService.getServerID("carSeat"));
		carzuo.setCompanyID(account.getCompanyID());
		carzuo.setOrganizationID(organizationID);
		carzuo.setQuasiID(carquasi.getQuasiID());
		baseBeansList.add(carzuo);
		}
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
    	return "success";
    }
    
    //导航中车辆座位准载修改保存
    public String updateCarseat(){
    	ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		//String obj = (String) session.get("session_value");
		String hql2="from carQuasi where companyID=? and quasiID=?";
		carQuasi carseats=(carQuasi) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carquasi.getQuasiID()});
		carseats.setNumeral(carquasi.getNumeral());
		carseats.setSeatNum(carquasi.getSeatNum());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		String hql4="select count(*) from carSeat where  companyID=? and quasiID=?";
		int num=baseBeanService.getConutByByHqlAndParams(hql4,new Object[]{account.getCompanyID(),carquasi.getQuasiID()});
		if(num != 0 ){
		String hql3="from carSeat where companyID=? and quasiID=?";
		carSeat zuowei=(carSeat) baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{account.getCompanyID(),carquasi.getQuasiID()});
		zuowei.setSeatNum(carzuo.getSeatNum());
		baseBeansList.add(zuowei);
		}else if(num==0 && (carzuo.getSeatNum() !=null) && (! "" .equals(carzuo.getSeatNum())) ){
		carzuo.setSeatID(serverService.getServerID("carSeat"));
		carzuo.setCompanyID(account.getCompanyID());
		carzuo.setOrganizationID(organizationID);
		carzuo.setQuasiID(carquasi.getQuasiID());
		baseBeansList.add(carzuo);
		}
		baseBeansList.add(carseats);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
    	return "success";
    }
    /**
	 * 车辆机动车行驶证查询按钮操作
	 * @return
	 */
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
			 String hqlcarsearch="from carQuasi cars where 1=1";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=?";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=?";
			 parms.add(organizationID);
			 if(carquasi.getCarNum() !=null && !"".equals(carquasi.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ?";
				 parms.add("%" + carquasi.getCarNum().trim()+"%");
			 }
			 if(carquasi.getCarType()!=null && !"".equals(carquasi.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ?";
				parms.add("%" + carquasi.getCarType().trim()+"%");
			 }
			 if(carquasi.getCarFrameNum()!=null && !"".equals(carquasi.getCarFrameNum().trim())){
				 hqlcarsearch+="and cars.carFrameNum like ?";
				 parms.add("%" + carquasi.getCarFrameNum().trim()+"%");
			 }
			 if(carquasi.getCarUtil()!=null && !"".equals(carquasi.getCarUtil().trim())){
				 hqlcarsearch+="and cars.carUtil like ?";
				 parms.add("%" + carquasi.getCarUtil().trim()+"%");
			 }
			 if(carquasi.getUtilPeople()!=null && !"".equals(carquasi.getUtilPeople().trim())){
				 hqlcarsearch+="and cars.utilPeople like ?";
				 parms.add("%" + carquasi.getUtilPeople().trim()+"%");
			 }
			 //单位往来关系
				connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
						"scode20110224xpd2t2jvda0000000002");
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getCarseatList";
		 }
		 session.put("tablesearch", carquasi);
		 return "getCarseatList";
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

	public carSeat getCarzuo() {
		return carzuo;
	}

	public void setCarzuo(carSeat carzuo) {
		this.carzuo = carzuo;
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