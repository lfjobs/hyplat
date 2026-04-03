package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DtDrivingAllInformation implements BaseBean{
		
		private String drivingAllInformationKey;
		private String drivingAllInformationID;
		
		private String companyID;
		private String staffID;
		private String relationID;//往来关系表主键ID
		
		private String dataTitle;//  04 公司信息  05 收费信息 06 证件信息 07 接送信息 08 培训计时
		private Date   addTime;//录入时间
		
		//------ 04 公司信息---------//
		private String organizationID;//分校id  公司子部门id
		private String organizationName;//分校名称 公司子部门名称
		private String registrationPhone;//报名电话
		private Date   signUpDate;//报名时间
		private String referrer; //推荐人  当前公司在职人员   
		private String referrerID;//推荐ID
		private String referrerPhone; //推荐人电话
		private String referrerIdentityCard; //推荐人身份证
		private String acceptPeople;//受理人  当前登录帐号绑定员工
		private String acceptPeopleID;//受理人ID
		
		//-------05收费信息-------------//
		private String chargeName;//收费名称
		
		private String codeID;//逻辑外键
		private String codeValue;//收费标准  金额数
		private Date   chargeTime;//收费时间
		private double chargeMoney;//收款金额
		private double arrearsMoney;//欠款金额
		
		private String cashierBillsID;//关联财务单据
		
		private String billNumber;//票据号
		private String payNumber;//缴款号
		private String settle;//是否结清
		private String arrearsMoneyNumber;//欠款票据号
		private String responsible;//责任人
		
		
		
		//-------06证件信息------------//
		private String applyCertificateID;//申领ID
		private String applyCertificate;//申领
		
		private String drivingCodeID;//准驾车代号ID
		private String drivingCode;//准驾车代号
		
		private Date applyDate;//申请时间
		
		//--------07接送信息---------//
		private String shuttleAddress;//接送地点
		private Date shuttleDate;//接时间
		private Date endShuttleDate;//送时间
		private String shuttleStaffID;//接送责任人ID
		private String shuttleStaff;//接送责任人
		private String shuttleStaffPhone;//接送责任人电话
		private String shuttleCarNumber;//接送车牌号
		private String shuttleCarXinHao;//接送型号
		private String shuttleSumPeople;//乘坐人数
		
		//--------08培训计时---------//
		private Date timingStartTime;//培训开始时间
		private Date timingEndTime;//培训结束时间
		private String timingTime;//培训时间
		private String timingAllTime;//培训总时间
		private String timingNote;//培训备注
		private String subjectStatus;//培训所属科目状态
		
		//---------09分车管理-------//
		private String carNumber;//车辆自编号
		private String carIdentifier;//车辆牌号
		private String carID;//车辆ID
		private String carType;//车辆类型
		private Date carDate;//分车时间
		private String carCoach;//教练员
		
		private Date  searchStaDate;//录入科目时间 查询起时间 非数据库字段
		private Date  searchEndDate;//录入科目时间 查询止时间 非数据库字段
		
		public String getSubjectStatus() {
			return subjectStatus;
		}
		public void setSubjectStatus(String subjectStatus) {
			this.subjectStatus = subjectStatus;
		}
		public String getDrivingAllInformationID() {
			return drivingAllInformationID;
		}
		public void setDrivingAllInformationID(String drivingAllInformationID) {
			this.drivingAllInformationID = drivingAllInformationID;
		}
		public String getDrivingAllInformationKey() {
			return drivingAllInformationKey;
		}
		public void setDrivingAllInformationKey(String drivingAllInformationKey) {
			this.drivingAllInformationKey = drivingAllInformationKey;
		}
		public String getCompanyID() {
			return companyID;
		}
		public void setCompanyID(String companyID) {
			this.companyID = companyID;
		}
		public String getStaffID() {
			return staffID;
		}
		public void setStaffID(String staffID) {
			this.staffID = staffID;
		}
		public String getRelationID() {
			return relationID;
		}
		public void setRelationID(String relationID) {
			this.relationID = relationID;
		}
		public String getOrganizationID() {
			return organizationID;
		}
		public void setOrganizationID(String organizationID) {
			this.organizationID = organizationID;
		}
		public String getOrganizationName() {
			return organizationName;
		}
		public void setOrganizationName(String organizationName) {
			this.organizationName = organizationName;
		}
		public String getRegistrationPhone() {
			return registrationPhone;
		}
		public void setRegistrationPhone(String registrationPhone) {
			this.registrationPhone = registrationPhone;
		}
		public String getReferrer() {
			return referrer;
		}
		public void setReferrer(String referrer) {
			this.referrer = referrer;
		}
		public String getReferrerID() {
			return referrerID;
		}
		public void setReferrerID(String referrerID) {
			this.referrerID = referrerID;
		}
		public String getReferrerPhone() {
			return referrerPhone;
		}
		public void setReferrerPhone(String referrerPhone) {
			this.referrerPhone = referrerPhone;
		}
		public String getReferrerIdentityCard() {
			return referrerIdentityCard;
		}
		public void setReferrerIdentityCard(String referrerIdentityCard) {
			this.referrerIdentityCard = referrerIdentityCard;
		}
		public String getAcceptPeople() {
			return acceptPeople;
		}
		public void setAcceptPeople(String acceptPeople) {
			this.acceptPeople = acceptPeople;
		}
		public String getAcceptPeopleID() {
			return acceptPeopleID;
		}
		public void setAcceptPeopleID(String acceptPeopleID) {
			this.acceptPeopleID = acceptPeopleID;
		}
		public String getDataTitle() {
			return dataTitle;
		}
		public void setDataTitle(String dataTitle) {
			this.dataTitle = dataTitle;
		}
		public String getCodeID() {
			return codeID;
		}
		public void setCodeID(String codeID) {
			this.codeID = codeID;
		}
		public String getCodeValue() {
			return codeValue;
		}
		public void setCodeValue(String codeValue) {
			this.codeValue = codeValue;
		}
		public String getChargeName() {
			return chargeName;
		}
		public void setChargeName(String chargeName) {
			this.chargeName = chargeName;
		}
		public double getChargeMoney() {
			return chargeMoney;
		}
		public void setChargeMoney(double chargeMoney) {
			this.chargeMoney = chargeMoney;
		}
		public double getArrearsMoney() {
			return arrearsMoney;
		}
		public void setArrearsMoney(double arrearsMoney) {
			this.arrearsMoney = arrearsMoney;
		}
		public String getApplyCertificateID() {
			return applyCertificateID;
		}
		public void setApplyCertificateID(String applyCertificateID) {
			this.applyCertificateID = applyCertificateID;
		}
		public String getApplyCertificate() {
			return applyCertificate;
		}
		public void setApplyCertificate(String applyCertificate) {
			this.applyCertificate = applyCertificate;
		}
		public String getDrivingCodeID() {
			return drivingCodeID;
		}
		public void setDrivingCodeID(String drivingCodeID) {
			this.drivingCodeID = drivingCodeID;
		}
		public String getDrivingCode() {
			return drivingCode;
		}
		public void setDrivingCode(String drivingCode) {
			this.drivingCode = drivingCode;
		}
		public Date getApplyDate() {
			return applyDate;
		}
		public void setApplyDate(Date applyDate) {
			this.applyDate = applyDate;
		}
		public String getShuttleAddress() {
			return shuttleAddress;
		}
		public void setShuttleAddress(String shuttleAddress) {
			this.shuttleAddress = shuttleAddress;
		}
		public Date getShuttleDate() {
			return shuttleDate;
		}
		public void setShuttleDate(Date shuttleDate) {
			this.shuttleDate = shuttleDate;
		}
		public String getCashierBillsID() {
			return cashierBillsID;
		}
		public void setCashierBillsID(String cashierBillsID) {
			this.cashierBillsID = cashierBillsID;
		}
		public Date getTimingStartTime() {
			return timingStartTime;
		}
		public void setTimingStartTime(Date timingStartTime) {
			this.timingStartTime = timingStartTime;
		}
		public Date getTimingEndTime() {
			return timingEndTime;
		}
		public void setTimingEndTime(Date timingEndTime) {
			this.timingEndTime = timingEndTime;
		}
		public String getTimingTime() {
			return timingTime;
		}
		public void setTimingTime(String timingTime) {
			this.timingTime = timingTime;
		}
		public String getTimingAllTime() {
			return timingAllTime;
		}
		public void setTimingAllTime(String timingAllTime) {
			this.timingAllTime = timingAllTime;
		}
		public String getTimingNote() {
			return timingNote;
		}
		public void setTimingNote(String timingNote) {
			this.timingNote = timingNote;
		}
		public Date getChargeTime() {
			return chargeTime;
		}
		public void setChargeTime(Date chargeTime) {
			this.chargeTime = chargeTime;
		}
		public String getBillNumber() {
			return billNumber;
		}
		public void setBillNumber(String billNumber) {
			this.billNumber = billNumber;
		}
		
		public String getSettle() {
			return settle;
		}
		public void setSettle(String settle) {
			this.settle = settle;
		}
		
		public String getPayNumber() {
			return payNumber;
		}
		public void setPayNumber(String payNumber) {
			this.payNumber = payNumber;
		}
		public String getArrearsMoneyNumber() {
			return arrearsMoneyNumber;
		}
		public void setArrearsMoneyNumber(String arrearsMoneyNumber) {
			this.arrearsMoneyNumber = arrearsMoneyNumber;
		}
		public String getResponsible() {
			return responsible;
		}
		public void setResponsible(String responsible) {
			this.responsible = responsible;
		}
		public Date getEndShuttleDate() {
			return endShuttleDate;
		}
		public void setEndShuttleDate(Date endShuttleDate) {
			this.endShuttleDate = endShuttleDate;
		}
		public String getShuttleStaff() {
			return shuttleStaff;
		}
		public void setShuttleStaff(String shuttleStaff) {
			this.shuttleStaff = shuttleStaff;
		}
		public String getShuttleStaffPhone() {
			return shuttleStaffPhone;
		}
		public void setShuttleStaffPhone(String shuttleStaffPhone) {
			this.shuttleStaffPhone = shuttleStaffPhone;
		}
		public String getShuttleCarNumber() {
			return shuttleCarNumber;
		}
		public void setShuttleCarNumber(String shuttleCarNumber) {
			this.shuttleCarNumber = shuttleCarNumber;
		}
		public String getShuttleCarXinHao() {
			return shuttleCarXinHao;
		}
		public void setShuttleCarXinHao(String shuttleCarXinHao) {
			this.shuttleCarXinHao = shuttleCarXinHao;
		}
		public String getShuttleSumPeople() {
			return shuttleSumPeople;
		}
		public void setShuttleSumPeople(String shuttleSumPeople) {
			this.shuttleSumPeople = shuttleSumPeople;
		}
		public String getShuttleStaffID() {
			return shuttleStaffID;
		}
		public void setShuttleStaffID(String shuttleStaffID) {
			this.shuttleStaffID = shuttleStaffID;
		}
		public String getCarNumber() {
			return carNumber;
		}
		public void setCarNumber(String carNumber) {
			this.carNumber = carNumber;
		}
		public String getCarIdentifier() {
			return carIdentifier;
		}
		public void setCarIdentifier(String carIdentifier) {
			this.carIdentifier = carIdentifier;
		}
		public String getCarID() {
			return carID;
		}
		public void setCarID(String carID) {
			this.carID = carID;
		}
		public String getCarType() {
			return carType;
		}
		public void setCarType(String carType) {
			this.carType = carType;
		}
		public Date getCarDate() {
			return carDate;
		}
		public void setCarDate(Date carDate) {
			this.carDate = carDate;
		}
		public String getCarCoach() {
			return carCoach;
		}
		public void setCarCoach(String carCoach) {
			this.carCoach = carCoach;
		}
		public Date getAddTime() {
			return addTime;
		}
		public void setAddTime(Date addTime) {
			this.addTime = addTime;
		}
		
		public Date getSignUpDate() {
			return signUpDate;
		}
		public void setSignUpDate(Date signUpDate) {
			this.signUpDate = signUpDate;
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
		
}
