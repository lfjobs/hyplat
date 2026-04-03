package hy.ea.bo.driving.view;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * ViewProgressStudent entity. @author MyEclipse Persistence Tools
 */

public class ViewProgressStudent implements java.io.Serializable,BaseBean,ExcelBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5308045797488056911L;
	// Fields

	private String studentid;
	private Date registrationdate;
	private String registrationcarname;
	private Date schoolopendate;
	private String studentname;
	private String studentsex;
	private String studentphone;
	private String studentcard;
	private String barcode;
	private String coach;
	private String entrance;
	private String subjectone;
	private String subjecttwo;
	private String subjectthree;
	private String subjectfour;
	private String graduation;
	private String companyid;
	private String studentcode;
	private String status;
	
	/**非数据库字段***/
	private Date searchStaDate;
	private Date searchEndDate;
	private String theProgress;
	// Constructors
	
	
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		String[]  properties={String.format("%1$tF", registrationdate),registrationcarname,studentname,studentsex,studentphone,studentcard,coach,
				"1".equals(entrance)?"通过":"未通过",
						"1".equals(subjectone)?"通过":"未通过",
							"1".equals(subjecttwo)?"通过":"未通过",
									"1".equals(subjectthree)?"通过":"未通过",
											"1".equals(subjectfour)?"通过":"未通过",
													"1".equals(graduation)?"通过":"未通过",};
		return properties;
	}
	
	public static String[] columnHeadings(){
		String[] titles = { "序号", "报名时间","报考车型", "学员姓名", "性别", "电话","身份证","教练","入学缴费","科目一","科目二","科目三","科目四","毕业"};
		return titles;
	}
	
	/** default constructor */
	public ViewProgressStudent() {
	}

	/** full constructor */
	public ViewProgressStudent(Date registrationdate,
			String registrationcarname, Date schoolopendate,
			String studentname, String studentsex, String studentphone,
			String studentcard, String barcode, String coach,
			String entrance, String subjectone, String subjecttwo,
			String subjectthree, String subjectfour,
			String graduation, String companyid) {
		this.registrationdate = registrationdate;
		this.registrationcarname = registrationcarname;
		this.schoolopendate = schoolopendate;
		this.studentname = studentname;
		this.studentsex = studentsex;
		this.studentphone = studentphone;
		this.studentcard = studentcard;
		this.barcode = barcode;
		this.coach = coach;
		this.entrance = entrance;
		this.subjectone = subjectone;
		this.subjecttwo = subjecttwo;
		this.subjectthree = subjectthree;
		this.subjectfour = subjectfour;
		this.graduation = graduation;
		this.companyid = companyid;
	}

	// Property accessors

	public String getStudentid() {
		return this.studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public Date getRegistrationdate() {
		return this.registrationdate;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public String getRegistrationcarname() {
		return this.registrationcarname;
	}

	public void setRegistrationcarname(String registrationcarname) {
		this.registrationcarname = registrationcarname;
	}

	public Date getSchoolopendate() {
		return this.schoolopendate;
	}

	public void setSchoolopendate(Date schoolopendate) {
		this.schoolopendate = schoolopendate;
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

	public String getCoach() {
		return this.coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getEntrance() {
		return this.entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	public String getSubjectone() {
		return this.subjectone;
	}

	public void setSubjectone(String subjectone) {
		this.subjectone = subjectone;
	}

	public String getSubjecttwo() {
		return this.subjecttwo;
	}

	public void setSubjecttwo(String subjecttwo) {
		this.subjecttwo = subjecttwo;
	}

	public String getSubjectthree() {
		return this.subjectthree;
	}

	public void setSubjectthree(String subjectthree) {
		this.subjectthree = subjectthree;
	}

	public String getSubjectfour() {
		return this.subjectfour;
	}

	public void setSubjectfour(String subjectfour) {
		this.subjectfour = subjectfour;
	}

	public String getGraduation() {
		return this.graduation;
	}

	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
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

	public String getTheProgress() {
		return theProgress;
	}

	public void setTheProgress(String theProgress) {
		this.theProgress = theProgress;
	}

	public String getStudentcode() {
		return studentcode;
	}

	public void setStudentcode(String studentcode) {
		this.studentcode = studentcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}