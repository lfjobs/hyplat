package hy.ea.ddsr.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.Company;
import hy.ea.bo.ddsr.Ddsrcoach;
import hy.ea.bo.ddsr.Ddsrreservationrecord;
import hy.ea.bo.ddsr.Dssrstudent;
import hy.ea.bo.ddsr.Dssrsubject;
import hy.ea.bo.ddsr.ReDssrstudentDssrsubject;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.COrganizationService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class AppointmentByWeChatAction {
	private static final Logger logger = LoggerFactory.getLogger(AppointmentByWeChatAction.class);
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private COrganizationService cOrganizationService;
	private Ddsrcoach ddsrcoach;
	private Dssrstudent dssrstudent;
	private Staff dtHrStaff;
	private int pageNumber;
	private List<BaseBean> beans;
	private PageForm pageForm;
	
	private String result;
	
	private String search;
	/**
	 * 科目
	 */
	private Dssrsubject dssrsubject;
	/**
	 * 
	 * 预约记录
	 */
	private Ddsrreservationrecord ddsrreservationrecord;
	private List<Object> arrayDateList=new ArrayList<Object>();
	Map<String, Object> session = ActionContext.getContext().getSession();
	public HttpServletRequest request = ServletActionContext.getRequest();
	private String unionCompanyID;
	/**
	 * 
	 * 教练分组查询条件按（部门）
	 */
	private DepartmentPost departmentPost;
	/**
	 * 登录页面
	 */
	public String login(){
		try {
			String password=dssrstudent.getStudPassword();
			dssrstudent=(Dssrstudent)baseBeanService.getBeanByHqlAndParams(" from Dssrstudent where dtHrStaff.staffIdentityCard=?", new Object[]{dssrstudent.getDtHrStaff().getStaffIdentityCard().trim()});
			if(dssrstudent==null){
				request.setAttribute("message", "用户不存在!!,请联系管理员!!");
				return "fail";
			}
			if("".equals(password.trim())){
				request.setAttribute("message", "密码不能为空!!,请联系管理员!!");
				return "fail";
			}
			if(!password.trim().equals(dssrstudent.getStudPassword())){
				request.setAttribute("message", "密码错误!!,请联系管理员!!");
				return "fail";
			}
		} catch (Exception e) {
			request.setAttribute("message", "数据异常!!,请联系管理员!!");
			return "fail";
		}
		session.put("dssrstudent", dssrstudent);
		List<BaseBean> beans=baseBeanService.getListBeanByHqlAndParams("  from ReDssrstudentDssrsubject where dssrstudent.studKey=?  ",  new Object[]{dssrstudent.getStudKey()});
		int count=0;
		for (BaseBean baseBean : beans) {
			ReDssrstudentDssrsubject redd=(ReDssrstudentDssrsubject)baseBean;
			if(Integer.valueOf(redd.getDssrsubject().getSubjType().toString()).intValue()>count){
				count=Integer.valueOf(redd.getDssrsubject().getSubjType().toString()).intValue();
			}
		}
		dssrsubject=new Dssrsubject();
		dssrsubject.setSubjType(Byte.valueOf(count+""));
		session.put("dssrsubject", dssrsubject);
		Company company=(Company) baseBeanService.getBeanByHqlAndParams(" from Company where companyID=? ", new Object[]{dssrstudent.getStudCompanyid()});
		session.put("company", company);
		return "success";
	}
	/**
	 * 
	 * 跳转到注册
	 */
	public String toRegistered(){
		
		return "toRegistered";
	}
	/**
	 * 
	 * 密码绑定
	 */
	public String registered(){
		try {
			String password=dssrstudent.getStudPassword();
			String passwordOther=dssrstudent.getStudPasswordOther();
			dssrstudent=(Dssrstudent)baseBeanService.getBeanByHqlAndParams(" from Dssrstudent where dtHrStaff.staffIdentityCard=?", new Object[]{dssrstudent.getDtHrStaff().getStaffIdentityCard().trim()});
			if(dssrstudent==null){
				request.setAttribute("message", "用户不存在!!,请联系管理员!!");
				return "fail";
			}
			if(null!=dssrstudent.getStudPassword()){
				request.setAttribute("message", "此用户已绑定密码!!不能重复绑定!!");
				return "fail";
			}
			if("".equals(password)||"".equals(passwordOther)){
				request.setAttribute("message", "密码或重复密码不能为空或长度小于6!!");
				return "fail";
			}
			if(!password.equals(passwordOther)){
				request.setAttribute("message", "密码与重复密码不一致!!");
				return "fail";
			}
			dssrstudent.setStudPassword(password);
			baseBeanService.update(dssrstudent);
			request.setAttribute("message", "密码绑定成功!!请登录!!");
			request.setAttribute("staffIdentityCard", dssrstudent.getDtHrStaff().getStaffIdentityCard());
			request.setAttribute("studPassword", dssrstudent.getStudPassword());
		} catch (Exception e) {
			logger.error("操作异常", e);
			request.setAttribute("message", "数据异常!!,请联系管理员!!");
			return "fail";
		}
		return "toLogin";
	}
	/**
	 * 首页
	 */
	public String toLogin(){
		return "toLogin"; //2014110702240400005
	}
	/**
	 * 预约查询
	 */
	public String toSearchPage(){
		return "toSearchPage";
	}
	/**
	 * 查询
	 * @return
	 */
	public String toSearch(){
		Dssrstudent dssrstudent=(Dssrstudent)session.get("dssrstudent");
		ddsrcoach.setCoacStar(dssrstudent.getStudStar());
		ddsrcoach.setCoacStatus(Byte.valueOf("10"));
		ddsrcoach.setCoacTeachtype(dssrstudent.getStudCredentials());
		session.put("ddsrcoach", ddsrcoach);
		session.put("ddsrreservationrecord", ddsrreservationrecord);
		return getListCoachReservationRecord();
	}
	/**
	 * 
	 * 参数：
	 * 返回：coachreservationrecord_list
	 */
	public String getListCoachReservationRecord() {
		
		if(search!=null&&"search".equals(search)){
			Ddsrreservationrecord ddsrreservationrecord=(Ddsrreservationrecord) session.get("ddsrreservationrecord");
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecord);
			arrayDateList=setTheDateHead(ddsrreservationrecord1.getSearchStaDate(),ddsrreservationrecord1.getSearchEndDate());
		}else{
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecord);
			arrayDateList=setTheDateHead(ddsrreservationrecord1.getSearchStaDate(),ddsrreservationrecord1.getSearchEndDate());
		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?3:pageNumber), getSqlAndParam().get(0).toString()
				, "select count(1) from("+getSqlAndParam().get(0).toString()+")",  
				(Object[])getSqlAndParam().get(1));
		return "coachreservationrecordOfWeChatList";
	}
	/**
	 * 构造sql语句
	 * @author allen
	 */
	private List<Object> getSqlAndParam(){
		List<Object> sqlAndParam=new ArrayList<Object>();
		List<Object> param=new ArrayList<Object>();
		
		StringBuffer sql=new StringBuffer("");
		StringBuffer sqlSelectStatic=new StringBuffer(" select ");
		
		sqlSelectStatic.append("max(dsrr.rere_key) rere_key,");
		sqlSelectStatic.append("dsc.coac_companyid coac_companyid,");
		sqlSelectStatic.append("dsc.coac_key  coac_key,");
		sqlSelectStatic.append("max(hs.staffname) staffname ,");
		sqlSelectStatic.append("max(dsc.coac_carNumber) coac_carNumber,");
		sqlSelectStatic.append("max(dsc.coac_status) coac_status,");
		sqlSelectStatic.append("max(dsc.coac_level) coac_level,");
		sqlSelectStatic.append("max(dsc.coac_teachtype) coac_teachtype,");
		sqlSelectStatic.append("max(dsc.coac_star) coac_star,");
		
		sql.append(sqlSelectStatic);
		
		
		StringBuffer sqlSelectDynamic=new StringBuffer("");
		if(arrayDateList!=null&&arrayDateList.size()>0){
			for (int i = 0; i < arrayDateList.size(); i++) {
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='10' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' | ' || ");
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='20' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' | ' || ");
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='30' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' | ' || ");
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='40' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' @ '||");
				sqlSelectDynamic.append("case when count(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"'  then 1 end)=sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' then dsrr.rere_peoplesum end) and sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' then dsrr.rere_peoplesum end)>0 then 1 else 0 end");
				sqlSelectDynamic.append(" sum"+i); 
				sqlSelectDynamic.append(",");
			}
		}
		/*sql.append(sqlSelectDynamic.substring(0, sqlSelectDynamic.lastIndexOf(",")));*/
		sql.append(sqlSelectDynamic);
		sql.append("max(hs.photo) photo ");
		
		StringBuffer sqlFromStatic=new StringBuffer(" from ");
		sqlFromStatic.append(" ddsrcoach dsc ");
		sqlFromStatic.append(" left join Re_ddsrCoach_dssrSubject rdds on rdds.coac_key=dsc.coac_key ");
		sqlFromStatic.append(" left join dssrSubject ds on ds.subj_key=rdds.subj_key ");
		sqlFromStatic.append(" left join dt_hr_staff hs on hs.staffkey=dsc.coac_key ");
		if(search!=null&&"search".equals(search)){
			Ddsrreservationrecord ddsrreservationrecordSessionScope=(Ddsrreservationrecord) session.get("ddsrreservationrecord");
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecordSessionScope);
			sqlFromStatic.append(" left join ddsrreservationrecord dsrr on dsrr.coac_key=dsc.coac_key and dsrr.rere_appdate between ? and ? ");
			param.add(ddsrreservationrecord1.getSearchStaDate());
			param.add(ddsrreservationrecord1.getSearchEndDate());
		}else{
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecord);
			sqlFromStatic.append(" left join ddsrreservationrecord dsrr on dsrr.coac_key=dsc.coac_key and dsrr.rere_appdate between ? and ? ");
			param.add(ddsrreservationrecord1.getSearchStaDate());
			param.add(ddsrreservationrecord1.getSearchEndDate());
		}
		sqlFromStatic.append(" left join Re_Dssrstudent_Ddsrresrecord rddr on rddr.rere_key=dsrr.rere_key ");
		sqlFromStatic.append(" left join dssrstudent dss on dss.stud_key=rddr.stud_key ");
		sqlFromStatic.append(" left join dt_hr_staff hs1 on hs1.staffkey=dss.stud_key ");
		sql.append(sqlFromStatic);
		
		StringBuffer sqlWhereStatic=new StringBuffer(" where ");
		sqlWhereStatic.append(" 1=1 ");
		
		Dssrstudent dssrstudent=(Dssrstudent) session.get("dssrstudent");
		sqlWhereStatic.append(" and dsc.coac_companyid=? ");
		param.add(dssrstudent.getStudCompanyid());
		
		Dssrsubject dssrsubject=(Dssrsubject) session.get("dssrsubject");
		sqlWhereStatic.append(" and ds.subj_type=? ");
		param.add(dssrsubject.getSubjType());
		if(ddsrcoach!=null&&ddsrcoach.getCoacKey()!=null&&!"".equals(ddsrcoach.getCoacKey())){
			sqlWhereStatic.append(" and dsc.coac_key=? ");
			param.add(ddsrcoach.getCoacKey().trim());
		}
		if(search!=null&&"search".equals(search)){
			Ddsrcoach ddsrcoach=(Ddsrcoach) session.get("ddsrcoach");
			if(ddsrcoach!=null){
				if(ddsrcoach.getDtHrStaff()!=null){
					if(ddsrcoach.getDtHrStaff().getStaffName()!=null&&!"".equals(ddsrcoach.getDtHrStaff().getStaffName().trim())){
						sqlWhereStatic.append(" and hs.STAFFNAME like ? ");
						param.add("%"+ddsrcoach.getDtHrStaff().getStaffName().trim()+"%");
					}
					if(ddsrcoach.getDtHrStaff().getStaffID()!=null&&!"".equals(ddsrcoach.getDtHrStaff().getStaffID().trim())){
						sqlWhereStatic.append(" and hs.staffID = ? ");
						param.add(ddsrcoach.getDtHrStaff().getStaffID().trim());
					}
				}
				if(ddsrcoach.getCoacLevel()!=null&&!"".equals(ddsrcoach.getCoacLevel().trim())){
						sqlWhereStatic.append(" and dsc.coac_level like ? ");
						param.add("%"+ddsrcoach.getCoacLevel().trim()+"%");
				}
				if(ddsrcoach.getCoacTeachtype()!=null&&!"".equals(ddsrcoach.getCoacTeachtype().trim())){
						sqlWhereStatic.append(" and dsc.coac_teachtype like ? ");
						param.add("%"+ddsrcoach.getCoacTeachtype().trim()+"%");
				}
				if(ddsrcoach.getCoacStar()!=null&&!"".equals(ddsrcoach.getCoacStar())){
					sqlWhereStatic.append(" and dsc.coac_star = ? ");
					param.add(ddsrcoach.getCoacStar());
				}
				if(ddsrcoach.getCoacStatus()!=null&&!"".equals(ddsrcoach.getCoacStatus())){
					sqlWhereStatic.append(" and dsc.coac_status = ? ");
					param.add(ddsrcoach.getCoacStatus());
				}
				if(ddsrcoach.getCoacKey()!=null&&!"".equals(ddsrcoach.getCoacKey().trim())){
					sqlWhereStatic.append(" and dsc.coac_key = ? ");
					param.add(ddsrcoach.getCoacKey().trim());
				}
			}
		}else{
			sqlWhereStatic.append(" and dsc.coac_status=? ");
			param.add(10);
		}
		/** start 按部门分组查询*/
		if(departmentPost!=null&&!"".equals(departmentPost.getOrganizationID())){
			String sql1 = "select 'X' from dtCos c left join dt_hr_Staff s on" +
					" c.staffid = s.staffid left join dt_hr_deptpost d on c.deppostid = d.deppostid where ";
			sql1 += " c.companyid = ? and c.cosStatus = ? and dsc.coac_key=s.staffkey ";
			param.add(dssrstudent.getStudCompanyid());
			param.add("50");
			sql1 += " and d.organizationID = ? ";
			param.add(departmentPost.getOrganizationID());
			sqlWhereStatic.append(" and exists ("+sql1+") ");
		}
		/** end 按部门分组查询*/
		sql.append(sqlWhereStatic);
		
		StringBuffer sqlGroupByStatic=new StringBuffer(" group by ");
		sqlGroupByStatic.append(" dsc.coac_companyid, ");
		sqlGroupByStatic.append(" dsc.coac_key ");
		sql.append(sqlGroupByStatic);
		
		if(search!=null&&"search".equals(search)){
			Ddsrreservationrecord ddsrreservationrecordSessionScope=(Ddsrreservationrecord) session.get("ddsrreservationrecord");
			if(ddsrreservationrecordSessionScope!=null){
				if(ddsrreservationrecordSessionScope.getRerePeoplesum()!=null&&!"".equals(ddsrreservationrecordSessionScope.getRerePeoplesum())){
					StringBuffer sqlHavingStatic=new StringBuffer(" having ");
					sqlHavingStatic.append(" (case when count(dsrr.rere_peoplesum)=sum(dsrr.rere_peoplesum) and sum(dsrr.rere_peoplesum)>0 then 1 else 0 end) = ?");
					param.add(ddsrreservationrecordSessionScope.getRerePeoplesum());
					sql.append(sqlHavingStatic);
				}
			}
		}
		
		sqlAndParam.add(sql);
		sqlAndParam.add(param.toArray());
		return sqlAndParam;
	}
	/**
	 * 构造或验证时间是否符合规定
	 */
	private Ddsrreservationrecord  validationOrConstructionDateParam(Ddsrreservationrecord  ddsrreservationrecord){
		Date searchStaDate=null;
		Date searchEndDate=null;
		Calendar calendarStaDate=DateUtil.toCalendarFromUtilDate(new Date());
		Calendar calendarEndDate=DateUtil.toCalendarFromUtilDate(new Date());
		if(ddsrreservationrecord!=null){
			if((ddsrreservationrecord.getSearchStaDate()==null||"".equals(ddsrreservationrecord.getSearchStaDate()))
					&&(ddsrreservationrecord.getSearchEndDate()==null||"".equals(ddsrreservationrecord.getSearchEndDate()))){
				try {
					searchStaDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarStaDate.getTime(),"yyyy-MM-dd"));
					calendarEndDate.add(Calendar.DAY_OF_MONTH, 7);
					searchEndDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarEndDate.getTime(),"yyyy-MM-dd"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
				ddsrreservationrecord.setSearchStaDate(searchStaDate);
				ddsrreservationrecord.setSearchEndDate(searchEndDate);
			}else if((ddsrreservationrecord.getSearchStaDate()==null||"".equals(ddsrreservationrecord.getSearchStaDate()))
					&&(ddsrreservationrecord.getSearchEndDate()!=null&&!"".equals(ddsrreservationrecord.getSearchEndDate()))){
				ddsrreservationrecord.setSearchStaDate(ddsrreservationrecord.getSearchEndDate());
				
			}else if((ddsrreservationrecord.getSearchEndDate()==null||"".equals(ddsrreservationrecord.getSearchEndDate()))
					&&(ddsrreservationrecord.getSearchStaDate()!=null&&!"".equals(ddsrreservationrecord.getSearchStaDate()))){
				ddsrreservationrecord.setSearchEndDate(ddsrreservationrecord.getSearchStaDate());
			}
			if(ddsrreservationrecord.getSearchEndDate().compareTo(ddsrreservationrecord.getSearchStaDate())==-1){
				try {
					searchStaDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarStaDate.getTime(),"yyyy-MM-dd"));
					calendarEndDate.add(Calendar.DAY_OF_MONTH, 7);
					searchEndDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarEndDate.getTime(),"yyyy-MM-dd"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
				ddsrreservationrecord.setSearchStaDate(searchStaDate);
				ddsrreservationrecord.setSearchEndDate(searchEndDate);
			}
		}else{
			
			try {
				searchStaDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarStaDate.getTime(),"yyyy-MM-dd"));
				calendarEndDate.add(Calendar.DAY_OF_MONTH, 7);
				searchEndDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarEndDate.getTime(),"yyyy-MM-dd"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}
			ddsrreservationrecord=new Ddsrreservationrecord();
			ddsrreservationrecord.setSearchStaDate(searchStaDate);
			ddsrreservationrecord.setSearchEndDate(searchEndDate);
			
		}
		
		return ddsrreservationrecord;
	}
	
	/**
	 * 设置表头参数
	 * @author allen
	 */
	public List<Object> setTheDateHead(Date startDate,Date endDate){
		int i=0;
		try {
			i = DateUtil.compareDate(DateUtil.toStrDateFromUtilDateByFormat(startDate,"yyyy-MM-dd"), DateUtil.toStrDateFromUtilDateByFormat(endDate,"yyyy-MM-dd"), 0);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar calendar=DateUtil.toCalendarFromUtilDate(startDate);
		for (int j = 0; j <= i; j++) {
			calendar.add(Calendar.DAY_OF_MONTH,j);
			try {
				arrayDateList.add(DateUtil.toStrDateFromUtilDateByFormat(calendar.getTime(),"yyyy-MM-dd"));
				calendar=DateUtil.toCalendarFromUtilDate(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}
		}
		return arrayDateList;
	}
	
	/**
	 * @time
	 * @author zc
	 * @note 获取教练部分信息以供前台展示
	 * @return
	 */
	public String  getListOfCoachByAjax(){
		
		try {
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber==0?3:pageNumber), getSqlAndParam().get(0).toString()
					, "select count(1) from("+getSqlAndParam().get(0).toString()+")",  
					(Object[])getSqlAndParam().get(1));
			Map<String, Object>  mapValue=new HashMap<String, Object>();
			mapValue.put("pageForm", pageForm);
			JSONObject json = JSONObject.fromObject(mapValue);
			result=json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return "success";
	}
	/**
	 * @time
	 * @author zc
	 * @note 查询所有机构数据，且将List树形结构数据转换成树形json结构
	 * @return
	 */
	public String getAllCOrganizationListByPID() {
		Dssrstudent dssrstudent=(Dssrstudent) session.get("dssrstudent");
	       List<COrganization> menuList = cOrganizationService.getOrganizationList(dssrstudent.getStudCompanyid()) ;
	       
	       List<COrganization> nodeList = new ArrayList<COrganization>();  
	       for(COrganization node1 : menuList){  
	           boolean mark = false;  
	           for(COrganization node2 : menuList){
	               if(node1.getOrganizationPID()!=null && node1.getOrganizationPID().equals(node2.getOrganizationID())){  
	                   mark = true;  
	                   if(node2.getChildren() == null)  
	                   node2.setChildren(new ArrayList<COrganization>());  
	                   node2.getChildren().add(node1);   
	                   break;  
	               }  
	           }  
	           if(!mark&&node1.getOrganizationPID().equals(dssrstudent.getStudCompanyid())){  
	               nodeList.add(node1);   
	           }  
	       }  
	       //转为json格式        
	       JsonConfig jsonConfig = new JsonConfig();
		   jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出  
		   JSONArray json = JSONArray.fromObject(nodeList,jsonConfig);  
	       result=json.toString(); 
	       
     return "success";
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


	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Ddsrcoach getDdsrcoach() {
		return ddsrcoach;
	}
	public void setDdsrcoach(Ddsrcoach ddsrcoach) {
		this.ddsrcoach = ddsrcoach;
	}
	public List<Object> getArrayDateList() {
		return arrayDateList;
	}
	public void setArrayDateList(List<Object> arrayDateList) {
		this.arrayDateList = arrayDateList;
	}
	public Staff getDtHrStaff() {
		return dtHrStaff;
	}
	public void setDtHrStaff(Staff dtHrStaff) {
		this.dtHrStaff = dtHrStaff;
	}
	public Dssrsubject getDssrsubject() {
		return dssrsubject;
	}
	public void setDssrsubject(Dssrsubject dssrsubject) {
		this.dssrsubject = dssrsubject;
	}
	public Ddsrreservationrecord getDdsrreservationrecord() {
		return ddsrreservationrecord;
	}
	public void setDdsrreservationrecord(Ddsrreservationrecord ddsrreservationrecord) {
		this.ddsrreservationrecord = ddsrreservationrecord;
	}
	public Dssrstudent getDssrstudent() {
		return dssrstudent;
	}
	public void setDssrstudent(Dssrstudent dssrstudent) {
		this.dssrstudent = dssrstudent;
	}
	public String getUnionCompanyID() {
		return unionCompanyID;
	}
	public void setUnionCompanyID(String unionCompanyID) {
		this.unionCompanyID = unionCompanyID;
	}
	public DepartmentPost getDepartmentPost() {
		return departmentPost;
	}
	public void setDepartmentPost(DepartmentPost departmentPost) {
		this.departmentPost = departmentPost;
	}
	
	
	
}
