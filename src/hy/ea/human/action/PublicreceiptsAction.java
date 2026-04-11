package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.CSP;
import hy.ea.bo.human.LogBook;
import hy.ea.bo.human.MonthSalary;
import hy.ea.bo.human.PayScale;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.publicreceipts.Publicreceipts;
import hy.ea.bo.human.publicreceipts.PublicreceiptsChild;
import hy.ea.bo.human.wage.PSHistory;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * PublicreceiptsAction 人事单
 * 
 * @author lf
 * 
 */
@Controller
@Scope("prototype")
public class PublicreceiptsAction {
	private static final Logger logger = LoggerFactory.getLogger(PublicreceiptsAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;

	private Publicreceipts publicreceipts;
	private PublicreceiptsChild publicreceiptsChild;
	private List<BaseBean> organizationlist;
	private String photoFileName;
	private Company company;
	public InputStream excelStream;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private String prID;
	private String companyname;
	private String organizationname;

	private String result;
	private String StaffIDvalue;
	private File photo;

	private String downLoadPath; // 下载传值

	/**
	 * 将罚单 审核流程 主管审核 00 人事审核01
	 */
	private String labelTag;
	private List<BaseBean> listGamJeom;
	private String types;
	private String type;//用于汇总

	

	/**
	 * 下载
	 * 
	 */
	public void downFile() {
		FileUtil fu = new FileUtil();
		try {
			fu.downFile(downLoadPath);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
	}

	/**
	 * 查看和打印调用的方法
	 */
	private void toSeePrint() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID, prID, };
		String hql = "from Publicreceipts where companyID=? and organizationID=? and prID=?";
		publicreceipts = (Publicreceipts) baseBeanService
				.getBeanByHqlAndParams(hql, params);
		String hql1 = "from PublicreceiptsChild where prID=?";
		publicreceiptsChild = (PublicreceiptsChild) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] { prID });
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { publicreceipts.getCompanyID() });
		companyname = company.getCompanyName();
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						publicreceipts.getCompanyID(),
						publicreceipts.getOrganizationID() });
		organizationname = cOrganization.getOrganizationName();
	}

	/** ************************************************员工级别变更************************************************** */

	/**
	 * 员工级别变更——根据负责人自动获取原级别明细
	 */
	public String getLevel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from PayScale t where t.companyID=? and  exists "
				+ "(select c.payScaleID from CSP c where t.payScaleID = c.payScaleID and exists "
				+ "(select c.staffID from COS s where c.staffID = s.staffID and s.companyID =? and s.staffID =?))";
		PayScale payScale = (PayScale) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID(),account.getCompanyID(), StaffIDvalue });
		String hql_pshistory = "from PSHistory where companyID = ? and staffID = ? and payScaleID= ? and status = ? ";
		PSHistory entity = (PSHistory) baseBeanService.getBeanByHqlAndParams(
				hql_pshistory, new Object[] { account.getCompanyID(),
						StaffIDvalue, payScale.getPayScaleID(), "01" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scale", payScale);
		if (entity != null)
			map.put("entry_date", entity.getEffectiveDate().toString()
					.substring(0, 10));
		else
			map.put("entry_date", "");
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 员工级别变更——员工级别下拉列表
	 * 
	 * @return
	 */
	public String getScaleList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select t.scale,t.positionPay,t.payScaleID from PayScale t where t.companyID = ? and t.status = ? order by t.scale";
		List<BaseBean> payScaleList = baseBeanService
				.getListBeanByHqlAndParams(hql, new Object[] {
						account.getCompanyID(), "00" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payScaleList", payScaleList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 员工级别变更单——根据条件查询(保存条件)
	 */
	public String toRankSearch() {
		ActionContext.getContext().getSession().put("publicreceiptRank",
				publicreceipts);
		return getRankPublicreceipt();
	}

	/**
	 * 员工级别变更单——列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getRankPublicreceipt() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String companyID = account.getCompanyID();
		String orgID = session.get("organizationID").toString();
		Object[] params1 = { companyID };
		String ohql = "from COrganization where companyID=? and Status = ?";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				new Object[] { companyID, "00" });
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params1);
		List<Object> list = getRankList(session, account, orgID);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "ranklist";
	}

	/**
	 * 员工级别变更单——查询列表（可根据条件查询）被调用
	 * 
	 * @return result
	 */
	private List<Object> getRankList(Map<String, Object> session,
			CAccount account, String organizationID) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select s.prid,c.companyname,cr.organizationname,s.vouchernum,s.principal,"
				+ " s.type,s.applydate,s.operator,d.rankhumanid,newLevel.scale newPayScale,d.rankeffectivedate,oldLevel.scale oldPayScale,"
				+ " d.rankstartdate,d.rankenddate,d.rankchangereason,d.rankContent,d.rankexamine,s.firstauditor,s.secondauditor,"
				+ " case when s.receiptsstatus='P' then '待审'"
				+ " when s.receiptsstatus='F' then '部门主管审核通过'"
				+ " when s.receiptsstatus='S' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='A' then '总经理审核通过'"
				+ " when s.receiptsstatus='R' then '驳回作废'"
				+ " when s.receiptsstatus='B' then '撤销' end,"
				+ " s.accessory"
				+ " from dtpublicreceiptschild d"
				+ " left join dtPayScale oldLevel on oldLevel.payScaleID = d.rankOldlevel "
				+ " left join dtPayScale newLevel on newLevel.payScaleID = d.rankShenzhigrade "
				+ " left join dtpublicreceipts s on s.prid=d.prid"
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization cr on  cr.organizationid = s.principalorganizationid";
		sql += " where s.type=?";
		parms.add("员工级别变更单");
		sql += " and s.companyID=?";
		parms.add(companyID);
		sql += " and s.organizationID=?";
		parms.add(organizationID);

		sql += " and s.operator=?";
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		parms.add(sta.getStaffName());
		if (search != null && search.equals("search")) {
			publicreceipts = (Publicreceipts) session.get("publicreceiptRank");

			if (publicreceipts.getVoucherNum() != null
					&& !"".equals(publicreceipts.getVoucherNum().trim())) {
				sql += " and s.voucherNum like ?";
				parms.add("%" + publicreceipts.getVoucherNum().trim() + "%");
			}

			if (!"".equals(publicreceipts.getPrincipal().trim())
					&& publicreceipts.getPrincipal() != null) {
				sql += " and s.principal like ?";
				parms.add("%" + publicreceipts.getPrincipal().trim() + "%");
			}
			if (publicreceipts.getPrincipalOrganizationID() != null
					&& !publicreceipts.getPrincipalOrganizationID().equals("")) {
				sql += " and s.principalOrganizationID=?";
				parms.add(publicreceipts.getPrincipalOrganizationID());
			}
			if (publicreceipts.getReceiptsStatus() != null
					&& !publicreceipts.getReceiptsStatus().equals("")) {
				sql += " and s.receiptsStatus = ? ";
				parms.add(publicreceipts.getReceiptsStatus());
			}
		}
		sql += " order by s.applyDate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 员工级别变更单——保存\修改
	 */
	public String saveRankPublicreceipts() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		publicreceipts.setPrID(serverService.getServerID("publicreceipts"));
		parameter = "添加员工级别变更单(凭证号:" + publicreceipts.getVoucherNum() + ")";
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/publicreceipts/rank/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			publicreceipts.setAccessory(photoPath);
		}
		publicreceipts.setOrganizationID(organizationID);
		publicreceipts.setCompanyID(account.getCompanyID());
		Company company1 = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { account
						.getCompanyID() });
		publicreceipts.setPcompanyID(company1.getCompanyPID());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		publicreceipts.setOperator(sta.getStaffName());
		publicreceipts.setType("员工级别变更单");

		publicreceipts.setReceiptsStatus("P");
		publicreceiptsChild.setOrID(serverService
				.getServerID("publicreceiptsChild"));
		publicreceiptsChild.setPrID(publicreceipts.getPrID());
		if (publicreceiptsChild.getRankNewScale() != null
				&& !publicreceiptsChild.getRankNewScale().equals("")) {

			String[] a = publicreceiptsChild.getRankNewScale().split("@");
			publicreceiptsChild.setRankShenzhigrade(a[0]);
			publicreceiptsChild.setRankNewScale(a[1]);

		}
		Calendar c = Calendar.getInstance();
		c.setTime(Utilities.getDateFromString(publicreceiptsChild
				.getRankEffectivedate(), "yy-MM-dd"));
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		publicreceiptsChild.setRankEnddate(Utilities.getDateString(c.getTime(),
				"yyyy-MM-dd"));
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(publicreceiptsChild);
		beans.add(publicreceipts);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 员工级别变更单——查看
	 * 
	 * @return
	 */
	public String toRank() {
		toSeePrint();
		return "rankedit";
	}

	/**
	 * 员工级别变更单——打印
	 * 
	 * @return
	 */
	public String toprintRank() {
		toSeePrint();
		return "printrank";
	}

	/**
	 * 员工级别变更单——导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showRankExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getRankList(session, account, organizationID);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1, sql
						.indexOf("s.accessory"));
		sql1 = sql1.replace("end,", "end");
		sql1 += sql.substring(sql.indexOf("from") - 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(PublicreceiptsChild
				.columnRankHeadings(), baseBeanService
				.getListBeanBySqlAndParams(sql1, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出员工级别变更单", account);
		baseBeanService.update(logBook);
		return "showrankexcel";
	}

	/**
	 * 员工级别变更单——删除
	 * 
	 * @return
	 */
	/*
	 * public String delRankPublicreceipts(){ Map<String, Object> session =
	 * ActionContext.getContext().getSession(); CAccount account = (CAccount)
	 * session.get("account"); String organizationID=(String)
	 * session.get("organizationID"); List<Object[]>paramss=new ArrayList<Object[]>();
	 * Object[] params1 = {account.getCompanyID(),publicreceipts.getPrID()};
	 * Object[] params2={publicreceipts.getPrID()}; paramss.add(params2);
	 * paramss.add(params1); String hql2="from Publicreceipts where companyID=?
	 * and prID=?"; Publicreceipts ot=(Publicreceipts)
	 * baseBeanService.getBeanByHqlAndParams(hql2, params1); CLogBook
	 * logbook=logBookService.saveCLogBook(organizationID,
	 * "删除员工级别变更单(凭证号:"+ot.getVoucherNum()+")", account); String dhql="delete
	 * from PublicreceiptsChild where prID=?"; String dhql2="delete from
	 * Publicreceipts where companyID=? and prID=?"; String[] hql={dhql,dhql2};
	 * List<BaseBean> beans=new ArrayList<BaseBean>(); beans.add(logbook);
	 * baseBeanService.executeHqlsByParamsList(beans,hql,paramss); return
	 * "success"; }
	 */

	/** *****************************************员工加班申请******************************************** */
	/**
	 * 员工加班申请表——根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getPublicreceipts()
	 */
	public String toOverSearch() {
		ActionContext.getContext().getSession().put("publicreceiptOver",
				publicreceipts);
		return getOverPublicreceipts();
	}

	/**
	 * 员工加班申请表——列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getOverPublicreceipts() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String companyID = account.getCompanyID();
		String orgID = session.get("organizationID").toString();
		String ohql = "from COrganization where companyID=? and Status = ?";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				new Object[] { companyID, "00" });
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { companyID });
		List<Object> list = getOverList(session, account, orgID);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "overlist";
	}

	/**
	 * 员工加班申请表——查询列表（可根据条件查询）被调用
	 * 
	 * @return result
	 */
	private List<Object> getOverList(Map<String, Object> session,
			CAccount account, String organizationID) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select s.prid, c.companyname,cr.organizationname, s.vouchernum,s.principal,"
				+ " s.type,s.applydate,s.operator,d.overtimepostname,d.overtimesort,"
				+ " d.overtimestartdate,d.overtimeenddate,d.overtimedays,d.overtimehour,d.overtimeWages,"
				+ " d.overTimeReason,d.overtimecontent,s.firstauditor,s.secondauditor,"
				+ " case when s.receiptsstatus='P' then '待审'"
				+ " when s.receiptsstatus='F' then '部门主管审核通过'"
				+ " when s.receiptsstatus='S' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='A' then '总经理审核通过'"
				+ " when s.receiptsstatus='R' then '驳回作废'"
				+ " when s.receiptsstatus='B' then '撤销' end,"
				+ " s.accessory"
				+ " from dtpublicreceiptschild d"
				+ " left join dtpublicreceipts s on s.prid=d.prid"
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization cr on  cr.organizationid = s.principalorganizationid";
		sql += " where 1=1";

		sql += " and s.type=?";
		parms.add("员工加班申请单");
		sql += " and s.companyID=?";
		parms.add(companyID);
		sql += " and s.organizationID=?";
		parms.add(organizationID);

		sql += " and s.operator=?";
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		parms.add(sta.getStaffName());

		if (search != null && search.equals("search")) {
			publicreceipts = (Publicreceipts) session.get("publicreceiptOver");
			if (publicreceipts.getReceiptsStatus() != null
					&& !publicreceipts.getReceiptsStatus().equals("")) {
				sql += " and s.receiptsStatus = ? ";
				parms.add(publicreceipts.getReceiptsStatus());
			}
			if (!publicreceipts.getPrincipal().trim().equals("")
					&& publicreceipts.getPrincipal() != null) {
				sql += " and s.principal like ?";
				parms.add("%" + publicreceipts.getPrincipal().trim() + "%");
			}
			if (publicreceipts.getPrincipalOrganizationID() != null
					&& !publicreceipts.getPrincipalOrganizationID().equals("")) {
				sql += " and s.principalOrganizationID=?";
				parms.add(publicreceipts.getPrincipalOrganizationID());
			}
			if (publicreceipts.getVoucherNum() != null
					&& !"".equals(publicreceipts.getVoucherNum().trim())) {
				sql += " and s.voucherNum like ?";
				parms.add("%" + publicreceipts.getVoucherNum().trim() + "%");
			}
		}
		sql += " order by s.applyDate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 员工加班申请表——保存
	 */
	public String saveOverPublicreceipts() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/publicreceipts/over/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			publicreceipts.setAccessory(photoPath);
		}
		publicreceipts.setPrID(serverService.getServerID("publicreceipts"));
		parameter = "添加员工加班申请单(凭证号:" + publicreceipts.getVoucherNum() + ")";
		publicreceipts.setOrganizationID(organizationID);
		publicreceipts.setPrincipalOrganizationID(organizationID);
		publicreceipts.setCompanyID(account.getCompanyID());
		Company company1 = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { account
						.getCompanyID() });
		publicreceipts.setPcompanyID(company1.getCompanyPID());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		String sqlorg = "select d.postname"
				+ " from dtcos t"
				+ " left join dtcorganization c on t.organizationid = c.organizationid"
				+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
				+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
			Object orgpost = baseBeanService.getObjectBySqlAndParams(sqlorg, new Object[] {
					account.getCompanyID(),account.getStaffID()});
			publicreceiptsChild.setOverTimePostName(orgpost.toString());
		publicreceipts.setOperator(sta.getStaffName());
		publicreceipts.setApplyDate(Utilities.getDateString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		publicreceipts.setType("员工加班申请单");
		publicreceipts.setReceiptsStatus("P");
		publicreceipts.setPrincipal(sta.getStaffName());
		publicreceiptsChild.setOrID(serverService
				.getServerID("publicreceiptsChild"));
		publicreceiptsChild.setPrID(publicreceipts.getPrID());
		publicreceiptsChild.setOverTimePostName(sta.getStaffPost());
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(publicreceiptsChild);
		beans.add(publicreceipts);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 员工加班申请表——查看
	 * 
	 * @return
	 */
	public String toOver() {
		toSeePrint();
		return "overedit";
	}

	/**
	 * 员工加班申请表——打印
	 * 
	 * @return
	 */
	public String toprintOver() {
		toSeePrint();
		return "printover";
	}

	/**
	 * 员工加班申请表——导出
	 */
	@SuppressWarnings("unchecked")
	public String showOverExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getOverList(session, account, organizationID);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1, sql
						.indexOf("s.accessory"));
		sql1 = sql1.replace("end,", "end");
		sql1 += sql.substring(sql.indexOf("from") - 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(PublicreceiptsChild
				.columnOverHeadings(), baseBeanService
				.getListBeanBySqlAndParams(sql1, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出员工加班申请单", account);
		baseBeanService.update(logBook);
		return "showrankexcel";
	}

	/** ***********************************员工请假管理************************************************** */
	/**
	 * 页面获取责任人请假(事假、病假)天数
	 */
	@SuppressWarnings("unchecked")
	public String getLeaveDays() {
		String sql = "select t.leavetype,sum(t.leavedays) from dtpublicreceiptschild t where exists "
				+ " (select s.prid from dtpublicreceipts s where t.prid = s.prid and s.type = '员工请假申请单' "
				+ " and s.applydate between to_char(last_day(add_months(sysdate,-1))+1, 'yyyy-mm-dd') "
				+ " and to_char(sysdate+1,'yyyy-mm-dd') and s.receiptsStatus = 'S' and s.principalID = ?) "
				+ " group by t.leavetype ";

		List<BaseBean> leaveDaysList = baseBeanService
				.getListBeanBySqlAndParams(sql, new Object[] { StaffIDvalue });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leaveDaysList", leaveDaysList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 根据条件查询请假单
	 */
	public String toLeaveSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", publicreceipts);
		return getLeaveBillsList();
	}

	/**
	 * 请假单列表
	 */
	public String getLeaveBillsList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String orgID = session.get("organizationID").toString();
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String companyID = account.getCompanyID();
		Object[] params1 = { companyID };
		String ohql = "from COrganization where companyID=? and Status = '00'";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", params1);
		List<Object> list = LeaveList(session, account, orgID);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "leaveBillslist";
	}

	/**
	 * 员工请假单——查询列表（可根据条件查询）被调用
	 * 
	 * @return result
	 */
	private List<Object> LeaveList(Map<String, Object> session,
			CAccount account, String organizationID) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select s.prid,c.companyname,cr.organizationname as cccc,s.vouchernum,s.principal,"
				+ " s.type,s.applydate,s.operator,d.leavePostName,d.leaveDays,d.leaveHour,d.leavecasual,"
				+ " d.leavesick,d.checkdiscount,d.leaveStartDate,d.Signdate,d.Terminatedate,"
				+ " d.leaveEndDate,d.leaveReceiver, d.leaveType,d.leaveReason,s.firstauditor,s.secondauditor,"
				+ " case when s.receiptsstatus='P' then '待审'"
				+ " when s.receiptsstatus='F' then '部门主管审核通过'"
				+ " when s.receiptsstatus='S' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='A' then '总经理审核通过'"
				+ " when s.receiptsstatus='R' then '驳回作废'"
				+ " when s.receiptsstatus='B' then '撤销' end,"
				+ " s.accessory"
				+ " from dtpublicreceiptschild d"
				+ " left join dtpublicreceipts s on s.prid=d.prid"
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization cr on  cr.organizationid = s.principalorganizationid";
		sql += " where 1=1";

		sql += " and s.type=?";
		parms.add("员工请假申请单");
		sql += " and s.companyID=?";
		parms.add(companyID);
		sql += " and s.organizationID=?";
		parms.add(organizationID);

		sql += " and s.operator=?";
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		parms.add(sta.getStaffName());

		if (search != null && search.equals("search")) {
			publicreceipts = (Publicreceipts) session.get("tablesearch");
			if (publicreceipts.getReceiptsStatus() != null
					&& !publicreceipts.getReceiptsStatus().equals("")) {
				sql += " and s.receiptsStatus = ? ";
				parms.add(publicreceipts.getReceiptsStatus());
			}
			if (!publicreceipts.getPrincipal().trim().equals("")
					&& publicreceipts.getPrincipal() != null) {
				sql += " and s.principal like ?";
				parms.add("%" + publicreceipts.getPrincipal().trim() + "%");
			}
			if (!publicreceipts.getPrincipalOrganizationID().equals("")
					&& publicreceipts.getPrincipalOrganizationID() != null) {
				sql += " and s.principalOrganizationID=?";
				parms.add(publicreceipts.getPrincipalOrganizationID());
			}
			if (publicreceipts.getVoucherNum() != null
					&& !"".equals(publicreceipts.getVoucherNum().trim())) {
				sql += " and s.voucherNum like ?";
				parms.add("%" + publicreceipts.getVoucherNum().trim() + "%");
			}
		}
		sql += " order by s.applyDate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 请假单转审核页面查询
	 * 
	 * @return
	 */
	public String toLeave() {
		toSeePrint();
		return "leavedit";
	}

	/**
	 * 员工请假单保存
	 * 
	 * @return
	 */

	public String saveLeaveBills() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/publicreceipts/over/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			publicreceipts.setAccessory(photoPath);
		}
		publicreceipts.setPrID(serverService.getServerID("publicreceipts"));
		parameter = "添加员工请假申请单(凭证号:" + publicreceipts.getVoucherNum() + ")";
		publicreceipts.setOrganizationID(organizationID);
		publicreceipts.setCompanyID(account.getCompanyID());
		Company company1 = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { account
						.getCompanyID() });
		publicreceipts.setPcompanyID(company1.getCompanyPID());
		publicreceipts.setType("员工请假申请单");
		
		publicreceipts.setReceiptsStatus("P");
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		publicreceipts.setOperator(sta.getStaffName());
		publicreceipts.setPrincipal(sta.getStaffName());
		publicreceipts.setPrincipalOrganizationID(organizationID);
		publicreceipts.setVoucherNum(publicreceipts.getVoucherNum());
		publicreceiptsChild.setOrID(serverService
				.getServerID("publicreceiptsChild"));
		publicreceiptsChild.setPrID(publicreceipts.getPrID());
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(publicreceiptsChild);
		beans.add(publicreceipts);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 员工请假单导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showLeaveExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = LeaveList(session, account, organizationID);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1, sql
						.indexOf("s.accessory"));
		sql1 = sql1.replace("end,", "end");
		sql1 += sql.substring(sql.indexOf("from") - 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(PublicreceiptsChild
				.columnLeaveHeadings(), baseBeanService
				.getListBeanBySqlAndParams(sql1, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出员工请假单", account);
		baseBeanService.update(logBook);
		return "showleaveexcel";
	}

	/**
	 * 打印员工请假单
	 * 
	 * @return
	 */

	public String printLeaveBill() {
		toSeePrint();
		return "printLeaveBill";
	}

	/** ****************************************************奖励单/扣分单************************************************* */
	/**
	 * 页面获取制单人
	 */
	public String getOperator() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hqlForMan = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staff", staff.getStaffName());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 页面获金额大写
	 */
	public String getMoneyToUp() {
		String money = Utilities.digitUppercase(Double
				.parseDouble(publicreceiptsChild.getRorpMoney()));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("money", money);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 奖励扣分单列表
	 */
	public String getListRewardPunishment() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String orgID = session.get("organizationID").toString();
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String companyID = account.getCompanyID();
		String ohql = "from COrganization where companyID=? and Status = ?";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				new Object[] { companyID, "00" });
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { companyID });
		List<Object> list = getRewardPunishment(session, account, orgID,type);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);

		return "rewardPunishment";
	}

	/**
	 * 返回奖励扣分单sql与参数
	 * 
	 * @return
	 */
	private List<Object> getRewardPunishment(Map<String, Object> session,
			CAccount account, String organizationID,String type) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> result = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select s.prid,c.companyname,co.organizationname,s.vouchernum,s.principal,cr.organizationname as cccc,"
				+ " s.type,s.applydate,s.operator,d.rorpDeductPoint,d.rorpDate,"
				+ " d.rorpMyriad,d.rorpReason,s.firstauditor,s.secondauditor,s.finalauditor,"
				+ " case when s.receiptsstatus='p' then '待审'"
				+ " when s.receiptsstatus='f' then '部门主管审核通过'"
				+ " when s.receiptsstatus='s' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='a' then '总经理审核通过'"
				+ " when s.receiptsstatus='r' then '驳回作废'"
				+ " when s.receiptsstatus='b' then '撤销' end," + " s.accessory";
		sql += " from dtpublicreceiptschild d"
				+ " left join dtpublicreceipts s on s.prid=d.prid"
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization co on s.principalOrganizationID=co.organizationid"
				+ " left join dtcorganization cr on  cr.organizationid = s.principalorganizationid"
				+ " where (s.type='奖励单' or s.type='扣分单')";
		
         
		
		if(type!=null&&!type.equals("")){
			if(type.equals("c")){
				sql += " and s.companyID=?";
				parms.add(companyID);
			}else if(type.equals("g")){
				sql += "  and s.companyID in(select companyID from dtCompany where companyPID = ? or companyID = ?)";
				parms.add(companyID);
				parms.add(companyID);
			}else{
				sql += " and s.companyID=?";
				parms.add(companyID);
				sql += " and s.organizationID=?";
				parms.add(organizationID);

				sql += " and s.operator=?";
				String hqlForMan = "from Staff where staffID = ?";
				Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
						new Object[] { account.getStaffID() });
				parms.add(sta.getStaffName());
			}
		}
		if (search != null && search.equals("search")) {
			publicreceipts = (Publicreceipts) session.get("tablesearchPub");
			if (publicreceipts.getType() != null
					&& !publicreceipts.getType().equals("")) {
				sql += " and s.type = ? ";
				parms.add(publicreceipts.getType());
			}

			if (publicreceipts.getReceiptsStatus() != null
					&& !publicreceipts.getReceiptsStatus().equals("")) {
				sql += " and s.receiptsStatus = ? ";
				parms.add(publicreceipts.getReceiptsStatus());
			}
			if (!publicreceipts.getPrincipal().trim().equals("")
					&& publicreceipts.getPrincipal() != null) {
				sql += " and s.principal like ?";
				parms.add("%" + publicreceipts.getPrincipal().trim() + "%");
			}
			if (publicreceipts.getVoucherNum() != null
					&& !"".equals(publicreceipts.getVoucherNum().trim())) {
				sql += " and s.voucherNum like ?";
				parms.add("%" + publicreceipts.getVoucherNum().trim() + "%");
			}
			
			if(publicreceipts.getCompanyID()!=null&&!publicreceipts.getCompanyID().equals("")){
				sql += " and s.companyID=?";
				parms.add(publicreceipts.getCompanyID());
			}
			
			if(publicreceipts.getPrincipalOrganizationID()!=null&&!publicreceipts.getPrincipalOrganizationID().equals("")&&!publicreceipts.getPrincipalOrganizationID().startsWith("c")){
				sql += " and s.principalorganizationid=?";
				parms.add(publicreceipts.getPrincipalOrganizationID());
			}
			
		}
		sql += " order by s.applyDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 保存奖励扣分单
	 * 
	 * @return
	 */
	public String saveRewardPunishment() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/staffrank/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			publicreceipts.setAccessory(photoPath);
		}
		publicreceipts.setPrID(serverService.getServerID("publicreceipts"));
		parameter = "添加员工奖罚申请单(凭证号:" + publicreceipts.getVoucherNum() + ")";
		publicreceipts.setOrganizationID(organizationID);
		publicreceipts.setCompanyID(account.getCompanyID());
		Company company1 = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { account
						.getCompanyID() });
		publicreceipts.setPcompanyID(company1.getCompanyPID());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		publicreceipts.setOperator(sta.getStaffName());
		publicreceipts.setApplyDate(Utilities.getDateString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		publicreceipts.setReceiptsStatus("P");
		publicreceiptsChild.setOrID(serverService
				.getServerID("publicreceiptsChild"));
		publicreceiptsChild.setPrID(publicreceipts.getPrID());
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(publicreceiptsChild);
		beans.add(publicreceipts);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 导出奖励扣分单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getRewardPunishment(session, account,
				organizationID,type);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1, sql
						.indexOf("s.accessory"));
		sql1 = sql1.replace("end,", "end");
		sql1 += sql.substring(sql.indexOf("from") - 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(PublicreceiptsChild
				.columnRewardPunishment(), baseBeanService
				.getListBeanBySqlAndParams(sql1, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出奖罚单",
				account);
		baseBeanService.update(logBook);

		return "showrewardexcel";

	}

	/**
	 * 奖励扣分单查询
	 * 
	 * @return
	 */
	public String toSearchReward() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearchPub", publicreceipts);
		return getListRewardPunishment();
	}

	/**
	 * 奖励扣分单查看
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String editReward() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		String sql = "select s.prid,c.companyname,co.organizationname,s.vouchernum,s.principal,cr.organizationname as cccc,"
				+ " s.type,s.applydate,s.operator,d.rorpDeductPoint,d.rorpDate,"
				+ " d.rorpMyriad,d.rorpReason,s.firstauditor,s.secondauditor,s.finalauditor,"
				+ " case when s.receiptsstatus='p' then '待审'"
				+ " when s.receiptsstatus='f' then '部门主管审核通过'"
				+ " when s.receiptsstatus='s' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='a' then '总经理审核通过'"
				+ " when s.receiptsstatus='r' then '驳回作废'"
				+ " when s.receiptsstatus='b' then '撤销' end," + " s.accessory";
		sql += " from dtpublicreceiptschild d"
				+ " left join dtpublicreceipts s on s.prid=d.prid"
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization co on s.principalOrganizationID=co.organizationid"
				+ " left join dtcorganization cr on  cr.organizationid = s.principalorganizationid"
				+ " where 1=1  and s.prID=? ";
		Object[] params = {prID };
		listGamJeom = baseBeanService.getListBeanBySqlAndParams(sql, params);
		return "editRew";
	}

	/**
	 * 奖励扣分单打印
	 * 
	 * @return
	 */
	public String printReward() {
		editReward();
		return "printRew";
	}

	/** ****************************************人事二级审核***************************************** */
	/**
	 * 奖励扣分单审核列表
	 */
	public String getListPublicreceipts() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String orgID = session.get("organizationID").toString();
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String companyID = account.getCompanyID();
		String ohql = "from COrganization where companyID=? and Status = ?";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				new Object[] { companyID, "00" });
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { companyID });
		List<Object> list = getPublicreceiptsSqlAndParms(session, account,
				orgID);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "auditPunishment";
	}

	/**
	 * 返回单据审核sql与参数
	 * 
	 * @return
	 */
	private List<Object> getPublicreceiptsSqlAndParms(
			Map<String, Object> session, CAccount account, String organizationID) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> result = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select s.prid,c.companyname,co.organizationname,s.vouchernum,s.principal,"
				+ " s.type,s.applydate,s.operator,s.firstauditor,s.secondauditor,s.finalauditor,"
				+ " case when s.receiptsstatus='p' then '待审'"
				+ " when s.receiptsstatus='f' then '部门主管审核通过'"
				+ " when s.receiptsstatus='s' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='a' then '总经理审核通过'"
				+ " when s.receiptsstatus='r' then '驳回作废'"
				+ " when s.receiptsstatus='b' then '撤销' end," + " s.accessory";
		sql += " from dtpublicreceipts s "
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization co on co.organizationid=s.principalOrganizationID"
				+ " where 1=1 ";
		sql += " and s.companyID=?";
		parms.add(companyID);
		sql += " and s.organizationID=?";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			publicreceipts = (Publicreceipts) session.get("tablesearchFPub");
			if (!publicreceipts.getVoucherNum().trim().equals("")
					&& publicreceipts.getVoucherNum() != null) {
				sql += " and s.vouchernum like ?";
				parms.add("%" + publicreceipts.getVoucherNum().trim() + "%");
			}
			if (!publicreceipts.getPrincipal().trim().equals("")
					&& publicreceipts.getPrincipal() != null) {
				sql += " and s.principal like ?";
				parms.add("%" + publicreceipts.getPrincipal().trim() + "%");
			}
			if (!publicreceipts.getOperator().trim().equals("")
					&& publicreceipts.getOperator() != null) {
				sql += " and s.operator like ?";
				parms.add("%" + publicreceipts.getOperator().trim() + "%");
			}
			if (publicreceipts.getPrincipalOrganizationID() != null
					&& !publicreceipts.getPrincipalOrganizationID().equals("")) {
				sql += " and s.principalOrganizationID=?";
				parms.add(publicreceipts.getPrincipalOrganizationID());
			}
			if (publicreceipts.getReceiptsStatus() != null
					&& !publicreceipts.getReceiptsStatus().equals("")) {
				sql += " and s.receiptsstatus=?";
				parms.add(publicreceipts.getReceiptsStatus());
			}
			if (publicreceipts.getType() != null
					&& !publicreceipts.getType().equals("")) {
				sql += " and s.type=?";
				parms.add(publicreceipts.getType());
			}
		} else {
			if (labelTag.equals("00")) {
				sql += " and s.receiptsstatus = ?";
				parms.add("P");
			}
			if (labelTag.equals("01")) {
				sql += " and s.receiptsstatus = ?";
				parms.add("F");
			}
		}
		sql += " order by s.applyDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;

	}

	/**
	 * 奖励扣分单审核导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ShowExcelPublic() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getPublicreceiptsSqlAndParms(session, account,
				organizationID);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1, sql
						.indexOf("s.accessory"));
		sql1 = sql1.replace("end,", "end");
		sql1 += sql.substring(sql.indexOf("from") - 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(Publicreceipts
				.columnPublicreceipt(), baseBeanService
				.getListBeanBySqlAndParams(sql1, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出人事审核单据", account);
		baseBeanService.update(logBook);

		return "showrewardexcel";

	}

	/**
	 * 奖励扣分单审核查询
	 * 
	 * @return
	 */
	public String toSearchPublicreceipts() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearchFPub", publicreceipts);
		return getListPublicreceipts();
	}

	/**
	 * 人事单据审核通过与驳回操作
	 * 
	 * @return
	 */
	/**
	 * 加班奖励得分
	 */
	private String overtimeWages;

	/**
	 * 本次考勤折扣分数
	 */
	private String checkdiscount;

	@SuppressWarnings("deprecation")
	public String AjaxPublicreceiptsAudit() {
		String[] hql_forDel = null;
		List<Object[]> pars = null;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String hql_ForStaff = "from Staff where staffID = ? ";
		Staff entity = (Staff) baseBeanService.getBeanByHqlAndParams(
				hql_ForStaff, new Object[] { account.getStaffID() });

		String hql = " from Publicreceipts s where s.companyID=? and s.organizationID=? and s.prID=?";
		String organizationID = (String) session.get("organizationID");
		Publicreceipts publicrece = (Publicreceipts) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] {
						account.getCompanyID(), organizationID, prID });
		String hql1 = " from PublicreceiptsChild sd where  sd.prID=?";
		PublicreceiptsChild childEntity = (PublicreceiptsChild) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] { prID });
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (types.equals("主管审核通过")) {
			publicrece.setReceiptsStatus("F");
			publicrece.setFirstAuditor(entity.getStaffName());
		} else if (types.equals("主管驳回作废")) {
			publicrece.setReceiptsStatus("R");
			publicrece.setFirstAuditor(entity.getStaffName());
		} else if (types.equals("人事审核通过")) {
			publicrece.setReceiptsStatus("S");
			publicrece.setSecondAuditor(entity.getStaffName());
			LogBook log = null;
			if (publicrece.getType().equals("员工加班申请单") && overtimeWages != null
					&& !"".equals(overtimeWages)) {
				childEntity.setOvertimeWages(overtimeWages);
				log = GenerateLog(publicrece, childEntity);
				beans.add(log);
				MonthSalary mon = null;
				mon = updateMonthSalary(publicrece,log);
				beans.add(mon);
			}
			if (publicrece.getType().equals("奖励单")
					|| publicrece.getType().equals("扣分单")) {
				log = GenerateLog(publicrece, childEntity);
				beans.add(log);
				MonthSalary mon = null;
				mon = updateMonthSalary(publicrece,log);
				beans.add(mon);
			}
			if (publicrece.getType().equals("员工请假申请单") && checkdiscount != null
					&& !"".equals(checkdiscount)) {
				childEntity.setCheckdiscount(checkdiscount);
				log = GenerateLog(publicrece, childEntity);
				beans.add(log);
				MonthSalary mon = null;
				mon = updateMonthSalary(publicrece,log);
				beans.add(mon);
			}
			if (publicrece.getType().equals("员工级别变更单")) {
				//级别生效日期
				Date date2 = null;
				date2 = Utilities.getDateFromString(childEntity
						.getRankEffectivedate(), "yyyy-MM-dd");
				
				// 删除历史中待升级的员工级别 start
				hql_forDel = new String[] { "delete PSHistory where companyID = ? and staffID = ? and status = ? " };
				pars = new ArrayList<Object[]>();
				Object[] obj = new Object[] { publicrece.getCompanyID(),
						publicrece.getPrincipalID(), "03" };
				pars.add(obj);
				// ////end
				// 当前月份小于级别生效月份
				if (new Date().compareTo(date2)<0) {
					PSHistory PSHistoryNew = new PSHistory();
					PSHistoryNew.setPshID(serverService
							.getServerID("PSHistory"));
					PSHistoryNew.setCompanyID(publicrece.getCompanyID());
					PSHistoryNew.setStaffID(publicrece.getPrincipalID());
					PSHistoryNew.setPayScaleID(childEntity
							.getRankShenzhigrade());
					PSHistoryNew.setApplyDate(new Date());
					Date date1 = null;
					date1 = Utilities.getDateFromString(childEntity
							.getRankEffectivedate(), "yyyy-MM-dd");
					PSHistoryNew.setEffectiveDate(date1);
					PSHistoryNew.setStatus("03");
					beans.add(PSHistoryNew);

				} else {
					String hql_pshistory = "from PSHistory where companyID = ? and staffID = ? and payScaleID= ? and status = ? ";
					PSHistory PSHistoryHtr = (PSHistory) baseBeanService
							.getBeanByHqlAndParams(hql_pshistory, new Object[] {
									publicrece.getCompanyID(),
									publicrece.getPrincipalID(),
									childEntity.getRankOldlevel(), "01" });
					if (PSHistoryHtr != null) {
						PSHistoryHtr.setStatus("02");
						beans.add(PSHistoryHtr);
					}
					PSHistory PSHistoryNew = new PSHistory();
					PSHistoryNew.setPshID(serverService
							.getServerID("PSHistory"));
					PSHistoryNew.setCompanyID(publicrece.getCompanyID());
					PSHistoryNew.setStaffID(publicrece.getPrincipalID());
					PSHistoryNew.setPayScaleID(childEntity
							.getRankShenzhigrade());
					PSHistoryNew.setApplyDate(new Date());
					Date date1 = null;
					date1 = Utilities.getDateFromString(childEntity
							.getRankEffectivedate(), "yyyy-MM-dd");
					PSHistoryNew.setEffectiveDate(date1);
					PSHistoryNew.setStatus("01");
					beans.add(PSHistoryNew);
					
					String hqlCsp = " from CSP cs where cs.companyID=? and cs.staffID=?";
					CSP csp = (CSP) baseBeanService.getBeanByHqlAndParams(
							hqlCsp, new Object[] { publicrece.getCompanyID(),
									publicrece.getPrincipalID() });
					if(csp == null){
						csp = new CSP();
						csp.setCompanyID(account.getCompanyID());
						csp.setCspID(serverService.getServerID("csp"));
						csp.setStaffID(publicrece.getPrincipalID());
					}
					csp.setPayScaleID(childEntity.getRankShenzhigrade());
					beans.add(csp);
				}
			}
			if (!"员工级别变更单".equals(publicrece.getType())) {
				parameter = "添加工作日志(时间:"
						+ Utilities.getDateString(new Date(), "yyyy-MM-dd")
						+ ")";
				parameter += "(人员名称:" + publicrece.getPrincipal() + ")(操作时间"
						+ new Date().toLocaleString() + ")";
				CLogBook cLogBook = logBookService.saveCLogBook(publicrece
						.getPrincipalOrganizationID(), parameter, account);
				beans.add(cLogBook);
			}
		} else if (types.equals("人事驳回作废")) {
			publicrece.setReceiptsStatus("R");
			publicrece.setSecondAuditor(entity.getStaffName());
		}
		beans.add(publicrece);
		beans.add(childEntity);
		baseBeanService.executeHqlsByParamsList(beans, hql_forDel, pars);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("publicrece", entity.getStaffName());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public LogBook GenerateLog(Publicreceipts pub, PublicreceiptsChild pubChild) {
		LogBook log = new LogBook();
		log.setLogBookID(serverService.getServerID("logbook"));
		log.setCompanyID(pub.getCompanyID());
		log.setOrganizationID(pub.getPrincipalOrganizationID());
		log.setStaffID(pub.getPrincipalID());
		String inputTime = Utilities.getDateString(new Date(),
				"yyyy-MM-dd HH:mm:ss");
		log.setInputDate(inputTime);
		if (pub.getType().equals("员工加班申请单")) {
			if (pubChild.getOverTimeSort().equals("工作日加班")) {
				log.setScoreSort("scode201202157awfwsxchm0000000003");
				log.setBisect(pubChild.getOvertimeWages());
				log.setTodaydate(Utilities.getDateFromString(pubChild
						.getOverTimeStartDate(), "yyyy-MM-dd"));
				log.setJobContent("加班" + pubChild.getOverTimeHour()
						+ "小时，加班单据凭证号：" + pub.getVoucherNum());
			}
			if (pubChild.getOverTimeSort().equals("休息日加班")) {
				log.setScoreSort("scode201007306kdf8m76me0000000004");
				log.setBisect(pubChild.getOvertimeWages());
				log.setTodaydate(Utilities.getDateFromString(pubChild
						.getOverTimeStartDate(), "yyyy-MM-dd"));
				log.setJobContent("从" + pubChild.getOverTimeStartDate() + "至"
						+ pubChild.getOverTimeEndDate() + "加班，加班单据凭证号："
						+ pub.getVoucherNum());
			}
			if (pubChild.getOverTimeSort().equals("法定假日加班")) {
				log.setScoreSort("scode201202157awfwsxchm0000000004");
				log.setBisect(pubChild.getOvertimeWages());
				log.setTodaydate(Utilities.getDateFromString(pubChild
						.getOverTimeStartDate(), "yyyy-MM-dd"));
				log.setJobContent("从" + pubChild.getOverTimeStartDate() + "至"
						+ pubChild.getOverTimeEndDate() + "加班，加班单据凭证号："
						+ pub.getVoucherNum());
			}

		}
		if (pub.getType().equals("员工请假申请单")) {
			log.setScoreSort("scode20100812ikdv89y6kt0000000036");
			log.setBisect(pubChild.getCheckdiscount());
			log.setTodaydate(Utilities.getDateFromString(pubChild
					.getLeaveStartDate(), "yyyy-MM-dd"));
			log.setJobContent("员工请假申请单凭证号：" + pub.getVoucherNum());
		}
		if (pub.getType().equals("奖励单")) {
			log.setScoreSort("scode20100812ikdv89y6kt0000000031");
			log.setBisect(pubChild.getRorpDeductPoint());
			log.setTodaydate(pubChild.getRorpDate());
			log.setJobContent("奖励单凭证号：" + pub.getVoucherNum());
		}
		if (pub.getType().equals("扣分单")) {
			log.setScoreSort("scode20100812ikdv89y6kt0000000034");
			log.setBisect(pubChild.getRorpDeductPoint());
			log.setTodaydate(pubChild.getRorpDate());
			log.setJobContent("扣分单凭证号：" + pub.getVoucherNum());
		}
		//String dateString = Utilities.getDateString(new Date(), "yyyy-MM-dd");
		//Date todaydate = Utilities.getDateFromString(dateString, "yyyy-MM-dd");
		/* log.setTodaydate(todaydate); */
		/*String sql = "select to_char(t.becomesdate,'yyyy-mm-dd') from dtcos c,dtaudition t where c.cosstatus='50' and c.status='01'"
				+ " and t.companyid=c.companyid and t.staffid=c.staffid and t.staffid=? and t.companyid=? ";
		List<BaseBean> auditionList = baseBeanService
				.getListBeanBySqlAndParams(sql, new Object[] {
						pub.getPrincipalID(), pub.getCompanyID() });
		String becomesdate = auditionList.toArray()[0].toString();
		Date date1 = Utilities.getDateFromString(becomesdate, "yyyy-MM-dd");
		int number1 = todaydate.compareTo(date1);
		if (number1 == -1) {
			log.setLogoStatus("00");// 转正的日志状态（入职前6个月的）
		} else {
			log.setLogoStatus("01");// 转正后的日志状态（入职后6个月的）
		}*/
		log.setStatus("00");

		return log;
	}
	
	/**
	 * 修改月工资
	 * @param pub
	 * @param log
	 * @return
	 */
	public MonthSalary updateMonthSalary(Publicreceipts pub,LogBook log){
		MonthSalary mon = new MonthSalary();
		String hql = "from MonthSalary where companyid = ? and staffid = ? and months = ? and status = ?";
		Object[] param = new Object[]{log.getCompanyID(),log.getStaffID(),
				Utilities.getDateString(log.getTodaydate(), "yyyy-MM"),"01"};
		mon = (MonthSalary)baseBeanService.getBeanByHqlAndParams(hql, param);
		if(mon != null){
			if (pub.getType().equals("员工加班申请单") || pub.getType().equals("奖励单")){
				if(log.getScoreSort().equals("scode201202157awfwsxchm0000000003")){ //工作日加班
					mon.setWorknightintegral(getscore(String.format("%.2f",Float.parseFloat(mon.getWorknightintegral()) 
							+ Float.parseFloat(log.getBisect()))));//晚上加班
					
				}
				else if(log.getScoreSort().equals("scode201007306kdf8m76me0000000004")){ //休息日加班
					mon.setWeekendintegral(getscore(String.format("%.2f",Float.parseFloat(mon.getWeekendintegral()) 
							+ Float.parseFloat(log.getBisect()))));//周末加班
				}
				else if(log.getScoreSort().equals("scode201202157awfwsxchm0000000004")){ //法定节假日加班
					mon.setWorkholidaysintegral(getscore(String.format("%.2f", Float.parseFloat(mon.getWorkholidaysintegral()) 
							+ Float.parseFloat(log.getBisect()))));//节假日加班
				}else{ //奖励单 
					mon.setRewardintegral(getscore(String.format("%.2f",Float.parseFloat(mon.getRewardintegral()) 
							+ Float.parseFloat(log.getBisect())))); //奖励得分
				}
				mon.setDueintegral(String.format("%.3f",Float.parseFloat(mon.getDueintegral()) + Float.parseFloat(log.getBisect()))); //应得积分
				mon.setObtainedintegral(String.format("%.3f",Float.parseFloat(mon.getObtainedintegral()) + Float.parseFloat(log.getBisect()))); //实得积分
			}else{
				if(log.getScoreSort().equals("scode20100812ikdv89y6kt0000000036")){ //请假单
					mon.setAttendancediscount(getscore(String.format("%.2f",Float.parseFloat(mon.getAttendancediscount()) 
							+ Float.parseFloat(log.getBisect())))); //考勤折扣
				}else{ //扣分单
					mon.setViolationdiscount(getscore(String.format("%.2f",Float.parseFloat(mon.getViolationdiscount()) 
							+ Float.parseFloat(log.getBisect())))); //违规折扣
				}
				mon.setObtainedintegral(String.format("%.3f", Float.parseFloat(mon.getObtainedintegral()) - Float.parseFloat(log.getBisect()))); //实得积分
			}
			mon.setObtainedmenoy(String.format("%.2f", Float.parseFloat(mon.getObtainedintegral())* 20)); //实得工资
		}
		return mon;
	}
	
	/**
	 * 获取精确分数
	 * @param str
	 * @return
	 */
	private String getscore(String str){
		if(str.substring(str.length()-1).equals("0")){
			if(str.substring(str.length()-2,str.length()-1).equals("0")){ //去除小数点后多余的00
				str = str.substring(0,str.indexOf("."));
			}else{ //去除小数点后多余的0
				str = str.substring(0,str.length()-1);
			}
		}
		return str;
	}

	/*
	 * 人事归档单据撤销
	 */
	public String toBackBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> hqls = null;
		List<Object[]> pars = null;
		CLogBook cLogBook = null;

		String phql = "from Publicreceipts where companyID = ? and organizationID = ? and prID = ?";
		Object[] params = { account.getCompanyID(), organizationID, prID };
		Publicreceipts publicreceipt = (Publicreceipts) baseBeanService
				.getBeanByHqlAndParams(phql, params);
		// 修改Publicreceipts表中单据状态
		publicreceipt.setReceiptsStatus("B");
		beans.add(publicreceipt);

		String hql = "from PublicreceiptsChild where prID = ?";
		PublicreceiptsChild publicreceiptChild = (PublicreceiptsChild) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { prID });

		hqls = new ArrayList<String>();
		pars = new ArrayList<Object[]>();
		cLogBook = new CLogBook();
		if (publicreceipts.getType().equals("员工级别变更单")) {
			String pshql = "from PSHistory where companyID = ? and staffID = ? and payScaleID = ?";
			Object[] pshpara = { account.getCompanyID(),
					publicreceipt.getPrincipalID(),
					publicreceiptChild.getRankShenzhigrade() };
			PSHistory pshistory = (PSHistory) baseBeanService
					.getBeanByHqlAndParams(pshql, pshpara);

			if (pshistory.getStatus().equals("03")) {
				// 删除状态为03(待生效级别)的数据
				String dpshql = "delete PSHistory where companyID = ? and staffID = ? and payScaleID = ? and status = ?";
				Object[] dprams = { account.getCompanyID(),
						publicreceipt.getPrincipalID(),
						publicreceiptChild.getRankShenzhigrade(), "03" };
				hqls.add(dpshql);
				pars.add(dprams);
			} else {
				// 修改CSP表中的职位
				String chql = "from CSP c where exists (select c.staffID from COS s "
						+ " where c.staffID = s.staffID and s.companyID =? and s.staffID =?)";
				Object[] param = { account.getCompanyID(),
						publicreceipt.getPrincipalID() };
				CSP csp = (CSP) baseBeanService.getBeanByHqlAndParams(chql,
						param);
				csp.setPayScaleID(publicreceiptChild.getRankOldlevel());
				beans.add(csp);

				// 删除状态为01的数据
				String dshql1 = "delete PSHistory where companyID = ? and staffID = ? and payScaleID = ? and status = ?";
				Object[] dshpara1 = { account.getCompanyID(),
						publicreceipt.getPrincipalID(),
						publicreceiptChild.getRankShenzhigrade(), "01" };
				hqls.add(dshql1);
				pars.add(dshpara1);

				// 查询原级别状态为02的数据
				String pshql2 = "from PSHistory where companyID = ? and staffID = ? and payScaleID = ? and status = ?";
				Object[] prams2 = { account.getCompanyID(),
						publicreceipt.getPrincipalID(),
						publicreceiptChild.getRankOldlevel(), "02" };
				PSHistory pshistory2 = (PSHistory) baseBeanService
						.getBeanByHqlAndParams(pshql2, prams2);
				// 把原级别数据状态为02的改为01
				if (pshistory2 != null) {
					pshistory2.setStatus("01");
					beans.add(pshistory2);
				}
			}
			parameter = "撤销员工级别变更单:(凭证号:" + publicreceipt.getVoucherNum() + ")";
			cLogBook = logBookService.saveCLogBook(publicreceipt
					.getPrincipalOrganizationID(), parameter, account);
			beans.add(cLogBook);
		} else {
			String monhql = "from MonthSalary where companyid = ? and staffid = ? and months = ? and status = ?";
			String month = "";
			if(publicreceiptChild.getOverTimeStartDate() != null 
					&& !"".equals(publicreceiptChild.getOverTimeStartDate())){ //加班单
				month = publicreceiptChild.getOverTimeStartDate().substring(0,7);
			}else if(publicreceiptChild.getLeaveStartDate() != null 
					&& !"".equals(publicreceiptChild.getLeaveStartDate())){ //请假单
				month = publicreceiptChild.getLeaveStartDate().substring(0,7);
			}else{ //奖罚单
				month = Utilities.getDateString(publicreceiptChild.getRorpDate(), "yyyy-MM");
			}
			Object[] para = new Object[]{account.getCompanyID(),publicreceipt.getPrincipalID(),month,"01"};
			MonthSalary mons = (MonthSalary)baseBeanService.getBeanByHqlAndParams(monhql, para);
			
			if (publicreceipts.getType().equals("员工加班申请单")) {
				parameter = "撤销员工加班申请单:(凭证号:" + publicreceipt.getVoucherNum()
						+ ")";
				cLogBook = logBookService.saveCLogBook(publicreceipt
						.getPrincipalOrganizationID(), parameter, account);
				beans.add(cLogBook);
				
				if(mons != null){
					if(publicreceiptChild.getOverTimeSort().equals("工作日加班")){
						mons.setWorknightintegral(getscore(String.format("%.2f",Float.parseFloat(mons.getWorknightintegral()) 
								- Float.parseFloat(publicreceiptChild.getOvertimeWages()))));//晚上加班
					}else if(publicreceiptChild.getOverTimeSort().equals("休息日加班")){
						mons.setWeekendintegral(getscore(String.format("%.2f",Float.parseFloat(mons.getWeekendintegral()) 
								- Float.parseFloat(publicreceiptChild.getOvertimeWages()))));//晚上加班
					}else{ //法定假日加班
						mons.setWorkholidaysintegral(getscore(String.format("%.2f",Float.parseFloat(mons.getWorkholidaysintegral()) 
								- Float.parseFloat(publicreceiptChild.getOvertimeWages()))));//晚上加班
						
					}
					mons.setDueintegral(String.format("%.3f", Float.parseFloat(mons.getDueintegral()) - Float.parseFloat(publicreceiptChild.getOvertimeWages()))); //应得积分
					mons.setObtainedintegral(String.format("%.3f", Float.parseFloat(mons.getObtainedintegral()) - Float.parseFloat(publicreceiptChild.getOvertimeWages()))); //实得积分
				}
			}
			if (publicreceipts.getType().equals("员工请假申请单")) {
				parameter = "撤销员工请假申请单:(凭证号:" + publicreceipt.getVoucherNum()
						+ ")";
				cLogBook = logBookService.saveCLogBook(publicreceipt
						.getPrincipalOrganizationID(), parameter, account);
				beans.add(cLogBook);
				
				if(mons != null){
					mons.setAttendancediscount(getscore(String.format("%.2f",Float.parseFloat(mons.getAttendancediscount()) 
							- Float.parseFloat(publicreceiptChild.getCheckdiscount())))); //考勤折扣
					mons.setObtainedintegral(String.format("%.3f", Float.parseFloat(mons.getObtainedintegral()) + Float.parseFloat(publicreceiptChild.getCheckdiscount()))); //实得积分
				}
			}
			if (publicreceipts.getType().equals("奖励单")) {
				parameter = "撤销奖励单:(凭证号:" + publicreceipt.getVoucherNum() + ")";
				cLogBook = logBookService.saveCLogBook(publicreceipt
						.getPrincipalOrganizationID(), parameter, account);
				beans.add(cLogBook);
				
				if(mons != null){
					mons.setRewardintegral(getscore(String.format("%.2f",Float.parseFloat(mons.getRewardintegral()) 
							- Float.parseFloat(publicreceiptChild.getRorpDeductPoint())))); //奖励得分
					mons.setDueintegral(String.format("%.3f", Float.parseFloat(mons.getDueintegral()) - Float.parseFloat(publicreceiptChild.getRorpDeductPoint()))); //应得积分
					mons.setObtainedintegral(String.format("%.3f", Float.parseFloat(mons.getObtainedintegral()) - Float.parseFloat(publicreceiptChild.getRorpDeductPoint()))); //实得积分
				}
			}
			if (publicreceipts.getType().equals("扣分单")) {
				parameter = "撤销扣分单:(凭证号:" + publicreceipt.getVoucherNum() + ")";
				cLogBook = logBookService.saveCLogBook(publicreceipt
						.getPrincipalOrganizationID(), parameter, account);
				beans.add(cLogBook);
				
				if(mons != null){
					mons.setViolationdiscount(getscore(String.format("%.2f",Float.parseFloat(mons.getViolationdiscount()) 
							- Float.parseFloat(publicreceiptChild.getRorpDeductPoint())))); //违规折扣
					mons.setObtainedintegral(String.format("%.3f", Float.parseFloat(mons.getObtainedintegral()) + Float.parseFloat(publicreceiptChild.getRorpDeductPoint()))); //实得积分
				}
			}
			if(mons != null){
				mons.setObtainedmenoy(String.format("%.2f", Float.parseFloat(mons.getObtainedintegral())* 20)); //实得工资
				beans.add(mons);
			}
			String dlochql = "delete LogBook where companyID = ? and organizationID = ? and staffID = ? and jobContent like ?";
			Object[] locpara = { account.getCompanyID(),
					publicreceipt.getPrincipalOrganizationID(),
					publicreceipt.getPrincipalID(),
					"%" + publicreceipt.getVoucherNum() + "%" };
			hqls.add(dlochql);
			pars.add(locpara);
		}
		baseBeanService.executeHqlsByParamsList(beans, hqls
				.toArray(new String[0]), pars);
		return getListPublicreceipts();
	}
	
	
	
	/**
	 * 根据当前公司获取其子公司
	 * 
	 * @return
	 */
	public String getAllCompanyByCurrent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = account.getCompanyID();

		String hql = " from Company where (companyPID = ? or companyID = ?) and companyStatus = ?";
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { companyID,companyID, "00" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companylist", companylist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	
	// JSON取得部门列表
	public String getoList() {
		Map<String, Object>  session=ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		HttpServletRequest  request=ServletActionContext.getRequest();
		String companyID = request.getParameter("companyID");
		String ohql = "from COrganization where companyID=? and Status = '00' order by organizationNumber";
		
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				new Object[]{companyID});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationlist", organizationlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public Publicreceipts getPublicreceipts() {
		return publicreceipts;
	}

	public void setPublicreceipts(Publicreceipts publicreceipts) {
		this.publicreceipts = publicreceipts;
	}

	public PublicreceiptsChild getPublicreceiptsChild() {
		return publicreceiptsChild;
	}

	public void setPublicreceiptsChild(PublicreceiptsChild publicreceiptsChild) {
		this.publicreceiptsChild = publicreceiptsChild;
	}

	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStaffIDvalue() {
		return StaffIDvalue;
	}

	public void setStaffIDvalue(String staffIDvalue) {
		StaffIDvalue = staffIDvalue;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPrID() {
		return prID;
	}

	public void setPrID(String prID) {
		this.prID = prID;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getDownLoadPath() {
		return downLoadPath;
	}

	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}

	public String getLabelTag() {
		return labelTag;
	}

	public void setLabelTag(String labelTag) {
		this.labelTag = labelTag;
	}

	public List<BaseBean> getListGamJeom() {
		return listGamJeom;
	}

	public void setListGamJeom(List<BaseBean> listGamJeom) {
		this.listGamJeom = listGamJeom;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getOvertimeWages() {
		return overtimeWages;
	}

	public void setOvertimeWages(String overtimeWages) {
		this.overtimeWages = overtimeWages;
	}

	public String getCheckdiscount() {
		return checkdiscount;
	}

	public void setCheckdiscount(String checkdiscount) {
		this.checkdiscount = checkdiscount;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
