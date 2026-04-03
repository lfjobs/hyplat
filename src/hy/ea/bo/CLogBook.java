package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;


public class CLogBook implements BaseBean,Serializable{
	private String  clogbookKey;
	private String  clogbookID;
	private String  companyID;
	private String  accountEmail;//登录帐号
	private String	clogbookCounect;//操作内容
	private Date 	clogbookDay;//操作时间
	private String  clogbookUrl;//操作URL
	//IP地址  之前存储ip地址。后来存储网卡 mac地址
	private String  clogbookIP;
	
	public String getClogbookKey() {
		return clogbookKey;
	}
	public void setClogbookKey(String clogbookKey) {
		this.clogbookKey = clogbookKey;
	}
	public String getClogbookID() {
		return clogbookID;
	}
	public void setClogbookID(String clogbookID) {
		this.clogbookID = clogbookID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getAccountEmail() {
		return accountEmail;
	}
	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}
	public String getClogbookCounect() {
		return clogbookCounect;
	}
	public void setClogbookCounect(String clogbookCounect) {
		this.clogbookCounect = clogbookCounect;
	}

	public Date getClogbookDay() {
		return clogbookDay;
	}
	public void setClogbookDay(Date clogbookDay) {
		this.clogbookDay = clogbookDay;
	}
	public String getClogbookTime() {
		return clogbookDay.toString();
	}
	public String getClogbookIP() {
		return clogbookIP;
	}
	public void setClogbookIP(String clogbookIP) {
		this.clogbookIP = clogbookIP;
	}
	public String getClogbookUrl() {
		return clogbookUrl;
	}
	public void setClogbookUrl(String clogbookUrl) {
		this.clogbookUrl = clogbookUrl;
	}
}
