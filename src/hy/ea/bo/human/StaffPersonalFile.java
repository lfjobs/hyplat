/**
 * PersonalRecord
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 人事档案
 * 
 * @author YJG
 */
public class StaffPersonalFile implements BaseBean ,ExcelBean{
	private String recordKey;
	private String recordID;
	private String staffID;
	private String companyID;
	private String recordCode;//档案编号
	private String recordName;//档案名称
	private Date controlStartDate;//管理起时间
	private Date controlEndDate;//管理止时间
	private String dutyOfficer;//档案责任人
	private String recordBoxCode;//档案盒编号
	private String recordBoxName;//档案盒名称
	private String assessor;// 审核人
	private String assessorCode;// 审核人编码
	private Date assessorDate;// 审核时间
	private String photo;// 附件

	private File filePhoto;
	private String filePhotoFileName;
    private String filePhotoContentType;
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "档案编号", "档案名称", "管理起时间", "管理止时间", "档案责任人",
				"档案盒编号", "档案盒名称", "审核人", "审核人人员编号", "审核时间" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { recordCode, recordName,
				String.format("%1$tF", controlStartDate),
				String.format("%1$tF", controlEndDate), dutyOfficer,
				recordBoxCode, recordBoxName,assessor,
				assessorCode, String.format("%1$tF", assessorDate) };
		return properties;
	}

	public String getRecordKey() {
		return recordKey;
	}

	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public Date getControlStartDate() {
		return controlStartDate;
	}

	public void setControlStartDate(Date controlStartDate) {
		this.controlStartDate = controlStartDate;
	}

	public Date getControlEndDate() {
		return controlEndDate;
	}

	public void setControlEndDate(Date controlEndDate) {
		this.controlEndDate = controlEndDate;
	}

	public String getDutyOfficer() {
		return dutyOfficer;
	}

	public void setDutyOfficer(String dutyOfficer) {
		this.dutyOfficer = dutyOfficer;
	}

	public String getRecordBoxCode() {
		return recordBoxCode;
	}

	public void setRecordBoxCode(String recordBoxCode) {
		this.recordBoxCode = recordBoxCode;
	}

	public String getRecordBoxName() {
		return recordBoxName;
	}

	public void setRecordBoxName(String recordBoxName) {
		this.recordBoxName = recordBoxName;
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



	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
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
