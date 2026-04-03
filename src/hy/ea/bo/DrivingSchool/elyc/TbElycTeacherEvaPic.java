package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * mz
 * 教练评价关联图片
 */
public class TbElycTeacherEvaPic implements BaseBean,Serializable {
    private String etpKey;
    private String etpId;
    private String eacherEvaluatetId;//教练评价表ID
    private String picpath;//图片路径

    public String getEtpKey() {
        return etpKey;
    }

    public void setEtpKey(String etpKey) {
        this.etpKey = etpKey;
    }

    public String getEtpId() {
        return etpId;
    }

    public void setEtpId(String etpId) {
        this.etpId = etpId;
    }

    public String getEacherEvaluatetId() {
        return eacherEvaluatetId;
    }

    public void setEacherEvaluatetId(String eacherEvaluatetId) {
        this.eacherEvaluatetId = eacherEvaluatetId;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }
}
