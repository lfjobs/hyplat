package com.wechat.bo.sft;

/**
 * 退款申请API
 */
public class Refunds {

    private  String sub_mchid;  //微信支付分配二级商户的商户号。示例值： 1900000109

    private String sp_appid; //电商平台APPID 电商平台在微信公众平台申请服务号对应的APPID，申请商户功能的时候微信支付会配置绑定关系。

    private String sub_appid; //二级商户APPID  级商户在微信申请公众号成功后分配的帐号ID，需要电商平台侧配置绑定关系才能传参（即二级商户已绑定微信公众号时传入）。

    private String transaction_id; //微信订单号

    private String   out_trade_no;  //商户订单号
    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@，同一退款单号多次请求只退一笔。
     *
     */
    private String out_refund_no; //商户退款单号

    /**否
     *  若商户传入，会在下发给用户的退款消息中体现退款原因。
     注意：若订单退款金额≤1元，且属于部分退款，则不会在退款消息中体现退款原因
     示例值：商品已售完
     *
     *
     */
   private String reason;

    /**
     *
     *退款结果回调url
     * 异步接收微信支付退款结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     * 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效，优先回调当前传的地址。
     */
   private String notify_url;



   private RefundsAmount amount;  //订单金额信息

    public String getSub_mchid() {
        return sub_mchid;
    }

    public void setSub_mchid(String sub_mchid) {
        this.sub_mchid = sub_mchid;
    }

    public String getSp_appid() {
        return sp_appid;
    }

    public void setSp_appid(String sp_appid) {
        this.sp_appid = sp_appid;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public RefundsAmount getAmount() {
        return amount;
    }

    public void setAmount(RefundsAmount amount) {
        this.amount = amount;
    }
}
