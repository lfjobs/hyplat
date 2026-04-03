package hy.ea.bo.finance.brokerage;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2018-11-22.
 */
public class PVipBroHistory implements BaseBean {
    private String vipbroKey;
    private String vipbroId;//vip价佣金id
    private Double brokerage;//代理佣金
    private String ppid;//产品id
    private String typePpid;//代理类别id
    private String companyId;//公司id
    private String vipId;//vip价id

    public String getVipbroKey() {
        return vipbroKey;
    }

    public void setVipbroKey(String vipbroKey) {
        this.vipbroKey = vipbroKey;
    }

    public String getVipbroId() {
        return vipbroId;
    }

    public void setVipbroId(String vipbroId) {
        this.vipbroId = vipbroId;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getTypePpid() {
        return typePpid;
    }

    public void setTypePpid(String typePpid) {
        this.typePpid = typePpid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public PVipBroHistory(String vipbroKey, String vipbroId, Double brokerage, String ppid, String typePpid, String companyId, String vipId) {
        this.vipbroKey = vipbroKey;
        this.vipbroId = vipbroId;
        this.brokerage = brokerage;
        this.ppid = ppid;
        this.typePpid = typePpid;
        this.companyId = companyId;
        this.vipId = vipId;
    }

    public PVipBroHistory() {
    }

    @Override
    public String toString() {
        return "PVipBrokerage{" +
                "vipbroKey='" + vipbroKey + '\'' +
                ", vipbroId='" + vipbroId + '\'' +
                ", brokerage=" + brokerage +
                ", ppid='" + ppid + '\'' +
                ", typePpid='" + typePpid + '\'' +
                ", companyId='" + companyId + '\'' +
                ", vipId='" + vipId + '\'' +
                '}';
    }
}
