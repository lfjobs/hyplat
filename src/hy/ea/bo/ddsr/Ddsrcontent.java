package hy.ea.bo.ddsr;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * Ddsrcontent entity. @author MyEclipse Persistence Tools
 */

public class Ddsrcontent implements java.io.Serializable ,ExcelBean,BaseBean{

	// Fields

	private String key;
	private String id;
	private String type;
	private String subject;
	private String program;
	private String content;
	private String companyID;
	private Date    addTime ;
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
	
	

}