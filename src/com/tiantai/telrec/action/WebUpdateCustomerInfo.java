package com.tiantai.telrec.action;

import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bo.TelrecCustomerInfo;

public class WebUpdateCustomerInfo extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5329731366971690294L;
	private TelrecCustomerInfo customerInfo;
	private com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie;

	public com.tiantai.telrec.service.ClientQueryCustomerService getQueryCustomerServie() {
		return queryCustomerServie;
	}

	public void setQueryCustomerServie(
			com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie) {
		this.queryCustomerServie = queryCustomerServie;
	}

	public TelrecCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(TelrecCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String execute() {
		TelrecCustomerInfo c = (TelrecCustomerInfo) this.queryCustomerServie
				.getCustomerInfoForId(this.customerInfo.getId());
		c.setCustomer_address(customerInfo.getCustomer_address());
		try {
			this.queryCustomerServie.updateCustomer(customerInfo);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}

	}
}
