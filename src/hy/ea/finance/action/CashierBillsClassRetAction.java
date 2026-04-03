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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

/**
 * 出纳分单据展示：CashierBillsClassRetAction
 * 
 * @author 李伟志
 * 
 */
public class CashierBillsClassRetAction {
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
	/**
	 * 单据类别
	 */
	private String billsType;
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
	private String BType;
	private String cStaus;
	/** **************************************************** */
	/**
	 * 预算审核——获取单据类别(供调用)
	 * 
	 * @param status
	 *            单据类别编号
	 * @return
	 */
	private String getBillsTypeMap(String status) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("00", "预算前调查单");
		result.put("01", "预算管理单");
		result.put("02", "确定预算单");
		result.put("03", "预算市场调查单");
		result.put("04", "采购招标管理单");
		result.put("05", "采购比价管理单");
		result.put("06", "采购物品审批管理单");
		result.put("07", "采购前验货单");
		result.put("08", "采购管理单");
		result.put("09", "采购收货验货单");
		result.put("10", "半成品入库单");
		result.put("11", "半成品出库单");
		result.put("12", "半成品库存单");
		result.put("13", "半成品质检单");
		result.put("14", "生成入库单");
		result.put("15", "生产出库单");
		result.put("16", "生产库存单");
		result.put("17", "生产质检单");
		result.put("18", "成品入库单");
		result.put("19", "成品出库单");
		result.put("20", "成品库存单");
		result.put("21", "成品质检单");
		/*result.put("22", "预算前调查资金管理单");
		result.put("23", "预算资金单");
		result.put("24", "确定预算单");
		result.put("25", "市场调查资金管理单");
		result.put("26", "采购招标资金管理单");
		result.put("27", "采购比价资金管理单");
		result.put("28", "采购资金审批管理单");
		result.put("29", "采购前审批拨现金对账单");
		result.put("30", "采购资金管理单");
		result.put("31", "收货资金验收单");
		result.put("32", "半成品入库资金管理单");
		result.put("33", "半成品出库资金管理单");
		result.put("34", "半成品库存资金管理单");
		result.put("35", "半成品质检资金管理单");
		result.put("36", "生产入库资金管理单");
		result.put("37", "生产出库资金管理单");
		result.put("38", "生产库存资金管理单");
		result.put("39", "生产质检资金管理单");
		result.put("40", "成品入库资金管理单");
		result.put("41", "成品出库资金管理单");
		result.put("42", "成品库存资金管理单");
		result.put("43", "成品质检管理单");*/
		return result.get(status);
	}
	
	
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
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		billsType=getBillsTypeMap(BType);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, parms);
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
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
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
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		
		String hql = "from CashierBillsVO where status = ? and billsType = ?";
		parms.add("07");
		parms.add(getBillsTypeMap(BType));
		if(cStaus == "jt"){
			hql += " and ( companyID = ? or pcompanyID = ?)";
			parms.add(account.getCompanyID());
			parms.add(account.getCompanyID());
		}else{
			hql += " and companyID = ? ";
			parms.add(account.getCompanyID());
		}
		if (journalNums != null && journalNums.split(",").length >= 1) {
			hql += " and (";
			String[] arrays = journalNums.split(",");
			for (int i = 0; i < arrays.length - 1; i++) {
				hql += " journalNum = ? or";
				parms.add(arrays[i]);
			}
			hql += " journalNum = ?";
			parms.add(arrays[arrays.length - 1]);
			hql += ")";
		}
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if (null != cashierBillsVO.getStaffID()
					&& !"".equals(cashierBillsVO.getStaffID())) {
				hql += " and staffID = ?";
				parms.add(cashierBillsVO.getStaffID());
			}
			if (null != cashierBillsVO.getDepartmentID()
					&& !"".equals(cashierBillsVO.getDepartmentID())
					&& !account.getCompanyID().equals(
							cashierBillsVO.getDepartmentID())) {
				hql += " and departmentID = ?";
				parms.add(cashierBillsVO.getDepartmentID());
			}
			if (null != cashierBillsVO.getBillsType()
					&& !"".equals(cashierBillsVO.getBillsType())) {
				hql += " and billsType = ?";
				parms.add(cashierBillsVO.getBillsType());
			}
			if (null != cashierBillsVO.getJournalNum()
					&& !"".equals(cashierBillsVO.getJournalNum())) {
				hql += " and journalNum like ? ";
				parms.add("%" + cashierBillsVO.getJournalNum() + "%");
			}
			if (null != cashierBillsVO.getContactConnections()
					&& !"".equals(cashierBillsVO.getContactConnections())) {
				hql += " and contactConnections = ? ";
				parms.add(cashierBillsVO.getContactConnections());
			}
			if (null != cashierBillsVO.getPhone()
					&& !"".equals(cashierBillsVO.getPhone())) {
				hql += " and phone = ? ";
				parms.add(cashierBillsVO.getPhone());
			}
			if (null != cashierBillsVO.getCcompanyname()
					&& !"".equals(cashierBillsVO.getCcompanyname())) {
				hql += " and ccompanyname like ? ";
				parms.add("%" + cashierBillsVO.getCcompanyname() + "%");
			}
			if (null != cashierBillsVO.getContactUserName()
					&& !"".equals(cashierBillsVO.getContactUserName())) {
				hql += " and staffname like ? ";
				parms.add("%" + cashierBillsVO.getContactUserName() + "%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				hql += " and cashierDate between ? and ? ";

				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));

				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (null != cashierBillsVO.getTaxstatus()
					&& !"".equals(cashierBillsVO.getTaxstatus())) {
				hql += " and taxstatus = ? ";
				parms.add(cashierBillsVO.getTaxstatus());
			}
			if (null != cashierBillsVO.getDepStatue()
					&& !"".equals(cashierBillsVO.getDepStatue())) {
				if ("00".equals(cashierBillsVO.getDepStatue())) {
					hql += " and (depStatue = ? or depStatue is null)";
					parms.add("00");
				} else {
					hql += " and depStatue = ? ";
					parms.add(cashierBillsVO.getDepStatue());
				}
			}
		}
		
		hql += " order by companyID asc , journalNum desc";
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 转科一培训
	 */
	public String updateContactUserStatus() {
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


	public String getBillsType() {
		return billsType;
	}


	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}


	public String getBType() {
		return BType;
	}


	public void setBType(String type) {
		BType = type;
	}


	public String getCStaus() {
		return cStaus;
	}


	public void setCStaus(String staus) {
		cStaus = staus;
	}
	
	
}
