package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarCheckdefect entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CarCheckDefect implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4100358252256272970L;
	private String defectflowkey;
	private String defectflowid;
	private String checktaskid;//任务
	private String checkdeviceflowid;//设备
	private String pointid;//检查点
	private String pointitemid;//检查项
	private Date checkdatetime;
	private String textval;
	private Float floatvalue;
	private Date datetimeval;
	private String booleanval;
	private String listval;
	private Integer finishflag;
	private String remark;
	private String companyid;


	public String getDefectflowkey() {
		return this.defectflowkey;
	}

	public void setDefectflowkey(String defectflowkey) {
		this.defectflowkey = defectflowkey;
	}

	public String getDefectflowid() {
		return this.defectflowid;
	}

	public void setDefectflowid(String defectflowid) {
		this.defectflowid = defectflowid;
	}

	public String getChecktaskid() {
		return this.checktaskid;
	}

	public void setChecktaskid(String checktaskid) {
		this.checktaskid = checktaskid;
	}

	public String getCheckdeviceflowid() {
		return this.checkdeviceflowid;
	}

	public void setCheckdeviceflowid(String checkdeviceflowid) {
		this.checkdeviceflowid = checkdeviceflowid;
	}

	

	public String getPointid() {
		return pointid;
	}

	public void setPointid(String pointid) {
		this.pointid = pointid;
	}

	public String getPointitemid() {
		return pointitemid;
	}

	public void setPointitemid(String pointitemid) {
		this.pointitemid = pointitemid;
	}

	public Date getCheckdatetime() {
		return this.checkdatetime;
	}

	public void setCheckdatetime(Date checkdatetime) {
		this.checkdatetime = checkdatetime;
	}

	public String getTextval() {
		return this.textval;
	}

	public void setTextval(String textval) {
		this.textval = textval;
	}

	public Float getFloatvalue() {
		return this.floatvalue;
	}

	public void setFloatvalue(Float floatvalue) {
		this.floatvalue = floatvalue;
	}

	public Date getDatetimeval() {
		return this.datetimeval;
	}

	public void setDatetimeval(Date datetimeval) {
		this.datetimeval = datetimeval;
	}

	public String getBooleanval() {
		return this.booleanval;
	}

	public void setBooleanval(String booleanval) {
		this.booleanval = booleanval;
	}

	public String getListval() {
		return this.listval;
	}

	public void setListval(String listval) {
		this.listval = listval;
	} 

	public Integer getFinishflag() {
		return finishflag;
	}

	public void setFinishflag(Integer finishflag) {
		this.finishflag = finishflag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

}