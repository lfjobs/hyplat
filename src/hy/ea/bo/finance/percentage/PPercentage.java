package hy.ea.bo.finance.percentage;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018-11-7.
 */
public class PPercentage implements BaseBean {
    private String percentageKey;
    private String percentageId;//价格百分比id
    private Double retail;//零售价百分比
    private Double activity;//活动价百分比
    private Double vip;//vip价百分比
    private Double wholesale;//批发价百分比
    private Double special;//特价百分比
    private String principal;//设置价格百分比责任人
    private Date times;//设置时间
    private String companyId;//公司id
    private String codeId;//行业[分类]id
    private String ppId;//产品id
    private String brokerageId;//代理佣金百分比id


    public String getPercentageKey() {
        return percentageKey;
    }

    public void setPercentageKey(String percentageKey) {
        this.percentageKey = percentageKey;
    }

    public String getPercentageId() {
        return percentageId;
    }

    public void setPercentageId(String percentageId) {
        this.percentageId = percentageId;
    }

    public Double getRetail() {
        return retail;
    }

    public void setRetail(Double retail) {
        this.retail = retail;
    }

    public Double getActivity() {
        return activity;
    }

    public void setActivity(Double activity) {
        this.activity = activity;
    }

    public Double getVip() {
        return vip;
    }

    public void setVip(Double vip) {
        this.vip = vip;
    }

    public Double getWholesale() {
        return wholesale;
    }

    public void setWholesale(Double wholesale) {
        this.wholesale = wholesale;
    }

    public Double getSpecial() {
        return special;
    }

    public void setSpecial(Double special) {
        this.special = special;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getTimes() {
        return times;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getBrokerageId() {
        return brokerageId;
    }

    public void setBrokerageId(String brokerageId) {
        this.brokerageId = brokerageId;
    }

    @Override
    public String toString() {
        return "PPercentage{" +
                "percentageKey='" + percentageKey + '\'' +
                ", percentageId='" + percentageId + '\'' +
                ", retail=" + retail +
                ", activity=" + activity +
                ", vip=" + vip +
                ", wholesale=" + wholesale +
                ", special=" + special +
                ", principal='" + principal + '\'' +
                ", times=" + times +
                ", companyId='" + companyId + '\'' +
                ", codeId='" + codeId + '\'' +
                ", ppId='" + ppId + '\'' +
                ", brokerageId='" + brokerageId + '\'' +
                '}';
    }
}
