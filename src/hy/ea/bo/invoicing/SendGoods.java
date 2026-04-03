package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Objects;

public class SendGoods implements BaseBean,Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sendgoodkey;
    private String sendgoodid;
    private String sendid;
    private String goodsid;
    private String goodname;
    private String unit;
    private String ordernum;
    private String quantity;
    private String error;
    private String unitprice;
    private String totalprices;
    private String goodstype;
    private String ppid;

    public String getSendgoodkey() {
        return sendgoodkey;
    }

    public void setSendgoodkey(String sendgoodkey) {
        this.sendgoodkey = sendgoodkey;
    }

    public String getSendgoodid() {
        return sendgoodid;
    }

    public void setSendgoodid(String sendgoodid) {
        this.sendgoodid = sendgoodid;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getTotalprices() {
        return totalprices;
    }

    public void setTotalprices(String totalprices) {
        this.totalprices = totalprices;
    }

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendGoods sendGoods = (SendGoods) o;
        return Objects.equals(sendgoodkey, sendGoods.sendgoodkey) &&
                Objects.equals(sendgoodid, sendGoods.sendgoodid) &&
                Objects.equals(sendid, sendGoods.sendid) &&
                Objects.equals(goodsid, sendGoods.goodsid) &&
                Objects.equals(goodname, sendGoods.goodname) &&
                Objects.equals(unit, sendGoods.unit) &&
                Objects.equals(ordernum, sendGoods.ordernum) &&
                Objects.equals(quantity, sendGoods.quantity) &&
                Objects.equals(error, sendGoods.error) &&
                Objects.equals(unitprice, sendGoods.unitprice) &&
                Objects.equals(totalprices, sendGoods.totalprices) &&
                Objects.equals(goodstype, sendGoods.goodstype) &&
                Objects.equals(ppid, sendGoods.ppid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sendgoodkey, sendgoodid, sendid, goodsid, goodname, unit, ordernum, quantity, error, unitprice, totalprices, goodstype, ppid);
    }
}
