package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.InstitutionsRegistration;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
/**
 * 机构注册登记资料
 */
public class InstitutionsRegistrationAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private InstitutionsRegistration  institutionsRegistration;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private Map<String, InstitutionsRegistration> institutionsRegistrationmap;
	private int pageNumber;
	private String departmentID;
	

	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", institutionsRegistration);
		return getListInstitutionsRegistration();
	}

	/**
	 * @version zg 2011-6-1
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String  organizationID=(String) session.get("organizationID");
		DetachedCriteria dc = DetachedCriteria.forClass(InstitutionsRegistration.class);
		dc.add(Restrictions.eq("companyID",account.getCompanyID()));
		if(departmentID!=null&&!departmentID.equals("")&&!departmentID.equals(organizationID)){
			dc.add(Restrictions.eq("organizationPID",departmentID));
		}else if(departmentID!=null&&!departmentID.equals("")){
			dc.add(Restrictions.eq("organizationID",organizationID));
		}
		if (institutionsRegistration!=null&&institutionsRegistration.getOrganizationPID()!=null && !institutionsRegistration.getOrganizationPID().equals("")) {
			dc.add(Restrictions.eq("organizationPID",institutionsRegistration.getOrganizationPID()));
		}
		if (search != null && search.equals("search")){
		 	institutionsRegistration = (InstitutionsRegistration) session.get("tablesearch");
			if (institutionsRegistration.getRegistrationDate() != null && institutionsRegistration.getOpenAccountDate() != null && !("").equals(institutionsRegistration.getRegistrationDate())&& !("").equals( institutionsRegistration.getOpenAccountDate())) {
			 dc.add(Restrictions.between("registrationDate",institutionsRegistration.getRegistrationDate(),institutionsRegistration.getOpenAccountDate()));				
			}
			if (institutionsRegistration.getConPerson()!=null && !institutionsRegistration.getConPerson().equals("")) {
				dc.add(Restrictions.like("conPerson", institutionsRegistration.getConPerson(),MatchMode.ANYWHERE));
			}
			if(institutionsRegistration.getBankName()!=null && !institutionsRegistration.getBankName().equals("")){
				dc.add(Restrictions.like("bankName", institutionsRegistration.getBankName(),MatchMode.ANYWHERE));
			}
			
			dc.addOrder(Order.desc("registrationDate"));
		}
		
		return dc;
}
	
	public String getListInstitutionsRegistration(){
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "institutionsregistration";
	}
	
	/**
	 * 支持记账单银行账户查询
	 * @return
	 */
	public String getListInstitutionsRegistrationCopy(){
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "bankList";
	}
	/**
	 * 支持记账单银行账户条件查询
	 * @version zg 2011-6-1
	 * @return
	 */
	public String toSearchBankNum(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", institutionsRegistration);
		return getListInstitutionsRegistrationCopy();
	}
	
	
	public String showExcel() {
		excelStream = excelService.showExcel(InstitutionsRegistration.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"导出机构注册登记资料", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String addInstitutionsRegistration(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String  organizationID=(String) session.get("organizationID");
		ArrayList<BaseBean>    baseBeanList=new ArrayList<BaseBean>();
		if (institutionsRegistrationmap!=null) {
			List<String> parermiters = new ArrayList<String>();
		for (InstitutionsRegistration institutionsregistrations : institutionsRegistrationmap.values()) {
		 this.institutionsRegistration.setOrganizationPID(institutionsRegistration.getOrganizationPID());			
		if (institutionsregistrations.getRegistrationID() == null
				|| "".equals(institutionsregistrations.getRegistrationID() )) {
			institutionsregistrations.setRegistrationID(serverService.getServerID("registration"));
			parameter = "添加机构注册登记资料(注册登记帐号:"+institutionsregistrations.getBankAccount()+")";
		}
		else
		{
		 String hql2="from InstitutionsRegistration where companyID=?  and registrationID=?";
		 InstitutionsRegistration aff=(InstitutionsRegistration) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(), institutionsregistrations.getRegistrationID()});
		 parameter = "修改机构注册登记资料(注册登记帐号:"+aff.getBankAccount()+")";
		
		}		
		institutionsregistrations.setCompanyID(account.getCompanyID());
		institutionsregistrations.setOrganizationID(organizationID);
		baseBeanList.add(institutionsregistrations);
		parermiters.add(parameter);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
		logBookService.saveLogsListAndexecuteHqlsByParams(null, parermiters, account);
		}
		return "success";
	}

	public String delInstitutionsRegistration() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> beans = new ArrayList<BaseBean>();		
		Object[] params = { account.getCompanyID(),institutionsRegistration.getRegistrationID() };
	    String hql2="from InstitutionsRegistration where companyID=?  and registrationID=?";
	    InstitutionsRegistration aff=(InstitutionsRegistration) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    CLogBook logBook = logBookService.saveCLogBook(null, "删除机构注册登记资料(注册帐号:"+aff.getBankAccount()+")", account);
	    beans.add(logBook);
	    String[] hql = {"delete from InstitutionsRegistration where companyID=?  and registrationID=?"};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}

	
	
	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InstitutionsRegistration getInstitutionsRegistration() {
		return institutionsRegistration;
	}

	public void setInstitutionsRegistration(
			InstitutionsRegistration institutionsRegistration) {
		this.institutionsRegistration = institutionsRegistration;
	}

	public Map<String, InstitutionsRegistration> getInstitutionsRegistrationmap() {
		return institutionsRegistrationmap;
	}

	public void setInstitutionsRegistrationmap(
			Map<String, InstitutionsRegistration> institutionsRegistrationmap) {
		this.institutionsRegistrationmap = institutionsRegistrationmap;
	}

	
	

	
	
}
