package com.wechat.bo.sft;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 二级商户余额提现API
 */
public class Withdraw  implements  java.io.Serializable,BaseBean {
    private String wdkey;
    private String wdid;
    //提现申请参数开始
    private String sub_mchid;  //二级商户号
    private String out_request_no; //商户提现单号，由商户自定义生成，必须是字母数字。示例值：20190611222222222200000000012122

    private int amount; //提现金额   单位：分，金额不能超过8亿元 示例值：1
    private String remark; //否 商户对提现单的备注，商户自定义字段。示例值：交易提现
    private String bank_memo;  // 否  展示在收款银行系统中的附言，数字、字母最长32个汉字（能否成功展示依赖银行系统支持）
    //提现申请参数结束


    //查询状态
    private String withdraw_id;//预约提现单号提交预约提现申请后，由微信支付返回的申请单号，作为查询申请状态的唯一标识
    /**
     *
     * CREATE_SUCCESS：受理成功
     SUCCESS：提现成功
     FAIL：提现失败
     REFUND：提现退票
     CLOSE：关单
     INIT：业务单已创建
     */
    private String status;//预约提现单状态
    private String create_time;//提交预约时间返回的


    private String update_time; //提现状态更新时间
    private String reason;//失败原因
    private String account_number;//入账银行账号后四位
    private String account_bank;//入账银行



    private String sccid;//操作人
    private Date oprdate;//操作时间
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBank_memo() {
        return bank_memo;
    }

    public void setBank_memo(String bank_memo) {
        this.bank_memo = bank_memo;
    }

    public String getWithdraw_id() {
        return withdraw_id;
    }

    public void setWithdraw_id(String withdraw_id) {
        this.withdraw_id = withdraw_id;
    }

    public String getWdkey() {
        return wdkey;
    }

    public void setWdkey(String wdkey) {
        this.wdkey = wdkey;
    }

    public String getWdid() {
        return wdid;
    }

    public void setWdid(String wdid) {
        this.wdid = wdid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public Date getOprdate() {
        return oprdate;
    }

    public void setOprdate(Date oprdate) {
        this.oprdate = oprdate;
    }
}
