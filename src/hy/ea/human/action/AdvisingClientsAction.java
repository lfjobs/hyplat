package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.AdvisingClients;
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
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
/**
 * 	市场调查办
 */
public class AdvisingClientsAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private AdvisingClients advisingClients;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	private List<BaseBean> beans;

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", advisingClients);
		return getListAdvisingClients();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(AdvisingClients.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		if (search != null && search.equals("search")) {
			advisingClients = (AdvisingClients) session.get("tablesearch");
		if (!"".equals(advisingClients.getCompanyName())){
				dc.add(Restrictions.like("companyName",advisingClients.getCompanyName(),MatchMode.ANYWHERE));
		}
		if (!"".equals(advisingClients.getContactPeople())){
			dc.add(Restrictions.like("contactPeople",advisingClients.getContactPeople(),MatchMode.ANYWHERE));
		}
	}
		return dc;
}

	public String getListAdvisingClients(){
	/*	Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) ";
		Object[] params = { ((CAccount)session.get("account")).getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);*/
		
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
			.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getList());
		return "advisingClients";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(AdvisingClients.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出客户指导办", account);
		beans.add(logBook);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String addAdvisingClients() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (advisingClients.getAdvisingClientsID() == null
				|| "".equals(advisingClients.getAdvisingClientsID())) {
			advisingClients.setAdvisingClientsID(serverService.getServerID("advisingClients"));
			parameter = "添加客户指导办(公司名称:"+advisingClients.getCompanyName()+")";
		}
		else
		{
		 String hql2="from AdvisingClients where companyID=?  and advisingClientsID=?";
		 AdvisingClients aff=(AdvisingClients) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),advisingClients.getAdvisingClientsID() });
		 parameter = "修改客户指导办(公司名称:"+aff.getCompanyName()+")";
		
		}
		advisingClients.setOrganizationID(organizationID);
		advisingClients.setCompanyID(account.getCompanyID());
		beans = new ArrayList<BaseBean>();
		beans.add(advisingClients);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public String delAdvisingClients() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		Object[] params = { account.getCompanyID(), advisingClients.getAdvisingClientsID()};
	    String hql2="from AdvisingClients where companyID=?  and advisingClientsID=?";
	    AdvisingClients aff=(AdvisingClients) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除客户指导办(公司名称:"+aff.getCompanyName()+")", account);
	    beans.add(logBook);
		String hql = "delete from AdvisingClients where companyID=?  and advisingClientsID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "success";
	}
	





	public AdvisingClients getAdvisingClients() {
		return advisingClients;
	}

	public void setAdvisingClients(AdvisingClients advisingClients) {
		this.advisingClients = advisingClients;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
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
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
