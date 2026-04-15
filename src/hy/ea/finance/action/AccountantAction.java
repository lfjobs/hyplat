package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.projectBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.bo.invoicing.CostSheetDetail;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;

/**
 * 待会计审核管理：AccountantAction
 * 
 * @author wrm
 * 
 */
public class AccountantAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
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
	private List<BaseBean> beans;
	private List<BaseBean> stafflist;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
	private List<CCode> codeRelationList;
	private List<CCode> connectionlist;
	private String search;
	private String sdate;
	private String edate;
	private String comments;
	private String cashierBillsID;
	/**
	 * 单据类别
	 */
	private List<CCode> billTypes;
	
	private String checkOutUrl;//列表页面路径

	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		return getAccountantList();
	}

	/**
	 * 待会计审核列表
	 * 
	 * @param :account.getCompanyID()
	 * @return : list
	 */
	public String getAccountantList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=? )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50", organizationID });
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<Object> list = getList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql, parms);
		// .getPageFormBySQL(
		// (null != pageForm ? pageForm.getPageNumber() : 1),
		// (pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
		// + sql.substring(sql.indexOf("from")), parms);
		return "list";
	}

	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @param :CashierBills 查询条件
	 * @version zg 2011-6-1
	 * @return ：DetachedCriteria
	 */

	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");

		String hql = "from CashierBillsVO t";

		hql += " where 1=1 ";
		hql += " and t.companyID = ? ";
		parms.add(account.getCompanyID());
		hql += " and t.organizationID = ? ";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if(null!=cashierBillsVO.getProjectName().trim()
					&&!"".equals(cashierBillsVO.getProjectName().trim())){
				hql += " and projectName like ?";
				parms.add("%"+cashierBillsVO.getProjectName().trim()+"%");
			}
			if (null != cashierBillsVO.getJournalNum().trim()
					&& !"".equals(cashierBillsVO.getJournalNum().trim())) {
				hql += " and journalNum like ? ";
				parms.add("%" + cashierBillsVO.getJournalNum().trim() + "%");
			}
			if (null != cashierBillsVO.getStaffname().trim()
					&& !"".equals(cashierBillsVO.getStaffname().trim())) {
				hql += " and staffname like ?";
				parms.add("%"+cashierBillsVO.getStaffname().trim()+"%");
			}
			if (null != cashierBillsVO.getBillsType()
					&& !"".equals(cashierBillsVO.getBillsType())) {
				hql += " and billstype=?";
				parms.add(cashierBillsVO.getBillsType());
			}
			/*if (null != cashierBillsVO.getInputName().trim()
					&& !"".equals(cashierBillsVO.getInputName().trim())) {
				hql += " and inputName like ?";
				parms.add("%"+cashierBillsVO.getInputName().trim()+"%");
			}*/
			/*if (null != cashierBillsVO.getDepartmentID().trim()
					&& !"".equals(cashierBillsVO.getDepartmentID().trim())) {
				hql += " and departmentid = ?";
				parms.add(cashierBillsVO.getDepartmentID().trim());
			}*/
			
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				hql += " and cashierDate between ? and ? ";

				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));

				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (cashierBillsVO.getStatus() != null
					&& cashierBillsVO.getStatus().equals("history")) {
				hql += " and (t.status between  ? and ? or (t.status = ? and t.accountant is not null and t.cashier is null))";
				parms.add("06");
				parms.add("07");
				parms.add("10");
			}else
			/*if (cashierBillsVO.getStatus() != null
					&& cashierBillsVO.getStatus().equals("wait"))*/ {
				hql += " and t.status = ? ";
				parms.add("05");
			}
		} else {
			hql += " and t.status = ? ";
			parms.add("05");
		}
		hql += " order by t.cashierDate desc";
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 导出待会计审核
	 * 
	 * @param : account organizationID
	 * @return : showexcel
	 */

	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(CashierBillsVO.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出待会计审核", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * JSON修改待会计审核单状态
	 */
	public String updateAccountant() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		String[] str = cashierBills.getCashierBillsID().split(",");
		List<Object[]> parmsList = new ArrayList<Object[]>();
		for (int i = 0; i < str.length; i++) {
			String hql = "update CashierBills set status = ?,accountant= ? where companyID = ?  and cashierBillsID = ? ";
			Object[] params = { cashierBills.getStatus(), sta.getStaffName(),
					account.getCompanyID(), str[i] };
			parmsList.add(params);
			String hql1 = "from  CashierBills  where companyID = ?  and cashierBillsID = ? ";
			CashierBills cb = (CashierBills) baseBeanService
					.getBeanByHqlAndParams(hql1, new Object[] {
							account.getCompanyID(),
							str[i]});
			String hql2=null;
			List<Object> params2 = new ArrayList<Object>();
			if(cb.getBillsType()!=null&&(cb.getBillsType().equals("采购单")
					||cb.getBillsType().equals("验货单")||cb.getBillsType().equals("采购入库单")
						||cb.getBillsType().equals("销售出库单"))){
				
				hql2 = "update FinancialBill set billStatus = ? where companyID = ?  and cashierBillsID = ? ";
				params2.add(cashierBills.getStatus());
				params2.add(account.getCompanyID());
				params2.add(str[i] );
			}
			parmsList.add(params2.toArray());
			beans = new ArrayList<BaseBean>();
			beans=SaveOrUpdateCheckbill(sta,cb,cashierBills.getStatus(),beans);
			CLogBook logBook = null;
			if (cashierBills.getStatus().equals("03")) {
				logBook = logBookService
						.saveCLogBook(organizationID, "改会计审核为待出纳状态:(凭证号："
								+ cb.getJournalNum() + ")", account);
			} else {
				logBook = logBookService.saveCLogBook(organizationID,
						"驳回待会计审核(凭证号:" + cb.getJournalNum() + ")", account);
			}
			beans.add(logBook);
			baseBeanService.executeHqlsByParamsList(beans,
					new String[] { hql,hql2 }, parmsList);
		}
		return "success";
	}
	
	/**
	 * 添加审核信息调用
	 * @param staff
	 * @param bills
	 * @param status
	 * @param beans
	 * @return
	 */
	public List<BaseBean> SaveOrUpdateCheckbill(Staff staff,CashierBills bills,String status,List<BaseBean> beans){
		String cashid="";
		Boolean bl=true;
		if(status.equals("10")){
			CashierBills cBills=null;
			try {
				cBills = (CashierBills)bills.cloneCashierBills();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cBills.setCashierBillsID(serverService.getServerID("cashierTally"));
			cashid=cBills.getCashierBillsID();
			cBills.setCashierBillsKey(null);
			cBills.setJournalNum(serverService.getBillID(cBills.getCompanyID()));
			cBills.setStatus("11");
			beans.add(cBills);
			List<BaseBean> goodlist=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?", new Object []{bills.getCashierBillsID() });
			for (int i = 0; i < goodlist.size(); i++) {
				GoodsBills goodsBills=(GoodsBills)goodlist.get(i);
				GoodsBills goodsBills2=null;
				try {
					goodsBills2=(GoodsBills)goodsBills.cloneGoodsBills();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goodsBills2.setCashierBillsID(cBills.getCashierBillsID());
				goodsBills2.setGoodsBillsID(serverService.getServerID("goodsbills"));
				goodsBills2.setGoodsBillsKey(null);
				beans.add(goodsBills2);
			}
		}
		BillCheck billCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=? and audittime is null", new Object[]{bills.getCashierBillsID()});
		if(billCheck==null){
			billCheck=saveCheckbill(bills,bl);
		}
		BillCheck billCheck2=null;
		try {
			billCheck2=(BillCheck)billCheck.cloneBillCheck();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		billCheck2.setCheckid(serverService.getServerID("billcheck"));
		billCheck2.setCheckkey(null);
		billCheck2.setOldBillsID(bills.getCashierBillsID());
		if(status.equals("10")){
			if(billCheck2.getBilltatus().substring(0, 4).equals("凭证管理")){
				billCheck2.setBilltatus(billCheck2.getBilltatus().substring(0, 4)+"主管审核阶段");
			}else{
				billCheck2.setBilltatus(billCheck2.getBilltatus().substring(0, 4)+"招标前审核阶段");
			}
		}else{
			billCheck2.setBilltatus(billCheck2.getBilltatus().substring(0, 4)+"出纳审核阶段");
		}
		if(!cashid.equals("")){
			billCheck2.setNewBillsID(cashid);
		}
		beans.add(billCheck2);
		billCheck.setAuditorid(staff.getStaffID());
		billCheck.setAuditorname(staff.getStaffName());
		billCheck.setAudittime(new Date());
		billCheck.setAuditorstatus(status.equals("10")?"03":"02");
		billCheck.setComments(comments);
		beans.add(billCheck);
		return beans;
	}
	
	/**
	 * 添加审核信息
	 * @param cashierBills
	 * @return
	 */
	public BillCheck saveCheckbill(CashierBills cashierBills,Boolean bl){
		BillCheck billCheck=new BillCheck();
		billCheck.setCheckid(serverService.getServerID("billcheck"));
		billCheck.setNewBillsID(cashierBills.getCashierBillsID());
		billCheck.setOldBillsID(cashierBills.getCashierBillsID());
		billCheck.setFirstBillsID(cashierBills.getCashierBillsID());
		billCheck.setJournalNum(cashierBills.getJournalNum());
		billCheck.setAuditorcompanyid(cashierBills.getCompanyID());
		billCheck.setAuditorcompanyname(cashierBills.getCompanyName());
		billCheck.setAuditororgID(cashierBills.getDepartmentID());
		billCheck.setAuditororgName(cashierBills.getDataDepotName());
		if(cashierBills.getInputid()!=null){
			billCheck.setInputid(cashierBills.getInputid());
			Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{cashierBills.getInputid()});
			billCheck.setInputname(staff.getStaffName());
		}
		billCheck.setStaffID(cashierBills.getStaffID());
		billCheck.setStaffName(cashierBills.getStaffName());
		billCheck.setBillsType(cashierBills.getBillsType());
		billCheck.setAuditorstatus("01");
		billCheck.setCashierDate(cashierBills.getCashierDate());
		if(bl){
			billCheck.setBilltatus("凭证管理会计审核阶段");
		}else{
			billCheck.setBilltatus("预算管理会计审核阶段");
		}
		return billCheck;
	}
	
	/**
	 * JSON查询审核信息
	 * @return
	 */
	public String AjxaGetCheckbill(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql="from BillCheck where newBillsID=?";
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashierBillsID.substring(0,40)});
		Map<String, Object> map=new HashMap<String, Object>();
		if(bCheck!=null){
			String hql2="from BillCheck where firstBillsID=? order by audittime desc";
			List<BaseBean> checkList=baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{bCheck.getFirstBillsID()});
			map.put("checkList", checkList);
		}else{
			map.put("checkList", null);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject obj = JSONObject.fromObject(map,jsonConfig);
		result = obj.toString();
		return "success";
	}

	/**
	 * 去待会计审核查看页面
	 * 
	 * @param :companyID，cashierBills.getCashierBillsID()修改
	 * @return : edit
	 */
	public String toSaveAccountant() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String re="";
		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID().substring(0,40) };
			String hql1 = "from GoodsBillsVO where companyID = ?  and cashierBillsID=?";
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
		if(cashierBillsVO.getStatusbill()!=null&&cashierBillsVO.getStatusbill().equals("00")){
			re="edit2";
		}else{
			re="edit";
		}
		return re;
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

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getCheckOutUrl() {
		return checkOutUrl;
	}

	public void setCheckOutUrl(String checkOutUrl) {
		this.checkOutUrl = checkOutUrl;
	}

}
