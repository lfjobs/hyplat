package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * TPrdcodeRel entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TPrdcodeRel implements java.io.Serializable,BaseBean {

	// Fields

	private TPrdcodeRelId id;

	// Constructors

	/** default constructor */
	public TPrdcodeRel() {
	}

	/** full constructor */
	public TPrdcodeRel(TPrdcodeRelId id) {
		this.id = id;
	}

	// Property accessors

	public TPrdcodeRelId getId() {
		return this.id;
	}

	public void setId(TPrdcodeRelId id) {
		this.id = id;
	}

}