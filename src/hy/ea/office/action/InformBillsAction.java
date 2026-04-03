package hy.ea.office.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.InformBills;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.*;
/*
 * 通知单管理
 * */
@Controller
@Scope("prototype")
public class InformBillsAction {
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
    private InformBills informBills;
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

	//根据条件查询通知单
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", informBills);
		return getInformBillsList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(InformBills.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			informBills = (InformBills) session.get("tablesearch");
			if(!informBills.getVoucherNum().equals(""))
			{
				dc.add(Restrictions.like("voucherNum", informBills.getVoucherNum(),MatchMode.ANYWHERE));
			}
			if(!informBills.getPrincipal().equals(""))
			{
				dc.add(Restrictions.like("principal", informBills.getPrincipal(),MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("deptID", informBills.getDeptID()));
		} 
		return dc;
	}

	//通知单列表
	public String getInformBillsList() {
        try {
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            String companyID = account.getCompanyID();
            Object[] params1 = {companyID};
            String ohql = "from COrganization where companyID=? and Status = '00'";
            organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
            company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
            pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
        } catch (Exception e){}
        return "informbillslist";
	}
	//文件管理页面
	public String getOfficeFileManager() {
		return "officefilemanager";	
	}
	// 导出通知单列表

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
		InformBills.setOMap(map);
		organizationlist.add(company);
		excelStream = excelService.showExcel(InformBills.columnHeadings(), baseBeanService.getListByDC(getList()));
		String organizationID=(String) session.get("organizationID");
		CLogBook  logbook=logBookService.saveCLogBook(organizationID,"导出通知单", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
  //通知单保存
    
    public String saveInformBills()
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
			informBills.setAccessory(photoPath);
		}
		if (informBills.getInformID()== null
				|| "".equals(informBills.getInformID())) {
			informBills.setInformID(serverService.getServerID("informBills"));
			parameter = "添加通知单(凭证号:"+informBills.getVoucherNum()+")";
		}
		else
		{
			 String hql2="from InformBills where companyID=?  and informID=?";
			 InformBills jeom=(InformBills) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),informBills.getInformID()});
		     parameter = "修改通知单(凭证号:"+jeom.getVoucherNum()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		informBills.setOrganizationID(organizationID);
		informBills.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(informBills);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "succ";
    }
    //删除通知单
	 public String delInformBills()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
		    Object[] params = {account.getCompanyID(),informBills.getInformID()};
		    String hql2="from InformBills where companyID=?  and informID=?";
			InformBills jeom=(InformBills) baseBeanService.getBeanByHqlAndParams(hql2, params);
			CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除通知单(凭证号:"+jeom.getVoucherNum()+")", account);
	    	String hql="delete from InformBills where companyID=?  and informID=?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
			return "succ";
	    }

	public InformBills getInformBills() {
		return informBills;
	}

	public void setInformBills(InformBills informBills) {
		this.informBills = informBills;
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

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

}
