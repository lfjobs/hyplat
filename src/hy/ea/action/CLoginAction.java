package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.COA;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.CLoginService;
import hy.ea.service.CREMIService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;

@Controller
@Scope("prototype")
public class CLoginAction {
	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	@Resource
	private CLoginService loginService;
	@Resource
	private CREMIService cremiService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	private String companyIdentifier;
	private CAccount account;
	private String validateCode;
	private String companyType;
	private String imgLog;
	private String activityLogin;
	private String weixinCompanyId;
	protected static final Logger LOGGER = Logger.getLogger(Class.class);
	private String result;
	private TEshopCustomer customer;

	public String actLogin() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String account=customer.getAccount();
		String password=customer.getPassword();
		TEshopCustomer cus=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{account});
		
			if(cus!=null && account.equals(cus.getAccount()) &&Utilities.MD5(password).equals(cus.getPassword())){
				Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{cus.getStaffid()});
				
				SessionWrap sw = SessionWrap.getInstance();
				sw.setObject(session, SessionWrap.KEY_STAFF, staff);
				sw.setObject(session, SessionWrap.KEY_CUSTOMER, cus);
				return "activityLogin";
			}else{
				return "activityLoginFaild";
			}
				
		
	}
	public String login() {

		// long start = System.currentTimeMillis();
		// 用户名密码不能为空

		ActionContext.getContext().put("companyIdentifier", companyIdentifier);
		ActionContext.getContext().put("account", account);
		if (null == account || null == account.getAccountEmail()
				|| null == account.getAccountPassword()) {
			ActionContext.getContext().put("result", 97);
			if (null != activityLogin && !activityLogin.equals("")
					&& activityLogin.equals("activity")) {
				return "activityLoginFaild";
			} else {
				return "failed";
			}
		}
		if (null == activityLogin || activityLogin.equals("")) {

			// 验证码不能为空
			Object oValidateCode = ActionContext.getContext().getSession()
					.get("invalidImge");
			if (null == oValidateCode) {
				ActionContext.getContext().put("result", 98);
				return "failed";
			}

			// 后台处理验证码的问题
			String validateCodeFromSession = (String) oValidateCode;
			if (!validateCode.equalsIgnoreCase(validateCodeFromSession)) {
				ActionContext.getContext().put("result", 99);
				return "failed";
			}
		}
		/**
		 * 1 :登录企业不存在 2或者4:登录账号状态不正常 3或者5:登录账号密码不正确 6 :禁止多开登录 99 :验证码不正确 98
		 * :验证码不能为空 97 :用户名和密码不能为空 0 :正常
		 */
		String sql="select   d.companyidentifier  from dtCompany d  where d.companyidentifier=? ";
		 Object  obj= baseBeanService.getObjectBySqlAndParams(sql,new String[]{companyType});
		
		int result = loginService
				.login(companyIdentifier, account,obj!=null?obj.toString():"");
		if (0 != result) {
			ActionContext.getContext().put("result", result);
			if (null != activityLogin && !activityLogin.equals("")
					&& activityLogin.equals("activity")) {
				return "activityLoginFaild";
			} else {
				return "failed";
			}
		}

		// 单点登录处理
		if (0 == result) {
			// 浏览器多开登录
			CAccount caccount = (CAccount) ActionContext.getContext()
					.getSession().get("account");

			if (caccount != null) {
				// 正式发布开启
				ActionContext.getContext().put("result", 6);
				sessionMap.get(caccount.getAccountID()).invalidate();
				return "failed";
			}
			if (sessionMap.containsKey(account.getAccountID())) {
				try {
					sessionMap.get(account.getAccountID()).invalidate();
				} catch (Exception e) {

				}
			}
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (session != null) {
				sessionMap.put(account.getAccountID(), session);
			}
			// 将登陆员工专岗所属部门放入Session中
			Object object = baseBeanService
					.getObjectBySqlAndParams(
							"select co.organizationname,co.organizationid from dtcos cs left join dt_hr_deptpost hd on cs.deppostid=hd.deppostid left join dtcorganization co on co.organizationid=hd.organizationid where cs.status=? and cs.staffid=? and cs.companyid=?",
							new Object[] { "01", account.getStaffID(),
									account.getCompanyID() });
			if (object != null) {
				session.setAttribute("orgName", object.toString());
			} else {
				session.setAttribute("orgName", "");
			}
			if (account.getStaffID() != null) {
				BaseBean bean = baseBeanService.getBeanByHqlAndParams(
						"from Staff where staffID = ?",
						new Object[] { account.getStaffID() });
				if (bean != null) {
					Staff sta = (Staff) bean;
					account.setStaffName(sta.getStaffName());
				}

			}
			// 将account信息放入Session中
			session.setAttribute("account", account);

			// 存储集团公司的标识
			BaseBean bean = baseBeanService.getBeanByHqlAndParams(
					"from Company where companyID = ?",
					new Object[] { account.getCompanyID() });
			if (bean != null) {
				Company com = (Company) bean;
				// 这个集团公司标志 在下面的 currentcompany 里面都有了。。暂时不去掉因为怕其他地方调用。
				session.setAttribute("groupCompanySn", com.getGroupCompanySn());
				// 将当前公司存入session
				session.setAttribute("currentcompany", com);
			}

			// 将cir放入session中
			HashMap<String, String> irMap = cremiService.getCIRMapByRoleID(
					account.getCompanyID(), account.getRoleID());
			session.setAttribute("cir", irMap);
			// 将coa放入session中
			String hql = " from COA where  companyID = ? and accountID = ?  ";
			Object[] params = { account.getCompanyID(), account.getAccountID() };
			List<BaseBean> organizationlist = baseBeanService
					.getListBeanByHqlAndParams(hql, params);
			HashMap<String, String> coa = new HashMap<String, String>();
			for (BaseBean basebena : organizationlist) {
				COA coas = (COA) basebena;
				coa.put(coas.getOrganizationID(), coas.getAccountID());
			}
			session.setAttribute("coa", coa);
			// 设置账户为在线
			account.setAccountOnLine("01");
			List<BaseBean> beans = new ArrayList<BaseBean>();
			beans.add(account);
			CLogBook logBook = logBookService.saveCLogBook(null, "登录系统",
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		// long end = System.currentTimeMillis();
		// System.out.println("#----TEST START @" + start + ", END @" + end
		// + "----#");
		// System.out.println("\t Test total time:" + (end - start) + "ms(~"
		// + (((double) (end - start)) / 1000.0) + "s)");
		// LOGGER.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		// LOGGER.info("\t Test total time:" + (end - start) + "ms(~"
		// + (((double) (end - start)) / 1000.0) + "s)");
		if (null != activityLogin && !activityLogin.equals("")
				&& activityLogin.equals("activity")) {
			/*
			 * HttpServletResponse response=ServletActionContext.getResponse();
			 * try { response.sendRedirect(
			 * "/page/mobile/activity/activity_publish.jsp?weixinCompanyId="
			 * +weixinCompanyId); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
			return "activityLogin";
		} else {
			return (result == 0) ? "success" : "failed";
		}
	}

	// 5L5C微网站登陆
	public String loginMini() {
		ActionContext.getContext().put("companyIdentifier", companyIdentifier);
		ActionContext.getContext().put("account", account);
		if (null == account || null == account.getAccountEmail()
				|| null == account.getAccountPassword()) {
			ActionContext.getContext().put("result", 97);
			if (null != activityLogin && !activityLogin.equals("")
					&& activityLogin.equals("activity")) {
				return "activityLoginFaild";
			} else {
				return "failed";
			}
		}
		int result = loginService
				.login(companyIdentifier, account, companyType);
		if (0 != result) {
			ActionContext.getContext().put("result", result);
			if (null != activityLogin && !activityLogin.equals("")
					&& activityLogin.equals("activity")) {
				return "activityLoginFaild";
			} else {
				return "failed";
			}
		}

		// 单点登录处理
		if (0 == result) {
			// 浏览器多开登录
			CAccount caccount = (CAccount) ActionContext.getContext()
					.getSession().get("account");

			if (caccount != null) {
				// 正式发布开启
				ActionContext.getContext().put("result", 6);
				sessionMap.get(caccount.getAccountID()).invalidate();
				return "failed";
			}
			if (sessionMap.containsKey(account.getAccountID())) {
				try {
					sessionMap.get(account.getAccountID()).invalidate();
				} catch (Exception e) {

				}
			}
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (session != null) {
				sessionMap.put(account.getAccountID(), session);
			}
			// 将登陆员工专岗所属部门放入Session中
			Object[] object = (Object[]) baseBeanService
					.getObjectBySqlAndParams(
							"select co.organizationname,co.organizationid from dtcos cs left join dt_hr_deptpost hd on cs.deppostid=hd.deppostid left join dtcorganization co on co.organizationid=hd.organizationid where cs.status=? and cs.staffid=? and cs.companyid=?",
							new Object[] { "01", account.getStaffID(),
									account.getCompanyID() });
			if (object != null && object[0] != null && object[1] != null) {
				session.setAttribute("orgName", object[0].toString());
				session.setAttribute("organizationID", object[1].toString());
			} else {
				session.setAttribute("orgName", "");
			}
			if (account.getStaffID() != null) {
				BaseBean bean = baseBeanService.getBeanByHqlAndParams(
						"from Staff where staffID = ?",
						new Object[] { account.getStaffID() });
				if (bean != null) {
					Staff sta = (Staff) bean;
					account.setStaffName(sta.getStaffName());
				}

			}
			// 将account信息放入Session中
			session.setAttribute("account", account);

			// 存储集团公司的标识
			BaseBean bean = baseBeanService.getBeanByHqlAndParams(
					"from Company where companyID = ?",
					new Object[] { account.getCompanyID() });
			if (bean != null) {
				Company com = (Company) bean;
				// 这个集团公司标志 在下面的 currentcompany 里面都有了。。暂时不去掉因为怕其他地方调用。
				session.setAttribute("groupCompanySn", com.getGroupCompanySn());
				// 将当前公司存入session
				session.setAttribute("currentcompany", com);
			}

			// 将cir放入session中
			HashMap<String, String> irMap = cremiService.getCIRMapByRoleID(
					account.getCompanyID(), account.getRoleID());
			session.setAttribute("cir", irMap);
			// 将coa放入session中
			String hql = " from COA where  companyID = ? and accountID = ?  ";
			Object[] params = { account.getCompanyID(), account.getAccountID() };
			List<BaseBean> organizationlist = baseBeanService
					.getListBeanByHqlAndParams(hql, params);
			HashMap<String, String> coa = new HashMap<String, String>();
			for (BaseBean basebena : organizationlist) {
				COA coas = (COA) basebena;
				coa.put(coas.getOrganizationID(), coas.getAccountID());
			}
			session.setAttribute("coa", coa);
			// 设置账户为在线
			account.setAccountOnLine("01");
			List<BaseBean> beans = new ArrayList<BaseBean>();
			beans.add(account);
			CLogBook logBook = logBookService.saveCLogBook(null, "登录系统",
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return (result == 0) ? "successMini" : "failed";
	}

	public String logOut() {
		// 正式发布开启
		HttpSession session = ServletActionContext.getRequest().getSession();
		account = (CAccount) session.getAttribute("account");
		if (account != null) {
			companyIdentifier=account.getCompany().getCompanyIdentifier();
			account = (CAccount) baseBeanService.getBeanByKey(CAccount.class,
					account.getAccountKey());
			account.setAccountOnLine("00");
			List<BaseBean> beans = new ArrayList<BaseBean>();
			beans.add(account);
			CLogBook logBook = logBookService.saveCLogBook(null, "注销系统！",
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
			if (sessionMap.containsKey(account.getAccountID())) {
				try {
					sessionMap.get(account.getAccountID()).invalidate();
				} catch (Exception e) {

				}
			}
		}
		return "failed";
	}

	/**
	 * 行业类别加载
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getSCode() {

		String sql = "select c.codeid, c.codevalue from dtscode c where c.groupsn = ? and c.isleaf = ?";
		List cc = baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[] {"hylb","00" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scodelist", cc);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 查询公司机构列表
	 * @return
	 */
	public String getIdentifier(){
		List<BaseBean> lb = new ArrayList<BaseBean>();
		String hql = "from Company c where c.companyStatus = ? and c.companyType = ? order by c.companyName";
		String hql1 = "from Company c where c.companyStatus = ? and c.companyType in (?,?) order by c.companyName";
		if(companyType.equals("01")){
			lb = baseBeanService.getListBeanByHqlAndParams(hql1 ,
				new Object[] {"00","11","12" });
		}else{
			lb = baseBeanService.getListBeanByHqlAndParams(hql ,
				new Object[] {"00",companyType });
		}
		if( lb.size() == 0 ){
			lb = baseBeanService.getListBeanByHqlAndParams(hql ,
					new Object[] {"00","00" });
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("identlist", lb);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}
	
	public static Map<String, HttpSession> getSessionMap() {
		return sessionMap;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

	public String getCompanyIdentifier() {
		return companyIdentifier;
	}

	public void setCompanyIdentifier(String companyIdentifier) {
		this.companyIdentifier = companyIdentifier;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getImgLog() {
		return imgLog;
	}

	public void setImgLog(String imgLog) {
		this.imgLog = imgLog;
	}

	public String getActivityLogin() {
		return activityLogin;
	}

	public void setActivityLogin(String activityLogin) {
		this.activityLogin = activityLogin;
	}

	public String getWeixinCompanyId() {
		return weixinCompanyId;
	}

	public void setWeixinCompanyId(String weixinCompanyId) {
		this.weixinCompanyId = weixinCompanyId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public TEshopCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
	}

}
