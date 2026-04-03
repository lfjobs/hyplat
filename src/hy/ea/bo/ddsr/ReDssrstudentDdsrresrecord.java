package hy.ea.bo.ddsr;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * ReDssrstudentDdsrresrecord entity. @author MyEclipse Persistence Tools
 */

public class ReDssrstudentDdsrresrecord implements BaseBean,java.io.Serializable {

	// Fields

	private String stureKey;
	private Dssrstudent dssrstudent;
	private Ddsrreservationrecord ddsrreservationrecord;
	private Byte stureStatus;
	private Date stureCreatedate;
	private Date stureUpdatedate;

	// Constructors

	/** default constructor */
	public ReDssrstudentDdsrresrecord() {
	}

	/** full constructor */
	public ReDssrstudentDdsrresrecord(Dssrstudent dssrstudent,
			Ddsrreservationrecord ddsrreservationrecord) {
		this.dssrstudent = dssrstudent;
		this.ddsrreservationrecord = ddsrreservationrecord;
	}

	// Property accessors

	public String getStureKey() {
		return this.stureKey;
	}

	public void setStureKey(String stureKey) {
		this.stureKey = stureKey;
	}

	public Dssrstudent getDssrstudent() {
		return this.dssrstudent;
	}

	public void setDssrstudent(Dssrstudent dssrstudent) {
		this.dssrstudent = dssrstudent;
	}

	public Ddsrreservationrecord getDdsrreservationrecord() {
		return this.ddsrreservationrecord;
	}

	public void setDdsrreservationrecord(
			Ddsrreservationrecord ddsrreservationrecord) {
		this.ddsrreservationrecord = ddsrreservationrecord;
	}

	public Date getStureCreatedate() {
		return stureCreatedate;
	}

	public void setStureCreatedate(Date stureCreatedate) {
		this.stureCreatedate = stureCreatedate;
	}

	public Date getStureUpdatedate() {
		return stureUpdatedate;
	}

	public void setStureUpdatedate(Date stureUpdatedate) {
		this.stureUpdatedate = stureUpdatedate;
	}

	public Byte getStureStatus() {
		return stureStatus;
	}

	public void setStureStatus(Byte stureStatus) {
		this.stureStatus = stureStatus;
	}
	
}