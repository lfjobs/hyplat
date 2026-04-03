package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *  扣除积分金币后，仍不够或者逃单的
 * 未支付完记录
 */
public class UnPayRecord implements BaseBean,java.io.Serializable {

	private String uprkey;
	private String uprId;
	private String sccId;//欠款人
	private String journalNum;//订单号
	private String totalMoney;//总金额
	private String scoreNum;//积分扣除数
	private String coinNum;//金币扣除数
	private String remainNum;//剩余金额
    private Date createDate;//创建日期
	private String status;//状态 00 未支付完 01 支付完毕
    private Date finishDate;//全额付款完毕时间


	public String getUprkey() {
		return uprkey;
	}

	public void setUprkey(String uprkey) {
		this.uprkey = uprkey;
	}

	public String getUprId() {
		return uprId;
	}

	public void setUprId(String uprId) {
		this.uprId = uprId;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(String scoreNum) {
		this.scoreNum = scoreNum;
	}

	public String getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(String coinNum) {
		this.coinNum = coinNum;
	}

	public String getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(String remainNum) {
		this.remainNum = remainNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
}