package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * 记录是否回调过
 * @author mz
 *
 */
public class PayCallRecord implements BaseBean{
	
    private String pckey;
    private String pcId;
	private String journalNum;//商户订单号

	public String getPckey() {
		return pckey;
	}

	public void setPckey(String pckey) {
		this.pckey = pckey;
	}

	public String getPcId() {
		return pcId;
	}

	public void setPcId(String pcId) {
		this.pcId = pcId;
	}


	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
}
