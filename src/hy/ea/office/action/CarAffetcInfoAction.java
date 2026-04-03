package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarAssetcInformation;
import hy.ea.bo.office.CarInformation;
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
 

/*
 * 车辆资产信息
 * */
@Controller
@Scope("prototype")
public class CarAffetcInfoAction {
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
	private CarAssetcInformation carassetcinformation;
	private CarInformation carInformation;
	private Map<String, CarAssetcInformation> carassetcinformationmap;
	private String result;
	private String parameter;
	private String type;
	
	/**
	 * 按条件查询
	 * 
	 * @return
	 */
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", carassetcinformation);
		return getListCarByCompanyID();
	}
	public String getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			carassetcinformation=  (CarAssetcInformation) ActionContext.getContext().getSession().get("tablesearch");
			 StringBuffer suner=new StringBuffer("from CarAssetcInformation cars where 1=1");
			 List<Object> parms=new ArrayList<Object>();
			 suner.append("and cars.companyID=? ");
			 parms.add(companyID);
			 suner.append("and cars.organizationID=? ");
			 parms.add(organizationID);
			 if(carassetcinformation.getPropertyname() !=null && !"".equals(carassetcinformation.getPropertyname())){
				 suner.append("and cars.propertyname like ? ");
				 parms.add("%" + carassetcinformation.getPropertyname()+"%");
			 }
			 if(carassetcinformation.getCertificatenum()!=null && !"".equals(carassetcinformation.getCertificatenum().trim())){
				 suner.append("and cars.certificatenum like ? ");
				parms.add("%" + carassetcinformation.getCertificatenum().trim()+"%");
			 }
			 if(carassetcinformation.getTrackNumber()!=null && !"".equals(carassetcinformation.getTrackNumber().trim())){
				 suner.append("and cars.trackNumber like ? ");
				 parms.add("%" + carassetcinformation.getTrackNumber().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						suner.toString(), parms.toArray());
			 return "getCarassetcinformationList";
		 }
		 session.put("tablesearch", carassetcinformation);
		 return "getCarassetcinformationList";
	}
	/**
	 * 得到车辆资产列表
	 * */
	public String getCaraffetcinformationList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getCarassetcinformationList";
	}
	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarAssetcInformation.class);
	
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
			carassetcinformation =  (CarAssetcInformation) session.get("tablesearch");
			if(carassetcinformation.getPropertyname()!=null&&!carassetcinformation.getPropertyname().equals("")){
				dc.add(Restrictions.like("propertyname",carassetcinformation.getPropertyname().trim(),MatchMode.ANYWHERE));
			}
			
			if(carassetcinformation.getCertificatenum()!=null&&!carassetcinformation.getCertificatenum().equals("")){
				dc.add(Restrictions.like("certificatenum",carassetcinformation.getCertificatenum().trim(),MatchMode.ANYWHERE));
			}
			
			if(carassetcinformation.getTrackNumber()!=null&&!carassetcinformation.getTrackNumber().equals("")){
				dc.add(Restrictions.like("trackNumber",carassetcinformation.getTrackNumber().trim(),MatchMode.ANYWHERE));
			}
		}
		
		return dc;
	}
	
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", carassetcinformation);
		return getCaraffetcinformationList();
	}
	/**
	 * 导出
	 * 
	 * @return
	 */
	public String showCarsafeinformationExcel() {
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
	
		String hql = "from CarAssetcInformation  where companyID = ? and organizationid = ?  ";
				
		Object[] params = { ((CAccount)session.get("account")).getCompanyID(), organizationID};
		excelStream = excelService.showExcel(CarAssetcInformation.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, params));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆资产信息", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteCarAssetcinformation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String hql = "delete CarAssetcInformation where AssetcID = ?";
		Object[] params = {carassetcinformation.getAssetcID()};
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}
	/**
	 * 修改车辆资产信息
	 * */
	public String UpdateCarAssetcinformationList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String hql2="from CarAssetcInformation where companyID= ? and assetcID = ?";
		CarAssetcInformation carassetc=(CarAssetcInformation) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carassetcinformation.getAssetcID()});
		carassetc.setPropertyname(carassetcinformation.getPropertyname());
		carassetc.setConnectTime(carassetcinformation.getConnectTime());
		carassetc.setStandard(carassetcinformation.getStandard());
		carassetc.setPrice(carassetcinformation.getPrice());
		carassetc.setCertificatenum(carassetcinformation.getCertificatenum());
		carassetc.setTotleCount(carassetcinformation.getTotleCount());
		carassetc.setTrackNumber(carassetcinformation.getTrackNumber());
		carassetc.setConnectPeople(carassetcinformation.getConnectPeople());
		carassetc.setCount(carassetcinformation.getCount());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carassetc);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	/**
	 * 保存车辆资产信息
	 * 
	 * @return
	 */
	public String saveCarAssetcinformationList() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		carassetcinformation.setAssetcID(serverService.getServerID("CarAssetcInformation"));
		carassetcinformation.setCarID(cars.getCarID());
		carassetcinformation.setCertificatenum(carassetcinformation.getCertificatenum());
		carassetcinformation.setCompanyID(account.getCompanyID());
		carassetcinformation.setOrganizationID(organizationID);
		carassetcinformation.setConnectPeople(carassetcinformation.getConnectPeople());
		carassetcinformation.setCount(carassetcinformation.getCount());
		carassetcinformation.setTotleCount(carassetcinformation.getTotleCount());
		carassetcinformation.setTrackNumber(carassetcinformation.getTrackNumber());
		carassetcinformation.setPrice(carassetcinformation.getPrice());
		carassetcinformation.setPropertyname(carassetcinformation.getPropertyname());
		carassetcinformation.setConnectTime(carassetcinformation.getConnectTime());
		carassetcinformation.setStandard(carassetcinformation.getStandard());
			List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(carassetcinformation);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null,null);
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
	
	public CarAssetcInformation getCarassetcinformation() {
		return carassetcinformation;
	}
	public void setCarassetcinformation(CarAssetcInformation carassetcinformation) {
		this.carassetcinformation = carassetcinformation;
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
	public Map<String, CarAssetcInformation> getCarassetcinformationmap() {
		return carassetcinformationmap;
	}
	public void setCarassetcinformationmap(
			Map<String, CarAssetcInformation> carassetcinformationmap) {
		this.carassetcinformationmap = carassetcinformationmap;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
