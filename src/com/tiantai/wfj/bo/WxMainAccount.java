package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 *
 * 微信商户号系统记录总账户
 */
public class WxMainAccount implements BaseBean {

	private String  wxmKey;
	private String  wxmId;
	private String  companyID;//公司ID
	private String  sr;//总收入多少
	private String  zc;//总支出多少
	private String  ye;//余额多少

	private String rate;//默认0.006

	public String getWxmKey() {
		return wxmKey;
	}

	public void setWxmKey(String wxmKey) {
		this.wxmKey = wxmKey;
	}

	public String getWxmId() {
		return wxmId;
	}

	public void setWxmId(String wxmId) {
		this.wxmId = wxmId;
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
