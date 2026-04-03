package com.wechat.bo.sft;


import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 二级商户进件
 */
public class ApplyParam  implements  java.io.Serializable,BaseBean{

     private String applyKey;
     private String applyID;

     private String staffID;
     private Date applydate;
    /**
     * 业务申请编号 必填
     * 1、服务商自定义的商户唯一编号。
     * 2、每个编号对应一个申请单，每个申请单审核通过后会生成一个微信支付商户号。
     * 3、若申请单被驳回，可填写相同的“业务申请编号”，即可覆盖修改原申请单信息 。
     * 示例值：APPLYMENT_00000000001
     */
    private String out_request_no;
    /**
     * 主体类型
     * 2401：小微商户，指无营业执照的个人商家。
     * 2500：个人卖家，指无营业执照，已持续从事电子商务经营活动满6个月，且期间经营收入累计超过20万元的个人商家。（若选择该主体，请在“补充说明”填写相关描述）
     * 4：个体工商户，营业执照上的主体类型一般为个体户、个体工商户、个体经营。
     * 2：企业，营业执照上的主体类型一般为有限公司、有限责任公司。
     * 3：党政机关，包括国内各级、各类政府机构
     * 5: 事业单位（如：公安、党 团、司法、交通、旅游、工商税务、市政、医疗、教育、学校等机构）
     * 1708：其他组织，不属于企业、政府/事业单位的组织机构（如社会团体、民办非企业、基 金会），要求机构已办理组织机构代码证。
     * 示例值：2401
     */
    private String organization_type;

    /**
     *
     * 请根据申请主体的实际情况填写，可参考选择金融机构指引：
     1、若商户主体是金融机构，则填写：true。
     2、若商户主体不是金融机构，则填写：false。
     若未传入将默认填写：false。
     示例值：true
     */
    private boolean finance_institution;//false
    /**
     * 营业执照/登记证书信息 条件选填
     * 1、主体为“小微/个人卖家”时，不填。
     * 2、主体为“个体工商户/企业”时，请上传营业执照。
     * 3、主体为“党政、机关及事业单位/其他组织”时，请上传登记证书。
     */
    private BusinessLicenseInfo business_license_info;
    /**
     *
     * 废弃
     * 组织机构代码证信息 条件选填
     * query主体为企业/党政、机关及事业单位/其他组织，且证件号码不是18位时必填。
     */
    private OrganizationCertInfo organization_cert_info;
    /**
     * 经营者/法人证件类型 非必填
     * 1、主体为“小微/个人卖家”，可选择：身份证。
     * 2、主体为“个体户/企业/党政、机关及事业单位/其他组织”，可选择：以下任一证件类型。
     * 3、若没有填写，系统默认选择：身份证。
     * 枚举值:
     * IDENTIFICATION_TYPE_MAINLAND_IDCARD：中国大陆居民-身份证
     * IDENTIFICATION_TYPE_OVERSEA_PASSPORT：其他国家或地区居民-护照
     * IDENTIFICATION_TYPE_HONGKONG：中国香港居民–来往内地通行证
     * IDENTIFICATION_TYPE_MACAO：中国澳门居民–来往内地通行证
     * IDENTIFICATION_TYPE_TAIWAN：中国台湾居民–来往大陆通行证
     * 示例值：IDENTIFICATION_TYPE_MACAO
     */
    private String id_doc_type;
    /**
     * 经营者/法人身份证信息 条件选填
     * query请填写经营者/法人的身份证信息
     * 证件类型为“身份证”时填写。
     */
    private IdCardInfo id_card_info;
    /**
     * 经营者/法人其他类型证件信息 条件选填
     * query证件类型为“来往内地通行证、来往大陆通行证、护照”时填写。
     */
    private IdDocInfo id_doc_info;
    /**
     * 废弃
     * 是否填写结算账户信息 必填
     * query可根据实际情况，填写“true”或“false”。
     * 1、若为“true”，则需填写结算账户信息。
     * 2、若为“false”，则无需填写结算账户信息。
     * 示例值：true
     */
  //  private Boolean need_account_info;
    /**
     * 结算账户信息 条件选填
     * query若"是否填写结算账户信息"填写为“true”, 则必填，填写为“false”不填 。
     */
    private AccountInfo account_info;
    /**
     * 超级管理员信息 必填
     * query请填写店铺的超级管理员信息。
     * 超级管理员需在开户后进行签约，并可接收日常重要管理信息和进行资金操作，请确定其为商户法定代表人或负责人。
     */
    private ContactInfo contact_info;
    /**
     * 店铺信息 必填
     * query请填写店铺信息
     */
    private SalesSceneInfo sales_scene_info;
    /**
     * 商户简称 必填
     * queryUTF-8格式，中文占3个字节，即最多16个汉字长度。将在支付完成页向买家展示，需与商家的实际售卖商品相符 。
     * 示例值：腾讯
     */
    private String merchant_shortname;
    /**
     * 特殊资质 非必填
     * query
     * 1、若店铺业务包含互联网售药，则需上传特殊资质-《互联网药品交易服务证》。
     * 2、最多可上传5张照片，请填写通过图片上传接口预先上传图片生成好的MediaID 。
     * 示例值：[\"jTpGmxUX3FBWVQ5NJInE4d2I6_H7I4\"]
     */
    private String qualifications;
    /**
     * 补充材料 非必填
     * query 最多可上传5张照片，请填写通过图片上传接口预先上传图片生成好的MediaID 。
     * 示例值：[\"jTpGmg05InE4d2I6_H7I4\"]
     */
    private String business_addition_pics;
    /**
     * 补充说明 非必填
     * query 可填写512字以内 。
     * 示例值：特殊情况，说明原因
     */
    private String business_addition_desc;


    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public String getOrganization_type() {
        return organization_type;
    }

