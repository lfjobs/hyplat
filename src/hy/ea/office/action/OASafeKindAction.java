package hy.ea.office.action;

//安全检查类别
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.OASafeKind;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class OASafeKindAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private InputStream excelStream;
	private OASafeKind oasafeKind;
	private PageForm pageForm;
	private String parameter;
	private String result;
	private int pageNumber;
	private String search;

	private List<BaseBean> oasalist;
	private String parentID;

	private File photo;
	private String photoFileName;
	private String photoContentType;

	/**
	 * 安全类别状态：00表示正常，01表示删除
	 */

	/**
	 * 安全检查类别列表
	 */
	public String getListOASafeKind() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListBYDC());
		return "safeKindlist";
	}

	public DetachedCriteria getListBYDC() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(OASafeKind.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (null != parentID && !"".equals(parentID)) {
			dc.add(Restrictions.eq("parentID", parentID));
		}
		if (search != null && search.equals("search")) {
			OASafeKind oasID = (OASafeKind) session.get("tablesearch");
			if (!"".equals(oasID.getName())) {
				dc.add(Restrictions.like("name", oasID.getName(),
						MatchMode.ANYWHERE));
			}
			if (!"".equals(oasID.getGuideline())) {
				dc.add(Restrictions.like("guideline", oasID.getGuideline(),
						MatchMode.ANYWHERE));
			}
		}
		return dc;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", oasafeKind);
		return getListOASafeKind();
	}

	// @Resource
	// private UpLoadFileService fileService;
	/**
	 * 添加或修改安全检查类别列表
	 */
	public String saveOASafeKind() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if (photo != null && !photo.equals("")) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, photoFileName,
					photo, account.getCompanyID(), "/office/safe/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			if (oasafeKind.getAttachment() != null
					&& !"".equals(oasafeKind.getAttachment())) {
				ArrayList<String> paths = new ArrayList<String>();
				paths.add(path + oasafeKind.getAttachment());
				fileService.deletePhotos(paths);
			}
			oasafeKind.setAttachment(photoPath);
		}
		if (oasafeKind.getId() == null || "".equals(oasafeKind.getId())) {
			oasafeKind.setId(serverService.getServerID("id"));
			parameter = "添加安全检查类别(安全检查类别名称:" + oasafeKind.getName() + ")";
		} else {
			Object[] params = { account.getCompanyID(), oasafeKind.getId() };
			String hql1 = "from OASafeKind where companyID=? and ID=? ";
			OASafeKind os = (OASafeKind) baseBeanService.getBeanByHqlAndParams(
					hql1, params);
			parameter = "修改安全检查类别(安全检查类别名称:" + os.getName() + ")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		oasafeKind.setStatus("00");
		oasafeKind.setCompanyID(account.getCompanyID());
		oasafeKind.setParentID(parentID);
		CLogBook  logbook=logBookService.saveCLogBook(null, parameter, account);
		beans.add(logbook);
		beans.add(oasafeKind);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", oasafeKind);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 删除某条安全检查类别
	 */
	public String delOASafeKind() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { account.getCompanyID(), oasafeKind.getId() };
		String hql2 = "from OASafeKind where companyID = ?  and id = ? and status='00' ";
		OASafeKind oa = (OASafeKind) baseBeanService.getBeanByHqlAndParams(
				hql2, params);
		CLogBook  logbook=logBookService.saveCLogBook(null, "删除安全检查类别(名称：" + oa.getName() + ")",
				account);
		List<BaseBean> beans=new ArrayList<BaseBean>();
		beans.add(logbook);
		String hql1 = "update OASafeKind set status='98' where companyID = ? and id = ? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql1 }, params);
		return "succ";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(OASafeKind.columnHeadings(),
				baseBeanService.getListByDC(getListBYDC()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook  logbook=logBookService.saveCLogBook(null, "导出安全列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	/**
	 * 用于显示父类安全类别 parentID 为001 的是安全类别
	 */
	public String getSafeKinds() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = " from OASafeKind where  parentID = ? and companyID = ?";
		Object[] params = { parentID, account.getCompanyID() };
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql, params);
		return "safeKindlist";
	}

	/**
	 * ajax查询 根据companyID和parentID查询列表
	 */
	public String getSafeKindsByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "succ";
		}
		//判断是否有预设安全类别的数据
		String hql = "from OASafeKind s where  s.parentID = ? and s.companyID = ? and s.status = '01' ";
		Object[] params = {parentID, account.getCompanyID() };
		List<BaseBean> bBeans = baseBeanService.getListBeanByHqlAndParams(hql, params);
		if(bBeans.size()>0||!parentID.equals("001")){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("safeKindList", bBeans);
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return "success";
			
		}
		//预设数据
		bBeans = defaultValue(account.getCompanyID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("safeKindList", bBeans);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 预设安全类别
	 * @param companyID 公司ID
	 */
	public List<BaseBean> defaultValue(String companyID) {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String[] arr = { "火灾预防", "防盗管理", "防霉管理", "防毒管理", "污染预防", "雪灾预防",
				"冰雹预防", "冻害预防", "垮塌预防", "地震预防", "洪涝预防", "防泥石流", "虫害预防", "疾病预防",
				"安全用电", "雷雨预防", "防龙卷风", "食品安全", "车辆设备" };
		OASafeKind entity = null;
		for (int i = 0; i < arr.length; i++) {
			entity = new OASafeKind();
			entity.setId(serverService.getServerID("id"));
			entity.setName(arr[i]);
			entity.setParentID("001");
			entity.setStatus("01");
			entity.setCompanyID(companyID);
			beans.add(entity);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return beans;
	}

	/**
	 * 用于判断安全类别名称唯一的方法
	 */
	public String checkTypeName() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "succ";
		}
		String hql = " from OASafeKind where  parentID = '001' and name = ? and companyID = ? and status='00' ";
		Object[] params = { parameter, account.getCompanyID() };
		List<BaseBean> safeKindList = baseBeanService
				.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		if (safeKindList.size() == 1) {
			map.put("size", 1);
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
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

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public OASafeKind getOasafeKind() {
		return oasafeKind;
	}

	public void setOasafeKind(OASafeKind oasafeKind) {
		this.oasafeKind = oasafeKind;
	}

	public List<BaseBean> getOasalist() {
		return oasalist;
	}

	public void setOasalist(List<BaseBean> oasalist) {
		this.oasalist = oasalist;
	}

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

}
