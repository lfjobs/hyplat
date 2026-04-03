package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CAccountRecords;
import hy.ea.bo.CLogBook;
import hy.ea.bo.COA;
import hy.ea.bo.CRole;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.bo.util.CAccountUtil;
import hy.ea.service.CAccountService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CRoleService;
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

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CAccountAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CAccountService accountService;
	@Resource
	private ServerService serverService;
	@Resource
	private CRoleService roleService;
	@Resource
	private CLogBookService logBookService;
	private List<BaseBean> beans;
	private CAccount caccount;
	private PageForm pageForm;
	private List<CRole> roleList;
	private String result;
	private String parameter;
	private String password;
	private String newPassword;
	private String organizations;
	private String accountID;
	private int pageNumber;
	private String methodX;
	private String roleIDX;
	private String search;
	private CAccountUtil cacUtil;
	
	public String toSearchAOP(){
		
		ActionContext.getContext().getSession().put("tablesearch", cacUtil);
		
		return getListAOP();
	}
	
	public String getListAOP(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getListAOPList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		roleList = roleService.getRoleList(account.getCompanyID());
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber), sql, "select count( count(ca.accountid)) "
						+ sql.substring(sql.indexOf("from")),params);		
	
		return "getListAOP";
	}
	
	private List<Object>  getListAOPList(Map<String, Object> session, CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String sql = "select max(ca.accountid),max(cor.organizationNumber),max(cor.organizationname),max(cor.opostCode)," +
				" max(cor.opostName),max(dep.postnum),max(dep.postname),max(ca.accountname),max(ca.accountemail)," +
				" max(ca.accountStatus),max(ca.accountOnLine),max(ca.roleID) from dtCAccount ca " +
				" left join dtAudition au on au.staffid = ca.staffid" +
				" left join dtcos cos on cos.staffid = ca.staffid" +
				" left join dtcorganization cor on cor.organizationid = cos.organizationid" +
				" left join dt_hr_deptpost dep on dep.deppostid =cos.deppostid" +
				" where ca.companyid = ?";
		
		parms.add(account.getCompanyID());

		if (search != null && search.equals("search")) {
			cacUtil = (CAccountUtil) ActionContext.getContext().getSession()
					.get("tablesearch");
			if (null != cacUtil.getOrgName()
					&& !"".equals(cacUtil.getOrgName())) {
				sql += " and cor.organizationname like ?";
				parms.add("%" + cacUtil.getOrgName().trim() + "%");
			}
			if (null != cacUtil.getOpoName()
					&& !"".equals(cacUtil.getOpoName())) {
				sql += " and cor.opostName like ?";
				parms.add("%" + cacUtil.getOpoName().trim() + "%");
			}
			if (null != cacUtil.getDepName()
					&& !"".equals(cacUtil.getDepName())) {
				sql += " and dep.postname like ?";
				parms.add("%" + cacUtil.getDepName().trim() + "%");
			}
			if (null != cacUtil.getCacName()
					&& !"".equals(cacUtil.getCacName())) {
				sql += " and ca.accountname like ?";
				parms.add("%" + cacUtil.getCacName().trim() + "%");
			}
			if (null != cacUtil.getCacstauts()
					&& !"".equals(cacUtil.getCacstauts())) {
				sql += " and ca.accountStatus = ?";
				parms.add(cacUtil.getCacstauts());
			}
		}
		sql += " group by ca.accountid";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}
	/**
	 * 判读账号邮箱唯一
	 */
	public String isAccount() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		boolean isA = accountService.isAccount(account.getCompanyID(),
				parameter.trim());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isA", isA);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 统计在线人数
	 */
	public String getCaccoutOnLineNumber() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*) from CAccount where accountOnLine = '01' and companyID = ? and accountStatus = '00' ";
		Object[] parms = { account.getCompanyID() };
		int OnLineNumber = baseBeanService.getConutByByHqlAndParams(hql, parms);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("OnLineNumber", OnLineNumber);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 保存或更新account key由hibernate自动生成，ID由SID生成器生成
	 * 
	 * @return
	 */
	public String saveCAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		// int companyLicenseCount =
		// companyService.getCompanyByCompanyID(account.getCompanyID()).getCompanyLicenseCount();
		// int number =
		// accountService.getConutByCompanyID(account.getCompanyID());
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (null == caccount.getAccountID()
				|| "".equals(caccount.getAccountID())) {
			// if(number >= companyLicenseCount){
			// return getListCAccount() ;
			// }
			caccount.setAccountID(serverService.getServerID("caccount"));
			caccount.setAccountOnLine("00");
			parameter = "添加帐号";
			parameter += "(帐号名称:" + caccount.getAccountEmail() + ")";
			caccount.setAccountPassword(Utilities.MD5(caccount
					.getAccountPassword()));
			CAccountRecords CArecords = new CAccountRecords();
			CArecords.setCaccountRecordsID(serverService
					.getServerID("CAccountRecords"));
			CArecords.setAccountID(caccount.getAccountID());
			CArecords.setCompanyID(account.getCompanyID());
			CArecords.setBeforePeople(caccount.getStaffID());
			CArecords.setStartDate(new Date());
			beans.add(CArecords);
		} else {
			parameter = "修改帐号";
			parameter += "(帐号名称:"
					+ accountService.getCAccountByCompanyIDAndaccountID(
							account.getCompanyID(), caccount.getAccountID())
							.getAccountEmail() + "  修改为  "
					+ caccount.getAccountEmail() + ")";
			if (!caccount.getAccountPassword().equals(
					accountService.getCAccountByCompanyIDAndaccountID(
							account.getCompanyID(), caccount.getAccountID())
							.getAccountPassword())) {
				caccount.setAccountPassword(Utilities.MD5(caccount
						.getAccountPassword()));
			}
			if (caccount.getAccountStatus().equals("02")) {
				caccount.setAccountOnLine("00");
			}
			if (caccount.getAfterStaffID() != null
					&& !caccount.getAfterStaffID().equals("")) {
				String hql = " from CAccountRecords where accountID=? and beforePeople=? and companyID=?";
				CAccountRecords rec = (CAccountRecords) baseBeanService
						.getBeanByHqlAndParams(
								hql,
								new Object[] { caccount.getAccountID(),
										caccount.getStaffID(),
										account.getCompanyID() });
				if (rec != null) {
					rec.setAfterPeople(caccount.getAfterStaffID());
					rec.setEndDate(new Date());
					beans.add(rec);
				} else {
					CAccountRecords CArecords = new CAccountRecords();
					CArecords.setCaccountRecordsID(serverService
							.getServerID("CAccountRecords"));
					CArecords.setAccountID(caccount.getAccountID());
					CArecords.setCompanyID(account.getCompanyID());
					CArecords.setAfterPeople(caccount.getAfterStaffID());
					CArecords.setBeforePeople(caccount.getStaffID());
					CArecords.setEndDate(new Date());
					beans.add(CArecords);
				}
				CAccountRecords CArecords = new CAccountRecords();
				CArecords.setCaccountRecordsID(serverService
						.getServerID("CAccountRecords"));
				CArecords.setAccountID(caccount.getAccountID());
				CArecords.setCompanyID(account.getCompanyID());
				CArecords.setBeforePeople(caccount.getAfterStaffID());
				CArecords.setStartDate(new Date());
				beans.add(CArecords);
				caccount.setStaffID(caccount.getAfterStaffID());
			}
		}
		caccount.setCompanyID(account.getCompanyID());
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(caccount);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getListCAccount();
	}

	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", caccount);
		return getListCAccount();
	}

	/**
	 * 根据conpanyID查询所有的account
	 * 
	 * @return
	 */
	public String getListCAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		roleList = roleService.getRoleList(account.getCompanyID());
		String hql = " from CAccount c where c.companyID = ? ";
		List<String> arry = new ArrayList<String>();
		arry.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			caccount = (CAccount) ActionContext.getContext().getSession()
					.get("tablesearch");
			if (null != caccount.getAccountName()
					&& !"".equals(caccount.getAccountName())) {
				hql += " and c.accountName like ?";
				arry.add("%" + caccount.getAccountName().trim() + "%");
			}
			if (null != caccount.getRoleID()
					&& !"".equals(caccount.getRoleID())) {
				hql += " and c.roleID = ?";
				arry.add(caccount.getRoleID());
			}
			if (null != caccount.getAccountEmail()
					&& !"".equals(caccount.getAccountEmail())) {
				hql += " and c.accountEmail like ?";
				arry.add("%" + caccount.getAccountEmail().trim() + "%");
			}

			if (null != caccount.getAccountStatus()
					&& !"".equals(caccount.getAccountStatus())) {
				hql += " and c.accountStatus=?";
				arry.add(caccount.getAccountStatus().trim());
			}
			if (null != caccount.getAccountOnLine()
					&& !"".equals(caccount.getAccountOnLine())) {
				hql += " and  c.accountOnLine = ?";
				arry.add( caccount.getAccountOnLine().trim() );
			}
			/*if (!"".equals(roleIDX) && "Y".equals(methodX)) {
				hql += " and roleID=?";
				arry.add(roleIDX);
			}*/
		}
		hql += " order by accountName  ";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, arry.toArray());
		return "list";
	}

	/**
	 * 根据ID(Not Key)取得account信息，转到编辑页面
	 * 
	 * @return
	 */
	public String editCAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		roleList = roleService.getRoleList(account.getCompanyID());
		if (null == caccount || caccount.getAccountID() == null) {
			return "to_edit";
		}
		caccount = accountService.getCAccountByCompanyIDAndaccountID(
				account.getCompanyID(), caccount.getAccountID());
		String hql = "from CStaffCos where companyID = ?  and staffID = ?";
		Object[] params = { account.getCompanyID(), caccount.getStaffID() };
		BaseBean baseBean = baseBeanService.getBeanByHqlAndParams(hql, params);
		if (baseBean != null) {
			CStaffCos cosstaff = (CStaffCos) baseBean;
			caccount.setStaffName(cosstaff.getStaffName());
		}
		return "to_edit";
	}

	/**
	 * 根据ID(Not Key)删除account
	 * 
	 * @return
	 */
	public String delCAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hqlForCOA = "delete from COA where companyID = ?  and accountID = ?";
		String hqlForAccount = " delete CAccount where companyID = ? and accountID = ? ";
		Object[] params = { account.getCompanyID(), caccount.getAccountID() };
		CLogBook logBook = logBookService.saveCLogBook(
				null,
				"删除帐号(账户名称："
						+ accountService
								.getCAccountByCompanyIDAndaccountID(
										account.getCompanyID(),
										caccount.getAccountID())
								.getAccountEmail() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hqlForCOA, hqlForAccount }, params);
		return getListCAccount();
	}

	/**
	 * 根据参数获取人员staffID
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	public String getSearchStaff() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "from CStaffCos where companyID = ?  and staffName like ?";
		Object[] params = { account.getCompanyID(), "%" + parameter + "%" };
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				params);
		List<String[]> Arraylist = new ArrayList<String[]>();
		for (BaseBean baseBean : list) {
			CStaffCos staffCos = (CStaffCos) baseBean;
			Arraylist.add(new String[] { staffCos.getStaffName(),
					staffCos.getStaffID() });
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Arraylist", Arraylist);
		map.put("stafflist", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public String toChangePassword() {
		return "tochangepassword";
	}

	public String changePassword() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		result = "修改失败";
		if (password != "" && newPassword != ""
				&& Utilities.MD5(password).equals(account.getAccountPassword())) {
			String hql = "update CAccount set accountPassword = ? where companyID = ? and accountID = ?";
			Object[] params = { Utilities.MD5(newPassword),
					account.getCompanyID(), account.getAccountID() };
			CLogBook logBook = logBookService.saveCLogBook(null, "修改个人帐号(账户名称："
					+ account.getAccountEmail() + ")", account);
			beans = new ArrayList<BaseBean>();
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
					new String[] { hql }, params);
			caccount = accountService.getCAccountByCompanyIDAndaccountID(
					account.getCompanyID(), account.getAccountID());
			ServletActionContext.getRequest().getSession()
					.setAttribute("account", caccount);
			result = "修改成功!";
		}

		return "changepassword";
	}

	/*
	 * 获取此帐号所分配的部门列表
	 */
	public String getOrganizationForAccount() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from COrganization where organizationID in (select organizationID from COA where  companyID = ? and accountID=? ) ";
		String hql1 = " from Company where companyID in (select organizationID from COA where  companyID = ? and accountID=? ) ";
		Object[] params = { account.getCompanyID(), accountID };
		List<BaseBean> organizationlist = baseBeanService
				.getListBeanByHqlAndParams(hql, params);
		List<BaseBean> companylist = baseBeanService.getListBeanByHqlAndParams(
				hql1, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companylist", companylist);
		map.put("organizationList", organizationlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/*
	 * 帐号部门分配保存
	 */
	public String accountRedeployOrganization() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		String hql = "delete from COA where companyID = ?  and accountID = ?";
		Object[] params = { account.getCompanyID(), accountID };
		String[] organization = organizations.split(",");
		for (int i = 0; i < organization.length; i++) {
			COA coa = new COA();
			coa.setCompanyID(account.getCompanyID());
			coa.setAccountID(accountID);
			coa.setOrganizationID(organization[i]);
			coa.setCoaID(serverService.getServerID("coa"));
			beans.add(coa);
		}
		caccount = accountService.getCAccountByCompanyIDAndaccountID(
				account.getCompanyID(), accountID);
		CLogBook logBook = logBookService.saveCLogBook(null, "帐号机构权限分配(账户名称："
				+ caccount.getAccountEmail() + ")", account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		if (account.getAccountID().equals(accountID)) {
			String hqlcos = " from COA where  companyID = ? and accountID=?  ";
			Object[] paramscos = { account.getCompanyID(),
					account.getAccountID() };
			List<BaseBean> organizationlist = baseBeanService
					.getListBeanByHqlAndParams(hqlcos, paramscos);
			HashMap<String, String> coaMap = new HashMap<String, String>();
			for (BaseBean basebena : organizationlist) {
				COA coas = (COA) basebena;
				coaMap.put(coas.getOrganizationID(), coas.getAccountID());
			}
			session.put("coa", coaMap);
		}

		return getListCAccount();
	}

	/**
	 * 版本升级
	 * 
	 * @return
	 */
	public String updateRelease() {

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (accountID == null || "".equals(accountID)) {
			return "success";
		}
		CLogBook logBook = logBookService.saveCLogBook(null, "版本升级(升级账户："
				+ account.getAccountEmail() + ")", account);
		accountService.updateRelease(account, logBook);
		accountID = account.getAccountID();

		try {
			CLoginAction.getSessionMap().get(account.getAccountID())
					.invalidate();
		} catch (Exception e) {
		}

		return "success";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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

	public CAccountUtil getCacUtil() {
		return cacUtil;
	}

	public void setCacUtil(CAccountUtil cacUtil) {
		this.cacUtil = cacUtil;
	}

}