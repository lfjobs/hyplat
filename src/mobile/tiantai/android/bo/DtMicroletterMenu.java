package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DtMicroletterMenu entity. @author MyEclipse Persistence Tools
 */

public class DtMicroletterMenu implements java.io.Serializable ,BaseBean{

	// Fields

	private String microlettermenukey;//逻辑主键
	private String microlettermenuid;//菜单ID
	private String companyid;//公司ID
	private String microlettermenupid;//菜单父ID
	private String microlettermenuname;//菜单名称
	private String microlettermenutext;//菜单文本描述
	private String microlettermenuimageurl;//菜单图片描述
	private Date microlettermenucdate;//菜单创建时间
	private Date microlettermenuudate;//菜单更新时间
	private BigDecimal microlettermenulevel;//菜单层级
	private BigDecimal microlettermenustatus;//菜单状态
	private Set dtMicroletterMenuContents = new HashSet(0);

	// Constructors

	/** default constructor */
	public DtMicroletterMenu() {
	}

	/** full constructor */
	public DtMicroletterMenu(String microlettermenuid, String companyid,
			String microlettermenupid, String microlettermenuname,
			String microlettermenutext, String microlettermenuimageurl,
			Date microlettermenucdate, Date microlettermenuudate,
			BigDecimal microlettermenulevel, BigDecimal microlettermenustatus,
			Set dtMicroletterMenuContents) {
		this.microlettermenuid = microlettermenuid;
		this.companyid = companyid;
		this.microlettermenupid = microlettermenupid;
		this.microlettermenuname = microlettermenuname;
		this.microlettermenutext = microlettermenutext;
		this.microlettermenuimageurl = microlettermenuimageurl;
		this.microlettermenucdate = microlettermenucdate;
		this.microlettermenuudate = microlettermenuudate;
		this.microlettermenulevel = microlettermenulevel;
		this.microlettermenustatus = microlettermenustatus;
		this.dtMicroletterMenuContents = dtMicroletterMenuContents;
	}

	// Property accessors

	public String getMicrolettermenukey() {
		return this.microlettermenukey;
	}

	public void setMicrolettermenukey(String microlettermenukey) {
		this.microlettermenukey = microlettermenukey;
	}

	public String getMicrolettermenuid() {
		return this.microlettermenuid;
	}

	public void setMicrolettermenuid(String microlettermenuid) {
		this.microlettermenuid = microlettermenuid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getMicrolettermenupid() {
		return this.microlettermenupid;
	}

	public void setMicrolettermenupid(String microlettermenupid) {
		this.microlettermenupid = microlettermenupid;
	}

	public String getMicrolettermenuname() {
		return this.microlettermenuname;
	}

	public void setMicrolettermenuname(String microlettermenuname) {
		this.microlettermenuname = microlettermenuname;
	}

	public String getMicrolettermenutext() {
		return this.microlettermenutext;
	}

	public void setMicrolettermenutext(String microlettermenutext) {
		this.microlettermenutext = microlettermenutext;
	}

	public String getMicrolettermenuimageurl() {
		return this.microlettermenuimageurl;
	}

	public void setMicrolettermenuimageurl(String microlettermenuimageurl) {
		this.microlettermenuimageurl = microlettermenuimageurl;
	}

	public Date getMicrolettermenucdate() {
		return this.microlettermenucdate;
	}

	public void setMicrolettermenucdate(Date microlettermenucdate) {
		this.microlettermenucdate = microlettermenucdate;
	}

	public Date getMicrolettermenuudate() {
		return this.microlettermenuudate;
	}

	public void setMicrolettermenuudate(Date microlettermenuudate) {
		this.microlettermenuudate = microlettermenuudate;
	}

	public BigDecimal getMicrolettermenulevel() {
		return this.microlettermenulevel;
	}

	public void setMicrolettermenulevel(BigDecimal microlettermenulevel) {
		this.microlettermenulevel = microlettermenulevel;
	}

	public BigDecimal getMicrolettermenustatus() {
		return this.microlettermenustatus;
	}

	public void setMicrolettermenustatus(BigDecimal microlettermenustatus) {
		this.microlettermenustatus = microlettermenustatus;
	}

	public Set getDtMicroletterMenuContents() {
		return this.dtMicroletterMenuContents;
	}

	public void setDtMicroletterMenuContents(Set dtMicroletterMenuContents) {
		this.dtMicroletterMenuContents = dtMicroletterMenuContents;
	}

}