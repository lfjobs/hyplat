package hy.ea.production.action.cprocedure.process;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.ProductionAmount;
import hy.ea.bo.production.ProductionDocuments;
import hy.ea.bo.production.StaffTrack;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

public class SetProductionAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private ProductionAmount productionAmount;
	private List<BaseBean> baseList;
	private List<Object> objList;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String type;
	private String category;
	private String fiveClear;
	/*
	 * 获取列表
	 */
	public String listPage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<Object> obj=new ArrayList<Object>();
		StringBuilder hql=new StringBuilder();
		hql.append("from ProductionAmount where companyID=? and category=?");
		obj.add(account.getCompanyID());obj.add(category);
		if(!"".equals(fiveClear)&&fiveClear!=null){
			hql.append(" and fiveClear=?");
			obj.add(fiveClear);
		}
		if(!"".equals(category)&&category!=null){
			hql.append(" and category=?");
			obj.add(category);
		}
		if(productionAmount!=null){
			if(productionAmount.getBatchNumber()!=null&&!productionAmount.getBatchNumber().equals("")){
				hql.append(" and batchNumber like ?");
				obj.add("%"+productionAmount.getBatchNumber()+"%");
			}
			if(productionAmount.getStaffName()!=null&&!productionAmount.getStaffName().equals("")){
				hql.append(" and staffName like ?");
				obj.add("%"+productionAmount.getStaffName()+"%");
			}
			if(productionAmount.getGoodsName()!=null&&!productionAmount.getGoodsName().equals("")){
				hql.append(" and goodsName like ?");
				obj.add("%"+productionAmount.getGoodsName()+"%");
			}
		}
		hql.append(" order by singleDate desc");
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), hql.toString(), obj.toArray());
		return "list";
	}
	
	/*
	 * 获取添加或修改页面
	 */
	public String addOrModifyPage(){
		if("add".equals(type)){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");		
			productionAmount=new ProductionAmount();
			productionAmount.setCompanyID(account.getCompanyID());
			productionAmount.setCompanyName(account.getCompanyName());
			productionAmount.setSingleID(account.getStaffID());
			productionAmount.setSingleName(account.getStaffName()); 
			productionAmount.setSingleDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			productionAmount.setBatchNumber(getBatchNumber(account.getCompanyID()));
		}else{
			String hql="from ProductionAmount where productionAmountID=?";
			productionAmount=(ProductionAmount)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{
					productionAmount.getProductionAmountID()});
		}
		return "data";
	}
	/*
	 * 获取产品结构
	 */
	@SuppressWarnings("unchecked")
	public String productMix(){
		Object o=baseBeanService.getObjectByHqlAndParams(
				"select sorting from ProductPackaging where ppid=?",new Object[]{productionAmount.getProductID()});
		
		String sql="select goodsname,ppid,parentId,quantity,productCode,hierarchical  from dt_ProductPackaging connect by parentId= prior ppid start with ppID=?";
		
		if(o!=null&&!"0".equals(o.toString())){
			sql+=" order by sorting";
		}
		objList=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{productionAmount.getProductID()});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",objList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/*
	 * 获取产品列表
	 */
	public String productList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String hql="from ProductPackaging where companyID=? and productstate=? and category=? and fiveClear=? and field=?";
		baseList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"01",category,fiveClear,"01"});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",baseList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/*
	 * 添加或修改
	 */
	public String addOrModify(){
		if(productionAmount.getProductionAmountID()==null||"".equals(productionAmount.getProductionAmountID())){
			productionAmount.setProductionAmountID(serverService.getServerID("ProductionAmount"));
			productionAmount.setProductionAmountKey(null);
			productionAmount.setStatus("00");
		}
		baseBeanService.saveOrUpdate(productionAmount);
		return "success";
	}
	/*
	 * 删除
	 */
	public String del(){
		String hql="from ProductionAmount where productionAmountID=?";
		productionAmount=(ProductionAmount)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{
				productionAmount.getProductionAmountID()});
		baseBeanService.deleteBeanByKey(ProductionAmount.class, productionAmount.getProductionAmountKey());
		return "success";
	}
	/*
	 * 预览或打印
	 */
	public String seeOrPrint(){
		String hql="from ProductionAmount where productionAmountID=?";
		productionAmount=(ProductionAmount)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{
				productionAmount.getProductionAmountID()});
		
		if("see".equals(type)){
			return "see";
		}else{
			return "print";
		}
	}
	/*
	 * 获取生产批次号
	 */
	public String getBatchNumber(String companyID){
		String hql="from Company where companyID=?";
		Company cc=(Company)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyID});
		String sql="select count(*) from dtProductionAmount where companyID=? and batchNumber like ?";
		int batchNumber=baseBeanService.getConutByBySqlAndParams(
					sql, new Object[]{companyID,"%"+cc.getCompanyIdentifier().toUpperCase()+new SimpleDateFormat("yyyyMMdd").format(new Date())+"%"});
		String str=cc.getCompanyIdentifier().toUpperCase()+new SimpleDateFormat("yyyyMMdd").format(new Date())+(100000+batchNumber);
		return str;
	}
	/*
	 * 提交
	 */
	public String submit() throws ParseException{
		List<BaseBean> list=new ArrayList<BaseBean>();

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		ProductionAmount pa=(ProductionAmount)baseBeanService.getBeanByHqlAndParams(
				"from ProductionAmount where productionAmountID=?",new Object[]{productionAmount.getProductionAmountID()});
		
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?", new Object[]{pa.getProductID()});
		
		GoodsBills goods=new GoodsBills();
		goods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
		goods.setCompanyID(account.getCompanyID());
		
		ProductionDocuments pd=new ProductionDocuments();
		pd.setProDocumentsID(serverService.getServerID("ProductionDocuments"));
		
		goods.setCashierBillsID(pd.getProDocumentsID());
		
		pd.setCompanyID(account.getCompanyID());
		pd.setCompanyName(account.getCompanyName());
		pd.setCashierBillsID(pa.getProductionAmountID());
		pd.setGoodsBillsID(goods.getGoodsBillsID());
		pd.setProDate(new SimpleDateFormat("yyyy-MM-dd : ").format(new Date()));
		pd.setBatchNumber(serverService.getBillID(account.getCompanyID()));
		pd.setStaffID(pa.getStaffID());
		pd.setStaffName(pa.getStaffName());
		pd.setInputID(account.getStaffID());
		pd.setInputName(account.getStaffName());
		pd.setStatus("00");pd.setType("01");
		pd.setSuperposition("01");
		pd.setCategory(pp.getCategory());
		pd.setFiveClear(fiveClear);
		
		goods.setPpID(pp.getPpID());
		goods.setGoodsID(pp.getGoodsID());
		goods.setTypeID(pp.getType());
		goods.setGoodsNum(pp.getGoodsNum());
		goods.setGoodsName(pp.getGoodsName());
		goods.setPrice(pp.getPrice());
		goods.setQuantity(pa.getAmount());
		goods.setMoney(Integer.parseInt(pp.getPrice())*Integer.parseInt(pa.getAmount())+"");
		goods.setStatus("28");
		

		pa.setStatus("01");
		list.add(pa);list.add(pd);list.add(goods);
		track(list,pa,goods);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);

		return "success";
	}
	private void track(List<BaseBean> list,ProductionAmount pa,GoodsBills goods) throws ParseException{
		
		List<Object> obj=new ArrayList<Object>();
		String sql="select p.ppid,p.hierarchical  from dt_ProductPackaging p where p.goodsname=? and p.field=?" +
				" connect by p.parentId = prior p.ppid  start with p.ppID = ?";
			obj.add(goods.getGoodsName());
			obj.add("01");
			obj.add(goods.getPpID());
		@SuppressWarnings("unchecked")
		List<Object> list2=baseBeanService.getListBeanBySqlAndParams(sql, obj.toArray());
		int ir=0;
		for(int i=0;i<list2.size();i++){
			Object[] o=(Object[]) list2.get(i); 
			if(i!=list2.size()-1){
				Object[] o2=(Object[]) list2.get(i+1);
				if(Integer.parseInt(o[1].toString())>=Integer.parseInt(o2[1].toString())){
					ir++;
				}
			}else{
				ir++;
			}
		}
		
		StaffTrack st=new StaffTrack();
		st.setStId(serverService.getServerID("StaffTrack"));
		st.setCashierBillsID(pa.getProductionAmountID());				//订单ID
		st.setEnrollDate(new SimpleDateFormat("yyyy-MM-dd").parse(pa.getSetDate()));						//下单时间
		st.setPpID(goods.getPpID());												//产品ID
		st.setPpName(goods.getGoodsName());						//产品名称	
		st.setStartDate(new Date());												//生产时间
		st.setResponID(pa.getStaffID());								//责任人
		st.setResponName(pa.getStaffName());				//责任人
		st.setPronum(ir+"");
		st.setStatus("00");
		list.add(st);
		
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

	public ProductionAmount getProductionAmount() {
		return productionAmount;
	}

	public void setProductionAmount(ProductionAmount productionAmount) {
		this.productionAmount = productionAmount;
	}

	public List<BaseBean> getBaseList() {
		return baseList;
	}

	public void setBaseList(List<BaseBean> baseList) {
		this.baseList = baseList;
	}

	public List<Object> getObjList() {
		return objList;
	}

	public void setObjList(List<Object> objList) {
		this.objList = objList;
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
	
}
