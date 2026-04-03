package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

import java.sql.Time;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class CustomerCouponBean implements BaseBean
{
    private String customerCouponId;
    private String couponName;
    private Long couponFaceVal;
    private String couponId;
    private String businessmenId;
    private Time startingTime;
    private Time endTime;
    private String staffId;
    private String source;

    public String getCustomerCouponId() {
        return customerCouponId;
    }

    public void setCustomerCouponId(String customerCouponId) {
        this.customerCouponId = customerCouponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Long getCouponFaceVal() {
        return couponFaceVal;
    }

    public void setCouponFaceVal(Long couponFaceVal) {
        this.couponFaceVal = couponFaceVal;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getBusinessmenId() {
        return businessmenId;
    }

    public void setBusinessmenId(String businessmenId) {
        this.businessmenId = businessmenId;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Time startingTime) {
        this.startingTime = startingTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
