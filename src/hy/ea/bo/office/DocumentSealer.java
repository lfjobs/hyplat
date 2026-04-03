package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DocumentSealer implements BaseBean,java.io.Serializable {


	private String key;
	private String id;
	private String docId;

	private String companyIDofSealer;
	private String deptIDofSealer;
	private String sealerID;
	private String applyNo;


	private Date sealTime; // 已盖章时间、未分发时间
	private String state;//状态 00未盖， 01盖了
	private String isStamp;//盖章
	private String isSign;//签字
	private String videoURL;//视频URL

	private String fullName;//姓名或者公司名称
	private String identityCard;//身份证或者营业执照
	private String identityType;//1或者11
	private String loadLink;
	private String pdfpath;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getCompanyIDofSealer() {
		return companyIDofSealer;
	}

	public void setCompanyIDofSealer(String companyIDofSealer) {
		this.companyIDofSealer = companyIDofSealer;
	}

	public String getDeptIDofSealer() {
		return deptIDofSealer;
	}

	public void setDeptIDofSealer(String deptIDofSealer) {
		this.deptIDofSealer = deptIDofSealer;
	}

	public String getSealerID() {
		return sealerID;
	}

	public void setSealerID(String sealerID) {
		this.sealerID = sealerID;
	}

	public Date getSealTime() {
		return sealTime;
	}

	public void setSealTime(Date sealTime) {
		this.sealTime = sealTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsStamp() {
		return isStamp;
	}

	public void setIsStamp(String isStamp) {
		this.isStamp = isStamp;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getLoadLink() {
		return loadLink;
	}

	public void setLoadLink(String loadLink) {
		this.loadLink = loadLink;
	}

	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}
}
