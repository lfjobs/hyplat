package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * 税务单据待经理审核管理：ManagerAction
 * 
 * @author lf
 * 
 */
@Controller
@Scope("prototype")
public class ManagerAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private List<BaseBean> stafflist;
	private List<CCode> codeRelationList;
	private List<CCode> connectionlist;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
	private String search;
	/**
	 * 页面查询制单日期
	 */
	private String sdate;
	private String edate;
	/**
	 * 页面查询报税日期
	 */
	private String tsdate;
	private String tedate;
	/**
	 * 储存页面传过来的税务状态
	 */
	private String taxstatusDu;
	/**
	 * 单据类别
	 */
	private List<CCode> billTypes;

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @return getCashierTallyList() 单据报税待经理审核管理列表
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getDutiableList();
	}

	/**
	 * 单据报税待经理审核管理列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getDutiableList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and companyID=? and cosStatus=? )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50" });
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);

		return "list";
	}

	/**
	 * 单据报税待经理审核查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            CashierBills 查询条件
	 * @return ：DetachedCriteria
	 */
	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		// VOtool.getCashierBillVO()是用来生成sql语句的
		String sql = VOtool.getCashierBillVO(2);
		// 用来保存参数
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		sql += " where t.companyid = ? ";
		parms.add(account.getCompanyID());
		if (taxstatusDu != null && taxstatusDu.equals("taxstatusDu")) {
			sql += " and (t.taxstatus between ? and ? or (t.taxstatus=? and t.manager is not null and t.financial is null))";
			parms.add("03");
			parms.add("04");
			parms.add("10");
		} else {
			sql += " and t.taxstatus = ? ";
			parms.add("02");
		}
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");

			if (null != cashierBillsVO.getStaffID()
					&& !"".equals(cashierBillsVO.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(cashierBillsVO.getStaffID());
			}
			if (null != cashierBillsVO.getDepartmentID()
					&& !"".equals(cashierBillsVO.getDepartmentID())
					&& !account.getCompanyID().equals(
							cashierBillsVO.getDepartmentID())) {
				sql += " and t.departmentID = ?";
				parms.add(cashierBillsVO.getDepartmentID());
			}
			if (null != cashierBillsVO.getBillsType()
					&& !"".equals(cashierBillsVO.getBillsType())) {
				sql += " and t.billsType = ?";
				parms.add(cashierBillsVO.getBillsType());
			}
			if (null != cashierBillsVO.getJournalNum()
					&& !"".equals(cashierBillsVO.getJournalNum())) {
				sql += " and t.journalNum like ? ";
				parms.add("%" + cashierBillsVO.getJournalNum() + "%");
			}
			if (null != cashierBillsVO.getTaxstatus()
					&& !"".equals(cashierBillsVO.getTaxstatus())) {
				sql += " and t.taxstatus = ? ";
				parms.add(cashierBillsVO.getTaxstatus());
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and t.cashierDate between ? and ? ";
				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (tsdate != null && tedate != null && !("").equals(tsdate)
					&& !("").equals(tedate)) {
				sql += " and t.taxDate between ? and ? ";
				parms.add(Utilities.getDateFromString(tsdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(tedate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (null != cashierBillsVO.getContactConnections()
					&& !"".equals(cashierBillsVO.getContactConnections())) {
				sql += " and t.contactConnections = ? ";
				parms.add(cashierBillsVO.getContactConnections());
			}
			if (null != cashierBillsVO.getPhone()
					&& !"".equals(cashierBillsVO.getPhone())) {
				sql += " and t.phone = ?";
				parms.add(cashierBillsVO.getPhone());
			}
			if (null != cashierBillsVO.getCcompanyname()
					&& !"".equals(cashierBillsVO.getCcompanyname())) {
				sql += " and cc.companyname like ? ";
				parms.add("%" + cashierBillsVO.getCcompanyname() + "%");
			}
			if (null != cashierBillsVO.getContactUserName()
					&& !"".equals(cashierBillsVO.getContactUserName())) {
				sql += " and cu.staffname like ? ";
				parms.add("%" + cashierBillsVO.getContactUserName() + "%");
			}
		}

		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出单据报税待经理审核管理列表
	 * 
	 * @param :
	 *            account organizationID
	 * @return : showexcel
	 */

	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeading(),
				baseBeanService.getListBeanBySqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出单据报税待经理审核管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 单据报税待经理审核管理数据查看
	 * 
	 * @return
	 */
	public String toCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where companyID = ? and cashierBillsID=?";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where companyID = ?  and cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
			String hql2 = " from CashierBills where companyID = ?  and cashierBillsID=?";
			cashierBills = (CashierBills) baseBeanService
					.getBeanByHqlAndParams(hql2, params1); 
		}
		return "edit";
	}

	/**
	 * JSON修改待经理审核单状态
	 */
	public String updateResponsible() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		String hql = "update CashierBills set taxstatus = ?,manager= ? where companyID = ?  and cashierBillsID = ? ";
		Object[] params = { cashierBills.getTaxstatus(), sta.getStaffName(),
				account.getCompanyID(), cashierBills.getCashierBillsID() };
		String hql1 = "from  CashierBills  where companyID = ?  and cashierBillsID = ? ";
		CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql1, new Object[] { account.getCompanyID(),
						cashierBills.getCashierBillsID() });
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = null;
		if (cashierBills.getTaxstatus().equals("10")) {
			logbook = logBookService.saveCLogBook(organizationID, "经理驳回作废(凭证号:"
					+ cb.getJournalNum() + ")", account);
		} else {
			logbook = logBookService.saveCLogBook(organizationID,
					"转财务审核单:(凭证号：" + cb.getJournalNum() + ")", account);
		}
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
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

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
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

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public String getTaxstatusDu() {
		return taxstatusDu;
	}

	public void setTaxstatusDu(String taxstatusDu) {
		this.taxstatusDu = taxstatusDu;
	}

	public List<CCode> getBillTypes() {
		return billTypes;
	}

	public void setBillTypes(List<CCode> billTypes) {
		this.billTypes = billTypes;
	}

	public String getTsdate() {
		return tsdate;
	}

	public void setTsdate(String tsdate) {
		this.tsdate = tsdate;
	}

	public String getTedate() {
		return tedate;
	}

	public void setTedate(String tedate) {
		this.tedate = tedate;
	}

}
