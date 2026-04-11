package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.OASafeInspectInfo;
import hy.ea.bo.office.OASafeInspectItem;
import hy.ea.bo.office.OASafeKind;
import hy.ea.bo.office.vo.OASafeInspectInfoVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 安全检查任务单据
 * 
 * @author zgzg
 * 
 */
@Controller
@Scope("prototype")
public class OASafeInspectInfoAction {
	private static final Logger logger = LoggerFactory.getLogger(OASafeInspectInfoAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private UpLoadFileService fileService;
	private String parameter;
	private OASafeInspectInfoVO entityVO;
	private PageForm pageForm;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	private List<BaseBean> oasafeKindList;
	private OASafeInspectInfo entity;
	private Map<String, OASafeInspectItem> oasafeInspectItemmap;
	private String result;
	private String search;
	private String typeID;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private int pageNumber;
	private String sdate;
	private String edate;
	private String partnerID;
	private String departmentID;
	private String typeName;
	private String companyID;
	private String staffPost;
	private String billDate;// 临时制单日期

	/**
	 * 离线查询
	 * 
	 * @return
	 */
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria
				.forClass(OASafeInspectInfoVO.class);
		dc.add(Restrictions.eq("tsicompanyID", account.getCompanyID()));
		dc.add(Restrictions.eq("departmentID", (String) session
				.get("organizationID")));
		if (search != null && !search.equals("")) {
			entityVO = (OASafeInspectInfoVO) session.get("tablesearch");
			if (entityVO.getTypeName() != null
					&& !entityVO.getTypeName().trim().equals("")) {
				dc.add(Restrictions.eq("typeName", entityVO.getTypeName()
						.trim()));
			}
			if (entityVO.getInspectNO() != null
					&& !entityVO.getInspectNO().trim().equals("")) {
				dc.add(Restrictions.eq("inspectNO", entityVO.getInspectNO()
						.trim()));
			}
			if (entityVO.getTsiDeptID() != null
					&& !entityVO.getTsiDeptID().trim().equals("")) {
				dc.add(Restrictions.eq("tsiDeptID", entityVO.getTsiDeptID()
						.trim()));
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				dc.add(Restrictions.between("makeDate", Utilities
						.getDateFromString(sdate + " 00:00:00:000",
								"yyyy-MM-dd HH:mm:ss:sss"), Utilities
						.getDateFromString(edate + " 23:59:59:999",
								"yyyy-MM-dd HH:mm:ss:sss")));
			}
			if (entityVO.getExUnitName() != null
					&& !entityVO.getExUnitName().trim().equals("")) {
				dc.add(Restrictions.eq("exUnitName", entityVO.getExUnitName()
						.trim()));
			}
			if (entityVO.getExPerson() != null
					&& !entityVO.getExPerson().trim().equals("")) {
				dc.add(Restrictions.eq("exPerson", entityVO.getExPerson()
						.trim()));
			}
		}

		dc.addOrder(Order.desc("makeDate"));
		return dc;
	}

