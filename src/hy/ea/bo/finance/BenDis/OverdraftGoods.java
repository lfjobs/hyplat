package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.util.Objects;

public class OverdraftGoods implements BaseBean {
    private String ogkey;
    private String ogid;
    private String overdraftid;
    private String goodsid;
    private String goodname;
    private String unit;
    private String quantity;
    private String unitprice;
    private String totalprices;
    private String goodstype;
    private String ppid;

    public String getOgkey() {
        return ogkey;
    }

    public void setOgkey(String ogkey) {
        this.ogkey = ogkey;
    }

    public String getOgid() {
        return ogid;
    }

    public void setOgid(String ogid) {
        this.ogid = ogid;
    }

    public String getOverdraftid() {
        return overdraftid;
    }

    public void setOverdraftid(String overdraftid) {
        this.overdraftid = overdraftid;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
        OverdraftGoods that = (OverdraftGoods) o;
        return Objects.equals(ogkey, that.ogkey) &&
                Objects.equals(ogid, that.ogid) &&
                Objects.equals(overdraftid, that.overdraftid) &&
                Objects.equals(goodsid, that.goodsid) &&
                Objects.equals(goodname, that.goodname) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(unitprice, that.unitprice) &&
                Objects.equals(totalprices, that.totalprices) &&
                Objects.equals(goodstype, that.goodstype) &&
                Objects.equals(ppid, that.ppid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ogkey, ogid, overdraftid, goodsid, goodname, unit, quantity, unitprice, totalprices, goodstype, ppid);
    }
}
