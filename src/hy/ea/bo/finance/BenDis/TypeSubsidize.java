package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import javax.persistence.*;

/**
 * Created by Administrator on 2018-01-06.
 */
@Entity
@Table(name = "DT_TYPE_SUBSIDIZE", schema = "HYPLAT", catalog = "")
public class TypeSubsidize implements BaseBean,java.io.Serializable{
    private String stkey;
    private String stid;
    private String stname;
    private Byte stnum;

    @Id
    @Column(name = "STKEY", nullable = false, length = 50)
    public String getStkey() {
        return stkey;
    }

    public void setStkey(String stkey) {
        this.stkey = stkey;
    }

    @Basic
    @Column(name = "STID", nullable = true, length = 50)
    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }

    @Basic
    @Column(name = "STNAME", nullable = true, length = 50)
    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    @Basic
    @Column(name = "STNUM", nullable = true, precision = 0)
    public Byte getStnum() {
        return stnum;
    }

    public void setStnum(Byte stnum) {
        this.stnum = stnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeSubsidize that = (TypeSubsidize) o;

        if (stkey != null ? !stkey.equals(that.stkey) : that.stkey != null) return false;
        if (stid != null ? !stid.equals(that.stid) : that.stid != null) return false;
        if (stname != null ? !stname.equals(that.stname) : that.stname != null) return false;
        if (stnum != null ? !stnum.equals(that.stnum) : that.stnum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stkey != null ? stkey.hashCode() : 0;
        result = 31 * result + (stid != null ? stid.hashCode() : 0);
        result = 31 * result + (stname != null ? stname.hashCode() : 0);
        result = 31 * result + (stnum != null ? stnum.hashCode() : 0);
        return result;
    }
}
