package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 资料列表
 * @author zgzg
 *
 */
public class StaffDocumentation implements BaseBean ,ExcelBean{

	private String documentationkey;
	private String companyID;
	private String documentationID;
	private String staffID;
	private String documentationNumber;//资料编号
	private String documentationName;//资料名称
	private Date documentationManagerStart;//资料管理起时间
	private Date documentationManagerEnd;//资料管理止时间
	private String documentationBoxNumber;//资料盒编号
	private String documentationBoxName;//资料盒名称
	private String auditor;
	private String auditorNumber;//审核人人员编号
	private String auditorTime;
	private String ddesc;
	private String photo;
	private File    photos;
	private String  photosFileName;
	private String  photosContentType;
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "资料编号", "资料名称", "资料管理起时间", "资料管理止时间",
				"资料盒编号", "资料盒名称", "审核人", "审核人人员编号", "审核时间", "备注说明" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { documentationNumber, documentationName,
				String.format("%1$tF", documentationManagerStart),
				String.format("%1$tF", documentationManagerEnd),
				documentationBoxNumber, documentationBoxName,
				auditor, auditorNumber, auditorTime, ddesc };
		return properties;
	}

	public String getDocumentationkey() {
		return documentationkey;
	}

	public void setDocumentationkey(String documentationkey) {
		this.documentationkey = documentationkey;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getDocumentationID() {
		return documentationID;
	}

	public void setDocumentationID(String documentationID) {
		this.documentationID = documentationID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getDocumentationNumber() {
		return documentationNumber;
	}

	public void setDocumentationNumber(String documentationNumber) {
		this.documentationNumber = documentationNumber;
	}

	public String getDocumentationName() {
		return documentationName;
	}

	public void setDocumentationName(String documentationName) {
		this.documentationName = documentationName;
	}

	public Date getDocumentationManagerStart() {
		return documentationManagerStart;
	}

	public void setDocumentationManagerStart(Date documentationManagerStart) {
		this.documentationManagerStart = documentationManagerStart;
	}

	public Date getDocumentationManagerEnd() {
		return documentationManagerEnd;
	}

	public void setDocumentationManagerEnd(Date documentationManagerEnd) {
		this.documentationManagerEnd = documentationManagerEnd;
	}

	public String getDocumentationBoxNumber() {
		return documentationBoxNumber;
	}

	public void setDocumentationBoxNumber(String documentationBoxNumber) {
		this.documentationBoxNumber = documentationBoxNumber;
	}

	public String getDocumentationBoxName() {
		return documentationBoxName;
	}

	public void setDocumentationBoxName(String documentationBoxName) {
		this.documentationBoxName = documentationBoxName;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
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

	public String getDdesc() {
		return ddesc;
	}

	public void setDdesc(String ddesc) {
		this.ddesc = ddesc;
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

}
