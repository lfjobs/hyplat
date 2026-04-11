package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.ViewDismisscontract;
import hy.ea.bo.human.vo.ViewFormalcontract;
import hy.ea.bo.office.archives.DtArchivesArchives;
import hy.ea.bo.office.archives.DtArchivesArchiveshistory;
import hy.ea.bo.office.archives.DtArchivesAttachment;
import hy.ea.bo.office.archives.DtArchivesCataloguearchives;
import hy.ea.bo.office.archives.DtArchivesInventorylocation;
import hy.ea.bo.office.archives.DtArchivesArchives.ArchiveTemp;
import hy.ea.bo.office.vo.ViewArchive;
import hy.ea.service.CLogBookService;
import hy.ea.util.FileUtil;
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
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

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
public class ArchiveManageAction {
	private static final Logger logger = LoggerFactory.getLogger(ArchiveManageAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;

	private DtArchivesCataloguearchives archiveCatalogue;
	private ArchiveTemp archiveTemp = new ArchiveTemp();
	private ArchiveTemp archiveTemp2;
	private ViewArchive viewArchive = new ViewArchive();
	private PageForm pageForm;
	private String search;
	private Company company;
	private String result;
	private int pageNumber;
	private String parameter;
	private Staff outuser;
	private List<ArchiveTemp> archivelist;
	private String printtype;
	private String catemodule;
	private String type;
	private String historyID;
	private Staff searchCStaff;
	/**
	 * 请求转发使用 区别 学员报名归档 与 教务科一生产归档
	 */
	public String extensionStaffCoach;
	public Staff cstaff;




	/**
	 * 
	 * 
	 * 获取档案列表(包括档案列表和历史记录表字段)
	 * 
	 * @return
	 */

	public String getArchiveList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		if (account == null) {
			return "not_login";
		}
		if (catemodule == null || catemodule.equals("")) {
			catemodule = (String) session.get("module");
		}else{
			session.remove("module");
			session.put("module", catemodule);
		}
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getlist(type));
	

