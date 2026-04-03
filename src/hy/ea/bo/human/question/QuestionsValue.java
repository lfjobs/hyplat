package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

/**
 * 每道题目对应答案选项
 */
public class QuestionsValue implements BaseBean{
	private String qrvKey;
	private String qrvID;
	private String qrID;//题目ID
    private String codeName;//A，B，
	private String codeValue;//答案1，答案2
	private int seq;//序列号
	private String staffID;//创建人
	private String qrveID;//考试ID
	private String qreID;//考试的QuestionsID
	public String getQrvKey() {
		return qrvKey;
	}

	public void setQrvKey(String qrvKey) {
		this.qrvKey = qrvKey;
	}

	public String getQrvID() {
		return qrvID;
	}

	public void setQrvID(String qrvID) {
		this.qrvID = qrvID;
	}

	public String getQrID() {
		return qrID;
	}

	public void setQrID(String qrID) {
		this.qrID = qrID;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getQrveID() {
		return qrveID;
	}

	public void setQrveID(String qrveID) {
		this.qrveID = qrveID;
	}

	public String getQreID() {
		return qreID;
	}

	public void setQreID(String qreID) {
		this.qreID = qreID;
	}
}