package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class WebQueryCustomerById extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5386497520613531266L;
	private String id;

	private com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie;

	public void setQueryCustomerServie(
			com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie) {
		this.queryCustomerServie = queryCustomerServie;
	}

	@SuppressWarnings("unused")
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Object obj = this.queryCustomerServie.getCustomerInfoForId(id);
		try {
			JSONObject jsonArray = JSONObject.fromObject(obj);
			String outString = jsonArray.toString();
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(outString);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public com.tiantai.telrec.service.ClientQueryCustomerService getQueryCustomerServie() {
		return queryCustomerServie;
	}

}
