package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by ljc on 2017/3/13 0013.
 *  招商申请
 */
public class InvestApply implements BaseBean
{
    private String inapKey;
    private String inapId;
    //申请产品代理id
    private String susid;
    //申请人sccid
    private String sccId;
    //审核00拒绝01同意
    private String agree;
    //生成时间
    private Date inapDate;
    //区域产品id
    private String areappid;
    //区域名称
    private String areaName;

    public String getInapKey() {
        return inapKey;
    }

    public void setInapKey(String inapKey) {
        this.inapKey = inapKey;
    }

    public String getInapId() {
        return inapId;
    }

    public void setInapId(String inapId) {
        this.inapId = inapId;
    }

    public String getSusid() {
        return susid;
    }

    public void setSusid(String susid) {
        this.susid = susid;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public Date getInapDate() {
        return inapDate;
    }

    public String getAreappid() {
        return areappid;
    }

    public void setAreappid(String areappid) {
        this.areappid = areappid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setInapDate(Date inapDate) {
        this.inapDate = inapDate;
    }
}
