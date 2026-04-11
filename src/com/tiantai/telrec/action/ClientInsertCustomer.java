package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bo.TelrecCustomerInfo;

public class ClientInsertCustomer extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4909530700996231293L;

	private com.tiantai.telrec.service.ClientInsertCustomerService insertCustomerService;
	private String customer_name;
	private String customer_tel;
	private String customer_mobile;
	private String customer_type;
	private String customer_unit;
	private String customer_address;
	private String customer_fax;
	private String customer_email;
	private String customer_hometel;
	private String customer_birthday;
	private String customer_title;
	private String customer_postcode;
	private String customer_memo;
	private String customer_companyid;

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_tel() {
		return customer_tel;
	}

	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}

	public String getCustomer_mobile() {
		return customer_mobile;
	}

	public void setCustomer_mobile(String customer_mobile) {
		this.customer_mobile = customer_mobile;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getCustomer_unit() {
		return customer_unit;
	}

	public void setCustomer_unit(String customer_unit) {
		this.customer_unit = customer_unit;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_fax() {
		return customer_fax;
	}

	public void setCustomer_fax(String customer_fax) {
		this.customer_fax = customer_fax;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public String getCustomer_hometel() {
		return customer_hometel;
	}

	public void setCustomer_hometel(String customer_hometel) {
		this.customer_hometel = customer_hometel;
	}

	public String getCustomer_birthday() {
		return customer_birthday;
	}

	public void setCustomer_birthday(String customer_birthday) {
		this.customer_birthday = customer_birthday;
	}

	public String getCustomer_title() {
		return customer_title;
	}

	public void setCustomer_title(String customer_title) {
		this.customer_title = customer_title;
	}

	public String getCustomer_postcode() {
		return customer_postcode;
	}

	public void setCustomer_postcode(String customer_postcode) {
		this.customer_postcode = customer_postcode;
	}

	public String getCustomer_memo() {
		return customer_memo;
	}

	public void setCustomer_memo(String customer_memo) {
		this.customer_memo = customer_memo;
	}

	public String getCustomer_companyid() {
		return customer_companyid;
	}

	public void setCustomer_companyid(String customer_companyid) {
		this.customer_companyid = customer_companyid;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String execute() throws Exception {
		//logger.info("ClientInsertCustomer do");
		HttpServletResponse response = ServletActionContext.getResponse();
		TelrecCustomerInfo info = this.setCustomerInfo();
		String id = null;
		id = this.insertCustomerService.insertCustomer(info);
		response.setCharacterEncoding("UTF-8");
		if (null == id)
			id = "error";
		response.getWriter().write(id);
		response.flushBuffer();
		return null;
	}

	private TelrecCustomerInfo setCustomerInfo() {
		TelrecCustomerInfo info = new TelrecCustomerInfo();
		info.setCustomer_address(this.customer_address);
		info.setCustomer_birthday(this.customer_birthday);
		info.setCustomer_companyid(this.customer_companyid);
		info.setCustomer_email(this.customer_email);
		info.setCustomer_fax(this.customer_fax);
		info.setCustomer_hometel(this.customer_hometel);
		info.setCustomer_memo(this.customer_memo);
		info.setCustomer_mobile(this.customer_mobile);
		info.setCustomer_name(this.customer_name);
		info.setCustomer_tel(this.customer_tel);
		info.setCustomer_title(this.customer_title);
		info.setCustomer_unit(this.customer_unit);
		return info;
	}

	public com.tiantai.telrec.service.ClientInsertCustomerService getInsertCustomerService() {
		return insertCustomerService;
	}

	public void setInsertCustomerService(
			com.tiantai.telrec.service.ClientInsertCustomerService insertCustomerService) {
		this.insertCustomerService = insertCustomerService;
	}

}
