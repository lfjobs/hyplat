package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;


public class WfjGuize implements BaseBean {

	private String  wfjGuizeKey;
	private String  wfjGuizeId;
	private String  companyId;
	private String  wfjGuizeName;
	private String  wfjGuizeCalc;//A加(默认)或者M 减
	private Integer  wfjGuizeUnit;//0：固定值(默认) 1：百分比
	private Integer  wfjGuizeScore;
	private String wfjGuizePPID;//代理类型id
	private String wfjGuizeIco;//图标
	/**
	 * 其他：正常 9：删除 8，停用 1.展示微金币页面 2.佣金 3.金币提现,充值
	 */
	private Integer  wfjGuizeState;
	private Integer   wfjGuizeOrder;//排序
	private String wfjGuizeExplain;//说明
	public WfjGuize() {
		super();
	}
	public WfjGuize(String wfjGuizeKey, String wfjGuizeId, String companyId, String wfjGuizeName,
			String wfjGuizeCalc, Integer wfjGuizeUnit, Integer wfjGuizeScore, Integer wfjGuizeState,
			Integer wfjGuizeOrder, String wfjGuizeExplain) {
		super();
		this.wfjGuizeKey = wfjGuizeKey;
		this.wfjGuizeId = wfjGuizeId;
		this.companyId = companyId;
		this.wfjGuizeName = wfjGuizeName;
		this.wfjGuizeCalc = wfjGuizeCalc;
		this.wfjGuizeUnit = wfjGuizeUnit;
		this.wfjGuizeScore = wfjGuizeScore;
		this.wfjGuizeState = wfjGuizeState;
		this.wfjGuizeOrder = wfjGuizeOrder;
		this.wfjGuizeExplain = wfjGuizeExplain;
	}
	
	public String getWfjGuizeKey() {
		return wfjGuizeKey;
	}
	public void setWfjGuizeKey(String wfjGuizeKey) {
		this.wfjGuizeKey = wfjGuizeKey;
	}
	public String getWfjGuizeId() {
		return wfjGuizeId;
	}
	public void setWfjGuizeId(String wfjGuizeId) {
		this.wfjGuizeId = wfjGuizeId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getWfjGuizeName() {
		return wfjGuizeName;
	}
	public void setWfjGuizeName(String wfjGuizeName) {
		this.wfjGuizeName = wfjGuizeName;
	}
	public String getWfjGuizeCalc() {
		return wfjGuizeCalc;
	}
	public void setWfjGuizeCalc(String wfjGuizeCalc) {
		this.wfjGuizeCalc = wfjGuizeCalc;
	}
	public Integer getWfjGuizeUnit() {
		return wfjGuizeUnit;
	}
	public void setWfjGuizeUnit(Integer wfjGuizeUnit) {
		this.wfjGuizeUnit = wfjGuizeUnit;
	}
	public Integer getWfjGuizeScore() {
		return wfjGuizeScore;
	}
	public void setWfjGuizeScore(Integer wfjGuizeScore) {
		this.wfjGuizeScore = wfjGuizeScore;
	}
	public Integer getWfjGuizeState() {
		return wfjGuizeState;
	}
	public void setWfjGuizeState(Integer wfjGuizeState) {
		this.wfjGuizeState = wfjGuizeState;
	}
	public Integer getWfjGuizeOrder() {
		return wfjGuizeOrder;
	}
	public void setWfjGuizeOrder(Integer wfjGuizeOrder) {
		this.wfjGuizeOrder = wfjGuizeOrder;
	}
	public String getWfjGuizeExplain() {
		return wfjGuizeExplain;
	}
	public void setWfjGuizeExplain(String wfjGuizeExplain) {
		this.wfjGuizeExplain = wfjGuizeExplain;
	}

    public String getWfjGuizePPID()
    {
        return wfjGuizePPID;
    }

    public void setWfjGuizePPID(String wfjGuizePPID)
    {
        this.wfjGuizePPID = wfjGuizePPID;
    }

	public String getWfjGuizeIco() {
		return wfjGuizeIco;
	}

	public void setWfjGuizeIco(String wfjGuizeIco) {
		this.wfjGuizeIco = wfjGuizeIco;
	}
}
