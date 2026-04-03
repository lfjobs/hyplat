package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarCylinderInformation entity. @author fhw
 */

public class DtCarCylinderInformation implements BaseBean {

	// Fields
/*车辆气瓶信息管理*/
	private String carCylinderKey;   //pk
	private String carCylinderId;   //气瓶业务主键
	private String companyid;
	private String staffID; //往来个人ID
	private String ccompanyID;//往来单位Id
	private String organizationid;
	private String cylinderNum;// 气瓶编号
	private String cylinderType;//类型
	private String designThickness;//设计壁厚
	private String cylinderModel;//型号
	private String manufactureCompany;//制造单位
	private Date leavefactorydate;//出厂日期
	private String volume;//容积
	private String weight;//重量
	private String nominalworkpressure;//公称工作压力
	private String hydraulicTestPressure;//水压试验压力
	private String texture;//材质
	private String fiber;//纤维
	private String resin;//树脂
	private String manufacturingcountry;//制造国别
	private String madecode;//制造代号；
	private String licensenumber;//车牌号码
	private String registrationcode;//登记代码
	private String certificateNumber;//登记证号
	private String area;//区域
	private String licenseplatetype;//厂牌型号
	private String chassisnumber;//车架号
	private String enginenumber;//发动机号
	private String installationunit;//安装单位
	private String installationnumber;//安装数量
	private String certifyingauthority;//发证机构
	private Date installationdate;//安装日期
	private Date issuedate;//发证日期
	private String status;//气瓶使用状态  01 表正常 00 已报废

	// Constructors

	/** default constructor */
	public DtCarCylinderInformation() {
	}

	/** full constructor */
	public DtCarCylinderInformation(String carCylinderId, String companyid,
			String organizationid, String cylinderNum, String cylinderType,
			String designThickness, String cylinderModel,
			String manufactureCompany, Date leavefactorydate, String volume,
			String weight, String nominalworkpressure,
			String hydraulicTestPressure, String texture, String fiber,
			String resin, String manufacturingcountry, String licensenumber,
			String registrationcode, String certificateNumber, String area,
			String licenseplatetype, String chassisnumber, String enginenumber,
			String installationunit, String installationnumber,
			String certifyingauthority, Date installationdate, Date issuedate) {
		this.carCylinderId = carCylinderId;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.cylinderNum = cylinderNum;
		this.cylinderType = cylinderType;
		this.designThickness = designThickness;
		this.cylinderModel = cylinderModel;
		this.manufactureCompany = manufactureCompany;
		this.leavefactorydate = leavefactorydate;
		this.volume = volume;
		this.weight = weight;
		this.nominalworkpressure = nominalworkpressure;
		this.hydraulicTestPressure = hydraulicTestPressure;
		this.texture = texture;
		this.fiber = fiber;
		this.resin = resin;
		this.manufacturingcountry = manufacturingcountry;
		this.licensenumber = licensenumber;
		this.registrationcode = registrationcode;
		this.certificateNumber = certificateNumber;
		this.area = area;
		this.licenseplatetype = licenseplatetype;
		this.chassisnumber = chassisnumber;
		this.enginenumber = enginenumber;
		this.installationunit = installationunit;
		this.installationnumber = installationnumber;
		this.certifyingauthority = certifyingauthority;
		this.installationdate = installationdate;
		this.issuedate = issuedate;
	}

	// Property accessors

	public String getCarCylinderKey() {
		return this.carCylinderKey;
	}

	public void setCarCylinderKey(String carCylinderKey) {
		this.carCylinderKey = carCylinderKey;
	}

	public String getCarCylinderId() {
		return this.carCylinderId;
	}

	public void setCarCylinderId(String carCylinderId) {
		this.carCylinderId = carCylinderId;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getCylinderNum() {
		return this.cylinderNum;
	}

	public void setCylinderNum(String cylinderNum) {
		this.cylinderNum = cylinderNum;
	}

	public String getCylinderType() {
		return this.cylinderType;
	}

	public void setCylinderType(String cylinderType) {
		this.cylinderType = cylinderType;
	}

	public String getDesignThickness() {
		return this.designThickness;
	}

	public void setDesignThickness(String designThickness) {
		this.designThickness = designThickness;
	}

	public String getCylinderModel() {
		return this.cylinderModel;
	}

	public void setCylinderModel(String cylinderModel) {
		this.cylinderModel = cylinderModel;
	}

	public String getManufactureCompany() {
		return this.manufactureCompany;
	}

	public void setManufactureCompany(String manufactureCompany) {
		this.manufactureCompany = manufactureCompany;
	}

	public Date getLeavefactorydate() {
		return this.leavefactorydate;
	}

	public void setLeavefactorydate(Date leavefactorydate) {
		this.leavefactorydate = leavefactorydate;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getNominalworkpressure() {
		return this.nominalworkpressure;
	}

	public void setNominalworkpressure(String nominalworkpressure) {
		this.nominalworkpressure = nominalworkpressure;
	}

	public String getHydraulicTestPressure() {
		return this.hydraulicTestPressure;
	}

	public void setHydraulicTestPressure(String hydraulicTestPressure) {
		this.hydraulicTestPressure = hydraulicTestPressure;
	}

	public String getTexture() {
		return this.texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getFiber() {
		return this.fiber;
	}

	public void setFiber(String fiber) {
		this.fiber = fiber;
	}

	public String getResin() {
		return this.resin;
	}

	public void setResin(String resin) {
		this.resin = resin;
	}

	public String getManufacturingcountry() {
		return this.manufacturingcountry;
	}

	public void setManufacturingcountry(String manufacturingcountry) {
		this.manufacturingcountry = manufacturingcountry;
	}

	public String getLicensenumber() {
		return this.licensenumber;
	}

	public void setLicensenumber(String licensenumber) {
		this.licensenumber = licensenumber;
	}

	public String getRegistrationcode() {
		return this.registrationcode;
	}

	public void setRegistrationcode(String registrationcode) {
		this.registrationcode = registrationcode;
	}

	public String getCertificateNumber() {
		return this.certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLicenseplatetype() {
		return this.licenseplatetype;
	}

	public void setLicenseplatetype(String licenseplatetype) {
		this.licenseplatetype = licenseplatetype;
	}

	public String getChassisnumber() {
		return this.chassisnumber;
	}

	public void setChassisnumber(String chassisnumber) {
		this.chassisnumber = chassisnumber;
	}

	public String getEnginenumber() {
		return this.enginenumber;
	}

	public void setEnginenumber(String enginenumber) {
		this.enginenumber = enginenumber;
	}

	public String getInstallationunit() {
		return this.installationunit;
	}

	public void setInstallationunit(String installationunit) {
		this.installationunit = installationunit;
	}

	public String getInstallationnumber() {
		return this.installationnumber;
	}

	public void setInstallationnumber(String installationnumber) {
		this.installationnumber = installationnumber;
	}

	public String getCertifyingauthority() {
		return this.certifyingauthority;
	}

	public void setCertifyingauthority(String certifyingauthority) {
		this.certifyingauthority = certifyingauthority;
	}

	public Date getInstallationdate() {
		return this.installationdate;
	}

	public void setInstallationdate(Date installationdate) {
		this.installationdate = installationdate;
	}

	public Date getIssuedate() {
		return this.issuedate;
	}

	public void setIssuedate(Date issuedate) {
		this.issuedate = issuedate;
	}

	public String getMadecode() {
		return madecode;
	}

	public void setMadecode(String madecode) {
		this.madecode = madecode;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}