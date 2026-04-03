package com.tiantai.wfj.bo;

import java.math.BigDecimal;

/**
 * AbstractTEshopStarlevel entity provides the base persistence definition of
 * the TEshopStarlevel entity. @author MyEclipse Persistence Tools
 */

public abstract class TEshopStarlevel implements java.io.Serializable {

	// Fields

	private BigDecimal id;
	private BigDecimal beginmark;
	private BigDecimal endmark;
	private BigDecimal num;
	private String imagepath;

	// Constructors	

	/** default constructor */
	public TEshopStarlevel() {
	}

	/** full constructor */
	public TEshopStarlevel(BigDecimal beginmark, BigDecimal endmark,
			BigDecimal num, String imagepath) {
		this.beginmark = beginmark;
		this.endmark = endmark;
		this.num = num;
		this.imagepath = imagepath;
	}

	// Property accessors

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getBeginmark() {
		return this.beginmark;
	}

	public void setBeginmark(BigDecimal beginmark) {
		this.beginmark = beginmark;
	}

	public BigDecimal getEndmark() {
		return this.endmark;
	}

	public void setEndmark(BigDecimal endmark) {
		this.endmark = endmark;
	}

	public BigDecimal getNum() {
		return this.num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public String getImagepath() {
		return this.imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

}