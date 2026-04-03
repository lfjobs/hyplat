package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarCarwindingdetection entity. @author MyEclipse Persistence Tools
 */

public class DtCarCarwindingdetection implements BaseBean {

	// Fields
//气瓶缠绕瓶检测记录表
	private String carwindingkey;
	private String carwindingdetectionid;
	private String companyid;
	private String organizationid;
	private String checkoutNum;
	private String carCylinderId;
	private String scratchScratchChisel;  //划伤擦伤凿伤
	private String heatDamage;//热火损伤 
	private String abrasion;//磨损 
	private String gasLeakage;//气体泄漏 
	private String chemicalCorrosion;//化学品腐蚀
	private String naturalAgeing;//自然老化
	private String carAccidents;//汽车事故着火受高热
	private String impactForce;//冲击力 
	private String stressCorrosion;//应力腐蚀裂纹
	private String leakageMetalScratchScratchChis;//外漏金属划伤擦伤凿伤
	private String embossing;//凸起 
	private String sunken;//凹陷
	private String pittingCorrosion;//点腐蚀
	private String lineCorrosion;//线腐蚀
	private String surfaceCorrosion;//面腐蚀
	private String metalCorrosionUnder;//缠绕层下的金属腐蚀
	private String threadInspection;//瓶口螺纹检查
	private String appearanceInspectionResults;//外观检查结果
	private String appearanceInspectionPeople;//外观检查人
	private String bareMetalPartsNondestructiveIn;//裸露金属部分无损检查
	private String bareMetalTestResults;//裸露金属检查结果
	private String bareMetalInspection;//裸露金属检查人
	private String measuredBottleWeight;//实测瓶重(kg) 
	private String totalWeightBottlesWater;//瓶水总重(kg) 
	private String waterHeavy;//水重(kg)
	private String waterVolumeCoefficient;//水容积系数(L/kg)
	private String measuredVolume;// 实测容积(L)
	private String measuredResults;//实测检查结果
	private String measuredCheckPeople;//实测检查人  
	private String phValue;//Ph 
	private String phAndBt;//Ph*βT	
	private String pushTotalWaterValue;//压入总水量A值（ml）
	private String waterPipePressureBvalue;//管道压入水量B值（ml）
	private String residualDeformationValue;//残余变形值（ml）
	private String wholeDeformationValue;//全变形值（ml）
	private String residualDeformationRate;//残余变形率（%） 
	private String hydrostaticTestResults;//水压试验检查结果
	private String checkWaterPressureTest;//水压试验检查人
	private String internalDry;//内部干燥   
	private String valveReplacement;//瓶阀更换
	private String airTightTest;//气密性试验（20Mpa）
	private String internalInspectionResult;//内部检查结果  
	private String internalInspectionPeople;//内部检查人  
	private String vacuumReplacement;//抽真空或置换
	private String inspectionTag;//检验标记 
	private Date nextTestDate;//下次检验日期
	private String spreading;//涂敷
	private String vacuumCoatingTestResults;//真空涂敷检查结果
	private String vacuumCoatingInspection;//真空涂敷检查人
	private String gasCylindersDisassemblingUnits;//气瓶拆装单位 
	private String dismantlingUnitTestResults;//拆装单位检查结果
	private String checkOneUnit;// 拆装单位检查人
	private String resultInspection;//检查结论 
	private String inspector;//检 查 员
	private String recorder;//记 录 员
	private Date inspectionDate;//检验日期

	// Constructors

	/** default constructor */
	public DtCarCarwindingdetection() {
	}

