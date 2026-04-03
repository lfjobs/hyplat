package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.EnterpriseLogo;
import hy.ea.bo.office.EnterpriseSpirit;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

//集团汇总  企业精神
import com.opensymphony.xwork2.ActionContext;

public class EnterpriseSpiritGAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private EnterpriseSpirit enterpriseSpirit;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	

	// 根据条件查询企业形象
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseSpirit);
		return getEnterpriseSpiritList();
	}

	// 企业形象列表
	public String getEnterpriseSpiritList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql = "select kk.spiritid,kk.enterprisename,kk.spiritcontent,kk.spiritnote from dtenterprisespirit kk " +
				"where exists(select dd.companyid from dtcompany dd where kk.companyid = dd.companyid  and  " +
				" (dd.companyid = ? or dd.companypid = ?))";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			enterpriseSpirit = (EnterpriseSpirit) session.get("tablesearch");
			if (enterpriseSpirit.getEnterpriseName() != null
					&& !"".equals(enterpriseSpirit.getEnterpriseName().trim())) {
				enterpriseSpirit = (EnterpriseSpirit) session.get("tablesearch");
				sql += " and kk.enterprisename like ?";
				params.add("%" + enterpriseSpirit.getEnterpriseName().trim() + "%");

			}
			if (enterpriseSpirit.getSpiritContent() != null
					&& !"".equals(enterpriseSpirit.getSpiritContent().trim())) {
				enterpriseSpirit = (EnterpriseSpirit) session.get("tablesearch");
				sql += "and kk.spiritcontent like ?";
				params.add('%' + enterpriseSpirit.getSpiritContent().trim() + '%');
			}
		}
		Object[] param = params.toArray();
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), param);
		return "spiritlist";
	}

	// 导出企业形象列表

	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql = "select kk.enterprisename,kk.spiritcontent,kk.spiritnote" +
				" from dtenterprisespirit kk where exists(select * from dtcompany dd " +
				"where kk.companyid = dd.companyid  and  exists(select dd.groupcompanysn " +
				"from dtcompany aa where aa.groupcompanysn = dd.groupcompanysn and kk.companyid = ? ))";
		Object[] params = { account.getCompanyID() };
		excelStream = excelService.showExcel(EnterpriseLogo.columnHeadings(),baseBeanService.getListBeanBySqlAndParams(sql, params));
		String companyID = account.getCompanyID();
		CLogBook logbook = logBookService.saveCLogBook(companyID, "导出企业形象",
				account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	public EnterpriseSpirit getEnterpriseSpirit() {
		return enterpriseSpirit;
	}

	public void setEnterpriseSpirit(EnterpriseSpirit enterpriseSpirit) {
		this.enterpriseSpirit = enterpriseSpirit;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	
}
