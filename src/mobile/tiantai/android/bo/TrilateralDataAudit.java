package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

public class TrilateralDataAudit implements BaseBean {
    private String id;
    private String trilateralDataId;

    //发送人id
    private String sendStaffId;
    //发送人名称
    private String sendStaffName;


    //审核人id
    private String auditStaffId;
    //审核人名称
    private String auditStaffName;
    //审核意见
    private String auditOpinion;
    //审核意见详情
    private String auditStatus;
    //时间
    private String auditTime;
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrilateralDataId() {
        return trilateralDataId;
    }

    public void setTrilateralDataId(String trilateralDataId) {
        this.trilateralDataId = trilateralDataId;
    }

    public String getSendStaffId() {
        return sendStaffId;
    }

    public void setSendStaffId(String sendStaffId) {
        this.sendStaffId = sendStaffId;
    }

    public String getSendStaffName() {
        return sendStaffName;
    }

    public void setSendStaffName(String sendStaffName) {
        this.sendStaffName = sendStaffName;
    }

    public String getAuditStaffId() {
        return auditStaffId;
    }

    public void setAuditStaffId(String auditStaffId) {
        this.auditStaffId = auditStaffId;
    }

    public String getAuditStaffName() {
        return auditStaffName;
    }

    public void setAuditStaffName(String auditStaffName) {
        this.auditStaffName = auditStaffName;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
