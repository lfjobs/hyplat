package hy.ea.driving.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.driving.view.ViewProgressStudent;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

/** 
 * @author zc
 * @version 1.01 
 * @describe 学员进度跟踪
 */
@Controller
@Scope("prototype")
public class ProgressStudentAction{
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
	
	/**
	 * 
	 * @param
	 * 学员跟踪查询
	 */
	public String toSearchOfViewProgressStudent(){
		session.put("searchOfProgressStudent", viewProgressStudent);
		return getListOfViewProgressStudent();
	}
	/**
	 * @param
	 * @return
	 * 获取学员跟踪列表
	 */
	public String getListOfViewProgressStudent(){
		
		//String  hql=" from ViewProgressStudent where companyid=? "; //暂未使用
		
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
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(viewProgressStudent.getSearchEndDate());
					calendar.add(Calendar.DATE, 1);
					params.add(calendar.getTime());
				}
				if(viewProgressStudent.getCoach()!=null&&!"".equals(viewProgressStudent.getCoach())){
					sql+=" and ddpt.coachname like ? ";
					params.add("%" + viewProgressStudent.getCoach().trim() + "%");
				}
				if(viewProgressStudent.getStudentname()!=null&&!"".equals(viewProgressStudent.getStudentname())){
					sql+=" and ddp.studentname like ? ";
					params.add("%" + viewProgressStudent.getStudentname().trim() + "%");
				}
				if(viewProgressStudent.getStudentcard()!=null&&!"".equals(viewProgressStudent.getStudentcard())){
					sql+=" and ddp.studentcard like ? ";
					params.add("%" + viewProgressStudent.getStudentcard().trim() + "%");
				}
			}
		}else{
			sql+=" and ddp.registrationdate between ? and ? ";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.add(Calendar.DATE, 1);
			params.add(calendar.getTime());
			params.add(calendar1.getTime());
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
	 * 
	 * 学员跟踪进度导出
	 * @return
	 */
	public String toExportOfViewProgressStudent(){
		
		String  hql=" from ViewProgressStudent where companyid=? ";
		List<Object> params=new ArrayList<Object>();
		params.add(account.getCompanyID());
		if(search!=null&&"search".equals("search")){
			ViewProgressStudent viewProgressStudent=(ViewProgressStudent)session.get("searchOfProgressStudent");
			if(viewProgressStudent!=null){
				if(viewProgressStudent.getSearchStaDate()!=null&&!"".equals(viewProgressStudent.getSearchStaDate())&&
						viewProgressStudent.getSearchEndDate()!=null&&!"".equals(viewProgressStudent.getSearchEndDate())){
					hql+=" and registrationdate between ? and ? ";
					params.add(viewProgressStudent.getSearchStaDate());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(viewProgressStudent.getSearchEndDate());
					calendar.add(Calendar.DATE, 1);
					params.add(calendar.getTime());
				}
				if(viewProgressStudent.getCoach()!=null&&!"".equals(viewProgressStudent.getCoach())){
					hql+=" and coach like ? ";
					params.add("%" + viewProgressStudent.getCoach().trim() + "%");
				}
				if(viewProgressStudent.getStudentname()!=null&&!"".equals(viewProgressStudent.getStudentname())){
					hql+=" and studentname like ? ";
					params.add("%" + viewProgressStudent.getStudentname().trim() + "%");
				}
				if(viewProgressStudent.getStudentcard()!=null&&!"".equals(viewProgressStudent.getStudentcard())){
					hql+=" and studentcard like ? ";
					params.add("%" + viewProgressStudent.getStudentcard().trim() + "%");
				}
				if(viewProgressStudent.getTheProgress()!=null&&!"".equals(viewProgressStudent.getTheProgress())){
					hql+=" and status like ? ";
					params.add("%" + viewProgressStudent.getTheProgress().trim() + "%");
				}
				/*if(viewProgressStudent.getTheProgress()!=null&&!"".equals(viewProgressStudent.getTheProgress())){
					if("00".equals(viewProgressStudent.getTheProgress())){
						hql+=" and entrance = ? ";
						params.add("1");
					}else if("01".equals(viewProgressStudent.getTheProgress())){
						hql+=" and subjectone = ? ";
						params.add("1");
					}else if("02".equals(viewProgressStudent.getTheProgress())){
						hql+=" and subjecttwo = ? ";
						params.add("1");
					}else if("03".equals(viewProgressStudent.getTheProgress())){
						hql+=" and subjectthree = ? ";
						params.add("1");
					}else if("04".equals(viewProgressStudent.getTheProgress())){
						hql+=" and subjectfour = ? ";
						params.add("1");
					}else if("05".equals(viewProgressStudent.getTheProgress())){
						hql+=" and graduation = ? ";
						params.add("1");
					}
				}*/
			}
		}else{
			hql+=" and registrationdate between ? and ? ";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.add(Calendar.DATE, 1);
			params.add(calendar.getTime());
			params.add(calendar1.getTime());
		}
		/*
		hql+=" and registrationcarname  in ( ?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		params.add("C1");params.add("C2");params.add("C3");params.add("C4");params.add("C5");params.add("D");params.add("E");
		params.add("F");params.add("B1");params.add("B2");params.add("A1");params.add("A2");params.add("A3");*/
		excelStream=excelService.showExcel(ViewProgressStudent.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(hql, params.toArray()));
		return "showexcel";
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
	
	
	
	
}
