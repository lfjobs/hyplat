package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.Warehouse;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.production.service.WarehouseService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

/**
 * 进销存出库
 * 
 * @author Administrator
 * 
 */
public class InvoicingWarehousingAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private WarehouseService warehouseService;
	public InputStream excelStream;
	private FinancialBill financialBill; // 进销存单据表(采购单据表)
	private FinancialBillsGood financialBillsGood;// 子表
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
	private String kutime;
	private stockInv stockinv;

	private Object[] objectny;
	List<Object[]> lists;

	private String zongID;
	
	private String arrgoodsNum; 

	/**
	 * 仓库列表中--库列表
	 */
	private List<BaseBean> warehouseList;

	private List<FinancialBillsGood> BillsGoodList;

	private String message;

	private ContactUser contactUser1;

	private ContactCompanyView contactCompanyView1;

	private Inventory inventoryParam;
	
	private stockInv stockinvParam;

	private String sdate;

	private String edate;

    private String state;	
    
    private String one;
    
    private String print;
    
    private int camount;

	private String result;

	private String typeID;
	private String subjectsID;
	
	private String seedID;//库存物品id
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	/**
	 * 前台传入参数 作用于销售出库 状态值 07
	 */
	private String billStatus;
	/**
	 * 库房管理打印条码
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	public String getListGoodsNumByID() throws UnsupportedEncodingException {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { seedID,"03",account.getCompanyID()};
		String sql = "select dg.gnID,dg.goodsnumber,gm.goodsName,gm.goodsCoding from dtgoodsnum dg " 
		             + "left join dtGoodsManage gm on dg.goodsID=gm.goodsID " 
				     + "where dg.cashierBillsID= ? and dg.status=? and dg.companyID=?";
		List goodsNumList = baseBeanService.getListBeanBySqlAndParams(
				sql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsNumList", goodsNumList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @return
	 */
	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql = VOtool.getCashierBillVO(4);
		sql += " where f.groupCompanySn = ?";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and f.organizationID=?";
		parms.add(organizationID);
		sql += "and f.billStatus=?";
		parms.add(billStatus != null && billStatus.equals("07") ? "07" : "03");
		if (search != null && search.equals("search")) {
			FinancialBill financialBill = (FinancialBill) session
					.get("financialBill");
			if (financialBill.getJournalNum() != null
					&& !financialBill.getJournalNum().trim().equals("")) {
				sql += " and f.journalNum like ? ";
				parms.add("%" + financialBill.getJournalNum().trim() + "%");
			}
			if (financialBill.getStaffID() != null
					&& !financialBill.getStaffID().equals("")) {
				sql += " and f.staffID = ? ";
				parms.add(financialBill.getStaffID());
			}
			if (financialBill.getCcompanyRelationship() != null
					&& !financialBill.getCcompanyRelationship().equals("")) {
				sql += " and f.ccompanyRelationship = ? ";
				parms.add(financialBill.getCcompanyRelationship());
			}
			if (financialBill.getCstaffRelationship() != null
					&& !financialBill.getCstaffRelationship().equals("")) {
				sql += " and f.cstaffRelationship = ? ";
				parms.add(financialBill.getCstaffRelationship());
			}
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				sql += " and to_date(f.billsDate,'yyyy-MM-dd') between ? and ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if (financialBill.getCcompanyName() != null
					&& !financialBill.getCcompanyName().trim().equals("")) {
				sql += " and f.ccompanyName like ? ";
				parms.add("%" + financialBill.getCcompanyName().trim() + "%");
			}
			if (financialBill.getCstaffName() != null
					&& !financialBill.getCstaffName().trim().equals("")) {
				sql += " and f.cstaffName like ? ";
				parms.add("%" + financialBill.getCstaffName().trim() + "%");
			}
		}
		sql += "order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/** ********************************************************出库管理******************************************************* */

	/**
	 * 出库管理——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearchWare() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		ActionContext.getContext().getSession().put("financialBillsGood",
				financialBillsGood);
		ActionContext.getContext().getSession().put("sdate",
				sdate);
		ActionContext.getContext().getSession().put("edate",
				edate);
		return getWareManagementList();
	}

	/**
	 * 出库管理——列表
	 * 
	 * @return
	 */
	public String getWareManagementList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID };
		List<Object> list = getWareList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus='50' and c.organizationID=?) ";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "warelist";
	}

	// 按出库审核确定需求理解
	public List<Object> getWareList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql = " select g.financialgoodid,g.goodsnum,g.sortcode,g.goodsname,"
				+ " g.type,g.quantity,g.unitprice, g.amount,case when g.status='00' then '未收货' when g.status='01' then '已收货' "
				+ " when g.status='02' then '已验库'  when g.status='03' then '已入库' when g.status='07' then '出库'"
				+ " when g.status='08' then '入库失败' when g.status='09' then '出库审核' else '' end, "
				+ " case when g.goodstatus='00' then '正常' when g.goodstatus='01' then '维修' "
				+ " when g.goodstatus='02' then '报废' else '' end, f.journalNum, f.billsType,f.StaffName, "
				+ " case when f.billStatus='00' then '未收货' when f.billStatus='01' then '已收货' when f.billStatus='02' then '已入库' "
				+ " when f.billStatus='03' then '已出库'"
				+ " when f.billStatus='04' then '市场调查'"
				+ " when f.billStatus='07' then '销售出库' else '' end  from dt_inv_fbg g"
				+ " left join dt_inv_fb f on g.financialbillid=f.financialbillid";
		sql += " where f.groupCompanySn = ? ";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and f.organizationID=? ";
		parms.add(organizationID);
		sql += " and f.billStatus=? ";
		parms.add(billStatus != null && billStatus.equals("07") ? "07" : "03");
		if (search != null && search.equals("search")) {
			FinancialBill financialBill = (FinancialBill) session
					.get("financialBill");
			FinancialBillsGood financialBillsGood = (FinancialBillsGood) session
					.get("financialBillsGood");
			String sdate=(String)session.get("sdate");
			String edate=(String)session.get("edate");
			if (financialBillsGood.getGoodsNum() != null
					&& !financialBillsGood.getGoodsNum().trim().equals("")) {
				sql += " and g.goodsNum like ? ";
				parms.add("%" + financialBillsGood.getGoodsNum().trim() + "%");
			}
			if (financialBill.getJournalNum() != null
					&& !financialBill.getJournalNum().trim().equals("")) {
				sql += " and f.journalNum like ? ";
				parms.add("%" + financialBill.getJournalNum().trim() + "%");
			}
			if (financialBill.getStaffID() != null
					&& !financialBill.getStaffID().equals("")) {
				sql += " and f.staffID = ? ";
				parms.add(financialBill.getStaffID());
			}
			if (financialBill.getCcompanyRelationship() != null
					&& !financialBill.getCcompanyRelationship().equals("")) {
				sql += " and f.ccompanyRelationship = ? ";
				parms.add(financialBill.getCcompanyRelationship());
			}
			if (financialBill.getCstaffRelationship() != null
					&& !financialBill.getCstaffRelationship().equals("")) {
				sql += " and f.cstaffRelationship = ? ";
				parms.add(financialBill.getCstaffRelationship());
			}
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				sql += " and to_date(f.billsDate,'yyyy-MM-dd') between ? and ? ";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if (financialBill.getCcompanyName() != null
					&& !financialBill.getCcompanyName().trim().equals("")) {
				sql += " and f.ccompanyName like ? ";
				parms.add("%" + financialBill.getCcompanyName().trim() + "%");
			}
			if (financialBill.getCstaffName() != null
					&& !financialBill.getCstaffName().trim().equals("")) {
				sql += " and f.cstaffName like ? ";
				parms.add("%" + financialBill.getCstaffName().trim() + "%");
			}
		}
		sql += " order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	// 出库管理出库确认
	@SuppressWarnings("deprecation")
	public String updatback() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		
		List<Object[]> list = new ArrayList<Object[]>();
		List<BaseBean> listbabe = new ArrayList<BaseBean>();
		parameter = "员工：" + account.getAccountName();
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql = "update FinancialBillsGood set status=? where financialgoodID=? ";
		String[] hqls = { hql };
		list
				.add(new Object[] { "07",
						financialBillsGood.getFinancialgoodID() });
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
		// List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		String hql1 = "from FinancialBillsGood where status=? and financialgoodID=?";
		FinancialBillsGood inv = (FinancialBillsGood) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] { "07",
						financialBillsGood.getFinancialgoodID() });
		stockInv stockinvs = new stockInv();
		stockinvs.setStockinvID(serverService.getServerID("stockInv"));
		stockinvs.setCompanyID(account.getCompanyID());
		stockinvs.setGroupCompanySn(groupCompanySn);
		stockinvs.setGoodsID(inv.getGoodsID());
		stockinvs.setGoodsType(inv.getType());
		stockinvs.setGoodsName(inv.getGoodsName());
		stockinvs.setInvenQuantity(inv.getQuantity());
		String hql2 = "from Inventory where goodsID=?";
		Inventory Inventorys = (Inventory) baseBeanService
				.getBeanByHqlAndParams(hql2, new Object[] { inv.getGoodsID() });
		if (Inventorys != null) {
			if (Inventorys.getInvenOnline() != null) {
				stockinvs.setInvenOnline(Inventorys.getInvenOnline());
				stockinvs.setInvenUnderline(Inventorys.getInvenUnderline());
			}
		}
		stockinvs.setSummoney(inv.getAmount());// 商品总金额
		stockinvs.setIntime(new Date());
		stockinvs.setType("01");
		listbabe.add(stockinvs);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(listbabe, null,
				null);
		return getWareManagementList();
	}

	/**
	 * 出库驳回
	 */
	public String rebut() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list = new ArrayList<Object[]>();
		List<BaseBean> listbabe = new ArrayList<BaseBean>();
		parameter = "员工：" + account.getAccountName();
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql = "from FinancialBillsGood where status=? and financialgoodID=? ";
		FinancialBillsGood goods = (FinancialBillsGood) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { "09",
						financialBillsGood.getFinancialgoodID() });
		String hql1 = "from Inventory where inventoryID=? and companyID=?";
		Inventory inv = (Inventory) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] {
						goods.getInventoryID(), account.getCompanyID() });
		int nums = Integer.parseInt(goods.getQuantity())
				+ Integer.parseInt(inv.getInvenQuantity());
		double prince = Double.parseDouble(inv.getUnitPrice()) * nums;
		String hql2 = "update Inventory set invenQuantity=?,sumPrice=? where  inventoryID=? and companyID=?";
		String[] hqls = { hql2 };
		list.add(new Object[] { String.valueOf(nums), String.valueOf(prince),
				goods.getInventoryID(), account.getCompanyID() });
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
		String hql3 = "delete FinancialBillsGood where status=? and financialgoodID=? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(listbabe,
				new String[] { hql3 }, new Object[] { "09",
						financialBillsGood.getFinancialgoodID() });
		return getWareManagementList();
	}

	/**
	 * 出库管理—查看
	 * 
	 * @return
	 */
	public String toWareManagement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String hqls = "from FinancialBillsGood where  financialgoodID=?";
		FinancialBillsGood ss = (FinancialBillsGood) baseBeanService
				.getBeanByHqlAndParams(hqls, new Object[] { financialBillsGood
						.getFinancialgoodID() });
		String hql = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
		financialBill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { groupCompanySn, account.getCompanyID(),
						organizationID, ss.getFinancialbillID() });
		
		BillsGoodList = new ArrayList<FinancialBillsGood>();
		String hqlg = "from FinancialBillsGood where financialbillID=? order by numbers";
		List<BaseBean> financialBillsGoodList = baseBeanService
				.getListBeanByHqlAndParams(hqlg, new Object[] { financialBill
						.getFinancialbillID() });
		//计算总金额
		if(financialBillsGoodList!=null){
		    int size = financialBillsGoodList.size();
			for(int i=0;i<size;i++) {
			financialBillsGood=(FinancialBillsGood) financialBillsGoodList.get(i); 
			int amount=Integer.parseInt(financialBillsGood.getAmount());
			camount+=amount;
		     }
		}
		if (financialBillsGoodList != null
				&& financialBillsGoodList.size() != 0
				&& financialBillsGoodList.get(0) != null) {
			for (int i = 0; i < financialBillsGoodList.size(); i++) {
				FinancialBillsGood financialBillsGood = (FinancialBillsGood) financialBillsGoodList
						.get(i);
				String hql1 = " from Inventory where inventoryID=?";
				Inventory inventory = (Inventory) baseBeanService
						.getBeanByHqlAndParams(hql1,
								new Object[] { financialBillsGood
										.getInventoryID() });
				if (inventory != null) {
					financialBillsGood.setPware(inventory.getWarehouse());
					financialBillsGood.setParea(inventory.getArea());
					financialBillsGood.setPframe(inventory.getFrame());
					financialBillsGood.setPlace(inventory.getPosition());
					BillsGoodList.add(financialBillsGood);
				}
			}
		}
		String hql1 = "from Warehouse where companyID=?";
		List<BaseBean> warehouseList1 = baseBeanService
				.getListBeanByHqlAndParams(hql1, new Object[] { account
						.getCompanyID() });
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < warehouseList1.size(); i++) {
			Warehouse warehouse = (Warehouse) warehouseList1.get(i);
			map.put(warehouse.getWareID(), warehouse.getWareName());
		}
		FinancialBillsGood.setOrgMap(map);

		String hql2 = " from ContactUser where relationID=? ";
		contactUser1 = (ContactUser) baseBeanService.getBeanByHqlAndParams(
				hql2, new Object[] { financialBill.getCstaffID() });

		String hql3 = " from ContactCompanyView where ccompanyID=? ";
		contactCompanyView1 = (ContactCompanyView) baseBeanService
				.getBeanByHqlAndParams(hql3, new Object[] { financialBill
						.getCcompanyID() });

		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { financialBill.getCompanyID() });
		companyname = company.getCompanyName();

		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						financialBill.getCompanyID(),
						financialBill.getOrganizationID() });
		organizationname = cOrganization.getOrganizationName();
		if(print!=null&&"print".equals("print")){
			return "wareeditprint";
		}
		return "wareedit";
	}

	/**
	 * 出库管理,添加页面
	 * 
	 * @return
	 */
	public String addWareManagement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		String companyID = account.getCompanyID();
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");

		String hql = "from Warehouse where companyID=? and  wareType=? and states=?";
		warehouseList = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID(), "1", "00" });
		return "wareadd";
	}

	/**
	 * 出库管理——保存
	 */
	public String saveWareManagement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		financialBill.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加采购单据（凭证号" + financialBill.getJournalNum() + "）";
		financialBill.setOrganizationID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);

		financialBill.setBillsType(billStatus != null
				&& billStatus.equals("07") ? "销售出库单" : "出库单");
		financialBill.setBillStatus(billStatus != null
				&& billStatus.equals("07") ? "07" : "03");
		financialBill.setBillsDate(new Date());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		financialBill.setBillStaffName(sta.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		//financialBill.setStaffID(sta.getStaffID());
		//financialBill.setStaffName(sta.getStaffName());
		/*if (financialBill.getStaffName() != null
				&& !financialBill.getStaffName().equals("")) {
			String staffname = financialBill.getStaffName().substring(0,
					financialBill.getStaffName().indexOf("-"));
			financialBill.setStaffName(staffname);
		}*/
		if (financialBill.getCstaffID() != null
				&& !"".equals(financialBill.getCstaffID())) {
			String hql2 = " from ContactUser where relationID=? ";
			ContactUser contactUser1 = (ContactUser) baseBeanService
					.getBeanByHqlAndParams(hql2, new Object[] { financialBill
							.getCstaffID() });
			financialBill.setCstaffName(contactUser1.getStaffName());
			financialBill.setCstaffRelationship(contactUser1.getRelation());
		}
		if (financialBill.getCcompanyID() != null
				&& !"".equals(financialBill.getCcompanyID())) {
			String hql3 = " from ContactCompanyView where ccompanyID=? ";
			ContactCompanyView contactCompanyView1 = (ContactCompanyView) baseBeanService
					.getBeanByHqlAndParams(hql3, new Object[] { financialBill
							.getCcompanyID() });
			financialBill.setCcompanyName(contactCompanyView1.getCompanyName());
			financialBill.setCcompanyRelationship(contactCompanyView1
					.getContactConnections());
			financialBill.setCcompanyTel(contactCompanyView1.getCompanyTel());
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(financialBill);
		if (goodsmap != null) {
			for (FinancialBillsGood goods : goodsmap.values()) {
				goods.setFinancialbillID(financialBill.getFinancialbillID());
				goods.setFinancialgoodID(serverService
						.getServerID("financialgood"));
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setGoodsName(goodsManage.getGoodsName());
				goods.setGoodsNum(goodsManage.getGoodsCoding());
				goods.setModelNumber(goodsManage.getModel());
				goods.setBrand(goodsManage.getMnemonicCode());
				goods.setType(goodsManage.getTypeID());
				goods.setStatus("09");
				goods.setGoodstatus("00");
				FinancialBillsGood.setOrgMap(null);
				if (goods.getGoodsID() != null
						&& !"".equals(goods.getGoodsID())) {
					Inventory inventory = new Inventory();
					if (goods.getWeizhi() != null
							&& !"".equals(goods.getWeizhi())) {
						String hql1 = " from Inventory where inventoryID=? and weizhi=? ";
						inventory = (Inventory) baseBeanService
								.getBeanByHqlAndParams(hql1, new Object[] {
										goods.getInventoryID(),
										goods.getWeizhi() });
					} else {
						String hql1 = " from Inventory where inventoryID=? and weizhi is null ";
						inventory = (Inventory) baseBeanService
								.getBeanByHqlAndParams(hql1,
										new Object[] { goods.getInventoryID() });
					}
					if (inventory == null) {
						message = "所选库房中没有所填物品  " + goodsManage.getGoodsName()
								+ "!  请核对信息...";
						return "failed";
					} else {
						int invenQuant = Integer.parseInt(inventory
								.getInvenQuantity());// 结存商品数量
						int quantity = Integer.parseInt(goods.getQuantity());// 出库商品数量
						if (invenQuant >= quantity) {
							int sum = invenQuant - quantity;// 出库后商品总数量
							inventory.setInvenQuantity(String.valueOf(sum));
							goods.setInventoryID(inventory.getInventoryID());
							inventory.setSumPrice(String.valueOf(Double
									.parseDouble(inventory.getUnitPrice())
									* sum));
							baseBeanList.add(inventory);
						} else {
							message = "所选出库物品 " + goodsManage.getGoodsName()
									+ " 的数量不足 " + quantity + ", 库存数量为 "
									+ invenQuant;
							return "failed";
						}
					}
					baseBeanList.add(goods);
				}
			}
			dd(baseBeanList,goodsmap,arrgoodsNum);
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "success";
	}
	
	/**
	 * 
	 * @param baseBeanList
	 * @param goodsmap
	 */
	private void dd(List<BaseBean> baseBeanList,Map<String, FinancialBillsGood> goodsmap,String arrGoodsNum)
	{
		int i =0;
		String[] arr = arrGoodsNum.split(",");
		for (FinancialBillsGood goods : goodsmap.values()) {
			String goodsId = goods.getGoodsID();
			String inventoryId = goods.getInventoryID();
			String hql = " from GoodsNum where cashierBillsID = ? and goodsID = ? and goodsnumber = ?";
			GoodsNum goodsNum = (GoodsNum)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{inventoryId,goodsId,arr[i]});
			goodsNum.setStatus("04");
			baseBeanList.add(goodsNum);			
			i++;
		}
	}

	/**
	 * 库房列表sql与参数
	 * 
	 * @return
	 */
	public List<Object> getInventoryManagementBySqlAndParams() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql = "select zong.goodsid,zong.vv2,zong.vv3,zong1.qq1, " +
				"case when zong1.qq1 >= zong.vv4 then '蓝色' when zong1.qq1 <= zong.vv5 " +
				"then '红色' when (zong1.qq1 < zong.vv4) and (zong1.qq1 > zong.vv5) then " +
				"'黑色' else '' end,zong1.qq1 cc,zong1.qq2 from (select distinct (goodsid) " +
				"goodsid, companyid vv1,goodsname vv2, goodstype vv3, invenonline vv4," +
				"invenunderline vv5 from dt_inv_stockinvt) zong left join (select s.s1 qq, " +
				"case when t.d2 is null then s.s2 else s.s2 - t.d2 end qq1, case when " +
				"t.d3 is null then s.s3 else s.s3 - t.d3 end qq2 from (select t.goodsid s1," +
				"sum(t.invenquantity) s2,sum(t.summoney) s3 from dt_inv_stockinvt t where " +
				"t.type = '00' and t.companyid = ? group by (t.goodsid)) s left join " +
				"(select t.goodsid d1,sum(t.invenquantity) d2,sum(t.summoney) d3 from " +
				"dt_inv_stockinvt t where t.type in ('01', '02') and t.companyid = ? " +
				"group by (t.goodsid)) t on s.s1 = t.d1) zong1 on zong.goodsid = zong1.qq " +
				"where zong.vv1 = ?";
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			Inventory inven = (Inventory) session.get("tableSearch");
			if (inven != null && inven.getGoodsType() != null
					&& !"".equals(inven.getGoodsType().trim())) {
				sql += " and zong.vv3 like ?";
				parms.add("%" + inven.getGoodsType().trim() + "%");
			}
			if (inven != null && inven.getGoodsName() != null
					&& !"".equals(inven.getGoodsName().trim())) {
				sql += " and zong.vv2 like ?";
				parms.add("%" + inven.getGoodsName().trim() + "%");
			}
		}
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
//进销存明细表
	public String getspan() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<Object> parms = new ArrayList<Object>();
		String sql = "select t.inventoryid ,t.goodsname,t.goodstype,t.barcode,t.invenquantity," 
		        + "t.unitprice,t.sumprice,t.goodstatus,t.unit,(t.invenquantity+t.badquantity)," 
				+ "case when (t.invenquantity+t.badquantity)>=t.invenonline then '蓝色' " 
		        + "when (t.invenquantity+t.badquantity)<=invenunderline then '红色' " 
				+ "when ((t.invenquantity+t.badquantity)<t.invenonline) and ((t.invenquantity+t.badquantity)>invenunderline)  then '黑色'" 
		        + " else '' end,t.invenquantity-invenunderline,t.warehouseName"
				+ " from dt_inv_invt t  left join "
				+ "(select goodsid,sum(invenquantity) a1,sum(badquantity) a2,sum(invenquantity + badquantity) a3 from dt_inv_invt where goodstatus='00' group by (goodsid)) s "
				+ " on t.goodsid=s.goodsid " + " where t.companyid=?";
		parms.add(account.getCompanyID());
		/*sql += " and t.status=?";
		parms.add("03");*/
		sql += " and t.goodsid=?";
		parms.add(zongID);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 库房列表
	 * 
	 * @return
	 */
	public String getInventoryManagementList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from Warehouse where companyID=? and wareType='1'";
		warehouseList = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		List<Object> list = getInventoryManagementBySqlAndParams();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);

		return "inventoryManagement";
	}
	/**
	 * 进销存明细列表
	 * 
	 * @return
	 */
	public String getInventoryPoolList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from Warehouse where companyID=? and wareType='1'";
		warehouseList = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID() });
		List<Object> list = getInventoryPoolBySqlAndParams();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		if(print!=null&&"print".equals("print")){
			return "inventoryPoolBill";
		}
		return "inventoryPool";
	}
	/**
	 * 进销存明细列表sql与参数
	 * 
	 * @return
	 */
	public List<Object> getInventoryPoolBySqlAndParams() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql = "select t.goodsID,t.goodsname,t.goodstype,t.intime,case when t.type='00' then '入库' when t.type='01'then '出库' when t.type='02'then '借出' else '' end,t.invenQuantity,t.summoney"
				+ " from dt_inv_stockinvt t"
				+ " where t.companyid=? ";
		parms.add(account.getCompanyID());
		
		if (search != null && search.equals("search")) {
			stockInv stoinv = (stockInv) session.get("tableSearch");
			String sdate=(String)session.get("sdate");
			String edate=(String)session.get("edate");
			if (stoinv != null && stoinv.getGoodsType() != null
					&& !"".equals(stoinv.getGoodsType().trim())) {
				sql += " and t.goodstype like ?";
				parms.add("%" + stoinv.getGoodsType().trim() + "%");
			}
			if (sdate != null && edate != null && !"".equals(sdate)
			&& !"".equals(edate)) {
				sql += " and to_date(t.intime,'yyyy-MM-dd hh24:mi:ss') " +
						"between to_date(?,'yyyy-MM-dd hh24:mi:ss') " +
						"and  to_date(?,'yyyy-MM-dd hh24:mi:ss')";
				parms.add(sdate);
				parms.add(edate);
			}
			if (stoinv != null && stoinv.getGoodsName() != null
					&& !"".equals(stoinv.getGoodsName().trim())) {
				sql += " and t.goodsname like ?";
				parms.add("%" + stoinv.getGoodsName().trim() + "%");
			}
		}
		sql+=" order by intime desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 进销存汇总列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getInventoryDetailList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");//帐号，用户
		List<Object> list = getInventoryDetailBySqlAndParams();//汇总查询
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);	
		
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		// 获取每月总金额
		String sql1 = "select sum(ins.summoney) ss from dt_inv_stockinvt ins where ins.intime<=? and ins.type=? and ins.companyid=?";
		String timeyear = Utilities.getDateString(new Date(), "yyyy-MM");
		String mm = "";
		if (timeyear.substring(5, 6).equals("0")) {
			mm = timeyear.substring(6, 7);
		} else {
			mm += timeyear.substring(5, 7);
		}
		lists = new ArrayList<Object[]>();
		for (int i = 1; i <= Integer.parseInt(mm); i++) {
			int tt = Utilities.getDayMouth(Integer.parseInt(timeyear.substring(
					0, 4)), i);
			String time = timeyear.substring(0, 4) + "-" + i + "-" + tt
					+ " 23:59:59";
			List<BaseBean> list1 = baseBeanService.getListBeanBySqlAndParams(
					sql1, new Object[] { time, "00", account.getCompanyID() });
			List<BaseBean> list2 = baseBeanService.getListBeanBySqlAndParams(
					sql1, new Object[] { time, "01", account.getCompanyID() });
			List<BaseBean> list3 = baseBeanService.getListBeanBySqlAndParams(
					sql1, new Object[] { time, "01", account.getCompanyID() });
			Object[] obj = new Object[4];
			obj[0] = getMonth(i);
			if (list1.size() > 0) {
				obj[1] = list1.get(0);
			} else {
				obj[1] = null;
			}
			if (list2.size() > 0) {
				obj[2] = list2.get(0);
			} else {
				obj[2] = null;
			}
			if (list3.size() > 0) {
				obj[3] = list3.get(0);
			} else {
				obj[3] = null;
			}
			lists.add(obj);
		}
		objectny = lists.toArray();
		if(print!=null&&"print".equals("print")){
			return "inventoryDetailBill";
		}
		return "inventoryDetail";
	}
	/**
	 * 进销存汇总列表sql与参数
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<Object> getInventoryDetailBySqlAndParams() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String times = (new Date().toLocaleString()).substring(0, 6) + "%";
		kutime = (new Date().toLocaleString()).substring(0, 4) + "年"
				+ (new Date().toLocaleString()).substring(5, 6) + "月";
		
	    String year=(new Date().toLocaleString()).substring(0, 4);
	    //上一年计算
	    String syear=String.valueOf(Integer.parseInt(year)-1);
	    //上一个月计算
	    String month=String.valueOf(Integer.parseInt((new Date().toLocaleString())
				.substring(5, 6)) - 1); 
	    String ssd="";
	    if(month.equals("0")){
	    	ssd = syear+ "-12" + "%";
	    }else{
	    	ssd = (new Date().toLocaleString()).substring(0, 5)
					+ month + "%";
	    }
		String sql="";
		if(state!= null && state.equals("1"))
		{sql="select zong.vv1,zong.vv2,zong.vv3, bb.bb1, zong2.aa1,zong2.aa2,zong1.qq1,zong.vv4 ||'~'|| zong.vv5,zong1.qq1 cc,zong1.qq2";}
		else if(state== null)
		{sql="select zong.goodsid,zong.vv1,zong.vv2,zong.vv3, bb.bb1, zong2.aa1,zong2.aa2,zong1.qq1,zong.vv4,zong.vv5,zong1.qq1 cc,zong1.qq2";}
		
		sql +=" from (select distinct (goodsid) goodsid,companyid vv1,goodsname vv2,goodstype vv3,invenonline vv4,invenunderline vv5 from dt_inv_stockinvt) zong"
				+ " left join (select s.s1 qq, case when t.d2 is null then s.s2 else s.s2 - t.d2 end qq1, case when t.d3 is null then s.s3 else s.s3 - t.d3 end qq2"
				+ " from (select t.goodsid s1,sum(t.invenquantity) s2,  sum(t.summoney) s3 "
				+ " from dt_inv_stockinvt t where t.type = '00' group by (t.goodsid)) s"
				+ " left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3"
				+ " from dt_inv_stockinvt t where t.type in( '01','02') group by (t.goodsid)) t on s.s1 = t.d1) zong1 "
				+ " on zong.goodsid = zong1.qq"
				+ " left join (select s.s1 aa, case when c.c2 is null then  s.s2  else  s.s2 - c.c2 end aa1, case  when t.d2 is null then  0 else  t.d2 end aa2"
				+ "  from (select t.goodsid s1, sum(t.invenquantity) s2, sum(t.summoney) s3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) s"
				+ " left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type = '01' group by (t.goodsid)) t on s.s1 = t.d1"
				+ " left join (select goodsid c1,sum(invenquantity) c2 from dt_inv_stockinvt where intime like ? and type in ('01','02') group by goodsid) c"
				+ " on c.c1= s.s1) zong2 "
				+ " on zong.goodsid = zong2.aa"
				+ " left join (select ss.ss1 bb,case when tt.dd2 is null then  ss.ss2 when ss.ss2 is null then  0  else ss.ss2 - tt.dd2 end bb1"
				+ " from (select t.goodsid ss1, sum(t.invenquantity) ss2, sum(t.summoney) ss3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) ss"
				+ " left join (select t.goodsid dd1, sum(t.invenquantity) dd2, sum(t.summoney) dd3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type in('01','02')"
				+ " group by (t.goodsid)) tt on ss.ss1 = tt.dd1) bb on zong.goodsid =  bb.bb where zong.vv1 = ?";
		parms.add(times);
		parms.add(times);
		parms.add(times);
		parms.add(ssd);
		parms.add(ssd);
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			stockInv stoinv = (stockInv) session.get("tableSearch");
			if (stoinv != null && stoinv.getGoodsType() != null
					&& !"".equals(stoinv.getGoodsType().trim())) {
				sql += " and zong.vv3 like ?";
				parms.add("%" + stoinv.getGoodsType().trim() + "%");
			}
			if (stoinv != null && stoinv.getGoodsName() != null
					&& !"".equals(stoinv.getGoodsName().trim())) {
				sql += " and zong.vv2 like ?";
				parms.add("%" + stoinv.getGoodsName().trim() + "%");
			}
		}
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 进销存明细列表查询
	 * 
	 * @return
	 */
	public String toSearchInventoryPool() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tableSearch", stockinvParam);
		session.put("sdate", sdate);//开始日期
		session.put("edate", edate);//结束日期
		return getInventoryPoolList();
	}
	/**
	 * 进销存汇总列表查询
	 * 
	 * @return
	 */
	public String toSearchInventoryDetail() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tableSearch", stockinvParam);
		session.put("sdate", sdate);//开始日期
		session.put("edate", edate);//结束日期
		return getInventoryDetailList();
	}
	
	/**
	 * 库房列表查询
	 * 
	 * @return
	 */
	public String toSearchInventoryManagement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tableSearch", inventoryParam);
		return getInventoryManagementList();
	}
	/**
	 * 进销存明细列表导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String poolShowExcel() {
		List<Object> result = getInventoryPoolBySqlAndParams();
		String sql = result.get(0).toString();
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] prarms = (Object[]) result.get(1);
		excelStream = excelService.showExcel(stockInv.columnHeading(),
				baseBeanService.getListBeanBySqlAndParams(sql, prarms));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook logbook = logBookService.saveCLogBook(null, "导出进销存明细列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
	/**
	 * 进销存汇总列表导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String detailShowExcel() {
		List<Object> result = getInventoryDetailBySqlAndParams();
		String sql = result.get(0).toString();
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] prarms = (Object[]) result.get(1);
		excelStream = excelService.showExcel(stockInv.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, prarms));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook logbook = logBookService.saveCLogBook(null, "导出进销存汇总列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
	/*
	 * 微分金Excel导出
	 */
	@SuppressWarnings("unchecked")
	public String WFJExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String sql="select  s.goodsname,s.goodstype,s.intime,case when s.type='00' then '入库' when" +
				" s.type='01'then '出库' when s.type='02'then '借出' else '' end,s.invenQuantity,to_char(s.summoney, 'FM99999990.099')," +
				"gg.goodsname,cc.ctUserName from dt_inv_stockinvt s " +
				"left join dtgoodsbills g on s.goodsbillsid = g.goodsbillsid " +
				"left join dtcashierbills c on g.cashierbillsid = c.cashierbillsid " +
				"left join dtcashierbills cc on c.jnumorder=cc.journalnum " +
				"left join dtgoodsbills gg on gg.cashierbillsid=cc.cashierbillsid " +
				"where s.companyid = ? and s.goodsname=? order by intime desc";
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID(),"微分金金币"});
		String[] str={"物品名称","物品类别","日期","业务类型","数量","总金额","订单物品名称","会员名称"};
		excelStream=warehouseService.OutOrderExcel("进销存明细,-4", str, list);
		CLogBook logbook = logBookService.saveCLogBook(null, "导出进销存明细列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
	/**
	 * 库房列表导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		List<Object> result = getInventoryManagementBySqlAndParams();
		String sql = result.get(0).toString();
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] prarms = (Object[]) result.get(1);
		excelStream = excelService.showExcel(Inventory.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, prarms));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook logbook = logBookService.saveCLogBook(null, "导出库房列表", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	/**
	 * JSON取得库存物品列表
	 */
	public String getInventory() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sn = session.get("groupCompanySn").toString();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Inventory.class);
		dc.add(Restrictions.eq("groupCompanySn", sn));
		dc.add(Restrictions.eq("status", "03"));
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (null != typeID && !"".equals(typeID)) {
			dc.add(Restrictions.eq("goodsType", typeID));
		}
		if (null != subjectsID && !"".equals(subjectsID)) {
			dc.add(Restrictions.eq("subjectsID", subjectsID));
		}
		dc.add(Restrictions.or(Restrictions.eq("barcode", parameter), Restrictions.eq("goodsCoding", parameter)));

		List<BaseBean> list = baseBeanService.getListByDC(dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodlist", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 从dtgoodsnum表（对应GoodsNum.java对象）中获取库存商品的明细列表（即需要获取到库存中的每件物品，以达到通过物品编号和每件物品的序列号，跟踪物品的出库与库存情况）。
	 */
	@SuppressWarnings("unchecked")
	public String getInventoryWithGoodsNum() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		if (account == null) {			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyid = account.getCompanyID();
		String sn = session.get("groupCompanySn").toString();
		
		String sql = "select dgs.cashierbillsid,invt.weizhi,invt.goodsName,decode(invt.barCode,NULL, ' ',invt.barcode) as barCode,invt.goodscoding,invt.goodsType,1/*invt.invenQuantity*/," +
				"decode(invt.unit,NULL,' ',invt.unit) as unit,invt.goodsID,dgs.gnID,dgs.goodsNumber from dtgoodsnum dgs left join dt_inv_invt invt " +
				"on dgs.cashierBillsID = invt.inventoryID where dgs.status<'04' and dgs.companyid = ? and invt.groupCompanySn = ? ";
		
		if (!StringUtils.isBlank(parameter.trim())){
			sql += " and (invt.barcode = ? or invt.goodscoding = ?) "; 
		}
		sql += " order by dgs.goodsNumber ";
		
		List<BaseBean> list = null;
		
		if (!StringUtils.isBlank(parameter.trim())){
			list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyid,sn,parameter,parameter});
		}else{
			list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyid,sn});
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodlist", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * JSON取得库存物品列表
	 */
	public String QueryArchiveInfo() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from Inventory where defaultStorage = ?";
		Inventory inventory = (Inventory) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { parameter.trim() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inventory", inventory);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	// 申请报损功能 ct
	public String breakdowngood() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list = new ArrayList<Object[]>();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		List<BaseBean> listbabe = new ArrayList<BaseBean>();
		parameter = "员工：" + account.getAccountName()+"申请报损！";
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql = "from Inventory f where f.inventoryID=? and f.companyID=?";
		Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { inventoryParam.getInventoryID(),
						account.getCompanyID() });
		int numbad = 0;
		if (inv.getBadQuantity()==null) {
			numbad = Integer.parseInt(inventoryParam.getBadQuantity());
		} else {
			numbad = Integer.parseInt(inventoryParam.getBadQuantity())
					+ Integer.parseInt(inv.getBadQuantity());
		}
		int num = Integer.parseInt(inv.getInvenQuantity())
				- Integer.parseInt(inventoryParam.getBadQuantity());// 用于获得库存数量
		double asum = Double.parseDouble(inv.getUnitPrice()) * num;// 用于获得库存总价格
		String hql1 = "update Inventory set invenQuantity=?, badQuantity=?,sumPrice=?  where inventoryID=? and companyID=?";
		String[] hqls = { hql1 };
		list.add(new Object[] { String.valueOf(num), String.valueOf(numbad),
				String.valueOf(asum), inventoryParam.getInventoryID(),
				account.getCompanyID() });
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
		Inventory inven = new Inventory();
		inven.setInventoryID(serverService.getServerID("inventory"));
		inven.setCompanyID(account.getCompanyID());
		inven.setGroupCompanySn(inv.getGroupCompanySn());
		inven.setOrganizationID(organizationID);
		inven.setDepartmentID(organizationID);
		inven.setStaffID(inv.getStaffID());
		inven.setStaffName(inv.getStaffName());
		inven.setWarehouse(inv.getWarehouse());
		inven.setWarehouseName(inv.getWarehouseName());
		inven.setGoodsID(inv.getGoodsID());
		inven.setGoodsType(inv.getGoodsType());
		inven.setGoodsName(inv.getGoodsName());
		inven.setBarcode(inv.getBarcode());
		inven.setStandard(inv.getStandard());
		inven.setGoodsCoding(inv.getGoodsCoding());
		inven.setUnit(inv.getUnit());
		inven.setUnitPrice(inv.getUnitPrice());
		inven.setInvenQuantity(String.valueOf(num));
		inven.setBadQuantity(inventoryParam.getBadQuantity());
		inven.setGoodstatus(inventoryParam.getGoodstatus());
		inven.setSumPrice(String.valueOf(asum));
		inven.setSubjectsID(inv.getSubjectsID());
		inven.setSubjectsName(inv.getSubjectsName());
		inven.setStatus("10");
		inven.setInventoryNum(inv.getInventoryNum());//序号
		inven.setWeizhi(inv.getWeizhi());
		baseBeanList.add(inven);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "success";
	}
	// 设置上下限值
	public String record() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list = new ArrayList<Object[]>();
		List<BaseBean> listbabe = new ArrayList<BaseBean>();
		String hql = "from Inventory f where f.inventoryID=? and f.companyID=?";// 查询获取goodsid
		Inventory inv = (Inventory) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { inventoryParam.getInventoryID(),
						account.getCompanyID() });
		parameter = "员工：" + account.getAccountName()+"设置上下限！";
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql1 = "update Inventory set invenOnline=?,invenUnderline=?  where goodsID=? and companyID=? ";
		String[] hqls = { hql1 };
		list.add(new Object[] { inventoryParam.getInvenOnline(),
				inventoryParam.getInvenUnderline(), inv.getGoodsID(),
				account.getCompanyID() });
		listbabe.add(cLogBook);
        baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
		
		String hql2 = "update stockInv set invenOnline=?,invenUnderline=?  where goodsID=? and companyID=? ";
		String[] hql2s = { hql2 };
		baseBeanService.executeHqlsByParamsList(null, hql2s, list);
		return "success";
	}

	@SuppressWarnings("deprecation")
	public List<Object> panList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String times = (new Date().toLocaleString()).substring(0, 6) + "%";
		kutime = (new Date().toLocaleString()).substring(0, 4) + "年"
				+ (new Date().toLocaleString()).substring(5, 6) + "月";
		String ssd = (new Date().toLocaleString()).substring(0, 5)
				+ String.valueOf(Integer.parseInt((new Date().toLocaleString())
						.substring(5, 6)) - 1) + "%";
		String sql = "select zong.goodsid,zong.vv1,zong.vv2,zong.vv3, bb.bb1, zong2.aa1,zong2.aa2,zong1.qq1,zong.vv4,zong.vv5,zong1.qq1 cc,zong1.qq2"
				+ " from (select distinct (goodsid) goodsid,companyid vv1,goodsname vv2,goodstype vv3,invenonline vv4,invenunderline vv5 from dt_inv_stockinvt) zong"
				+ " left join (select s.s1 qq, case when t.d2 is null then s.s2 else s.s2 - t.d2 end qq1, case when t.d3 is null then s.s3 else s.s3 - t.d3 end qq2"
				+ " from (select t.goodsid s1,sum(t.invenquantity) s2,  sum(t.summoney) s3 "
				+ " from dt_inv_stockinvt t where t.type = '00' group by (t.goodsid)) s"
				+ " left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3"
				+ " from dt_inv_stockinvt t where t.type in( '01','02') group by (t.goodsid)) t on s.s1 = t.d1) zong1 "
				+ " on zong.goodsid = zong1.qq"
				+ " left join (select s.s1 aa, case when c.c2 is null then  s.s2  else  s.s2 - c.c2 end aa1, case  when t.d2 is null then  0 else  t.d2 end aa2"
				+ "  from (select t.goodsid s1, sum(t.invenquantity) s2, sum(t.summoney) s3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) s"
				+ " left join (select t.goodsid d1, sum(t.invenquantity) d2, sum(t.summoney) d3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type = '01' group by (t.goodsid)) t on s.s1 = t.d1"
				+ " left join (select goodsid c1,sum(invenquantity) c2 from dt_inv_stockinvt where intime like ? and type in ('01','02') group by goodsid) c"
				+ " on c.c1= s.s1) zong2 "
				+ " on zong.goodsid = zong2.aa"
				+ " left join (select ss.ss1 bb,case when tt.dd2 is null then  ss.ss2 when ss.ss2 is null then  0  else ss.ss2 - tt.dd2 end bb1"
				+ " from (select t.goodsid ss1, sum(t.invenquantity) ss2, sum(t.summoney) ss3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type = '00' group by (t.goodsid)) ss"
				+ " left join (select t.goodsid dd1, sum(t.invenquantity) dd2, sum(t.summoney) dd3"
				+ " from dt_inv_stockinvt t where t.intime like ? and t.type in('01','02')"
				+ " group by (t.goodsid)) tt on ss.ss1 = tt.dd1) bb on zong.goodsid =  bb.bb where zong.vv1 = ?";
		parms.add(times);
		parms.add(times);
		parms.add(times);
		parms.add(ssd);
		parms.add(ssd);
		parms.add(account.getCompanyID());
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
    //库存盘点
	@SuppressWarnings("unchecked")
	public String invprint() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = panList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		List<BaseBean> listw = (List<BaseBean>) baseBeanService
				.getListBeanBySqlAndParams(sql, parms);
		pageForm = new PageForm();
		pageForm.setList(listw);
		// 获取没有月总金额
		String sql1 = "select sum(ins.summoney) ss from dt_inv_stockinvt ins where ins.intime<=? and ins.type=? and ins.companyid=?";
		String timeyear = Utilities.getDateString(new Date(), "yyyy-MM");
		String mm = "";
		if (timeyear.substring(5, 6).equals("0")) {
			mm = timeyear.substring(6, 7);
		} else {
			mm += timeyear.substring(5, 7);
		}
		lists = new ArrayList<Object[]>();
		for (int i = 1; i <= Integer.parseInt(mm); i++) {
			int tt = Utilities.getDayMouth(Integer.parseInt(timeyear.substring(
					0, 4)), i);
			String time = timeyear.substring(0, 4) + "-" + i + "-" + tt
					+ " 23:59:59";
			List<BaseBean> list1 = baseBeanService.getListBeanBySqlAndParams(
					sql1, new Object[] { time, "00", account.getCompanyID() });
			List<BaseBean> list2 = baseBeanService.getListBeanBySqlAndParams(
					sql1, new Object[] { time, "01", account.getCompanyID() });
			List<BaseBean> list3 = baseBeanService.getListBeanBySqlAndParams(
					sql1, new Object[] { time, "01", account.getCompanyID() });
			Object[] obj = new Object[4];
			obj[0] = getMonth(i);
			if (list1.size() > 0) {
				obj[1] = list1.get(0);
			} else {
				obj[1] = null;
			}
			if (list2.size() > 0) {
				obj[2] = list2.get(0);
			} else {
				obj[2] = null;
			}
			if (list3.size() > 0) {
				obj[3] = list3.get(0);
			} else {
				obj[3] = null;
			}
			lists.add(obj);
		}
		objectny = lists.toArray();
		// stockInv=(stockInv)baseBeanService.getObjectBySqlAndParams(sql1,new
		// Object[]{})
		return "invprintss";
	}

	/**
	 * 获取月份
	 * 
	 * @param i
	 * @return
	 */
	private String getMonth(int i) {
		String mm = "";
		switch (i) {
		case 1:
			mm = "一月";
			break;
		case 2:
			mm = "二月";
			break;
		case 3:
			mm = "三月";
			break;
		case 4:
			mm = "四月";
			break;
		case 5:
			mm = "五月";
			break;
		case 6:
			mm = "六月";
			break;
		case 7:
			mm = "七月";
			break;
		case 8:
			mm = "八月";
			break;
		case 9:
			mm = "九月";
			break;
		case 10:
			mm = "十月";
			break;
		case 11:
			mm = "十一月";
			break;
		case 12:
			mm = "十二月";
			break;
		}
		return mm;
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

	public List<BaseBean> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<BaseBean> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public List<FinancialBillsGood> getBillsGoodList() {
		return BillsGoodList;
	}

	public void setBillsGoodList(List<FinancialBillsGood> billsGoodList) {
		BillsGoodList = billsGoodList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Inventory getInventoryParam() {
		return inventoryParam;
	}

	public void setInventoryParam(Inventory inventoryParam) {
		this.inventoryParam = inventoryParam;
	}

	public stockInv getStockinvParam() {
		return stockinvParam;
	}

	public void setStockinvParam(stockInv stockinvParam) {
		this.stockinvParam = stockinvParam;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public int getCamount() {
		return camount;
	}

	public void setCamount(int camount) {
		this.camount = camount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public FinancialBillsGood getFinancialBillsGood() {
		return financialBillsGood;
	}

	public void setFinancialBillsGood(FinancialBillsGood financialBillsGood) {
		this.financialBillsGood = financialBillsGood;
	}

	public String getKutime() {
		return kutime;
	}

	public void setKutime(String kutime) {
		this.kutime = kutime;
	}

	public stockInv getStockinv() {
		return stockinv;
	}

	public void setStockinv(stockInv stockinv) {
		this.stockinv = stockinv;
	}

	public Object[] getObjectny() {
		return objectny;
	}

	public void setObjectny(Object[] objectny) {
		this.objectny = objectny;
	}

	public List<Object[]> getLists() {
		return lists;
	}

	public void setLists(List<Object[]> lists) {
		this.lists = lists;
	}

	public String getZongID() {
		return zongID;
	}

	public void setZongID(String zongID) {
		this.zongID = zongID;
	}

	public String getSubjectsID() {
		return subjectsID;
	}

	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}

	public String getArrgoodsNum() {
		return arrgoodsNum;
	}

	public void setArrgoodsNum(String arrgoodsNum) {
		this.arrgoodsNum = arrgoodsNum;
	}
	public String getSeedID() {
		return seedID;
	}
	public void setSeedID(String seedID) {
		this.seedID = seedID;
	}	
	

}
