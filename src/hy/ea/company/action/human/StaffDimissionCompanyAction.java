package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.vo.COSDimissionStaffVO;
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
 * 集团工作--离职员工
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class StaffDimissionCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	private COSDimissionStaffVO staffdimission;
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
		List<BaseBean> lists = baseBeanService.getListBeanByHqlAndParams(hql,params);
		excelStream = excelService.showExcel(COSDimissionStaffVO.columnHeadings(),lists);
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", staffdimission);
		return getStaffDimissionList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getStaffDimissionList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		
		return "getStaffDimissionList";
	}
	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String hql = "from COSDimissionStaffVO j where exists ( select c.companyID from Company c"
				+ " where j.companyID = c.companyID and (c.companyID = ? or c.companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			staffdimission = (COSDimissionStaffVO) session.get("tablesearch");
			//人员姓名
			if (staffdimission.getStaffName()!= null
					&& !staffdimission.getStaffName().trim().equals("")) {
				hql += " and j.staffName like ? ";
				parms.add("%" + staffdimission.getStaffName().trim() + "%");
			}
			//公司
			if (staffdimission.getCompanyID()!= null
					&& !staffdimission.getCompanyID().equals("")) {
				hql += " and j.companyID = ? ";
				parms.add(staffdimission.getCompanyID());
			}
			//人员编号
			if (staffdimission.getStaffCode()!= null
					&& !staffdimission.getStaffCode().trim().equals("")) {
				hql += " and j.staffCode like ? ";
				parms.add("%" + staffdimission.getStaffCode().trim() + "%");
			}
		}
		hql += " order by j.companyName,j.staffCode,j.dimissionDate";
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

	public COSDimissionStaffVO getStaffdimission() {
		return staffdimission;
	}

	public void setStaffdimission(COSDimissionStaffVO staffdimission) {
		this.staffdimission = staffdimission;
	}

}
