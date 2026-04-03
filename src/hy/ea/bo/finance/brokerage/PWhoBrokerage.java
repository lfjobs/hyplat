package hy.ea.bo.finance.brokerage;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2018-11-22.
 */
public class PWhoBrokerage implements BaseBean {
    private String whobroKey;
    private String whobroId;//批发价佣金id
    private Double brokerage;//代理佣金
    private String ppid;//产品id
    private String typePpid;//代理类别id
    private String companyId;//公司id
    private String wholesaleId;//批发价id

    public String getWhobroKey() {
        return whobroKey;
    }

    public void setWhobroKey(String whobroKey) {
        this.whobroKey = whobroKey;
    }

    public String getWhobroId() {
        return whobroId;
    }

    public void setWhobroId(String whobroId) {
        this.whobroId = whobroId;
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

    public String getWholesaleId() {
        return wholesaleId;
    }

    public void setWholesaleId(String wholesaleId) {
        this.wholesaleId = wholesaleId;
    }

    public PWhoBrokerage(String whobroKey, String whobroId, Double brokerage, String ppid, String typePpid, String companyId, String wholesaleId) {
        this.whobroKey = whobroKey;
        this.whobroId = whobroId;
        this.brokerage = brokerage;
        this.ppid = ppid;
        this.typePpid = typePpid;
        this.companyId = companyId;
        this.wholesaleId = wholesaleId;
    }

    public PWhoBrokerage() {
    }

    @Override
    public String toString() {
        return "PWhoBrokerage{" +
                "whobroKey='" + whobroKey + '\'' +
                ", whobroId='" + whobroId + '\'' +
                ", brokerage=" + brokerage +
                ", ppid='" + ppid + '\'' +
                ", typePpid='" + typePpid + '\'' +
                ", companyId='" + companyId + '\'' +
                ", wholesaleId='" + wholesaleId + '\'' +
                '}';
    }
}
