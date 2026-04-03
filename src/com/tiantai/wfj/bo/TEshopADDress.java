package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

/**
 * 新增地址表 用于微信
 * 
 * @author ttst_wk
 *2015年6月29日17:04:48
 */
public class TEshopADDress implements BaseBean {
	private String askey;
	private String asID;//地址ID
	private String userID;//用户ID
	private String assheng;//大省
	private String asshi;//中市
	private String asquxian;//小区县
	private String asxiangx;//详细地址
	private String asdianhua;//收货人电话
	private String asname;//收货人姓名
	public String getAskey() {
		return askey;
	}
	public void setAskey(String askey) {
		this.askey = askey;
	}
	public String getAsID() {
		return asID;
	}
	public void setAsID(String asID) {
		this.asID = asID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAssheng() {
		return assheng;
	}
	public void setAssheng(String assheng) {
		this.assheng = assheng;
	}
	public String getAsshi() {
		return asshi;
	}
	public void setAsshi(String asshi) {
		this.asshi = asshi;
	}
	public String getAsquxian() {
		return asquxian;
	}
	public void setAsquxian(String asquxian) {
		this.asquxian = asquxian;
	}
	public String getAsxiangx() {
		return asxiangx;
	}
	public void setAsxiangx(String asxiangx) {
		this.asxiangx = asxiangx;
	}
	public String getAsdianhua() {
		return asdianhua;
	}
	public void setAsdianhua(String asdianhua) {
		this.asdianhua = asdianhua;
	}
	public String getAsname() {
		return asname;
	}
	public void setAsname(String asname) {
		this.asname = asname;
	}
	
	
}
