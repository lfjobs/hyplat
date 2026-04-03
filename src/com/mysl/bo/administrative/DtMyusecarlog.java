package com.mysl.bo.administrative;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * DtMyattach entity. @author MyEclipse Persistence Tools
 */

public class DtMyusecarlog implements java.io.Serializable,BaseBean, ExcelBean {
	/*
	 * 行车日志
	 */
	private String  key;//主键
	private String  lid; //业务主键
	private String  companyname;//公司名称
	private String  companyid;//公司ID
	private String  carapplyid;      //出车单外键
	
	private String  userremarks;  //用车人备注
	private String  usercarname;  //用车人
	private String  carcode;      //车牌号
	private String  carusereason; //项目名称
	private String  destination;  //目的地
	private String  cardriver;    //驾驶员
	private String  cardriverid;    //驾驶员id
	private String  cardriverorgid;    //驾驶员部门id
	private String  cardriverorgname;    //驾驶员部门
	private String  cardrivercompanyid;    //驾驶员公司id
	private String  cardrivercompanyname;    //驾驶员公司
	
	private String  starnum;  //起点里程数
	private String  endnum;  //终点里程数
	private String  countnum;  //本次出车里程数
	private String  roadtoll;  //过路费
	private String  parkingfees;  //停车费
	private String  driverremarks;      //驾驶员备注
	
	
	public String[] properties() {
		String[] properties = { companyname,carcode,carusereason, usercarname, destination, 
				userremarks,cardriver,starnum,endnum,countnum,roadtoll,parkingfees,driverremarks};
		return properties;

	}
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "公司", "车牌号", "项目名称", "用车人","目的地",
				"用车人备注", "驾驶员","起点里程数（km）","终点里程数（km）", "本次出车里程数（km）","过路费（元）","停车费（元）","驾驶员备注"};
		return titles;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getCarapplyid() {
		return carapplyid;
	}

	public void setCarapplyid(String carapplyid) {
		this.carapplyid = carapplyid;
	}

	public String getUserremarks() {
		return userremarks;
	}

	public void setUserremarks(String userremarks) {
		this.userremarks = userremarks;
	}

	public String getUsercarname() {
		return usercarname;
	}

	public void setUsercarname(String usercarname) {
		this.usercarname = usercarname;
	}

	public String getCarcode() {
		return carcode;
	}

	public void setCarcode(String carcode) {
		this.carcode = carcode;
	}

	public String getCarusereason() {
		return carusereason;
	}

	public void setCarusereason(String carusereason) {
		this.carusereason = carusereason;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStarnum() {
		return starnum;
	}

	public void setStarnum(String starnum) {
		this.starnum = starnum;
	}

	public String getEndnum() {
		return endnum;
	}

	public void setEndnum(String endnum) {
		this.endnum = endnum;
	}

	public String getCountnum() {
		return countnum;
	}

	public void setCountnum(String countnum) {
		this.countnum = countnum;
	}

	public String getRoadtoll() {
		return roadtoll;
	}

	public void setRoadtoll(String roadtoll) {
		this.roadtoll = roadtoll;
	}

	public String getParkingfees() {
		return parkingfees;
	}

	public void setParkingfees(String parkingfees) {
		this.parkingfees = parkingfees;
	}

	public String getCardriver() {
		return cardriver;
	}

	public void setCardriver(String cardriver) {
		this.cardriver = cardriver;
	}

	public String getCardriverid() {
		return cardriverid;
	}

	public void setCardriverid(String cardriverid) {
		this.cardriverid = cardriverid;
	}

	public String getCardriverorgid() {
		return cardriverorgid;
	}

	public void setCardriverorgid(String cardriverorgid) {
		this.cardriverorgid = cardriverorgid;
	}

	public String getCardriverorgname() {
		return cardriverorgname;
	}

	public void setCardriverorgname(String cardriverorgname) {
		this.cardriverorgname = cardriverorgname;
	}

	public String getCardrivercompanyid() {
		return cardrivercompanyid;
	}

	public void setCardrivercompanyid(String cardrivercompanyid) {
		this.cardrivercompanyid = cardrivercompanyid;
	}

	public String getCardrivercompanyname() {
		return cardrivercompanyname;
	}

	public void setCardrivercompanyname(String cardrivercompanyname) {
		this.cardrivercompanyname = cardrivercompanyname;
	}

	public String getDriverremarks() {
		return driverremarks;
	}

	public void setDriverremarks(String driverremarks) {
		this.driverremarks = driverremarks;
	}
	
	
    
}