package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Objects;

public class DeliverGoods implements BaseBean,Serializable{
    /**
	 * 拣货单产品
	 */
	private static final long serialVersionUID = 1L;
	private String deliverykey;
    private String deliveryid;
    private String orderid;//拣货单id
    private String goodsid;//物品id
    private String goodname;//产品名称
    private String unit;//
    private String ordernum;//订单数量
    private String quantity;//实际拣货数量
    private String error;//误差
    private String unitprice;//单价
    private String totalprices;//总金额
    private String goodstype;
    private String ppid;

    public String getDeliverykey() {
        return deliverykey;
    }

    public void setDeliverykey(String deliverykey) {
        this.deliverykey = deliverykey;
    }

    public String getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(String deliveryid) {
        this.deliveryid = deliveryid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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
        DeliverGoods that = (DeliverGoods) o;
        return Objects.equals(deliverykey, that.deliverykey) &&
                Objects.equals(deliveryid, that.deliveryid) &&
                Objects.equals(orderid, that.orderid) &&
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
        return Objects.hash(deliverykey, deliveryid, orderid, goodsid, goodname, unit, ordernum, quantity, error, unitprice, totalprices, goodstype, ppid);
    }
}
