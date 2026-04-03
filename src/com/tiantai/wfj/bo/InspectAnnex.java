package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 安全巡查附件
 */
public class InspectAnnex implements BaseBean, ExcelBean,java.io.Serializable  {
    private String iaid;
    private String iaKey;
    private String siid;  //安全巡查id SafetyInspect
    private String fildType; //文件类型： 01:图片  02:视频
    private String fildPath; //文件地址

    public InspectAnnex(String iaid, String iaKey, String siid, String fildType, String fildPath) {
        this.iaid = iaid;
        this.iaKey = iaKey;
        this.siid = siid;
        this.fildType = fildType;
        this.fildPath = fildPath;
    }

    public InspectAnnex() {

    }

    public String getIaid() {
        return iaid;
    }

    public void setIaid(String iaid) {
        this.iaid = iaid;
    }

    public String getIaKey() {
        return iaKey;
    }

    public void setIaKey(String iaKey) {
        this.iaKey = iaKey;
    }

    public String getSiid() {
        return siid;
    }

    public void setSiid(String siid) {
        this.siid = siid;
    }

    public String getFildType() {
        return fildType;
    }

    public void setFildType(String fildType) {
        this.fildType = fildType;
    }

    public String getFildPath() {
        return fildPath;
    }

    public void setFildPath(String fildPath) {
        this.fildPath = fildPath;
    }

    @Override
    public String[] properties() {
        return new String[0];
    }
}
