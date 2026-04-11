package hy.ea.ddsr.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.ddsr.Ddsrcoach;
import hy.ea.bo.ddsr.Ddsrworktime;
import hy.ea.bo.ddsr.Dssrsubject;
import hy.ea.bo.ddsr.ReDdsrcoachDssrsubject;
import hy.ea.bo.driving.Identification;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 预约培训系统的教练管理
 * 
 * @author zhb
 * 
 */
@Controller
@Scope("prototype")
public class DdsrCoachManagerAction {
	private static final Logger logger = LoggerFactory.getLogger(DdsrCoachManagerAction.class);

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String innerAction;
	
	private Staff staff;//社会人力对象Bean
	private Ddsrcoach coach;//教练
	private Dssrsubject subject;//科目对象Bean
	private ReDdsrcoachDssrsubject reCoachSubj;//教练和科目对应(一对一)
	private Ddsrworktime ddsrWorkTime;//工作时间段
	
	private List<BaseBean> subjectList;//本公司下的全部科目
	
	private String delCoach;// 删除教练的ID串儿
	private String coacKey2;//教练表的主键,如果有,则说明教练表中已经存在(只能更新);如果没有,说明教练表中不存在(需要插入)
	
	/*查询条件: 姓名、IC卡编号、车辆牌号、驾龄、状态、教授科目、准教类型、是否星级*/
	private String searchName;
	private String searchIcNumber;
	private String searchCarNumber;
	private Byte searchJialing;
	private Byte searchZhuangtai;	
	private String searchTeachSubj;
	private String searchTeachType;
	private Byte searchStar;
	private String searchIsrecommend;//推荐 取消推荐
	private String searchIsrecommendS;//集团推荐 取消推荐
	
	private String zjWotiStrdateHour;
	private String zjWotiStrdateTime;
	private String zjWotiEnddateHour;
	private String zjWotiEnddateTime;
	private String swWotiStrdateHour;
	private String swWotiStrdateTime;
	private String swWotiEnddateHour;
	private String swWotiEnddateTime;
	private String xwWotiStrdateHour;
	private String xwWotiStrdateTime;
	private String xwWotiEnddateHour;
	private String xwWotiEnddateTime;
	private String wjWotiStrdateHour;
	private String wjWotiStrdateTime;
	private String wjWotiEnddateHour;
	private String wjWotiEnddateTime;
	
	private String buttonFlag;//点“确认”按钮和"设为本校常规上班时间"按钮的标识
	private String selCoach;// 选择教练的ID串儿
	private String result;//ajax返回信息
	private String isrecommendStr;//设置推荐教练的ID串
	private String isrecommendTrue;//推荐 取消推荐
	/**
	 * 
	 *@param 集团公司标识符  判断是否是集团公司请求
	 */
	private String  companyGroupLogo;
	
	public String doDdsrCoachManagerAction() {
		if (innerAction == null || "".equals(innerAction.trim()))
			return getDdsrCoachList();
		else if ("showDdsrCoachList".equals(innerAction.trim()))//列表显示
			return getDdsrCoachList();
		else if ("updateDdsrCoach".equals(innerAction.trim()))//修改
			return updateDdsrCoach();
		else if ("delDdsrCoach".equals(innerAction.trim()))//删除(逻辑删除)
			return delDdsrCoach();
		else if ("setWorkTime".equals(innerAction.trim()))//设置工作时段
			return setWorkTime();
		else
			return getDdsrCoachList();		
	}
	
	private String setWorkTime(){
		if (ddsrWorkTime.getWotiType()==10){
			return setWorkTime4Company();
		}else{
			return setWorkTime4Coach();
		}		
	}
	
