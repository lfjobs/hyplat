package hy.ea.bo.human.vo;

import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.human.StaffAgreement;
import hy.ea.bo.human.StaffBankAccount;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.bo.human.StaffContact;
import hy.ea.bo.human.StaffDocumentation;
import hy.ea.bo.human.StaffEducation;
import hy.ea.bo.human.StaffEncourage;
import hy.ea.bo.human.StaffFamilyMember;
import hy.ea.bo.human.StaffInvestigation;
import hy.ea.bo.human.StaffPersonalFile;
import hy.ea.bo.human.StaffPhysicalCondition;
import hy.ea.bo.human.StaffPoliticalStatus;
import hy.ea.bo.human.StaffPunishment;
import hy.ea.bo.human.StaffReceiptAddress;
import hy.ea.bo.human.StaffResume;
import hy.ea.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class StaffVO {
	private Staff cstaff;
	private Map<String,StaffContact> phone;
	private Map<String,StaffEducation> education;
	private Map<String,StaffResume> record;
	private Map<String,StaffFamilyMember> family;
	private Map<String,StaffPhysicalCondition> staffBody;
	private Map<String,StaffAddress> address;
	private Map<String,StaffPoliticalStatus> zzmm;
	private Map<String,StaffEncourage> rewards;
	private Map<String,StaffPunishment> punishment;
	private Map<String,StaffInvestigation> survey;
	private Map<String,StaffCertificate> certificate;
	private Map<String,StaffDocumentation> humanData;
	private Map<String,StaffPersonalFile> personalFile;
	private Map<String,StaffBankAccount> bankSn;
	private Map<String,StaffAgreement> contract;
	private Map<String, StaffReceiptAddress> receiptAddress;
	/**
	 * 为了人员添加页面预设code下拉框
	 */
	private Map<String,String> codeMenu;
	
	public Map<String,String> getCodeMenu() {
		if(codeMenu==null||codeMenu.values().size()==0){
			codeMenu=new HashMap<String, String>();
			codeMenu.put("zjlx", Constant.CODE_ZZLX);
			codeMenu.put("cflx", Constant.CODE_CFLB);
			codeMenu.put("jllb", Constant.CODE_JLLB);
			codeMenu.put("zzmm", Constant.CODE_ZZMM);
			codeMenu.put("tjnr", Constant.CODE_TJNR);
			codeMenu.put("brgx", Constant.CODE_BRGX);
			codeMenu.put("gwlx", Constant.CODE_GWLX);
			codeMenu.put("gwqk", Constant.CODE_GWQK);
			codeMenu.put("zylx", Constant.CODE_ZYLX);
			codeMenu.put("xllx", Constant.CODE_XLLX);
			codeMenu.put("xxxs", Constant.CODE_XXXS);
			codeMenu.put("jylb", Constant.CODE_JYLB);
			codeMenu.put("lxlx", Constant.CODE_LXLX);
			codeMenu.put("dzlx", Constant.CODE_DZLX);
			codeMenu.put("mzlx", Constant.CODE_MZLX);
			codeMenu.put("wlgx", Constant.CODE_WLGX);
		}
		return codeMenu;
	}
	public Staff getCstaff() {
		return cstaff;
	}
	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}
	public Map<String,StaffContact> getPhone() {
		return phone;
	}
	public void setPhone(Map<String,StaffContact> phone) {
		this.phone = phone;
	}
	public Map<String,StaffEducation> getEducation() {
		return education;
	}
	public void setEducation(Map<String,StaffEducation> education) {
		this.education = education;
	}
	public Map<String,StaffResume> getRecord() {
		return record;
	}
	public void setRecord(Map<String,StaffResume> record) {
		this.record = record;
	}
	public Map<String,StaffFamilyMember> getFamily() {
		return family;
	}
	public void setFamily(Map<String,StaffFamilyMember> family) {
		this.family = family;
	}
	public Map<String,StaffPhysicalCondition> getStaffBody() {
		return staffBody;
	}
	public void setStaffBody(Map<String,StaffPhysicalCondition> staffBody) {
		this.staffBody = staffBody;
	}
	public Map<String,StaffAddress> getAddress() {
		return address;
	}
	public void setAddress(Map<String,StaffAddress> address) {
		this.address = address;
	}
	public Map<String,StaffPoliticalStatus> getZzmm() {
		return zzmm;
	}
	public void setZzmm(Map<String,StaffPoliticalStatus> zzmm) {
		this.zzmm = zzmm;
	}
	public Map<String,StaffEncourage> getRewards() {
		return rewards;
	}
	public void setRewards(Map<String,StaffEncourage> rewards) {
		this.rewards = rewards;
	}
	public Map<String,StaffPunishment> getPunishment() {
		return punishment;
	}
	public void setPunishment(Map<String,StaffPunishment> punishment) {
		this.punishment = punishment;
	}
	public Map<String,StaffInvestigation> getSurvey() {
		return survey;
	}
	public void setSurvey(Map<String,StaffInvestigation> survey) {
		this.survey = survey;
	}
	public Map<String,StaffCertificate> getCertificate() {
		return certificate;
	}
	public void setCertificate(Map<String,StaffCertificate> certificate) {
		this.certificate = certificate;
	}

	public Map<String,StaffDocumentation> getHumanData() {
		return humanData;
	}
	public void setHumanData(Map<String,StaffDocumentation> humanData) {
		this.humanData = humanData;
	}
	public void setCodeMenu(Map<String, String> codeMenu) {
		this.codeMenu = codeMenu;
	}
	public Map<String,StaffPersonalFile> getPersonalFile() {
		return personalFile;
	}
	public void setPersonalFile(Map<String,StaffPersonalFile> personalFile) {
		this.personalFile = personalFile;
	}
	public Map<String,StaffBankAccount> getBankSn() {
		return bankSn;
	}
	public void setBankSn(Map<String,StaffBankAccount> bankSn) {
		this.bankSn = bankSn;
	}
	public Map<String,StaffAgreement> getContract() {
		return contract;
	}
	public void setContract(Map<String,StaffAgreement> contract) {
		this.contract = contract;
	}
	public Map<String, StaffReceiptAddress> getReceiptAddress() {
		return receiptAddress;
	}
	public void setReceiptAddress(Map<String, StaffReceiptAddress> receiptAddress) {
		this.receiptAddress = receiptAddress;
	}
	
	
}
