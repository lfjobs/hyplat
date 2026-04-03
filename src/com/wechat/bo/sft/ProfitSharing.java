package com.wechat.bo.sft;

import java.util.List;

/**
 * 请求分账
 */
public class ProfitSharing {

     private String appid;//公众账号ID
     private String sub_mchid;//分账出资的电商平台二级商户，填写微信支付分配的商户号
     private String transaction_id;// 微信支付订单号。

    /**
     *商户分账单号
     *    商户系统内部的分账单号，在商户系统内部唯一（单次分账、多次分账、完结分账应使用不同的商户分账单号），
     *    同一分账单号多次请求等同一次。
     *
     *
     */
    private String out_order_no;
    /**
     *
     * 是否完成分账
     1、如果为true，该笔订单剩余未分账的金额会解冻回电商平台二级商户；
     2、如果为false，该笔订单剩余未分账的金额不会解冻回电商平台二级商户，可以对该笔订单再次进行分账。
     */
    private boolean finish;

    /**
     *
     *
     *  分账接收方列表，支持设置出资商户作为分账接收方，单次分账最多可有5个分账接收方
     */
    private List<Receivers> receivers;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSub_mchid() {
        return sub_mchid;
    }

    public void setSub_mchid(String sub_mchid) {
        this.sub_mchid = sub_mchid;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public List<Receivers> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Receivers> receivers) {
        this.receivers = receivers;
    }
}
