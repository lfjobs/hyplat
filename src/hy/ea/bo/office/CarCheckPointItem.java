package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarCheckpointitem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CarCheckPointItem implements java.io.Serializable,BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2770069495296535114L;
	private String checkpointitemkey;
	private String checkpointitemid;
	private String checkpointid;
	private String companyid;
	private String checkpointitemname;
	private String datatype;
	private Integer needflag;
	private Integer maxlen;
	private Integer maxdata;
	private Integer mindata;
	private Integer listflag;
	private String listtype;
	private Integer useflag;
	private String remark;
	private Date createtime;

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}



	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public Integer getNeedflag() {
		return this.needflag;
	}

	public void setNeedflag(Integer needflag) {
		this.needflag = needflag;
	}

	public Integer getMaxlen() {
		return this.maxlen;
	}

	public void setMaxlen(Integer maxlen) {
		this.maxlen = maxlen;
	}

	public Integer getMaxdata() {
		return this.maxdata;
	}

	public void setMaxdata(Integer maxdata) {
		this.maxdata = maxdata;
	}

	public Integer getMindata() {
		return this.mindata;
	}

	public void setMindata(Integer mindata) {
		this.mindata = mindata;
	}

	public Integer getListflag() {
		return this.listflag;
	}

	public void setListflag(Integer listflag) {
		this.listflag = listflag;
	}

	public String getListtype() {
		return this.listtype;
	}

	public void setListtype(String listtype) {
		this.listtype = listtype;
	}

	public Integer getUseflag() {
		return this.useflag;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckpointitemkey() {
		return checkpointitemkey;
	}

	public void setCheckpointitemkey(String checkpointitemkey) {
		this.checkpointitemkey = checkpointitemkey;
	}

	public String getCheckpointitemid() {
		return checkpointitemid;
	}

	public void setCheckpointitemid(String checkpointitemid) {
		this.checkpointitemid = checkpointitemid;
	}

	public String getCheckpointid() {
		return checkpointid;
	}

	public void setCheckpointid(String checkpointid) {
		this.checkpointid = checkpointid;
	}

	public String getCheckpointitemname() {
		return checkpointitemname;
	}

	public void setCheckpointitemname(String checkpointitemname) {
		this.checkpointitemname = checkpointitemname;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}