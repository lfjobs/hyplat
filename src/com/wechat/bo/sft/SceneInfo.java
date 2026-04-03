package com.wechat.bo.sft;

/**
 * 支付场景信息描述  否
 */
public class SceneInfo {
     private String device_id;  //终端设备号（门店号或收银设备ID） 。示例值：POS1:1  否
     private String payer_client_ip; //用户端实际ip格式: ip(ipv4+ipv6) 示例值：14.17.22.32 是

    private H5info h5_info;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPayer_client_ip() {
        return payer_client_ip;
    }

    public void setPayer_client_ip(String payer_client_ip) {
        this.payer_client_ip = payer_client_ip;
    }

    public H5info getH5_info() {
        return h5_info;
    }

    public void setH5_info(H5info h5_info) {
        this.h5_info = h5_info;
    }
}
