package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * 初始库存子表
 */
public class InitializeGoods  implements BaseBean, Serializable {
    private String initgoodskey;
    private String initgoodsid;
    private String initializeid;  //初始库存表id
    private String warehouse;  //库房id
    private String warehousename;  //库房名称
    private String goodsid;  //物品id
    private String goodname;  //物品名字
    private String unit;  //单位
    private String quantity;  //数量/重量
    private String codeid;  //商品分类id
    private String codename;  //商品分类名称
    private String ppid;  //产品id
    private String showweixin;  //是否在微商店显示 0否 01是
    private String staffid;  //责任人id
    private String staffname;  //责任人名字

    public InitializeGoods() {
    }

    public InitializeGoods(String initgoodsid, String initializeid, String warehouse, String warehousename, String goodsid, String goodname, String unit, String quantity, String codeid, String codename, String ppid, String showweixin) {
        this.initgoodsid = initgoodsid;
        this.initializeid = initializeid;
        this.warehouse = warehouse;
        this.warehousename = warehousename;
        this.goodsid = goodsid;
        this.goodname = goodname;
        this.unit = unit;
        this.quantity = quantity;
        this.codeid = codeid;
        this.codename = codename;
        this.ppid = ppid;
        this.showweixin = showweixin;
    }

    public InitializeGoods(String initgoodsid, String initializeid, String warehouse, String warehousename, String goodsid, String goodname, String unit, String quantity, String codeid, String codename, String ppid, String showweixin, String staffid, String staffname) {
        this.initgoodsid = initgoodsid;
        this.initializeid = initializeid;
        this.warehouse = warehouse;
        this.warehousename = warehousename;
        this.goodsid = goodsid;
        this.goodname = goodname;
        this.unit = unit;
        this.quantity = quantity;
        this.codeid = codeid;
        this.codename = codename;
        this.ppid = ppid;
        this.showweixin = showweixin;
        this.staffid = staffid;
        this.staffname = staffname;
    }

    public String getInitgoodskey() {
        return initgoodskey;
    }

    public void setInitgoodskey(String initgoodskey) {
        this.initgoodskey = initgoodskey;
    }

    public String getInitgoodsid() {
        return initgoodsid;
    }

    public void setInitgoodsid(String initgoodsid) {
        this.initgoodsid = initgoodsid;
    }

    public String getInitializeid() {
        return initializeid;
    }

    public void setInitializeid(String initializeid) {
        this.initializeid = initializeid;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
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

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getShowweixin() {
        return showweixin;
    }

    public void setShowweixin(String showweixin) {
        this.showweixin = showweixin;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitializeGoods that = (InitializeGoods) o;

        if (initgoodskey != null ? !initgoodskey.equals(that.initgoodskey) : that.initgoodskey != null) return false;
        if (initgoodsid != null ? !initgoodsid.equals(that.initgoodsid) : that.initgoodsid != null) return false;
        if (initializeid != null ? !initializeid.equals(that.initializeid) : that.initializeid != null) return false;
        if (warehouse != null ? !warehouse.equals(that.warehouse) : that.warehouse != null) return false;
        if (warehousename != null ? !warehousename.equals(that.warehousename) : that.warehousename != null)
            return false;
        if (goodsid != null ? !goodsid.equals(that.goodsid) : that.goodsid != null) return false;
        if (goodname != null ? !goodname.equals(that.goodname) : that.goodname != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (codeid != null ? !codeid.equals(that.codeid) : that.codeid != null) return false;
        if (codename != null ? !codename.equals(that.codename) : that.codename != null) return false;
        if (ppid != null ? !ppid.equals(that.ppid) : that.ppid != null) return false;
        if (showweixin != null ? !showweixin.equals(that.showweixin) : that.showweixin != null) return false;
        if (staffid != null ? !staffid.equals(that.staffid) : that.staffid != null) return false;
        if (staffname != null ? !staffname.equals(that.staffname) : that.staffname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = initgoodskey != null ? initgoodskey.hashCode() : 0;
        result = 31 * result + (initgoodsid != null ? initgoodsid.hashCode() : 0);
        result = 31 * result + (initializeid != null ? initializeid.hashCode() : 0);
        result = 31 * result + (warehouse != null ? warehouse.hashCode() : 0);
        result = 31 * result + (warehousename != null ? warehousename.hashCode() : 0);
        result = 31 * result + (goodsid != null ? goodsid.hashCode() : 0);
        result = 31 * result + (goodname != null ? goodname.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (codeid != null ? codeid.hashCode() : 0);
        result = 31 * result + (codename != null ? codename.hashCode() : 0);
        result = 31 * result + (ppid != null ? ppid.hashCode() : 0);
        result = 31 * result + (showweixin != null ? showweixin.hashCode() : 0);
        result = 31 * result + (staffid != null ? staffid.hashCode() : 0);
        result = 31 * result + (staffname != null ? staffname.hashCode() : 0);
        return result;
    }
}
