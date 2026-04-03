package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;

/**
 * 招聘登记.面试.入职管理
 */
public class Audition implements BaseBean,ExcelBean {
	private String auditionKey;
	private String auditionID;
	private String companyID;
	private String staffID;
	private String staffName;
	private String photo;
	private String reference;  //电话
	private String referenceCode; //qq

	private String tpId;//管理简历池
	//招聘
	private String staffIdentityCard;// 身份证
	private String auditionDirection;//应聘方向
	private String auditionPost;// 应聘岗位
	private String experience;//工作经验
	
	//面试
	private String place;//面试地点
	private String examiner;//面试考官
	private Date auditionDate;//面试时间约的
	private String auditionDept;//面试部门
	private Date  becomesDate;//入职后第六个月的月初
	private String contactor;//联系人
	private String contactTel;//联系人电话
	private Date regisDate;//面试登记时间
	private String bstID;//笔试题库ID
	private String bstName;//笔试题库名称
	private String mstID;//面试题库ID
	private String mstName;//笔试题库名称

	private String commentionb;//笔试评语
	private String auditionPointb;//笔试分数
	private  Date bsDate;//笔试时间
	private String bsState;//笔试考试结果
	private String bsstaffID;//笔试评定人
	private String  ksState;//口试考试结果
	private  Date msDate;//面试时间

	private Date mspdDate;//面试评定时间
	private String mspdStaffID;//面试评定操纵人员

	private String commentionk;//口试评语
	private String auditionPointk;//口试分数
	//入职
	private String commention;//综合评语
	private String auditionPoint;//综合分数
	private Date zpDate;//综合评定操作时间
	private String zpStaffID;//综合评定人员
	private String  zpState;//综合评定结果
	private String  ztState;//转通知 1 ：已转通知  空：未转通知 3：已通知，


