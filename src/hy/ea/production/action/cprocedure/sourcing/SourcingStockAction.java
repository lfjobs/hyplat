package hy.ea.production.action.cprocedure.sourcing;

import java.text.ParseException;
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

import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

public class SourcingStockAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private WarehouseService warehouseService;
	private List<BaseBean> beanList;
	private List<Object> objList;
	private String cashierBillsID;
	private List<GoodsBills> goodsBillsList;
	private PageForm pageForm;
	private int pageNumber;	
	private CashierBills cashierbills;
	private FinancialBill financialBill;
	private GoodsBills goodsBills;
	private String fiveClear;			//组织机构
	private String result;					//ajax返回字段
	private String type;		//生产类别  00：订单 01：计划

	/*
	 * 获取验货列表
	 */
	public String  inspectionList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Object> obj=new ArrayList<Object>();
		CAccount account = (CAccount) session.get("account");		
		String sql="select c.cashierbillsid,c.journalnum,c.companyName,f.staffName staff,c.departmentName,f.paymentType,f.staffsName,c.cashierDate,c.status";
		StringBuilder sql2=new StringBuilder();
		sql2.append(" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid=f.cashierbillsid where c.companyID=? and c.billsType=?  and (c.status=? or c.status=?) and c.type=?");
		obj.add(account.getCompanyID());obj.add("生产采购单");obj.add("12");obj.add("14");obj.add(type);
		if(cashierbills!=null){
			if(!"".equals(cashierbills.getJournalNum())&&cashierbills.getJournalNum()!=null){
				sql2.append(" and c.journalNum like ?");
				obj.add("%"+cashierbills.getJournalNum()+"%");
			}
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			sql2.append(" and c.fiveClear=?");
			obj.add(fiveClear);
		}
		sql2.append(" order by c.cashierdate desc");
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql+sql2.toString(),"select count(*) "+sql2.toString(), obj.toArray());
		return "inspectionList";
	}
	/*
	 * 获取验货信息
	 */
	public String inspectionData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
		FinancialBill f=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?",new Object[]{cashierBillsID});
		List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(
				"from GoodsBills where cashierBillsID=?",new Object[]{cashierBillsID});
		re.setAttribute("staffName",account.getStaffName());
		re.setAttribute("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		re.setAttribute("c",c);re.setAttribute("f",f);
		re.setAttribute("organizationID",session.get("organizationID").toString());
		re.setAttribute("goodsList",goodsList);
		if("see".equals(re.getParameter("type"))){
			return "inspectionSee";
		}else{
			return "inspectionPrint";
		}
	}
	/*
	 * 添加验货信息
	 */
	public String addInspection() throws Exception{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		//修改原数据
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
		c.setStatus("14");
		list.add(c);
		FinancialBill f=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?", new Object[]{cashierBillsID});
		f.setExaminegoodsDate(financialBill.getExaminegoodsDate());
		f.setStaffsID(financialBill.getStaffsID());
		f.setStaffsName(financialBill.getStaffsName());
		f.setBillStaffID(account.getStaffID());
		f.setBillStaffName(account.getStaffName());
		f.setBillsDate(new Date());
		list.add(f);
		
		//克隆一条为退货单数据
		CashierBills cc=(CashierBills) c.cloneCashierBills();
		cc.setCashierBillsKey(null);
		cc.setCashierBillsID(serverService.getServerID("CashierBills"));
		cc.setCashierDate(new Date());
		cc.setBillsType("生产采购退货单");
		cc.setjNumOrder(c.getJournalNum());
		cc.setJournalNum(serverService.getBillID(account.getCompanyID()));
		FinancialBill ff=(FinancialBill)f.cloneFinancialBill();
		ff.setFinancialbillKey(null);
		ff.setFinancialbillID(serverService.getServerID("FinancialBill"));
		ff.setCashierBillsID(cc.getCashierBillsID());
		ff.setBillsType("生产采购退货单");
		ff.setJournalNum(cc.getJournalNum());
		int is=0;
		for(int i=0;i<goodsBillsList.size();i++){
			//修改原数据的物品单据
			GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
					"from GoodsBills where goodsBillsID=?",new Object[]{goodsBillsList.get(i).getGoodsBillsID()});
			goods.setIsQualify(goodsBillsList.get(i).getIsQualify());
			goods.setMoney(Double.parseDouble(goods.getIsQualify())*Double.parseDouble(goods.getPrice())+"");
			list.add(goods);
			if(Double.parseDouble(goods.getQuantity())-Double.parseDouble(goods.getIsQualify())!=0){
				//克隆退货单的物品单据
				GoodsBills cloneGoods=(GoodsBills) goods.cloneGoodsBills();
				cloneGoods.setGoodsBillsKey(null);
				cloneGoods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
				cloneGoods.setCashierBillsID(cc.getCashierBillsID());
				cloneGoods.setQuantity((Double.parseDouble(goods.getQuantity())-Double.parseDouble(goods.getIsQualify()))+"");
				cloneGoods.setMoney((Double.parseDouble(goods.getQuantity())-Double.parseDouble(goods.getIsQualify()))*Double.parseDouble(goods.getPrice())+"");
				cloneGoods.setIsQualify(null);
				list.add(cloneGoods);
				is=1;
			}
		}
		if(is!=0){
			list.add(cc);
			list.add(ff);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	
	/*
	 * 获取收货列表
	 */
	public String goodsReceiptList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Object> obj=new ArrayList<Object>();
		CAccount account = (CAccount) session.get("account");		
		String sql="select c.cashierbillsid,c.journalnum,c.companyName,f.staffName staff,c.departmentName,f.paymentType,f.consigneeName,c.cashierDate,c.status";
		String sql2=" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid=f.cashierbillsid where c.companyID=? and c.billsType=? and (c.status=? or c.status=?) and c.type=?";
		obj.add(account.getCompanyID());obj.add("生产采购单");obj.add("13");obj.add("14");obj.add(type);
		if(cashierbills!=null){
			if(!"".equals(cashierbills.getJournalNum())&&cashierbills.getJournalNum()!=null){
				sql2+=" and c.journalNum like ?";
				obj.add("%"+cashierbills.getJournalNum()+"%");
			}
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			sql2+=" and c.fiveClear = ?";
			obj.add(fiveClear);
		}
		sql2+=" order by c.cashierdate desc";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql+sql2,"select count(*) "+sql2, obj.toArray());
		return "goodsReceiptList";
	}
	//添加收货信息
	public String addGoodsReceipt(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		//修改原数据
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
		c.setStatus("13");
		list.add(c);
		FinancialBill f=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?", new Object[]{cashierBillsID});
		f.setGoodsReceiptDate(financialBill.getGoodsReceiptDate());
		f.setBillStaffID(account.getStaffID());
		f.setBillStaffName(account.getStaffName());
		f.setConsigneeID(financialBill.getConsigneeID());
		f.setConsigneeName(financialBill.getConsigneeName());
		f.setBillsDate(new Date());
		list.add(f);
		
		for(int i=0;i<goodsBillsList.size();i++){
			//修改原数据的物品单据
			GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
					"from GoodsBills where goodsBillsID=?",new Object[]{goodsBillsList.get(i).getGoodsBillsID()});
			goods.setReQuantity(goodsBillsList.get(i).getReQuantity());
			list.add(goods);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	//获取单据信息
	public String goodsReceiptData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
		FinancialBill f=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?",new Object[]{cashierBillsID});
		List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(
				"from GoodsBills where cashierBillsID=?",new Object[]{cashierBillsID});
		re.setAttribute("staffName",account.getStaffName());
		re.setAttribute("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		re.setAttribute("c",c);re.setAttribute("f",f);
		re.setAttribute("organizationID",session.get("organizationID").toString());
		re.setAttribute("goodsList",goodsList);
		if("see".equals(re.getParameter("type"))){
			return "goodsReceiptSee";
		}else{
			return "goodsReceiptPrint";
		}
	}

	/*
	 * 获取入库单列表
	 */
	public String storageList() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest re=ServletActionContext.getRequest();
		List<Object> obj=new ArrayList<Object>();
		CAccount account = (CAccount) session.get("account");		
		String eDate=re.getParameter("eDate");
		String sDate=re.getParameter("sDate");
		String sql="select c.cashierbillsid,c.journalnum,c.companyName,c.billstype,c.departmentName,f.consigneeName,c.inputName,f.paymentType,c.cashierDate,c.status";
		String sql2=" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid=f.cashierbillsid where c.companyID=?  and c.type=?";
		obj.add(account.getCompanyID());obj.add(type);
		
		if(cashierbills!=null){
			if(!"".equals(cashierbills.getJournalNum())&&cashierbills.getJournalNum()!=null){
				sql2+=" and c.journalNum like ?";
				obj.add("%"+cashierbills.getJournalNum()+"%");
			}
			if(!"".equals(cashierbills.getInputName())&&cashierbills.getInputName()!=null){
				sql2+=" and c.inputName like ?";
				obj.add("%"+cashierbills.getInputName()+"%");
			}
			if("15".equals(cashierbills.getStatus())){
				sql2+=" and (c.status = ? or c.status=? or c.status=?) and c.billsType=?  and statusbill=?";
				obj.add("15");obj.add("22");obj.add("26");obj.add("采购入库单");obj.add("05");
			}else{
				sql2+=" and c.status = ? and c.billsType=?";
				obj.add("13");obj.add("生产采购单");
			}
		}else{
			sql2+=" and c.status = ? and c.billsType=?";
			obj.add("13");obj.add("生产采购单");
		}
		if(!"".equals(sDate)&&sDate!=null){
			sql2+=" and c.cashierDate >=?";
			obj.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(sDate));
			re.setAttribute("sDate", sDate);
		}
		if(!"".equals(eDate)&&eDate!=null){
			sql2+=" and c.cashierDate <=?";
			obj.add(new SimpleDateFormat("yyyy-MM-dd").parseObject(eDate));
			re.setAttribute("eDate", eDate);
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			sql2+=" and c.fiveClear =?";
			obj.add(fiveClear);
		}
		sql2+=" order by c.cashierdate desc";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), sql+sql2,"select count(*) "+sql2, obj.toArray());
		return "storageList";
	}
	/*
	 * 获取入库单添加页面信息
	 */
	public String storageAddPage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
		FinancialBill f=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?",new Object[]{cashierBillsID});
		List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(
				"from GoodsBills where cashierBillsID=?",new Object[]{cashierBillsID});
		re.setAttribute("staffName",account.getStaffName());
		re.setAttribute("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		re.setAttribute("c",c);re.setAttribute("f",f);re.setAttribute("journalNum", serverService.getBillID(account.getCompanyID()));
		re.setAttribute("organizationID",session.get("organizationID").toString());
		re.setAttribute("goodsList",goodsList);
		
		return "storageAddPage";
	}
	/*
	 * 添加入库单
	 */
	public String AddStorage() throws CloneNotSupportedException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		//根据采购单生成入库单
		CashierBills cashi =(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?", new Object[]{cashierbills.getCashierBillsID()});
		CashierBills c=(CashierBills) cashi.cloneCashierBills();
		c.setCashierBillsKey(null);
		c.setCashierBillsID(serverService.getServerID("CashierBills"));
		c.setJournalNum(serverService.getBillID(account.getCompanyID()));
		c.setjNumOrder(cashierbills.getJournalNum());
		c.setCashierDate(new Date());
		c.setStaffName(cashierbills.getStaffName());
		c.setStaffID(cashierbills.getStaffID());
		c.setBillsType("采购入库单");
		c.setInputid(account.getStaffID());
		c.setInputName(account.getStaffName());
		c.setStatus("22");
		c.setFiveClear(fiveClear);
		c.setStatusbill("05");
		list.add(c);
		
		cashi.setStatus("21");
		list.add(cashi);
		
		FinancialBill fin=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?",new Object[]{cashierbills.getCashierBillsID()});
		FinancialBill f=(FinancialBill) fin.cloneFinancialBill();
		f.setFinancialbillKey(null);
		f.setFinancialbillID(serverService.getServerID("FinancialBill"));
		f.setCashierBillsID(c.getCashierBillsID());
		f.setPurchaseDate(financialBill.getPurchaseDate());
		f.setSubjectName(financialBill.getSubjectName());
		f.setSubjectID(financialBill.getSubjectID());
		f.setDepotName(financialBill.getDepotName());
		f.setDepotID(financialBill.getDepotID());
		f.setBillsType("采购入库单");
		f.setJournalNum(c.getJournalNum());
		f.setBillStatus("22");
		list.add(f);
		for(int i=0;i<goodsBillsList.size();i++){
			GoodsBills good=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
					"from GoodsBills where goodsBillsID=?", new Object[]{goodsBillsList.get(i).getGoodsBillsID()});
			GoodsBills goods=(GoodsBills) good.cloneGoodsBills();
			goods.setGoodsBillsKey(null);
			goods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
			goods.setCashierBillsID(c.getCashierBillsID());
			goods.setQuantity(good.getReQuantity());
			goods.setKcStatus("22");
			goods.setGoodstatus("00");
			list.add(goods);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	/*
	 * 获取入库单预览及打印页面
	 */
	public String  storageData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
		FinancialBill f=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?",new Object[]{cashierBillsID});
		List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(
				"from GoodsBills where cashierBillsID=?",new Object[]{cashierBillsID});
		re.setAttribute("c",c);re.setAttribute("f",f);
		re.setAttribute("goodsList",goodsList);
		
		//审核信息
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?", 
				new Object[]{cashierBillsID});
		if(bCheck!=null){
			List<BaseBean> BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
					"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
			String str="";
			Map<String,String> billcheckmap=new HashMap<String, String>();
			if(BillCheckList.size()>0){
				for(int i=0;i<BillCheckList.size();i++){
					BillCheck bc=(BillCheck) BillCheckList.get(i);
					if(bc.getDeptpost()!=null){
						billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
						billcheckmap.put(bc.getDeptpost()+"zt",bc.getAuditorstatus());
						str+=bc.getAuditorname()+" : "+(bc.getComments()==null?" ":bc.getComments())+" ; ";
					}
				}
			}
			re.setAttribute("str",str);
			re.setAttribute("billcheckmap", billcheckmap);
		}else{
			re.setAttribute("str",null);
			re.setAttribute("billcheckmap", null);
		}
		if("see".equals(re.getParameter("type"))){
			return "storageSee";
		}else{
			return "storagePrint";
		}
	}
	/*
	 *确认入库 
	 */
	public String confirmStorage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
		FinancialBill f=(FinancialBill)baseBeanService.getBeanByHqlAndParams(
				"from FinancialBill where cashierBillsID=?",new Object[]{cashierBillsID});
		List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(
				"from GoodsBills where cashierBillsID=?",new Object[]{cashierBillsID});
		c.setStatus("15");list.add(c);
		f.setBillStatus("15");list.add(f);
		for(int i=0;i<goodsList.size();i++){
			GoodsBills goods=(GoodsBills) goodsList.get(i);
			String invHql="from Inventory where companyID=? and warehouse=? and goodsID=? and unitPrice=? and goodstatus=?";
			Object[] obj={account.getCompanyID(),f.getDepotID(),goods.getGoodsID(),goods.getPrice(),"00"};
			Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(invHql, obj);
			if(inv==null){
				inv=new Inventory();
				inv.setInventoryID(serverService.getServerID("Inventory"));
				inv.setCompanyID(account.getCompanyID());
				inv.setWarehouse(f.getDepotID());
				inv.setWarehouseName(f.getDepotName());
				inv.setGoodsID(goods.getGoodsID());
				inv.setGoodsType(goods.getTypeID());
				inv.setGoodsName(goods.getGoodsName());
				inv.setStandard(goods.getStandard());
				inv.setGoodsCoding(goods.getGoodsNum());
				inv.setUnit(goods.getBoxStandard());
				inv.setUnitPrice(goods.getPrice());
				inv.setInvenQuantity(goods.getQuantity());
				inv.setGoodstatus("00");
				inv.setSumPrice(goods.getMoney());
				inv.setSubjectsID(f.getSubjectID());
				inv.setSubjectsName(f.getSubjectName());
				list.add(inv);
			}else{
				inv.setInvenQuantity(Long.parseLong(inv.getInvenQuantity())+Long.parseLong(goods.getQuantity())+"");
				inv.setSumPrice(Double.parseDouble(inv.getSumPrice())+Double.parseDouble(goods.getMoney())+"");
				list.add(inv);
			}
			stockInv sto=new stockInv();
			sto.setStockinvID(serverService.getServerID("stockInv"));
			sto.setCompanyID(account.getCompanyID());
			sto.setGoodsBillsId(goods.getGoodsBillsID());
			sto.setGoodsID(goods.getGoodsID());
			sto.setGoodsType(goods.getTypeID());
			sto.setGoodsName(goods.getGoodsName());
			sto.setInvenQuantity(goods.getQuantity());
			sto.setSummoney(goods.getMoney());
			sto.setIntime(new Date());
			sto.setType("00");
			sto.setStaffID(c.getStaffID());
			sto.setStaffName(c.getStaffName());
			sto.setWarehouse(f.getDepotID());
			sto.setWarehouseName(f.getDepotName());
			list.add(sto);
			String[] str={"GoodsNum",account.getCompanyID(),inv.getInventoryID(),goods.getGoodsBillsID(),
						goods.getGoodsID(),"03",goods.getQuantity(),goods.getGoodsNum()};
			warehouseService.numberOfGeneratedItems(str);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	/*
	 * 入库单提交审核
	 */
	public String submitAudit(){
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{cashierbills.getCashierBillsID()});
		c.setStatus("26");
		baseBeanService.update(c);
		return "success";
	}

	/*
	 *入库单查询是否已审核
	 */
	public String warehousingQueryAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String BillHql="from BillCheck where newBillsID=? and auditorcompanyid=?";
		List<BaseBean> billList=baseBeanService.getListBeanByHqlAndParams(BillHql, new Object[]{cashierBillsID,account.getCompanyID()});
		String cashiHql="from CashierBills where cashierbillsid=? and companyid=?";
		CashierBills ut=(CashierBills)baseBeanService.getBeanByHqlAndParams(cashiHql, new Object[]{cashierBillsID,account.getCompanyID()});
		Map<String,Object> map=new HashMap<String, Object>();
		if(billList.size()==0||"10".equals(ut.getStatus())){
			map.put("sta","0");
		}else{
			map.put("sta","1");
		}
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public List<BaseBean> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<BaseBean> beanList) {
		this.beanList = beanList;
	}
	public List<Object> getObjList() {
		return objList;
	}
	public void setObjList(List<Object> objList) {
		this.objList = objList;
	}
	public List<GoodsBills> getGoodsBillsList() {
		return goodsBillsList;
	}
	public void setGoodsBillsList(List<GoodsBills> goodsBillsList) {
		this.goodsBillsList = goodsBillsList;
	}
	public CashierBills getCashierbills() {
		return cashierbills;
	}
	public void setCashierbills(CashierBills cashierbills) {
		this.cashierbills = cashierbills;
	}
	public FinancialBill getFinancialBill() {
		return financialBill;
	}
	public void setFinancialBill(FinancialBill financialBill) {
		this.financialBill = financialBill;
	}
	public GoodsBills getGoodsBills() {
		return goodsBills;
	}
	public void setGoodsBills(GoodsBills goodsBills) {
		this.goodsBills = goodsBills;
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
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}

