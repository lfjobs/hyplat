package com.wechat.bo.sft;

/**
 * 订单金额信息  是
 */
public class Amount {
    private String key;
    /**
     * 子单金额，单位为分
     境外场景下，标价金额要超过商户结算币种的最小单位金额，例如结算币种为美元，则标价金额必须大于1美分
     示例值：100
     */
    private int  total_amount;  //是

    /**
     * 符合ISO 4217标准的三位字母代码，人民币：CNY 。
     示例值：CNY
     */
    private String currency;//是

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
