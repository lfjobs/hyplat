package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 个人仓库    PersonalWarehouseAction
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class PersonalWarehouseAction {
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	private String search;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private Inventory inventoryParam;
	private stockInv stockinv;
	private List<BaseBean> stafflist;

	/**
	 * 个人库房列表查询
	 * 
	 * @return
	 */
	public String toSearchInventoryManagement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tableSearch", inventoryParam);
		return getInventoryManagementList();
	}

	/**
	 * 个人库房列表
	 * 
	 * @return
	 */
	public String getInventoryManagementList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getInventoryManagementBySqlAndParams();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { account.getCompanyID(), "50" });
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);

		return "list";
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
		String sql = "select t.inventoryid,t.goodsname,t.goodstype,t.barcode,t.invenquantity,t.unitprice,t.sumprice,t.unit,t.goodstatus,t.goodsid"
				+ " from dt_inv_invt t "
				+ " where t.companyid=? and t.staffid=?";
		parms.add(account.getCompanyID());
		parms.add(account.getStaffID());
		sql += " and t.status=?";
		parms.add("03");
		if (search != null && search.equals("search")) {
			Inventory inven = (Inventory) session.get("tableSearch");
			if (inven != null && inven.getGoodsType() != null
					&& !"".equals(inven.getGoodsType().trim())) {
				sql += " and t.goodsType like ?";
				parms.add("%" + inven.getGoodsType().trim() + "%");
			}
			if (inven != null && inven.getGoodsName() != null
					&& !"".equals(inven.getGoodsName().trim())) {
				sql += " and t.goodsName like ?";
				parms.add("%" + inven.getGoodsName().trim() + "%");
			}
		}
		sql += " order by t.goodsname";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 物品借出
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getjiechu() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> parames = new ArrayList<BaseBean>();
		int quantity = Integer.parseInt(inventoryParam.getInvenQuantity());
		String staffid = inventoryParam.getStaffID();
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=?", new Object[] { staffid });
		inventoryParam = (Inventory) baseBeanService.getBeanByHqlAndParams(
				"from Inventory iy where iy.inventoryID=?",
				new Object[] { inventoryParam.getInventoryID() });
		if(Integer.parseInt(inventoryParam.getInvenQuantity())>quantity){
			int invenQuant = Integer.parseInt(inventoryParam.getInvenQuantity());// 结存商品数量
			double sumPrice = Double.parseDouble(inventoryParam.getSumPrice());// 结存商品总金额
			double unitPrice = Double.parseDouble(inventoryParam.getUnitPrice());// 商品单价
			double amount = unitPrice * quantity;// 收入合格商品总金额
			double averagePrice = (sumPrice - amount) / (invenQuant - quantity);// 移动加权平均单价
			BigDecimal b = new BigDecimal(averagePrice);
			double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 四舍五入后保留小数后俩位
			int sum = invenQuant - quantity;// 入库后库存商品总数量
			double sumPrice1 = f1 * sum;// 入库后库存商品总金额
			inventoryParam.setUnitPrice(String.valueOf(f1));
			inventoryParam.setSumPrice(String.valueOf(sumPrice1));
			inventoryParam.setInvenQuantity(String.valueOf(sum));
			parames.add(inventoryParam);
		}else{
			String hqlDelete=" delete from Inventory iy where iy.inventoryID=? ";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqlDelete}, new Object[]{inventoryParam.getInventoryID()});
		}
		String hql1 = " from Inventory where goodsID=? and staffID=? and weizhi is null and status=? and goodstatus=?";
		Inventory inventory = (Inventory) baseBeanService
				.getBeanByHqlAndParams(hql1,
						new Object[] { inventoryParam.getGoodsID(), staffid,
								"03", "00" });
		if (inventory == null) {
			Inventory inven = new Inventory();
			inven.setInventoryID(serverService.getServerID("inventory"));
			inven.setCompanyID(inventoryParam.getCompanyID());
			inven.setGroupCompanySn(inventoryParam.getGroupCompanySn());
			inven.setWeizhi(inventoryParam.getWeizhi());
			inven.setStaffID(staff.getStaffID());
			inven.setStaffName(staff.getStaffName());
			if (inventoryParam.getInvenOnline() != null) {
				inven.setInvenOnline(inventoryParam.getInvenOnline());
				inven.setInvenUnderline(inventoryParam.getInvenUnderline());
			}
			inven.setGoodstatus("00");
			inven.setStatus("03");
			inven.setBadQuantity("0");
			inven.setGoodsID(inventoryParam.getGoodsID());
			inven.setGoodsType(inventoryParam.getGoodsType());
			inven.setGoodsName(inventoryParam.getGoodsName());
			inven.setBarcode(inventoryParam.getBarcode());
			inven.setStandard(inventoryParam.getStandard());
			inven.setUnit(inventoryParam.getUnit());
			inven.setUnitPrice(String.valueOf(Double.parseDouble(inventoryParam
					.getUnitPrice())));
			inven.setInvenQuantity(String.valueOf(quantity));
			double unitPrice3 = Double.parseDouble(inventoryParam
					.getUnitPrice());// 商品单价
			inven.setSumPrice(String.valueOf(quantity * unitPrice3));// 商品总金额
			parames.add(inven);

		} else {
			int invenQuant2 = Integer.parseInt(inventory.getInvenQuantity());// 结存商品数量
			double sumPrice2 = Double.parseDouble(inventory.getSumPrice());// 结存商品总金额
			double unitPrice2 = Double.parseDouble(inventoryParam
					.getUnitPrice());// 商品单价
			double amount2 = unitPrice2 * quantity;// 收入合格商品总金额
			double averagePrice2 = (sumPrice2 + amount2)
					/ (invenQuant2 + quantity);// 移动加权平均单价
			BigDecimal b2 = new BigDecimal(averagePrice2);
			double f12 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 四舍五入后保留小数后俩位
			int sum2 = invenQuant2 + quantity;// 入库后库存商品总数量
			double sumPrice12 = f12 * sum2;// 入库后库存商品总金额
			inventory.setSumPrice(String.valueOf(sumPrice12));
			inventory.setUnitPrice(String.valueOf(f12));
			inventory.setInvenQuantity(String.valueOf(sum2));
			parames.add(inventory);
		}

		stockInv sInv = new stockInv();
		stockInv sInv2 = new stockInv();
		sInv.setStockinvID(serverService.getServerID("stockInv"));
		sInv.setCompanyID(inventoryParam.getCompanyID());
		sInv.setGroupCompanySn(inventoryParam.getGroupCompanySn());
		sInv.setGoodsID(inventoryParam.getGoodsID());
		sInv.setGoodsType(inventoryParam.getGoodsType());
		sInv.setGoodsName(inventoryParam.getGoodsName());
		sInv.setInvenQuantity(String.valueOf(quantity));
		sInv.setStaffID(staffid);
		sInv.setStaffName(staff.getStaffName());
		sInv.setType("02");

		sInv2.setStockinvID(serverService.getServerID("stockInv"));
		sInv2.setCompanyID(inventoryParam.getCompanyID());
		sInv2.setGroupCompanySn(inventoryParam.getGroupCompanySn());
		sInv2.setGoodsID(inventoryParam.getGoodsID());
		sInv2.setGoodsType(inventoryParam.getGoodsType());
		sInv2.setGoodsName(inventoryParam.getGoodsName());
		sInv2.setInvenQuantity(String.valueOf(quantity));
		sInv2.setType("00");
		if (inventoryParam.getInvenOnline() != null) {
			sInv.setInvenOnline(inventoryParam.getInvenOnline());
			sInv.setInvenUnderline(inventoryParam.getInvenUnderline());
			sInv2.setInvenOnline(inventoryParam.getInvenOnline());
			sInv2.setInvenUnderline(inventoryParam.getInvenUnderline());
		}
		double unitPrice4 = Double.parseDouble(inventoryParam.getUnitPrice());// 商品单价
		sInv.setSummoney(String.valueOf(Integer.parseInt(inventoryParam
				.getInvenQuantity()) * unitPrice4));// 商品总金额
		sInv.setIntime(new Date());
		sInv2.setSummoney(String.valueOf(Integer.parseInt(inventoryParam
				.getInvenQuantity()) * unitPrice4));// 商品总金额
		sInv2.setIntime(new Date());
		parames.add(sInv);
		parames.add(sInv2);
		parameter = "员工：" + inventoryParam.getStaffName() + "借出物品（物品 ID："
				+ inventoryParam.getGoodsID() + "）给员工：" + staff.getStaffName();
		CLogBook cLogBook = logBookService.saveCLogBook(null, parameter,
				account);
		parames.add(cLogBook);
		baseBeanService
				.saveBeansListAndexecuteHqlsByParams(parames, null, null);
		return "success";
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public Inventory getInventoryParam() {
		return inventoryParam;
	}

	public void setInventoryParam(Inventory inventoryParam) {
		this.inventoryParam = inventoryParam;
	}

	public stockInv getStockinv() {
		return stockinv;
	}

	public void setStockinv(stockInv stockinv) {
		this.stockinv = stockinv;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}
}