		return "archivelist";
	}

	// 根据条件查询证件管理列表
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", viewArchive);
		return getArchiveList();
	}

	private DetachedCriteria getlist(String type) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(ViewArchive.class);
		dc.add(Restrictions.eq("catemodule", (String) session.get("module")));
		if (type!=null&&type.equals("company")) {
			dc.add(Restrictions.eq("companyid", account.getCompanyID()));
		} else if (type!=null&&type.equals("group")) {
			String hql = "from Company where companyID = ?";

			hql = "select companyID from Company where companyPID = ? or companyID = ?";

			List<BaseBean> list = baseBeanService
					.getListBeanByHqlAndParams(hql,
							new Object[] { account.getCompanyID(),
									account.getCompanyID() });

			dc.add(Restrictions.in("companyid", list));
		} else {
			dc.add(Restrictions.eq("inuser", account.getStaffID()));
		}
		if (search != null && search.equals("search")) {
			viewArchive = (ViewArchive) session.get("tablesearch");
			if (null != viewArchive.getArchivecode()
					&& !"".equals(viewArchive.getArchivecode())) {
				dc.add(Restrictions.like("archivecode", viewArchive
						.getArchivecode(), MatchMode.ANYWHERE));
			}
			if (null != viewArchive.getName()
					&& !"".equals(viewArchive.getName())) {
				dc.add(Restrictions.like("name", viewArchive.getName(),
						MatchMode.ANYWHERE));
			}
			if (null != viewArchive.getCategroyid()
					&& !"".equals(viewArchive.getCategroyid())) {
				dc.add(Restrictions.like("categroyid", viewArchive
						.getCategroyid(), MatchMode.ANYWHERE));
			}
			if (null != viewArchive.getBarcode()
					&& !"".equals(viewArchive.getBarcode())) {
				dc.add(Restrictions.like("barcode", viewArchive.getBarcode(),
						MatchMode.ANYWHERE));
			}
			if (null != viewArchive.getChipid()
					&& !"".equals(viewArchive.getChipid())) {
				dc.add(Restrictions.like("chipid", viewArchive.getChipid(),
						MatchMode.ANYWHERE));
			}
			if (null != viewArchive.getSecuritylevel()
					&& !"".equals(viewArchive.getSecuritylevel())) {
				dc.add(Restrictions.like("securitylevel", viewArchive
						.getSecuritylevel(), MatchMode.ANYWHERE));
			}
			if (null != viewArchive.getLocationid()
					&& !"".equals(viewArchive.getLocationid())) {
				dc.add(Restrictions.like("locationid", viewArchive
						.getLocationid(), MatchMode.ANYWHERE));
			}
			if (null != viewArchive.getState()
					&& !"".equals(viewArchive.getState())) {
				dc.add(Restrictions.eq("state", viewArchive.getState()));
			}
			if (null != viewArchive.getObsoletestatus()
					&& !"".equals(viewArchive.getObsoletestatus())) {
				dc.add(Restrictions.eq("obsoletestatus", viewArchive
						.getObsoletestatus()));
			}
		} else {
			dc.add(Restrictions.eq("state", 1l));
			dc.add(Restrictions.eq("obsoletestatus", "00"));
		}

		return dc;
	}

	/**
	 * 
	 * 得到选中的出库单
	 * 
	 * @return
	 */
	public String getOutLibraryInfoList() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String historyIDs = request.getParameter("historyIDs");
		String[] historyIDArray = historyIDs.split(",");
		String hql = "from ViewArchive where historyid = ?";
		String hqlOrg = "from  COrganization where organizationID=?";
		String hqlCom = "from Company where companyID = ?";
		Company company = null;
		COrganization org = null;
		ViewArchive viewArchive = null;
		List<ViewArchive> archivelist = new ArrayList<ViewArchive>();
		for (int i = 0; i < historyIDArray.length; i++) {
			viewArchive = (ViewArchive) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { historyIDArray[i] });

			if (viewArchive.getState().equals(0l)
					|| viewArchive.getObsoletestatus().equals("01")) {
				continue;
			}

			viewArchive.setIntimestr(Utilities.getDateString(viewArchive
					.getIntime(), "yyyy-MM-dd HH:mm:ss"));
			company = (Company) baseBeanService.getBeanByHqlAndParams(hqlCom,
					new Object[] { account.getCompanyID() });

			org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlOrg,
					new Object[] { (String) session.get("organizationID") });
			viewArchive.setCompanyname(company.getCompanyName());
			viewArchive.setOrg(org.getOrganizationName());
			viewArchive.setOuttimestr(Utilities.getDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
			archivelist.add(viewArchive);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("archivelist", archivelist);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

		return "success";
	}

	/*
	 * 提交入库
	 * 
	 */

	public String addArchives() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String catemodule = (String) session.get("module");
		if (account == null) {
			return "not_login";
		}
		try {
			DtArchivesArchives oldArchive = null;
			if (archiveTemp.getHistoryID() == null
					|| "".equals(archiveTemp.getHistoryID())) {
				archiveTemp.setHistoryID(serverService.getServerID("histoyid"));
				archiveTemp.setArchivesid(serverService
						.getServerID("archiveid"));
				parameter = "添加档案(档案名称:" + archiveTemp.getName() + ")";
				// 声明各个实体对象
				List<BaseBean> beans = new ArrayList<BaseBean>();
				DtArchivesArchives archive = new DtArchivesArchives();
				DtArchivesArchiveshistory history = new DtArchivesArchiveshistory();

				DtArchivesCataloguearchives catalogue = null;
				if (!catemodule.equals("global")) {
					// 存储档案信息
					String hqlcc = "from DtArchivesCataloguearchives where catemodule = ? and comanyid = ?";

					DtArchivesCataloguearchives addcatalogue = null;
					DtArchivesCataloguearchives cataloguetest = (DtArchivesCataloguearchives) baseBeanService
							.getBeanByHqlAndParams(hqlcc,
									new Object[] { organizationID,
											account.getCompanyID() });
					if (cataloguetest == null) {
						String parentid = "";
					if (catemodule.equals("01") || catemodule.equals("02")
							|| catemodule.equals("03")) {

							for (int i = 0; i < 4; i++) {
								addcatalogue = new DtArchivesCataloguearchives();
								if (i == 0) {
									addcatalogue.setName("人事处");
									addcatalogue.setCatemodule(organizationID);
								} else if (i == 1) {
									addcatalogue.setName("实习协议");
									addcatalogue.setCatemodule("02");
								} else if (i == 2) {
									addcatalogue.setName("劳动合同");
									addcatalogue.setCatemodule("01");
								} else if (i == 3) {
									addcatalogue.setName("劳务协议");
									addcatalogue.setCatemodule("03");
								}
								if (i == 0) {
									parentid = serverService
											.getServerID("catalogueid");
									addcatalogue.setArchiveid(parentid);
								} else {
									addcatalogue.setArchiveid(serverService
											.getServerID("catalogueid"));
									addcatalogue.setParent(parentid);
								}
								addcatalogue
										.setComanyid(account.getCompanyID());

								Date cur = new Date();
								addcatalogue.setCreatetime(cur);
								addcatalogue
										.setCreateuser(account.getStaffID());
								addcatalogue.setModifytime(cur);
								addcatalogue
										.setModifyuser(account.getStaffID());
								baseBeanService.save(addcatalogue);

							}

						}
					if (catemodule.equals("theory") || catemodule.equals("piletest")
							|| catemodule.equals("yard")) {
							for (int i = 0; i < 5; i++) {
								addcatalogue = new DtArchivesCataloguearchives();
								if (i == 0) {
									addcatalogue.setName("教务处");
									addcatalogue.setCatemodule(organizationID);
								} else if (i == 1) {
									addcatalogue.setName("理论");
									addcatalogue.setCatemodule("theory");
								} else if (i == 2) {
									addcatalogue.setName("桩考");
									addcatalogue.setCatemodule("piletest");
								} else if (i == 3) {
									addcatalogue.setName("场地");
									addcatalogue.setCatemodule("yard");
								} else {
									addcatalogue.setName("路考");
									addcatalogue.setCatemodule("roadtest");
								}
								if (i == 0) {
									parentid = serverService.getServerID("catalogueid");
									addcatalogue.setArchiveid(parentid);
								} else {
									addcatalogue.setArchiveid(serverService
											.getServerID("catalogueid"));
									addcatalogue.setParent(parentid);
								}
								addcatalogue.setComanyid(account.getCompanyID());

								Date cur = new Date();
								addcatalogue.setCreatetime(cur);
								addcatalogue.setCreateuser(account.getStaffID());
								addcatalogue.setModifytime(cur);
								addcatalogue.setModifyuser(account.getStaffID());
								baseBeanService.save(addcatalogue);

							}

						}
						catalogue = (DtArchivesCataloguearchives) baseBeanService
								.getBeanByHqlAndParams(hqlcc, new Object[] {
										catemodule, account.getCompanyID() });
					} else {
						String hqlmo = "from DtArchivesCataloguearchives where comanyid = ? and catemodule = ?";
						catalogue = (DtArchivesCataloguearchives) baseBeanService
								.getBeanByHqlAndParams(hqlmo, new Object[] {
										account.getCompanyID(), catemodule });
					}
				} else {

					String hqlid = "from DtArchivesCataloguearchives where archiveid = ?";
					catalogue = (DtArchivesCataloguearchives) baseBeanService
							.getBeanByHqlAndParams(hqlid,
									new Object[] { archiveTemp.getCatalogue() });

				}

				String hql = "from DtArchivesInventorylocation where locationid = ?";
				DtArchivesInventorylocation location = (DtArchivesInventorylocation) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { archiveTemp
								.getLocation() });

				CLogBook logbook = logBookService.saveCLogBook(organizationID,
						parameter, account);

				// 对档案的存储
				archive.setArchivesid(archiveTemp.getArchivesid());
				archive.setArchiveCode(archiveTemp.getArchiveCode());
				archive.setBarcode(archiveTemp.getBarcode());
				archive.setChipid(archiveTemp.getChipid());
				archive.setName(archiveTemp.getName());
//				archive.setContractCode(archiveTemp.getContractCode());
				if (archiveTemp.getContractSignDate() != null
						&& !archiveTemp.getContractSignDate().equals("")) {
					archive.setContractSignDate(Utilities.getDateFromString(
							archiveTemp.getContractSignDate(), "yyyy-MM-dd"));
				}
				if (archiveTemp.getStartDate() != null
						&& !archiveTemp.getStartDate().equals("")) {
					archive.setStartValidity(Utilities.getDateFromString(
							archiveTemp.getStartDate(), "yyyy-MM-dd"));
				}
				if (archiveTemp.getEndDate() != null
						&& !archiveTemp.getEndDate().equals("")) {
					archive.setEndValidity(Utilities.getDateFromString(
							archiveTemp.getEndDate(), "yyyy-MM-dd"));
				}
				archive.setCompanyid(account.getCompanyID());
				Date time = new Date();
				archive.setCreatedate(time);
				archive.setModifydate(time);
				archive.setCreatuser(account.getStaffID());
				archive.setModifyuser(account.getStaffID());
				archive.setDtArchivesCataloguearchives(catalogue);
//				archive.setArchiveCode(archiveService.getCode(account
//						.getCompanyID()));
				archive.setSecuritylevel(archiveTemp.getSecuritylevel());
				archive.setStaffID(archiveTemp.getStaffID());
				archive.setObsoletestatus("00");
				beans.add(logbook);
				beans.add(archive);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
						null, null);

				String hqlarchive = "from DtArchivesArchives where archivesid = ?";
				oldArchive = (DtArchivesArchives) baseBeanService
						.getBeanByHqlAndParams(hqlarchive,
								new Object[] { archiveTemp.getArchivesid() });

				// 对历史记录的存储
				history.setHistoryid(archiveTemp.getHistoryID());
				history.setIntime(new Date());
				history.setInuser(account.getStaffID());
				history.setState("1");
				history.setDtArchivesInventorylocation(location);
				history.setDtArchivesArchives(oldArchive);
				baseBeanService.save(history);
			} else {
				String hql = "from DtArchivesArchiveshistory where historyID = ?";
				DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { archiveTemp
								.getHistoryID() });
				oldArchive = history.getDtArchivesArchives();
				if (archiveTemp.getName() != null
						&& !archiveTemp.getName().equals("")) {

					oldArchive.setName(archiveTemp.getName());
					oldArchive.setBarcode(archiveTemp.getBarcode());
					oldArchive.setChipid(archiveTemp.getChipid());
					oldArchive.setModifydate(new Date());
					oldArchive.setModifyuser(account.getStaffID());
					oldArchive.setSecuritylevel(archiveTemp.getSecuritylevel());
					if(archiveTemp.getAttach() != null
					&& !archiveTemp.getAttach().equals("")){
						oldArchive.setAttach("1");
					}
					if (archiveTemp.getStartDate() != null
							&& !archiveTemp.getStartDate().equals("")) {
						oldArchive.setStartValidity(Utilities
								.getDateFromString(archiveTemp.getStartDate(),
										"yyyy-MM-dd"));
					}
					if (archiveTemp.getEndDate() != null
							&& !archiveTemp.getEndDate().equals("")) {
						oldArchive.setEndValidity(Utilities.getDateFromString(
								archiveTemp.getEndDate(), "yyyy-MM-dd"));
					}
					DtArchivesCataloguearchives catalogue = null;
					hql = "from DtArchivesCataloguearchives where archiveid = ?";
					catalogue = (DtArchivesCataloguearchives) baseBeanService
							.getBeanByHqlAndParams(hql,
									new Object[] { archiveTemp.getCatalogue() });

					oldArchive.setDtArchivesCataloguearchives(catalogue);

					baseBeanService.update(oldArchive);

				}
				hql = "from DtArchivesInventorylocation where locationid = ?";
				DtArchivesInventorylocation location = (DtArchivesInventorylocation) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { archiveTemp
								.getLocation() });

				history.setDtArchivesInventorylocation(location);
				baseBeanService.update(history);

			}

			// 对附件的存储
			DtArchivesAttachment attachment = null;
			if (archiveTemp.getAttach() != null
					&& !archiveTemp.getAttach().equals("")) {
				String[] attachArray = archiveTemp.getAttach().split(",");
				String[] sizeArray = archiveTemp.getFilesize().split(",");
				for (int i = 0; i < attachArray.length; i++) {
					attachment = new DtArchivesAttachment();

					attachment.setAttachmentid(serverService
							.getServerID("archive"));
					attachment.setCompanyid(account.getCompanyID());
					attachment.setCreatedate(new Date());
					attachment.setCreatuser(account.getStaffID());
					String filename = attachArray[i].substring(attachArray[i]
							.lastIndexOf("/") + 1);
					attachment.setFilename(filename);
					attachment.setExtension(filename.substring(filename
							.lastIndexOf('.') + 1));
					attachment.setFilepath(attachArray[i]);
					attachment.setFilesize(Float.parseFloat(sizeArray[i]));
					attachment.setDtArchivesArchives(oldArchive);
					baseBeanService.save(attachment);
				}
			}

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";
	}

	/**
	 * 
	 * 判断档案是否可以修改；已出库不可修改暂不使用
	 * 
	 * @return
	 */
	public String IsCanUpdate() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String historyID = request.getParameter("historyID");
		String hql = "from DtArchivesArchiveshistory where historyID = ?";
		DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { historyID });
		DtArchivesArchives archive = history.getDtArchivesArchives();
		Map<String, Object> map = new HashMap<String, Object>();

		if (history.getState().equals("0")
				|| archive.getObsoletestatus().equals("01")) {
			map.put("result", "fail");
		} else {
			map.put("result", "success");
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/*
	 * 
	 * 判断档案可修改范围
	 */
	public String IsUpdateEndge() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String historyID = request.getParameter("historyID");
		String hql = "from DtArchivesArchiveshistory where historyID = ?";
		String hql1 = "from DtArchivesArchiveshistory where dtArchivesArchives = ?";
		DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { historyID });
		DtArchivesArchives archive = history.getDtArchivesArchives();
		List<BaseBean> historylist = baseBeanService.getListBeanByHqlAndParams(
				hql1, new Object[] { archive });
		Map<String, Object> map = new HashMap<String, Object>();
		if (historylist.size() > 1) {
			map.put("result", "fail");
		} else {
			map.put("result", "success");
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 判断芯片是否重复
	 * 
	 * @return
	 */
	public String IsChipRepeat() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String chipid = request.getParameter("chipid");
		String hql = "from DtArchivesArchives where chipid = ? and obsoletestatus='00'";
		List<BaseBean> archivelist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { chipid });
		Map<String, Object> map = new HashMap<String, Object>();

		if (archivelist.size() == 0) {
			map.put("result", "success");// 不重复
		} else {
			map.put("result", "fail");

		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 出库提交
	 * 
	 * @return
	 */
	public String OutLibrary() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String historyIDs = request.getParameter("historyIDs");
		String outuserid = request.getParameter("outuserid");
		String outtimestr = request.getParameter("outtimestr");
		String outstatus = request.getParameter("outstatus");
		String resultstr = "";

		try {
			// 添加出库信息
			String[] hisArray = historyIDs.split(",");
			String hql = "from DtArchivesArchiveshistory where historyID =?";
			for (int i = 0; i < hisArray.length; i++) {
				DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
						.getBeanByHqlAndParams(hql,
								new Object[] { hisArray[i] });
				DtArchivesArchives oldarchive = history.getDtArchivesArchives();
				String result = IsCanOutLibaray(hisArray[i], outuserid,
						outstatus);
				if (!result.equals("success")) {
					resultstr += oldarchive.getArchiveCode() + ",";

				} else {
					history.setOutuser(outuserid);
					if (!outtimestr.equals("")) {
						history.setOuttime(Utilities.getDateFromString(
								outtimestr, "yyyy-MM-dd HH:mm:ss"));
					}
					history.setState("0");
					history.setDtArchivesInventorylocation(null);
					baseBeanService.update(history);

					// 添加出库接收人的入库信息

					// 历史记录
					DtArchivesArchiveshistory newhistory = new DtArchivesArchiveshistory();
					newhistory.setHistoryid(serverService
							.getServerID("historyid"));
					newhistory.setIntime(new Date());
					newhistory.setInuser(outuserid);
					newhistory.setState("1");
					newhistory.setDtArchivesArchives(oldarchive);
					baseBeanService.save(newhistory);

					if (outstatus != null && !outstatus.equals("")) {
						String hqlcate = "from DtArchivesCataloguearchives where catemodule = ?";
						DtArchivesCataloguearchives catelogue = (DtArchivesCataloguearchives) baseBeanService
								.getBeanByHqlAndParams(hqlcate,
										new Object[] { outstatus });
						oldarchive.setDtArchivesCataloguearchives(catelogue);
						baseBeanService.update(oldarchive);

					}
				}

			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", resultstr);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";
	}

	/**
	 * 
	 * 判断是否允许出库：两种情况不可出库1,已出库；2，出库给自己；
	 * 
	 * @return
	 */
	public String IsCanOutLibaray(String historyID, String outuserid,
			String outstatus) {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		String result = "";

		String hql = "from DtArchivesArchiveshistory where historyID = ?";
		DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { historyID });
		DtArchivesArchives archive = history.getDtArchivesArchives();
		DtArchivesCataloguearchives catelogue = archive
				.getDtArchivesCataloguearchives();
		if (history.getState().equals("0")
				|| (outuserid.equals(history.getInuser()) && catelogue
						.getCatemodule().equals(outstatus))
				|| archive.getObsoletestatus().equals("01")) {
			result = "fail";

		} else {
			result = "success";
		}

		return result;
	}

	/**
	 * 
	 * 跟踪获取数据
	 * 
	 * @return
	 */
	public String getTrackList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String historyID = request.getParameter("historyID");
			String hql = "from DtArchivesArchiveshistory where historyID = ?";
			DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { historyID });
			DtArchivesArchives oldarchive = history.getDtArchivesArchives();
			hql = "from DtArchivesArchiveshistory where dtArchivesArchives = ? order by intime";
			List<BaseBean> historyList = baseBeanService
					.getListBeanByHqlAndParams(hql, new Object[] { oldarchive });

			hql = "from Staff where StaffID = ?";
			Staff sinuser = null;
			Staff soutuser = null;
			for (BaseBean b : historyList) {
				DtArchivesArchiveshistory dthistory = (DtArchivesArchiveshistory) b;
				sinuser = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { dthistory.getInuser() });
				dthistory.setInuser(sinuser.getStaffName());
				dthistory.setIntimestr(dthistory.getIntime().toString()
						.substring(0, 19));
				if (dthistory.getOutuser() != null
						&& !dthistory.getOutuser().equals("")) {
					soutuser = (Staff) baseBeanService.getBeanByHqlAndParams(
							hql, new Object[] { dthistory.getOutuser() });
					dthistory.setOutuser(soutuser.getStaffName());
					dthistory.setOuttimestr(dthistory.getOuttime().toString()
							.substring(0, 19));

				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("historyList", historyList);
			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(DtArchivesArchiveshistory.class);
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("dtArchivesArchiveses")
							|| arg1.equals("dtArchivesInventorylocation")) {
						return true;
					} else {
						return false;
					}
				}
			});
			JSONObject jo = JSONObject.fromObject(map, cfg);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 
	 * 获取修改时数据
	 * 
	 * @return
	 */
	public String getUpdateView() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		String hql = "from DtArchivesArchiveshistory where historyID = ?";

		DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { historyID });
	
		DtArchivesArchives oldarchive = history.getDtArchivesArchives();


		hql = "from DtArchivesAttachment where dtArchivesArchives = ?";
		List<BaseBean> attachlist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { oldarchive });

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("attachlist", attachlist);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 * 修改时删除附件
	 * 
	 * @return
	 */
	public String deleteAttach() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String attachmentid = request.getParameter("attachmentid");
		try {
			String hql = "from DtArchivesAttachment where attachmentid = ?";
			DtArchivesAttachment attachment = (DtArchivesAttachment) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { attachmentid });
			baseBeanService.deleteBeanByKey(DtArchivesAttachment.class,
					attachment.getAttachmentkey());
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			FileUtil.delete(path + attachment.getFilepath());
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "suc");
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 删除档案历史记录（未使用）
	 * 
	 * @return
	 */
	public String deleteArchive() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String historyIDs = request.getParameter("historyIDs");
			String[] hisArray = historyIDs.split(",");
			String hql1 = "from DtArchivesArchiveshistory where historyID = ?";
			String hql2 = "from DtArchivesAttachment where dtArchivesArchives = ?";
			DtArchivesArchiveshistory history = null;
			String resultstr = "";
			for (int i = 0; i < hisArray.length; i++) {
				history = (DtArchivesArchiveshistory) baseBeanService
						.getBeanByHqlAndParams(hql1,
								new Object[] { hisArray[i] });
				DtArchivesArchives archive = history.getDtArchivesArchives();

				// 判断是否是自己创建的档案
				if (archive.getCreatuser().equals(account.getStaffID())) {
					// 是自己的档案，判断是否已出库
					if (history.getState().equals("1")
							&& archive.getObsoletestatus().equals("00")) {
						// 在库中，可以删除
						baseBeanService.deleteBeanByKey(
								DtArchivesArchiveshistory.class, history
										.getHistorykey());
						hql2 = "from DtArchivesAttachment where dtArchivesArchives = ?";
						List<BaseBean> attachlist = baseBeanService
								.getListBeanByHqlAndParams(hql2,
										new Object[] { archive });
						String path = ServletActionContext.getRequest()
								.getSession().getServletContext().getRealPath(
										"/");
						for (BaseBean attach : attachlist) {
							DtArchivesAttachment attachment = (DtArchivesAttachment) attach;
							FileUtil.delete(path + attachment.getFilepath());
							baseBeanService.deleteBeanByKey(
									DtArchivesAttachment.class, attachment
											.getAttachmentkey());
						}

						baseBeanService.deleteBeanByKey(
								DtArchivesArchives.class, archive
										.getArchiveskey());

					} else {
						resultstr += archive.getArchiveCode() + ",";
					}
				} else {
					resultstr += archive.getArchiveCode() + ",";

				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", resultstr);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 
	 * 作废档案
	 * 
	 * @return
	 */
	public String obsoleteArchive() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String historyIDs = request.getParameter("historyIDs");
		String[] hisArray = historyIDs.split(",");
		String hql1 = "from DtArchivesArchiveshistory where historyID = ?";
		DtArchivesArchiveshistory history = null;
		for (int i = 0; i < hisArray.length; i++) {
			history = (DtArchivesArchiveshistory) baseBeanService
					.getBeanByHqlAndParams(hql1, new Object[] { hisArray[i] });
			DtArchivesArchives archive = history.getDtArchivesArchives();

			if (archive != null) {
				archive.setObsoletestatus("01");
			}
			baseBeanService.update(archive);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "suc");
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 
	 * 获取编号
	 * 
	 * @param companyID停用，转移到service
	 * @return
	 */
	public String getCode(String companyID) {
		String hql = "from DtArchivesArchives where companyID = ?";
		String sql = "select max(to_number(archiveCode)) from DT_ARCHIVES_ARCHIVES where companyID= ?";
		int nextnum = 0;
		List<BaseBean> list = (List<BaseBean>) baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] { companyID });

		if (list.size() == 0) {
			return "1";
		} else {
			int a = baseBeanService.getConutByBySqlAndParams(sql,
					new Object[] { companyID });
			nextnum = a + 1;

		}
		return nextnum + "";

	}

	/**
	 * 获得档案类别名称列表
	 * 
	 * @return
	 */
	public String getCatalogueAndLocation() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		try {
			String hqlarchive = "";
			String hqllocation = "";
			Object[] paramsarc = null;
			Object[] paramsloc = null;
			if (type != null & !type.equals("") && !type.equals("null")) {
				if (type.equals("company")) {
					hqlarchive = "from DtArchivesCataloguearchives where comanyid = ? order by modifytime desc ";
					hqllocation = "from DtArchivesInventorylocation where companyideas = ? order by createdate desc";
					paramsarc = new Object[] { account.getCompanyID() };
					paramsloc = new Object[] { account.getCompanyID() };

				} else {
					hqlarchive = "from DtArchivesCataloguearchives d where d.comanyid in(select y.companyID from Company y where y.companyPID = ? or y.companyID = ?)  order by modifytime desc";
					hqllocation = "from DtArchivesInventorylocation s where s.companyideas in(select y.companyID from Company y where y.companyPID = ? or y.companyID = ?)  order by createdate desc";
					paramsarc = new Object[] { account.getCompanyID(),
							account.getCompanyID() };
					paramsloc = new Object[] { account.getCompanyID(),
							account.getCompanyID() };
				}
			} else {
				hqlarchive = "from DtArchivesCataloguearchives where comanyid = ? order by modifytime desc";
				hqllocation = "from DtArchivesInventorylocation where userid = ? order by createdate desc";
				paramsarc = new Object[] { account.getCompanyID() };
				paramsloc = new Object[] { account.getStaffID() };
			}
			// 获得档案列表
			List<BaseBean> cataloguelist = baseBeanService
					.getListBeanByHqlAndParams(hqlarchive, paramsarc);

			// 获取存储位置列表
			List<BaseBean> locationlist = baseBeanService
					.getListBeanByHqlAndParams(hqllocation, paramsloc);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cataloguelist", cataloguelist);
			map.put("locationlist", locationlist);
			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(DtArchivesCataloguearchives.class);
			cfg.setRootClass(DtArchivesInventorylocation.class);
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("dtArchivesArchiveses")
							|| arg1.equals("dtArchivesArchiveshistories")) {
						return true;
					} else {
						return false;
					}
				}
			});
			JSONObject jo = JSONObject.fromObject(map, cfg);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/**
	 * 
	 * 获取附件列表
	 */
	public String getAttachList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String archiveid = request.getParameter("archiveid");
			String hql = "from DtArchivesArchives where archivesid = ?";
			DtArchivesArchives archive = (DtArchivesArchives) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { archiveid });
			hql = "from DtArchivesAttachment where dtArchivesArchives = ?";
			List<BaseBean> attachlist = baseBeanService
					.getListBeanByHqlAndParams(hql, new Object[] { archive });
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attachlist", attachlist);
			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(DtArchivesAttachment.class);
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("dtArchivesArchives")) {
						return true;
					} else {
						return false;
					}
				}
			});
			JSONObject jo = JSONObject.fromObject(map, cfg);
			this.result = jo.toString();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}

	/*
	 * 
	 * 打印预览
	 */
	public String toPrintArchive() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		String organizationID = (String) session.get("organizationID");
		String historyIDs = archiveTemp.getHistoryID();
		String[] historyIDArray = historyIDs.split(",");
		String hql = "from DtArchivesArchiveshistory where historyID = ?";
		String hqlOrg = "from  COrganization where organizationID=?";
		String hqlCom = "from Company where companyID = ?";
		String hqlstaff = "from Staff where staffID = ?";
		Company company = null;
		COrganization org = null;
		Staff inuser = null;
		archivelist = new ArrayList<ArchiveTemp>();
		for (int i = 0; i < historyIDArray.length; i++) {
			DtArchivesArchiveshistory history = (DtArchivesArchiveshistory) baseBeanService
					.getBeanByHqlAndParams(hql,
							new Object[] { historyIDArray[i] });
			DtArchivesInventorylocation location = history
					.getDtArchivesInventorylocation();
			DtArchivesArchives oldarchive = history.getDtArchivesArchives();
			DtArchivesCataloguearchives catalogue = oldarchive
					.getDtArchivesCataloguearchives();
			if (history.getState().equals("0")
					|| oldarchive.getObsoletestatus().equals("01")) {
				continue;
			}
			archiveTemp2 = new ArchiveTemp();
			archiveTemp2.setHistoryID(historyIDArray[i]);
			archiveTemp2.setArchiveCode(oldarchive.getArchiveCode());
			archiveTemp2.setName(oldarchive.getName());
			archiveTemp2.setBarcode(oldarchive.getBarcode());
			archiveTemp2.setChipid(oldarchive.getChipid());
			archiveTemp2.setOuttimestr(Utilities.getDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss"));
			if (oldarchive.getSecuritylevel().equals("01")) {
				archiveTemp2.setSecuritylevel("一级");
			} else if (oldarchive.getSecuritylevel().equals("02")) {
				archiveTemp2.setSecuritylevel("二级");
			} else {
				archiveTemp2.setSecuritylevel("三级");
			}
			archiveTemp2.setCatalogue(catalogue.getName());
			if (location != null) {
				archiveTemp2.setLocation(location.getLocationname());
			}
			archiveTemp2.setIntime(history.getIntime());
			if (!archiveTemp.getOuttimestr().equals("")) {
				archiveTemp2.setOuttimestr(Utilities.getDateString(new Date(),
						"yyyy-MM-dd HH:mm:ss"));
			} else {
				if (history.getOuttime() != null
						&& !history.getOuttime().equals("")) {
					archiveTemp2.setOuttimestr(Utilities.getDateString(history
							.getOuttime(), "yyyy-MM-dd HH:mm:ss"));
				}
			}

			company = (Company) baseBeanService.getBeanByHqlAndParams(hqlCom,
					new Object[] { oldarchive.getCompanyid() });
			org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlOrg,
					new Object[] { organizationID });
			inuser = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
					new Object[] { history.getInuser() });
			if (archiveTemp.getOutuser() != null
					&& !archiveTemp.getOutuser().equals("")) {
				outuser = (Staff) baseBeanService.getBeanByHqlAndParams(
						hqlstaff, new Object[] { archiveTemp.getOutuser() });
				archiveTemp2.setOutuser(outuser.getStaffID());
				archiveTemp2.setOutusername(outuser.getStaffName());
			}
			archiveTemp2.setCompanyName(company.getCompanyName());
			archiveTemp2.setOrganization(org.getOrganizationName());
			archiveTemp2.setInuser(inuser.getStaffName());
			archivelist.add(archiveTemp2);
		}

		return "toPrint";
	}

	public DetachedCriteria getList(String type) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = null;
		if (type.equals("formal")) {
			dc = DetachedCriteria.forClass(ViewFormalcontract.class);
		} else {

			dc = DetachedCriteria.forClass(ViewDismisscontract.class);
		}

		dc.addOrder(Order.desc("contractsigndate"));
		dc.add(Restrictions.eq("companyid", account.getCompanyID()));

		if (search != null && search.equals("search")) {
			archiveTemp = (ArchiveTemp) session.get("tablesearch");
			if (archiveTemp.getStaffID() != null
					&& !archiveTemp.getStaffID().equals(""))
				dc.add(Restrictions.like("staffid", archiveTemp.getStaffID(),
						MatchMode.ANYWHERE));
		}
		return dc;

	}

	// 合同台账；type;在职；离职；
	public String getContractParamList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getList(type));

		return "contract";
	}

	/**
	 * 
	 * 合同台账查询
	 * 
	 * @return
	 */
	public String toSearchContract() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", archiveTemp);
		return getContractParamList();
	}

	/**
	 * 
	 * 续签
	 * 
	 * @return
	 */
	public String renewalDate() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		if (archiveTemp.getHistoryID() != null
				&& !"".equals(archiveTemp.getHistoryID())) {
			String[] historyarry = archiveTemp.getHistoryID().split(",");
			String hql = "from DtArchivesArchiveshistory where historyID = ?";
			DtArchivesArchiveshistory history = null;
			for (int i = 0; i < historyarry.length; i++) {
				history = (DtArchivesArchiveshistory) baseBeanService
						.getBeanByHqlAndParams(hql,
								new Object[] { historyarry[i] });
				DtArchivesArchives oldArchive = history.getDtArchivesArchives();
				if (archiveTemp.getRenewalDate() != null
						&& !archiveTemp.getRenewalDate().equals("")) {
					oldArchive.setRenewalDate(Utilities.getDateFromString(
							archiveTemp.getRenewalDate(), "yyyy-MM-dd"));
				}
				baseBeanService.update(oldArchive);
			}
		}

		return "success";
	}
	
	
	/**
	 * 
	 * 
	 * 获得正式员工列表
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getStaffList();
	}

	//这里暂时是学员的信息；
	public String getStaffList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
					getdclist());

		return "stafflist";
	}
	
	public DetachedCriteria getdclist() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(ContactUser.class);
		searchCStaff = (Staff) session.get("tablesearch");
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("relation", "学员"));
		if (search != null && search != "") {
			if (searchCStaff.getStaffIdentityCard() != null
					&& searchCStaff.getStaffIdentityCard().equals("")) {
				dc.add(Restrictions.like("staffIdentityCard", searchCStaff
						.getStaffIdentityCard(), MatchMode.ANYWHERE));
			}
		}
		return dc;
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

	public ArchiveTemp getArchiveTemp() {
		return archiveTemp;
	}

	public void setArchiveTemp(ArchiveTemp archiveTemp) {
		this.archiveTemp = archiveTemp;
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

	public Staff getOutuser() {
		return outuser;
	}

	public void setOutuser(Staff outuser) {
		this.outuser = outuser;
	}

	public List<ArchiveTemp> getArchivelist() {
		return archivelist;
	}

	public void setArchivelist(List<ArchiveTemp> archivelist) {
		this.archivelist = archivelist;
	}

	public ArchiveTemp getArchiveTemp2() {
		return archiveTemp2;
	}

	public void setArchiveTemp2(ArchiveTemp archiveTemp2) {
		this.archiveTemp2 = archiveTemp2;
	}

	public String getPrinttype() {
		return printtype;
	}

	public void setPrinttype(String printtype) {
		this.printtype = printtype;
	}

	public String getCatemodule() {
		return catemodule;
	}

	public void setCatemodule(String catemodule) {
		this.catemodule = catemodule;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}

	public ViewArchive getViewArchive() {
		return viewArchive;
	}

	public void setViewArchive(ViewArchive viewArchive) {
		this.viewArchive = viewArchive;
	}

	public String getHistoryID() {
		return historyID;
	}

	public void setHistoryID(String historyID) {
		this.historyID = historyID;
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}
}
