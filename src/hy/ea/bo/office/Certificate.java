package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
/**
 * 单位证书管理
 * @author Administrator
 *
 */
public class Certificate implements BaseBean ,ExcelBean,java.io.Serializable{
	private String certificateID;
	private String certificatkey;
	private String companyID;				 //公司ID
	private String ccompanyID;               //来往单位ID
	private String certificateCode;          //证件编号
	private String certificateType;          //证件类型
	private String certificateName;          //证件名称
	private Date   certificateTiime;         //发件时间
	private Date   availabilityDate ;        //有效期
	private String certificateDepth;         //发证部门
	private String certificateAccessory ;    //证书附件
	private String certificateLocation;      //存放地点
	private String responsible;              //责任人
	private String certificateCopyNumber;    //证件副本数
	private String remark;                   //备注
	private File photo;
	private String photoFileName;
	private String photoContentType;
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "证件编号","证件类型","证件名称","发件时间" , "有效期", "发证部门", "证件附件","存放地点","责任人",
				"证件副本数","备注"};
		return titles;
	}

	public String[] properties() {
		String[] properties = {certificateCode,certificateType,certificateName,String.format("%1$tF", certificateTiime),String.format("%1$tF", availabilityDate),
				certificateDepth,
				certificateAccessory,certificateLocation,
				responsible,certificateCopyNumber,remark};
		return properties;
	}


	public String getCertificateID() {
		return certificateID;
	}


	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}

	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getCertificateCode() {
		return certificateCode;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public Date getCertificateTiime() {
		return certificateTiime;
	}
	public void setCertificateTiime(Date certificateTiime) {
		this.certificateTiime = certificateTiime;
	}
	public String getCertificateDepth() {
		return certificateDepth;
	}
	public void setCertificateDepth(String certificateDepth) {
		this.certificateDepth = certificateDepth;
	}
	public String getCertificateAccessory() {
		return certificateAccessory;
	}
	public void setCertificateAccessory(String certificateAccessory) {
		this.certificateAccessory = certificateAccessory;
	}
	public String getCertificateLocation() {
		return certificateLocation;
	}
	public void setCertificateLocation(String certificateLocation) {
		this.certificateLocation = certificateLocation;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	public String getCertificateCopyNumber() {
		return certificateCopyNumber;
	}
	public void setCertificateCopyNumber(String certificateCopyNumber) {
		this.certificateCopyNumber = certificateCopyNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCertificatkey() {
		return certificatkey;
	}
	public void setCertificatkey(String certificatkey) {
		this.certificatkey = certificatkey;
	}
	public Date getAvailabilityDate() {
		return availabilityDate;
	}
	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}
	
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
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
}
