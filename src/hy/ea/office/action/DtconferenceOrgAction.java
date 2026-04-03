package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Dtconferenceorg;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 会议组织机构管理
 * 
 * @author 李伟志
 * 
 */
@Controller
@Scope("prototype")
public class DtconferenceOrgAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private PageForm pageForm;
	private InputStream excelStream;
	private int pageNumber;
	private String search;
	private List<BaseBean> beans;
	private String parameter;
	private Dtconferenceorg dtconferenceorg;
	private Map<String,Staff> staffListes;
	private Staff searchCStaff;
	
	
	public String toSeach(){
		ActionContext.getContext().getSession().put("dtconferenceorg",
				dtconferenceorg);
		
		return getAllDtconOrg();
	}
	

	/**
	 * 
	 * 导出参会花名册
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public String showDtconOrgExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		List<Object> list = (List<Object>)ActionContext.getContext().getSession().get("allList");
		String sql = (String) list.get(0);
		String sql1 = sql.substring(0, 64);
		String sql2 = sql.substring(164);
		
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(Dtconferenceorg.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql1+sql2, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出花名册", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delDtconferenceOrg(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		
		String hql  ="delete from Dtconferenceorg  where conferenceorgid = ?";
		Object[] params = {dtconferenceorg.getConferenceorgid()};
		beans = new ArrayList<BaseBean>();
		parameter = "删除会议组织机构：(-->)";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		
		
		
		return "success";
	}
	
	/**
	 * 获取全部
	 * 
	 * @return
	 */
	
	public String getAllDtconOrg(){
		List<Object> list = getListsql();
		ActionContext.getContext().getSession().put("allList",list);
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		
		return "getAllDtconOrg";
	}
	
	
	private List<Object> getListsql(){
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		
		String sql = "select f.conorgname,h.staffname,f.jobname,h.reference," +
				" f.remarks,f.responsible,f.conferenceorgid,f.companyid," +
				" f.organizationid,f.conferenceorgekey,f.ctname,f.ctdate" +
				"  from dtconferenceorg f left join " +
				" dt_hr_staff h on h.staffID = f.responsible where 1 = 1";
		
		sql+=" order by  f.conorgname,f.ctdate";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	
	/**
	 * 添加
	 * @return
	 */
	public String addDtconferenceOrg(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		beans = new ArrayList<BaseBean>();
		
		if(!dtconferenceorg.getConferenceorgid().equals("")){
			dtconferenceorg.setUpname(account.getAccountName());
			dtconferenceorg.setUpdates(new Date().toString());
			beans.add(dtconferenceorg);
		}else{
			dtconferenceorg.setConferenceorgid(serverService.getServerID("conferenceorg"));
			dtconferenceorg.setOrganizationid(organizationID);
			dtconferenceorg.setCompanyid(account.getCompanyID());	
			dtconferenceorg.setCtname(account.getAccountName());
			dtconferenceorg.setCtdate(new Date().toString());
			beans.add(dtconferenceorg);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		
		
		return "success";
	}
	
	
	/**
	 * 获取所有在职员工信息 getAllStaff()
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getAllStaff(){
		String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and cosStatus = ? )";
		List param = new ArrayList();
		param.add("50");
		if(search != null && search.equals("search")){
			searchCStaff = (Staff)ActionContext.getContext().getSession().get("cstaff");
			if(!"".equals(searchCStaff.getStaffCode())){
				hql += " and s.staffCode like ?";
				param.add("%"+searchCStaff.getStaffCode()+"%");
			}
			if(!"".equals(searchCStaff.getStaffName())){
				hql += " and s.staffName like ?";
				param.add("%"+searchCStaff.getStaffName()+"%");
			}
			if(!"".equals(searchCStaff.getStaffIdentityCard())){
				hql += " and s.staffIdentityCard like ?";
				param.add("%"+searchCStaff.getStaffIdentityCard()+"%");
			}
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql,
				param.toArray());
		
		return "staffForCashier";
	}
	
	public String getSearchStaff(){
		ActionContext.getContext().getSession().put("cstaff",
				searchCStaff);
		return getAllStaff();
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

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
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

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	public Map<String, Staff> getStaffListes() {
		return staffListes;
	}


	public void setStaffListes(Map<String, Staff> staffListes) {
		this.staffListes = staffListes;
	}


	public Dtconferenceorg getDtconferenceorg() {
		return dtconferenceorg;
	}


	public void setDtconferenceorg(Dtconferenceorg dtconferenceorg) {
		this.dtconferenceorg = dtconferenceorg;
	}


	public Staff getSearchCStaff() {
		return searchCStaff;
	}


	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	

}
