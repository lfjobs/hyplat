package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.StaffTransfer;
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
 * 员工调令单管理
 * */
@Controller
@Scope("prototype")
public class StaffTransferAction {
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
    private StaffTransfer staffTransfer;
	private PageForm pageForm;
	private InputStream excelStream;
	private List<BaseBean> organizationlist;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private String search;
	private Company company;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询员工调令单
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", staffTransfer);
		return getstaffTransferList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(StaffTransfer.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			staffTransfer = (StaffTransfer) session.get("tablesearch");
			if(!staffTransfer.getVoucherNum().equals(""))
			{
				dc.add(Restrictions.like("voucherNum", staffTransfer.getVoucherNum(),MatchMode.ANYWHERE));
			}
			if(!staffTransfer.getPrincipal().equals(""))
			{
				dc.add(Restrictions.like("principal", staffTransfer.getPrincipal(),MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("deptID", staffTransfer.getDeptID()));
		} 
		return dc;
	}

	//员工调令单列表
	public String getstaffTransferList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = {companyID};
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "staffTransferlist";	
	}
	// 导出员工调令单列表

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = {companyID};
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : organizationlist) {
			COrganization c=(COrganization) b;
			map.put(c.getOrganizationID(), c.getOrganizationName());
		}
		map.put(company.getCompanyID(), company.getCompanyName());
		StaffTransfer.setOMap(map);
		organizationlist.add(company);
		excelStream = excelService.showExcel(StaffTransfer.columnHeadings(),baseBeanService.getListByDC(getList()));
		String organizationID=(String) session.get("organizationID");
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出员工调令单", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //员工调令单保存
    
    public String savestaffTransfer()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,photoFileName, photo, account.getCompanyID(),"/human/staffTransfer/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			staffTransfer.setAccessory(photoPath);
		}
		if (staffTransfer.getTransferID()== null
				|| "".equals(staffTransfer.getTransferID())) {
			staffTransfer.setTransferID(serverService.getServerID("staffTransfer"));
			parameter = "添加员工调令单(凭证号:"+staffTransfer.getVoucherNum()+")";
		}
		else
		{
			 String hql2="from StaffTransfer where companyID=?  and transferID=?";
			 StaffTransfer tran=(StaffTransfer) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),staffTransfer.getTransferID()});
		     parameter = "修改员工调令单(凭证号:"+tran.getVoucherNum()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		staffTransfer.setOrganizationID(organizationID);
		staffTransfer.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(staffTransfer);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "succ";
    }
    //删除员工调令单
	 public String delstaffTransfer()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),staffTransfer.getTransferID()};
		    String hql2="from StaffTransfer where companyID=?  and transferID=?";
			 StaffTransfer tran=(StaffTransfer) baseBeanService.getBeanByHqlAndParams(hql2, params);
			 CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除员工调令单(凭证号:"+tran.getVoucherNum()+")", account);
	    	String[] hql={"delete from StaffTransfer where companyID=?  and transferID=?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			return "succ";
	    }

	public StaffTransfer getStaffTransfer() {
		return staffTransfer;
	}

	public void setStaffTransfer(StaffTransfer staffTransfer) {
		this.staffTransfer = staffTransfer;
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

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	
}
