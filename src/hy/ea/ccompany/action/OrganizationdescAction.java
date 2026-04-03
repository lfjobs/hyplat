package hy.ea.ccompany.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Organizationdesc;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

public class OrganizationdescAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	private InputStream excelStream;
	private Organizationdesc organizationdesc;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String parameter;
	private Map<String,Organizationdesc> orgdescmap;
	private String ogName;
	private String ogID;
	/**
	 *  删除
	 * @return
	 */
	public String delete(){
		String hql = "delete from Organizationdesc o where o.organizationdescid = ? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql}, new Object[]{organizationdesc.getOrganizationdescid()});
		return "success";
	}
	
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
		excelStream = excelService.showExcel(Organizationdesc.columnHeadings(), lists);
		return "showexcel";
	}
	
	/**
	 * 保存/修改
	 * @return
	 */
	public String saveOrganizationDesc(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		ArrayList<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if (orgdescmap!=null) {
			List<String> parermiters = new ArrayList<String>();
		for (Organizationdesc odesc  : orgdescmap.values()) {
		 	if (odesc.getOrganizationdescid() == null
					|| "".equals(odesc.getOrganizationdescid() )) {
		 		odesc.setCompanyid(account.getCompanyID());
		 		odesc.setOrganizationdescid(serverService.getServerID("organizationdesc"));
		 		odesc.setCname(account.getAccountEmail());
		 		odesc.setCtime(new Date());
				parameter = "添加部门机构职责(当前部门:"+ogName+")";
			}
			else
			{
				odesc.setUname(account.getAccountEmail());
			 	odesc.setUtime(new Date());
			 	parameter = "修改部门机构职责(当前部门:"+ogName+")";
			
			}		
			baseBeanList.add(odesc);
			parermiters.add(parameter);
		}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			baseBeanList.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
		}
		return "success";
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", organizationdesc);
		return getOrganizationdescList();
	}
	
	/**
	 * 获取全部
	 * @return
	 */
	public String getOrganizationdescList(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "getList";
	}
	
	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String hql = "from Organizationdesc o where o.companyid = ? and o.organizationid = ?";
		parms.add(companyID);
		parms.add(organizationdesc.getOrganizationid());
		if (search != null && search.equals("search")){
			organizationdesc = (Organizationdesc) session.get("tablesearch");
		}
		hql += " order by o.companyid";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	public Organizationdesc getOrganizationdesc() {
		return organizationdesc;
	}

	public void setOrganizationdesc(Organizationdesc organizationdesc) {
		this.organizationdesc = organizationdesc;
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

	public Map<String, Organizationdesc> getOrgdescmap() {
		return orgdescmap;
	}

	public void setOrgdescmap(Map<String, Organizationdesc> orgdescmap) {
		this.orgdescmap = orgdescmap;
	}

	public String getOgName() {
		return ogName;
	}

	public void setOgName(String ogName) {
		this.ogName = ogName;
	}

	public String getOgID() {
		return ogID;
	}

	public void setOgID(String ogID) {
		this.ogID = ogID;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
}