	/**
	 * 条件查询
	 * 
	 * @return
	 */
	public String toSearch() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", entityVO);
		return getSafeInspectList();
	}

	/**
	 * 删除安全检测单据
	 * 
	 * @return
	 */
	public String delSafeInspectBill() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String departmentID = (String) session.get("organizationID");
		String hql1 = "delete from OASafeInspectInfo where companyID = ? and id = ?";
		String hql2 = "delete from OASafeInspectItem where refid = ? ";
		Object[] parm1 = { account.getCompanyID(), entityVO.getInspectID() };
		Object[] parm2 = { entityVO.getInspectID() };
		List<Object[]> parmsList = new ArrayList<Object[]>();
		parmsList.add(parm1);
		parmsList.add(parm2);
		String hqlForLog = "from OASafeInspectInfo where companyID = ? and id = ?";
		OASafeInspectInfo inspectInfo = (OASafeInspectInfo) baseBeanService
				.getBeanByHqlAndParams(hqlForLog, parm1);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(departmentID,
				"删除安全检测单据(凭证号:" + inspectInfo.getInspectNO() + ")", account);
		beans.add(logbook);
		baseBeanService.executeHqlsByParamsList(beans, new String[] { hql1,
				hql2 }, parmsList);
		return "success";
	}

	/**
	 * 列表查询
	 * 
	 * @return
	 */
	public String getSafeInspectList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList());
		return "list";
	}

	/**
	 * 安全任务保存
	 * 
	 * @return :
	 */
	public String saveOASafeInspectItem() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			if (photo != null) {
				String path = ServletActionContext.getRequest().getSession()
						.getServletContext().getRealPath("/");
				String photoPath = fileService.savePhoto(path, photoFileName,
						photo, account.getCompanyID(), "/office/safe/"
								+ Utilities.getDateString(new Date(),
										"yyyy-MM-dd"));
				entity.setAttachment(photoPath);
			}
			if (null == entity.getId() || "".equals(entity.getId())) {
				entity.setId((serverService.getServerID("oaSafeInspectInfo")));
				entity.setMakeDate(new Date());
				parameter = "添加安全检查单（检查单号" + entity.getInspectNO() + "）";
			} else {
				parameter = "修改安全任务单（检查单号" + entity.getInspectNO() + "）";
				entity.setMakeDate(sdf.parse(billDate));
			}
			entity.setCompanyID(account.getCompanyID());
			entity.setDepartmentID(organizationID);
			if (entityVO != null) {
				entity.setUnitRelationShip(entityVO.getExUnitRelation());
				entity.setPersonalRelationShip(entityVO.getExPersonRelation());
			}
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
			baseBeanList.add(entity);
			if (oasafeInspectItemmap != null) {
				for (OASafeInspectItem inspectItem : oasafeInspectItemmap
						.values()) {
					if (inspectItem.getPhoto() != null
							&& !"".equals(inspectItem.getPhoto())) {
						String path = ServletActionContext.getRequest()
								.getSession().getServletContext().getRealPath(
										"/");
						String photoPath = fileService.savePhoto(path,
								inspectItem.getPhotoContentType(), inspectItem
										.getPhoto(), account.getCompanyID(),
								"/office/inspectItem/"
										+ Utilities.getDateString(new Date(),
												"yyyy-MM-dd"));
						inspectItem.setAttachMent(photoPath);
					}
					if (null == inspectItem.getId()
							|| "".equals(inspectItem.getId())) {
						inspectItem.setId(serverService
								.getServerID("inspectItem"));
					}
					inspectItem.setRefid(entity.getId());
					baseBeanList.add(inspectItem);
				}
			}
			CLogBook logbook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			baseBeanList.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);

		} catch (ParseException e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 安全检查项删除
	 * 
	 */
	public String delOASafeInspectItem() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { typeID };
		String hql = " delete from OASafeInspectItem where  id=? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
				new String[] { hql }, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeID", typeID);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 去安全任务添加、修改页面
	 * 
	 * @return : edit
	 */
	public String toSaveOASafeInspectInfo() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		oasafeKindList = (List<BaseBean>) baseBeanService
				.getListBeanByHqlAndParams(
						" from OASafeKind where companyID=? and parentID=? and (status=? or status = ?)",
						new Object[] { companyID, "001", "00", "01" });
		if (entityVO != null) {
			Object[] params1 = { entityVO.getInspectID() };
			String hql1 = "from OASafeInspectItem where refid=?";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from OASafeInspectInfoVO where inspectID=?";
			entityVO = (OASafeInspectInfoVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
		} else {
			entityVO = new OASafeInspectInfoVO();
			entityVO.setTsicompanyID(account.getCompanyID());
			entityVO.setTsiDeptID(organizationID);
			entityVO.setInspectNO(serverService.getBillID(companyID));
		}

		return "edit";
	}

	/**
	 * JSON取得安全类别列表
	 */
	public String getOASafeKindManager() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(OASafeKind.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("parentID", typeName));
		dc.add(Restrictions.eq("status", "00"));
		if (null != parameter && !"".equals(parameter)) {
			dc.add(Restrictions.like("descRiption", parameter,
					MatchMode.ANYWHERE));
		}
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * AJAX根据人员取得岗位职责
	 * 
	 * @return
	 */
	public String ajaxResponsibilitiesList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { companyID, departmentID, partnerID, staffPost };
		String hql = "from Responsibilities where companyID=? and organizationID=? and staffID=? and postmanage=?";
		List<BaseBean> responsibilitiesList = baseBeanService
				.getListBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("responsibilitiesList", responsibilitiesList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public OASafeInspectInfoVO getEntityVO() {
		return entityVO;
	}

	public void setEntityVO(OASafeInspectInfoVO entityVO) {
		this.entityVO = entityVO;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public CLogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}

	public OASafeInspectInfo getEntity() {
		return entity;
	}

	public void setEntity(OASafeInspectInfo entity) {
		this.entity = entity;
	}

	public Map<String, OASafeInspectItem> getOasafeInspectItemmap() {
		return oasafeInspectItemmap;
	}

	public void setOasafeInspectItemmap(
			Map<String, OASafeInspectItem> oasafeInspectItemmap) {
		this.oasafeInspectItemmap = oasafeInspectItemmap;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
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

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List<BaseBean> getOasafeKindList() {
		return oasafeKindList;
	}

	public void setOasafeKindList(List<BaseBean> oasafeKindList) {
		this.oasafeKindList = oasafeKindList;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffPost() {
		return staffPost;
	}

	public void setStaffPost(String staffPost) {
		this.staffPost = staffPost;
	}

	/**
	 * 临时制单日期
	 * 
	 * @return
	 */
	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

}
