package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CertificateaTable;
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
 * 车辆证件
 * */
public class CertificateaTableAction {
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
	private CertificateaTable certificateatable;
	private CarInformation carInformation;
	private String parameter;
	private String type;
	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(CertificateaTable.class);
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
			certificateatable =   (CertificateaTable) session.get("tablesearch");
			if(certificateatable.getCarNum()!=null&&!certificateatable.getCarNum().equals("")){
				dc.add(Restrictions.like("carNum",certificateatable.getCarNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(certificateatable.getCarType()!=null&&!certificateatable.getCarType().equals("")){
				dc.add(Restrictions.like("carType",certificateatable.getCarType().trim(),MatchMode.ANYWHERE));
			}
			
			if(certificateatable.getEngineNum()!=null&&!certificateatable.getEngineNum().equals("")){
				dc.add(Restrictions.like("engineNum",certificateatable.getEngineNum().trim(),MatchMode.ANYWHERE));
			}
			
			if(certificateatable.getCarPeople()!=null&&!certificateatable.getCarPeople().equals("")){
				dc.add(Restrictions.like("carPeople",certificateatable.getCarPeople().trim(),MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	public String getCertificateaTableList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getcertificateatablelist";
	}
	
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", certificateatable);
		return getCertificateaTableList();
	}
	//导出按钮
	@SuppressWarnings("unchecked")
	public String showcateaExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select carNum,cartype,framenumber,enginenum,carPeople, certificateaname," +
				" receivedate, effectivedate, certificateanumber, giveorgan, certificateaisbn, copies," +
				" verifierpeople from dt_car_certificateatable where companyID= ? and organizationID=? and carID is not null";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(CertificateaTable.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆相关证件管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//删除功能
	public String deleteatable(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[]param={account.getCompanyID(),organizationID,certificateatable.getCertifiID()};
		String hql="from CertificateaTable where companyID=? and organizationID=? and certifiID=?";
		CertificateaTable tts=(CertificateaTable) baseBeanService.getBeanByHqlAndParams(hql,param);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆相关子证件信息管理:("+tts.getCertifiID()+")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql2="delete CertificateaTable where companyID=? and organizationID=? and certifiID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql2 }, param);
		return "success";
	}
	//添加保存
	public String savecateatList(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID=? and dt.carID=?";
		Object[] params={account.getCompanyID(),carInformation.getCarID()};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		certificateatable.setCertifiID(serverService.getServerID("CertificateaTable"));
		certificateatable.setCarID(cars.getCarID());
		certificateatable.setCarNum(cars.getCarNum());
		certificateatable.setCarType(cars.getCarType());
		certificateatable.setFrameNumber(cars.getCarFrameNum());
		certificateatable.setEngineNum(cars.getEngineNum());
		certificateatable.setCarPeople(cars.getStaffName());
		certificateatable.setOrganizationID(organizationID);
		certificateatable.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(certificateatable);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	//修改保存
	public String updateCatea(){
		ActionContext actionContext=ActionContext.getContext();
    	Map<String,Object> session=actionContext.getSession();
    	CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CertificateaTable where companyID=? and certifiID=?";
		CertificateaTable ttab=(CertificateaTable) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),certificateatable.getCertifiID()});
		certificateatable.setCarID(ttab.getCarID());
		certificateatable.setCarNum(ttab.getCarNum());
		certificateatable.setFrameNumber(ttab.getFrameNumber());
		certificateatable.setCarType(ttab.getCarType());
		certificateatable.setEngineNum(ttab.getEngineNum());
		certificateatable.setCarPeople(ttab.getCarPeople());
		certificateatable.setOrganizationID(organizationID);
		certificateatable.setCompanyID(account.getCompanyID());
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList =new ArrayList<BaseBean>();
		baseBeansList.add(certificateatable);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}
	//查询按钮
	public String toSearchCar(){
		ActionContext.getContext().getSession().put("tablesearch", certificateatable);
		return getListCarByCompanyID();
	}
	public String  getListCarByCompanyID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		if(search !=null && search.equals("search")){
			 certificateatable=  (CertificateaTable) ActionContext.getContext().getSession().get("tablesearch");
			 StringBuffer suner=new StringBuffer("from CertificateaTable cars where 1=1");
			 List<Object> parms=new ArrayList<Object>();
			 suner.append("and cars.companyID=? ");
			 parms.add(companyID);
			 suner.append("and cars.organizationID=? ");
			 parms.add(organizationID);
			 if(certificateatable.getCarNum() !=null && !"".equals(certificateatable.getCarNum().trim())){
				 suner.append("and cars.carNum like ? ");
				 parms.add("%" + certificateatable.getCarNum().trim()+"%");
			 }
			 if(certificateatable.getCarType()!=null && !"".equals(certificateatable.getCarType().trim())){
				 suner.append("and cars.carType like ? ");
				parms.add("%" + certificateatable.getCarType().trim()+"%");
			 }
			 if(certificateatable.getEngineNum()!=null && !"".equals(certificateatable.getEngineNum().trim())){
				 suner.append("and cars.engineNum like ? ");
				 parms.add("%" + certificateatable.getEngineNum().trim()+"%");
			 }
			 if(certificateatable.getCarPeople()!=null && !"".equals(certificateatable.getCarPeople().trim())){
				 suner.append("and cars.carPeople like ? ");
				 parms.add("%" + certificateatable.getCarPeople().trim()+"%");
			 }
			 pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
						.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
						suner.toString(), parms.toArray());
			 return "getcertificateatablelist";
		 }
		 session.put("tablesearch", certificateatable);
		 return "getcertificateatablelist";
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
	public CertificateaTable getCertificateatable() {
		return certificateatable;
	}
	public void setCertificateatable(CertificateaTable certificateatable) {
		this.certificateatable = certificateatable;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