	/** full constructor */
	public DtCarCarwindingdetection(String carwindingdetectionid,
			String companyid, String organizationid, String checkoutNum,String carCylinderId,
			String scratchScratchChisel, String heatDamage,
			String abrasion, String gasLeakage,String chemicalCorrosion, String naturalAgeing,
			String carAccidents, String impactForce, String stressCorrosion,
			String leakageMetalScratchScratchChis, String embossing,
			String sunken, String pittingCorrosion, String lineCorrosion,
			String surfaceCorrosion, String metalCorrosionUnder,
			String threadInspection, String appearanceInspectionResults,
			String appearanceInspectionPeople,
			String bareMetalPartsNondestructiveIn, String bareMetalTestResults,
			String bareMetalInspection, String measuredBottleWeight,
			String totalWeightBottlesWater, String waterHeavy,
			String waterVolumeCoefficient, String measuredVolume,
			String measuredResults, String measuredCheckPeople, String phValue,
			String phAndBt, String pushTotalWaterValue,
			String waterPipePressureBvalue, String residualDeformationValue,
			String wholeDeformationValue, String residualDeformationRate,
			String hydrostaticTestResults, String checkWaterPressureTest,
			String internalDry, String valveReplacement, String airTightTest,
			String internalInspectionResult, String internalInspectionPeople,
			String vacuumReplacement, String inspectionTag, Date nextTestDate,
			String spreading, String vacuumCoatingTestResults,
			String vacuumCoatingInspection,
			String gasCylindersDisassemblingUnits,
			String dismantlingUnitTestResults, String checkOneUnit,
			String resultInspection, String inspector, String recorder,
			Date inspectionDate) {
		this.carwindingdetectionid = carwindingdetectionid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.checkoutNum = checkoutNum;
		this.carCylinderId = carCylinderId;
		this.scratchScratchChisel = scratchScratchChisel;
		this.heatDamage = heatDamage;
		this.chemicalCorrosion = chemicalCorrosion;
		this.abrasion = abrasion;
		this.gasLeakage = gasLeakage;
		this.naturalAgeing = naturalAgeing;
		this.carAccidents = carAccidents;
		this.impactForce = impactForce;
		this.stressCorrosion = stressCorrosion;
		this.leakageMetalScratchScratchChis = leakageMetalScratchScratchChis;
		this.embossing = embossing;
		this.sunken = sunken;
		this.pittingCorrosion = pittingCorrosion;
		this.lineCorrosion = lineCorrosion;
		this.surfaceCorrosion = surfaceCorrosion;
		this.metalCorrosionUnder = metalCorrosionUnder;
		this.threadInspection = threadInspection;
		this.appearanceInspectionResults = appearanceInspectionResults;
		this.appearanceInspectionPeople = appearanceInspectionPeople;
		this.bareMetalPartsNondestructiveIn = bareMetalPartsNondestructiveIn;
		this.bareMetalTestResults = bareMetalTestResults;
		this.bareMetalInspection = bareMetalInspection;
		this.measuredBottleWeight = measuredBottleWeight;
		this.totalWeightBottlesWater = totalWeightBottlesWater;
		this.waterHeavy = waterHeavy;
		this.waterVolumeCoefficient = waterVolumeCoefficient;
		this.measuredVolume = measuredVolume;
		this.measuredResults = measuredResults;
		this.measuredCheckPeople = measuredCheckPeople;
		this.phValue = phValue;
		this.phAndBt = phAndBt;
		this.pushTotalWaterValue = pushTotalWaterValue;
		this.waterPipePressureBvalue = waterPipePressureBvalue;
		this.residualDeformationValue = residualDeformationValue;
		this.wholeDeformationValue = wholeDeformationValue;
		this.residualDeformationRate = residualDeformationRate;
		this.hydrostaticTestResults = hydrostaticTestResults;
		this.checkWaterPressureTest = checkWaterPressureTest;
		this.internalDry = internalDry;
		this.valveReplacement = valveReplacement;
		this.airTightTest = airTightTest;
		this.internalInspectionResult = internalInspectionResult;
		this.internalInspectionPeople = internalInspectionPeople;
		this.vacuumReplacement = vacuumReplacement;
		this.inspectionTag = inspectionTag;
		this.nextTestDate = nextTestDate;
		this.spreading = spreading;
		this.vacuumCoatingTestResults = vacuumCoatingTestResults;
		this.vacuumCoatingInspection = vacuumCoatingInspection;
		this.gasCylindersDisassemblingUnits = gasCylindersDisassemblingUnits;
		this.dismantlingUnitTestResults = dismantlingUnitTestResults;
		this.checkOneUnit = checkOneUnit;
		this.resultInspection = resultInspection;
		this.inspector = inspector;
		this.recorder = recorder;
		this.inspectionDate = inspectionDate;
	}

