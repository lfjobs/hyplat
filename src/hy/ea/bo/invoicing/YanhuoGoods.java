package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 入库单物品表
 */
public class YanhuoGoods implements BaseBean,Serializable {
    private String yhgoodskey;
    private String yhgoodsid;
    private String yhid;//单据id
    private String goodsid;//物品id
    private String goodname;//物品名字
    private String unit;//单位
    private String isqualify;//验货合格数量/重量
    private String goodstype;//
    private String ppid;//产品id
    private String ccompanyID;          //往来单位ID
    private String ccompanyName;        //往来单位name
    private String contactUserID;// 往来个人ID
    private String ctUserName;//往来个人name
    private Date manufactureDate;//生产日期
    private String codeid; //保质期id
    private String codename; //保质期值

    public String getYhgoodskey() {
        return yhgoodskey;
    }

    public void setYhgoodskey(String yhgoodskey) {
        this.yhgoodskey = yhgoodskey;
    }

    public String getYhgoodsid() {
        return yhgoodsid;
    }

    public void setYhgoodsid(String yhgoodsid) {
        this.yhgoodsid = yhgoodsid;
    }

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid;
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

    public String getIsqualify() {
        return isqualify;
    }

    public void setIsqualify(String isqualify) {
        this.isqualify = isqualify;
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

    public YanhuoGoods(String yhgoodsid, String yhid, String goodsid, String goodname, String unit, String isqualify, String goodstype, String ppid, String ccompanyID, String ccompanyName, String contactUserID, String ctUserName, Date manufactureDate, String codeid, String codename) {
        this.yhgoodsid = yhgoodsid;
        this.yhid = yhid;
        this.goodsid = goodsid;
        this.goodname = goodname;
        this.unit = unit;
        this.isqualify = isqualify;
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

    public YanhuoGoods() {
    }
}
