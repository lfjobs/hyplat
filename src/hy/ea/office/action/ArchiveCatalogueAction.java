package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.archives.DtArchivesCataloguearchives;
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
public class ArchiveCatalogueAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private DtArchivesCataloguearchives archiveCatalogue;
	private PageForm pageForm;
	private List<BaseBean> childlist;
	private String search;
	private Company company;
	private String result;
	private int pageNumber;
	private String parameter;
	private String hid;
	private String type;


	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", archiveCatalogue);
		return getCatalogueList();
	}

	/**
	 * 
	 * @param p
	 *            用来判断是查全部还是子查父类别
	 * @return
	 */
	public DetachedCriteria getList(String check) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria
				.forClass(DtArchivesCataloguearchives.class);

		if (type != null&&!type.equals("")&&!type.equals("null")) {
			if (type.equals("company")) {
				dc.add(Restrictions.eq("comanyid", account.getCompanyID()));
			} else {
				String hql = "from Company where companyID = ?";

				hql = "select companyID from Company where companyPID = ? or companyID = ?";

				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] { account.getCompanyID(),
										account.getCompanyID() });

				dc.add(Restrictions.in("comanyid", list));
			}
		} else {
			dc.add(Restrictions.eq("comanyid", account.getCompanyID()));
		}
		dc.addOrder(Order.desc("modifytime"));
		dc.add(Restrictions.isNull("parent"));// 得到父
		if(check==null||!check.equals("check")){
		dc.add(Restrictions.eq("catemodule","global"));
		}
		if (search != null && search.equals("search")) {
			archiveCatalogue = (DtArchivesCataloguearchives) session
					.get("tablesearch");
			dc.add(Restrictions.like("name", archiveCatalogue.getName(),
					MatchMode.ANYWHERE));
		}
		return dc;
	}

	/**
	 * 获取类别
	 * 
	 * @return
	 */
	public String getCatalogueList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String check = request.getParameter("check");
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getList(check));// 父
		String hql1 = "from DtArchivesCataloguearchives where comanyid = ? and parent is not null and catemodule = 'global' order by modifytime desc";
		if(check!=null&&check.equals("check")){
			hql1 = "from DtArchivesCataloguearchives where comanyid = ? and parent is not null order by modifytime desc";
		}
		
		childlist = baseBeanService.getListBeanByHqlAndParams(hql1,
				new Object[] { account.getCompanyID() });// 子

		String hql = "from Staff where staffID = ?";
		Staff staff = null;
		if (pageForm != null) {
			for (BaseBean b : pageForm.getList()) {
				DtArchivesCataloguearchives catalogue = (DtArchivesCataloguearchives) b;
				if (catalogue.getCreateuser() != null
						&& !"".equals(catalogue.getCreateuser())) {
					staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { catalogue.getCreateuser() });
					catalogue.setCreateuser(staff.getStaffName());
				}
				if (catalogue.getModifyuser() != null
						&& !"".equals(catalogue.getModifyuser())) {
					staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
							new Object[] { catalogue.getModifyuser() });
					catalogue.setModifyuser(staff.getStaffName());
				}
			}

			if (childlist.size() != 0) {
				for (BaseBean b : childlist) {
					DtArchivesCataloguearchives catalogue = (DtArchivesCataloguearchives) b;
					if (catalogue.getCreateuser() != null
							&& !"".equals(catalogue.getCreateuser())) {
						staff = (Staff) baseBeanService
								.getBeanByHqlAndParams(hql,
										new Object[] { catalogue
												.getCreateuser() });
						catalogue.setCreateuser(staff.getStaffName());
					}
					if (catalogue.getModifyuser() != null
							&& !"".equals(catalogue.getModifyuser())) {
						staff = (Staff) baseBeanService
								.getBeanByHqlAndParams(hql,
										new Object[] { catalogue
												.getModifyuser() });
						catalogue.setModifyuser(staff.getStaffName());
					}
				}
			}
		}
		return "cataloguelist";
	}

	/**
	 * 
	 * 添加一级类别
	 * 
	 * @return
	 */
	public String addCatalogue() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		if (archiveCatalogue.getArchiveid() == null
				|| "".equals(archiveCatalogue.getArchiveid())) {
			archiveCatalogue.setArchiveid(serverService
					.getServerID("catalogueid"));

			archiveCatalogue.setCreateuser(account.getStaffID());
			archiveCatalogue.setComanyid(account.getCompanyID());
			Date cur = new Date();
			archiveCatalogue.setCreatetime(cur);
			archiveCatalogue.setModifytime(cur);
			archiveCatalogue.setCatemodule((String)session.get("module"));
			baseBeanService.save(archiveCatalogue);
		} else {
			String hql = "from DtArchivesCataloguearchives where archiveid = ?";
			DtArchivesCataloguearchives oldcatalogue = (DtArchivesCataloguearchives) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { archiveCatalogue
							.getArchiveid() });
			oldcatalogue.setName(archiveCatalogue.getName());
			oldcatalogue.setModifytime(new Date());
			oldcatalogue.setModifyuser(account.getStaffID());

			
			baseBeanService.update(oldcatalogue);
		}
		return "success";

	}

	/**
	 * 
	 * 删除类别信息
	 * 
	 * @return
	 */
	public String deleteCatalogue() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		String hql1 = "from DtArchivesArchives where dtArchivesCataloguearchives = ?";
		List<BaseBean> archivelist = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String archiveid = request.getParameter("archiveid");
		// 根据id获取该类别
		String hql = "from DtArchivesCataloguearchives where archiveid = ?";
		DtArchivesCataloguearchives oldcatalogue = (DtArchivesCataloguearchives) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { archiveid });
		// 根据该类别，判断是否已引用该类别
		archivelist = baseBeanService.getListBeanByHqlAndParams(hql1,
				new Object[] { oldcatalogue });
		Map<String, Object> map = new HashMap<String, Object>();
		if (archivelist.size() == 0) {
			// 如果父类别没被引用，判断是否有子类别
			hql = "from DtArchivesCataloguearchives where parent = ?";
			List<BaseBean> secondCatalogue = baseBeanService
					.getListBeanByHqlAndParams(hql, new Object[] { archiveid });
			boolean bool = true;
			if (secondCatalogue.size() != 0) {
				// 如果存在子目录，判断子目录是否被引用
				for (BaseBean s : secondCatalogue) {
					archivelist = baseBeanService.getListBeanByHqlAndParams(
							hql1, new Object[] { s });
					if (archivelist.size() != 0) {
						bool = false;
						break;
					}
				}
			}
			if (bool == false) {
				map.put("result", "fail");
			} else {
				map.put("result", "suc");
				baseBeanService.deleteBeanByKey(
						DtArchivesCataloguearchives.class, oldcatalogue
								.getArchivekey());
			}
		} else {
			map.put("result", "fail");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 判断类别是否可以编辑
	 * 
	 * @return
	 */
	public String IsCanEdit() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		String hql1 = "from DtArchivesArchives where dtArchivesCataloguearchives = ?";
		List<BaseBean> archivelist = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String archiveid = request.getParameter("archiveid");
		// 根据id获取该类别
		String hql = "from DtArchivesCataloguearchives where archiveid = ?";
		DtArchivesCataloguearchives oldcatalogue = (DtArchivesCataloguearchives) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { archiveid });
		// 根据该类别，判断是否已引用该类别
		archivelist = baseBeanService.getListBeanByHqlAndParams(hql1,
				new Object[] { oldcatalogue });
		Map<String, Object> map = new HashMap<String, Object>();
		if(archivelist.size()==0){
			map.put("result", "suc");
		}else{
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

	public DtArchivesCataloguearchives getArchiveCatalogue() {
		return archiveCatalogue;
	}

	public void setArchiveCatalogue(DtArchivesCataloguearchives archiveCatalogue) {
		this.archiveCatalogue = archiveCatalogue;
	}

	public List<BaseBean> getChildlist() {
		return childlist;
	}

	public void setChildlist(List<BaseBean> childlist) {
		this.childlist = childlist;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
