package hy.ea.marketing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
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

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * 待营销审核管理：MarketingExamineAction
 * 
 * @author zl
 * 
 */
public class MarketingExamineAction {
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
	private String sdate;
	private String edate;
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
	
	private Map<String, GoodsBills> goodsmap;
	
	/**
	 * 单据类别
	 */
	private List<CCode> billsTypeList;
	
	private String BType;
	
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
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierBillsVO
	 * @return getMarketingList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getMarketingList();
	}
	
	/**
	 * 待营销审核管理列表
	 */
	public String getMarketingList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID };
		String staffhql = "from Staff t where exists( select staffID from COS s where t.staffID=s.staffID and s.companyID=? and s.organizationID=? and s.cosStatus='50' )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
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
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @param : CashierBills 查询条件
	 * @return ：list
	 */

	private List<Object> getList(){
		List<Object> result = new ArrayList<Object>();
		String sql = VOtool.getCashierBillVO(3);
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		sql += " where t.billstype = '咨询跟踪单'";
		sql += " and t.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and t.organizationid = ? ";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if (cashierBillsVO != null) {
				if (null != cashierBillsVO.getStaffID()
						&& !"".equals(cashierBillsVO.getStaffID())) {
					sql += " and t.staffID = ?";
					parms.add(cashierBillsVO.getStaffID().trim());
				}
				if (null != cashierBillsVO.getDepartmentID()
						&& !"".equals(cashierBillsVO.getDepartmentID())) {
					sql += " and t.departmentid = ?";
					parms.add(cashierBillsVO.getDepartmentID().trim());
				}
				if (null != cashierBillsVO.getJournalNum()
						&& !"".equals(cashierBillsVO.getJournalNum())) {
					sql += " and t.journalNum like ? ";
					parms.add("%" + cashierBillsVO.getJournalNum().trim() + "%");
				}
				if (null != cashierBillsVO.getContactConnections()
						&& !"".equals(cashierBillsVO.getContactConnections())) {
					sql += " and t.contactConnections = ? ";
					parms.add(cashierBillsVO.getContactConnections());
				}
				if (null != cashierBillsVO.getPhone()
						&& !"".equals(cashierBillsVO.getPhone())) {
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
				if (null != cashierBillsVO.getCcompanyname()
						&& !"".equals(cashierBillsVO.getCcompanyname())) {
					sql += " and cc.companyname like ? ";
					parms.add("%" + cashierBillsVO.getCcompanyname().trim() + "%");
				}
				if (null != cashierBillsVO.getContactUserName()
						&& !"".equals(cashierBillsVO.getContactUserName())) {
					sql += " and cu.staffname like ? ";
					parms.add("%" + cashierBillsVO.getContactUserName().trim() + "%");
				}
				if (null != cashierBillsVO.getConsultStatus() 
						&& cashierBillsVO.getConsultStatus().equals("hostStatus")) {
					sql += " and (t.consultStatus = '02' or t.consultStatus = '03')";
				} else {
					sql += " and t.consultStatus = '01'";
				}
			}
		}else{
			sql += " and t.consultStatus = '01'";
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 待营销审核跳转编辑页面
	 * 
	 * @return
	 */
	public String toMarketingPage() {
		toPage();
		return "edit";
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
		
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		costTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000030");
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		priceManageList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000032");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		
		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where companyID = ?  and cashierBillsID=? order by goodsNomber asc";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where companyID = ?  and cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
		}
	}
	
	/**
	 * JSON修改待营销审核单状态
	 */
	public String updateMarketing() {
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
		String hql = "update CashierBills set consultStatus = '"
				+ cashierBills.getConsultStatus() + "',marketer= '"
				+ sta.getStaffName()
				+ "' where companyID = ?  and cashierBillsID = ? ";
		Object[] params = { account.getCompanyID(),
				cashierBills.getCashierBillsID() };
		String hql1 = "from  CashierBills  where companyID = ?  and cashierBillsID = ? ";
		CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql1, params);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		CLogBook logbook = null;
		if (cashierBills.getConsultStatus().equals("10")) {
			logbook = logBookService.saveCLogBook(organizationID,
					"驳回待营销审核(凭证号:" + cb.getJournalNum() + ")", account);
		} else {
			logbook = logBookService.saveCLogBook(organizationID,
					"转人事审核单:(凭证号：" + cb.getJournalNum() + ")", account);
		}
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}
	
	/**
	 * 导出待营销审核管理
	 * @param : account organizationID
	 * @return : showexcel
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		sql ="select " + sql.substring(sql.indexOf(",")+1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeadingService(),
				baseBeanService.getListBeanBySqlAndParams(sql, parms));
		CLogBook logBook =logBookService.saveCLogBook(organizationID,"导出营销审核管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
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

	public List<CCode> getBillsTypeList() {
		return billsTypeList;
	}

	public void setBillsTypeList(List<CCode> billsTypeList) {
		this.billsTypeList = billsTypeList;
	}

	public Map<String, GoodsBills> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, GoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
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

	public String getBType() {
		return BType;
	}
	
	public void setBType(String type) {
		BType = type;
	}

}
