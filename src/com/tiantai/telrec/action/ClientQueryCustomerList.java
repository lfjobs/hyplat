package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bo.TelrecCustomerInfo;

public class ClientQueryCustomerList extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6244129724552838869L;
	private String name;
	private String telphone;
	private String mobile;
	private String type;
	private String company;
	private String address;
	private String fax;
	private String email;
	private String hometel;
	private String birthday;
	private String position;
	private String postcode;

	private com.tiantai.telrec.service.ClientQueryCustomerService customerService;

	public com.tiantai.telrec.service.ClientQueryCustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(
			com.tiantai.telrec.service.ClientQueryCustomerService customerService) {
		this.customerService = customerService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHometel() {
		return hometel;
	}

	public void setHometel(String hometel) {
		this.hometel = hometel;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@SuppressWarnings("rawtypes")
	public String execute() throws Exception {
		/* Map map = this.getQueryMap(); */
		com.tiantai.telrec.bo.TelrecCustomerInfo info = this.setCustomerInfo();
		List list = this.getCustomerService().getCustomerInfoList(info);
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONArray array = JSONArray.fromObject(list);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

	private TelrecCustomerInfo setCustomerInfo() {
		TelrecCustomerInfo info = new TelrecCustomerInfo();

		info.setCustomer_address(null == this.address
				|| this.address.equals("") ? null : this.address);
		info.setCustomer_birthday(null == this.birthday
				|| this.birthday.equals("") ? null : this.birthday);
		info
				.setCustomer_email(null == this.email || this.email.equals("") ? null
						: this.email);
		info.setCustomer_fax(null == this.fax || this.fax.equals("") ? null
				: this.fax);
		info.setCustomer_hometel(null == this.hometel
				|| this.hometel.equals("") ? null : this.hometel);
		info
				.setCustomer_mobile(null == this.mobile
						|| this.mobile.equals("") ? null : this.mobile);
		info.setCustomer_name(null == this.name || this.name.equals("") ? null
				: this.name);
		info
				.setCustomer_tel(null == this.telphone
						|| this.telphone.equals("") ? null : this.telphone);
		info.setCustomer_title(null == this.position
				|| this.position.equals("") ? null : this.position);
		info
				.setCustomer_unit(null == this.company
						|| this.company.equals("") ? null : this.company);
		return info;
	}

	/*
	 * private Map getQueryMap() { Map map = new HashMap(); try { if
	 * (!this.birthday.equals("")) { map.put("customer_birthday", "%" +
	 * this.birthday + "%"); } if (!this.company.equals("")) {
	 * map.put("customer_unit", "%" + this.company + "%"); } if
	 * (!this.address.equals("")) { map.put("customer_address", "%" +
	 * this.address + "%"); } if (!this.email.equals("")) {
	 * map.put("customer_email", "%" + this.email + "%"); } if
	 * (!this.fax.equals("")) { map.put("suctomer_fax", "%" + this.fax + "%"); }
	 * if (!this.hometel.equals("")) { map.put("customer_hometel", "%" +
	 * this.hometel + "%"); } if (!this.mobile.equals("")) {
	 * map.put("customer_mobile", "%" + this.mobile + "%"); } if
	 * (!this.name.equals("")) { map.put("customer_name", "%" + this.name +
	 * "%"); } if (!this.position.equals("")) { map.put("customer_title", "%" +
	 * this.position + "%"); } if (!this.postcode.equals("")) {
	 * map.put("customer_postcode", "%" + this.postcode + "%"); } if
	 * (!this.type.equals("")) { map.put("customer_type", "%" + this.type +
	 * "%"); } } catch (Exception e) { logger.error("操作异常", e); } return map; }
	 */

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
