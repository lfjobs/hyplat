package hy.ea.bo.office;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;

/**
 * 文件管理
 * @author Administrator
 *
 */
public class CorPhoto implements BaseBean{
	private String key;
	private String photoID;
    private String organizationID;
	private String companyID;
	private String companyName;
	private String photoCode;				//编码
	private String photoName;				//名称
	private String pnShort;				//名称
	private String photoDepict;				//题片主题描述
	private Date uploadTime;				//上传时间
	private String photoFile;				//图片文件
	private String remark;                 //备注
	private String sortTypePhoto;   //顺序
	private String creatorID;
	private String creatorName;
	private CorPhotoBox corPhotoBox;
	private File   photo;
	private String photoFileName;
	private String photoContentType;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPhotoID() {
		return photoID;
	}
	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getPhotoCode() {
		return photoCode;
	}
	public void setPhotoCode(String photoCode) {
		this.photoCode = photoCode;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoDepict() {
		return photoDepict;
	}
	public void setPhotoDepict(String photoDepict) {
		this.photoDepict = photoDepict;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	public CorPhotoBox getCorPhotoBox() {
		return corPhotoBox;
	}
	public void setCorPhotoBox(CorPhotoBox corPhotoBox) {
		this.corPhotoBox = corPhotoBox;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getPnShort() {
		return pnShort;
	}
	public void setPnShort(String pnShort) {
		this.pnShort = pnShort;
	}
	public String getSortTypePhoto() {
		return sortTypePhoto;
	}
	public void setSortTypePhoto(String sortTypePhoto) {
		this.sortTypePhoto = sortTypePhoto;
	}
	

	
}
