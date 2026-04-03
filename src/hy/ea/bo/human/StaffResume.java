package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 个人履历
 */
public class StaffResume implements BaseBean ,ExcelBean{
	private String recordKey;
	private String recordID;
	private String companyID;
	private String staffID;
	private String resumeID;// 个人简历id
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private String companyName;// 工作单位
	private String ccompanyID;// 工作单位ID
	private String position;// 职务
	private String duties;// 工作内容
	private String reference;// 证明人
	private String referencePhon;// 备注吧

	private String postName;// 岗位名称
	private String postCase;// 岗位情况
	private String postAddress;// 单位地址
	private String fileNumber;// 文件号
	private String fileAttachments;// 文件附件
	private String referenceCode;// 审核人人员编号
	private Date referenceTime;// 审核时间
    private String photo;
    
    private File filePhoto;
    private String filePhotoFileName;
    private String filePhotoContentType;
    private String degree;//进度zzl

	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "起始时间", "截止时间", "工作单位", "岗位名称", "岗位情况", "职务",
				"单位地址", "工作内容及职责", "文件号", "审核人", "审核人人员编号", "审核时间",
				"备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { String.format("%1$tF", startTime),
				String.format("%1$tF", endTime), companyName, postName,
				oMap.get(postCase), position, postAddress, duties, fileNumber,
				reference, referenceCode,
				String.format("%1$tF", referenceTime), referencePhon };
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
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

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getResumeID() {
		return resumeID;
	}

	public void setResumeID(String resumeID) {
		this.resumeID = resumeID;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getReferencePhon() {
		return referencePhon;
	}

	public void setReferencePhon(String referencePhon) {
		this.referencePhon = referencePhon;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostCase() {
		return postCase;
	}

	public void setPostCase(String postCase) {
		this.postCase = postCase;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getFileAttachments() {
		return fileAttachments;
	}

	public void setFileAttachments(String fileAttachments) {
		this.fileAttachments = fileAttachments;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public Date getReferenceTime() {
		return referenceTime;
	}

	public void setReferenceTime(Date referenceTime) {
		this.referenceTime = referenceTime;
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

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	 
	
}
