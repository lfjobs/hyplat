package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 记录货柜正在扫码的用户
 */
public class HgRelateUser implements BaseBean,java.io.Serializable {

	private String hrukey;
	private String hruId;
	private String sccId;
	private String hgCode;//货柜编号
    private Date scanDate;//扫码开门时间  关门后删除数据
	private String loginMode;//登陆方式scard phone scan

	private String doorState;//1 开门中。。。
	public String getHrukey() {
		return hrukey;
	}

	public void setHrukey(String hrukey) {
		this.hrukey = hrukey;
	}

	public String getHruId() {
		return hruId;
	}

	public void setHruId(String hruId) {
		this.hruId = hruId;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getHgCode() {
		return hgCode;
	}

	public void setHgCode(String hgCode) {
		this.hgCode = hgCode;
	}

	public Date getScanDate() {
		return scanDate;
	}

	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	public String getDoorState() {
		return doorState;
	}

	public void setDoorState(String doorState) {
		this.doorState = doorState;
	}
}