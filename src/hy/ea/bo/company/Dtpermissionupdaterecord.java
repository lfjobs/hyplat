package hy.ea.bo.company;

import java.util.Date;

/**
 * Dtpermissionupdaterecord entity.
 * @author MyEclipse Persistence Tools
 * 权限修改记录
 */

public class Dtpermissionupdaterecord implements java.io.Serializable {

	// Fields
	private String crolechangelogokey;
	private String crolechangelogoid;
	private String croledescribe;
	private String croledetails;
	private Date crolechangelogodate;

	// Constructors

	/** default constructor */
	public Dtpermissionupdaterecord() {
	}

	/** full constructor */
	public Dtpermissionupdaterecord(String crolechangelogoid,
			String croledescribe, String croledetails, Date crolechangelogodate) {
		this.crolechangelogoid = crolechangelogoid;
		this.croledescribe = croledescribe;
		this.croledetails = croledetails;
		this.crolechangelogodate = crolechangelogodate;
	}

	// Property accessors

	public String getCrolechangelogokey() {
		return this.crolechangelogokey;
	}

	public void setCrolechangelogokey(String crolechangelogokey) {
		this.crolechangelogokey = crolechangelogokey;
	}

	public String getCrolechangelogoid() {
		return this.crolechangelogoid;
	}

	public void setCrolechangelogoid(String crolechangelogoid) {
		this.crolechangelogoid = crolechangelogoid;
	}

	public String getCroledescribe() {
		return this.croledescribe;
	}

	public void setCroledescribe(String croledescribe) {
		this.croledescribe = croledescribe;
	}

	public String getCroledetails() {
		return this.croledetails;
	}

	public void setCroledetails(String croledetails) {
		this.croledetails = croledetails;
	}

	public Date getCrolechangelogodate() {
		return this.crolechangelogodate;
	}

	public void setCrolechangelogodate(Date crolechangelogodate) {
		this.crolechangelogodate = crolechangelogodate;
	}

}