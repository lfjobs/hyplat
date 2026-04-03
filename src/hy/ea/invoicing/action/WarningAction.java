package hy.ea.invoicing.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.invoicing.Inventory;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;
/**
 * 库存预警 WarningAction
 * @author lf
 *
 */
public class WarningAction {
	@Resource
	private BaseBeanService baseBeanService;
	
	private Inventory inventory;
	private List<BaseBean> warehouseList;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	
	/**
	 * 库存预警——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("inventory",
				inventory);
		return getWarningList();
	}

	/**
	 * 库存预警——列表
	 * 
	 * @return
	 */
	public String getWarningList() {
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		/*warehouseList=baseBeanService.getListBeanByHqlAndParams("from Warehouse where wareName like '%库'", null);*/
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql = "select i.goodsid,w.wareid,i.groupCompanySn,w.warename,i.goodsname,"
				+ " i.unit,sum(i.invenquantity),i.invenonline,i.invenunderline"
				+ " from dt_inv_invt i"
				+ " left join dt_inv_wh w on w.wareid=i.warehouse"
				+ " where i.groupcompanysn=? and i.companyid=?";
		parms.add(groupCompanySn);
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			if (inventory.getWarehouse() != null
					&& !inventory.getWarehouse().equals("")) {
				sql += " and i.warehouse = ? ";
				parms.add(inventory.getWarehouse());
			}
		}
		sql+=" group by i.goodsid,i.warehouse,w.warename,w.wareid,i.groupCompanySn,i.goodsname,i.unit,i.invenonline,i.invenunderline having sum(i.invenquantity)>i.invenunderline";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) from( "
						+ sql+")", parms.toArray());
		return "list";
	}
	
	/**
	 * 设置上下限值
	 * @return
	 */
	public String updateUpDownLine(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "update Inventory set invenOnline = '"+inventory.getInvenOnline()+"',"
				+ " invenUnderline = '"+inventory.getInvenUnderline()+"'"
				+ " where groupCompanySn = ? and companyID = ? and warehouse = ? and goodsID = ?";
		Object[] params = {};
		if(inventory.getUnit() != null && !"".equals(inventory.getUnit())){
			hql += " and unit = ?";
			params = new Object[]{inventory.getGroupCompanySn(),account.getCompanyID(),
					inventory.getWarehouse(),inventory.getGoodsID(),inventory.getUnit()};
		}else{
			hql += " and unit is null";
			params = new Object[]{inventory.getGroupCompanySn(),account.getCompanyID(),
					inventory.getWarehouse(),inventory.getGoodsID()};
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[] {hql}, params);
		return "success";
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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

	public List<BaseBean> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<BaseBean> warehouseList) {
		this.warehouseList = warehouseList;
	}
	
	
}
