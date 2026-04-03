package hy.ea.bo.finance.percentage;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018-11-7.
 */
public class BPercentage implements BaseBean {
    private String brokerageKey;
    private String brokerageId;
    private Double sbaz;//设备安装佣金百分比
    private Double tp;//贴牌佣金百分比
    private Double cjdl;//村级代理佣金百分比
    private Double sjdl;//省级代理佣金百分比
    private Double sbtz;//设备投资佣金百分比
    private Double xjdl;//县级代理佣金百分比
    private Double khjf;//客户积分佣金百分比
    private Double ywyj;//业务佣金百分比
    private String principal;//设置佣金百分比责任人
    private Date times;//设置时间
    private String companyId;//公司id

    public String getBrokerageKey() {
        return brokerageKey;
    }

    public void setBrokerageKey(String brokerageKey) {
        this.brokerageKey = brokerageKey;
    }

    public String getBrokerageId() {
        return brokerageId;
    }

    public void setBrokerageId(String brokerageId) {
        this.brokerageId = brokerageId;
    }

    public Double getSbaz() {
        return sbaz;
    }

    public void setSbaz(Double sbaz) {
        this.sbaz = sbaz;
    }

    public Double getTp() {
        return tp;
    }

    public void setTp(Double tp) {
        this.tp = tp;
    }

    public Double getCjdl() {
        return cjdl;
    }

    public void setCjdl(Double cjdl) {
        this.cjdl = cjdl;
    }

    public Double getSjdl() {
        return sjdl;
    }

    public void setSjdl(Double sjdl) {
        this.sjdl = sjdl;
    }

    public Double getSbtz() {
        return sbtz;
    }

    public void setSbtz(Double sbtz) {
        this.sbtz = sbtz;
    }

    public Double getXjdl() {
        return xjdl;
    }

    public void setXjdl(Double xjdl) {
        this.xjdl = xjdl;
    }

    public Double getKhjf() {
        return khjf;
    }

    public void setKhjf(Double khjf) {
        this.khjf = khjf;
    }

    public Double getYwyj() {
        return ywyj;
    }

    public void setYwyj(Double ywyj) {
        this.ywyj = ywyj;
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

    public BPercentage() {
    }

    public BPercentage(String brokerageKey, String brokerageId, Double sbaz, Double tp, Double cjdl, Double sjdl, Double sbtz, Double xjdl, Double khjf, Double ywyj, String principal, Date times, String companyId) {
        this.brokerageKey = brokerageKey;
        this.brokerageId = brokerageId;
        this.sbaz = sbaz;
        this.tp = tp;
        this.cjdl = cjdl;
        this.sjdl = sjdl;
        this.sbtz = sbtz;
        this.xjdl = xjdl;
        this.khjf = khjf;
        this.ywyj = ywyj;
        this.principal = principal;
        this.times = times;
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "BPercentage{" +
                "brokerageKey='" + brokerageKey + '\'' +
                ", brokerageId='" + brokerageId + '\'' +
                ", sbaz=" + sbaz +
                ", tp=" + tp +
                ", cjdl=" + cjdl +
                ", sjdl=" + sjdl +
                ", sbtz=" + sbtz +
                ", xjdl=" + xjdl +
                ", khjf=" + khjf +
                ", ywyj=" + ywyj +
                ", principal='" + principal + '\'' +
                ", times=" + times +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
