package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

/**
 * DtMemberBackup entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtMemberBackup  implements BaseBean,java.io.Serializable {
	
    // Fields 
	
     private String mbKey;
     private String mbId;
     private String cashId;
     private String cashJum;
     private String MType;
     private String MId;
     private String MZh;
     private String staffId;
     private String staffName;
     private String comId;
     private String MStatus;
     private String jbNum;
     private String jbbl;
     private String mbPid;
     private String status;


    // Constructors

    /** default constructor */
    public DtMemberBackup() {
    }

    
    /** full constructor */
    public DtMemberBackup(String mbId, String cashId, String cashJum, String MType, String MId, String MZh, String staffId, String staffName, String comId, String MStatus, String jbNum, String mbPid) {
        this.mbId = mbId;
        this.cashId = cashId;
        this.cashJum = cashJum;
        this.MType = MType;
        this.MId = MId;
        this.MZh = MZh;
        this.staffId = staffId;
        this.staffName = staffName;
        this.comId = comId;
        this.MStatus = MStatus;
        this.jbNum = jbNum;
        this.mbPid = mbPid;
    }

   
    // Property accessors

    public String getMbKey() {
        return this.mbKey;
    }
    
    public void setMbKey(String mbKey) {
        this.mbKey = mbKey;
    }

    public String getMbId() {
        return this.mbId;
    }
    
    public void setMbId(String mbId) {
        this.mbId = mbId;
    }

    public String getCashId() {
        return this.cashId;
    }
    
    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

    public String getCashJum() {
        return this.cashJum;
    }
    
    public void setCashJum(String cashJum) {
        this.cashJum = cashJum;
    }

    public String getMType() {
        return this.MType;
    }
    
    public void setMType(String MType) {
        this.MType = MType;
    }

    public String getMId() {
        return this.MId;
    }
    
    public void setMId(String MId) {
        this.MId = MId;
    }

    public String getMZh() {
        return this.MZh;
    }
    
    public void setMZh(String MZh) {
        this.MZh = MZh;
    }

    public String getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return this.staffName;
    }
    
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getComId() {
        return this.comId;
    }
    
    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getMStatus() {
        return this.MStatus;
    }
    
    public void setMStatus(String MStatus) {
        this.MStatus = MStatus;
    }

    public String getJbNum() {
        return this.jbNum;
    }
    
    public void setJbNum(String jbNum) {
        this.jbNum = jbNum;
    }

    public String getMbPid() {
        return this.mbPid;
    }
    
    public void setMbPid(String mbPid) {
        this.mbPid = mbPid;
    }
    
	public String getJbbl() {
		return jbbl;
	}

	public void setJbbl(String jbbl) {
		this.jbbl = jbbl;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
   
}