package mobile.tiantai.android.action.storeProduction.budgetSheet;

/**
 * TemplateDto
 *
 * @author zch
 */
public class TemplateDto {
    private String templateType;
    private String templateHeadline;
    private String templateText;
    private String customerKey;
    private String money;

    public TemplateDto() {
    }

    public TemplateDto(String templateType, String templateHeadline, String templateText, String customerKey, String money) {
        this.templateType = templateType;
        this.templateHeadline = templateHeadline;
        this.templateText = templateText;
        this.customerKey = customerKey;
        this.money = money;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateHeadline() {
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

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
