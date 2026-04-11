package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.service.ClientMissCalledService;


public class ClientMissCallShow extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8852216134116390387L;
	// 显示没有接打的电话
	private String startDate;
	private String endDate;
	private String userid;
	private String simpleorg;
	private String username;
	private String password;
	private ClientMissCalledService missedCalledService;

	public String getSimpleorg() {
		return simpleorg;
	}

	public void setSimpleorg(String simpleorg) {
		this.simpleorg = simpleorg;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@SuppressWarnings("rawtypes")
	public String execute() {
		HttpServletResponse response = ServletActionContext.getResponse();
	/*	Object obj = UserCheck.Instance().checkUser(simpleorg, username,
				password);
		if (null != obj) {*/
			List list = this.getMissedCalledService().getMissedCallForUserId(
					getStartDate(), getEndDate(), getUserid());
			ServletActionContext.getRequest().setAttribute("list", list);

			JSONArray jsonArray = JSONArray.fromObject(list);
			String outString = jsonArray.toString();
			response.setCharacterEncoding("UTf-8");
			try {
				response.getWriter().print(outString);
				response.flushBuffer();
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
	
	
		return null;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ClientMissCalledService getMissedCalledService() {
		return missedCalledService;
	}

	public void setMissedCalledService(
			ClientMissCalledService missedCalledService) {
		this.missedCalledService = missedCalledService;
	}
}
