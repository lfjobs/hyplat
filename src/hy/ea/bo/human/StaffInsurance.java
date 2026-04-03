package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 社会保险
 * @author zg
 *
 */
public class StaffInsurance implements BaseBean ,ExcelBean{
	private String insuranceKey;
	private String insuranceID;
	private String companyID;
	private String staffID;
	private Date startTime;// 保险开始时间
	private Date endTime;// 保险结束时间
	private String companyAdress;// 购买单位地址
	private String endowment;// 有无养老保险
	private String medical;// 有无医疗保险
	private String maternity;// 有无生育保险
	private String unemployment;// 有无失来保险
	private String accident;// 有无意外保险
	private String amount;// 保险金额
	private String phone;// 保险电话
	private String organization;// 部门
	private String auditor;// 审核人

	private String insuranceName;// 保险名称
	private String insuranceUnit;//保险单位
	private String effectiveTime;// 有效时间
	private String referenceCode;// 审核人人员编号
	private Date verifyTime;// 审核时间
	private String fileAttachments;// 附件
	private String insuranceDesc;// 备注
    private String photo;
    private File    photos;
    private String photosFileName;
	private String  photosContentType;
    
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "购买时间", "购买保险名称","保险单位", "有效时间", "保险金额", "保险电话",
				 "审核人", "审核人人员编号", "审核时间", "备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { String.format("%1$tF", startTime),
				insuranceName,insuranceUnit, effectiveTime, amount, phone,
				 auditor, referenceCode,
				String.format("%1$tF", verifyTime), insuranceDesc };
		return properties;
	}

	public String getInsuranceKey() {
		return insuranceKey;
	}

	public void setInsuranceKey(String insuranceKey) {
		this.insuranceKey = insuranceKey;
	}

	public String getInsuranceID() {
		return insuranceID;
	}

	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCompanyAdress() {
		return companyAdress;
	}

	public void setCompanyAdress(String companyAdress) {
		this.companyAdress = companyAdress;
	}

	public String getEndowment() {
		return endowment;
	}

	public void setEndowment(String endowment) {
		this.endowment = endowment;
	}

	public String getMedical() {
		return medical;
	}

	public void setMedical(String medical) {
		this.medical = medical;
	}

	public String getMaternity() {
		return maternity;
	}

	public void setMaternity(String maternity) {
		this.maternity = maternity;
	}

	public String getUnemployment() {
		return unemployment;
	}

	public void setUnemployment(String unemployment) {
		this.unemployment = unemployment;
	}

	public String getAccident() {
		return accident;
	}

	public void setAccident(String accident) {
		this.accident = accident;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getFileAttachments() {
		return fileAttachments;
	}

	public void setFileAttachments(String fileAttachments) {
		this.fileAttachments = fileAttachments;
	}

	public String getInsuranceDesc() {
		return insuranceDesc;
	}

	public void setInsuranceDesc(String insuranceDesc) {
		this.insuranceDesc = insuranceDesc;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public File getPhotos() {
		return photos;
	}

	public void setPhotos(File photos) {
		this.photos = photos;
	}

	public String getPhotosContentType() {
		return photosContentType;
	}

	public void setPhotosContentType(String photosContentType) {
		this.photosContentType = photosContentType;
	}

	public String getInsuranceUnit() {
		return insuranceUnit;
	}

	public void setInsuranceUnit(String insuranceUnit) {
		this.insuranceUnit = insuranceUnit;
	}

	public String getPhotosFileName() {
		return photosFileName;
	}

	public void setPhotosFileName(String photosFileName) {
		this.photosFileName = photosFileName;
	}



}
