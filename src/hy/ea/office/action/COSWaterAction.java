package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.COSWater;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
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
 * 用水管理
 * */
@Controller
@Scope("prototype")
public class COSWaterAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private COSWater coWater;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", coWater);
		return getListForPage();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COSWater.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", (String) session
				.get("organizationID")));
		if (search != null && search.equals("search")) {
			coWater = (COSWater) session.get("tablesearch");
			if (!"".equals(coWater.getUnit()))
				dc.add(Restrictions.like("unit", coWater.getUnit(),
						MatchMode.ANYWHERE));
		}
		return dc;
	}

	public String getListForPage() {
		String hql = " from Staff where staffID in (select staffID from COS where companyID = ? and cosStatus = '50' ) ";
		Object[] params = { ((CAccount) (ActionContext.getContext()
				.getSession().get("account"))).getCompanyID() };
		staffList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "list";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(COSWater.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CLogBook cLogBook  = logBookService.saveCLogBook(organizationID, "导出用水管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String save() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (coWater.getWaterID() == null || "".equals(coWater.getWaterID())) {
			coWater.setWaterID(serverService.getServerID("coWater"));
			parameter = "添加用水管理(用水单位:" + coWater.getUnit() + ")";
		} else {
			String hql2 = "from COSWater where companyID=?  and waterID=?";
			COSWater wat = (COSWater) baseBeanService.getBeanByHqlAndParams(
					hql2, new Object[] { account.getCompanyID(),
							coWater.getWaterID() });
			parameter = "修改用水管理((用水单位:" + wat.getUnit() + ")";

		}
		coWater.setOrganizationID(organizationID);
		coWater.setCompanyID(account.getCompanyID());
		//baseBeanService.update(coWater);
		CLogBook cLogBook  = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(coWater);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
	}

	public String del() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), coWater.getWaterID() };
		String hql2 = "from COSWater where companyID=?  and waterID=?";
		COSWater wat = (COSWater) baseBeanService.getBeanByHqlAndParams(hql2,
				params);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除用水管理((用水单位:"
				+ wat.getUnit() + ")", account);
		String hql = "delete from COSWater where companyID=?  and waterID=?";
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public COSWater getCoWater() {
		return coWater;
	}

	public void setCoWater(COSWater coWater) {
		this.coWater = coWater;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
