package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 传递称重数据
 */
public class ScaleWeight implements BaseBean {
    private String swkey;
    private String swid;
    private String companyId; //公司id
    private String scaleCoding; //秤盘编号
    private String containerCoding;//货柜终端机编号
    private String hgcode;//货柜库房编号
    private double weight;//重量
    private Date time;//时间


    public String getSwkey() {
        return swkey;
    }

    public void setSwkey(String swkey) {
        this.swkey = swkey;
    }

    public String getSwid() {
        return swid;
    }

    public void setSwid(String swid) {
        this.swid = swid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getScaleCoding() {
        return scaleCoding;
    }

    public void setScaleCoding(String scaleCoding) {
        this.scaleCoding = scaleCoding;
    }

    public String getContainerCoding() {
        return containerCoding;
    }

    public void setContainerCoding(String containerCoding) {
        this.containerCoding = containerCoding;
    }

    public String getHgcode() {
        return hgcode;
    }

    public void setHgcode(String hgcode) {
        this.hgcode = hgcode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return " \"ScaleWeight\":{" +
                " \"swkey\":\"" +swkey+ '\"' +
                ", \"swid\":\"" +swid+ '\"' +
                ", \"companyId\":\"" +companyId+ '\"' +
                ", \"scaleCoding\":\"" +scaleCoding+ '\"' +
                ", \"containerCoding\":\"" +containerCoding+ '\"' +
                ", \"weight:" + weight +
                ", \"time:" + time +
                "},";
    }
}
