package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarChecktask entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CarCheckTask implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970991173985321331L;
	private String checktaskkey;
	private String checktaskid;
	private String checkplanid;
	private String checktaskname;
	private String companyid;
	private String operatorid;
	private String operatorname;
	private String principal;
	private String principalname;
	private String confimerid;
	private String confimername;
	private String checked;
	private Date starttime;
	private Date endtime;
	private Date createtime;
	private Integer taskstatus;//0:未开始，1进行中，2完成
	private Integer checkflag;//1]0,未检查，1检查
	private String tasktype;


	public String getChecktaskkey() {
		return this.checktaskkey;
	}

	public void setChecktaskkey(String checktaskkey) {
		this.checktaskkey = checktaskkey;
	}

	public String getChecktaskid() {
		return this.checktaskid;
	}

	public void setChecktaskid(String checktaskid) {
		this.checktaskid = checktaskid;
	}

	public String getCheckplanid() {
		return this.checkplanid;
	}

	public void setCheckplanid(String checkplanid) {
		this.checkplanid = checkplanid;
	}

	public String getChecktaskname() {
		return this.checktaskname;
	}

	public void setChecktaskname(String checktaskname) {
		this.checktaskname = checktaskname;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOperatorid() {
		return this.operatorid;
	}

	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getConfimerid() {
		return this.confimerid;
	}

	public void setConfimerid(String confimerid) {
		this.confimerid = confimerid;
	}

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
 

	public Integer getTaskstatus() {
		return taskstatus;
	}

	public void setTaskstatus(Integer taskstatus) {
		this.taskstatus = taskstatus;
	}

	public Integer getCheckflag() {
		return checkflag;
	}

	public void setCheckflag(Integer checkflag) {
		this.checkflag = checkflag;
	}

	public String getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getOperatorname() {
		return operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	public String getPrincipalname() {
		return principalname;
	}

	public void setPrincipalname(String principalname) {
		this.principalname = principalname;
	}

	public String getConfimername() {
		return confimername;
	}

	public void setConfimername(String confimername) {
		this.confimername = confimername;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}