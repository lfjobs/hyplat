package hy.ea.bo.office.archives;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtArchivesAttachment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtArchivesAttachment implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7729854567447099220L;
	private String attachmentkey;
	private DtArchivesArchives dtArchivesArchives;
	private String attachmentid;
	private String companyid;
	private String filename;
	private String filepath;
	private String extension;
	private Float filesize;
	private String creatuser;
	private Date createdate;
	private String modifyuser;
	private Date modifydate;

	// Constructors

	/** default constructor */
	public DtArchivesAttachment() {
	}

	/** minimal constructor */
	public DtArchivesAttachment(String attachmentkey,
			DtArchivesArchives dtArchivesArchives, String attachmentid,
			String companyid, String filename, String filepath,
			String extension, Float filesize, String creatuser, Date createdate) {
		this.attachmentkey = attachmentkey;
		this.dtArchivesArchives = dtArchivesArchives;
		this.attachmentid = attachmentid;
		this.companyid = companyid;
		this.filename = filename;
		this.filepath = filepath;
		this.extension = extension;
		this.filesize = filesize;
		this.creatuser = creatuser;
		this.createdate = createdate;
	}

	/** full constructor */
	public DtArchivesAttachment(String attachmentkey,
			DtArchivesArchives dtArchivesArchives, String attachmentid,
			String companyid, String filename, String filepath,
			String extension, Float filesize, String creatuser,
			Date createdate, String modifyuser, Date modifydate) {
		this.attachmentkey = attachmentkey;
		this.dtArchivesArchives = dtArchivesArchives;
		this.attachmentid = attachmentid;
		this.companyid = companyid;
		this.filename = filename;
		this.filepath = filepath;
		this.extension = extension;
		this.filesize = filesize;
		this.creatuser = creatuser;
		this.createdate = createdate;
		this.modifyuser = modifyuser;
		this.modifydate = modifydate;
	}

	// Property accessors

	public String getAttachmentkey() {
		return this.attachmentkey;
	}

	public void setAttachmentkey(String attachmentkey) {
		this.attachmentkey = attachmentkey;
	}

	public DtArchivesArchives getDtArchivesArchives() {
		return this.dtArchivesArchives;
	}

	public void setDtArchivesArchives(DtArchivesArchives dtArchivesArchives) {
		this.dtArchivesArchives = dtArchivesArchives;
	}

	public String getAttachmentid() {
		return this.attachmentid;
	}

	public void setAttachmentid(String attachmentid) {
		this.attachmentid = attachmentid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Float getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Float filesize) {
		this.filesize = filesize;
	}

	public String getCreatuser() {
		return this.creatuser;
	}

	public void setCreatuser(String creatuser) {
		this.creatuser = creatuser;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getModifyuser() {
		return this.modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public Date getModifydate() {
		return this.modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

}