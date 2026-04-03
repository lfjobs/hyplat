package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 社保设置
 * @author
 *
 */
public class SocialSecuritySetup implements BaseBean, Serializable {
	private static final long serialVersionUID = -44214909491977594L;
	private String socialSecuritySetupId;
	private String companyId;
	private String deductionType;//扣社保方式，统一扣：UNIFIED,按工资计算:COMPUTE
	private String socialSecurityLowLevel;//社保最低基数
	private String socialSecurityLevel;//社保缴纳基数
	private String elderlyCareRate;//养老比率
	private String medicalRate;//医疗比率
	private String unemploymentRete;//失业金比率
	private String elderlyCareDiscountMoney;//养老扣费
	private String medicalDiscountMoney;//医疗扣费
	private String unemploymentDiscountMoney;//失业金扣费
	private Date setupDate;//设置时间
	private String setStaffId;//设置人Id
	private String staffName;
	private String referenceGuaranteeSalary;
	private String referenceWelfareSalary;

	public SocialSecuritySetup() {
	}

	public SocialSecuritySetup(String socialSecuritySetupId, String companyId, String deductionType, String socialSecurityLowLevel, String socialSecurityLevel, String elderlyCareRate, String medicalRate, String unemploymentRete, String elderlyCareDiscountMoney, String medicalDiscountMoney, String unemploymentDiscountMoney, Date setupDate, String setStaffId) {
		this.socialSecuritySetupId = socialSecuritySetupId;
		this.companyId = companyId;
		this.deductionType = deductionType;
		this.socialSecurityLowLevel = socialSecurityLowLevel;
		this.socialSecurityLevel = socialSecurityLevel;
		this.elderlyCareRate = elderlyCareRate;
		this.medicalRate = medicalRate;
		this.unemploymentRete = unemploymentRete;
		this.elderlyCareDiscountMoney = elderlyCareDiscountMoney;
		this.medicalDiscountMoney = medicalDiscountMoney;
		this.unemploymentDiscountMoney = unemploymentDiscountMoney;
		this.setupDate = setupDate;
		this.setStaffId = setStaffId;
	}

	public String getSocialSecuritySetupId() {
		return socialSecuritySetupId;
	}

	public void setSocialSecuritySetupId(String socialSecuritySetupId) {
		this.socialSecuritySetupId = socialSecuritySetupId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}

	public String getSocialSecurityLowLevel() {
		return socialSecurityLowLevel;
	}

	public void setSocialSecurityLowLevel(String socialSecurityLowLevel) {
		this.socialSecurityLowLevel = socialSecurityLowLevel;
	}

	public String getSocialSecurityLevel() {
		return socialSecurityLevel;
	}

	public void setSocialSecurityLevel(String socialSecurityLevel) {
		this.socialSecurityLevel = socialSecurityLevel;
	}

	public String getElderlyCareRate() {
		return elderlyCareRate;
	}

	public void setElderlyCareRate(String elderlyCareRate) {
		this.elderlyCareRate = elderlyCareRate;
	}

	public String getUnemploymentRete() {
		return unemploymentRete;
	}

	public void setUnemploymentRete(String unemploymentRete) {
		this.unemploymentRete = unemploymentRete;
	}

	public String getElderlyCareDiscountMoney() {
		return elderlyCareDiscountMoney;
	}

	public void setElderlyCareDiscountMoney(String elderlyCareDiscountMoney) {
		this.elderlyCareDiscountMoney = elderlyCareDiscountMoney;
	}

	public String getUnemploymentDiscountMoney() {
		return unemploymentDiscountMoney;
	}

	public void setUnemploymentDiscountMoney(String unemploymentDiscountMoney) {
		this.unemploymentDiscountMoney = unemploymentDiscountMoney;
	}

	public Date getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public String getSetStaffId() {
		return setStaffId;
	}

	public void setSetStaffId(String setStaffId) {
		this.setStaffId = setStaffId;
	}

	public String getMedicalRate() {
		return medicalRate;
	}

	public void setMedicalRate(String medicalRate) {
		this.medicalRate = medicalRate;
	}

	public String getMedicalDiscountMoney() {
		return medicalDiscountMoney;
	}

	public void setMedicalDiscountMoney(String medicalDiscountMoney) {
		this.medicalDiscountMoney = medicalDiscountMoney;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getReferenceGuaranteeSalary() {
		return referenceGuaranteeSalary;
	}

	public void setReferenceGuaranteeSalary(String referenceGuaranteeSalary) {
		this.referenceGuaranteeSalary = referenceGuaranteeSalary;
	}

	public String getReferenceWelfareSalary() {
		return referenceWelfareSalary;
	}

	public void setReferenceWelfareSalary(String referenceWelfareSalary) {
		this.referenceWelfareSalary = referenceWelfareSalary;
	}
}
