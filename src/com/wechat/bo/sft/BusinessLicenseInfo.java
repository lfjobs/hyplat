package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 二级商户进件——营业执照/登记证书信息  条件选填
 *
 * 1、主体为“小微/个人卖家”时，不填。
 2、主体为“个体工商户/企业”时，请上传营业执照。
 3、主体为“党政、机关及事业单位/其他组织”时，请上传登记证书。
 *
 */
public class BusinessLicenseInfo implements  java.io.Serializable,BaseBean {

    private  String bliKey;
    private String bliID;
    /**
     *证件扫描件 必填
     * 1、主体为“个体工商户/企业”时，请上传营业执照的证件图片。
     * 2、主体为“党政、机关及事业单位/其他组织”时，请上传登记证书的证件图片。
     * 3、可上传1张图片，请填写通过图片上传接口预先上传图片生成好的MediaID 。
     * 4、图片要求：
     （1）请上传证件的彩色扫描件或彩色数码拍摄件，黑白复印件需加盖公章（公章信息需完整） 。
     （2）不得添加无关水印（非微信支付商户申请用途的其他水印）。
     （3）需提供证件的正面拍摄件，完整、照面信息清晰可见。信息不清晰、扭曲、压缩变形、反光、不完整均不接受。
     （4）不接受二次剪裁、翻拍、PS的证件照片。
     示例值： 47ZC6GC-vnrbEny__Ie_An5-tCpqxucuxi-vByf3Gjm7KE53JXvGy9tqZm2XAUf-4KGprrKhpVBDIUv0OF4wFNIO4kqg05InE4d2I6_H7I4
     */
    private String business_license_copy;

    /**
     *证件注册号 必填
     *
     * 1、主体为“个体工商户/企业”时，请填写营业执照上的注册号/统一社会信用代码，须为15位数字或 18位数字|大写字母。
     *  2、主体为“党政、机关及事业单位/其他组织”时，请填写登记证书的证书编号。
     示例值：123456789012345678
     */
    private String business_license_number;

    /**
     *证件名称 必填
     */
    private String business_license_name;
    /**
     *经营范围
     */
    private String business_scope;
    /**
     *年审管理
     */
    private String annual;
    /**
     *发证单位
     */
    private String issueUnit;
    /**
     *审核人
     */
    private String auditor;
    /*商户名称  必填
    1、请填写营业执照/登记证书的商家名称，2~110个字符，支持括号 。
    2、个体工商户/党政、机关及事业单位，不能以“公司”结尾。
    3、个体工商户，若营业执照上商户名称为空或为“无”，请填写"个体户+经营者姓名"，如“个体户张三” 。
        示例值：腾讯科技有限公司
     */
    private String merchant_name;
    /**
     * 经营者/法定代表人姓名  必填
     * 请填写证件的经营者/法定代表人姓名
     示例值：张三
     */
    private String legal_person;
    /**注册地址
     * 条件选填
     * 主体为“党政、机关及事业单位/其他组织”时必填，请填写登记证书的注册地址。
     示例值：深圳南山区科苑路
     */
    private String company_address;
    /**
     * 营业期限
     * 条件选填
     *1、主体为“党政、机关及事业单位/其他组织”时必填，请填写证件有效期。
     2、若证件有效期为长期，请填写：长期。
     3、结束时间需大于开始时间。
     4、有效期必须大于60天，即结束时间距当前时间需超过60天。
     示例值：[\"2014-01-01\",\"长期\"]
     */
    private String business_time;

    public String getBusiness_license_copy() {
        return business_license_copy;
    }

    public void setBusiness_license_copy(String business_license_copy) {
        this.business_license_copy = business_license_copy;
    }

    public String getBusiness_license_number() {
        return business_license_number;
    }

    public void setBusiness_license_number(String business_license_number) {
        this.business_license_number = business_license_number;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getBusiness_time() {
        return business_time;
    }

    public void setBusiness_time(String business_time) {
        this.business_time = business_time;
    }

    public String getBliKey() {
        return bliKey;
    }

    public void setBliKey(String bliKey) {
        this.bliKey = bliKey;
    }

    public String getBliID() {
        return bliID;
    }

    public void setBliID(String bliID) {
        this.bliID = bliID;
    }

    public String getBusiness_license_name() {
        return business_license_name;
    }

    public void setBusiness_license_name(String business_license_name) {
        this.business_license_name = business_license_name;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public String getIssueUnit() {
        return issueUnit;
    }

    public void setIssueUnit(String issueUnit) {
        this.issueUnit = issueUnit;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
}
