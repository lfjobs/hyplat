package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DoubaoSession implements BaseBean ,java.io.Serializable {

	private String key;
	private String id;
	private String sccId;
	private String sessionId;
	private String type;
	private boolean isfile;
	private String text;


	private Date createDate;//创建时间

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIsfile() {
		return isfile;
	}

	public void setIsfile(boolean isfile) {
		this.isfile = isfile;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
