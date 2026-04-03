package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

/**
 * CrmCustomerPO 客户信息(粉丝导入)
 *
 * @author zch
 */
public class CrmCustomerPO implements BaseBean {
    // 主键，使用 UUID 自动生成
    private String id;

    // 导入粉丝的所有人ID
    private String importerId;

    // 客户信息获取方式（导入方式）
    private String importerMode;

    // 客户名称
    private String userName;

    // 粉丝的联系方式
    private String contactInfo;
    // 粉丝的证件号码
    private String cardNumber;
    // 粉丝的证件类型
    private String cardType;
    // 粉丝的居住地址
    private String residentialAddress;
    // 粉丝的社会职位
    private String socialStatus;
    // 账号权益(会员等级)，0 未注册会员 1 注册会员
    private String memberLevel;
    // 备注信息
    private String extendInfo;

    // 创建时间，自动填充
    private String createdAt;

    // 更新时间，自动填充
    private String updatedAt;
    private String unitCompany;
    //是否电话联系
    private String byPhone;
    // 粉丝意见
    private String staffOpinions;
    // 是否意向客户
    private String interestedParties;
    // 是否托管
    private String isCustody;
    //托管账号
    private String custodyAccount;
    //托管密码
    private String custodyAccountPwd;
    //三方平台是否发视频
    private String isSend;
    //是否发送短信
    private String sendMessage;

    public CrmCustomerPO() {
    }

    public CrmCustomerPO(String id, String importerId, String importerMode, String userName, String contactInfo, String cardNumber, String cardType, String residentialAddress, String socialStatus, String memberLevel, String extendInfo, String createdAt, String updatedAt, String unitCompany, String byPhone, String staffOpinions, String interestedParties, String isCustody, String custodyAccount, String custodyAccountPwd, String isSend, String sendMessage) {
        this.id = id;
        this.importerId = importerId;
        this.importerMode = importerMode;
        this.userName = userName;
        this.contactInfo = contactInfo;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.residentialAddress = residentialAddress;
        this.socialStatus = socialStatus;
        this.memberLevel = memberLevel;
        this.extendInfo = extendInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.unitCompany = unitCompany;
        this.byPhone = byPhone;
        this.staffOpinions = staffOpinions;
        this.interestedParties = interestedParties;
        this.isCustody = isCustody;
        this.custodyAccount = custodyAccount;
        this.custodyAccountPwd = custodyAccountPwd;
        this.isSend = isSend;
        this.sendMessage = sendMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImporterId() {
        return importerId;
    }

    public void setImporterId(String importerId) {
        this.importerId = importerId;
    }

    public String getImporterMode() {
        return importerMode;
    }

    public void setImporterMode(String importerMode) {
        this.importerMode = importerMode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getSocialStatus() {
        return socialStatus;
    }

    public void setSocialStatus(String socialStatus) {
        this.socialStatus = socialStatus;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUnitCompany() {
        return unitCompany;
    }

    public void setUnitCompany(String unitCompany) {
        this.unitCompany = unitCompany;
    }

    public String getByPhone() {
        return byPhone;
    }

    public void setByPhone(String byPhone) {
        this.byPhone = byPhone;
    }

    public String getStaffOpinions() {
        return staffOpinions;
    }

    public void setStaffOpinions(String staffOpinions) {
        this.staffOpinions = staffOpinions;
    }

    public String getInterestedParties() {
        return interestedParties;
    }

    public void setInterestedParties(String interestedParties) {
        this.interestedParties = interestedParties;
    }

    public String getIsCustody() {
        return isCustody;
    }

    public void setIsCustody(String isCustody) {
        this.isCustody = isCustody;
    }

    public String getCustodyAccount() {
        return custodyAccount;
    }

    public void setCustodyAccount(String custodyAccount) {
        this.custodyAccount = custodyAccount;
    }

    public String getCustodyAccountPwd() {
        return custodyAccountPwd;
    }

    public void setCustodyAccountPwd(String custodyAccountPwd) {
        this.custodyAccountPwd = custodyAccountPwd;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }
}
