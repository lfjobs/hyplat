package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.driving.view.ViewProgressStudentSubject;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/** 
 * @author zc
 * @version 1.01 
 * @describe 学员科目进度跟踪
 */
@Controller
@Scope("prototype")
public class ProgressStudentSubjectAction{
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
	
	private ViewProgressStudentSubject viewProgressStudentSubject;
	
	/**
	 * 
	 * @param
	 * 学员科目跟踪查询
	 */
	public String toSearchOfViewProgressStudentSubject(){
		session.put("searchOfProgressStudent", viewProgressStudentSubject);
		return getListOfViewProgressStudentSubject();
	}
	/**
	 * @param
	 * @return
	 * 获取学员科目跟踪列表
	 * @unused
	 *	hql+=" and registrationcarname  in ( ?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	 *	params.add("C1");params.add("C2");params.add("C3");params.add("C4");params.add("C5");params.add("D");params.add("E");
	 *	params.add("F");params.add("B1");params.add("B2");params.add("A1");params.add("A2");params.add("A3");
	 * @describe
	 */
	public String getListOfViewProgressStudentSubject(){
		
		String  hql=" from ViewProgressStudentSubject where companyid=? ";
		
		List<Object> params=new ArrayList<Object>();
		params.add(account.getCompanyID());
		hql+=" and docstatus= ? ";
		params.add(viewProgressStudentSubject.getDocstatus());
		if(search!=null&&"search".equals(search)){
			ViewProgressStudentSubject viewProgressStudentSubject=(ViewProgressStudentSubject)session.get("searchOfProgressStudent");
			if(viewProgressStudentSubject!=null){
				if(viewProgressStudentSubject.getSearchStaDate()!=null&&!"".equals(viewProgressStudentSubject.getSearchStaDate())&&
						viewProgressStudentSubject.getSearchEndDate()!=null&&!"".equals(viewProgressStudentSubject.getSearchEndDate())){
					hql+=" and registrationdate between ? and ? ";
					params.add(viewProgressStudentSubject.getSearchStaDate());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(viewProgressStudentSubject.getSearchEndDate());
					calendar.add(Calendar.DATE, 1);
					params.add(calendar.getTime());
				}
				if(viewProgressStudentSubject.getCoachname()!=null&&!"".equals(viewProgressStudentSubject.getCoachname())){
					hql+=" and coachname like ? ";
					params.add("%" + viewProgressStudentSubject.getCoachname().trim() + "%");
				}
				if(viewProgressStudentSubject.getStudentname()!=null&&!"".equals(viewProgressStudentSubject.getStudentname())){
					hql+=" and studentname like ? ";
					params.add("%" + viewProgressStudentSubject.getStudentname().trim() + "%");
				}
				if(viewProgressStudentSubject.getStudentcard()!=null&&!"".equals(viewProgressStudentSubject.getStudentcard())){
					hql+=" and studentcard like ? ";
					params.add("%" + viewProgressStudentSubject.getStudentcard().trim() + "%");
				}
				if(viewProgressStudentSubject.getTheProgress()!=null&&!"".equals(viewProgressStudentSubject.getTheProgress())){
					hql+=" and finished = ? ";
					params.add(viewProgressStudentSubject.getTheProgress().trim());
				}
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
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params.toArray());
		return "list";
	}
	/**
	 * 
	 * 学员科目跟踪进度导出
	 * @return
	 */
	public String toExportOfViewProgressStudentSubject(){
		
		String  hql=" from ViewProgressStudentSubject where companyid=? ";
		List<Object> params=new ArrayList<Object>();
		params.add(account.getCompanyID());
		hql+=" and docstatus= ? ";
		params.add(viewProgressStudentSubject.getDocstatus());
		if(search!=null&&"search".equals("search")){
			ViewProgressStudentSubject viewProgressStudentSubject=(ViewProgressStudentSubject)session.get("searchOfProgressStudent");
			if(viewProgressStudentSubject!=null){
				if(viewProgressStudentSubject.getSearchStaDate()!=null&&!"".equals(viewProgressStudentSubject.getSearchStaDate())&&
						viewProgressStudentSubject.getSearchEndDate()!=null&&!"".equals(viewProgressStudentSubject.getSearchEndDate())){
					hql+=" and registrationdate between ? and ? ";
					params.add(viewProgressStudentSubject.getSearchStaDate());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(viewProgressStudentSubject.getSearchEndDate());
					calendar.add(Calendar.DATE, 1);
					params.add(calendar.getTime());
				}
				if(viewProgressStudentSubject.getCoachname()!=null&&!"".equals(viewProgressStudentSubject.getCoachname())){
					hql+=" and coachname like ? ";
					params.add("%" + viewProgressStudentSubject.getCoachname().trim() + "%");
				}
				if(viewProgressStudentSubject.getStudentname()!=null&&!"".equals(viewProgressStudentSubject.getStudentname())){
					hql+=" and studentname like ? ";
					params.add("%" + viewProgressStudentSubject.getStudentname().trim() + "%");
				}
				if(viewProgressStudentSubject.getStudentcard()!=null&&!"".equals(viewProgressStudentSubject.getStudentcard())){
					hql+=" and studentcard like ? ";
					params.add("%" + viewProgressStudentSubject.getStudentcard().trim() + "%");
				}
				if(viewProgressStudentSubject.getTheProgress()!=null&&!"".equals(viewProgressStudentSubject.getTheProgress())){
					hql+=" and finished = ? ";
					params.add(viewProgressStudentSubject.getTheProgress().trim());
				}
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
		List<String>   titleList=new ArrayList<String>();
		List<BaseBean>   list=baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		if(list!=null&&list.size()>0){
			ViewProgressStudentSubject  viewProgressStudentSubject=(ViewProgressStudentSubject)list.get(0);
			String[] completeArray=viewProgressStudentSubject.getComplete().split(",");
			for (int i = 0; i < completeArray.length; i++) {
				titleList.add(completeArray[i].split("-")[0]);
			}
		}
		ViewProgressStudentSubject.setTitleList(titleList);
		excelStream=excelService.showExcel(ViewProgressStudentSubject.columnHeadings(),list );
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

	public ViewProgressStudentSubject getViewProgressStudentSubject() {
		return viewProgressStudentSubject;
	}

	public void setViewProgressStudentSubject(ViewProgressStudentSubject viewProgressStudentSubject) {
		this.viewProgressStudentSubject = viewProgressStudentSubject;
	}
	
	
	
	
}
