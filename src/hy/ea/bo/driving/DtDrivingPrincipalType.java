package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtDrivingPrincipalType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtDrivingPrincipalType implements BaseBean ,java.io.Serializable {

	// Fields

	private String principaltypekey;
	private String principaltypeid;
	private String companyid;
	private String drivingprincipalid;
	private String coachid;
	private String coachname;
	private String carNumber;
	private String testsum;
	private String testresult;
	private String docstatus;
	private String studentstatus;
	private String subjectsstatus;
	private Date   inputTime;
	private String toTestOther; // 01 已预约测试  null  未预约测试
	private String testOtherResult;

	// Constructors

	/** default constructor */
	public DtDrivingPrincipalType() {
	}

	/** minimal constructor */
	public DtDrivingPrincipalType(String principaltypeid) {
		this.principaltypeid = principaltypeid;
	}

	/** full constructor */
	public DtDrivingPrincipalType(String principaltypeid,
			String drivingprincipalid, String coachid, String coachname,
			String testsum, String testresult, String docstatus,
			String studentstatus) {
		this.principaltypeid = principaltypeid;
		this.drivingprincipalid = drivingprincipalid;
		this.coachid = coachid;
		this.coachname = coachname;
		this.testsum = testsum;
		this.testresult = testresult;
		this.docstatus = docstatus;
		this.studentstatus = studentstatus;
	}

	// Property accessors

	public String getPrincipaltypekey() {
		return this.principaltypekey;
	}

	public void setPrincipaltypekey(String principaltypekey) {
		this.principaltypekey = principaltypekey;
	}

	public String getPrincipaltypeid() {
		return this.principaltypeid;
	}

	public void setPrincipaltypeid(String principaltypeid) {
		this.principaltypeid = principaltypeid;
	}

	public String getDrivingprincipalid() {
		return this.drivingprincipalid;
	}

	public void setDrivingprincipalid(String drivingprincipalid) {
		this.drivingprincipalid = drivingprincipalid;
	}

	public String getCoachid() {
		return this.coachid;
	}

	public void setCoachid(String coachid) {
		this.coachid = coachid;
	}

	public String getCoachname() {
		return this.coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getTestsum() {
		return this.testsum;
	}

	public void setTestsum(String testsum) {
		this.testsum = testsum;
	}

	public String getTestresult() {
		return this.testresult;
	}

	public void setTestresult(String testresult) {
		this.testresult = testresult;
	}

	public String getDocstatus() {
		return this.docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	public String getStudentstatus() {
		return this.studentstatus;
	}

	public void setStudentstatus(String studentstatus) {
		this.studentstatus = studentstatus;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getSubjectsstatus() {
		return subjectsstatus;
	}

	public void setSubjectsstatus(String subjectsstatus) {
		this.subjectsstatus = subjectsstatus;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public String getToTestOther() {
		return toTestOther;
	}

	public void setToTestOther(String toTestOther) {
		this.toTestOther = toTestOther;
	}

	public String getTestOtherResult() {
		return testOtherResult;
	}

	public void setTestOtherResult(String testOtherResult) {
		this.testOtherResult = testOtherResult;
	}
	
}