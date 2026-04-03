package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.CarBills;
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
 * 派车单
 * */
@Controller
@Scope("prototype")
public class CarBillsAction {
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
	private CarBills carBills;
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

	// 根据条件查询派车单
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", carBills);
		return getcarBillsList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarBills.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			carBills = (CarBills) session.get("tablesearch");
			if (!carBills.getVoucherNum().equals("")) {
				dc.add(Restrictions.like("voucherNum",
						carBills.getVoucherNum(), MatchMode.ANYWHERE));
			}
			if (!carBills.getPrincipal().equals("")) {
				dc.add(Restrictions.like("principal", carBills.getPrincipal(),
						MatchMode.ANYWHERE));
			}
			dc.add(Restrictions.eq("deptID", carBills.getDeptID()));
		}
		return dc;
	}

	// 派车单列表
	public String getcarBillsList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = { companyID };
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params1);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "carBillslist";
	}

	// 导出派车单列表

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = { companyID };
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params1);
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : organizationlist) {
			COrganization c = (COrganization) b;
			map.put(c.getOrganizationID(), c.getOrganizationName());
		}
		map.put(company.getCompanyID(), company.getCompanyName());
		CarBills.setOMap(map);
		organizationlist.add(company);
		excelStream = excelService.showExcel(CarBills.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		String organizationID = (String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"导出派车单", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	// 派车单保存

	public String savecarBills() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/carBills/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			carBills.setAccessory(photoPath);
		}
		if (carBills.getCarBillsID() == null
				|| "".equals(carBills.getCarBillsID())) {
			carBills.setCarBillsID(serverService.getServerID("carBills"));
			parameter = "添加派车单(凭证号:" + carBills.getVoucherNum() + ")";
		} else {
			String hql2 = "from CarBills where carBillsID=? and companyID=?";
			CarBills carb = (CarBills) baseBeanService.getBeanByHqlAndParams(
					hql2, new Object[] { carBills.getCarBillsID(),
							account.getCompanyID() });
			parameter = "修改派车单(凭证号:" + carb.getVoucherNum() + ")";

		}
		carBills.setOrganizationID(organizationID);
		carBills.setCompanyID(account.getCompanyID());
		// baseBeanService.update(carBills);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(carBills);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				null, null);
		return "success";
	}

	// 删除派车单
	public String delcarBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), carBills.getCarBillsID() };
		String hql2 = "from CarBills where  companyID=?  and carBillsID=?";
		CarBills carb = (CarBills) baseBeanService.getBeanByHqlAndParams(hql2,
				params);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				"删除派车单(凭证号:" + carb.getVoucherNum() + ")", account);
		String hql = "delete from CarBills where companyID=?  and carBillsID=?";
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				new String[] { hql }, params);
		return "success";
	}

	public CarBills getCarBills() {
		return carBills;
	}

	public void setCarBills(CarBills carBills) {
		this.carBills = carBills;
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
