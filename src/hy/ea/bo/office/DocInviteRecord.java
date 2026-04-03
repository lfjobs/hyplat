package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DocInviteRecord implements BaseBean,java.io.Serializable {


	private String key;
	private String id;
	private String docId;

	private String status;//状态，打算传阅，还是群发等。
	private String telphone;//对方手机号账号
	private String seqno;//序列号
	private String oprState;//是否已经处理完毕 00 01
	private Date  inventDate;//邀请时间
	private Date receiveDate;//注册后接收时间
	private String sccid;//发送人即作为推荐人
	private String staffID;//发送人ID
	private String docstatus;//公文发送前状态
	private String bystaffID;//被邀请人的staffID 有可能这个未注册用户已经在人力资源里了

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

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getOprState() {
		return oprState;
	}

	public void setOprState(String oprState) {
		this.oprState = oprState;
	}

	public Date getInventDate() {
		return inventDate;
	}

	public void setInventDate(Date inventDate) {
		this.inventDate = inventDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	public String getBystaffID() {
		return bystaffID;
	}

	public void setBystaffID(String bystaffID) {
		this.bystaffID = bystaffID;
	}
}
