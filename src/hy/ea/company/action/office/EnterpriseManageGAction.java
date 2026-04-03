package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseManage;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
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
/*
 * 管理理念管理 --集团汇总
 * */
@Controller
@Scope("prototype")
public class EnterpriseManageGAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseManage enterpriseManage;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询管理理念
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseManage);
		return getEnterpriseManageList();
	}
	
	//管理理念列表
	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		
		String hql = "from EnterpriseManage t where exists ( select c.companyID from Company c"
				+ " where t.companyID = c.companyID and (c.companyID = ? or companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			enterpriseManage = (EnterpriseManage) session.get("tablesearch");
			if (null != enterpriseManage.getManageIdea()
					&& !"".equals(enterpriseManage.getManageIdea().trim())) {
				hql += " and t.manageIdea like ?";
				parms.add("%" + enterpriseManage.getManageIdea().trim() + "%");
			}
			if (null != enterpriseManage.getManageContent()
					&& !"".equals(enterpriseManage.getManageContent().trim())) {
				hql += " and t.manageContent like ?";
				parms.add("%" + enterpriseManage.getManageContent().trim() + "%");
			}
		}
		hql += " order by t.companyID desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}
	// 管理理念列表
	public String getEnterpriseManageList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "managelist";	
	}
  
	//excel导出
	public String showExcel(){
		Map<String,Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		if(account == null){
			return "tologin";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(EnterpriseManage.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出管理理念集团汇总", account);
		baseBeanService.update(logbook);
		return "showexcel";
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public EnterpriseManage getEnterpriseManage() {
		return enterpriseManage;
	}

	public void setEnterpriseManage(EnterpriseManage enterpriseManage) {
		this.enterpriseManage = enterpriseManage;
	}
}