    public void setOrganization_type(String organization_type) {
        this.organization_type = organization_type;
    }

    public BusinessLicenseInfo getBusiness_license_info() {
        return business_license_info;
    }

    public void setBusiness_license_info(BusinessLicenseInfo business_license_info) {
        this.business_license_info = business_license_info;
    }

    public OrganizationCertInfo getOrganization_cert_info() {
        return organization_cert_info;
    }

    public void setOrganization_cert_info(OrganizationCertInfo organization_cert_info) {
        this.organization_cert_info = organization_cert_info;
    }

    public String getId_doc_type() {
        return id_doc_type;
    }

    public void setId_doc_type(String id_doc_type) {
        this.id_doc_type = id_doc_type;
    }

    public IdCardInfo getId_card_info() {
        return id_card_info;
    }

    public void setId_card_info(IdCardInfo id_card_info) {
        this.id_card_info = id_card_info;
    }

    public IdDocInfo getId_doc_info() {
        return id_doc_info;
    }

    public void setId_doc_info(IdDocInfo id_doc_info) {
        this.id_doc_info = id_doc_info;
    }


    public AccountInfo getAccount_info() {
        return account_info;
    }

    public void setAccount_info(AccountInfo account_info) {
        this.account_info = account_info;
    }

    public ContactInfo getContact_info() {
        return contact_info;
    }

    public void setContact_info(ContactInfo contact_info) {
        this.contact_info = contact_info;
    }

    public SalesSceneInfo getSales_scene_info() {
        return sales_scene_info;
    }

    public void setSales_scene_info(SalesSceneInfo sales_scene_info) {
        this.sales_scene_info = sales_scene_info;
    }

    public String getMerchant_shortname() {
        return merchant_shortname;
    }

    public void setMerchant_shortname(String merchant_shortname) {
        this.merchant_shortname = merchant_shortname;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getBusiness_addition_pics() {
        return business_addition_pics;
    }

    public void setBusiness_addition_pics(String business_addition_pics) {
        this.business_addition_pics = business_addition_pics;
    }

    public String getBusiness_addition_desc() {
        return business_addition_desc;
    }

    public void setBusiness_addition_desc(String business_addition_desc) {
        this.business_addition_desc = business_addition_desc;
    }

    public String getApplyKey() {
        return applyKey;
    }

    public void setApplyKey(String applyKey) {
        this.applyKey = applyKey;
    }

    public String getApplyID() {
        return applyID;
    }

    public void setApplyID(String applyID) {
        this.applyID = applyID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public boolean isFinance_institution() {
        return finance_institution;
    }

    public void setFinance_institution(boolean finance_institution) {
        this.finance_institution = finance_institution;
    }
}
