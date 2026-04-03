package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 电子秤预置键信息
 * @author Administrator
 *
 */
public class PresetKey implements BaseBean {

	private String prKey;
	private String prId;
	private String scpId;//
	private String keyNo;//键号
    private String rowx;//行
    private String columns;//列
    private String title;//名称
	private Integer plu;// 商品号
	private String companyID;

	public String getPrKey() {
		return prKey;
	}

	public void setPrKey(String prKey) {
		this.prKey = prKey;
	}

	public String getPrId() {
		return prId;
	}

	public void setPrId(String prId) {
		this.prId = prId;
	}

	public String getScpId() {
		return scpId;
	}

	public void setScpId(String scpId) {
		this.scpId = scpId;
	}

	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}

	public String getRowx() {
		return rowx;
	}

	public void setRowx(String rowx) {
		this.rowx = rowx;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPlu() {
		return plu;
	}

	public void setPlu(Integer plu) {
		this.plu = plu;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
}
