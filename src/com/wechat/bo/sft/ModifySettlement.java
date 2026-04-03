package com.wechat.bo.sft;

/**
 * 修改结算帐号API
 */
public class ModifySettlement {
    /**
     *
     * 请填写本服务商负责进件的特约商户号。
     特殊规则：长度最小8个字节。
     示例值：1511101111
     */
  private String sub_mchid;// 特约商户号


    /**
     *
     *账户类型
     *   据特约商户号的主体类型，可选择的账户类型如下：
     1、小微主体：经营者个人银行卡
     2、个体工商户主体：经营者个人银行卡/ 对公银行账户
     3、企业主体：对公银行账户
     4、党政、机关及事业单位主体：对公银行账户
     5、其他组织主体：对公银行账户
     枚举值：
     ACCOUNT_TYPE_BUSINESS：对公银行账户
     ACCOUNT_TYPE_PRIVATE：经营者个人银行卡
     *
     *
     *
     */
    private String account_type;

    /**
     *开户银行
     * 请填写开户银行名称，详细参见《开户银行对照表》。
     示例值：工商银行
     */
    private String account_bank;

    private String bank_address_code; //开户银行省市编码

    /**
     *
     * 开户银行全称（含支行）
     *
     * 若开户银行为“其他银行”，则需二选一填写“开户银行全称（含支行）”或“开户银行联行号”。
     填写银行全称，如"深圳农村商业银行XXX支行" ，详细参见开户银行全称（含支行）对照表。
     */
    private String bank_name;

    /**
     *开户银行联行号
     * 若开户银行为“其他银行”，则需二选一填写“开户银行全称（含支行）”或“开户银行联行号”。
     填写银行联行号，详细参见《开户银行全称（含支行）对照表》。
     */
    private String bank_branch_id;
    /**
     * 银行账号
     *
     *  1、数字，长度遵循系统支持的对公/对私卡号长度要求
     2、该字段需进行加密处理，加密方法详见《敏感信息加密说明》。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     *
     */
    private String account_number;

    public String getSub_mchid() {
        return sub_mchid;
    }

    public void setSub_mchid(String sub_mchid) {
        this.sub_mchid = sub_mchid;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getBank_address_code() {
        return bank_address_code;
    }

    public void setBank_address_code(String bank_address_code) {
        this.bank_address_code = bank_address_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_branch_id() {
        return bank_branch_id;
    }

    public void setBank_branch_id(String bank_branch_id) {
        this.bank_branch_id = bank_branch_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
}
