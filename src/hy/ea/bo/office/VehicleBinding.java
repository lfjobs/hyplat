package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 车辆绑定人员关系
 *
 * @author xgb
 */
public class VehicleBinding implements BaseBean {
    private String vbID;
    private String vbKey;
    private String carID;//车辆id
    private String staffID;//人员id
    private Date bindingTime;//绑定时间
    private Date unbundlingTime;//解绑时间
    private String status;//00:正常,01:已删除
    private String isDefault;//是否默认使用扣钱 00 不默认，01 默认

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUnbundlingTime() {

        return unbundlingTime;
    }

    public void setUnbundlingTime(Date unbundlingTime) {
        this.unbundlingTime = unbundlingTime;
    }

    public Date getBindingTime() {

        return bindingTime;
    }

    public void setBindingTime(Date bindingTime) {
        this.bindingTime = bindingTime;
    }

    public String getStaffID() {

        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getVbKey() {
        return vbKey;
    }

    public void setVbKey(String vbKey) {
        this.vbKey = vbKey;
    }

    public String getVbID() {

        return vbID;
    }

    public void setVbID(String vbID) {
        this.vbID = vbID;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
