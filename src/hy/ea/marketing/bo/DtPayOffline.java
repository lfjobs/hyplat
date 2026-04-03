package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

/**
 * DtCrmCustomer entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtPayOffline implements java.io.Serializable, BaseBean {

    // Fields
    private String payOfflineKey;
    private String payOfflineId;
    private String staffId;// Staffid
    private String companyId;// companyid
    private String paymentType;// 线下支付方式
    private String paymentName;// 线下支付名称
    private String params;//线下支付配置参数

    // Constructors

    /**
     * default constructor
     */
    public DtPayOffline() {
    }

    /**
     * minimal constructor
     */
    public DtPayOffline(String staffId, String companyId) {
        this.staffId = staffId;
        this.companyId = companyId;
    }

    /**
     * full constructor
     */
    public DtPayOffline(String payOfflineKey, String payOfflineId, String staffId, String companyId, String paymentType, String params) {
        this.payOfflineKey = payOfflineKey;
        this.payOfflineId = payOfflineId;
        this.staffId = staffId;
        this.companyId = companyId;
        this.paymentType = paymentType;
        this.params = params;
    }
// Property accessors

    public String getPayOfflineKey() {
        return payOfflineKey;
    }

    public void setPayOfflineKey(String payOfflineKey) {
        this.payOfflineKey = payOfflineKey;
    }

    public String getPayOfflineId() {
        return payOfflineId;
    }

    public void setPayOfflineId(String payOfflineId) {
        this.payOfflineId = payOfflineId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}