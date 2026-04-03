package hy.ea.bo.ddsr;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Ddsrdiscuss entity. @author MyEclipse Persistence Tools
 */

public class Ddsrdiscuss implements java.io.Serializable {

	// Fields

	private String discKey;
	private ReDssrstudentDdsrresrecord reDssrstudentDdsrresrecord;
	private Ddsrdiscuss ddsrdiscuss;
	private String discCompanyid;
	private String discTheme;
	private String discContent;
	private Boolean discIsdelete;
	private String discPath;
	private Date discDatetime;
	private Integer discSuccess;
	private Integer discFail;
	private Set ddsrdiscusses = new HashSet(0);

	// Constructors

	/** default constructor */
	public Ddsrdiscuss() {
	}

	/** full constructor */
	public Ddsrdiscuss(ReDssrstudentDdsrresrecord reDssrstudentDdsrresrecord,
			Ddsrdiscuss ddsrdiscuss, String discCompanyid, String discTheme,
			String discContent, Boolean discIsdelete, String discPath,
			Date discDatetime, Integer discSuccess, Integer discFail,
			Set ddsrdiscusses) {
		this.reDssrstudentDdsrresrecord = reDssrstudentDdsrresrecord;
		this.ddsrdiscuss = ddsrdiscuss;
		this.discCompanyid = discCompanyid;
		this.discTheme = discTheme;
		this.discContent = discContent;
		this.discIsdelete = discIsdelete;
		this.discPath = discPath;
		this.discDatetime = discDatetime;
		this.discSuccess = discSuccess;
		this.discFail = discFail;
		this.ddsrdiscusses = ddsrdiscusses;
	}

	// Property accessors

	public String getDiscKey() {
		return this.discKey;
	}

	public void setDiscKey(String discKey) {
		this.discKey = discKey;
	}

	public ReDssrstudentDdsrresrecord getReDssrstudentDdsrresrecord() {
		return this.reDssrstudentDdsrresrecord;
	}

	public void setReDssrstudentDdsrresrecord(
			ReDssrstudentDdsrresrecord reDssrstudentDdsrresrecord) {
		this.reDssrstudentDdsrresrecord = reDssrstudentDdsrresrecord;
	}

	public Ddsrdiscuss getDdsrdiscuss() {
		return this.ddsrdiscuss;
	}

	public void setDdsrdiscuss(Ddsrdiscuss ddsrdiscuss) {
		this.ddsrdiscuss = ddsrdiscuss;
	}

	public String getDiscCompanyid() {
		return this.discCompanyid;
	}

	public void setDiscCompanyid(String discCompanyid) {
		this.discCompanyid = discCompanyid;
	}

	public String getDiscTheme() {
		return this.discTheme;
	}

	public void setDiscTheme(String discTheme) {
		this.discTheme = discTheme;
	}

	public String getDiscContent() {
		return this.discContent;
	}

	public void setDiscContent(String discContent) {
		this.discContent = discContent;
	}

	public Boolean getDiscIsdelete() {
		return this.discIsdelete;
	}

	public void setDiscIsdelete(Boolean discIsdelete) {
		this.discIsdelete = discIsdelete;
	}

	public String getDiscPath() {
		return this.discPath;
	}

	public void setDiscPath(String discPath) {
		this.discPath = discPath;
	}

	public Date getDiscDatetime() {
		return this.discDatetime;
	}

	public void setDiscDatetime(Date discDatetime) {
		this.discDatetime = discDatetime;
	}

	public Integer getDiscSuccess() {
		return this.discSuccess;
	}

	public void setDiscSuccess(Integer discSuccess) {
		this.discSuccess = discSuccess;
	}

	public Integer getDiscFail() {
		return this.discFail;
	}

	public void setDiscFail(Integer discFail) {
		this.discFail = discFail;
	}

	public Set getDdsrdiscusses() {
		return this.ddsrdiscusses;
	}

	public void setDdsrdiscusses(Set ddsrdiscusses) {
		this.ddsrdiscusses = ddsrdiscusses;
	}

}