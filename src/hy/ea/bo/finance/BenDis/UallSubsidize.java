package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Administrator on 2018-01-06.
 */
@Entity
@Table(name = "DT_UALL_SUBSIDIZE", schema = "HYPLAT", catalog = "")
public class UallSubsidize implements BaseBean,java.io.Serializable{
    private String uskey;
    private String usid;
    private String ssid; //消费补助设置表id
    private String psccid; //上级帐号id
    private String sccid; //当前帐号id
    private Long slevel; //当前帐号级数
    private Long countNum; //当前帐号在当前行业所占位数
    private Date adddate; //添加时间

    @Id
    @Column(name = "USKEY", nullable = false, length = 50)
    public String getUskey() {
        return uskey;
    }

    public void setUskey(String uskey) {
        this.uskey = uskey;
    }

    @Basic
    @Column(name = "USID", nullable = true, length = 50)
    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    @Basic
    @Column(name = "SSID", nullable = true, length = 50)
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    @Basic
    @Column(name = "PSCCID", nullable = true, length = 50)
    public String getPsccid() {
        return psccid;
    }

    public void setPsccid(String psccid) {
        this.psccid = psccid;
    }

    @Basic
    @Column(name = "SCCID", nullable = true, length = 50)
    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    @Basic
    @Column(name = "SLEVEL", nullable = true, precision = 0)
    public Long getSlevel() {
        return slevel;
    }

    public void setSlevel(Long slevel) {
        this.slevel = slevel;
    }

    @Basic
    @Column(name = "ADDDATE", nullable = true)
    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    @Basic
    @Column(name = "CountNum", nullable = true)
    public Long getCountNum() {
        return countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UallSubsidize that = (UallSubsidize) o;

        if (uskey != null ? !uskey.equals(that.uskey) : that.uskey != null) return false;
        if (usid != null ? !usid.equals(that.usid) : that.usid != null) return false;
        if (ssid != null ? !ssid.equals(that.ssid) : that.ssid != null) return false;
        if (psccid != null ? !psccid.equals(that.psccid) : that.psccid != null) return false;
        if (sccid != null ? !sccid.equals(that.sccid) : that.sccid != null) return false;
        if (slevel != null ? !slevel.equals(that.slevel) : that.slevel != null) return false;
        if (adddate != null ? !adddate.equals(that.adddate) : that.adddate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uskey != null ? uskey.hashCode() : 0;
        result = 31 * result + (usid != null ? usid.hashCode() : 0);
        result = 31 * result + (ssid != null ? ssid.hashCode() : 0);
        result = 31 * result + (psccid != null ? psccid.hashCode() : 0);
        result = 31 * result + (sccid != null ? sccid.hashCode() : 0);
        result = 31 * result + (slevel != null ? slevel.hashCode() : 0);
        result = 31 * result + (adddate != null ? adddate.hashCode() : 0);
        return result;
    }
}
