package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtDrivingTestOther entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtDrivingTestOther  implements BaseBean{

	// Fields

	private String key;
	private String id;
	private String companyid;
	private String drivingprincipalid;
	private String principaltypeid;
	private Date testdate;
	private String examresult;//考试最终结果
	private String examtype;
	private String examScore;//第一次考试得分
	private String appointmenType;//预约方式	
	private String appointmenPeopleID;//约考责任人ID
	private String appointmenPeople;//约考责任人
	private String firstExamTime;//第一次考试时间
	private String firstExamPoints;//第一次考试扣分项目
	private String firstExamresult;//第一次考试结果
	private String firstExaminerID;//第一次考试考官ID
	private String firstExaminer;//第一次考试考官
	
	
	private String secondExamTime;//第二次考试时间
	private String secondExamPoints;//第二次考试扣分项目
	private String secondExamScore;//第二次考试得分
	private String secondExamresult;//第二次考试结果
	private String secondExaminerID;//第二次考试考官
	private String secondExaminer;//第二次考试考官
	
	private String ifPay;//是否缴费
	private double payMoney;//收费金额  非数据库字段
	private String payName;//收费名称 非数据库字段
	// Constructors
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
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getDrivingprincipalid() {
		return drivingprincipalid;
	}
	public void setDrivingprincipalid(String drivingprincipalid) {
		this.drivingprincipalid = drivingprincipalid;
	}
	public String getPrincipaltypeid() {
		return principaltypeid;
	}
	public void setPrincipaltypeid(String principaltypeid) {
		this.principaltypeid = principaltypeid;
	}
	public Date getTestdate() {
		return testdate;
	}
	public void setTestdate(Date testdate) {
		this.testdate = testdate;
	}
	public String getExamresult() {
		return examresult;
	}
	public void setExamresult(String examresult) {
		this.examresult = examresult;
	}
	public String getExamtype() {
		return examtype;
	}
	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}
	public String getExamScore() {
		return examScore;
	}
	public void setExamScore(String examScore) {
		this.examScore = examScore;
	}
	public String getIfPay() {
		return ifPay;
	}
	public void setIfPay(String ifPay) {
		this.ifPay = ifPay;
	}
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getAppointmenType() {
		return appointmenType;
	}
	public void setAppointmenType(String appointmenType) {
		this.appointmenType = appointmenType;
	}
	public String getAppointmenPeopleID() {
		return appointmenPeopleID;
	}
	public void setAppointmenPeopleID(String appointmenPeopleID) {
		this.appointmenPeopleID = appointmenPeopleID;
	}
	public String getAppointmenPeople() {
		return appointmenPeople;
	}
	public void setAppointmenPeople(String appointmenPeople) {
		this.appointmenPeople = appointmenPeople;
	}
	public String getFirstExamTime() {
		return firstExamTime;
	}
	public void setFirstExamTime(String firstExamTime) {
		this.firstExamTime = firstExamTime;
	}
	public String getFirstExamPoints() {
		return firstExamPoints;
	}
	public void setFirstExamPoints(String firstExamPoints) {
		this.firstExamPoints = firstExamPoints;
	}
	public String getFirstExaminerID() {
		return firstExaminerID;
	}
	public void setFirstExaminerID(String firstExaminerID) {
		this.firstExaminerID = firstExaminerID;
	}
	public String getFirstExaminer() {
		return firstExaminer;
	}
	public void setFirstExaminer(String firstExaminer) {
		this.firstExaminer = firstExaminer;
	}
	public String getSecondExamTime() {
		return secondExamTime;
	}
	public void setSecondExamTime(String secondExamTime) {
		this.secondExamTime = secondExamTime;
	}
	public String getSecondExamPoints() {
		return secondExamPoints;
	}
	public void setSecondExamPoints(String secondExamPoints) {
		this.secondExamPoints = secondExamPoints;
	}
	public String getSecondExamScore() {
		return secondExamScore;
	}
	public void setSecondExamScore(String secondExamScore) {
		this.secondExamScore = secondExamScore;
	}
	public String getSecondExamresult() {
		return secondExamresult;
	}
	public void setSecondExamresult(String secondExamresult) {
		this.secondExamresult = secondExamresult;
	}
	public String getSecondExaminerID() {
		return secondExaminerID;
	}
	public void setSecondExaminerID(String secondExaminerID) {
		this.secondExaminerID = secondExaminerID;
	}
	public String getSecondExaminer() {
		return secondExaminer;
	}
	public void setSecondExaminer(String secondExaminer) {
		this.secondExaminer = secondExaminer;
	}
	public String getFirstExamresult() {
		return firstExamresult;
	}
	public void setFirstExamresult(String firstExamresult) {
		this.firstExamresult = firstExamresult;
	}

	
}