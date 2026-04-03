package hy.ea.office.action;
/*
 * 工资级别升降管理 lwt
 */

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.COSPayGradeModulation;
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

@Controller
@Scope("prototype")
public class COSPayGradeModulationAction {
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
	private COSPayGradeModulation payGradeModulation;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> orgList;
	private File photo;
	private String photoFileName;
	private Company company;
	private String photoContentType;
	private int pageNumber;
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", payGradeModulation);
		return getListForPage();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria
				.forClass(COSPayGradeModulation.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session
				.get("organizationID")));
		if (search != null && search.equals("search")) {
			payGradeModulation = (COSPayGradeModulation) session
					.get("tablesearch");
			if (!"".equals(payGradeModulation.getClasses0()))
				dc.add(Restrictions.like("voucherNomber", payGradeModulation
						.getVoucherNomber(), MatchMode.ANYWHERE));
			if (!"".equals(payGradeModulation.getStaffCode()))
				dc.add(Restrictions.like("staffCode", payGradeModulation
						.getStaffCode(), MatchMode.ANYWHERE));
		}
		return dc;
	}

	public String getListForPage() {
		String hql = " from COrganization where companyID = ? and Status = '00'";
		Object[] params = { ((CAccount) (ActionContext.getContext().getSession().get("account"))).getCompanyID() };
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params);
		orgList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "list";
	}

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		Object[] params1 = {companyID};
		String ohql = "from COrganization where companyID=? and Status = '00'";
		orgList = baseBeanService.getListBeanByHqlAndParams(ohql,params1);
		company=(Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", params1);
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean b : orgList) {
			COrganization c=(COrganization) b;
			map.put(c.getOrganizationID(), c.getOrganizationName());
		}
		map.put(company.getCompanyID(), company.getCompanyName());
		COSPayGradeModulation.setOMap(map);
		orgList.add(company);
		String organizationID=(String) session.get("organizationID");
		excelStream = excelService.showExcel(COSPayGradeModulation.columnHeadings(), baseBeanService.getListByDC(getList()));
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,"导出工资级别升降", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String save() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,
					photoFileName, photo, account.getCompanyID(),"/human/payGradeModulation/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			payGradeModulation.setAffix(photoPath);
		}
		if (payGradeModulation.getPgmID() == null
				|| "".equals(payGradeModulation.getPgmID())) {
			payGradeModulation.setPgmID(serverService.getServerID("paygrade"));
			parameter = "添加工资级别升降(凭证号:"+payGradeModulation.getVoucherNomber()+")";
		}
		else
		{
			Object[] params = { account.getCompanyID(),payGradeModulation.getPgmID() };
			 String hql2="from COSPayGradeModulation where companyID=?  and pgmID=?";
			 COSPayGradeModulation paycrade=(COSPayGradeModulation) baseBeanService.getBeanByHqlAndParams(hql2,params);
		     parameter = "修改工资级别升降(凭证号:"+paycrade.getVoucherNomber()+")";
		
		}
		payGradeModulation.setOrganizationID(organizationID);
		payGradeModulation.setCompanyID(account.getCompanyID());
		//baseBeanService.update(payGradeModulation);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(payGradeModulation);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}

	public String del() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(),
				payGradeModulation.getPgmID() };
		String organizationID = (String) session.get("organizationID");
		 String hql2="from COSPayGradeModulation where companyID=?  and pgmID=?";
		 COSPayGradeModulation paycrade=(COSPayGradeModulation) baseBeanService.getBeanByHqlAndParams(hql2,params);
		 CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除工资级别升降(凭证号:"+paycrade.getVoucherNomber()+")", account);
		String hql = "delete from COSPayGradeModulation where companyID=?  and pgmID=?";
		//baseBeanService.executeHqlByParams(hql, params);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
		return "success";
	}

	public final COSPayGradeModulation getPayGradeModulation() {
		return payGradeModulation;
	}

	public final void setPayGradeModulation(
			COSPayGradeModulation payGradeModulation) {
		this.payGradeModulation = payGradeModulation;
	}

	public final PageForm getPageForm() {
		return pageForm;
	}

	public final void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public final String getResult() {
		return result;
	}

	public final void setResult(String result) {
		this.result = result;
	}

	public final String getSearch() {
		return search;
	}

	public final void setSearch(String search) {
		this.search = search;
	}

	public final List<BaseBean> getOrgList() {
		return orgList;
	}

	public final void setOrgList(List<BaseBean> orgList) {
		this.orgList = orgList;
	}

	public final InputStream getExcelStream() {
		return excelStream;
	}

	public final void setExcelStream(InputStream excelStream) {
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
