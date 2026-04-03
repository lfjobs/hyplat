package com.wechat.bo.sft;

/**
 * 订单金额信息  是
 */
public class AmountH5 {

    /**
     * 符合ISO 4217标准的三位字母代码，人民币：CNY 。
     * 示例值：CNY
     */
    private String currency;//是

    private int total;//服务商h5支付

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
