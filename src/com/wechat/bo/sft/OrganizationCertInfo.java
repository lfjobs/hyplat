package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 * 二级商户进件——组织机构代码证信息  条件选填
 *主体为“企业/党政、机关及事业单位/其他组织”，且营业执照/登记证书号码不是18位时必填。
 */
public  class OrganizationCertInfo implements  java.io.Serializable,BaseBean {
    private String ociKey;
    private String ociID;
    /**组织机构代码证照片  必填
     *
     * 可上传1张图片，请填写通过图片上传接口预先上传图片生成好的MediaID。
     示例值：vByf3Gjm7KE53JXv\prrKhpVBDIUv0OF4wFNIO4kqg05InE4d2I6_H7I4
     */
    private String organization_copy;
    /**
     * 组织机构代码 必填
     *
     *1、请填写组织机构代码证上的组织机构代码。
     2、可填写9或10位 数字|字母|连字符。
     示例值：12345679-A
     *
     *
     */
    private String organization_number;
    /**
     * 组织机构代码有效期限   必填
     *1、请填写组织机构代码证的有效期限，注意参照示例中的格式。
     2、若证件有效期为长期，请填写：长期。
     3、结束时间需大于开始时间。
     4、有效期必须大于60天，即结束时间距当前时间需超过60天。
     示例值：[\"2014-01-01\",\"长期\"]
     */
    private String organization_time;


    public String getOrganization_copy() {
        return organization_copy;
    }

    public void setOrganization_copy(String organization_copy) {
        this.organization_copy = organization_copy;
    }

    public String getOrganization_number() {
        return organization_number;
    }

    public void setOrganization_number(String organization_number) {
        this.organization_number = organization_number;
    }

    public String getOrganization_time() {
        return organization_time;
    }

    public void setOrganization_time(String organization_time) {
        this.organization_time = organization_time;
    }

    public String getOciKey() {
        return ociKey;
    }

    public void setOciKey(String ociKey) {
        this.ociKey = ociKey;
    }

    public String getOciID() {
        return ociID;
    }

    public void setOciID(String ociID) {
        this.ociID = ociID;
    }
}