package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Objects;

/**
 * Created by lyc on 2018-10-12.盘点
 */
public class InvCheckGoods implements BaseBean, java.io.Serializable {
    private String checkinvKey;           //主键
    private String checkinvId;            //库存id
    private String goodsID;                 //物品外键
    private String goodsType;               //物品类别
    private String goodsName;               //物品名称
    private String ppID;                    //产品id
    private String invenQuantity;           //库存数量
    private String realQuantity;            //实际数量(盘点数量)
    private String price;                   //单价
    private String invenOnline;             //库存上限
    private String invenUnderline;          //库存下限
    private String FBILLID;                 //盘库单据id
    private int error;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setFBILLID(String FBILLID) {
        this.FBILLID = FBILLID;
    }

    public String getFBILLID() {
        return FBILLID;
    }

    public String getCheckinvKey() {
        return checkinvKey;
    }

    public void setCheckinvKey(String checkinvKey) {
        this.checkinvKey = checkinvKey;
    }

    public String getCheckinvId() {
        return checkinvId;
    }

    public void setCheckinvId(String checkinvId) {
        this.checkinvId = checkinvId;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPpID() {
        return ppID;
    }

    public void setPpID(String ppID) {
        this.ppID = ppID;
    }

    public String getInvenQuantity() {
        return invenQuantity;
    }

    public void setInvenQuantity(String invenQuantity) {
        this.invenQuantity = invenQuantity;
    }

    public String getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(String realQuantity) {
        this.realQuantity = realQuantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInvenOnline() {
        return invenOnline;
    }

    public void setInvenOnline(String invenOnline) {
        this.invenOnline = invenOnline;
    }

    public String getInvenUnderline() {
        return invenUnderline;
    }

    public void setInvenUnderline(String invenUnderline) {
        this.invenUnderline = invenUnderline;
    }

    @Override
    public String toString() {
        return "\"InvCheckGoods\":{" +
                "\"checkinvKey \":\"" + checkinvKey + '\"' +
                ", \"checkinvId \":\"" + checkinvId + '\"' +
                ", \"goodsID \":\"" + goodsID + '\"' +
                ", \"goodsType \":\"" + goodsType + '\"' +
                ", \"goodsName \":\"" + goodsName + '\"' +
                ", \"ppID \":\"" + ppID + '\"' +
                ", \"invenQuantity \":\"" + invenQuantity + '\"' +
                ", \"realQuantity \":\"" + realQuantity + '\"' +
                ", \"price \":\"" + price + '\"' +
                ", \"invenOnline \":\"" + invenOnline + '\"' +
                ", \"invenUnderline \":\"" + invenUnderline + '\"' +
                ", \"FBILLID \":\"" + FBILLID + '\"' +
                ", \"error\":" + error +
                "},";
    }
}
