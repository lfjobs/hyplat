package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DtMicroletterMenuContent entity. @author MyEclipse Persistence Tools
 */

public class DtMicroletterMenuContent implements java.io.Serializable ,BaseBean{

	// Fields

	private String microlettermenucontentkey;
	private DtMicroletterMenu dtMicroletterMenu;
	private String microlettermenucontentid;
	private BigDecimal microlettermenucontenttype;
	private String microlettermenucontent;
	private Date microlettermenucontentcdate;
	private Date microlettermenucontentudate;
	private String microlettermenucontentimageurl;
	
	private String microlettermenucontentlabel;
	private String microlettermenucontentdescribe;
	
	private File   photo;
	private String photoFileName;
	private String photoContentType;

	// Constructors

	/** default constructor */
	public DtMicroletterMenuContent() {
	}

	/** full constructor */
	public DtMicroletterMenuContent(DtMicroletterMenu dtMicroletterMenu,
			String microlettermenucontentid,
			BigDecimal microlettermenucontenttype,
			String microlettermenucontent, Date microlettermenucontentcdate,
			Date microlettermenucontentudate) {
		this.dtMicroletterMenu = dtMicroletterMenu;
		this.microlettermenucontentid = microlettermenucontentid;
		this.microlettermenucontenttype = microlettermenucontenttype;
		this.microlettermenucontent = microlettermenucontent;
		this.microlettermenucontentcdate = microlettermenucontentcdate;
		this.microlettermenucontentudate = microlettermenucontentudate;
	}

	// Property accessors

	public String getMicrolettermenucontentkey() {
		return this.microlettermenucontentkey;
	}

	public void setMicrolettermenucontentkey(String microlettermenucontentkey) {
		this.microlettermenucontentkey = microlettermenucontentkey;
	}

	public DtMicroletterMenu getDtMicroletterMenu() {
		return this.dtMicroletterMenu;
	}

	public void setDtMicroletterMenu(DtMicroletterMenu dtMicroletterMenu) {
		this.dtMicroletterMenu = dtMicroletterMenu;
	}

	public String getMicrolettermenucontentid() {
		return this.microlettermenucontentid;
	}

	public void setMicrolettermenucontentid(String microlettermenucontentid) {
		this.microlettermenucontentid = microlettermenucontentid;
	}

	public BigDecimal getMicrolettermenucontenttype() {
		return this.microlettermenucontenttype;
	}

	public void setMicrolettermenucontenttype(
			BigDecimal microlettermenucontenttype) {
		this.microlettermenucontenttype = microlettermenucontenttype;
	}

	public String getMicrolettermenucontent() {
		return this.microlettermenucontent;
	}

	public void setMicrolettermenucontent(String microlettermenucontent) {
		this.microlettermenucontent = microlettermenucontent;
	}

	public Date getMicrolettermenucontentcdate() {
		return this.microlettermenucontentcdate;
	}

	public void setMicrolettermenucontentcdate(Date microlettermenucontentcdate) {
		this.microlettermenucontentcdate = microlettermenucontentcdate;
	}

	public Date getMicrolettermenucontentudate() {
		return this.microlettermenucontentudate;
	}

	public void setMicrolettermenucontentudate(Date microlettermenucontentudate) {
		this.microlettermenucontentudate = microlettermenucontentudate;
	}

	public String getMicrolettermenucontentimageurl() {
		return microlettermenucontentimageurl;
	}

	public void setMicrolettermenucontentimageurl(
			String microlettermenucontentimageurl) {
		this.microlettermenucontentimageurl = microlettermenucontentimageurl;
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

	public String getMicrolettermenucontentlabel() {
		return microlettermenucontentlabel;
	}

	public void setMicrolettermenucontentlabel(String microlettermenucontentlabel) {
		this.microlettermenucontentlabel = microlettermenucontentlabel;
	}

	public String getMicrolettermenucontentdescribe() {
		return microlettermenucontentdescribe;
	}

	public void setMicrolettermenucontentdescribe(
			String microlettermenucontentdescribe) {
		this.microlettermenucontentdescribe = microlettermenucontentdescribe;
	}
	
}