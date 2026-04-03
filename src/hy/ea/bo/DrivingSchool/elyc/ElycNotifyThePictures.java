package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * xgb
 * 驾校通知图片
 */
public class ElycNotifyThePictures implements BaseBean, Serializable {
    private String entpKey;
    private String entpId;
    private String edsnId;//驾校通知id
    private String picpath;//图片路径

    public String getEntpKey() {
        return entpKey;
    }

    public void setEntpKey(String entpKey) {
        this.entpKey = entpKey;
    }

    public String getEntpId() {
        return entpId;
    }

    public void setEntpId(String entpId) {
        this.entpId = entpId;
    }

    public String getEdsnId() {
        return edsnId;
    }

    public void setEdsnId(String edsnId) {
        this.edsnId = edsnId;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }
}
