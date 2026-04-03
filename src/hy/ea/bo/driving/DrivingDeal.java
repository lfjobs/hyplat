package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtDrivingTest entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DrivingDeal  implements BaseBean{

	// Fields

	private String drivingDealkey;
	private String drivingDealid;
	private String companyid;
	private String staffID;
	private String states;//00 预定建档 01 成交建档
	
	
	public DrivingDeal() {
		super();
	}
	public DrivingDeal(String drivingDealkey, String drivingDealid,
			String companyid, String staffID, String states) {
		super();
		this.drivingDealkey = drivingDealkey;
		this.drivingDealid = drivingDealid;
		this.companyid = companyid;
		this.staffID = staffID;
		this.states = states;
	}
	public String getDrivingDealkey() {
		return drivingDealkey;
	}
	public void setDrivingDealkey(String drivingDealkey) {
		this.drivingDealkey = drivingDealkey;
	}
	public String getDrivingDealid() {
		return drivingDealid;
	}
	public void setDrivingDealid(String drivingDealid) {
		this.drivingDealid = drivingDealid;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	
}