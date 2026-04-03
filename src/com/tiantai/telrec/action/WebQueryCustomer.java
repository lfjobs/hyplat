package com.tiantai.telrec.action;

import hy.ea.bo.CAccount;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class WebQueryCustomer extends ActionSupport {
	private com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie;
	private int pageNum;
	private int recNum;

	public com.tiantai.telrec.service.ClientQueryCustomerService getQueryCustomerServie() {
		return queryCustomerServie;
	}

	public void setQueryCustomerServie(
			com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie) {
		this.queryCustomerServie = queryCustomerServie;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getRecNum() {
		return recNum;
	}

	public void setRecNum(int recNum) {
		this.recNum = recNum;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public String execute() {
		List list = new ArrayList();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyid = account.getCompanyID();
		//System.out.println(companyid);
		list = queryCustomerServie.queryCustomerForSplit(pageNum, recNum,
				companyid);
		ServletActionContext.getRequest().setAttribute("list", list);
		return this.SUCCESS;
	}
}
