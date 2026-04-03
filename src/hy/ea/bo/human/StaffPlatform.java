package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2018/7/31.
 */
public class StaffPlatform implements java.io.Serializable,BaseBean {
    private String platformKey;
    private String platformID;
    private String ccompanyID;
    private String staffID;

    public String getPlatformKey() {
        return platformKey;
    }

    public void setPlatformKey(String platformKey) {
        this.platformKey = platformKey;
    }

    public String getPlatformID() {
        return platformID;
    }

    public void setPlatformID(String platformID) {
        this.platformID = platformID;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
}
