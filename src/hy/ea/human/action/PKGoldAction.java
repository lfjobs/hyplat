package hy.ea.human.action;

import hy.ea.bo.CAccount;
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
 * pkgold
 * @author Administrator
 *
 */
public class PKGoldAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	private List<BaseBean> resumeList;
	private PKGold pkgold;
	private String search;
	private PageForm pageForm;
	private int pageNumber;
	private List<BaseBean> beans;
	private String result;
	
	/**
	 * 定时任务 
	 */
	@SuppressWarnings("deprecation")
	public void saveAllPKGold(){
		String sql = "select distinct(s.staffID),s.staffname,o.organizationid,o.organizationname," +
				" d.deppostid,d.postname,p.pkpay,com.companyid,com.companyname" +
				" from dtcos c left join dt_hr_staff s on c.staffid = s.staffid" +
				" left join dtaudition a on c.staffid = a.staffid" +
				" left join dt_hr_deptpost d on c.depPostID = d.depPostID" +
				" left join dtcorganization o on c.organizationID = o.organizationID " +
				" left join dtCsp csp on c.staffid = csp.staffid" +
				" left join dtPayScale p on  csp.payscaleid = p.payscaleid  " +
				" left join dtcompany com on c.companyid = com.companyid" +
				" where c.cosStatus = ?" +
				" and c.status = ?" +
				" and a.status = ?" +
				" and p.pkpay is not null";
		@SuppressWarnings("rawtypes")
		List list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"50","01","22"});
		if(list.size()>0){
			beans = new ArrayList<BaseBean>();
			for(int i = 0;i<list.size();i++){
				Object[] o = (Object[])list.get(i);
				pkgold = new PKGold();
				pkgold.setPkgoldID(serverService.getServerID("pkgold"));
				pkgold.setCompanyID(o[7].toString());
				pkgold.setCompanyName(o[8].toString());
				pkgold.setStaffID(o[0].toString());
				pkgold.setStaffName(o[1].toString());
				if(o[2] != null)
					pkgold.setOrganizationID(o[2].toString());
				if(o[3] != null)
					pkgold.setOrganizationName(o[3].toString());
				if(o[4] != null)
					pkgold.setDepID(o[4].toString());
				if(o[5] != null)
					pkgold.setDepName(o[5].toString());
				if(o[6] != null)
					pkgold.setGold(o[6].toString());
				pkgold.setStatus("00");
				String dat = new Date().toLocaleString().split(" ")[0];
				pkgold.setPkDate(dat.split("-")[0]+"-"+dat.split("-")[1]);
				beans.add(pkgold);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
	}
	/**
	 * 定时 save 公司汇总
	 */
	@SuppressWarnings("deprecation")
	public void saveAllPKGoldPool(){
		
		String sql = "select sum(hp.gold),hp.companyid,hp.companyname from dt_hr_pkgold hp where hp.status = ? and hp.pkDate like ? group by hp.companyid,hp.companyname ";
		
		String d1 = new Date().toLocaleString().split(" ")[0];
		d1 = "%"+d1.split("-")[0]+"-"+d1.split("-")[1]+"%";
		List list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"00",d1});
		
		
		if(list.size()>0){
			beans = new ArrayList<BaseBean>();
			for(int i = 0;i<list.size();i++){
			Object[] o = (Object[])list.get(i);
			PKGoldPool goldpool = new PKGoldPool();
			goldpool.setPkgoldpoolID(serverService.getServerID("pkgoldpool"));
			goldpool.setGoldpool(o[0].toString());
			goldpool.setCompanyID(o[1].toString());
			goldpool.setCompanyName(o[2].toString());
			String dat = new Date().toLocaleString().split(" ")[0];
			goldpool.setPkDate(dat.split("-")[0]+"-"+dat.split("-")[1]);
			
			String hql = "from PKGoldPool p where p.companyID = ? and p.pkDate like ?";
			String d2 = new Date().toLocaleString().split(" ")[0]; //2012-1-1
			
			if(d2.split("-")[1] == "1"){
				d2 =(Integer.parseInt(d2.split("-")[0])-1) + "-12";
			}else{
				d2 = d2.split("-")[0] + "-" + (Integer.parseInt(d2.split("-")[1]) - 1);
			}
			
			
			PKGoldPool pool = (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{o[1].toString(),"%"+d2+"%"});
			if(pool == null){
				goldpool.setGoldbalpool(o[0].toString());
			}else{
				goldpool.setGoldbalpool(Integer.parseInt(pool.getGoldbalpool())+Integer.parseInt(o[0].toString())+"");
			}
			goldpool.setGoldpaypool("0");
			
			
			beans.add(goldpool);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
	}
	
	public String toSearch(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", pkgold);
		
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
		
		DetachedCriteria dc=DetachedCriteria.forClass(PKGold.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			pkgold = (PKGold) session.get("tablesearch");
			if(pkgold != null){
				if(pkgold.getStaffName() !=null && !"".equals(pkgold.getStaffName().trim())){
					dc.add(Restrictions.like("staffName", pkgold.getStaffName(), MatchMode.ANYWHERE));
				}
				if(pkgold.getPkDate() != null && !"".equals(pkgold.getPkDate())){
						String d = pkgold.getPkDate().split("-")[0]+"-"+Integer.parseInt(pkgold.getPkDate().split("-")[1]);
						dc.add(Restrictions.like("pkDate", d, MatchMode.ANYWHERE));
				}
				if(pkgold.getStatus() != null && !"".equals(pkgold.getStatus())){
					dc.add(Restrictions.eq("status", pkgold.getStatus()));
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

	public PKGold getPkgold() {
		return pkgold;
	}

	public void setPkgold(PKGold pkgold) {
		this.pkgold = pkgold;
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

}
