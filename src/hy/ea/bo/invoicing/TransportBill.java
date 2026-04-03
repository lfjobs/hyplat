package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TransportBill  implements BaseBean, Serializable {
    /**
	 * 送货单
	 */
	private static final long serialVersionUID = 1L;
	private String transportkey;
    private String transportid;//id
    private String journalnum;//订单编号
    private String transportnum;//送货单号
    private String companyid;//公司id
    private String purchaserid;//采购商id
    private String purchasername;//采购商名字
    private String pickingid;//送货人id
    private String pichingname;//送货人名字
    private String sellermessage;//买家留言
    private String cashierbillsid;//订单id
    private Date adddate;//添加时间
    private String status;//单据状态  01:未送货 02:已送货


    public String getTransportkey() {
        return transportkey;
    }

    public void setTransportkey(String transportkey) {
        this.transportkey = transportkey;
    }

    public String getTransportid() {
        return transportid;
    }

    public void setTransportid(String transportid) {
        this.transportid = transportid;
    }

    public String getJournalnum() {
        return journalnum;
    }

    public void setJournalnum(String journalnum) {
        this.journalnum = journalnum;
    }

    public String getTransportnum() {
        return transportnum;
    }

    public void setTransportnum(String transportnum) {
        this.transportnum = transportnum;
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
        TransportBill that = (TransportBill) o;
        return Objects.equals(transportkey, that.transportkey) &&
                Objects.equals(transportid, that.transportid) &&
                Objects.equals(journalnum, that.journalnum) &&
                Objects.equals(transportnum, that.transportnum) &&
                Objects.equals(companyid, that.companyid) &&
                Objects.equals(purchaserid, that.purchaserid) &&
                Objects.equals(purchasername, that.purchasername) &&
                Objects.equals(pickingid, that.pickingid) &&
                Objects.equals(pichingname, that.pichingname) &&
                Objects.equals(sellermessage, that.sellermessage) &&
                Objects.equals(cashierbillsid, that.cashierbillsid) &&
                Objects.equals(adddate, that.adddate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportkey, transportid, journalnum, transportnum, companyid, purchaserid, purchasername, pickingid, pichingname, sellermessage, cashierbillsid, adddate, status);
    }
}
