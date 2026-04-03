package hy.ea.company.action.finance;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.history.HistoryCashierBills;
import hy.ea.bo.history.HistoryGoodsBills;
import hy.ea.bo.history.HistoryRelation;
import hy.ea.bo.history.vo.HistoryCashierBillVO;
import hy.ea.bo.history.vo.HistoryGoodsBillVO;
import hy.ea.bo.human.COrganization;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * ArchivesAction 集团单据归档
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class ArchivesSummaryAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	public InputStream excelStream;
	private String result;
	private CashierBillsVO cashierBillsVO;
	private HistoryCashierBillVO historyVO;
	private HistoryCashierBills historyCashierBills;
	private HistoryRelation historyRelation;
	private Map<String, CashierBills> cashierBillsmap;
	private List<BaseBean> CashierBillslists;
	private PageForm pageForm;
	private int pageNumber;
	private String companyname;
	private String organizationname;
	private String search;
	private String tt;
	private Map<String, HistoryGoodsBills> goodsmap;
	private Map<String, HistoryCashierBillVO> cashierBillsmaps;
	private String nuDate; // 部门查询时间
	private String nuvDate; //公司查询时间
	
	/**
	 * 页面查询制单日期
	 */
	private String sdate;
	private String edate;
	private String sfdate;
	private String efdate;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
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
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	/**
	 * 仓库类别
	 */
	private List<CCode> typelist;
	/**
	 * 个人往来关系
	 */
	private List<CCode> codeRelationList;
	/**
	 * 物品类别
	 */
	private List<CCode> codeList;
	
	/**
	 * 根据生成报表，生成传值字段
	 * ct
	 */
	private String chuanzhi;
	private String pan;
	/**
	 * (报表)根据条件查询(调用)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public List<Object> getStatementsList(){
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		//String organizationID = (String) session.get("organizationID");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID());
		List<Object> parms = new ArrayList<Object>();
		String hql = "from HistoryGoodsBillVO t where 1=1";
		if (sfdate != null && efdate != null && !("").equals(sfdate)&& !("").equals(efdate)) {
			hql += " and t.endDate between ? and ? ";
			String ss=sfdate + " 00:00:00";
			String ee=efdate + " 23:59:59";
			parms.add(Utilities.getDateFromString(ss,"yyyy-MM-dd hh:mm:ss"));
			parms.add(Utilities.getDateFromString(ee,"yyyy-MM-dd hh:mm:ss"));
		}
		if (null != historyVO.getCompanyID()
				&& !"".equals(historyVO.getCompanyID())) {
			hql += " and t.companyID = ? ";
				parms.add(historyVO.getCompanyID());
		} else {
			if (cids.size() == 1) {
				hql += " and t.companyID = ? ";
				parms.add(cids.get(0));
			} else {
				hql += " and (t.pcompanyID = ? or t.companyID = ? )";
				parms.add(account.getCompanyID());
				parms.add(account.getCompanyID());
			}
		}
		if (null != historyVO.getDepartmentID()
				&& !"".equals(historyVO.getDepartmentID())
				&& !account.getCompanyID().equals(
						historyVO.getDepartmentID())&&historyVO.getDepartmentID().substring(0,12).equals("organization")) {
			hql += " and t.departmentID = ?";
			parms.add(historyVO.getDepartmentID().trim());
		}
		//单据类别
		if (null != historyVO.getBillsType()
				&& !"".equals(historyVO.getBillsType())) {
			hql += " and t.billsType = ?";
			parms.add(historyVO.getBillsType().trim());
		}
		/*//责任人
		if (null != historyVO.getStaffID()
				&& !"".equals(historyVO.getStaffID())) {
			hql += " and t.staffID = ?";
			parms.add(historyVO.getStaffID().trim());
		}*/
		//单位往来关系
		if (null != historyVO.getContactConnections()
				&& !"".equals(historyVO.getContactConnections())) {
			hql += " and t.contactConnections = ? ";
			parms.add(historyVO.getContactConnections().trim());
		}
		//个人往来关系
		if (null != historyVO.getPhone()
				&& !"".equals(historyVO.getPhone())) {
			hql += " and t.phone = ?";
			parms.add(historyVO.getPhone().trim());
		}
		//往来单位
		if (null != historyVO.getCcompanyname()
				&& !"".equals(historyVO.getCcompanyname())) {
			hql += " and t.ccompanyname like ? ";
			parms.add("%" + historyVO.getCcompanyname().trim() + "%");
		}
		//往来个人
		if (null != historyVO.getContactUserName()
				&& !"".equals(historyVO.getContactUserName())) {
			hql += " and t.contactUserName like ? ";
			parms.add("%" + historyVO.getContactUserName().trim() + "%");
		}
		chuanzhi="&historyVO.departmentID="+historyVO.getDepartmentID().trim()+"&historyVO.companyID="+historyVO.getCompanyID().trim()
				  +"&historyVO.billsType="+historyVO.getBillsType().trim()+"&historyVO.ccompanyname="+historyVO.getCcompanyname().trim()+"&historyVO.contactUserName="+historyVO.getContactUserName().trim()+"&historyVO.contactConnections="+historyVO.getContactConnections().trim()+
					"&historyVO.phone="+historyVO.getPhone().trim();
		//+"&historyVO.staffID="+historyVO.getStaffID().trim();
		hql+=" order by t.archivesDate,t.archivesNum";
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 单据归档列表查询
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getArchivesList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=? )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50", organizationID });
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
		
		return "list";
	}
	
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("historyVO",
				historyVO);
		ActionContext.getContext().getSession().put("historyRelation",
				historyRelation);
		return getArchivesList();
	}
	
	
	
	/**
	 * 根据条件查询(调用)
	 * @return
	 */
	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		//String organizationID =(String)ActionContext.getContext().getSession().get("organizationID");
		ArrayList<String> cids = companyserverService
				.getCompanyAndItsChildrenIDs(account.getCompanyID());
		String hql = " from HistoryCashierBillVO t";
		// 用来保存参数
		List<Object> parms = new ArrayList<Object>();
		hql += " where 1=1 ";
		if (search != null && search.equals("search")) {
			historyVO = (HistoryCashierBillVO) session.get("historyVO");
			historyRelation = (HistoryRelation) session.get("historyRelation");
			if (null != historyVO.getStaffID()
					&& !"".equals(historyVO.getStaffID())) {
				hql += " and t.staffID = ?";
				parms.add(historyVO.getStaffID().trim());
			}
			if (null != historyVO.getCompanyID()
					&& !"".equals(historyVO.getCompanyID())) {
				hql += " and t.companyID = ? ";
					parms.add(historyVO.getCompanyID());
			} else {
				if (cids.size() == 1) {
					hql += " and t.companyID = ? ";
					parms.add(cids.get(0));
				} else {
					hql += " and (t.pcompanyID = ? or t.companyID = ? )";
					parms.add(account.getCompanyID());
					parms.add(account.getCompanyID());
				}
			}
			if (null != historyVO.getDepartmentID()
					&& !"".equals(historyVO.getDepartmentID())
					&& !account.getCompanyID().equals(
							historyVO.getDepartmentID())) {
				hql += " and t.departmentID = ?";
				parms.add(historyVO.getDepartmentID().trim());
			}
			if (null != historyVO.getBillsType()
					&& !"".equals(historyVO.getBillsType())) {
				hql += " and t.billsType = ?";
				parms.add(historyVO.getBillsType().trim());
			}
			if (null != historyVO.getJournalNum()
					&& !"".equals(historyVO.getJournalNum())) {
				hql += " and t.journalNum like ? ";
				parms.add("%" + historyVO.getJournalNum().trim() + "%");
			}
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				hql += " and t.cashierDate between ? and ? ";
				parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(edate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (null != historyVO.getContactConnections()
					&& !"".equals(historyVO.getContactConnections())) {
				hql += " and t.contactConnections = ? ";
				parms.add(historyVO.getContactConnections().trim());
			}
			if (null != historyVO.getPhone()
					&& !"".equals(historyVO.getPhone())) {
				hql += " and t.phone = ?";
				parms.add(historyVO.getPhone().trim());
			}
			if (null != historyVO.getCcompanyname()
					&& !"".equals(historyVO.getCcompanyname())) {
				hql += " and t.companyname like ? ";
				parms.add("%" + historyVO.getCcompanyname().trim() + "%");
			}
			if (null != historyVO.getContactUserName()
					&& !"".equals(historyVO.getContactUserName())) {
				hql += " and t.staffname like ? ";
				parms.add("%" + historyVO.getContactUserName().trim() + "%");
			}
			// 关联表
			if (null != historyRelation.getDeparchivesNum()
					&& !"".equals(historyRelation.getDeparchivesNum())) {
				hql += " and t.deparchivesNum like ? ";
				parms.add("%" + historyRelation.getDeparchivesNum().trim() + "%");
			}
			if (null != nuDate && !"".equals(nuDate)) {
				hql += " and t.deparchivesDate between ? and ? ";
				parms.add(Utilities.getDateFromString(nuDate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(nuDate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
			if (null != historyRelation.getArchivesNum()
					&& !"".equals(historyRelation.getArchivesNum())) {
				hql += " and t.archivesNum like ? ";
				parms.add("%" + historyRelation.getArchivesNum().trim() + "%");
			}
			if (null != nuvDate && !"".equals(nuvDate)) {
				hql += " and t.archivesDate between ? and ? ";
				parms.add(Utilities.getDateFromString(nuvDate + " 00:00:00",
						"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(nuvDate + " 23:59:59",
						"yyyy-MM-dd hh:mm:ss"));
			}
		}else {
				if (cids.size() == 1) {
					hql += " and t.companyID = ? ";
					parms.add(cids.get(0));
				} else {
					hql += " and (t.pcompanyID = ? or t.companyID = ? )";
					parms.add(account.getCompanyID());
					parms.add(account.getCompanyID());
				}
		}
		hql += " order by t.cashierDate desc";
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 单据归档生成报表
	 * 
	 * @param :
	 *            account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String toStatements(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID =(String)ActionContext.getContext().getSession().get("organizationID");
		List<Object> list = getStatementsList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		CashierBillslists=baseBeanService.getListBeanByHqlAndParams(hql, parms);
		//公司
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { account.getCompanyID() });
		if(tt.equals("z")){
			String orghql = "from COrganization where companyID=? and organizationID=?";
			COrganization cOrganization = (COrganization) baseBeanService
					.getBeanByHqlAndParams(orghql, new Object[] {account.getCompanyID(),organizationID});
			organizationname = cOrganization.getOrganizationName();
		}
		companyname = company.getCompanyName();
		return "tostatements";
	}
	
	
	
	 /**
	 *
	 * 查看
	 * 
	 * @return
	 */
	public String toCashier() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		//单据类别
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		//费用类别
		costTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000030");
		//付款方式
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		//物流方式
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		//单价管理
		priceManageList = codeService.getCCodeListByPID(companyID,
		"scode20101101dfs3uhdprp0000000032");
		// contactRegardList =
		// codeService.getCCodeListByPID(companyID,"scode20110106hfjes5ucxp0000000017");
		//仓库类别
		typelist = codeService.getCCodeListByPID(companyID,
		"scode20101014v5zed7cukk0000000004");
		//单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
		"scode20110224xpd2t2jvda0000000002");
		//个人往来关系
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		//物品类别
		codeList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20101014v5zed7cukk0000000002");
		if (historyCashierBills != null) {
			Object[] params1 = {historyCashierBills.getCashierBillsID() };
			String hql1 = "from HistoryGoodsBillVO where cashierBillsID=?";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from HistoryCashierBillVO where cashierBillsID=?";
			historyVO = (HistoryCashierBillVO) baseBeanService.getBeanByHqlAndParams(hql, params1);
			String hql2 = " from HistoryCashierBills where cashierBillsID=?";
			historyCashierBills = (HistoryCashierBills) baseBeanService.getBeanByHqlAndParams(hql2, params1);
		}
		if(tt.equals("x")){
			return "edit";
		}else if(tt.equals("z")){
			return "edit1";
		}
		return null;
	}
	
	//添加导出（生成报表）
	@SuppressWarnings("unused")
	public String toshow(){
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getStatementsList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.goodsShowExcels(HistoryGoodsBillVO.columnHeading(),
				baseBeanService.getListBeanByHqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出集团归档单据管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Map<String, CashierBills> getCashierBillsmap() {
		return cashierBillsmap;
	}

	public void setCashierBillsmap(Map<String, CashierBills> cashierBillsmap) {
		this.cashierBillsmap = cashierBillsmap;
	}

	public HistoryCashierBills getHistoryCashierBills() {
		return historyCashierBills;
	}

	public void setHistoryCashierBills(HistoryCashierBills historyCashierBills) {
		this.historyCashierBills = historyCashierBills;
	}

	public HistoryRelation getHistoryRelation() {
		return historyRelation;
	}

	public void setHistoryRelation(HistoryRelation historyRelation) {
		this.historyRelation = historyRelation;
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

	public List<BaseBean> getCashierBillslists() {
		return CashierBillslists;
	}

	public void setCashierBillslists(List<BaseBean> cashierBillslists) {
		CashierBillslists = cashierBillslists;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public HistoryCashierBillVO getHistoryVO() {
		return historyVO;
	}

	public void setHistoryVO(HistoryCashierBillVO historyVO) {
		this.historyVO = historyVO;
	}

	public Map<String, HistoryGoodsBills> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, HistoryGoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public List<CCode> getBillsTypeList() {
		return billTypes;
	}

	public void setBillsTypeList(List<CCode> billsTypeList) {
		this.billTypes = billsTypeList;
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

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
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

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public Map<String, HistoryCashierBillVO> getCashierBillsmaps() {
		return cashierBillsmaps;
	}

	public void setCashierBillsmaps(Map<String, HistoryCashierBillVO> cashierBillsmaps) {
		this.cashierBillsmaps = cashierBillsmaps;
	}

	public String getNuDate() {
		return nuDate;
	}

	public void setNuDate(String nuDate) {
		this.nuDate = nuDate;
	}

	public String getNuvDate() {
		return nuvDate;
	}

	public void setNuvDate(String nuvDate) {
		this.nuvDate = nuvDate;
	}

	public String getSfdate() {
		return sfdate;
	}

	public void setSfdate(String sfdate) {
		this.sfdate = sfdate;
	}

	public String getEfdate() {
		return efdate;
	}

	public void setEfdate(String efdate) {
		this.efdate = efdate;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getChuanzhi() {
		return chuanzhi;
	}

	public void setChuanzhi(String chuanzhi) {
		this.chuanzhi = chuanzhi;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
}
