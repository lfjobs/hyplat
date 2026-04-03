package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseCulture;
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
 * 企业文化管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseCultureAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseCulture enterpriseCulture;
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

	//根据条件查询企业文化
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseCulture);
		return getEnterpriseCultureList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseCulture.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseCulture = (EnterpriseCulture) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseCulture.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 企业文化列表
	public String getEnterpriseCultureList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "culturelist";	
	}
  //企业文化保存
    
    public String saveEnterpriseCulture()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (enterpriseCulture.getCultureID()== null|| "".equals(enterpriseCulture.getCultureID())) {
			enterpriseCulture.setCultureID(serverService.getServerID("enterpriseCulture"));
			parameter = "添加企业文化(企业文化:"+enterpriseCulture.getCulture()+")";
		}
		else
		{
			 String hql2="from EnterpriseCulture where companyID=?  and cultureID=?";
			 EnterpriseCulture spirit=(EnterpriseCulture) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseCulture.getCultureID()});
		     parameter = "修改企业文化(企业文化:"+spirit.getCulture()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseCulture.setOrganizationID(organizationID);
		enterpriseCulture.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseCulture);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除企业文化
	 public String delEnterpriseCulture()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseCulture.getCultureID()};
		    String hql2="from EnterpriseCulture where companyID=?  and cultureID=?";
		    EnterpriseCulture spirit=(EnterpriseCulture) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业文化(企业文化:"+spirit.getCulture()+")", account);
	    	String hql="delete from EnterpriseCulture where companyID=?  and cultureID=?";
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

	public EnterpriseCulture getEnterpriseCulture() {
		return enterpriseCulture;
	}

	public void setEnterpriseCulture(EnterpriseCulture enterpriseCulture) {
		this.enterpriseCulture = enterpriseCulture;
	}

	

}
