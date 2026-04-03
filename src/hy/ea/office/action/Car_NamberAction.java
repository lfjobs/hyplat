package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarNumber;
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
public class Car_NamberAction {
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
	private CarNumber carNumber;
	private CarInformation carInformation;
	private Map<String, CarNumber> carNummap;
	private String parameter;
	private String result;
	private String brandDate;
	private String staffname;
	private String carnum;
	private String fzstaff;
	private String type;

	/**
	 * 得到车牌号列表
	 * 
	 * @return
	 */
	public String getCarNumList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		StringBuffer sbsql = new StringBuffer(); 
		String sql  = "select c.cnkey,o.organizationname,c.staffname,c.brandDate,c.carNum,c.Unit,c.address,c.fzDate,c.fzOrgizationID,c.fzStaffID, " +
		"c.cnID,c.carID,c.companyid,c.organizationid from dt_car_number c left join dtcorganization o on c.organizationid  = o.organizationid where ";
		sbsql.append(sql);
		Object[] params = null;
		if (carInformation != null && carInformation.getCarID() != null) {
			params = new Object[] { account.getCompanyID(), organizationID,
					carInformation.getCarID() };
			sbsql.append(" c.companyid=?");
			sbsql.append(" and c.organizationid=?");
			sbsql.append(" and c.carID = ?");
			

		} else {
			if (type != null && !type.equals("")) {
				if (type.equals("c")) {
					sbsql.append(" c.companyid=?");
					params = new Object[] { account.getCompanyID() };
				} else if (type.equals("g")) {
					String sqlappend2 = " and c.companyid in(select companyid from dtCompany where companyPID = ? or companyID = ?)";
				    sbsql.append(sqlappend2);
					params = new Object[] { account.getCompanyID(),
							account.getCompanyID() };
				} else {
					sbsql.append(" c.companyid=?");
					sbsql.append(" and c.organizationid=?");
					params = new Object[] { account.getCompanyID(),
							organizationID };
				}
			}

		}
		 
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "+sql.substring(sql.indexOf("from")), params);
		
