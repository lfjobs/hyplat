package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 
 * 快捷应用记录表
 * @author ttst_wk
 *
 */
public class KuaiJie implements BaseBean ,java.io.Serializable{

	private String  kjkey;
	private String  kjid;//快捷应用ID
	private String  staffid; //人员的staffid
	private String  moKauiMing ; //拥有的功能名字
    private String  tupian; //功能的图标
    private Date   kdate;     //生成记录时间

    public KuaiJie() {
    }

    public KuaiJie(String kjkey, String kjid, String staffid, String moKauiMing, String tupian, Date kdate) {
        this.kjkey = kjkey;
        this.kjid = kjid;
        this.staffid = staffid;
        this.moKauiMing = moKauiMing;
        this.tupian = tupian;
        this.kdate = kdate;
    }

    public String getKjkey() {
        return kjkey;
    }

    public void setKjkey(String kjkey) {
        this.kjkey = kjkey;
    }

    public String getKjid() {
        return kjid;
    }

    public void setKjid(String kjid) {
        this.kjid = kjid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getMoKauiMing() {
        return moKauiMing;
    }

    public void setMoKauiMing(String moKauiMing) {
        this.moKauiMing = moKauiMing;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public Date getKdate() {
        return kdate;
    }

    public void setKdate(Date kdate) {
        this.kdate = kdate;
    }
}
