package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 预算单据父表 CostSheetBillHistory
 * 
 * @author mz
 * 
 */
public class CostSheetBillHistory implements BaseBean ,java.io.Serializable{

	private String key;
	private String hisId;

	// 传阅接收人相关信息
	private String receiverID;
	private String receiverDeptID;
	private String receiverCompanyID;
	private String receiverName;
	private String receiverDeptName;
	private String receiverCompanyName;

	// 项目单ID
	private String costSheetBillID;

	// 传阅人相关信息；
	private String passerID;
	private String passerDeptID;
	private String passerCompanyID;
	private String passerName;
	private String passerDeptName;
	private String passerCompanyName;
	private String groupCompanySn;//集团；
	
	private Date passtime;

	public Date getPasstime() {
		return passtime;
	}

	public void setPasstime(Date passtime) {
		this.passtime = passtime;
	}

	public String getGroupCompanySn() {
		return groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHisId() {
		return hisId;
	}

	public void setHisId(String hisId) {
		this.hisId = hisId;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverDeptID() {
		return receiverDeptID;
	}

	public void setReceiverDeptID(String receiverDeptID) {
		this.receiverDeptID = receiverDeptID;
	}

	public String getReceiverCompanyID() {
		return receiverCompanyID;
	}

	public void setReceiverCompanyID(String receiverCompanyID) {
		this.receiverCompanyID = receiverCompanyID;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverDeptName() {
		return receiverDeptName;
	}

	public void setReceiverDeptName(String receiverDeptName) {
		this.receiverDeptName = receiverDeptName;
	}

	public String getReceiverCompanyName() {
		return receiverCompanyName;
	}

	public void setReceiverCompanyName(String receiverCompanyName) {
		this.receiverCompanyName = receiverCompanyName;
	}

	public String getCostSheetBillID() {
		return costSheetBillID;
	}

	public void setCostSheetBillID(String costSheetBillID) {
		this.costSheetBillID = costSheetBillID;
	}

	public String getPasserID() {
		return passerID;
	}

	public void setPasserID(String passerID) {
		this.passerID = passerID;
	}

	public String getPasserDeptID() {
		return passerDeptID;
	}

	public void setPasserDeptID(String passerDeptID) {
		this.passerDeptID = passerDeptID;
	}

	public String getPasserCompanyID() {
		return passerCompanyID;
	}

	public void setPasserCompanyID(String passerCompanyID) {
		this.passerCompanyID = passerCompanyID;
	}

	public String getPasserName() {
		return passerName;
	}

	public void setPasserName(String passerName) {
		this.passerName = passerName;
	}

	public String getPasserDeptName() {
		return passerDeptName;
	}

	public void setPasserDeptName(String passerDeptName) {
		this.passerDeptName = passerDeptName;
	}

	public String getPasserCompanyName() {
		return passerCompanyName;
	}

	public void setPasserCompanyName(String passerCompanyName) {
		this.passerCompanyName = passerCompanyName;
	}

}
