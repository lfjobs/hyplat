package hy.ea.production.action.cprocedure;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * 生成流程中出库管理
 * @author zj
 *
 */
@Controller
@Scope("prototype")
public class ItemQuantityAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private WarehouseService warehouseService;
	private PageForm pageForm;
	private int pageNumber;				//每页显示的条数
	private String result;					//ajax返回字段
	private String depotid;             	//库房ID
	private String parameter;				//物品参数（条码或物品编号）
	private String inventoryID;			//库存表ID
	private UtboundOrderVo utboundOrderVo;
	private InputStream excelStream;
	private String type;						//判断单据是从什么类型过来的
	private String statusbill;					//判断单据来源
	
	
	/**
	 * 获取主页面信息
	 */
	public String getTheProductionOfTheHomePageInformation(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<Object> list=new ArrayList<Object>();
		String hql="from UtboundOrderVo u where u.billstype=? and u.companyid=? and u.organizationid=? and u.status=? and u.statusbill=?";
		list.add("生产出库单");list.add(account.getCompanyID());list.add((String) session.get("organizationID"));
		list.add("16");list.add(statusbill);
		if(utboundOrderVo!=null){
			if(utboundOrderVo.getJournalnum()!=null&&!"".equals(utboundOrderVo.getJournalnum())){
				hql+=" and u.journalnum like ?";
				list.add("%"+utboundOrderVo.getJournalnum()+"%");
			}
			if(utboundOrderVo.getGoodsname()!=null&&!"".equals(utboundOrderVo.getGoodsname())){
				hql+=" and u.goodsname like ?";
				list.add("%"+utboundOrderVo.getGoodsname()+"%");
			}
		}
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber <1 ? 10 : pageNumber), hql, list.toArray());
		return "pageInformation";
	}

	/**
	 * 获取添加页面基本信息
	 */
	@SuppressWarnings("unused")
	public String ajaxAddASinglePageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd");
		String journalNum=serverService.getBillID(account.getCompanyID());
		String currentTime=df.format(new Date());
		String hql="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
		List<BaseBean> depotList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"001",account.getCompanyID(),"原材料库","00"});
		String staffHql="from Staff where groupCompanySn=? and staffID=?";
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{session.get("groupCompanySn").toString(),account.getStaffID()});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("staff",staff);
		map.put("currentTime",currentTime);
		map.put("journalNum", journalNum);
		map.put("staffId",account.getStaffID());
		map.put("staffName",account.getStaffName());
		map.put("list",depotList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}
	/**
	 * 获取物品信息
	 */
	public String ajaxGetItemInformationGoodsInformation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		if (account == null) {			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return Action.SUCCESS;
		}
		String companyid = account.getCompanyID();
		String sn = session.get("groupCompanySn").toString();
		List<Object> params=new ArrayList<Object>();
		String hql="from Inventory where groupCompanySn=? and companyID=? and warehouse=? and invenQuantity>0";
		params.add(sn);params.add(companyid);params.add(depotid);
		if(parameter!=null&&!"".equals(parameter)){
			hql+=" and (barcode=? or goodsCoding=?)";
			params.add(parameter);params.add(parameter);
		}
		List<BaseBean>	list = baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodlist", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return Action.SUCCESS;
	}
	
	/**
	 * 获取物品的可用序号区间
	 */
	public String ajaxToObtainTheCurrentItemAvailableSerialNumber(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String hql="from GoodsNum where cashierBillsID=? and companyID=? and status=? order by goodsnumber";
		List<BaseBean> goodsNumList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{inventoryID,account.getCompanyID(),"03"});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",goodsNumList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}
	
	/**
	 * 获取所填序号中可用数量
	 */
	@SuppressWarnings("unchecked")
	public String ajaxAccessNumberInTheNumberOfAvailable(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		String start=re.getParameter("start");String end=re.getParameter("end");
		List<Object> list=new ArrayList<Object>();
		String sql="select goodsnumber from dtGoodsNum where cashierBillsID=? and companyID=? and status=? ";
		list.add(inventoryID);list.add(account.getCompanyID());list.add("03");
		/*字符串类型数字超过10以后,大于或者小于的判断将不再适用*/
		if(start!=null&&!"".equals(start)){
			sql+=" and goodsnumber>=?";
			list.add(start);
		}
		if(end!=null&&!"".equals(end)){
			sql+=" and goodsnumber<=?";
			list.add(end);
		}
		sql+=" group by goodsnumber";
		List<BaseBean> goodsNumList=baseBeanService.getListBeanBySqlAndParams(sql, list.toArray());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("number",goodsNumList.size());
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}
	
	/**
	 * 添加出库单数据
	 */
	public String AddProductionOutOfADocument(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		List<BaseBean> list=new ArrayList<BaseBean>();
		String start=re.getParameter("start");String end=re.getParameter("end");
		String cashierbillsId=serverService.getServerID("CashierBills");
		String financialbillId=serverService.getServerID("FinancialBill");
		String stockinvId=serverService.getServerID("stockInv");
		String goodsbillsId=serverService.getServerID("GoodsBills");
		String organizationID=(String) session.get("organizationID");
		String groupCompanySn=session.get("groupCompanySn").toString();
		//存储出库单 
		CashierBills cashier=new CashierBills();
		cashier.setCashierBillsID(cashierbillsId);
		cashier.setJournalNum(utboundOrderVo.getJournalnum());
		cashier.setBillsType("生产出库单");
		cashier.setGroupCompanySn(groupCompanySn);
		cashier.setCompanyID(account.getCompanyID());
		cashier.setCompanyName(account.getCompanyName());
		cashier.setOrganizationID(organizationID);
		cashier.setCashierDate(new Date());
		cashier.setStaffID(utboundOrderVo.getStaffID());
		cashier.setStaffName(utboundOrderVo.getStaffName());
		cashier.setInputid(account.getStaffID());
		cashier.setInputName(account.getStaffName());
		cashier.setStatus("16");
		cashier.setStatusbill(statusbill);
		list.add(cashier);
		
		//添加进销存单据 
		FinancialBill fin=new FinancialBill();
		fin.setFinancialbillID(financialbillId);
		fin.setCashierBillsID(cashierbillsId);
		fin.setGroupCompanySn(groupCompanySn);
		fin.setCcompanyID(account.getCompanyID());
		fin.setOrganizationID(organizationID);
		fin.setDepotID(utboundOrderVo.getDepotid());
		fin.setDepotName(utboundOrderVo.getDepotname());
		fin.setDrdepotID("001");
		fin.setDrdepotName("生产流水线");
		fin.setBillsType("生产出库单");
		fin.setJournalNum(utboundOrderVo.getJournalnum());
		fin.setBillsDate(new Date());
		fin.setBillStaffID(account.getStaffID());
		fin.setBillStaffName(account.getStaffName());
		fin.setBillStatus("16");
		list.add(fin);
		
		//添加物品单据
		String invHql="from Inventory where inventoryID=? and companyID=? and goodstatus=?"; 
		Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(invHql, new Object[]{utboundOrderVo.getInventoryID(),account.getCompanyID(),"00"});
		GoodsBills goods=new GoodsBills();
		goods.setGoodsBillsID(goodsbillsId);
		goods.setCompanyID(account.getCompanyID());
		goods.setInventoryID(utboundOrderVo.getInventoryID());
		goods.setStockinvID(stockinvId);
		goods.setCashierBillsID(cashierbillsId);
		goods.setGoodsID(inv.getGoodsID());
		goods.setTypeID(inv.getGoodsType());
		goods.setGoodsNum(inv.getGoodsCoding());
		goods.setGoodsName(utboundOrderVo.getGoodsname());
		goods.setStandard(utboundOrderVo.getStandard());
		goods.setPrice(utboundOrderVo.getPrice());
		goods.setQuantity(utboundOrderVo.getQuantity());
		goods.setMoney(utboundOrderVo.getMoney());
		goods.setDepotID(utboundOrderVo.getDepotid());
		goods.setDepotName(utboundOrderVo.getDepotname());
		goods.setKcStatus("16");
		goods.setGoodsStatus("00");
		list.add(goods);
		
		//添加一条库存盘点表的出库数据
		stockInv stoinv=new stockInv();
		stoinv.setStockinvID(stockinvId);
		stoinv.setCompanyID(account.getCompanyID());
		stoinv.setGroupCompanySn(groupCompanySn);
		stoinv.setGoodsID(inv.getGoodsID());
		stoinv.setGoodsType(inv.getGoodsType());
		stoinv.setGoodsName(inv.getGoodsName());
		stoinv.setInvenQuantity(utboundOrderVo.getQuantity());
		stoinv.setSummoney(utboundOrderVo.getMoney());
		stoinv.setIntime(new Date());
		stoinv.setType("01");
		stoinv.setStaffID(account.getStaffID());
		stoinv.setStaffName(account.getStaffName());
		stoinv.setWarehouse(utboundOrderVo.getDepotid());
		stoinv.setWarehouseName(utboundOrderVo.getDepotname());
		list.add(stoinv);
		
		//修改物品序号表中的状态 utboundOrderVo
		List<Object> goodsNumObj=new ArrayList<Object>();
		String goodsNumHql="update GoodsNum set goodsBillsID=?,status=? where companyID=? and cashierBillsID=? and status=?";
		goodsNumObj.add(goodsbillsId);goodsNumObj.add("04");goodsNumObj.add(account.getCompanyID());
		goodsNumObj.add(inv.getInventoryID());goodsNumObj.add("03");
		if(start!=null&&!"".equals(start)){
			goodsNumHql+=" and goodsnumber>=?";
			goodsNumObj.add(start);
		}
		if(end!=null&&!"".equals(end)){
			goodsNumHql+=" and goodsnumber<=?";
			goodsNumObj.add(end);
		}
		
		//修改库存表中的数量
		String inveSql="update dt_inv_invt set invenQuantity=invenQuantity-?,sumPrice=sumPrice-? where companyID=? and inventoryID=?";
		Object[] inveObj={utboundOrderVo.getQuantity(),utboundOrderVo.getMoney(),account.getCompanyID(),utboundOrderVo.getInventoryID()};
		baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{inveSql},inveObj);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, new String[]{goodsNumHql}, goodsNumObj.toArray());
		return Action.SUCCESS;
	}
	
	/**
	 * 导出EXCEL表格
	 * @throws UnsupportedEncodingException 
	 */
	public String exportExcelTable() throws UnsupportedEncodingException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String sql="select case when v.journalnum is null then ' ' else v.journalnum end" 
					 + ",case when v.goodsnum is null then ' ' else v.goodsnum end" 
					 + ",case when v.goodsname is null then ' ' else v.goodsname end" 
					 + ",case when v.standard is null then ' ' else v.standard end" 
					 + ",case when v.quantity is null then ' ' else v.quantity end" 
					 + ",case when v.price is null then ' ' else v.price end" 
					 + ",case when v.money is null then ' ' else v.money end" 
					 + ",case when v.depotname is null then ' ' else v.depotname end,' -- '" 
					 + ",case when v.staffName is null then ' ' else v.staffName end,v.cashierDate from utboundordervo v where"  
					 + " v.billstype=? and v.companyid=? and v.organizationid=? and v.status=? and v.statusbill=?";
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"生产出库单",account.getCompanyID(),(String) session.get("organizationID"),"16",statusbill});
		excelStream=warehouseService.OutboundOrderExcel(list);
		return "showexcel";
	}
	
	/*
	 * 打印页面
	 */
	public String toPrint(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		if("ajax".equals(type)){
			String hql="from GoodsNum where goodsBillsID=? and status=? and companyID=? order by goodsnumber";
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{utboundOrderVo.getGoodsbillsid(),"04",account.getCompanyID()});
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("list",list);
			JSONObject js=JSONObject.fromObject(map);
			result=js.toString();
			return Action.SUCCESS;
		}else{
			HttpServletRequest re=ServletActionContext.getRequest();
			String hql="from UtboundOrderVo where cashierbillsid=? and companyid=? and organizationid=? and billstype=? and status=?";
			Object[] obj={utboundOrderVo.getCashierbillsid(),account.getCompanyID(),(String) session.get("organizationID"),"生产出库单","16"};
			UtboundOrderVo ut=(UtboundOrderVo)baseBeanService.getBeanByHqlAndParams(hql, obj);
			re.setAttribute("orgName", (String) session.get("organizationName"));  re.setAttribute("comName", account.getCompanyName());
			re.setAttribute("ut", ut);
			return "toPrint";
		}
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
	public String getDepotid() {
		return depotid;
	}
	public void setDepotid(String depotid) {
		this.depotid = depotid;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getInventoryID() {
		return inventoryID;
	}
	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
	}
	public UtboundOrderVo getUtboundOrderVo() {
		return utboundOrderVo;
	}
	public void setUtboundOrderVo(UtboundOrderVo utboundOrderVo) {
		this.utboundOrderVo = utboundOrderVo;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatusbill() {
		return statusbill;
	}

	public void setStatusbill(String statusbill) {
		this.statusbill = statusbill;
	}


	
}
