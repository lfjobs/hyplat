package hy.ea.bo.office;

import hy.plat.bo.BaseBean;


/*
 * 收费标准
 * */
public class FeeScale implements BaseBean{
	
	private String feecID;//主键
	private String feeckey;
	private String chargeNumber;//收费编号
	private String siteId;//场地id
	private String goodsID;//收费物品id
	private String timeUnits;//时间单位  0小时 1：天 2：月 3:年
	private String timeType;//包天如果是包天 是几小时制度  24小时 8小时等，或者当天
	private String feeMini;//免费时长单位分钟
	private String tripTime;//超过多长时间跳价位
	private String staffID;//责任人ID
	private String staffName;//责任人
	private String CompanyID;//公司ID
	private String startUsing;//是否启用00:启用,01:未启用,02:已删除 03 拒绝 04 已提交审核
	private String carType;//车类型 p：私家车  c:教练车
	private String isTotalPct;//0未选中，默认使用 1选中，不使用 是否使用消费红包  暂时不用，默认都是加消费红包的
	
	
	public String getFeecID() {
		return feecID;
	}
	public void setFeecID(String feecID) {
		this.feecID = feecID;
	}
	public String getFeeckey() {
		return feeckey;
	}
	public void setFeeckey(String feeckey) {
		this.feeckey = feeckey;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getChargeNumber() {
		return chargeNumber;
	}
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getStartUsing() {
		return startUsing;
	}
	public void setStartUsing(String startUsing) {
		this.startUsing = startUsing;
	}

	public String getTimeUnits() {
		return timeUnits;
	}

	public void setTimeUnits(String timeUnits) {
		this.timeUnits = timeUnits;
	}

	public String getFeeMini() {
		return feeMini;
	}

	public void setFeeMini(String feeMini) {
		this.feeMini = feeMini;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getTripTime() {
		return tripTime;
	}

	public void setTripTime(String tripTime) {
		this.tripTime = tripTime;
	}

	public String getIsTotalPct() {
		return isTotalPct;
	}

	public void setIsTotalPct(String isTotalPct) {
		this.isTotalPct = isTotalPct;
	}
}