	private String setWorkTime4Company(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String companyID = account.getCompanyID();
		
		Ddsrworktime zjDdsrworktime = new Ddsrworktime();
		zjDdsrworktime.setDdsrcoach(null);		
		zjDdsrworktime.setWotiCompanyid(companyID);
		zjDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
		zjDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
		zjDdsrworktime.setWotiClass(Short.parseShort("10"));
		zjDdsrworktime.setWotiStrdate(zjWotiStrdateHour + ":" + zjWotiStrdateTime);
		zjDdsrworktime.setWotiEnddate(zjWotiEnddateHour + ":" + zjWotiEnddateTime);
		zjDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
		zjDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
		zjDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
		
		Ddsrworktime swDdsrworktime = new Ddsrworktime();
		swDdsrworktime.setDdsrcoach(null);		
		swDdsrworktime.setWotiCompanyid(companyID);
		swDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
		swDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
		swDdsrworktime.setWotiClass(Short.parseShort("20"));
		swDdsrworktime.setWotiStrdate(swWotiStrdateHour + ":" + swWotiStrdateTime);
		swDdsrworktime.setWotiEnddate(swWotiEnddateHour + ":" + swWotiEnddateTime);
		swDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
		swDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
		swDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
		
		Ddsrworktime xwDdsrworktime = new Ddsrworktime();
		xwDdsrworktime.setDdsrcoach(null);		
		xwDdsrworktime.setWotiCompanyid(companyID);
		xwDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
		xwDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
		xwDdsrworktime.setWotiClass(Short.parseShort("30"));
		xwDdsrworktime.setWotiStrdate(xwWotiStrdateHour + ":" + xwWotiStrdateTime);
		xwDdsrworktime.setWotiEnddate(xwWotiEnddateHour + ":" + xwWotiEnddateTime);
		xwDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
		xwDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
		xwDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
		
		Ddsrworktime wjDdsrworktime = new Ddsrworktime();
		wjDdsrworktime.setDdsrcoach(null);		
		wjDdsrworktime.setWotiCompanyid(companyID);
		wjDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
		wjDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
		wjDdsrworktime.setWotiClass(Short.parseShort("40"));
		wjDdsrworktime.setWotiStrdate(wjWotiStrdateHour + ":" + wjWotiStrdateTime);
		wjDdsrworktime.setWotiEnddate(wjWotiEnddateHour + ":" + wjWotiEnddateTime);
		wjDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
		wjDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
		wjDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
		
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();	
		baseBeansList.add(zjDdsrworktime);
		baseBeansList.add(swDdsrworktime);
		baseBeansList.add(xwDdsrworktime);
		baseBeansList.add(wjDdsrworktime);
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);		
		return (buttonFlag.equals("confirm")) ? "success" : null;
	}
	
	private String setWorkTime4Coach(){
		String[] arrCoach = selCoach.split(",");
		if (arrCoach.length>0)
		{
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");		
			String companyID = account.getCompanyID();
			List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			
			for (String coachKey : arrCoach) {
				Ddsrcoach coach = (Ddsrcoach)baseBeanService.getBeanByKey(Ddsrcoach.class, coachKey);
				
				Ddsrworktime zjDdsrworktime = new Ddsrworktime();
				zjDdsrworktime.setDdsrcoach(coach);		
				zjDdsrworktime.setWotiCompanyid(companyID);
				zjDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
				zjDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
				zjDdsrworktime.setWotiClass(Short.parseShort("10"));
				zjDdsrworktime.setWotiStrdate(zjWotiStrdateHour + ":" + zjWotiStrdateTime);
				zjDdsrworktime.setWotiEnddate(zjWotiEnddateHour + ":" + zjWotiEnddateTime);
				zjDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
				zjDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
				zjDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
				
				Ddsrworktime swDdsrworktime = new Ddsrworktime();
				swDdsrworktime.setDdsrcoach(coach);		
				swDdsrworktime.setWotiCompanyid(companyID);
				swDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
				swDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
				swDdsrworktime.setWotiClass(Short.parseShort("20"));
				swDdsrworktime.setWotiStrdate(swWotiStrdateHour + ":" + swWotiStrdateTime);
				swDdsrworktime.setWotiEnddate(swWotiEnddateHour + ":" + swWotiEnddateTime);
				swDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
				swDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
				swDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
				
				Ddsrworktime xwDdsrworktime = new Ddsrworktime();
				xwDdsrworktime.setDdsrcoach(coach);		
				xwDdsrworktime.setWotiCompanyid(companyID);
				xwDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
				xwDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
				xwDdsrworktime.setWotiClass(Short.parseShort("30"));
				xwDdsrworktime.setWotiStrdate(xwWotiStrdateHour + ":" + xwWotiStrdateTime);
				xwDdsrworktime.setWotiEnddate(xwWotiEnddateHour + ":" + xwWotiEnddateTime);
				xwDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
				xwDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
				xwDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
				
				Ddsrworktime wjDdsrworktime = new Ddsrworktime();
				wjDdsrworktime.setDdsrcoach(coach);		
				wjDdsrworktime.setWotiCompanyid(companyID);
				wjDdsrworktime.setWotiStrdaydate(ddsrWorkTime.getWotiStrdaydate());
				wjDdsrworktime.setWotiEnddaydate(ddsrWorkTime.getWotiEnddaydate());
				wjDdsrworktime.setWotiClass(Short.parseShort("40"));
				wjDdsrworktime.setWotiStrdate(wjWotiStrdateHour + ":" + wjWotiStrdateTime);
				wjDdsrworktime.setWotiEnddate(wjWotiEnddateHour + ":" + wjWotiEnddateTime);
				wjDdsrworktime.setWotiCreatedate(Calendar.getInstance().getTime());
				wjDdsrworktime.setWotiUpdatedate(Calendar.getInstance().getTime());
				wjDdsrworktime.setWotiType(ddsrWorkTime.getWotiType());
				
				baseBeansList.add(zjDdsrworktime);
				baseBeansList.add(swDdsrworktime);
				baseBeansList.add(xwDdsrworktime);
				baseBeansList.add(wjDdsrworktime);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		}
		return "success";
	}
	
	private void getAllSubjects()
	{
		String hql = " from Dssrsubject where subjCompanyid = ?";
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String companyID = account.getCompanyID();
		setSubjectList(baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{companyID}));
	}
	
	private String delDdsrCoach() {
		if (delCoach!=null && !delCoach.trim().equals(""))
		{
			String[] ids = delCoach.split(",");			
			String[] sqls = new String[ids.length];
			List<Object[]> list = new ArrayList<Object[]>(ids.length);
			int i = 0;
			for (String id : ids) {
				sqls[i] = "update dt_hr_staff set staffstatus='98' where staffkey = ? ";	//逻辑删除
				list.add(new Object[]{id});
				i++;
			}
			baseBeanService.executeSqlsByParmsList(null, sqls, list);
		}		
		return null;
	}

	private String updateDdsrCoach() {
		//修改要做的事比较多，需要更新Staff，需要更新DdsrCoach，需要修改DdsrCoach与科目的关联关系（Re_ddsrCoach_dssrSubject）
		String sqlUpdateStaff = "update dt_hr_Staff set staffName=?,staffIdentityCard=?,reference=?,staffAddress=?,staffDesc=? where staffKey=?";
		Object[] paramsUpdateStaff ={staff.getStaffName(),staff.getStaffIdentityCard(),staff.getReference(),staff.getStaffAddress(),staff.getStaffDesc(),staff.getStaffKey()};
		
		String sqlDelReDssrcoachDssrsubject = "delete Re_ddsrCoach_dssrSubject where coac_key = ?";
		Object[] paramsDelReDssrcoachDssrsubject = {staff.getStaffKey()};
		
		//默认是插入coach
		/*String sqlAddOrUpdCoach = "insert into ddsrcoach (coac_key, coac_companyid, coac_releasedate, coac_icnumber, coac_level, coac_teachtype, coac_status, " +
								  "coac_star, coac_createdate, coac_updatedate, coac_carnumber, coac_paperscode, coac_satisfaction)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";*/
		String sqlAddOrUpdCoach = "insert into ddsrcoach (coac_key, coac_companyid, coac_releasedate, coac_icnumber, coac_level, coac_teachtype, coac_status, " +
				  "coac_star, coac_carnumber, coac_paperscode,coac_certificatedate)values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] paramsAddOrUpdCoach = {staff.getStaffKey(),coach.getCoacCompanyid(),coach.getCoacReleasedate(),coach.getCoacIcnumber(),coach.getCoacLevel(),coach.getCoacTeachtype(),
				coach.getCoacStatus(),coach.getCoacStar(),coach.getCoacCarNumber(),coach.getCoacPapersCode(),coach.getCoacCertificateDate()};
		
		if (coacKey2!=null && !"".equals(coacKey2.trim()))//教练表的主键,如果有,则说明教练表中已经存在(只能更新);如果没有,说明教练表中不存在(需要插入)
		{
			sqlAddOrUpdCoach = "update ddsrcoach set coac_releasedate=?,coac_icnumber=?,coac_level=?,coac_teachtype=?,coac_status=?,coac_star=?,coac_updatedate=?,coac_carnumber=?," +
					"coac_paperscode=?,coac_certificatedate=?  where coac_key=?";
			paramsAddOrUpdCoach[0] = coach.getCoacReleasedate();
			paramsAddOrUpdCoach[1] = coach.getCoacIcnumber();
			paramsAddOrUpdCoach[2] = coach.getCoacLevel();
			paramsAddOrUpdCoach[3] = coach.getCoacTeachtype();
			paramsAddOrUpdCoach[4] = coach.getCoacStatus();
			paramsAddOrUpdCoach[5] = coach.getCoacStar();
			paramsAddOrUpdCoach[6] = Calendar.getInstance().getTime();
			paramsAddOrUpdCoach[7] = coach.getCoacCarNumber();
			paramsAddOrUpdCoach[8] = coach.getCoacPapersCode();
			paramsAddOrUpdCoach[9] = coach.getCoacCertificateDate();
			paramsAddOrUpdCoach[10] = staff.getStaffKey();
		}
		
		String[] arrSqlAddReDdsrcoachDssrsubject = null;
		Object[][] arrParamsAddReDdsrcoachDssrsubject = null;
		String multiSubjKey = subject.getSubjKey();
		int sumSubjKey = 0;
		if (multiSubjKey!=null && !"".equals(multiSubjKey)){
			sumSubjKey = multiSubjKey.split(",").length;
			arrSqlAddReDdsrcoachDssrsubject = new String[sumSubjKey];
			arrParamsAddReDdsrcoachDssrsubject = new Object[sumSubjKey][];
			for (int i = 0; i < sumSubjKey; i++) {
				arrSqlAddReDdsrcoachDssrsubject[i] = "insert into re_ddsrcoach_dssrsubject(cosu_key, coac_key, subj_key)values(?, ?, ?)";
				arrParamsAddReDdsrcoachDssrsubject[i] = new Object[3];
				arrParamsAddReDdsrcoachDssrsubject[i][0] = serverService.getServerID("cosu_key");
				arrParamsAddReDdsrcoachDssrsubject[i][1] = staff.getStaffKey();
				arrParamsAddReDdsrcoachDssrsubject[i][2] = multiSubjKey.split(",")[i];
			}			
		}		
		
		String[] sqls = null;//{sqlUpdateStaff,sqlDelReDssrcoachDssrsubject,sqlAddOrUpdCoach};		
		List<Object[]> list = null;
		
		if (sumSubjKey>0){
			sqls = new String[3+sumSubjKey];
			sqls[0] = sqlUpdateStaff;
			sqls[1] = sqlDelReDssrcoachDssrsubject;
			sqls[2] = sqlAddOrUpdCoach;
			
			list = new ArrayList<Object[]>();
			list.add(paramsUpdateStaff);
			list.add(paramsDelReDssrcoachDssrsubject);
			list.add(paramsAddOrUpdCoach);			
		}
		
		for (int i = 0; i < sumSubjKey; i++) {
			sqls[3+i] = arrSqlAddReDdsrcoachDssrsubject[i];
			list.add(arrParamsAddReDdsrcoachDssrsubject[i]);
		}
				
		baseBeanService.executeSqlsByParmsList(null, sqls, list);		
		return "success";
	}

	private String getDdsrCoachList() {
		getAllSubjects();
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.lastIndexOf("from")), parms);
		
		return "showDdsrCoachList";		        
	}
	
	public void getWorkTimeByCoachs()
	{		
		if (selCoach!=null || !"".equals(selCoach)){
			String[] arrCoach = selCoach.split(",");
			Object[] params = new Object[arrCoach.length];
			StringBuffer sql = new StringBuffer("select distinct dwt.woti_class,dwt.woti_strdate,dwt.woti_enddate,to_char(woti_strdaydate,'yyyy-mm-dd'),to_char(woti_enddaydate,'yyyy-mm-dd') ");
			sql.append("from ddsrworktime dwt where dwt.coac_key in (");
			for (int i=0; i<arrCoach.length; i++)
			{
				sql.append((i<arrCoach.length-1) ? "?," : "?");
				params[i] = arrCoach[i];
			}
			sql.append(")");
			if (arrCoach.length==1)
			{
				sql.append(" and dwt.woti_updatedate = (select max(woti_updatedate) from ddsrworktime where coac_key = lower('" + params[0] + "'))");
			}
			sql.append(" order by dwt.woti_class");			
			List result = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params);	
			
			JSONArray json = JSONArray.fromObject(result);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");			
			try {
				response.getWriter().print(json.toString());
			} catch (IOException e) {				
				logger.error("操作异常", e);
			}
		}
	}
	
	public void getWorkTimeByCompany()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String companyID = account.getCompanyID();
		String sql = "select woti_class,woti_strdate,woti_enddate,to_char(woti_strdaydate,'yyyy-mm-dd'),to_char(woti_enddaydate,'yyyy-mm-dd') from ddsrworktime " +
					 "where woti_updatedate = (select max(woti_updatedate) from ddsrworktime where coac_key is null) " +
                     "and woti_companyid = ? order by woti_class";
		List result = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyID});
		
		JSONArray json = JSONArray.fromObject(result);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");			
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {				
			logger.error("操作异常", e);
		}
		
	}
	
	private List<Object> getList()
	{
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		/*查询条件: 姓名、IC卡编号、车辆牌号、驾龄、状态、教授科目、是否星级*/
		if (search != null && search.equals("search")) {// 点击查询按钮过来的
			Map<String, Object> map = new HashMap<String, Object>();			
			map.put("searchName", searchName);
			map.put("searchIcNumber", searchIcNumber);
			map.put("searchCarNumber", searchCarNumber);
			map.put("searchJialing", searchJialing);
			map.put("searchZhuangtai", searchZhuangtai);			
			map.put("searchTeachType", searchTeachType);
			map.put("searchStar", searchStar);	
			map.put("searchIsrecommendTure", searchIsrecommend);
			map.put("companyGroupLogo", companyGroupLogo);
			session.put("coachSearchCriteriaInMap", map);// 将查询条件保存在session中
		}else if (search != null && search.equals("turnpage")) {// 翻页过来的
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) session.get("coachSearchCriteriaInMap");
			if (map!=null){
				searchName = (String)map.get("searchName");
				searchIcNumber = (String)map.get("searchIcNumber");
				searchCarNumber = (String)map.get("searchCarNumber");
				searchJialing = (Byte)map.get("searchJialing");
				searchZhuangtai = (Byte)map.get("searchZhuangtai");				
				searchTeachType = (String)map.get("searchTeachType");
				searchStar = (Byte)map.get("searchStar"); 
				companyGroupLogo = (String)map.get("companyGroupLogo");
				searchIsrecommend=(String)map.get("searchIsrecommendTure");
			}
		}else{//第一次进来的
			
		}
		
		StringBuffer childQuery = new StringBuffer("");
		childQuery.append(" (select coac.coac_key,");
		childQuery.append(" WM_CONCAT(redcds.subj_key) as subj_key,");
		childQuery.append(" WM_CONCAT(decode(dssu.subj_type,10,'科目一',20,'科目二',30,'科目三','科目四')) as subj_type");
		childQuery.append(" FROM ddsrcoach coac");
		childQuery.append(" left join Re_ddsrCoach_dssrSubject redcds");
		childQuery.append(" on coac.coac_key = redcds.coac_key");
		childQuery.append(" left join dssrsubject dssu");
		childQuery.append(" on redcds.subj_key = dssu.subj_key");
		childQuery.append(" group by coac.coac_key) newCoach ");
		
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT HRST.STAFFKEY   AS COAC_KEY,"); //0
		sql.append("HRST.STAFFNAME         AS STAFFNAME,"); //1
		sql.append("HRST.USEDNMAE          AS USEDNAME,");
		sql.append("HRST.SEX               AS SEX,");
		sql.append("HRST.BIRTHDAY          AS BIRTHDAY,");
		sql.append("newCoach.SUBJ_TYPE     AS SUBJ_TYPE,");//教授科目 5    逗号分隔好的
		sql.append("HRST.STAFFADDRESS      AS STAFFADDRESS,");
		sql.append("HRST.PHOTO             AS PHOTO,");		//7
		sql.append("HRST.REFERENCE         AS REFERENCE,");//联系电话 8
		sql.append("HRST.NATIVEPLACE       AS NATIVEPLACE,");
		sql.append("HRST.STAFFDESC         AS STAFFDESC,");
		sql.append("COACH.COAC_PAPERSCODE  AS COAC_PAPERSCODE,");//教练员证号  11
		sql.append("COACH.COAC_RELEASEDATE AS COAC_RELEASEDATE,");//驾驶发证日期 12
		sql.append("COACH.COAC_ICNUMBER    AS COAC_ICNUMBER,");//IC卡号 13
		sql.append("COACH.COAC_LEVEL       AS COAC_LEVEL,");//等级 14
		sql.append("COACH.COAC_TEACHTYPE   AS COAC_TEACHTYPE,");//准教类型 	15
		sql.append("COACH.COAC_STATUS      AS COAC_STATUS,");
		sql.append("COACH.COAC_STAR        AS COAC_STAR,");//是否星级 17
		sql.append("COACH.COAC_CARNUMBER   AS COAC_CARNUMBER,");//车辆牌照 	18		
		sql.append("HRST.STAFFIDENTITYCARD AS STAFFIDENTITYCARD, ");
		sql.append("COACH.COAC_KEY         AS COAC_KEY2, ");
		sql.append("dtc.companyid          AS companyid, ");//所属公司 21
		sql.append("newCoach.subj_key      AS subj_key, ");//教授科目id 22   逗号分隔好的
		sql.append("trunc((sysdate-COACH.COAC_RELEASEDATE)/365) as JIALING,");//驾龄 23
		sql.append("hrst.STATUS            AS STATUS,"); //24
		sql.append("hrst.staffid           AS staffid,");//staffid	25
		sql.append("COACH.COAC_ISRECOMMEND  AS COAC_ISRECOMMEND,");//公司推荐	26
		sql.append("trunc((sysdate-COACH.COAC_CERTIFICATEDATE)/365) as JIAOLING,");//教龄 27
		sql.append("COACH.COAC_CertificateDate AS COAC_CertificateDate, ");//教练发证日期 28
		sql.append("COACH.COAC_ISRECOMMENDS     AS COAC_ISRECOMMENDS ");//集团推荐	29
		
		sql.append(" from DT_HR_STAFF HRST ");
		sql.append(" LEFT JOIN ddsrCoach coach ");
		sql.append(" ON HRST.STAFFKEY = coach.coac_key ");
		sql.append(" LEFT JOIN DTCONTACTRELATION DTC ");
		sql.append(" ON HRST.STAFFID = DTC.STAFFID ");
		/*sql.append(" LEFT JOIN RE_DDSRCOACH_DSSRSUBJECT RDCDS ");
		sql.append(" ON coach.coac_key = RDCDS.COAC_KEY ");
		sql.append(" LEFT JOIN DSSRSUBJECT DSU ");
		sql.append(" ON RDCDS.SUBJ_KEY = DSU.SUBJ_KEY ");*/
		sql.append(" left join " );
		sql.append(childQuery);
		sql.append(" on coach.COAC_KEY = newCoach.COAC_KEY ");
		sql.append(" WHERE DTC.RELATION = '教练' "); //往来关系必须是教练		
		
		/**
		 * sql连接集团及集团下分公司的ID
		 */
		String orsql="";
		List<Object> parmsCompanyIDS=new ArrayList<Object>();
		if("companyGroupLogo".equals(companyGroupLogo)){
			DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
			dcCompany.add(Restrictions.eq("companyStatus", "00"));
			dcCompany.add(Restrictions.or(Restrictions.eq("companyPID", ((CAccount) session.get("account")).getCompanyID()), Restrictions.eq("companyID", ((CAccount) session.get("account")).getCompanyID())));
			List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);
			String orWhere="";
			for (BaseBean baseBean : listCompany) {
				Company company=(Company)baseBean;
				orWhere+="  dtc.companyid=? or ";
				parmsCompanyIDS.add(company.getCompanyID());
			}
			orsql+=" and ( "+orWhere.substring(0, orWhere.lastIndexOf("or"))+" ) ";
			sql.append(orsql);
			parms.addAll(parmsCompanyIDS);
		}else{
			sql.append(" and dtc.companyid = ? ");
			parms.add(((CAccount) session.get("account")).getCompanyID());
		}
		sql.append(" and HRST.STAFFSTATUS <> '98' ");
		
		//查询条件
		if (null != searchName && !"".equals(searchName.trim())) {
			sql.append(" and hrst.staffname like '%'||?||'%' ");
			parms.add(searchName);
		}
		
		if (null != searchIcNumber && !"".equals(searchIcNumber.trim())) {
			sql.append(" and coach.coac_ICnumber like '%'||?||'%' ");
			parms.add(searchIcNumber);
		}		
		
		if (null != searchCarNumber && !"".equals(searchCarNumber.trim())) {
			sql.append(" and coach.coac_carNumber like '%'||?||'%' ");
			parms.add(searchCarNumber);
		}
		
		if (null != searchJialing && searchJialing != 0) {
			switch (searchJialing) {
			case 10: //5年以下
				sql.append(" and trunc((sysdate-COACH.COAC_RELEASEDATE)/365)<=5 ");
				break;
			case 20: //5-10年
				sql.append(" and trunc((sysdate-COACH.COAC_RELEASEDATE)/365)>=5 and  trunc((sysdate-COACH.COAC_RELEASEDATE)/365)<=10 ");
				break;
			case 30: //10-20年
				sql.append(" and trunc((sysdate-COACH.COAC_RELEASEDATE)/365)>=10 and  trunc((sysdate-COACH.COAC_RELEASEDATE)/365)<=20 ");
				break;
			case 40: //20年以上
				sql.append(" and trunc((sysdate-COACH.COAC_RELEASEDATE)/365)>=20 ");
				break;
			default:
				break;
			}			
		}		
		
		
		if (null != searchTeachSubj && !"0".equals(searchTeachSubj.trim())) {
			sql.append(" and instr(newCoach.subj_key, ?)>0 ");
			parms.add(searchTeachSubj);
		}
						
		if (null != searchZhuangtai && searchZhuangtai != 0) {
			sql.append(" and coach.coac_status = ? ");
			parms.add(searchZhuangtai);
		}
				
		if (null != searchTeachType && !"".equals(searchTeachType.trim())) {
			sql.append(" and coach.coac_teachtype = ? ");
			parms.add(searchTeachType);
		}
		
		if (null != searchStar && searchStar != 0) {
			sql.append(" and coach.coac_star = ? ");
			parms.add(searchStar);
		}		
		
		if("companyGroupLogo".equals(companyGroupLogo)){
			sql.append(" and coach.coac_isrecommend = ? ");
			parms.add("1");
		}else{
			if (null != searchIsrecommend && !"".equals(searchIsrecommend)) {
				sql.append(" and coach.coac_isrecommend = ? ");
				parms.add(searchIsrecommend);
			}
		}
		result.add(sql.toString());
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 
	 * Ajax1推荐教练设置
	 * @return
	 */
	public String setRecommended(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql="";
		if("companyGroupLogo".equals(companyGroupLogo)){
			sql="update ddsrCoach set COAC_ISRECOMMENDS=? where coac_key = ? ";
		}else{
			sql="update ddsrCoach set coac_isrecommend=? where coac_key = ? ";
		}
		if (isrecommendStr!=null && !isrecommendStr.trim().equals("")&&isrecommendTrue!=null && !isrecommendTrue.trim().equals(""))
		{
			String[] ids = isrecommendStr.split(",");			
			String[] sqls = new String[ids.length];
			List<Object[]> list = new ArrayList<Object[]>(ids.length);
			int i = 0;
			for (String id : ids) {
				sqls[i] = sql;	//逻辑删除
				list.add(new Object[]{isrecommendTrue,id});
				i++;
			}
			baseBeanService.executeSqlsByParmsList(null, sqls, list);
		}		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", Integer.parseInt(isrecommendTrue));
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	public String getDelCoach() {
		return delCoach;
	}

	public void setDelCoach(String delCoach) {
		this.delCoach = delCoach;
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
	public String getInnerAction() {
		return innerAction;
	}
	public void setInnerAction(String innerAction) {
		this.innerAction = innerAction;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchIcNumber() {
		return searchIcNumber;
	}

	public void setSearchIcNumber(String searchIcNumber) {
		this.searchIcNumber = searchIcNumber;
	}

	public Byte getSearchZhuangtai() {
		return searchZhuangtai;
	}

	public void setSearchZhuangtai(Byte searchZhuangtai) {
		this.searchZhuangtai = searchZhuangtai;
	}

	public Byte getSearchStar() {
		return searchStar;
	}

	public void setSearchStar(Byte searchStar) {
		this.searchStar = searchStar;
	}


	public String getSearchCarNumber() {
		return searchCarNumber;
	}

	public void setSearchCarNumber(String searchCarNumber) {
		this.searchCarNumber = searchCarNumber;
	}

	public Byte getSearchJialing() {
		return searchJialing;
	}

	public void setSearchJialing(Byte searchJialing) {
		this.searchJialing = searchJialing;
	}

	public String getSearchTeachType() {
		return searchTeachType;
	}

	public void setSearchTeachType(String searchTeachType) {
		this.searchTeachType = searchTeachType;
	}
	
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Ddsrcoach getCoach() {
		return coach;
	}

	public void setCoach(Ddsrcoach coach) {
		this.coach = coach;
	}

	public Dssrsubject getSubject() {
		return subject;
	}

	public void setSubject(Dssrsubject subject) {
		this.subject = subject;
	}

	public ReDdsrcoachDssrsubject getReCoachSubj() {
		return reCoachSubj;
	}

	public void setReCoachSubj(ReDdsrcoachDssrsubject reCoachSubj) {
		this.reCoachSubj = reCoachSubj;
	}

	public String getSearchTeachSubj() {
		return searchTeachSubj;
	}

	public void setSearchTeachSubj(String searchTeachSubj) {
		this.searchTeachSubj = searchTeachSubj;
	}

	public List<BaseBean> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<BaseBean> subjectList) {
		this.subjectList = subjectList;
	}

	public String getCoacKey2() {
		return coacKey2;
	}

	public void setCoacKey2(String coacKey2) {
		this.coacKey2 = coacKey2;
	}

	public Ddsrworktime getDdsrWorkTime() {
		return ddsrWorkTime;
	}

	public void setDdsrWorkTime(Ddsrworktime ddsrWorkTime) {
		this.ddsrWorkTime = ddsrWorkTime;
	}

	public String getZjWotiStrdateHour() {
		return zjWotiStrdateHour;
	}

	public void setZjWotiStrdateHour(String zjWotiStrdateHour) {
		this.zjWotiStrdateHour = zjWotiStrdateHour;
	}

	public String getZjWotiStrdateTime() {
		return zjWotiStrdateTime;
	}

	public void setZjWotiStrdateTime(String zjWotiStrdateTime) {
		this.zjWotiStrdateTime = zjWotiStrdateTime;
	}

	public String getZjWotiEnddateHour() {
		return zjWotiEnddateHour;
	}

	public void setZjWotiEnddateHour(String zjWotiEnddateHour) {
		this.zjWotiEnddateHour = zjWotiEnddateHour;
	}

	public String getZjWotiEnddateTime() {
		return zjWotiEnddateTime;
	}

	public void setZjWotiEnddateTime(String zjWotiEnddateTime) {
		this.zjWotiEnddateTime = zjWotiEnddateTime;
	}

	public String getSwWotiStrdateHour() {
		return swWotiStrdateHour;
	}

	public void setSwWotiStrdateHour(String swWotiStrdateHour) {
		this.swWotiStrdateHour = swWotiStrdateHour;
	}

	public String getSwWotiStrdateTime() {
		return swWotiStrdateTime;
	}

	public void setSwWotiStrdateTime(String swWotiStrdateTime) {
		this.swWotiStrdateTime = swWotiStrdateTime;
	}

	public String getSwWotiEnddateHour() {
		return swWotiEnddateHour;
	}

	public void setSwWotiEnddateHour(String swWotiEnddateHour) {
		this.swWotiEnddateHour = swWotiEnddateHour;
	}

	public String getSwWotiEnddateTime() {
		return swWotiEnddateTime;
	}

	public void setSwWotiEnddateTime(String swWotiEnddateTime) {
		this.swWotiEnddateTime = swWotiEnddateTime;
	}

	public String getXwWotiStrdateHour() {
		return xwWotiStrdateHour;
	}

	public void setXwWotiStrdateHour(String xwWotiStrdateHour) {
		this.xwWotiStrdateHour = xwWotiStrdateHour;
	}

	public String getXwWotiStrdateTime() {
		return xwWotiStrdateTime;
	}

	public void setXwWotiStrdateTime(String xwWotiStrdateTime) {
		this.xwWotiStrdateTime = xwWotiStrdateTime;
	}

	public String getXwWotiEnddateHour() {
		return xwWotiEnddateHour;
	}

	public void setXwWotiEnddateHour(String xwWotiEnddateHour) {
		this.xwWotiEnddateHour = xwWotiEnddateHour;
	}

	public String getXwWotiEnddateTime() {
		return xwWotiEnddateTime;
	}

	public void setXwWotiEnddateTime(String xwWotiEnddateTime) {
		this.xwWotiEnddateTime = xwWotiEnddateTime;
	}

	public String getWjWotiStrdateHour() {
		return wjWotiStrdateHour;
	}

	public void setWjWotiStrdateHour(String wjWotiStrdateHour) {
		this.wjWotiStrdateHour = wjWotiStrdateHour;
	}

	public String getWjWotiStrdateTime() {
		return wjWotiStrdateTime;
	}

	public void setWjWotiStrdateTime(String wjWotiStrdateTime) {
		this.wjWotiStrdateTime = wjWotiStrdateTime;
	}

	public String getWjWotiEnddateHour() {
		return wjWotiEnddateHour;
	}

	public void setWjWotiEnddateHour(String wjWotiEnddateHour) {
		this.wjWotiEnddateHour = wjWotiEnddateHour;
	}

	public String getWjWotiEnddateTime() {
		return wjWotiEnddateTime;
	}

	public void setWjWotiEnddateTime(String wjWotiEnddateTime) {
		this.wjWotiEnddateTime = wjWotiEnddateTime;
	}

	public String getButtonFlag() {
		return buttonFlag;
	}

	public void setButtonFlag(String buttonFlag) {
		this.buttonFlag = buttonFlag;
	}

	public String getSelCoach() {
		return selCoach;
	}

	public void setSelCoach(String selCoach) {
		this.selCoach = selCoach;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIsrecommendStr() {
		return isrecommendStr;
	}

	public void setIsrecommendStr(String isrecommendStr) {
		this.isrecommendStr = isrecommendStr;
	}

	public String getIsrecommendTrue() {
		return isrecommendTrue;
	}

	public void setIsrecommendTrue(String isrecommendTrue) {
		this.isrecommendTrue = isrecommendTrue;
	}

	public String getSearchIsrecommend() {
		return searchIsrecommend;
	}

	public void setSearchIsrecommend(String searchIsrecommend) {
		this.searchIsrecommend = searchIsrecommend;
	}

	public String getSearchIsrecommendS() {
		return searchIsrecommendS;
	}

	public void setSearchIsrecommendS(String searchIsrecommendS) {
		this.searchIsrecommendS = searchIsrecommendS;
	}

	public String getCompanyGroupLogo() {
		return companyGroupLogo;
	}

	public void setCompanyGroupLogo(String companyGroupLogo) {
		this.companyGroupLogo = companyGroupLogo;
	}

	
	
	
	
}
