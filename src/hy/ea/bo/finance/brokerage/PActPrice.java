package hy.ea.bo.finance.brokerage;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018-12-19.
 */
public class PActPrice implements BaseBean {
    private String actPriceKey;
    private String actPriceId;//活动价id
    private Double actPrice;//活动价
    private Double factory;//成本价
    private Double brokerage;//业务佣金
    private Double brokerages;//代理佣金和
    private String investType;//设备投资类型[01:教练车 02:创客单车 03:超市 00:无]
    private String ppid;//产品id
    private String companyId;//公司id
    private String activityId;//活动id
    private String state;//状态[00:正常 01:删除]
    private Integer amount;//原数量
    private Integer quantity;//现数量

    //非数据库字段
    private Double sbtz;//设备投资佣金
    private Double tp;//贴牌佣金
    private Double sbaz;//设备安装佣金
    private Double sjdl;//省级代理佣金
    private Double xjdl;//县级代理佣金
    private Double cjdl;//村级代理佣金
    private Double khjf;//客户积分佣金

    private String sbtzId;//设备投资佣金代理id
    private String tpId;//贴牌佣金代理id
    private String sbazId;//设备安装佣金代理id
    private String sjdlId;//省级代理佣金代理id
    private String xjdlId;//县级代理佣金代理id
    private String cjdlId;//村级代理佣金代理id
    private String khjfId;//客户积分佣金代理id

    public String getActPriceKey() {
        return actPriceKey;
    }

    public void setActPriceKey(String actPriceKey) {
        this.actPriceKey = actPriceKey;
    }

    public String getActPriceId() {
        return actPriceId;
    }

    public void setActPriceId(String actPriceId) {
        this.actPriceId = actPriceId;
    }

    public Double getActPrice() {
        return actPrice;
    }

    public void setActPrice(Double actPrice) {
        this.actPrice = actPrice;
    }

    public Double getFactory() {
        return factory;
    }

    public void setFactory(Double factory) {
        this.factory = factory;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
    }

    public Double getBrokerages() {
        return brokerages;
    }

    public void setBrokerages(Double brokerages) {
        this.brokerages = brokerages;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSbtz() {
        return sbtz;
    }

    public void setSbtz(Double sbtz) {
        this.sbtz = sbtz;
    }

    public Double getTp() {
        return tp;
    }

    public void setTp(Double tp) {
        this.tp = tp;
    }

    public Double getSbaz() {
        return sbaz;
    }

    public void setSbaz(Double sbaz) {
        this.sbaz = sbaz;
    }

    public Double getSjdl() {
        return sjdl;
    }

    public void setSjdl(Double sjdl) {
        this.sjdl = sjdl;
    }

    public Double getXjdl() {
        return xjdl;
    }

    public void setXjdl(Double xjdl) {
        this.xjdl = xjdl;
    }

    public Double getCjdl() {
        return cjdl;
    }

    public void setCjdl(Double cjdl) {
        this.cjdl = cjdl;
    }

    public Double getKhjf() {
        return khjf;
    }

    public void setKhjf(Double khjf) {
        this.khjf = khjf;
    }

    public String getSbtzId() {
        return sbtzId;
    }

    public void setSbtzId(String sbtzId) {
        this.sbtzId = sbtzId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getSbazId() {
        return sbazId;
    }

    public void setSbazId(String sbazId) {
        this.sbazId = sbazId;
    }

    public String getSjdlId() {
        return sjdlId;
    }

    public void setSjdlId(String sjdlId) {
        this.sjdlId = sjdlId;
    }

    public String getXjdlId() {
        return xjdlId;
    }

    public void setXjdlId(String xjdlId) {
        this.xjdlId = xjdlId;
    }

    public String getCjdlId() {
        return cjdlId;
    }

    public void setCjdlId(String cjdlId) {
        this.cjdlId = cjdlId;
    }

    public String getKhjfId() {
        return khjfId;
    }

    public void setKhjfId(String khjfId) {
        this.khjfId = khjfId;
    }

    @Override
    public String toString() {
        return "PActPrice{" +
                "actPriceKey='" + actPriceKey + '\'' +
                ", actPriceId='" + actPriceId + '\'' +
                ", actPrice=" + actPrice +
                ", factory=" + factory +
                ", brokerage=" + brokerage +
                ", brokerages=" + brokerages +
                ", investType='" + investType + '\'' +
                ", ppid='" + ppid + '\'' +
                ", companyId='" + companyId + '\'' +
                ", activityId='" + activityId + '\'' +
                ", state='" + state + '\'' +
                ", amount=" + amount +
                ", quantity=" + quantity +
                ", sbtz=" + sbtz +
                ", tp=" + tp +
                ", sbaz=" + sbaz +
                ", sjdl=" + sjdl +
                ", xjdl=" + xjdl +
                ", cjdl=" + cjdl +
                ", khjf=" + khjf +
                ", sbtzId='" + sbtzId + '\'' +
                ", tpId='" + tpId + '\'' +
                ", sbazId='" + sbazId + '\'' +
                ", sjdlId='" + sjdlId + '\'' +
                ", xjdlId='" + xjdlId + '\'' +
                ", cjdlId='" + cjdlId + '\'' +
                ", khjfId='" + khjfId + '\'' +
                '}';
    }
}
