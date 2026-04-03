package hy.ea.bo.lottery;
import hy.plat.bo.BaseBean;
/**
 * Created by jcy on 2017-07-19.
 *票券
 */
public class Ticket implements BaseBean{
    private String tkey;
    private String tid;
    private String tname;       //票券名称
    private String tprice;      //票券价格
    private String tcount;      //票券数量
    private String ttype;       //票券类型
    private String ppid;        //产品id
    private String activityId;  //活动

    public String getTkey() {
        return tkey;
    }
    public void setTkey(String tkey) {
        this.tkey = tkey;
    }
    public String getTid() {
        return tid;
    }
    public void setTid(String tid) {
        this.tid = tid;
    }
    public String getTname() {
        return tname;
    }
    public void setTname(String tname) {
        this.tname = tname;
    }
    public String getTprice() {
        return tprice;
    }
    public void setTprice(String tprice) {
        this.tprice = tprice;
    }
    public String getTcount() {
        return tcount;
    }
    public void setTcount(String tcount) {
        this.tcount = tcount;
    }
    public String getTtype() {
        return ttype;
    }
    public void setTtype(String ttype) {
        this.ttype = ttype;
    }
    public String getPpid() {
        return ppid;
    }
    public void setPpid(String ppid) {
        this.ppid = ppid;
    }
    public String getActivityId() {
        return activityId;
    }
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
