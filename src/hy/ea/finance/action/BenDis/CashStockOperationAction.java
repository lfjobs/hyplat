package hy.ea.finance.action.BenDis;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * 现金库存管理   --出库、入库
 */
public class CashStockOperationAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private WarehouseService warehouseService;
	private InputStream excelStream;
	private UtboundOrderVo utboundOrderVo;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String type;    			// 00：入库   01：出库
	
	
	/*
	 * 获取主页面信息
	 */
	public String getCashOfTheHomePage() throws ParseException{
		
		HttpServletRequest re=ServletActionContext.getRequest();
		List<Object> obj=new ArrayList<Object>();
		String edate=re.getParameter("edate");
		String sdate=re.getParameter("sdate");
		if("00".equals(type)){
			String sql1="select c.cashierbillsid,s.staffname,s.staffcode,com.companyname,g.goodsnum,g.goodsname," +
					"g.quantity,g.money,co.codevalue,c.journalnum,de.depotname,' -- ',c.cashierdate,c.inputname";
			String sql2=" from dtgoodsbills g left join dtcashierbills c on c.cashierbillsid=g.cashierbillsid" +
							 " left join dt_inv_fb f on g.cashierbillsid=f.cashierbillsid" +
							 " left join DT_ORDER_BILL_ADD os on os.oa_bill_jounum=c.jnumorder" +
						  	 " left join T_ESHOP_CUSCOM es on os.oa_sccid=es.sccid" +
						  	 " left join (select codenumber, codevalue from dtccode where " +
						  	 " codepid = ? and companyid = ?) co on es.custype =co.codenumber" +
							 " left join dt_hr_Staff s on es.staffid=s.staffid" +
							 " left join dtCompany com on os.oa_gys_id=com.companyid" +
							 " left join dtDepotManage de on g.depotid=de.depotid" +
							 " where c.billstype=? and c.companyid=? and c.statusbill=?";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("收款单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("04");
			if(utboundOrderVo!=null){
				if(!"".equals(utboundOrderVo.getJournalnum())&&utboundOrderVo.getJournalnum()!=null){
					sql2+=" and c.journalnum like ?";
					obj.add("%"+utboundOrderVo.getJournalnum()+"%");
				}
				if(!"".equals(utboundOrderVo.getStaffName())&&utboundOrderVo.getStaffName()!=null){
					sql2+=" and s.staffname like ?";
					obj.add("%"+utboundOrderVo.getStaffName()+"%");
				}
			}
			if(!"".equals(sdate)&&sdate!=null){
				sql2+=" and c.cashierdate>=?";
				obj.add(new SimpleDateFormat("yyyyMMdd").parse(sdate));
			}
			if(!"".equals(edate)&&edate!=null){
				sql2+=" and c.cashierdate<=?";
				obj.add(new SimpleDateFormat("yyyyMMdd").parse(edate));
			}
			sql2+=" order by c.cashierdate desc";
			pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 15 : pageNumber), sql1+sql2, "select count(*)"+sql2, obj.toArray());
			return "inPageData";
		}else{
			String hql="from DepotManage where companyID=? and depotPID=? and depotName=?";
			DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{
				"company201009046vxdyzy4wg0000000025","003","资金仓库"});
			
			String sql1="select c.cashierbillsid,c.journalnum,c.jnumorder,c.projectName,c.ccompanyName," +
					"c.ctUserName,g.quantity,g.money,d.depotname,'平台',c.inputname,c.cashierdate";
			String sql2=" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid = f.cashierbillsid" +
					" left join (select cashierbillsid,depotid,sum(cast(quantity as int)) quantity," +
					"sum(cast(money as int)) money from dtgoodsbills where companyid = ? and depotID = ?" +
					" group by cashierbillsid,quantity,money) g on c.cashierbillsid=g.cashierbillsid" +
					" left join dtDepotManage d on d.depotid=g.depotid where c.billstype=?" +
					" and c.companyid=? and c.statusbill=?";
			obj.add("company201009046vxdyzy4wg0000000025");obj.add(depot.getDepotID());
			obj.add("支款单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("04");
			if(utboundOrderVo!=null){
				if(!"".equals(utboundOrderVo.getJournalnum())&&utboundOrderVo.getJournalnum()!=null){
					sql2+=" and c.journalnum like ?";
					obj.add("%"+utboundOrderVo.getJournalnum()+"%");
				}
			}
			if(!"".equals(sdate)&&sdate!=null){
				sql2+=" and c.cashierdate>=?";
				obj.add(new SimpleDateFormat("yyyyMMdd").parse(sdate));
			}
			if(!"".equals(edate)&&edate!=null){
				sql2+=" and c.cashierdate<=?";
				obj.add(new SimpleDateFormat("yyyyMMdd").parse(edate));
			}
			sql2+=" order by c.cashierdate desc";
			pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 15 : pageNumber), sql1+sql2, "select count(*)"+sql2, obj.toArray());
			return "outPageData";
		}
	}
	/*
	 * 获取预览页面信息
	 */
	public String getCashPreviewPage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String status=re.getParameter("status");
		List<Object> obj=new ArrayList<Object>();
		if("00".equals(type)){
			String sql1="select g.goodsbillsid,s.staffname,s.staffcode,com.companyname,g.goodsnum,g.goodsname," +
					"g.quantity,g.money,co.codevalue,c.journalnum,de.depotname,' -- ',c.cashierdate,c.inputname";
			String sql2=" from dtgoodsbills g left join dtcashierbills c on c.cashierbillsid=g.cashierbillsid" +
							 " left join dt_inv_fb f on g.cashierbillsid=f.cashierbillsid" +
							 " left join DT_ORDER_BILL_ADD os on os.oa_bill_jounum=c.jnumorder" +
						  	 " left join T_ESHOP_CUSCOM es on os.oa_sccid=es.sccid" +
						  	 " left join (select codenumber, codevalue from dtccode where " +
						  	 " codepid = ? and companyid = ?) co on es.custype =co.codenumber" +
							 " left join dt_hr_Staff s on es.staffid=s.staffid" +
							 " left join dtCompany com on os.oa_gys_id=com.companyid" +
							 " left join dtDepotManage de on g.depotid=de.depotid" +
							 " where c.billstype=? and c.companyid=? and c.statusbill=? and g.goodsbillsid=?";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("收款单");obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("04");obj.add(utboundOrderVo.getGoodsbillsid());
			Object[] goods=(Object[]) baseBeanService.getObjectBySqlAndParams(sql1+sql2,obj.toArray());
			re.setAttribute("goods",goods);
			if("01".equals(status)){
				return "inToSee";
			}else{
				return "inPrevie";
			}
		}else{
			String hql="from DepotManage where companyID=? and depotPID=? and depotName=?";
			DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{
				"company201009046vxdyzy4wg0000000025","003","资金仓库"});
			
			String sql1="select c.cashierbillsid,c.journalnum,c.jnumorder,c.projectName,c.ccompanyName," +
					"c.ctUserName,g.quantity,g.money,d.depotname,'平台',c.inputname,c.cashierdate";
			String sql2=" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid = f.cashierbillsid" +
					" left join (select cashierbillsid,depotid,sum(cast(quantity as int)) quantity," +
					"sum(cast(money as int)) money from dtgoodsbills where companyid = ? and depotID = ?" +
					" group by cashierbillsid,quantity,money) g on c.cashierbillsid=g.cashierbillsid" +
					" left join dtDepotManage d on d.depotid=g.depotid where c.billstype=? " +
					" and c.companyid=? and c.statusbill=? and c.cashierbillsid=?";
			obj.add("company201009046vxdyzy4wg0000000025");obj.add(depot.getDepotID());
			obj.add("支款单");obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("04");obj.add(utboundOrderVo.getCashierbillsid());
			Object[] goods=(Object[]) baseBeanService.getObjectBySqlAndParams(sql1+sql2,obj.toArray());
			re.setAttribute("goods",goods);
			String hql2="from GoodsBills where cashierbillsID=?";
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{utboundOrderVo.getCashierbillsid()});
			re.setAttribute("list",list);
			if("01".equals(status)){
				return "outToSee";
			}else{
				return "outPrevie";
			}
		}
	}
	/*
	 * 导出EXCEL表格
	 */
	@SuppressWarnings("unchecked")
	public String CashExportExcelTable(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		List<Object> obj=new ArrayList<Object>();
		String title=re.getParameter("title");
		String[] str=re.getParameter("str").split(",");
		if("00".equals(type)){
			String sql1="select s.staffname,s.staffcode,com.companyname,g.goodsnum,g.goodsname," +
					"g.quantity,g.money,co.codevalue,c.journalnum,de.depotname,' -- ',c.cashierdate,c.inputname";
			String sql2=" from dtgoodsbills g left join dtcashierbills c on c.cashierbillsid=g.cashierbillsid" +
							 " left join dt_inv_fb f on g.cashierbillsid=f.cashierbillsid" +
							 " left join DT_ORDER_BILL_ADD os on os.oa_bill_jounum=c.jnumorder" +
						  	 " left join T_ESHOP_CUSCOM es on os.oa_sccid=es.sccid" +
						  	 " left join (select codenumber, codevalue from dtccode where " +
						  	 " codepid = ? and companyid = ?) co on es.custype =co.codenumber" +
							 " left join dt_hr_Staff s on es.staffid=s.staffid" +
							 " left join dtCompany com on os.oa_gys_id=com.companyid" +
							 " left join dtDepotManage de on g.depotid=de.depotid" +
							 " where c.billstype=? and c.companyid=? and c.statusbill=? order by c.cashierdate";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("收款单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("04");
			List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql1+sql2,obj.toArray());
			excelStream=warehouseService.OutOrderExcel(title, str, list);
		}else{
			String hql="from DepotManage where companyID=? and depotPID=? and depotName=?";
			DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{
				"company201009046vxdyzy4wg0000000025","003","资金仓库"});
			
			String sql1="select c.journalnum,c.jnumorder,c.projectName,c.ccompanyName," +
					"c.ctUserName,g.quantity,g.money,d.depotname,'平台',c.inputname,c.cashierdate";
			String sql2=" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid = f.cashierbillsid" +
					" left join (select cashierbillsid,depotid,sum(cast(quantity as int)) quantity," +
					"sum(cast(money as int)) money from dtgoodsbills where companyid = ? and depotID = ?" +
					" group by cashierbillsid,quantity,money) g on c.cashierbillsid=g.cashierbillsid" +
					" left join dtDepotManage d on d.depotid=g.depotid where c.billstype=?" +
					" and c.companyid=? and c.statusbill=? order by c.cashierdate";
			obj.add("company201009046vxdyzy4wg0000000025");obj.add(depot.getDepotID());
			obj.add("支款单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("04");
			List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql1+sql2,obj.toArray());
			excelStream=warehouseService.OutOrderExcel(title, str, list);
		}
		return "showexcel";
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
	public UtboundOrderVo getUtboundOrderVo() {
		return utboundOrderVo;
	}
	public void setUtboundOrderVo(UtboundOrderVo utboundOrderVo) {
		this.utboundOrderVo = utboundOrderVo;
	}
	
}
