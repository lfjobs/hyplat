package hy.ea.production.action.dcheck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.ProductInspection;
import hy.ea.bo.production.ProductionAssembly;
import hy.ea.bo.production.ProductionDocuments;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

public class ProductInspectionAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private UtboundOrderVo utboundOrderVo;
	private ProductionAssembly assembly;
	private ProductInspection inspection;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String type;
	private String category;
	private String fiveClear;
	/*
	 * 获取列表
	 */
	public String inspectionList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		List<Object> list=new ArrayList<Object>();
		String sql="select p.proDocumentsID,g.goodsBillsid,p.batchNumber,g.goodsname,g.goodsnum,g.standard,g.price,"
							+"g.quantity,i.qualifiedQuantity,i.unqualifiedQuantity,i.inspectionManName,i.inspectionTime," 
							+"i.status  from dtProDocuments p  left join dtgoodsbills g on g.cashierbillsid=p.proDocumentsID"
							+" left join dtAssembly a on  a.productID=g.ppid and a.cashierBillsID=p.cashierBillsID left join" 
							+" dtInspection i on i.proAssemblyID=a.proAssemblyID  where  p.companyID=? and p.status=? " 
							+"  and g.status=?  and p.category=?";
		list.add(account.getCompanyID());list.add(type);list.add("28"); list.add(category);

		if(utboundOrderVo!=null){
			if(utboundOrderVo.getJournalnum()!=null&&!"".equals(utboundOrderVo.getJournalnum())){
				sql+=" and p.batchNumber like ?";
				list.add("%"+utboundOrderVo.getJournalnum()+"%");
			}
			if(utboundOrderVo.getGoodsname()!=null&&!"".equals(utboundOrderVo.getGoodsname())){
				sql+=" and g.goodsname like ?";
				list.add("%"+utboundOrderVo.getGoodsname()+"%");
			}
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			sql+=" and p.fiveClear=?";
			list.add(fiveClear);
		}
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),  sql, "select count(*) from ("+sql+")", list.toArray());
		return "list";
	}
	/*
	 * 获取单据信息
	 */
	public String inspectionData(){
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionDocuments pd=(ProductionDocuments)baseBeanService.getBeanByHqlAndParams(
				"from ProductionDocuments where proDocumentsID=?",new Object[]{goods.getCashierBillsID()});
		ServletActionContext.getRequest().setAttribute("c", pd);
		ServletActionContext.getRequest().setAttribute("g", goods);
		return "data";
	}
	/*
	 * ajax获取产品结构树
	 */
	public String ajaxGetProductMix(){
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		List<Object> obj=new ArrayList<Object>();
		Object o=baseBeanService.getObjectByHqlAndParams(
				"select sorting from ProductPackaging where ppid=?",new Object[]{goods.getPpID()});
		String sql=" select pp.ppid,pp.parentid,pp.hierarchical,pp.goodsname,pp.quantity,a.outgoingQuantity" 
				+",a.status,a.proAssemblyID,i.qualifiedQuantity,i.unqualifiedQuantity,i.inspectionManName";
		String sql2=" from (select p.goodsname,p.ppid,p.parentId,p.quantity,p.productCode,p.hierarchical,p.sorting" 
				+" from dt_ProductPackaging p connect by p.parentId = prior p.ppid  start with p.ppID = ? and p.goodsname=?) pp" 
				+" left join dtAssembly  a on a.productID=pp.ppid and a.productPID=? and a.cashierBillsID=?" 
				+"left join dtInspection i on i.proAssemblyID=a.proAssemblyID and i.status=?  where a.status=?";
		if(utboundOrderVo!=null&&utboundOrderVo.getPpId()!=null&&!"".equals(utboundOrderVo.getPpId())){
			obj.add(utboundOrderVo.getPpId());
			obj.add(utboundOrderVo.getGoodsname());
		}else{
			obj.add(goods.getPpID());
			obj.add(goods.getGoodsName());
		}
		if(o!=null&&!"0".equals(o.toString())){
			sql2+=" order by pp.sorting";
		}
		obj.add(goods.getPpID());obj.add(goods.getCashierBillsID());obj.add("00");obj.add("01");		
		@SuppressWarnings("unchecked")
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql+sql2, obj.toArray());
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",list);map.put("goods",goods);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/*
	 * 获取产品信息
	 */
	public String operationPage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest re=ServletActionContext.getRequest();
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionAssembly ass=(ProductionAssembly)baseBeanService.getBeanByHqlAndParams(
				"from ProductionAssembly where proAssemblyID=? ",new Object[]{assembly.getProAssemblyID()});
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?",new Object[]{utboundOrderVo.getPpId()});
		ProductInspection pi=(ProductInspection)baseBeanService.getBeanByHqlAndParams(
				"from ProductInspection where proAssemblyID=? and status=?", new Object[]{assembly.getProAssemblyID(),"00"});
		re.setAttribute("ass", ass);re.setAttribute("pi",pi);
		re.setAttribute("pp",pp);re.setAttribute("goods",goods);
		re.setAttribute("orgId", session.get("organizationID").toString());
		re.setAttribute("st",getTwoLevelMenu(goods.getPpID()));

		return "page";
	}
	/*
	 * 检验产品
	 */
	public String inspection(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		List<BaseBean> list=new ArrayList<BaseBean>();
		ProductInspection pi=(ProductInspection)baseBeanService.getBeanByHqlAndParams(
				"from ProductInspection where proInspectionID=? and proAssemblyID=?",new Object[]{
						inspection.getProInspectionID(),inspection.getProAssemblyID()});
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionDocuments pd=(ProductionDocuments)baseBeanService.getBeanByHqlAndParams(
				"from ProductionDocuments where proDocumentsID=?",new Object[]{goods.getCashierBillsID()});
		ProductionAssembly ass=(ProductionAssembly)baseBeanService.getBeanByHqlAndParams(
				"from ProductionAssembly where proAssemblyID=?", new Object[]{inspection.getProAssemblyID()});
		
		if(pi==null){
			pi=new ProductInspection();
			pi.setProInspectionID(serverService.getServerID("ProductInspection"));
			pi.setProAssemblyID(inspection.getProAssemblyID());
			pi.setSingleID(account.getStaffID());
			pi.setSingleName(account.getStaffName());
			pi.setSingleTime(new SimpleDateFormat("yyyy-MM-dd : ").format(new Date()));
		}
		pi.setQualifiedQuantity(inspection.getQualifiedQuantity());
		pi.setResidualQuantity(inspection.getQualifiedQuantity());
		pi.setUnqualifiedQuantity(inspection.getUnqualifiedQuantity());
		pi.setInspectionManID(inspection.getInspectionManID());
		pi.setInspectionManName(inspection.getInspectionManName());
		pi.setInspectionTime(inspection.getInspectionTime());
		pi.setRemarks(inspection.getRemarks());
		pi.setStatus((inspection.getQualifiedQuantity().equals("0")?"01":"00"));
		list.add(pi);
		
		String sql2="select pp.ppid,pp.goodsname,pp.quantity,a.proassemblyid,i.proInspectionID from (select p.ppid,p.goodsname,"
				+"p.quantity from dt_ProductPackaging p connect by p.parentId = prior p.ppid  start with p.ppID = ?) pp"
				+" left join dtassembly a on a.productid=pp.ppid and a.productpid=? left join dtInspection i on i.proAssemblyID=a.proAssemblyID ";

		@SuppressWarnings("unchecked")
		List<Object> insList=baseBeanService.getListBeanBySqlAndParams(sql2, new Object[]{ass.getProductID(),goods.getPpID()});
		for(int i=1;i<insList.size();i++){
			Object[] obj=(Object[]) insList.get(i);
			if(obj[4]==null||"".equals(obj[4])){
				ProductInspection pir=new ProductInspection();
				pir.setProInspectionID(serverService.getServerID("ProductInspection"));
				pir.setProAssemblyID(obj[3].toString());
				pir.setQualifiedQuantity(Integer.parseInt(inspection.getQualifiedQuantity())*Integer.parseInt(obj[2].toString())+"");
				pir.setResidualQuantity(Integer.parseInt(inspection.getQualifiedQuantity())*Integer.parseInt(obj[2].toString())+"");
				pir.setUnqualifiedQuantity(Integer.parseInt(inspection.getUnqualifiedQuantity())*Integer.parseInt(obj[2].toString())+"");
				pir.setInspectionManID(inspection.getInspectionManID());
				pir.setInspectionManName(inspection.getInspectionManName());
				pir.setInspectionTime(inspection.getInspectionTime());
				pir.setStatus((inspection.getQualifiedQuantity().equals("0")?"01":"00"));
				pir.setSingleID(account.getStaffID());
				pir.setSingleName(account.getStaffName());
				pir.setRemarks("系统自动生成");
				list.add(pir);
			}
		}
		
		if(inspection.getQualifiedQuantity().equals("0")&&pd.getSuperposition().equals("00")){
			String sql="update dtAssembly a set a.status=? where a.productid in (select pp2.ppid from (select"
						+" pp.ppid,rownum rn from (select p.ppid from dt_ProductPackaging p connect by p.parentId =" 
					+" prior p.ppid  start with p.ppID = ?) pp) pp2 where pp2.rn>1)  and a.productpid=?";
			ass.setStatus("02");ass.setOutgoingQuantity("");list.add(ass);
			baseBeanService.saveBeansListAndexecuteSqlsByParams(list,new String[]{sql},new Object[]{"02",ass.getProductID(),ass.getProductPID()});
		}else{
			String sql="select i.proinspectionid,ass.proassemblyid,ass.outgoingQuantity,pp.ppid from (select p.goodsname,p.ppid,p.parentId,p.quantity,p.productCode," +
					"p.hierarchical  from dt_ProductPackaging p connect by p.parentId = prior p.ppid  start with" 
					+" p.ppID = ? and p.goodsname=?) pp left join dtassembly ass on ass.productid=pp.ppid and" 
					+" ass.productpid=? and ass.cashierbillsid=? left join dtinspection i on ass.proassemblyid=" +
					"i.proassemblyid and i.status='00' where  pp.hierarchical=2";
			@SuppressWarnings("unchecked")
			List<Object> qList=baseBeanService.getListBeanBySqlAndParams(sql,
					new Object[]{goods.getPpID(),goods.getGoodsName(),goods.getPpID(),pd.getProDocumentsID()});
			int ir=0;String assID="";
			for(int i=0;i<qList.size();i++){
				Object[] o=(Object[]) qList.get(i);
				if(o[0]==null||o[0].equals("")){
					ir++;
					if(o[1]!=null)
						assID=o[1].toString();
				}
			}
			List<String> sqls=new ArrayList<String>();
			List<Object[]> objs=new ArrayList<Object[]>();
			if(ir==1&&assID.equals(inspection.getProAssemblyID())&&!"0".equals(inspection.getQualifiedQuantity())){
				DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(
						"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"检验库","001",account.getCompanyID()});
				for(int i=0;i<qList.size();i++){
					Object[] or=(Object[]) qList.get(i);
					ProductPackaging pp2=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
							"from ProductPackaging where ppID=?", new Object[]{or[3]});
						outboundOperation(list,pp2,depot,account,or[2].toString());
						sqls.add("update  dtgoodsnum g3 set g3.status=? where g3.gnid in (select g2.gnid from (select * from dtgoodsnum g1 where g1.goodsid=? and g1.status=? and g1.goodsbillsid=? order by g1.goodsnumber) g2 where rownum<=?)");
						objs.add(new Object[]{"04",pp2.getGoodsID(),"03",goods.getGoodsBillsID(),or[2].toString()});
				}
				ProductionAssembly ass2=(ProductionAssembly)baseBeanService.getBeanByHqlAndParams(
						"from ProductionAssembly where productID=? and productPID=? and cashierBillsID=?", 
						new Object[]{goods.getPpID(),goods.getPpID(),pd.getProDocumentsID()});
				ProductInspection pis=new ProductInspection();
				pis.setProInspectionID(serverService.getServerID("ProductInspection"));
				pis.setProAssemblyID(ass2.getProAssemblyID());
				pis.setQualifiedQuantity(goods.getQuantity());
				pis.setResidualQuantity(goods.getQuantity());
				pis.setUnqualifiedQuantity("0");
				pis.setInspectionManID(inspection.getInspectionManID());
				pis.setInspectionManName(inspection.getInspectionManName());
				pis.setInspectionTime(inspection.getInspectionTime());
				pis.setStatus("00");
				pis.setSingleID(account.getStaffID());
				pis.setSingleName(account.getStaffName());
				pis.setRemarks("系统自动生成");
				list.add(pis);
				pd.setStatus("02");list.add(pd);
			}
			baseBeanService.executeSqlsByParmsList(list,sqls.toArray(new String[sqls.size()]),objs);
		}
		return "success";
	}
	
	/*
	 * 单产品检验
	 */
	public String singleInspection(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		List<BaseBean> list=new ArrayList<BaseBean>();
		List<String> sqls=new ArrayList<String>();
		List<Object[]> objs=new ArrayList<Object[]>();
		ProductInspection pi=(ProductInspection)baseBeanService.getBeanByHqlAndParams(
				"from ProductInspection where proInspectionID=? and proAssemblyID=?",new Object[]{
						inspection.getProInspectionID(),inspection.getProAssemblyID()});
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{utboundOrderVo.getGoodsbillsid()});
		ProductionDocuments pd=(ProductionDocuments)baseBeanService.getBeanByHqlAndParams(
				"from ProductionDocuments where proDocumentsID=?",new Object[]{goods.getCashierBillsID()});
		ProductionAssembly ass=(ProductionAssembly)baseBeanService.getBeanByHqlAndParams(
				"from ProductionAssembly where proAssemblyID=?", new Object[]{inspection.getProAssemblyID()});
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?", new Object[]{goods.getPpID()});
		
		if(pi==null){
			pi=new ProductInspection();
			pi.setProInspectionID(serverService.getServerID("ProductInspection"));
			pi.setProAssemblyID(inspection.getProAssemblyID());
			pi.setSingleID(account.getStaffID());
			pi.setSingleName(account.getStaffName());
			pi.setSingleTime(new SimpleDateFormat("yyyy-MM-dd : ").format(new Date()));
		}
		pi.setQualifiedQuantity(inspection.getQualifiedQuantity());
		pi.setResidualQuantity(inspection.getQualifiedQuantity());
		pi.setUnqualifiedQuantity(inspection.getUnqualifiedQuantity());
		pi.setInspectionManID(inspection.getInspectionManID());
		pi.setInspectionManName(inspection.getInspectionManName());
		pi.setInspectionTime(inspection.getInspectionTime());
		pi.setRemarks(inspection.getRemarks());
		pi.setStatus((inspection.getQualifiedQuantity().equals("0")?"01":"00"));
		list.add(pi);
				
		DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(
				"from DepotManage where depotName=? and depotPID=? and companyID=?", new Object[]{"检验库","001",account.getCompanyID()});
		outboundOperation(list,pp,depot,account,ass.getOutgoingQuantity());
		sqls.add("update  dtgoodsnum g3 set g3.status=? where g3.gnid in (select g2.gnid from (select * from dtgoodsnum g1 where g1.goodsid=? and g1.status=? and g1.goodsbillsid=? order by g1.goodsnumber) g2 where rownum<=?)");
		objs.add(new Object[]{"04",pp.getGoodsID(),"03",goods.getGoodsBillsID(),ass.getOutgoingQuantity()});
		
		pd.setStatus("02");list.add(pd);
		
		baseBeanService.executeSqlsByParmsList(list,sqls.toArray(new String[sqls.size()]),objs);
		return "success";
	}
	
	/*
	 * 获取产品二级菜单
	 */
	@SuppressWarnings("unchecked")
	private String getTwoLevelMenu(String ppID){
		 String 	sql2=" select pp.ppid, pp.parentid, pp.goodsname from (select p.goodsname, p.ppid,p.parentId," 
					+"p.hierarchical from dt_ProductPackaging p connect by p.parentId= prior p.ppid start with" 
					+" p.ppid=?)  pp where pp.hierarchical = '2'";
		List<Object> list2=baseBeanService.getListBeanBySqlAndParams(sql2, new Object[]{ppID});
		if(list2.size()<1)
			return "00";
		else
			return "01";
	}

	private String outboundOperation(List<BaseBean> list,
			ProductPackaging pp,DepotManage depot,CAccount account,String quantity){
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
 		Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(
				"from Inventory where goodsID=? and warehouse=? and productId=?",new Object[]{pp.getGoodsID(),depot.getDepotID(),pp.getPpID()});
		inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())-Integer.parseInt(quantity)+"");
		inv.setSumPrice((Double.parseDouble(inv.getSumPrice())-Integer.parseInt(quantity)*Double.parseDouble(inv.getUnitPrice()))+"");
		list.add(inv);
		return inv.getInventoryID();
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
	public ProductionAssembly getAssembly() {
		return assembly;
	}
	public void setAssembly(ProductionAssembly assembly) {
		this.assembly = assembly;
	}
	public ProductInspection getInspection() {
		return inspection;
	}
	public void setInspection(ProductInspection inspection) {
		this.inspection = inspection;
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
