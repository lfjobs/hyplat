package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.wage.PKGold;
import hy.ea.bo.human.wage.PKGoldPool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

/**
 * pkgoldpool
 * @author Administrator
 *
 */
public class PKGoldPoolAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	private List<BaseBean> resumeList;
	private PKGoldPool pkgoldpool;
	private PKGold pkgold;
	private String search;
	private PageForm pageForm;
	private int pageNumber;
	private List<BaseBean> beans;
	private String result;
	
	
	
	@SuppressWarnings("deprecation")
	public String toGoldpaypool(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String d = new Date().toLocaleString().split(" ")[0];
		String hql = "from PKGoldPool p where p.companyID = ? and pkDate like ?";
		PKGoldPool pnew= (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"%"+d.split("-")[0]+"-"+d.split("-")[1]+"%"});
		if(pnew == null){
			if(d.split("-")[1] == "1"){
				d =(Integer.parseInt(d.split("-")[0])-1) + "-12";
			}else{
				d = d.split("-")[0] + "-" + (Integer.parseInt(d.split("-")[1]) - 1);
			}
			PKGoldPool pold = (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"%"+d+"%"});
			PKGoldPool pool = new PKGoldPool();
			pool.setCompanyID(account.getCompanyID());
			pool.setCompanyName(account.getCompanyName());
			pool.setPkgoldpoolID(serverService.getServerID("pkgoldpool"));
			pool.setPkDate(d.split("-")[0]+"-"+d.split("-")[1]);
//			总额
			pool.setGoldpool("0");
//			支出
			pool.setGoldpaypool("0");
//			余额
			pool.setGoldbalpool(pold.getGoldbalpool());
			beans = new ArrayList<BaseBean>();
			beans.add(pool);
			baseBeanService.executeHqlsByParamsList(beans, null, null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pool", pool);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pool", pnew);
				
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
	}
	
	@SuppressWarnings("deprecation")
	public String savePKGold(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = "from COrganization o where o.organizationID = ?";
		COrganization org = (COrganization)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{organizationID});		String d = new Date().toLocaleString().split(" ")[0];
		//单条
		pkgold.setPkgoldID(serverService.getServerID("pkgold"));
		String d1 = pkgold.getPkDate();
		d1 = Integer.parseInt(d1.split("-")[0])+"-"+Integer.parseInt(d1.split("-")[1])+"-"+Integer.parseInt(d1.split("-")[2]);
		pkgold.setPkDate(d1);
		pkgold.setStaffID(account.getStaffID());
		pkgold.setStaffName(account.getAccountEmail());
		pkgold.setOrganizationID(organizationID);
		pkgold.setOrganizationName(org.getOrganizationName());
		beans = new ArrayList<BaseBean>();
		beans.add(pkgold);
		//汇总
		String hql1 = "from PKGoldPool p where p.companyID = ? and p.pkDate like ?";
		PKGoldPool pool = (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID(),"%"+d.split("-")[0]+"-"+d.split("-")[1]+"%"});
		//支出汇总
		pool.setGoldpaypool(Integer.parseInt(pool.getGoldpaypool())+Integer.parseInt(pkgold.getGold())+"");
		//余额
		pool.setGoldbalpool(Integer.parseInt(pool.getGoldbalpool())-Integer.parseInt(pkgold.getGold())+"");
		beans.add(pool);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}
	
	public String toSearch(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", pkgoldpool);
		
		return getList();
	}
	
	
	public String getList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0?15:pageNumber), getlists(session,account));
		
		
		return "list";
		
	}

	private DetachedCriteria getlists(Map<String,Object> session ,CAccount account){
		
		DetachedCriteria dc=DetachedCriteria.forClass(PKGoldPool.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			pkgoldpool = (PKGoldPool) session.get("tablesearch");
			if(pkgoldpool != null){
				if(pkgoldpool.getPkDate() != null && !"".equals(pkgoldpool.getPkDate())){
				String d = pkgoldpool.getPkDate().split("-")[0]+"-"+Integer.parseInt(pkgoldpool.getPkDate().split("-")[1]);
				dc.add(Restrictions.like("pkDate", d, MatchMode.ANYWHERE));
				}
			}
		}
		
		return dc;
	}
	
	
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public PKGoldPool getPkgoldpool() {
		return pkgoldpool;
	}

	public void setPkgoldpool(PKGoldPool pkgoldpool) {
		this.pkgoldpool = pkgoldpool;
	}

	public List<BaseBean> getResumeList() {
		return resumeList;
	}

	public void setResumeList(List<BaseBean> resumeList) {
		this.resumeList = resumeList;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public PKGold getPkgold() {
		return pkgold;
	}

	public void setPkgold(PKGold pkgold) {
		this.pkgold = pkgold;
	}

}
