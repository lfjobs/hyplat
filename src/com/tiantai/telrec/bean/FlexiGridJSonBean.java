package com.tiantai.telrec.bean;

import java.util.List;

import com.tiantai.telrec.bo.TelrecCustomerInfo;

public class FlexiGridJSonBean {
	public List<TelrecCustomerInfo> rows;
	public int total;
	public int page;
	public List<TelrecCustomerInfo> getRows() {
		return rows;
	}
	public void setRows(List<TelrecCustomerInfo> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
}
