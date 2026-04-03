package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

/**
 * 家庭成员
 */
public class StaffFamilyMember implements BaseBean,ExcelBean {
	private String memberKey;
	private String memberID;
	private String companyID;
	private String staffID;

	private String memberName;
	private String memberRelationship;// 与本人关系
	private Date memberBirthDay;// 生日
	private String memberCompanyName;// 工作单位
	private String memberPosition;// 职务
	private String memberDuties;// 工作内容
	private String memberAddress;// 住址
	private String memberPhone;// 电话
	private String memberDesc;// 备注

	private String postType;// 岗们类型
	private String workPhone;// 工作电话
	private String reference;// 证明人
	private String referenceCode;// 审核人人员编号
	private Date referenceTime;// 审核时间

	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "姓名", "与本人的关系", "出生日期", "工作单位", "职务", "岗位类型",
				"工作内容", "住址", "电话号码", "工作电话", "证明人",  "审核人人员编号", "审核时间",
				"备注" };
		return titles;
	}

	public String[] properties() {
		String[] properties = { memberName, oMap.get(memberRelationship),
				String.format("%1$tF", memberBirthDay), memberCompanyName,
				memberPosition, oMap.get(postType), memberDuties, memberAddress,
				memberPhone, workPhone, reference,
				referenceCode, String.format("%1$tF", referenceTime),
				memberDesc };
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
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

	public Date getReferenceTime() {
		return referenceTime;
	}

	public void setReferenceTime(Date referenceTime) {
		this.referenceTime = referenceTime;
	}

	public String getMemberKey() {
		return memberKey;
	}

	public void setMemberKey(String memberKey) {
		this.memberKey = memberKey;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberRelationship() {
		return memberRelationship;
	}

	public void setMemberRelationship(String memberRelationship) {
		this.memberRelationship = memberRelationship;
	}

	public Date getMemberBirthDay() {
		return memberBirthDay;
	}

	public void setMemberBirthDay(Date memberBirthDay) {
		this.memberBirthDay = memberBirthDay;
	}

	public String getMemberCompanyName() {
		return memberCompanyName;
	}

	public void setMemberCompanyName(String memberCompanyName) {
		this.memberCompanyName = memberCompanyName;
	}

	public String getMemberPosition() {
		return memberPosition;
	}

	public void setMemberPosition(String memberPosition) {
		this.memberPosition = memberPosition;
	}

	public String getMemberDuties() {
		return memberDuties;
	}

	public void setMemberDuties(String memberDuties) {
		this.memberDuties = memberDuties;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberDesc() {
		return memberDesc;
	}

	public void setMemberDesc(String memberDesc) {
		this.memberDesc = memberDesc;
	}

}
