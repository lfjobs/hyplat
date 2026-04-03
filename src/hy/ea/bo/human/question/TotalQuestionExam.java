package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 总题库
 */
public class TotalQuestionExam implements BaseBean{
	private String tqKey;
	private String tqeID;//考试ID
	private String tqID;
	private int  duration;//时长 分钟
	private int  totalscore;//总分数
	private int qualifiedSocre;//合格分数
	private int totalQues;//题目数
	private String titleBase;//题库名称
	private String staffID;//考试人
	private String staffName;//考试人姓名
	private String qbtID;//题库类别
	private String typeName;
	private Date createDate;
	private String type;//口试还是笔试题
	private String companyID;
      private String status;//99删除 00 正常


	public String getTqKey() {
		return tqKey;
	}

	public void setTqKey(String tqKey) {
		this.tqKey = tqKey;
	}

	public String getTqID() {
		return tqID;
	}

	public void setTqID(String tqID) {
		this.tqID = tqID;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(int totalscore) {
		this.totalscore = totalscore;
	}

	public int getQualifiedSocre() {
		return qualifiedSocre;
	}

	public void setQualifiedSocre(int qualifiedSocre) {
		this.qualifiedSocre = qualifiedSocre;
	}

	public String getTitleBase() {
		return titleBase;
	}

	public void setTitleBase(String titleBase) {
		this.titleBase = titleBase;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getQbtID() {
		return qbtID;
	}

	public void setQbtID(String qbtID) {
		this.qbtID = qbtID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTotalQues() {
		return totalQues;
	}

	public void setTotalQues(int totalQues) {
		this.totalQues = totalQues;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTqeID() {
		return tqeID;
	}

	public void setTqeID(String tqeID) {
		this.tqeID = tqeID;
	}
}
