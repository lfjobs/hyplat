package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

/**
 * 凭证子表单据
 * 陈婷
 */
public class VoucherBills implements BaseBean{
	private String vouchersKey;        		//子表主键
	private String vouchersID;         		//子表业务主键
	private String voucherID;				//父表凭证主键
	private String subjectsID;				//科目ID
	private String subjectsName;			//科目名称
	private String cashierBillsID;			//单据号ID
	private String goodsID;					//物品ID
	private String reasonThing;				//摘要
	private String loan;					//借方金额
	private String forLoan;					//贷方金额
	private String goodsBillsID;			//物品票据id
	public String getVouchersKey() {
		return vouchersKey;
	}
	public void setVouchersKey(String vouchersKey) {
		this.vouchersKey = vouchersKey;
	}
	public String getVouchersID() {
		return vouchersID;
	}
	public void setVouchersID(String vouchersID) {
		this.vouchersID = vouchersID;
	}
	public String getVoucherID() {
		return voucherID;
	}
	public void setVoucherID(String voucherID) {
		this.voucherID = voucherID;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getSubjectsName() {
		return subjectsName;
	}
	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getReasonThing() {
		return reasonThing;
	}
	public void setReasonThing(String reasonThing) {
		this.reasonThing = reasonThing;
	}
	public String getLoan() {
		return loan;
	}
	public void setLoan(String loan) {
		this.loan = loan;
	}
	public String getForLoan() {
		return forLoan;
	}
	public void setForLoan(String forLoan) {
		this.forLoan = forLoan;
	}
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}
}
