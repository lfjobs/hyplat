package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

/**
 * DtMemberBackup entity. @author MyEclipse Persistence Tools
 */

public class DtDaiLiMember  implements BaseBean {
	
    // Fields 
	
     private String mbKey;
     private String mbId;
     private String cashId; //订单id
     private String cashJum;//订单编号
     private String mbType;//会员类别
     private String mbCusId;//会员id
     private String mbZh;//会员账号
     private String staffId;//人员id
     private String staffName;//人员姓名
     private String comId;//公司id
     private String mbStatus;//判断公司个人状态 2：公司 1：个人
     private String jbNum;//积分或金币个数
     private String mbPid;//父id
     private String status;//状态 0：正常 1：作废
     private String typePpid;  //代理资格的ppid
     private String zgMingcheng;  //代理资格的名称
     private String ppid;  //产品的ppid
	 private String goodsbillid;  //订单产品id
    // Constructors

    /** default constructor */
    public DtDaiLiMember() {
    }

    public DtDaiLiMember(String mbKey, String mbId, String cashId, String cashJum, String mbType, String mbCusId, String mbZh, String staffId, String staffName, String comId, String mStatus, String jbNum, String mbPid, String status, String typePpid, String zgMingcheng, String ppid, String mbStatus) {
        this.mbKey = mbKey;
        this.mbId = mbId;
        this.cashId = cashId;
        this.cashJum = cashJum;
        this.mbType = mbType;
        this.mbId = mbCusId;
        this.mbZh = mbZh;
        this.staffId = staffId;
        this.staffName = staffName;
        this.comId = comId;
        this.mbStatus = mbStatus;
        this.jbNum = jbNum;
        this.mbPid = mbPid;
        this.status = status;
        this.typePpid = typePpid;
        this.zgMingcheng = zgMingcheng;
        this.ppid = ppid;
    }

	public String getMbKey() {
		return mbKey;
	}

	public void setMbKey(String mbKey) {
		this.mbKey = mbKey;
	}

	public String getMbId() {
		return mbId;
	}

	public void setMbId(String mbId) {
		this.mbId = mbId;
	}

	public String getCashId() {
		return cashId;
	}

	public void setCashId(String cashId) {
		this.cashId = cashId;
	}

	public String getCashJum() {
		return cashJum;
	}

	public void setCashJum(String cashJum) {
		this.cashJum = cashJum;
	}

	public String getMbType() {
		return mbType;
	}

	public void setMbType(String mbType) {
		this.mbType = mbType;
	}

	public String getMbCusId() {
		return mbCusId;
	}

	public void setMbCusId(String mbCusId) {
		this.mbCusId = mbCusId;
	}

	public String getMbZh() {
		return mbZh;
	}

	public void setMbZh(String mbZh) {
		this.mbZh = mbZh;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getMbStatus() {
		return mbStatus;
	}

	public void setMbStatus(String mbStatus) {
		this.mbStatus = mbStatus;
	}

	public String getJbNum() {
		return jbNum;
	}

	public void setJbNum(String jbNum) {
		this.jbNum = jbNum;
	}

	public String getMbPid() {
		return mbPid;
	}

	public void setMbPid(String mbPid) {
		this.mbPid = mbPid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypePpid() {
		return typePpid;
	}

	public void setTypePpid(String typePpid) {
		this.typePpid = typePpid;
	}

	public String getZgMingcheng() {
		return zgMingcheng;
	}

	public void setZgMingcheng(String zgMingcheng) {
		this.zgMingcheng = zgMingcheng;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getGoodsbillid() {
		return goodsbillid;
	}

	public void setGoodsbillid(String goodsbillid) {
		this.goodsbillid = goodsbillid;
	}
}