package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.CompleteAllter;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/*
 * 整改通知单管理
 * */
@Controller
@Scope("prototype")
public class CompleteAllterAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
    private CompleteAllter completeAllter;
	private PageForm pageForm;
	private InputStream excelStream;
	private List<BaseBean> organizationlist;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private String result;
	private String search;
	private Company company;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询整改通知单管理
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", completeAllter);
		return getCmpleteAllterList();
	}

	private DetachedCriteria getCALList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CompleteAllter.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			completeAllter = (CompleteAllter) session.get("tablesearch");
			if(!completeAllter.getVoucherNumber().equals(""))
			{
				dc.add(Restrictions.like("voucherNumber", completeAllter.getVoucherNumber(),MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("deptID", completeAllter.getDeptID()));
		} 
		return dc;
	}

	//整改通知单管理列表
	public String getCmpleteAllterList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = {companyID};
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getCALList());
		return "completeAllterList";	
	}
	// 导出整改通知单管理列表

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = {companyID};
		String ohql = "from COrganization where companyID=? ";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : organizationlist) {
			COrganization c=(COrganization) b;
			map.put(c.getOrganizationID(), c.getOrganizationName());
		}
		map.put(company.getCompanyID(), company.getCompanyName());
		CompleteAllter.setOMap(map);
		organizationlist.add(company);
		excelStream = excelService.showExcel(CompleteAllter.columnHeadings(), baseBeanService.getListByDC(getCALList()));
		String organizationID=(String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出整改通知单管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
  //整改通知单管理保存
    
    public String saveCompleteAllter()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,
					photoFileName, photo, account.getCompanyID(),"/human/informBills/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			completeAllter.setAccessories(photoPath);
		}
		if (completeAllter.getCompleteAllterID()== null
				|| "".equals(completeAllter.getCompleteAllterID())) {
			completeAllter.setCompleteAllterID(serverService.getServerID("completeAllterID"));
			parameter = "添加整改通知单管理(凭证号:"+completeAllter.getVoucherNumber()+")";
		}
		else
		{
			 String hql2="from CompleteAllter where companyID=?  and completeAllterID=?";
			 CompleteAllter jeom=(CompleteAllter) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),completeAllter.getCompleteAllterID()});
		     parameter = "修改整改通知单管理(凭证号:"+jeom.getVoucherNumber()+")";
		}
		completeAllter.setOrganizationID(organizationID);
		completeAllter.setCompanyID(account.getCompanyID());
		//baseBeanService.update(completeAllter);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(completeAllter);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
    	return "success";
    	
    }
    //删除整改通知单管理
	 public String delCompleteAllter()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),completeAllter.getCompleteAllterID()};
		    String hql2="from CompleteAllter where companyID=?  and completeAllterID=?";
		    CompleteAllter jeom=(CompleteAllter) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook cLogBook =  logBookService.saveCLogBook(organizationID, "删除整改通知单管理(凭证号:"+jeom.getVoucherNumber()+")", account);
	    	String hql="delete from CompleteAllter where companyID=?  and completeAllterID=?";
			//baseBeanService.executeHqlByParams(hql, params);
	    	List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(cLogBook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
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

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompleteAllter getCompleteAllter() {
		return completeAllter;
	}

	public void setCompleteAllter(CompleteAllter completeAllter) {
		this.completeAllter = completeAllter;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

}
