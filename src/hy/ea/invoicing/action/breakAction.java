package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import com.opensymphony.xwork2.ActionContext;

/**
 * 报损管理
 * @author Administrator
 *
 */
public class breakAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	public InputStream excelStream;
	private FinancialBill financialBill;  //进销存单据表(采购单据表)
	private FinancialBillsGood financialBillsGood;//子表
	private Inventory   inventoryParam;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private Map<String, FinancialBillsGood> goodsmap;
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	private String companyname;
	private String organizationname;
	private List<FinancialBillsGood> BillsGoodList;
	
	private ContactUser contactUser1;
	
	private ContactCompanyView contactCompanyView1;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;

	/**
	 * 报损管理——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearchWare() {
		ActionContext.getContext().getSession().put("inventoryParam",
				inventoryParam);
		return getbreakList();
	}

	/**
	 * 报损管理——列表
	 * 
	 * @return
	 */
	public String getbreakList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID};
		List<Object> list = getWareList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus='50' and c.organizationID=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "breaklist";
	}
	//按报损查询列表
	public List<Object> getWareList(){
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		//String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql="select f.inventoryID,f.goodsName,g.typeID,g.goodsCoding,f.barcode," +
				" f.badQuantity,f.unitPrice,f.badQuantity*f.unitPrice,case when f.goodstatus='00' then '正常' when f.goodstatus='01' then '维修' when f.goodstatus='02' then '报废' else '' end,g.model,f.unit " +
				" from dt_inv_invt f left join dtGoodsManage g on f.goodsID=g.goodsID ";
		sql+=" where f.groupCompanySn = ?";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql +=" and f.status=?";
		parms.add("10");
		sql +=" and (f.goodstatus=? or";
		parms.add("01");
		sql +=" f.goodstatus=?)";
		parms.add("02");
		if (search != null && search.equals("search")) {
			Inventory inventoryParam=(Inventory)session.get("inventoryParam");
			if(inventoryParam!=null&&inventoryParam.getGoodsName()!=null&&!"".equals(inventoryParam.getGoodsName().trim())){
				sql+=" and g.goodsName like ?";
				parms.add("%"+inventoryParam.getGoodsName().trim()+"%");
			}
			if(inventoryParam!=null&&inventoryParam.getGoodsCoding()!=null&&!"".equals(inventoryParam.getGoodsCoding().trim())){
				sql+=" and g.goodsCoding like ?";
				parms.add("%"+inventoryParam.getGoodsCoding().trim()+"%");
			}
		}
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	//报损确认
	public String updatbreak(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list=new ArrayList<Object[]>();
		List<BaseBean> listbabe=new ArrayList<BaseBean>(); 
		parameter = "员工："+account.getAccountName();
		CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);
		String hql1="from Inventory f where f.inventoryID=? and f.companyID=?";
		Inventory inv=(Inventory) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{inventoryParam.getInventoryID(),account.getCompanyID()} );
		int num =Integer.parseInt(inv.getBadQuantity());//用于获得库存数量
		double asum=Double.parseDouble(inv.getUnitPrice())*num;//用于获得库存总价格
		String hql="update Inventory set status=?,invenQuantity=?,sumPrice=? where inventoryID=? ";
		String[] hqls={hql};
		list.add(new Object[]{"03",inv.getBadQuantity(),String.valueOf(asum),inventoryParam.getInventoryID()});
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe,hqls,list);
		return getbreakList();
	}
	//报损驳回
	public String breakrebut(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list=new ArrayList<Object[]>();
		List<BaseBean> listbabe=new ArrayList<BaseBean>(); 
		parameter = "员工："+account.getAccountName();
		CLogBook cLogBook=logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql1="from Inventory where inventoryID=?";
		Inventory  inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{inventoryParam.getInventoryID()});//报损的那条数据
		Inventory  inv=new Inventory();
		if(inventory.getStaffID() !=null){
		if(inventory.getWeizhi() !=null){
		String hql2="from Inventory where goodsID=? and weizhi=? and staffID=? and status=? and goodstatus=? and unitPrice=?";
		inv=(Inventory)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{inventory.getGoodsID(),inventory.getWeizhi(),inventory.getStaffID(),"03","00",inventory.getUnitPrice()});
		}else{
			String hql2="from Inventory where goodsID=?  and staffID=? and status=? and goodstatus=? and unitPrice=? and weizhi is null";
			inv=(Inventory)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{inventory.getGoodsID(),inventory.getStaffID(),"03","00",inventory.getUnitPrice()});	
		}
		}else{
			if(inventory.getWeizhi() !=null){
			String hql2="from Inventory where goodsID=? and weizhi=?  and status=? and goodstatus=? and unitPrice=? and staffID is null";
			inv=(Inventory)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{inventory.getGoodsID(),inventory.getWeizhi(),"03","00",inventory.getUnitPrice()});	
			}else{
				String hql2="from Inventory where goodsID=?   and status=? and goodstatus=? and unitPrice=? and staffID is null and weizhi is null";
				inv=(Inventory)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{inventory.getGoodsID(),"03","00",inventory.getUnitPrice()});	
			}
		}
		String hql="update Inventory set invenQuantity=?,sumPrice=?,badQuantity=? where inventoryID=? ";
		int num=Integer.parseInt(inv.getInvenQuantity())+Integer.parseInt(inventory.getBadQuantity());
		double asum=Double.parseDouble(inv.getUnitPrice())*num;
		int numbak=Integer.parseInt(inv.getBadQuantity())-Integer.parseInt(inventory.getBadQuantity());
		String[] hqls={hql};
		list.add(new Object[]{String.valueOf(num),String.valueOf(asum),String.valueOf(numbak),inv.getInventoryID()});
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe,hqls,list);
		String hql3="delete Inventory where inventoryID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(listbabe,new String[] { hql3 }, new Object[] {inventoryParam.getInventoryID()});
		return getbreakList();
	}
	public FinancialBill getFinancialBill() {
		return financialBill;
	}

	public void setFinancialBill(FinancialBill financialBill) {
		this.financialBill = financialBill;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public Map<String, FinancialBillsGood> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, FinancialBillsGood> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}


	public List<FinancialBillsGood> getBillsGoodList() {
		return BillsGoodList;
	}

	public void setBillsGoodList(List<FinancialBillsGood> billsGoodList) {
		BillsGoodList = billsGoodList;
	}


	public ContactUser getContactUser1() {
		return contactUser1;
	}

	public void setContactUser1(ContactUser contactUser1) {
		this.contactUser1 = contactUser1;
	}

	public ContactCompanyView getContactCompanyView1() {
		return contactCompanyView1;
	}

	public void setContactCompanyView1(ContactCompanyView contactCompanyView1) {
		this.contactCompanyView1 = contactCompanyView1;
	}


	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}


	public FinancialBillsGood getFinancialBillsGood() {
		return financialBillsGood;
	}

	public void setFinancialBillsGood(FinancialBillsGood financialBillsGood) {
		this.financialBillsGood = financialBillsGood;
	}

	public Inventory getInventoryParam() {
		return inventoryParam;
	}

	public void setInventoryParam(Inventory inventoryParam) {
		this.inventoryParam = inventoryParam;
	}
	
	
}
