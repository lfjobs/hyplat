package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 二级商户进件——结算银行账户  条件选填
 *
 * 若"是否填写结算账户信息"填写为“true”, 则必填，填写为“false”不填 。
 *
 */
public class AccountInfo  implements  java.io.Serializable,BaseBean {

    private String acKey;
    private String acId;
    /***
     * 账户类型  必填
     *
     *1、若主体为企业/党政、机关及事业单位/其他组织，可填写：74-对公账户。
     *12、主体为“小微/个人卖家”，可选择：75-对私账户。
     *13、若主体为个体工商户，可填写：74-对公账户、75-对私账户。
     *1示例值：75
     */
    private String bank_account_type;
    /**
     *
     * 开户银行  必填
     *  详细参见开户银行对照表。
     *  示例值：工商银行
     *
     *
     */
    private String account_bank;




    /**
     *开户名称 必填
     *  1、选择经营者个人银行卡时，开户名称必须与身份证姓名一致。
     * 2、选择对公账户时，开户名称必须与营业执照上的“商户名称”一致。
     * 3、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     * 示例值：AOZdYGISxo4yw96uY1Pk7Rq79Jtt7+I8juwEc4P4TG5xzchG/5IL9DBd+Z0zZXkw==
     *
     */
    private String account_name;

    /**
     * 开户银行省市编码  必填
     *
     *  至少精确到市，详细参见省市区编号对照表。
     *
     * 示例值：110000

     */
    private String bank_address_code;


    private String bank_address;//开户银行地址


    /**开户银行联行号  条件选填
     *1、17家直连银行无需填写，如为其他银行，开户银行全称（含支行）和开户银行联行号二选一。
     *2、详细参见开户银行全称（含支行）对照表。
     *示例值：402713354941
     *
     */
    private String bank_branch_id;

    /**
     * 开户银行全称 （含支行） 条件选填
     *  1、17家直连银行无需填写，如为其他银行，开户银行全称（含支行）和开户银行联行号二选一。
     *2、需填写银行全称，如"深圳农村商业银行XXX支行" 。
     *3、详细参见开户银行全称（含支行）对照表。
     示例值：施秉县农村信用合作联社城关信用社
     */
    private String bank_name;
    /**
     *
     *  银行帐号  必填
     *   1、数字，长度遵循系统支持的对公/对私卡号长度要求表。
     * 2、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     *示例值： d+xT+MQCvrLHUVDWv/8MR/dB7TkXLVfSrUxMPZy6jWWYzpRrEEaYQE8ZRGYoeorwC+w==
     *
     */
    private String account_number;


    private String account_copy;

    public String getBank_account_type() {
        return bank_account_type;
    }

    public void setBank_account_type(String bank_account_type) {
        this.bank_account_type = bank_account_type;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_address_code() {
        return bank_address_code;
    }

    public void setBank_address_code(String bank_address_code) {
        this.bank_address_code = bank_address_code;
    }

    public String getBank_branch_id() {
        return bank_branch_id;
    }

    public void setBank_branch_id(String bank_branch_id) {
        this.bank_branch_id = bank_branch_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAcKey() {
        return acKey;
    }

    public void setAcKey(String acKey) {
        this.acKey = acKey;
    }

    public String getAcId() {
        return acId;
    }

    public void setAcId(String acId) {
        this.acId = acId;
    }

    public String getAccount_copy() {
        return account_copy;
    }

    public void setAccount_copy(String account_copy) {
        this.account_copy = account_copy;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }
}
