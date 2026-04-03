package com.tiantai.wfj.bo;

import java.util.Date;

import hy.plat.bo.BaseBean;
/**
 * 安卓营销接口
 * @author ttst_WK
 *
 */

public class MarKeting implements BaseBean{
  
	private String mkkey;
	private String mkID;//营销ID
	private String mkuserID;//邀请人（这里只推销员手机号）
	private String mkSccId;//邀请人（sccId）
	private String userID;//被邀请人(这里只用户 )
	private String userSccId;//被邀请人（sccId）
	private Date mkdate;//邀请时间
	
	private String source;//来源
	
	public Date getMkdate() {
		return mkdate;
	}
	public void setMkdate(Date mkdate) {
		this.mkdate = mkdate;
	}
	public String getMkkey() {
		return mkkey;
	}
	public void setMkkey(String mkkey) {
		this.mkkey = mkkey;
	}
	public String getMkID() {
		return mkID;
	}
	public void setMkID(String mkID) {
		this.mkID = mkID;
	}
	public String getMkuserID() {
		return mkuserID;
	}
	public void setMkuserID(String mkuserID) {
		this.mkuserID = mkuserID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getMkSccId() {
		return mkSccId;
	}
	public void setMkSccId(String mkSccId) {
		this.mkSccId = mkSccId;
	}
	public String getUserSccId() {
		return userSccId;
	}
	public void setUserSccId(String userSccId) {
		this.userSccId = userSccId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	

}
