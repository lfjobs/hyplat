package hy.ea.driving.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.driving.DtCarCarwindingdetection;
import hy.ea.bo.driving.DtCarCylinderInformation;
import hy.ea.service.CLogBookService;
import hy.ea.util.DateJsonValueProcessor;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;
public class CarProductregisterAction {
	private static final Logger logger = LoggerFactory.getLogger(CarProductregisterAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private DtCarCylinderInformation carCylinderInformation;
	private List<BaseBean> beans;
	private String ccompanyIDlinder; // 往来单位ID
	private String concertpeople;// 往来个人ID
	private String search;
	private DtCarCarwindingdetection carwindingdetection;
	private String carCylinderId;
	private String parameter;
	private String edit;
	private String bottletype;//气瓶检测类型
	private String carlinderID;
	private PageForm pageForm;
	private Object obj;
	private String result;
	private int pageNumber;
	private String liscennum; //车牌号
	private String showtype;  //用于区分显示返回的list    （registration 登记证打印列表 ）
	private Map<String,DtCarCylinderInformation> carcymap;

	/**
	 * 模糊检索
	 * 
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", carCylinderInformation);
		return getListProductregister();
	}

	// 默认加载
	public String getListProductregister() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from DtCarCylinderInformation where companyid = ? and organizationid = ?";
		String organizationID = (String) session.get("organizationID");
		List<Object> parms = new ArrayList<Object>();
		parms.add(account.getCompanyID());
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			carCylinderInformation = (DtCarCylinderInformation) session
					.get("tablesearch");
			if (null != carCylinderInformation.getCylinderNum()
					&& !"".equals(carCylinderInformation.getCylinderNum())) {
				hql += "and cylinderNum = ?";
				parms.add(carCylinderInformation.getCylinderNum());
			}
			if (null != carCylinderInformation.getCertificateNumber()
					&& !"".equals(carCylinderInformation.getCertificateNumber())) {
				hql += "and certificateNumber = ?";
				parms.add(carCylinderInformation.getCertificateNumber());
			}

		}
		
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), hql, parms.toArray());
		return "cylinderlist";
	}

	/*
	 * //删除车辆气瓶信息 public String del (){ Map<String, Object> session =
	 * ActionContext.getContext().getSession(); CAccount account = (CAccount)
	 * session.get("account"); String organizationID = (String)
	 * session.get("organizationID"); String hql =
	 * "delete from DtCarCylinderInformation where companyID = ?"; Object[]
	 * params = { account.getCompanyID()}; CLogBook logBook =
	 * logBookService.saveCLogBook(organizationID,
	 * "删除车辆气瓶信息(气瓶ID：+ carCylinderInformation.getCarCylinderId()" , account);
	 * beans = new ArrayList<BaseBean>(); beans.add(logBook);
	 * baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[] {
	 * hql }, params); return "success";
	 * 
	 * }
	 */

	/* 保存车辆气瓶产品登记信息 */
	public String saveCarlinderInformation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		if (null == carlinderID || "".equals(carlinderID)) {
				parameter = "气瓶基础资料录入";
				if(carcymap != null){
					for (DtCarCylinderInformation carlinder : carcymap.values()) {
						carlinder.setCarCylinderId(serverService.getServerID("DtCarCylinderInformation"));
						carlinder.setCompanyid(account.getCompanyID());
						carlinder.setOrganizationid(organizationID);
						carlinder.setCcompanyID(ccompanyIDlinder);
						carlinder.setStaffID(concertpeople);
						carlinder.setLicensenumber(carCylinderInformation.getLicensenumber());
						carlinder.setRegistrationcode(carCylinderInformation.getRegistrationcode());
						carlinder.setCertificateNumber(carCylinderInformation.getCertificateNumber());
						carlinder.setArea(carCylinderInformation.getArea());
						carlinder.setLicenseplatetype(carCylinderInformation.getLicenseplatetype());
						carlinder.setChassisnumber(carCylinderInformation.getChassisnumber());
						carlinder.setEnginenumber(carCylinderInformation.getEnginenumber());
						carlinder.setInstallationunit(carCylinderInformation.getInstallationunit());
						carlinder.setInstallationnumber(carCylinderInformation.getInstallationnumber());
						carlinder.setCertifyingauthority(carCylinderInformation.getCertifyingauthority());
						carlinder.setInstallationdate(carCylinderInformation.getInstallationdate());
						carlinder.setIssuedate(carCylinderInformation.getIssuedate());
						carlinder.setStatus("01");
						beans.add(carlinder);

					}
				}
				CLogBook logBook = logBookService
						.saveCLogBook(null, parameter, account);
				beans.add(logBook);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}else{
			String hql = "from DtCarCylinderInformation where companyid= ? and organizationid = ? and  licensenumber= ?";
			List<BaseBean> beans = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),organizationID,liscennum});
			if(beans.size()>0){
				List<BaseBean> beanss = new ArrayList<BaseBean>();
					for(int i=0;i<beans.size();i++){
							DtCarCylinderInformation li = (DtCarCylinderInformation)beans.get(i);
							li.setLicensenumber(carCylinderInformation.getLicensenumber());
							li.setRegistrationcode(carCylinderInformation.getRegistrationcode());
							li.setCertificateNumber(carCylinderInformation.getCertificateNumber());
							li.setArea(carCylinderInformation.getArea());
							li.setLicenseplatetype(carCylinderInformation.getLicenseplatetype());
							li.setChassisnumber(carCylinderInformation.getChassisnumber());
							li.setEnginenumber(carCylinderInformation.getEnginenumber());
							li.setInstallationunit(carCylinderInformation.getInstallationunit());
							li.setInstallationnumber(carCylinderInformation.getInstallationnumber());
							li.setCertifyingauthority(carCylinderInformation.getCertifyingauthority());
							li.setInstallationdate(carCylinderInformation.getInstallationdate());
							li.setIssuedate(carCylinderInformation.getIssuedate());
							li.setCcompanyID(ccompanyIDlinder);li.setStaffID(concertpeople);
							beanss.add(li);
							
					}
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beanss, null, null);
					
					//对当前气瓶信息数据记录条修改
					String hql2 = "from DtCarCylinderInformation where companyid= ? and organizationid = ? and  carCylinderId =?";
					DtCarCylinderInformation carlinders= (DtCarCylinderInformation)baseBeanService.getBeanByHqlAndParams(hql2,new Object[]{account.getCompanyID(),organizationID,carlinderID.trim()});
					List<BaseBean> carlinderbean = new ArrayList<BaseBean>();
					parameter = "气瓶基础资料修改:编号" +carlinderID + ")"; 
					carlinders.setCylinderNum(carCylinderInformation.getCylinderNum());
					carlinders.setCylinderType(carCylinderInformation.getCylinderType());
					carlinders.setDesignThickness(carCylinderInformation.getDesignThickness());   
					carlinders.setCylinderModel(carCylinderInformation.getCylinderModel());   
					carlinders.setMadecode(carCylinderInformation.getMadecode());   
					carlinders.setManufacturingcountry(carCylinderInformation.getManufacturingcountry());   
					carlinders.setResin(carCylinderInformation.getResin());   
					carlinders.setTexture(carCylinderInformation.getTexture());  
					carlinders.setManufactureCompany(carCylinderInformation.getManufactureCompany());  
					carlinders.setHydraulicTestPressure(carCylinderInformation.getHydraulicTestPressure());  
					carlinders.setNominalworkpressure(carCylinderInformation.getNominalworkpressure());  
					carlinders.setFiber(carCylinderInformation.getFiber());  
					carlinders.setWeight(carCylinderInformation.getWeight()); 
					carlinders.setLeavefactorydate(carCylinderInformation.getLeavefactorydate()); 
					carlinders.setVolume(carCylinderInformation.getVolume()); 
					carlinderbean.add(carlinders);
					CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
					beans.add(logBook);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(carlinderbean, null, null);
			}
		}
		return "success";
	}

	// 查看
	public String detailinformation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = " select car.cylinderNum,cylinderType,car.designThickness,car.cylinderModel,car.manufactureCompany," +
				"car.leavefactorydate,car.volume,car.weight,car.nominalworkpressure,car.hydraulicTestPressure,"+
