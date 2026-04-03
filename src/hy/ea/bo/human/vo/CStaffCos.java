package hy.ea.bo.human.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司所有在职员工
 * @author Administrator
 *
 */
public class CStaffCos implements BaseBean,ExcelBean ,Serializable {
	private String cosKey;
	private String staffID;
	private String companyID;
	private String categoryname;
	private String staffcategoryid;
	
	private String companyName;//公司名称
	private String staffName;// 人员名
	//98 表示删除
	private String staffStatus;

	private String staffCode;// 人员编号
	private String recordCode;// 档案编号
	
	private String usedNmae;// 曾用名
	private String sex;// 性别
	private String nativePlace;// 籍贯
	private String nationality;// 国籍
	private String nation;// 民族
	private String staffIdentityCard;// 身份证
	private String address;// 家庭地址
	private String staffAddress;// 家庭地址
	private String referenceOrganization;// 邮箱
	private String reference;// 电话
	private String referenceCode;// qq
	private Date verifyTime;// 录入时间
	private String staffDesc;// 备注
	private String birthday;// 日期
	private String photo;// 照片路径

	/**
	 * 不了不写十次同样的代码!!!!!!!!!!!!!!!!
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司名称","人员编号", "档案编号", "员工工种","人员姓名", "曾用名", "性别", "出生日期",
				"国籍","籍贯","民族", "身份证"};//,"家庭地址", "电话", "qq","邮箱", "录入时间", "备注" };
		return titles;
	}
	/**
	 * 集团汇总
	 * @return
	 */
	public static String[] columnHeadings1(){
		String[] titles = {"序号", "公司名称","人员编号", "档案编号", "员工工种","人员姓名","部门名称","岗位名称", "曾用名", "性别", "出生日期",
				"国籍","籍贯","民族", "身份证"};
		return titles;
	}
	
	public String[] properties() {
		String[] properties = { companyName ,staffCode, recordCode,categoryname, staffName,usedNmae, sex,
				birthday,nationality, nativePlace,nation, staffIdentityCard};//,staffAddress,
				//reference, referenceCode,referenceOrganization,
				//String.format("%1$tF", verifyTime),staffDesc };
		return properties;
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

	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}


	

	public String getCosKey() {
		return cosKey;
	}
	public void setCosKey(String cosKey) {
		this.cosKey = cosKey;
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

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffStatus() {
		return staffStatus;
	}

	public void setStaffStatus(String staffStatus) {
		this.staffStatus = staffStatus;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStaffAddress() {
		return staffAddress;
	}
	public void setStaffAddress(String staffAddress) {
		this.staffAddress = staffAddress;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getStaffcategoryid() {
		return staffcategoryid;
	}
	public void setStaffcategoryid(String staffcategoryid) {
		this.staffcategoryid = staffcategoryid;
	}

}
