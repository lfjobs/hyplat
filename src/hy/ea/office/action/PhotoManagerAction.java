package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CorPhoto;
import hy.ea.bo.office.CorPhotoBox;
import hy.ea.office.service.PhotoManagerService;
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
 * 图片管理
 */
public class PhotoManagerAction {
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

	// 在移动文件时新建相册
	public String createPhotoBox2() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String photoBoxName = request.getParameter("photoBoxName");// 名称
		String remark = request.getParameter("remark");// 描述
		String hql = "";
		String sortType = "";
		hql = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		hql = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		CorPhotoBox corPhotoBox = new CorPhotoBox();
		String photoBoxID = serverService.getServerID("box_");
		corPhotoBox.setPhotoBoxID(photoBoxID);
		corPhotoBox.setPhotoBoxName(photoBoxName);
		if (photoBoxName.length() > 6) {
			corPhotoBox.setPbnShort(photoBoxName.substring(0, 6) + "...");
		} else {
			corPhotoBox.setPbnShort(photoBoxName);
		}
		corPhotoBox.setRemark(remark);
		corPhotoBox.setCompanyID(account.getCompanyID());
		corPhotoBox.setCompanyName(company.getCompanyName());
		corPhotoBox.setCreatorID(account.getStaffID());
		corPhotoBox.setCreatorName(staff.getStaffName());
		corPhotoBox.setCreateTime(new Date());
		corPhotoBox.setPhotoNumber(new Integer(0));
		corPhotoBox.setCoverUrl("images/ea/office/fileCabinet/nopic.jpg");

