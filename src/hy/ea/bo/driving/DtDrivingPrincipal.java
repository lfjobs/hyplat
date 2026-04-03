package hy.ea.bo.driving;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtDrivingPrincipal entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtDrivingPrincipal  implements BaseBean,ExcelBean,java.io.Serializable {

	// Fields

	private String drivingprincipalkey;
	private String drivingprincipalid;
	private String companygroupid;
	private String companyid;
	private String oragtionid;
	private String organizationID;
	private String responsiblePersonsID;
	private String drivingsecondaryid;
	private Date registrationdate;
	private String registrationaddress;
	private String registrationcarid;
	private String registrationcarname;
	private Float registrationcost;
	private Date resgistrationmedicaldate;
	private String istrues;
	private String reason;
	private Date schoolopendate;
	private String serialnumber;
	private String recruitid;
	private String recruitname;
	private String recruitdepartmentid;
	private String recruitdepatmentname;
	private String recruitpost;
	private String studentid;
	private String studentname;
	private String studentsex;
	private String studentphone;
	private String studentcard;
	private String barcode;
	private String studentnote;
	private Date   operationdate;
	private String organizationName;
	private String studentCode;// 报开学号
	private String studentPeriods;// 学员期数
	
	private Date  searchStaDate;//录入科目时间 查询起时间 非数据库字段
	private Date  searchEndDate;//录入科目时间 查询止时间 非数据库字段
	
	private Date  searchStaDateOne;//报名时间 查询起时间 非数据库字段
	private Date  searchEndDateOne;//报名时间 查询止时间 非数据库字段
    
	private Boolean ifPay;
	private Float  money;
	
	private String dataIsComplete;//证件资料 是否齐全   一， 不齐全 01  二，齐全  00
	/**
	 * Excel表例标题合格率统计
	 */
	public static String[] columnHeadings() {
		String[] titles = { "序号", "科目类型","教练员","起时间", "止时间", "参考人数", "合格人数","不合格人数","缺考人数","误报人数","合格率"};
		return titles;
	}
	
	/**
	 * Excel表例标题考试统计
	 */
	public static String[] columnHeadings1() {
		String[] titles = { "序号", "报名时间","科目类型","车型", "姓名", "性别", "身份证号","电话","教练员","约考时间","预约方式","约考责任人","考试结果",
				"第一次考试时间","第一次考试扣分项目","第一次学员得分","第一次考试结果","第一次考试考官",
				"第二次考试时间","第二次考试扣分项目","第二次学员得分","第二次考试结果","第二次考试考官"};
		return titles;
	}
	/**
	 * Excel表例标题证件是否合格
	 */
	public static String[] columnHeadingsOfdataIsComplete() {
		String[] titles = { "序号", "报名时间","学员姓名", "性别", "电话","身份证号","车型"};
		return titles;
	}
	/**
	 * Excel表例标题证件是否报开学
	 */
	public static String[] columnHeadingsOfBaoKaiXue() {
		String[] titles = { "序号", "报名时间","报开学时间","报开学号","学员期数","学员姓名", "性别", "电话","身份证号","车型"};
		return titles;
	}
	// Constructors
	
	/** default constructor */
	public DtDrivingPrincipal() {
	}

	/** minimal constructor */
	public DtDrivingPrincipal(String drivingprincipalid, String companygroupid) {
		this.drivingprincipalid = drivingprincipalid;
		this.companygroupid = companygroupid;
	}

	/** full constructor */
	public DtDrivingPrincipal(String drivingprincipalid, String companygroupid,
			String companyid, String oragtionid, String drivingsecondaryid,
			Date registrationdate, String registrationaddress,
			String registrationcarid, String registrationcarname,
			Float registrationcost, Date resgistrationmedicaldate,
			String istrues, String reason, Date schoolopendate,
			String serialnumber, String recruitid, String recruitname,
			String recruitdepartmentid, String recruitdepatmentname,
			String recruitpost, String coachid, String coachname,
			String studentid, String studentname, String studentsex,
			String studentphone, String studentcard, String barcode,
			String testsum, String testresult, String studentnote,
			String docstatus, String studentstatus) {
		this.drivingprincipalid = drivingprincipalid;
		this.companygroupid = companygroupid;
		this.companyid = companyid;
		this.oragtionid = oragtionid;
		this.drivingsecondaryid = drivingsecondaryid;
		this.registrationdate = registrationdate;
		this.registrationaddress = registrationaddress;
		this.registrationcarid = registrationcarid;
		this.registrationcarname = registrationcarname;
		this.registrationcost = registrationcost;
		this.resgistrationmedicaldate = resgistrationmedicaldate;
		this.istrues = istrues;
		this.reason = reason;
		this.schoolopendate = schoolopendate;
		this.serialnumber = serialnumber;
		this.recruitid = recruitid;
		this.recruitname = recruitname;
		this.recruitdepartmentid = recruitdepartmentid;
		this.recruitdepatmentname = recruitdepatmentname;
		this.recruitpost = recruitpost;
		this.studentid = studentid;
		this.studentname = studentname;
		this.studentsex = studentsex;
		this.studentphone = studentphone;
		this.studentcard = studentcard;
		this.barcode = barcode;
		this.studentnote = studentnote;
	}

	// Property accessors

	public String getDrivingprincipalkey() {
		return this.drivingprincipalkey;
	}

	public void setDrivingprincipalkey(String drivingprincipalkey) {
		this.drivingprincipalkey = drivingprincipalkey;
	}

	public String getDrivingprincipalid() {
		return this.drivingprincipalid;
	}

	public void setDrivingprincipalid(String drivingprincipalid) {
		this.drivingprincipalid = drivingprincipalid;
	}

	public String getCompanygroupid() {
		return this.companygroupid;
	}

	public void setCompanygroupid(String companygroupid) {
		this.companygroupid = companygroupid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOragtionid() {
		return this.oragtionid;
	}

	public void setOragtionid(String oragtionid) {
		this.oragtionid = oragtionid;
	}

	public String getDrivingsecondaryid() {
		return this.drivingsecondaryid;
	}

	public void setDrivingsecondaryid(String drivingsecondaryid) {
		this.drivingsecondaryid = drivingsecondaryid;
	}

	public Date getRegistrationdate() {
		return this.registrationdate;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public String getRegistrationaddress() {
		return this.registrationaddress;
	}

	public void setRegistrationaddress(String registrationaddress) {
		this.registrationaddress = registrationaddress;
	}

	public String getRegistrationcarid() {
		return this.registrationcarid;
	}

	public void setRegistrationcarid(String registrationcarid) {
		this.registrationcarid = registrationcarid;
	}

	public String getRegistrationcarname() {
		return this.registrationcarname;
	}

	public void setRegistrationcarname(String registrationcarname) {
		this.registrationcarname = registrationcarname;
	}

	public Float getRegistrationcost() {
		return this.registrationcost;
	}

	public void setRegistrationcost(Float registrationcost) {
		this.registrationcost = registrationcost;
	}

	public Date getResgistrationmedicaldate() {
		return this.resgistrationmedicaldate;
	}

	public void setResgistrationmedicaldate(Date resgistrationmedicaldate) {
		this.resgistrationmedicaldate = resgistrationmedicaldate;
	}

	public String getIstrues() {
		return this.istrues;
	}

	public void setIstrues(String istrues) {
		this.istrues = istrues;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getSchoolopendate() {
		return this.schoolopendate;
	}

	public void setSchoolopendate(Date schoolopendate) {
		this.schoolopendate = schoolopendate;
	}

	public String getSerialnumber() {
		return this.serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getRecruitid() {
		return this.recruitid;
	}

	public void setRecruitid(String recruitid) {
		this.recruitid = recruitid;
	}

	public String getRecruitname() {
		return this.recruitname;
	}

	public void setRecruitname(String recruitname) {
		this.recruitname = recruitname;
	}

	public String getRecruitdepartmentid() {
		return this.recruitdepartmentid;
	}

	public void setRecruitdepartmentid(String recruitdepartmentid) {
		this.recruitdepartmentid = recruitdepartmentid;
	}

	public String getRecruitdepatmentname() {
		return this.recruitdepatmentname;
	}

	public void setRecruitdepatmentname(String recruitdepatmentname) {
		this.recruitdepatmentname = recruitdepatmentname;
	}

	public String getRecruitpost() {
		return this.recruitpost;
	}

	public void setRecruitpost(String recruitpost) {
		this.recruitpost = recruitpost;
	}


	public String getStudentid() {
		return this.studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getStudentname() {
		return this.studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getStudentsex() {
		return this.studentsex;
	}

	public void setStudentsex(String studentsex) {
		this.studentsex = studentsex;
	}

	public String getStudentphone() {
		return this.studentphone;
	}

	public void setStudentphone(String studentphone) {
		this.studentphone = studentphone;
	}

	public String getStudentcard() {
		return this.studentcard;
	}

	public void setStudentcard(String studentcard) {
		this.studentcard = studentcard;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getStudentnote() {
		return this.studentnote;
	}

	public void setStudentnote(String studentnote) {
		this.studentnote = studentnote;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getResponsiblePersonsID() {
		return responsiblePersonsID;
	}

	public void setResponsiblePersonsID(String responsiblePersonsID) {
		this.responsiblePersonsID = responsiblePersonsID;
	}

	public Date getOperationdate() {
		return operationdate;
	}

	public void setOperationdate(Date operationdate) {
		this.operationdate = operationdate;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public String getStudentPeriods() {
		return studentPeriods;
	}

	public void setStudentPeriods(String studentPeriods) {
		this.studentPeriods = studentPeriods;
	}

	public Date getSearchStaDate() {
		return searchStaDate;
	}

	public void setSearchStaDate(Date searchStaDate) {
		this.searchStaDate = searchStaDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	

	public Boolean getIfPay() {
		return ifPay;
	}

	public void setIfPay(Boolean ifPay) {
		this.ifPay = ifPay;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getDataIsComplete() {
		return dataIsComplete;
	}

	public void setDataIsComplete(String dataIsComplete) {
		this.dataIsComplete = dataIsComplete;
	}

	public Date getSearchStaDateOne() {
		return searchStaDateOne;
	}

	public void setSearchStaDateOne(Date searchStaDateOne) {
		this.searchStaDateOne = searchStaDateOne;
	}

	public Date getSearchEndDateOne() {
		return searchEndDateOne;
	}

	public void setSearchEndDateOne(Date searchEndDateOne) {
		this.searchEndDateOne = searchEndDateOne;
	}

	
	
}