package hy.ea.human.action.salary;

import hy.base.action.BaseAction;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
/**
 * 计件工资金额
 * @author l
 *
 */
@SuppressWarnings("serial")
public class ConfJJMoneyAction extends BaseAction<CashierBills>{

	private GoodsBills gbs;
	private String search;
	private String conftype;
	/**
	 * 查询
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", gbs);
		return findItem();
	}
	
	/**
	 * 加载
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findItem(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		String sql = "select cb.cashierbillsid, gb.goodsbillsid, cb.companyname, cb.projectname, gb.targetdeptname," +
			" gb.targetSalerID, gb.targetSalerName, gb.goodsnum, gb.goodsname, gb.quantity, gb.price," +
			" gb.connectid, gb.connectname, jj.cj_state, jj.cj_tcxs,gb.targetStart,gb.targetEnd from Dtgoodsbills gb" +
			" join dt_human_wage_cof_jj jj on jj.jj_code_name = gb.goodsname" +
			" left join dtCashierBills cb on gb.cashierbillsid = cb.cashierbillsid" +
			" left join dtCos c on c.staffid = gb.targetSalerID" +
			" left join dt_hr_deptpost d on c.deppostid = d.deppostid" +
			" where gb.companyid = ? and c.cosStatus = ? and c.status = ? and jj.conf_jj_state = ?" +
			" and cb.paystatus = ?";
		List list = new ArrayList();
		list.add(this.getCurrentAccount().getCompanyID());
		list.add("50");
		list.add("01");
		list.add(Long.parseLong("0"));
		list.add("02");
		
		if(null == conftype ){
			sql += " and cb.organizationID = ?";
			list.add(this.getOrganizationId());
		}
		if (search != null && search.equals("search")) {
			gbs = (GoodsBills) session.get("tablesearch");
			if(null != gbs.getTargetSalerName() && !gbs.getTargetSalerName().equals("")){
				sql += " and gb.targetSalerName like ?";
				list.add("%" + gbs.getTargetSalerName().trim() + "%");
			}
			if(null != gbs.getGoodsName() && !gbs.getGoodsName().equals("")){
				sql += " and gb.goodsname like ?";
				list.add("%" + gbs.getGoodsName().trim() + "%");
			}
		}
		sql += " and d.deppostid = jj.deppost_i_d ";
		pageForm = baseBeanService.getPageFormBySQL(
			(null != pageForm ? pageForm.getPageNumber() : 1),
			(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) from ("
				+ sql +" )", list.toArray());
		
		return "itemList";
	}


	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public GoodsBills getGbs() {
		return gbs;
	}

	public void setGbs(GoodsBills gbs) {
		this.gbs = gbs;
	}

	public String getConftype() {
		return conftype;
	}

	public void setConftype(String conftype) {
		this.conftype = conftype;
	}
	
	
}
