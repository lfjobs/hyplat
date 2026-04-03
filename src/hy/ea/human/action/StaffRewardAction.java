package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Honor;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffReward;
import hy.ea.bo.human.publicreceipts.Publicreceipts;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.bean.ExcelImport;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 奖励模块
 * @author lwz
 *
 */
@Controller
@Scope("prototype")
public class StaffRewardAction {
	@Resource
	private CompanyService companyService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private StaffReward reward;
	private PageForm pageForm;
	private int pageNumber;
	private String parameter;
	private List<BaseBean> beans;
	private String search; 
	private String result;
	private List<Company> comList;
	private String comID; 
	private String orgID;
	private String sID;
	private String staffName; //查询责任人search
	private Publicreceipts pub; //人事公共表
	private String aud; //保存、审核状态
	private String ott;	//跳转页面
	private String rewardids; 
	private List<BaseBean> honorList;
	private List<BaseBean> beansl;
	private List<BaseBean> beanslist;
	private int num ;//年度荣誉数量.
	private int maxnum;
	public String toSearch(){
		
		ActionContext.getContext().getSession().put("tablesearch",
				reward);
		
		return getStaffReward();
	}
	
	public String getStaffReward(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = companyService.getCompanyAndItsChildren(account.getCompanyID());
		comList = new ArrayList<Company>();
		for(int i = 0; i<beans.size();i++){
			comList.add((Company)beans.get(i));
		}
		List<Object> list = getlist(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), hql, params);
		
