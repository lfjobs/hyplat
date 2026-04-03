package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 车辆CNG信息(改装)
 * 
 * @author Administrator
 * 
 */
public class CarCNG implements BaseBean, ExcelBean,java.io.Serializable {
	private String cngKey;// 数据库主键
	private String cngID;// 业务主键
	private String companyID;
	private String organizationID;
	private String carID;// 外键
	private String refitName;// 改装厂名称
	private String refitPhone;// 改装厂电话
	private String refitContact;// 改装厂联系人
	private String refitCompany;// 改装企业名称
	private String refitLicenseCode;// 改装许可证
	private Date refitDate;// 改装时间
	private String refitAgoFuel;// 改装前燃料种类
	private String refitAfterFuel;// 改装后燃料种类
	private String cylinderType;// 气瓶型号
	private String refitEligibleCode;// 改装合格证号
	private String cylinderEligibleCode;// 气瓶改装合格证号
	private String cylinderDetectCode;// 气瓶定期检验登记证号
	private Date cylinderDetectDate;// 气瓶检验时间
	private String cylinderDetectName;// 气瓶检测厂名称
	private String cylinderDetectPhone;// 气瓶检测厂电话
	private String cylinderDetectContact;// 气瓶检测厂责任人
	private String remarks; // 备注
	
	private String carType;//车牌类型
	private String  carNum;//车牌号
	private String engineNum;//发动机号
	private String carPeople;//负责人

	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","发动机号","负责人","改装厂名称", "改装厂电话", "改装厂联系人", "改装企业名称",
				"改装许可证", "改装时间", " 改装前燃料种类", "改装后燃料种类", "气瓶型号", "改装合格证号",
				"气瓶改装合格证号", "气瓶定期检验登记证号", "气瓶检验时间", "气瓶检测厂名称", "气瓶检测厂电话",
				"气瓶检测厂责任人", "备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { refitName, refitPhone, refitContact,
				refitCompany, refitLicenseCode, String.format("%1$tF",refitDate),
				refitAgoFuel, refitAfterFuel, cylinderType, refitEligibleCode,
				cylinderEligibleCode, cylinderDetectCode,
				String.format("%1$tF",cylinderDetectDate), cylinderDetectName,
				cylinderDetectPhone, cylinderDetectContact, remarks };
		return properties;
	}

	public String getCngKey() {
		return cngKey;
	}

	public void setCngKey(String cngKey) {
		this.cngKey = cngKey;
	}

	public String getCngID() {
		return cngID;
	}

	public void setCngID(String cngID) {
		this.cngID = cngID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getRefitName() {
		return refitName;
	}

	public void setRefitName(String refitName) {
		this.refitName = refitName;
	}

	public String getRefitPhone() {
		return refitPhone;
	}

	public void setRefitPhone(String refitPhone) {
		this.refitPhone = refitPhone;
	}
	public String getRefitContact() {
		return refitContact;
	}
	public void setRefitContact(String refitContact) {
		this.refitContact = refitContact;
	}
	public String getRefitCompany() {
		return refitCompany;
	}
	public void setRefitCompany(String refitCompany) {
		this.refitCompany = refitCompany;
	}
	public String getRefitLicenseCode() {
		return refitLicenseCode;
	}
	public void setRefitLicenseCode(String refitLicenseCode) {
		this.refitLicenseCode = refitLicenseCode;
	}
	public Date getRefitDate() {
		return refitDate;
	}
	public void setRefitDate(Date refitDate) {
		this.refitDate = refitDate;
	}
	public String getRefitAgoFuel() {
		return refitAgoFuel;
	}
	public void setRefitAgoFuel(String refitAgoFuel) {
		this.refitAgoFuel = refitAgoFuel;
	}
	public String getRefitAfterFuel() {
		return refitAfterFuel;
	}
	public void setRefitAfterFuel(String refitAfterFuel) {
		this.refitAfterFuel = refitAfterFuel;
	}
	public String getCylinderType() {
		return cylinderType;
	}
	public void setCylinderType(String cylinderType) {
		this.cylinderType = cylinderType;
	}
	public String getRefitEligibleCode() {
		return refitEligibleCode;
	}
	public void setRefitEligibleCode(String refitEligibleCode) {
		this.refitEligibleCode = refitEligibleCode;
	}
	public String getCylinderEligibleCode() {
		return cylinderEligibleCode;
	}
	public void setCylinderEligibleCode(String cylinderEligibleCode) {
		this.cylinderEligibleCode = cylinderEligibleCode;
	}
	public String getCylinderDetectCode() {
		return cylinderDetectCode;
	}
	public void setCylinderDetectCode(String cylinderDetectCode) {
		this.cylinderDetectCode = cylinderDetectCode;
	}
	public Date getCylinderDetectDate() {
		return cylinderDetectDate;
	}
	public void setCylinderDetectDate(Date cylinderDetectDate) {
		this.cylinderDetectDate = cylinderDetectDate;
	}
	public String getCylinderDetectName() {
		return cylinderDetectName;
	}
	public void setCylinderDetectName(String cylinderDetectName) {
		this.cylinderDetectName = cylinderDetectName;
	}
	public String getCylinderDetectPhone() {
		return cylinderDetectPhone;
	}
	public void setCylinderDetectPhone(String cylinderDetectPhone) {
		this.cylinderDetectPhone = cylinderDetectPhone;
	}
	public String getCylinderDetectContact() {
		return cylinderDetectContact;
	}
	public void setCylinderDetectContact(String cylinderDetectContact) {
		this.cylinderDetectContact = cylinderDetectContact;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public String getCarPeople() {
		return carPeople;
	}
	public void setCarPeople(String carPeople) {
		this.carPeople = carPeople;
	}
}
