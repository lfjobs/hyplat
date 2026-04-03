package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.vo.GoodsCashierBillsVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * 物品管理 ：GoodsCashierSummaryAction
 * 
 * @author
 * 
 */
public class GoodsCashierSummaryAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CCodeService codeService;
	public InputStream excelStream;
	private String result;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String period;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	/**
	 * 往来个人
	 */
	private List<CCode> codeRelationList;
	/**
	 * 往来单位
	 */
	private List<CCode> connectionlist;
	private GoodsCashierBillsVO cashierBills;
	private String search;
	private String sdate;
	private String edate;
	private String cc;   //判断公司还是集团
	private String treePID;
	private List<BaseBean> goodslist;
	private List<BaseBean> cashierlist;
	/**
	 * 单据类型
	 */
	private List<CCode> billTypes;

	/**
	 * 当前公司ID
	 */
	private String currentCompanyID;
	/**
	 * 当前部门ID
	 */
	private String currentOrgnizationID;

	/**
	 * *******************************公司汇总 start
	 * *************************************
	 */
	/**
	 * 公司汇总 条件查询 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", cashierBills);
		return getCashierList();
	}

	/**
	 * 物品明细管理列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getCashierList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// ArrayList<String>
		// cids=companyserverService.getCompanyAndItsChildrenIDs((String)
		// session.get("companyID"));
		// DetachedCriteria dcc=DetachedCriteria.forClass(CStaffCos.class);
		// dcc.add(Restrictions.in("companyID", cids));
		// stafflist=baseBeanService.getListByDC(dcc);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "companylist";
	}

	/**
	 * 根据部门查询部门下的人员
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getStaffList() {
		String sql = "select t.staffID,t.staffName from dt_hr_staff t where exists"
				+ "(select s.staffID  from dtcos s where t.staffID=s.staffID and s.cosStatus=? "
				+ "and  s.companyID= ?  and s.organizationID = "
				+ "( select e.organizationID from dtCOrganization e where e.organizationID =?))";
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[] { "50", currentCompanyID, currentOrgnizationID });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stafflist", list);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}

	/**
	 * 公司汇总 条件查询 语句拼接 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            GoodsCashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID());
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.status = ? ";
		parms.add("07");
		if(period!=null&&period.equals("01")){
			sql += " and t.cashierDate between ? and ? ";
			Calendar   c   =   Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -30);
			parms.add(c.getTime());
			parms.add(new Date());
		}
		if (search != null && search.equals("search")) {
			cashierBills = (GoodsCashierBillsVO) session.get("tablesearch");
			if (cashierBills.getCompanyID() != null
					&& !"".equals(cashierBills.getCompanyID())) {
				sql += " and t.companyID = ? ";
				parms.add(cashierBills.getCompanyID());
			} else {
				if (cids != null) {
					if (cids.size() == 1) {
						sql += " and t.companyID=? ";
						parms.add(account.getCompanyID());
					} else {
						sql += " and (t.pcompanyID=? or t.companyID=?) ";
						parms.add(account.getCompanyID());
						parms.add(account.getCompanyID());
					}
				}
			}
			if (null != cashierBills.getStaffID()
					&& !"".equals(cashierBills.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(cashierBills.getStaffID().trim());
			}
			if (null != cashierBills.getDepartmentID()
					&& !"".equals(cashierBills.getDepartmentID())
					&& !cashierBills.getCompanyID().equals(
							cashierBills.getDepartmentID())) {

				sql += " and (t.organizationID=? or t.departmentID=?)";
				parms.add(cashierBills.getDepartmentID().trim());
				parms.add(cashierBills.getDepartmentID().trim());
			}
			if (null != cashierBills.getBillsType()
					&& !"".equals(cashierBills.getBillsType())) {
				sql += " and t.billsType like ?";
				parms.add("%" + cashierBills.getBillsType().trim() + "%");
			}
			if (null != cashierBills.getJournalNum()
					&& !"".equals(cashierBills.getJournalNum())) {
				sql += " and t.journalNum like ?";
				parms.add("%" + cashierBills.getJournalNum().trim() + "%");
			}
			if (null != cashierBills.getGoodsName()
					&& !"".equals(cashierBills.getGoodsName())) {
				sql += " and  t.goodsName like ?";
				parms.add("%" + cashierBills.getGoodsName().trim() + "%");
			}
			if (null != cashierBills.getGoodsCoding()
					&& !"".equals(cashierBills.getGoodsCoding())) {
				sql += " and t.goodsCoding like ?";
				parms.add("%" + cashierBills.getGoodsCoding().trim() + "%");
			}
			if (null != cashierBills.getCcompanyname()
					&& !"".equals(cashierBills.getCcompanyname())) {
				sql += " and t.companyname like ?";
				parms.add("%" + cashierBills.getCcompanyname().trim() + "%");
			}
			if (null != cashierBills.getContactUserName()
					&& !"".equals(cashierBills.getContactUserName())) {
				sql += " and t.contactUserName like ?";
				parms.add("%" + cashierBills.getContactUserName().trim() + "%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and t.cashierDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate + " 00:00:00:000",
						"yyyy-MM-dd HH:mm:ss:sss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59:999",
						"yyyy-MM-dd HH:mm:ss:sss"));
			}
		} else {
			if (cids != null) {
				if (cids.size() == 1) {
					sql += " and t.companyID=?";
					parms.add(account.getCompanyID());
				} else {
					sql += " and (t.pcompanyID=? or t.companyID=?)";
					parms.add(account.getCompanyID());
					parms.add(account.getCompanyID());
				}
			}
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 查看
	 * 
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") public String toCashier() { Map<String,
	 * Object> session = ActionContext.getContext().getSession(); String sql
	 * =VOtool.getGoodsBillVO(2); String sql2 = VOtool.getCashierBillVO(2);
	 * String[] parms = {}; CAccount account = (CAccount)session.get("account");
	 * parms.add(account.getCompanyID()); parms[0] =account.getCompanyID();
	 * parms[1] = journalnum; goodslist = (List<BaseBean>)baseBeanService
	 * .getBeanByHqlAndParams(sql, parms); cashierlist = (List<BaseBean>)baseBeanService
	 * .getBeanByHqlAndParams(sql2, parms); return ""; }
	 */

	/**
	 * 导出物品明细管理列表
	 * 
	 * @param :
	 *            account organizationID
	 * @return : showexcel
	 */

	public String showExcel() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		//sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.goodsShowExcel(GoodsCashierBillsVO
				.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(
				sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出出纳记账明细", account);
		baseBeanService.update(logBook);
		return "showexcel";

	}

	/**
	 * *******************************公司汇总 end
	 * *************************************
	 */

	/**
	 * *******************************财务物品汇总 start
	 * *************************************
	 */
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toProSearch() {
		ActionContext.getContext().getSession()
				.put("tablesearch", cashierBills);
		return getProCashierList();
	}

	/**
	 * 物品明细管理列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getProCashierList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String staffhql = "from Staff where staffID in ( select staffID from COS where companyID=? and  cosStatus=? )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, new Object[]{account.getCompanyID(),"50"});
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<Object> list = getProList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "prolist";
	}

	/**
	 * 物品明细查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            GoodsCashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getProList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.companyID = ?";
		parms.add(account.getCompanyID().trim());
		sql += " and t.status = ? ";
		parms.add("07");
		if(period!=null&&period.equals("01")){
			sql += " and t.cashierDate between ? and ? ";
			Calendar   c   =   Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -30);
			parms.add(c.getTime());
			parms.add(new Date());
		}
		if (search != null && search.equals("search")) {
			cashierBills = (GoodsCashierBillsVO) session.get("tablesearch");
			if (null != cashierBills.getStaffID()
					&& !"".equals(cashierBills.getStaffID())) {
				sql += " and t.staffID = ?";
				parms.add(cashierBills.getStaffID().trim());
			}
			if (null != cashierBills.getDepartmentID()
					&& !"".equals(cashierBills.getDepartmentID())
					&& !account.getCompanyID().equals(
							cashierBills.getDepartmentID())) {
				sql += " and t.departmentID=?";
				parms.add(cashierBills.getDepartmentID().trim());
			}
			if (null != cashierBills.getBillsType()
					&& !"".equals(cashierBills.getBillsType())) {
				sql += " and t.billsType like ?";
				parms.add("%" + cashierBills.getBillsType().trim() + "%");
			}
			if (null != cashierBills.getJournalNum()
					&& !"".equals(cashierBills.getJournalNum())) {
				sql += " and t.journalNum like ?";
				parms.add("%" + cashierBills.getJournalNum().trim() + "%");
			}
			if (null != cashierBills.getGoodsName()
					&& !"".equals(cashierBills.getGoodsName())) {
				sql += " and t.goodsName like ?";
				parms.add("%" + cashierBills.getGoodsName().trim() + "%");
			}
			if (null != cashierBills.getGoodsCoding()
					&& !"".equals(cashierBills.getGoodsCoding())) {
				sql += " and t.goodsCoding like ?";
				parms.add("%" + cashierBills.getGoodsCoding().trim() + "%");
			}
			if (null != cashierBills.getCcompanyname()
					&& !"".equals(cashierBills.getCcompanyname())) {
				sql += " and t.companyname like ?";
				parms.add("%" + cashierBills.getCcompanyname().trim() + "%");
			}
			if (null != cashierBills.getContactUserName()
					&& !"".equals(cashierBills.getContactUserName())) {
				sql += " and t.contactUserName like ?";
				parms.add("%" + cashierBills.getContactUserName().trim() + "%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and t.cashierDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate + " 00:00:00:000",
						"yyyy-MM-dd HH:mm:ss:sss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59:999",
						"yyyy-MM-dd HH:mm:ss:sss"));
			}
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出物品明细管理列表
	 * 
	 * @param :
	 *            account organizationID
	 * @return : showexcel
	 */

	public String showProExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = this.getProList();
		String sql = (String) list.get(0);
		//sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.goodsShowExcel(GoodsCashierBillsVO
				.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(
				sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出出纳记账明细", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * *******************************财务物品汇总 end
	 * *************************************
	 */
	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public GoodsCashierBillsVO getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(GoodsCashierBillsVO cashierBills) {
		this.cashierBills = cashierBills;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<CCode> getBillTypes() {
		return billTypes;
	}

	public void setBillTypes(List<CCode> billTypes) {
		this.billTypes = billTypes;
	}

	public String getCurrentCompanyID() {
		return currentCompanyID;
	}

	public void setCurrentCompanyID(String currentCompanyID) {
		this.currentCompanyID = currentCompanyID;
	}

	public String getCurrentOrgnizationID() {
		return currentOrgnizationID;
	}

	public void setCurrentOrgnizationID(String currentOrgnizationID) {
		this.currentOrgnizationID = currentOrgnizationID;
	}

	public List<BaseBean> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<BaseBean> goodslist) {
		this.goodslist = goodslist;
	}

	public List<BaseBean> getCashierlist() {
		return cashierlist;
	}

	public void setCashierlist(List<BaseBean> cashierlist) {
		this.cashierlist = cashierlist;
	}

	public String getTreePID() {
		return treePID;
	}

	public void setTreePID(String treePID) {
		this.treePID = treePID;
	}
	public String getCc() {                 
		return cc;                          
	}                                       
	                                        
	public void setCc(String cc) {          
		this.cc = cc;                       
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
