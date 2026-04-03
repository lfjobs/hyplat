package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

/**
 * 物品附属表
 */
public class GoodsBarcode implements BaseBean,java.io.Serializable {
    private String barcodekey;
    private String barcodeid;
    private String goodsid; //物品id
    private String barcode; //条码
    private String spcation; //规格
    private String quantity; //数量
    private String variable1Id; //单位
    private String status; //状态 00：默认 01:不默认
    private String companyID;//公司ID

    public String getBarcodekey() {
        return barcodekey;
    }

    public void setBarcodekey(String barcodekey) {
        this.barcodekey = barcodekey;
    }

    public String getBarcodeid() {
        return barcodeid;
    }

    public void setBarcodeid(String barcodeid) {
        this.barcodeid = barcodeid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSpcation() {
        return spcation;
    }

    public void setSpcation(String spcation) {
        this.spcation = spcation;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getVariable1Id() {
        return variable1Id;
    }

    public void setVariable1Id(String variable1Id) {
        this.variable1Id = variable1Id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsBarcode that = (GoodsBarcode) o;

        if (barcodekey != null ? !barcodekey.equals(that.barcodekey) : that.barcodekey != null) return false;
        if (barcodeid != null ? !barcodeid.equals(that.barcodeid) : that.barcodeid != null) return false;
        if (goodsid != null ? !goodsid.equals(that.goodsid) : that.goodsid != null) return false;
        if (barcode != null ? !barcode.equals(that.barcode) : that.barcode != null) return false;
        if (spcation != null ? !spcation.equals(that.spcation) : that.spcation != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (variable1Id != null ? !variable1Id.equals(that.variable1Id) : that.variable1Id != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = barcodekey != null ? barcodekey.hashCode() : 0;
        result = 31 * result + (barcodeid != null ? barcodeid.hashCode() : 0);
        result = 31 * result + (goodsid != null ? goodsid.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (spcation != null ? spcation.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (variable1Id != null ? variable1Id.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }


}
