package hy.ea.invoicing.action;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.invoicing.Inventory;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
/**
 *	财务库存模块
 * @author zj
 */
public class FinancialInventoryAction {
	@Resource
	private BaseBeanService baseBeanService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String result;
	/*
	 * 获取库存盘点总数据
	 */
	@SuppressWarnings("unchecked")
	public String getPageHomeData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
			String sql="select i1.goodsname,i2.q,i1.warehouse,i1.warehousename,i2.q2  from (select case " +
					" when ss2.q is null then  ss1.q else ss1.q - ss2.q" +
					" end q, ss1.q q2, ss2.n  from (select sum(s1.invenquantity) q,s1.goodsname n from dt_inv_stockinvt s1 left join" +
					" dtdepotmanage d1 on s1.warehouse = d1.depotid where d1.companyid =? and d1.depotpid = ? " +
					" and s1.type = ? and (s1.goodsname = ? or s1.goodsname = ? or s1.goodsname = ?)" +
					" group by s1.goodsname, s1.warehousename) ss1 left join (select sum(s1.invenquantity) q,s1.goodsname n" +
					" from dt_inv_stockinvt s1 left join dtdepotmanage d1 on s1.warehouse = d1.depotid where d1.companyid =?" +
					" and d1.depotpid = ? and (s1.type = ? or s1.type =?) and (s1.goodsname = ? or s1.goodsname = ? " +
					" or s1.goodsname = ?) group by s1.goodsname, s1.warehousename) ss2 on ss1.n = ss2.n where ss1.n = ss2.n) i2" +
					" left join dt_inv_stockinvt i1 on i1.goodsname = i2.n left join dtdepotmanage depot on i1.warehouse = depot.depotid" +
					" where i1.companyid =? and depot.depotpid=? group by i1.goodsname,i2.q,i1.warehouse,i1.warehousename,i2.q2";
			Object[] obj={account.getCompanyID(),"003","00","银行存款","库存金币","微分金金币",account.getCompanyID(),
							"003","01","02","银行存款","库存金币","微分金金币",account.getCompanyID(),"003"};
			pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql,"select count(*) from ("+sql+")", obj);
			re.setAttribute("pageForm",pageForm);
		return "pageHome";
	}
	/*
	 * 获取库存表里物品信息
	 */
	public String getInventoryItemInformation(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String goodsName=re.getParameter("goods");
		String warehouse=re.getParameter("warehouse");
		String hql="from Inventory where companyID=? and goodsName=? and warehouse=?";
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, 
							new Object[]{account.getCompanyID(),goodsName,warehouse});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",list);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	/*
	 * 库存盘点
	 */
	@SuppressWarnings("unchecked")
	public String getStockingData() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String datte=re.getParameter("datte");
		String sql="select sto.goodsname,sto.goodsType,ago.ssagq q1,storage.sseq q2,outs.ssfq q3,too.sstoq q4,too.sstoq2 q5," +
				"sto.warehousename from dt_inv_stockinvt sto left join (select case when s2.ssbq is null then s1.ssaq else s1.ssaq - s2.ssbq" +
				" end sstoq,s1.ssaq sstoq2,s1.ssan sston from (select sum(e1.invenquantity) ssaq, e1.goodsname ssan from dt_inv_stockinvt" +
				" e1 left join dtdepotmanage e2 on e1.warehouse = e2.depotid where e2.companyid = ? and e2.depotpid = ? and" +
				" e1.type = ? group by e1.goodsname, e1.warehousename) s1 left join (select sum(f1.invenquantity) ssbq, f1.goodsname" +
				" ssbn from dt_inv_stockinvt f1 left join dtdepotmanage f2 on f1.warehouse = f2.depotid where f2.companyid = ? and" +
				" f2.depotpid = ? and (f1.type =? or f1.type =?) group by f1.goodsname, f1.warehousename) s2 on s1.ssan = s2.ssbn)" +
				" too on too.sston = sto.goodsname left join (select case when k2.ssdq is null then k1.sscq else k1.sscq - k2.ssdq end" +
				" ssagq,k1.sscn ssagn from (select sum(c1.invenquantity) sscq, c1.goodsname sscn from dt_inv_stockinvt c1 left join" +
				" dtdepotmanage c2 on c1.warehouse = c2.depotid where c2.companyid = ? and c2.depotpid = ? and c1.type =? and" +
				" c1.intime < ? group by c1.goodsname, c1.warehousename) k1 left join (select sum(d1.invenquantity) ssdq,d1.goodsname" +
				" ssdn from dt_inv_stockinvt d1 left join dtdepotmanage d2 on d1.warehouse = d2.depotid where d2.companyid =? and" +
				" d2.depotpid = ? and (d1.type =? or d1.type = ?) and d1.intime <? group by d1.goodsname, d1.warehousename) k2 on" +
				" k1.sscn = k2.ssdn) ago on ago.ssagn = sto.goodsname left join (select sum(a1.invenquantity) sseq, a1.goodsname ssen" +
				" from dt_inv_stockinvt a1 left join dtdepotmanage a2 on a1.warehouse = a2.depotid where a2.companyid = ?" +
				" and a2.depotpid =? and a1.type =? and a1.intime >=? and a1.intime<=? group by a1.goodsname, a1.warehousename) storage on" +
				" storage.ssen =sto.goodsname left join (select sum(b1.invenquantity) ssfq, b1.goodsname ssfn from dt_inv_stockinvt b1" +
				" left join dtdepotmanage b2 on b1.warehouse = b2.depotid where b2.companyid = ? and b2.depotpid =? and (b1.type" +
				" = ? or b1.type =?) and b1.intime >= ? and b1.intime<=?  group by b1.goodsname, b1.warehousename) outs on outs.ssfn = sto.goodsname" +
				" left join dtdepotmanage depot on sto.warehouse = depot.depotid where depot.companyid = ? and depot.depotpid =?" +
				" and (sto.goodsname = ? or sto.goodsname = ? or sto.goodsname =?) group by sto.goodsname,sto.goodsType, ago.ssagq," +
				" storage.sseq,outs.ssfq,too.sstoq,too.sstoq2,sto.warehousename";
		List<Object> obj=new ArrayList<Object>();
		Date d=new SimpleDateFormat("yyyyMM").parse(datte);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();   
		calendar.setTime(d);  
		calendar.set(Calendar.DAY_OF_MONTH,1);
		String time=df.format(calendar.getTime());
		calendar.add(Calendar.MONTH,1);//月增加1天 
		calendar.add(Calendar.DAY_OF_MONTH,-1);//日期倒数一日,既得到本月最后一天 
		String time2=df.format(calendar.getTime());
		for(int i=0;i<7;i++){
			if(i!=6){
				if(i%2==0){
					obj.add("company201009046vxdyzy4wg0000000025");
					obj.add("003");obj.add("00");
					if(i!=0){
						obj.add(time);
						if(i==4){
							obj.add(time2);
						}
					}
				}else{
					obj.add("company201009046vxdyzy4wg0000000025");
					obj.add("003");obj.add("01");obj.add("02");
					if(i!=1){
						obj.add(time);
						if(i==5){
							obj.add(time2);
						}
					}
				}
			}else{
				obj.add("company201009046vxdyzy4wg0000000025");
				obj.add("003");	obj.add("银行存款");obj.add("库存现金");obj.add("微分金金币");
			}
		}
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql, obj.toArray());
		re.setAttribute("list",list);
		re.setAttribute("datte",datte);
		return "stocking";
	}
	
	/*
	 * 获取当前公司名称
	 */
	public String getCompanyName(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("companyName",account.getCompanyName());
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/*
	 * 设置上下限值
	 */
	public String SetUpperAndLowerLimits(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String invenOnline=re.getParameter("invenOnline");
		String invenUnderline=re.getParameter("invenUnderline");
		String inventoryID=re.getParameter("inventoryID");
		
		String hql="update Inventory set invenOnline=?,invenUnderline=? where inventoryID=? and companyID=?";
		Object[] obj={invenOnline,invenUnderline,inventoryID,account.getCompanyID()};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql}, obj);
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
