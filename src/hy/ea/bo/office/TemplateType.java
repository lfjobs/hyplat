package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class TemplateType implements BaseBean ,java.io.Serializable{

	private String key;
	private String temptId;
	private String companyId;
	private String templateTypeName;//公文类型的代表字母
	private Date time;
	private String createrID;
	private String createrName;
	private String parentId;//父ID
	private int seq;
	private String module;//类别 比如合同还是公文或者其他

	private String sysSet;//预设00 预设  01正常


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTemptId() {
		return temptId;
	}

	public void setTemptId(String temptId) {
		this.temptId = temptId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
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

	public String getSysSet() {
		return sysSet;
	}

	public void setSysSet(String sysSet) {
		this.sysSet = sysSet;
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
}
