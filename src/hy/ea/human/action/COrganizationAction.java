package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SInterface;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SInterfaceService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class COrganizationAction {
	@Resource
	private COrganizationService organizationService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ServerService serverService;
	@Resource
	private SInterfaceService sinterfaceService;
	@Resource
	private CLogBookService logBookService;
	private COrganization organization;
	private COrganization porganization;
	private String organizationID;
	private String organizationName;
	private Company company;
	private String result;
	private PageForm pageForm;
	private List<COrganization> organizationlist;
	private List<COrganization> children;
	private List<SInterface> SInterfaceList;
	private InputStream excelStream;
	private String childrenID;
	private String selectedtreeID;
	private String parameter;
	private int pageNumber;
	private String search;
	private List<BaseBean> beans;

	/*
	 * 当前机构父ID
	 */
	private String currentOrgnizationPID;

	/**
	 * 人事科目管理,机构设置下,查找当前机构下级机构 from COrganization where organizationPID = ? and
	 * companyID = ? and Status = '00' order by organizationNumber
	 * 
	 * @return
	 */
	public String subordinateList() {
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getList());

		SInterfaceList = sinterfaceService.getSInterfaceListByStatus();
		return "subordinateList";
	}

	/*
	 * 
	 * 查询
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", organization);
		return subordinateList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(COrganization.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationPID", organizationID));
		dc.add(Restrictions.ne("Status", "98"));
		if (search != null && search.equals("search")) {
			organization = (COrganization) session.get("tablesearch");
			if (!"".equals(organization.getOcode())
					&& organization.getOcode() != null) {
				dc.add(Restrictions.like("ocode", organization.getOcode(),
						MatchMode.ANYWHERE));
			}
			if (!"".equals(organization.getOrganizationName())
					&& organization.getOrganizationName() != null) {
				dc.add(Restrictions.like("organizationName",
						organization.getOrganizationName(), MatchMode.ANYWHERE));
			}
			if (!"".equals(organization.getOpostCode())
					&& organization.getOpostCode() != null) {
				dc.add(Restrictions.like("opostCode",
						organization.getOpostCode(), MatchMode.ANYWHERE));
			}
		}
		dc.addOrder(Order.asc("organizationNumber"));
		dc.addOrder(Order.asc("organizationCreateDate"));
		return dc;
	}

	/**
	 * 人事科目管理,机构设置下,添加或保存当前机构下级机构页面
	 * 
	 * @return
	 */
	public String subordinateToAdd() {
		toAdd();
		return "subordinateToAdd";
	}

	/**
	 * 人事科目管理,机构设置下,添加或修改
	 * 
	 * @return
	 */
	public String subordinateSave() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		if (organization.getOrganizationID().equals("")) {
			String phql = "select count(*) from COrganization ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			organization.setOcode("JGN" + pcount);

			String counthql = "select count(*) from COrganization where organizationPID = ?";
			int count = baseBeanService.getConutByByHqlAndParams(counthql,
					new Object[] { organization.getOrganizationPID() });

			if (count == 0) {
				organization.setOpostCode("0001");
			}
			if (count != 0) {
				String maxhql = "select count(*) from COrganization where opostCode is not null and organizationPID = ?";
				int maxcount = baseBeanService.getConutByByHqlAndParams(maxhql,
						new Object[] { organization.getOrganizationPID() });

				// 判断maxcount+1是否为4位数字，若不足前补0
				if (Integer.toString(maxcount + 1).length() < 4) {
					DecimalFormat myformat = new DecimalFormat(); // 格式化对象的类
					myformat.applyPattern("0000"); // 修改格式模板
					String max = myformat.format(maxcount + 1); // 格式化数字
					organization.setOpostCode(max);
				} else {
					String max1 = Integer.toString(maxcount + 1);
					organization.setOpostCode(max1);
				}
			}

			organization.setOrganizationID(serverService
					.getServerID("organization"));
			organization.setOrganizationCreateDate(new Date());
			parameter = "添加机构(机构名称:" + organization.getOrganizationName() + ")";
		} else {
			COrganization con = (COrganization) baseBeanService
					.getBeanByHqlAndParams(
							"from COrganization where companyID = ? and organizationID = ?",
							new Object[] { account.getCompanyID(),
									organization.getOrganizationID() });
			parameter = "修改机构(机构名称:" + con.getOrganizationName() + ")";
		}
		organization.setCompanyID(account.getCompanyID());
		organization.setStatus("00");
		beans.add(organization);
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return subordinateList();
	}

	/**
	 * 子部门选择注册企业
	 * 
	 * @return
	 */
	public String getRegistAll() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Company company = companyService.getCompanyByCompanyID(account
				.getCompanyID());
		String sql = "select company.companyname companyname, company.companyidentifier companyidentifier, t.companyaddress,t.companymanager,"
				+ " t.companyphone,t.companyemail from dtcompany company left join dtcdetail t on t.companyid = company.companyid"
				+ " where company.companytype = '00' and (company.companypid = ? or company.companyid = ?)";

		List<Object> parms = new ArrayList<Object>();
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());

		if (search != null && search.equals("search")) {
			company = (Company) ActionContext.getContext().getSession()
					.get("tablesearch");
			if (!"".equals(company.getCompanyIdentifier())) {
				sql += " and company.companyidentifier like ?";
				parms.add("%" + company.getCompanyIdentifier() + "%");
			}
			if (!"".equals(company.getCompanyName())) {
				sql += " and company.companyname like ?";
				parms.add("%" + company.getCompanyName() + "%");
			}
		}
		String sqlcount = "select count(1) "
				+ sql.substring(sql.indexOf("from"));
		sql += " order by company.companyregisterdate desc";

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, sqlcount,
				parms.toArray());
		return "registAlllist";
	}

	/**
	 * 根据条件查询注册企业
	 */
	public String toRegistSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", company);
		return getRegistAll();
	}

	/**
	 * 公司组织机构责任人
	 * 
	 * @return
	 */
	public String getOrgManager() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "from Staff where staffID in(select staffID from COS where cosStatus = '50' and companyID = ? and organizationID = ?)";
		Object[] params = { account.getCompanyID(), currentOrgnizationPID };
		if (currentOrgnizationPID != null
				&& currentOrgnizationPID.startsWith("company")) {
			hql = "from Staff where staffID in(select staffID from COS where cosStatus = '50' and companyID = ?)";
			pageForm = baseBeanService.getPageForm(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 5 : pageNumber), hql,
					new Object[] { account.getCompanyID() });
			return "staffForCashier";
		}
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), hql, params);

		return "staffForCashier";
	}

	/**
	 * 人事科目管理,机构设置下,删除
	 * 
	 * @return
	 */
	public String subordinateDel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		if (organizationID.equals(account.getCompanyID())) {
			return getOrganizationListAll();
		}
		COrganization organization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(
						"from COrganization where companyID=? and organizationID = ? ",
						new Object[] { account.getCompanyID(), organizationID });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除机构(机构名称："
				+ organization.getOrganizationName() + ")", account);
		beans.add(logBook);
		String hql1 = " delete from COA where companyID = ?  and organizationID = ?";
		String hql2 = " update COS set organizationID = '99' where companyID = ? and organizationID = ? ";
		String hql3 = " update  COrganization set Status = '98' where companyID = ? and organizationID = ? ";
		Object[] params = { account.getCompanyID(), organizationID };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql1, hql2, hql3 }, params);
		// organizationService.updateOrganizationByID(organizationID,account.getCompanyID());
		organizationID = selectedtreeID;
		return subordinateList();
	}

	// /////////////////////////////////////////////////////////////////////////////////
	/**
	 * 得到单位对象
	 * 
	 * @return
	 */
	public String getCompanyMessage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		company = companyService.getCompanyByCompanyID(account.getCompanyID());
		return "list";
	}

	// 保存机构
	public String saveOrganization() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		if (organization.getOrganizationID().equals("")) {
			String phql = "select count(*) from COrganization ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			organization.setOcode("JGN" + pcount);
			organization.setOrganizationID(serverService
					.getServerID("organization"));
			organization.setOrganizationCreateDate(new Date());
			parameter = "添加机构(机构名称:" + organization.getOrganizationName() + ")";
		} else {
			COrganization con = (COrganization) baseBeanService
					.getBeanByHqlAndParams(
							"from COrganization where companyID = ?  and  organizationID = ? ",
							new Object[] { account.getCompanyID(),
									organization.getOrganizationID() });
			parameter = "修改机构机构名称:" + con.getOrganizationName() + ")";
		}
		organization.setCompanyID(account.getCompanyID());
		organization.setStatus("00");
		beans.add(organization);
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return getOrganizationListAll();
	}

	// 删除机构，根据机构ID和对应的单位ID删除
	public String delOrganization() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (organizationID.equals(account.getCompanyID())) {
			organizationID = selectedtreeID;
			return getOrganizationListAll();
		}
		COrganization organization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(
						"from COrganization where companyID=? and organizationID = ? ",
						new Object[] { account.getCompanyID(), organizationID });
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除机构(机构名称："
				+ organization.getOrganizationName() + ")", account);
		beans.add(logBook);
		String hql1 = " delete from COA where companyID = ?  and organizationID = ?";
		String hql2 = " update COS set organizationID = '99' where companyID = ? and organizationID = ? ";
		String hql3 = " update  COrganization set Status = '98' where companyID = ? and organizationID = ? ";
		Object[] params = { account.getCompanyID(), organizationID };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql1, hql2, hql3 }, params);
		organizationID = selectedtreeID;
		return getOrganizationListAll();
	}

	// 查看某个机构的详细信息
	public String toeditOrganization() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		organization = (COrganization) baseBeanService.getBeanByKey(
				COrganization.class, organizationID);
		Map<String, COrganization> map = new HashMap<String, COrganization>();
		map.put("organization", organization);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 查询机构树,根据机构ID和对应单位ID查询
	 */
	public String getOrganizationList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<COrganization> organizationList = organizationService
				.getOrganizationList(
						(!"0".equals(organizationID) ? organizationID : account
								.getCompanyID()), account.getCompanyID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", organizationList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	// 导出Excel
	public String showExcel() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		List<COrganization> olist = new ArrayList<COrganization>();
		organizationlist = organizationService.getOrganizationByID(
				organizationID, account.getCompanyID(), olist);
		excelStream = organizationService.exportOrganization(organizationlist);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出机构树", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 递归查询出下面所有的机构 lwt
	 */
	public String getOrganizationListAll() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "from COrganization c  where c.companyID=? ";
		List<Object> parms = new ArrayList<Object>();
		parms.add(account.getCompanyID());
		//hql+=" and c.organizationPID=?";
		//parms.add(organizationID);
		if (search != null && search.equals("search")) {
			if (null != organization.getOrganizationUrl()
					&& !"".equals(organization.getOrganizationUrl())) {
				hql += " and c.organizationUrl = ?";
				parms.add(organization.getOrganizationUrl().trim());
			}
			if (null != organization.getOrganizationName()
					&& !"".equals(organization.getOrganizationName())) {
				hql += " and c.organizationName like ?";
				parms.add("%" + organization.getOrganizationName().trim() + "%");
			}
			pageForm = baseBeanService.getPageForm(
					(null != pageForm ? pageForm
							.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, parms.toArray());
		} else {
			if (account.getCompanyID().equals(organizationID)) {
				Object[] params = { account.getCompanyID() };
				pageForm = baseBeanService
						.getPageForm(
								(null != pageForm ? pageForm.getPageNumber()
										: 1),
								(pageNumber == 0 ? 10 : pageNumber),
								" from COrganization where ( Status = '00' or Status = '01' ) and companyID = ?",
								params);
			} else {
				pageForm = organizationService.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber), organizationID,
						account.getCompanyID());
			}
			// SInterfaceList = sinterfaceService.getSInterfaceListByStatus();

		}
		SInterfaceList = sinterfaceService.getSInterfaceListByStatus();
		return "organizationlist";
	}

	public String toAdd() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		organizationlist = new ArrayList<COrganization>();
		if (null != organization.getOrganizationID()) {
			organization = (COrganization) organizationService.getOrganization(
					account.getCompanyID(), organization.getOrganizationID());
			company = (Company) companyService.getCompanyByCompanyID(account
					.getCompanyID());
		} else if (porganization.getOrganizationID() != null) {
			organization.setOrganizationPID(porganization.getOrganizationID());
			organizationlist.add(porganization);
		}
		SInterfaceList = sinterfaceService.getSInterfaceListByStatus();
		return "add";
	}

	/**
	 * 修改机构去机构属性
	 */
	public String toAddgetorganizationlist() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String OID = (String) ActionContext.getContext().getSession()
				.get("organizationID");
		List<COrganization> corganizationlist = organizationService
				.getOrganizationList(account.getCompanyID());
		organization = (COrganization) organizationService.getOrganization(
				account.getCompanyID(), OID);
		organizationlist = new ArrayList<COrganization>();
		for (int i = 0; i < corganizationlist.size(); i++) {
			COrganization corganization = corganizationlist.get(i);
			if (!corganization.getOrganizationID().equals(organizationID)) {
				organizationlist.add(corganization);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", organizationlist);
		map.put("organization", organization);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";

	}

	/**
	 * 去排序子机构页面
	 */
	public String toSortChildOrganization() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		children = organizationService.getOrganizationList(organizationID,
				account.getCompanyID());
		return "sortchildren";
	}

	/**
	 * 排序子机构
	 * 
	 * @author zg
	 * @verson 2011-4-19
	 */
	public String sortChildOrganization() {
		String[] ids = childrenID.split("_");
		String hql = "";
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			hql += "update COrganization set organizationNumber = " + i
					+ " where organizationID = '" + id + "'_";
		}
		String[] hqls = hql.substring(0, hql.length() - 1).split("_");

		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hqls, null);
		return getOrganizationListAll();
	}
    /**
     * 查询公司所有部门数据
     */
    public String getOrganizationListByCompanyId() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<COrganization> corganizationlist = organizationService
                .getOrganizationListAll(companyId);
        JSONArray obj = JSONArray.fromObject(corganizationlist);
        result = obj.toString();
        return "success";
    }
    public COrganization getOrganization() {
		return organization;
	}

	public void setOrganization(COrganization organization) {
		this.organization = organization;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public List<COrganization> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<COrganization> organizationlist) {
		this.organizationlist = organizationlist;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<COrganization> getChildren() {
		return children;
	}

	public void setChildren(List<COrganization> children) {
		this.children = children;
	}

	public String getSelectedtreeID() {
		return selectedtreeID;
	}

	public void setSelectedtreeID(String selectedtreeID) {
		this.selectedtreeID = selectedtreeID;
	}

	public List<SInterface> getSInterfaceList() {
		return SInterfaceList;
	}

	public void setSInterfaceList(List<SInterface> interfaceList) {
		SInterfaceList = interfaceList;
	}

	public String getChildrenID() {
		return childrenID;
	}

	public void setChildrenID(String childrenID) {
		this.childrenID = childrenID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public COrganization getPorganization() {
		return porganization;
	}

	public void setPorganization(COrganization porganization) {
		this.porganization = porganization;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getCurrentOrgnizationPID() {
		return currentOrgnizationPID;
	}

	public void setCurrentOrgnizationPID(String currentOrgnizationPID) {
		this.currentOrgnizationPID = currentOrgnizationPID;
	}

}
