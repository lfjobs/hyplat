package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 验货单表
 */
public class Yanhuobill implements BaseBean,Serializable {
    private String yhkey;
    private String yhid;
    private String shid;
    private String cashid;//订单id
    private String journalnum;//单据编号
    private String companyid;//公司id
    private String ghcomname;//供货商id
    private String ghcomid;//供货商名字
    private String cgstaffname;//采购员id
    private String cgstaffid;//采购员名字
    private String staffid;//责任人id
    private String staffname;//责任人名字
    private Date adddate;//添加时间
    private String status;//单据状态  01:验货


    public String getYhkey() {
        return yhkey;
    }

    public void setYhkey(String yhkey) {
        this.yhkey = yhkey;
    }

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    public String getShid() {
        return shid;
    }

    public void setShid(String shid) {
        this.shid = shid;
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

    public String getGhcomname() {
        return ghcomname;
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

    public Yanhuobill(String yhid, String shid, String cashid, String journalnum, String companyid, String ghcomname, String ghcomid, String cgstaffname, String cgstaffid, String staffid, String staffname, Date adddate, String status) {
        this.yhid = yhid;
        this.shid = shid;
        this.cashid = cashid;
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

    public Yanhuobill() {
    }
}
