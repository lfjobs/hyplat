package hy.tel.bo;

import hy.ea.bo.ExcelBean;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class TelOutRecord implements BaseBean,ExcelBean {

	private String id;
	private String visitedUser;
	private Date visitedTime;
	private String visitedContent;
	private String telType;
	private long isDel;
	private String company;
    private String telNumber;
	private Date beginTime;
	private Date endTime;
	private String savePath;
	private Staff user;
    private String visitedType;
    private String telcodeType;//电话号码类型；座机0手机1
    
	public String getTelcodeType() {
		return telcodeType;
	}

	public void setTelcodeType(String telcodeType) {
		this.telcodeType = telcodeType;
	}

	@Override
	public String[] properties() {
		String[] properties = { };
		return properties;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVisitedUser() {
		return visitedUser;
	}

	public void setVisitedUser(String visitedUser) {
		this.visitedUser = visitedUser;
	}

	public Date getVisitedTime() {
		return visitedTime;
	}

	public void setVisitedTime(Date visitedTime) {
		this.visitedTime = visitedTime;
	}

	public String getVisitedContent() {
		return visitedContent;
	}

	public void setVisitedContent(String visitedContent) {
		this.visitedContent = visitedContent;
	} 
	
	public String getTelType() {
		return telType;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

	public long getIsDel() {
		return isDel;
	}

	public void setIsDel(long isDel) {
		this.isDel = isDel;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Staff getUser() {
		return user;
	}

	public void setUser(Staff user) {
		this.user = user;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getVisitedType() {
		return visitedType;
	}

	public void setVisitedType(String visitedType) {
		this.visitedType = visitedType;
	} 
	
}