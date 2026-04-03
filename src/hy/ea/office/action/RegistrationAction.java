package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.Registration;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
/**
 * 注册登记资料
 */
public class RegistrationAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private Registration  registration;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private Map<String, Registration> registrationmap;
	private int pageNumber;
	

	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", registration);
		return getListRegistration();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(Registration.class);
		dc.add(Restrictions.eq("ccompanyID",registration.getCcompanyID()));
		if (search != null && search.equals("search")){
		 	registration = (Registration) session.get("tablesearch");
			if (registration.getRegistrationDate() != null && registration.getOpenAccountDate() != null && !("").equals(registration.getRegistrationDate())&& !("").equals( registration.getOpenAccountDate())) {
			 dc.add(Restrictions.between("registrationDate",registration.getRegistrationDate(),registration.getOpenAccountDate()));				
			}
			dc.addOrder(Order.desc("registrationDate"));
	}
		return dc;
}
	
	public String getListRegistration(){
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "registration";
	}

	
	public String showExcel() {
		excelStream = excelService.showExcel(Registration.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook  logbook=logBookService.saveCLogBook(null,"导出注册登记资料", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	public String addRegistration(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		ArrayList<BaseBean>    baseBeanList=new ArrayList<BaseBean>();
		if (registrationmap!=null) {
		for (Registration registrations : registrationmap.values()) {
		 this.registration.setCcompanyID(registration.getCcompanyID());			
		if (registrations.getRegistrationID() == null|| "".equals(registrations.getRegistrationID() )) {
			registrations.setRegistrationID(serverService.getServerID("registration"));
			parameter = "添加注册登记资料(注册登记帐号:"+registrations.getBankAccount()+")";
		}
		else
		{
		 String hql2="from Registration where companyID=?  and registrationID=?";
		 Registration aff=(Registration) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(), registrations.getRegistrationID()});
		 parameter = "修改注册登记资料(注册登记帐号:"+aff.getBankAccount()+")";
		
		}		
		registrations.setCompanyID(account.getCompanyID());
		baseBeanList.add(registrations);
		CLogBook  logbook=logBookService.saveCLogBook(null, parameter, account);
		baseBeanList.add(logbook);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
		
		}
		return "success";
	}

	public String delRegistration() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
						
		Object[] params = { account.getCompanyID(),registration.getRegistrationID() };
	    String hql2="from Registration where companyID=?  and registrationID=?";
	    Registration aff=(Registration) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    CLogBook  logbook=logBookService.saveCLogBook(null, "删除注册登记资料(注册帐号:"+aff.getBankAccount()+")", account);
		String[] hql = {"delete from Registration where companyID=?  and registrationID=?"};
		List<BaseBean> beans=new ArrayList<BaseBean>();
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
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

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public Map<String, Registration> getRegistrationmap() {
		return registrationmap;
	}

	public void setRegistrationmap(Map<String, Registration> registrationmap) {
		this.registrationmap = registrationmap;
	}

	

	
	
}
