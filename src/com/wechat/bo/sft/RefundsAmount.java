package com.wechat.bo.sft;

/**
 * 订单金额信息
 */
public class RefundsAmount {

    private int refund; //退款金额  退款金额，币种的最小单位，只能为整数，不能超过原订单支付金额。

    private int total;//  原订单金额 原支付交易的订单总金额，币种的最小单位，只能为整数。
    /**
     *
     * 符合ISO 4217标准的三位字母代码，目前只支持人民币：CNY。
     示例值：CNY  退款币种
     */
    private String currency;

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