	// Property accessors

	public String getCarwindingkey() {
		return this.carwindingkey;
	}

	public void setCarwindingkey(String carwindingkey) {
		this.carwindingkey = carwindingkey;
	}

	public String getCarwindingdetectionid() {
		return this.carwindingdetectionid;
	}

	public void setCarwindingdetectionid(String carwindingdetectionid) {
		this.carwindingdetectionid = carwindingdetectionid;
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

	public String getCheckoutNum() {
		return checkoutNum;
	}

	public void setCheckoutNum(String checkoutNum) {
		this.checkoutNum = checkoutNum;
	}

	public void setCarCylinderId(String carCylinderId) {
		this.carCylinderId = carCylinderId;
	}

	public String getScratchScratchChisel() {
		return this.scratchScratchChisel;
	}

	public void setScratchScratchChisel(String scratchScratchChisel) {
		this.scratchScratchChisel = scratchScratchChisel;
	}

	public String getHeatDamage() {
		return this.heatDamage;
	}

	public void setHeatDamage(String heatDamage) {
		this.heatDamage = heatDamage;
	}

	public String getAbrasion() {
		return this.abrasion;
	}

	public void setAbrasion(String abrasion) {
		this.abrasion = abrasion;
	}

	public String getGasLeakage() {
		return this.gasLeakage;
	}

	public void setGasLeakage(String gasLeakage) {
		this.gasLeakage = gasLeakage;
	}

	public String getNaturalAgeing() {
		return this.naturalAgeing;
	}

	public void setNaturalAgeing(String naturalAgeing) {
		this.naturalAgeing = naturalAgeing;
	}

	public String getCarAccidents() {
		return this.carAccidents;
	}

	public void setCarAccidents(String carAccidents) {
		this.carAccidents = carAccidents;
	}

	public String getImpactForce() {
		return this.impactForce;
	}

	public void setImpactForce(String impactForce) {
		this.impactForce = impactForce;
	}

	public String getStressCorrosion() {
		return this.stressCorrosion;
	}

	public void setStressCorrosion(String stressCorrosion) {
		this.stressCorrosion = stressCorrosion;
	}

	public String getLeakageMetalScratchScratchChis() {
		return this.leakageMetalScratchScratchChis;
	}

	public void setLeakageMetalScratchScratchChis(
			String leakageMetalScratchScratchChis) {
		this.leakageMetalScratchScratchChis = leakageMetalScratchScratchChis;
	}

	public String getEmbossing() {
		return this.embossing;
	}

	public void setEmbossing(String embossing) {
		this.embossing = embossing;
	}

	public String getSunken() {
		return this.sunken;
	}

	public void setSunken(String sunken) {
		this.sunken = sunken;
	}

	public String getPittingCorrosion() {
		return this.pittingCorrosion;
	}

	public void setPittingCorrosion(String pittingCorrosion) {
		this.pittingCorrosion = pittingCorrosion;
	}

	public String getLineCorrosion() {
		return this.lineCorrosion;
	}

	public void setLineCorrosion(String lineCorrosion) {
		this.lineCorrosion = lineCorrosion;
	}

	public String getSurfaceCorrosion() {
		return this.surfaceCorrosion;
	}

	public void setSurfaceCorrosion(String surfaceCorrosion) {
		this.surfaceCorrosion = surfaceCorrosion;
	}

	public String getMetalCorrosionUnder() {
		return this.metalCorrosionUnder;
	}

	public void setMetalCorrosionUnder(String metalCorrosionUnder) {
		this.metalCorrosionUnder = metalCorrosionUnder;
	}

	public String getThreadInspection() {
		return this.threadInspection;
	}

	public void setThreadInspection(String threadInspection) {
		this.threadInspection = threadInspection;
	}

	public String getAppearanceInspectionResults() {
		return this.appearanceInspectionResults;
	}

	public void setAppearanceInspectionResults(
			String appearanceInspectionResults) {
		this.appearanceInspectionResults = appearanceInspectionResults;
	}

	public String getAppearanceInspectionPeople() {
		return this.appearanceInspectionPeople;
	}

	public void setAppearanceInspectionPeople(String appearanceInspectionPeople) {
		this.appearanceInspectionPeople = appearanceInspectionPeople;
	}

	public String getBareMetalPartsNondestructiveIn() {
		return this.bareMetalPartsNondestructiveIn;
	}

	public void setBareMetalPartsNondestructiveIn(
			String bareMetalPartsNondestructiveIn) {
		this.bareMetalPartsNondestructiveIn = bareMetalPartsNondestructiveIn;
	}

	public String getBareMetalTestResults() {
		return this.bareMetalTestResults;
	}

	public void setBareMetalTestResults(String bareMetalTestResults) {
		this.bareMetalTestResults = bareMetalTestResults;
	}

	public String getBareMetalInspection() {
		return this.bareMetalInspection;
	}

	public void setBareMetalInspection(String bareMetalInspection) {
		this.bareMetalInspection = bareMetalInspection;
	}

	public String getMeasuredBottleWeight() {
		return this.measuredBottleWeight;
	}

	public void setMeasuredBottleWeight(String measuredBottleWeight) {
		this.measuredBottleWeight = measuredBottleWeight;
	}

	public String getTotalWeightBottlesWater() {
		return this.totalWeightBottlesWater;
	}

	public void setTotalWeightBottlesWater(String totalWeightBottlesWater) {
		this.totalWeightBottlesWater = totalWeightBottlesWater;
	}

	public String getWaterHeavy() {
		return this.waterHeavy;
	}

	public void setWaterHeavy(String waterHeavy) {
		this.waterHeavy = waterHeavy;
	}

	public String getWaterVolumeCoefficient() {
		return this.waterVolumeCoefficient;
	}

	public void setWaterVolumeCoefficient(String waterVolumeCoefficient) {
		this.waterVolumeCoefficient = waterVolumeCoefficient;
	}

	public String getMeasuredVolume() {
		return this.measuredVolume;
	}

	public void setMeasuredVolume(String measuredVolume) {
		this.measuredVolume = measuredVolume;
	}

	public String getMeasuredResults() {
		return this.measuredResults;
	}

	public void setMeasuredResults(String measuredResults) {
		this.measuredResults = measuredResults;
	}

	public String getMeasuredCheckPeople() {
		return this.measuredCheckPeople;
	}

	public void setMeasuredCheckPeople(String measuredCheckPeople) {
		this.measuredCheckPeople = measuredCheckPeople;
	}

	public String getPhValue() {
		return this.phValue;
	}

	public void setPhValue(String phValue) {
		this.phValue = phValue;
	}

	public String getPhAndBt() {
		return this.phAndBt;
	}

	public void setPhAndBt(String phAndBt) {
		this.phAndBt = phAndBt;
	}

	public String getPushTotalWaterValue() {
		return this.pushTotalWaterValue;
	}

	public void setPushTotalWaterValue(String pushTotalWaterValue) {
		this.pushTotalWaterValue = pushTotalWaterValue;
	}

	public String getWaterPipePressureBvalue() {
		return this.waterPipePressureBvalue;
	}

	public void setWaterPipePressureBvalue(String waterPipePressureBvalue) {
		this.waterPipePressureBvalue = waterPipePressureBvalue;
	}

	public String getResidualDeformationValue() {
		return this.residualDeformationValue;
	}

	public void setResidualDeformationValue(String residualDeformationValue) {
		this.residualDeformationValue = residualDeformationValue;
	}

	public String getWholeDeformationValue() {
		return this.wholeDeformationValue;
	}

	public void setWholeDeformationValue(String wholeDeformationValue) {
		this.wholeDeformationValue = wholeDeformationValue;
	}

	public String getResidualDeformationRate() {
		return this.residualDeformationRate;
	}

	public void setResidualDeformationRate(String residualDeformationRate) {
		this.residualDeformationRate = residualDeformationRate;
	}

	public String getHydrostaticTestResults() {
		return this.hydrostaticTestResults;
	}

	public void setHydrostaticTestResults(String hydrostaticTestResults) {
		this.hydrostaticTestResults = hydrostaticTestResults;
	}

	public String getCheckWaterPressureTest() {
		return this.checkWaterPressureTest;
	}

	public void setCheckWaterPressureTest(String checkWaterPressureTest) {
		this.checkWaterPressureTest = checkWaterPressureTest;
	}

	public String getInternalDry() {
		return this.internalDry;
	}

	public void setInternalDry(String internalDry) {
		this.internalDry = internalDry;
	}

	public String getValveReplacement() {
		return this.valveReplacement;
	}

	public void setValveReplacement(String valveReplacement) {
		this.valveReplacement = valveReplacement;
	}

	public String getAirTightTest() {
		return this.airTightTest;
	}

	public void setAirTightTest(String airTightTest) {
		this.airTightTest = airTightTest;
	}

	public String getInternalInspectionResult() {
		return this.internalInspectionResult;
	}

	public void setInternalInspectionResult(String internalInspectionResult) {
		this.internalInspectionResult = internalInspectionResult;
	}

	public String getInternalInspectionPeople() {
		return this.internalInspectionPeople;
	}

	public void setInternalInspectionPeople(String internalInspectionPeople) {
		this.internalInspectionPeople = internalInspectionPeople;
	}

	public String getVacuumReplacement() {
		return this.vacuumReplacement;
	}

	public void setVacuumReplacement(String vacuumReplacement) {
		this.vacuumReplacement = vacuumReplacement;
	}

	public String getInspectionTag() {
		return this.inspectionTag;
	}

	public void setInspectionTag(String inspectionTag) {
		this.inspectionTag = inspectionTag;
	}

	public Date getNextTestDate() {
		return this.nextTestDate;
	}

	public void setNextTestDate(Date nextTestDate) {
		this.nextTestDate = nextTestDate;
	}

	public String getSpreading() {
		return this.spreading;
	}

	public void setSpreading(String spreading) {
		this.spreading = spreading;
	}

	public String getVacuumCoatingTestResults() {
		return this.vacuumCoatingTestResults;
	}

	public void setVacuumCoatingTestResults(String vacuumCoatingTestResults) {
		this.vacuumCoatingTestResults = vacuumCoatingTestResults;
	}

	public String getVacuumCoatingInspection() {
		return this.vacuumCoatingInspection;
	}

	public void setVacuumCoatingInspection(String vacuumCoatingInspection) {
		this.vacuumCoatingInspection = vacuumCoatingInspection;
	}

	public String getGasCylindersDisassemblingUnits() {
		return this.gasCylindersDisassemblingUnits;
	}

	public void setGasCylindersDisassemblingUnits(
			String gasCylindersDisassemblingUnits) {
		this.gasCylindersDisassemblingUnits = gasCylindersDisassemblingUnits;
	}

	public String getDismantlingUnitTestResults() {
		return this.dismantlingUnitTestResults;
	}

	public void setDismantlingUnitTestResults(String dismantlingUnitTestResults) {
		this.dismantlingUnitTestResults = dismantlingUnitTestResults;
	}

	public String getCheckOneUnit() {
		return this.checkOneUnit;
	}

	public void setCheckOneUnit(String checkOneUnit) {
		this.checkOneUnit = checkOneUnit;
	}

	public String getResultInspection() {
		return this.resultInspection;
	}

	public void setResultInspection(String resultInspection) {
		this.resultInspection = resultInspection;
	}

	public String getInspector() {
		return this.inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public String getRecorder() {
		return this.recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public Date getInspectionDate() {
		return this.inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public String getChemicalCorrosion() {
		return chemicalCorrosion;
	}

	public void setChemicalCorrosion(String chemicalCorrosion) {
		this.chemicalCorrosion = chemicalCorrosion;
	}

}