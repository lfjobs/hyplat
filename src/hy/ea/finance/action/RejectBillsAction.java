package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;


/**
 * 驳回单据管理   RejectBillsAction
 * @author lf
 *
 */
public class RejectBillsAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CompanyService companyserverService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
    /**
	 * 单据类别
	 */
	private List<CCode> billsTypeList;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	/**
	 * 个人往来关系
	 */
	private List<CCode> codeRelationList;
	private String search;
	private String sdate;
	private String edate;
	private String level;//区分总部 allCompany  公司 company  部门 organization

	/** **************************************************** */
	/**
	 * 出纳记账列表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getCashierBillsList() {
		search();
		return "list";
	}
	
	/**
	 * 去出纳记账添加、修改页面
	 * 
	 */
	public String toSaveCashierBills() {
		toPage();
		return "edit";
	}

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getCashierBillsList();
	}

	/**
	 * 封装查询类表的方法
	 */
	private void search() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, parms);
	}

	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            CashierBills 查询条件
	 * @return ：beanlist
	 */

	private List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		String sql = "from CashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID());
		sql += " where t.status =? ";
		parms.add("10");
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if (cashierBillsVO != null) {
				if (null != cashierBillsVO.getStaffID().trim()
						&& !"".equals(cashierBillsVO.getStaffID().trim())) {
					sql += " and t.staffID = ?";
					parms.add(cashierBillsVO.getStaffID().trim());
				}
				if (null != cashierBillsVO.getCompanyID()
						&& !"".equals(cashierBillsVO.getCompanyID())) {
						sql += " and t.companyID = ? ";
						parms.add(cashierBillsVO.getCompanyID());
				} else {
					if (cids.size() == 1) {
						sql += " and t.companyID = ? ";
						parms.add(cids.get(0));
					}else {
						sql += " and (t.pcompanyID = ? or t.companyID = ? )";
						parms.add(account.getCompanyID());
						parms.add(account.getCompanyID());
					}
				}
				if (null != cashierBillsVO.getDepartmentID()
						&& !"".equals(cashierBillsVO.getDepartmentID())) {
					sql += " and (t.organizationID = ? or t.departmentID = ? ) ";
					parms.add(cashierBillsVO.getDepartmentID());
					parms.add(cashierBillsVO.getDepartmentID());
				}
				if (null != cashierBillsVO.getBillsType()
						&& !"".equals(cashierBillsVO.getBillsType())) {
					sql += " and t.billsType = ?";
					parms.add(cashierBillsVO.getBillsType().trim());
				}
				if (null != cashierBillsVO.getJournalNum().trim()
						&& !"".equals(cashierBillsVO.getJournalNum().trim())) {
					sql += " and t.journalNum like ? ";
					parms.add("%" + cashierBillsVO.getJournalNum().trim()
									+ "%");
				}
				if (null != cashierBillsVO.getContactConnections().trim()
						&& !"".equals(cashierBillsVO.getContactConnections()
								.trim())) {
					sql += " and t.contactConnections = ? ";
					parms.add(cashierBillsVO.getContactConnections());
				}
				if (null != cashierBillsVO.getPhone().trim()
						&& !"".equals(cashierBillsVO.getPhone().trim())) {
					sql += " and t.phone = ? ";
					parms.add(cashierBillsVO.getPhone().trim());
				}
				if (sdate != null && edate != null && !("").equals(sdate)
						&& !("").equals(edate)) {
					sql += " and t.cashierDate between ? and ? ";

					parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));

					parms.add(Utilities.getDateFromString(edate + " 23:59:59",
							"yyyy-MM-dd hh:mm:ss"));
				}
				if (null != cashierBillsVO.getCcompanyname().trim()
						&& !"".equals(cashierBillsVO.getCcompanyname().trim())) {
					sql += " and t.companyname like ? ";
					parms.add("%" + cashierBillsVO.getCcompanyname().trim()
							+ "%");
				}
				if (null != cashierBillsVO.getContactUserName().trim()
						&& !"".equals(cashierBillsVO.getContactUserName()
								.trim())) {
					sql += " and t.staffname like ? ";
					parms.add("%" + cashierBillsVO.getContactUserName().trim()
							+ "%");
				}
			}
		} else {
			if(level!=null&&"allCompany".equals(level.trim())){
				if (cids.size() == 1) {
					sql += " and t.companyID = ? ";
					parms.add(account.getCompanyID());
				} else {
					sql += " and (t.pcompanyID= ? or t.companyID= ? ) ";
					parms.add(account.getCompanyID());
					parms.add(account.getCompanyID());
				}
			}
			if(level!=null&&"company".equals(level.trim())){
				sql += " and t.companyID = ? ";
				parms.add(account.getCompanyID());
			}
			if(level!=null&&"organization".equals(level.trim())){
				sql += " and t.companyID = ? ";
				parms.add(account.getCompanyID());
				sql += " and t.organizationID = ? ";
				parms.add(organizationID);
			}
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出出纳记账
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
		List<BaseBean> lists=baseBeanService.getListBeanByHqlAndParams(sql, parms);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeadings(),lists);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出出纳记账", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	
	/**
	 * 封装跳转添加或编辑页面的方法
	 * 
	 * @param :companyID,
	 *            organizationID 添加
	 * @param :companyID,
	 *            organizationID ，cashierTally.getCashierID()修改
	 * @return : edit
	 */
	private void toPage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where companyID = ?  and cashierBillsID=? order by goodsNomber asc";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where companyID = ?  and cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
			String hql2="from CashierBills where companyID = ?  and cashierBillsID=?";
			cashierBills=(CashierBills) baseBeanService.getBeanByHqlAndParams(hql2, params1);
		}
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

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
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

	public List<CCode> getBillsTypeList() {
		return billsTypeList;
	}

	public void setBillsTypeList(List<CCode> billsTypeList) {
		this.billsTypeList = billsTypeList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
