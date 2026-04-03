package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2019-12-23.
 */
public class Pricechange implements BaseBean,java.io.Serializable{
    private String pckey;
    private String pcid;
    private String comId;//公司id
    private String sqid;//购物记录数据id
    private String ppid;//物品id
    private String priceid;//原价id
    private String efPrice;//出厂价
    private String rePrice;//售价
    private String brokerage;//业务佣金
    private String proxySumPrice;//代理佣金和
    private String pricestatus;//价格种类 0：零售  1：批发价格 2：VIP  3. 普通活动 4.特价活动  5.变价记录表
    private Date sjdate;//添加时间
    private String staffid;//变价人id

    public String getPckey() {
        return pckey;
    }

    public void setPckey(String pckey) {
        this.pckey = pckey;
    }

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getSqid() {
        return sqid;
    }

    public void setSqid(String sqid) {
        this.sqid = sqid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getPriceid() {
        return priceid;
    }

    public void setPriceid(String priceid) {
        this.priceid = priceid;
    }

    public String getEfPrice() {
        return efPrice;
    }

    public void setEfPrice(String efPrice) {
        this.efPrice = efPrice;
    }

    public String getRePrice() {
        return rePrice;
    }

    public void setRePrice(String rePrice) {
        this.rePrice = rePrice;
    }

    public String getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(String brokerage) {
        this.brokerage = brokerage;
    }

    public String getProxySumPrice() {
        return proxySumPrice;
    }

    public void setProxySumPrice(String proxySumPrice) {
        this.proxySumPrice = proxySumPrice;
    }

    public String getPricestatus() {
        return pricestatus;
    }

    public void setPricestatus(String pricestatus) {
        this.pricestatus = pricestatus;
    }

    public Date getSjdate() {
        return sjdate;
    }

    public void setSjdate(Date sjdate) {
        this.sjdate = sjdate;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pricechange that = (Pricechange) o;

        if (pckey != null ? !pckey.equals(that.pckey) : that.pckey != null) return false;
        if (pcid != null ? !pcid.equals(that.pcid) : that.pcid != null) return false;
        if (comId != null ? !comId.equals(that.comId) : that.comId != null) return false;
        if (sqid != null ? !sqid.equals(that.sqid) : that.sqid != null) return false;
        if (ppid != null ? !ppid.equals(that.ppid) : that.ppid != null) return false;
        if (priceid != null ? !priceid.equals(that.priceid) : that.priceid != null) return false;
        if (efPrice != null ? !efPrice.equals(that.efPrice) : that.efPrice != null) return false;
        if (rePrice != null ? !rePrice.equals(that.rePrice) : that.rePrice != null) return false;
        if (brokerage != null ? !brokerage.equals(that.brokerage) : that.brokerage != null) return false;
        if (proxySumPrice != null ? !proxySumPrice.equals(that.proxySumPrice) : that.proxySumPrice != null)
            return false;
        if (pricestatus != null ? !pricestatus.equals(that.pricestatus) : that.pricestatus != null) return false;
        if (sjdate != null ? !sjdate.equals(that.sjdate) : that.sjdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pckey != null ? pckey.hashCode() : 0;
        result = 31 * result + (pcid != null ? pcid.hashCode() : 0);
        result = 31 * result + (comId != null ? comId.hashCode() : 0);
        result = 31 * result + (sqid != null ? sqid.hashCode() : 0);
        result = 31 * result + (ppid != null ? ppid.hashCode() : 0);
        result = 31 * result + (priceid != null ? priceid.hashCode() : 0);
        result = 31 * result + (efPrice != null ? efPrice.hashCode() : 0);
        result = 31 * result + (rePrice != null ? rePrice.hashCode() : 0);
        result = 31 * result + (brokerage != null ? brokerage.hashCode() : 0);
        result = 31 * result + (proxySumPrice != null ? proxySumPrice.hashCode() : 0);
        result = 31 * result + (pricestatus != null ? pricestatus.hashCode() : 0);
        result = 31 * result + (sjdate != null ? sjdate.hashCode() : 0);
        return result;
    }
}
