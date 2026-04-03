package com.tiantai.wfj.bo;

import java.util.Date;
import java.util.List;

import hy.plat.bo.BaseBean;

/**
 * 
 * 通訊錄
 * @author ttst_wk
 *
 */
public class AddressBook implements BaseBean {

	private String  abkey;
	private String  abid;//通訊錶ID
	private String  abphone;//通訊錶電話 表示別人的電話
	private String  abphonename;//通訊錄的名字 表示名字
	private String  abWhetherwfj;//是否註冊微分金  0表示可以  1 就是没有
	private String  abuserphone;//這條信息擁有的人
	private String  abGrouping;//判断这条数据 是否是分组   0 分组  1 电话
	private String  abABid;//分组给谁了
	private Date   abdata;//這條信息生成的時間这
	public String getAbkey() {
		return abkey;
	}
	public void setAbkey(String abkey) {
		this.abkey = abkey;
	}
	public String getAbid() {
		return abid;
	}
	public void setAbid(String abid) {
		this.abid = abid;
	}
	public String getAbGrouping() {
		return abGrouping;
	}
	public void setAbGrouping(String abGrouping) {
		this.abGrouping = abGrouping;
	}
	public String getAbABid() {
		return abABid;
	}
	public void setAbABid(String abABid) {
		this.abABid = abABid;
	}

	public String getAbphone() {
		return abphone;
	}
	public void setAbphone(String abphone) {
		this.abphone = abphone;
	}
	public String getAbphonename() {
		return abphonename;
	}
	public void setAbphonename(String abphonename) {
		this.abphonename = abphonename;
	}
	public String getAbWhetherwfj() {
		return abWhetherwfj;
	}
	public void setAbWhetherwfj(String abWhetherwfj) {
		this.abWhetherwfj = abWhetherwfj;
	}
	public String getAbuserphone() {
		return abuserphone;
	}
	public void setAbuserphone(String abuserphone) {
		this.abuserphone = abuserphone;
	}
	public Date getAbdata() {
		return abdata;
	}
	public void setAbdata(Date abdata) {
		this.abdata = abdata;
	}
	
}
