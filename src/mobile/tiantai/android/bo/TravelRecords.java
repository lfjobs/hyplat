package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/6 0006.  行车记录表
 */
public class TravelRecords  implements BaseBean {

    private String trkey;           //行程的的key
    private String trid;           //行程的id
    private String sccid;        //人员的sccid
    private Date beginDate ;     //开始行驶的时间
    private Date endDate ;        //结束行驶的时间
    private String scxiaoshi ;       //行驶的小时数
    private String scfenzhong;         //行驶的分钟数
    private String trMoney ;        //行驶的总金金币数
    private String deviceid ;       //骑行的车辆编码号

    public String getTrkey() {
        return trkey;
    }

    public void setTrkey(String trkey) {
        this.trkey = trkey;
    }

    public String getTrid() {
        return trid;
    }

    public void setTrid(String trid) {
        this.trid = trid;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
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

    public String getScxiaoshi() {
        return scxiaoshi;
    }

    public void setScxiaoshi(String scxiaoshi) {
        this.scxiaoshi = scxiaoshi;
    }

    public String getScfenzhong() {
        return scfenzhong;
    }

    public void setScfenzhong(String scfenzhong) {
        this.scfenzhong = scfenzhong;
    }

    public String getTrMoney() {
        return trMoney;
    }

    public void setTrMoney(String trMoney) {
        this.trMoney = trMoney;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
