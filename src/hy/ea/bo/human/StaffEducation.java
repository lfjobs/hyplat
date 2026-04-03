/**
 * Education
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 
 * 学历学位
 * @author YJG
 * 
 */
public class StaffEducation implements BaseBean ,ExcelBean{
	private String educationKey;
	private String educationID;
	private String companyID;
	private String staffID;
	private Date educationEnd;//毕业日期
	private Date educationStart;//入学日期
	private String educationName;//学校（培训机构）名称
	private String majorType;//所学专业类型
	private String educationHistory;//学历
	private String educationStyle;//学习形式
	private String educationType;//教育类别
	private String provePerson;//证明人
	private String provePhone;//证明人电话
	private String assessor;// 审核人
	private String assessorCode;// 审核人编码
	private Date assessorDate;// 审核时间
	private String educationDesc;// 备注
	private String photo;// 附件
	
	/**
	 * param:filePhoto;
	 * modfy time :2010-10-28
	 * info:附件文件 
	 */
	private File filePhoto;
	private String filePhotoFileName;
	private String filePhotoContentType;
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "入学日期", "毕业日期", "学校（培训机构）名称", "所学专业类型", "学历",
				"学习形式", "教育类别", "证明人", "证明人电话", "审核人", "审核人员编号",
				"审核时间", "备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { String.format("%1$tF", educationStart),
				String.format("%1$tF", educationEnd), educationName,oMap.get(majorType) ,
				oMap.get(educationHistory),oMap.get(educationStyle) ,oMap.get(educationType) , provePerson,
				provePhone, assessor, assessorCode,
				String.format("%1$tF", assessorDate), educationDesc };
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getEducationKey() {
		return educationKey;
	}

	public void setEducationKey(String educationKey) {
		this.educationKey = educationKey;
	}

	public String getEducationID() {
		return educationID;
	}

	public void setEducationID(String educationID) {
		this.educationID = educationID;
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

	public Date getEducationEnd() {
		return educationEnd;
	}

	public void setEducationEnd(Date educationEnd) {
		this.educationEnd = educationEnd;
	}

	public Date getEducationStart() {
		return educationStart;
	}

	public void setEducationStart(Date educationStart) {
		this.educationStart = educationStart;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public String getMajorType() {
		return majorType;
	}

	public void setMajorType(String majorType) {
		this.majorType = majorType;
	}

	public String getEducationHistory() {
		return educationHistory;
	}

	public void setEducationHistory(String educationHistory) {
		this.educationHistory = educationHistory;
	}

	public String getEducationStyle() {
		return educationStyle;
	}

	public void setEducationStyle(String educationStyle) {
		this.educationStyle = educationStyle;
	}

	public String getEducationType() {
		return educationType;
	}

	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}

	public String getProvePerson() {
		return provePerson;
	}

	public void setProvePerson(String provePerson) {
		this.provePerson = provePerson;
	}

	public String getProvePhone() {
		return provePhone;
	}

	public void setProvePhone(String provePhone) {
		this.provePhone = provePhone;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public String getAssessorCode() {
		return assessorCode;
	}

	public void setAssessorCode(String assessorCode) {
		this.assessorCode = assessorCode;
	}

	public Date getAssessorDate() {
		return assessorDate;
	}

	public void setAssessorDate(Date assessorDate) {
		this.assessorDate = assessorDate;
	}

	public String getEducationDesc() {
		return educationDesc;
	}

	public void setEducationDesc(String educationDesc) {
		this.educationDesc = educationDesc;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
 

	public File getFilePhoto() {
		return filePhoto;
	}

	public void setFilePhoto(File filePhoto) {
		this.filePhoto = filePhoto;
	}

	public String getFilePhotoContentType() {
		return filePhotoContentType;
	}

	public void setFilePhotoContentType(String filePhotoContentType) {
		this.filePhotoContentType = filePhotoContentType;
	}

	public String getFilePhotoFileName() {
		return filePhotoFileName;
	}

	public void setFilePhotoFileName(String filePhotoFileName) {
		this.filePhotoFileName = filePhotoFileName;
	}

}
