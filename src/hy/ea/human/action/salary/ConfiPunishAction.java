package hy.ea.human.action.salary;

import hy.base.action.BaseAction;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.human.salary.WageCofi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
/**
 * 基础设定金额
 * 基本、职务、考评、考勤、奖惩
 * @author l
 *
 */
@SuppressWarnings("serial")
public class ConfiPunishAction extends BaseAction<CashierBills>{

	private GoodsBills gbs;
	private String search;
	private String strypee;//判断计划的状态根据状态查询
	private String conftype; //部门、生产 
	private String contype; //基本00-013411、职务01-013412、考评03-013413、考勤02-013414、奖惩04-013417
	
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
		String sql = "select cb.cashierbillsid, gb.goodsbillsid, cb.companyname, cb.projectname," +
			" gb.targetdeptname, gb.targetSalerID, gb.targetSalerName, gb.goodsnum, gb.goodsname," +
			" gb.quantity, gb.price, ci.addsub_state,gb.targetStart,gb.targetEnd" +
			" from Dtgoodsbills gb join dt_human_wage_cofi ci on ci.code_value = gb.goodsname" +
			" left join dtCashierBills cb on gb.cashierbillsid = cb.cashierbillsid where  gb.companyid = ?" +
			" and cb.paystatus = ?" ;
		List list = new ArrayList();
		list.add(this.getCurrentAccount().getCompanyID());
	
		list.add("03");
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
		pageForm = baseBeanService.getPageFormBySQL(
			(null != pageForm ? pageForm.getPageNumber() : 1),
			(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) from ("
				+ sql +" )", list.toArray());
		
		return "itemList";
	}
	/**
	 * 基本、职务、考评、考勤、奖惩提成Ajax
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCofiPunish(){
		String hql = " from WageCofi ci where ci.codeValue = ? and ci.companyId = ? " +
				"and ci.wageCofState = ? and ci.wageState = ?";
		List list = new ArrayList();
			list.add(gbs.getGoodsName());
			list.add(this.getCurrentAccount().getCompanyID());
			list.add(Long.parseLong("0"));
		if(contype.equals("013411")){
			list.add(Long.parseLong("0"));
		}else if(contype.equals("013412")){
			list.add(Long.parseLong("1"));
		}else if(contype.equals("013413")){
			list.add(Long.parseLong("2"));			
		}else if(contype.equals("013414")){
			list.add(Long.parseLong("3"));			
		}else if(contype.equals("013417")){
			list.add(Long.parseLong("4"));			
		}
		WageCofi cofi = (WageCofi)baseBeanService.getBeanByHqlAndParams(hql,list.toArray());
		
		if(null != cofi){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("cofi",cofi);
			JSONObject oj = JSONObject.fromObject(map);
			result = oj;
		}
		
		return "success";
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

	public String getContype() {
		return contype;
	}
	public String getStrypee() {
		return strypee;
	}

	public void setStrypee(String strypee) {
		this.strypee = strypee;
	}

	public void setContype(String contype) {
		this.contype = contype;
	}
	
	
}
