package hy.ea.bo.human.adance;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * DtHrAttendLogstus entity. @author MyEclipse Persistence Tools
 */

public class AttendLogstus  implements BaseBean {


    // Fields    

     private String attendLogstusKey;
     private String attendLogstusId;
     private String staffid;
     private Date alDate;
     private String alStatus; //迟到早退 
     private String altime ; //时间长度
     private String companyid;
     private String groupcompanysn;
     private String uname;
     private Date ctime;
     private String cname;
     private Date utime;



    public String getAttendLogstusKey() {
        return this.attendLogstusKey;
    }
    
    public void setAttendLogstusKey(String attendLogstusKey) {
        this.attendLogstusKey = attendLogstusKey;
    }

    public String getAttendLogstusId() {
        return this.attendLogstusId;
    }
    
    public void setAttendLogstusId(String attendLogstusId) {
        this.attendLogstusId = attendLogstusId;
    }

    public String getStaffid() {
        return this.staffid;
    }
    
    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public Date getAlDate() {
        return this.alDate;
    }
    
    public void setAlDate(Date alDate) {
        this.alDate = alDate;
    }

    public String getAlStatus() {
        return this.alStatus;
    }
    
    public void setAlStatus(String alStatus) {
        this.alStatus = alStatus;
    }

    public String getCompanyid() {
        return this.companyid;
    }
    
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getGroupcompanysn() {
        return this.groupcompanysn;
    }
    
    public void setGroupcompanysn(String groupcompanysn) {
        this.groupcompanysn = groupcompanysn;
    }

    public String getUname() {
        return this.uname;
    }
    
    public void setUname(String uname) {
        this.uname = uname;
    }

    public Date getCtime() {
        return this.ctime;
    }
    
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getCname() {
        return this.cname;
    }
    
    public void setCname(String cname) {
        this.cname = cname;
    }

    public Date getUtime() {
        return this.utime;
    }
    
    public void setUtime(Date utime) {
        this.utime = utime;
    }

	public String getAltime() {
		return altime;
	}

	public void setAltime(String altime) {
		this.altime = altime;
	}
   
}