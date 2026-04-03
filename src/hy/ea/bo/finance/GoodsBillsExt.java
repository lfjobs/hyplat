package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * 物品单据管理扩展表：GoodsBillsExt
 *
 * @author 陈红
 */
public class GoodsBillsExt implements BaseBean, Cloneable, java.io.Serializable {
    private String goodsBillsID;
    private String goodsBillsExtKey;
    private String goodsBillsExtId;
    private String accountName;//往来单位/个人名称
    private String accountNameId;//往来单位/个人id
    private String accountNum;//账户号

    private String openBank;//开户行
    private String accountPhone;//账户联系方式

    private String accountFlag;//账户类型：personal个人，company公司
    private String budgetAmount;//预算金额
    private String loanDirection;//借贷方向

    private String borrowAmount;//借方金额
    private String loanAmount;//贷方金额

    private long orderNum;//序号
    private String balance;//余额
    private String initialBalance;//初始余额
    private String specsParentId;//规格父级id

    private String accountNameFrom;//往来单位/个人名称
    private String accountNameIdFrom;//往来单位/个人id
    private String accountNumFrom;//账户号

    private String openBankFrom;//开户行
    private String accountPhoneFrom;//账户联系方式

    private String accountFlagFrom;//账户类型：personal个人，company公司

//    private String companyName;//单位名称
//    private String companyAccount;//单位账户
//    private String personalName;//个人姓名
//    private String personalAccount;//个人账户

    private String teamReward;//团队奖励
    private String teamRewardInfo;//团队奖励明细

    private String personalReward;//个人奖励
    private String personalRewardInfo;//个人奖励明细

    private String teamPunishment;//团队惩罚
    private String teamPunishmentInfo;//团队惩罚明细

    private String personalPunishment;//个人惩罚
    private String personalPunishmentInfo;//个人惩罚明细


    public String getAccountNameId() {
        return accountNameId;
    }

    public void setAccountNameId(String accountNameId) {
        this.accountNameId = accountNameId;
    }

    public String getGoodsBillsID() {
        return goodsBillsID;
    }

    public void setGoodsBillsID(String goodsBillsID) {
        this.goodsBillsID = goodsBillsID;
    }

    public String getGoodsBillsExtKey() {
        return goodsBillsExtKey;
    }

    public String getGoodsBillsExtId() {
        return goodsBillsExtId;
    }

    public void setGoodsBillsExtId(String goodsBillsExtId) {
        this.goodsBillsExtId = goodsBillsExtId;
    }

    public void setGoodsBillsExtKey(String goodsBillsExtKey) {
        this.goodsBillsExtKey = goodsBillsExtKey;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getLoanDirection() {
        return loanDirection;
    }

    public void setLoanDirection(String loanDirection) {
        this.loanDirection = loanDirection;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(String initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getSpecsParentId() {
        return specsParentId;
    }

    public void setSpecsParentId(String specsParentId) {
        this.specsParentId = specsParentId;
    }

    public String getAccountNameFrom() {
        return accountNameFrom;
    }

    public void setAccountNameFrom(String accountNameFrom) {
        this.accountNameFrom = accountNameFrom;
    }

    public String getAccountNameIdFrom() {
        return accountNameIdFrom;
    }

    public void setAccountNameIdFrom(String accountNameIdFrom) {
        this.accountNameIdFrom = accountNameIdFrom;
    }

    public String getAccountNumFrom() {
        return accountNumFrom;
    }

    public void setAccountNumFrom(String accountNumFrom) {
        this.accountNumFrom = accountNumFrom;
    }

    public String getOpenBankFrom() {
        return openBankFrom;
    }

    public void setOpenBankFrom(String openBankFrom) {
        this.openBankFrom = openBankFrom;
    }

    public String getAccountPhoneFrom() {
        return accountPhoneFrom;
    }

    public void setAccountPhoneFrom(String accountPhoneFrom) {
        this.accountPhoneFrom = accountPhoneFrom;
    }

    public String getAccountFlagFrom() {
        return accountFlagFrom;
    }

    public void setAccountFlagFrom(String accountFlagFrom) {
        this.accountFlagFrom = accountFlagFrom;
    }

    public String getTeamReward() {
        return teamReward;
    }

    public void setTeamReward(String teamReward) {
        this.teamReward = teamReward;
    }

    public String getPersonalReward() {
        return personalReward;
    }

    public void setPersonalReward(String personalReward) {
        this.personalReward = personalReward;
    }

    public String getTeamPunishment() {
        return teamPunishment;
    }

    public void setTeamPunishment(String teamPunishment) {
        this.teamPunishment = teamPunishment;
    }

    public String getPersonalPunishment() {
        return personalPunishment;
    }

    public void setPersonalPunishment(String personalPunishment) {
        this.personalPunishment = personalPunishment;
    }

    public String getTeamRewardInfo() {
        return teamRewardInfo;
    }

    public void setTeamRewardInfo(String teamRewardInfo) {
        this.teamRewardInfo = teamRewardInfo;
    }

    public String getPersonalRewardInfo() {
        return personalRewardInfo;
    }

    public void setPersonalRewardInfo(String personalRewardInfo) {
        this.personalRewardInfo = personalRewardInfo;
    }

    public String getTeamPunishmentInfo() {
        return teamPunishmentInfo;
    }

    public void setTeamPunishmentInfo(String teamPunishmentInfo) {
        this.teamPunishmentInfo = teamPunishmentInfo;
    }

    public String getPersonalPunishmentInfo() {
        return personalPunishmentInfo;
    }

    public void setPersonalPunishmentInfo(String personalPunishmentInfo) {
        this.personalPunishmentInfo = personalPunishmentInfo;
    }
}
