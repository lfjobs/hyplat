package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 人员身体状况
 * @author zg
 *
 */
public class StaffPhysicalCondition implements BaseBean ,ExcelBean{
	private String conditionrKey;
	private String conditionrID;
	private String companyID;
	private String staffID;

	private Date examinationTime;// 体检时间
	private String examinationHospital;// 体检医院
	private String bloodType;// 血型
	private String weight;// 体重
	private String height;// 身高
	private String conditions;// 身状况
	private String communicableDiseases;// 有无传染病
	private String auditor;// 审核人
	private String conditionDesc;// 备注
	private Date verifyTime;// 审核时间

	private String details;// 体检内容
	private String indicator;// 体检指标
	private String normal;// 是否正常
	private String doctorAdvice;// 医生意见
	private String doctorName;// 医生姓名
	private String referenceCode;// 审核人人员编号
	private String orderedIndicator;// 要求体检指标
	private String photo;// 附件
	
	private File filephoto;//附件
	private String filephotoFileName;
	private String filephotoContentType;
    
	
	
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "体检时间", "体检医院", "体检内容", "体检指标", "是否正常",
				"医生意见", "医生姓名", "审核人", "审核人人员编号", "审核时间", "要求体检指标",
				"备注" };
		return titles;
	}
	public String[] properties() {
		String[] properties = { String.format("%1$tF", examinationTime),
				examinationHospital, oMap.get(details), indicator, normal, doctorAdvice,
				doctorName, auditor, referenceCode,
				String.format("%1$tF", verifyTime), orderedIndicator,
				conditionDesc };
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getDoctorAdvice() {
		return doctorAdvice;
	}

	public void setDoctorAdvice(String doctorAdvice) {
		this.doctorAdvice = doctorAdvice;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getOrderedIndicator() {
		return orderedIndicator;
	}

	public void setOrderedIndicator(String orderedIndicator) {
		this.orderedIndicator = orderedIndicator;
	}



	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getConditionrKey() {
		return conditionrKey;
	}

	public void setConditionrKey(String conditionrKey) {
		this.conditionrKey = conditionrKey;
	}

	public String getConditionrID() {
		return conditionrID;
	}

	public void setConditionrID(String conditionrID) {
		this.conditionrID = conditionrID;
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

	public Date getExaminationTime() {
		return examinationTime;
	}

	public void setExaminationTime(Date examinationTime) {
		this.examinationTime = examinationTime;
	}

	public String getExaminationHospital() {
		return examinationHospital;
	}

	public void setExaminationHospital(String examinationHospital) {
		this.examinationHospital = examinationHospital;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getCommunicableDiseases() {
		return communicableDiseases;
	}

	public void setCommunicableDiseases(String communicableDiseases) {
		this.communicableDiseases = communicableDiseases;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	public Date getVerifyTime(){
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	public File getFilephoto() {
		return filephoto;
	}
	public void setFilephoto(File filephoto) {
		this.filephoto = filephoto;
	}
	public String getFilephotoContentType() {
		return filephotoContentType;
	}
	public void setFilephotoContentType(String filephotoContentType) {
		this.filephotoContentType = filephotoContentType;
	}
	public String getFilephotoFileName() {
		return filephotoFileName;
	}
	public void setFilephotoFileName(String filephotoFileName) {
		this.filephotoFileName = filephotoFileName;
	}
	
}
