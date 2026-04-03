package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 每道题目收藏
 */
public class QuestionsCollect implements BaseBean{
	private String qcKey;
	private String qcID;
	private String qreID;//考试ID
	private String qrID;
	private Date createDate;//添加时间


	public String getQcKey() {
		return qcKey;
	}

	public void setQcKey(String qcKey) {
		this.qcKey = qcKey;
	}

	public String getQcID() {
		return qcID;
	}

	public void setQcID(String qcID) {
		this.qcID = qcID;
	}

	public String getQreID() {
		return qreID;
	}

	public void setQreID(String qreID) {
		this.qreID = qreID;
	}

	public String getQrID() {
		return qrID;
	}

	public void setQrID(String qrID) {
		this.qrID = qrID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}