package hy.ea.finance.action.BenDis;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.CashierBills;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;


/**
 * 微分金销售出库单
 */
public class SalesManagementAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private WarehouseService warehouseService;
	private InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String type;
	private CashierBills cashierBills;

	/*
	 *获取主页面信息(查询当前供应商销售出库信息)
	 */
	public String getHomePageInformationList() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String sdate=re.getParameter("sdate");
		String edate=re.getParameter("edate");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();   
		calendar.add(Calendar.MONTH, -1);
		Date date=df.parse(df.format(calendar.getTime()));
		List<Object> obj=new ArrayList<Object>(); 
		String sql1="select ca.cashierbillsid,ca.journalnum,ca.jnumorder,' - ',f.drdepotid,f.drdepotname,' -- ',ca.staffid,ca.staffname,ca.cashierdate";
		/*String sql2=" from dtcashierbills ca left join dt_inv_fb f on ca.cashierbillsid = f.cashierbillsid" +
				" left join dtDepotManage d on f.drdepotid = d.depotid where exists (select c.journalnum" +
				" from dtcashierbills c where ca.jnumorder = c.journalnum and exists (select o.oa_bill_id" +
				" from DT_ORDER_BILL_ADD o where o.oa_bill_id = c.cashierbillsid and exists (select t.sccId" +
				" from T_ESHOP_CUSCOM t where t.sccId = o.oa_sccid ";
		if(!"".equals(re.getParameter("type"))&&re.getParameter("type")!=null){
			sql2+=" and t.cusType=?";
			obj.add(re.getParameter("type"));
		}
		sql2+=" connect by t.Superioragent = prior t.account" +
				" start with t.staffid = ?))) and ca.billstype = ? and ca.status = ? and ca.cashierdate>? and ca.wfStatus1 is not null";
		obj.add(account.getStaffID());*/
		String sql2=" from dtcashierbills ca left join dt_inv_fb f on ca.cashierbillsid = f.cashierbillsid" +
				" left join dtDepotManage d on f.drdepotid = d.depotid where exists (select c.journalnum" +
				" from dtcashierbills c where ca.jnumorder = c.journalnum and exists (select o.oa_bill_id" +
				" from DT_ORDER_BILL_ADD o where o.oa_bill_id = c.cashierbillsid and o.oa_gys_id=?))" +
				" and ca.billstype = ? and ca.status = ? ";
		obj.add(account.getCompanyID());obj.add("销售出库单");obj.add("19");
		if(cashierBills!=null){
			if(cashierBills.getJournalNum()!=null&&!"".equals(cashierBills.getJournalNum())){
				sql2+=" and ca.journalnum like ?";
				obj.add("%"+cashierBills.getJournalNum()+"%");
			}
			if(cashierBills.getjNumOrder()!=null&&!"".equals(cashierBills.getjNumOrder())){
				sql2+=" and ca.jnumorder like ?";
				obj.add("%"+cashierBills.getjNumOrder()+"%");
			}
			if(cashierBills.getStaffName()!=null&&!"".equals(cashierBills.getStaffName())){
				sql2+=" and ca.staffname like ?";
				obj.add("%"+cashierBills.getStaffName()+"%");
			}
		}
		if(sdate!=null&&!"".equals(sdate)){
			sql2+=" and ca.cashierdate>=?";
			obj.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(sdate));
		}
		if(edate!=null&&!"".equals(edate)){
			sql2+=" and ca.cashierdate<=?";
			obj.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(edate));
		}
		if((sdate==null||"".equals(sdate))&&(edate==null||"".equals(edate))){
			sql2+=" and ca.cashierdate>?";
			obj.add(date);
		}
		sql2+=" order by ca.cashierdate desc";
		String hql="from TEshopCusCom where staffid=?";
		TEshopCusCom te=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
		re.setAttribute("te",te);
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql1+sql2, "select count(*) "+sql2, obj.toArray());
		return "pageData";
	}
	
	/*
	 *获取主页面信息(销售出库信息汇总)
	 */
	public String getCollectInformationList() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();   
		calendar.add(Calendar.MONTH, -1);
		Date date=df.parse(df.format(calendar.getTime()));
		List<Object> obj=new ArrayList<Object>(); 
		String sql1="select ca.cashierbillsid,ca.journalnum,ca.jnumorder,' - ',f.drdepotid,f.drdepotname,' -- ',ca.staffid,ca.staffname,ca.cashierdate";
		/*String sql2=" from dtcashierbills ca left join dt_inv_fb f on ca.cashierbillsid = f.cashierbillsid" +
				" left join dtDepotManage d on f.drdepotid = d.depotid where exists (select c.journalnum" +
				" from dtcashierbills c where ca.jnumorder = c.journalnum and exists (select o.oa_bill_id" +
				" from DT_ORDER_BILL_ADD o where o.oa_bill_id = c.cashierbillsid and exists (select t.sccId" +
				" from T_ESHOP_CUSCOM t where t.sccId = o.oa_sccid ";
		if(!"".equals(re.getParameter("type"))&&re.getParameter("type")!=null){
			sql2+=" and t.cusType=?";
			obj.add(re.getParameter("type"));
		}
		sql2+=" connect by t.Superioragent = prior t.account" +
				" start with t.staffid = ?))) and ca.billstype = ? and ca.status = ? and ca.cashierdate>? and ca.wfStatus1 is not null";
		obj.add(account.getStaffID());*/
		String sql2=" from dtcashierbills ca left join dt_inv_fb f on ca.cashierbillsid = f.cashierbillsid" +
				" left join dtDepotManage d on f.drdepotid = d.depotid where exists (select c.journalnum" +
				" from dtcashierbills c where ca.jnumorder = c.journalnum and" +
				" ca.billstype = ? and ca.status = ? and ca.cashierdate>?) ";
		obj.add("销售出库单");obj.add("19");obj.add(date);
		if(cashierBills!=null){
			if(cashierBills.getJournalNum()!=null&&!"".equals(cashierBills.getJournalNum())){
				sql2+=" and ca.journalnum like ?";
				obj.add("%"+cashierBills.getJournalNum()+"%");
			}
			if(cashierBills.getjNumOrder()!=null&&!"".equals(cashierBills.getjNumOrder())){
				sql2+=" and ca.jnumorder like ?";
				obj.add("%"+cashierBills.getjNumOrder()+"%");
			}
			if(cashierBills.getStaffName()!=null&&!"".equals(cashierBills.getStaffName())){
				sql2+=" and ca.staffname like ?";
				obj.add("%"+cashierBills.getStaffName()+"%");
			}
		}
		String hql="from TEshopCusCom where staffid=?";
		TEshopCusCom te=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
		re.setAttribute("te",te);
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql1+sql2, "select count(*) "+sql2, obj.toArray());
		return "pageData";
	}
	
	/*
	 * 导出EXCEL表格
	 */
	@SuppressWarnings("unchecked")
	public String exportExcelTable() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();   
		calendar.add(Calendar.MONTH, -1);
		Date date=df.parse(df.format(calendar.getTime()));
		String title=re.getParameter("title");
		String[] str=re.getParameter("str").split(",");
		List<Object> obj=new ArrayList<Object>(); 
		String sql1="select ca.journalnum,ca.jnumorder,' - ',f.drdepotname,' -- ',ca.staffname,ca.cashierdate";
		String sql2=" from dtcashierbills ca left join dt_inv_fb f on ca.cashierbillsid = f.cashierbillsid where exists (select c.journalnum" +
				" from dtcashierbills c where ca.jnumorder = c.journalnum and " +
				"ca.billstype = ? and ca.status = ? and ca.cashierdate>?)";	
		obj.add("销售出库单");obj.add("19");obj.add(date);
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql1+sql2, obj.toArray());
		excelStream=warehouseService.OutOrderExcel(title, str, list);
		return "showexcel";
	}
	
	/*
	 * 获取打印或预览页面
	 */
	public String getPreviewPageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String cashierBillsId=cashierBills.getCashierBillsID();
		List<Object> obj=new ArrayList<Object>(); 
		String sql="select ca.cashierbillsid,ca.journalnum,ca.jnumorder,' - ',f.drdepotid," +
				"f.drdepotname,' -- ',ca.staffid,ca.staffname,ca.cashierdate,cc.ctUserName"+
				" from dtcashierbills ca left join dt_inv_fb f on ca.cashierbillsid = f.cashierbillsid" +
				" left join dtDepotManage d on f.drdepotid = d.depotid " +
				" left join dtcashierbills cc on cc.journalnum=ca.jnumorder " +
				" where  ca.billstype = ? and ca.status = ? and ca.cashierbillsid=?";
		obj.add("销售出库单");obj.add("19");obj.add(cashierBillsId);
		Object[] cashi=(Object[])baseBeanService.getObjectBySqlAndParams(sql, obj.toArray());
		String hql="from GoodsBills where cashierBillsID=?";
		List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBillsId});
		re.setAttribute("list",goodsList);re.setAttribute("cashierBillsId", cashierBillsId);
		re.setAttribute("cashi",cashi);
		re.setAttribute("companyName", account.getCompanyName());
		if("01".equals(type)){
			return "toSee";
		}else{
			return "previe";
		}
	}
	
	@SuppressWarnings("unchecked")
	public String piliangdayin(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<Object> obj=new ArrayList<Object>();
		String sql="select ca.cashierbillsid,ca.journalnum,ca.jnumorder,' - ',f.drdepotid,f.drdepotname," +
				"' -- ',ca.staffid,ca.staffname,ca.cashierdate,cc.ctUserName  from dtcashierbills ca" +
				" left join dt_inv_fb f on ca.cashierbillsid = f.cashierbillsid" +
				" left join dtDepotManage d on f.drdepotid = d.depotid" +
				" left join dtcashierbills cc on cc.journalnum = ca.jnumorder" +
				" left join dtgoodsbills g on g.cashierbillsid=ca.cashierbillsid" +
				" left join dt_inv_stockinvt s on s.goodsbillsid=g.goodsbillsid" +
				" where ca.billstype = ? and ca.status = ? and s.goodsname=?  and s.type=?" +
				" and s.warehousename=? and s.companyid=?";
		obj.add("销售出库单");obj.add("19");obj.add("抱国醇(一箱6瓶)");
		obj.add("01");obj.add("销售库");
		obj.add("company201009046vxdyzy4wg0000000025");
		List<Object> array=baseBeanService.getListBeanBySqlAndParams(sql, obj.toArray());
		for(int i=0;i<array.size();i++){
			Object[] objs=(Object[])array.get(i);
			String hql="from GoodsBills where cashierBillsID=?";
			List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{objs[0]});
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("obj",objs);
			map.put("list",goodsList);
			list.add(map);
		}
		ServletActionContext.getRequest().setAttribute("arrayList", list);
		return "piliangdayin";
	}
	
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public CashierBills getCashierBills() {
		return cashierBills;
	}
	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}	
}
