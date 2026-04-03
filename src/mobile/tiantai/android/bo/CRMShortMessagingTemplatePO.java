package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

/**
 * CRMShortMessagingTemplatePO
 *
 * @author zch
 */
public class CRMShortMessagingTemplatePO implements BaseBean {
    private String id;
    private String customerKey;
    private String templateType;
    private String templateHeadline;
    private String templateText;
    private String createdAt;
    private String updatedAt;
    private Integer auditStatus;
    private String auditCreatedAT;
    private String auditPassAT;
    private String serviceProvider;
    private String serviceProviderToken;
    private Integer freezeStatus;
    private String freezeReason;
    private String money;

    public CRMShortMessagingTemplatePO() {
    }

    public CRMShortMessagingTemplatePO(String id, String customerKey, String templateType, String templateHeadline, String templateText, String createdAt, String updatedAt, Integer auditStatus, String auditCreatedAT, String auditPassAT, String serviceProvider, String serviceProviderToken, Integer freezeStatus, String freezeReason, String money) {
        this.id = id;
        this.customerKey = customerKey;
        this.templateType = templateType;
        this.templateHeadline = templateHeadline;
        this.templateText = templateText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.auditStatus = auditStatus;
        this.auditCreatedAT = auditCreatedAT;
        this.auditPassAT = auditPassAT;
        this.serviceProvider = serviceProvider;
        this.serviceProviderToken = serviceProviderToken;
        this.freezeStatus = freezeStatus;
        this.freezeReason = freezeReason;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String  getTemplateHeadline() {
        return templateHeadline;
    }

    public void setTemplateHeadline(String templateHeadline) {
        this.templateHeadline = templateHeadline;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditCreatedAT() {
        return auditCreatedAT;
    }

    public void setAuditCreatedAT(String auditCreatedAT) {
        this.auditCreatedAT = auditCreatedAT;
    }

    public String getAuditPassAT() {
        return auditPassAT;
    }

    public void setAuditPassAT(String auditPassAT) {
        this.auditPassAT = auditPassAT;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getServiceProviderToken() {
        return serviceProviderToken;
    }

    public void setServiceProviderToken(String serviceProviderToken) {
        this.serviceProviderToken = serviceProviderToken;
    }

    public Integer getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(Integer freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    public String getFreezeReason() {
        return freezeReason;
    }

    public void setFreezeReason(String freezeReason) {
        this.freezeReason = freezeReason;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
