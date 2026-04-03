package hy.ea.bo.ddsr;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Ddsrtrainingrecord entity. @author MyEclipse Persistence Tools
 */

public class Ddsrtrainingrecord implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	private String key;
	private String id;
	private String complete;
	private String ddsrstudentid;
	private String subject;
	private String type;
	private String program;
	private String time;
	private String   startDate;
	private String   endDate;
	private String content;
	private String opinionOfStudent;//学员意见
	private String opinionOfCoach;//教练意见
	private Date addtime;
	private String companyID;
	// Constructors
	/**
	 * 
	 * @note 此实体类作用：保存学员  教学项目  是否完成
	 */

	/** default constructor */
	public Ddsrtrainingrecord() {
	}


	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComplete() {
		return this.complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getDdsrstudentid() {
		return this.ddsrstudentid;
	}

	public void setDdsrstudentid(String ddsrstudentid) {
		this.ddsrstudentid = ddsrstudentid;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOpinionOfStudent() {
		return opinionOfStudent;
	}

	public void setOpinionOfStudent(String opinionOfStudent) {
		this.opinionOfStudent = opinionOfStudent;
	}

	public String getOpinionOfCoach() {
		return opinionOfCoach;
	}

	public void setOpinionOfCoach(String opinionOfCoach) {
		this.opinionOfCoach = opinionOfCoach;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
}