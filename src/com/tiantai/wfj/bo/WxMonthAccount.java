package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 *
 * 微信商户号系统记录月账户
 */
public class WxMonthAccount implements BaseBean {

	private String  wxaKey;
	private String  wxaId;
	private String  companyID;//公司ID
	private String  sr;//总收入多少
	private String  zc;//总支出多少
	private String  ye;//余额多少
	private String month;//年月份


	public String getWxaKey() {
		return wxaKey;
	}

	public void setWxaKey(String wxaKey) {
		this.wxaKey = wxaKey;
	}

	public String getWxaId() {
		return wxaId;
	}

	public void setWxaId(String wxaId) {
		this.wxaId = wxaId;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}

	public String getZc() {
		return zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	public String getYe() {
		return ye;
	}

	public void setYe(String ye) {
		this.ye = ye;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
