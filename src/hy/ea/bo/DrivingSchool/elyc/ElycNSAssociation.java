package hy.ea.bo.DrivingSchool.elyc;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * xgb
 * 驾校通知学员关联表
 */
public class ElycNSAssociation implements BaseBean, Serializable {
    private String ensaKey;
    private String ensaId;
    private String edsnId;//驾校通知id
    private String staffId;//驾校学员id

    public String getEnsaKey() {
        return ensaKey;
    }

    public void setEnsaKey(String ensaKey) {
        this.ensaKey = ensaKey;
    }

    public String getEnsaId() {
        return ensaId;
    }

    public void setEnsaId(String ensaId) {
        this.ensaId = ensaId;
    }

    public String getEdsnId() {
        return edsnId;
    }

    public void setEdsnId(String edsnId) {
        this.edsnId = edsnId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
