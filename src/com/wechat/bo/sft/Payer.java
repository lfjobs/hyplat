package com.wechat.bo.sft;

/**
 * 支付者
 */
public class Payer {

  private String sp_openid;
  private String sub_openid;

    public String getSp_openid() {
        return sp_openid;
    }

    public void setSp_openid(String sp_openid) {
        this.sp_openid = sp_openid;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }
}
