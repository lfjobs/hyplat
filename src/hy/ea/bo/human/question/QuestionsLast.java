package hy.ea.bo.human.question;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 记录最后一次看的题
 */
public class QuestionsLast implements BaseBean{
	private String qlKey;
	private String qlID;
	private String tqeID;//题目
	private String tqID;
	private String staffID;//考试ID
	private int pageNumber;
	private Date createDate;//添加时间

	public String getQlKey() {
		return qlKey;
	}

	public void setQlKey(String qlKey) {
		this.qlKey = qlKey;
	}

	public String getQlID() {
		return qlID;
	}

	public void setQlID(String qlID) {
		this.qlID = qlID;
	}

	public String getTqeID() {
		return tqeID;
	}

	public void setTqeID(String tqeID) {
		this.tqeID = tqeID;
	}

	public String getTqID() {
		return tqID;
	}

	public void setTqID(String tqID) {
		this.tqID = tqID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}