package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;
/**
 * 支付订单与第三方交易号关联表
 * @author mz
 *
 */
public class PayCashierBill implements BaseBean{
	
	private String pcbKey;
	private String pcbID;
	private String payJournalNum;//支付订单号
	private String oriJournalNum;//原订单号
	private String trade_no;//第三方交易号
	private String dtype;//佣金单子
	public String getPcbKey() {
		return pcbKey;
	}
	public void setPcbKey(String pcbKey) {
		this.pcbKey = pcbKey;
	}
	public String getPcbID() {
		return pcbID;
	}
	public void setPcbID(String pcbID) {
		this.pcbID = pcbID;
	}
	public String getPayJournalNum() {
		return payJournalNum;
	}
	public void setPayJournalNum(String payJournalNum) {
		this.payJournalNum = payJournalNum;
	}
	public String getOriJournalNum() {
		return oriJournalNum;
	}
	public void setOriJournalNum(String oriJournalNum) {
		this.oriJournalNum = oriJournalNum;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
}
