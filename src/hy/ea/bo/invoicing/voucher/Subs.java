package hy.ea.bo.invoicing.voucher;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 凭证科目期初余额表
 * 陈婷
 */
public class Subs implements BaseBean{
	private String subearlyKey;        		//科目子表主键
	private String subearlyID;         		//科目子表业务主键
	private String companyID;				//公司ID
	private String subjectsID;				//科目ID
	private String subjectsPID;				//父科目ID
	private String sdirection;              //期初方向
	private String edirection;              //期末方向
	private String subjectsNumbers;			//科目编号
	private String subjectsName;			//科目名称
	private String currentLevel;			//当前级别
	private String startCash;				//期初余额
	private String cloan;                   //本期借方
	private String cforLoan;                //本期贷方
	private String endCash;					//期末余额
	private Date datess;					//日期
	private String install;					//判断
	public String getSubearlyKey() {
		return subearlyKey;
	}
	public void setSubearlyKey(String subearlyKey) {
		this.subearlyKey = subearlyKey;
	}
	public String getSubearlyID() {
		return subearlyID;
	}
	public void setSubearlyID(String subearlyID) {
		this.subearlyID = subearlyID;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getSubjectsNumbers() {
		return subjectsNumbers;
	}
	public void setSubjectsNumbers(String subjectsNumbers) {
		this.subjectsNumbers = subjectsNumbers;
	}
	public String getSubjectsName() {
		return subjectsName;
	}
	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}
	public String getStartCash() {
		return startCash;
	}
	public void setStartCash(String startCash) {
		this.startCash = startCash;
	}
	public String getEndCash() {
		return endCash;
	}
	public void setEndCash(String endCash) {
		this.endCash = endCash;
	}
	public Date getDatess() {
		return datess;
	}
	public void setDatess(Date datess) {
		this.datess = datess;
	}
	public String getInstall() {
		return install;
	}
	public void setInstall(String install) {
		this.install = install;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	public String getSdirection() {
		return sdirection;
	}
	public void setSdirection(String sdirection) {
		this.sdirection = sdirection;
	}
	public String getEdirection() {
		return edirection;
	}
	public void setEdirection(String edirection) {
		this.edirection = edirection;
	}
	public String getCloan() {
		return cloan;
	}
	public void setCloan(String cloan) {
		this.cloan = cloan;
	}
	public String getCforLoan() {
		return cforLoan;
	}
	public void setCforLoan(String cforLoan) {
		this.cforLoan = cforLoan;
	}
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String getSubjectsPID() {
		return subjectsPID;
	}
	public void setSubjectsPID(String subjectsPID) {
		this.subjectsPID = subjectsPID;
	}
}
