package com.tiantai.wfj.edmandServe.bo;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lf on 2017-07-12.
 * 抢单管理
 */
@Entity
@Table(name = "DT_DEMAND_SERVE", schema = "HYPLAT", catalog = "")
public class DemandServe implements BaseBean{
    private String dskey;
    private String dsid;
    private String dscomid; //抢单人员公司id
    private String dsddid;  //需求详情id
    private String dscid;//关联id (来源不同关联表不同,来源1:抢单主体是个人关联staff 2:抢单主体是企业关联company)
    private String dstype; //来源 1:个人 2:企业
    private String dssccid;  //抢单人员微分金帐号id
    private Date dsdate;  //抢单时间
    private String dsaddress;  //抢单地址
    private String coordinate; //抢单地址经纬度
    private String dsstatus;  //状态 0：正常 1：通过 2：移除

    @Id
    @Column(name = "DSKEY", nullable = false, length = 50)
    public String getDskey() {
        return dskey;
    }

    public void setDskey(String dskey) {
        this.dskey = dskey;
    }

    @Basic
    @Column(name = "DSID", nullable = true, length = 50)
    public String getDsid() {
        return dsid;
    }

    public void setDsid(String dsid) {
        this.dsid = dsid;
    }

    @Basic
    @Column(name = "DSCOMID", nullable = true, length = 50)
    public String getDscomid() {
        return dscomid;
    }

    public void setDscomid(String dscomid) {
        this.dscomid = dscomid;
    }

    @Basic
    @Column(name = "DSDDID", nullable = true, length = 50)
    public String getDsddid() {
        return dsddid;
    }

    public void setDsddid(String dsddid) {
        this.dsddid = dsddid;
    }


    @Basic
    @Column(name = "DSDATE", nullable = true)
    public Date getDsdate() {
        return dsdate;
    }

    public void setDsdate(Date dsdate) {
        this.dsdate = dsdate;
    }

    @Basic
    @Column(name = "DSSTATUS", nullable = true, length = 5)
    public String getDsstatus() {
        return dsstatus;
    }

    public void setDsstatus(String dsstatus) {
        this.dsstatus = dsstatus;
    }

    @Basic
    @Column(name = "DSSCCID", nullable = true, length = 5)
    public String getDssccid() { return dssccid; }

    public void setDssccid(String dssccid) { this.dssccid = dssccid; }

    public String getDsaddress() {
        return dsaddress;
    }

    public void setDsaddress(String dsaddress) {
        this.dsaddress = dsaddress;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getDscid() {
        return dscid;
    }

    public void setDscid(String dscid) {
        this.dscid = dscid;
    }

    public String getDstype() {
        return dstype;
    }

    public void setDstype(String dstype) {
        this.dstype = dstype;
    }
}
