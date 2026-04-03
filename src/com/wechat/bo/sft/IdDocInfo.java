package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 二级商户进件——经营者/法人其他类型证件信息  条件选填
 *  证件类型为“来往内地通行证、来往大陆通行证、护照”时填写。
 *
 */
public class IdDocInfo implements  java.io.Serializable,BaseBean {

    private String docKey;
    private String docId;
    /**
     * 证件姓名   必填
     * 1、请填写经营者/法人姓名。
     *2、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     *示例值：jTpGmxUX3FBWVQ5NJTZvlKX_gdU4LC-ehEuo0BJqRTvDujqhThn4ReFxikqJ5YW6zFQ

     */
    private String id_doc_name;
    /**
     *  证件号码  必填
     *  7~11位 数字|字母|连字符 。
     * 该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     *  示例值：jTpGmxUX3FBWVQ5NJTZvlKX_go0BJqRTvDujqhThn4ReFxikqJ5YW6zFQ
     *
     */
    private String id_doc_number;
    /**
     * 证件照片  必填
     *    1、可上传1张图片，请填写通过图片上传接口预先上传图片生成好的MediaID。
     *    2、2M内的彩色图片，格式可为bmp、png、jpeg、jpg或gif 。
     *    示例值：xi-vByf3Gjm7KE53JXvGy9tqZm2XAUf-4KGprrKhpVBDIUv0OF4wFNIO4kqg05InE4d2I6_H7I4
     *
     */
    private String id_doc_copy;


    /**
     *
     *  证件结束日期    必填
     *   1、请按照示例值填写。
     * 2、若证件有效期为长期，请填写：长期。
     * 3、证件有效期需大于60天 。
     * 示例值：2020-01-02
     *
     */
    private String doc_period_end;


    public String getId_doc_name() {
        return id_doc_name;
    }

    public void setId_doc_name(String id_doc_name) {
        this.id_doc_name = id_doc_name;
    }

    public String getId_doc_number() {
        return id_doc_number;
    }

    public void setId_doc_number(String id_doc_number) {
        this.id_doc_number = id_doc_number;
    }

    public String getId_doc_copy() {
        return id_doc_copy;
    }

    public void setId_doc_copy(String id_doc_copy) {
        this.id_doc_copy = id_doc_copy;
    }

    public String getDoc_period_end() {
        return doc_period_end;
    }

    public void setDoc_period_end(String doc_period_end) {
        this.doc_period_end = doc_period_end;
    }


    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
