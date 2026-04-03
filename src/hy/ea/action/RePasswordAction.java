package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.util.Utilities;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import hy.plat.service.impl.ServerServiceImpl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class RePasswordAction {
	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	
	private String companyIdentifier; //机构名称
	private Company company;
	private Staff staff;
	private String validateCode;
	private String accountEmail; //用户名
	private String companyEmail; //公司邮件
	private String companyID;
	private String staffID;
	
	private String message="";
	private String result;
	/**
	 * 页面判断组织机构是否存在
	 * @return
	 */
	public String isCompanyIdentifier(){
		String hql="from Company where companyIdentifier=? and companyStatus='00'";
	    Company company= (Company) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyIdentifier});
		if (company==null){	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("log", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}
		if (company!=null) {
			companyID = company.getCompanyID();
	    	Map<String, Object> map = new HashMap<String, Object>();
			map.put("log", 2);
			map.put("companyID", companyID);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}
		return "success";
	}

	/**
	 * 页面判断用户名是否存在
	 * @return
	 */
	public String isAccountEmail(){
		String hql1="from CAccount where accountEmail=? and companyID=?";
		CAccount cAccount = (CAccount)baseBeanService.getBeanByHqlAndParams(hql1,
				new Object[]{accountEmail,companyID});
		if(cAccount==null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("log", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}
		if(cAccount!=null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("log", 2);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}
		return "success";
	}
	
	/**
	 * 页面判断邮箱地址是否存在
	 * @return
	 */
	public String isCompanyEmail(){
		String hql = "from CDetail where companyEmail=? and companyID=?";
		CDetail cDetail =(CDetail)baseBeanService.getBeanByHqlAndParams(hql,
				new Object[]{companyEmail,companyID});
		if(cDetail==null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("log", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}
		if(cDetail!=null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("log", 2);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		}
		return "success";
	}
	
	
	
	/**
	 * 机构名、用户名、验证码
	 * @return
	 */
	public String retrieve() {
		//判断密码是否为空
		Object oValidateCode = ActionContext.getContext().getSession().get(
		"invalidImge");
		if (null == oValidateCode) {
			return "failed";
		}
		
		// 后台处理验证码的问题
		String validateCodeFromSession = (String) oValidateCode;
		if (!validateCode.equalsIgnoreCase(validateCodeFromSession)) {
			return "failed";
		}
		
		//每天最多修改密码3次
		if(ServerServiceImpl.getCgpasslist().get(companyIdentifier)!=null&&ServerServiceImpl.getCgpasslist().get(companyIdentifier)==3){
		    message="操作已失败3次！请明天再来!";
			return "fail";
		}
		
		//判断组织机构名是否存在
	    String hql="from Company where companyIdentifier=? and companyStatus='00'";
	    Company company= (Company) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyIdentifier});
		
	    if (company!=null) {
	    	companyID=company.getCompanyID();
		}if (company==null){
			message="组织机构名不存在!";
			serverService.getCBooleanByCompanyIdentifier(companyIdentifier);
			return "failed";
		}
	    
	  //判断用户名是否存在
		String hql1="from CAccount where accountEmail=? and companyID=?";
		CAccount cAccount = (CAccount)baseBeanService.getBeanByHqlAndParams(hql1,
				new Object[]{accountEmail,companyID});
		if(cAccount!=null){
			staffID = cAccount.getStaffID();
		}if(cAccount==null){
			message="用户名不正确!";
			serverService.getCBooleanByCompanyIdentifier(companyIdentifier);
			return "failed";
		}
		
		return "success";
	}
	
	/**
	 * 邮箱验证
	 * @return
	 */
	public String repass(){
		
		//判断密码是否为空
		Object oValidateCode = ActionContext.getContext().getSession().get(
		"invalidImge");
		if (null == oValidateCode) {
			return "false";
		}
		
		// 后台处理验证码的问题
		String validateCodeFromSession = (String) oValidateCode;
		if (!validateCode.equalsIgnoreCase(validateCodeFromSession)) {
			return "false";
		}
		//每天最多修改密码3次
		if(ServerServiceImpl.getCgpasslist().get(companyIdentifier)!=null&&ServerServiceImpl.getCgpasslist().get(companyIdentifier)==3){
			message="操作已失败3次！请明天再来!";
			return "fail";
		}
		//判断邮箱地址是否存在
		String hql = "from CDetail where companyEmail=? and companyID=?";
		CDetail cDetail =(CDetail)baseBeanService.getBeanByHqlAndParams(hql,
				new Object[]{companyEmail,companyID});
		if(cDetail==null){
			serverService.getCBooleanByCompanyIdentifier(companyIdentifier);
			message="邮箱地址不正确!";
			return "false";
		}
		
		//修改密码
		String hql2="update CAccount set accountPassword =? where accountEmail =? and companyID =?";
		Object [] params = {Utilities.MD5("123456"),accountEmail,companyID};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql2}, params);

		//将新密码发到邮箱里
		if(Utilities.getReport("密码找回","你的组织机构名是："+companyIdentifier+".  你的用户名："+accountEmail+". 你的密码：123456",companyEmail)){
			return "succ";
		}else{
			message="邮件发送失败!";
			return "false";
		}
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String logOut() {
		return "failed";
	}
	public static Map<String, HttpSession> getSessionMap() {
		return sessionMap;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
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


	public String getAccountEmail() {
		return accountEmail;
	}


	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}


	public String getCompanyID() {
		return companyID;
	}


	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}


	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
}
