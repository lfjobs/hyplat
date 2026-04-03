package hy.ea.ccompany.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CAccountRecords;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.COA;
import hy.ea.bo.CRole;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CAccountService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CRoleService;
import hy.ea.service.CompanyService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CCAccountAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CAccountService accountService;
	@Resource
	private ServerService serverService;
	@Resource
	private CRoleService roleService;
	@Resource
	private COrganizationService organizationService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CCodeService codeService;
	private CAccount caccount;
	private PageForm pageForm;
	private List<CRole>  roleList;
	private String result;
	private String parameter;
	private String organizations;
    private String accountID;
	private int pageNumber;
	private List<BaseBean> beans;
	private String methodX;
	private String roleIDX;
	private String search;
	private String compid; // 需要操作的公司ID
	
	private String title;
	public String companyID;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
     * 判读账号邮箱唯一
     */
	public String isAccount() {
		boolean isA = accountService.isAccount(compid,parameter.trim());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isA", isA);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 保存或更新account
	 * key由hibernate自动生成，ID由SID生成器生成
	 * @return
	 */
	public String saveCAccount() {
//		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
//		int  companyLicenseCount = companyService.getCompanyByCompanyID(account.getCompanyID()).getCompanyLicenseCount();
//		int  number  = accountService.getConutByCompanyID(account.getCompanyID());
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if(null == caccount.getAccountID() || "".equals(caccount.getAccountID())){
//			if(number >= companyLicenseCount){
//				return getListCAccount() ;
//			}
			caccount.setAccountID(serverService.getServerID("caccount"));
			caccount.setAccountOnLine("00");
			parameter = "添加帐号";
			parameter += "(帐号名称:"+caccount.getAccountEmail()+")";
			caccount.setAccountPassword(Utilities.MD5(caccount.getAccountPassword()));
		}else{
			parameter = "修改帐号";
			parameter += "(帐号名称:"+accountService.getCAccountByCompanyIDAndaccountID(caccount.getCompanyID(), caccount.getAccountID()).
			getAccountEmail()+"  修改为  "+caccount.getAccountEmail()+")";
			if(!caccount.getAccountPassword().equals(accountService.getCAccountByCompanyIDAndaccountID(caccount.getCompanyID(), caccount.getAccountID()).getAccountPassword())){
				caccount.setAccountPassword(Utilities.MD5(caccount.getAccountPassword()));
				}
			if(caccount.getAfterStaffID()!=null&&!caccount.getAfterStaffID().equals("")){
				String hql=" from CAccountRecords where accountID=? and beforePeople=? and companyID=?";
				CAccountRecords  rec=(CAccountRecords)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{caccount.getAccountID(),caccount.getStaffID(),caccount.getCompanyID()});
				if(rec!=null){
					rec.setAfterPeople(caccount.getAfterStaffID());
					rec.setEndDate(new Date());
					beans.add(rec);
				}else{
					CAccountRecords  CArecords=new CAccountRecords();
					CArecords.setCaccountRecordsID(serverService.getServerID("CAccountRecords"));
					CArecords.setAccountID(caccount.getAccountID());
					CArecords.setCompanyID(caccount.getCompanyID());
					CArecords.setAfterPeople(caccount.getAfterStaffID());
					CArecords.setBeforePeople(caccount.getStaffID());
					CArecords.setEndDate(new Date());
					beans.add(CArecords);
				}
				CAccountRecords  CArecords=new CAccountRecords();
				CArecords.setCaccountRecordsID(serverService.getServerID("CAccountRecords"));
				CArecords.setAccountID(caccount.getAccountID());
				CArecords.setCompanyID(caccount.getCompanyID());
				CArecords.setBeforePeople(caccount.getAfterStaffID());
				CArecords.setStartDate(new Date());
				beans.add(CArecords);
				caccount.setStaffID(caccount.getAfterStaffID());
			}
		}
		
		beans.add(caccount);
		CLogBook logBook = logBookService.saveCLogBook(account.getCompanyID(), parameter, account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getListCAccount();
	}
	/**
	 * 查询功能
	 * @return
	 */
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch", caccount);
		return getListCAccount();
	}
	
	/**
	 * 根据conpanyID查询所有的account
	 * @return
	 */
	public String getListCAccount() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		companyID = account.getCompanyID();
		List<BaseBean> beans=baseBeanService.getListBeanByHqlAndParams(" from Company c ", null);
		Map<String, String> map=new HashMap<String, String>();
		for (BaseBean bean : beans) {
			Company  com=(Company)bean;
			map.put(com.getCompanyID(), com.getCompanyName());
		}
		CAccount.setoRmap(map);
		if(title!=null&&"title".equals(title)){
			companyID=compid;
		}
		roleList = roleService.getRoleList(companyID);
		String hql = " from CAccount where 1=1";
		List<String> arry=new ArrayList<String>();
		if(!"".equals(roleIDX)&&"Y".equals(methodX)){
			hql+=" and roleID=?";
			arry.add(roleIDX);
		}
		if(search !=null && search.equals("search")){
			caccount = (CAccount) ActionContext.getContext().getSession().get("tablesearch");
			if(caccount != null){
				if(caccount.getAccountName()!=null && !"".equals(caccount.getAccountName().trim())){
					hql+=" and accountName like ? ";
					arry.add("%" +caccount.getAccountName().trim()+"%");
				}
				if(caccount.getRoleID()!=null && !"".equals(caccount.getRoleID())){
					hql+=" and roleID = ? ";
					arry.add(caccount.getRoleID());
				}
				if(caccount.getAccountStatus()!=null && !"".equals(caccount.getAccountStatus())){
					hql+=" and accountStatus = ? ";
					arry.add(caccount.getAccountStatus());
				}
				if(caccount.getAccountOnLine()!=null && !"".equals(caccount.getAccountOnLine())){
					hql+=" and accountOnLine = ? ";
					arry.add(caccount.getAccountOnLine());
				}
				if(caccount.getCompanyID()!=null && !"".equals(caccount.getCompanyID())){
					hql+=" and companyID = ? ";
					arry.add(caccount.getCompanyID());
					roleList = roleService.getRoleList(caccount.getCompanyID());
				}
			}
		}else {
			hql+=" and companyID = ?";
			arry.add(companyID);
		}
		hql+=" order by companyID,accountName   ";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, arry.toArray());
		return "list";
	}
	
	/**
	 * 动态生成角色列表
	 * @return
	 */
	public String toseachto(){
		roleList = roleService.getRoleList(compid);
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleList", roleList);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
	}

	/**
	 * 根据ID(Not Key)取得account信息，转到编辑页面
	 * @return
	 */
	public String editCAccount() {
		roleList = roleService.getRoleList(compid);
		if(null == caccount){
			return "to_edit";
		}
		caccount = accountService.getCAccountByCompanyIDAndaccountID(compid,caccount.getAccountID());
		String hql = "from CStaffCos where companyID = ?  and staffID = ?";
		Object[] params = { compid,  caccount.getStaffID()};
		BaseBean baseBean =   baseBeanService.getBeanByHqlAndParams(hql, params);
		if(baseBean!=null){
		CStaffCos cosstaff = (CStaffCos)baseBean;
		caccount.setStaffName(cosstaff.getStaffName());
		}
		return "to_edit";
	}

	/**
	 * 根据ID(Not Key)删除account
	 * @return
	 */
	public String delCAccount() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String hqlForCOA = "delete from COA where companyID = ?  and accountID = ?";
		String hqlForAccount = " delete CAccount where companyID = ? and accountID = ? ";
		
		Object[] params = { compid,  caccount.getAccountID()};
		CLogBook logBook = logBookService.saveCLogBook(compid,"删除帐号(账户名称："+ accountService.getCAccountByCompanyIDAndaccountID(compid, caccount.getAccountID()).getAccountEmail()+")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hqlForCOA,hqlForAccount}, params);
		return getListCAccount();
	}
	/**
	 * 根据参数获取人员staffID
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public String getSearchStaff()  {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from CStaffCos where companyID = ?  and staffName like ?";
		Object[] params = { compid,  "%"+parameter+"%"};
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		List<String[]> Arraylist=new ArrayList<String[]>();
		for(BaseBean baseBean : list){
			CStaffCos staffCos = (CStaffCos)baseBean;
			Arraylist.add(new String[]{staffCos.getStaffName(),staffCos.getStaffID()});
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Arraylist", Arraylist);
		map.put("stafflist", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/*
	 * 获取此帐号所分配的部门列表
	*/
	public String getOrganizationForAccount()
	{
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from COrganization where organizationID in (select organizationID from COA where  companyID = ? and accountID=? ) ";
		String hql1 = " from Company where companyID in (select organizationID from COA where  companyID = ? and accountID=? ) ";
		Object[] params = { compid, accountID};
		List<BaseBean> organizationlist = baseBeanService.getListBeanByHqlAndParams(hql, params);
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(hql1, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companylist", companylist);
		map.put("organizationList", organizationlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 查询机构树,根据机构ID和对应单位ID查询
	 */
	public String getOrganizationList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<COrganization> oList;
		oList = organizationService.getOrganizationList(compid, compid);
		List<Company>	companylist = companyService.getCompanyListByPID(compid);
		if(null != companylist){
			map.put("companylist", companylist);
		}
		if(null != oList){
		map.put("organizationList", oList);
		}
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		
		return "success";
	}
	/*
	 * 帐号部门分配保存
	 * */
	public String accountRedeployOrganization()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "delete from COA where companyID = ?  and accountID = ?";
		Object[] params = { compid, accountID};
		String[] organization=organizations.split(",");
		beans=new ArrayList<BaseBean>();
		for(int i=0;i<organization.length;i++)
		{
			COA coa=new COA();
			coa.setCompanyID(compid);
			coa.setAccountID(accountID);
			coa.setOrganizationID(organization[i]);
			coa.setCoaID(serverService.getServerID("coa"));
			beans.add(coa);
		}
		if(account.getAccountID().equals(accountID)){
			String hqlcos = " from COA where  companyID = ? and accountID=?  ";
			Object[] paramscos = { compid, account.getAccountID()};
			List<BaseBean> organizationlist = baseBeanService.getListBeanByHqlAndParams(hqlcos, paramscos);
			HashMap<String, String> coaMap = new HashMap<String,String>();
			for(BaseBean basebena : organizationlist){
				COA coas = (COA)basebena;
				coaMap.put(coas.getOrganizationID(),coas.getAccountID());
			}
			session.put("coa", coaMap);
		}
		caccount = accountService.getCAccountByCompanyIDAndaccountID(compid,accountID);
		CLogBook logBook = logBookService.saveCLogBook(compid,"帐号机构权限分配(账户名称："+ caccount.getAccountEmail()+")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		caccount = accountService.getCAccountByCompanyIDAndaccountID(compid,accountID);
		return getListCAccount();
	}

	public List<CRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<CRole> roleList) {
		this.roleList = roleList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public CAccount getCaccount() {
		return caccount;
	}

	public void setCaccount(CAccount caccount) {
		this.caccount = caccount;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	
    public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getOrganizations() {
		return organizations;
	}

	public void setOrganizations(String organizations) {
		this.organizations = organizations;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getMethodX() {
		return methodX;
	}
	public void setMethodX(String methodX) {
		this.methodX = methodX;
	}
	public String getRoleIDX() {
		return roleIDX;
	}
	public void setRoleIDX(String roleIDX) {
		this.roleIDX = roleIDX;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getCompid() {
		return compid;
	}
	public void setCompid(String compid) {
		this.compid = compid;
	}
	
}