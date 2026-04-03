package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class OverdraftBill implements BaseBean,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String overdraftkey;
    private String overdraftid;
    private String journalnum;//订单编号
    private String overdraftnum;//欠款单号
    private String companyid;//供货商公司id
    private String purchaserid;//采购商、欠款公司id
    private String purchasername;//采购商、欠款公司名称
    private String staffid;//责任人id
    private String staffname;//责任人名称
    private String cashierbillsid;//订单id
    private Date adddate;//制单时间
    private String status;//单据状态  01:欠款 02:已付款

    public String getOverdraftkey() {
        return overdraftkey;
    }

    public void setOverdraftkey(String overdraftkey) {
        this.overdraftkey = overdraftkey;
    }

    public String getOverdraftid() {
        return overdraftid;
    }

    public void setOverdraftid(String overdraftid) {
        this.overdraftid = overdraftid;
    }

    public String getJournalnum() {
        return journalnum;
    }

    public void setJournalnum(String journalnum) {
        this.journalnum = journalnum;
    }

    public String getOverdraftnum() {
        return overdraftnum;
    }

    public void setOverdraftnum(String overdraftnum) {
        this.overdraftnum = overdraftnum;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getPurchaserid() {
        return purchaserid;
    }

    public void setPurchaserid(String purchaserid) {
        this.purchaserid = purchaserid;
    }

    public String getPurchasername() {
        return purchasername;
    }

    public void setPurchasername(String purchasername) {
        this.purchasername = purchasername;
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

    public String getCashierbillsid() {
        return cashierbillsid;
    }

    public void setCashierbillsid(String cashierbillsid) {
        this.cashierbillsid = cashierbillsid;
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
        OverdraftBill that = (OverdraftBill) o;
        return Objects.equals(overdraftkey, that.overdraftkey) &&
                Objects.equals(overdraftid, that.overdraftid) &&
                Objects.equals(journalnum, that.journalnum) &&
                Objects.equals(overdraftnum, that.overdraftnum) &&
                Objects.equals(companyid, that.companyid) &&
                Objects.equals(purchaserid, that.purchaserid) &&
                Objects.equals(purchasername, that.purchasername) &&
                Objects.equals(staffid, that.staffid) &&
                Objects.equals(staffname, that.staffname) &&
                Objects.equals(cashierbillsid, that.cashierbillsid) &&
                Objects.equals(adddate, that.adddate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(overdraftkey, overdraftid, journalnum, overdraftnum, companyid, purchaserid, purchasername, staffid, staffname, cashierbillsid, adddate, status);
    }
}
