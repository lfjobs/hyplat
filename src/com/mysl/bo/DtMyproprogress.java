package com.mysl.bo;

import hy.plat.bo.BaseBean;


/**
 * DtMyproprogress entity. @author MyEclipse Persistence Tools
 */

public class DtMyproprogress implements java.io.Serializable,BaseBean {

	// Fields

	private String progkey;//主键
	private String progid;//业务主键
	private String proid;//项目id
	private String staffid;//执行人id
	private String staffname;//执行人姓名
	private int tasknumbyone;//每个人的任务总数
	private int scsjnum;//生产设计阶段已完成
	private int sjwcnum;//设计完成阶段已完成数
	private int tjcgnum;//提交成国阶段已完成数
	private int xmdanum;//项目档案阶段已完成数

	// Constructors

	/** default constructor */
	public DtMyproprogress() {
	}

	/** full constructor */
	public DtMyproprogress(String progkey, String progid, String proid,
			String staffid, String staffname, int tasknumbyone, int scsjnum,
			int sjwcnum, int tjcgnum, int xmdanum) {
		super();
		this.progkey = progkey;
		this.progid = progid;
		this.proid = proid;
		this.staffid = staffid;
		this.staffname = staffname;
		this.tasknumbyone = tasknumbyone;
		this.scsjnum = scsjnum;
		this.sjwcnum = sjwcnum;
		this.tjcgnum = tjcgnum;
		this.xmdanum = xmdanum;
	}

	// Property accessors

	public String getProgkey() {
		return this.progkey;
	}
	public void setProgkey(String progkey) {
		this.progkey = progkey;
	}

	public String getProgid() {
		return this.progid;
	}

	public void setProgid(String progid) {
		this.progid = progid;
	}

	public String getProid() {
		return this.proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public int getTasknumbyone() {
		return tasknumbyone;
	}

	public void setTasknumbyone(int tasknumbyone) {
		this.tasknumbyone = tasknumbyone;
	}

	public int getScsjnum() {
		return scsjnum;
	}

	public void setScsjnum(int scsjnum) {
		this.scsjnum = scsjnum;
	}

	public int getSjwcnum() {
		return sjwcnum;
	}

	public void setSjwcnum(int sjwcnum) {
		this.sjwcnum = sjwcnum;
	}

	public int getTjcgnum() {
		return tjcgnum;
	}

	public void setTjcgnum(int tjcgnum) {
		this.tjcgnum = tjcgnum;
	}

	public int getXmdanum() {
		return xmdanum;
	}

	public void setXmdanum(int xmdanum) {
		this.xmdanum = xmdanum;
	}

}