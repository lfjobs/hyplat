package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseManner;
import hy.ea.service.CLogBookService;
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
/*
 * 工作态度管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseMannerAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseManner enterpriseManner;
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

	//根据条件查询工作态度
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseManner);
		return getEnterpriseMannerList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseManner.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseManner = (EnterpriseManner) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseManner.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 工作态度列表
	public String getEnterpriseMannerList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
		return "mannerlist";	
	}
  //工作态度保存
    
    public String saveEnterpriseManner()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (enterpriseManner.getMannerID()== null|| "".equals(enterpriseManner.getMannerID())) {
			enterpriseManner.setMannerID(serverService.getServerID("enterpriseManner"));
			parameter = "添加工作态度(企业名称:"+enterpriseManner.getEnterpriseName()+")";
		}
		else
		{
			 String hql2="from EnterpriseManner where companyID=?  and mannerID=?";
			 EnterpriseManner spirit=(EnterpriseManner) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseManner.getMannerID()});
		     parameter = "修改工作态度(企业名称:"+spirit.getEnterpriseName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseManner.setOrganizationID(organizationID);
		enterpriseManner.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseManner);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "succ";
    }
    //删除工作态度
	 public String delEnterpriseManner()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseManner.getMannerID()};
		    String hql2="from EnterpriseManner where companyID=?  and mannerID=?";
		    EnterpriseManner spirit=(EnterpriseManner) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除工作态度(企业名称:"+spirit.getEnterpriseName()+")", account);
	    	String hql="delete from EnterpriseManner where companyID=?  and mannerID=?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
			return "succ";
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

	public EnterpriseManner getEnterpriseManner() {
		return enterpriseManner;
	}

	public void setEnterpriseManner(EnterpriseManner enterpriseManner) {
		this.enterpriseManner = enterpriseManner;
	}


}
