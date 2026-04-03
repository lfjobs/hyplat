package  hy.ea.bo.office.vo;

import hy.plat.bo.BaseBean;

import java.util.Date;


public class ViewCarcheckresults implements java.io.Serializable,BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1346693196417709973L;
	// Fields

	private String defectflowkey;
	private String checktaskid;
	private String checktaskname;
	private String checkpointid;
	private String checkpointname;
	private String checkpointitemid;
	private String checkpointitemname;
	private String carid;
	private String carnum;
	private String companyid;
	private Date checkdatetime;
	private String finishflag;

	public Date getCheckdatetime() {
		return checkdatetime;
	}

	public void setCheckdatetime(Date checkdatetime) {
		this.checkdatetime = checkdatetime;
	}

	public String getFinishflag() {
		return finishflag;
	}

	public void setFinishflag(String finishflag) {
		this.finishflag = finishflag;
	}



	public String getDefectflowkey() {
		return defectflowkey;
	}

	public void setDefectflowkey(String defectflowkey) {
		this.defectflowkey = defectflowkey;
	}

	public String getChecktaskid() {
		return this.checktaskid;
	}

	public void setChecktaskid(String checktaskid) {
		this.checktaskid = checktaskid;
	}

	public String getChecktaskname() {
		return this.checktaskname;
	}

	public void setChecktaskname(String checktaskname) {
		this.checktaskname = checktaskname;
	}

	public String getCheckpointid() {
		return this.checkpointid;
	}

	public void setCheckpointid(String checkpointid) {
		this.checkpointid = checkpointid;
	}

	public String getCheckpointname() {
		return this.checkpointname;
	}

	public void setCheckpointname(String checkpointname) {
		this.checkpointname = checkpointname;
	}

	public String getCheckpointitemid() {
		return this.checkpointitemid;
	}

	public void setCheckpointitemid(String checkpointitemid) {
		this.checkpointitemid = checkpointitemid;
	}

	public String getCheckpointitemname() {
		return this.checkpointitemname;
	}

	public void setCheckpointitemname(String checkpointitemname) {
		this.checkpointitemname = checkpointitemname;
	}

	public String getCarid() {
		return this.carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getCarnum() {
		return this.carnum;
	}

	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

}