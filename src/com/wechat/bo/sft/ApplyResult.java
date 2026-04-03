package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

import java.util.List;

/**
 *
 * 通过业务申请编号查询申请状态
 * 返回结果
 */
public class ApplyResult  implements java.io.Serializable,BaseBean{
    private String arKey;
    private String arId;
    /**
     * 申请状态
     * 枚举值：
     CHECKING：资料校验中
     ACCOUNT_NEED_VERIFY：待账户验证
     AUDITING：审核中
     REJECTED：已驳回
     NEED_SIGN：待签约
     FINISH：完成
     FROZEN：已冻结
     示例值：FINISH
     */
    private String applyment_state;
    /**
     *
     * 	申请状态描述
     示例值：“审核中”
     */
    private String applyment_state_desc;

    /**
     * 签约链接
     * 1、当申请状态为NEED_SIGN时才返回。
       2、建议将链接转为二维码展示，需让申请单-管理者用微信扫码打开，完成签约。
     *
     */
    private String sign_url;
    /**
     *
     * 电商平台二级商户号
     *
     * 当申请状态为NEED_SIGN或FINISH时才返回。
     */
    private String  sub_mchid;

    /**
     *
     * 	提交接口填写的业务申请编号。
     示例值：APPLYMENT_00000000001
     */
    private String out_request_no;

    /**
     *
     * 微信支付分配的申请单号。
     示例值：2000002124775691
     */
    private String  applyment_id;
    /**
     * 汇款账户验证信息
     */
    private AccountValidation  account_validation;
    /**
     * 汇款账户验证信息
     */
    private String  account_validationID;
    /**
     *
     * 驳回原因详情
     */
    private List<AuditDetail> audit_detail;

    /**
     *
     * 君子签认证结果
     */
    private String jzqresultCode;
    private String jzqmsg;
    //返回
    private String emailOrMobile;


    public String getApplyment_state() {
        return applyment_state;
    }

    public void setApplyment_state(String applyment_state) {
        this.applyment_state = applyment_state;
    }

    public String getApplyment_state_desc() {
        return applyment_state_desc;
    }

    public void setApplyment_state_desc(String applyment_state_desc) {
        this.applyment_state_desc = applyment_state_desc;
    }

    public String getSign_url() {
        return sign_url;
    }

    public void setSign_url(String sign_url) {
        this.sign_url = sign_url;
    }

    public String getSub_mchid() {
        return sub_mchid;
    }

    public void setSub_mchid(String sub_mchid) {
        this.sub_mchid = sub_mchid;
    }

    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public String getApplyment_id() {
        return applyment_id;
    }

    public void setApplyment_id(String applyment_id) {
        this.applyment_id = applyment_id;
    }

    public AccountValidation getAccount_validation() {
        return account_validation;
    }

    public void setAccount_validation(AccountValidation account_validation) {
        this.account_validation = account_validation;
    }

    public List<AuditDetail> getAudit_detail() {
        return audit_detail;
    }

    public void setAudit_detail(List<AuditDetail> audit_detail) {
        this.audit_detail = audit_detail;
    }

    public String getArKey() {
        return arKey;
    }

    public void setArKey(String arKey) {
        this.arKey = arKey;
    }

    public String getArId() {
        return arId;
    }

    public void setArId(String arId) {
        this.arId = arId;
    }


    public String getAccount_validationID() {
        return account_validationID;
    }

    public void setAccount_validationID(String account_validationID) {
        this.account_validationID = account_validationID;
    }

    public String getJzqresultCode() {
        return jzqresultCode;
    }

    public void setJzqresultCode(String jzqresultCode) {
        this.jzqresultCode = jzqresultCode;
    }

    public String getJzqmsg() {
        return jzqmsg;
    }

    public void setJzqmsg(String jzqmsg) {
        this.jzqmsg = jzqmsg;
    }

    public String getEmailOrMobile() {
        return emailOrMobile;
    }

    public void setEmailOrMobile(String emailOrMobile) {
        this.emailOrMobile = emailOrMobile;
    }


}
