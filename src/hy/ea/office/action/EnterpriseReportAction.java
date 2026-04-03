package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseReport;
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
 * 企业纪实管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseReportAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseReport enterpriseReport;
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

	//根据条件查询企业纪实
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseReport);
		return getEnterpriseReportList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseReport.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseReport = (EnterpriseReport) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseReport.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 企业纪实列表
	public String getEnterpriseReportList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "reportlist";	
	}
  //企业纪实保存
    
    public String saveEnterpriseReport()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (enterpriseReport.getReportID()== null|| "".equals(enterpriseReport.getReportID())) {
			enterpriseReport.setReportID(serverService.getServerID("enterpriseReport"));
			parameter = "添加企业纪实(纪实名称:"+enterpriseReport.getReportName()+")";
		}
		else
		{
			 String hql2="from EnterpriseReport where companyID=?  and reportID=?";
			 EnterpriseReport spirit=(EnterpriseReport) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseReport.getReportID()});
		     parameter = "修改企业纪实(纪实名称:"+spirit.getReportName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseReport.setOrganizationID(organizationID);
		enterpriseReport.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseReport);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除企业纪实
	 public String delEnterpriseReport()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseReport.getReportID()};
		    String hql2="from EnterpriseReport where companyID=?  and reportID=?";
		    EnterpriseReport spirit=(EnterpriseReport) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业纪实(纪实名称:"+spirit.getReportName()+")", account);
	    	String hql="delete from EnterpriseReport where companyID=?  and reportID=?";
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

	public EnterpriseReport getEnterpriseReport() {
		return enterpriseReport;
	}

	public void setEnterpriseReport(EnterpriseReport enterpriseReport) {
		this.enterpriseReport = enterpriseReport;
	}

	
	

}
