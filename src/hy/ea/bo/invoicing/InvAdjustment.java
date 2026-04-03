package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2020-10-10.
 */
public class InvAdjustment implements BaseBean,ExcelBean,java.io.Serializable{
    private String iakey;
    private String iaid;
    private Date adddate; //添加时间
    private String invid; //库存id
    private String staffid; //责任人-调整人id
    private String comid; //公司id
    private String ppid; //产品id
    private String barcode; //条码号
    private String invnum; //原库存
    private String adjnum; //调整库存

    public String getIakey() {
        return iakey;
    }

    public void setIakey(String iakey) {
        this.iakey = iakey;
    }

    public String getIaid() {
        return iaid;
    }

    public void setIaid(String iaid) {
        this.iaid = iaid;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public String getInvid() {
        return invid;
    }

    public void setInvid(String invid) {
        this.invid = invid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getComid() {
        return comid;
    }

    public void setComid(String comid) {
        this.comid = comid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getInvnum() {
        return invnum;
    }

    public void setInvnum(String invnum) {
        this.invnum = invnum;
    }

    public String getAdjnum() {
        return adjnum;
    }

    public void setAdjnum(String adjnum) {
        this.adjnum = adjnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvAdjustment that = (InvAdjustment) o;

        if (iakey != null ? !iakey.equals(that.iakey) : that.iakey != null) return false;
        if (iaid != null ? !iaid.equals(that.iaid) : that.iaid != null) return false;
        if (adddate != null ? !adddate.equals(that.adddate) : that.adddate != null) return false;
        if (invid != null ? !invid.equals(that.invid) : that.invid != null) return false;
        if (staffid != null ? !staffid.equals(that.staffid) : that.staffid != null) return false;
        if (comid != null ? !comid.equals(that.comid) : that.comid != null) return false;
        if (ppid != null ? !ppid.equals(that.ppid) : that.ppid != null) return false;
        if (barcode != null ? !barcode.equals(that.barcode) : that.barcode != null) return false;
        if (invnum != null ? !invnum.equals(that.invnum) : that.invnum != null) return false;
        if (adjnum != null ? !adjnum.equals(that.adjnum) : that.adjnum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iakey != null ? iakey.hashCode() : 0;
        result = 31 * result + (iaid != null ? iaid.hashCode() : 0);
        result = 31 * result + (adddate != null ? adddate.hashCode() : 0);
        result = 31 * result + (invid != null ? invid.hashCode() : 0);
        result = 31 * result + (staffid != null ? staffid.hashCode() : 0);
        result = 31 * result + (comid != null ? comid.hashCode() : 0);
        result = 31 * result + (ppid != null ? ppid.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (invnum != null ? invnum.hashCode() : 0);
        result = 31 * result + (adjnum != null ? adjnum.hashCode() : 0);
        return result;
    }

    @Override
    public String[] properties() {
        return new String[0];
    }
}
