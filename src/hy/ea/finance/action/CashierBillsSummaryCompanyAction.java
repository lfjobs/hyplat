package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
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
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/**
 * 出纳单据统计管理：CashierBillsSummaryCompanyAction
 * 
 * @author yjg
 * 
 */
public class CashierBillsSummaryCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
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
	 * 个人往来关系
	 */
	private List<CCode> codeRelationList;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
	private String search;
	private String sdate;
	private String edate;
	private String status;
	private List<CCode> billTypes;
	private String journalNums;
	private String tohide;

	/** *******************************公司汇总************************************* */
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getCashierList();
	}

	/**
	 * 公司汇总出纳单据统计列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getCashierList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50" });
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<Object> list = getListCompany();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,  parms);
		return "companylist";
	}

	/**
	 * 根据单据编号模糊查询列表
	 * 
	 * @return
	 */
	public String getAjaxCashierList() {
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

	/**
	 * 导出汇总出纳单据
	 * 
	 * @param :
	 *            account organizationID
	 * @return : showexcel
	 */

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getListCompany();
		String sql = (String) list.get(0);
		//sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出公司汇总出纳单据统计", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 查看
	 * 
	 * @return
	 */
	public String toCashier() {
		if (cashierBillsVO != null) {
			Object[] params1 = { cashierBillsVO.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where  cashierBillsID=?";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where  cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
			String hql2="from CashierBills where  cashierBillsID=?";
			cashierBills=(CashierBills) baseBeanService.getBeanByHqlAndParams(hql2, params1);
		}
		return "edit";
	}

	/**
	 * 查询出纳单据列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            CashierBillsVO 查询条件
	 * @return ：DetachedCriteria
	 */

	public List<Object> getListCompany() {
		List<Object> result = new ArrayList<Object>();
		String sql ="from CashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		sql += " where 1=1 ";
		sql += " and t.companyID = ? ";
		parms.add(account.getCompanyID());
		if(period!=null&&period.equals("01")){
			sql += " and t.cashierDate between ? and ? ";
			Calendar   c   =   Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -30);
			parms.add(c.getTime());
			parms.add(new Date());
		}
		if (journalNums != null && journalNums.split(",").length >= 1) {
			sql += " and (";
			String[] arrays = journalNums.split(",");
			for (int i = 0; i < arrays.length - 1; i++) {
				sql += " t.journalNum = ? or";
				parms.add(arrays[i]);
			}
			sql += " t.journalNum = ?";
			parms.add(arrays[arrays.length - 1]);
			sql += ")";
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
			if (null != cashierBillsVO.getContactConnections()
					&& !"".equals(cashierBillsVO.getContactConnections())) {
				sql += " and t.contactConnections = ? ";
				parms.add(cashierBillsVO.getContactConnections());
			}
			if (null != cashierBillsVO.getPhone()
					&& !"".equals(cashierBillsVO.getPhone())) {
				sql += " and t.phone = ? ";
				parms.add(cashierBillsVO.getPhone());
			}
			if (null != cashierBillsVO.getCcompanyname()
					&& !"".equals(cashierBillsVO.getCcompanyname())) {
				sql += " and t.companyname like ? ";
				parms.add("%" + cashierBillsVO.getCcompanyname() + "%");
			}
			if (null != cashierBillsVO.getContactUserName()
					&& !"".equals(cashierBillsVO.getContactUserName())) {
				sql += " and t.staffname like ? ";
				parms.add("%" + cashierBillsVO.getContactUserName() + "%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and t.cashierDate between ? and ? ";

				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));

				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			
			if (null != cashierBillsVO.getStatus().trim()
					&& !"".equals(cashierBillsVO.getStatus().trim())) {
				sql += " and t.status = ?";
				parms.add(cashierBillsVO.getStatus().trim());
			} else {
				sql += " and t.status between ? and ?";
				parms.add("04");
				parms.add("10");
			}
			 
			if (null != cashierBillsVO.getTaxstatus()    
					&& !"".equals(cashierBillsVO.getTaxstatus())) {
				sql += " and t.taxstatus = ? ";
				parms.add(cashierBillsVO.getTaxstatus());
			}
			if (null != cashierBillsVO.getDepStatue()
					&& !"".equals(cashierBillsVO.getDepStatue())) {
				if ("00".equals(cashierBillsVO.getDepStatue())) {
					sql += " and (t.depStatue = ? or t.depStatue is null)";
					parms.add("00");
				} else {
					sql += " and t.depStatue = ? ";
					parms.add(cashierBillsVO.getDepStatue());
				}
			}
		}
		
		sql += " order by t.companyID asc , t.journalNum desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 转科一培训
	 */
	/*public String updateContactUserStatus() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		// 转科一培训
		String hql1 = "update CashierBills set depStatue = ? where companyID = ?  and cashierBillsID = ? ";
		Object[] parms = new Object[] { "01", account.getCompanyID(),
				cashierBills.getCashierBillsID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
				new String[] { hql1 }, parms);
		return "success";
	}
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

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getJournalNums() {
		return journalNums;
	}

	public void setJournalNums(String journalNums) {
		this.journalNums = journalNums;
	}

	public String getTohide() {
		return tohide;
	}

	public void setTohide(String tohide) {
		this.tohide = tohide;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
