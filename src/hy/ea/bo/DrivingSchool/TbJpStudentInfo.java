package hy.ea.bo.DrivingSchool;


import hy.plat.bo.BaseBean;

import java.util.Date;

/*
 *	学员信息
 * */
public class TbJpStudentInfo implements BaseBean{
	private String StudentKey;
	private String studentId;
	private String staffId;
	private String stuNum;
	private String companyId;
	private String name;
	private String sex;
	private Date brith;
	private String nationality;
	private String cardNum;
	private String cardType;
	private String busiType;
	private String trainType;
	private String perdriType;
	private String phone;
	private String address;
	private String tempCardNo;
	private String stayAddress;
	private String photo;
	private String fingerPrint;
	private String drilicNum;
	private Date fstDrilicDate;
	private String isLocal;
	private String archivesNum;
	private String jsCardId;
	private Date applyDate;
	private String checkStatus;
	private String syncStatus;
	private Date createDate;
	private Date updateDate;
	private String createPerson;
	private String updatePerson;
	private String synctype;
	private String delFlag;
	private String charId;
	private Long	faceId;
	private Long faceCode;
	private String cardNo;
	private String syncXlycStatus;
	private String syncoldjp;
	private String pkTbJpStudentId;  //快车同步的id
	public static String[]
	columnHeadings() {
		String[] titles = { "序号","学员编号", "学员姓名", "性别", "身份证", "最常用联系方式", "出生日期","培训车型","驾校名称"};
		return titles;
	}

	public String getStudentKey() {
		return StudentKey;
	}

	public void setStudentKey(String studentKey) {
		StudentKey = studentKey;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStuNum() {
		return stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBrith() {
		return brith;
	}

	public void setBrith(Date brith) {
		this.brith = brith;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}

	public String getPerdriType() {
		return perdriType;
	}

	public void setPerdriType(String perdriType) {
		this.perdriType = perdriType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTempCardNo() {
		return tempCardNo;
	}

	public void setTempCardNo(String tempCardNo) {
		this.tempCardNo = tempCardNo;
	}

	public String getStayAddress() {
		return stayAddress;
	}

	public void setStayAddress(String stayAddress) {
		this.stayAddress = stayAddress;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public String getDrilicNum() {
		return drilicNum;
	}

	public void setDrilicNum(String drilicNum) {
		this.drilicNum = drilicNum;
	}

	public Date getFstDrilicDate() {
		return fstDrilicDate;
	}

	public void setFstDrilicDate(Date fstDrilicDate) {
		this.fstDrilicDate = fstDrilicDate;
	}

	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(String isLocal) {
		this.isLocal = isLocal;
	}

	public String getArchivesNum() {
		return archivesNum;
	}

	public void setArchivesNum(String archivesNum) {
		this.archivesNum = archivesNum;
	}

	public String getJsCardId() {
		return jsCardId;
	}

	public void setJsCardId(String jsCardId) {
		this.jsCardId = jsCardId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getSynctype() {
		return synctype;
	}

	public void setSynctype(String synctype) {
		this.synctype = synctype;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCharId() {
		return charId;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}

	public Long getFaceId() {
		return faceId;
	}

	public void setFaceId(Long faceId) {
		this.faceId = faceId;
	}

	public Long getFaceCode() {
		return faceCode;
	}

	public void setFaceCode(Long faceCode) {
		this.faceCode = faceCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSyncXlycStatus() {
		return syncXlycStatus;
	}

	public void setSyncXlycStatus(String syncXlycStatus) {
		this.syncXlycStatus = syncXlycStatus;
	}

	public String getSyncoldjp() {
		return syncoldjp;
	}

	public void setSyncoldjp(String syncoldjp) {
		this.syncoldjp = syncoldjp;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getPkTbJpStudentId() {
		return pkTbJpStudentId;
	}

	public void setPkTbJpStudentId(String pkTbJpStudentId) {
		this.pkTbJpStudentId = pkTbJpStudentId;
	}
}
