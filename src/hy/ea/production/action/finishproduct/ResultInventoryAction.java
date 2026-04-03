package hy.ea.production.action.finishproduct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.FieldConStor;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * 生产成果库存模块
 * @author zj
 *
 */
public class ResultInventoryAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private List<FieldConStor> fieldConStors;
	private PageForm pageForm;
	private int pageNumber;				//每页显示的条数
	private String result;					//ajax返回字段
	private String category;  //产品类型  00：单产品  01：组装产品
	private String fiveClear;  //组织机构
	private String fiveClearName; //组织机构ID
	private String type;
	
	/*
	 * 获取库存列表
	 */
	public String getResultInvList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		StringBuilder hql=new StringBuilder();
		List<Object> obj=new ArrayList<Object>();
		hql.append("from Inventory");
		hql.append(" where companyID=? and goodstatus=? and category=?");
		obj.add(account.getCompanyID());obj.add("00");obj.add(category);
		if(fiveClear!=null&&!"".equals(fiveClear)){
			hql.append(" and warehouse=? and warehouseName=?");
			obj.add(fiveClear);obj.add(fiveClearName+"库"); 
		} 
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber <1 ? 10 : pageNumber), hql.toString(), obj.toArray());
		return "list";
	}
	
	/*
	 * 获取单选或多选下级信息
	 */
	public String getLowerField(){
		String ppID=ServletActionContext.getRequest().getParameter("ppID");
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(
				"from  ProductPackaging where parentId=?", new Object[]{ppID});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",list);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	
	/* ----------------------------------------------------------------------------办公室 - 加班------------------------------------------------------------------------------------*/
	
	/*
	 * 获取添加页面信息
	 */
	public String getResultsAddPages(){
		HttpServletRequest re=ServletActionContext.getRequest();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Company c=(Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?",new Object[]{account.getCompanyID()});
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=?",new Object[]{account.getStaffID()});
		Object[] strs=new Object[3];
		String sql = "select c.organizationid,c.organizationname,d.postname"
				+ " from dtcos t"
				+ " left join dtcorganization c on t.organizationid = c.organizationid"
				+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
				+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
		strs = (Object[]) baseBeanService.getObjectBySqlAndParams(sql,new Object[] { account.getCompanyID(), account.getStaffID() });
		
		re.setAttribute("department", strs);
		re.setAttribute("staff",staff);
		re.setAttribute("currentcompany", c);
		re.setAttribute("account", account);
		re.setAttribute("serialnumber", serverService.getBillID(account.getCompanyID()));
		
		String invID=re.getParameter("invID");
		Inventory inv=(Inventory) baseBeanService.getBeanByHqlAndParams(
				"from Inventory where inventoryID=?",new Object[]{invID});
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(
				"from ProductPackaging where companyID=? and  parentId=? and field=?",
				new Object[]{account.getCompanyID(),inv.getProductId(),"00"});
		Map<String,Object> map=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			ProductPackaging pp=(ProductPackaging) list.get(i);
			map.put(pp.getGoodsName(),pp);
		}
		re.setAttribute("ppID",inv.getProductId());
		re.setAttribute("map",map);
		return "add";
	}
	/*
	 * 添加物品出库信息
	 */
	public String addResultsData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		String ppID=re.getParameter("ppID");
		ProductPackaging pp=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?",new Object[]{ppID});
		
		Inventory inv=(Inventory) baseBeanService.getBeanByHqlAndParams(
				"from Inventory where productId=? and warehouse=?",new Object[]{pp.getPpID(),fiveClear});
		inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())-1+"");		
		list.add(inv);
		
		GoodsNum gn=(GoodsNum) baseBeanService.getBeanByHqlAndParams(
				"from GoodsNum where cashierbillsID=? and goodsID=? and status=? and companyID=? and rownum <2",
				new Object[]{inv.getInventoryID(),pp.getGoodsID(),"03",account.getCompanyID()});
		gn.setStatus("04");
		list.add(gn);
		
		
		Inventory it=new Inventory();
		it.setInventoryID(serverService.getServerID("Inventory"));
		it.setCompanyID(account.getCompanyID());
		it.setStaffID(account.getStaffID());
		it.setStaffName(account.getStaffName());
		it.setWarehouse(account.getStaffID());
		it.setWarehouseName(account.getStaffName());
		it.setGoodsID(inv.getGoodsID());
		it.setGoodsName(inv.getGoodsName());
		it.setGoodsType(inv.getGoodsType());
		it.setBarcode(inv.getBarcode());
		it.setStandard(inv.getStandard());
		it.setGoodsCoding(inv.getGoodsCoding());
		it.setUnit(inv.getUnit());
		it.setUnitPrice(inv.getUnitPrice());
		it.setInvenQuantity("1");
		it.setGoodstatus("00");
		it.setStatus("07");
		it.setWeizhi(inv.getWarehouse());
		it.setProductId(inv.getProductId());
		it.setCategory(inv.getCategory());
		it.setFiveClear(inv.getFiveClear());
		list.add(it);
		

		
		
		List<BaseBean> ppList=baseBeanService.getListBeanByHqlAndParams(
				"from ProductPackaging where companyID=? and  parentId=? and field=?",
				new Object[]{account.getCompanyID(),ppID,"00"});
		Map<String,Object> map=new HashMap<String, Object>();
		for(int i=0;i<ppList.size();i++){
			ProductPackaging pr=(ProductPackaging) ppList.get(i);
			map.put(pr.getPpID(),pr);
		}
	
		CashierBills c=new CashierBills();
		c.setCashierBillsID(serverService.getServerID("CashierBills"));
		c.setCompanyID(account.getCompanyID());
		c.setCompanyName(account.getCompanyName());
		c.setBillsType("加班申请单");
		c.setJournalNum(((FieldConStor)fieldConStors.get(3)).getContent());
		c.setStaffID(((FieldConStor)fieldConStors.get(4)).getTextID());
		c.setStaffName(((FieldConStor)fieldConStors.get(4)).getContent());
		c.setCashierDate(new Date());
		c.setStatus("26");
		c.setFiveClear(pp.getFiveClear());
		list.add(c);
		
		GoodsBills goods=new GoodsBills();
		goods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
		goods.setCompanyID(account.getCompanyID());
		goods.setInventoryID(it.getInventoryID());
		goods.setGoodsnumber(gn.getGoodsnumber());
		goods.setCashierBillsID(c.getCashierBillsID());
		goods.setGoodsID(pp.getGoodsID());
		goods.setTypeID(pp.getTradeID());
		goods.setGoodsNum(pp.getGoodsNum());
		goods.setGoodsName(pp.getGoodsName());
		goods.setStandard(pp.getStandard());
		goods.setPrice(pp.getPrice());
		goods.setMoney(pp.getPrice());	
		goods.setQuantity("1");
		goods.setCategory(pp.getCategory());
		goods.setPpID(pp.getPpID());
		list.add(goods);
		
		
		stockInv sto=new stockInv();
		sto.setStockinvID(serverService.getServerID("stockInv"));
		sto.setCompanyID(account.getCompanyID());
		sto.setGoodsBillsId(goods.getGoodsBillsID());
		sto.setGoodsID(goods.getGoodsID());
		sto.setGoodsType(goods.getTypeID());
		sto.setGoodsName(goods.getGoodsName());
		sto.setInvenQuantity("1");
		sto.setIntime(new Date());
		sto.setType("01");
		sto.setStaffID(account.getStaffID());
		sto.setStaffName(account.getStaffName());
		list.add(sto);
		
		if(fieldConStors!=null){
			for(int i=0;i<fieldConStors.size();i++){
				FieldConStor f=(FieldConStor) fieldConStors.get(i);
				ProductPackaging pr=(ProductPackaging) map.get(f.getFieldPpID());
				
				f.setFieldConID(serverService.getServerID("FieldConStor"));
				f.setPpID(pp.getPpID());
				f.setGoodsName(pp.getGoodsName());
				f.setFieldPpID(pr.getPpID());
				f.setFieldPpName(pr.getGoodsName());
				f.setPrimaryField(pr.getPrimaryField());
				f.setTwoLevelField(pr.getTwoLevelField());
				f.setCashierBillsID(c.getCashierBillsID());
				list.add(f);
			}
		}
	
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	/*
	 * 切换列表页面显示
	 */
	public String outgoingItemList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		StringBuilder hql=new StringBuilder();
		List<Object> obj=new ArrayList<Object>();
		hql.append("from UtboundOrderVo");
		hql.append(" where companyID=? and category=? and fiveClear=? and billsType=?  and (status=? or status=?)");
		obj.add(account.getCompanyID());obj.add(category);obj.add(fiveClear);
		obj.add("加班申请单");obj.add("26");obj.add("15");
		
		hql.append(" order by cashierDate desc");
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber <1 ? 10 : pageNumber), hql.toString(), obj.toArray());
		return "libList";
	}
	/*
	 * 获取修改数据详细信息
	 */
	public String seeInformation(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String invID=re.getParameter("invID");
		Object[] o=(Object[]) baseBeanService.getObjectBySqlAndParams(
				"select c.cashierbillsid,g.ppid from dtcashierbills c left join dtgoodsbills g" +
				" on c.cashierbillsid=g.cashierbillsid where c.cashierbillsid=?",new Object[]{invID});
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(
				"from FieldConStor where cashierbillsID=? and ppID=?",o);
		Map<String,Object> map=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			FieldConStor pp=(FieldConStor) list.get(i);
			map.put(pp.getFieldPpName(),pp);
		}
		re.setAttribute("cashierBillsID",o[0]);
		re.setAttribute("ppID",o[1]);
		re.setAttribute("map",map);
		return "print";
	} 
	
	/**
	 * 获取查看审核页面信息
	 */
	public String getResultInvPageData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String cashierBillsID=re.getParameter("cashierBillsID");

		
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
				
		Object[] o=(Object[]) baseBeanService.getObjectBySqlAndParams(
				"select c.cashierbillsid,g.ppid from dtcashierbills c left join dtgoodsbills g" +
				" on c.cashierbillsid=g.cashierbillsid where c.cashierbillsid=?",new Object[]{cashierBillsID});
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(
				"from FieldConStor where cashierbillsID=? and ppID=?",o);
		Map<String,Object> map=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			FieldConStor pp=(FieldConStor) list.get(i);
			map.put(pp.getFieldPpName(),pp);
		}
		re.setAttribute("cashierBillsID",o[0]);
		re.setAttribute("ppID",o[1]);
		re.setAttribute("map",map);
		return "invPrint";
	}
	
	/**
	 * 出库单审核
	 */
	public String ResultInvSingleAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String status=re.getParameter("status");
		String audit=re.getParameter("audit");
		String deptpost=re.getParameter("deptpost");
		String cashierBillsID=re.getParameter("cashierBillsID");
		List<BaseBean> list=new ArrayList<BaseBean>();
		BillCheck bc=new BillCheck();
		bc.setCheckid(serverService.getServerID("BillCheck"));
		bc.setAuditorcompanyid(account.getCompanyID());
		bc.setAuditorcompanyname(account.getCompanyName());
		bc.setFirstBillsID(cashierBillsID);
		bc.setInputid(account.getStaffID());
		bc.setNewBillsID(cashierBillsID);
		bc.setInputname(account.getStaffName());
		bc.setAudittime(new Date());
		bc.setAuditorid(account.getStaffID());
		bc.setAuditorname(account.getStaffName());
		bc.setComments(audit);
		bc.setDeptpost(deptpost);
		bc.setAuditorstatus("yes".equals(status)?"02":"03");
		list.add(bc);
		if(!"yes".equals(status)){
			CashierBills ca=(CashierBills)baseBeanService.getBeanByHqlAndParams(" from CashierBills where cashierBillsID=? and companyID=?",
								new Object[]{cashierBillsID,account.getCompanyID()});
			ca.setStatus("10");
			list.add(ca);
		}

		baseBeanService.saveBeansListAndexecuteHqlsByParams(list,null,null);
		return "success";
	}

	/*
	 * 确认出库
	 */
	
	public String itemConfirmation(){
		String cashierBillsID=ServletActionContext.getRequest().getParameter("cashierBillsID");
		CashierBills c=(CashierBills) baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?", new Object[]{cashierBillsID});
		c.setStatus("15");
		baseBeanService.update(c);
		
		return "success";
	}

	/*
	 * 修改出库信息
	 */
	public String editResultsData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		Map<String,Object> map=new HashMap<String, Object>();
		List<BaseBean> baseList=new ArrayList<BaseBean>();
		String cashierBillsID=re.getParameter("cashierBillsID");
		String ppID=re.getParameter("ppID");
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(
				"from FieldConStor where cashierbillsID=? and ppID=?",new Object[]{cashierBillsID,ppID});
		for(int i=0;i<list.size();i++){
			FieldConStor f=(FieldConStor) list.get(i);
			map.put(f.getFieldPpID(),f);
		}
		
		for(int i=0;i<fieldConStors.size();i++){
			FieldConStor fie=fieldConStors.get(i);
			FieldConStor f=(FieldConStor) map.get(fie.getFieldPpID());
			f.setTextID(fie.getTextID());
			f.setContent(fie.getContent());
			baseList.add(f);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseList, null, null);
		return "success";
	}
	
	/* ----------------------------------------------------------------------------办公室 - 加班 - 结束------------------------------------------------------------------------------------*/
	
	


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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public String getFiveClearName() {
		return fiveClearName;
	}
	public void setFiveClearName(String fiveClearName) {
		this.fiveClearName = fiveClearName;
	}

	public List<FieldConStor> getFieldConStors() {
		return fieldConStors;
	}

	public void setFieldConStors(List<FieldConStor> fieldConStors) {
		this.fieldConStors = fieldConStors;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
