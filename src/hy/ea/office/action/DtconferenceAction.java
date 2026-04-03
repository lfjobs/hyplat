package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.office.Dtconference;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
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
 * 现场会议管理
 * 
 * @author 李伟志
 * 
 */
@Controller
@Scope("prototype")
public class DtconferenceAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private InputStream excelStream;
	private int pageNumber;
	private String search;
	private List<BaseBean> beans;
	private Dtconference dtconference;
	private String newState; //00 ：准备会议阶段01：正式会议阶段02：会议闭幕阶段
	private String parameter;
	private String staffIDvalue;
	private List<DepartmentPost> depList ;
	private String result;
	private File photo;
	private List<Dtconference> dtconList;
	private String childrenID;
	
	/**
	 * 会议流程排序
	 * @return
	 */
	public String sortChildDtcon() {
		String[] ids = childrenID.split("_");
 		String hql = "";	
		for (int i=0;i<ids.length;i++) {
			String id=ids[i];
			String n = (i+1)+"";
			if(n.length()!=2){
				n = "0"+n;
			}
			 hql += "update Dtconference set serNum = '"+n+"' where conferenceid = '"+id+"' and state ='"+newState+"'_";
		}
		String[] hqls = hql.substring(0, hql.length()-1).split("_");
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,hqls,null);
		return getDtconferenceList();
	}

	
	/**
	 * 获取会议流程列表
	 * @return
	 */
	public String sortchild_Dtconference(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		String hql = "from Dtconference d where d.companyid = ? and d.organizationid = ? and d.state = ? and d.hisdata = ? order by d.serNum";
		List<BaseBean> obj = baseBeanService.getListBeanByHqlAndParams(hql , new Object[]{account.getCompanyID(),organizationID,newState,"00"});
		dtconList = new ArrayList<Dtconference>();
		for(int i = 0; i <obj.size();i++){
			dtconList.add((Dtconference)obj.get(i));
		}
		return "sortchild_Dtconference";
	}
	
	
	/**
	 * 加载岗位事件
	 * 
	 * @return
	 */
	
	public String getPos(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String hql = "from DepartmentPost d where exists ( select c.depPostID from COS c" 
				+ " where c.depPostID = d.depPostID and  c.companyID = ? and c.cosStatus = ?" 
				+ " and c.status = ? and c.staffID = ?)";
		
		Object[] params = {account.getCompanyID(),"50","01", staffIDvalue };
		DepartmentPost dept = (DepartmentPost)baseBeanService.getBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dept", dept);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 
	 * 导出会议信息
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public String showDtconferenceExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		List<Object> list = (List<Object>)ActionContext.getContext().getSession().get("allList");
		String sql = (String) list.get(0);
		String sql1 = sql.substring(0, 7);
		String sql2 = sql.substring(51, 182);
		sql2 = sql2.replace("c.orgname","o.organizationName");
		String sql3 = sql.substring(218);
		
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(Dtconference.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql1+sql2+sql3, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出会议信息", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	
	
	/**
	 * 
	 * 删除会议信息
	 * @return
	 */
	
	public String deleteDtconference(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = "delete Dtconference where state = ? and conferenceid = ?";
		Object[] params = { newState,dtconference.getConferenceid() };
		beans = new ArrayList<BaseBean>();
		parameter = "删除会议信息：(用户名：" +account.getAccountName() + ")";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);

		return "success";
	}
	
	/**
	 * 
	 * 保存/修改会议信息
	 * @return
	 */
	public String addDtconference(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = null;
		
		if(dtconference.getAnnexurl() != null && !"".equals(dtconference.getAnnexurl()) 
				&& dtconference.getAnnexurl().indexOf("\\\\")==-1){
			dtconference.setAnnexurl(dtconference.getAnnexurl().replaceAll("\\\\", "\\\\\\\\"));
		}
		
		if(!dtconference.getConferenceid().equals("")){
			Dtconference dtconferenceNew = getDtconferenceOld();
			
			dtconferenceNew.setStartdate(dt(dtconference.getStartdate()));
			dtconferenceNew.setEnddate(dt(dtconference.getEnddate()));
			dtconferenceNew.setOrgname(dtconference.getOrgname());
			dtconferenceNew.setPostname(dtconference.getPostname());
			dtconferenceNew.setJobname(dtconference.getJobname().trim());
			dtconferenceNew.setResponsible(dtconference.getResponsible());
			dtconferenceNew.setFlowname(dtconference.getFlowname().trim());
			dtconferenceNew.setTcontent(dtconference.getTcontent());
			dtconferenceNew.setUpdatename(account.getAccountName());
			dtconferenceNew.setUpdatedate(new Date().toString());
			dtconferenceNew.setAnnexurl(dtconference.getAnnexurl());
			dtconferenceNew.setSerNum(dtconference.getSerNum());
			dtconferenceNew.setCondate(dtconference.getCondate());
			beans.add(dtconferenceNew);
			parameter = "修改现场会议:(用户名：" + account.getAccountName() + ")";
			
		}else{
			dtconference.setCompanyid(account.getCompanyID());
			dtconference.setOrganizationid(organizationID);
			dtconference.setConferenceid(serverService.getServerID("dtconference"));
			dtconference.setState(newState);
			dtconference.setCreatename(account.getAccountName());
			dtconference.setCreatedate(new Date().toString());
			dtconference.setStartdate(dt(dtconference.getStartdate()));
			dtconference.setEnddate(dt(dtconference.getEnddate()));
			dtconference.setHisdata("00");
			beans.add(dtconference);
			parameter = "创建现场会议:(用户名：" + account.getAccountName() + ")";
			
		}
		
		logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		beans.add(logBook);
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				null, null);
		
		return "success";
	}
	
	/**
     * 前台ajax修改存储文件路径
     * @return
     */
    public String updateEdit(){
    	List<BaseBean> beans=new ArrayList<BaseBean>();
    	String hql = "from Dtconference where conferenceid = ?";
    	Dtconference dtconferences = (Dtconference)baseBeanService.getBeanByHqlAndParams(hql, 
    						new Object[]{dtconference.getConferenceid()});
    	if(dtconference.getAnnexurl()!=null&&!"".equals(dtconference.getAnnexurl())
    			&& dtconference.getAnnexurl().indexOf("\\\\")==-1){
    		dtconferences.setAnnexurl(dtconference.getAnnexurl().replaceAll("\\\\", "\\\\\\\\"));
		}
    	beans.add(dtconferences);
    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
    }
	
	public String toSeach(){
		ActionContext.getContext().getSession().put("dtconference",dtconference);
		return getDtconferenceList();
	}
	// 时间类型转换 00:00
	private String dt(String ur){
			ur = ur.substring(0, 5);
		return ur;
	}
	
	/**
	 *  获取所有会议信息
	 * @return
	 */
	
	public String getDtconferenceList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		
		String sql0 = "select count(*) from dtconference where CompanyID=? and organizationID = ? and State =? and hisdata = ?";
		int j = baseBeanService.getConutByBySqlAndParams(sql0, new Object[]{account.getCompanyID(),organizationID,newState,"00"});
		
		if(j == 0){
			preset();
		}
		
		List<Object> list = getListsql();
		ActionContext.getContext().getSession().put("allList",list);
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		
		return "getDtconferenceList";
	}
	
	/**
	 * 
	 * 获取一条Dtconference实体信息
	 * @return
	 */
	private Dtconference getDtconferenceOld(){
		String hql = "from Dtconference where conferenceid =  ? ";
		Object[] params = { dtconference.getConferenceid() };
		List<BaseBean> baseList = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		Dtconference dtconferenceOld = (Dtconference) baseList.get(0);
		
		return dtconferenceOld;
	}
	/**
	 * 
	 * 	根据条件查询全部信息
	 * 
	 * @return
	 */
	private List<Object> getListsql() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");
		
		String sql = "select c.conferenceid,c.companyid,c.organizationid,c.condate," +
				"c.startdate,c.enddate,c.orgname,c.postname,c.jobname," +
				"c.responsible,c.flowname,c.tcontent,c.annexid,c.annexname,c.annexurl,c.state,o.organizationName,c.serNum" +
				" from DTConference c left join dtCOrganization o on c.orgname = o.organizationid where 1=1";
		sql+= " and c.state = ?";
		parms.add(newState);
		sql+= " and c.organizationid = ?";
		parms.add(organizationID);
		sql+= " and c.hisdata = ?";
		parms.add("00");
		if (search != null && search.equals("search")) {
			dtconference = (Dtconference) session.get("dtconference");
			if (null != dtconference.getCondate()
					&& !"".equals(dtconference.getCondate())) {
				sql += " and c.condate = ? ";
				parms.add(dtconference.getCondate());
			}
			if (null != dtconference.getStartdate()
					&& !"".equals(dtconference.getStartdate())) {
				sql += " and c.startdate >= ? ";
				parms.add(dt(dtconference.getStartdate()));
			}
			if (null != dtconference.getEnddate()
					&& !"".equals(dtconference.getEnddate())) {
				sql += " and c.enddate <= ? ";
				parms.add(dt(dtconference.getEnddate()));
			}
			
			if (null != dtconference.getResponsible()
					&& !"".equals(dtconference.getResponsible())) {
				sql += " and c.responsible like ? ";
				parms.add("%"+dtconference.getResponsible()+"%");
			}
			if (null != dtconference.getFlowname()
					&& !"".equals(dtconference.getFlowname())) {
				sql += " and c.flowname like ? ";
				parms.add("%"+dtconference.getFlowname()+"%");
			}
		}
		sql+= " order by c.serNum";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	//预设
		
	private String preset(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		beans = new ArrayList<BaseBean>();
		String[] flown = new String[]{"叫醒管理","早餐管理","接送管理","早会管理","入场管理"
				,"开幕管理","会议管理","休息管理","会议管理","午餐管理","中午休息","下午叫醒"
				,"下午入场","下午开幕","下午会议","会议休息","下午会议","晚餐管理","活动入场"
				,"晚间管理","接送管理","住宿管理","夜间值班"};
		for(int i=0;i<flown.length;i++){
			dtconference = new Dtconference();
			dtconference.setCompanyid(account.getCompanyID());
			dtconference.setOrganizationid(organizationID);
			dtconference.setConferenceid(serverService.getServerID("dtconference"));
			dtconference.setState(newState);
			String n = (i+1)+"";
			if(n.length()!=2){
				n = "0"+n;
			}
			dtconference.setSerNum(n);
			dtconference.setHisdata("00");
			dtconference.setCreatename(account.getAccountName());
			dtconference.setCreatedate(new Date().toString());
			dtconference.setFlowname(flown[i]);
			beans.add(dtconference);
			
		}
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				null, null);
		return "ok";
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


	public ShowExcelService getExcelService() {
		return excelService;
	}


	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}


	public CLogBookService getLogBookService() {
		return logBookService;
	}


	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}


	public PageForm getPageForm() {
		return pageForm;
	}


	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}


	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public Dtconference getDtconference() {
		return dtconference;
	}

	public void setDtconference(Dtconference dtconference) {
		this.dtconference = dtconference;
	}

	public String getNewState() {
		return newState;
	}

	public void setNewState(String newState) {
		this.newState = newState;
	}

	public String getStaffIDvalue() {
		return staffIDvalue;
	}

	public void setStaffIDvalue(String staffIDvalue) {
		this.staffIDvalue = staffIDvalue;
	}

	public List<DepartmentPost> getDepList() {
		return depList;
	}

	public void setDepList(List<DepartmentPost> depList) {
		this.depList = depList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}


	public List<Dtconference> getDtconList() {
		return dtconList;
	}


	public void setDtconList(List<Dtconference> dtconList) {
		this.dtconList = dtconList;
	}


	public String getChildrenID() {
		return childrenID;
	}


	public void setChildrenID(String childrenID) {
		this.childrenID = childrenID;
	}




	

	
}
