package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseLogo;
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
 * 企业形象管理
 * */
@Controller
@Scope("prototype")
public class EnterpriseLogoAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseLogo enterpriseLogo;
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

	//根据条件查询企业形象
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseLogo);
		return getEnterpriseLogoList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseLogo.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			enterpriseLogo = (EnterpriseLogo) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", enterpriseLogo.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 企业形象列表
	public String getEnterpriseLogoList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),  (pageNumber==0?10:pageNumber), getList());
		return "logolist";	
	}
	// 导出企业形象列表

	public String showExcel() {
		excelStream = excelService.showExcel(EnterpriseLogo.columnHeadings(), baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出企业形象", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //企业形象保存
    
    public String saveEnterpriseLogo()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,photoFileName, photo, account.getCompanyID(),"/human/enterpriseLogo/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			enterpriseLogo.setLogoPhoto(photoPath);
		}
		if (enterpriseLogo.getLogoID()== null|| "".equals(enterpriseLogo.getLogoID())) {
			enterpriseLogo.setLogoID(serverService.getServerID("enterpriseLogo"));
			parameter = "添加企业形象(徽标类型:"+enterpriseLogo.getLogoType()+")";
		}
		else
		{
			 String hql2="from EnterpriseLogo where companyID=?  and logoID=?";
			 EnterpriseLogo logo=(EnterpriseLogo) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),enterpriseLogo.getLogoID()});
		     parameter = "修改企业形象(徽标类型:"+logo.getLogoType()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		enterpriseLogo.setOrganizationID(organizationID);
		enterpriseLogo.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(enterpriseLogo);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除企业形象
	 public String delEnterpriseLogo()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseLogo.getLogoID()};
		    String hql2="from EnterpriseLogo where companyID=?  and logoID=?";
		    EnterpriseLogo logo=(EnterpriseLogo) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业形象(徽标类型:"+logo.getLogoType()+")", account);
	    	String hql="delete from EnterpriseLogo where companyID=?  and logoID=?";
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

	public EnterpriseLogo getEnterpriseLogo() {
		return enterpriseLogo;
	}

	public void setEnterpriseLogo(EnterpriseLogo enterpriseLogo) {
		this.enterpriseLogo = enterpriseLogo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}


}
