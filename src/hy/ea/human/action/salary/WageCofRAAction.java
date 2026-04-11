package hy.ea.human.action.salary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.base.action.BaseAction;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.salary.WageCofcl;
import hy.ea.bo.human.salary.WageCofi;
import hy.ea.bo.human.salary.WageCofra;
import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * 级差工资设定管理
 * @author zg
 *
 */
@SuppressWarnings("serial")
public class WageCofRAAction extends BaseAction<WageCofra> {

	private WageCofra wcofra=this.getModel();//
	private String parameter;
	private Map<String, WageCofcl> wcofclmap;
	
	private int nums;//
	private String search;
	
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", wcofra);
		return findItem();
	}
	
	//[wagcofra20150505PAGWP4GNRS0000000159, 2, 2015-01-01, A1-1, A1, 1400, 2300, 2300, 1000, 1400, 6200, 0, 2]
	public String findItem() {
		List<Object> list = getList();
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber),sql, "select count(*) from ("+sql+")" , params);
		return "itemList";
	}
	
	private List<Object> getList(){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		
		parms.add(1);
		parms.add(7);
		parms.add(0);
		parms.add(this.getCurrentAccount().getCompanyID());
		parms.add(1);
		 List<BaseBean> confi = baseBeanService.getListBeanByHqlAndParams("from WageCofi ci where ci.wageState between ? and ? and ci.wageCofState = ? and ci.companyId = ? and ci.jicha = ? order by ci.wageCofSortSn"
				,parms.toArray());
		parms = new ArrayList<Object>();
		WageCofi fi = null;
		nums = confi.size();
		String sql = "select r.cof_ra_i_d, r.cof_sort_sn, r.cof_ra_date, r.ra_num, r.cr_name";
		if(confi != null){
			for(int i = 0 ; i <confi.size() ; i++){
				fi = new WageCofi();
				fi = (WageCofi)confi.get(i);
				sql += " ,max(case when l.type_p_i_d = ? then l.type_money  end)";
				parms.add(fi.getWageCofId());
			}
		}

		sql += ",r.sum_money,r.CONF_RA_STATE   from dt_human_wage_cof_ra r left join dt_human_wage_cof_cl l" +
			" on l.cof_ra_i_d = r.cof_ra_i_d " +
			" where r.company_i_d = ? ";
		if(wcofra.getCjUdate()!=null&&!"".equals(wcofra.getCjUdate()))
		{
			sql+="and BETWEEN";
			
			
		}
		
		parms.add(this.getCurrentAccount().getCompanyID());
		sql += " group by r.cof_ra_i_d, r.cof_sort_sn, r.cof_ra_date, r.ra_num, r.cr_name, r.sum_money,r.CONF_RA_STATE order by CONF_RA_STATE";
		results.add(sql);
		results.add(parms.toArray());
		ActionContext.getContext().put("confi", confi);
		return results;
	}
	@SuppressWarnings("deprecation")
	public String toEdit(){
		wcofra = (WageCofra)baseBeanService.getBeanByHqlAndParams("from WageCofra r where r.cofRaID = ?", new Object[]{wcofra.getCofRaID()});
		 List<BaseBean> beans = baseBeanService.getListBeanByHqlAndParams("from WageCofcl l where l.cofRaID = ?", new Object[]{wcofra.getCofRaID()});
		if(wcofra != null){
			wcofra.setYyyyCofDate(wcofra.getCofRaDate().getYear()+1900+"");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("wcr",wcofra);
		map.put("wcl",beans);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		ActionContext.getContext().put("beans", beans);
		return "success";
	}
	
	public String saveItem() {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = null;
		if (null == wcofra.getCofRaID() || "".equals(wcofra.getCofRaID())){
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
				java.util.Date date=sdf.parse(wcofra.getYyyyCofDate()+"-1-1");  
				wcofra.setCofRaDate(date);
			} catch (ParseException e) {
				logger.error("操作异常", e);
			}
			if(null != wcofra.getSumMoneyT() && !"".equals(wcofra.getSumMoneyT().trim())){
				//构造以字符串内容为值的BigDecimal类型的变量bd   
				BigDecimal bd=new BigDecimal(wcofra.getSumMoneyT().trim());   
				//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
				bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				wcofra.setSumMoney(bd);
			}
			wcofra.setCofSortSn(Integer.parseInt(wcofra.getCofSortSnT().trim()));
			wcofra.setConfRaState(Long.parseLong("0"));
			wcofra.setCofRaID(serverService.getServerID("wagcofra"));
			wcofra.setCompanyID(this.getCurrentAccount().getCompanyID());
			wcofra.setGroupCompanySn(this.getGroupCompanySn());
			wcofra.setCjAdate(new Date());
			wcofra.setCjAname(this.getCurrentAccount().getAccountEmail());
			parameter="添加级差工资设定";
		}else{
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
				java.util.Date date=sdf.parse(wcofra.getYyyyCofDate()+"-1-1");  
				wcofra.setCofRaDate(date);
			} catch (ParseException e) {
				logger.error("操作异常", e);
			}
			if(null != wcofra.getSumMoneyT() && !"".equals(wcofra.getSumMoneyT().trim())){
				//构造以字符串内容为值的BigDecimal类型的变量bd   
				BigDecimal bd=new BigDecimal(wcofra.getSumMoneyT().trim());   
				//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
				bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				wcofra.setSumMoney(bd);
			}
			wcofra.setCofSortSn(Integer.parseInt(wcofra.getCofSortSnT().trim()));
			wcofra.setConfRaState(Long.parseLong("0"));
			hql = " delete from WageCofcl c where c.cofRaID = ?";
			parameter="修改级差工资设定";
		}
		beans.add(wcofra);
		if(wcofclmap != null){
			for(WageCofcl wcof : wcofclmap.values()){
				wcof.setCofClID(serverService.getServerID("wagcofcl"));
				wcof.setCofRaID(wcofra.getCofRaID());
				if(null != wcof.getTypeMoneyT() && !"".equals(wcof.getTypeMoneyT().trim())){
					//构造以字符串内容为值的BigDecimal类型的变量bd   
					BigDecimal bd=new BigDecimal(wcof.getTypeMoneyT().trim());   
					//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
					bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					wcof.setTypeMoney(bd);
				}
				beans.add(wcof);
			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, this.getCurrentAccount());
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{wcofra.getCofRaID()});
		}
		ActionContext.getContext().put("beans", beans);
		return findItem();
	}
	
	public String delItem(){
		List<BaseBean>	beans = new ArrayList<BaseBean>();
		String hql = "delete from WageCofra c where c.cofRaID = ?";
		String hql1 = "delete from WageCofcl l where l.cofRaID = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "删除级差工资", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql,hql1}, new Object[]{wcofra.getCofRaID()});
		return findItem();
	}
	
	public String upItem(){
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "update WageCofra c set c.confRaState = ? where c.cofRaID = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "停用工资基础", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{ Long.parseLong("1"),wcofra.getCofRaID()});
		return findItem();
	}
	

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public WageCofra getWcofra() {
		return wcofra;
	}

	public void setWcofra(WageCofra wcofra) {
		this.wcofra = wcofra;
	}



	public Map<String, WageCofcl> getWcofclmap() {
		return wcofclmap;
	}

	public void setWcofclmap(Map<String, WageCofcl> wcofclmap) {
		this.wcofclmap = wcofclmap;
	}



	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	



}
