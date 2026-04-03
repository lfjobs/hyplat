package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 短链
 *
 */
public class ShortUrl implements BaseBean{
    

	private String sukey;
	private String suId;
	private String surl;//短链
	private String curl;//长链
	private String seq;//序号
    private String staffID;//创建人
	public String getSukey() {
		return sukey;
	}

	public void setSukey(String sukey) {
		this.sukey = sukey;
	}

	public String getSuId() {
		return suId;
	}

	public void setSuId(String suId) {
		this.suId = suId;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getCurl() {
		return curl;
	}

	public void setCurl(String curl) {
		this.curl = curl;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}



	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
}
