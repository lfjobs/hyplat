package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseGoal;
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
 * 企业目标管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseGoalAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseGoal enterpriseGoal;
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

	//根据条件查询企业目标
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseGoal);
		return getEnterpriseGoalList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseGoal.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseGoal = (EnterpriseGoal) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseGoal.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 企业目标列表
	public String getEnterpriseGoalList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "goallist";	
	}
  //企业目标保存
    
    public String saveEnterpriseGoal()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (enterpriseGoal.getGoalID()== null|| "".equals(enterpriseGoal.getGoalID())) {
			enterpriseGoal.setGoalID(serverService.getServerID("enterpriseGoal"));
			parameter = "添加企业目标(企业名称:"+enterpriseGoal.getEnterpriseName()+")";
		}
		else
		{
			 String hql2="from EnterpriseGoal where companyID=?  and goalID=?";
			 EnterpriseGoal spirit=(EnterpriseGoal) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseGoal.getGoalID()});
		     parameter = "修改企业目标(企业名称:"+spirit.getEnterpriseName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseGoal.setOrganizationID(organizationID);
		enterpriseGoal.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseGoal);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "succ";
    }
    //删除企业目标
	 public String delEnterpriseGoal()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseGoal.getGoalID()};
		    String hql2="from EnterpriseGoal where companyID=?  and goalID=?";
		    EnterpriseGoal spirit=(EnterpriseGoal) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业目标(企业名称:"+spirit.getEnterpriseName()+")", account);
	    	String hql="delete from EnterpriseGoal where companyID=?  and goalID=?";
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

	public EnterpriseGoal getEnterpriseGoal() {
		return enterpriseGoal;
	}

	public void setEnterpriseGoal(EnterpriseGoal enterpriseGoal) {
		this.enterpriseGoal = enterpriseGoal;
	}

	
	

}
