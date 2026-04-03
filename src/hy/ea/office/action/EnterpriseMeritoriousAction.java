package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseMeritorious;
import hy.ea.service.CLogBookService;
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
 * 企业功臣管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseMeritoriousAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private String parameter;
	private EnterpriseMeritorious enterpriseMeritorious;
	private PageForm pageForm;
	private InputStream excelStream;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private String search;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询企业功臣
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseMeritorious);
		return getEnterpriseMeritoriousList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseMeritorious.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseMeritorious = (EnterpriseMeritorious) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseMeritorious.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 企业功臣列表
	public String getEnterpriseMeritoriousList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
		return "meritoriouslist";	
	}
  //企业功臣保存
    
    public String savEnterpriseMeritorious()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,photoFileName, photo, account.getCompanyID(),"/human/carInformation/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			enterpriseMeritorious.setPicture(photoPath);
		}
		if (enterpriseMeritorious.getMeritoriouID()== null|| "".equals(enterpriseMeritorious.getMeritoriouID())) {
			enterpriseMeritorious.setMeritoriouID(serverService.getServerID("enterpriseMeritorious"));
			parameter = "添加企业功臣(功臣名称:"+enterpriseMeritorious.getStaffName()+")";
		}
		else
		{
			 String hql2="from EnterpriseMeritorious where companyID=?  and meritoriouID=?";
			 EnterpriseMeritorious spirit=(EnterpriseMeritorious) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseMeritorious.getMeritoriouID()});
		     parameter = "修改企业功臣(功臣名称:"+spirit.getStaffName()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseMeritorious.setOrganizationID(organizationID);
		enterpriseMeritorious.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseMeritorious);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除企业功臣
	 public String delEnterpriseMeritorious()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseMeritorious.getMeritoriouID()};
		    String hql2="from EnterpriseMeritorious where companyID=?  and meritoriouID=?";
		    EnterpriseMeritorious spirit=(EnterpriseMeritorious) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业功臣(功臣名称:"+spirit.getStaffName()+")", account);
	    	String hql="delete from EnterpriseMeritorious where companyID=?  and meritoriouID=?";
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

	public EnterpriseMeritorious getEnterpriseMeritorious() {
		return enterpriseMeritorious;
	}

	public void setEnterpriseMeritorious(EnterpriseMeritorious enterpriseMeritorious) {
		this.enterpriseMeritorious = enterpriseMeritorious;
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

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	
	

}