"car.texture,car.fiber,car.resin,car.manufacturingcountry,car.madecode,car.licensenumber,car.registrationcode," +
"car.certificateNumber,car.area,car.licenseplatetype,"+
"car.chassisnumber,car.enginenumber,car.installationunit,car.installationnumber,car.certifyingauthority,car.installationdate," +
"car.issuedate,con.companyName,con.companyTel,con.cresponsible,"+
"con.responsibleTel,con.industryType,con.dealIn,con.companyAddr,dts.staffName,dts.reference,dts.staffIdentityCard,"+
"dts.referenceCode,dts.referenceOrganization,rel.relation,dts.staffAddress,car.carCylinderId,dts.staffID from dt_carcylinderinformation car " +
"left join dtContactRelation rel on rel.staffid = car.staffid " +
"left join dtContactCompany con on con.ccompanyid = car.ccompanyid " +
"left join dt_hr_staff dts on dts.staffid = car.staffid " +
"where car.carcylinderid = ? and car.companyid = ? and car.organizationid = ?";
		Object[] param = { carCylinderId, account.getCompanyID(),
				organizationID };
		@SuppressWarnings("rawtypes")
		List reList3 = baseBeanService.getListBeanBySqlAndParams(hql, param);
		if (reList3 != null) {
			obj = reList3.get(0);
		}
		if(edit.equals("edit")){
			return "updatecheck";   //修改气瓶基本信息
			
		}if("carseedetial".equals(edit)){
			String seedetail = "from DtCarCylinderInformation where  companyid = ? and organizationid = ?and licensenumber=?";
			@SuppressWarnings("rawtypes")
			List list = baseBeanService.getListBeanByHqlAndParams(seedetail, new Object[]{account.getCompanyID(),organizationID,liscennum});
			session.put("carlinderlist",list);
			return "seedatial";
		}if("cylinderdetial".equals(edit)){
			String seedetail = "from DtCarCylinderInformation where  companyid = ? and organizationid = ?and licensenumber=?";
			@SuppressWarnings("rawtypes")
			List list2 = baseBeanService.getListBeanByHqlAndParams(seedetail, new Object[]{account.getCompanyID(),organizationID,liscennum});
			session.put("registrationlist",list2);
			return "printcylinder";
		}if("updateregistration".equals(edit)){
			
			return "updateregistration"; //更换登记证
		}if("printsheetlist".equals(edit)){
			String sql = "select cc.staffname st1,dd.staffname at2, aa.licensenumber,bb.checkoutnum,aa.cylindernum," +
					"bb.checkdate,bb.nextcheckdate  from dt_carcylinderinformation aa inner join dt_car_dispitchlist bb on aa.carcylinderid = bb.cylindernum" +
					" left join dt_hr_staff cc on cc.staffid = bb.staffid " +
					"left join dt_hr_staff dd on dd.staffid = bb.staffids " +
					"where aa.carcylinderid = ? and aa.companyid = ? and aa.organizationid=? ";
			obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{carCylinderId,account.getCompanyID(),organizationID});
			return "printsheetlist"; //跳转到打印派工单页面
			
		}
		else{
			return "editcheck";     //查看气瓶详细信息
		}
	}

	
	//查询车辆记录表所有信息
	public String getAllListProductregister(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		String sql = "select cylinder.carCylinderId,cylinder.cylinderNum,staff.staffname,cylinder.cylinderType,cylinder.cylinderModel," +
				"cylinder.licensenumber,cylinder.certificatenumber,cylinder.manufactureCompany" +
				",cylinder.leavefactorydate,cylinder.volume,cylinder.weight from DT_CarCylinderInformation cylinder " +
				"left join dt_hr_staff staff on staff.staffid = cylinder.staffid where cylinder.status='01'";
		String sqlcount = "select count(*) from DT_CarCylinderInformation cylinder " +
				"left join dt_hr_staff staff on staff.staffid = cylinder.staffid where cylinder.status='01'";
		List<Object> parms = new ArrayList<Object>();
		if (search != null && search.equals("search")) {
			carCylinderInformation = (DtCarCylinderInformation) session
					.get("tablesearch");
			if (null != carCylinderInformation.getCylinderNum()
					&& !"".equals(carCylinderInformation.getCylinderNum())) {
				sql += "where cylinderNum = ?";
				parms.add(carCylinderInformation.getCylinderNum());
			}
			if (null != carCylinderInformation.getCertificateNumber()
					&& !"".equals(carCylinderInformation.getCertificateNumber())) {
				sql += "and certificateNumber = ?";
				parms.add(carCylinderInformation.getCertificateNumber());
			}

		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, sqlcount,  parms.toArray());
		if("recordlist".equals(showtype)){
			return "allRecordlist";
		}
		
		return "allRecordlist";
		
	}
	//查询气瓶基本资料 便于检测
	public String detectionInformation(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String sql = "select aa.*,hs.staffname,pit.* from dt_carcylinderinformation aa" +
				" left join dt_hr_staff hs on aa.staffid = hs.staffid " +
				"left join dt_car_dispitchlist pit on aa.carcylinderid = pit.cylindernum where aa.companyid = ? and aa.organizationid = ? and aa.carcylinderid = ?";
			obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{account.getCompanyID(),organizationID ,carlinderID});	
			if("cylinders".equals(bottletype)){
				return "cylinderpage";
				}if("print".equals(bottletype)){
					
					return "registrationprint";
				}else{
					
					return "Windingpage";
				}
		
	}
	//车用登记证号列表
	public String getlistregistrationlist(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String sqlcount = "";
		if("registration".equals(showtype)){
			String sqlprint = " select tt.*,hr.staffname,hr.reference from  dt_carcylinderinformation tt  " +
					"left join dt_hr_staff hr on tt.staffid = hr.staffid  where tt.carcylinderid in " +
					"(select max(ss.carcylinderid)  from dt_carcylinderinformation ss group by ss.licensenumber) and tt.companyid = ? and tt.organizationid=?";
			sqlcount+="select count(distinct car.licensenumber) from DT_CarCylinderInformation car " +
					"left join dt_hr_staff staff on staff.staffid = car.staffid where car.companyid = ?  and car.organizationid=?";
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 5 : pageNumber), sqlprint, sqlcount, new Object[]{account.getCompanyID(),organizationID});
			return "registrationlist";   //使用登记证打印列表
		}
		String sql = "select car.carCylinderId,staff.staffName,car.licensenumber,car.certificateNumber,staff.reference," +
				"car.installationnumber,car.enginenumber,car.chassisnumber,car.cylinderNum,car.cylinderType,car.leavefactorydate," +
				"car.installationdate from DT_CarCylinderInformation car " +
				"left join dt_hr_staff staff on staff.staffid = car.staffid where car.companyid = ?  and car.organizationid=? and car.status='00'";
		sqlcount+="select count(*) from DT_CarCylinderInformation car " +
				"left join dt_hr_staff staff on staff.staffid = car.staffid where car.companyid = ?  and car.organizationid=? and car.status='00'";
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, sqlcount, new Object[]{account.getCompanyID(),organizationID});
		if("panfeitongzhi".equals(showtype)){
			return "panfeitongzhilist"; //判废通知列表界面
		}else{
			
			return "metereportlist"; //
		}
	}
	//更换登记证保存
	public void updateregistration(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = "from DtCarCylinderInformation where companyid=? and organizationid=? and licensenumber= ?";
		try {
			List<BaseBean> beans = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),organizationID,liscennum});
			if(beans.size()>0){
				List<BaseBean> beanss = new ArrayList<BaseBean>();
				for(int i=0;i<beans.size();i++){
					DtCarCylinderInformation li = (DtCarCylinderInformation)beans.get(i);
					li.setCertificateNumber(edit);
					beanss.add(li);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
						}	
					}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		}
		
	public String  swdad(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出  
		String sql = "";
		if(carlinderID!=null){
			String hql = "from DtCarCylinderInformation where companyid=? and organizationid=? and carCylinderId= ? ";
			DtCarCylinderInformation beans = (DtCarCylinderInformation) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),organizationID,carlinderID});
			try {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("datasizelist",beans );
				JSONObject json=JSONObject.fromObject(map,jsonConfig);
				result=json.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}
			return "success";
		}
		if( !"".equals(edit.trim())){
			 sql+= "select * from dt_carcylinderinformation where  companyid = ? and organizationid = ? and status='01' and cylinderNum like ? ";
			 Object[] param ={account.getCompanyID(),organizationID,"%"+edit+"%"};
			List<String> returndatalist = baseBeanService.getListBeanBySqlAndParams(sql, param);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("returndatalist",returndatalist );
			JSONObject json=JSONObject.fromObject(map,jsonConfig);
			result=json.toString();
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

	public DtCarCylinderInformation getCarCylinderInformation() {
		return carCylinderInformation;
	}

	public void setCarCylinderInformation(
			DtCarCylinderInformation carCylinderInformation) {
		this.carCylinderInformation = carCylinderInformation;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getCcompanyIDlinder() {
		return ccompanyIDlinder;
	}

	public void setCcompanyIDlinder(String ccompanyIDlinder) {
		this.ccompanyIDlinder = ccompanyIDlinder;
	}

	public String getConcertpeople() {
		return concertpeople;
	}

	public void setConcertpeople(String concertpeople) {
		this.concertpeople = concertpeople;
	}

	public String getCarCylinderId() {
		return carCylinderId;
	}

	public void setCarCylinderId(String carCylinderId) {
		this.carCylinderId = carCylinderId;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getCarlinderID() {
		return carlinderID;
	}

	public void setCarlinderID(String carlinderID) {
		this.carlinderID = carlinderID;
	}

	public Map<String, DtCarCylinderInformation> getCarcymap() {
		return carcymap;
	}

	public void setCarcymap(Map<String, DtCarCylinderInformation> carcymap) {
		this.carcymap = carcymap;
	}

	public String getBottletype() {
		return bottletype;
	}

	public void setBottletype(String bottletype) {
		this.bottletype = bottletype;
	}

	public String getShowtype() {
		return showtype;
	}

	public void setShowtype(String showtype) {
		this.showtype = showtype;
	}

	public String getLiscennum() {
		return liscennum;
	}

	public void setLiscennum(String liscennum) {
		this.liscennum = liscennum;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
