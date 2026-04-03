package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Objects;

public class TransportGoods  implements BaseBean, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tgoodkey;
    private String tgoodid;
    private String transportid;
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

    public String getTgoodkey() {
        return tgoodkey;
    }

    public void setTgoodkey(String tgoodkey) {
        this.tgoodkey = tgoodkey;
    }

    public String getTgoodid() {
        return tgoodid;
    }

    public void setTgoodid(String tgoodid) {
        this.tgoodid = tgoodid;
    }

    public String getTransportid() {
        return transportid;
    }

    public void setTransportid(String transportid) {
        this.transportid = transportid;
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
        TransportGoods that = (TransportGoods) o;
        return Objects.equals(tgoodkey, that.tgoodkey) &&
                Objects.equals(tgoodid, that.tgoodid) &&
                Objects.equals(transportid, that.transportid) &&
                Objects.equals(goodsid, that.goodsid) &&
                Objects.equals(goodname, that.goodname) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(ordernum, that.ordernum) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(error, that.error) &&
                Objects.equals(unitprice, that.unitprice) &&
                Objects.equals(totalprices, that.totalprices) &&
                Objects.equals(goodstype, that.goodstype) &&
                Objects.equals(ppid, that.ppid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tgoodkey, tgoodid, transportid, goodsid, goodname, unit, ordernum, quantity, error, unitprice, totalprices, goodstype, ppid);
    }
}
