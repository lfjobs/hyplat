package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;

/**
 * 文件管理
 * @author Administrator
 *
 */
public class CorporationPhoto implements BaseBean ,ExcelBean,java.io.Serializable {
	private String corporationPhotoID;
	private String corporationPhotoKey;
    private String organizationID;
	private String companyID;
	private String corporationPhotoCode;				//编码
	private String corporationPhotoName;				//名称
	private String corporationPhotoDepict;				//题片主题描述
	private String shootingYear;				        //摄制年度
	private String PhotoFile;				            //图片文件
	private String remark;                              //备注
	
	private File   photo;
	private String photoFileName;
	private String photoContentType;
	

	public static String[] columnHeadings() {
		String[] titles = { "序号", "编码","名称","题片主题描述","摄制年度" ,"备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = {corporationPhotoCode,corporationPhotoName, corporationPhotoDepict,
				shootingYear,remark};
		return properties;
	}
	public String getCorporationPhotoID() {
		return corporationPhotoID;
	}
	public void setCorporationPhotoID(String corporationPhotoID) {
		this.corporationPhotoID = corporationPhotoID;
	}
	public String getCorporationPhotoKey() {
		return corporationPhotoKey;
	}
	public void setCorporationPhotoKey(String corporationPhotoKey) {
		this.corporationPhotoKey = corporationPhotoKey;
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
	public String getCorporationPhotoCode() {
		return corporationPhotoCode;
	}
	public void setCorporationPhotoCode(String corporationPhotoCode) {
		this.corporationPhotoCode = corporationPhotoCode;
	}
	public String getCorporationPhotoName() {
		return corporationPhotoName;
	}
	public void setCorporationPhotoName(String corporationPhotoName) {
		this.corporationPhotoName = corporationPhotoName;
	}
	public String getCorporationPhotoDepict() {
		return corporationPhotoDepict;
	}
	public void setCorporationPhotoDepict(String corporationPhotoDepict) {
		this.corporationPhotoDepict = corporationPhotoDepict;
	}
	public String getShootingYear() {
		return shootingYear;
	}
	public void setShootingYear(String shootingYear) {
		this.shootingYear = shootingYear;
	}
	public String getPhotoFile() {
		return PhotoFile;
	}
	public void setPhotoFile(String photoFile) {
		PhotoFile = photoFile;
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
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	
}
