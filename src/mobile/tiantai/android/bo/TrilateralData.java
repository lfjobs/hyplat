package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

public class TrilateralData implements BaseBean {
    private String id;
    private String companyName;
    private String companyId;
    private String personnelID;
    private String name;
    private String sex;
    private String sector;
    private String phone;
    private String registrationPlatformName;
    private String registrationNo;
    private String registerAccount;
    private String pwd;
    private String uploadProject;
    private String promotionReasons;
    private String distributionService;
    private String serviceTracking;
    private String businessPersonnel;
    //0未删除   1已删除
    private String state;
    //录入人id
    private String staffId;
    private String staffName;
    //私信
    private String privateMessage;
    //审核
    private String audit1;
    //合同地址
    private String contractAddress;
    private String contractAddId;
    //附件地址
    private String attachmentAddress;
    private String attachmentAddressName;
    //分配状态
    private String distributionState;
    //分配人
    private String distributionPersonId;
    //分配人姓名
    private String distributionPersonName;
    //分配人公司
    private String distributionPersonCompanyId;
    //分配人手机号
    private String distributionPersonPhone;

    //时间
    private String time;
    //修改时间
    private String updateTime;
    //录入id
    private String inputId;
    //录入姓名
    private String inputName;
    //录入方式   1已托管 2未托管
    private String trusteeship;

    //是否上传   1已上传 2未上传
    private String isUpload;


    private String sectorId;
    private String sectorCode;

    public TrilateralData() {
    }

    public TrilateralData(String id, String companyName, String companyId, String personnelID, String name, String sex, String sector, String phone, String registrationPlatformName, String registrationNo, String registerAccount, String pwd, String uploadProject, String promotionReasons, String distributionService, String serviceTracking, String businessPersonnel, String state, String staffId, String staffName, String privateMessage, String audit1, String contractAddress, String contractAddId, String attachmentAddress, String attachmentAddressName, String distributionState, String distributionPersonId, String distributionPersonName, String distributionPersonCompanyId, String distributionPersonPhone, String time, String updateTime, String inputId, String inputName, String trusteeship, String isUpload, String sectorId, String sectorCode) {
        this.id = id;
        this.companyName = companyName;
        this.companyId = companyId;
        this.personnelID = personnelID;
        this.name = name;
        this.sex = sex;
        this.sector = sector;
        this.phone = phone;
        this.registrationPlatformName = registrationPlatformName;
        this.registrationNo = registrationNo;
        this.registerAccount = registerAccount;
        this.pwd = pwd;
        this.uploadProject = uploadProject;
        this.promotionReasons = promotionReasons;
        this.distributionService = distributionService;
        this.serviceTracking = serviceTracking;
        this.businessPersonnel = businessPersonnel;
        this.state = state;
        this.staffId = staffId;
        this.staffName = staffName;
        this.privateMessage = privateMessage;
        this.audit1 = audit1;
        this.contractAddress = contractAddress;
        this.contractAddId = contractAddId;
        this.attachmentAddress = attachmentAddress;
        this.attachmentAddressName = attachmentAddressName;
        this.distributionState = distributionState;
        this.distributionPersonId = distributionPersonId;
        this.distributionPersonName = distributionPersonName;
        this.distributionPersonCompanyId = distributionPersonCompanyId;
        this.distributionPersonPhone = distributionPersonPhone;
        this.time = time;
        this.updateTime = updateTime;
        this.inputId = inputId;
        this.inputName = inputName;
        this.trusteeship = trusteeship;
        this.isUpload = isUpload;
        this.sectorId = sectorId;
        this.sectorCode = sectorCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(String personnelID) {
        this.personnelID = personnelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistrationPlatformName() {
        return registrationPlatformName;
    }

    public void setRegistrationPlatformName(String registrationPlatformName) {
        this.registrationPlatformName = registrationPlatformName;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getRegisterAccount() {
        return registerAccount;
    }

    public void setRegisterAccount(String registerAccount) {
        this.registerAccount = registerAccount;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUploadProject() {
        return uploadProject;
    }

    public void setUploadProject(String uploadProject) {
        this.uploadProject = uploadProject;
    }

    public String getPromotionReasons() {
        return promotionReasons;
    }

    public void setPromotionReasons(String promotionReasons) {
        this.promotionReasons = promotionReasons;
    }

    public String getDistributionService() {
        return distributionService;
    }

    public void setDistributionService(String distributionService) {
        this.distributionService = distributionService;
    }

    public String getServiceTracking() {
        return serviceTracking;
    }

    public void setServiceTracking(String serviceTracking) {
        this.serviceTracking = serviceTracking;
    }

    public String getBusinessPersonnel() {
        return businessPersonnel;
    }

    public void setBusinessPersonnel(String businessPersonnel) {
        this.businessPersonnel = businessPersonnel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(String privateMessage) {
        this.privateMessage = privateMessage;
    }

    public String getAudit1() {
        return audit1;
    }

    public void setAudit1(String audit1) {
        this.audit1 = audit1;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddId() {
        return contractAddId;
    }

    public void setContractAddId(String contractAddId) {
        this.contractAddId = contractAddId;
    }

    public String getAttachmentAddress() {
        return attachmentAddress;
    }

    public void setAttachmentAddress(String attachmentAddress) {
        this.attachmentAddress = attachmentAddress;
    }

    public String getAttachmentAddressName() {
        return attachmentAddressName;
    }

    public void setAttachmentAddressName(String attachmentAddressName) {
        this.attachmentAddressName = attachmentAddressName;
    }

    public String getDistributionState() {
        return distributionState;
    }

    public void setDistributionState(String distributionState) {
        this.distributionState = distributionState;
    }

    public String getDistributionPersonId() {
        return distributionPersonId;
    }

    public void setDistributionPersonId(String distributionPersonId) {
        this.distributionPersonId = distributionPersonId;
    }

    public String getDistributionPersonName() {
        return distributionPersonName;
    }

    public void setDistributionPersonName(String distributionPersonName) {
        this.distributionPersonName = distributionPersonName;
    }

    public String getDistributionPersonCompanyId() {
        return distributionPersonCompanyId;
    }

    public void setDistributionPersonCompanyId(String distributionPersonCompanyId) {
        this.distributionPersonCompanyId = distributionPersonCompanyId;
    }

    public String getDistributionPersonPhone() {
        return distributionPersonPhone;
    }

    public void setDistributionPersonPhone(String distributionPersonPhone) {
        this.distributionPersonPhone = distributionPersonPhone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getTrusteeship() {
        return trusteeship;
    }

    public void setTrusteeship(String trusteeship) {
        this.trusteeship = trusteeship;
    }

    public String getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }
}
