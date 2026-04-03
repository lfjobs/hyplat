package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.Warehouse;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 进销存 库存
 * 
 * @author Administrator
 * 
 */
@Controller
@Scope("prototype")
public class InvoicingStockAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;

	private FinancialBill financialBill; // 进销存单据表(采购单据表)
	/**
	 * '05' 盘库单 '06' 移库单
	 */
	private String type;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private String companyname;
	private String organizationname;
	private Map<String, FinancialBillsGood> goodsmap;
	private String sdate;
	private String edate;
	private String message;
	private List<FinancialBillsGood> BillsGoodList;
	private List<BaseBean> warehouseList;

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
		sql += " and f.billStatus=?";
		parms.add(type);
		if (search != null && search.equals("search")) {
			financialBill = (FinancialBill) session.get("financialBill");
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
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				sql += " and f.billsDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate, "yyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyy-MM-dd"));
			}
		}
		sql += " order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 查看、打印预览调用方法
	 */
	public void toSee() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		// 表单
		String hql = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
		financialBill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { groupCompanySn, account.getCompanyID(),
						organizationID, financialBill.getFinancialbillID() });
		// 物品
		BillsGoodList = new ArrayList<FinancialBillsGood>();
		String hqlg = "from FinancialBillsGood where financialbillID=? order by numbers";
		List<BaseBean> financialBillsGoodList = baseBeanService
				.getListBeanByHqlAndParams(hqlg, new Object[] { financialBill
						.getFinancialbillID() });
		if (financialBillsGoodList != null) {
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
					if (financialBillsGood.getShiftQuantity() != null
							&& !financialBillsGood.getShiftQuantity()
									.equals("")) {
						String[] arys = new String[] {
								financialBillsGood.getKu(),
								financialBillsGood.getQu(),
								financialBillsGood.getJia(),
								financialBillsGood.getWei() };
						String[] arys1 = new String[4];
						for (int j = 0; j < arys.length; j++) {
							arys1[j] = ((Warehouse) baseBeanService
									.getBeanByHqlAndParams(
											"from Warehouse where companyID=? and wareID=?",
											new Object[] {
													account.getCompanyID(),
													arys[j] })).getWareName();
						}
						financialBillsGood.setKu(arys1[0]);
						financialBillsGood.setQu(arys1[1]);
						financialBillsGood.setJia(arys1[2]);
						financialBillsGood.setWei(arys1[3]);
					}
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
		// 公司
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { financialBill.getCompanyID() });
		companyname = company.getCompanyName();
		// 部门
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						financialBill.getCompanyID(),
						financialBill.getOrganizationID() });
		organizationname = cOrganization.getOrganizationName();
	}

	/** ********************************************************盘库管理******************************************************* */
	/**
	 * 盘库管理——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toStocktakingSearch() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		return getStocktakingList();
	}

	/**
	 * 盘库管理——列表
	 * 
	 * @return
	 */
	public String getStocktakingList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID };
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus='50' and c.organizationID=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "Stocktakinglist";
	}

	/**
	 * 盘库管理——到添加页面
	 * 
	 * @return
	 */
	public String addStocktaking() {
		return "addstocktaking";
	}

	/**
	 * 盘库管理——保存
	 */
	public String saveStocktaking() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		financialBill
				.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加盘库单据（凭证号" + financialBill.getJournalNum() + "）";
		financialBill.setOrganizationID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);
		financialBill.setBillsType("盘库单");
		financialBill.setBillStatus("05");
		financialBill.setBillsDate(new Date());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		financialBill.setBillStaffName(sta.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		if (financialBill.getStaffName() != null
				&& !financialBill.getStaffName().equals("")) {
			String staffname = financialBill.getStaffName().substring(0,
					financialBill.getStaffName().indexOf("-"));
			financialBill.setStaffName(staffname);
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(financialBill);
		if (goodsmap != null) {
			for (FinancialBillsGood goods : goodsmap.values()) {
				if (goods.getGoodsID() != null
						&& !"".equals(goods.getGoodsID())) {
					goods
							.setFinancialbillID(financialBill
									.getFinancialbillID());
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
					FinancialBillsGood.setOrgMap(null);
					String hql1 = " from Inventory where goodsID=? and warehouse=? and area=? and frame=? and position=?";
					Inventory inventory = (Inventory) baseBeanService
							.getBeanByHqlAndParams(hql1, new Object[] {
									goods.getGoodsID(), goods.getPware(),
									goods.getParea(), goods.getPframe(),
									goods.getPlace() });
					if (inventory == null) {
						message = "所选库房中没有所填物品  " + goodsManage.getGoodsName()
								+ "!  请核对信息...";
						return "failed";
					} else {
						goods.setInvenQuantity(inventory.getInvenQuantity());
						goods.setInventoryID(inventory.getInventoryID());
						inventory.setInvenQuantity(goods
								.getStocktakingQuantity());
						inventory.setInventoryID(goods.getInventoryID());
						inventory.setGoodsID(goods.getGoodsID());
						baseBeanList.add(inventory);
					}
				}
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "success";
	}

	/**
	 * 盘库管理——查看
	 */
	public String toStocktaking() {
		toSee();
		return "tostocktaking";
	}

	/**
	 * 盘库管理——打印预览
	 */
	public String toStocktakingPrint() {
		toSee();
		return "tostocktakingprint";
	}

	/** ********************************************************移库管理******************************************************* */
	/**
	 * 移库管理——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toShiftSearch() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		return getShiftList();
	}

	/**
	 * 移库管理——列表
	 * 
	 * @return
	 */
	public String getShiftList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID };
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus='50' and c.organizationID=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "ShiftList";
	}

	/**
	 * 移库管理——到添加页面
	 * 
	 * @return
	 */
	public String addShift() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from Warehouse where companyID=? and  wareType=? and states=?";
		warehouseList = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID(), "1", "00" });
		return "addshift";
	}

	/**
	 * 移库管理——保存
	 */
	public String saveShift() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		financialBill
				.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加移库单据（凭证号" + financialBill.getJournalNum() + "）";
		financialBill.setOrganizationID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);
		financialBill.setBillsType("移库单");
		financialBill.setBillStatus("06");
		financialBill.setBillsDate(new Date());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		financialBill.setBillStaffName(sta.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		if (financialBill.getStaffName() != null
				&& !financialBill.getStaffName().equals("")) {
			String staffname = financialBill.getStaffName().substring(0,
					financialBill.getStaffName().indexOf("-"));
			financialBill.setStaffName(staffname);
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(financialBill);
		if (goodsmap != null) {
			for (FinancialBillsGood goods : goodsmap.values()) {
				if (goods.getGoodsID() != null
						&& !"".equals(goods.getGoodsID())) {
					goods
							.setFinancialbillID(financialBill
									.getFinancialbillID());
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

					FinancialBillsGood.setOrgMap(null);
					String hql1 = " from Inventory where goodsID=? and warehouse=? and area=? and frame=? and position=?";
					Inventory inventory = (Inventory) baseBeanService
							.getBeanByHqlAndParams(hql1, new Object[] {
									goods.getGoodsID(), goods.getPware(),
									goods.getParea(), goods.getPframe(),
									goods.getPlace() });
					if (inventory == null) {
						message = "所选库房中没有所填物品  " + goodsManage.getGoodsName()
								+ "!  请核对信息...";
						return "failed";
					} else {
						goods.setUnitPrice(inventory.getUnitPrice());
						goods.setQuantity(inventory.getInvenQuantity());
						goods.setAmount(inventory.getSumPrice());
						float iqt = Float.parseFloat(inventory
								.getInvenQuantity());
						float sqt = Float.parseFloat(goods.getShiftQuantity());
						if (iqt >= sqt) {
							float si = iqt - sqt;
							inventory.setInvenQuantity(String.valueOf(si));
							inventory.setSumPrice(String.valueOf(Float
									.parseFloat(inventory.getUnitPrice())
									* si));
						} else {
							message = "移库物品 " + goodsManage.getGoodsName()
									+ " 的数量不足 " + sqt + ", 库存数量为 " + iqt;
							return "failed";
						}
						baseBeanList.add(inventory);

						hql1 = " from Inventory where goodsID=? and warehouse=? and area=? and frame=? and position=?";
						inventory = (Inventory) baseBeanService
								.getBeanByHqlAndParams(hql1, new Object[] {
										goods.getGoodsID(), goods.getKu(),
										goods.getQu(), goods.getJia(),
										goods.getWei() });
						if (inventory == null) {
							Inventory inven = new Inventory();
							inven.setInventoryID(serverService
									.getServerID("inventory"));
							goods.setInventoryID(inven.getInventoryID());
							inven.setCompanyID(account.getCompanyID());
							inven.setGroupCompanySn(groupCompanySn);
							inven.setWarehouse(goods.getKu());
							inven.setArea(goods.getQu());
							inven.setFrame(goods.getJia());
							inven.setPosition(goods.getWei());
							inven.setGoodsID(goods.getGoodsID());
							inven.setGoodsType(goods.getType());
							inven.setGoodsName(goods.getGoodsName());
							inven.setBarcode(goods.getSortCode());
							inven.setStandard(goods.getSortCode());
							inven.setUnit(goods.getUnit());
							inven.setUnitPrice(String.valueOf(Double
									.parseDouble(goods.getUnitPrice())));
							inven.setInvenQuantity(goods.getShiftQuantity());
							inven.setSumPrice(String.valueOf(Float
									.parseFloat(goods.getUnitPrice())
									* sqt));
							String hql2 = "from Warehouse where companyID=? and wareID=?";
							String[] arys = new String[] { goods.getKu(),
									goods.getQu(), goods.getJia(),
									goods.getWei() };
							String[] arys1 = new String[4];
							for (int i = 0; i < arys.length; i++) {
								arys1[i] = ((Warehouse) baseBeanService
										.getBeanByHqlAndParams(hql2,
												new Object[] {
														account.getCompanyID(),
														arys[i] }))
										.getWareName();
							}
							inven.setWarehouseName(arys1[0]);
							inven.setAreaName(arys1[1]);
							inven.setFrameName(arys1[2]);
							inven.setPositionName(arys1[3]);
							baseBeanList.add(inven);
						} else {
							int invenQuant = Integer.parseInt(inventory
									.getInvenQuantity());// 结存商品数量
							int quantity = Integer.parseInt(goods
									.getShiftQuantity());// 收入商品数量

							double sumPrice = Double.parseDouble(inventory
									.getSumPrice());// 结存商品总金额
							double amount = Double.parseDouble(goods
									.getAmount());// 收入商品总金额
							double averagePrice = (sumPrice + amount)
									/ (invenQuant + quantity);// 移动加权平均单价
							BigDecimal b = new BigDecimal(averagePrice);// 转换类型
							double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
									.doubleValue();// 四舍五入后保留小数点后俩位
							int sum = invenQuant + quantity;// 入库后库存商品总数量
							double sumPrice1 = f1 * sum;// 入库后库存商品总金额
							goods.setInventoryID(inventory.getInventoryID());
							inventory.setSumPrice(String.valueOf(sumPrice1));
							inventory.setUnitPrice(String.valueOf(f1));
							inventory.setInvenQuantity(String.valueOf(sum));
							baseBeanList.add(inventory);
						}
					}
				}
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "success";
	}

	/**
	 * 盘库管理——查看
	 */
	public String toShift() {
		toSee();
		return "toShift";
	}

	/**
	 * 盘库管理——打印预览
	 */
	public String toShiftPrint() {
		toSee();
		return "toShiftprint";
	}

	public FinancialBill getFinancialBill() {
		return financialBill;
	}

	public void setFinancialBill(FinancialBill financialBill) {
		this.financialBill = financialBill;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
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

	public Map<String, FinancialBillsGood> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, FinancialBillsGood> goodsmap) {
		this.goodsmap = goodsmap;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FinancialBillsGood> getBillsGoodList() {
		return BillsGoodList;
	}

	public void setBillsGoodList(List<FinancialBillsGood> billsGoodList) {
		BillsGoodList = billsGoodList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BaseBean> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<BaseBean> warehouseList) {
		this.warehouseList = warehouseList;
	}

}
