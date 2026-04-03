package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class PrizeCouponBean implements BaseBean
{
    private String couponId;
    private String couponName;
    private String couponFaceVal;
    private Timestamp staringTime;
    private Timestamp endTime;
    private String limitMoney;
    private Long couponNum;
    private String companyId;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponFaceVal() {
        return couponFaceVal;
    }

    public void setCouponFaceVal(String couponFaceVal) {
        this.couponFaceVal = couponFaceVal;
    }

    public Timestamp getStaringTime() {
        return staringTime;
    }

    public void setStaringTime(Timestamp staringTime) {
        this.staringTime = staringTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(String limitMoney) {
        this.limitMoney = limitMoney;
    }

    public Long getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Long couponNum) {
        this.couponNum = couponNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

}
