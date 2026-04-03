package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2019-10-23.
 */
public class InvtFbCheck implements BaseBean, java.io.Serializable{
    private String fbillkey;
    private String fbillid;
    private String groupcompanysn;  //组织机构id
    private String companyid;   //公司id
    private String companyName;   //公司名称
    private String organizationid;  //部门id
    private String orgName;  //部门名称
    private String departmentid;    //子部门id
    private String billstatus;     //单据状态  草稿 已调整库存
    private String staffid;       //盘库人（制单人）id
    private String staffname;       //盘库人（制单人）名字
    private String billstype;//单据类别
    private String journalnum;     //单据编号
    private String warehouse;      //库房id
    private String warehousename;  //库房名称
    private String goodsmoney;//总金额
    private Date billsdate;         //盘库时间(制单时间)

    public String getFbillkey() {
        return fbillkey;
    }

    public void setFbillkey(String fbillkey) {
        this.fbillkey = fbillkey;
    }

    public String getFbillid() {
        return fbillid;
    }

    public void setFbillid(String fbillid) {
        this.fbillid = fbillid;
    }

    public String getGroupcompanysn() {
        return groupcompanysn;
    }

    public void setGroupcompanysn(String groupcompanysn) {
        this.groupcompanysn = groupcompanysn;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(String organizationid) {
        this.organizationid = organizationid;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
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

    public String getBillstype() {
        return billstype;
    }

    public void setBillstype(String billstype) {
        this.billstype = billstype;
    }

    public String getJournalnum() {
        return journalnum;
    }

    public void setJournalnum(String journalnum) {
        this.journalnum = journalnum;
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

    public String getGoodsmoney() {
        return goodsmoney;
    }

    public void setGoodsmoney(String goodsmoney) {
        this.goodsmoney = goodsmoney;
    }

    public Date getBillsdate() {
        return billsdate;
    }

    public void setBillsdate(Date billsdate) {
        this.billsdate = billsdate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvtFbCheck that = (InvtFbCheck) o;

        if (fbillkey != null ? !fbillkey.equals(that.fbillkey) : that.fbillkey != null) return false;
        if (fbillid != null ? !fbillid.equals(that.fbillid) : that.fbillid != null) return false;
        if (groupcompanysn != null ? !groupcompanysn.equals(that.groupcompanysn) : that.groupcompanysn != null)
            return false;
        if (companyid != null ? !companyid.equals(that.companyid) : that.companyid != null) return false;
        if (organizationid != null ? !organizationid.equals(that.organizationid) : that.organizationid != null)
            return false;
        if (departmentid != null ? !departmentid.equals(that.departmentid) : that.departmentid != null) return false;
        if (billstatus != null ? !billstatus.equals(that.billstatus) : that.billstatus != null) return false;
        if (staffid != null ? !staffid.equals(that.staffid) : that.staffid != null) return false;
        if (staffname != null ? !staffname.equals(that.staffname) : that.staffname != null) return false;
        if (billstype != null ? !billstype.equals(that.billstype) : that.billstype != null) return false;
        if (journalnum != null ? !journalnum.equals(that.journalnum) : that.journalnum != null) return false;
        if (warehouse != null ? !warehouse.equals(that.warehouse) : that.warehouse != null) return false;
        if (warehousename != null ? !warehousename.equals(that.warehousename) : that.warehousename != null)
            return false;
        if (goodsmoney != null ? !goodsmoney.equals(that.goodsmoney) : that.goodsmoney != null) return false;
        if (billsdate != null ? !billsdate.equals(that.billsdate) : that.billsdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fbillkey != null ? fbillkey.hashCode() : 0;
        result = 31 * result + (fbillid != null ? fbillid.hashCode() : 0);
        result = 31 * result + (groupcompanysn != null ? groupcompanysn.hashCode() : 0);
        result = 31 * result + (companyid != null ? companyid.hashCode() : 0);
        result = 31 * result + (organizationid != null ? organizationid.hashCode() : 0);
        result = 31 * result + (departmentid != null ? departmentid.hashCode() : 0);
        result = 31 * result + (billstatus != null ? billstatus.hashCode() : 0);
        result = 31 * result + (staffid != null ? staffid.hashCode() : 0);
        result = 31 * result + (staffname != null ? staffname.hashCode() : 0);
        result = 31 * result + (billstype != null ? billstype.hashCode() : 0);
        result = 31 * result + (journalnum != null ? journalnum.hashCode() : 0);
        result = 31 * result + (warehouse != null ? warehouse.hashCode() : 0);
        result = 31 * result + (warehousename != null ? warehousename.hashCode() : 0);
        result = 31 * result + (goodsmoney != null ? goodsmoney.hashCode() : 0);
        result = 31 * result + (billsdate != null ? billsdate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\"InvtFbCheck\":{" +
                "\"fbillkey \":\"" + fbillkey + '\"' +
                ", \"fbillid \":\"" + fbillid + '\"' +
                ", \"groupcompanysn \":\"" + groupcompanysn + '\"' +
                ", \"companyid \":\"" + companyid + '\"' +
                ", \"companyName \":\"" + companyName + '\"' +
                ", \"organizationid \":\"" + organizationid + '\"' +
                ", \"orgName \":\"" + orgName + '\"' +
                ", \"departmentid \":\"" + departmentid + '\"' +
                ", \"billstatus \":\"" + billstatus + '\"' +
                ", \"staffid \":\"" + staffid + '\"' +
                ", \"staffname \":\"" + staffname + '\"' +
                ", \"billstype \":\"" + billstype + '\"' +
                ", \"journalnum \":\"" + journalnum + '\"' +
                ", \"warehouse \":\"" + warehouse + '\"' +
                ", \"warehousename \":\"" + warehousename + '\"' +
                ", \"goodsmoney \":\"" + goodsmoney + '\"' +
                ", \"billsdate \":" + billsdate +
                "},";
    }
}
