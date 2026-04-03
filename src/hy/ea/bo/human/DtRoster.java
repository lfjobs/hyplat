package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtRoster entity. @author MyEclipse Persistence Tools
 * 花名册
 */

public class DtRoster implements BaseBean,java.io.Serializable{

	// Fields

	private String rosterkey;
	private String rosterid;
	private String companyid;
	private String name;
	private String phone;
	private String email;
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;

	// Constructors

	/** default constructor */
	public DtRoster() {
	}

	/** full constructor */
	public DtRoster(String rosterid, String companyid, String name,
			String phone, String email, String cname, Date ctime, String uname,
			Date utime) {
		this.rosterid = rosterid;
		this.companyid = companyid;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.cname = cname;
		this.ctime = ctime;
		this.uname = uname;
		this.utime = utime;
	}

	// Property accessors

	public String getRosterkey() {
		return this.rosterkey;
	}

	public void setRosterkey(String rosterkey) {
		this.rosterkey = rosterkey;
	}

	public String getRosterid() {
		return this.rosterid;
	}

	public void setRosterid(String rosterid) {
		this.rosterid = rosterid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}


}