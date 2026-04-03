package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.ddsr.Ddsrcontent;
import hy.ea.bo.ddsr.Ddsrsyllabus;
import hy.ea.bo.ddsr.Ddsrtrainingrecord;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.view.ViewProgressStudent;
import hy.ea.bo.human.Staff;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/** 
 * @author zc
 * @version 1.01 
 * @describe 学员登记表
 */
@Controller
@Scope("prototype")
public class TrainingRegistrationAction{
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private PageForm pageForm;
	private String results;
	private int pageNumber;
	private String search;
	private Map<String, Object> session=ActionContext.getContext().getSession();
	private CAccount account=(CAccount) session.get("account");
	
	private ViewProgressStudent viewProgressStudent;
	
	private DtDrivingPrincipal  dtDrivingPrincipal;
	
	private Staff cstaff;
	
	private Map<String, Ddsrtrainingrecord> ddsrtrainingrecordMap;
	
	private Ddsrtrainingrecord ddsrtrainingrecord;
	
	private Ddsrsyllabus ddsrsyllabus;
	
	private Ddsrcontent ddsrcontent;
	/**
	 * 
	 * @param
	 * 学员跟踪查询
	 */
	public String toSearchOfViewTrainingRegistration(){
		session.put("searchOfProgressStudent", viewProgressStudent);
		return getListOfViewTrainingRegistration();
	}
	/**
	 * @param
	 * @return
	 * 获取学员跟踪列表
	 */
	public String getListOfViewTrainingRegistration(){
		List<Object> params=new ArrayList<Object>();
		String sql=" select ddp.studentid,max(dhs.recordcode) studentcode,max(ddp.registrationdate) registrationdate," +
				"max(upper(ddp.registrationcarname)) registrationcarname,max(ddp.schoolopendate) schoolopendate," +
				"max(ddp.studentname) studentname,max(ddp.studentsex) studentsex,max(ddp.studentphone) studentphone," +
				"max(ddp.studentcard) studentcard,max(ddp.barcode) barcode," +
				"wmsys.wm_concat(decode(ddpt.coachname,null,'无',ddpt.coachname) || '(' || decode(ddpt.docstatus,'04','科四','03','科三','02','科二','科一') ||')') coach," +
				"sum(case when ddpt.docstatus='01'  then '1' else '0' end ) entrance," +
				"sum(case when ddpt.docstatus='01' and ddpt.studentstatus in ('08','06','07') then '1' else '0' end ) subjectone," +
				"sum(case when ddpt.docstatus='02' and ddpt.studentstatus in ('05','06','07') then '1' else '0' end ) subjecttwo," +
				"sum(case when ddpt.docstatus='03' and ddpt.studentstatus in ('05','06','07') then '1' else '0' end ) subjectthree," +
				"sum(case when ddpt.docstatus='04' and ddpt.studentstatus in ('05','06','07') then '1' else '0' end ) subjectfour," +
				"sum(case when ddpt.docstatus='04' and ddpt.studentstatus in ('07') then '1' else '0' end ) graduation ," +
				"ddp.companyid  companyid,wm_concat(ddpt.docstatus || ddpt.studentstatus) status" +
				" from dt_driving_principal ddp" +
				" left join dt_driving_principal_type ddpt on ddpt.drivingprincipalid=ddp.drivingprincipalid" +
				" left join dt_hr_staff dhs on dhs.staffid=ddp.studentid";
		sql+=" where 1=1";
		sql+=" and ddp.companyid=? ";
		params.add(account.getCompanyID());
		if(search!=null&&"search".equals(search)){
			ViewProgressStudent viewProgressStudent=(ViewProgressStudent)session.get("searchOfProgressStudent");
			if(viewProgressStudent!=null){
				if(viewProgressStudent.getSearchStaDate()!=null&&!"".equals(viewProgressStudent.getSearchStaDate())&&
						viewProgressStudent.getSearchEndDate()!=null&&!"".equals(viewProgressStudent.getSearchEndDate())){
					sql+=" and ddp.registrationdate between ? and ? ";
					params.add(viewProgressStudent.getSearchStaDate());
					params.add(viewProgressStudent.getSearchEndDate());
				}
				if(viewProgressStudent.getCoach()!=null&&!"".equals(viewProgressStudent.getCoach())){
					sql+=" and ddpt.coach like ? ";
					params.add("%" + viewProgressStudent.getCoach().trim() + "%");
				}
				if(viewProgressStudent.getStudentname()!=null&&!"".equals(viewProgressStudent.getStudentname())){
					sql+=" and ddp.studentname like ? ";
					params.add("%" + viewProgressStudent.getStudentname().trim() + "%");
				}
				if(viewProgressStudent.getStudentcard()!=null&&!"".equals(viewProgressStudent.getStudentcard())){
					sql+=" and dhs.studentcard like ? ";
					params.add("%" + viewProgressStudent.getStudentcard().trim() + "%");
				}
			}
		}else{
			sql+=" and ddp.registrationdate between ? and ? ";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			params.add(calendar.getTime());
			params.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getCurrentDate("yyyy-MM-dd")));
		}
		
		sql+=" group by ddp.companyid,ddp.studentid";
		
		if(search!=null&&"search".equals(search)){
			ViewProgressStudent viewProgressStudent=(ViewProgressStudent)session.get("searchOfProgressStudent");
			if(viewProgressStudent!=null){
				if(viewProgressStudent.getTheProgress()!=null&&!"".equals(viewProgressStudent.getTheProgress())){
					sql+=" having wm_concat(ddpt.docstatus || ddpt.studentstatus) like ? ";
					params.add("%" + viewProgressStudent.getTheProgress().trim() + "%");
				}
			}
		}
		sql+=" order by max(ddp.registrationdate) desc nulls last  ";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) from ("+sql+")", params.toArray());
		
		
		
		return "list";
	}	
	/**
	 * @time 2014-09-16
	 * @author zc
	 * @return
	 * 获得培训记录登记表数据（查询展示部分内容）
	 */
	public String getRegistrationOftrainingForPrintPreview(){
		try {
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{cstaff.getStaffID()});
			String  hql=" from DtDrivingPrincipal where studentcard=? ";
			dtDrivingPrincipal=(DtDrivingPrincipal) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cstaff.getStaffIdentityCard()});
			String hql1=" from Ddsrsyllabus where companyID=? and type=? order by serial ";
			List<BaseBean>  beanList=baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID(),dtDrivingPrincipal.getRegistrationcarname().toUpperCase()});
			ActionContext.getContext().put("beanList", beanList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return "registrationOftrainingForPrintPreview";
	}
	/**
	 * @time 2014-09-16
	 * @author zc
	 * @return
	 * 跳转至学员登记表编辑页面
	 */
	public String toRegistrationOftraining(){
		try {
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{cstaff.getStaffID()});
			String  hql=" from DtDrivingPrincipal where studentcard=? ";
			dtDrivingPrincipal=(DtDrivingPrincipal) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cstaff.getStaffIdentityCard()});
			String hql1=" from Ddsrtrainingrecord where companyID=? and ddsrstudentid=? order by addtime desc";
			List<BaseBean>  beanList=baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID(),dtDrivingPrincipal.getStudentid()});
			ActionContext.getContext().put("beanList", beanList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return "editRegistrationOftraining";
	}
	/**
	 * @time 2014-09-22
	 * @author zc
	 * @return 
	 * 添加培训起止时间 与 学员教练培训意见
	 */
	public String toSaveDdsrtrainingrecordByAjax(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		List<BaseBean>  beanList=new ArrayList<BaseBean>();
		
		for (Ddsrtrainingrecord ddst : ddsrtrainingrecordMap.values()) {
			if(null==ddst.getId()||"".equals(ddst.getId())){
				ddst.setId(serverService.getServerID("ddsrtrainingrecord"));
			}
			ddst.setCompanyID(account.getCompanyID());
			ddst.setAddtime(new Date());
			beanList.add(ddst);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("message", "操作成功");
		JSONObject json = JSONObject.fromObject(mapValue);
		results=json.toString();
		return "success";
	}
	
	/**
	 * @time 2014-09-11
	 * @author zc
	 * @return
	 * 获得培训起止时间 与 学员教练培训意见
	 */
	public String getListOfDdsrtrainingrecordByAjax(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String hql=" from Ddsrtrainingrecord where companyID=? and ddsrstudentid=?";
		List<BaseBean>  beanList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),ddsrtrainingrecord.getDdsrstudentid()});
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("beanList", beanList);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject json = JSONObject.fromObject(mapValue,jsonConfig);
		results=json.toString();
		return "success";
	}
	/**
	 * @time 2014-09-11
	 * @author zc
	 * @return
	 * 删除学员培训记录
	 */
	public String delOfDdsrtrainingrecordByAjax(){
		baseBeanService.deleteBeanByKey(Ddsrtrainingrecord.class, ddsrtrainingrecord.getKey());
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("message", "删除成功!");
		JSONObject json = JSONObject.fromObject(mapValue);
		results=json.toString();
		return "success";
	}
	
	/**
	 * @time 2014-09-11
	 * @author zc
	 * @return
	 * 获得培训项目
	 */
	public String getListOfDdsrsyllabusByAjax(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String hql=" from Ddsrsyllabus where companyID=? and subject=? and type=?";
		List<BaseBean>  beanList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),ddsrsyllabus.getSubject(),ddsrsyllabus.getType().toUpperCase()});
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("beanList", beanList);
		JSONObject json = JSONObject.fromObject(mapValue);
		results=json.toString();
		return "success";
	}
	/**
	 * @time 2014-09-11
	 * @author zc
	 * @return
	 * 获得培训项目
	 */
	public String getListOfDetailsContentOfByAjax(){		
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		List<Object> params=new ArrayList<Object>();
		String hql=" from Ddsrcontent where 1=1 ";
		hql+=" and  companyID=? ";
		params.add(account.getCompanyID());
		if(ddsrcontent!=null){
			if(ddsrcontent.getType()!=null&&!"".equals(ddsrcontent.getType())){
				hql+=" and type = ? ";
				params.add(ddsrcontent.getType().trim());
			}
			if(ddsrcontent.getSubject()!=null&&!"".equals(ddsrcontent.getSubject())){
				hql+=" and subject = ? ";
				params.add(ddsrcontent.getSubject().trim());
			}
			if(ddsrcontent.getProgram()!=null&&!"".equals(ddsrcontent.getProgram())){
				hql+=" and program = ? ";
				params.add(ddsrcontent.getProgram().trim());
			}
		}
		HttpServletRequest request=ServletActionContext.getRequest();
		String word=request.getParameter("word");
		hql+=" and content like ? ";
		params.add("%"+word.trim()+"%");
		
		List<BaseBean>  beanList=baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		JSONArray array = new JSONArray();
		for (int i = 0; i < beanList.size(); i++) {
			Ddsrcontent d=(Ddsrcontent)beanList.get(i);
			Map<String, Object>  mapValue=new HashMap<String, Object>();
			mapValue.put("content", d.getContent());
			array.add(JSONObject.fromObject(mapValue));
		}
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("data", array);
		/*JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出
		 */		
		JSONObject json = JSONObject.fromObject(mapValue);
		results=json.toString();
		return "success";
	}
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
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

	public ViewProgressStudent getViewProgressStudent() {
		return viewProgressStudent;
	}

	public void setViewProgressStudent(ViewProgressStudent viewProgressStudent) {
		this.viewProgressStudent = viewProgressStudent;
	}
	public Staff getCstaff() {
		return cstaff;
	}
	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}
	public DtDrivingPrincipal getDtDrivingPrincipal() {
		return dtDrivingPrincipal;
	}
	public void setDtDrivingPrincipal(DtDrivingPrincipal dtDrivingPrincipal) {
		this.dtDrivingPrincipal = dtDrivingPrincipal;
	}
	public Map<String, Ddsrtrainingrecord> getDdsrtrainingrecordMap() {
		return ddsrtrainingrecordMap;
	}
	public void setDdsrtrainingrecordMap(
			Map<String, Ddsrtrainingrecord> ddsrtrainingrecordMap) {
		this.ddsrtrainingrecordMap = ddsrtrainingrecordMap;
	}
	public Ddsrtrainingrecord getDdsrtrainingrecord() {
		return ddsrtrainingrecord;
	}
	public void setDdsrtrainingrecord(Ddsrtrainingrecord ddsrtrainingrecord) {
		this.ddsrtrainingrecord = ddsrtrainingrecord;
	}
	public Ddsrsyllabus getDdsrsyllabus() {
		return ddsrsyllabus;
	}
	public void setDdsrsyllabus(Ddsrsyllabus ddsrsyllabus) {
		this.ddsrsyllabus = ddsrsyllabus;
	}
	public Ddsrcontent getDdsrcontent() {
		return ddsrcontent;
	}
	public void setDdsrcontent(Ddsrcontent ddsrcontent) {
		this.ddsrcontent = ddsrcontent;
	}
	
	
	
	
}
