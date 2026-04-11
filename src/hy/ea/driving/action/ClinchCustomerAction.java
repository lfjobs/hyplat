package hy.ea.driving.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 成交客户
 *@author 陈婷
 */
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.driving.DtDrivingAllInformation;
import hy.ea.bo.driving.DtDrivingCard;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.DtDrivingPrincipalType;
import hy.ea.bo.driving.view.DrivingStudentView;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffPABill;
import hy.ea.bo.human.StaffPersonalFile;
import hy.ea.bo.invoicing.StudentArchiveBill;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ClinchCustomerAction implements SessionAware{
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;
	private ContactUser contactUser;
	private DtDrivingPrincipal dtDrivingPrincipal;
	private DtDrivingPrincipalType dtDrivingPrincipalType;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String result;
	private String search;
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	public InputStream excelStream;
	public String statusFor;//教务处传值状态
	private CashierBills cashierBills;
	private StudentArchiveBill paBill;
	private Staff staff;
	private StaffPersonalFile personalRecord;
	private String managerID;
	private CashierBillsVO cashierBillsVO1;
	private CashierBillsVO cashierBillsVO2;
	
	private DtDrivingAllInformation  dtDrivingAllInformation;
	/**
	 *  此标识符区别  咨询客户  预定客户  成交客户
	 */
	private String module_Identifier;
	/**
	 *   此标识符区别 (咨询客户  预定客户  成交客户)   与  教务处生产（培训）  跳转页面
	 * 
	 */
	private String view_Identifier;   
	/**
	 *  页面导航标识
	 */
	private String module_title;
	/**
	 * 
	 *  用来区分，已打印和未打印的
	 */
	private String typeprint;
	/**
	 * 
	 * 集团公司标识符  判断是否是集团公司请求
	 */
	private String  companyGroupLogo;
	/**
	 * 区分档案管理和教务生产跟踪服务
	 */
	private String dangan;
	private String staffIdArr;
	
	private List<BaseBean> stafflist;
	
	/**
	 * 打印次数 map  key ： staffID ， value ： printCount  打印次数
	 */
	private static Map<String,String> mapPc = new HashMap<String,String>(); 
	/**
	 * 学员状态map  key ： staffID ，value ：status  学员状态
	 */
	private static Map<String, String> mapOcm=new HashMap<String, String>();
	/**
	 * 学员状态map  key ： companyID ，value ：companyName  公司名称
	 */
	private static Map<String, String> mapCompany =new HashMap<String, String>();
	
	/**
	 * 学员状态map  key ： staffID ，value ：registrationdate 录入时间 
	 */
	private static Map<String, Object> mapStaffRegistrationdate =new HashMap<String, Object>();
	public static Map<String, String> getMapOcm() {
		return mapOcm;
	}
	public static void setMapOcm(Map<String, String> mapOcm) {
		ClinchCustomerAction.mapOcm = mapOcm;
	}
	
	public static Map<String, Object> getMapStaffRegistrationdate() {
		return mapStaffRegistrationdate;
	}
	public static void setMapStaffRegistrationdate(
			Map<String, Object> mapStaffRegistrationdate) {
		ClinchCustomerAction.mapStaffRegistrationdate = mapStaffRegistrationdate;
	}
	//根据条件查询学员
	public String toSearch() { 
	    session.put("tablesearch", dtDrivingPrincipal);
		return getListContactUser();
	}
	//根据条件查询往来个人 
	public String toSearchConsult() { 
		    session.put("tablesearch", contactUser);
			return getListContactUserConsult();
	}
	/**
	 * 根据往来个人查询银行账户
	 * @return
	 */
	public String getListRegistrationUser() {
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { contactUser.getStaffID()};
		 //查询银行账户
		 String hql="from StaffBankAccount where staffID= ?";
		 List<BaseBean> bankList=baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankList", bankList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 
	 * 查询学员列表
	 * @return
	 */
	public List<Object> getList()
	{
		List<Object> list=new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>(); 
		CAccount account = (CAccount) session.get("account");
		 codeRelationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		String hql=" from ContactUser c where 1=1 ";
		/**
		 * hql连接集团及集团下分公司的ID
		 */
		String orHql="";
		List<Object> parmsCompanyIDS=new ArrayList<Object>();
		if("companyGroupLogo".equals(companyGroupLogo)){
			DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
			dcCompany.add(Restrictions.eq("companyStatus", "00"));
			dcCompany.add(Restrictions.or(Restrictions.eq("companyPID", account.getCompanyID()), Restrictions.eq("companyID", account.getCompanyID())));
			List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);
			String orWhere="";
			for (BaseBean baseBean : listCompany) {
				Company company=(Company)baseBean;
				orWhere+="  c.companyID=? or ";
				parmsCompanyIDS.add(company.getCompanyID());
			}
			orHql+=" and ( "+orWhere.substring(0, orWhere.lastIndexOf("or"))+" ) ";;
		}
		if("companyGroupLogo".equals(companyGroupLogo)){
			hql+=orHql;
			parms.addAll(parmsCompanyIDS);
		}else{
			hql+=" and c.companyID=? ";
			parms.add(account.getCompanyID());
		}
		hql+=" and c.relation like ? ";
		parms.add("%学员%");
		if (search != null && search.equals("search")) {
			this.contactUser = (ContactUser) session.get("tablesearch");
			if(contactUser.getStaffName()!=null&&!"".equals(contactUser.getStaffName()))
			{
				hql+=" and c.staffName like ? ";
				parms.add("%"+contactUser.getStaffName()+"%");
			}
			if(contactUser.getStaffIdentityCard()!=null&&!"".equals(contactUser.getStaffIdentityCard()))
			{
				hql+=" and c.staffIdentityCard like ? ";
				parms.add("%"+contactUser.getStaffIdentityCard()+"%");
			}
			if(contactUser.getStaffAddress()!=null&&!"".equals(contactUser.getStaffAddress()))
			{
				hql+=" and c.staffAddress like ? ";
				parms.add("%"+contactUser.getStaffAddress()+"%");
			}
			if(contactUser.getReference()!=null&&!"".equals(contactUser.getReference()))
			{
				hql+=" and c.reference like ? ";
				parms.add("%"+contactUser.getReference()+"%");
			}
			if(contactUser.getStaffCode()!=null&&!"".equals(contactUser.getStaffCode()))
			{
				hql+=" and c.staffCode like ? ";
				parms.add("%"+contactUser.getStaffCode()+"%");
			}
		}
		if("customer_Reservation".equals(module_Identifier)){
			hql+=" and  exists ( select 1 from DrivingDeal  d where d.companyid = c.companyID and d.states=? and  c.staffID=d.staffID) ";
			parms.add("00");
		}
		if("customer_Deal".equals(module_Identifier)){
			hql+=" and  exists ( select 1 from DrivingDeal  d where d.companyid = c.companyID and d.states=? and  c.staffID=d.staffID) ";
			parms.add("01");
		}
		if ("educational_train".equals(view_Identifier)) {
			hql+=" and exists (select  1 from DtDrivingPrincipal dp  where dp.companyid=c.companyID and " +
					"exists ( select 1 from DtDrivingPrincipalType dpt " +
					"where dp.drivingprincipalid=dpt.drivingprincipalid and dpt.docstatus=?) and dp.studentid=c.staffID)";
			parms.add(module_title==null?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04");
		}
		if(typeprint!=null&&typeprint.equals("history")){
			hql+=" and exists (select 1 from DtDrivingCard t where t.companyID=c.companyID and c.staffID = t.studentId) ";
		}
		list.add(hql);
		list.add(parms.toArray());
		return list;
	}
	
	private DetachedCriteria getStudentDC(){
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dcstudent = DetachedCriteria.forClass(DrivingStudentView.class);
		if("companyGroupLogo".equals(companyGroupLogo)){
			DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
			dcCompany.add(Restrictions.eq("companyStatus", "00"));
			dcCompany.add(Restrictions.or(Restrictions.eq("companyPID", account.getCompanyID()), Restrictions.eq("companyID", account.getCompanyID())));
			List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);
			List<Object> companyIDList=new ArrayList<Object>();
			for (BaseBean baseBean : listCompany) {
				Company company=(Company)baseBean;
				companyIDList.add(company.getCompanyID());
			}
			dcstudent.add(Restrictions.in("companyid", companyIDList.toArray()));
		}else{
			dcstudent.add(Restrictions.eq("companyid", account.getCompanyID()));
		}
		if (search != null && search.equals("search")) {
			dtDrivingPrincipal = (DtDrivingPrincipal) session.get("tablesearch");
			if(dtDrivingPrincipal.getCompanyid()!=null&&!"".equals(dtDrivingPrincipal.getCompanyid().trim()))
			{
				dcstudent.add(Restrictions.eq("companyid", dtDrivingPrincipal.getCompanyid().trim()));
			}
			if(dtDrivingPrincipal.getSearchStaDate()!=null&&!"".equals(dtDrivingPrincipal.getSearchStaDate())&&dtDrivingPrincipal.getSearchEndDate()!=null&&!"".equals(dtDrivingPrincipal.getSearchEndDate()))
			{
				dcstudent.add(Restrictions.between("inputTime", dtDrivingPrincipal.getSearchStaDate(), dtDrivingPrincipal.getSearchEndDate()));
			}
			if(dtDrivingPrincipal.getStudentname()!=null&&!"".equals(dtDrivingPrincipal.getStudentname().trim()))
			{
				dcstudent.add(Restrictions.like("staffname", dtDrivingPrincipal.getStudentname().trim(),MatchMode.ANYWHERE));
			}
			if(dtDrivingPrincipal.getStudentcard()!=null&&!"".equals(dtDrivingPrincipal.getStudentcard().trim()))
			{
				dcstudent.add(Restrictions.like("staffidentitycard", dtDrivingPrincipal.getStudentcard().trim(),MatchMode.ANYWHERE));
			}
			if(dtDrivingPrincipal.getStudentCode()!=null&&!"".equals(dtDrivingPrincipal.getStudentCode().trim()))
			{
				dcstudent.add(Restrictions.like("staffcode", dtDrivingPrincipal.getStudentCode().trim(),MatchMode.ANYWHERE));
			}
			if(dtDrivingPrincipal.getStudentphone()!=null&&!"".equals(dtDrivingPrincipal.getStudentphone().trim()))
			{
				if("01".equals(dtDrivingPrincipal.getStudentphone().trim())){
					dcstudent.add(Restrictions.eq("studentstatus", "07"));
				}else{
					dcstudent.add(Restrictions.eq("studentstatus", "06"));
					dcstudent.add(Restrictions.isNotNull("testresult"));
				}
			}
			if(dtDrivingPrincipal.getStudentPeriods()!=null&&!"".equals(dtDrivingPrincipal.getStudentPeriods().trim()))
			{
				dcstudent.add(Restrictions.eq("studentPeriods", dtDrivingPrincipal.getStudentPeriods().trim()));
			}
		}else if(!"history".equals(typeprint)&&module_title!=null){
			dcstudent.add(Restrictions.eq("studentstatus", "06"));
			dcstudent.add(Restrictions.isNotNull("testresult"));
		}
		if("customer_Reservation".equals(module_Identifier)){
			dcstudent.add(Restrictions.eq("states", "00"));
		}
		if("customer_Deal".equals(module_Identifier)){
			dcstudent.add(Restrictions.eq("states", "01"));
		}
		if ("educational_train".equals(view_Identifier)) {
			dcstudent.add(Restrictions.eq("docstatus", module_title==null?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04"));
		}
		if(typeprint!=null&&typeprint.equals("history")){
			dcstudent.add(Restrictions.isNotNull("printcount"));
		}
		dcstudent.add(Restrictions.isNotNull("registrationdate"));
		dcstudent.addOrder(Order.desc("registrationdate"));
		return dcstudent;
	}
	
	/**
	 * 导出学员信息表
	 * @return
	 */
	public String exportExcelDrivingStudentView() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		List<BaseBean> list = baseBeanService.getListByDC(getStudentDC());
		excelStream = excelService.showExcel(DrivingStudentView.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出学员信息表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 往来学员列表
	 * @return
	 */
	public String getListContactUser() { 
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getStudentDC());
		if ("educational_train".equals(view_Identifier)) {
			if(typeprint!=null&&typeprint.equals("history")){
				return "historylist";
			}
			return "list_extend";
		}
		return "list";
	}
	/**
	 * 往来个人列表
	 * @return
	 */
	public String getListContactUserConsult() { 
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?15:pageNumber), getList().get(0).toString(), (Object[])(getList().get(1)));			
			return "list";
	}
	
	 /**
	  * @ mapOcm
	  *  设置map集合  key   ：studentid   value :  docstatus
	  */
	@SuppressWarnings("unchecked")
	public void setMapOcm(){
		//CAccount account = (CAccount) session.get("account");
		String sql=" select dp.studentid," +
				"case " +
				" when dpt.docstatus='01' and dpt.studentstatus='04' then '未报开学'" +
				" when dpt.docstatus='01' and dpt.studentstatus='05' and  (dp.istrues is null or dp.istrues ='不合格') then '已报开学'" +
				" when dpt.docstatus='01' and dpt.studentstatus='03' and  dp.istrues='合格' then '已预约培训'" +
				" when dpt.docstatus='01' and dpt.studentstatus='08' then '已培训'" +
				" when dpt.docstatus='01' and dpt.studentstatus = '06' and dpt.testresult is  null   then '已约考'" +
				" when dpt.docstatus='01' and dpt.studentstatus = '06' and dpt.testresult is not null then '不合格'" +
				" when dpt.docstatus='01' and dpt.studentstatus = '07'  then '已合格'" +
				" when dpt.docstatus !='01' and dpt.studentstatus = '02'  then '未预约培训'" +
				" when dpt.docstatus !='01' and dpt.studentstatus = '03'  then '已预约培训'" +
				" when dpt.docstatus !='01' and dpt.studentstatus = '05'  then '已培训'" +
				" when dpt.docstatus !='01' and dpt.studentstatus = '06' and dpt.testresult is  null   then '已约考'" +
				" when dpt.docstatus !='01' and dpt.studentstatus = '06' and dpt.testresult is not null then '不合格'" +
				" when dpt.docstatus !='01' and dpt.studentstatus = '07'  then '已合格'" +
				" else '未定义' end," +
				" dp.istrues,dpt.studentstatus,dpt.docstatus" +
				" from dt_driving_principal dp,dt_driving_principal_type dpt" +
				" where dp.drivingprincipalid=dpt.drivingprincipalid   and dpt.docstatus=? ";
		List<Object>  list=new ArrayList<Object>();
		list.add(module_title==null?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04");
		
		List<BaseBean>   beans=baseBeanService.getListBeanBySqlAndParams(sql, list.toArray());
		for (int i = 0; i < beans.size(); i++) {
			Object bean = (Object)beans.get(i);
			Object[]  beanArry=(Object[])bean;
			mapOcm.put(beanArry[0].toString(), beanArry[1].toString());
		}
	}
	/**
	 * 
	 * 设置公司  map集合
	 * 
	 */
	public void setMapCompany(){
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
		dcCompany.add(Restrictions.eq("companyStatus", "00"));
		dcCompany.add(Restrictions.or(Restrictions.eq("companyPID", account.getCompanyID()), Restrictions.eq("companyID", account.getCompanyID())));
		List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);
		for(BaseBean b:listCompany){
			Company com = (Company) b;
			mapCompany.put(com.getCompanyID(),com.getCompanyName());
		}
	}
	/**
	 * 
	 * 设置 map 学员主表  （时间，等）
	 * 
	 */
	public void setMapStaffRegistrationdate(PageForm pageForm){
		if(pageForm!=null){
			List<Object> parms = new ArrayList<Object>(); 
			for (int i = 0; i < pageForm.getList().size(); i++) {
				ContactUser cu=(ContactUser)pageForm.getList().get(i);
				parms.add(cu.getStaffID());
			}
			CAccount account = (CAccount) session.get("account");
			DetachedCriteria dcDtDrivingPrincipal = DetachedCriteria.forClass(DtDrivingPrincipal.class);
			if(!"companyGroupLogo".equals(companyGroupLogo)){
				dcDtDrivingPrincipal.add(Restrictions.eq("companyid", account.getCompanyID()));
			}
			dcDtDrivingPrincipal.add(Restrictions.in("studentid", parms.toArray()));
			List<BaseBean> listdcDtDrivingPrincipal = baseBeanService.getListByDC(dcDtDrivingPrincipal);
			for(BaseBean b:listdcDtDrivingPrincipal){
				DtDrivingPrincipal dp = (DtDrivingPrincipal) b;
				mapStaffRegistrationdate.put(dp.getStudentid(),dp.getRegistrationdate());
			}
		}
	}
	/**
	 * 
	 * 查询打印次数 按公司查找
	 */
	public void setMapPc(){
		CAccount account = (CAccount) session.get("account");
		//记录打印次数
		String hql = "from DtDrivingCard where companyID = ?";
	    List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getCompanyID()});
		for(BaseBean b:list){
			DtDrivingCard card = (DtDrivingCard) b;
			mapPc.put(card.getStudentId(),String.valueOf(card.getPrintCount()));
		}
	}
	//往来关系
	public String getRelationList() {
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList().get(0).toString(), (Object[])(getList().get(1)));
		return "relation";
	}
	
	/**
	 * 根据往来个人名称模糊查询列表
	 * @return
	 */
	public String getListContactUserBycontactUserName() {
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from ContactUser where staffName like ? and relation like ?  and companyID = ? ";
		if(statusFor!=null&&statusFor.equals("relation")){
			hql += " and staffID in (select t.contactUserID from CashierBills t where t.depStatue='01' and companyID = '" + account.getCompanyID() + "') ";
		}
		Object[] parms = {"%"+contactUser.getStaffName()+"%","%"+contactUser.getRelation()+"%",account.getCompanyID()} ;
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql,parms);
		 codeRelationList = codeService.getCCodeListByPID(account
					.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		map.put("codeRelationList", codeRelationList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 根据单据编号查询往来个人-学员
	 * @return
	 */
	public String getOneContactUserByJournalNum() {
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from ContactUser where companyID = ? ";
			   hql += " and staffID in (select t.contactUserID from CashierBills t where t.depStatue='01' " +
			   		"and companyID = '" + account.getCompanyID() + "' and t.journalNum='"+ cashierBills.getJournalNum() +"') ";		
		Object[] parms = {account.getCompanyID()} ;
		contactUser=(ContactUser) baseBeanService.getBeanByHqlAndParams(hql, parms);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contactUser", contactUser);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 招生报表查询
	 * @return
	 */
	public  String toSearchByAdmissions(){
		session.put("dtDrivingAllInformation", dtDrivingAllInformation);
		session.put("dtDrivingPrincipal", dtDrivingPrincipal);
		return  getListStudentArchive();
	}
	/**
	 * 
	 * @see 构建 教务处-营销  -学员档案管理 学员招生报表 DetachedCriteria 
	 * @return
	 */
	private DetachedCriteria getStudentByAdmissionsDC(){
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dcstudent = DetachedCriteria.forClass(DrivingStudentView.class);
		if("companyGroupLogo".equals(companyGroupLogo)){
			DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
			dcCompany.add(Restrictions.eq("companyStatus", "00"));
			dcCompany.add(Restrictions.or(Restrictions.eq("companyPID", account.getCompanyID()), Restrictions.eq("companyID", account.getCompanyID())));
			List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);
			List<Object> companyIDList=new ArrayList<Object>();
			for (BaseBean baseBean : listCompany) {
				Company company=(Company)baseBean;
				companyIDList.add(company.getCompanyID());
			}
			dcstudent.add(Restrictions.in("companyid", companyIDList.toArray()));
		}else{
			dcstudent.add(Restrictions.eq("companyid", account.getCompanyID()));
		}
		if("customer_Reservation".equals(module_Identifier)){
			dcstudent.add(Restrictions.eq("states", "00"));
		}
		if("customer_Deal".equals(module_Identifier)){
			dcstudent.add(Restrictions.eq("states", "01"));
		}
		if ("educational_train".equals(view_Identifier)) {
			dcstudent.add(Restrictions.eq("docstatus", module_title==null||"".equals(module_title)?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04"));
		}
		if(typeprint!=null&&typeprint.equals("history")){
			dcstudent.add(Restrictions.isNotNull("printcount"));
		}
		if(search!=null&&"search".equals(search)){
			DtDrivingAllInformation dtDrivingAllInformation=(DtDrivingAllInformation)session.get("dtDrivingAllInformation");
			DtDrivingPrincipal dtDrivingPrincipal=(DtDrivingPrincipal)session.get("dtDrivingPrincipal");
			if(dtDrivingAllInformation!=null){
				if(dtDrivingAllInformation.getOrganizationID()!=null&&!"".equals(dtDrivingAllInformation.getOrganizationID())){
					if(!dtDrivingAllInformation.getOrganizationID().startsWith("company")){
						dcstudent.add(Restrictions.eq("ddmorganizationid", dtDrivingAllInformation.getOrganizationID()));
					}
				}
				if(dtDrivingAllInformation.getReferrerID()!=null&&!"".equals(dtDrivingAllInformation.getReferrerID())){
					dcstudent.add(Restrictions.eq("ddmreferrerid", dtDrivingAllInformation.getReferrerID()));
				}
				if(dtDrivingAllInformation.getSearchStaDate()!=null&&!"".equals(dtDrivingAllInformation.getSearchStaDate())
						&&dtDrivingAllInformation.getSearchEndDate()!=null&&!"".equals(dtDrivingAllInformation.getSearchEndDate())){
					dcstudent.add(Restrictions.between("ddmsignupdate", dtDrivingAllInformation.getSearchStaDate(), dtDrivingAllInformation.getSearchEndDate()));
				}
			}
			if(dtDrivingPrincipal!=null){
				if(dtDrivingPrincipal.getStudentname()!=null&&!"".equals(dtDrivingPrincipal.getStudentname())){
					dcstudent.add(Restrictions.like("staffname", dtDrivingPrincipal.getStudentname(), MatchMode.ANYWHERE));
				}
			}
		}
		dcstudent.add(Restrictions.isNotNull("registrationdate"));
		dcstudent.addOrder(Order.desc("ddmsignupdate"));
		dcstudent.addOrder(Order.desc("registrationdate"));
		return dcstudent;
	}
	/**
	 * 
	 * @see 教务处-营销  -学员档案管理 查询
	 * @return
	 */
	public String getListStudentArchive() { 
		
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),getStudentByAdmissionsDC());
		if (pageForm != null) {
			session.put("RecordCount", pageForm.getRecordCount());
		}
		return "archivelist";
	}
	/**
	 * 
	 * @see 教务处-营销  -学员档案管理 导出 招生报表
	 * @return
	 */
	public String toExportByAdmissions(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		List<BaseBean> list = baseBeanService.getListByDC(getStudentByAdmissionsDC());
		DrivingStudentView.setTitleByExport("Admissions");
		excelStream = excelService.showExcel(DrivingStudentView.columnHeadingsOfAdmissions(),list);
		DrivingStudentView.setTitleByExport("");
		CLogBook logBook = logBookService.saveCLogBook(null,"导出招生信息报表", account);
		baseBeanService.update(logBook);
		return "showexcel";
		
	}
	// 用于ajax查询一个学员
	public String getAjaxListStudentArchive() {
		CAccount account = (CAccount) session.get("account");
		List<Object> parms = new ArrayList<Object>();
		String hql = "from ContactUser c where 1=1 ";
		if(parameter==null){
			parameter="";
		}
		/**
		 * hql连接集团及集团下分公司的ID
		 */
		String orHql="";
		List<Object> parmsCompanyIDS=new ArrayList<Object>();
		if("companyGroupLogo".equals(companyGroupLogo)){
			DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
			dcCompany.add(Restrictions.eq("companyStatus", "00"));
			dcCompany.add(Restrictions.or(Restrictions.eq("companyPID", account.getCompanyID()), Restrictions.eq("companyID", account.getCompanyID())));
			List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);
			String orWhere="";
			for (BaseBean baseBean : listCompany) {
				Company company=(Company)baseBean;
				orWhere+="  c.companyID=? or ";
				parmsCompanyIDS.add(company.getCompanyID());
			}
			orHql+=" and ( "+orWhere.substring(0, orWhere.lastIndexOf("or"))+" ) ";;
		}
		if("companyGroupLogo".equals(companyGroupLogo)){
			hql+=orHql;
			parms.addAll(parmsCompanyIDS);
		}else{
			hql+=" and c.companyID=? ";
			parms.add(account.getCompanyID());
		}
		
		hql+=" and c.relation = ?  and (c.staffCode = ? or c.staffIdentityCard = ?) " +
					"and  exists ( select 1 from DrivingDeal  d where d.companyid =c.companyID and d.states='01' and  c.staffID=d.staffID) ";
		parms.add("学员");
		parms.add(parameter.trim());
		parms.add(parameter.trim());
		
		if ("educational_train".equals(view_Identifier)) {
			hql+=" and exists (select  1 from DtDrivingPrincipal dp  where dp.companyid=c.companyID and " +
					"exists ( select 1 from DtDrivingPrincipalType dpt " +
					"where dp.drivingprincipalid=dpt.drivingprincipalid and dpt.docstatus=?) and dp.studentid=c.staffID)";
			parms.add(module_title==null?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04");
		}
		
		try{
		ContactUser student = (ContactUser) baseBeanService.getBeanByHqlAndParams(hql,parms.toArray());
		if(student!=null){
			if ("educational_train".equals(view_Identifier)) {
				student.setRelation((module_title==null?"科一":module_title.startsWith("科一")?"科一":module_title.startsWith("科二")?"科二":module_title.startsWith("科三")?"科三":"科四")+(student!=null?mapOcm.get(student.getStaffID()):"状态异常"));
				student.setStaffDesc(mapCompany.get(student.getCompanyID()));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("student", student);
		if("customer_Consultation".equals(module_Identifier)){
			Staff  staff=(Staff)baseBeanService.getBeanByHqlAndParams(" from Staff where staffIdentityCard=? or staffCode=? ", new Object[]{parameter.trim(),parameter.trim()});
			map.put("student", staff);
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		}catch(Exception e){
			logger.error("操作异常", e);
		}

		return "success";

	}
	
	/**
	 * 
	 * 
	 * 填写学员出库单
	 * @return
	 */
	public String toSaveStudentArchive() { 
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		String hqlstaff = "from Staff where staffID = ?";
		String hqlorg = "from COrganization where organizationID = ?";
		String hqlcom = "from Company where companyID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
				new Object[] { account.getStaffID() });
		COrganization org = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hqlorg, new Object[] { (String) session
						.get("organizationID") });
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				hqlcom, new Object[] { companyID });
		paBill = new StudentArchiveBill();
		paBill.setStaffName(staff.getStaffName());
		paBill.setOrganizationName(org.getOrganizationName());
		paBill.setStaffID(account.getStaffID());
		paBill.setOrganizationID((String) session.get("organizationID"));
		paBill.setCompanyID(company.getCompanyID());
		paBill.setCompanyName(company.getCompanyName());

		return "add";
	}
	
	
	/**
	 * 学员档案——保存
	 */
	public String saveStudentArchive() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String groupCompanySn = session.get("groupCompanySn").toString();
			if (groupCompanySn == null) {
				return "login";
			}
			String pabillID = serverService.getServerID("parchive");
			paBill.setPabillID(pabillID);
			parameter = "添加学员档案单据（凭证号" + paBill.getJournalNum() + "）";
			paBill.setOrganizationID(organizationID);
			paBill.setCompanyID(account.getCompanyID());
			paBill.setGroupCompanySn(groupCompanySn);
			paBill.setBillsType("异动单");
			paBill.setBillsDate(Utilities.getDateString(new Date(),
					"yyyy-MM-dd"));
			paBill.setBillStaffName(paBill.getStaffName());
			paBill.setBillStaffID(account.getStaffID());
			paBill.setBillStatus("00");
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
			baseBeanList.add(paBill);
			String[] staffs = staff.getStaffID().split(",");
			String[] prices = staff.getPrice().split(",");
			String hql = "from Staff where staffID = ?";
			Staff oldstaff = null;
			Staff manager = null;
			StaffPABill spa = null;
			if (staffs.length != 0) {
				for (int i = 0; i < staffs.length - 1; i++) {
					oldstaff = (Staff) baseBeanService.getBeanByHqlAndParams(
							hql, new Object[] { staffs[i].trim() });
					oldstaff.setPrice(prices[i].trim());
					baseBeanService.update(oldstaff);
					// 物品和单子绑定
					spa = new StaffPABill();
					spa.setSpaId(serverService.getServerID("spa"));
					spa.setPaBillID(pabillID);
					spa.setStaffID(staffs[i].trim());
					baseBeanService.save(spa);

					// 修改学员档案记录
					personalRecord = new StaffPersonalFile();
					personalRecord.setRecordID(serverService
							.getServerID("personalRecord"));
					personalRecord.setCompanyID(account.getCompanyID());
					personalRecord.setControlStartDate(new Date());
					
					if (managerID != null && !managerID.equals("")) {
						manager = (Staff) baseBeanService.getBeanByHqlAndParams(
								hql, new Object[] { managerID });
						if(manager!=null){
						   personalRecord.setDutyOfficer(manager.getStaffName());
						}
					} else {
						personalRecord.setDutyOfficer(account.getStaffID());
					}
					personalRecord.setStaffID(staffs[i].trim());
					baseBeanService.save(personalRecord);

				}

			}

			CLogBook logBook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			baseBeanList.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}
	
	/**
	 * 
	 * 单据查询
	 * 
	 * @return
	 */
	public String toSearchSheet() { 
		session.put("tablesearch", paBill);
		return getSArchiveSheetList();
	}

	
	// 出库单据
	public String getSArchiveSheetList() { 
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		DetachedCriteria dc = DetachedCriteria
				.forClass(StudentArchiveBill.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			paBill = (StudentArchiveBill) session.get("tablesearch");
			if (paBill.getBillStaffName() != null
					&& !paBill.getBillStaffName().equals("")) {
				dc.add(Restrictions.like("billStaffName", paBill
						.getBillStaffName(), MatchMode.ANYWHERE));
			}
		}

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		session.put("tablesearch", paBill);

		return "sheet";
	}
	//查看单子
	public String toViewSheet() {
		toView();
		return "edit";
	}
	
	//打印单子
	public String toprintSheet() {
		toView();
		return "printpa";
	}
	
	public void toView() {
		if (paBill != null) {
			String hql1 = "from Staff s where exists(select staffID from StaffPABill p where paBillID = ? and s.staffID = p.staffID)";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, new Object[] { paBill.getPabillID() });
			String hql = " from StudentArchiveBill where  pabillID=?";
			paBill = (StudentArchiveBill) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { paBill
							.getPabillID() });

			String hqlorg = "from COrganization  where organizationID = ?";
			String hqlcom = "from Company where companyID = ?";
			COrganization org = (COrganization) baseBeanService
					.getBeanByHqlAndParams(hqlorg, new Object[] { paBill
							.getOrganizationID() });
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hqlcom, new Object[] { paBill.getCompanyID() });
			if (org != null) {
				paBill.setOrganizationName(org.getOrganizationName());

			}
			if (company != null) {
				paBill.setCompanyName(company.getCompanyName());
			}

			String hqlc = "from CashierBillsVO where ccompanyID = ?";
			String hqls = "from CashierBillsVO where contactUserID = ?";
			cashierBillsVO1 = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hqlc, new Object[] { paBill
							.getCcompanyID() });
			cashierBillsVO2 = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hqls, new Object[] { paBill
							.getCstaffID() });
		}
	}
	
	// 用于芯片查找
	public String getStudentArchive() {
		String hql = "from Staff where chipid = ?";
		Staff staffinfo = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{parameter.trim()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffinfo", staffinfo);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}
	
	/**
	 * 
	 * 窗口查询 打印预览
	 * @return
	 */
	public String toPrintPre(){
		try{
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dcstudent = DetachedCriteria.forClass(DrivingStudentView.class);
		if("companyGroupLogo".equals(companyGroupLogo)){
				DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
				dcCompany.add(Restrictions.eq("companyStatus", "00"));
				dcCompany.add(Restrictions.or(Restrictions.eq("companyPID", account.getCompanyID()), Restrictions.eq("companyID", account.getCompanyID())));
				List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);
				List<Object> companyIDList=new ArrayList<Object>();
				for (BaseBean baseBean : listCompany) {
					Company company=(Company)baseBean;
					companyIDList.add(company.getCompanyID());
				}
				dcstudent.add(Restrictions.in("companyid", companyIDList.toArray()));
		}else{
				dcstudent.add(Restrictions.eq("companyid", account.getCompanyID()));
		}
		dcstudent.add(Restrictions.eq("docstatus", module_title==null?"01":module_title.startsWith("科一")?"01":module_title.startsWith("科二")?"02":module_title.startsWith("科三")?"03":"04"));
		dcstudent.add(Restrictions.in("staffid",Arrays.asList(staffIdArr.split(","))));
		dcstudent.add(Restrictions.eq("states", "01"));
		stafflist = baseBeanService.getListByDC(dcstudent);
		}catch(Exception e){
			logger.error("操作异常", e);
		}
		
		
		return "toprintprev";
		
	}
	public ContactUser getContactUser() {
		return contactUser;
	}
	public void setContactUser(ContactUser ContactUser) {
		this.contactUser = ContactUser;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}
	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
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
	public String getStatusFor() {
		return statusFor;
	}
	public void setStatusFor(String statusFor) {
		this.statusFor = statusFor;
	}
	public CashierBills getCashierBills() {
		return cashierBills;
	}
	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}
	public String getModule_Identifier() {
		return module_Identifier;
	}
	public void setModule_Identifier(String module_Identifier) {
		this.module_Identifier = module_Identifier;
	}
	public List<CCode> getPayTypeList() {
		return payTypeList;
	}
	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}
	public List<CCode> getLogisticsList() {
		return logisticsList;
	}
	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}
	public List<CCode> getConnectionlist() {
		return connectionlist;
	}
	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}
	public StudentArchiveBill getPaBill() {
		return paBill;
	}
	public void setPaBill(StudentArchiveBill paBill) {
		this.paBill = paBill;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public StaffPersonalFile getPersonalRecord() {
		return personalRecord;
	}
	public void setPersonalRecord(StaffPersonalFile personalRecord) {
		this.personalRecord = personalRecord;
	}
	public String getManagerID() {
		return managerID;
	}
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}
	public CashierBillsVO getCashierBillsVO1() {
		return cashierBillsVO1;
	}
	public void setCashierBillsVO1(CashierBillsVO cashierBillsVO1) {
		this.cashierBillsVO1 = cashierBillsVO1;
	}
	public CashierBillsVO getCashierBillsVO2() {
		return cashierBillsVO2;
	}
	public void setCashierBillsVO2(CashierBillsVO cashierBillsVO2) {
		this.cashierBillsVO2 = cashierBillsVO2;
	}
		public String getView_Identifier() {
		return view_Identifier;
	}
	public void setView_Identifier(String view_Identifier) {
		this.view_Identifier = view_Identifier;
	}
	public String getModule_title() {
		return module_title;
	}
	public void setModule_title(String module_title) {
		this.module_title = module_title;
	}
	
	private Map<String,Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String getTypeprint() {
		return typeprint;
	}
	public void setTypeprint(String typeprint) {
		this.typeprint = typeprint;
	}
	public static Map<String, String> getMapPc() {
		return mapPc;
	}
	public static void setMapPc(Map<String, String> mapPc) {
		ClinchCustomerAction.mapPc = mapPc;
	}
	public String getStaffIdArr() {
		return staffIdArr;
	}
	public void setStaffIdArr(String staffIdArr) {
		this.staffIdArr = staffIdArr;
	}
	public List<BaseBean> getStafflist() {
		return stafflist;
	}
	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}
	public String getCompanyGroupLogo() {
		return companyGroupLogo;
	}
	public void setCompanyGroupLogo(String companyGroupLogo) {
		this.companyGroupLogo = companyGroupLogo;
	}
	public static Map<String, String> getMapCompany() {
		return mapCompany;
	}
	public static void setMapCompany(Map<String, String> mapCompany) {
		ClinchCustomerAction.mapCompany = mapCompany;
	}
	public DtDrivingPrincipal getDtDrivingPrincipal() {
		return dtDrivingPrincipal;
	}
	public void setDtDrivingPrincipal(DtDrivingPrincipal dtDrivingPrincipal) {
		this.dtDrivingPrincipal = dtDrivingPrincipal;
	}
	public DtDrivingAllInformation getDtDrivingAllInformation() {
		return dtDrivingAllInformation;
	}
	public void setDtDrivingAllInformation(
			DtDrivingAllInformation dtDrivingAllInformation) {
		this.dtDrivingAllInformation = dtDrivingAllInformation;
	}
	public String getDangan() {
		return dangan;
	}
	public void setDangan(String dangan) {
		this.dangan = dangan;
	}
	
	
	
}
