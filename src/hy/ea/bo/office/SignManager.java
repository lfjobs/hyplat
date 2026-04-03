package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class SignManager implements BaseBean, ExcelBean {

	private String signmanagerkey;
	private String signmanagerid;
	private String relationtable;
	private String relationid;
	private String position;
	private String signid;
	private String signstat;
	private Date datetime;

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		String[] properties = { signmanagerid, relationtable, relationid, position,
				signid, signstat, String.format("%1$tF", datetime) };
		return null;
	}


	public String getRelationtable() {
		return relationtable;
	}

	public void setRelationtable(String relationtable) {
		this.relationtable = relationtable;
	}

	public String getRelationid() {
		return relationid;
	}

	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}

	public String getPosition() {
		return position;
	}

	public String getSignid() {
		return signid;
	}

	public void setSignid(String signid) {
		this.signid = signid;
	}

	public String getSignstat() {
		return signstat;
	}

	public void setSignstat(String signstat) {
		this.signstat = signstat;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSignmanagerid() {
		return signmanagerid;
	}


	public void setSignmanagerid(String signmanagerid) {
		this.signmanagerid = signmanagerid;
	}


	public String getSignmanagerkey() {
		return signmanagerkey;
	}


	public void setSignmanagerkey(String signmanagerkey) {
		this.signmanagerkey = signmanagerkey;
	}

}
