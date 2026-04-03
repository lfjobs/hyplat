package com.wechat.bo.sft;

/**
 *
 * 合并支付请求参数
 */
public class ServicePayParam {

    private String  sp_appid ;//服务商申请的公众号或移动应用appid。
    private String  sp_mchid;//服务商户号，由微信支付生成并下发
    private String sub_appid;  //子商户申请的公众号或移动应用appid。否
    private String sub_mchid;  //子商户的商户号，由微信支付生成并下发。
    private String description;  //商品描述
    private String out_trade_no;  //商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。

    private String attach;  //附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用 否
    /**
     *
     * 通知URL必须为直接可访问的URL，不允许携带查询串。
     格式：URL
     示例值：https://www.weixin.qq.com/wxpay/pay.php
     */
    private String notify_url;


    private AmountH5 amount;

    private SceneInfo  scene_info; //否	body支付场景信息描述

    private Payer payer;

    public String getSp_appid() {
        return sp_appid;
    }

    public void setSp_appid(String sp_appid) {
        this.sp_appid = sp_appid;
    }

    public String getSp_mchid() {
        return sp_mchid;
    }

    public void setSp_mchid(String sp_mchid) {
        this.sp_mchid = sp_mchid;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSub_mchid() {
        return sub_mchid;
    }

    public void setSub_mchid(String sub_mchid) {
        this.sub_mchid = sub_mchid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }



    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public AmountH5 getAmount() {
        return amount;
    }

    public void setAmount(AmountH5 amount) {
        this.amount = amount;
    }

    public SceneInfo getScene_info() {
        return scene_info;
    }

    public void setScene_info(SceneInfo scene_info) {
        this.scene_info = scene_info;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }
}
