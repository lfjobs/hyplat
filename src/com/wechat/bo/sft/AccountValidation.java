package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

/**
 *
 * 汇款账户验证信息
 *
 * 当申请状态为ACCOUNT_NEED_VERIFY 时有返回，可根据指引汇款，完成账户验证。
 */
public class AccountValidation  implements  java.io.Serializable,BaseBean {

    private String avKey;
    private String avId;
    //  付款户名	 解密
    private String account_name;

    // 付款卡号	 解密
    private String  account_no;

    //  汇款金额
    private String  pay_amount;
    //收款卡号
    private String  destination_account_number;

    //收款户名
    private String  destination_account_name;

    //开户银行	destination_account_bank
    private String   destination_account_bank;

    //省市信息	city
    private String   city;

    //备注信息	remark  商户汇款时，需要填写的备注信息。 示例值：入驻账户验证
    private String   remark;

    //汇款截止时间	deadline	string[1,20]  v请在此时间前完成汇款。示例值：2018-12-10 17:09:01
    private String   deadline;

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getDestination_account_number() {
        return destination_account_number;
    }

    public void setDestination_account_number(String destination_account_number) {
        this.destination_account_number = destination_account_number;
    }

    public String getDestination_account_name() {
        return destination_account_name;
    }

    public void setDestination_account_name(String destination_account_name) {
        this.destination_account_name = destination_account_name;
    }

    public String getDestination_account_bank() {
        return destination_account_bank;
    }

    public void setDestination_account_bank(String destination_account_bank) {
        this.destination_account_bank = destination_account_bank;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getAvKey() {
        return avKey;
    }

    public void setAvKey(String avKey) {
        this.avKey = avKey;
    }

    public String getAvId() {
        return avId;
    }

    public void setAvId(String avId) {
        this.avId = avId;
    }
}
