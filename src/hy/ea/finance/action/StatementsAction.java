package hy.ea.finance.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.finance.BeginningBalance;
import hy.ea.bo.history.HistoryRelation;
import hy.ea.bo.history.vo.HistoryCashierBillVO;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * StatementsAction  现金、银行日记账
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class StatementsAction {
	@Resource
	private BaseBeanService baseBeanService;
	private HistoryCashierBillVO historyVO;
	private HistoryRelation historyRelation;
	private List<BaseBean> CashierBillslists;
	private PageForm pageForm;
	private int pageNumber;
	private String companyname;
	private String organizationname;
	private String search;
	private String tt;   //判断是部门报表还是公司报表
	private String zz;   //报表状态   '00' 现金日记账  '01' 银行日记账  '02' 现金银行总账  '03' 现金支出 '04'应收账款 '05'应付账款
	private String qcye;   //期初余额
	
	/**
	 * 页面查询制单日期
	 */
	private String sdate;
	private String edate;
	private String sfdate;
	private String efdate;
	private String companyid;

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
		List<Object> parms = new ArrayList<Object>();
		String sql = "from HistoryGoodsBillVO t";
		sql+=" where 1=1";
		if(zz.equals("01")){
			sql+=" and (t.PbillsTypeID=? or t.PbillsTypeID=? or t.PbillsTypeID=? or t.PbillsTypeID=?)";
		}else{
			sql+=" and (t.PbillsTypeID=? or t.PbillsTypeID=? or t.PbillsTypeID=?)";
		}
		/*if(zz.equals("00")){//现金日记账
			parms.add("scode20130104uyj3s8t4b50000000002");
			parms.add("scode20130104uyj3s8t4b50000000003");
		}*/
		if(zz.equals("00")){//现金收入
			parms.add("scode20130104uyj3s8t4b50000000002");
		}else if(zz.equals("03")){//现金支出
			parms.add("scode20130104uyj3s8t4b50000000003");
		}else if(zz.equals("04")){//应收
			parms.add("scode20130104uyj3s8t4b50000000004");
		}else if(zz.equals("05")){//应付
			parms.add("scode20130104uyj3s8t4b50000000005");
		}else if(zz.equals("01")){//银行日记账
			parms.add("scode20130104uyj3s8t4b50000000006");
			parms.add("scode20130104uyj3s8t4b50000000007");
		}
		parms.add("scode201303255bfk6jsacr0000000003");
		parms.add("scode201303255bfk6jsacr0000000002");
		String mm="";
		if (sdate != null && !("").equals(sdate)) {
			sql += " and t.startDate between ? and ? ";
			if(sdate.substring(5, 6).equals("0")){
				mm =sdate.substring(6, 7);
			}else{
				mm +=  sdate.substring(5, 7);
			}
			int tt=Utilities.getDayMouth(Integer.parseInt(sdate.substring(0, 4)), Integer.parseInt(mm));
			String ss=sdate + "-01 00:00:00";
			String ee=sdate +"-"+tt+ " 23:59:59";
			parms.add(Utilities.getDateFromString(ss,"yyyy-MM-dd"));
			parms.add(Utilities.getDateFromString(ee,"yyyy-MM-dd"));
		}
		sql+=" and t.companyID=?";
		if(companyid!=null&&!companyid.equals("")){
			parms.add(companyid);
		}else{
			parms.add(account.getCompanyID());
		}
		sql+=" order by t.archivesDate,t.archivesNum";
		result.add(sql);
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
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
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
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toPage() {
		return "toPage";
	}
	
	/**
	 * 根据条件查询(保存条件)
	 * 
	 * @param cashierVo
	 * @return getCashierTallyList()
	 */
	public String toSummaryPage() {
		return "toSummaryPage";
	}
	
	/**
	 * 根据条件查询(调用)
	 * @return
	 */
	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		String sql = "from HistoryCashierBillVO t";
		// 用来保存参数
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		sql += " where t.companyID = ? ";
		parms.add(account.getCompanyID());
		sql+=" and (t.PbillsTypeID=? or t.PbillsTypeID=?)";
		if(zz.equals("00")){//现金日记账
			parms.add("scode20130104uyj3s8t4b50000000002");
			parms.add("scode20130104uyj3s8t4b50000000003");
		}else if(zz.equals("01")){//银行日记账
			parms.add("scode20130104uyj3s8t4b50000000006");
			parms.add("scode20130104uyj3s8t4b50000000007");
		}
			
		if (search != null && search.equals("search")) {
			historyVO = (HistoryCashierBillVO) session.get("historyVO");
			historyRelation = (HistoryRelation) session.get("historyRelation");
			if (sfdate != null && efdate != null && !("").equals(sfdate)&& !("").equals(efdate)) {
				sql += " and t.startDate between ? and ? ";
				String ss=sfdate + " 00:00:00";
				String ee=efdate + " 23:59:59";
				parms.add(Utilities.getDateFromString(ss,"yyyy-MM-dd hh:mm:ss"));
				parms.add(Utilities.getDateFromString(ee,"yyyy-MM-dd hh:mm:ss"));
			}
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
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
		if(companyid==null||companyid.equals("")){
			companyid=account.getCompanyID();
		}
		List<Object> list = getStatementsList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		CashierBillslists=baseBeanService.getListBeanByHqlAndParams(hql, parms);
		//公司
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { account.getCompanyID() });
		BeginningBalance beginningBalance=(BeginningBalance)baseBeanService.getBeanByHqlAndParams("from BeginningBalance where Customizedate=? and companyID=?", new Object[]{Utilities.getDateFromString(sdate.substring(0,7)+"-01", "yyyy-MM-dd"),companyid});
		if(beginningBalance!=null){
			if(zz.equals("00")){
				qcye=beginningBalance.getCashBalance();
			}else if(zz.equals("01")){
				qcye=beginningBalance.getBankBalance();
			}
		}
		companyname = company.getCompanyName();
		return "tostatements";
	}


	public HistoryCashierBillVO getHistoryVO() {
		return historyVO;
	}


	public void setHistoryVO(HistoryCashierBillVO historyVO) {
		this.historyVO = historyVO;
	}


	public HistoryRelation getHistoryRelation() {
		return historyRelation;
	}


	public void setHistoryRelation(HistoryRelation historyRelation) {
		this.historyRelation = historyRelation;
	}


	public List<BaseBean> getCashierBillslists() {
		return CashierBillslists;
	}


	public void setCashierBillslists(List<BaseBean> cashierBillslists) {
		CashierBillslists = cashierBillslists;
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


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}


	public String getTt() {
		return tt;
	}


	public void setTt(String tt) {
		this.tt = tt;
	}


	public String getZz() {
		return zz;
	}


	public void setZz(String zz) {
		this.zz = zz;
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

	public String getQcye() {
		return qcye;
	}

	public void setQcye(String qcye) {
		this.qcye = qcye;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
}

