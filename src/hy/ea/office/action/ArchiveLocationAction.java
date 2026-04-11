package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.office.archives.DtArchivesInventorylocation;
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
public class ArchiveLocationAction {
	private static final Logger logger = LoggerFactory.getLogger(ArchiveLocationAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private DtArchivesInventorylocation archivelocation;
	private PageForm pageForm;
	private String search;
	private Company company;
	private String result;
	private int pageNumber;
	private String parameter;
	private String type;

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", archivelocation);
		return getLocationList();
	}

	public DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String locmodule = (String) session.get("module");
		DetachedCriteria dc = DetachedCriteria
				.forClass(DtArchivesInventorylocation.class);

		dc.addOrder(Order.desc("createdate"));
		if (type != null&&!type.equals("")&&!type.equals("null")) {
			if (type.equals("company")) {
				dc.add(Restrictions.eq("companyideas", account.getCompanyID()));
			} else {
				String hql = "from Company where companyID = ?";

				hql = "select companyID from Company where companyPID = ? or companyID = ?";

				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] { account.getCompanyID(),
										account.getCompanyID() });

				dc.add(Restrictions.in("companyideas", list));
			}
		} else {
			dc.add(Restrictions.eq("userid", account.getStaffID()));
			if (!locmodule.equals("global")) {
				dc.add(Restrictions.eq("locmodule", locmodule));
			}
		}
		if (search != null && search.equals("search")) {
			archivelocation = (DtArchivesInventorylocation) session
					.get("tablesearch");
			if (archivelocation.getLocationname() != null
					&& !archivelocation.getLocationname().equals(""))
				dc.add(Restrictions.like("locationname", archivelocation
						.getLocationname().trim(), MatchMode.ANYWHERE));
		}
		return dc;
	}

	/**
	 * 获取存储位置列表
	 * 
	 * @return
	 */
	public String getLocationList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "locationlist";
	}

	/**
	 * 
	 * 添加位置信息
	 * 
	 * @return
	 */
	public String addLocation() {
		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			if (account == null) {
				return "not_login";
			}

			HttpServletRequest request = ServletActionContext.getRequest();
			String locationname = request.getParameter("locationname");
			if (locationname != null && !locationname.equals("")) {
				DtArchivesInventorylocation fastlocation = new DtArchivesInventorylocation();
				fastlocation.setLocationname(locationname);
				fastlocation.setLocationid(serverService
						.getServerID("locationid"));
				fastlocation.setUserid(account.getStaffID());
				fastlocation.setCompanyideas(account.getCompanyID());
				fastlocation.setCreatedate(new Date());
				fastlocation.setLocmodule("global");
				baseBeanService.save(fastlocation);

			} else if (archivelocation.getLocationid() == null
					|| "".equals(archivelocation.getLocationid())) {
				archivelocation.setLocationid(serverService
						.getServerID("locationid"));

				archivelocation.setUserid(account.getStaffID());
				archivelocation.setCompanyideas(account.getCompanyID());
				archivelocation.setCreatedate(new Date());
				archivelocation.setLocmodule((String) session.get("module"));
				baseBeanService.save(archivelocation);
			} else {
				String hql = "from DtArchivesInventorylocation where locationid = ?";
				DtArchivesInventorylocation oldlocation = (DtArchivesInventorylocation) baseBeanService
						.getBeanByHqlAndParams(
								hql,
								new Object[] { archivelocation.getLocationid() });
				oldlocation.setLocationname(archivelocation.getLocationname());
				baseBeanService.update(oldlocation);
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
	public String deleteLocation() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String locationid = request.getParameter("locationid");

		String hql = "from DtArchivesInventorylocation where locationid = ?";
		DtArchivesInventorylocation oldlocation = (DtArchivesInventorylocation) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { locationid });

		hql = "from DtArchivesArchiveshistory where dtArchivesInventorylocation = ?";
		List<BaseBean> historylist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { oldlocation });

		Map<String, Object> map = new HashMap<String, Object>();
		if (historylist.size() == 0) {
			baseBeanService.deleteBeanByKey(DtArchivesInventorylocation.class,
					oldlocation.getLocationkey());
			map.put("result", "suc");
		} else {
			map.put("result", "fail");
		}

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

	public DtArchivesInventorylocation getArchivelocation() {
		return archivelocation;
	}

	public void setArchivelocation(DtArchivesInventorylocation archivelocation) {
		this.archivelocation = archivelocation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
