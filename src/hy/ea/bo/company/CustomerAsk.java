package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

public class CustomerAsk implements BaseBean{
	private String key;
	private String askID;
	private String staffID;//单位ID
	private String customerName;//单位名称
	private String linkman;//联系人
	private String phone;//联系人电话
	private String sax;//联系人性别
	private String qq;//qq
	private String wxNumber;//微信号
	private String content;//咨询问题
	private String createTime;//咨询时间
	private String askType;//是否处理 0 未处理  1 已处理
	private String answerTime;//处理时间
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAskID() {
		return askID;
	}
	public void setAskID(String askID) {
		this.askID = askID;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAskType() {
		return askType;
	}
	public void setAskType(String askType) {
		this.askType = askType;
	}
	public String getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}
	public String getSax() {
		return sax;
	}
	public void setSax(String sax) {
		this.sax = sax;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWxNumber() {
		return wxNumber;
	}
	public void setWxNumber(String wxNumber) {
		this.wxNumber = wxNumber;
	}
	

	
}
