package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.vo.COSJobTaskVO;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 集团工作--任务汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class JobTaskCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	private COSJobTaskVO cosJobTask;

	/**
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		List<BaseBean> lists = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(COSJobTaskVO.columnHeadings(),lists);
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", cosJobTask);
		return getJobTaskList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getJobTaskList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		
		return "getJobTaskList";
	}

	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String hql = "from COSJobTaskVO j where exists ( select c.companyID from Company c"
				+ " where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			cosJobTask = (COSJobTaskVO) session.get("tablesearch");
			//人员姓名
			if (cosJobTask.getStaffName()!= null
					&& !cosJobTask.getStaffName().trim().equals("")) {
				hql += " and j.staffName like ? ";
				parms.add("%" + cosJobTask.getStaffName().trim() + "%");
			}
			//公司
			if (cosJobTask.getCompanyID()!= null
					&& !cosJobTask.getCompanyID().equals("")) {
				hql += " and j.companyID = ? ";
				parms.add(cosJobTask.getCompanyID());
			}
			//部门
			if (cosJobTask.getOrganizationID()!= null
					&& !cosJobTask.getOrganizationID().equals("") && !cosJobTask.getCompanyID().equals(cosJobTask.getOrganizationID())) {
				hql += " and j.organizationID = ? ";
				parms.add(cosJobTask.getOrganizationID());
			}
			//人员编号
			if (cosJobTask.getStaffCode()!= null
					&& !cosJobTask.getStaffCode().trim().equals("")) {
				hql += " and j.staffCode like ? ";
				parms.add("%" + cosJobTask.getStaffCode().trim() + "%");
			}
			//日期
			if (cosJobTask.getStartDate()!= null
					&& !cosJobTask.getStartDate().equals("")) {
				hql += " and j.startDate between ? and ? ";
				parms.add(cosJobTask.getStartDate());
				parms.add(cosJobTask.getEndDate());
			}
		}
		hql += " order by j.companyName,j.staffCode,j.startDate";
		results.add(hql);
		results.add(parms.toArray());
		return results;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public COSJobTaskVO getCosJobTask() {
		return cosJobTask;
	}

	public void setCosJobTask(COSJobTaskVO cosJobTask) {
		this.cosJobTask = cosJobTask;
	}

}
