package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * 相关单据关联表
 * @author lou
 *
 */
public class RelatedBill implements BaseBean{
	private String rbID;
	private String rbKey;
	private String cashid;  //单据id
	private String cashfid;		//单据id外键
	private String billtype;	//外键单据类别   要是微分金  那么cashid就是用户账号 cashfid就是产品id
	
	public String getRbID() {
		return rbID;
	}
	public void setRbID(String rbID) {
		this.rbID = rbID;
	}
	public String getRbKey() {
		return rbKey;
	}
	public void setRbKey(String rbKey) {
		this.rbKey = rbKey;
	}
	public String getCashid() {
		return cashid;
	}
	public void setCashid(String cashid) {
		this.cashid = cashid;
	}
	public String getCashfid() {
		return cashfid;
	}
	public void setCashfid(String cashfid) {
		this.cashfid = cashfid;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}
	
	
}
