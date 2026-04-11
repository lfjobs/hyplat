package com.tiantai.wfj.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.Budget;
import com.tiantai.wfj.bo.Cart;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.BudgetionService;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;

/**
 * 项目预算 
 * 
 */

@Service
public class BudgetionServiceImpl implements BudgetionService {
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private ServerService idgec;
	
	/**
	 * 查询所有项目预算
	 * @param companyId 公司Id
	 * @param pageNumber 当前页
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public PageForm budgetPageForm(String companyId,Integer pageNumber){
		PageForm pageForm = new PageForm();
		StringBuilder sql=new StringBuilder();
		sql.append("select cb.cashierbillsid,cb.projectname,cb.priceSub from dtcashierbills cb ");
		sql.append(" where cb.companyid = ? and cb.proid=? order by cb.cashierdate desc");
		Object [] params=new Object[]{companyId,"005"};
		String sqlcount="select count(*) from ("+sql.toString()+")";
		int count = beandao.getConutByBySqlAndParams(sqlcount,params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / 10 + ((count % 10) == 0 ? 0 : 1);
		if (pageNumber < 1)   
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = 10 * (pageNumber - 1);
		int maxResult = Math.min(10, count - firstResult);
		List<BaseBean> listBaseBean = beandao
				.getConutByBySqlAndParamsAndPage(sql.toString(), params, firstResult,
						maxResult);
		pageForm.setPageSize(10);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	/**
	 * 保存
	 * @param companyId 公司Id
	 * @param projectName 项目名称  
	 * @Description user 用户名
	 * @return
	 */
	@Override @Transactional
	public boolean saveBudget(String companyId,String projectName,String content){
		boolean flag;
		try {
			flag = true;
			List<BaseBean> beans = new ArrayList<BaseBean>();
			CashierBills cb = new CashierBills();
			cb.setCashierBillsID(idgec.getServerID("CashierBills"));
			cb.setCompanyID(companyId);			
			cb.setProjectName(projectName);			
			cb.setCashierDate(new Date());
			cb.setProID("005");
			
			String [] temp=content.split(",");
			String [] ppnames=temp[0].split("#");
			String [] prices=temp[1].split("\\u002A");
			Float total=(float) 0.00;
			for(String s:prices){
				total+=Float.valueOf(s);
			}
			total=(float)(Math.round(total*100))/100;//保留2位数
			cb.setPriceSub(total.toString());
			beans.add(cb);
			GoodsBills gb=null;
			for(int i=0;i<ppnames.length;i++){
				gb=new GoodsBills();
				gb.setGoodsBillsID(idgec.getServerID("GoodsBills"));
				gb.setCashierBillsID(cb.getCashierBillsID());
				gb.setGoodsName(ppnames[i]);
				gb.setPrice(prices[i]);
				beans.add(gb);
			}
			beandao.executeSqlsByParmsList(beans, null, null);
		} catch (Exception e) {
			logger.info("值：{}", e);
			flag=false;
		}
		return flag;
	}
	/**
	 * 跳转
	 * @param user 用户账号
	 * @param 
	 * @return
	 */
	@Override @Transactional
	public String toBudget(String user){		
		String str;
		try {
			str = "";
			TEshopCusCom tec = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",
					new Object[] { user });
			if(tec!=null){
				str=tec.getCompanyId();
			}			
		} catch (Exception e) {			
				str="";
		}
		return str;
	}
	/**
	 * 删除
	 * @param cashierBillsID 
	 * @param 
	 * @return
	 */
	@Override @Transactional
	public boolean delBudget(String cashierBillsID){
		boolean flag;
		List<Object[]> params = new ArrayList<Object[]>();		
		List<String> sqls=new ArrayList<String>();		
		try {		
			flag = true;
			String sql = "delete from dtCashierBills where cashierBillsID=?";
			String sql_g="delete from dtGoodsBills where cashierBillsID=?";
			sqls.add(sql);
			sqls.add(sql_g);
			params.add(new Object[] { cashierBillsID });
			params.add(new Object[]{cashierBillsID});
			List<BaseBean> budgetlist=beandao.getListBeanBySqlAndParams("select goodsBillsID from dtGoodsBills where cashierBillsID=?", new Object[]{cashierBillsID});
			
			for(int i=0;i<budgetlist.size();i++){
				Object obj=budgetlist.get(i);				
				String sql_b="delete from DT_Budget where goodsBillsID=?";
				sqls.add(sql_b);
				params.add(new Object[]{obj});
			}
			beandao.executeSqlsByParmsList(null, sqls.toArray(new String[]{}), params);
		} catch (Exception e) {
			logger.info("值：{}", e);
			flag=false;
		}
		return flag;
	}
	/**
	 * 项目预算详情
	 * @param cashierBillsID 预算项目Id
	 * @param 
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override 
	public List<BaseBean> budgetDetail(String cashierBillsID){		
		List<BaseBean> list=new ArrayList<BaseBean>();
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("with a as(select cb.cashierbillsid,cb.projectname,gb.goodsbillsid,gb.goodsname,gb.price,cb.PriceSub from dtcashierbills cb, dtgoodsbills gb");
			sql.append(" where cb.cashierbillsid = gb.cashierbillsid");
			sql.append(" and cb.cashierbillsid = ? and cb.proid= ? order by cb.cashierdate desc)");
			sql.append(" select a.* ,b.goodsBillsID as gbId,b.price as re_price,b.ppname,b.ppid from a left join dt_budget b on a.goodsbillsid=b.GOODSBILLSID");
			list=beandao.getListBeanBySqlAndParams(sql.toString(), new Object[]{cashierBillsID,"005"});
		} catch (Exception e) {
			list=null;
		}
		return list;
	}
	/**
	 * 根据产品名称搜索
	 * @param ppname 产品名称 
	 * @param pageNumber 当前页
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public PageForm searchPro(String ppname,int pageNumber){
		StringBuilder sql=new StringBuilder();
		sql.append("select ps.ppname,ps.ppid,ps.re_price,p.brand,p.image,p.goodsid,p.companyid,p.stockSize,p.monthSales,cc.companyAddr,ccom.ccompany_Id from");
		sql.append(" DT_PRO_SETUP ps,dt_ProductPackaging p,DT_ccom_com ccom,dtContactCompany cc where");
		sql.append(" p.ppid=ps.ppid and p.showweixin = ? and p.companyid=ccom.compnay_id and ccom.ccompany_Id=cc.ccompanyID");
		sql.append(" and p.type!= ? and p.type!= ? and ps.ppname like ?");
		Object [] params=new Object[]{"01","公司会员","个人会员","%"+ppname+"%"};
		String sqlcount="select count(*) from ("+sql.toString()+")";
		int count = beandao.getConutByBySqlAndParams(sqlcount,params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / 10 + ((count % 10) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = 10 * (pageNumber - 1);
		int maxResult = Math.min(10, count - firstResult);
		List<BaseBean> listBaseBean = beandao
				.getConutByBySqlAndParamsAndPage(sql.toString(), params, firstResult,
						maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(10);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}
	/**
	 * 选择产品
	 * @param goodsBillsID 物品单据管理id 
	 * @param ppId 产品id
	 * @return
	 */
	@Transactional
	@Override
	public boolean chancePro(String goodsBillsID,String ppId){
		boolean flag;
		try {
			flag = true;
			List<BaseBean> beans = new ArrayList<BaseBean>();
			List<Object> parms=new ArrayList<Object>();
			String[] temp = ppId.split(",");
			StringBuilder sql = new StringBuilder();
			sql.append("select e.com_Id, e.ppid, e.ppname, e.re_Price, pp.photo as photoPath");
	        sql.append(" from dt_Pro_Setup e,dt_productpackaging pp");
	        sql.append(" where e.ppid = pp.ppID");
	        sql.append(" and e.ppid in ( ");
	        StringBuilder s=new StringBuilder();
	        for(int i=0;i<temp.length;i++){
	        	if(i==temp.length-1){
	        		s.append("?");
	        	}else{
	        		s.append("?,");
	        	}
	        	parms.add(temp[i]);
	        }
	        sql.append(s.toString()+" )");
			List<BaseBean> prolist = beandao.getListBeanBySqlAndParams(sql.toString(),
					parms.toArray(new Object[]{}));
			Budget budget = null;
			if (!prolist.isEmpty()) {
				for (int i = 0; i < prolist.size(); i++) {
					Object o = prolist.get(i);
					Object [] obj=(Object[]) o;
					budget = new Budget();
					budget.setBudgetId(idgec.getServerID("Budget"));
					budget.setGoodsBillsID(goodsBillsID);
					budget.setPpId(obj[1].toString());
					budget.setPpname(obj[2].toString());
					budget.setPrice(obj[3].toString());
					beans.add(budget);
				}
			}
			beandao.executeSqlsByParmsList(beans, null, null);
		} catch (Exception e) {
			logger.info("值：{}", e);
			flag=false;
		}
		return flag;
	}
	/**
	 * 删除已选择产品
	 * @param goodsBillsID 物品单据管理id 
	 * @param ppId 产品id
	 * @return
	 */
	@Transactional
	@Override
	public boolean delChancePro(String goodsBillsID,String ppId){
		boolean flag;
		try {
			flag = true;
			StringBuilder hql = new StringBuilder();
			List<Object[]> parms = new ArrayList<Object[]>();
			hql.append("delete Budget where goodsBillsID=? and ppId=?");
			Object[] obj = new Object[] { goodsBillsID, ppId };
			parms.add(obj);
			beandao.executeHqlsByParmsList(null, new String[] { hql.toString() }, parms);			
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}
	/**
	 * 一键加入购物车，多个产品
	 * @param ppid 产品id 
	 * @param user 登录账号
	 * @return itemNum 数量
	 */
	@Override @Transactional
	public boolean oneKeyJoinCart(String ppid,String user ){
		boolean flag;
		List<BaseBean> beans=new ArrayList<BaseBean>();
		List<Object> parms=new ArrayList<Object>();
		List<String> sqls=new ArrayList<String>();
		List<Object[]> pa = new ArrayList<Object[]>();
		try {
			flag = true;			
			String[] ppids = ppid.split(",");//多个产品id
			TEshopCusCom tec = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",
					new Object[] { user });
			StringBuilder s=new StringBuilder(); 
			if (tec != null) {
				if (tec.getStaffid() != null && tec.getStaffid().length() > 0) {
					if (ppids.length > 0) {
						StringBuilder sql = new StringBuilder();
						sql.append("select e.com_Id,e.ppid,e.ppname,e.re_Price,pp.photo as photoPath");
						sql.append(" from dt_Pro_Setup e,dt_ProductPackaging pp ");
						sql.append(" where e.ppid=pp.ppID and e.ppid in ( ");
						
						 for(int i=0;i<ppids.length;i++){
					        	if(i==ppids.length-1){
					        		s.append("?");
					        	}else{
					        		s.append("?,");
					        	}
					        	parms.add(ppids[i]);
					        }
					    sql.append(s.toString()+" )");
						List<BaseBean> pplist=beandao.getListBeanBySqlAndParams(sql.toString(), parms.toArray(new Object[]{}));
						sql.delete(0, sql.length());
						sql.append("select c.pid from DT_cart c where staff_Id=? and pid in ( ");
						sql.append(s.toString()+" )");
						parms.add(0, tec.getStaffid());
						List<BaseBean> cartlist=beandao.getListBeanBySqlAndParams(sql.toString(), parms.toArray(new Object[]{}));
					
						Cart cat;
						if(cartlist==null||cartlist.size()==0){							
							for(int i=0;i<pplist.size();i++){
								Object o=pplist.get(i);
								Object [] obj=(Object[])o;
								cat=new Cart();
								cat.setCartId(idgec.getServerID("cart"));
								cat.setCompanyId(obj[0].toString());
								cat.setItemNum(ElementNum(ppids,obj[1].toString()));
								cat.setPid(obj[1].toString());
								cat.setPname(obj[2].toString());
								cat.setPrice(obj[3].toString());
								cat.setStaffId(tec.getStaffid());
								cat.setPic(obj[4]==null?null:obj[4].toString());
								cat.setStardard(null);
								beans.add(cat);
							}
						}else{	
							for(int j=0;j<pplist.size();j++){
								Object o=pplist.get(j);
								Object [] p=(Object[])o;																							
									if(cartlist.contains(p[1].toString())){//查询购物车是否有该产品，然后查数量更新,没有重新生成
										pa.add(new Object[] { tec.getStaffid(), p[1].toString() });										
										String upsql="update Cart u set u.itemNum=u.itemNum+"+ElementNum(ppids,p[1].toString())+" where u.staffId=? and u.pid=?" ;
										sqls.add(upsql);
									}else{ 
										cat=new Cart();
										cat.setCartId(idgec.getServerID("cart"));
										cat.setCompanyId(p[0].toString());
										cat.setItemNum(ElementNum(ppids,p[1].toString()));
										cat.setPid(p[1].toString());
										cat.setPname(p[2].toString());
										cat.setPrice(p[3].toString());
										cat.setStaffId(tec.getStaffid());
										cat.setPic(p[4]==null?null:p[4].toString());
										cat.setStardard(null);
										beans.add(cat);
									}
							}
						}					
					}
				}
			} 
			beandao.executeHqlsByParmsList(beans, sqls.toArray(new String[]{}), pa);
		} catch (Exception e) {
			logger.info("值：{}", e);
			flag=false;
		}
		return flag;
	}
	/**
	 *判读数组中指定元素的个数
	 *@return
	 */
	public Integer ElementNum(String [] arr,String element){
		Integer t=0;
		for(int i=0;i<arr.length;i++){
			if(element.equals(arr[i])){
				t++;
			}
		}
		return t;
	}
}
