package com.tiantai.wfj.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.TEshopExtinfo;
import com.tiantai.wfj.util.SessionWrap;

@Controller
@Scope("prototype")
public class WfjEDistributionAction<AccountName> {

	@Resource
	private BaseBeanService baseBeanService;
	
	private List<BaseBean> beans;
	private List<BaseBean> dailibeans;
	private TEshopCustomer customer;
	private TEshopCusCom cusCom;
	private Staff staff;
	private Company company;
	private TEshopExtinfo shop;
	
	private String result;
	
	public String getwfjHome(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,SessionWrap.KEY_CUSTOMER);
		if(scc==null || scc.getOrganizationID()==null){
			return "login";
		}
		if (cus == null) {
			return "login";
		}
		String sql = "select cusType from T_ESHOP_CUSCOM where staffid=?";
		String cusType = (String) baseBeanService.getObjectBySqlAndParams(sql, new Object[]{cus.getStaffid()});
		if(cusType!=null&&"01".equals(cusType)){
			String hql = " from TEshopCusCom where staffid=?";
			cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cus.getStaffid()} );
			//登录类型是公司
			String comhql = " from Company where companyID = ? ";
			company=(Company) baseBeanService.getBeanByHqlAndParams(comhql, new Object[]{cusCom.getCompanyId()});
			if(company!=null&&"02".equals(company.getComType())){
				//公司
				return "company";
			}else if(company!=null&&"03".equals(company.getComType())){
				//高级合伙人
				return "highPartner";
			}else if(company!=null&&"05".equals(company.getComType())){
				//合伙人 
				return "partner";
			}
		}else if(cusType!=null&&"02".equals(cusType)){
			//登录类型是店铺
			String shophql = " from TEshopExtinfo where organizationID = ? ";
			shop = (TEshopExtinfo)baseBeanService.getBeanByHqlAndParams(shophql, new Object[]{scc.getOrganizationID()});
			if(shop!=null&&"01".equals(shop.getShopType())){
				//01 店铺  
				return "shop";
			}else if(shop!=null&&"02".equals(shop.getShopType())){
				//02 产品代理商VIP会员
				return "agencyVIP";
			}else if(shop!=null&&"03".equals(shop.getShopType())){
				//03  产品代理零售商会员
				return "agencyRetail";
			}
		}else{
			return "consumer";
		}
		return "login";
	}
	public String getwfjConsumer(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}
		return "consumer";
	}
	
	public String getwfjgoodsAgency(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}	
		return "goodsAgency";
	}
	public String getwfjIntegral(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}	
		return "integral";
	}
	
	public String getwfjAgencyRetailDetail(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}	
		return "agencyRetailDetail";
	}
	
	public String getwfjAgencyVIPDetail(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}	
		return "agencyVIPDetail";
	}
	
	public String getwfjConsumerDetail(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}	
		return "consumerDetail";
	}
	
	public String getwfjCommission(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}	
		return "commission";
	}
	
	/**
	 * ty
	 * 个人产品查询
	 * 根据产品添加时候绑定的人的ID 查询他自己 然后 在根据产品的ID查询出钱的信息
	 * 然后 跟产品的ID查询出属于那个公司的信息
	 * 然后 根据产品表里面的物品ID 查询出这个物品的数量以及所剩数量
	 *sql 
	 */
	@SuppressWarnings("unchecked")
	public String  getlist()
	{
 		HttpServletRequest request=ServletActionContext.getRequest();		
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		TEshopCusCom scc= (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);		
		String httppase=request.getParameter("httppase");
		//01 0 2  03  //01公司  02 店主 03  游客
		if(cus==null){
			return "login";
		}
		String customerId = cus.getStaffid();
		/*产品管理*/
		String sql="select  pp.ppID, pp.goodsid ,pp.goodsname as ,pp.shangjiastatus ,pp.quantity ,pc.money ,pp.image " +
				"from  dt_ProductPackaging  pp, dt_ProductPriceCategory  pc ,PROFITSHARE ps,dtGoodsManage gm " +
				"where pp.ppid=pc.ppid and pp.ppid=ps.ppid and pp.showweixin='01'  and pp.goodsid=gm.goodsid ";
		String hql1="from TEshopCustomer where  staffid = ? ";
		customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{customerId});
		String hql2="from Staff where staffID=?";
		staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{customerId}); 	
		if( scc!=null&&scc.getCusType()!=null&&"".equals(scc.getCusType()))
		{			
		}
		else if(scc!=null&&"01".equals(scc.getCusType()))
		{
			sql+="and  pp.companyid=? ORDER by pp.shangjiastatus DESC ";		    
			beans= baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{scc.getCompanyId()});	
		}
		else if(scc!=null&&"02".equals(scc.getCusType()))
		{
			sql+="and   pp.organizationid=? ORDER by pp.shangjiastatus DESC ";
			beans= baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{scc.getOrganizationID()});		
		}
		/*代理产品管理*/
		String sql1="select dp.ppkey,dp.ppid ,dp.goodsname ,dc.money , ps.agent   ," +
			"dco.companyname , dp.shangjiastatus , dp.quantity ,dp.image " +
			"from dt_ProductPackaging dp ,dt_ProductPriceCategory dc ,PROFITSHARE ps,T_ESHOP_Daicp  dai,dtCompany  dco " +
			"where  dai.cpppID=dp.ppid and dp.showweixin='01'   and ps.ppid=dp.ppid and   dai.cpcomdid=dco.companyid and dp.ppid=dc.ppid " +
			//and dp.showweixin='02
			" and dai.cpuserid=? and dai.cpusercomdid=?";
			dailibeans = new ArrayList<BaseBean>();
			dailibeans= baseBeanService.getListBeanBySqlAndParams(sql1,new Object[]{scc.getStaffid(),scc.getCompanyId()});	
		return "product";
		
	}
	
	//新首页获取驾校
	public String refre(){
		/*CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}*/
		List<Object> params = new ArrayList<Object>();
		String hql = " from Company where isSt='0' and showwechat='00' ";
		if(result!="" && result!=null){
			hql += "and companyName like ? ";
			params.add("%" + result + "%");
		}
		List<BaseBean> list =  baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		Map<String, Object> map1 = new HashMap<String, Object>();
		for(BaseBean com:list){
		company = (Company)com;
			if(company.getCompanyID()!=null) {	
				hql += "and companyID=?";
				params.add(company.getCompanyID());
				company = (Company) baseBeanService.getObjectByHqlAndParams(hql, params.toArray());
				map1.put("companyID",map1.get("companyID")==null?(company.getCompanyID()==null?"":company.getCompanyID()):map1.get("companyID")+","+(company.getCompanyID()==null?"":company.getCompanyID()));
				map1.put("companyName",map1.get("companyName")==null?(company.getCompanyName()==null?"":company.getCompanyName()):map1.get("companyName")+","+(company.getCompanyName()==null?"":company.getCompanyName()));
				logger.info("调试信息");
				hql = " from Company where isSt='0' and showwechat='00' ";
				params.clear();
				if(result!="" && result!=null){
					hql += "and companyName like ? ";
					params.add("%" + result + "%");
				}
			}
		}
		JSONObject oj = JSONObject.fromObject(map1);
		result = oj.toString();
		return "success";
	}

	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public TEshopCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public List<BaseBean> getDailibeans() {
		return dailibeans;
	}

	public void setDailibeans(List<BaseBean> dailibeans) {
		this.dailibeans = dailibeans;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public TEshopExtinfo getShop() {
		return shop;
	}

	public void setShop(TEshopExtinfo shop) {
		this.shop = shop;
	}

	public TEshopCusCom getCusCom() {
		return cusCom;
	}

	public void setCusCom(TEshopCusCom cusCom) {
		this.cusCom = cusCom;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	
}
