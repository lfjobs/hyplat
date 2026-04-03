package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * DtProMain entity. @author MyEclipse Persistence Tools
 */

public class ProMain implements BaseBean {

	// Fields

	private String yjKey;
	private String yjId;
	private String yjName;
	private String meNumber;
	private String status;
	private String dcStatus;
	private String comId;
	private String codeId;
	
	private String model;
	
	private String goodsName;

	// Constructors

	/** default constructor */
	public ProMain() {
	}
	
	

	public ProMain(String yjKey, String yjId, String meNumber, String status,
			String dcStatus, String comId, String codeId,String goodsName) {
		super();
		this.yjKey = yjKey;
		this.yjId = yjId;
		this.meNumber = meNumber;
		this.status = status;
		this.dcStatus = dcStatus;
		this.comId = comId;
		this.codeId = codeId;
		this.goodsName=goodsName;
	}
	
	public ProMain(String yjKey, String yjId, String meNumber, String status,
			String dcStatus, String comId, String codeId,String goodsName,String model) {
		super();
		this.yjKey = yjKey;
		this.yjId = yjId;
		this.meNumber = meNumber;
		this.status = status;
		this.dcStatus = dcStatus;
		this.comId = comId;
		this.codeId = codeId;
		this.goodsName=goodsName;
		this.model=model;
	}

	/** full constructor */
	public ProMain(String yjId, String yjName, String meNumber,
			String status, String dcStatus, String comId) {
		this.yjId = yjId;
		this.yjName = yjName;
		this.meNumber = meNumber;
		this.status = status;
		this.dcStatus = dcStatus;
		this.comId = comId;
	}

	// Property accessors

	public String getYjKey() {
		return this.yjKey;
	}

	public void setYjKey(String yjKey) {
		this.yjKey = yjKey;
	}

	public String getYjId() {
		return this.yjId;
	}

	public void setYjId(String yjId) {
		this.yjId = yjId;
	}

	public String getYjName() {
		return this.yjName;
	}

	public void setYjName(String yjName) {
		this.yjName = yjName;
	}

	public String getMeNumber() {
		return this.meNumber;
	}

	public void setMeNumber(String meNumber) {
		this.meNumber = meNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDcStatus() {
		return this.dcStatus;
	}

	public void setDcStatus(String dcStatus) {
		this.dcStatus = dcStatus;
	}

	public String getComId() {
		return this.comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}