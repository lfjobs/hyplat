package hy.ea.production.action.cprocedure.process;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.ProductionAssembly;
import hy.ea.bo.production.ProductionDocuments;
import hy.ea.bo.production.StaffTrack;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

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
/**
 * 生产组装
 * @author zj
 *
 */
public class ProductionAssemblyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private WarehouseService warehouseService;
	@Resource
	private ServerService serverService;
	private ProductionAssembly productionAssembly;
	private UtboundOrderVo utboundOrderVo;
	private PageForm pageForm;
	private int pageNumber;
	private String category;
	private String fiveClear;
	private String result;
	private String type;
	
	/*
	 * 获取订单列表
	 */
	public String getProductOrderList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<Object> list=new ArrayList<Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select c.cashierbillsid,g.goodsbillsid,c.journalNum,g.goodsname,g.goodsnum,g.standard"); 
		sql.append(",c.ctUserName,g.price,g.quantity,g.money,g.status from dtcashierbills c left join dtgoodsbills g "); 
		sql.append("on c.cashierbillsid=g.cashierbillsid  where  c.staffid=? and c.companyID=? and c.status=?");
		sql.append("  and c.billsType=? and (g.status=? or g.status=?) and g.category=?");
		list.add(account.getStaffID());list.add(account.getCompanyID());
		list.add("27");list.add("项目收入预算单");list.add("27");list.add("28");list.add(category);
		if(utboundOrderVo!=null){
			if(utboundOrderVo.getJournalnum()!=null&&!"".equals(utboundOrderVo.getJournalnum())){
				sql.append(" and c.journalnum like ?");
				list.add("%"+utboundOrderVo.getJournalnum()+"%");
			}
			if(utboundOrderVo.getGoodsname()!=null&&!"".equals(utboundOrderVo.getGoodsname())){
				sql.append(" and g.goodsname like ?");
				list.add("%"+utboundOrderVo.getGoodsname()+"%");
			}
			if(utboundOrderVo.getStaffName()!=null&&!"".equals(utboundOrderVo.getStaffName())){
				sql.append(" and c.staffname like ?");
				list.add("%"+utboundOrderVo.getStaffName()+"%");
			}
		}
		if(fiveClear!=null&&!fiveClear.equals("")){
			sql.append(" and c.fiveClear=?");
			list.add(fiveClear);
		}
		sql.append(" order by c.cashierdate desc");
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),  sql.toString(), "select count(*) from ("+sql.toString()+")", list.toArray());
		return "orderList";
	}
	/*
	 * 开始生产
	 */
	public String startProduction() throws CloneNotSupportedException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<BaseBean> list=new ArrayList<BaseBean>();
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		CashierBills cashi=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=?",new Object[]{goods.getCashierBillsID()});
		ProductPackaging pp=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=? and compangID=?", new Object[]{goods.getPpID(),account.getCompanyID()});
		goods.setStatus("28");
		GoodsBills g=(GoodsBills) goods.cloneGoodsBills();
		g.setGoodsBillsKey(null);
		g.setGoodsBillsID(serverService.getServerID("GoodsBills"));
		ProductionDocuments pd=new ProductionDocuments();
		pd.setProDocumentsID(serverService.getServerID("ProductionDocuments"));
		g.setCashierBillsID(pd.getProDocumentsID());
		pd.setCompanyID(account.getCompanyID());
		pd.setCompanyName(account.getCompanyName());
		pd.setCashierBillsID(cashi.getCashierBillsID());
		pd.setGoodsBillsID(g.getGoodsBillsID());
		pd.setProDate(new SimpleDateFormat("yyyy-MM-dd : ").format(new Date()));
		pd.setBatchNumber(serverService.getBillID(account.getCompanyID()));
		pd.setStaffID(cashi.getStaffID());
		pd.setStaffName(cashi.getStaffName());
		pd.setInputID(account.getStaffID());
		pd.setInputName(account.getStaffName());
		pd.setStatus("00");pd.setType("00");
		pd.setContactUserID(cashi.getContactUserID());
		pd.setContactUserName(cashi.getCtUserName());
		pd.setReference(cashi.getReference());
		pd.setCategory(pp.getCategory());
		pd.setFiveClear(goods.getFiveClear());
		
		list.add(g);list.add(pd);list.add(goods);
		track(list,cashi,goods);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	/*
	 * 获取组装列表
	 */
	public String getProductAssemblyList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		String ajax=re.getParameter("ajax");
		String status=re.getParameter("status");
		String parameter=re.getParameter("parameter");
		status=status==null?"01":status;
		List<Object> list=new ArrayList<Object>();
		String sql="select p.proDocumentsID,g.goodsbillsid,p.batchNumber,g.goodsname,g.goodsnum,g.standard,p.contactUserName,g.price,g.quantity,g.money,g.ppid" 
					+" from dtProDocuments p  left join dtgoodsbills g on g.cashierbillsid=p.proDocumentsID where  p.companyID=? and (p.status=? " 
					+" or p.status=?)  and g.status=? and p.type=? ";
		list.add(account.getCompanyID());
		list.add("00");list.add(status);list.add("28");
		if("01".equals(type)){
			list.add("01");
		}else{
			list.add("00");
			sql+=" and p.staffid=?";
			list.add(account.getStaffID());
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			sql+="and p.fiveClear=?";
			list.add(fiveClear);
		}
		if(category!=null&&!"".equals(category)){
			sql+="and p.category=?";
			list.add(category);
		}
		if(parameter!=null&&!"".equals(parameter)){
			sql+=" and (g.goodsname like ? or g.goodsNum like ?)";
			list.add("%"+parameter+"%");list.add("%"+parameter+"%");
		}
		if(utboundOrderVo!=null){
			if(utboundOrderVo.getJournalnum()!=null&&!"".equals(utboundOrderVo.getJournalnum())){
				sql+=" and p.batchNumber like ?";
				list.add("%"+utboundOrderVo.getJournalnum()+"%");
			}
			if(utboundOrderVo.getGoodsname()!=null&&!"".equals(utboundOrderVo.getGoodsname())){
				sql+=" and g.goodsname like ?";
				list.add("%"+utboundOrderVo.getGoodsname()+"%");
			}
			if(utboundOrderVo.getStaffName()!=null&&!"".equals(utboundOrderVo.getStaffName())){
				sql+=" and p.contactUserName like ?";
				list.add("%"+utboundOrderVo.getStaffName()+"%");
			}
		}
		sql+=" order by p.proDate desc";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),  sql, "select count(*) from ("+sql+")", list.toArray());
		if("ajax".equals(ajax)){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("pageForm",pageForm);
			JSONObject js=JSONObject.fromObject(map);
			result=js.toString();
			return "success";
		}
		return "assList";
	}

	/*
	 * 获取产品列表信息
	 */
	public String getProductData(){
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionDocuments pd=(ProductionDocuments)baseBeanService.getBeanByHqlAndParams(
				"from ProductionDocuments where proDocumentsID=?",new Object[]{goods.getCashierBillsID()});
		ServletActionContext.getRequest().setAttribute("c", pd);
		ServletActionContext.getRequest().setAttribute("g", goods);
			return "orderData";
	}
	
	/*
	 * ajax获取产品结构树
	 */
	@SuppressWarnings("unchecked")
	public String ajaxGetProductMix(){
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		List<Object> obj=new ArrayList<Object>();
		Object o=baseBeanService.getObjectByHqlAndParams(
				"select sorting from ProductPackaging where ppid=?",new Object[]{utboundOrderVo.getPpId()});
		String sql=" select pp.ppid,pp.parentid,pp.hierarchical,pp.goodsname,pp.quantity,i.invenquantity,a.outgoingQuantity" 
				+",a.status,a.startTime,a.endTime,a.coachTeaching,a.studentLearningLog,a.teachingSupervisor" 
				+" from (select p.goodsname,p.ppid,p.parentId,p.quantity,p.productCode,p.hierarchical,p.sorting" 
				+" from dt_ProductPackaging p where field=? connect by p.parentId = prior p.ppid  start with p.ppID = ? and p.goodsname=?) pp" 
				+" left join dt_inv_invt i on pp.ppid=i.productid and i.warehousename='半成品库' left join dtAssembly"
				+" a on a.productID=pp.ppid and a.productPID=? and a.cashierBillsID=?";
		obj.add("01");
		if(utboundOrderVo!=null&&utboundOrderVo.getPpId()!=null&&!"".equals(utboundOrderVo.getPpId())){
			obj.add(utboundOrderVo.getPpId());
			obj.add(utboundOrderVo.getGoodsname());
		}else{
			obj.add(goods.getPpID());
			obj.add(goods.getGoodsName());
		}
		if(o!=null&&!"0".equals(o.toString())){
			sql+=" order by pp.sorting";
		}
		obj.add(goods.getPpID());obj.add(goods.getCashierBillsID());
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql, obj.toArray());
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",list);map.put("goods",goods);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/*
	 * 获取设置页面信息
	 */
	@SuppressWarnings("unchecked")
	public String getProductAddData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		HttpServletRequest re=ServletActionContext.getRequest();
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionAssembly ass=(ProductionAssembly)baseBeanService.getBeanByHqlAndParams(
				"from ProductionAssembly where productID=? and productPID=? and cashierBillsID=?",new Object[]{
						utboundOrderVo.getPpId(),goods.getPpID(),goods.getCashierBillsID()});
		Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(
				"from Inventory where warehouseName=? and productId=?",new Object[]{"半成品库",utboundOrderVo.getPpId()});
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?",new Object[]{utboundOrderVo.getPpId()});
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(
					"select p.goodsName from dt_productpackaging p connect by prior p.parentid=p.ppid start with p.ppid=?",
					new Object[]{utboundOrderVo.getPpId()});
		re.setAttribute("orgId", session.get("organizationID").toString());
		re.setAttribute("ass", ass);re.setAttribute("inv",inv);
		re.setAttribute("pp",pp);re.setAttribute("goods",goods);re.setAttribute("list",list);
		return "orderAdd";
	}
	
	/*
	 * 设置信息
	 */
	public String getProductAdd(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<BaseBean> list=new ArrayList<BaseBean>();
		ProductionAssembly ass=(ProductionAssembly)baseBeanService.getBeanByHqlAndParams(
				"from ProductionAssembly where proAssemblyID=?", new Object[]{productionAssembly.getProAssemblyID()});
		if(ass==null){
			ass=new ProductionAssembly();
			ass.setProAssemblyID(serverService.getServerID("ProductionAssembly"));
			ass.setProductPID(productionAssembly.getProductPID());
			ass.setProductID(productionAssembly.getProductID());
			ass.setCashierBillsID(productionAssembly.getCashierBillsID());
			ass.setGoodsName(productionAssembly.getGoodsName());
			ass.setStructureQuantity(productionAssembly.getStructureQuantity());
			ass.setStartTime(productionAssembly.getStartTime());
			ass.setTeachingSupervisor(productionAssembly.getTeachingSupervisor());
			ass.setStatus("00");
		}
		String outQuantity=Integer.parseInt(ass.getOutgoingQuantity()==null?"0":ass.getOutgoingQuantity())
					+Integer.parseInt(productionAssembly.getOutgoingQuantity())+"";
		ass.setOutgoingQuantity(outQuantity);
		ass.setEndTime(productionAssembly.getEndTime());
		ass.setJournal((ass.getJournal()==null?"":ass.getJournal())+" | "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss: ").format(new Date())+productionAssembly.getJournal());
		ass.setRemarks(productionAssembly.getRemarks());
		list.add(ass);
		
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?",new Object[]{productionAssembly.getProductID()});
		DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(
				"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"半成品库","001",account.getCompanyID()});
		DepotManage depot2=(DepotManage)baseBeanService.getBeanByHqlAndParams(
				"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"生产库","001",account.getCompanyID()});
		List<String> sqls=new ArrayList<String>();
		List<Object[]> objs=new ArrayList<Object[]>();
		
		String invID=outboundOperation(list,pp,depot,account,productionAssembly.getOutgoingQuantity());
		
		String invID2=storageOperation(list,pp,depot2,ass.getOutgoingQuantity(),account);
		sqls.add("update  dtgoodsnum g3 set g3.cashierbillsid=?,g3.goodsbillsID=? where g3.gnid in (select g2.gnid from (select * from dtgoodsnum g1 where g1.goodsid=? and g1.status=? and g1.cashierbillsid=? order by g1.goodsnumber) g2 where rownum<=?)");
		objs.add(new Object[]{invID2,utboundOrderVo.getGoodsbillsid(),pp.getGoodsID(),"03",invID,productionAssembly.getOutgoingQuantity()});
		
		baseBeanService.executeSqlsByParmsList(list, sqls.toArray(new String[sqls.size()]), objs);
		return "success";
	}
	
	/*
	 * 获取产品二级菜单
	 */
	@SuppressWarnings("unchecked")
	public String getProductPrint(){
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		List<Object> obj=new ArrayList<Object>();
		String sql=" select pp.ppid,pp.parentid,pp.goodsname" 
				+" from (select p.goodsname,p.ppid,p.parentId,p.quantity,p.productCode,p.hierarchical" 
				+" from dt_ProductPackaging p connect by p.parentId = prior p.ppid  start with p.ppID = ? and p.goodsname=?) pp" 
				+" left join dt_inv_invt i on pp.ppid=i.productid and i.warehousename='半成品库' left join dtAssembly"
				+" a on a.productID=pp.ppid and a.productPID=? and a.cashierBillsID=? where hierarchical=?";
		obj.add(goods.getPpID());obj.add(goods.getGoodsName());obj.add(goods.getPpID());obj.add(goods.getCashierBillsID());obj.add("2");
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql,obj.toArray());
		String 	sql2=" select pp.ppid, pp.parentid, pp.goodsname from (select p.goodsname, p.ppid,p.parentId," 
						+"p.hierarchical from dt_ProductPackaging p  connect by prior parentId=ppID start with ppID=?)" 
						+" pp where pp.hierarchical = '2'";
		List<Object> list2=baseBeanService.getListBeanBySqlAndParams(sql2, new Object[]{utboundOrderVo.getPpId()});
		ServletActionContext.getRequest().setAttribute("list",list);
		ServletActionContext.getRequest().setAttribute("quantity",goods.getQuantity());
		if(list2.size()<1)
			ServletActionContext.getRequest().setAttribute("obj","null");
		else
			ServletActionContext.getRequest().setAttribute("obj",list2.get(0));
		return "orderPrint";
	}
	
	/*
	 * 获取产品最底级菜单
	 */
	@SuppressWarnings("unchecked")
	public String getTheLowest(){
		String ppID=ServletActionContext.getRequest().getParameter("ppID");
		
		StringBuilder sql=new StringBuilder();
		sql.append("select ppID,goodsNum,goodsName,model,variableID,price,quantity,remark");
		sql.append(" from dt_ProductPackaging where assemble is not  null and delStatus=?");
		sql.append(" connect by parentId= prior ppID start with ppID=? ");
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(
				sql.toString(),new Object[]{"00",ppID});
		ProductPackaging pp=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?",new Object[]{ppID});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",list);map.put("pp",pp);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	/*
	 * 打印
	 */
	public String getProductSee(){
		
		return "orderSee";
	}
	
	/*
	 * 提交
	 */
	@SuppressWarnings("unchecked")
	public String assemblySub(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<BaseBean> list=new ArrayList<BaseBean>();
		List<String> sqls=new ArrayList<String>();
		List<Object[]> objs=new ArrayList<Object[]>();
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?", new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionDocuments pd=(ProductionDocuments)baseBeanService.getBeanByHqlAndParams(
				"from ProductionDocuments where proDocumentsID=?",new Object[]{goods.getCashierBillsID()});
		sqls.add("update dtAssembly a set a.status=?,a.outgoingQuantity=a.structureQuantity where a.productid in (select p.ppid from" +
				" dt_ProductPackaging p connect by p.parentId = prior p.ppid  start with p.ppID = ?)" +
				" and a.productpid=? and a.cashierBillsID=?");
		objs.add(new Object[]{"01",productionAssembly.getProductID(),goods.getPpID(),pd.getProDocumentsID()});
		String sql2="select pp.ppid,pp.goodsname,pp.quantity,a.proassemblyid,pp.hierarchical from (select p.ppid,p.goodsname,"
						+"p.quantity,p.hierarchical from dt_ProductPackaging p connect by p.parentId = prior p.ppid  start with p.ppID = ?) pp"
						+" left join dtassembly a on a.productid=pp.ppid and a.productpid=?  and a.cashierBillsID=?";
		
		List<Object> assList=baseBeanService.getListBeanBySqlAndParams(sql2, new Object[]{productionAssembly.getProductID(),goods.getPpID(),pd.getProDocumentsID()});
		for(int i=0;i<assList.size();i++){
			Object[] o=(Object[]) assList.get(i);
			if(o[3]==null||o[3].equals("")){
				ProductionAssembly as=new ProductionAssembly();
				as.setProAssemblyID(serverService.getServerID("ProductionAssembly"));
				as.setProductPID(goods.getPpID());
				as.setProductID(o[0].toString());
				as.setGoodsName(o[1].toString());
				as.setCashierBillsID(pd.getProDocumentsID());
				as.setStructureQuantity(o[2].toString());
				as.setOutgoingQuantity(Integer.parseInt(o[2].toString())*Integer.parseInt(goods.getQuantity())+"");
				as.setStatus("01");
				o[3]=as.getProAssemblyID().toString();
				list.add(as);
			}
			ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
					"from ProductPackaging where ppID=?",new Object[]{o[0].toString()});
			DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(
					"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"生产库","001",account.getCompanyID()});
			DepotManage depot2=(DepotManage)baseBeanService.getBeanByHqlAndParams(
					"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"检验库","001",account.getCompanyID()});
			ProductionAssembly ass=(ProductionAssembly)baseBeanService.getBeanByHqlAndParams(
					"from ProductionAssembly where proAssemblyID=?", new Object[]{o[3].toString()});
			if(i!=assList.size()-1&&i!=0){
				Object[] o2=(Object[]) assList.get(i+1);
				if(Integer.parseInt(o[4].toString())>=Integer.parseInt(o2[4].toString())){
					outboundOperation(list,pp,depot,account,ass.getOutgoingQuantity());
					
					sqls.add("update  dtgoodsnum g3 set g3.status=? where g3.gnid in (select g2.gnid from (select * from dtgoodsnum g1 where g1.goodsid=? and g1.status=? and g1.goodsbillsid=? order by g1.goodsnumber) g2 where rownum<=?)");
					objs.add(new Object[]{"04",pp.getGoodsID(),"03",goods.getGoodsBillsID(),ass.getOutgoingQuantity()});
				}
			}else if(i==0){
				String quantity=Integer.parseInt(o[2].toString())*Integer.parseInt(goods.getQuantity())+"";
				String invID=storageOperation(list,pp,depot2,quantity,account);
				String[] str={"GoodsNum",account.getCompanyID(),invID,utboundOrderVo.getGoodsbillsid(),
						pp.getGoodsID(),"03",quantity,pp.getGoodsNum()};
				warehouseService.numberOfGeneratedItems(str);
			}else{
				outboundOperation(list,pp,depot,account,ass.getOutgoingQuantity());
				sqls.add("update  dtgoodsnum g3 set g3.status=? where g3.gnid in (select g2.gnid from (select * from dtgoodsnum g1 where g1.goodsid=? and g1.status=? and g1.goodsbillsid=? order by g1.goodsnumber) g2 where rownum<=?)");
				objs.add(new Object[]{"04",pp.getGoodsID(),"03",goods.getGoodsBillsID(),ass.getOutgoingQuantity()});
			}
		}
		String sql="select pp.ppid,pp.goodsname from (select p.goodsname,p.ppid,p.parentId,p.quantity," 
				+"p.productCode,p.hierarchical from dt_ProductPackaging p connect by p.parentId = prior p.ppid" 
				+" start with p.ppID = ? and p.goodsname =?) pp left join dtassembly ass on ass.productid = pp.ppid" 
				+" and ass.productpid = ? and ass.cashierbillsid = ?where pp.hierarchical =? and ass.proassemblyid is null";
		List<Object> arrayList=baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[]{goods.getPpID(),goods.getGoodsName(),goods.getPpID(),pd.getProDocumentsID(),"2"});
		
		ProductionAssembly PpAss=(ProductionAssembly) baseBeanService.getBeanByHqlAndParams(
				"from ProductionAssembly where productID=? and productPID=? and cashierBillsID=?",
				new Object[]{goods.getPpID(),goods.getPpID(),goods.getCashierBillsID()});
		if((arrayList.size()==1&&productionAssembly.getProductID().equals(((Object[])arrayList.get(0))[0])&&PpAss==null)||PpAss==null){
			ProductionAssembly ass=new ProductionAssembly();
			ass.setProAssemblyID(serverService.getServerID("ProductionAssembly"));
			ass.setProductPID(goods.getPpID());
			ass.setProductID(goods.getPpID());
			ass.setGoodsName(goods.getGoodsName());
			ass.setCashierBillsID(pd.getProDocumentsID());
			ass.setStructureQuantity("1");
			ass.setOutgoingQuantity("");
			ass.setStatus("01");
			list.add(ass);
		}
		pd.setStatus("01");list.add(pd);
		baseBeanService.executeSqlsByParmsList(list,sqls.toArray(new String[sqls.size()]),objs);
		return "success";
	}
	
	/*
	 * 单产品提交
	 */
	public String singleAssemblySub(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<BaseBean> list=new ArrayList<BaseBean>();
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?", new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionDocuments pd=(ProductionDocuments)baseBeanService.getBeanByHqlAndParams(
				"from ProductionDocuments where proDocumentsID=?",new Object[]{goods.getCashierBillsID()});
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?",new Object[]{goods.getPpID()});
		DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(
				"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"半成品库","001",account.getCompanyID()});
		DepotManage depot2=(DepotManage)baseBeanService.getBeanByHqlAndParams(
				"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"检验库","001",account.getCompanyID()});
		
		ProductionAssembly as=new ProductionAssembly();
		as.setProAssemblyID(serverService.getServerID("ProductionAssembly"));
		as.setProductPID(goods.getPpID());
		as.setProductID(pp.getPpID());
		as.setGoodsName(pp.getGoodsName());
		as.setCashierBillsID(pd.getProDocumentsID());
		as.setStructureQuantity(goods.getQuantity());
		as.setOutgoingQuantity(goods.getQuantity());
		as.setStatus("01");
		list.add(as);
		
		outboundOperation(list,pp,depot,account,goods.getQuantity());
		String invID=storageOperation(list,pp,depot2,goods.getQuantity(),account);
		String[] str={"GoodsNum",account.getCompanyID(),invID,utboundOrderVo.getGoodsbillsid(),
				pp.getGoodsID(),"03",goods.getQuantity(),pp.getGoodsNum()};
		warehouseService.numberOfGeneratedItems(str);
		
		pd.setStatus("01");list.add(pd);
		baseBeanService.executeSqlsByParmsList(list,null,null);
		return "success";
	}
	
	/*
	 * ajax获取生产列表，供其他功能使用
	 */
	public String ajaxGetProductionList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String sql="select pd.proDocumentsID,pd.batchNumber,p.tradeCode,p.barCode,p.productCode,p.goodsname,p.brand,p.model,p.standard,p.variableID,g.quantity,p.price,g.money from dtProDocuments pd left join dtgoodsbills g on g.cashierbillsid=pd.proDocumentsID left join dt_ProductPackaging p on p.ppid=g.ppid" +
				" where pd.companyID=? and pd.category=? and pd.type=? and pd.fiveClear=?";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),  sql, "select count(*) from ("+sql+")",
				new Object[]{account.getCompanyID(),category,type,fiveClear});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	/*
	 * 出库动作管理
	 */
	private String outboundOperation(List<BaseBean> list,ProductPackaging pp,DepotManage depot,CAccount account,String quantity){
		stockInv sto=new stockInv();
		sto.setStockinvID(serverService.getServerID("stockInv"));
		sto.setCompanyID(account.getCompanyID());
		sto.setGoodsBillsId(utboundOrderVo.getGoodsbillsid());
		sto.setGoodsID(pp.getGoodsID());
		sto.setGoodsType(pp.getType());
		sto.setGoodsName(pp.getGoodsName());
		sto.setInvenQuantity(quantity);
		sto.setSummoney(pp.getMoney());
		sto.setIntime(new Date());
		sto.setType("01");
		sto.setStaffID(account.getStaffID());
		sto.setStaffName(account.getStaffName());
		sto.setWarehouse(depot.getDepotID());
		sto.setWarehouseName(depot.getDepotName());
		list.add(sto);
 		/*Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(
				"from Inventory where goodsID=? and warehouse=? and productId=?",new Object[]{pp.getGoodsID(),depot.getDepotID(),pp.getPpID()});
		inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())-Integer.parseInt(quantity)+"");
		inv.setSumPrice((Double.parseDouble(inv.getSumPrice())-Integer.parseInt(quantity)*Double.parseDouble(inv.getUnitPrice()))+"");
		list.add(inv);*/
		return "123";
	}
	/*
	 * 入库动作管理
	 */
	private String storageOperation(List<BaseBean> list,ProductPackaging pp,DepotManage depot,String quantity,CAccount account){
		stockInv sto2=new stockInv();
		sto2.setStockinvID(serverService.getServerID("stockInv"));
		sto2.setCompanyID(account.getCompanyID());
		sto2.setGoodsBillsId(utboundOrderVo.getGoodsbillsid());
		sto2.setGoodsID(pp.getGoodsID());
		sto2.setGoodsType(pp.getType());
		sto2.setGoodsName(pp.getGoodsName());
		sto2.setInvenQuantity(quantity);
		sto2.setSummoney(pp.getMoney());
		sto2.setIntime(new Date());
		sto2.setType("00");
		sto2.setStaffID(account.getStaffID());
		sto2.setStaffName(account.getStaffName());
		sto2.setWarehouse(depot.getDepotID());
		sto2.setWarehouseName(depot.getDepotName());
		list.add(sto2);
		Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(
				"from Inventory where goodsID=? and warehouse=? and productId=?",new Object[]{pp.getGoodsID(),depot.getDepotID(),pp.getPpID()});
		if(inv==null){
			inv=new Inventory();
			inv.setInventoryID(serverService.getServerID("Inventory"));
			inv.setCompanyID(account.getCompanyID());
			inv.setWarehouse(depot.getDepotID());
			inv.setWarehouseName(depot.getDepotName());
			inv.setGoodsID(pp.getGoodsID());
			inv.setGoodsType(pp.getType());
			inv.setGoodsName(pp.getGoodsName());
			inv.setGoodsCoding(pp.getGoodsNum());
			inv.setStandard(pp.getStandard());
			inv.setUnitPrice(pp.getPrice());
			inv.setGoodstatus("00");
			inv.setProductId(pp.getPpID());
			inv.setInvenQuantity(quantity);
			inv.setSumPrice(Integer.parseInt(quantity)*Integer.parseInt(pp.getPrice())+"");
		}else{
			inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())+Integer.parseInt(quantity)+"");
			inv.setSumPrice(Double.parseDouble(inv.getSumPrice())+Integer.parseInt(quantity)*Double.parseDouble(pp.getPrice())+"");
		}
		list.add(inv);
		return inv.getInventoryID();
	}
	
	private void track(List<BaseBean> list,CashierBills cashi,GoodsBills goods){
		Staff s=(Staff)baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=?",new Object[]{cashi.getContactUserID()});
		
		List<Object> obj=new ArrayList<Object>();
		String sql="select p.ppid,p.hierarchical  from dt_ProductPackaging p connect by p.parentId = prior p.ppid" 
					+"  start with p.ppID = ? and p.goodsname=?";
			obj.add(goods.getPpID());
			obj.add(goods.getGoodsName());
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
		st.setCashierBillsID(cashi.getCashierBillsID());				//订单ID
		st.setEnrollDate(cashi.getCashierDate());						//下单时间
		st.setPpID(goods.getPpID());												//产品ID
		st.setPpName(goods.getGoodsName());						//产品名称	
		st.setStartDate(new Date());												//生产时间
		st.setCompanyID(cashi.getCcompanyID());					//往来公司
		st.setCompanyName(cashi.getCcompanyName());	//往来公司	
		st.setResponID(cashi.getStaffID());								//教练
		st.setResponName(cashi.getStaffName());				//教练

		st.setStaffName(s.getStaffName());					//学员
		st.setStaffID(s.getStaffID());									//学员
		st.setSex(s.getSex());
		st.setTel(s.getReference());
		st.setIdentiCard(s.getStaffIdentityCard());
		st.setHeadimage(s.getHeadimage());
		st.setPronum(ir+"");
		st.setStatus("00");
		list.add(st);
	}
	public ProductionAssembly getProductionAssembly() {
		return productionAssembly;
	}
	public void setProductionAssembly(ProductionAssembly productionAssembly) {
		this.productionAssembly = productionAssembly;
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
