package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CorPhoto;
import hy.ea.bo.office.CorPhotoBox;
import hy.ea.office.service.PhotoManagerService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 图片库管理
 */
public class PhotoBoxManagerAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private PhotoManagerService photoManagerService;
	private List<BaseBean> Beanlist;
	private CorPhotoBox corPhotoBox;
	private CorPhoto corPhoto;
	private List<CorPhotoBox> listbox;
	private List<CorPhoto> listpic;
	private List<CorPhoto> listslide;
	private PageForm pageForm;
	private String result;
	private String search;
	private int pageNumber;
	private String photoBoxIdMark;
	private ServletConfig servletConfig;

	// 返回所有图片库列表
	public String getCorPhotoBoxList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String companyID = account.getCompanyID();
		String sortType = "";
		String hql = "from CorPhotoBox where companyID = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { companyID });
		if (list.size() != 0) {
			BaseBean baseBean = list.get(0);
			CorPhotoBox cpb = (CorPhotoBox) baseBean;
			sortType = cpb.getSortType();
			if (sortType.equalsIgnoreCase("sortAsc")) {
				hql = "from CorPhotoBox where companyID = ? order by createTime Asc";

			} else if (sortType.equalsIgnoreCase("sortDesc")) {
				hql = "from CorPhotoBox where companyID = ? order by createTime Desc";

			} else if (sortType.equalsIgnoreCase("sortName")) {
				hql = "from CorPhotoBox where companyID = ? order by photoBoxName";

			} else {
				hql = "from CorPhotoBox where companyID = ? order by to_number(sortType) Asc";

			}
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql, new Object[] { companyID });

		return "corPhotoBoxlist";
	}

	// 返回所有排序图片库列表
	public String getCorPhotoBoxSortList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String companyID = account.getCompanyID();
		String sortType = "";
		String hql = "from CorPhotoBox where companyID = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { companyID });
		if (list.size() != 0) {
			BaseBean baseBean = list.get(0);
			CorPhotoBox cpb = (CorPhotoBox) baseBean;
			sortType = cpb.getSortType();
			if (sortType.equalsIgnoreCase("sortAsc")) {
				hql = "from CorPhotoBox where companyID = ? order by createTime Asc";
			} else if (sortType.equalsIgnoreCase("sortDesc")) {
				hql = "from CorPhotoBox where companyID = ? order by createTime Desc";
			} else if (sortType.equalsIgnoreCase("sortName")) {
				hql = "from CorPhotoBox where companyID = ? order by photoBoxName";
			} else {
				hql = "from CorPhotoBox where companyID = ? order by to_number(sortType) Asc";
			}
		}
		Beanlist = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { companyID });
		listbox = new ArrayList<CorPhotoBox>();
		for (BaseBean beanlist : Beanlist) {
			CorPhotoBox cb = (CorPhotoBox) beanlist;
			listbox.add(cb);
		}
		return "corPhotoBoxSortlist";
	}

	// 创建和修改图片库
	public String createOrUpdatePhotoBox() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		// 创建图片库
		String hql = "";
		String sortType = "";
		if (corPhotoBox.getPhotoBoxID() == ""
				|| corPhotoBox.getPhotoBoxID().equals("")) {

			hql = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { account.getCompanyID() });
			hql = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID() });
			// 判断图片库名称的长度，设置简写名称
			String photoBoxName = corPhotoBox.getPhotoBoxName();
			if (photoBoxName.length() > 6) {
				corPhotoBox.setPbnShort(photoBoxName.substring(0, 6) + "...");
			} else {
				corPhotoBox.setPbnShort(photoBoxName);
			}
			corPhotoBox.setPhotoBoxID(serverService.getServerID("box_"));
			corPhotoBox.setCompanyID(account.getCompanyID());
			corPhotoBox.setCompanyName(company.getCompanyName());
			corPhotoBox.setCreatorID(account.getStaffID());
			corPhotoBox.setCreatorName(staff.getStaffName());
			corPhotoBox.setCreateTime(new Date());
			corPhotoBox.setPhotoNumber(new Integer(0));
			corPhotoBox.setCoverUrl("images/ea/office/fileCabinet/nopic.jpg");

			// 根据旧图片库的排序规则,l设置新生成的图片库的排序规则
			hql = "from CorPhotoBox where CompanyID = ?";
			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
					hql, new Object[] { account.getCompanyID() });
			if (list.size() > 0) {
				BaseBean baseBean = list.get(0);
				CorPhotoBox cpb = (CorPhotoBox) baseBean;
				sortType = cpb.getSortType();
				if (sortType.equals("sortAsc") || sortType.equals("sortDesc")
						|| sortType.equals("sortName")) {
					corPhotoBox.setSortType(sortType);
				} else {
					corPhotoBox.setSortType(String.valueOf(list.size() + 1));
				}
			} else {
				corPhotoBox.setSortType("sortDesc");
			}

			baseBeanService.save(corPhotoBox);
		} else {
			// 修改图片库
			hql = "from CorPhotoBox where photoBoxID = ?";
			CorPhotoBox oldcpb = (CorPhotoBox) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { corPhotoBox
							.getPhotoBoxID() });
			// 判断图片库名称的长度，设置简写名称
			String photoBoxName = corPhotoBox.getPhotoBoxName();
			if (photoBoxName.length() > 6) {
				oldcpb.setPbnShort(photoBoxName.substring(0, 6) + "...");
			} else {
				oldcpb.setPbnShort(photoBoxName);
			}
			oldcpb.setPhotoBoxName(photoBoxName);
			oldcpb.setRemark(corPhotoBox.getRemark());
			baseBeanService.update(oldcpb);
		}
		return getCorPhotoBoxList();
	}	
	// 删除图片库
	public String delPicBox() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoBoxID = request.getParameter("photoBoxID");
			String hql = "";
			hql = "from CorPhotoBox where photoBoxID = ?";
			CorPhotoBox cpb = (CorPhotoBox) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { photoBoxID });
			hql = "from CorPhoto where corPhotoBox = ?";
			List<BaseBean> listphotos = (List<BaseBean>) baseBeanService
					.getListBeanByHqlAndParams(hql, new Object[] { cpb });
			for (BaseBean bb : listphotos) {
				CorPhoto p = (CorPhoto) bb;
				photoManagerService.deletePhoto((path + p.getPhotoFile())
						.replace("/", "\\"));
			}

			baseBeanService.deleteBeanByKey(CorPhotoBox.class, cpb.getKey());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	// 根据公司查询所有的图片库
	public String queryAllPhotoBox() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		String companyID = account.getCompanyID();
		String hql = "from CorPhotoBox where companyID = ?";
		List<BaseBean> copbean = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { companyID });
		List<CorPhotoBox> cpblist = new ArrayList<CorPhotoBox>();
		for (BaseBean bean : copbean) {
			CorPhotoBox cpb = (CorPhotoBox) bean;
			cpblist.add(cpb);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig cfg = new JsonConfig();
		cfg.setRootClass(CorPhotoBox.class);
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if (arg1.equals("corPhotos")) {
					return true;
				} else {
					return false;
				}
			}
		});
		map.put("result", cpblist);
		JSONObject oj = JSONObject.fromObject(map, cfg);
		result = oj.toString();

		return "success";
	}

	// 图片库排序sortType:排序类型；sortAsc,sortDesc,sortName
	public String sortPicBox() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		ServletRequest request = ServletActionContext.getRequest();
		String sortType = request.getParameter("sortType");

		ServletActionContext.getRequest().getSession().setAttribute("sortType",
				sortType);
		String companyID = account.getCompanyID();
		String hql = "";
		if (sortType.equals("sortAsc")) {
			hql = "from CorPhotoBox where companyID = ? order by createTime ";// 升序
		} else if (sortType.equals("sortDesc")) {
			hql = "from CorPhotoBox where companyID = ? order by createTime Desc";// 降序
		} else {
			hql = "from CorPhotoBox where companyID = ? order by photoBoxName";// 按名称字母和中文分别排序
		}
		Beanlist = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { companyID });
		listbox = new ArrayList<CorPhotoBox>();
		for (BaseBean beanlist : Beanlist) {
			CorPhotoBox cb = (CorPhotoBox) beanlist;
			listbox.add(cb);
		}
		return "corPhotoBoxSortlist";

	}

	

	// 保存图片库顺序
	public String saveSort() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
		String sortType = (String) session.getAttribute("sortType");
		if (sortType != null) {
			String companyID = account.getCompanyID();
			String hql = "from CorPhotoBox where companyID = ?";
			Beanlist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { companyID });
			for (BaseBean beanlist : Beanlist) {
				CorPhotoBox cb = (CorPhotoBox) beanlist;
				cb.setSortType(sortType);
				baseBeanService.update(cb);
			}
		}
		return "success";
	}


	// 图片库查询
	public String searchPicBox() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		ServletRequest request = ServletActionContext.getRequest();
		String tpkname = request.getParameter("tpkname");
		String startString = request.getParameter("startDate");
		String endString = request.getParameter("endDate");
		Date startDate = null;
		Date endDate = null;

		if (!startString.equalsIgnoreCase("")) {
			startDate = Utilities.getDateFromString(startString,
					"yyyy-MM-dd hh:mm:ss");
		}
		if (!endString.equalsIgnoreCase("")) {
			endDate = Utilities.getDateFromString(endString,
					"yyyy-MM-dd hh:mm:ss");
		}
		String companyID = account.getCompanyID();
		String query = "";
		Object[] params = new Object[] { companyID };
		if (!tpkname.equals("")) {
			query += " and photoBoxName like '%" + tpkname + "%'";
		}

		if (startDate != null && endDate != null) {
			query += " and createTime between ? and ?";
			params = new Object[] { companyID, startDate, endDate };

		} else if (startDate != null) {
			query += " and createTime > ?";
			params = new Object[] { companyID, startDate };
		}

		String hql = "from CorPhotoBox where companyID = ?" + query
				+ " order by createTime ";
		try {
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "corPhotoBoxlist";
	}

	// 设置图片库封面
	public String setCover() {
		ServletRequest request = ServletActionContext.getRequest();
		String fileId = request.getParameter("fileId");
		String hql = "from CorPhoto where photoID = ?";
		CorPhoto cp = (CorPhoto) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { fileId });
		CorPhotoBox cpb = cp.getCorPhotoBox();
		cpb.setCoverUrl(cp.getPhotoFile());
		baseBeanService.update(cpb);
		return "success";
	}

	// 拖动图片库排序
	public String dragSort() {
		ServletActionContext.getRequest().getSession().setAttribute("sortType",
				null);
		ServletRequest request = ServletActionContext.getRequest();
		String aaa = request.getParameter("aaa");
		String arraykey[] = aaa.split("&");
		for (int i = 0; i < arraykey.length; i++) {
			String key = arraykey[i].substring(arraykey[i].indexOf('=') + 1);
			CorPhotoBox cpb = (CorPhotoBox) baseBeanService.getBeanByKey(
					CorPhotoBox.class, key);
			cpb.setSortType(String.valueOf(i + 1));
			baseBeanService.update(cpb);

		}
		return "success";
	}
	public CorPhotoBox getCorPhotoBox() {
		return corPhotoBox;
	}

	public void setCorPhotoBox(CorPhotoBox corPhotoBox) {
		this.corPhotoBox = corPhotoBox;
	}

	public List<CorPhotoBox> getListbox() {
		return listbox;
	}

	public void setListbox(List<CorPhotoBox> listbox) {
		this.listbox = listbox;
	}

	public List<BaseBean> getBeanlist() {
		return Beanlist;
	}

	public void setBeanlist(List<BaseBean> beanlist) {
		Beanlist = beanlist;
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

	public CorPhoto getCorPhoto() {
		return corPhoto;
	}

	public void setCorPhoto(CorPhoto corPhoto) {
		this.corPhoto = corPhoto;
	}

	public List<CorPhoto> getListslide() {
		return listslide;
	}

	public void setListslide(List<CorPhoto> listslide) {
		this.listslide = listslide;
	}

	public String getPhotoBoxIdMark() {
		return photoBoxIdMark;
	}

	public void setPhotoBoxIdMark(String photoBoxIdMark) {
		this.photoBoxIdMark = photoBoxIdMark;
	}

	public List<CorPhoto> getListpic() {
		return listpic;
	}

	public void setListpic(List<CorPhoto> listpic) {
		this.listpic = listpic;
	}

	public ServletConfig getServletConfig() {
		return servletConfig;
	}

	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
	}

}