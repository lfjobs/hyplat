package com.tiantai.telrec.action;

import hy.ea.bo.CAccount;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bo.TelrecCustomerInfo;

public class WebInsertCustomerInfo extends ActionSupport {
	private com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie;

	public void setQueryCustomerServie(
			com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie) {
		this.queryCustomerServie = queryCustomerServie;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -149675238209084881L;
	private TelrecCustomerInfo customerInfo;

	public TelrecCustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(TelrecCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String execute() {

		HttpServletRequest request = ServletActionContext.getRequest();

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyid = account.getCompanyID();
		this.customerInfo.setRela_companyid(companyid);
		this.customerInfo.setUser_id(account.getAccountID());
		boolean flag = this.queryCustomerServie.hasCustomer(customerInfo);
		if (flag) {
			try {
				this.queryCustomerServie.insertCustomerInfo(customerInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;

		} else {
			// 改客户已存在
			request.setAttribute("msg", "该用户已存在");
			return ERROR;
		}
	}
}
