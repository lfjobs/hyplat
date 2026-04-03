package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class ArchiveDocRelate implements BaseBean ,java.io.Serializable{

	private String key;
	private String adrId;
	private String docId;
	private Date time;
	private String createrID;
	private String createrName;
	private String companyID;
	private String adtId;//父ID
	private int seq;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAdtId() {
		return adtId;
	}

	public void setAdtId(String adtId) {
		this.adtId = adtId;
	}



	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCreaterID() {
		return createrID;
	}

	public void setCreaterID(String createrID) {
		this.createrID = createrID;
	}



	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}



	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getAdrId() {
		return adrId;
	}

	public void setAdrId(String adrId) {
		this.adrId = adrId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
}
