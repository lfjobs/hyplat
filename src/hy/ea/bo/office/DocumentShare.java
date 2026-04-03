package hy.ea.bo.office;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class DocumentShare implements BaseBean {

	private String key;
	private String shareId;
	private String docId;
	private String companyId;
    private String groupCompanySn;
    private String shareType;//分享类型 当前公司：current；集团公司：group
    private Date shareTime;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public Date getShareTime() {
		return shareTime;
	}
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}

}
