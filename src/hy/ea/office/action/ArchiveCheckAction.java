package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.archives.DtArchivesCataloguearchives;
import hy.ea.bo.office.archives.DtArchivesCheckvouchs;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ArchiveCheckAction {
	private static final Logger logger = LoggerFactory.getLogger(ArchiveCheckAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private DtArchivesCheckvouchs archivecheck;
	private PageForm pageForm;
	private String search;
	private Company company;
	private String result;
	private int pageNumber;
	private String parameter;
	private String type;

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", archivecheck);
		return getCheckList();
	}

	public DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria
				.forClass(DtArchivesCheckvouchs.class);

		if (type != null&&!type.equals("")&&!type.equals("null")) {
			if (type.equals("company")) {
				dc.add(Restrictions.eq("companyid", account.getCompanyID()));
			} else {
				String hql = "from Company where companyID = ?";

				hql = "select companyID from Company where companyPID = ? or companyID = ?";

				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] { account.getCompanyID(),
										account.getCompanyID() });

				dc.add(Restrictions.in("companyid", list));
			}
		} else {
			dc.add(Restrictions.eq("createuser", account.getStaffID()));
		}
		dc.addOrder(Order.desc("checktime"));
		if (search != null && search.equals("search")) {
			archivecheck = (DtArchivesCheckvouchs) session.get("tablesearch");
			if (archivecheck.getCategoryid() != null
					&& !archivecheck.getCategoryid().equals("")) {
				dc.add(Restrictions.like("categoryid", archivecheck
						.getCategoryid(), MatchMode.ANYWHERE));
			}
			if (archivecheck.getResults() != null
					&& !archivecheck.getResults().equals("")) {
				dc.add(Restrictions.like("results", archivecheck.getResults(),
						MatchMode.ANYWHERE));
			}
			if (archivecheck.getCheckuser() != null
					&& !archivecheck.getCheckuser().equals("")) {
				dc.add(Restrictions.like("checkuser", archivecheck
						.getCheckuser(), MatchMode.ANYWHERE));
			}
			if (archivecheck.getDepartmentid() != null
					&& !archivecheck.getDepartmentid().equals("")) {
				dc.add(Restrictions.like("departmentid", archivecheck
						.getDepartmentid(), MatchMode.ANYWHERE));
			}
			if (archivecheck.getCompanyid() != null
					&& !archivecheck.getCompanyid().equals("")) {
				dc.add(Restrictions.like("companyid", archivecheck
						.getCompanyid(), MatchMode.ANYWHERE));
			}
			if ((archivecheck.getStartDate() != null && !archivecheck
					.getStartDate().equals(""))
					&& (archivecheck.getEndDate() != null && !archivecheck
							.getEndDate().equals(""))) {

				Date start = Utilities.getDateFromString(archivecheck
						.getStartDate(), "yyyy-MM-dd HH:mm:ss");
				Date end = Utilities.getDateFromString(archivecheck
						.getEndDate(), "yyyy-MM-dd HH:mm:ss");
				dc.add(Restrictions.between("checktime", start, end));
			}

		}
		return dc;
	}

	/**
	 * 获取存储位置列表
	 * 
	 * @return
	 */
	public String getCheckList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());

		String hqlstaff = "from Staff where staffID = ?";
		String hqlcata = "from DtArchivesCataloguearchives where archiveid = ?";
		String hqlorg = "from COrganization where organizationID = ?";

		Staff checkuser = null;
		DtArchivesCataloguearchives catalogue = null;
		COrganization org = null;
		if (pageForm != null) {
			for (BaseBean b : pageForm.getList()) {
				DtArchivesCheckvouchs check = (DtArchivesCheckvouchs) b;
				checkuser = (Staff) baseBeanService.getBeanByHqlAndParams(
						hqlstaff, new Object[] { check.getCheckuser() });
				catalogue = (DtArchivesCataloguearchives) baseBeanService
						.getBeanByHqlAndParams(hqlcata, new Object[] { check
								.getCategoryid() });
				org = (COrganization) baseBeanService.getBeanByHqlAndParams(
						hqlorg, new Object[] { check.getDepartmentid() });

				if (checkuser != null) {
					check.setCheckusername(checkuser.getStaffName());
				}
				if (catalogue != null) {
					check.setCataloguename(catalogue.getName());
				}
				if (org != null) {
					check.setDepartmentname(org.getOrganizationName());
				}
			}
		}
		return "checklist";
	}

	/**
	 * 
	 * 添加位置信息
	 * 
	 * @return
	 */

	public String addCheck() {
		try {
			CAccount account = (CAccount) ActionContext.getContext()
					.getSession().get("account");
			if (account == null) {
				return "not_login";
			}
			if (archivecheck.getCheckid() == null
					|| "".equals(archivecheck.getCheckid())) {
				archivecheck.setCheckid(serverService
						.getServerID("checkVouchsId"));
				archivecheck.setChecktime(new Date());
				archivecheck.setCompanyid(account.getCompanyID());
				archivecheck.setCreateuser(account.getStaffID());
				baseBeanService.save(archivecheck);
			} else {
				String hql = "from DtArchivesCheckvouchs where checkid = ?";
				DtArchivesCheckvouchs oldcheck = (DtArchivesCheckvouchs) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { archivecheck
								.getCheckid() });
				oldcheck.setChecklocation(archivecheck.getChecklocation());
				oldcheck.setChecktime(new Date());
				oldcheck.setResults(archivecheck.getResults());
				oldcheck.setCheckuser(archivecheck.getCheckuser());
				oldcheck.setDepartmentid(archivecheck.getDepartmentid());
				oldcheck.setCategoryid(archivecheck.getCategoryid());

				baseBeanService.update(oldcheck);
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 
	 * 删除位置信息
	 * 
	 * @return
	 */
	public String deleteCheck() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String checkid = request.getParameter("checkid");

		String hql = "from DtArchivesCheckvouchs where checkid = ?";
		DtArchivesCheckvouchs oldcheck = (DtArchivesCheckvouchs) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { checkid });
		baseBeanService.deleteBeanByKey(DtArchivesCheckvouchs.class, oldcheck
				.getCheckkey());

		return "success";
	}

	/**
	 * 
	 * 
	 * 获取修改信息
	 * 
	 * @return
	 */
	public String getCheckView() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String checkid = request.getParameter("checkid");
		String hql = "from DtArchivesCheckvouchs where checkid = ?";
		DtArchivesCheckvouchs check = (DtArchivesCheckvouchs) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { checkid });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", check);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public DtArchivesCheckvouchs getArchivecheck() {
		return archivecheck;
	}

	public void setArchivecheck(DtArchivesCheckvouchs archivecheck) {
		this.archivecheck = archivecheck;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
