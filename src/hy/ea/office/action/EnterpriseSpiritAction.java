package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseSpirit;
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
 * 企业精神管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseSpiritAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseSpirit enterpriseSpirit;
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

	//根据条件查询企业精神
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseSpirit);
		return getEnterpriseSpiritList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseSpirit.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseSpirit = (EnterpriseSpirit) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseSpirit.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 企业精神列表
	public String getEnterpriseSpiritList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "spiritlist";	
	}
  //企业精神保存
    
    public String savEnterpriseSpirit()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (enterpriseSpirit.getSpiritID()== null|| "".equals(enterpriseSpirit.getSpiritID())) {
			enterpriseSpirit.setSpiritID(serverService.getServerID("enterpriseSpirit"));
			parameter = "添加企业精神(企业名称:"+enterpriseSpirit.getEnterpriseName()+")";
		}
		else
		{
			 String hql2="from EnterpriseSpirit where companyID=?  and spiritID=?";
			 EnterpriseSpirit spirit=(EnterpriseSpirit) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseSpirit.getSpiritID()});
		     parameter = "修改企业精神(企业名称:"+spirit.getEnterpriseName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseSpirit.setOrganizationID(organizationID);
		enterpriseSpirit.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseSpirit);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除企业精神
	 public String delEnterpriseSpirit()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseSpirit.getSpiritID()};
		    String hql2="from EnterpriseSpirit where companyID=?  and spiritID=?";
		    EnterpriseSpirit spirit=(EnterpriseSpirit) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业精神(企业名称:"+spirit.getEnterpriseName()+")", account);
	    	String hql="delete from EnterpriseSpirit where companyID=?  and spiritID=?";
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

	public EnterpriseSpirit getEnterpriseSpirit() {
		return enterpriseSpirit;
	}

	public void setEnterpriseSpirit(EnterpriseSpirit enterpriseSpirit) {
		this.enterpriseSpirit = enterpriseSpirit;
	}

	

}
