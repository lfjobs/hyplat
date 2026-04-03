package hy.ea.bo.production.drive;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 
 * 
 * 教练扩展信息
 * 
 * @author mz
 *
 */
public class CoachInfo implements BaseBean {

	private String ciKey;
	private String ciId;
	private String staffID;//人员ID
	private String trainSchool;//之前的培训学校
	private String drivelictype;//驾驶证准驾驶车型
	private Date   drivelicDate;//初领驾照日期
	private Date   trainCertDate;//教证日期
	private String applyCarType;//申请类型
	private String applyAllowCarType;//申请准教车型
	private String iccardCode;//IC卡编号
	private String coachlevel;//教练等级
	private String state;//教练状态
	private String satisdegree;//满意度    
	private String trainArea;//培训场地
	private String briefIntro;//简介
	
	
	
	public Date getTrainCertDate() {
		return trainCertDate;
	}
	public void setTrainCertDate(Date trainCertDate) {
		this.trainCertDate = trainCertDate;
	}
	public String getIccardCode() {
		return iccardCode;
	}
	public void setIccardCode(String iccardCode) {
		this.iccardCode = iccardCode;
	}
	public String getSatisdegree() {
		return satisdegree;
	}
	public void setSatisdegree(String satisdegree) {
		this.satisdegree = satisdegree;
	}
	public String getCiKey() {
		return ciKey;
	}
	public void setCiKey(String ciKey) {
		this.ciKey = ciKey;
	}
	public String getCiId() {
		return ciId;
	}
	public void setCiId(String ciId) {
		this.ciId = ciId;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getTrainSchool() {
		return trainSchool;
	}
	public void setTrainSchool(String trainSchool) {
		this.trainSchool = trainSchool;
	}
	public String getDrivelictype() {
		return drivelictype;
	}
	public void setDrivelictype(String drivelictype) {
		this.drivelictype = drivelictype;
	}
	public Date getDrivelicDate() {
		return drivelicDate;
	}
	public void setDrivelicDate(Date drivelicDate) {
		this.drivelicDate = drivelicDate;
	}
	public String getApplyCarType() {
		return applyCarType;
	}
	public void setApplyCarType(String applyCarType) {
		this.applyCarType = applyCarType;
	}
	public String getApplyAllowCarType() {
		return applyAllowCarType;
	}
	public void setApplyAllowCarType(String applyAllowCarType) {
		this.applyAllowCarType = applyAllowCarType;
	}
	
	public String getCoachlevel() {
		return coachlevel;
	}
	public void setCoachlevel(String coachlevel) {
		this.coachlevel = coachlevel;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTrainArea() {
		return trainArea;
	}
	public void setTrainArea(String trainArea) {
		this.trainArea = trainArea;
	}
	public String getBriefIntro() {
		return briefIntro;
	}
	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}
	
	
}