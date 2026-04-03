package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 入库单表
 */
public class Rukubill implements BaseBean,Serializable {
    private String rkkey;
    private String rkid;
    private String cashid;//订单id
    private String transportid;//订单id
    private String journalnum;//单据编号
    private String companyid;//公司id
    private String ghcomname;//供货商名字
    private String ghcomid;//供货商id
    private String cgstaffname;//入库责任人员id
    private String cgstaffid;//入库责任人名字
    private String staffid;//制单人id
    private String staffname;//制单人名字
    private String warehouse;//库房id
    private String warehousename;//库房名称
    private Date adddate;//添加时间
    private String status;//单据状态  01:入库

    public String getRkkey() {
        return rkkey;
    }

    public void setRkkey(String rkkey) {
        this.rkkey = rkkey;
    }

    public String getRkid() {
        return rkid;
    }

    public void setRkid(String rkid) {
        this.rkid = rkid;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
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

    public Rukubill(String rkid, String cashid, String transportid, String journalnum, String companyid, String ghcomname, String ghcomid, String cgstaffname, String cgstaffid, String staffid, String staffname, String warehouse, String warehousename, Date adddate, String status) {
        this.rkid = rkid;
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
        this.warehouse = warehouse;
        this.warehousename = warehousename;
        this.adddate = adddate;
        this.status = status;
    }

    public Rukubill() {
    }
}
