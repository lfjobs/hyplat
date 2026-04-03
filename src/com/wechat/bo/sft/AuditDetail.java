package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 驳回原因详情
 *
 * 各项资料的审核情况。当申请状态为REJECTED或 FROZEN时才返回。
 */
public class AuditDetail implements  java.io.Serializable,BaseBean {
    private String adKey;
    private String adId;
    /**
     *
     * 提交申请单的资料项名称。
     示例值：id_card_copy
     */
   private String param_name;
    /**
     *
     * 	提交资料项被驳回原因。
     示例值：身份证背面识别失败，请上传更清晰的身份证图片
     */
   private String  reject_reason;

   private String arId;

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public String getReject_reason() {
        return reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
    }

    public String getAdKey() {
        return adKey;
    }

    public void setAdKey(String adKey) {
        this.adKey = adKey;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getArId() {
        return arId;
    }

    public void setArId(String arId) {
        this.arId = arId;
    }
}
