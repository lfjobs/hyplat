package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 账号自动扣款服务权限
 */
public class CusComAuth implements BaseBean,java.io.Serializable {

	private String cakey;
	private String caId;
	private String sccId;
	private String account;//登录账号
	private String coinfee;//金币积分是否自动扣费 0不自动扣费 1 自动扣费
	private String wxfee;//微信是否自动扣费0不自动扣费 1 自动扣费
	private String staffid;
    private Date coinDate;//金币积分自动扣费开通时间或者不开通（解除时间）
    private Date wxDate;//微信是自动扣费开通时间
	private String source;//开通来源 0 货柜
	private String ktip;//开通提示  默认提示0或者null 1不提示了

	public String getCakey() {
		return cakey;
	}

	public void setCakey(String cakey) {
		this.cakey = cakey;
	}

	public String getCaId() {
		return caId;
	}

	public void setCaId(String caId) {
		this.caId = caId;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCoinfee() {
		return coinfee;
	}

	public void setCoinfee(String coinfee) {
		this.coinfee = coinfee;
	}

	public String getWxfee() {
		return wxfee;
	}

	public void setWxfee(String wxfee) {
		this.wxfee = wxfee;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public Date getCoinDate() {
		return coinDate;
	}

	public void setCoinDate(Date coinDate) {
		this.coinDate = coinDate;
	}

	public Date getWxDate() {
		return wxDate;
	}

	public void setWxDate(Date wxDate) {
		this.wxDate = wxDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getKtip() {
		return ktip;
	}

	public void setKtip(String ktip) {
		this.ktip = ktip;
	}
}