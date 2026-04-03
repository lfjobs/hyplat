package hy.ea.office.dao.impl;

import hy.ea.bo.office.SignManager;

import java.util.List;

public class FlexiGridJSon {
	
	public List<SignManager> rows;
	public int total;
	public int page;
	public List<SignManager> getRows() {
		return rows;
	}
	public void setRows(List<SignManager> rows) {
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
