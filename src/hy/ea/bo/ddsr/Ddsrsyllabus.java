package hy.ea.bo.ddsr;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * Ddsrsyllabus entity. @author MyEclipse Persistence Tools
 */

public class Ddsrsyllabus implements java.io.Serializable ,ExcelBean,BaseBean{

	// Fields

	private String key;
	private String id;
	private String subject;
	private String type;
	private String program;
	private String programType;
	private String time;
	private String companyID;
	private int    serial ;

	// Constructors
	/** default constructor */
	public Ddsrsyllabus() {
	}

	/** full constructor */
	public Ddsrsyllabus(String id, String subject, String type, String program,
			String time) {
		this.id = id;
		this.subject = subject;
		this.type = type;
		this.program = program;
		this.time = time;
	}

	// Property accessors

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

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProgram() {
		return this.program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}
	

}