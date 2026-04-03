package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 最近联系人
 */
public class RecentContact implements BaseBean {

	private String key;
	private String id;
	private String staffID;
	private Date createDate;
	private String companyID;//公司还是个人传的
	private String scompanyID;//传给谁的
	private String scompanyName;
	private String sorgID;
	private String sorgName;
	private String sstaffID;
	private String staffName;
	private String telphone;
	private String source;//来源 集团内部01 ，往来单位02， 往来个人.03  未注册 04
	private String oprState;//分发 pub




	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getScompanyID() {
		return scompanyID;
	}

	public void setScompanyID(String scompanyID) {
		this.scompanyID = scompanyID;
	}

	public String getSorgID() {
		return sorgID;
	}

	public void setSorgID(String sorgID) {
		this.sorgID = sorgID;
	}

	public String getScompanyName() {
		return scompanyName;
	}

	public void setScompanyName(String scompanyName) {
		this.scompanyName = scompanyName;
	}

	public String getSorgName() {
		return sorgName;
	}

	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}

	public String getSstaffID() {
		return sstaffID;
	}

	public void setSstaffID(String sstaffID) {
		this.sstaffID = sstaffID;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getOprState() {
		return oprState;
	}

	public void setOprState(String oprState) {
		this.oprState = oprState;
	}
}