	private Date registerDate;//报到时间
	private String auditionPlace;//报到地点
	private String  bdcontactor;//报到联系人
	private String bdcontactortel;//报到联系人电话
	private String remark;//备注
	private String staffCategoryID; //工种类别id（code）
	private String categoryName;//工种名称
	private String status;//状态   01 待登记 00:待面试  10:待考试 	笔试通过 12	 11：口试通过未笔试 33:考试结果未通过      	21:未入职 （考试通过） 22:已入职    99:已离职
	/**
	 * Excel表例标题
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "姓名","身份证", "应聘方向", "应聘岗位", "工作经验","面试地点","面试考官","面试时间","面试部门","评语","分数","报到时间","报到地点","备注",
				"状态" };
		return titles;
	}
  public Audition(){}

	public Audition(String staffName, String photo, String staffIdentityCard,String reference,String referenceCode,
	   String auditionKey, String auditionID,String companyID, String staffID,
		String auditionDirection, String auditionPost, String experience,
		String place, String examiner, Date auditionDate, String auditionDept,
		String commention, String auditionPoint, Date registerDate,
		String auditionPlace,Date becomesDate, String remark, String status,String staffCategoryID,String categoryName,String bsState,String ksState,String zpState,String ztState) {
	super();
	this.reference = reference;
	this.referenceCode = referenceCode;
	this.auditionKey = auditionKey;
	this.auditionID = auditionID;
	this.staffID = staffID;
	this.companyID = companyID;
	this.staffName = staffName;
	this.photo = photo;
	this.staffIdentityCard = staffIdentityCard;
	this.auditionDirection = auditionDirection;
	this.auditionPost = auditionPost;
	this.experience = experience;
	this.place = place;
	this.examiner = examiner;
	this.auditionDate = auditionDate;
	this.auditionDept = auditionDept;
	this.commention = commention;
	this.auditionPoint = auditionPoint;
	this.registerDate = registerDate;
	this.auditionPlace = auditionPlace;
	this.becomesDate =becomesDate;
	this.remark = remark;
	this.status = status;
	this.staffCategoryID=staffCategoryID;
	this.categoryName=categoryName;
	this.bsState = bsState;
	this.ksState = ksState;
	this.zpState = zpState;
	this.ztState = ztState;
}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String[] properties() {
		String[] properties = { staffName, staffIdentityCard,auditionDirection, auditionPost,experience,
				place,examiner, String.format("%1$tF", auditionDate),auditionDept,commention,auditionPoint,
				String.format("%1$tF", registerDate),auditionPlace,remark, oMap.get(status)};
		return properties;
	}

	public String getAuditionKey() {
		return auditionKey;
	}

	public void setAuditionKey(String auditionKey) {
		this.auditionKey = auditionKey;
	}

	public String getAuditionID() {
		return auditionID;
	}

	public void setAuditionID(String auditionID) {
		this.auditionID = auditionID;
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

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	public String getAuditionDirection() {
		return auditionDirection;
	}

	public void setAuditionDirection(String auditionDirection) {
		this.auditionDirection = auditionDirection;
	}

	public String getAuditionPost() {
		return auditionPost;
	}

	public void setAuditionPost(String auditionPost) {
		this.auditionPost = auditionPost;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	

	public String getCommention() {
		return commention;
	}

	public void setCommention(String commention) {
		this.commention = commention;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 状态 00:待面试  10:待考试  
	 *     21:未入职  22:已入职  99:已离职
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 状态 00:待面试  10:待考试  21:未入职  22:已入职    99:已离职
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getExaminer() {
		return examiner;
	}
	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}
	public Date getAuditionDate() {
		return auditionDate;
	}
	public void setAuditionDate(Date auditionDate) {
		this.auditionDate = auditionDate;
	}
	public String getAuditionDept() {
		return auditionDept;
	}
	public void setAuditionDept(String auditionDept) {
		this.auditionDept = auditionDept;
	}
	public String getAuditionPoint() {
		return auditionPoint;
	}
	public void setAuditionPoint(String auditionPoint) {
		this.auditionPoint = auditionPoint;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getAuditionPlace() {
		return auditionPlace;
	}
	public void setAuditionPlace(String auditionPlace) {
		this.auditionPlace = auditionPlace;
	}
	/**
	 * 入职后第六个月的月初
	 * @return
	 */
	public Date getBecomesDate() {
		return becomesDate;
	}
	/**
	 * 入职后第六个月的月初
	 * @param becomesDate
	 */
	public void setBecomesDate(Date becomesDate) {
		this.becomesDate = becomesDate;
	}
	public String getStaffCategoryID() {
		return staffCategoryID;
	}
	public void setStaffCategoryID(String staffCategoryID) {
		this.staffCategoryID = staffCategoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public Date getRegisDate() {
		return regisDate;
	}

	public void setRegisDate(Date regisDate) {
		this.regisDate = regisDate;
	}

	public String getBstID() {
		return bstID;
	}

	public void setBstID(String bstID) {
		this.bstID = bstID;
	}

	public String getMstID() {
		return mstID;
	}

	public void setMstID(String mstID) {
		this.mstID = mstID;
	}

	public String getBstName() {
		return bstName;
	}

	public void setBstName(String bstName) {
		this.bstName = bstName;
	}

	public String getMstName() {
		return mstName;
	}

	public void setMstName(String mstName) {
		this.mstName = mstName;
	}

	public String getCommentionb() {
		return commentionb;
	}

	public void setCommentionb(String commentionb) {
		this.commentionb = commentionb;
	}

	public String getAuditionPointb() {
		return auditionPointb;
	}

	public void setAuditionPointb(String auditionPointb) {
		this.auditionPointb = auditionPointb;
	}

	public Date getBsDate() {
		return bsDate;
	}

	public void setBsDate(Date bsDate) {
		this.bsDate = bsDate;
	}

	public Date getMsDate() {
		return msDate;
	}

	public void setMsDate(Date msDate) {
		this.msDate = msDate;
	}

	public String getBsState() {
		return bsState;
	}

	public void setBsState(String bsState) {
		this.bsState = bsState;
	}

	public String getKsState() {
		return ksState;
	}

	public void setKsState(String ksState) {
		this.ksState = ksState;
	}

	public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}

	public String getCommentionk() {
		return commentionk;
	}

	public void setCommentionk(String commentionk) {
		this.commentionk = commentionk;
	}

	public String getAuditionPointk() {
		return auditionPointk;
	}

	public void setAuditionPointk(String auditionPointk) {
		this.auditionPointk = auditionPointk;
	}

	public String getZpState() {
		return zpState;
	}

	public void setZpState(String zpState) {
		this.zpState = zpState;
	}

	public String getZtState() {
		return ztState;
	}

	public void setZtState(String ztState) {
		this.ztState = ztState;
	}

	public String getBdcontactor() {
		return bdcontactor;
	}

	public void setBdcontactor(String bdcontactor) {
		this.bdcontactor = bdcontactor;
	}

	public String getBdcontactortel() {
		return bdcontactortel;
	}

	public void setBdcontactortel(String bdcontactortel) {
		this.bdcontactortel = bdcontactortel;
	}

	public Date getMspdDate() {
		return mspdDate;
	}

	public void setMspdDate(Date mspdDate) {
		this.mspdDate = mspdDate;
	}

	public String getMspdStaffID() {
		return mspdStaffID;
	}

	public void setMspdStaffID(String mspdStaffID) {
		this.mspdStaffID = mspdStaffID;
	}

	public Date getZpDate() {
		return zpDate;
	}

	public void setZpDate(Date zpDate) {
		this.zpDate = zpDate;
	}

	public String getZpStaffID() {
		return zpStaffID;
	}

	public void setZpStaffID(String zpStaffID) {
		this.zpStaffID = zpStaffID;
	}

	public String getBsstaffID() {
		return bsstaffID;
	}

	public void setBsstaffID(String bsstaffID) {
		this.bsstaffID = bsstaffID;
	}
}
