package hy.ea.bo.ddsr;

import hy.plat.bo.BaseBean;

/**
 * ReDdsrcoachDssrsubject entity. @author MyEclipse Persistence Tools
 */

public class ReDdsrcoachDssrsubject implements BaseBean,java.io.Serializable {

	// Fields

	private String cosuKey;
	private Ddsrcoach ddsrcoach;
	private Dssrsubject dssrsubject;

	// Constructors

	/** default constructor */
	public ReDdsrcoachDssrsubject() {
	}

	/** full constructor */
	public ReDdsrcoachDssrsubject(Ddsrcoach ddsrcoach, Dssrsubject dssrsubject) {
		this.ddsrcoach = ddsrcoach;
		this.dssrsubject = dssrsubject;
	}

	// Property accessors

	public String getCosuKey() {
		return this.cosuKey;
	}

	public void setCosuKey(String cosuKey) {
		this.cosuKey = cosuKey;
	}

	public Ddsrcoach getDdsrcoach() {
		return this.ddsrcoach;
	}

	public void setDdsrcoach(Ddsrcoach ddsrcoach) {
		this.ddsrcoach = ddsrcoach;
	}

	public Dssrsubject getDssrsubject() {
		return this.dssrsubject;
	}

	public void setDssrsubject(Dssrsubject dssrsubject) {
		this.dssrsubject = dssrsubject;
	}

}