package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 二级商户进件——超级管理员信息 必填
 *
 *  请填写店铺的超级管理员信息。
 *  超级管理员需在开户后进行签约，并可接收日常重要管理信息和进行资金操作，请确定其为商户法定代表人或负责人。
 *
 */
public class ContactInfo implements  java.io.Serializable,BaseBean {

    private String coKey;
    private String coId;

    /**
     *超级管理员类型  必填
     *  1、主体为“小微/个人卖家 ”，可选择：65-经营者/法人。
     * 2、主体为“个体工商户/企业/党政、机关及事业单位/其他组织”，可选择：65-经营者/法人、66- 负责人。 （负责人：经商户授权办理微信支付业务的人员，授权范围包括但不限于签约，入驻过程需完成账户验证）。
     * 示例值：65
     */
    private String contact_type;
    /**
     *  超级管理员姓名  必填
     *   1、若管理员类型为“法人”，则该姓名需与法人身份证姓名一致。
     * 2、若管理员类型为“经办人”，则可填写实际经办人的姓名。
     * 3、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
        （后续该管理员需使用实名微信号完成签约）
     *       示例值： pVd1HJ6zyvPedzGaV+X3IdGdbDnuC4Eelw/wDa4SzfeespQO/0kjiwfqdfg==
     *
     */
    private String contact_name;
    /**
     * 超级管理员身份证件号码  必填
     *   1、若管理员类型为法人，则该身份证号码需与法人身份证号码一致。若管理员类型为经办人，则可填写实际经办人的身份证号码。
     *  2、可传身份证、来往内地通行证、来往大陆通行证、护照等证件号码。
     *  3、超级管理员签约时，校验微信号绑定的银行卡实名信息，是否与该证件号码一致。
     *  4、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     *  示例值：pVd1HJ6zmty7/mYNxLMpRSvMRtelw/wDa4SzfeespQO/0kjiwfqdfg==

     */


    private String contact_id_card_number;


    /** 新增20220714
     *
     * 1、当超级管理员类型是经办人时，请上传超级管理员证件的正面照片。
     2、若证件类型为身份证，请上传人像面照片。
     */
    private  String  contact_id_doc_copy;

    /**新增20220714
     *
     * 1、当超级管理员类型是经办人时，请上传超级管理员证件的反面照片。
     2、若证件类型为护照，无需上传反面照片。
     */
    private String contact_id_doc_copy_back;

    /**
     *新增20220714
     *
     * 1、当超级管理员类型是经办人时，请上传证件有效期开始时间。
     2、请按照示例值填写。
     3、结束时间大于开始时间。
     示例值：2019-06-06
     */
    private String contact_id_doc_period_begin;

    /**
     *
     *新增20220714
     *
     * 1、当超级管理员类型是经办人时，请上传证件有效期结束时间。
     2、请按照示例值填写，若证件有效期为长期，请填写：长期。
     3、结束时间大于开始时间。
     示例值：2026-06-06
     */
    private String contact_id_doc_period_end;

    /**
     *
     * 1、当超级管理员类型是经办人时，请上传业务办理授权函。
     2、请参照示例图打印业务办理授权函，全部信息需打印，不支持手写商户信息，并加盖公章。
     */
    private String business_authorization_letter;
    /**
     * 超级管理员手机     必填
     *  1、请填写管理员的手机号，11位数字， 用于接收微信支付的重要管理信息及日常操作验证码 。
     * 2、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     * 示例值：pVd1HJ6zyvPedzGaV+X3qtmrq9bb9tPROvwia4ibL+F6mfjbzQIzfb3HHLEjZ4YiNWWNeespQO/0kjiwfqdfg==
     *
     *
     */
    private String mobile_phone;

    /**
     * 超级管理员邮箱  条件选填
     *   1、主体类型为“小微商户/个人卖家”可选填，其他主体需必填。
     *  2、用于接收微信支付的开户邮件及日常业务通知。
     *  3、需要带@，遵循邮箱格式校验 。
     * 4、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     * 示例值：pVd1HJ6zyvPedzGaV+X3qtmrq9bb9tPROvwia4ibL+FWWNUlw/wDa4SzfeespQO/0kjiwfqdfg==
     *
     *
     */
    private String contact_email;

    public String getCoKey() {
        return coKey;
    }

    public void setCoKey(String coKey) {
        this.coKey = coKey;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_id_card_number() {
        return contact_id_card_number;
    }

    public void setContact_id_card_number(String contact_id_card_number) {
        this.contact_id_card_number = contact_id_card_number;
    }

    public String getContact_id_doc_copy() {
        return contact_id_doc_copy;
    }

    public void setContact_id_doc_copy(String contact_id_doc_copy) {
        this.contact_id_doc_copy = contact_id_doc_copy;
    }

    public String getContact_id_doc_copy_back() {
        return contact_id_doc_copy_back;
    }

    public void setContact_id_doc_copy_back(String contact_id_doc_copy_back) {
        this.contact_id_doc_copy_back = contact_id_doc_copy_back;
    }

    public String getContact_id_doc_period_begin() {
        return contact_id_doc_period_begin;
    }

    public void setContact_id_doc_period_begin(String contact_id_doc_period_begin) {
        this.contact_id_doc_period_begin = contact_id_doc_period_begin;
    }

    public String getContact_id_doc_period_end() {
        return contact_id_doc_period_end;
    }

    public void setContact_id_doc_period_end(String contact_id_doc_period_end) {
        this.contact_id_doc_period_end = contact_id_doc_period_end;
    }

    public String getBusiness_authorization_letter() {
        return business_authorization_letter;
    }

    public void setBusiness_authorization_letter(String business_authorization_letter) {
        this.business_authorization_letter = business_authorization_letter;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }
}