		return "list";
	}
	/**
	 * 获取 / results
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getlist(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String hql = " from StaffReward w where w.companyid = ?";
		parms.add(account.getCompanyID());
		if(ott.equals("01")){
			hql += " and w.status = ?";
			parms.add("20");
		}else if(ott.equals("02")){
			hql += " and w.status = ?";
			parms.add("30");
		}
		if(search != null && search.equals("search")) {
			reward = (StaffReward) session.get("tablesearch");
			if (reward.getCompanyid() != null
					&& !"".equals(reward.getCompanyid())) {
				hql += " and w.companyid = ?";
				parms.add(reward.getCompanyid());
			}
			if (reward.getStaffname() != null
					&& !"".equals(reward.getStaffname())) {
				hql += " and w.staffname like ?";
				parms.add("%" + reward.getStaffname() + "%");
			}
		}
		
		hql += " order by onetimes";
		results.add(hql);
		results.add(parms.toArray());
		return  results;
	}
	
	/**
	 * 保存草稿
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String save(){
	
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff s where s.staffID = ? ", new Object[]{account.getStaffID()});
		beans = new ArrayList<BaseBean>();
		if(null==reward.getRewardid()||"".equals(reward.getRewardid())){
			reward.setRewardid(serverService.getServerID("reward"));
			reward.setOnestaffid(staff.getStaffID());
			reward.setOnename(staff.getStaffName());
			reward.setOnetimes(new Date().toLocaleString());
			if(aud.equals("10")){
				reward.setStatus("10");
			}else {
				reward.setStatus("20");
			}
			beans.add(reward);
			
		}else{
			if(aud.equals("10")){
				reward.setStatus("10");
			}else{
				reward.setStatus("20");
			}
			beans.add(reward);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		System.err.println();
		return "success";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delReward(){
		String hql = "delete from StaffReward r where r.rewardid = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{reward.getRewardid()});
		return "success";
	}
	
	@SuppressWarnings("deprecation")
	public String upSTATUS(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff s where s.staffID = ? ", new Object[]{account.getStaffID()});
		String[] rep = rewardids.split(",");
		for(int i=0;i<rep.length;i++){
			if(!rep[i].equals("")){
				List<Object> parms = new ArrayList<Object>();
				String hql = "update StaffReward r set r.status = ? ";
				if(aud.equals("20")){
					parms.add("20");//待督查审核
				}else if(aud.equals("21")){
					parms.add("21");//督查退回
				}else if(aud.equals("30")){
					parms.add("30");//待董事长审批
					hql += " ,r.twoname = ?,r.twostaffid = ?,r.twotimes = ?";
					parms.add(staff.getStaffName());
					parms.add(staff.getStaffID());
					parms.add(new Date().toLocaleString());
				}else if(aud.equals("31")){
					parms.add("31");//董事长退回
					hql += " ,r.threename = ?,r.threestaffid = ?,r.threetimes = ?";
					parms.add(staff.getStaffName());
					parms.add(staff.getStaffID());
					parms.add(new Date().toLocaleString());
				}else {
					parms.add("99");//审批通过
					hql += " ,r.threename = ?,r.threestaffid = ?,r.threetimes = ?";
					parms.add(staff.getStaffName());
					parms.add(staff.getStaffID());
					parms.add(new Date().toLocaleString());
				}
				hql += " where r.rewardid = ? ";
				parms.add(rep[i]);
				
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, parms.toArray());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suc","suc" );
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		
		return  "success";
	}

	/**
	 * select赋值
	 * @return
	 */
	public String getSel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String hql = " from Honor h where h.companyID = ? and h.honorType = ?";
		honorList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),reward.getRewstatus()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("honorList",honorList );
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		
		return "success";
	}
	
	/**
	 * （个人/单位）奖励汇总统计表
	 * return 
	 */
	@SuppressWarnings("unchecked")
	public String printONE(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		List<Object> parms = new ArrayList<Object>();
		String sql  = "select r.companyname,r.deptname,r.staffname,r.codevalue,r.money from dt_hr_staff_reward r " +
				" where r.companyid in (select c.companyid from dtcompany c where " +
				" c.companyID = ? or c.companyPID = ? )" ;
		
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		if(!reward.getStatus().equals("")){
			sql += " and r.status = ?";
			parms.add(reward.getStatus());
		}
		if(!reward.getOneormore().equals("")){
			sql += " and r.oneormore = ?";
			parms.add(reward.getOneormore());
		}
		if(!reward.getRewstatus().equals("")){
			sql += " and r.rewstatus = ?";
			parms.add(reward.getRewstatus());
		}
		if(!reward.getRewtimes().equals("")){
			sql += " and r.rewtimes like ?";
			parms.add("%" + reward.getRewtimes() + "%");
		}
		
		beans = baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
		return "printONE";
	}

	/**
	 *  年度奖励汇总
	 * return 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printMORE(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String hql = "from Honor h where h.companyID = ? and h.honorType = ? ";
		//荣誉类别数据
		beans = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"02"});
		List parm = new ArrayList();
		String sql = "select max(r.orgname),max(r.deptname),max(r.staffname) " ;
		String sql0 = "";
		if(beans!= null){
			num = beans.size();
			maxnum = beans.size()+4;
			for(int i= 0 ;i<beans.size();i++){
				Honor h = (Honor)beans.get(i);
				sql0 += " sum(case when r.codeid= ? then r.money else '0' end),";
				parm.add(h.getHonorID());
			}
		}	
		sql0 += " sum(r.money) from dt_hr_staff_reward r " +
				" where r.rewstatus = ?" +
				" and r.oneormore = ?" +
				" and r.companyid = ? " +
				" and r.rewtimes like ? group by ";
		parm.add("02");
		parm.add("00");
		parm.add(account.getCompanyID());
		parm.add("%"+reward.getRewtimes()+"%");
		String url = sql+","+sql0+" r.staffid";
		beansl = baseBeanService.getListBeanBySqlAndParams(url, parm.toArray());

		url = "select "+sql0+" r.companyid";
		beanslist = baseBeanService.getListBeanBySqlAndParams(url, parm.toArray());
		
		
		
		
		return "printMORE";
	}
	
	/**
	 * 获取人员信息
	 * @param orgName
	 * @param postName
	 * @param staffName
	 * @return
	 */
	public String getSCO(){
		Object[] params = null;
		String sql = "select d.deppostid,d.postname,s.staffid,s.staffname,o.organizationid,o.organizationname,c.status from dt_hr_staff s " +
				" left join dtcos c on c.staffid = s.staffid" +
				" left join dtcorganization o on o.organizationid = c.organizationid" +
				" left join dt_hr_deptpost d on d.deppostid = c.deppostid" +
				" where  c.cosStatus = ?" +
				" and c.companyid = ?";
		params = new Object[]{"50",comID};
//			" where s.staffid = 'cstaff20120803WMN8DPGE4Y0000024205'" +
		if (search != null && search.equals("search")) {
			sql += " and s.staffname like ?";
			params = new Object[]{"50",comID,"%"+staffName+"%"};
		}
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "getSCO";
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public StaffReward getReward() {
		return reward;
	}

	public void setReward(StaffReward reward) {
		this.reward = reward;
	}

	public List<Company> getComList() {
		return comList;
	}

	public void setComList(List<Company> comList) {
		this.comList = comList;
	}

	public String getComID() {
		return comID;
	}

	public void setComID(String comID) {
		this.comID = comID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getOtt() {
		return ott;
	}

	public void setOtt(String ott) {
		this.ott = ott;
	}

	public String getRewardids() {
		return rewardids;
	}

	public void setRewardids(String rewardids) {
		this.rewardids = rewardids;
	}

	public List<BaseBean> getHonorList() {
		return honorList;
	}

	public void setHonorList(List<BaseBean> honorList) {
		this.honorList = honorList;
	}

	public List<BaseBean> getBeansl() {
		return beansl;
	}

	public void setBeansl(List<BaseBean> beansl) {
		this.beansl = beansl;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getMaxnum() {
		return maxnum;
	}

	public void setMaxnum(int maxnum) {
		this.maxnum = maxnum;
	}

	public List<BaseBean> getBeanslist() {
		return beanslist;
	}

	public void setBeanslist(List<BaseBean> beanslist) {
		this.beanslist = beanslist;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getsID() {
		return sID;
	}

	public void setsID(String sID) {
		this.sID = sID;
	}
	

	
	
}
