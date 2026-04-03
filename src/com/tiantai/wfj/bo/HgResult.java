package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 记录货柜正在扫码的用户
 */
public class HgResult implements BaseBean,java.io.Serializable {

	private String hrkey;
	private String hrId;
	private String sccId;
	private String hgCode;//货柜编号
	private String journalNum;
	private Date createDate;


	public String getHrkey() {
		return hrkey;
	}

	public void setHrkey(String hrkey) {
		this.hrkey = hrkey;
	}

	public String getHrId() {
		return hrId;
	}

	public void setHrId(String hrId) {
		this.hrId = hrId;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getHgCode() {
		return hgCode;
	}

	public void setHgCode(String hgCode) {
		this.hgCode = hgCode;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}