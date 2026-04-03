
package hy.ea.bo;

import hy.plat.bo.BaseBean;

/**
 * 公司详细信息表
 */
public class CDetail implements BaseBean{
	private String  detailKey;
	private String  detailID;
	private String 	companyID;
	private String 	companyAddress;//公司地址
	private String 	companyPhone;//公司电话
	private String 	companyManager;//公司负责人
	private String managertel;//负责人手机
	private String 	companyEmail;//公司邮箱
	private String logo;//公司logo @mz
	
	private String registrationNumber;//注册号
	private String licenseNumber;//营业执照
	private String licensePic;//营业执照图片
	private String bankNumber;//开户行名称
	private String bankAccount;//开户行账号
    private String province;   //开户行省
    private String city;//开户行市
	private String busiManagerID;//业务员

	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getDetailKey() {
		return detailKey;
	}
	public void setDetailKey(String detailKey) {
		this.detailKey = detailKey;
	}
	public String getDetailID() {
		return detailID;
	}
	public void setDetailID(String detailID) {
		this.detailID = detailID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyManager() {
		return companyManager;
	}
	public void setCompanyManager(String companyManager) {
		this.companyManager = companyManager;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getLicensePic() {
		return licensePic;
	}
	public void setLicensePic(String licensePic) {
		this.licensePic = licensePic;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getManagertel() {
		return managertel;
	}

	public void setManagertel(String managertel) {
		this.managertel = managertel;
	}

	public String getBusiManagerID() {
		return busiManagerID;
	}

	public void setBusiManagerID(String busiManagerID) {
		this.busiManagerID = busiManagerID;
	}
}
