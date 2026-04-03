package hy.ea.bo.ddsr;

import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Dssrstudent entity. @author MyEclipse Persistence Tools
 */

public class Dssrstudent implements BaseBean,java.io.Serializable {

	// Fields

	private String studKey;
	private Staff dtHrStaff;
	private String studCompanyid;
	private String studIcnumber;
	private Byte studInforclass;
	private Byte studStatus;
	private Byte studStar;
	private Short studSumhour;
	private String studNper;
	private Short studRemhour;
	private Short studStonumber;
	private String studCredentials;
	private Date studCreatedate;
	private Date studUpdatedate;
	private String studPassword;
	private String studPasswordOther;
	private Set reDssrstudentDssrsubjects = new HashSet(0);
	private Set reDssrstudentDdsrresrecords = new HashSet(0);

	// Constructors

	/** default constructor */
	public Dssrstudent() {
	}

	/** minimal constructor */
	public Dssrstudent(Staff dtHrStaff) {
		this.dtHrStaff = dtHrStaff;
	}

	/** full constructor */
	public Dssrstudent(Staff dtHrStaff, String studCompanyid,
			String studIcnumber, Byte studInforclass, Byte studStatus,
			Byte studStar, Short studSumhour, String studNper,
			Short studRemhour, Short studStonumber, Date studCreatedate,
			Date studUpdatedate, Set reDssrstudentDssrsubjects,
			Set reDssrstudentDdsrresrecords) {
		this.dtHrStaff = dtHrStaff;
		this.studCompanyid = studCompanyid;
		this.studIcnumber = studIcnumber;
		this.studInforclass = studInforclass;
		this.studStatus = studStatus;
		this.studStar = studStar;
		this.studSumhour = studSumhour;
		this.studNper = studNper;
		this.studRemhour = studRemhour;
		this.studStonumber = studStonumber;
		this.studCreatedate = studCreatedate;
		this.studUpdatedate = studUpdatedate;
		this.reDssrstudentDssrsubjects = reDssrstudentDssrsubjects;
		this.reDssrstudentDdsrresrecords = reDssrstudentDdsrresrecords;
	}

	// Property accessors

	public String getStudKey() {
		return this.studKey;
	}

	public void setStudKey(String studKey) {
		this.studKey = studKey;
	}

	public Staff getDtHrStaff() {
		return this.dtHrStaff;
	}

	public void setDtHrStaff(Staff dtHrStaff) {
		this.dtHrStaff = dtHrStaff;
	}

	public String getStudCompanyid() {
		return this.studCompanyid;
	}

	public void setStudCompanyid(String studCompanyid) {
		this.studCompanyid = studCompanyid;
	}

	public String getStudIcnumber() {
		return this.studIcnumber;
	}

	public void setStudIcnumber(String studIcnumber) {
		this.studIcnumber = studIcnumber;
	}

	public Byte getStudInforclass() {
		return this.studInforclass;
	}

	public void setStudInforclass(Byte studInforclass) {
		this.studInforclass = studInforclass;
	}

	public Byte getStudStatus() {
		return this.studStatus;
	}

	public void setStudStatus(Byte studStatus) {
		this.studStatus = studStatus;
	}

	public Byte getStudStar() {
		return this.studStar;
	}

	public void setStudStar(Byte studStar) {
		this.studStar = studStar;
	}

	public Short getStudSumhour() {
		return this.studSumhour;
	}

	public void setStudSumhour(Short studSumhour) {
		this.studSumhour = studSumhour;
	}

	public String getStudNper() {
		return this.studNper;
	}

	public void setStudNper(String studNper) {
		this.studNper = studNper;
	}

	public Short getStudRemhour() {
		return this.studRemhour;
	}

	public void setStudRemhour(Short studRemhour) {
		this.studRemhour = studRemhour;
	}

	public Short getStudStonumber() {
		return this.studStonumber;
	}

	public void setStudStonumber(Short studStonumber) {
		this.studStonumber = studStonumber;
	}

	public Date getStudCreatedate() {
		return this.studCreatedate;
	}

	public void setStudCreatedate(Date studCreatedate) {
		this.studCreatedate = studCreatedate;
	}

	public Date getStudUpdatedate() {
		return this.studUpdatedate;
	}

	public void setStudUpdatedate(Date studUpdatedate) {
		this.studUpdatedate = studUpdatedate;
	}

	public Set getReDssrstudentDssrsubjects() {
		return this.reDssrstudentDssrsubjects;
	}

	public void setReDssrstudentDssrsubjects(Set reDssrstudentDssrsubjects) {
		this.reDssrstudentDssrsubjects = reDssrstudentDssrsubjects;
	}

	public Set getReDssrstudentDdsrresrecords() {
		return this.reDssrstudentDdsrresrecords;
	}

	public void setReDssrstudentDdsrresrecords(Set reDssrstudentDdsrresrecords) {
		this.reDssrstudentDdsrresrecords = reDssrstudentDdsrresrecords;
	}

	public String getStudCredentials() {
		return studCredentials;
	}

	public void setStudCredentials(String studCredentials) {
		this.studCredentials = studCredentials;
	}

	public String getStudPassword() {
		return studPassword;
	}

	public void setStudPassword(String studPassword) {
		this.studPassword = studPassword;
	}

	public String getStudPasswordOther() {
		return studPasswordOther;
	}

	public void setStudPasswordOther(String studPasswordOther) {
		this.studPasswordOther = studPasswordOther;
	}
	
}