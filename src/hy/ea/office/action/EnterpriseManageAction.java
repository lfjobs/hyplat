package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseManage;
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
 * 管理理念管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
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

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseManage.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseManage = (EnterpriseManage) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseManage.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 管理理念列表
	public String getEnterpriseManageList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
		return "managelist";	
	}
  //管理理念保存
    
    public String saveEnterpriseManage()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (enterpriseManage.getManageID()== null|| "".equals(enterpriseManage.getManageID())) {
			enterpriseManage.setManageID(serverService.getServerID("enterpriseManage"));
			parameter = "添加管理理念(管理理念:"+enterpriseManage.getManageIdea()+")";
		}
		else
		{
			 String hql2="from EnterpriseManage where companyID=?  and manageID=?";
			 EnterpriseManage spirit=(EnterpriseManage) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseManage.getManageID()});
		     parameter = "修改管理理念(管理理念:"+spirit.getManageIdea()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseManage.setOrganizationID(organizationID);
		enterpriseManage.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseManage);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除管理理念
	 public String delEnterpriseManage()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseManage.getManageID()};
		    String hql2="from EnterpriseManage where companyID=?  and manageID=?";
		    EnterpriseManage spirit=(EnterpriseManage) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除管理理念(管理理念:"+spirit.getManageIdea()+")", account);
	    	String hql="delete from EnterpriseManage where companyID=?  and manageID=?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
			return "success";
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
