package hy.ea.ccompany.action;

import java.util.ArrayList;
import java.util.List;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.bo.Company;
import hy.ea.service.CLogBookService;
import hy.ea.service.CRoleService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


@Controller @Scope("prototype")
public class CCRoleAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CRoleService roleService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private CRole crole;
	private PageForm pageForm;
	private int pageNumber;
	private String parameter;
	private List<BaseBean> beans;
	private Company company;
	private String search;
	private String compID;// 需要操作的公司ID
	/**
	 * 保存修改角色信息
	 * @return
	 */
	public String savaCRole(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		if(null == crole.getRoleID() || "".equals(crole.getRoleID())){
			crole.setRoleID(serverService.getServerID("crole"));
			parameter = "添加角色";
			parameter += "(角色名称:"+crole.getRoleName()+")";
		}else{
			parameter = "修改角色";
			parameter += "(角色名称:"+roleService.getRoleByID(crole.getCompanyID(), crole.getRoleID()).getRoleName()+")";
		}
		
		beans = new ArrayList<BaseBean>();
		beans.add(crole);
		CLogBook logBook = logBookService.saveCLogBook(account.getCompanyID(), parameter, account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getListCRole();
	}
	

	/**
	 * 查询按钮功能
	 * @return
	 */
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch", crole);
		return getListCRole();
	}
	
	/**
	 * 默认查询总部
	 * 条件查询
	 */
	public String getListCRole() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		
		List<String> arry=new ArrayList<String>();
		String hql = " from CRole where 1 = 1";
		
		if(search !=null && search.equals("search")){
			crole = (CRole) ActionContext.getContext().getSession().get("tablesearch");
			if(crole != null){
				if(crole.getCompanyID()!=null && !"".equals(crole.getCompanyID())){
					hql+=" and companyID = ? ";
					arry.add(crole.getCompanyID());
				}
				if(crole.getOrganizationName()!=null && !"".equals(crole.getOrganizationName())){
					if(!crole.getOrganizationName().contains("company")){
						hql+=" and organizationName = ? ";
						arry.add(crole.getOrganizationName());
					}
				}
				if(crole.getRoleName()!=null && !"".equals(crole.getRoleName().trim())){
					hql+=" and roleName like ? ";
					arry.add("%" +crole.getRoleName().trim() + "%" );
				}
			}
		}else{
			hql+=" and companyID = ? ";
			arry.add(account.getCompanyID());
		}
		hql += " order by roleName ";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, arry.toArray());
		return "list";
	}

	public String editCRole() {
		crole = roleService.getRoleByID(compID,crole.getRoleID());
		return "to_edit";
	}

	public String delCRole() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		String hqlRMCO = " delete CREMI where companyID = ? and roleID = ? ";
		String hqlRole = " delete CRole where companyID = ? and roleID = ? ";
		Object[] parm = {compID,crole.getRoleID()};
		CLogBook logBook = logBookService.saveCLogBook(companyID,"删除角色(角色名称："+ roleService.getRoleByID(compID,crole.getRoleID()).getRoleName()
				+")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hqlRMCO,hqlRole}, parm);
		return getListCRole();
	}
	public CRole getCrole() {
		return crole;
	}
	public void setCrole(CRole crole) {
		this.crole = crole;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}


	public String getCompID() {
		return compID;
	}


	public void setCompID(String compID) {
		this.compID = compID;
	}
	
}

