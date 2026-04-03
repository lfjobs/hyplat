package hy.ea.invoicing.action.voucher;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.invoicing.voucher.DtInvVoucher;
import hy.ea.bo.invoicing.voucher.DtInvVouchers;
import hy.ea.service.CLogBookService;
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
 * 凭证序时账报表管理
 * 陈婷
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class VoucherSequenceAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private InputStream excelStream;
	private int pageNumber;
	private String search;
	private String result;
	private DtInvVoucher voucher;
	private DtInvVouchers vouchers;
	private String sdate;//开始时间	
	private String edate;//结束时间
	private String kemu;//用于传值科目
	private String keid;//用于传值科目id
	private String vnum;//用于传值
	private List<BaseBean> sublist;//科目id
	private String level;
	
	/**
	 * 公司--查询页面（调用）
	 * @return
	 */
	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql="select fu.companyname,fu.tallyDate,fu.vouchernum,zi.subjectsname,zi.subjectsid, zi.reasonthing, zi.loan,zi.forloan,fu.makepeople " +
				" from  dt_inv_vouchers  zi" +
				" left join dt_inv_voucher fu on fu.voucherid = zi.voucherid";
		 sql += " where fu.companyid  = ? ";
		 parms.add(account.getCompanyID());
		 sql += " and fu.status =?";
		 parms.add("10");
		if (search != null && search.equals("search")) {
			DtInvVouchers vouchers = (DtInvVouchers) session.get("tablesearch");
			if (vouchers.getSubjectsid() != null && !vouchers.getSubjectsid() .trim().equals("")) {
				sql += " and zi.subjectsID like ? ";
				parms.add("%" + vouchers.getSubjectsid().trim() + "%");
				keid=vouchers.getSubjectsid();
			}
			if (vouchers.getSubjectsname() != null && !vouchers.getSubjectsname().equals("")) {
				sql += " and zi.subjectsname = ? ";
				parms.add(vouchers.getSubjectsname());
				kemu=vouchers.getSubjectsname();
			}
			if (vnum != null && !vnum.equals("")) {
				sql += " and fu.vouchernum = ? ";
				parms.add(vnum);
			}
			if (sdate != null && edate != null && !"".equals(sdate) && !"".equals(edate)) {
				sql += " and fu.tallyDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
		}
		sql += "order by fu.tallyDate,zi.goodsID desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	public String getVsequenceList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from CSubjects s where s.companyID=? order by subjectsNumbers";
		sublist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "+ sql.substring(sql.indexOf("from")), parms);
		return "list";
	}
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch", vouchers);
		return getVsequenceList();
	}
	/**
	 * 公司--打印预览
	 * @return
	 */
	public String toPrint(){
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 200 : pageNumber), sql, "select count(1) "+ sql.substring(sql.indexOf("from")), parms);
		return "print";
	}
	/**
	 * 公司--导出功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel(){
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select fu.companyname,fu.tallyDate,fu.vouchernum,zi.subjectsname,zi.subjectsid, zi.reasonthing, zi.loan,zi.forloan,fu.makepeople " +
				" from  dt_inv_vouchers  zi" +
				" left join dt_inv_voucher fu on fu.voucherid = zi.voucherid";
		 sql += " where fu.companyid  = ? ";
		 parms.add(account.getCompanyID());
		 sql += " and fu.status =?";
		 parms.add("10");
		if (keid != null && ! keid.equals("")) {
			sql += " and zi.subjectsID = ? ";
			parms.add( keid );
		}
		if (kemu!= null && ! kemu.equals("")) {
			sql += " and zi.subjectsname = ? ";
			parms.add(kemu);
		}
		if (vnum != null && !vnum.equals("")) {
			sql += " and fu.vouchernum = ? ";
			parms.add(vnum);
		}
		if (sdate != null && edate != null && !"".equals(sdate) && !"".equals(edate)) {
			sql += " and fu.tallyDate between ? and ?";
			parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		sql += "order by fu.tallyDate,zi.goodsID desc";
		List<BaseBean> lists=baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
		//excelStream = excelService.showExcel(DtInvVoucher.columnHeading1(),lists);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出序时报表管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**************************************************************集团汇总**********************************************************/
	/**
	 * 集团--查询页面
	 * @return
	 */
	public List<Object> getCList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(level==null){
			level=account.getCompanyID();
		}
		String sql="select fu.companyname,fu.tallyDate,fu.vouchernum,zi.subjectsname,zi.subjectsid, zi.reasonthing, zi.loan,zi.forloan,fu.makepeople " +
				" from  dt_inv_vouchers  zi" +
				" left join dt_inv_voucher fu on fu.voucherid = zi.voucherid";
		 sql += " where fu.companyid  = ? ";
		 parms.add(level);
		 sql += " and fu.status =?";
		 parms.add("10");
		if (search != null && search.equals("search")) {
			DtInvVouchers vouchers = (DtInvVouchers) session.get("tablesearch");
			if (vouchers.getSubjectsid() != null && !vouchers.getSubjectsid() .trim().equals("")) {
				sql += " and zi.subjectsID like ? ";
				parms.add("%" + vouchers.getSubjectsid().trim() + "%");
				keid=vouchers.getSubjectsid();
			}
			if (vouchers.getSubjectsname() != null && !vouchers.getSubjectsname().equals("")) {
				sql += " and zi.subjectsname = ? ";
				parms.add(vouchers.getSubjectsname());
				kemu=vouchers.getSubjectsname();
			}
			if (vnum != null && !vnum.equals("")) {
				sql += " and fu.vouchernum = ? ";
				parms.add(vnum);
			}
			if (sdate != null && edate != null && !"".equals(sdate) && !"".equals(edate)) {
				sql += " and fu.tallyDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
		}
		sql += "order by fu.tallyDate,zi.goodsID desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	public String getCVsequenceList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from CSubjects s where s.companyID=? order by subjectsNumbers";
		sublist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		List<Object> list = getCList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "+ sql.substring(sql.indexOf("from")), parms);
		return "clist";
	}
	public String toCSearch(){
		ActionContext.getContext().getSession().put("tablesearch", vouchers);
		return getCVsequenceList();
	}
	/**
	 * 集团--打印预览
	 * @return
	 */
	public String toCPrint(){
		List<Object> list = getCList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 200 : pageNumber), sql, "select count(1) "+ sql.substring(sql.indexOf("from")), parms);
		return "print";
	}
	/**
	 * 集团--导出功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String CshowExcel(){
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(level==null){
			level=account.getCompanyID();
		}
		String organizationID = session.get("organizationID").toString();
		String sql="select fu.companyname,fu.tallyDate,fu.vouchernum,zi.subjectsname,zi.subjectsid, zi.reasonthing, zi.loan,zi.forloan,fu.makepeople " +
				" from  dt_inv_vouchers  zi" +
				" left join dt_inv_voucher fu on fu.voucherid = zi.voucherid";
		 sql += " where fu.companyid  = ? ";
		 parms.add(level);
		 sql += " and fu.status =?";
		 parms.add("10");
		if (keid != null && ! keid.equals("")) {
			sql += " and zi.subjectsID = ? ";
			parms.add( keid );
		}
		if (kemu!= null && ! kemu.equals("")) {
			sql += " and zi.subjectsname = ? ";
			parms.add(kemu);
		}
		if (vnum != null && !vnum.equals("")) {
			sql += " and fu.vouchernum = ? ";
			parms.add(vnum);
		}
		if (sdate != null && edate != null && !"".equals(sdate) && !"".equals(edate)) {
			sql += " and fu.tallyDate between ? and ?";
			parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		sql += "order by fu.tallyDate,zi.goodsID desc";
		List<BaseBean> lists=baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
		//excelStream = excelService.showExcel(DtInvVoucher.columnHeading1(),lists);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出序时报表管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}
	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
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

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
	
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public DtInvVoucher getVoucher() {
		return voucher;
	}
	public void setVoucher(DtInvVoucher voucher) {
		this.voucher = voucher;
	}
	public DtInvVouchers getVouchers() {
		return vouchers;
	}
	public void setVouchers(DtInvVouchers vouchers) {
		this.vouchers = vouchers;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getKemu() {
		return kemu;
	}
	public void setKemu(String kemu) {
		this.kemu = kemu;
	}
	public List<BaseBean> getSublist() {
		return sublist;
	}
	public void setSublist(List<BaseBean> sublist) {
		this.sublist = sublist;
	}
	public String getKeid() {
		return keid;
	}
	public void setKeid(String keid) {
		this.keid = keid;
	}
	public String getVnum() {
		return vnum;
	}
	public void setVnum(String vnum) {
		this.vnum = vnum;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}