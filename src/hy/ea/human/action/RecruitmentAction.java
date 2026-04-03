package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Dtrecruitrule;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/*
 * 招聘规划汇总
 * */
@Controller
@Scope("prototype")
public class RecruitmentAction {
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private Dtrecruitrule dtrecruitrule;
	private String search;
	private Date sDate;
	private Date eDate;
	private PageForm pageForm;
	private String recruitruleid;
	private InputStream excelStream;
	private int pageNumber; 
	private List<BaseBean> listBasic;
	private String tabdateVal; //打印查询条件
	 
	/**
	 * 根据条件查询招聘计划列表
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", dtrecruitrule);
		return getRecruitmentList();
	} 
	
	/**
	 *获得招聘计划列表
	 * @return
	 */
	public String getRecruitmentList() {
		Map<String, Object> session =  ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "+sql.substring(sql.indexOf("from")), params);
		return "userList";
	}
	
	/**
	 * 查询列表（可根据条件查询）被调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		parms.add(account.getCompanyID());
		String sql1 = "select d.postname,c.organizationname,n.staffname,r.starttime,r.endtime,r.nownumbers,"
					+ " r.addnumbers,r.addreason,r.cutnumbers,r.cutreason,r.inputnumbers,r.channels,r.tabdate";
		String sql2 = ",r.recruitrulekey,r.recruitruleid,r.organizationid,r.deptpostid,r.staffid";
		
		String sql3  = " from dtrecruitrule r" 
					+ " left join dt_hr_deptpost d on d.deppostid = r.deptpostid" 
					+ " left join dtcorganization c on c.organizationid = r.organizationid" 
					+ " left join dt_hr_staff n on n.staffid = r.staffid" 
					+ " where r.companyID = ?";
		
		String sql = sql1 + sql2 + sql3;
		String sql4 = sql1 + sql3;
		
		if(search != null && search.equals("search")){
			dtrecruitrule = (Dtrecruitrule) session.get("tablesearch");
			if(null!=dtrecruitrule.getOrganizationid()&&!"".equals(dtrecruitrule.getOrganizationid()))
			{
				sql += " and r.organizationid = ?";
				parms.add(dtrecruitrule.getOrganizationid());
			}
			if(null!=dtrecruitrule.getDeptpostid()&&!"".equals(dtrecruitrule.getDeptpostid())){
				sql+=" and r.deptpostid  =　?";
				parms.add(dtrecruitrule.getDeptpostid());
			}
			if(null!=dtrecruitrule.getTabdate()&&!"".equals(dtrecruitrule.getTabdate())){
				sql+=" and r.tabdate  =　?";
				parms.add(dtrecruitrule.getTabdate());
			}
		}
		sql+= " order by r.recruitruleid";
		sql4+= " order by r.recruitruleid";
		results.add(sql);
		results.add(parms.toArray());
		results.add(sql4);
		return results;
	}
	
	/**
	 * 保存/修改招聘规划
	 * @return
	 */
	public String saveRecruitment(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (dtrecruitrule.getRecruitruleid() == null 
				|| "".equals(dtrecruitrule.getRecruitruleid())) {
			dtrecruitrule.setRecruitruleid(serverService.getServerID("dtrecruitrule"));
			dtrecruitrule.setTabdate(Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			parameter = "添加 "+request.getParameter("orgName")+" 中的 "
					+ request.getParameter("postName")+" 岗位招聘规划";
		}
		else{
		     parameter = "修改 "+request.getParameter("orgName")+" 中的 "
					+ request.getParameter("postName")+" 岗位招聘规划";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		dtrecruitrule.setCompanyID(account.getCompanyID());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(dtrecruitrule);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
		
	}
	
	/**
	 * 删除招聘规划
	 * @return
	 */
	public String delRecruitment(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql = "delete Dtrecruitrule where recruitruleid = ?";
		Object[] params = {dtrecruitrule.getRecruitruleid() };
		List<BaseBean> listBasics = new ArrayList<BaseBean>();
		parameter = "删除 "+request.getParameter("orgName")+" 中的 "
					+ request.getParameter("postName")+" 岗位招聘规划";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		listBasics.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(listBasics,
				new String[] { hql }, params);
		return getRecruitmentList();		
	}
	
	/**
	 * 导出
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session =  ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String sql = list.get(2).toString();
		Object[] params = (Object[]) list.get(1);
		excelStream = excelService.showExcel(Dtrecruitrule.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, params));
		CLogBook logBook =logBookService.saveCLogBook(organizationID,"导出招聘规划管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 打印预览
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String printaccess(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		listBasic = baseBeanService.getListBeanBySqlAndParams(sql, params);
		return "information";
		
	}
	
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}
	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public Dtrecruitrule getDtrecruitrule() {
		return dtrecruitrule;
	}
	public void setDtrecruitrule(Dtrecruitrule dtrecruitrule) {
		this.dtrecruitrule = dtrecruitrule;
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
	public ServerService getServerService() {
		return serverService;
	}
	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Date getsDate() {
		return sDate;
	}
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}
	public Date geteDate() {
		return eDate;
	}
	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}
	public String getRecruitruleid() {
		return recruitruleid;
	}
	public void setRecruitruleid(String recruitruleid) {
		this.recruitruleid = recruitruleid;
	}
	public List<BaseBean> getListBasic() {
		return listBasic;
	}
	public void setListBasic(List<BaseBean> listBasic) {
		this.listBasic = listBasic;
	}
	public String getTabdateVal() {
		return tabdateVal;
	}
	public void setTabdateVal(String tabdateVal) {
		this.tabdateVal = tabdateVal;
	}
}
