package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * 单位图片.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ContactImg implements BaseBean ,Serializable {

	private String contactimgKey;
	private String contactimgID;
	private String ccompanyID;
	private String imgName;
	private String imgContent;
	private String imgFile;
	private Date ctime;
	private String cname;
	private Date utime;
	private String uname;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	
	public String getContactimgKey() {
		return contactimgKey;
	}

	public void setContactimgKey(String contactimgKey) {
		this.contactimgKey = contactimgKey;
	}

	public String getContactimgID() {
		return contactimgID;
	}

	public void setContactimgID(String contactimgID) {
		this.contactimgID = contactimgID;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgContent() {
		return imgContent;
	}

	public void setImgContent(String imgContent) {
		this.imgContent = imgContent;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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

}