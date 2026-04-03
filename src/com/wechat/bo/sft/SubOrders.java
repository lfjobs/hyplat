package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *-子单信息
 * 最多支持子单条数：50  是
 */
public class SubOrders implements  java.io.Serializable,BaseBean {
    private String sokey;
    private String soId;

    private String mchid;  //子单发起方商户号，必须与发起方appid有绑定关系。示例值：1900000109

    private String attach;//是  附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。示例值：深圳分店

    private String  out_trade_no;//  是 子单商户订单号 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。示例值：20150806125346

    private String sub_mchid;// 是  二级商户商户号，由微信支付生成并下发。服务商子商户的商户号，被合单方。直连商户不用传二级商户号。注意：仅适用于电商平台 服务商示例值：1900000109

   private String description;  // 是 商品简单描述。需传入应用市场上的APP名字-实际商品名称，例如：天天爱消除-游戏充值。示例值：腾讯充值中心-QQ会员充值

    private Amount amount;  //订单金额信息

    private String  money;

    private SettleInfo settle_info;  //否	结算信息


    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
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

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public SettleInfo getSettle_info() {
        return settle_info;
    }

    public void setSettle_info(SettleInfo settle_info) {
        this.settle_info = settle_info;
    }

    public String getSokey() {
        return sokey;
    }

    public void setSokey(String sokey) {
        this.sokey = sokey;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
