package hy.ea.bo.finance.brokerage;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2018-12-19.
 */
public class PActivityBrokerage implements BaseBean {
    private String activitybroKey;
    private String activitybroId;//活动价佣金id
    private Double brokerage;//代理佣金
    private String ppid;//产品id
    private String typePpid;//代理类别id
    private String companyId;//公司id
    private String actPriceId;//活动价id
    private String state;//状态[00:正常 01:删除]

    public String getActivitybroKey() {
        return activitybroKey;
    }

    public void setActivitybroKey(String activitybroKey) {
        this.activitybroKey = activitybroKey;
    }

    public String getActivitybroId() {
        return activitybroId;
    }

    public void setActivitybroId(String activitybroId) {
        this.activitybroId = activitybroId;
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

    public String getActPriceId() {
        return actPriceId;
    }

    public void setActPriceId(String actPriceId) {
        this.actPriceId = actPriceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PActivityBrokerage{" +
                "activitybroKey='" + activitybroKey + '\'' +
                ", activitybroId='" + activitybroId + '\'' +
                ", brokerage=" + brokerage +
                ", ppid='" + ppid + '\'' +
                ", typePpid='" + typePpid + '\'' +
                ", companyId='" + companyId + '\'' +
                ", actPriceId='" + actPriceId + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