		return "getCarRoadList";
	}
	/**
	 * 按条件查询
	 * */
	 public String toSearchCar() {
			ActionContext.getContext().getSession()
					.put("tablesearch", carNumber);
			return getListCarByCompanyID();
		} 
	 
	public String getListCarByCompanyID() {
		try{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		StringBuffer sbsql = new StringBuffer(); 
		String sql = "select c.cnkey,o.organizationname,c.staffname,c.brandDate,c.carNum,c.Unit,c.address,c.fzDate,c.fzOrgizationID,c.fzStaffID, "
				+ "c.cnID,c.carID,c.companyid,c.organizationid from dt_car_number c left join dtcorganization o on c.organizationid  = o.organizationid  where ";
		List<Object> parms = new ArrayList<Object>();
		sbsql.append(sql);
		
		if(carInformation!=null&&carInformation.getCarID()!=null&&!carInformation.getCarID().equals("")&&!carInformation.getCarID().equals("undefined")){
			sbsql.append(" c.companyid=?");
			sbsql.append(" and c.organizationid=?");
			sbsql.append(" and c.carID = ?");
			parms.add(companyID);
			parms.add(organizationID);
			parms.add(carInformation.getCarID());
		} else {
			if (type != null && !type.equals("")) {
				if (type.equals("c")) {
					sbsql.append(" c.companyid=?");
					parms.add(companyID);
				} else if (type.equals("g")) {
					String sqlappend2 = "  c.companyid in(select companyid from dtCompany where companyPID = ? or companyID = ?)";
				    sbsql.append(sqlappend2);
				    parms.add(companyID);
					parms.add(companyID);
				} else {
					sbsql.append(" c.companyid=?");
					sbsql.append(" and c.organizationid=?");
					parms.add(companyID);
					parms.add(organizationID);
				}
			}

		}
		

		if (search != null && search.equals("search")) {
			carNumber = (CarNumber) ActionContext.getContext().getSession()
					.get("tablesearch");
			
			if (carNumber.getStaffName() != null
					&& !"".equals(carNumber.getStaffName())) {
				sbsql.append(" and c.staffName like ? ");
				parms.add("%" + carNumber.getStaffName() + "%");
			}
			if (carNumber.getFzStaffID() != null
					&& !"".equals(carNumber.getFzStaffID().trim())) {
				sbsql.append(" and c.fzStaffID like ?");
				parms.add("%" + carNumber.getFzStaffID().trim() + "%");
			}
			if (carNumber.getCarNum() != null
					&& !"".equals(carNumber.getCarNum().trim())) {
				sbsql.append(" and c.carNum like ? ");
				parms.add("%" + carNumber.getCarNum().trim() + "%");
			}
		}
		String sqlcount =  "select count(1) "
			+ sbsql.toString().substring(sbsql.toString().indexOf("from"));
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sbsql.toString(),sqlcount, parms.toArray());
		}catch(Exception e){
		 e.printStackTrace();
		}
		return "getCarRoadList";
		
	}
	
	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcelCarnumber() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select  dept.organizationname, dt.staffid,dt.branddate,dt.carnum,dt.unit,dt.address,dt.fzdate,dt.fzorgizationid,dt.fzstaffid from dt_car_number dt left join dtcorganization dept on dept.organizationid=dt.organizationid where dt.companyID = ? and dt.organizationid = ?";
		//String hql = "from CarNumber where companyID = ? and organizationid = ?  ";
		Object[] params = { ((CAccount)session.get("account")).getCompanyID(),organizationID };
		excelStream = excelService.showExcel(CarNumber.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, params));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车牌号管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteCarroad() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String hql = "delete CarNumber where cnID = ?";
		Object[] params = {carNumber.getCnID()};
		beans = new ArrayList<BaseBean>();
		//parameter = "删除车牌号：(车牌号：" + carInformation.getCarNum() + ")";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}

	/**
	 * 保存车牌信息
	 * 
	 * @return
	 */
	public String saveCarNumList() {
		try{
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carNumber.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carNumber.setCompanyID(account.getCompanyID());
		carNumber.setOrganizationID(organizationID);
		carNumber.setCnID(serverService.getServerID("CarNumber"));
		carNumber.setStaffID(cars.getStaffID());
		carNumber.setStaffName(cars.getStaffName());
		carNumber.setCarNum(cars.getCarNum());
		@SuppressWarnings("unused")
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carNumber);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	//修改车牌信息
	public String UpdateCarroadList() {
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql2="from CarNumber where companyID= ? and cnID= ?";
		CarNumber carnum=(CarNumber) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carNumber.getCnID()});
		carnum.setBrandDate(carNumber.getBrandDate());
		carnum.setAddress(carNumber.getAddress());
		carnum.setFzDate(carNumber.getFzDate());
		carnum.setFzStaffID(carNumber.getFzStaffID());
		carnum.setUnit(carNumber.getUnit());
		carnum.setFzOrgizationID(carNumber.getFzOrgizationID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carnum);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//车辆车牌号中选择车辆查询
	private DetachedCriteria getcarList(){
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

	public CarNumber getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(CarNumber carNumber) {
		this.carNumber = carNumber;
	}

	public String getBrandDate() {
		return brandDate;
	}
	public void setBrandDate(String brandDate) {
		this.brandDate = brandDate;
	}
	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}

	public Map<String, CarNumber> getCarNummap() {
		return carNummap;
	}

	public void setCarNummap(Map<String, CarNumber> carNummap) {
		this.carNummap = carNummap;
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getCarnum() {
		return carnum;
	}
	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}
	public String getFzstaff() {
		return fzstaff;
	}
	public void setFzstaff(String fzstaff) {
		this.fzstaff = fzstaff;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
