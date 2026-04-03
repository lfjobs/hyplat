package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货单物品表
 */
public class ShouhuoGoods implements BaseBean,Serializable {
    private String shgoodskey;
    private String shgoodsid;
    private String shid;//单据id
    private String goodsid;//物品id
    private String goodname;//物品名字
    private String unit;//单位
    private String requantity;//收货数量/重量
    private String goodstype;//
    private String ppid;//产品id
    private String ccompanyID;          //往来单位ID
    private String ccompanyName;        //往来单位name
    private String contactUserID;// 往来个人ID
    private String ctUserName;//往来个人name
    private Date manufactureDate;//生产日期
    private String codeid; //保质期id
    private String codename; //保质期值

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

    public String getRequantity() {
        return requantity;
    }

    public void setRequantity(String requantity) {
        this.requantity = requantity;
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

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getCcompanyName() {
        return ccompanyName;
    }

    public void setCcompanyName(String ccompanyName) {
        this.ccompanyName = ccompanyName;
    }

    public String getContactUserID() {
        return contactUserID;
    }

    public void setContactUserID(String contactUserID) {
        this.contactUserID = contactUserID;
    }

    public String getCtUserName() {
        return ctUserName;
    }

    public void setCtUserName(String ctUserName) {
        this.ctUserName = ctUserName;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
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

    public String getShgoodskey() {
        return shgoodskey;
    }

    public void setShgoodskey(String shgoodskey) {
        this.shgoodskey = shgoodskey;
    }

    public String getShgoodsid() {
        return shgoodsid;
    }

    public void setShgoodsid(String shgoodsid) {
        this.shgoodsid = shgoodsid;
    }

    public String getShid() {
        return shid;
    }

    public void setShid(String shid) {
        this.shid = shid;
    }

    public ShouhuoGoods(String shgoodsid, String shid, String goodsid, String goodname, String unit, String requantity, String goodstype, String ppid, String ccompanyID, String ccompanyName, String contactUserID, String ctUserName, Date manufactureDate, String codeid, String codename) {
        this.shgoodsid = shgoodsid;
        this.shid = shid;
        this.goodsid = goodsid;
        this.goodname = goodname;
        this.unit = unit;
        this.requantity = requantity;
        this.goodstype = goodstype;
        this.ppid = ppid;
        this.ccompanyID = ccompanyID;
        this.ccompanyName = ccompanyName;
        this.contactUserID = contactUserID;
        this.ctUserName = ctUserName;
        this.manufactureDate = manufactureDate;
        this.codeid = codeid;
        this.codename = codename;
    }

    public ShouhuoGoods() {
    }
}
