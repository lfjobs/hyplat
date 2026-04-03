package com.tiantai.telrec.action;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ClientQueryCustomerForUpdate extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7466418865082883342L;
	private String customerId;
	private com.tiantai.telrec.service.ClientQueryCustomerService customerService;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public com.tiantai.telrec.service.ClientQueryCustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(
			com.tiantai.telrec.service.ClientQueryCustomerService customerService) {
		this.customerService = customerService;
	}

	public String execute() throws Exception {
		HttpServletResponse rsp = ServletActionContext.getResponse();
		Object obj = this.customerService.getCustomerInfoForId(this.customerId);
		JSONObject jsonObject = JSONObject.fromObject(obj);
		String outString = jsonObject.toString();
		rsp.setCharacterEncoding("UTF-8");
		rsp.getWriter().write(outString);
		rsp.flushBuffer();
		return null;
	}
}
