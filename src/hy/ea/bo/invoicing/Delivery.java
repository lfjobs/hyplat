package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Delivery implements BaseBean,Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderkey;
    private String orderid;//拣货出库id
    private String journalnum;//订单编号
    private String deliverynum;//拣货出库单号
    private String companyid;//公司id
    private String purchaserid;//采购商id
    private String purchasername;//采购商名字
    private String pickingid;//拣货人id
    private String pichingname;//拣货人名字
    private String sellermessage;//买家留言
    private String cashierbillsid;//订单id
    private Date adddate;//添加时间
    private String status;//单据状态  01:未拣货 02:已拣货

    public String getOrderkey() {
        return orderkey;
    }

    public void setOrderkey(String orderkey) {
        this.orderkey = orderkey;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getJournalnum() {
        return journalnum;
    }

    public void setJournalnum(String journalnum) {
        this.journalnum = journalnum;
    }

    public String getDeliverynum() {
        return deliverynum;
    }

    public void setDeliverynum(String deliverynum) {
        this.deliverynum = deliverynum;
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
        Delivery delivery = (Delivery) o;
        return Objects.equals(orderkey, delivery.orderkey) &&
                Objects.equals(orderid, delivery.orderid) &&
                Objects.equals(journalnum, delivery.journalnum) &&
                Objects.equals(deliverynum, delivery.deliverynum) &&
                Objects.equals(companyid, delivery.companyid) &&
                Objects.equals(purchaserid, delivery.purchaserid) &&
                Objects.equals(purchasername, delivery.purchasername) &&
                Objects.equals(pickingid, delivery.pickingid) &&
                Objects.equals(pichingname, delivery.pichingname) &&
                Objects.equals(sellermessage, delivery.sellermessage) &&
                Objects.equals(cashierbillsid, delivery.cashierbillsid) &&
                Objects.equals(adddate, delivery.adddate) &&
                Objects.equals(status, delivery.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderkey, orderid, journalnum, deliverynum, companyid, purchaserid, purchasername, pickingid, pichingname, sellermessage, cashierbillsid, adddate, status);
    }
}
