package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 二级商户进件  ——经营者/法人身份证信息  条件选填
 *
 * 请填写经营者/法人的身份证信息
 *  证件类型为“身份证”时填写。
 */
public class IdCardInfo implements  java.io.Serializable,BaseBean {
    private String cardKey;
    private String cardId;
    /**
     * 身份证人像面照片 必填
     *1、请上传经营者/法定代表人的身份证人像面照片。
     *2、可上传1张图片，请填写通过图片上传接口预先上传图片生成好的MediaID。
     * 示例值：xpnFuAxhBTEO_PvWkfSCJ3zVIn001D8daLC-ehEuo0BJqRTvDujqhThn4ReFxikqJ5YW6zFQ
     *
     */
    private String id_card_copy;
    /**
     *  身份证国徽面照片  必填
     *
     * 1、请上传经营者/法定代表人的身份证国徽面照片。
     * 2 、可上传1张图片，请填写通过图片上传接口预先上传图片生成好的MediaID 。
     *  示例值：vByf3Gjm7KE53JXvGy9tqZm2XAUf-4KGprrKhpVBDIUv0OF4wFNIO4kqg05InE4d2I6_H7I4
     */
    private String id_card_national;
    /**
     * 身份证姓名   必填
     *
     *  1、请填写经营者/法定代表人对应身份证的姓名，2~30个中文字符、英文字符、符号。
     * 2、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     * 示例值：pVd1HJ6v/69bDnuC4EL5Kz4jBHLiCa8MRtelw/wDa4SzfeespQO/0kjiwfqdfg==
     *
     */
    private String id_card_name;
    /**
     * 身份证号码  必填
     *
     *1、请填写经营者/法定代表人对应身份证的号码。
     *2、15位数字或17位数字+1位数字|X ，该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     *示例值：zV+BEmytMNQCqQ8juwEc4P4TG5xzchG/5IL9DBd+Z0zZXkw==4
     */
    private String id_card_number;
    /**新增
     * 1、主体类型为企业时，需要填写。其他主体类型，无需上传。
     2、请按照身份证住址填写，如广东省深圳市南山区xx路xx号xx室
     3、该字段需进行加密处理
     */
    private String id_card_address;
    /**新增
     * 1、请按照示例值填写。
     2、结束时间大于开始时间。
     示例值：2019-06-06
     */
    private String id_card_valid_time_begin;
    /**
     * 身份证有效期限 必填
     * 	1、请填写身份证有效期的结束时间，注意参照示例中的格式。
     * 2、若证件有效期为长期，请填写：长期。
     * 3、证件有效期需大于60天。
     * 示例值：2026-06-06，长期
     */
    private String id_card_valid_time;


    private String legal_tel;//法人手机号、废弃

    public String getId_card_copy() {
        return id_card_copy;
    }

    public void setId_card_copy(String id_card_copy) {
        this.id_card_copy = id_card_copy;
    }

    public String getId_card_national() {
        return id_card_national;
    }

    public void setId_card_national(String id_card_national) {
        this.id_card_national = id_card_national;
    }

    public String getId_card_name() {
        return id_card_name;
    }

    public void setId_card_name(String id_card_name) {
        this.id_card_name = id_card_name;
    }

    public String getId_card_number() {
        return id_card_number;
    }

    public void setId_card_number(String id_card_number) {
        this.id_card_number = id_card_number;
    }

    public String getId_card_valid_time() {
        return id_card_valid_time;
    }

    public void setId_card_valid_time(String id_card_valid_time) {
        this.id_card_valid_time = id_card_valid_time;
    }


    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLegal_tel() {
        return legal_tel;
    }

    public void setLegal_tel(String legal_tel) {
        this.legal_tel = legal_tel;
    }

    public String getId_card_address() {
        return id_card_address;
    }

    public void setId_card_address(String id_card_address) {
        this.id_card_address = id_card_address;
    }

    public String getId_card_valid_time_begin() {
        return id_card_valid_time_begin;
    }

    public void setId_card_valid_time_begin(String id_card_valid_time_begin) {
        this.id_card_valid_time_begin = id_card_valid_time_begin;
    }
}
