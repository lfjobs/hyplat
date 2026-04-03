package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.NetworkFax;
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
/**
 * 网络传真管理
 * */
@Controller
@Scope("prototype")
public class NetworkFaxAction {
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
	private NetworkFax networkFax;
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

	//根据条件查询网络传真管理
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", networkFax);
		return getListNetworkFax();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(NetworkFax.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", (String) session.get("organizationID")));
		if (search != null && search.equals("search")) {
			networkFax = (NetworkFax) session.get("tablesearch");
			if (networkFax.getFaxNum()!=null && !"".equals(networkFax.getFaxNum().trim())) {
				dc.add(Restrictions.like("faxNum", networkFax.getFaxNum().trim(),MatchMode.ANYWHERE));
			}
			if (networkFax.getFaxCompanyID()!=null && !"".equals(networkFax.getFaxCompanyID().trim())) {
				dc.add(Restrictions.like("faxCompanyID", networkFax.getFaxCompanyID().trim(), MatchMode.ANYWHERE));
			}
			
		} 
		return dc;
	}

	// 网络传真管理列表  
	public String getListNetworkFax() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "networkfax";	
	}
	// 导出网络传真管理

	public String showExcel(){
		excelStream = excelService.showExcel(NetworkFax.columnHeadings(), baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出网络传真", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //网络传真管理保存
    
    public String saveNetworkFax()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,photoFileName, photo, account.getCompanyID(),"/human/networkfax/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			networkFax.setFaxPhoto(photoPath);
		}
		if (networkFax.getNetworkFaxID()== null|| "".equals(networkFax.getNetworkFaxID())) {
			networkFax.setNetworkFaxID(serverService.getServerID("networkFax"));
			parameter = "添加网络传真(传真号:"+networkFax.getFaxNum()+")";
		}
		else
		{
			 String hql2="from NetworkFax where companyID=?  and networkFaxID=?";
			 NetworkFax network=(NetworkFax) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),networkFax.getNetworkFaxID()});
		     parameter = "修改网络传真(传真号:"+network.getFaxNum()+")";
		
		}	
		List<BaseBean> beans=new ArrayList<BaseBean>();
		networkFax.setOrganizationID(organizationID);
		networkFax.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(networkFax);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除网络传真管理
	 public String delNetworkFax()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),networkFax.getNetworkFaxID()};
		    String hql2="from NetworkFax where companyID=?  and networkFaxID=?";
		    NetworkFax network=(NetworkFax) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除网络传真(传真号:"+network.getFaxNum()+")", account);
	    	String[] hql={"delete from NetworkFax where companyID=?  and networkFaxID=?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		    return "success";
	    }


	public NetworkFax getNetworkFax() {
		return networkFax;
	}

	public void setNetworkFax(NetworkFax networkFax) {
		this.networkFax = networkFax;
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

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}


}
