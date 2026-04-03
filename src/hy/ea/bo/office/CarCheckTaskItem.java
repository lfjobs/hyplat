package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

/**
 * DtCarChecktaskitem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CarCheckTaskItem implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6196483966214679242L;
	/**
	 * 
	 */
    private String checktaskitemkey;
	private String taskitemid;
	private String taskitemname;//对应的是pointname
	private String companyid;
	private String taskid;
	private Integer checkflag;
	private String pointid;

	public String getPointid() {
		return pointid;
	}

	public void setPointid(String pointid) {
		this.pointid = pointid;
	}

	public String getChecktaskitemkey() {
		return this.checktaskitemkey;
	}

	public void setChecktaskitemkey(String checktaskitemkey) {
		this.checktaskitemkey = checktaskitemkey;
	}

	public String getTaskitemid() {
		return this.taskitemid;
	}

	public void setTaskitemid(String taskitemid) {
		this.taskitemid = taskitemid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public Integer getCheckflag() {
		return this.checkflag;
	}

	public void setCheckflag(Integer checkflag) {
		this.checkflag = checkflag;
	}

	public String getTaskitemname() {
		return taskitemname;
	}

	public void setTaskitemname(String taskitemname) {
		this.taskitemname = taskitemname;
	}

}