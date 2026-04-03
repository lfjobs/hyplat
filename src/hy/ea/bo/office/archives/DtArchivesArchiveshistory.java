package hy.ea.bo.office.archives;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtArchivesArchiveshistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtArchivesArchiveshistory implements java.io.Serializable,BaseBean{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5524677558642872522L;
	private String historykey;
	private DtArchivesInventorylocation dtArchivesInventorylocation;
	private DtArchivesArchives dtArchivesArchives;
	private String historyid;
	private String inuser;
	private Date intime;
	private String outuser;
	private Date outtime;
	private String state;
	private String remark;
	private String intimestr;
	private String outtimestr;

	// Constructors


	/** default constructor */
	public DtArchivesArchiveshistory() {
	}

	/** minimal constructor */
	public DtArchivesArchiveshistory(String historykey,
			DtArchivesInventorylocation dtArchivesInventorylocation,
			DtArchivesArchives dtArchivesArchives, String historyid,
			String inuser, Date intime) {
		this.historykey = historykey;
		this.dtArchivesInventorylocation = dtArchivesInventorylocation;
		this.dtArchivesArchives = dtArchivesArchives;
		this.historyid = historyid;
		this.inuser = inuser;
		this.intime = intime;
	}

	/** full constructor */
	public DtArchivesArchiveshistory(String historykey,
			DtArchivesInventorylocation dtArchivesInventorylocation,
			DtArchivesArchives dtArchivesArchives, String historyid,
			String inuser, Date intime, String outuser, Date outtime,
			String state, String remark) {
		this.historykey = historykey;
		this.dtArchivesInventorylocation = dtArchivesInventorylocation;
		this.dtArchivesArchives = dtArchivesArchives;
		this.historyid = historyid;
		this.inuser = inuser;
		this.intime = intime;
		this.outuser = outuser;
		this.outtime = outtime;
		this.state = state;
		this.remark = remark;
	}

	// Property accessors

	public String getHistorykey() {
		return this.historykey;
	}

	public void setHistorykey(String historykey) {
		this.historykey = historykey;
	}

	public DtArchivesInventorylocation getDtArchivesInventorylocation() {
		return this.dtArchivesInventorylocation;
	}

	public void setDtArchivesInventorylocation(
			DtArchivesInventorylocation dtArchivesInventorylocation) {
		this.dtArchivesInventorylocation = dtArchivesInventorylocation;
	}

	public DtArchivesArchives getDtArchivesArchives() {
		return this.dtArchivesArchives;
	}

	public void setDtArchivesArchives(DtArchivesArchives dtArchivesArchives) {
		this.dtArchivesArchives = dtArchivesArchives;
	}

	public String getHistoryid() {
		return this.historyid;
	}

	public void setHistoryid(String historyid) {
		this.historyid = historyid;
	}

	public String getInuser() {
		return this.inuser;
	}

	public void setInuser(String inuser) {
		this.inuser = inuser;
	}

	public Date getIntime() {
		return this.intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public String getOutuser() {
		return this.outuser;
	}

	public void setOutuser(String outuser) {
		this.outuser = outuser;
	}

	public Date getOuttime() {
		return this.outtime;
	}

	public void setOuttime(Date outtime) {
		this.outtime = outtime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIntimestr() {
		return intimestr;
	}

	public void setIntimestr(String intimestr) {
		this.intimestr = intimestr;
	}

	public String getOuttimestr() {
		return outtimestr;
	}

	public void setOuttimestr(String outtimestr) {
		this.outtimestr = outtimestr;
	}

}