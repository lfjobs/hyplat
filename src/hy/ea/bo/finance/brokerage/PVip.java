package hy.ea.bo.finance.brokerage;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * Created by Administrator on 2018-12-11.
 */
public class PVip implements BaseBean {
    private String vipKey;
    private String vipId;//vipid
    private Double vip;//vip价
    private Double factory;//成本价
    private Double brokerage;//业务佣金
    private Double brokerages;//代理佣金和
    private String investType;//设备投资类型[01:教练车 02:创客单车 03:超市 00:无]
    private String principal;//设置vip价责任人
    private Date addTimes;//设置[添加]时间
    private Date updateTimes;//修改时间
    private String ppid;//产品id
    private String companyId;//公司id
    private String state;// 状态[00:正常/审核通过 01:删除 02:草稿 03:审核中 04:驳回]

    public String getVipKey() {
        return vipKey;
    }

    public void setVipKey(String vipKey) {
        this.vipKey = vipKey;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public Double getVip() {
        return vip;
    }

    public void setVip(Double vip) {
        this.vip = vip;
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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getAddTimes() {
        return addTimes;
    }

    public void setAddTimes(Date addTimes) {
        this.addTimes = addTimes;
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

    public Date getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(Date updateTimes) {
        this.updateTimes = updateTimes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PVip{" +
                "vipKey='" + vipKey + '\'' +
                ", vipId='" + vipId + '\'' +
                ", vip=" + vip +
                ", factory=" + factory +
                ", brokerage=" + brokerage +
                ", brokerages=" + brokerages +
                ", investType='" + investType + '\'' +
                ", principal='" + principal + '\'' +
                ", addTimes=" + addTimes +
                ", updateTimes=" + updateTimes +
                ", ppid='" + ppid + '\'' +
                ", companyId='" + companyId + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
