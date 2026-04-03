package com.wechat.bo.sft;

/**
 * -分账接收方列表  分账接收方列表，支持设置出资商户作为分账接收方，单次分账最多可有5个分账接收方
 */
public class Receivers {
    /**
     *
     * 分账接收方类型，枚举值：
     MERCHANT_ID：商户
     PERSONAL_OPENID：个人
     示例值：MERCHANT_ID
     */
    private String type;
    /**
     * 分账接收方账号：
     类型是MERCHANT_ID时，是商户号（mch_id或者sub_mch_id）
     类型是PERSONAL_OPENID时，是个人openid，openid获取方法
     示例值：1900000109
     *
     *
     */
    private String receiver_account;

    /**
     * 分账金额，单位为分，只能为整数，不能超过原订单支付金额及最大分账比例金额。
     示例值：190
     */
    private int amount;

    /**
     *
     * 分账的原因描述，分账账单中需要体现。
     示例值：分给商户1900000109
     */
    private String description;
    /**
     *
     * 可选项，在接收方类型为个人的时可选填，若有值，会检查与 receiver_name 是否实名匹配，不匹配会拒绝分账请求
     1、分账接收方类型是PERSONAL_OPENID时，是个人姓名的密文（选传，传则校验） 此字段的加密方法详见：敏感信息加密说明
     2、使用微信支付平台证书中的公钥
     3、使用RSAES-OAEP算法进行加密
     4、将请求中HTTP头部的Wechatpay-Serial设置为证书序列号
     示例值：hu89ohu89ohu89o
     *
     *
     *
     */
    private String receiver_name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiver_account() {
        return receiver_account;
    }

    public void setReceiver_account(String receiver_account) {
        this.receiver_account = receiver_account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }
}
