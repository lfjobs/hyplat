package hy.ea.bo.ddsr;

import hy.plat.bo.BaseBean;

/**
 * ReDssrstudentDssrsubject entity. @author MyEclipse Persistence Tools
 */

public class ReDssrstudentDssrsubject implements BaseBean,java.io.Serializable {

	// Fields

	private String stsuKey;
	private Dssrsubject dssrsubject;
	private Dssrstudent dssrstudent;

	// Constructors

	/** default constructor */
	public ReDssrstudentDssrsubject() {
	}

	/** full constructor */
	public ReDssrstudentDssrsubject(Dssrsubject dssrsubject,
			Dssrstudent dssrstudent) {
		this.dssrsubject = dssrsubject;
		this.dssrstudent = dssrstudent;
	}

	// Property accessors

	public String getStsuKey() {
		return this.stsuKey;
	}

	public void setStsuKey(String stsuKey) {
		this.stsuKey = stsuKey;
	}

	public Dssrsubject getDssrsubject() {
		return this.dssrsubject;
	}

	public void setDssrsubject(Dssrsubject dssrsubject) {
		this.dssrsubject = dssrsubject;
	}

	public Dssrstudent getDssrstudent() {
		return this.dssrstudent;
	}

	public void setDssrstudent(Dssrstudent dssrstudent) {
		this.dssrstudent = dssrstudent;
	}

}