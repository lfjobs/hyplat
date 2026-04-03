package hy.ea.action;

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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


@Controller @Scope("prototype")
public class CRoleAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CRoleService roleService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private List<BaseBean> organizationlist;
	private CRole crole;
	private PageForm pageForm;
	private int pageNumber;
	private String parameter;
	private String message;
	private List<BaseBean> beans;
	private Company company;
	
	public String savaCRole(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		if(null == crole.getRoleID() || "".equals(crole.getRoleID())){
			crole.setRoleID(serverService.getServerID("crole"));
			parameter = "添加角色";
			parameter += "(角色名称:"+crole.getRoleName()+")";
		}else{
			parameter = "修改角色";
			parameter += "(角色名称:"+roleService.getRoleByID(account.getCompanyID(), crole.getRoleID()).getRoleName()+")";
		}
		crole.setCompanyID(account.getCompanyID());
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans = new ArrayList<BaseBean>();
		beans.add(crole);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getListCRole();
	}

	public String getListCRole() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String hql1=" from Company where companyID=?";
		company=(Company)baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{account.getCompanyID()});
		String hql = " from CRole where companyID = ? order by organizationName";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, new String[]{account.getCompanyID()});
		return "list";
	}

	public String editCRole() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");		
		crole = roleService.getRoleByID(account.getCompanyID(),crole.getRoleID());
		return "to_edit";
	}

	public String delCRole() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		Object[] parm = {account.getCompanyID(),crole.getRoleID()};
		String hql = "from CAccount cat where cat.companyID = ? and cat.roleID = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, parm);
		if(list!=null && list.size()!=0){
			message="该角色存在账户，不能删除";
			return "fail";
		}else{
			CLogBook logBook = logBookService.saveCLogBook(null,"删除角色(角色名称："+ roleService.getRoleByID(account.getCompanyID(),crole.getRoleID()).getRoleName()
					+")", account);
			roleService.deleteRoleByID(account.getCompanyID(),crole.getRoleID(),logBook);
		}
		return "success";
		//return getListCRole();
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	}

}

