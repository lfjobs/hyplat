package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseIdea;
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
 * 企业理念管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseIdeaAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseIdea enterpriseIdea;
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

	//根据条件查询企业理念
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseIdea);
		return getEnterpriseIdeaList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseIdea.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseIdea = (EnterpriseIdea) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseIdea.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 企业理念列表
	public String getEnterpriseIdeaList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "idealist";	
	}
  //企业理念保存
    
    public String saveEnterpriseIdea()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (enterpriseIdea.getIdeaID()== null|| "".equals(enterpriseIdea.getIdeaID())) {
			enterpriseIdea.setIdeaID(serverService.getServerID("enterpriseIdea"));
			parameter = "添加企业理念(企业名称:"+enterpriseIdea.getEnterpriseName()+")";
		}
		else
		{
			 String hql2="from EnterpriseIdea where companyID=?  and ideaID=?";
			 EnterpriseIdea spirit=(EnterpriseIdea) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseIdea.getIdeaID()});
		     parameter = "修改企业理念(企业名称:"+spirit.getEnterpriseName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseIdea.setOrganizationID(organizationID);
		enterpriseIdea.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseIdea);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "succ";
    }
    //删除企业理念
	 public String delEnterpriseIdea()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseIdea.getIdeaID()};
		    String hql2="from EnterpriseIdea where companyID=?  and ideaID=?";
		    EnterpriseIdea spirit=(EnterpriseIdea) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业理念(企业名称:"+spirit.getEnterpriseName()+")", account);
	    	String hql="delete from EnterpriseIdea where companyID=?  and ideaID=?";
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

	public EnterpriseIdea getEnterpriseIdea() {
		return enterpriseIdea;
	}

	public void setEnterpriseIdea(EnterpriseIdea enterpriseIdea) {
		this.enterpriseIdea = enterpriseIdea;
	}


	

}
