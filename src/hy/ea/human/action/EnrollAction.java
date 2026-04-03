package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.driving.DtDrivingAllInformation;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.DtDrivingPrincipalType;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
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
public class EnrollAction { 
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private String result;
	// 接收photo文件
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private List<Integer> addormod;  //判断添加或修改标志
	private String showType;//add 添加 edit 修改 
	private int pageNumber;//用于选择人员信息的上下页
	private String sub;//代替token 手动做防止重复提交   存储生成的随机数
	private PageForm pageForm;
	private Staff cstaff;
	private ContactRelation contactrelation;
	private List<CCode> connectionlist;
	private List<BaseBean>  beanList;
	private List<BaseBean>  testList;
	private List<BaseBean>  coachList;
	private String typeType;
	private DtDrivingAllInformation  dtDrivingAllInformation;
	private DtDrivingAllInformation  RegistrationCompany;//报名单位
	
	private Map<String, DtDrivingAllInformation>  studentRegInformap;
	/**
	 * code 收费标准
	 */
	private List<CCode> standardlist;
	/**
	 * code 申领类别
	 */
	private List<CCode> applylist;
	/**
	 * code 准驾车型代号
	 */
	private List<CCode> quasilist;

	/**
	 * 获取人事菜单 
	 * @return list
	 */
	public String getHumanResource() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		//加载默认民族
		List<CCode> codeNationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100331mk6yn5b5f60000000006");
		//加载默认籍贯
		List<CCode> codeNativePlaceList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode2010053143wpua87db0000000008");
		ActionContext.getContext().put("nations", codeNationList);
		ActionContext.getContext().put("nativeplaces", codeNativePlaceList);
		if(!showType.equals("add")){
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{cstaff.getStaffID()});
		}
		return "list";
	}
	
	/**
	 * 选择社会人力人员
	 * 
	 * @return
	 */	
	public String getpeople(){
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
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getpeopleList());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	//社会人力页面查询
	public DetachedCriteria getpeopleList(){
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			String groupCompanySn = ActionContext.getContext().getSession().get("groupCompanySn").toString();
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
			DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
			dc.add(Restrictions.eq("groupCompanySn", groupCompanySn));
			dc.add(Restrictions.eq("staffStatus", "00"));
			if(null !=parameter && !"".equals(parameter)){
				dc.add(Restrictions.like("staffName", parameter,MatchMode.ANYWHERE));
			}
			if(null != typeType && !"".equals(typeType)){
				dc.add(Restrictions.like("staffIdentityCard", typeType,MatchMode.ANYWHERE));
			}
			dc.addOrder(Order.desc("verifyTime"));
			connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20110224xpd2t2jvda0000000002");
			return dc;
	}
	/**
	 * 保存修改信息
	 */
	public String savepeople(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String groupCompanySn = ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
		if (groupCompanySn == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql="from Staff dt where staffID = ? and groupCompanySn = ? ";
		Object[] params = { cstaff.getStaffID(), groupCompanySn };
		Staff staffs= (Staff) baseBeanService.getBeanByHqlAndParams(hql,params);
		contactrelation = new ContactRelation();
		contactrelation.setRelationID(serverService.getServerID("ContactRelation"));
		contactrelation.setCompanyID(account.getCompanyID());
		contactrelation.setStaffID(staffs.getStaffID());
		contactrelation.setRelation("学员");
		session.put("contactrelation", contactrelation);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(contactrelation);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		showType="edit";
		return "success";
	}
	/**
	 * 学员报名-公司基本信息
	 * @return
	 */
	public String  getStudentCompanyList(){
		getStudentRegInforList(dtDrivingAllInformation.getDataTitle());
		
		return "listCompany";
	}
	
	/**
	 * 学员报名-收费信息
	 * @param dataTitle
	 */
	public String getStudentShargeList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		standardlist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20130701h5z2i27jpg0000000004");
		getStudentRegInforList(dtDrivingAllInformation.getDataTitle());
		return "listSharge";
	}
	/**
	 * 学员报名-证件信息
	 * @param dataTitle
	 */
	public String getStudentPapersList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		applylist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20130312jfnwd72n7a0000000294");
		quasilist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20130312jfnwd72n7a0000000295");
		getStudentRegInforList(dtDrivingAllInformation.getDataTitle());
		return "listPapers";
	}
	/**
	 * 学员报名-接送信息
	 * @param dataTitle
	 */
	public String getStudentShuttleList(){
		getStudentRegInforList(dtDrivingAllInformation.getDataTitle());
		return "listShuttle";
	}
	/**
	 * 学员报名-分车管理
	 * @param dataTitle
	 */
	public String getFenCheShuttleList(){
		getStudentRegInforList(dtDrivingAllInformation.getDataTitle());
		return "listShuttleFenChe";
	}
	/**
	 * 学员报名-培训计时
	 * @param dataTitle
	 */
	public String getStudentTimingList(){
		getStudentRegInforList(dtDrivingAllInformation.getDataTitle());
		return "listTiming";
	}
	/**
	 * 学员报名-教练信息
	 * @param dataTitle
	 */
	public String getStudentCoachList(){
		//此方法为 教务DrivingAction  chain 方式转发
		return "listCoach";
	}
	/**
	 * 学员报名-培训信息
	 * @param dataTitle cstaff20110310VG7354ABPT0000000830
	 */
	public String getStudentTrainList(){
		
		String hql="select  new  DtDrivingOperationPeople(dop.drivingprincipalid as drivingprincipalid,dop.operationstatus as operationname," +
				"to_char(max(dop.operationdate),'yyyy-MM-dd') as operatinid,to_char(min(dop.operationdate),'yyyy-MM-dd') as notes) " +
				" from DtDrivingOperationPeople dop " +
				" where exists (select 1 from DtDrivingPrincipal dp where dp.drivingprincipalid=dop.drivingprincipalid and dp.studentid=?) " +
				" group by dop.drivingprincipalid,dop.operationstatus";
		beanList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{dtDrivingAllInformation.getStaffID()});
		return "listTrain";
	}
	/**
	 * 学员报名-考试信息
	 * @param dataTitle
	 */
	public String getStudentExamList(){
		String hql=" from DtDrivingTest dt where exists (select 1 from DtDrivingPrincipal dp where dp.drivingprincipalid=dt.drivingprincipalid and dp.studentid=?) ";
		beanList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{dtDrivingAllInformation.getStaffID()});
		return "listExam";
	}
	/**
	 * 学员报名-归档信息
	 * @param dataTitle    ?extensionStaffCoach=extensionStaffCoach&catemodule=global
	 */
	public String getStudentArchiveList(){
		//此方法为 教务ArchiveManageAction  chain 方式转发
		return "listArchive";
	}
	/**
	 * 学员报名-收集信息
	 * @param dataTitle    ?extensionStaffCoach=extensionStaffCoach&catemodule=global
	 */
	@SuppressWarnings("unchecked")
	public String getStudentCollectList(){
		String sql="select dp.resgistrationmedicaldate,aa.barcode,aa.chipid,dp.istrues,aa.startvalidity,aa.endvalidity from dt_driving_principal dp,dt_archives_archives aa where aa.staffid=dp.studentid and dp.studentid=?";
		beanList=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{dtDrivingAllInformation.getStaffID()});
		return "listCollect";
	}
	/**
	 * 查询操作
	 * @param dataTitle
	 */
	public void getStudentRegInforList(String dataTitle){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String ohql = "from Company where companyID=? and companyStatus = '00' ";
		Company  company=(Company) baseBeanService.getBeanByHqlAndParams(ohql, new Object[]{account.getCompanyID()});
		session.put("companyName", company.getCompanyName());
		
		DetachedCriteria dc = DetachedCriteria.forClass(DtDrivingAllInformation.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("staffID", dtDrivingAllInformation.getStaffID()));
		dc.add(Restrictions.eq("dataTitle", dataTitle));
		if("08".equals(dataTitle)){
			dc.add(Restrictions.eq("subjectStatus", dtDrivingAllInformation.getSubjectStatus()));
		}
		beanList=baseBeanService.getListByDC(dc);
	} 
	/**
	 * 学员信息保存
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveStudentRegInfors(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean>   beans=new ArrayList<BaseBean>();
		if(studentRegInformap!=null&&studentRegInformap.size()>0){
			for (DtDrivingAllInformation drivingAllInfor : studentRegInformap.values()) {
				if(drivingAllInfor.getDrivingAllInformationID()==null||"".equals(drivingAllInfor.getDrivingAllInformationID())){
					drivingAllInfor.setDrivingAllInformationID(serverService.getServerID("DtDrivingAllInfor"));
				}
				drivingAllInfor.setCompanyID(account.getCompanyID());
				drivingAllInfor.setAcceptPeopleID(account.getStaffID());
				drivingAllInfor.setAcceptPeople(account.getAccountName());
				if("08".equals(drivingAllInfor.getDataTitle())&&!"".equals(drivingAllInfor.getTimingStartTime())&&!"".equals(drivingAllInfor.getTimingEndTime())){
					try {
						drivingAllInfor.setTimingTime(String.valueOf(DateUtil.compareDate( DateUtil.toStrDateFromUtilDateByFormat(drivingAllInfor.getTimingStartTime(), "yyyy-MM-dd") ,
								DateUtil.toStrDateFromUtilDateByFormat(drivingAllInfor.getTimingEndTime(), "yyyy-MM-dd") , 0)+1));
					} catch (ParseException e) {
						drivingAllInfor.setTimingTime("0");
					}
				}
				if("09".equals(drivingAllInfor.getDataTitle())&&null!=drivingAllInfor.getCarID()){
					
					String hql = " from DtDrivingPrincipal where  companyid=? and studentid = ?";
					List<BaseBean>  beans1=baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
									account.getCompanyID(),
									drivingAllInfor.getStaffID() });
					
					if(beans1!=null&&beans1.size()==1){
						String hql1 = " from DtDrivingPrincipalType where companyid=?  and drivingprincipalid=?";
						List<BaseBean>  beans2= baseBeanService.getListBeanByHqlAndParams(hql1, new Object[] {account.getCompanyID(),( (DtDrivingPrincipal)beans1.get(0)).getDrivingprincipalid() });
							for (int i = 0; i < beans2.size(); i++) {
								DtDrivingPrincipalType   DtDrgPrType=(DtDrivingPrincipalType)beans2.get(i);
								
								DtDrgPrType.setCoachid(drivingAllInfor.getCarID());
								DtDrgPrType.setCoachname(drivingAllInfor.getCarCoach());
								DtDrgPrType.setCarNumber(drivingAllInfor.getCarIdentifier());
								beans.add(DtDrgPrType);
							}
					}else{
						return "fail";
					}
				}
				beans.add(drivingAllInfor);
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	/**
	 * 学员信息删除
	 * @return
	 */
	public String deleteStudentRegInfors(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=" delete from DtDrivingAllInformation where drivingAllInformationID=? and companyID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{dtDrivingAllInformation.getDrivingAllInformationID(),account.getCompanyID()});
		
		return "success";
	}
	
	/**
	 * 学员进度跟踪
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String studentInfortrack(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		//基本信息  联系方式
		cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{cstaff.getStaffID()});
		//公司信息
		RegistrationCompany=(DtDrivingAllInformation) baseBeanService.getBeanByHqlAndParams("from DtDrivingAllInformation where staffID=? and dataTitle=?  and companyID=?", new Object[]{cstaff.getStaffID(),"04",account.getCompanyID()});
		beanList=baseBeanService.getListBeanByHqlAndParams("from DtDrivingAllInformation where staffID=? and companyID=?", new Object[]{cstaff.getStaffID(),account.getCompanyID()});
		//教练信息
		String coachsql=" select  dp.registrationcarname,dj.coachname,dj.carNumber,dh.staffIdentityCard,dh.reference,dj.docstatus " +
				" from dt_driving_principal_type dj  " +
				" left join dt_driving_principal dp on  dp.drivingprincipalid=dj.drivingprincipalid " +
				" left join dt_hr_staff dh on dh.staffid=dp.responsiblePersonsID " +
				" where dp.companyid=? and dp.studentid=?";
		coachList=baseBeanService.getListBeanBySqlAndParams(coachsql, new Object[]{account.getCompanyID(),cstaff.getStaffID()});
		//考试信息
		String testsql="  select dj.docstatus ,dt.testdate,dt.examresult " +
				" from dt_driving_test dt ,dt_driving_principal_type dj  , dt_driving_principal dp " +
				" where  dj.principaltypeid=dt.principaltypeid and dp.drivingprincipalid=dj.drivingprincipalid and dp.companyid=? and dp.studentid=?";
		testList=baseBeanService.getListBeanBySqlAndParams(testsql,  new Object[]{account.getCompanyID(),cstaff.getStaffID()});
		return "infortrack";
	}
	public Map<String, DtDrivingAllInformation> getStudentRegInformap() {
		return studentRegInformap;
	}

	public void setStudentRegInformap(
			Map<String, DtDrivingAllInformation> studentRegInformap) {
		this.studentRegInformap = studentRegInformap;
	}

	public DtDrivingAllInformation getDtDrivingAllInformation() {
		return dtDrivingAllInformation;
	}

	public void setDtDrivingAllInformation(
			DtDrivingAllInformation dtDrivingAllInformation) {
		this.dtDrivingAllInformation = dtDrivingAllInformation;
	}

	public List<BaseBean> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<BaseBean> beanList) {
		this.beanList = beanList;
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

	public List<Integer> getAddormod() {
		return addormod;
	}
	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}
	public void setAddormod(List<Integer> addormod) {
		this.addormod = addormod;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public String getTypeType() {
		return typeType;
	}

	public void setTypeType(String typeType) {
		this.typeType = typeType;
	}

	public ContactRelation getContactrelation() {
		return contactrelation;
	}

	public void setContactrelation(ContactRelation contactrelation) {
		this.contactrelation = contactrelation;
	}
	public List<CCode> getStandardlist() {
		return standardlist;
	}

	public void setStandardlist(List<CCode> standardlist) {
		this.standardlist = standardlist;
	}

	public List<CCode> getApplylist() {
		return applylist;
	}

	public void setApplylist(List<CCode> applylist) {
		this.applylist = applylist;
	}

	public List<CCode> getQuasilist() {
		return quasilist;
	}

	public void setQuasilist(List<CCode> quasilist) {
		this.quasilist = quasilist;
	}

	public DtDrivingAllInformation getRegistrationCompany() {
		return RegistrationCompany;
	}

	public void setRegistrationCompany(DtDrivingAllInformation registrationCompany) {
		RegistrationCompany = registrationCompany;
	}

	public List<BaseBean> getTestList() {
		return testList;
	}

	public void setTestList(List<BaseBean> testList) {
		this.testList = testList;
	}

	public List<BaseBean> getCoachList() {
		return coachList;
	}

	public void setCoachList(List<BaseBean> coachList) {
		this.coachList = coachList;
	}
	
}