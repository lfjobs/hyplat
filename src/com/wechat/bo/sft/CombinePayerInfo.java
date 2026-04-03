package com.wechat.bo.sft;

/**
 * 	否	支付者信息
 */
public class CombinePayerInfo {
    /**
     *
     * 使用合单appid获取的对应用户openid。是用户在商户appid下的唯一标识。
     示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     */
   private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
