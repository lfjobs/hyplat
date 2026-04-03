package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *  个人工作日志-工作内容. 
 * @author lwz
 */

public class LogConten  implements BaseBean,ExcelBean {

	// Fields

	private String logcontenkey;
	private String logcontenid;
	private String companyid;
	private String logbookid; //日志id
	private String jpbname;		//计划项目名称
	private String jobstatus;	//状态
	private String contactcom;	//完成百分比
	private String remark;		//描述
	private String cname;
	private Date ctime;
	private String uname;
	private Date utime;
	//mz 关于项目和任务目标的ID
	private String projectID;
	private String projectName;
	private String jobTaskID;
	private String taskName;


	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLogcontenkey() {
		return this.logcontenkey;
	}

	public void setLogcontenkey(String logcontenkey) {
		this.logcontenkey = logcontenkey;
	}

	public String getLogcontenid() {
		return this.logcontenid;
	}

	public void setLogcontenid(String logcontenid) {
		this.logcontenid = logcontenid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getLogbookid() {
		return this.logbookid;
	}

	public void setLogbookid(String logbookid) {
		this.logbookid = logbookid;
	}

	public String getJpbname() {
		return this.jpbname;
	}

	public void setJpbname(String jpbname) {
		this.jpbname = jpbname;
	}

	public String getJobstatus() {
		return this.jobstatus;
	}

	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}

	public String getContactcom() {
		return this.contactcom;
	}

	public void setContactcom(String contactcom) {
		this.contactcom = contactcom;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getJobTaskID() {
		return jobTaskID;
	}

	public void setJobTaskID(String jobTaskID) {
		this.jobTaskID = jobTaskID;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
    
	
}