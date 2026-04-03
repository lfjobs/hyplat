package com.wechat.bo.sft;

/**
 * H5场景信息
 */
public class H5info {
    private String type;//场景类型  Wap：WAP网站应用； 必填

    private String app_name;//应用名称  否
    private String app_url;//网站URL  否

    private String bundle_id;//iOS平台BundleID  否
    private String package_name;//iAndroid平台PackageName  否

    private String wap_name;
    private String wap_url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getBundle_id() {
        return bundle_id;
    }

    public void setBundle_id(String bundle_id) {
        this.bundle_id = bundle_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getWap_name() {
        return wap_name;
    }

    public void setWap_name(String wap_name) {
        this.wap_name = wap_name;
    }

    public String getWap_url() {
        return wap_url;
    }

    public void setWap_url(String wap_url) {
        this.wap_url = wap_url;
    }
}
