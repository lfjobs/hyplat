package hy.ea.bo.finance.brokerage;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * Created by Administrator on 2018-11-22.
 */
public class PWholesale implements BaseBean {
    private String wholesaleKey;
    private String wholesaleId;//批发价id
    private Double wholesale;//批发价
    private Double factory;//成本价
    private Double brokerage;//业务佣金
    private Double brokerages;//代理佣金和
    private String investType;//投资类型[ 01:教练车 02:创客单车 03:超市 00:无]
    private String principal;//设置批发价责任人
    private Date addTimes;//设置[添加]时间
    private Date updateTimes;//修改时间
    private String ppid;//产品id
    private String companyId;//公司id
    private String state;// 状态[00:正常/审核通过 01:删除 02:草稿 03:审核中 04:驳回]

    public String getWholesaleKey() {
        return wholesaleKey;
    }

    public void setWholesaleKey(String wholesaleKey) {
        this.wholesaleKey = wholesaleKey;
    }

    public String getWholesaleId() {
        return wholesaleId;
    }

    public void setWholesaleId(String wholesaleId) {
        this.wholesaleId = wholesaleId;
    }

    public Double getWholesale() {
        return wholesale;
    }

    public void setWholesale(Double wholesale) {
        this.wholesale = wholesale;
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


    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
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

    public PWholesale(String wholesaleKey, String wholesaleId, Double wholesale, Double factory, Double brokerage, Double brokerages, String investType, String principal, Date addTimes, Date updateTimes, String ppid, String companyId, String state) {
        this.wholesaleKey = wholesaleKey;
        this.wholesaleId = wholesaleId;
        this.wholesale = wholesale;
        this.factory = factory;
        this.brokerage = brokerage;
        this.brokerages = brokerages;
        this.investType = investType;
        this.principal = principal;
        this.addTimes = addTimes;
        this.updateTimes = updateTimes;
        this.ppid = ppid;
        this.companyId = companyId;
        this.state = state;
    }

    public PWholesale() {
    }

    @Override
    public String toString() {
        return "PWholesale{" +
                "wholesaleKey='" + wholesaleKey + '\'' +
                ", wholesaleId='" + wholesaleId + '\'' +
                ", wholesale=" + wholesale +
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
