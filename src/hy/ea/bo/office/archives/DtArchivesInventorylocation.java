package hy.ea.bo.office.archives;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DtArchivesInventorylocation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtArchivesInventorylocation implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5311821801236321573L;
	private String locationkey;
	private String locationid;
	private String locationname;
	private String companyideas;
	private String userid;
	private Date createdate;
	private String remark;
	@SuppressWarnings("rawtypes")
	private Set dtArchivesArchiveshistories = new HashSet(0);
	private String locmodule;

	// Constructors

	/** default constructor */
	public DtArchivesInventorylocation() {
	}

	/** minimal constructor */
	public DtArchivesInventorylocation(String locationkey, String locationid,
			String locationname, String companyideas, String userid,
			Date createdate) {
		this.locationkey = locationkey;
		this.locationid = locationid;
		this.locationname = locationname;
		this.companyideas = companyideas;
		this.userid = userid;
		this.createdate = createdate;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public DtArchivesInventorylocation(String locationkey, String locationid,
			String locationname, String companyideas, String userid,
			Date createdate, String remark, Set dtArchivesArchiveshistories) {
		this.locationkey = locationkey;
		this.locationid = locationid;
		this.locationname = locationname;
		this.companyideas = companyideas;
		this.userid = userid;
		this.createdate = createdate;
		this.remark = remark;
		this.dtArchivesArchiveshistories = dtArchivesArchiveshistories;
	}

	// Property accessors

	public String getLocationkey() {
		return this.locationkey;
	}

	public void setLocationkey(String locationkey) {
		this.locationkey = locationkey;
	}

	public String getLocationid() {
		return this.locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getLocationname() {
		return this.locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getCompanyideas() {
		return this.companyideas;
	}

	public void setCompanyideas(String companyideas) {
		this.companyideas = companyideas;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@SuppressWarnings("rawtypes")
	public Set getDtArchivesArchiveshistories() {
		return this.dtArchivesArchiveshistories;
	}
	@SuppressWarnings("rawtypes")
	public void setDtArchivesArchiveshistories(Set dtArchivesArchiveshistories) {
		this.dtArchivesArchiveshistories = dtArchivesArchiveshistories;
	}

	public String getLocmodule() {
		return locmodule;
	}

	public void setLocmodule(String locmodule) {
		this.locmodule = locmodule;
	}

}