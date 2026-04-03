package mobile.tiantai.android.action.storeProduction.budgetSheet;

/**
 * DataDTO
 *
 * @author zch
 */
public class DataDTO {
    private String companyName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public DataDTO() {
    }

    public DataDTO(String companyName, String personnelID, String name, String sex, String sector, String phone, String registrationPlatformName, String registrationNo, String registerAccount, String pwd, String uploadProject, String promotionReasons, String distributionService, String serviceTracking, String businessPersonnel) {
        this.companyName = companyName;
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
    }
}
