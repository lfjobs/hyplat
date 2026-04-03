package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 初始库存表
 */
public class InitializeBill implements BaseBean, Serializable {
    private String initializekey;
    private String initializeid;
    private String journalnum;  //单据编号
    private String companyid;  //公司id
    private String staffid;  //责任人id
    private String staffname;  //责任人名字
    private String codeid;  //产品类别id
    private String codename;  //产品类别名称
    private Date adddate;  //添加时间
    private String status;  //单据状态  01:入库

    public InitializeBill() {
    }

    public InitializeBill(String initializeid, String journalnum, String companyid,String staffid, String staffname, String codeid, String codename, Date adddate, String status) {
        this.initializeid = initializeid;
        this.journalnum = journalnum;
        this.companyid = companyid;
        this.staffid = staffid;
        this.staffname = staffname;
        this.codeid = codeid;
        this.codename = codename;
        this.adddate = adddate;
        this.status = status;
    }

    public String getInitializekey() {
        return initializekey;
    }

    public void setInitializekey(String initializekey) {
        this.initializekey = initializekey;
    }

    public String getInitializeid() {
        return initializeid;
    }

    public void setInitializeid(String initializeid) {
        this.initializeid = initializeid;
    }

    public String getJournalnum() {
        return journalnum;
    }

    public void setJournalnum(String journalnum) {
        this.journalnum = journalnum;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitializeBill that = (InitializeBill) o;

        if (initializekey != null ? !initializekey.equals(that.initializekey) : that.initializekey != null)
            return false;
        if (initializeid != null ? !initializeid.equals(that.initializeid) : that.initializeid != null) return false;
        if (journalnum != null ? !journalnum.equals(that.journalnum) : that.journalnum != null) return false;
        if (companyid != null ? !companyid.equals(that.companyid) : that.companyid != null) return false;
        if (staffid != null ? !staffid.equals(that.staffid) : that.staffid != null) return false;
        if (staffname != null ? !staffname.equals(that.staffname) : that.staffname != null) return false;
        if (codeid != null ? !codeid.equals(that.codeid) : that.codeid != null) return false;
        if (codename != null ? !codename.equals(that.codename) : that.codename != null) return false;
        if (adddate != null ? !adddate.equals(that.adddate) : that.adddate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = initializekey != null ? initializekey.hashCode() : 0;
        result = 31 * result + (initializeid != null ? initializeid.hashCode() : 0);
        result = 31 * result + (journalnum != null ? journalnum.hashCode() : 0);
        result = 31 * result + (companyid != null ? companyid.hashCode() : 0);
        result = 31 * result + (staffid != null ? staffid.hashCode() : 0);
        result = 31 * result + (staffname != null ? staffname.hashCode() : 0);
        result = 31 * result + (codeid != null ? codeid.hashCode() : 0);
        result = 31 * result + (codename != null ? codename.hashCode() : 0);
        result = 31 * result + (adddate != null ? adddate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
