package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *mz 浏览历史记录
 */
public class StaffBrowseHistory implements java.io.Serializable,BaseBean {
    private String sbhkey;
    private String sbhid;
    private String sccid;
    private String modes;//1 商家 2 资讯 3 视频 4 商品 5应用
    private String ccompanyID;//商家ID
    private String ppidzx;//资讯
    private String videoID;//视频
    private String ppidsp;//商品
    private String appName;//e.g.招标，招聘，资讯，慈善等

    private Date createDate;

    public String getSbhkey() {
        return sbhkey;
    }

    public void setSbhkey(String sbhkey) {
        this.sbhkey = sbhkey;
    }

    public String getSbhid() {
        return sbhid;
    }

    public void setSbhid(String sbhid) {
        this.sbhid = sbhid;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public String getModes() {
        return modes;
    }

    public void setModes(String modes) {
        this.modes = modes;
    }

    public String getCcompanyID() {
        return ccompanyID;
    }

    public void setCcompanyID(String ccompanyID) {
        this.ccompanyID = ccompanyID;
    }

    public String getPpidzx() {
        return ppidzx;
    }

    public void setPpidzx(String ppidzx) {
        this.ppidzx = ppidzx;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getPpidsp() {
        return ppidsp;
    }

    public void setPpidsp(String ppidsp) {
        this.ppidsp = ppidsp;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
