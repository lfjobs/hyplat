package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 每道题目
 */
public class Questions implements BaseBean{
	private String qrKey;
	private String qrID;
	private String companyID;
	private String staffID;//编写人
	private String staffName;//编写人姓名
	private int  seq;//序号
	private String title;//标题
	private String correctAnswer;//正确答案
	private String picpath;//图片
	private String quesType;//00 单选 01 多选 02 判断 03 简答题
	private int score;//分值
	private String tqID;//题库ID
	private String tqeID;//考试题库ID
	private String status;//99删除 00 正常
	private Date createDate;//添加时间
	private String qreID;//考试ID


	public String getQrKey() {
		return qrKey;
	}

	public void setQrKey(String qrKey) {
		this.qrKey = qrKey;
	}

	public String getQrID() {
		return qrID;
	}

	public void setQrID(String qrID) {
		this.qrID = qrID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public String getQuesType() {
		return quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTqID() {
		return tqID;
	}

	public void setTqID(String tqID) {
		this.tqID = tqID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getQreID() {
		return qreID;
	}

	public void setQreID(String qreID) {
		this.qreID = qreID;
	}

	public String getTqeID() {
		return tqeID;
	}

	public void setTqeID(String tqeID) {
		this.tqeID = tqeID;
	}
}