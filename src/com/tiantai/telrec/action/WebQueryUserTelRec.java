package com.tiantai.telrec.action;

import hy.ea.bo.CAccount;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WebQueryUserTelRec extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7925739381588349152L;
	private com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService;
	private String starttime;
	private String endtime;
	private String id;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public String execute() {

		starttime = starttime.length() != 0 ? starttime.substring(0, 10) : "";
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		List list = telInfoService.queryTelrecInfoForCompanyid(companyID,
				starttime);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().getAttribute("list");
		request.setAttribute("list", list);
		return this.SUCCESS;
	}

	public com.tiantai.telrec.service.ClientInsertTelInfoService getTelInfoService() {
		return telInfoService;
	}

	public void setTelInfoService(
			com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService) {
		this.telInfoService = telInfoService;
	}
};
