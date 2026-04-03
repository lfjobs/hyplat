package com.tiantai.wfj.edmandServe.bo;

import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lf on 2017-07-12.
 * 需求详情管理
 */
@Entity
@Table(name = "DT_DEMAND_DETAIL", schema = "HYPLAT", catalog = "")
public class DemandDetail implements BaseBean {
    private String ddkey;
    private String ddid;
    private String ddsccid;  //发布需求人员帐号
    private String ddstaffid; //发布需求人员id
    private String ddcid;//关联id (来源不同关联表不同,来源1:发布主体是个人关联staff 2:发布主体是企业关联company)
    private String ddtype; //来源 1:个人 2:企业
    private String ddtitle;  //标题
    private String ddremark;  //备注
    private Date ddadddate; //添加时间
    private Date ddexpectdate;  //期望时间
    private String ddexpectprice; //期望价格
    private String ddcontactname;  //联系人
    private String ddcontactphone;  //联系电话
    private String ddaddress;  //地址
    private String ddstatus;  //状态 0：抢单中 1：已确认订单 2：过期 3：移除 4：结算
    private Character ddBool;  //状态 Y:有空缺 N:饱和（抢单数据5条为饱和）
    private Integer dscount; //抢单人数
    private String coordinate; //地址经纬度
    private String ddscodeid; //行业类别id
    private String ddworktype; //行业类别名称
    private String photo; //图片地址
    /********传值用不存数据库********/
    private String ddexdate;//预期时间

    public DemandDetail() {
        super();
    }

    public DemandDetail(String ddid, String ddsccid, String ddtitle, String ddremark, Date ddexpectdate, String ddexpectprice,
                        String ddcontactname, String ddcontactphone, String ddaddress,
                        String coordinate, String ddworktype) {
        super();
        this.ddid = ddid;
        this.ddsccid = ddsccid;
        this.ddtitle = ddtitle;
        this.ddremark = ddremark;
        this.ddremark = ddremark;
        this.ddexdate = Utilities.getDateString(ddexpectdate, "yyyy-MM-dd");
        this.ddexpectprice = (ddexpectprice == null ? "" : ddexpectprice);
        this.ddcontactname = ddcontactname;
        this.ddcontactphone = ddcontactphone;
        this.ddaddress = ddaddress;
        this.coordinate = coordinate;
        this.ddworktype = ddworktype;
        this.ddexpectdate = new Date();
        this.ddadddate = new Date();
        this.ddstaffid="";
        this.ddkey = "";
        this.ddscodeid = "";
        this.ddBool = new Character(' ');
        this.ddscodeid = "";
        this.ddstatus = "";
        this.dscount = 0;
        this.photo = (photo == null ? "" : photo);
    }

    public DemandDetail(String ddid, String ddsccid) {
        super();
        this.ddid = ddid;
        this.ddsccid = ddsccid;
    }

    @Id
    @Column(name = "DDKEY", nullable = false, length = 50)
    public String getDdkey() {
        return ddkey;
    }

    public void setDdkey(String ddkey) {
        this.ddkey = ddkey;
    }

    @Basic
    @Column(name = "DDID", nullable = true, length = 50)
    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid;
    }

    @Basic
    @Column(name = "DDSCCID", nullable = true, length = 50)
    public String getDdsccid() {
        return ddsccid;
    }

    public void setDdsccid(String ddsccid) {
        this.ddsccid = ddsccid;
    }

    @Basic
    @Column(name = "DDTITLE", nullable = true, length = 50)
    public String getDdtitle() {
        return ddtitle;
    }

    public void setDdtitle(String ddtitle) {
        this.ddtitle = ddtitle;
    }

    @Basic
    @Column(name = "DDREMARK", nullable = true, length = 100)
    public String getDdremark() {
        return ddremark;
    }

    public void setDdremark(String ddremark) {
        this.ddremark = ddremark;
    }

    @Basic
    @Column(name = "DDEXPECTDATE", nullable = true)
    public Date getDdexpectdate() {
        return ddexpectdate;
    }

    public void setDdexpectdate(Date ddexpectdate) {
        this.ddexpectdate = ddexpectdate;
    }

    @Basic
    @Column(name = "DDCONTACTNAME", nullable = true, length = 20)
    public String getDdcontactname() {
        return ddcontactname;
    }

    public void setDdcontactname(String ddcontactname) {
        this.ddcontactname = ddcontactname;
    }

    @Basic
    @Column(name = "DDCONTACTPHONE", nullable = true, length = 20)
    public String getDdcontactphone() {
        return ddcontactphone;
    }

    public void setDdcontactphone(String ddcontactphone) {
        this.ddcontactphone = ddcontactphone;
    }

    @Basic
    @Column(name = "DDADDRESS", nullable = true, length = 100)
    public String getDdaddress() {
        return ddaddress;
    }

    public void setDdaddress(String ddaddress) {
        this.ddaddress = ddaddress;
    }

    @Basic
    @Column(name = "DDSTATUS", nullable = true, length = 5)
    public String getDdstatus() {
        return ddstatus;
    }

    public void setDdstatus(String ddstatus) {
        this.ddstatus = ddstatus;
    }

    @Basic
    @Column(name = "DDADDDATE", nullable = true)
    public Date getDdadddate() {
        return ddadddate;
    }

    public void setDdadddate(Date ddadddate) {
        this.ddadddate = ddadddate;
    }

    @Basic
    @Column(name = "DDBOOL", nullable = true, length = 1)
    public Character getDdBool() {
        return ddBool;
    }

    public void setDdBool(Character ddBool) {
        this.ddBool = ddBool;
    }

    @Basic
    @Column(name = "COORDINATE", nullable = true, length = 50)
    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @Basic
    @Column(name = "DDSCODEID", nullable = true, length = 50)
    public String getDdscodeid() {
        return ddscodeid;
    }

    public void setDdscodeid(String ddscodeid) {
        this.ddscodeid = ddscodeid;
    }

    @Basic
    @Column(name = "DDWORKTYPE", nullable = true, length = 50)
    public String getDdworktype() {
        return ddworktype;
    }

    public void setDdworktype(String ddworktype) {
        this.ddworktype = ddworktype;
    }

    @Basic
    @Column(name = "DDEXPECTPRICE", nullable = true, length = 10)
    public String getDdexpectprice() {
        return ddexpectprice;
    }

    public void setDdexpectprice(String ddexpectprice) {
        this.ddexpectprice = ddexpectprice;
    }

    @Basic
    @Column(name = "DSCOUNT", nullable = true, length = 1)
    public Integer getDscount() {
        return dscount;
    }

    public void setDscount(Integer dscount) {
        this.dscount = dscount;
    }

    public String getDdexdate() {
        return ddexdate;
    }

    public void setDdexdate(String ddexdate) {
        this.ddexdate = ddexdate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDdstaffid() {
        return ddstaffid;
    }

    public void setDdstaffid(String ddstaffid) {
        this.ddstaffid = ddstaffid;
    }

    public String getDdcid() {
        return ddcid;
    }

    public void setDdcid(String ddcid) {
        this.ddcid = ddcid;
    }

    public String getDdtype() {
        return ddtype;
    }

    public void setDdtype(String ddtype) {
        this.ddtype = ddtype;
    }
}
