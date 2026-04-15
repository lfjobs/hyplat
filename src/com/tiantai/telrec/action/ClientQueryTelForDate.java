package com.tiantai.telrec.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

;

public class ClientQueryTelForDate extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8220189573638516758L;
	private String telType;
	private String date;
	private String user_id;
	private com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService;

	public com.tiantai.telrec.service.ClientInsertTelInfoService getTelInfoService() {
		return telInfoService;
	}

	public void setTelInfoService(
			com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService) {
		this.telInfoService = telInfoService;
	}

	public String getTelType() {
		return telType;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@SuppressWarnings("rawtypes")
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		List list = new ArrayList();
		list = this.getTelInfoService().queryTelrecForDate(user_id, date);
	//	System.out.println(list);
		JSONArray jsonArray = JSONArray.fromObject(list);
		String outString = jsonArray.toString();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(outString);
		response.flushBuffer();
		return null;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
