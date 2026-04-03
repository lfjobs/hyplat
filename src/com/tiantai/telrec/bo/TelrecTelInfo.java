package com.tiantai.telrec.bo;

import java.util.Date;

public class TelrecTelInfo {
	private String id;
	private String user_id;
	private String user_name;
	private String customer_id;
	private String customer_name;
	private Date start_time;
	private Date end_time;
	private String content;
	private String telno;
	private String in_or_out;
	private String path;
	private String teltype;// 通话类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	

	public String getIn_or_out() {
		return in_or_out;
	}

	public void setIn_or_out(String in_or_out) {
		this.in_or_out = in_or_out;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getTeltype() {
		return teltype;
	}

	public void setTeltype(String teltype) {
		this.teltype = teltype;
	}

}
