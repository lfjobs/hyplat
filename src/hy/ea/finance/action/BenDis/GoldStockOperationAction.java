package hy.ea.finance.action.BenDis;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;

/**
 * 金币库存管理  --出库、入库
 */
public class GoldStockOperationAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private WarehouseService warehouseService;
	private UtboundOrderVo utboundOrderVo;
	private InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String type;     //  00：入库   01：出库
	
	/*
	 * 获取主页面信息
	 */
	public String getGoldOfTheHomePage() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		List<Object> obj=new ArrayList<Object>();
		String edate=re.getParameter("edate");
		String sdate=re.getParameter("sdate");
		if("00".equals(type)){
			String sql1="select c.cashierbillsid,s.staffname,s.staffcode,com.companyname,g.goodsnum,g.goodsname," +
					"g.quantity,g.money,co.codevalue,c.journalnum,de.depotname,' -- ',c.cashierdate,c.inputname";
			String sql2=" from dtgoodsbills g left join dtcashierbills c on c.cashierbillsid=g.cashierbillsid" +
							 " left join dt_inv_fb f on g.cashierbillsid=f.cashierbillsid" +
						  	 " left join T_ESHOP_CUSCOM es on es.staffid = c.contactUserID " +
							 " left join DT_ORDER_BILL_ADD os on os.oa_sccid = es.sccid" +
						  	 " left join (select codenumber, codevalue from dtccode where " +
						  	 " codepid = ? and companyid = ?) co on es.custype =co.codenumber" +
							 " left join dt_hr_Staff s on es.staffid=s.staffid" +
							 " left join dtCompany com on os.oa_gys_id=com.companyid" +
							 " left join dtDepotManage de on g.depotid=de.depotid" +
							 " where c.billstype=? and c.companyid=? and c.status=?";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("金币入库单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("15");
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
			sql2+=" order by c.cashierdate";
			pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 15 : pageNumber), sql1+sql2, "select count(*)"+sql2, obj.toArray());
			return "inPageData";
		}else{
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
							 " where c.billstype=? and c.companyid=? and c.status=?";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("金币出库单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("16");
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
			sql2+=" order by c.cashierdate";
			pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 15 : pageNumber), sql1+sql2, "select count(*)"+sql2, obj.toArray());
			return "outPageData";	
		}
	}
	/*
	 * 获取预览页面信息
	 */
	@SuppressWarnings("unchecked")
	public String getGoldPreviewPage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String status=re.getParameter("status");
		List<Object> obj=new ArrayList<Object>();
			String sql="select c.cashierbillsid,s.staffname,s.staffcode,com.companyname,co.codevalue," +
					"c.journalnum,c.cashierdate,c.inputname,c.billstype from dtcashierbills c " +
					" left join dt_inv_fb f on c.cashierbillsid=f.cashierbillsid" +
					" left join T_ESHOP_CUSCOM es on es.staffid = c.contactUserID" +
					" left join DT_ORDER_BILL_ADD os on os.oa_sccid = es.sccid " +
					" left join (select codenumber, codevalue from dtccode where " +
					" codepid = ? and companyid = ?) co on es.custype = co.codenumber" +
					" left join dt_hr_Staff s on es.staffid = s.staffid" +
					" left join dtCompany com on os.oa_gys_id = com.companyid" +
					" where c.companyid = ? and c.cashierbillsid = ?";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add(utboundOrderVo.getCashierbillsid());
			Object[] cashi=(Object[]) baseBeanService.getObjectBySqlAndParams(sql,obj.toArray());
			String sql2="select g.goodsname,g.goodsnum,g.quantity,g.price,g.money,d.depotName," +
					"g.remark from dtgoodsbills g left join dtDepotManage d " +
					"on g.depotid = d.depotid where g.cashierbillsid=? ";
			List<Object> goodsList=baseBeanService.getListBeanBySqlAndParams(sql2, new Object[]{
					utboundOrderVo.getCashierbillsid()});
			re.setAttribute("cashi",cashi);
			re.setAttribute("goodsList",goodsList);
			if("01".equals(status)){
				return "inToSee";
			}else{
				return "inPrevie";
			}
	}
	/*
	 * 导出EXCEL表格
	 */
	@SuppressWarnings("unchecked")
	public String GoldExportExcelTable(){
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
						  	 " left join T_ESHOP_CUSCOM es on es.staffid = c.contactUserID " +
							 " left join DT_ORDER_BILL_ADD os on os.oa_sccid = es.sccid" +
						  	 " left join (select codenumber, codevalue from dtccode where " +
						  	 " codepid = ? and companyid = ?) co on es.custype =co.codenumber" +
							 " left join dt_hr_Staff s on es.staffid=s.staffid" +
							 " left join dtCompany com on os.oa_gys_id=com.companyid" +
							 " left join dtDepotManage de on g.depotid=de.depotid" +
							 " where c.billstype=? and c.companyid=? and c.status=? order by c.cashierdate";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("金币入库单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("15");
			List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql1+sql2,obj.toArray());
			excelStream=warehouseService.OutOrderExcel(title, str, list);
		}else{
			String sql1="select s.staffname,s.staffcode,com.companyname,g.goodsnum,g.goodsname," +
					"g.quantity,g.money,g.money,co.codevalue,c.journalnum,de.depotname,' -- ',c.inputname,c.cashierdate";
			String sql2=" from dtgoodsbills g left join dtcashierbills c on c.cashierbillsid=g.cashierbillsid" +
							 " left join dt_inv_fb f on g.cashierbillsid=f.cashierbillsid" +
							 " left join DT_ORDER_BILL_ADD os on os.oa_bill_jounum=c.jnumorder" +
						  	 " left join T_ESHOP_CUSCOM es on os.oa_sccid=es.sccid" +
						  	 " left join (select codenumber, codevalue from dtccode where " +
						  	 " codepid = ? and companyid = ?) co on es.custype =co.codenumber" +
							 " left join dt_hr_Staff s on es.staffid=s.staffid" +
							 " left join dtCompany com on os.oa_gys_id=com.companyid" +
							 " left join dtDepotManage de on g.depotid=de.depotid" +
							 " where c.billstype=? and c.companyid=? and c.status=?  order by c.cashierdate";
			obj.add("scode20150911hvgnyqbv8g0000000004");
			obj.add("company201009046vxdyzy4wg0000000025");
			obj.add("金币出库单");obj.add("company201009046vxdyzy4wg0000000025");obj.add("15");
			List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql1+sql2,obj.toArray());
			excelStream=warehouseService.OutOrderExcel(title, str, list);
		}
		return "showexcel";
	}
	/*
	 * 获取用户所属公司
	 */
	public String ajaxGetUserOwnedCompany(){
		String sql="select os.oa_sccid from dtcashierbills c" +
				" left join DT_ORDER_BILL_ADD os on c.jnumorder=os.oa_bill_jounum" +
				" where c.cashierbillsid=? and c.companyid=?";
		Object sccid=baseBeanService.getObjectBySqlAndParams(sql, new Object[]{
				utboundOrderVo.getCashierbillsid(),"company201009046vxdyzy4wg0000000025"});
		String hql="from TEshopCusCom  where sccId=?";
		String companyId="";
		TEshopCusCom te=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid});
		if(te!=null){
			while(true){
				String hql2="from TEshopCusCom  where account=? and logOff=0";
				te=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{te.getSuperioragent()});
				if(te.getCompanyId()!=null&&!"".equals(te.getCompanyId()));{
					companyId=te.getCompanyId();
					break;
				}
			}
		}
		String hql3="from Company where companyID=?";
		Company com=(Company)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{companyId});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("com",com);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
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
