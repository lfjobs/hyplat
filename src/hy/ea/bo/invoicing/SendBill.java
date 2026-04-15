package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SendBill implements BaseBean,Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sendkey;
    private String sendid;
    private String journalnum;//订单编号
    private String sendnum;//发货单号
    private String companyid;//公司id
    private String purchaserid;//采购商id
    private String purchasername;//采购商名字
    private String pickingid;//发货人id
    private String pichingname;//发货人名字
    private String sellermessage;//买家留言
    private String cashierbillsid;//订单id
    private Date adddate;//添加时间
    private String status;//单据状态  01:未发货 02:已发货

    public String getSendkey() {
        return sendkey;
    }

    public void setSendkey(String sendkey) {
        this.sendkey = sendkey;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getJournalnum() {
        return journalnum;
    }

    public void setJournalnum(String journalnum) {
        this.journalnum = journalnum;
    }

    public String getSendnum() {
        return sendnum;
    }

    public void setSendnum(String sendnum) {
        this.sendnum = sendnum;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getPurchaserid() {
        return purchaserid;
    }

    public void setPurchaserid(String purchaserid) {
        this.purchaserid = purchaserid;
    }

    public String getPurchasername() {
        return purchasername;
    }

    public void setPurchasername(String purchasername) {
        this.purchasername = purchasername;
    }

    public String getPickingid() {
        return pickingid;
    }

    public void setPickingid(String pickingid) {
        this.pickingid = pickingid;
    }

    public String getPichingname() {
        return pichingname;
    }

    public void setPichingname(String pichingname) {
        this.pichingname = pichingname;
    }

    public String getSellermessage() {
        return sellermessage;
    }

    public void setSellermessage(String sellermessage) {
        this.sellermessage = sellermessage;
    }

    public String getCashierbillsid() {
        return cashierbillsid;
    }

    public void setCashierbillsid(String cashierbillsid) {
        this.cashierbillsid = cashierbillsid;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendBill sendBill = (SendBill) o;
        return Objects.equals(sendkey, sendBill.sendkey) &&
                Objects.equals(sendid, sendBill.sendid) &&
                Objects.equals(journalnum, sendBill.journalnum) &&
                Objects.equals(sendnum, sendBill.sendnum) &&
                Objects.equals(companyid, sendBill.companyid) &&
                Objects.equals(purchaserid, sendBill.purchaserid) &&
                Objects.equals(purchasername, sendBill.purchasername) &&
                Objects.equals(pickingid, sendBill.pickingid) &&
                Objects.equals(pichingname, sendBill.pichingname) &&
                Objects.equals(sellermessage, sendBill.sellermessage) &&
                Objects.equals(cashierbillsid, sendBill.cashierbillsid) &&
                Objects.equals(adddate, sendBill.adddate) &&
                Objects.equals(status, sendBill.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sendkey, sendid, journalnum, sendnum, companyid, purchaserid, purchasername, pickingid, pichingname, sellermessage, cashierbillsid, adddate, status);
    }
}
