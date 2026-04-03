package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarCheckpoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CarCheckPoint implements java.io.Serializable,BaseBean{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6626842473947355242L;
	private String checkkey;
	private String checkpointid;
	private String devicetypeid;
	private String carNum;//车牌号
	private String companyid;
	private String checkpointname;
	private Long useflag;//0l未启用，1启用
	private String chip;
	private Date createtime;

	

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCheckkey() {
		return this.checkkey;
	}

	public void setCheckkey(String checkkey) {
		this.checkkey = checkkey;
	}



	public String getDevicetypeid() {
		return this.devicetypeid;
	}

	public void setDevicetypeid(String devicetypeid) {
		this.devicetypeid = devicetypeid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}



	public String getCheckpointid() {
		return checkpointid;
	}

	public void setCheckpointid(String checkpointid) {
		this.checkpointid = checkpointid;
	}

	public String getCheckpointname() {
		return checkpointname;
	}

	public void setCheckpointname(String checkpointname) {
		this.checkpointname = checkpointname;
	}

	public Long getUseflag() {
		return this.useflag;
	}

	public void setUseflag(Long useflag) {
		this.useflag = useflag;
	}

	public String getChip() {
		return this.chip;
	}

	public void setChip(String chip) {
		this.chip = chip;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

}