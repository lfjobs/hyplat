package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * DtHrStaffReward entity. @author MyEclipse Persistence Tools
 */

public class StaffReward implements BaseBean ,java.io.Serializable{


	private String rewardkey;
	private String rewardid;
	private String companyid;	//责任人公司
	private String companyname;
	private String staffid;		//责任人
	private String staffname;
	private String organizationid;//部门	
	private String orgname;
	private String deptid;		//职务
	private String deptname;
	private String codeid;		//荣誉名称
	private String codevalue;	
	private String money;
	private String rewstatus;	//奖励类别
	private String rewtimes; 	//奖励时间
	private String oneormore;	//奖励项目
	private String status; 		//审核状态
	private String onename;		//录入人
	private String onestaffid;
	private String onetimes;			//录入时间
	private String twoname;		//审核人
	private String twostaffid;
	private String twotimes;			//审核时间
	private String threename;  //审批人
	private String threestaffid;
	private String threetimes;			//审批时间
	private String manager;//公司经理
	private String president;//总部经理
	private String deptcharge;//部门主管
	private String headcharge;//总部主管
	private String remarks;

	public String getRewardkey() {
		return this.rewardkey;
	}

	public void setRewardkey(String rewardkey) {
		this.rewardkey = rewardkey;
	}

	public String getRewardid() {
		return this.rewardid;
	}

	public void setRewardid(String rewardid) {
		this.rewardid = rewardid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getDeptid() {
		return this.deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getCodeid() {
		return this.codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public String getCodevalue() {
		return this.codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	
	

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getOnetimes() {
		return onetimes;
	}

	public void setOnetimes(String onetimes) {
		this.onetimes = onetimes;
	}

	public String getTwotimes() {
		return twotimes;
	}

	public void setTwotimes(String twotimes) {
		this.twotimes = twotimes;
	}

	public String getThreetimes() {
		return threetimes;
	}

	public void setThreetimes(String threetimes) {
		this.threetimes = threetimes;
	}

	public String getOnename() {
		return this.onename;
	}

	public void setOnename(String onename) {
		this.onename = onename;
	}

	public String getOnestaffid() {
		return this.onestaffid;
	}

	public void setOnestaffid(String onestaffid) {
		this.onestaffid = onestaffid;
	}

	public String getTwoname() {
		return this.twoname;
	}

	public void setTwoname(String twoname) {
		this.twoname = twoname;
	}

	public String getTwostaffid() {
		return this.twostaffid;
	}

	public void setTwostaffid(String twostaffid) {
		this.twostaffid = twostaffid;
	}

	public String getThreename() {
		return this.threename;
	}

	public void setThreename(String threename) {
		this.threename = threename;
	}

	public String getThreestaffid() {
		return this.threestaffid;
	}

	public void setThreestaffid(String threestaffid) {
		this.threestaffid = threestaffid;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPresident() {
		return this.president;
	}

	public void setPresident(String president) {
		this.president = president;
	}

	public String getDeptcharge() {
		return this.deptcharge;
	}

	public void setDeptcharge(String deptcharge) {
		this.deptcharge = deptcharge;
	}

	public String getHeadcharge() {
		return this.headcharge;
	}

	public void setHeadcharge(String headcharge) {
		this.headcharge = headcharge;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRewstatus() {
		return rewstatus;
	}

	public void setRewstatus(String rewstatus) {
		this.rewstatus = rewstatus;
	}

	public String getRewtimes() {
		return rewtimes;
	}

	public void setRewtimes(String rewtimes) {
		this.rewtimes = rewtimes;
	}

	public String getOneormore() {
		return oneormore;
	}

	public void setOneormore(String oneormore) {
		this.oneormore = oneormore;
	}

}