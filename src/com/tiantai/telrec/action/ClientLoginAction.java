package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.util.Utilities;
import hy.plat.service.BaseBeanService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class ClientLoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8347820811624624873L;
	/**
	 * 
	 */
	@Resource
	private BaseBeanService baseBeanService;
	private String orgsimple;
	private String username;
	private String password;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute() {
		HttpServletResponse response = ServletActionContext.getResponse();
		String pwd = Utilities.MD5(password);
		String hql = "";
		hql = "from CAccount where accountEmail = ? and accountPassword = ? and accountStatus = ?";
		CAccount account = (CAccount) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { username, pwd,"00" });
		Map map = new HashMap();
		if (account != null) {
			hql = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { account.getCompanyID() });
	        hql = "from Staff where staffID = ?";
	        Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getStaffID()});
	        map.put("company", company);
			map.put("account", account);
			map.put("staff", staff);
		}

		try {

			JSONObject json = JSONObject.fromObject(map);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return null;

	}

	public String getOrgsimple() {
		return orgsimple;
	}

	public void setOrgsimple(String orgsimple) {
		this.orgsimple = orgsimple;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
