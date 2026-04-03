package com.wechat.bo.sft;

/**
 * 结算信息  否
 */
public class SettleInfo {

    /**
     *
     * 是否指定分账，枚举值
     true：是
     false：否
     示例值：true
     */

    private boolean profit_sharing;
    /**
     *
     * SettleInfo.profit_sharing为true时，该金额才生效。
     注意：单笔订单最高补差金额为5000元
     示例值：10   int64
     */
    private int   subsidy_amount;

    public boolean isProfit_sharing() {
        return profit_sharing;
    }

    public void setProfit_sharing(boolean profit_sharing) {
        this.profit_sharing = profit_sharing;
    }

    public int getSubsidy_amount() {
        return subsidy_amount;
    }

    public void setSubsidy_amount(int subsidy_amount) {
        this.subsidy_amount = subsidy_amount;
    }
}
