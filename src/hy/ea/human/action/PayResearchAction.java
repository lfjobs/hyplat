package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COPayResearch;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.bean.ExcelImport;
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

/**
 * 薪水调查
 * 
 * @author zg
 * 
 */
@Controller
@Scope("prototype")
public class PayResearchAction{
	private static final Logger logger = LoggerFactory.getLogger(PayResearchAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	private InputStream excelStream;
	private COPayResearch payResearch;
	private String result;
	private ExcelImport excelImport;
	private String search;
	private PageForm pageForm;
	private int pageNumber;
	private Map<String, COPayResearch> baseMap;
	private List<BaseBean> beans;

	@Resource
	private CLogBookService logBookService;
	private String parameter;

	/**
	 * 根据条件查询列表
	 * 
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", payResearch);
		return getCOPayResearchList();
	}

	/**
	 * 薪水调查列表
	 * 
	 */
	public String getCOPayResearchList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "getCOPayResearchList";
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria
				.forClass(COPayResearch.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			payResearch = (COPayResearch) session
					.get("tablesearch");
			if(null != payResearch.getIndustryCategory() 
					&& !"".equals(payResearch.getIndustryCategory())){
				dc.add(Restrictions.like("industryCategory", payResearch
						.getIndustryCategory(), MatchMode.ANYWHERE));
			}
			if(null != payResearch.getPost() 
					&& !"".equals(payResearch.getPost())){
				dc.add(Restrictions.like("post", payResearch
						.getPost(), MatchMode.ANYWHERE));
			}
			if(null != payResearch.getWorkingTenure() 
					&& !"".equals(payResearch.getWorkingTenure())){
				dc.add(Restrictions.like("workingTenure", payResearch
						.getWorkingTenure(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	/**
	 * 导出
	 * 
	 * @version zg
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		if (search != null && search.equals("search")) {
			// return searchStaff(account.getCompanyID());
		}
		String hql = "from COPayResearch where companyID = ? and organizationID = ? ";
		Object[] params = { account.getCompanyID(), organizationID };
		excelStream = excelService.showExcel(COPayResearch.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, params));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出薪水调查", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 导入
	 * 
	 * @version zg
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String excelImport() {
		if(excelImport.getExclePathstatus() == 0){
			if(!"excel".equals(excelImport.getExcelFileContentType().split("/")[1].split("-")[1])){
				return "excelImportfail";
			}
			if (excelImport.getExcelFile() != null) {
				excelImport.setLists(excelService.ExcelImporter(excelImport.getExcelFile()));
				if (excelImport.getLists().size() > 0) {
					if (excelImport.getLists().get(0).length - 2 == payResearch
							.excelImportPro().length) {
						excelImport.setPropers(payResearch.excelImportPro());
						excelImport.setDataCount(payResearch.excelImportPro().length);
						return "excelImport";
					}
				}
			}
			this.getCOPayResearchList();
		}else {
			List<BaseBean> list = new ArrayList<BaseBean>();
			excelStream = excelService.showExcel(COPayResearch.columnHeadings(),
					list);
			return "showexcel";
		}
		return "excelImportfail";
	}

	/**
	 * 将导入的数据存入数据库
	 * 
	 * @return
	 */
	public String saveExcelImport() {
		//logger.info("调试信息");
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		beans = new ArrayList<BaseBean>();
		if (this.getBaseMap() != null) {
			for (COPayResearch cpr : this.getBaseMap().values()) {
				if (null == cpr.getPayResearchID()
						|| "".equals(cpr.getPayResearchID())) {
					cpr.setPayResearchID(serverService
							.getServerID("payresearch"));
					parameter = "添加薪水调查(行业类别:" + cpr.getIndustryCategory()
							+ ")";
				} else {
					String hql = "from COPayResearch where companyID = ? and organizationID = ? and payResearchID=? ";
					Object[] params = { account.getCompanyID(), organizationID,
							cpr.getPayResearchID() };
					COPayResearch pay = (COPayResearch) baseBeanService
							.getBeanByHqlAndParams(hql, params);
					parameter = "修改薪水调查(行业类别:" + pay.getIndustryCategory()
							+ ")";
				}
				cpr.setCompanyID(account.getCompanyID());
				cpr.setOrganizationID(organizationID);
				beans.add(cpr);
			}
			CLogBook logBook = logBookService.saveCLogBook(account.getCompanyID(), parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "success";
	}

	public String save() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		if (null == payResearch.getPayResearchID()
				|| "".equals(payResearch.getPayResearchID())) {
			payResearch.setPayResearchID(serverService
					.getServerID("payresearch"));
			parameter = "添加薪水调查(行业类别:" + payResearch.getIndustryCategory()
					+ ")";
		} else {
			String hql = "from COPayResearch where companyID = ? and organizationID = ? and payResearchID=? ";
			Object[] params = { account.getCompanyID(), organizationID,
					payResearch.getPayResearchID() };
			COPayResearch pay = (COPayResearch) baseBeanService
					.getBeanByHqlAndParams(hql, params);
			parameter = "修改薪水调查(行业类别:" + pay.getIndustryCategory() + ")";
		}
		payResearch.setCompanyID(account.getCompanyID());
		payResearch.setOrganizationID(organizationID);
		beans = new ArrayList<BaseBean>();
		beans.add(payResearch);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public String del() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = "delete from COPayResearch where companyID = ? and organizationID = ? and payResearchID = ?";
		Object[] params = { account.getCompanyID(), organizationID,
				payResearch.getPayResearchID() };
		String hql1 = "from COPayResearch where companyID = ? and organizationID = ? and payResearchID = ?";
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除薪水调查(行业类别："
				+ ((COPayResearch) baseBeanService.getBeanByHqlAndParams(hql1,
						params)).getIndustryCategory() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}

	public COPayResearch getPayResearch() {
		return payResearch;
	}

	public void setPayResearch(COPayResearch payResearch) {
		this.payResearch = payResearch;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 获取实体集合
	 * 
	 * @return
	 */
	public Map<String, COPayResearch> getBaseMap() {
		return baseMap;
	}

	/**
	 * 设置实体集合
	 * 
	 * @param baseMap
	 */
	public void setBaseMap(Map<String, COPayResearch> baseMap) {
		this.baseMap = baseMap;
	}

	public ExcelImport getExcelImport() {
		return excelImport;
	}

	public void setExcelImport(ExcelImport excelImport) {
		this.excelImport = excelImport;
	}

	
}
