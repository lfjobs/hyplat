package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.carMotorcar;
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
 * 机动车行驶证
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class Car_MotorcarAction {
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
	private carMotorcar motorcar;
	private CarInformation carInformation;
	private String parameter;
	private String result;
	private String typeType;
	private String type;


	/**
	 * 查询车辆机动车证件管理
	 */
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(carMotorcar.class);
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
			motorcar = (carMotorcar) session.get("tablesearch");
			if(motorcar.getCarNum()!=null&&!motorcar.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",motorcar.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(motorcar.getCarType()!=null&&!motorcar.getCarType().equals("")){
				dc.add(Restrictions.like("carType",motorcar.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(motorcar.getEngineNum()!=null&&!motorcar.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",motorcar.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(motorcar.getCarpeople()!=null&&!motorcar.getCarpeople().equals("")){
				dc.add(Restrictions.like("carpeople",motorcar.getCarpeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	
	public String getMotorcarList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getMotorcarList";
	}
	
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", motorcar);
		return getMotorcarList();
	}
	
	/**
	 * 车辆机动车行驶证导出功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showCarmotorcarExcels(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		String hql=" select carNum,carType,engineNum,carpeople,flapperType,allPeople,address,useProp,recognitionCode,brandModel," +
				"registrationDate,cardDate,validityDate from DT_MOTORCAR_CARREGISTRATION " +
				" where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(hql, params);
		excelStream = excelService.showExcel(carMotorcar.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆机动车行驶证管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 车辆机动车行驶证删除功能
	 * @return
	 */
	public String deleteCarmotorcar(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[] param={account.getCompanyID(),organizationID,motorcar.getMotorcarID()};
		String hqls="from carMotorcar where companyID=? and organizationID=? and motorcarID=?";
		carMotorcar carm= (carMotorcar) baseBeanService.getBeanByHqlAndParams(hqls,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆机动车行驶证管理:("+carm.getMotorcarID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql="delete carMotorcar where companyID=? and organizationID=? and motorcarID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, param);
		return "success";
	}
	/**
	 * 车辆机动车行驶证添加中选择车辆查询
	 * @return
	 */
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
	//导航中车辆机动车行驶证添加保存信息
	public String saveCarmotorcar(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		motorcar.setMotorcarID(serverService.getServerID("carMotorcar"));
		motorcar.setCarID(cars.getCarID());
		motorcar.setCarNum(cars.getCarNum());
		motorcar.setCarType(cars.getCarType());
		motorcar.setEngineNum(cars.getEngineNum());
		motorcar.setCarpeople(cars.getStaffName());
		motorcar.setOrganizationID(organizationID);
		motorcar.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(motorcar);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//车辆机动车行驶证的修改保存
	public String updateCarmotorcar(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql2="from carMotorcar where companyID=? and motorcarID=?";
		carMotorcar carms=(carMotorcar) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),motorcar.getMotorcarID()});
		carms.setFlapperType(motorcar.getFlapperType());
		carms.setAllPeople(motorcar.getAllPeople());
		carms.setAddress(motorcar.getAddress());
		carms.setUseProp(motorcar.getUseProp());
		carms.setRecognitionCode(motorcar.getRecognitionCode());
		carms.setBrandModel(motorcar.getBrandModel());
		carms.setRegistrationDate(motorcar.getRegistrationDate());
		carms.setCardDate(motorcar.getCardDate());
		carms.setValidityDate(motorcar.getValidityDate());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carms);
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
					.put("tablesearch", motorcar);
			return getListCarByCompanyID();
		}
	 public String getListCarByCompanyID(){
		 Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount account = (CAccount) session.get("account");
		 String organizationID = (String) session.get("organizationID");
		 String companyID = account.getCompanyID();
		 if(search !=null && search.equals("search")){
			 motorcar=(carMotorcar) ActionContext.getContext().getSession().get("tablesearch");
			 String hqlcarsearch="from carMotorcar cars where 1=1 ";
			 List<Object> parms=new ArrayList<Object>();
			 hqlcarsearch+="and cars.companyID=? ";
			 parms.add(companyID);
			 hqlcarsearch+="and cars.organizationID=? ";
			 parms.add(organizationID);
			 if(motorcar.getCarNum() !=null && !"".equals(motorcar.getCarNum().trim())){
				 hqlcarsearch+="and cars.carNum like ? ";
				 parms.add("%" + motorcar.getCarNum().trim()+"%");
			 }
			 if(motorcar.getCarType()!=null && !"".equals(motorcar.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ? ";
				parms.add("%" + motorcar.getCarType().trim()+"%");
			 }
			 if(motorcar.getEngineNum()!=null && !"".equals(motorcar.getEngineNum().trim())){
				 hqlcarsearch+="and cars.engineNum like ? ";
				 parms.add("%" + motorcar.getEngineNum().trim()+"%");
			 }
			 if(motorcar.getCarpeople()!=null && !"".equals(motorcar.getCarpeople().trim())){
				 hqlcarsearch+="and cars.carpeople like ? ";
				 parms.add("%" + motorcar.getCarpeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						hqlcarsearch, parms.toArray());
			 return "getMotorcarList";
		 }
		 session.put("tablesearch", motorcar);
		 return "getMotorcarList";
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

	
	public carMotorcar getMotorcar() {
		return motorcar;
	}

	public void setMotorcar(carMotorcar motorcar) {
		this.motorcar = motorcar;
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

	public String getTypeType() {
		return typeType;
	}

	public void setTypeType(String typeType) {
		this.typeType = typeType;
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