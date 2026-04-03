package hy.ea.bo.driving.view;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class DrivingStudentView implements BaseBean,ExcelBean{
	private String drivingprincipalkey;
	private String staffid;
	private String staffcode;
	private String recordcode;
	private String staffname;
	private String usednmae;
	private String reference;
	private String sex;
	private String birthday;
	private String nativeplace;
	private String nationality;
	private String nation;
	private String staffidentitycard;
	private String companyid;
	private String companyname;
	private Date registrationdate;
	private String docstatus;
	private String studentstatus;
	private String istrues;
	private Integer printcount;
	private String states;
	private String studentstatusnote;
	private String studentPeriods;// 学员期数
	private String testresult;
	private Date inputTime;
	
	private String contactWay;//Email
	
	private String ddmorganizationid;//招生点ID
	private String ddmorganizationname;//招生点NAME
	private String ddmreferrerid;//招生员ID
	private String ddmreferrer;//招生员NAME
	private Date ddmsignupdate;//招生时间/报名时间
	private static String titleByExport;
	public static String[] columnHeadings() {
		String[] titles = { "序号","期数","录入日期","公司名称","人员编号","档案编号","人员姓名","联系方式","Email","曾用名","性别","出生日期"
				,"籍贯","国籍","民族","身份证号","学员状态","招生时间","招生点","招生员"};
		return titles;
	}
	
	public static String[] columnHeadingsOfAdmissions() {
		String[] titles = { "序号","招生时间","招生点","招生员","人员编号","人员姓名","联系方式","Email","性别","出生日期"
				,"身份证号","学员状态"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties={studentPeriods,String.format("%1$tF", registrationdate),companyname,staffcode,reference,contactWay,recordcode,
				 staffname,usednmae,sex,birthday,nativeplace,nationality,nation,staffidentitycard, studentstatusnote,String.format("%1$tF", ddmsignupdate),ddmorganizationname,ddmreferrer};
		
		String[] propertiesOfAdmissions={String.format("%1$tF", ddmsignupdate),ddmorganizationname,ddmreferrer,staffcode,staffname,
				 reference,contactWay,sex,birthday,staffidentitycard, studentstatusnote};
		
		if("Admissions".equals(titleByExport)){
			return propertiesOfAdmissions;
		}else{
			return properties;
		}
	}
	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	public String getStaffcode() {
		return staffcode;
	}
	public void setStaffcode(String staffcode) {
		this.staffcode = staffcode;
	}
	public String getRecordcode() {
		return recordcode;
	}
	public void setRecordcode(String recordcode) {
		this.recordcode = recordcode;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getUsednmae() {
		return usednmae;
	}
	public void setUsednmae(String usednmae) {
		this.usednmae = usednmae;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
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
	public String getStaffidentitycard() {
		return staffidentitycard;
	}
	public void setStaffidentitycard(String staffidentitycard) {
		this.staffidentitycard = staffidentitycard;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public Date getRegistrationdate() {
		return registrationdate;
	}
	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}
	public String getDocstatus() {
		return docstatus;
	}
	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}
	public String getStudentstatus() {
		return studentstatus;
	}
	public void setStudentstatus(String studentstatus) {
		this.studentstatus = studentstatus;
	}
	public String getIstrues() {
		return istrues;
	}
	public void setIstrues(String istrues) {
		this.istrues = istrues;
	}
	public Integer getPrintcount() {
		return printcount;
	}
	public void setPrintcount(Integer printcount) {
		this.printcount = printcount;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	public String getDrivingprincipalkey() {
		return drivingprincipalkey;
	}
	public void setDrivingprincipalkey(String drivingprincipalkey) {
		this.drivingprincipalkey = drivingprincipalkey;
	}
	public String getStudentstatusnote() {
		return studentstatusnote;
	}
	public void setStudentstatusnote(String studentstatusnote) {
		this.studentstatusnote = studentstatusnote;
	}
	public String getStudentPeriods() {
		return studentPeriods;
	}
	public void setStudentPeriods(String studentPeriods) {
		this.studentPeriods = studentPeriods;
	}

	public String getTestresult() {
		return testresult;
	}

	public void setTestresult(String testresult) {
		this.testresult = testresult;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getDdmorganizationid() {
		return ddmorganizationid;
	}

	public void setDdmorganizationid(String ddmorganizationid) {
		this.ddmorganizationid = ddmorganizationid;
	}

	public String getDdmorganizationname() {
		return ddmorganizationname;
	}

	public void setDdmorganizationname(String ddmorganizationname) {
		this.ddmorganizationname = ddmorganizationname;
	}

	public String getDdmreferrerid() {
		return ddmreferrerid;
	}

	public void setDdmreferrerid(String ddmreferrerid) {
		this.ddmreferrerid = ddmreferrerid;
	}

	public String getDdmreferrer() {
		return ddmreferrer;
	}

	public void setDdmreferrer(String ddmreferrer) {
		this.ddmreferrer = ddmreferrer;
	}

	public Date getDdmsignupdate() {
		return ddmsignupdate;
	}

	public void setDdmsignupdate(Date ddmsignupdate) {
		this.ddmsignupdate = ddmsignupdate;
	}

	public static String getTitleByExport() {
		return titleByExport;
	}

	public static void setTitleByExport(String titleByExport) {
		DrivingStudentView.titleByExport = titleByExport;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	
	

	
}
