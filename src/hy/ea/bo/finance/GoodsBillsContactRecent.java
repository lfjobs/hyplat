package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 物品单据管理扩展表：GoodsBillsExt
 *
 * @author 陈红
 */
public class GoodsBillsContactRecent implements BaseBean, Cloneable, java.io.Serializable {
    private String id;
    private String key;
    private String accountName;//往来单位/个人名称
    private String accountNameId;//往来单位/个人id
    private String accountNum;//账户号
    private String openBank;//开户行
    private String accountPhone;//账户联系方式

    private String accountFlag;//账户类型：personal个人，company公司；personalPlan个人（计划），companyPlan公司（计划）
    private Date createDate;
    private String staffId;
    private String staffName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNameId() {
        return accountNameId;
    }

    public void setAccountNameId(String accountNameId) {
        this.accountNameId = accountNameId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
