package hy.ea.bo.driving;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtDrivingOperationPeople entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtDrivingOperationPeople implements BaseBean{

	// Fields

	private String drivingoperationpeoplekey;
	private String drivingoperationpropleid;
	private String drivingprincipalid;
	private Date operationdate;
	private String operationname;
	private String operatinid;
	private String operationstatus;
	private String operationstatus1;
	private String istrues;
	private String notes;
	

	// Constructors

	/** default constructor */
	public DtDrivingOperationPeople() {
	}

	/** minimal constructor */
	public DtDrivingOperationPeople(String drivingoperationpropleid) {
		this.drivingoperationpropleid = drivingoperationpropleid;
	}

	/** full constructor */
	public DtDrivingOperationPeople(String drivingoperationpropleid,
			String drivingprincipalid, Date operationdate,
			String operationname, String operatinid, String operationstatus,
			String operationstatus1, String istrues, String notes) {
		this.drivingoperationpropleid = drivingoperationpropleid;
		this.drivingprincipalid = drivingprincipalid;
		this.operationdate = operationdate;
		this.operationname = operationname;
		this.operatinid = operatinid;
		this.operationstatus = operationstatus;
		this.operationstatus1 = operationstatus1;
		this.istrues = istrues;
		this.notes = notes;
	}
	/** full constructor */
	public DtDrivingOperationPeople(String drivingprincipalid,
			String operationname, String operatinid,
			String notes) {
		this.drivingprincipalid = drivingprincipalid;
		this.operationname = operationname;
		this.operatinid = operatinid;
		this.notes = notes;
	}

	// Property accessors

	public String getDrivingoperationpeoplekey() {
		return this.drivingoperationpeoplekey;
	}

	public void setDrivingoperationpeoplekey(String drivingoperationpeoplekey) {
		this.drivingoperationpeoplekey = drivingoperationpeoplekey;
	}

	public String getDrivingoperationpropleid() {
		return this.drivingoperationpropleid;
	}

	public void setDrivingoperationpropleid(String drivingoperationpropleid) {
		this.drivingoperationpropleid = drivingoperationpropleid;
	}

	public String getDrivingprincipalid() {
		return this.drivingprincipalid;
	}

	public void setDrivingprincipalid(String drivingprincipalid) {
		this.drivingprincipalid = drivingprincipalid;
	}

	public Date getOperationdate() {
		return this.operationdate;
	}

	public void setOperationdate(Date operationdate) {
		this.operationdate = operationdate;
	}

	public String getOperationname() {
		return this.operationname;
	}

	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}

	public String getOperatinid() {
		return this.operatinid;
	}

	public void setOperatinid(String operatinid) {
		this.operatinid = operatinid;
	}

	public String getOperationstatus() {
		return this.operationstatus;
	}

	public void setOperationstatus(String operationstatus) {
		this.operationstatus = operationstatus;
	}

	public String getOperationstatus1() {
		return this.operationstatus1;
	}

	public void setOperationstatus1(String operationstatus1) {
		this.operationstatus1 = operationstatus1;
	}

	public String getIstrues() {
		return this.istrues;
	}

	public void setIstrues(String istrues) {
		this.istrues = istrues;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}