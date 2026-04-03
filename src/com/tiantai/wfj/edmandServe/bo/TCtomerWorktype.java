package com.tiantai.wfj.edmandServe.bo;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lf on 2017-07-20.
 * 微分金帐号工种管理
 */
@Entity
@Table(name = "T_CTOMER_WORKTYPE", schema = "HYPLAT", catalog = "")
public class TCtomerWorktype implements BaseBean{
    private String cwkey;
    private String cwid;
    private String cwcustid; //添加工种帐号id
    private String cwstaffid;//添加工种人员id
    private String cwstaffname;//添加工种人员姓名
    private String cwcid;//关联id (来源不同关联表不同,来源1:主体是个人关联staff 2:主体是企业关联company)
    private String cwtype; //来源 1:个人 2:企业
    private String cwcode;//身份证号
    private String cwphone;//手机号
    private String cwcredentialsID;//证件id
    private String cwcredenname;//证件
    private String cwscodeid;  //行业类别（工种）id
    private String cwvalue; //行业类别（工种）名称
    private String ddaddress;  //地址
    private String coordinate; //地址经纬度
    private Date cwdate; //添加时间
    private String cwstatus; //状态 0：审核通过 1：移除 2：审核驳回 3:审核中

    @Id
    @Column(name = "CWKEY", nullable = false, length = 50)
    public String getCwkey() {
        return cwkey;
    }

    public void setCwkey(String cwkey) {
        this.cwkey = cwkey;
    }

    @Basic
    @Column(name = "CWID", nullable = true, length = 50)
    public String getCwid() {
        return cwid;
    }

    public void setCwid(String cwid) {
        this.cwid = cwid;
    }

    @Basic
    @Column(name = "CWCUSTID", nullable = true, length = 50)
    public String getCwcustid() {
        return cwcustid;
    }

    public void setCwcustid(String cwcustid) {
        this.cwcustid = cwcustid;
    }

    @Basic
    @Column(name = "CWSCODEID", nullable = true, length = 50)
    public String getCwscodeid() {
        return cwscodeid;
    }

    public void setCwscodeid(String cwscodeid) {
        this.cwscodeid = cwscodeid;
    }

    @Basic
    @Column(name = "CWVALUE", nullable = true, length = 50)
    public String getCwvalue() {
        return cwvalue;
    }

    public void setCwvalue(String cwvalue) {
        this.cwvalue = cwvalue;
    }

    @Basic
    @Column(name = "CWDATE", nullable = true)
    public Date getCwdate() {
        return cwdate;
    }

    public void setCwdate(Date cwdate) {
        this.cwdate = cwdate;
    }

    @Basic
    @Column(name = "CWSTATUS", nullable = true, length = 5)
    public String getCwstatus() {
        return cwstatus;
    }

    public void setCwstatus(String cwstatus) {
        this.cwstatus = cwstatus;
    }

    @Basic
    @Column(name = "cwstaffid", nullable = true, length = 50)
    public String getCwstaffid() {
        return cwstaffid;
    }

    public void setCwstaffid(String cwstaffid) {
        this.cwstaffid = cwstaffid;
    }

    @Basic
    @Column(name = "cwphone", nullable = true, length = 50)
    public String getCwphone() {
        return cwphone;
    }

    public void setCwphone(String cwphone) {
        this.cwphone = cwphone;
    }

    @Basic
    @Column(name = "cwcredentialsID", nullable = true, length = 50)
    public String getCwcredentialsID() {
        return cwcredentialsID;
    }

    public void setCwcredentialsID(String cwcredentialsID) {
        this.cwcredentialsID = cwcredentialsID;
    }

    @Basic
    @Column(name = "ddaddress", nullable = true, length = 50)
    public String getDdaddress() {
        return ddaddress;
    }

    public void setDdaddress(String ddaddress) {
        this.ddaddress = ddaddress;
    }

    @Basic
    @Column(name = "coordinate", nullable = true, length = 50)
    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getCwstaffname() {
        return cwstaffname;
    }

    public void setCwstaffname(String cwstaffname) {
        this.cwstaffname = cwstaffname;
    }

    public String getCwcode() {
        return cwcode;
    }

    public void setCwcode(String cwcode) {
        this.cwcode = cwcode;
    }

    public String getCwcredenname() {
        return cwcredenname;
    }

    public void setCwcredenname(String cwcredenname) {
        this.cwcredenname = cwcredenname;
    }

    public String getCwcid() {
        return cwcid;
    }

    public void setCwcid(String cwcid) {
        this.cwcid = cwcid;
    }

    public String getCwtype() {
        return cwtype;
    }

    public void setCwtype(String cwtype) {
        this.cwtype = cwtype;
    }
}
