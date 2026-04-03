package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
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
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/**
 * 报税单据汇总：TaxSummaryAction
 * 
 * @author lf
 * 
 */
@Controller
@Scope("prototype")
public class TaxSummaryAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;

	public InputStream excelStream;
	private CashierBillsVO cashierBillsVO;
	private String result;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private List<BaseBean> stafflist;
	private List<CCode> codeRelationList;
	private List<CCode> connectionlist;
	private CashierBills cashierBills;
	private String search;
	private String BType;
	private Map<String, GoodsBills> goodsmap;
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
	 * 单据类别
	 */
	private List<CCode> billTypes;
	/**
	 * 费用类别--GoodsBills
	 */
	private List<CCode> costTypeList;
	/**
	 * 付款方式--GoodsBills
	 */
	private List<CCode> payTypeList;
	/**
	 * 单价管理--GoodsBills
	 */
	private List<CCode> priceManageList;
	/**
	 * 物流方式--GoodsBills
	 */
	private List<CCode> logisticsList;
	/**
	 * 仓库类别
	 */
	private List<CCode> typelist;

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getTaxList();
	}

	/**
	 * 单据报税汇总管理列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getTaxList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? )";
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
	 * 查询汇总列表（可根据条件查询）被调用
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
		sql += " and t.taxstatus = ? ";
		parms.add("04");
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
	 * 导出单据报税汇总管理
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
				"导出单据报税汇总管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 报税单据汇总----查看
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
	 * 根据税务单据编号模糊查询列表
	 * 
	 * @return
	 */
	public String getAjaxDutiableList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params1 = { cashierBillsVO.getJournalNum() };
		String hql = " from CashierBillsVO where  journalNum=?";
		cashierBillsVO = (CashierBillsVO) baseBeanService
				.getBeanByHqlAndParams(hql, params1);
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));
		map.put("cashierBillsVO", cashierBillsVO);
		JSONObject jsonArray = JSONObject.fromObject(map, jsonConfig);
		result = jsonArray.toString();
		return "success";
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getBType() {
		return BType;
	}

	public void setBType(String type) {
		BType = type;
	}

	public Map<String, GoodsBills> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, GoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
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

	public List<CCode> getBillTypes() {
		return billTypes;
	}

	public void setBillTypes(List<CCode> billTypes) {
		this.billTypes = billTypes;
	}

	public List<CCode> getCostTypeList() {
		return costTypeList;
	}

	public void setCostTypeList(List<CCode> costTypeList) {
		this.costTypeList = costTypeList;
	}

	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getPriceManageList() {
		return priceManageList;
	}

	public void setPriceManageList(List<CCode> priceManageList) {
		this.priceManageList = priceManageList;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
}
