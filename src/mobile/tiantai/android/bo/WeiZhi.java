package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/15 0015.
 * 单车位置获取
 */
public class WeiZhi implements BaseBean,java.io.Serializable {
    private  String  weizhikey;
    private  String  weizhiid;
    private  String  userid; //用户唯一标识，十六位数字或者字符
    private  int  cmd;  // 0表示位置信息由开锁动作触发，1表示位置信息由关锁动作触发，2表示位置信息由定时回传触发
    private  String  deviceId; //设备的编号
    private  int  battery;  //电池电量剩余百分比,正表示充电，负表示放电
    private  int  bike;   //固定为 0
    private  int  lockstatus;  //锁的状态，值：0-锁车、1-开锁、2-异常
    private  float  lng;         //智能锁位置的经度，单位是度
    private  float  lat;          //智能锁位置的纬度，单位是度
    private  int  gx;              //电池电压，单位是 0.01 伏
    private  int  gy;           //充电电压，单位是 0.01 伏
    private  int  gz;          //求余 100 的值为卫星数，除以 100 的值为信号强度
    private   Date   wtime;       //位置信息的时间
    private  int  serialnum;   //用户下发指令的流水号，-1表示不是用户下发的指令应答，而是普通定时回传的位置信息
    private  String  sign ;   //以上字段值+userkey 组成的字符串后进行 uft-8 编码的字节流进行 md5 计算
    private  String sccid;    //购买此车辆的人员sccid


    public String getWeizhikey() {
        return weizhikey;
    }

    public void setWeizhikey(String weizhikey) {
        this.weizhikey = weizhikey;
    }

    public String getWeizhiid() {
        return weizhiid;
    }

    public void setWeizhiid(String weizhiid) {
        this.weizhiid = weizhiid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getBike() {
        return bike;
    }

    public void setBike(int bike) {
        this.bike = bike;
    }

    public int getLockstatus() {
        return lockstatus;
    }

    public void setLockstatus(int lockstatus) {
        this.lockstatus = lockstatus;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public int getGx() {
        return gx;
    }

    public void setGx(int gx) {
        this.gx = gx;
    }

    public int getGy() {
        return gy;
    }

    public void setGy(int gy) {
        this.gy = gy;
    }

    public int getGz() {
        return gz;
    }

    public void setGz(int gz) {
        this.gz = gz;
    }

    public Date getWtime() {
        return wtime;
    }

    public void setWtime(Date wtime) {
        this.wtime = wtime;
    }

    public int getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(int serialnum) {
        this.serialnum = serialnum;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }
}
