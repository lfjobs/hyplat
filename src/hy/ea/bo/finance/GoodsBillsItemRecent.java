package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 物品单据管理扩展表：GoodsBillsItemRecent
 *
 * @author 陈红
 */
public class GoodsBillsItemRecent implements BaseBean, Cloneable, java.io.Serializable {
    private String id;
    private String key;
    private String barCode;//条码
    private String goodsbillsId;//条目id

    private String flag;//添加类型
    private Date createDate;
    private String staffId;
    private String staffName;

    private String goodsName;
    private String goodsId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getGoodsbillsId() {
        return goodsbillsId;
    }

    public void setGoodsbillsId(String goodsbillsId) {
        this.goodsbillsId = goodsbillsId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
