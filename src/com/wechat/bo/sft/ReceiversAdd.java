package com.wechat.bo.sft;

/**
 * 添加分账接收方API
 */
public class ReceiversAdd {

    private String appid;//微信分配的apppid（公众号APPID或者小程序APPID）
    private String type; //分账接收方的类型，枚举值：MERCHANT_ID：商户 PERSONAL_OPENID：个人
    private String account;  //分账接收方的账号类型是MERCHANT_ID时，是商户号 类型是PERSONAL_OPENID时，是个人openid
    private String name;//分账接收方的名称，当type为MERCHANT_ID时，接收方名称是商户全称。
    /**
     *
     *   1、分账接收方类型是PERSONAL_OPENID时，是个人姓名的密文（选传，传则校验）
     此字段的加密的方式为：
     2、使用微信支付平台证书中的公钥
     3、使用RSAES-OAEP算法进行加密
     4、将请求中HTTP头部的Wechatpay-Serial设置为证书序列号
     *
     *
     *
     */
    private String encrypted_name;

    /**
     *   子商户与接收方的关系。
         枚举值：
        SUPPLIER：供应商
        DISTRIBUTOR：分销商
        SERVICE_PROVIDER：服务商
        PLATFORM：平台
        OTHERS：其他
     *
     *
     */
    private String relation_type;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncrypted_name() {
        return encrypted_name;
    }

    public void setEncrypted_name(String encrypted_name) {
        this.encrypted_name = encrypted_name;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(String relation_type) {
        this.relation_type = relation_type;
    }
}