		hql = "from CorPhotoBox where CompanyID = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		BaseBean baseBean = list.get(0);
		CorPhotoBox cpb = (CorPhotoBox) baseBean;
		sortType = cpb.getSortType();
		if (sortType.equals("sortAsc") || sortType.equals("sortDesc")
				|| sortType.equals("sortName")) {
			corPhotoBox.setSortType(sortType);
		} else {
			corPhotoBox.setSortType(String.valueOf(list.size() + 1));
		}
		baseBeanService.save(corPhotoBox);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", photoBoxID);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	// 移动图片到图片库
	public String movePicToBox() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String photoIdstr = request.getParameter("photoIdstr");// 要移动的所有图片的字符串
		String photoBoxIdNew = request.getParameter("photoBoxIdNew");// 要移动到的图片库ID
		String hql = "";
		String sortType = null;
		String[] photoarray = photoIdstr.split(",");
		// 根据要移动到的图片库ID获得该图片库，再根据图片库获得图片库的所有图片
		hql = "from CorPhotoBox where photoBoxID = ?";
		CorPhotoBox cpbb = (CorPhotoBox) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { photoBoxIdNew });
		hql = "from CorPhoto where corPhotoBox = ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { cpbb });
		if (list.size() != 0) {
			CorPhoto cpp = (CorPhoto) list.get(0);
			sortType = cpp.getSortTypePhoto();
			if (!sortType.equals("sortAsc") && !sortType.equals("sortDesc")
					&& !sortType.equals("sortName")) {

				sortType = list.size() + 1 + "";
			}

		} else {
			sortType = "sortDesc";
		}
		// 循环更改图片外键
		hql = "from CorPhoto where photoID = ?";
		for (int i = 0; i < photoarray.length; i++) {
			// 根据要移动的图片，获得图片原来所在图片库，然后设置它的封面
			CorPhoto cop = (CorPhoto) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { photoarray[i] });
			CorPhotoBox oldbox = cop.getCorPhotoBox();
			// 根据图片库找到所有的图片
			hql = "from CorPhoto where corPhotoBox = ?";
			List<BaseBean> coplist = (List<BaseBean>) baseBeanService
					.getListBeanByHqlAndParams(hql, new Object[] { oldbox });
			String coverurl2 = "";
			for (BaseBean bb : coplist) {
				CorPhoto copss = (CorPhoto) bb;
				coverurl2 = copss.getPhotoFile();
				if (!coverurl2.equalsIgnoreCase(cop.getPhotoFile()))
					break;
			}
			oldbox.setPhotoNumber(oldbox.getPhotoNumber() - 1);

			if (oldbox.getPhotoNumber() < 1) {// 说明旧的图片库已经无图片这时需要设置图片库的封面为空的封面
				oldbox.setCoverUrl("images/ea/office/fileCabinet/nopic.jpg");
			} else if (oldbox.getCoverUrl()
					.equalsIgnoreCase(cop.getPhotoFile())) {
				oldbox.setCoverUrl(coverurl2);
			}
			baseBeanService.update(oldbox);
			// 处理新增加的图片库问题
			hql = "from CorPhotoBox where photoBoxID = ?";
			CorPhotoBox newbox = (CorPhotoBox) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { photoBoxIdNew });
			newbox.setPhotoNumber(newbox.getPhotoNumber() + 1);

			hql = "from CorPhoto where photoID = ?";
			if (newbox.getCoverUrl().equalsIgnoreCase(
					"images/ea/office/fileCabinet/nopic.jpg")) {// 说明图片库里为空
				CorPhoto copic = (CorPhoto) baseBeanService
						.getBeanByHqlAndParams(hql,
								new Object[] { photoarray[0] });
				newbox.setCoverUrl(copic.getPhotoFile());
			}
			baseBeanService.update(newbox);
			CorPhoto copics = (CorPhoto) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { photoarray[i] });
			copics.setSortTypePhoto(sortType);
			copics.setCorPhotoBox(newbox);
			baseBeanService.update(copics);

		}

		return "success";
	}

	// 删除图片
	public String deletePhoto() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String photoIdstr = request.getParameter("photoIdstr");
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String hql = "from CorPhoto where photoID = ?";
		String hqldel = "delete from CorPhoto where photoID = ?";

		String photoarray[] = photoIdstr.split(",");
		for (int i = 0; i < photoarray.length; i++) {
			String coverurl2 = "";
			try {
				CorPhoto cop = (CorPhoto) baseBeanService
						.getBeanByHqlAndParams(hql,
								new Object[] { photoarray[i] });
				String photoFile = cop.getPhotoFile();// 获得真实图片路径
				// 更新图片库的信息
				CorPhotoBox cobox = cop.getCorPhotoBox();
				String hqlss = "from CorPhoto where corPhotoBox = ?";
				List<BaseBean> coplist = (List<BaseBean>) baseBeanService
						.getListBeanByHqlAndParams(hqlss,
								new Object[] { cobox });
				for (BaseBean bb : coplist) {
					CorPhoto copss = (CorPhoto) bb;
					coverurl2 = copss.getPhotoFile();
					if (!coverurl2.equalsIgnoreCase(cop.getPhotoFile()))
						break;
				}
				cobox.setPhotoNumber(cobox.getPhotoNumber() - 1);
				if (cobox.getPhotoNumber() < 1) {// 说明旧的图片库已经无图片这时需要设置图片库的封面为空的封面
					cobox.setCoverUrl("images/ea/office/fileCabinet/nopic.jpg");
				}
				if (cobox.getCoverUrl().equalsIgnoreCase(cop.getPhotoFile())) {
					cobox.setCoverUrl(coverurl2);
				}
				baseBeanService.update(cobox);

				baseBeanService
						.saveBeansListAndexecuteHqlsByParams(null,
								new String[] { hqldel },
								new Object[] { photoarray[i] });
				photoManagerService.deletePhoto((path + photoFile).replace("/",
						"\\"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "success";
	}

	// 得到图片缩略图列表
	public String showPhotoList() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String photoBoxID = request.getParameter("photoBoxID");
		if (photoBoxID == null) {
			if (photoBoxIdMark == null || photoBoxIdMark.equalsIgnoreCase("")) {
				photoBoxID = (String) request.getSession().getAttribute(
						"photoBoxID");

			} else {
				photoBoxID = photoBoxIdMark;
			}
		}
		String hql = "";
		hql = "from CorPhotoBox where photoBoxID = ?";
		CorPhotoBox cob = (CorPhotoBox) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { photoBoxID });
		hql = "from CorPhoto where corPhotoBox = ?";
		List<BaseBean> listcop = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { cob });
		if (listcop.size() != 0) {
			String sortTypePhoto = "";
			BaseBean baseBean = listcop.get(0);
			CorPhoto corp = (CorPhoto) baseBean;
			sortTypePhoto = corp.getSortTypePhoto();

			if (sortTypePhoto.equalsIgnoreCase("sortAsc")) {
				hql = "from CorPhoto where corPhotoBox = ? order by uploadTime";

			} else if (sortTypePhoto.equalsIgnoreCase("sortDesc")) {
				hql = "from CorPhoto where corPhotoBox = ? order by uploadTime Desc";

			} else if (sortTypePhoto.equalsIgnoreCase("sortName")) {
				hql = "from CorPhoto where corPhotoBox = ? order by photoName";
			} else {
				hql = "from CorPhoto where corPhotoBox = ? order by to_number(sortTypePhoto)";

			}
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql, new Object[] { cob });
		request.setAttribute("photoBoxID", photoBoxID);
		request.setAttribute("photoBoxName", cob.getPhotoBoxName());
		request.getSession().setAttribute("photoBoxID", photoBoxID);
		return "toPhotolist";
	}

	// 得到图片缩略图排序列表
	public String showPhotoSortList() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String photoBoxID = request.getParameter("photoBoxID");
		if (photoBoxID == null) {
			if (photoBoxIdMark == null || photoBoxIdMark.equalsIgnoreCase("")) {
				photoBoxID = (String) request.getSession().getAttribute(
						"photoBoxID");

			} else {
				photoBoxID = photoBoxIdMark;
			}
		}
		String hql = "";
		hql = "from CorPhotoBox where photoBoxID = ?";
		CorPhotoBox cob = (CorPhotoBox) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { photoBoxID });
		hql = "from CorPhoto where corPhotoBox = ?";
		List<BaseBean> listcop = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { cob });
		if (listcop.size() != 0) {
			String sortTypePhoto = "";
			BaseBean baseBean = listcop.get(0);
			CorPhoto corp = (CorPhoto) baseBean;
			sortTypePhoto = corp.getSortTypePhoto();

			if (sortTypePhoto.equalsIgnoreCase("sortAsc")) {
				hql = "from CorPhoto where corPhotoBox = ? order by uploadTime";

			} else if (sortTypePhoto.equalsIgnoreCase("sortDesc")) {
				hql = "from CorPhoto where corPhotoBox = ? order by uploadTime Desc";

			} else if (sortTypePhoto.equalsIgnoreCase("sortName")) {
				hql = "from CorPhoto where corPhotoBox = ? order by photoName";

			} else {
				hql = "from CorPhoto where corPhotoBox = ? order by to_number(sortTypePhoto)";

			}
		}

		listpic = new ArrayList<CorPhoto>();
		List<BaseBean> listp = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { cob });
		for (BaseBean b : listp) {
			CorPhoto cp = (CorPhoto) b;
			listpic.add(cp);
		}

		request.setAttribute("photoBoxID", photoBoxID);
		request.setAttribute("photoBoxName", cob.getPhotoBoxName());
		request.getSession().setAttribute("photoBoxID", photoBoxID);
		return "toPhotoSortlist";
	}

	public String showSlideList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String photoID = request.getParameter("photoID");
		String url = request.getParameter("url");
		String hql = "from CorPhoto where photoID = ?";
		CorPhoto cp = (CorPhoto) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { photoID });
		CorPhotoBox cpb = cp.getCorPhotoBox();
		String hql2 = "from CorPhoto where corPhotoBox = ?";
		Beanlist = baseBeanService.getListBeanByHqlAndParams(hql2,
				new Object[] { cpb });
		listslide = new ArrayList<CorPhoto>();
		for (BaseBean beanlist : Beanlist) {
			CorPhoto cp2 = (CorPhoto) beanlist;
			listslide.add(cp2);
		}
		request.setAttribute("url", url);
		request.setAttribute("photoNames", cp.getPhotoName());
		if (cp.getRemark() == null) {
			request.setAttribute("remarks", "暂无描述");
		} else {
			request.setAttribute("remarks", cp.getRemark());
		}
		request.setAttribute("photoKey", cp.getKey());
		request.setAttribute("photoBoxId", cpb.getPhotoBoxID());

		return "toSlidelist";
	}

	// 上传图片使用中
	public String startUpload2() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyID = account.getCompanyID();
		String staffID = account.getStaffID();
		String picPath = request.getParameter("picPath");
		String[] picPathArrary = picPath.split(",");
		String selectBox = request.getParameter("selectbox");
		String[] fileNameArray = new String[picPathArrary.length];
		for (int i = 0; i < picPathArrary.length; i++) {
			fileNameArray[i] = picPathArrary[i].substring(picPathArrary[i]
					.lastIndexOf("/") + 1);
		}
		String hql = "from Company where companyID = ?";
		String hql2 = "from Staff where staffID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { companyID });
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { staffID });
		CorPhotoBox cpbs = (CorPhotoBox) baseBeanService.getBeanByKey(
				CorPhotoBox.class, selectBox);
		cpbs.setPhotoNumber(cpbs.getPhotoNumber() + picPathArrary.length);
		String photoBoxID = cpbs.getPhotoBoxID();// 留着返回页面用

		String mark2 = (String) request.getParameter("mark");
		if (mark2.equalsIgnoreCase((String) request.getSession().getAttribute(
				"mark"))) {
			photoBoxIdMark = photoBoxID;
			return showPhotoList();
		}
		if (cpbs.getCoverUrl().equalsIgnoreCase(
				"images/ea/office/fileCabinet/nopic.jpg")) {
			cpbs.setCoverUrl(picPathArrary[0]);
		}
		baseBeanService.update(cpbs);

		for (int i = 0; i < picPathArrary.length; i++) {
			String photoID = serverService.getServerID("pic_");
			CorPhoto corPhoto = new CorPhoto();
			corPhoto.setPhotoID(photoID);
			corPhoto.setCompanyID(companyID);
			corPhoto.setCompanyName(company.getCompanyName());
			corPhoto.setCreatorID(staffID);
			corPhoto.setCreatorName(staff.getStaffName());

			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			corPhoto.setUploadTime(curDate);
			corPhoto.setPhotoFile(picPathArrary[i]);
			corPhoto.setPhotoName("未命名");
			corPhoto.setPnShort("未命名");
			String sortTypePhoto = "";
			String hqls = "from CorPhoto where corPhotoBox = ?";
			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
					hqls, new Object[] { cpbs });
			if (list.size() > 0) {
				for (BaseBean baseBean : list) {
					CorPhoto cpb = (CorPhoto) baseBean;
					sortTypePhoto = cpb.getSortTypePhoto();
					if (sortTypePhoto.equals("sortAsc")
							|| sortTypePhoto.equals("sortDesc")
							|| sortTypePhoto.equals("sortName")) {
						corPhoto.setSortTypePhoto(sortTypePhoto);
					} else {
						corPhoto.setSortTypePhoto(String
								.valueOf(list.size() + 1));
					}
					break;

				}
			} else {
				corPhoto.setSortTypePhoto("sortDesc");
			}
			corPhoto.setCorPhotoBox(cpbs);
			try {
				baseBeanService.save(corPhoto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		photoBoxIdMark = photoBoxID;
		String mark = (String) request.getParameter("mark");
		request.getSession().setAttribute("mark", mark);
		return showPhotoList();
	}

	// 图片列表排序sortType:排序类型；sortAsc,sortDesc,sortName
	public String sortPicList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		ServletRequest request = ServletActionContext.getRequest();
		String sortTypePhoto = request.getParameter("sortTypePhoto");
		ServletActionContext.getRequest().getSession().setAttribute(
				"sortTypePhoto", sortTypePhoto);
		String photoBoxId = request.getParameter("photoBoxId");
		String hqlbox = "from CorPhotoBox where photoBoxId = ?";
		CorPhotoBox cpb = (CorPhotoBox) baseBeanService.getBeanByHqlAndParams(
				hqlbox, new Object[] { photoBoxId });
		String hql = "";
		if (sortTypePhoto.equals("sortAsc")) {
			hql = "from CorPhoto where corPhotoBox = ? order by uploadTime ";// 升序
		} else if (sortTypePhoto.equals("sortDesc")) {
			hql = "from CorPhoto where corPhotoBox = ? order by uploadTime Desc";// 降序
		} else {
			hql = "from CorPhoto where corPhotoBox = ? order by photoName";// 按名称字母和中文分别排序
		}
		Beanlist = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { cpb });
		listpic = new ArrayList<CorPhoto>();
		for (BaseBean beanlist : Beanlist) {
			CorPhoto cp = (CorPhoto) beanlist;
			listpic.add(cp);
		}
		request.setAttribute("photoBoxID", photoBoxId);
		request.setAttribute("photoBoxName", cpb.getPhotoBoxName());
		return "toPhotoSortlist";

	}

	// 保存图片列表顺序
	public String savePhotoSort() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		ServletRequest request = ServletActionContext.getRequest();
		String photoBoxId = request.getParameter("photoBoxId");
		String hql1 = "from CorPhotoBox where photoBoxID = ?";
		CorPhotoBox cpb = (CorPhotoBox) baseBeanService.getBeanByHqlAndParams(
				hql1, new Object[] { photoBoxId });
		HttpSession session = ServletActionContext.getRequest().getSession();
		String sortTypePhoto = (String) session.getAttribute("sortTypePhoto");

		if (sortTypePhoto != null) {
			String hql = "from CorPhoto where corPhotoBox = ?";
			Beanlist = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { cpb });
			for (BaseBean beanlist : Beanlist) {
				CorPhoto cp = (CorPhoto) beanlist;
				cp.setSortTypePhoto(sortTypePhoto);
				baseBeanService.update(cp);
			}
		}
		return "success";
	}

	// 拖动图片列表排序
	public String dragPhotoSort() {
		ServletActionContext.getRequest().getSession().setAttribute(
				"sortTypePhoto", null);
		ServletRequest request = ServletActionContext.getRequest();
		String picdiv = request.getParameter("picdiv");
		String arraykey[] = picdiv.split("&");
		for (int i = 0; i < arraykey.length; i++) {
			String key = arraykey[i].substring(arraykey[i].indexOf('=') + 1);
			CorPhoto cp = (CorPhoto) baseBeanService.getBeanByKey(
					CorPhoto.class, key);
			cp.setSortTypePhoto(String.valueOf(i + 1));
			baseBeanService.update(cp);

		}
		return "success";
	}

	// 修改图片名称和描述
	public String updateTitleOrRemark() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		ServletRequest request = ServletActionContext.getRequest();
		String photoKey = request.getParameter("key");
		String photoName = request.getParameter("photoName");
		String remark = request.getParameter("remark");
		CorPhoto cop = (CorPhoto) baseBeanService.getBeanByKey(CorPhoto.class,
				photoKey);
		if (photoName != null) {
			if (photoName.length() > 8) {
				cop.setPnShort(photoName.substring(0, 8) + "...");
			} else {
				cop.setPnShort(photoName);
			}
			cop.setPhotoName(photoName);
		}

		if (remark != null) {
			cop.setRemark(remark);
		}
		baseBeanService.update(cop);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(CorPhoto.class);
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("corPhotoBox")) {
						return true;
					} else {
						return false;
					}
				}
			});
			map.put("result", cop);
			JSONObject oj = JSONObject.fromObject(map, cfg);
			result = oj.toString();
		} catch (Exception e) {
			e.printStackTrace();
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