package hy.ea.bo.office.archives;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DtArchivesCataloguearchives entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtArchivesCataloguearchives implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8107021622410489202L;
	private String archivekey;
	private String archiveid;
	private String comanyid;
	private String parent;
	private String name;
	private String createuser;
	private Date createtime;
	private String modifyuser;
	private Date modifytime;
	private String remark;
	
	private String catemodule;//用来判断是哪个模块的类别
	
	@SuppressWarnings("rawtypes")
	private Set dtArchivesArchiveses = new HashSet(0);

	// Constructors

	/** default constructor */
	public DtArchivesCataloguearchives() {
	}

	/** minimal constructor */
	public DtArchivesCataloguearchives(String archivekey, String archiveid,
			String comanyid, String name, String createuser, Date createtime) {
		this.archivekey = archivekey;
		this.archiveid = archiveid;
		this.comanyid = comanyid;
		this.name = name;
		this.createuser = createuser;
		this.createtime = createtime;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public DtArchivesCataloguearchives(String archivekey, String archiveid,
			String comanyid, String parent, String name, String createuser,
			Date createtime, String modifyuser, Date modifytime, String remark,
			Set dtArchivesArchiveses) {
		this.archivekey = archivekey;
		this.archiveid = archiveid;
		this.comanyid = comanyid;
		this.parent = parent;
		this.name = name;
		this.createuser = createuser;
		this.createtime = createtime;
		this.modifyuser = modifyuser;
		this.modifytime = modifytime;
		this.remark = remark;
		this.dtArchivesArchiveses = dtArchivesArchiveses;
	}

	// Property accessors

	public String getArchivekey() {
		return this.archivekey;
	}

	public void setArchivekey(String archivekey) {
		this.archivekey = archivekey;
	}

	public String getArchiveid() {
		return this.archiveid;
	}

	public void setArchiveid(String archiveid) {
		this.archiveid = archiveid;
	}

	public String getComanyid() {
		return this.comanyid;
	}

	public void setComanyid(String comanyid) {
		this.comanyid = comanyid;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateuser() {
		return this.createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getModifyuser() {
		return this.modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@SuppressWarnings("rawtypes")
	public Set getDtArchivesArchiveses() {
		return this.dtArchivesArchiveses;
	}

	@SuppressWarnings("rawtypes")
	public void setDtArchivesArchiveses(Set dtArchivesArchiveses) {
		this.dtArchivesArchiveses = dtArchivesArchiveses;
	}

	public String getCatemodule() {
		return catemodule;
	}

	public void setCatemodule(String catemodule) {
		this.catemodule = catemodule;
	}

}