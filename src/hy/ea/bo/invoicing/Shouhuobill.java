package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 收货单表
 */
public class Shouhuobill implements BaseBean,Serializable {
    private String shkey;
    private String shid;
    private String cashid;//订单id
    private String transportid;//送货单id
    private String journalnum;//单据编号
    private String companyid;//公司id
    private String ghcomname;//供货商id
    private String ghcomid;//供货商名字
    private String cgstaffname;//采购员id
    private String cgstaffid;//采购员名字
    private String staffid;//责任人id
    private String staffname;//责任人名字
    private Date adddate;//添加时间
    private String status;//单据状态  01:入库



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

    public String getGhcomname() {
        return ghcomname;
    }

    public String getShkey() {
        return shkey;
    }

    public void setShkey(String shkey) {
        this.shkey = shkey;
    }

    public String getShid() {
        return shid;
    }

    public void setShid(String shid) {
        this.shid = shid;
    }

    public void setGhcomname(String ghcomname) {
        this.ghcomname = ghcomname;
    }

    public String getGhcomid() {
        return ghcomid;
    }

    public void setGhcomid(String ghcomid) {
        this.ghcomid = ghcomid;
    }

    public String getCgstaffname() {
        return cgstaffname;
    }

    public void setCgstaffname(String cgstaffname) {
        this.cgstaffname = cgstaffname;
    }

    public String getCgstaffid() {
        return cgstaffid;
    }

    public void setCgstaffid(String cgstaffid) {
        this.cgstaffid = cgstaffid;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
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

    public String getCashid() {
        return cashid;
    }

    public void setCashid(String cashid) {
        this.cashid = cashid;
    }

    public String getTransportid() {
        return transportid;
    }

    public void setTransportid(String transportid) {
        this.transportid = transportid;
    }

    public Shouhuobill(String shid, String cashid, String transportid, String journalnum, String companyid, String ghcomname, String ghcomid, String cgstaffname, String cgstaffid, String staffid, String staffname, Date adddate, String status) {
        this.shid = shid;
        this.cashid = cashid;
        this.transportid = transportid;
        this.journalnum = journalnum;
        this.companyid = companyid;
        this.ghcomname = ghcomname;
        this.ghcomid = ghcomid;
        this.cgstaffname = cgstaffname;
        this.cgstaffid = cgstaffid;
        this.staffid = staffid;
        this.staffname = staffname;
        this.adddate = adddate;
        this.status = status;
    }

    public Shouhuobill() {
    }
}
