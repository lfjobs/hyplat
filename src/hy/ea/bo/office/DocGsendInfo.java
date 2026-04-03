package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DocGsendInfo implements BaseBean {

	private String key;
	private String gsId;
	private String docId;
	private String companyID;
	private String organizationID;
	private String readerID;
	private String readstate;//是否阅读；1:未阅读，0：阅读；
	private String delstate;//是否删除
	private String reciveTime;//收到文件时间
	private Date readtime;//阅读时间
	private String transfer; // 某个用户是否对该DOCUMENT有转发权限
	private String load; // 某个用户是否对该DOCUMENT有下载权限
	private String print; // 某个用户是否对该DOCUMENT有打印权限
	private String shares;// 共享
	private String pub;//发布到网站
	
	
	private String companyName;
	private String organizationName;
	private String staffName;
	public String getReciveTime() {
		return reciveTime;
	}
	public void setReciveTime(String reciveTime) {
		this.reciveTime = reciveTime;
	}

	public Date getReadtime() {
		return readtime;
	}
	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getGsId() {
		return gsId;
	}
	public void setGsId(String gsId) {
		this.gsId = gsId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getReaderID() {
		return readerID;
	}
	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}
	public String getReadstate() {
		return readstate;
	}
	public void setReadstate(String readstate) {
		this.readstate = readstate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getTransfer() {
		return transfer;
	}
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
	public String getLoad() {
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public String getShares() {
		return shares;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	public String getPub() {
		return pub;
	}
	public void setPub(String pub) {
		this.pub = pub;
	}
	public String getDelstate() {
		return delstate;
	}
	public void setDelstate(String delstate) {
		this.delstate = delstate;
	}
	
	

}
