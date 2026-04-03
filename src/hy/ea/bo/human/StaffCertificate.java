package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 证件列表
 * @author zgzg
 *
 */
public class StaffCertificate implements BaseBean,ExcelBean ,java.io.Serializable{

	private String credentialskey;
	private String companyID;
	private String credentialsID;
	private String staffID;
	private String credentialsName;//证件名称
	private String credentialsType;//证件类型
	private Date invalidateStart;//有效起时间
	private Date invalidateEnd;//有效止时间
	private Date invalidate;
	private String credentialsNo;//证件编号
	private String recordsNumber;//档案编号
	private String address;//住址
	private String organ;//发证机关（单位）
	private String credentialsrecordNo;//证件资料文号
	private String appendixNumber;//附件编号
	private String auditor;//审核人
	private String auditorNumber;//审核人人员编号
	private String auditorTime;//审核时间
	private String auditorState; // 状态[00:正常/审核通过 01:删除 02:草稿 03:审核中 04:驳回]
	private String appendix; //附录
	private String credentialsDesc;  //备注
	private String photo;
	private File photos;
	private String photosFileName;
	private String photosContentType;

	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "证件名称", "证件类型", "有效起时间", "有效止时间", "证件编号",
				"档案编号", "住址", "发证机关（单位）", "证件资料文号", "附件编号",  "审核人",
				"审核人人员编号", "审核时间", "备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { credentialsName, credentialsType,
				String.format("%1$tF", invalidateStart),
				String.format("%1$tF", invalidateEnd), credentialsNo,
				recordsNumber, address, organ, credentialsrecordNo,
				appendixNumber,auditor,auditorNumber,
				String.format("%1$tF", invalidate), credentialsDesc };
		return properties;
	}

	public String getCredentialskey() {
		return credentialskey;
	}

	public void setCredentialskey(String credentialskey) {
		this.credentialskey = credentialskey;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCredentialsID() {
		return credentialsID;
	}

	public void setCredentialsID(String credentialsID) {
		this.credentialsID = credentialsID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCredentialsName() {
		return credentialsName;
	}

	public void setCredentialsName(String credentialsName) {
		this.credentialsName = credentialsName;
	}

	public String getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}

	public Date getInvalidateStart() {
		return invalidateStart;
	}

	public void setInvalidateStart(Date invalidateStart) {
		this.invalidateStart = invalidateStart;
	}

	public Date getInvalidateEnd() {
		return invalidateEnd;
	}

	public void setInvalidateEnd(Date invalidateEnd) {
		this.invalidateEnd = invalidateEnd;
	}

	public Date getInvalidate() {
		return invalidate;
	}

	public void setInvalidate(Date invalidate) {
		this.invalidate = invalidate;
	}

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

	public String getRecordsNumber() {
		return recordsNumber;
	}

	public void setRecordsNumber(String recordsNumber) {
		this.recordsNumber = recordsNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getCredentialsrecordNo() {
		return credentialsrecordNo;
	}

	public void setCredentialsrecordNo(String credentialsrecordNo) {
		this.credentialsrecordNo = credentialsrecordNo;
	}

	public String getAppendixNumber() {
		return appendixNumber;
	}

	public void setAppendixNumber(String appendixNumber) {
		this.appendixNumber = appendixNumber;
	}
	public String getAuditorNumber() {
		return auditorNumber;
	}

	public void setAuditorNumber(String auditorNumber) {
		this.auditorNumber = auditorNumber;
	}

	public String getAuditorTime() {
		return auditorTime;
	}

	public void setAuditorTime(String auditorTime) {
		this.auditorTime = auditorTime;
	}

	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}

	public String getCredentialsDesc() {
		return credentialsDesc;
	}

	public void setCredentialsDesc(String credentialsDesc) {
		this.credentialsDesc = credentialsDesc;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
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

	public String getPhotosFileName() {
		return photosFileName;
	}

	public void setPhotosFileName(String photosFileName) {
		this.photosFileName = photosFileName;
	}

	public String getAuditorState() {
		return auditorState;
	}

	public void setAuditorState(String auditorState) {
		this.auditorState = auditorState;
	}
}
