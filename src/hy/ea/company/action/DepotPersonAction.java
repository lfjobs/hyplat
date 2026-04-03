package hy.ea.company.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.DepotPerson;
import hy.ea.bo.human.COrganization;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
 * 库房责任人
 * */
@Controller
@Scope("prototype")
public class DepotPersonAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private COrganizationService organizationService;
	@Resource
	private CompanyService companyService;
	private String parameter;
	private DepotPerson depotPerson;
	private PageForm pageForm;
	private InputStream excelStream;
	private List<COrganization> orgnizationList;
	private String search;
	private Company com;
	private int pageNumber;
	private Map<String, DepotPerson> depotPersonmap;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Company getCom() {
		return com;
	}

	public void setCom(Company com) {
		this.com = com;
	}

	// 根据条件查询库房责任人
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", depotPerson);
		return getListDepotPerson();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		com = companyService.getCompanyByCompanyID(companyID);
		DetachedCriteria dc = DetachedCriteria.forClass(DepotPerson.class);
		dc.add(Restrictions.eq("companyID", companyID));
		Map<String, String> ccmap = new HashMap<String, String>();

		// 所有部门列表
		orgnizationList = new ArrayList<COrganization>();
		orgnizationList = organizationService.getOrganizationList(companyID);
		if (null != orgnizationList) {
			for (COrganization o : orgnizationList) {
				ccmap.put(o.getOrganizationID(), o.getOrganizationName());
			}
		}
		ccmap.put(com.getCompanyID(), com.getCompanyName());
		DepotPerson.setOMap(ccmap);
		dc.add(Restrictions.eq("depotID", depotPerson.getDepotID()));
		if (search != null && search.equals("search")) {
			depotPerson = (DepotPerson) session.get("tablesearch");
			if (depotPerson.getResponsible() != null
					&& !"".equals(depotPerson.getResponsible())) {
				dc.add(Restrictions.like("responsible", depotPerson
						.getResponsible(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	// 库房责任人列表
	public String getListDepotPerson() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "depotperson";
	}

	// 库房责任人导出

	public String showExcel() {
		excelStream = excelService.showExcel(DepotPerson.columnHeadings(),
				baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook = logBookService.saveCLogBook(null, "导出库房责任人", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	// 库房责任人保存

	public String saveDepotPerson() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		ArrayList<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if (depotPersonmap != null) {
			for (DepotPerson depotPersons : depotPersonmap.values()) {
				depotPersons.setDepotID(depotPerson.getDepotID());
				if (depotPersons.getDepotPersonID() == null
						|| "".equals(depotPersons.getDepotPersonID())) {
					depotPersons.setDepotPersonID(serverService
							.getServerID("depotPerson"));
					parameter = "添加库房责任人(责任人:" + depotPersons.getResponsible()
							+ ")";
				} else {
					String hql2 = "from DepotPerson where companyID=?  and depotPersonID=?";
					DepotPerson network = (DepotPerson) baseBeanService
							.getBeanByHqlAndParams(hql2, new Object[] {
									account.getCompanyID(),
									depotPersons.getDepotPersonID() });
					parameter = "修改库房责任人(责任人:" + network.getResponsible() + ")";

				}
				depotPersons.setCompanyID(account.getCompanyID());
				baseBeanList.add(depotPersons);
			}
			CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
			baseBeanList.add(cLogBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,	null, null);
			
		}
		return "success";
	}

	// 删除库房责任人
	public String delDepotPerson() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(),
				depotPerson.getDepotPersonID() };
		String hql2 = "from DepotPerson where companyID=?  and depotPersonID=?";
		DepotPerson network = (DepotPerson) baseBeanService
				.getBeanByHqlAndParams(hql2, params);
		
		CLogBook cLogBook = logBookService.saveCLogBook(null, "删除库房责任人(责任人:"
				+ network.getResponsible() + ")", account);
		String hql = "delete from DepotPerson where companyID=?  and depotPersonID=?";
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
				new String[] { hql }, params);
		return "success";
	}

	public Map<String, DepotPerson> getDepotPersonmap() {
		return depotPersonmap;
	}

	public void setDepotPersonmap(Map<String, DepotPerson> depotPersonmap) {
		this.depotPersonmap = depotPersonmap;
	}

	public DepotPerson getDepotPerson() {
		return depotPerson;
	}

	public void setDepotPerson(DepotPerson depotPerson) {
		this.depotPerson = depotPerson;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<COrganization> getOrgnizationList() {
		return orgnizationList;
	}

	public void setOrgnizationList(List<COrganization> orgnizationList) {
		this.orgnizationList = orgnizationList;
	}

}
