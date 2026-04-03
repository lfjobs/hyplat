package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018-01-06.
 */
@Entity
@Table(name = "DT_SET_SUBSIDIZE", schema = "HYPLAT", catalog = "")
public class SetSubsidize implements BaseBean, java.io.Serializable {
    private String sskey;
    private String ssid;
    private String companyid; //公司id
    private String codeNum; //编号
    private String gtid; //行业分类
    private String stid; //消费补助类型
    private Double totalPct; //零售价赠送比例
    private Double wholesalepct; //批发价赠送比例
    private Double xfPct;//消费红包比例
    private Double xbPct;//消费补充红包比例
    private Double fsPct;//粉丝红包比例
    //private Double fbPct;//粉丝补充红包比例
    private Double flPct;//第一次赠送比例
    private Double slPct;//第二次赠送比例
    private Date adddate; //添加时间
    private String staffid; //操作人员
    private String stutas; //01正常 02删除

    private String staffName;
    private String stName;

    @Id
    @Column(name = "SSKEY", nullable = false, length = 50)
    public String getSskey() {
        return sskey;
    }

    public void setSskey(String sskey) {
        this.sskey = sskey;
    }

    @Basic
    @Column(name = "SSID", nullable = true, length = 50)
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    @Basic
    @Column(name = "companyid", nullable = true, length = 50)
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    @Basic
    @Column(name = "codeNum", nullable = true, length = 50)
    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    @Basic
    @Column(name = "GTID", nullable = true, length = 50)
    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    @Basic
    @Column(name = "STID", nullable = true, length = 50)
    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }

    @Basic
    @Column(name = "TOTAL_PCT", nullable = true, precision = 0)
    public Double getTotalPct() {
        return totalPct;
    }

    public void setTotalPct(Double totalPct) {
        this.totalPct = totalPct;
    }

    @Basic
    @Column(name = "FL_PCT", nullable = true, precision = 0)
    public Double getFlPct() {
        return flPct;
    }

    public void setFlPct(Double flPct) {
        this.flPct = flPct;
    }

    @Basic
    @Column(name = "SL_PCT", nullable = true, precision = 0)
    public Double getSlPct() {
        return slPct;
    }

    public void setSlPct(Double slPct) {
        this.slPct = slPct;
    }

    @Basic
    @Column(name = "ADDDATE", nullable = true)
    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    @Basic
    @Column(name = "STAFFID", nullable = true, length = 255)
    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    @Basic
    @Column(name = "stutas", nullable = true, length = 255)
    public String getStutas() {
        return stutas;
    }

    public void setStutas(String stutas) {
        this.stutas = stutas;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public Double getXfPct() {
        return xfPct;
    }

    public void setXfPct(Double xfPct) {
        this.xfPct = xfPct;
    }

    public Double getXbPct() {
        return xbPct;
    }

    public void setXbPct(Double xbPct) {
        this.xbPct = xbPct;
    }

    public Double getFsPct() {
        return fsPct;
    }

    public void setFsPct(Double fsPct) {
        this.fsPct = fsPct;
    }

    public Double getWholesalepct() {
        return wholesalepct;
    }

    public void setWholesalepct(Double wholesalepct) {
        this.wholesalepct = wholesalepct;
    }

    public SetSubsidize(String sskey, String ssid, String companyid, String codeNum, String gtid, String stid, Double totalPct, Double wholesalepct, Double flPct, Double slPct, Date adddate, String staffid, String stutas) {
        this.sskey = sskey;
        this.ssid = ssid;
        this.companyid = companyid;
        this.codeNum = codeNum;
        this.gtid = gtid;
        this.stid = stid;
        this.totalPct = totalPct;
        this.wholesalepct = wholesalepct;
        this.flPct = flPct;
        this.slPct = slPct;
        this.adddate = adddate;
        this.staffid = staffid;
        this.stutas = stutas;
    }


    public SetSubsidize(String sskey, String ssid, String codeNum, String gtid, String stName, Double totalPct, Double wholesalepct, Double flPct, Double slPct, Date adddate, String staffName, String stutas, Double xfPct, Double xbPct, Double fsPct) {
        this.sskey = sskey;
        this.ssid = ssid;
        this.codeNum = codeNum;
        this.gtid = gtid;
        this.stName = stName;
        this.totalPct = totalPct;
        this.wholesalepct = wholesalepct;
        this.flPct = flPct;
        this.slPct = slPct;
        this.xfPct = xfPct;
        this.xbPct = xfPct;
        this.fsPct = fsPct;
        this.adddate = adddate;
        this.staffName = staffName;
        this.stutas = stutas;
    }

    public SetSubsidize(String sskey, String ssid, String companyid, String codeNum, String gtid, String stid, Double totalPct, Double flPct, Double slPct, Date adddate, String staffid, String stutas) {
        this.sskey = sskey;
        this.ssid = ssid;
        this.companyid = companyid;
        this.codeNum = codeNum;
        this.gtid = gtid;
        this.totalPct = totalPct;
        this.flPct = flPct;
        this.slPct = slPct;
        this.adddate = adddate;
        this.stutas = stutas;
        this.staffid = staffid;
    }

    public SetSubsidize() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetSubsidize that = (SetSubsidize) o;

        if (sskey != null ? !sskey.equals(that.sskey) : that.sskey != null) return false;
        if (ssid != null ? !ssid.equals(that.ssid) : that.ssid != null) return false;
        if (gtid != null ? !gtid.equals(that.gtid) : that.gtid != null) return false;
        if (stid != null ? !stid.equals(that.stid) : that.stid != null) return false;
        if (totalPct != null ? !totalPct.equals(that.totalPct) : that.totalPct != null) return false;
        if (flPct != null ? !flPct.equals(that.flPct) : that.flPct != null) return false;
        if (slPct != null ? !slPct.equals(that.slPct) : that.slPct != null) return false;
        if (adddate != null ? !adddate.equals(that.adddate) : that.adddate != null) return false;
        if (staffid != null ? !staffid.equals(that.staffid) : that.staffid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sskey != null ? sskey.hashCode() : 0;
        result = 31 * result + (ssid != null ? ssid.hashCode() : 0);
        result = 31 * result + (gtid != null ? gtid.hashCode() : 0);
        result = 31 * result + (stid != null ? stid.hashCode() : 0);
        result = 31 * result + (totalPct != null ? totalPct.hashCode() : 0);
        result = 31 * result + (flPct != null ? flPct.hashCode() : 0);
        result = 31 * result + (slPct != null ? slPct.hashCode() : 0);
        result = 31 * result + (adddate != null ? adddate.hashCode() : 0);
        result = 31 * result + (staffid != null ? staffid.hashCode() : 0);
        return result;
    }
}
