package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DtSetSubsidizeCom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DT_SET_SUBSIDIZE_COM", schema = "HYPLAT", catalog = "")
public class SetSubsidizeCom implements BaseBean,java.io.Serializable {

	// Fields
	private String ssckey;
	private String sscid;
	private String ssid; //消费红包id
	private double totalPct; //零售价赠送金额比例
	private double wholesalePct; //批发价赠送金额比例
	private Date adddate; //添加时间
	private String staffid; //责任人id
	private String ccompanyid; //关联往来单位id
	private String stutas; //01正常 02删除

	// Constructors
	
	public SetSubsidizeCom(){
		super();
	}

	public SetSubsidizeCom(String ssckey, String sscid, String ssid,
			Long totalPct, Long wholesalePct, Date adddate, String staffid,
			String ccompanyid, String stutas) {
		super();
		this.ssckey = ssckey;
		this.sscid = sscid;
		this.ssid = ssid;
		this.totalPct = totalPct;
		this.wholesalePct = wholesalePct;
		this.adddate = adddate;
		this.staffid = staffid;
		this.ccompanyid = ccompanyid;
		this.stutas = stutas;
	}

	// Property accessors

	public String getSsckey() {
		return this.ssckey;
	}

	public void setSsckey(String ssckey) {
		this.ssckey = ssckey;
	}

	public String getSscid() {
		return this.sscid;
	}

	public void setSscid(String sscid) {
		this.sscid = sscid;
	}

	public String getSsid() {
		return this.ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public double getTotalPct() {
		return this.totalPct;
	}

	public void setTotalPct(double totalPct) {
		this.totalPct = totalPct;
	}

	public double getWholesalePct() {
		return this.wholesalePct;
	}

	public void setWholesalePct(double wholesalePct) {
		this.wholesalePct = wholesalePct;
	}

	public Date getAdddate() {
		return this.adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getCcompanyid() {
		return this.ccompanyid;
	}

	public void setCcompanyid(String ccompanyid) {
		this.ccompanyid = ccompanyid;
	}

	public String getStutas() {
		return this.stutas;
	}

	public void setStutas(String stutas) {
		this.stutas = stutas;
	}

}
