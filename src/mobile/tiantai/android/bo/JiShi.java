package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/2 0002.
 * 计算时间表
 */
public class JiShi implements BaseBean ,java.io.Serializable{

    private String jskey;           //计时的key
    private String jsid;           //计时的id
    private String deviceid;      // 车辆的编号
    private Date  beginDate;     // 开始时间
    private Date  endDate;      //结束时间
    private String sccid;    //骑车人员的sccid
    private String clmessage; //车辆 开锁 0    车辆关锁 1


    public String getJskey() {
        return jskey;
    }

    public void setJskey(String jskey) {
        this.jskey = jskey;
    }

    public String getJsid() {
        return jsid;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public String getClmessage() {
        return clmessage;
    }

    public void setClmessage(String clmessage) {
        this.clmessage = clmessage;
    }
}
