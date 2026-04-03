package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Humanresource;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarInformation;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/*
 * 车辆基本信息
 * */
@Controller
@Scope("prototype")
public class CarBaseInfoAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService; 
	private String parameter;
	private String typeJia;
	private String typeType;
	private CarInformation carInformation;
	private CarInformation searchCarInformation;
	private PageForm pageForm;
	private InputStream excelStream;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private String search;
	private String sub;
	private int pageNumber;
	private String result;
	private String deptID;
	private String companyname;
	private String organizationname;
	
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询车辆信息
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", carInformation);
		return getCarBaseInfoList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			carInformation = (CarInformation) session.get("tablesearch");
			if(carInformation.getCarNum()!=null&&!carInformation.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum", carInformation.getCarNum().trim(), MatchMode.ANYWHERE));
			}
			if(carInformation.getCarType()!=null&&!carInformation.getCarType().equals("")){
				dc.add(Restrictions.like("carType", carInformation.getCarType().trim(), MatchMode.ANYWHERE));
			}
			
			if(carInformation.getStaffName()!=null&&!carInformation.getStaffName().equals("")){
				dc.add(Restrictions.like("staffName", carInformation.getStaffName().trim(), MatchMode.ANYWHERE));
			}
		    
		} 
		return dc;
	}
	
	private DetachedCriteria  getListcar(){
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.isNull("companyID"));
		if (null != parameter && !"".equals(parameter)) {
			dc.add(Restrictions.like("carNum", parameter,
					MatchMode.ANYWHERE));
		}
		if (null != typeType && !"".equals(typeType)) {
			dc.add(Restrictions.like("carType", typeType,
					MatchMode.ANYWHERE));
		}
		if (null != typeJia && !"".equals(typeJia)) {
			dc.add(Restrictions.like("carFrameNum", typeJia,
					MatchMode.ANYWHERE));
		}
		dc.addOrder(Order.asc("engineNum"));
		return dc;
	}
	
	//查询车辆的总信息
	public String getCarByID(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		@SuppressWarnings("unused")
		String organizationID=(String) session.get("organizationID");
		String carID=carInformation.getCarID();
		@SuppressWarnings("unused")
		String obj = (String) session.get("session_value");
		String hql2="from CarInformation where carID=? and companyID=?";
		carInformation=(CarInformation) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{carID,companyID});
		return "getcarbyid";
	}
	//选择车辆
	public  String  getcarnull(){
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
		session.put("session_value", Math.random() + "");
		 pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getListcar());
		 Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return "success";
	}
	// 车辆信息列表  
	public String getCarBaseInfoList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "carBaseInfolist";	
	}
	
	
	/**
	 * 
	 * 点击添加
	 * @return
	 */
	
	public String getCarBaseInfo() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}

		String hql = "from CarInformation where carID = ?";
		carInformation = (CarInformation) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { carInformation
						.getCarID() });

		return "base";
	}
	// 导出车辆信息列表

	@SuppressWarnings({ "static-access", "unchecked" })
	public String showExcel() {
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select ds.companyname as gs,dept.organizationname as bm," +
				" dt.staffname,dt.cartype,dt.carnum,dt.enginenum,dt.carframenum," +
				"dt.registrationDate,case state3 when ? then ? when ? then ? when ? then ? end as state3," +
				"case state1 when ? then ? when ? then ? end as state1  from dt_car_carinformation dt " +
				"left join dtcompany ds on dt.companyid=ds.companyid left join dtcorganization dept " +
				"on dt.organizationid=dept.organizationid " +
				"where dt.companyID=? and dt.organizationID=? ";
		Object[] params={"00","未使用","01","已使用","10","下线","00","加盟车","01","本校车",account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(carInformation.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆基本管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
  //车辆信息保存
    
    public String saveCarInformation()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		@SuppressWarnings("unused")
		String obj = (String) session.get("session_value");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,photoFileName, photo, account.getCompanyID(),"/human/carInformation/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			carInformation.setPhoto(photoPath);
		}
		String hql2="from CarInformation where carID=?";
		CarInformation carinfo=(CarInformation) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{carInformation.getCarID()});
		carinfo.setStaffID(carInformation.getStaffID());
		carinfo.setStaffName(carInformation.getStaffName());
		carinfo.setState2(carInformation.getState2());
		carinfo.setState1(carInformation.getState1());
		carinfo.setState3(carInformation.getState3());
		carinfo.setOrganizationID(organizationID);
		carinfo.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carinfo);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
    	return "success";
    }
    
    //车辆修改信息
    public String updateCarInformation(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		@SuppressWarnings("unused")
		String obj = (String) session.get("session_value");
			 String hql2="from CarInformation where companyID=?  and carID=?";
			  CarInformation carinfo=(CarInformation) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carInformation.getCarID()});
		     parameter = "修改车辆信息(车牌号:"+carinfo.getCarNum()+")";
		     carinfo.setStaffID(carInformation.getStaffID());
				carinfo.setStaffName(carInformation.getStaffName());
				//carinfo.setState3(carInformation.getState3());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carinfo);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
    	return "success";
    }

    //下线车辆信息
	 public String delCarInformation()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
		    @SuppressWarnings("unused")
			String obj = (String) session.get("session_value");
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),carInformation.getCarID()};
		    String hql2="from CarInformation where companyID=?  and carID=?";
		    CarInformation carinfo=(CarInformation) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    carinfo.setState3(carInformation.getState3());
		    CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
			List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(carinfo);
			baseBeansList.add(cLogBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		    return "success";
	    }
	 
	 //调离部门
	 public String Depttransfer(){
		 	Map<String, Object> session = ActionContext.getContext().getSession();
		    @SuppressWarnings("unused")
			String obj = (String) session.get("session_value");
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),carInformation.getCarID()};
		    String hql2="from CarInformation where companyID=?  and carID=?";
		    CarInformation carinfo=(CarInformation) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    carinfo.setOrganizationID(carInformation.getOrganizationID());
		    carinfo.setStaffID(carInformation.getStaffID());
			carinfo.setStaffName(carInformation.getStaffName());
		    CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
			List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(carinfo);
			baseBeansList.add(cLogBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
			return "success";
	 }
	 
	 //车辆查询
	 public String toSearchCar() {
			ActionContext.getContext().getSession()
					.put("tablesearch", carInformation);
			return getListCarByCompanyID();
		}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] {companyID });
		companyname = company.getCompanyName();
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						companyID,
						organizationID});
		organizationname = cOrganization.getOrganizationName();
		if(search !=null && search.equals("search")){
			carInformation= (CarInformation) ActionContext.getContext().getSession().get("tablesearch");
			String hqlcarsearch="from CarInformation cars where 1=1";
			List<Object> parms=new ArrayList<Object>();
			hqlcarsearch+="and cars.companyID=?";
			parms.add(companyID);
			hqlcarsearch+="and cars.organizationID=?";
				parms.add(organizationID);
			if(carInformation.getCarNum()!=null && !"".equals(carInformation.getCarNum().trim())){
				hqlcarsearch+="and cars.carNum like ?";
				parms.add("%" + carInformation.getCarNum().trim()+"%");
			}
			if(carInformation.getCarType()!=null && !"".equals(carInformation.getCarType().trim())){
				hqlcarsearch+="and cars.carType like ?";
				parms.add("%" + carInformation.getCarType().trim()+"%");
			}
			if(carInformation.getStaffName()!=null && !"".equals(carInformation.getStaffName().trim())){
				hqlcarsearch+="and cars.staffName like ?";
				parms.add("%" + carInformation.getStaffName().trim()+"%");
			}
			if(carInformation.getState1()!=null && !"".equals(carInformation.getState1().trim())){
				hqlcarsearch+="and cars.state1 = ?";
				parms.add(carInformation.getState1().trim());
			}
			if(carInformation.getState3()!=null && !"".equals(carInformation.getState3().trim())){
				hqlcarsearch+="and cars.state3 = ?";
				parms.add(carInformation.getState3().trim());
			}
			hqlcarsearch+="order by cars.engineNum desc ";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					hqlcarsearch, parms.toArray());
			return "carInformationlist";
		}
		session.put("tablesearch", carInformation);
		return "carInformationlist";
		
	}
	
	//责任人查询
	public String getStaffList(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyID = account.getCompanyID();
			String organizationIDs=carInformation.getOrganizationID();
			String hql = "select t.staffID,t.staffName,t.staffCode from dt_hr_staff t,dtcos s  " +
			"where t.staffID=s.staffID and s.cosStatus=? and  s.companyID=? and s.organizationID = ?";
			@SuppressWarnings("unchecked")
			List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(hql,
					new Object[]{"50",companyID,organizationIDs});
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("stafflist", list);
			JSONObject jo = JSONObject.fromObject(map);
			result = jo.toString();
			return "success";
		}
	
	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
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

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDeptID() {
		return deptID;
	}

	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public CarInformation getSearchCarInformation() {
		return searchCarInformation;
	}

	public void setSearchCarInformation(CarInformation searchCarInformation) {
		this.searchCarInformation = searchCarInformation;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getTypeJia() {
		return typeJia;
	}

	public void setTypeJia(String typeJia) {
		this.typeJia = typeJia;
	}

	public String getTypeType() {
		return typeType;
	}

	public void setTypeType(String typeType) {
		this.typeType = typeType;
	}
	
}
