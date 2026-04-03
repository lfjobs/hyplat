package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class ArchiveDocType implements BaseBean ,java.io.Serializable{

	private String key;
	private String adtId;
	private String typeName;
	private Date time;
	private String createrID;
	private String createrName;
	private String companyID;
	private String companyName;
	private String parentId;//父ID
	private int seq;
	private String arnum;//档号
	private String module;
	private String barCode;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
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

	public String getArnum() {
		return arnum;
	}

	public void setArnum(String arnum) {
		this.arnum = arnum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
}
