package com.wechat.bo.sft;

import java.util.List;

/**
 *
 * 合并支付请求参数
 */
public class PayParam {

    private String  combine_appid ;  //合单发起方的appid。示例值：wxd678efh567hg6787
    private String  combine_mchid;//合单发起方商户号。示例值：1900000109
    private String combine_out_trade_no;  //合单支付总订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一 。示例值：P20150806125346

    private SceneInfo  scene_info; //否	body支付场景信息描述

    private List<SubOrders> sub_orders;  //子单信息

    private CombinePayerInfo combine_payer_info;  //否	body支付者信息

    /***
     * 交易起始时间  14
     * 订单生成时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     示例值：2019-12-31T15:59:60+08:00
     */
    private String time_start;
    /**交易结束时间
     * 订单失效时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     示例值：2019-12-31T15:59:60+08:00
     *
     *
     *
     */
    private String time_expire;

    /**
     * 通知地址
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的URL，不能携带参数。
     格式: URL
     示例值：https://yourapp.com/notify
     */
    private String notify_url;

    public String getCombine_appid() {
        return combine_appid;
    }

    public void setCombine_appid(String combine_appid) {
        this.combine_appid = combine_appid;
    }

    public String getCombine_mchid() {
        return combine_mchid;
    }

    public void setCombine_mchid(String combine_mchid) {
        this.combine_mchid = combine_mchid;
    }

    public String getCombine_out_trade_no() {
        return combine_out_trade_no;
    }

    public void setCombine_out_trade_no(String combine_out_trade_no) {
        this.combine_out_trade_no = combine_out_trade_no;
    }

    public SceneInfo getScene_info() {
        return scene_info;
    }

    public void setScene_info(SceneInfo scene_info) {
        this.scene_info = scene_info;
    }

    public List<SubOrders> getSub_orders() {
        return sub_orders;
    }

    public void setSub_orders(List<SubOrders> sub_orders) {
        this.sub_orders = sub_orders;
    }

    public CombinePayerInfo getCombine_payer_info() {
        return combine_payer_info;
    }

    public void setCombine_payer_info(CombinePayerInfo combine_payer_info) {
        this.combine_payer_info = combine_payer_info;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
