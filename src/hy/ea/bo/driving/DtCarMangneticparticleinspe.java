package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarMangneticparticleinspe entity. @author MyEclipse Persistence Tools
 */

public class DtCarMangneticparticleinspe implements BaseBean {

	// Fields
  //缠绕瓶磁粉探伤记录表
	private String mangneticparticleinspekey;
	private String mangneticparticleinspeid;
	private String companyid;
	private String organizationid;
	private String carCylinderId;
	private String checkoutNum;
	private String standard;
	private String heatTreatedCondition;
	private String surfaceStates;
	private String texture;
	private String detectionDeviceModelName;
	private String magnetizationMethod;
	private String typesMagneticPowder;
	private String magneticSuspensionType;
	private String magneticSuspensionConcentratio;
	private String sensitivityTestPanel;
	private String magnetizingTime;
	private String magnetizationLongitudinal;
	private String magnetizationCircumferential;
	private String magneticParticleMethod;
	private String testStandard;
	private String initialDefects;
	private String defectsTheEnd;
	private String longestDefectsStarting;
	private String maximumLengthDefects;
	private String defectQty;
	private String natureTheDefect;
	private String evaluateLevelof;
	private String conclusion;
	private String operator;
	private String inspector;
	private Date analysisDate;
	private String remark;

	// Constructors

	/** default constructor */
	public DtCarMangneticparticleinspe() {
	}

	/** full constructor */
	public DtCarMangneticparticleinspe(String mangneticparticleinspeid,
			String companyid, String organizationid, String carCylinderId,
			String checkoutNum, String standard, String heatTreatedCondition,
			String surfaceStates, String texture,
			String detectionDeviceModelName, String magnetizationMethod,
			String typesMagneticPowder, String magneticSuspensionType,
			String magneticSuspensionConcentratio, String sensitivityTestPanel,
			String magnetizingTime, String magnetizationLongitudinal,
			String magnetizationCircumferential, String magneticParticleMethod,
			String testStandard, String initialDefects, String defectsTheEnd,
			String longestDefectsStarting, String maximumLengthDefects,
			String defectQty, String natureTheDefect, String evaluateLevelof,
			String conclusion, String operator, String inspector,
			Date analysisDate, String remark) {
		this.mangneticparticleinspeid = mangneticparticleinspeid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.carCylinderId = carCylinderId;
		this.checkoutNum = checkoutNum;
		this.standard = standard;
		this.heatTreatedCondition = heatTreatedCondition;
		this.surfaceStates = surfaceStates;
		this.texture = texture;
		this.detectionDeviceModelName = detectionDeviceModelName;
		this.magnetizationMethod = magnetizationMethod;
		this.typesMagneticPowder = typesMagneticPowder;
		this.magneticSuspensionType = magneticSuspensionType;
		this.magneticSuspensionConcentratio = magneticSuspensionConcentratio;
		this.sensitivityTestPanel = sensitivityTestPanel;
		this.magnetizingTime = magnetizingTime;
		this.magnetizationLongitudinal = magnetizationLongitudinal;
		this.magnetizationCircumferential = magnetizationCircumferential;
		this.magneticParticleMethod = magneticParticleMethod;
		this.testStandard = testStandard;
		this.initialDefects = initialDefects;
		this.defectsTheEnd = defectsTheEnd;
		this.longestDefectsStarting = longestDefectsStarting;
		this.maximumLengthDefects = maximumLengthDefects;
		this.defectQty = defectQty;
		this.natureTheDefect = natureTheDefect;
		this.evaluateLevelof = evaluateLevelof;
		this.conclusion = conclusion;
		this.operator = operator;
		this.inspector = inspector;
		this.analysisDate = analysisDate;
		this.remark = remark;
	}

	// Property accessors

	public String getMangneticparticleinspekey() {
		return this.mangneticparticleinspekey;
	}

	public void setMangneticparticleinspekey(String mangneticparticleinspekey) {
		this.mangneticparticleinspekey = mangneticparticleinspekey;
	}

	public String getMangneticparticleinspeid() {
		return this.mangneticparticleinspeid;
	}

