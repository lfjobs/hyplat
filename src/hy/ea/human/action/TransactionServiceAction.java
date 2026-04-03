package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.TransactionService;
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
 * 	客户成交服务
 */
public class TransactionServiceAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private TransactionService transactionService;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	private List<BaseBean> beans;
	private String produce;

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", transactionService);
		return getListTransactionService();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(TransactionService.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		if (search != null && search.equals("search")) {
			transactionService = (TransactionService) session.get("tablesearch");
		if (!"".equals(transactionService.getTransactionServiceCode())){
				dc.add(Restrictions.like("transactionServiceCode",transactionService.getTransactionServiceCode(),MatchMode.ANYWHERE));
		}
		if (!"".equals(transactionService.getCustomerName())){
			dc.add(Restrictions.like("customerName",transactionService.getCustomerName(),MatchMode.ANYWHERE));
		}
	}
		return dc;
}
	
	/**
	 * 返回成交产品菜单树页面
	 * @return
	 */
	public String getFileTurnOverProducts(){
		if(null != produce && produce.equals("Company")){
			return "cFirstpage";
		}
		if(null != produce && produce.equals("group")){
			return "gFirstpage";
		}
		return "firstpage";
	}
	
	public String getListTransactionService(){
	/*	Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) ";
		Object[] params = { ((CAccount)session.get("account")).getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);*/
		
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
			.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getList());
		return "transactionService";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(TransactionService.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出客户成交服务", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String addTransactionService() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (transactionService.getTransactionServiceID() == null
				|| "".equals(transactionService.getTransactionServiceID())) {
			transactionService.setTransactionServiceID(serverService.getServerID("transactionService"));
			parameter = "添加客户成交服务(客户名称:"+transactionService.getCustomerName()+")";
		}
		else
		{
		 String hql2="from TransactionService where companyID=?  and transactionServiceID=?";
		 TransactionService aff=(TransactionService) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),transactionService.getTransactionServiceID() });
		 parameter = "修改客户成交服务(客户名称:"+aff.getCustomerName()+")";
		
		}
		transactionService.setOrganizationID(organizationID);
		transactionService.setCompanyID(account.getCompanyID());
		beans = new ArrayList<BaseBean>();
		beans.add(transactionService);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public String delTransactionService() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), transactionService.getTransactionServiceID()};
	    String hql2="from TransactionService where companyID=?  and transactionServiceID=?";
	    TransactionService aff=(TransactionService) baseBeanService.getBeanByHqlAndParams(hql2, params);
	    CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除客户成交服务(客户名称:"+aff.getCustomerName()+")", account);
	    beans = new ArrayList<BaseBean>();
	    beans.add(logBook);
		String[] hql = {"delete from TransactionService where companyID=?  and transactionServiceID=?"};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		return "success";
	}
	


	public TransactionService getTransactionService() {
		return transactionService;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
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

	public String getProduce() {
		return produce;
	}

	public void setProduce(String produce) {
		this.produce = produce;
	}
	
}
