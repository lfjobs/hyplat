package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 二级商户进件 ——-店铺信息 必填
 *
 * 请填写店铺信息
 */
public class SalesSceneInfo implements java.io.Serializable,BaseBean {
    private String ssKey;
    private String ssId;
    /**
     *  店铺名称 必填
     *  请填写店铺全称。
     *  示例值：爱烧烤
     *
     */
    private String store_name;
    /**
     *  店铺链接
     *  1、店铺二维码or店铺链接二选一必填。
     *  2、请填写店铺主页链接，需符合网站规范。
     *   示例值：http://www.qq.com
     *
     */
    private String store_url;
    /**
     * 店铺二维码
     *
     * 1、店铺二维码 or 店铺链接二选一必填。
     2、若为电商小程序，可上传店铺页面的小程序二维码。
     3、请填写通过图片上传接口预先上传图片生成好的MediaID，仅能上传1张图片 。
     示例值：jTpGmxUX3FBWVQ5NJTZvlKX_gdU4cRz7z5NxpnFuAxhBTEO1D8daLC-ehEuo0BJqRTvDujqhThn4ReFxikqJ5YW6zFQ
     *
     *
     *
     */
    private String store_qr_code;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_url() {
        return store_url;
    }

    public void setStore_url(String store_url) {
        this.store_url = store_url;
    }

    public String getStore_qr_code() {
        return store_qr_code;
    }

    public void setStore_qr_code(String store_qr_code) {
        this.store_qr_code = store_qr_code;
    }

    public String getSsKey() {
        return ssKey;
    }

    public void setSsKey(String ssKey) {
        this.ssKey = ssKey;
    }

    public String getSsId() {
        return ssId;
    }

    public void setSsId(String ssId) {
        this.ssId = ssId;
    }
}