	public void setMangneticparticleinspeid(String mangneticparticleinspeid) {
		this.mangneticparticleinspeid = mangneticparticleinspeid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getCarCylinderId() {
		return this.carCylinderId;
	}

	public void setCarCylinderId(String carCylinderId) {
		this.carCylinderId = carCylinderId;
	}

	public String getCheckoutNum() {
		return this.checkoutNum;
	}

	public void setCheckoutNum(String checkoutNum) {
		this.checkoutNum = checkoutNum;
	}

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getHeatTreatedCondition() {
		return this.heatTreatedCondition;
	}

	public void setHeatTreatedCondition(String heatTreatedCondition) {
		this.heatTreatedCondition = heatTreatedCondition;
	}

	public String getSurfaceStates() {
		return this.surfaceStates;
	}

	public void setSurfaceStates(String surfaceStates) {
		this.surfaceStates = surfaceStates;
	}

	public String getTexture() {
		return this.texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getDetectionDeviceModelName() {
		return this.detectionDeviceModelName;
	}

	public void setDetectionDeviceModelName(String detectionDeviceModelName) {
		this.detectionDeviceModelName = detectionDeviceModelName;
	}

	public String getMagnetizationMethod() {
		return this.magnetizationMethod;
	}

	public void setMagnetizationMethod(String magnetizationMethod) {
		this.magnetizationMethod = magnetizationMethod;
	}

	public String getTypesMagneticPowder() {
		return this.typesMagneticPowder;
	}

	public void setTypesMagneticPowder(String typesMagneticPowder) {
		this.typesMagneticPowder = typesMagneticPowder;
	}

	public String getMagneticSuspensionType() {
		return this.magneticSuspensionType;
	}

	public void setMagneticSuspensionType(String magneticSuspensionType) {
		this.magneticSuspensionType = magneticSuspensionType;
	}

	public String getMagneticSuspensionConcentratio() {
		return this.magneticSuspensionConcentratio;
	}

	public void setMagneticSuspensionConcentratio(
			String magneticSuspensionConcentratio) {
		this.magneticSuspensionConcentratio = magneticSuspensionConcentratio;
	}

	public String getSensitivityTestPanel() {
		return this.sensitivityTestPanel;
	}

	public void setSensitivityTestPanel(String sensitivityTestPanel) {
		this.sensitivityTestPanel = sensitivityTestPanel;
	}

	public String getMagnetizingTime() {
		return this.magnetizingTime;
	}

	public void setMagnetizingTime(String magnetizingTime) {
		this.magnetizingTime = magnetizingTime;
	}

	public String getMagnetizationLongitudinal() {
		return this.magnetizationLongitudinal;
	}

	public void setMagnetizationLongitudinal(String magnetizationLongitudinal) {
		this.magnetizationLongitudinal = magnetizationLongitudinal;
	}

	public String getMagnetizationCircumferential() {
		return this.magnetizationCircumferential;
	}

	public void setMagnetizationCircumferential(
			String magnetizationCircumferential) {
		this.magnetizationCircumferential = magnetizationCircumferential;
	}

	public String getMagneticParticleMethod() {
		return this.magneticParticleMethod;
	}

	public void setMagneticParticleMethod(String magneticParticleMethod) {
		this.magneticParticleMethod = magneticParticleMethod;
	}

	public String getTestStandard() {
		return this.testStandard;
	}

	public void setTestStandard(String testStandard) {
		this.testStandard = testStandard;
	}

	public String getInitialDefects() {
		return this.initialDefects;
	}

	public void setInitialDefects(String initialDefects) {
		this.initialDefects = initialDefects;
	}

	public String getDefectsTheEnd() {
		return this.defectsTheEnd;
	}

	public void setDefectsTheEnd(String defectsTheEnd) {
		this.defectsTheEnd = defectsTheEnd;
	}

	public String getLongestDefectsStarting() {
		return this.longestDefectsStarting;
	}

	public void setLongestDefectsStarting(String longestDefectsStarting) {
		this.longestDefectsStarting = longestDefectsStarting;
	}

	public String getMaximumLengthDefects() {
		return this.maximumLengthDefects;
	}

	public void setMaximumLengthDefects(String maximumLengthDefects) {
		this.maximumLengthDefects = maximumLengthDefects;
	}

	public String getDefectQty() {
		return this.defectQty;
	}

	public void setDefectQty(String defectQty) {
		this.defectQty = defectQty;
	}

	public String getNatureTheDefect() {
		return this.natureTheDefect;
	}

	public void setNatureTheDefect(String natureTheDefect) {
		this.natureTheDefect = natureTheDefect;
	}

	public String getEvaluateLevelof() {
		return this.evaluateLevelof;
	}

	public void setEvaluateLevelof(String evaluateLevelof) {
		this.evaluateLevelof = evaluateLevelof;
	}

	public String getConclusion() {
		return this.conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInspector() {
		return this.inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public Date getAnalysisDate() {
		return this.analysisDate;
	}

	public void setAnalysisDate(Date analysisDate) {
		this.analysisDate = analysisDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}