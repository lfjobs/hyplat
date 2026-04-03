package hy.ea.bo.company.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;


/**
 * 往来个人视图
 * @author 
 */
public class ContactUser implements BaseBean,ExcelBean,Serializable {
	private String relationKey;
	private String relationID;
	private String staffID;
	private String companyID;
	private String staffCode;// 人员编号
	private String recordCode;// 档案编号
	private String staffName;// 人员名
	private String usedNmae;// 曾用名
	private String sex;// 性别
	private String nativePlace;// 籍贯
	private String nationality;// 国籍
	private String nation;// 民族
	private String staffIdentityCard;// 身份证
	private String staffAddress;//家庭地址路径
	private String referenceOrganization;// 邮箱
	private String reference;// 电话
	private String referenceCode;// qq
	private Date verifyTime;// 录入时间
	private String staffDesc;// 备注
	private String birthday;// 日期
	private String photo;// 照片路径
	private String relation;

	
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","人员编号","档案编号","人员名" ,"往来关系","曾用名" ,"性别","出生日期","籍贯","国籍","民族","身份证"};//,"家庭地址","邮箱","qq","电话","录入时间","备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { staffCode,recordCode,staffName,relation,usedNmae,sex,birthday,nativePlace,nationality,nation,staffIdentityCard};//,staffAddress,referenceOrganization,referenceCode,reference,verifyTime == null ?"":String.format("%1$tF", verifyTime),staffDesc};
		return properties;
	}
	public String getRelationKey() {
		return relationKey;
	}
	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}
	public String getRelationID() {
		return relationID;
	}
	public void setRelationID(String relationID) {
		this.relationID = relationID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getRecordCode() {
		return recordCode;
	}
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getUsedNmae() {
		return usedNmae;
	}
	public void setUsedNmae(String usedNmae) {
		this.usedNmae = usedNmae;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}
	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}
	public String getStaffAddress() {
		return staffAddress;
	}
	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}
	public String getReferenceOrganization() {
		return referenceOrganization;
	}
	public void setReferenceOrganization(String referenceOrganization) {
		this.referenceOrganization = referenceOrganization;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	public String getStaffDesc() {
		return staffDesc;
	}
	public void setStaffDesc(String staffDesc) {
		this.staffDesc = staffDesc;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
}
