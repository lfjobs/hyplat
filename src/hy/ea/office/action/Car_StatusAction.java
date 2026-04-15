package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarNumber;
import hy.ea.bo.office.CarStatus;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class Car_StatusAction {
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
	private CarStatus carStatus;
	private CarInformation carInformation;
	private Map<String, CarNumber> carNummap;
	private String parameter;
	private String result;
	private String brandDate;
	private String staffname;
	private String carnum;
	private String fzstaff;
	private String type;
	
	
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CarStatus.class);
		dc.addOrder(Order.desc("operateDate"));
	
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
			carStatus =  (CarStatus) session.get("tablesearch");
			if(carStatus.getCarNum()!=null&&!carStatus.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",carStatus.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
//			if(carStatus.getCarType()!=null&&!carStatus.getCarType().equals("")){
//				dc.add(Restrictions.like("carType",carStatus.getCarType().trim(),MatchMode.ANYWHERE));
//			}
//			
//			if(carStatus.getEngineNum()!=null&&!carStatus.getEngineNum().equals("")){
//				dc.add(Restrictions.like("engineNum",carStatus.getEngineNum().trim(),MatchMode.ANYWHERE));
//			}
//			
//			if(carStatus.getViolatePeople()!=null&&!carStatus.getViolatePeople().equals("")){
//				dc.add(Restrictions.like("violatePeople",carStatus.getViolatePeople().trim(),MatchMode.ANYWHERE));
//			}
		}
		return dc;
	}
	/**
	 * 得到车辆状态列表
	 * 
	 * @return
	 */
	public String getCarStatusList() {

		 
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarStatusList";
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carStatus);
		return getCarStatusList();
	}

	 

	
	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcelCarstatus() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		
		String hql = "from CarStatus where carID = ?";
		excelStream = excelService.showExcel(CarStatus.columnHeadings(),baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{carStatus.getCarID()}));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆状态管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteCarstatus() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String hql = "delete CarStatus where stID = ?";
		Object[] params = {carStatus.getStID()};
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}

	/**
	 * 保存车辆状态信息
	 * 
	 * @return
	 */
	public String saveCarStatusList() {
		try {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String hql = "from CarInformation dt where dt.carID=?";
			CarInformation cars = (CarInformation) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { carInformation
							.getCarID() });
			
			cars.setState3(carStatus.getStatus());
			baseBeanService.update(cars);
			
			carStatus.setStID(serverService.getServerID("stID"));
			carStatus.setCompanyID(account.getCompanyID());
			carStatus.setOrganizationID(organizationID);
			carStatus.setStaffID(account.getStaffID());
			carStatus.setStaffName(cars.getStaffName());
			carStatus.setCarNum(cars.getCarNum());
			carStatus.setCarID(cars.getCarID());
			carStatus.setOperateDate(new Date());
			
			carStatus.setStatusname(carStatus.getStatusname(carStatus.getStatus()));

			

			String hqlstaff = "from Staff where staffID = ?";
			String hqlorg = "from COrganization where organizationID = ?";
			String hqlcom = "from Company where companyID = ?";

			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					hqlstaff, new Object[] { account.getStaffID() });
			COrganization org = (COrganization) baseBeanService
					.getBeanByHqlAndParams(hqlorg, new Object[] {organizationID});
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hqlcom, new Object[] { account.getCompanyID() });
			carStatus.setStaffName(staff.getStaffName());
			carStatus.setStaffCode(staff.getStaffCode());
			carStatus.setOrganizationName(org.getOrganizationName());
			carStatus.setCompanyName(company.getCompanyName());
			List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(carStatus);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
					null, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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



	public CarStatus getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(CarStatus carStatus) {
		this.carStatus = carStatus;
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
